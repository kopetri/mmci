package org.spongycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERConstructedOctetString;
import org.spongycastle.asn1.BEROutputStream;
import org.spongycastle.asn1.DERBMPString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.pkcs.AuthenticatedSafe;
import org.spongycastle.asn1.pkcs.CertBag;
import org.spongycastle.asn1.pkcs.ContentInfo;
import org.spongycastle.asn1.pkcs.EncryptedData;
import org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.spongycastle.asn1.pkcs.MacData;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.Pfx;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.SafeBag;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.jce.interfaces.BCKeyStore;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class JDKPKCS12KeyStore
  extends KeyStoreSpi
  implements PKCSObjectIdentifiers, X509ObjectIdentifiers, BCKeyStore
{
  static final int CERTIFICATE = 1;
  static final int KEY = 2;
  static final int KEY_PRIVATE = 0;
  static final int KEY_PUBLIC = 1;
  static final int KEY_SECRET = 2;
  private static final int MIN_ITERATIONS = 1024;
  static final int NULL = 0;
  private static final int SALT_SIZE = 20;
  static final int SEALED = 4;
  static final int SECRET = 3;
  private static final Provider bcProvider = new BouncyCastleProvider();
  private ASN1ObjectIdentifier certAlgorithm;
  private CertificateFactory certFact;
  private IgnoresCaseHashtable certs = new IgnoresCaseHashtable(null);
  private Hashtable chainCerts = new Hashtable();
  private ASN1ObjectIdentifier keyAlgorithm;
  private Hashtable keyCerts = new Hashtable();
  private IgnoresCaseHashtable keys = new IgnoresCaseHashtable(null);
  private Hashtable localIds = new Hashtable();
  protected SecureRandom random = new SecureRandom();
  
  public JDKPKCS12KeyStore(Provider paramProvider, ASN1ObjectIdentifier paramASN1ObjectIdentifier1, ASN1ObjectIdentifier paramASN1ObjectIdentifier2)
  {
    this.keyAlgorithm = paramASN1ObjectIdentifier1;
    this.certAlgorithm = paramASN1ObjectIdentifier2;
    if (paramProvider != null) {}
    try
    {
      this.certFact = CertificateFactory.getInstance("X.509", paramProvider);
      return;
    }
    catch (Exception localException)
    {
      throw new IllegalArgumentException("can't create cert factory - " + localException.toString());
    }
    this.certFact = CertificateFactory.getInstance("X.509");
  }
  
  private static byte[] calculatePbeMac(ASN1ObjectIdentifier paramASN1ObjectIdentifier, byte[] paramArrayOfByte1, int paramInt, char[] paramArrayOfChar, boolean paramBoolean, byte[] paramArrayOfByte2)
    throws Exception
  {
    SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance(paramASN1ObjectIdentifier.getId(), bcProvider);
    PBEParameterSpec localPBEParameterSpec = new PBEParameterSpec(paramArrayOfByte1, paramInt);
    BCPBEKey localBCPBEKey = (BCPBEKey)localSecretKeyFactory.generateSecret(new PBEKeySpec(paramArrayOfChar));
    localBCPBEKey.setTryWrongPKCS12Zero(paramBoolean);
    Mac localMac = Mac.getInstance(paramASN1ObjectIdentifier.getId(), bcProvider);
    localMac.init(localBCPBEKey, localPBEParameterSpec);
    localMac.update(paramArrayOfByte2);
    return localMac.doFinal();
  }
  
  private SubjectKeyIdentifier createSubjectKeyId(PublicKey paramPublicKey)
  {
    try
    {
      SubjectKeyIdentifier localSubjectKeyIdentifier = new SubjectKeyIdentifier(new SubjectPublicKeyInfo((ASN1Sequence)ASN1Primitive.fromByteArray(paramPublicKey.getEncoded())));
      return localSubjectKeyIdentifier;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("error creating key");
    }
  }
  
  private void doStore(OutputStream paramOutputStream, char[] paramArrayOfChar, boolean paramBoolean)
    throws IOException
  {
    if (paramArrayOfChar == null) {
      throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
    }
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    Enumeration localEnumeration1 = this.keys.keys();
    while (localEnumeration1.hasMoreElements())
    {
      byte[] arrayOfByte8 = new byte[20];
      this.random.nextBytes(arrayOfByte8);
      String str3 = (String)localEnumeration1.nextElement();
      PrivateKey localPrivateKey = (PrivateKey)this.keys.get(str3);
      PKCS12PBEParams localPKCS12PBEParams2 = new PKCS12PBEParams(arrayOfByte8, 1024);
      byte[] arrayOfByte9 = wrapKey(this.keyAlgorithm.getId(), localPrivateKey, localPKCS12PBEParams2, paramArrayOfChar);
      AlgorithmIdentifier localAlgorithmIdentifier3 = new AlgorithmIdentifier(this.keyAlgorithm, localPKCS12PBEParams2.toASN1Primitive());
      EncryptedPrivateKeyInfo localEncryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(localAlgorithmIdentifier3, arrayOfByte9);
      ASN1EncodableVector localASN1EncodableVector12 = new ASN1EncodableVector();
      boolean bool3 = localPrivateKey instanceof PKCS12BagAttributeCarrier;
      int k = 0;
      if (bool3)
      {
        PKCS12BagAttributeCarrier localPKCS12BagAttributeCarrier4 = (PKCS12BagAttributeCarrier)localPrivateKey;
        DERBMPString localDERBMPString3 = (DERBMPString)localPKCS12BagAttributeCarrier4.getBagAttribute(pkcs_9_at_friendlyName);
        if ((localDERBMPString3 == null) || (!localDERBMPString3.getString().equals(str3))) {
          localPKCS12BagAttributeCarrier4.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(str3));
        }
        if (localPKCS12BagAttributeCarrier4.getBagAttribute(pkcs_9_at_localKeyId) == null)
        {
          Certificate localCertificate5 = engineGetCertificate(str3);
          localPKCS12BagAttributeCarrier4.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(localCertificate5.getPublicKey()));
        }
        Enumeration localEnumeration8 = localPKCS12BagAttributeCarrier4.getBagAttributeKeys();
        while (localEnumeration8.hasMoreElements())
        {
          ASN1ObjectIdentifier localASN1ObjectIdentifier4 = (ASN1ObjectIdentifier)localEnumeration8.nextElement();
          ASN1EncodableVector localASN1EncodableVector15 = new ASN1EncodableVector();
          localASN1EncodableVector15.add(localASN1ObjectIdentifier4);
          localASN1EncodableVector15.add(new DERSet(localPKCS12BagAttributeCarrier4.getBagAttribute(localASN1ObjectIdentifier4)));
          k = 1;
          localASN1EncodableVector12.add(new DERSequence(localASN1EncodableVector15));
        }
      }
      if (k == 0)
      {
        ASN1EncodableVector localASN1EncodableVector13 = new ASN1EncodableVector();
        Certificate localCertificate4 = engineGetCertificate(str3);
        localASN1EncodableVector13.add(pkcs_9_at_localKeyId);
        localASN1EncodableVector13.add(new DERSet(createSubjectKeyId(localCertificate4.getPublicKey())));
        localASN1EncodableVector12.add(new DERSequence(localASN1EncodableVector13));
        ASN1EncodableVector localASN1EncodableVector14 = new ASN1EncodableVector();
        localASN1EncodableVector14.add(pkcs_9_at_friendlyName);
        localASN1EncodableVector14.add(new DERSet(new DERBMPString(str3)));
        localASN1EncodableVector12.add(new DERSequence(localASN1EncodableVector14));
      }
      SafeBag localSafeBag4 = new SafeBag(pkcs8ShroudedKeyBag, localEncryptedPrivateKeyInfo.toASN1Primitive(), new DERSet(localASN1EncodableVector12));
      localASN1EncodableVector1.add(localSafeBag4);
    }
    byte[] arrayOfByte1 = new DERSequence(localASN1EncodableVector1).getEncoded("DER");
    BERConstructedOctetString localBERConstructedOctetString = new BERConstructedOctetString(arrayOfByte1);
    byte[] arrayOfByte2 = new byte[20];
    this.random.nextBytes(arrayOfByte2);
    ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
    PKCS12PBEParams localPKCS12PBEParams1 = new PKCS12PBEParams(arrayOfByte2, 1024);
    AlgorithmIdentifier localAlgorithmIdentifier1 = new AlgorithmIdentifier(this.certAlgorithm, localPKCS12PBEParams1.toASN1Primitive());
    Hashtable localHashtable = new Hashtable();
    Enumeration localEnumeration2 = this.keys.keys();
    while (localEnumeration2.hasMoreElements()) {
      try
      {
        String str2 = (String)localEnumeration2.nextElement();
        Certificate localCertificate3 = engineGetCertificate(str2);
        CertBag localCertBag3 = new CertBag(x509Certificate, new DEROctetString(localCertificate3.getEncoded()));
        ASN1EncodableVector localASN1EncodableVector8 = new ASN1EncodableVector();
        boolean bool2 = localCertificate3 instanceof PKCS12BagAttributeCarrier;
        int j = 0;
        if (bool2)
        {
          PKCS12BagAttributeCarrier localPKCS12BagAttributeCarrier3 = (PKCS12BagAttributeCarrier)localCertificate3;
          DERBMPString localDERBMPString2 = (DERBMPString)localPKCS12BagAttributeCarrier3.getBagAttribute(pkcs_9_at_friendlyName);
          if ((localDERBMPString2 == null) || (!localDERBMPString2.getString().equals(str2))) {
            localPKCS12BagAttributeCarrier3.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(str2));
          }
          if (localPKCS12BagAttributeCarrier3.getBagAttribute(pkcs_9_at_localKeyId) == null) {
            localPKCS12BagAttributeCarrier3.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(localCertificate3.getPublicKey()));
          }
          Enumeration localEnumeration7 = localPKCS12BagAttributeCarrier3.getBagAttributeKeys();
          while (localEnumeration7.hasMoreElements())
          {
            ASN1ObjectIdentifier localASN1ObjectIdentifier3 = (ASN1ObjectIdentifier)localEnumeration7.nextElement();
            ASN1EncodableVector localASN1EncodableVector11 = new ASN1EncodableVector();
            localASN1EncodableVector11.add(localASN1ObjectIdentifier3);
            localASN1EncodableVector11.add(new DERSet(localPKCS12BagAttributeCarrier3.getBagAttribute(localASN1ObjectIdentifier3)));
            localASN1EncodableVector8.add(new DERSequence(localASN1EncodableVector11));
            j = 1;
          }
        }
        if (j == 0)
        {
          ASN1EncodableVector localASN1EncodableVector9 = new ASN1EncodableVector();
          localASN1EncodableVector9.add(pkcs_9_at_localKeyId);
          localASN1EncodableVector9.add(new DERSet(createSubjectKeyId(localCertificate3.getPublicKey())));
          localASN1EncodableVector8.add(new DERSequence(localASN1EncodableVector9));
          ASN1EncodableVector localASN1EncodableVector10 = new ASN1EncodableVector();
          localASN1EncodableVector10.add(pkcs_9_at_friendlyName);
          localASN1EncodableVector10.add(new DERSet(new DERBMPString(str2)));
          localASN1EncodableVector8.add(new DERSequence(localASN1EncodableVector10));
        }
        SafeBag localSafeBag3 = new SafeBag(certBag, localCertBag3.toASN1Primitive(), new DERSet(localASN1EncodableVector8));
        localASN1EncodableVector2.add(localSafeBag3);
        localHashtable.put(localCertificate3, localCertificate3);
      }
      catch (CertificateEncodingException localCertificateEncodingException3)
      {
        throw new IOException("Error encoding certificate: " + localCertificateEncodingException3.toString());
      }
    }
    Enumeration localEnumeration3 = this.certs.keys();
    while (localEnumeration3.hasMoreElements()) {
      try
      {
        String str1 = (String)localEnumeration3.nextElement();
        Certificate localCertificate2 = (Certificate)this.certs.get(str1);
        if (this.keys.get(str1) == null)
        {
          CertBag localCertBag2 = new CertBag(x509Certificate, new DEROctetString(localCertificate2.getEncoded()));
          ASN1EncodableVector localASN1EncodableVector5 = new ASN1EncodableVector();
          boolean bool1 = localCertificate2 instanceof PKCS12BagAttributeCarrier;
          int i = 0;
          if (bool1)
          {
            PKCS12BagAttributeCarrier localPKCS12BagAttributeCarrier2 = (PKCS12BagAttributeCarrier)localCertificate2;
            DERBMPString localDERBMPString1 = (DERBMPString)localPKCS12BagAttributeCarrier2.getBagAttribute(pkcs_9_at_friendlyName);
            if ((localDERBMPString1 == null) || (!localDERBMPString1.getString().equals(str1))) {
              localPKCS12BagAttributeCarrier2.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(str1));
            }
            Enumeration localEnumeration6 = localPKCS12BagAttributeCarrier2.getBagAttributeKeys();
            while (localEnumeration6.hasMoreElements())
            {
              ASN1ObjectIdentifier localASN1ObjectIdentifier2 = (ASN1ObjectIdentifier)localEnumeration6.nextElement();
              if (!localASN1ObjectIdentifier2.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId))
              {
                ASN1EncodableVector localASN1EncodableVector7 = new ASN1EncodableVector();
                localASN1EncodableVector7.add(localASN1ObjectIdentifier2);
                localASN1EncodableVector7.add(new DERSet(localPKCS12BagAttributeCarrier2.getBagAttribute(localASN1ObjectIdentifier2)));
                localASN1EncodableVector5.add(new DERSequence(localASN1EncodableVector7));
                i = 1;
              }
            }
          }
          if (i == 0)
          {
            ASN1EncodableVector localASN1EncodableVector6 = new ASN1EncodableVector();
            localASN1EncodableVector6.add(pkcs_9_at_friendlyName);
            localASN1EncodableVector6.add(new DERSet(new DERBMPString(str1)));
            localASN1EncodableVector5.add(new DERSequence(localASN1EncodableVector6));
          }
          SafeBag localSafeBag2 = new SafeBag(certBag, localCertBag2.toASN1Primitive(), new DERSet(localASN1EncodableVector5));
          localASN1EncodableVector2.add(localSafeBag2);
          localHashtable.put(localCertificate2, localCertificate2);
        }
      }
      catch (CertificateEncodingException localCertificateEncodingException2)
      {
        throw new IOException("Error encoding certificate: " + localCertificateEncodingException2.toString());
      }
    }
    Enumeration localEnumeration4 = this.chainCerts.keys();
    while (localEnumeration4.hasMoreElements())
    {
      try
      {
        CertId localCertId = (CertId)localEnumeration4.nextElement();
        Certificate localCertificate1 = (Certificate)this.chainCerts.get(localCertId);
        if (localHashtable.get(localCertificate1) != null) {
          continue;
        }
        CertBag localCertBag1 = new CertBag(x509Certificate, new DEROctetString(localCertificate1.getEncoded()));
        ASN1EncodableVector localASN1EncodableVector3 = new ASN1EncodableVector();
        if ((localCertificate1 instanceof PKCS12BagAttributeCarrier))
        {
          PKCS12BagAttributeCarrier localPKCS12BagAttributeCarrier1 = (PKCS12BagAttributeCarrier)localCertificate1;
          Enumeration localEnumeration5 = localPKCS12BagAttributeCarrier1.getBagAttributeKeys();
          while (localEnumeration5.hasMoreElements())
          {
            ASN1ObjectIdentifier localASN1ObjectIdentifier1 = (ASN1ObjectIdentifier)localEnumeration5.nextElement();
            if (!localASN1ObjectIdentifier1.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId))
            {
              ASN1EncodableVector localASN1EncodableVector4 = new ASN1EncodableVector();
              localASN1EncodableVector4.add(localASN1ObjectIdentifier1);
              localASN1EncodableVector4.add(new DERSet(localPKCS12BagAttributeCarrier1.getBagAttribute(localASN1ObjectIdentifier1)));
              localASN1EncodableVector3.add(new DERSequence(localASN1EncodableVector4));
            }
          }
        }
        localSafeBag1 = new SafeBag(certBag, localCertBag1.toASN1Primitive(), new DERSet(localASN1EncodableVector3));
      }
      catch (CertificateEncodingException localCertificateEncodingException1)
      {
        throw new IOException("Error encoding certificate: " + localCertificateEncodingException1.toString());
      }
      SafeBag localSafeBag1;
      localASN1EncodableVector2.add(localSafeBag1);
    }
    byte[] arrayOfByte3 = cryptData(true, localAlgorithmIdentifier1, paramArrayOfChar, false, new DERSequence(localASN1EncodableVector2).getEncoded("DER"));
    EncryptedData localEncryptedData = new EncryptedData(data, localAlgorithmIdentifier1, new BERConstructedOctetString(arrayOfByte3));
    ContentInfo[] arrayOfContentInfo = new ContentInfo[2];
    arrayOfContentInfo[0] = new ContentInfo(data, localBERConstructedOctetString);
    arrayOfContentInfo[1] = new ContentInfo(encryptedData, localEncryptedData.toASN1Primitive());
    AuthenticatedSafe localAuthenticatedSafe = new AuthenticatedSafe(arrayOfContentInfo);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    Object localObject1;
    ContentInfo localContentInfo;
    byte[] arrayOfByte5;
    byte[] arrayOfByte6;
    if (paramBoolean)
    {
      localObject1 = new DEROutputStream(localByteArrayOutputStream);
      ((DEROutputStream)localObject1).writeObject(localAuthenticatedSafe);
      byte[] arrayOfByte4 = localByteArrayOutputStream.toByteArray();
      localContentInfo = new ContentInfo(data, new BERConstructedOctetString(arrayOfByte4));
      arrayOfByte5 = new byte[20];
      this.random.nextBytes(arrayOfByte5);
      arrayOfByte6 = ((ASN1OctetString)localContentInfo.getContent()).getOctets();
    }
    for (;;)
    {
      try
      {
        byte[] arrayOfByte7 = calculatePbeMac(id_SHA1, arrayOfByte5, 1024, paramArrayOfChar, false, arrayOfByte6);
        AlgorithmIdentifier localAlgorithmIdentifier2 = new AlgorithmIdentifier(id_SHA1, new DERNull());
        DigestInfo localDigestInfo = new DigestInfo(localAlgorithmIdentifier2, arrayOfByte7);
        MacData localMacData = new MacData(localDigestInfo, arrayOfByte5, 1024);
        Pfx localPfx = new Pfx(localContentInfo, localMacData);
        if (!paramBoolean) {
          break label2051;
        }
        localObject2 = new DEROutputStream(paramOutputStream);
        ((DEROutputStream)localObject2).writeObject(localPfx);
        return;
      }
      catch (Exception localException)
      {
        throw new IOException("error constructing MAC: " + localException.toString());
      }
      localObject1 = new BEROutputStream(localByteArrayOutputStream);
      break;
      label2051:
      Object localObject2 = new BEROutputStream(paramOutputStream);
    }
  }
  
  /* Error */
  protected byte[] cryptData(boolean paramBoolean1, AlgorithmIdentifier paramAlgorithmIdentifier, char[] paramArrayOfChar, boolean paramBoolean2, byte[] paramArrayOfByte)
    throws IOException
  {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual 437	org/spongycastle/asn1/x509/AlgorithmIdentifier:getAlgorithm	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   4: invokevirtual 131	org/spongycastle/asn1/ASN1ObjectIdentifier:getId	()Ljava/lang/String;
    //   7: astore 6
    //   9: aload_2
    //   10: invokevirtual 440	org/spongycastle/asn1/x509/AlgorithmIdentifier:getParameters	()Lorg/spongycastle/asn1/ASN1Encodable;
    //   13: invokestatic 443	org/spongycastle/asn1/pkcs/PKCS12PBEParams:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/pkcs/PKCS12PBEParams;
    //   16: astore 7
    //   18: new 143	javax/crypto/spec/PBEKeySpec
    //   21: dup
    //   22: aload_3
    //   23: invokespecial 146	javax/crypto/spec/PBEKeySpec:<init>	([C)V
    //   26: astore 8
    //   28: aload 6
    //   30: getstatic 53	org/spongycastle/jce/provider/JDKPKCS12KeyStore:bcProvider	Ljava/security/Provider;
    //   33: invokestatic 136	javax/crypto/SecretKeyFactory:getInstance	(Ljava/lang/String;Ljava/security/Provider;)Ljavax/crypto/SecretKeyFactory;
    //   36: astore 10
    //   38: new 138	javax/crypto/spec/PBEParameterSpec
    //   41: dup
    //   42: aload 7
    //   44: invokevirtual 446	org/spongycastle/asn1/pkcs/PKCS12PBEParams:getIV	()[B
    //   47: aload 7
    //   49: invokevirtual 450	org/spongycastle/asn1/pkcs/PKCS12PBEParams:getIterations	()Ljava/math/BigInteger;
    //   52: invokevirtual 456	java/math/BigInteger:intValue	()I
    //   55: invokespecial 141	javax/crypto/spec/PBEParameterSpec:<init>	([BI)V
    //   58: astore 11
    //   60: aload 10
    //   62: aload 8
    //   64: invokevirtual 150	javax/crypto/SecretKeyFactory:generateSecret	(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   67: checkcast 152	org/spongycastle/jcajce/provider/symmetric/util/BCPBEKey
    //   70: astore 12
    //   72: aload 12
    //   74: iload 4
    //   76: invokevirtual 156	org/spongycastle/jcajce/provider/symmetric/util/BCPBEKey:setTryWrongPKCS12Zero	(Z)V
    //   79: aload 6
    //   81: getstatic 53	org/spongycastle/jce/provider/JDKPKCS12KeyStore:bcProvider	Ljava/security/Provider;
    //   84: invokestatic 461	javax/crypto/Cipher:getInstance	(Ljava/lang/String;Ljava/security/Provider;)Ljavax/crypto/Cipher;
    //   87: astore 13
    //   89: iload_1
    //   90: ifeq +29 -> 119
    //   93: iconst_1
    //   94: istore 14
    //   96: aload 13
    //   98: iload 14
    //   100: aload 12
    //   102: aload 11
    //   104: invokevirtual 464	javax/crypto/Cipher:init	(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   107: aload 13
    //   109: aload 5
    //   111: invokevirtual 467	javax/crypto/Cipher:doFinal	([B)[B
    //   114: astore 15
    //   116: aload 15
    //   118: areturn
    //   119: iconst_2
    //   120: istore 14
    //   122: goto -26 -> 96
    //   125: astore 9
    //   127: new 205	java/io/IOException
    //   130: dup
    //   131: new 101	java/lang/StringBuilder
    //   134: dup
    //   135: ldc_w 469
    //   138: invokespecial 106	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   141: aload 9
    //   143: invokevirtual 110	java/lang/Exception:toString	()Ljava/lang/String;
    //   146: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: invokevirtual 115	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   152: invokespecial 352	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   155: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	JDKPKCS12KeyStore
    //   0	156	1	paramBoolean1	boolean
    //   0	156	2	paramAlgorithmIdentifier	AlgorithmIdentifier
    //   0	156	3	paramArrayOfChar	char[]
    //   0	156	4	paramBoolean2	boolean
    //   0	156	5	paramArrayOfByte	byte[]
    //   7	73	6	str	String
    //   16	32	7	localPKCS12PBEParams	PKCS12PBEParams
    //   26	37	8	localPBEKeySpec	PBEKeySpec
    //   125	17	9	localException	Exception
    //   36	25	10	localSecretKeyFactory	SecretKeyFactory
    //   58	45	11	localPBEParameterSpec	PBEParameterSpec
    //   70	31	12	localBCPBEKey	BCPBEKey
    //   87	21	13	localCipher	Cipher
    //   94	27	14	i	int
    //   114	3	15	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   28	89	125	java/lang/Exception
    //   96	116	125	java/lang/Exception
  }
  
  public Enumeration engineAliases()
  {
    Hashtable localHashtable = new Hashtable();
    Enumeration localEnumeration1 = this.certs.keys();
    while (localEnumeration1.hasMoreElements()) {
      localHashtable.put(localEnumeration1.nextElement(), "cert");
    }
    Enumeration localEnumeration2 = this.keys.keys();
    while (localEnumeration2.hasMoreElements())
    {
      String str = (String)localEnumeration2.nextElement();
      if (localHashtable.get(str) == null) {
        localHashtable.put(str, "key");
      }
    }
    return localHashtable.keys();
  }
  
  public boolean engineContainsAlias(String paramString)
  {
    return (this.certs.get(paramString) != null) || (this.keys.get(paramString) != null);
  }
  
  public void engineDeleteEntry(String paramString)
    throws KeyStoreException
  {
    Key localKey = (Key)this.keys.remove(paramString);
    Certificate localCertificate = (Certificate)this.certs.remove(paramString);
    if (localCertificate != null) {
      this.chainCerts.remove(new CertId(localCertificate.getPublicKey()));
    }
    if (localKey != null)
    {
      String str = (String)this.localIds.remove(paramString);
      if (str != null) {
        localCertificate = (Certificate)this.keyCerts.remove(str);
      }
      if (localCertificate != null) {
        this.chainCerts.remove(new CertId(localCertificate.getPublicKey()));
      }
    }
    if ((localCertificate == null) && (localKey == null)) {
      throw new KeyStoreException("no such entry as " + paramString);
    }
  }
  
  public Certificate engineGetCertificate(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("null alias passed to getCertificate.");
    }
    Certificate localCertificate = (Certificate)this.certs.get(paramString);
    if (localCertificate == null)
    {
      String str = (String)this.localIds.get(paramString);
      if (str != null) {
        localCertificate = (Certificate)this.keyCerts.get(str);
      }
    }
    else
    {
      return localCertificate;
    }
    return (Certificate)this.keyCerts.get(paramString);
  }
  
  public String engineGetCertificateAlias(Certificate paramCertificate)
  {
    Enumeration localEnumeration1 = this.certs.elements();
    Enumeration localEnumeration2 = this.certs.keys();
    while (localEnumeration1.hasMoreElements())
    {
      Certificate localCertificate2 = (Certificate)localEnumeration1.nextElement();
      String str2 = (String)localEnumeration2.nextElement();
      if (localCertificate2.equals(paramCertificate)) {
        return str2;
      }
    }
    Enumeration localEnumeration3 = this.keyCerts.elements();
    Enumeration localEnumeration4 = this.keyCerts.keys();
    while (localEnumeration3.hasMoreElements())
    {
      Certificate localCertificate1 = (Certificate)localEnumeration3.nextElement();
      String str1 = (String)localEnumeration4.nextElement();
      if (localCertificate1.equals(paramCertificate)) {
        return str1;
      }
    }
    return null;
  }
  
  /* Error */
  public Certificate[] engineGetCertificateChain(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +14 -> 15
    //   4: new 99	java/lang/IllegalArgumentException
    //   7: dup
    //   8: ldc_w 505
    //   11: invokespecial 116	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   14: athrow
    //   15: aload_0
    //   16: aload_1
    //   17: invokevirtual 508	org/spongycastle/jce/provider/JDKPKCS12KeyStore:engineIsKeyEntry	(Ljava/lang/String;)Z
    //   20: ifne +9 -> 29
    //   23: aconst_null
    //   24: astore 4
    //   26: aload 4
    //   28: areturn
    //   29: aload_0
    //   30: aload_1
    //   31: invokevirtual 289	org/spongycastle/jce/provider/JDKPKCS12KeyStore:engineGetCertificate	(Ljava/lang/String;)Ljava/security/cert/Certificate;
    //   34: astore_2
    //   35: aload_2
    //   36: ifnull +285 -> 321
    //   39: new 510	java/util/Vector
    //   42: dup
    //   43: invokespecial 511	java/util/Vector:<init>	()V
    //   46: astore_3
    //   47: aload_2
    //   48: ifnull +233 -> 281
    //   51: aload_2
    //   52: checkcast 513	java/security/cert/X509Certificate
    //   55: astore 6
    //   57: aload 6
    //   59: getstatic 518	org/spongycastle/asn1/x509/X509Extensions:AuthorityKeyIdentifier	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   62: invokevirtual 131	org/spongycastle/asn1/ASN1ObjectIdentifier:getId	()Ljava/lang/String;
    //   65: invokevirtual 521	java/security/cert/X509Certificate:getExtensionValue	(Ljava/lang/String;)[B
    //   68: astore 7
    //   70: aconst_null
    //   71: astore 8
    //   73: aload 7
    //   75: ifnull +79 -> 154
    //   78: new 523	org/spongycastle/asn1/ASN1InputStream
    //   81: dup
    //   82: new 523	org/spongycastle/asn1/ASN1InputStream
    //   85: dup
    //   86: aload 7
    //   88: invokespecial 524	org/spongycastle/asn1/ASN1InputStream:<init>	([B)V
    //   91: invokevirtual 527	org/spongycastle/asn1/ASN1InputStream:readObject	()Lorg/spongycastle/asn1/ASN1Primitive;
    //   94: checkcast 404	org/spongycastle/asn1/ASN1OctetString
    //   97: invokevirtual 407	org/spongycastle/asn1/ASN1OctetString:getOctets	()[B
    //   100: invokespecial 524	org/spongycastle/asn1/ASN1InputStream:<init>	([B)V
    //   103: invokevirtual 527	org/spongycastle/asn1/ASN1InputStream:readObject	()Lorg/spongycastle/asn1/ASN1Primitive;
    //   106: checkcast 190	org/spongycastle/asn1/ASN1Sequence
    //   109: invokestatic 532	org/spongycastle/asn1/x509/AuthorityKeyIdentifier:getInstance	(Ljava/lang/Object;)Lorg/spongycastle/asn1/x509/AuthorityKeyIdentifier;
    //   112: astore 14
    //   114: aload 14
    //   116: invokevirtual 535	org/spongycastle/asn1/x509/AuthorityKeyIdentifier:getKeyIdentifier	()[B
    //   119: astore 15
    //   121: aconst_null
    //   122: astore 8
    //   124: aload 15
    //   126: ifnull +28 -> 154
    //   129: aload_0
    //   130: getfield 73	org/spongycastle/jce/provider/JDKPKCS12KeyStore:chainCerts	Ljava/util/Hashtable;
    //   133: new 357	org/spongycastle/jce/provider/JDKPKCS12KeyStore$CertId
    //   136: dup
    //   137: aload_0
    //   138: aload 14
    //   140: invokevirtual 535	org/spongycastle/asn1/x509/AuthorityKeyIdentifier:getKeyIdentifier	()[B
    //   143: invokespecial 538	org/spongycastle/jce/provider/JDKPKCS12KeyStore$CertId:<init>	(Lorg/spongycastle/jce/provider/JDKPKCS12KeyStore;[B)V
    //   146: invokevirtual 360	java/util/Hashtable:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   149: checkcast 291	java/security/cert/Certificate
    //   152: astore 8
    //   154: aload 8
    //   156: ifnonnull +88 -> 244
    //   159: aload 6
    //   161: invokevirtual 542	java/security/cert/X509Certificate:getIssuerDN	()Ljava/security/Principal;
    //   164: astore 9
    //   166: aload 9
    //   168: aload 6
    //   170: invokevirtual 545	java/security/cert/X509Certificate:getSubjectDN	()Ljava/security/Principal;
    //   173: invokevirtual 548	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   176: ifne +68 -> 244
    //   179: aload_0
    //   180: getfield 73	org/spongycastle/jce/provider/JDKPKCS12KeyStore:chainCerts	Ljava/util/Hashtable;
    //   183: invokevirtual 355	java/util/Hashtable:keys	()Ljava/util/Enumeration;
    //   186: astore 10
    //   188: aload 10
    //   190: invokeinterface 224 1 0
    //   195: ifeq +49 -> 244
    //   198: aload_0
    //   199: getfield 73	org/spongycastle/jce/provider/JDKPKCS12KeyStore:chainCerts	Ljava/util/Hashtable;
    //   202: aload 10
    //   204: invokeinterface 231 1 0
    //   209: invokevirtual 360	java/util/Hashtable:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   212: checkcast 513	java/security/cert/X509Certificate
    //   215: astore 11
    //   217: aload 11
    //   219: invokevirtual 545	java/security/cert/X509Certificate:getSubjectDN	()Ljava/security/Principal;
    //   222: aload 9
    //   224: invokevirtual 548	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   227: ifeq -39 -> 188
    //   230: aload 6
    //   232: aload 11
    //   234: invokevirtual 549	java/security/cert/X509Certificate:getPublicKey	()Ljava/security/PublicKey;
    //   237: invokevirtual 553	java/security/cert/X509Certificate:verify	(Ljava/security/PublicKey;)V
    //   240: aload 11
    //   242: astore 8
    //   244: aload_3
    //   245: aload_2
    //   246: invokevirtual 557	java/util/Vector:addElement	(Ljava/lang/Object;)V
    //   249: aload 8
    //   251: aload_2
    //   252: if_acmpeq +24 -> 276
    //   255: aload 8
    //   257: astore_2
    //   258: goto -211 -> 47
    //   261: astore 13
    //   263: new 198	java/lang/RuntimeException
    //   266: dup
    //   267: aload 13
    //   269: invokevirtual 558	java/io/IOException:toString	()Ljava/lang/String;
    //   272: invokespecial 201	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   275: athrow
    //   276: aconst_null
    //   277: astore_2
    //   278: goto -231 -> 47
    //   281: aload_3
    //   282: invokevirtual 561	java/util/Vector:size	()I
    //   285: anewarray 291	java/security/cert/Certificate
    //   288: astore 4
    //   290: iconst_0
    //   291: istore 5
    //   293: iload 5
    //   295: aload 4
    //   297: arraylength
    //   298: if_icmpeq -272 -> 26
    //   301: aload 4
    //   303: iload 5
    //   305: aload_3
    //   306: iload 5
    //   308: invokevirtual 565	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   311: checkcast 291	java/security/cert/Certificate
    //   314: aastore
    //   315: iinc 5 1
    //   318: goto -25 -> 293
    //   321: aconst_null
    //   322: areturn
    //   323: astore 12
    //   325: goto -137 -> 188
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	328	0	this	JDKPKCS12KeyStore
    //   0	328	1	paramString	String
    //   34	244	2	localObject1	Object
    //   46	260	3	localVector	Vector
    //   24	278	4	arrayOfCertificate	Certificate[]
    //   291	25	5	i	int
    //   55	176	6	localX509Certificate1	java.security.cert.X509Certificate
    //   68	19	7	arrayOfByte1	byte[]
    //   71	185	8	localObject2	Object
    //   164	59	9	localPrincipal	java.security.Principal
    //   186	17	10	localEnumeration	Enumeration
    //   215	26	11	localX509Certificate2	java.security.cert.X509Certificate
    //   323	1	12	localException	Exception
    //   261	7	13	localIOException	IOException
    //   112	27	14	localAuthorityKeyIdentifier	org.spongycastle.asn1.x509.AuthorityKeyIdentifier
    //   119	6	15	arrayOfByte2	byte[]
    // Exception table:
    //   from	to	target	type
    //   78	121	261	java/io/IOException
    //   129	154	261	java/io/IOException
    //   230	240	323	java/lang/Exception
  }
  
  public Date engineGetCreationDate(String paramString)
  {
    return new Date();
  }
  
  public Key engineGetKey(String paramString, char[] paramArrayOfChar)
    throws NoSuchAlgorithmException, UnrecoverableKeyException
  {
    if (paramString == null) {
      throw new IllegalArgumentException("null alias passed to getKey.");
    }
    return (Key)this.keys.get(paramString);
  }
  
  public boolean engineIsCertificateEntry(String paramString)
  {
    return (this.certs.get(paramString) != null) && (this.keys.get(paramString) == null);
  }
  
  public boolean engineIsKeyEntry(String paramString)
  {
    return this.keys.get(paramString) != null;
  }
  
  public void engineLoad(InputStream paramInputStream, char[] paramArrayOfChar)
    throws IOException
  {
    if (paramInputStream == null) {}
    Vector localVector;
    label291:
    int i;
    int j;
    int k;
    do
    {
      return;
      if (paramArrayOfChar == null) {
        throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
      }
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramInputStream);
      localBufferedInputStream.mark(10);
      if (localBufferedInputStream.read() != 48) {
        throw new IOException("stream does not represent a PKCS12 key store");
      }
      localBufferedInputStream.reset();
      Pfx localPfx = Pfx.getInstance((ASN1Sequence)new ASN1InputStream(localBufferedInputStream).readObject());
      ContentInfo localContentInfo = localPfx.getAuthSafe();
      localVector = new Vector();
      MacData localMacData1 = localPfx.getMacData();
      boolean bool1 = false;
      if (localMacData1 != null)
      {
        MacData localMacData2 = localPfx.getMacData();
        DigestInfo localDigestInfo = localMacData2.getMac();
        AlgorithmIdentifier localAlgorithmIdentifier = localDigestInfo.getAlgorithmId();
        byte[] arrayOfByte1 = localMacData2.getSalt();
        int i8 = localMacData2.getIterationCount().intValue();
        byte[] arrayOfByte2 = ((ASN1OctetString)localContentInfo.getContent()).getOctets();
        try
        {
          byte[] arrayOfByte3 = calculatePbeMac(localAlgorithmIdentifier.getObjectId(), arrayOfByte1, i8, paramArrayOfChar, false, arrayOfByte2);
          arrayOfByte4 = localDigestInfo.getDigest();
          boolean bool3 = Arrays.constantTimeAreEqual(arrayOfByte3, arrayOfByte4);
          bool1 = false;
          if (bool3) {
            break label291;
          }
          if (paramArrayOfChar.length > 0) {
            throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
          }
        }
        catch (IOException localIOException)
        {
          byte[] arrayOfByte4;
          throw localIOException;
          if (!Arrays.constantTimeAreEqual(calculatePbeMac(localAlgorithmIdentifier.getObjectId(), arrayOfByte1, i8, paramArrayOfChar, true, arrayOfByte2), arrayOfByte4)) {
            throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
          }
        }
        catch (Exception localException2)
        {
          throw new IOException("error constructing MAC: " + localException2.toString());
        }
        bool1 = true;
      }
      this.keys = new IgnoresCaseHashtable(null);
      this.localIds = new Hashtable();
      boolean bool2 = localContentInfo.getContentType().equals(data);
      i = 0;
      if (bool2)
      {
        ContentInfo[] arrayOfContentInfo = AuthenticatedSafe.getInstance(new ASN1InputStream(((ASN1OctetString)localContentInfo.getContent()).getOctets()).readObject()).getContentInfo();
        for (int m = 0;; m++)
        {
          int n = arrayOfContentInfo.length;
          if (m == n) {
            break;
          }
          if (arrayOfContentInfo[m].getContentType().equals(data))
          {
            ASN1Sequence localASN1Sequence5 = (ASN1Sequence)new ASN1InputStream(((ASN1OctetString)arrayOfContentInfo[m].getContent()).getOctets()).readObject();
            int i5 = 0;
            int i6 = localASN1Sequence5.size();
            if (i5 != i6)
            {
              SafeBag localSafeBag3 = SafeBag.getInstance(localASN1Sequence5.getObjectAt(i5));
              PrivateKey localPrivateKey3;
              String str8;
              String str9;
              if (localSafeBag3.getBagId().equals(pkcs8ShroudedKeyBag))
              {
                EncryptedPrivateKeyInfo localEncryptedPrivateKeyInfo2 = EncryptedPrivateKeyInfo.getInstance(localSafeBag3.getBagValue());
                localPrivateKey3 = unwrapKey(localEncryptedPrivateKeyInfo2.getEncryptionAlgorithm(), localEncryptedPrivateKeyInfo2.getEncryptedData(), paramArrayOfChar, bool1);
                PKCS12BagAttributeCarrier localPKCS12BagAttributeCarrier4 = (PKCS12BagAttributeCarrier)localPrivateKey3;
                ASN1Set localASN1Set4 = localSafeBag3.getBagAttributes();
                str8 = null;
                ASN1OctetString localASN1OctetString4 = null;
                if (localASN1Set4 != null)
                {
                  Enumeration localEnumeration4 = localSafeBag3.getBagAttributes().getObjects();
                  while (localEnumeration4.hasMoreElements())
                  {
                    ASN1Sequence localASN1Sequence6 = (ASN1Sequence)localEnumeration4.nextElement();
                    ASN1ObjectIdentifier localASN1ObjectIdentifier4 = (ASN1ObjectIdentifier)localASN1Sequence6.getObjectAt(0);
                    ASN1Set localASN1Set5 = (ASN1Set)localASN1Sequence6.getObjectAt(1);
                    int i7 = localASN1Set5.size();
                    ASN1Primitive localASN1Primitive4 = null;
                    if (i7 > 0)
                    {
                      localASN1Primitive4 = (ASN1Primitive)localASN1Set5.getObjectAt(0);
                      ASN1Encodable localASN1Encodable4 = localPKCS12BagAttributeCarrier4.getBagAttribute(localASN1ObjectIdentifier4);
                      if (localASN1Encodable4 != null)
                      {
                        if (!localASN1Encodable4.toASN1Primitive().equals(localASN1Primitive4)) {
                          throw new IOException("attempt to add existing attribute with different value");
                        }
                      }
                      else {
                        localPKCS12BagAttributeCarrier4.setBagAttribute(localASN1ObjectIdentifier4, localASN1Primitive4);
                      }
                    }
                    if (localASN1ObjectIdentifier4.equals(pkcs_9_at_friendlyName))
                    {
                      str8 = ((DERBMPString)localASN1Primitive4).getString();
                      this.keys.put(str8, localPrivateKey3);
                    }
                    else if (localASN1ObjectIdentifier4.equals(pkcs_9_at_localKeyId))
                    {
                      localASN1OctetString4 = (ASN1OctetString)localASN1Primitive4;
                    }
                  }
                }
                if (localASN1OctetString4 != null)
                {
                  str9 = new String(Hex.encode(localASN1OctetString4.getOctets()));
                  if (str8 == null) {
                    this.keys.put(str9, localPrivateKey3);
                  }
                }
              }
              for (;;)
              {
                i5++;
                break;
                this.localIds.put(str8, str9);
                continue;
                i = 1;
                this.keys.put("unmarked", localPrivateKey3);
                continue;
                if (localSafeBag3.getBagId().equals(certBag))
                {
                  localVector.addElement(localSafeBag3);
                }
                else
                {
                  System.out.println("extra in data " + localSafeBag3.getBagId());
                  System.out.println(ASN1Dump.dumpAsString(localSafeBag3));
                }
              }
            }
          }
          else if (arrayOfContentInfo[m].getContentType().equals(encryptedData))
          {
            EncryptedData localEncryptedData = EncryptedData.getInstance(arrayOfContentInfo[m].getContent());
            ASN1Sequence localASN1Sequence2 = (ASN1Sequence)ASN1Primitive.fromByteArray(cryptData(false, localEncryptedData.getEncryptionAlgorithm(), paramArrayOfChar, bool1, localEncryptedData.getContent().getOctets()));
            int i1 = 0;
            int i2 = localASN1Sequence2.size();
            if (i1 != i2)
            {
              SafeBag localSafeBag2 = SafeBag.getInstance(localASN1Sequence2.getObjectAt(i1));
              if (localSafeBag2.getBagId().equals(certBag)) {
                localVector.addElement(localSafeBag2);
              }
              for (;;)
              {
                i1++;
                break;
                if (localSafeBag2.getBagId().equals(pkcs8ShroudedKeyBag))
                {
                  EncryptedPrivateKeyInfo localEncryptedPrivateKeyInfo1 = EncryptedPrivateKeyInfo.getInstance(localSafeBag2.getBagValue());
                  PrivateKey localPrivateKey2 = unwrapKey(localEncryptedPrivateKeyInfo1.getEncryptionAlgorithm(), localEncryptedPrivateKeyInfo1.getEncryptedData(), paramArrayOfChar, bool1);
                  PKCS12BagAttributeCarrier localPKCS12BagAttributeCarrier3 = (PKCS12BagAttributeCarrier)localPrivateKey2;
                  String str6 = null;
                  ASN1OctetString localASN1OctetString3 = null;
                  Enumeration localEnumeration3 = localSafeBag2.getBagAttributes().getObjects();
                  while (localEnumeration3.hasMoreElements())
                  {
                    ASN1Sequence localASN1Sequence4 = (ASN1Sequence)localEnumeration3.nextElement();
                    ASN1ObjectIdentifier localASN1ObjectIdentifier3 = (ASN1ObjectIdentifier)localASN1Sequence4.getObjectAt(0);
                    ASN1Set localASN1Set3 = (ASN1Set)localASN1Sequence4.getObjectAt(1);
                    int i4 = localASN1Set3.size();
                    ASN1Primitive localASN1Primitive3 = null;
                    if (i4 > 0)
                    {
                      localASN1Primitive3 = (ASN1Primitive)localASN1Set3.getObjectAt(0);
                      ASN1Encodable localASN1Encodable3 = localPKCS12BagAttributeCarrier3.getBagAttribute(localASN1ObjectIdentifier3);
                      if (localASN1Encodable3 != null)
                      {
                        if (!localASN1Encodable3.toASN1Primitive().equals(localASN1Primitive3)) {
                          throw new IOException("attempt to add existing attribute with different value");
                        }
                      }
                      else {
                        localPKCS12BagAttributeCarrier3.setBagAttribute(localASN1ObjectIdentifier3, localASN1Primitive3);
                      }
                    }
                    if (localASN1ObjectIdentifier3.equals(pkcs_9_at_friendlyName))
                    {
                      str6 = ((DERBMPString)localASN1Primitive3).getString();
                      this.keys.put(str6, localPrivateKey2);
                    }
                    else if (localASN1ObjectIdentifier3.equals(pkcs_9_at_localKeyId))
                    {
                      localASN1OctetString3 = (ASN1OctetString)localASN1Primitive3;
                    }
                  }
                  String str7 = new String(Hex.encode(localASN1OctetString3.getOctets()));
                  if (str6 == null) {
                    this.keys.put(str7, localPrivateKey2);
                  } else {
                    this.localIds.put(str6, str7);
                  }
                }
                else if (localSafeBag2.getBagId().equals(keyBag))
                {
                  PrivateKey localPrivateKey1 = BouncyCastleProvider.getPrivateKey(new PrivateKeyInfo((ASN1Sequence)localSafeBag2.getBagValue()));
                  PKCS12BagAttributeCarrier localPKCS12BagAttributeCarrier2 = (PKCS12BagAttributeCarrier)localPrivateKey1;
                  String str4 = null;
                  ASN1OctetString localASN1OctetString2 = null;
                  Enumeration localEnumeration2 = localSafeBag2.getBagAttributes().getObjects();
                  while (localEnumeration2.hasMoreElements())
                  {
                    ASN1Sequence localASN1Sequence3 = (ASN1Sequence)localEnumeration2.nextElement();
                    ASN1ObjectIdentifier localASN1ObjectIdentifier2 = (ASN1ObjectIdentifier)localASN1Sequence3.getObjectAt(0);
                    ASN1Set localASN1Set2 = (ASN1Set)localASN1Sequence3.getObjectAt(1);
                    int i3 = localASN1Set2.size();
                    ASN1Primitive localASN1Primitive2 = null;
                    if (i3 > 0)
                    {
                      localASN1Primitive2 = (ASN1Primitive)localASN1Set2.getObjectAt(0);
                      ASN1Encodable localASN1Encodable2 = localPKCS12BagAttributeCarrier2.getBagAttribute(localASN1ObjectIdentifier2);
                      if (localASN1Encodable2 != null)
                      {
                        if (!localASN1Encodable2.toASN1Primitive().equals(localASN1Primitive2)) {
                          throw new IOException("attempt to add existing attribute with different value");
                        }
                      }
                      else {
                        localPKCS12BagAttributeCarrier2.setBagAttribute(localASN1ObjectIdentifier2, localASN1Primitive2);
                      }
                    }
                    if (localASN1ObjectIdentifier2.equals(pkcs_9_at_friendlyName))
                    {
                      str4 = ((DERBMPString)localASN1Primitive2).getString();
                      this.keys.put(str4, localPrivateKey1);
                    }
                    else if (localASN1ObjectIdentifier2.equals(pkcs_9_at_localKeyId))
                    {
                      localASN1OctetString2 = (ASN1OctetString)localASN1Primitive2;
                    }
                  }
                  String str5 = new String(Hex.encode(localASN1OctetString2.getOctets()));
                  if (str4 == null) {
                    this.keys.put(str5, localPrivateKey1);
                  } else {
                    this.localIds.put(str4, str5);
                  }
                }
                else
                {
                  System.out.println("extra in encryptedData " + localSafeBag2.getBagId());
                  System.out.println(ASN1Dump.dumpAsString(localSafeBag2));
                }
              }
            }
          }
          else
          {
            System.out.println("extra " + arrayOfContentInfo[m].getContentType().getId());
            System.out.println("extra " + ASN1Dump.dumpAsString(arrayOfContentInfo[m].getContent()));
          }
        }
      }
      this.certs = new IgnoresCaseHashtable(null);
      this.chainCerts = new Hashtable();
      this.keyCerts = new Hashtable();
      j = 0;
      k = localVector.size();
    } while (j == k);
    SafeBag localSafeBag1 = (SafeBag)localVector.elementAt(j);
    CertBag localCertBag = CertBag.getInstance(localSafeBag1.getBagValue());
    if (!localCertBag.getCertId().equals(x509Certificate)) {
      throw new RuntimeException("Unsupported certificate type: " + localCertBag.getCertId());
    }
    Certificate localCertificate;
    String str1;
    ASN1OctetString localASN1OctetString1;
    for (;;)
    {
      ASN1ObjectIdentifier localASN1ObjectIdentifier1;
      ASN1Primitive localASN1Primitive1;
      PKCS12BagAttributeCarrier localPKCS12BagAttributeCarrier1;
      try
      {
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(((ASN1OctetString)localCertBag.getCertValue()).getOctets());
        localCertificate = this.certFact.generateCertificate(localByteArrayInputStream);
        ASN1Set localASN1Set1 = localSafeBag1.getBagAttributes();
        str1 = null;
        localASN1OctetString1 = null;
        if (localASN1Set1 == null) {
          break;
        }
        Enumeration localEnumeration1 = localSafeBag1.getBagAttributes().getObjects();
        if (!localEnumeration1.hasMoreElements()) {
          break;
        }
        ASN1Sequence localASN1Sequence1 = (ASN1Sequence)localEnumeration1.nextElement();
        localASN1ObjectIdentifier1 = (ASN1ObjectIdentifier)localASN1Sequence1.getObjectAt(0);
        localASN1Primitive1 = (ASN1Primitive)((ASN1Set)localASN1Sequence1.getObjectAt(1)).getObjectAt(0);
        if (!(localCertificate instanceof PKCS12BagAttributeCarrier)) {
          break label1973;
        }
        localPKCS12BagAttributeCarrier1 = (PKCS12BagAttributeCarrier)localCertificate;
        ASN1Encodable localASN1Encodable1 = localPKCS12BagAttributeCarrier1.getBagAttribute(localASN1ObjectIdentifier1);
        if (localASN1Encodable1 != null)
        {
          if (localASN1Encodable1.toASN1Primitive().equals(localASN1Primitive1)) {
            break label1973;
          }
          throw new IOException("attempt to add existing attribute with different value");
        }
      }
      catch (Exception localException1)
      {
        throw new RuntimeException(localException1.toString());
      }
      localPKCS12BagAttributeCarrier1.setBagAttribute(localASN1ObjectIdentifier1, localASN1Primitive1);
      label1973:
      if (localASN1ObjectIdentifier1.equals(pkcs_9_at_friendlyName)) {
        str1 = ((DERBMPString)localASN1Primitive1).getString();
      } else if (localASN1ObjectIdentifier1.equals(pkcs_9_at_localKeyId)) {
        localASN1OctetString1 = (ASN1OctetString)localASN1Primitive1;
      }
    }
    this.chainCerts.put(new CertId(localCertificate.getPublicKey()), localCertificate);
    if (i != 0) {
      if (this.keyCerts.isEmpty())
      {
        String str3 = new String(Hex.encode(createSubjectKeyId(localCertificate.getPublicKey()).getKeyIdentifier()));
        this.keyCerts.put(str3, localCertificate);
        this.keys.put(str3, this.keys.remove("unmarked"));
      }
    }
    for (;;)
    {
      j++;
      break;
      if (localASN1OctetString1 != null)
      {
        String str2 = new String(Hex.encode(localASN1OctetString1.getOctets()));
        this.keyCerts.put(str2, localCertificate);
      }
      if (str1 != null) {
        this.certs.put(str1, localCertificate);
      }
    }
  }
  
  public void engineSetCertificateEntry(String paramString, Certificate paramCertificate)
    throws KeyStoreException
  {
    if (this.keys.get(paramString) != null) {
      throw new KeyStoreException("There is a key entry with the name " + paramString + ".");
    }
    this.certs.put(paramString, paramCertificate);
    this.chainCerts.put(new CertId(paramCertificate.getPublicKey()), paramCertificate);
  }
  
  public void engineSetKeyEntry(String paramString, Key paramKey, char[] paramArrayOfChar, Certificate[] paramArrayOfCertificate)
    throws KeyStoreException
  {
    if (((paramKey instanceof PrivateKey)) && (paramArrayOfCertificate == null)) {
      throw new KeyStoreException("no certificate chain for private key");
    }
    if (this.keys.get(paramString) != null) {
      engineDeleteEntry(paramString);
    }
    this.keys.put(paramString, paramKey);
    this.certs.put(paramString, paramArrayOfCertificate[0]);
    for (int i = 0; i != paramArrayOfCertificate.length; i++) {
      this.chainCerts.put(new CertId(paramArrayOfCertificate[i].getPublicKey()), paramArrayOfCertificate[i]);
    }
  }
  
  public void engineSetKeyEntry(String paramString, byte[] paramArrayOfByte, Certificate[] paramArrayOfCertificate)
    throws KeyStoreException
  {
    throw new RuntimeException("operation not supported");
  }
  
  public int engineSize()
  {
    Hashtable localHashtable = new Hashtable();
    Enumeration localEnumeration1 = this.certs.keys();
    while (localEnumeration1.hasMoreElements()) {
      localHashtable.put(localEnumeration1.nextElement(), "cert");
    }
    Enumeration localEnumeration2 = this.keys.keys();
    while (localEnumeration2.hasMoreElements())
    {
      String str = (String)localEnumeration2.nextElement();
      if (localHashtable.get(str) == null) {
        localHashtable.put(str, "key");
      }
    }
    return localHashtable.size();
  }
  
  public void engineStore(OutputStream paramOutputStream, char[] paramArrayOfChar)
    throws IOException
  {
    doStore(paramOutputStream, paramArrayOfChar, false);
  }
  
  public void engineStore(KeyStore.LoadStoreParameter paramLoadStoreParameter)
    throws IOException, NoSuchAlgorithmException, CertificateException
  {
    if (paramLoadStoreParameter == null) {
      throw new IllegalArgumentException("'param' arg cannot be null");
    }
    if (!(paramLoadStoreParameter instanceof JDKPKCS12StoreParameter)) {
      throw new IllegalArgumentException("No support for 'param' of type " + paramLoadStoreParameter.getClass().getName());
    }
    JDKPKCS12StoreParameter localJDKPKCS12StoreParameter = (JDKPKCS12StoreParameter)paramLoadStoreParameter;
    KeyStore.ProtectionParameter localProtectionParameter = paramLoadStoreParameter.getProtectionParameter();
    if (localProtectionParameter == null) {}
    for (char[] arrayOfChar = null;; arrayOfChar = ((KeyStore.PasswordProtection)localProtectionParameter).getPassword())
    {
      doStore(localJDKPKCS12StoreParameter.getOutputStream(), arrayOfChar, localJDKPKCS12StoreParameter.isUseDEREncoding());
      return;
      if (!(localProtectionParameter instanceof KeyStore.PasswordProtection)) {
        break;
      }
    }
    throw new IllegalArgumentException("No support for protection parameter of type " + localProtectionParameter.getClass().getName());
  }
  
  public void setRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
  }
  
  protected PrivateKey unwrapKey(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte, char[] paramArrayOfChar, boolean paramBoolean)
    throws IOException
  {
    String str = paramAlgorithmIdentifier.getAlgorithm().getId();
    PKCS12PBEParams localPKCS12PBEParams = PKCS12PBEParams.getInstance(paramAlgorithmIdentifier.getParameters());
    PBEKeySpec localPBEKeySpec = new PBEKeySpec(paramArrayOfChar);
    try
    {
      SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance(str, bcProvider);
      PBEParameterSpec localPBEParameterSpec = new PBEParameterSpec(localPKCS12PBEParams.getIV(), localPKCS12PBEParams.getIterations().intValue());
      SecretKey localSecretKey = localSecretKeyFactory.generateSecret(localPBEKeySpec);
      ((BCPBEKey)localSecretKey).setTryWrongPKCS12Zero(paramBoolean);
      Cipher localCipher = Cipher.getInstance(str, bcProvider);
      localCipher.init(4, localSecretKey, localPBEParameterSpec);
      PrivateKey localPrivateKey = (PrivateKey)localCipher.unwrap(paramArrayOfByte, "", 2);
      return localPrivateKey;
    }
    catch (Exception localException)
    {
      throw new IOException("exception unwrapping private key - " + localException.toString());
    }
  }
  
  protected byte[] wrapKey(String paramString, Key paramKey, PKCS12PBEParams paramPKCS12PBEParams, char[] paramArrayOfChar)
    throws IOException
  {
    PBEKeySpec localPBEKeySpec = new PBEKeySpec(paramArrayOfChar);
    try
    {
      SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance(paramString, bcProvider);
      PBEParameterSpec localPBEParameterSpec = new PBEParameterSpec(paramPKCS12PBEParams.getIV(), paramPKCS12PBEParams.getIterations().intValue());
      Cipher localCipher = Cipher.getInstance(paramString, bcProvider);
      localCipher.init(3, localSecretKeyFactory.generateSecret(localPBEKeySpec), localPBEParameterSpec);
      byte[] arrayOfByte = localCipher.wrap(paramKey);
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      throw new IOException("exception encrypting data - " + localException.toString());
    }
  }
  
  public static class BCPKCS12KeyStore
    extends JDKPKCS12KeyStore
  {
    public BCPKCS12KeyStore()
    {
      super(pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
    }
  }
  
  public static class BCPKCS12KeyStore3DES
    extends JDKPKCS12KeyStore
  {
    public BCPKCS12KeyStore3DES()
    {
      super(pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
    }
  }
  
  private class CertId
  {
    byte[] id;
    
    CertId(PublicKey paramPublicKey)
    {
      this.id = JDKPKCS12KeyStore.this.createSubjectKeyId(paramPublicKey).getKeyIdentifier();
    }
    
    CertId(byte[] paramArrayOfByte)
    {
      this.id = paramArrayOfByte;
    }
    
    public boolean equals(Object paramObject)
    {
      if (paramObject == this) {
        return true;
      }
      if (!(paramObject instanceof CertId)) {
        return false;
      }
      CertId localCertId = (CertId)paramObject;
      return Arrays.areEqual(this.id, localCertId.id);
    }
    
    public int hashCode()
    {
      return Arrays.hashCode(this.id);
    }
  }
  
  public static class DefPKCS12KeyStore
    extends JDKPKCS12KeyStore
  {
    public DefPKCS12KeyStore()
    {
      super(pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
    }
  }
  
  public static class DefPKCS12KeyStore3DES
    extends JDKPKCS12KeyStore
  {
    public DefPKCS12KeyStore3DES()
    {
      super(pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
    }
  }
  
  private static class IgnoresCaseHashtable
  {
    private Hashtable keys = new Hashtable();
    private Hashtable orig = new Hashtable();
    
    public Enumeration elements()
    {
      return this.orig.elements();
    }
    
    public Object get(String paramString)
    {
      String str = (String)this.keys.get(Strings.toLowerCase(paramString));
      if (str == null) {
        return null;
      }
      return this.orig.get(str);
    }
    
    public Enumeration keys()
    {
      return this.orig.keys();
    }
    
    public void put(String paramString, Object paramObject)
    {
      String str1 = Strings.toLowerCase(paramString);
      String str2 = (String)this.keys.get(str1);
      if (str2 != null) {
        this.orig.remove(str2);
      }
      this.keys.put(str1, paramString);
      this.orig.put(paramString, paramObject);
    }
    
    public Object remove(String paramString)
    {
      String str = (String)this.keys.remove(Strings.toLowerCase(paramString));
      if (str == null) {
        return null;
      }
      return this.orig.remove(str);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.JDKPKCS12KeyStore
 * JD-Core Version:    0.7.0.1
 */