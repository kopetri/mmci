package org.spongycastle.jce;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.CertificationRequest;
import org.spongycastle.asn1.pkcs.CertificationRequestInfo;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509Name;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.Strings;

public class PKCS10CertificationRequest
  extends CertificationRequest
{
  private static Hashtable algorithms = new Hashtable();
  private static Hashtable keyAlgorithms;
  private static Set noParams;
  private static Hashtable oids;
  private static Hashtable params = new Hashtable();
  
  static
  {
    keyAlgorithms = new Hashtable();
    oids = new Hashtable();
    noParams = new HashSet();
    algorithms.put("MD2WITHRSAENCRYPTION", new DERObjectIdentifier("1.2.840.113549.1.1.2"));
    algorithms.put("MD2WITHRSA", new DERObjectIdentifier("1.2.840.113549.1.1.2"));
    algorithms.put("MD5WITHRSAENCRYPTION", new DERObjectIdentifier("1.2.840.113549.1.1.4"));
    algorithms.put("MD5WITHRSA", new DERObjectIdentifier("1.2.840.113549.1.1.4"));
    algorithms.put("RSAWITHMD5", new DERObjectIdentifier("1.2.840.113549.1.1.4"));
    algorithms.put("SHA1WITHRSAENCRYPTION", new DERObjectIdentifier("1.2.840.113549.1.1.5"));
    algorithms.put("SHA1WITHRSA", new DERObjectIdentifier("1.2.840.113549.1.1.5"));
    algorithms.put("SHA224WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha224WithRSAEncryption);
    algorithms.put("SHA224WITHRSA", PKCSObjectIdentifiers.sha224WithRSAEncryption);
    algorithms.put("SHA256WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha256WithRSAEncryption);
    algorithms.put("SHA256WITHRSA", PKCSObjectIdentifiers.sha256WithRSAEncryption);
    algorithms.put("SHA384WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha384WithRSAEncryption);
    algorithms.put("SHA384WITHRSA", PKCSObjectIdentifiers.sha384WithRSAEncryption);
    algorithms.put("SHA512WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512WithRSAEncryption);
    algorithms.put("SHA512WITHRSA", PKCSObjectIdentifiers.sha512WithRSAEncryption);
    algorithms.put("SHA1WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("SHA224WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("SHA256WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("SHA384WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("SHA512WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("RSAWITHSHA1", new DERObjectIdentifier("1.2.840.113549.1.1.5"));
    algorithms.put("RIPEMD128WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
    algorithms.put("RIPEMD128WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
    algorithms.put("RIPEMD160WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
    algorithms.put("RIPEMD160WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
    algorithms.put("RIPEMD256WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
    algorithms.put("RIPEMD256WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
    algorithms.put("SHA1WITHDSA", new DERObjectIdentifier("1.2.840.10040.4.3"));
    algorithms.put("DSAWITHSHA1", new DERObjectIdentifier("1.2.840.10040.4.3"));
    algorithms.put("SHA224WITHDSA", NISTObjectIdentifiers.dsa_with_sha224);
    algorithms.put("SHA256WITHDSA", NISTObjectIdentifiers.dsa_with_sha256);
    algorithms.put("SHA384WITHDSA", NISTObjectIdentifiers.dsa_with_sha384);
    algorithms.put("SHA512WITHDSA", NISTObjectIdentifiers.dsa_with_sha512);
    algorithms.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
    algorithms.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
    algorithms.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
    algorithms.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
    algorithms.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
    algorithms.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
    algorithms.put("GOST3411WITHGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    algorithms.put("GOST3410WITHGOST3411", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    algorithms.put("GOST3411WITHECGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    algorithms.put("GOST3411WITHECGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    algorithms.put("GOST3411WITHGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    oids.put(new DERObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
    oids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3411WITHGOST3410");
    oids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "GOST3411WITHECGOST3410");
    oids.put(new DERObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
    oids.put(new DERObjectIdentifier("1.2.840.113549.1.1.2"), "MD2WITHRSA");
    oids.put(new DERObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
    oids.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
    oids.put(OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
    oids.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
    oids.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
    keyAlgorithms.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
    keyAlgorithms.put(X9ObjectIdentifiers.id_dsa, "DSA");
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA1);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA224);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA256);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA384);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA512);
    noParams.add(X9ObjectIdentifiers.id_dsa_with_sha1);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha224);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha256);
    noParams.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    noParams.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    AlgorithmIdentifier localAlgorithmIdentifier1 = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, new DERNull());
    params.put("SHA1WITHRSAANDMGF1", creatPSSParams(localAlgorithmIdentifier1, 20));
    AlgorithmIdentifier localAlgorithmIdentifier2 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, new DERNull());
    params.put("SHA224WITHRSAANDMGF1", creatPSSParams(localAlgorithmIdentifier2, 28));
    AlgorithmIdentifier localAlgorithmIdentifier3 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, new DERNull());
    params.put("SHA256WITHRSAANDMGF1", creatPSSParams(localAlgorithmIdentifier3, 32));
    AlgorithmIdentifier localAlgorithmIdentifier4 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, new DERNull());
    params.put("SHA384WITHRSAANDMGF1", creatPSSParams(localAlgorithmIdentifier4, 48));
    AlgorithmIdentifier localAlgorithmIdentifier5 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, new DERNull());
    params.put("SHA512WITHRSAANDMGF1", creatPSSParams(localAlgorithmIdentifier5, 64));
  }
  
  public PKCS10CertificationRequest(String paramString, X500Principal paramX500Principal, PublicKey paramPublicKey, ASN1Set paramASN1Set, PrivateKey paramPrivateKey)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    this(paramString, convertName(paramX500Principal), paramPublicKey, paramASN1Set, paramPrivateKey, BouncyCastleProvider.PROVIDER_NAME);
  }
  
  public PKCS10CertificationRequest(String paramString1, X500Principal paramX500Principal, PublicKey paramPublicKey, ASN1Set paramASN1Set, PrivateKey paramPrivateKey, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    this(paramString1, convertName(paramX500Principal), paramPublicKey, paramASN1Set, paramPrivateKey, paramString2);
  }
  
  public PKCS10CertificationRequest(String paramString, X509Name paramX509Name, PublicKey paramPublicKey, ASN1Set paramASN1Set, PrivateKey paramPrivateKey)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    this(paramString, paramX509Name, paramPublicKey, paramASN1Set, paramPrivateKey, BouncyCastleProvider.PROVIDER_NAME);
  }
  
  public PKCS10CertificationRequest(String paramString1, X509Name paramX509Name, PublicKey paramPublicKey, ASN1Set paramASN1Set, PrivateKey paramPrivateKey, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    String str = Strings.toUpperCase(paramString1);
    DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)algorithms.get(str);
    if (localDERObjectIdentifier == null) {}
    try
    {
      localDERObjectIdentifier = new DERObjectIdentifier(str);
      if (paramX509Name == null) {
        throw new IllegalArgumentException("subject must not be null");
      }
    }
    catch (Exception localException2)
    {
      throw new IllegalArgumentException("Unknown signature type requested");
    }
    if (paramPublicKey == null) {
      throw new IllegalArgumentException("public key must not be null");
    }
    if (noParams.contains(localDERObjectIdentifier)) {
      this.sigAlgId = new AlgorithmIdentifier(localDERObjectIdentifier);
    }
    for (;;)
    {
      Signature localSignature;
      try
      {
        this.reqInfo = new CertificationRequestInfo(paramX509Name, new SubjectPublicKeyInfo((ASN1Sequence)ASN1Primitive.fromByteArray(paramPublicKey.getEncoded())), paramASN1Set);
        if (paramString2 == null)
        {
          localSignature = Signature.getInstance(paramString1);
          localSignature.initSign(paramPrivateKey);
        }
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("can't encode public key");
      }
      try
      {
        localSignature.update(this.reqInfo.getEncoded("DER"));
        this.sigBits = new DERBitString(localSignature.sign());
        return;
      }
      catch (Exception localException1)
      {
        throw new IllegalArgumentException("exception encoding TBS cert request - " + localException1);
      }
      if (params.containsKey(str))
      {
        this.sigAlgId = new AlgorithmIdentifier(localDERObjectIdentifier, (ASN1Encodable)params.get(str));
      }
      else
      {
        this.sigAlgId = new AlgorithmIdentifier(localDERObjectIdentifier, DERNull.INSTANCE);
        continue;
        localSignature = Signature.getInstance(paramString1, paramString2);
      }
    }
  }
  
  public PKCS10CertificationRequest(ASN1Sequence paramASN1Sequence)
  {
    super(paramASN1Sequence);
  }
  
  public PKCS10CertificationRequest(byte[] paramArrayOfByte)
  {
    super(toDERSequence(paramArrayOfByte));
  }
  
  private static X509Name convertName(X500Principal paramX500Principal)
  {
    try
    {
      X509Principal localX509Principal = new X509Principal(paramX500Principal.getEncoded());
      return localX509Principal;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("can't convert name");
    }
  }
  
  private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier paramAlgorithmIdentifier, int paramInt)
  {
    return new RSASSAPSSparams(paramAlgorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, paramAlgorithmIdentifier), new ASN1Integer(paramInt), new ASN1Integer(1));
  }
  
  private static String getDigestAlgName(DERObjectIdentifier paramDERObjectIdentifier)
  {
    if (PKCSObjectIdentifiers.md5.equals(paramDERObjectIdentifier)) {
      return "MD5";
    }
    if (OIWObjectIdentifiers.idSHA1.equals(paramDERObjectIdentifier)) {
      return "SHA1";
    }
    if (NISTObjectIdentifiers.id_sha224.equals(paramDERObjectIdentifier)) {
      return "SHA224";
    }
    if (NISTObjectIdentifiers.id_sha256.equals(paramDERObjectIdentifier)) {
      return "SHA256";
    }
    if (NISTObjectIdentifiers.id_sha384.equals(paramDERObjectIdentifier)) {
      return "SHA384";
    }
    if (NISTObjectIdentifiers.id_sha512.equals(paramDERObjectIdentifier)) {
      return "SHA512";
    }
    if (TeleTrusTObjectIdentifiers.ripemd128.equals(paramDERObjectIdentifier)) {
      return "RIPEMD128";
    }
    if (TeleTrusTObjectIdentifiers.ripemd160.equals(paramDERObjectIdentifier)) {
      return "RIPEMD160";
    }
    if (TeleTrusTObjectIdentifiers.ripemd256.equals(paramDERObjectIdentifier)) {
      return "RIPEMD256";
    }
    if (CryptoProObjectIdentifiers.gostR3411.equals(paramDERObjectIdentifier)) {
      return "GOST3411";
    }
    return paramDERObjectIdentifier.getId();
  }
  
  static String getSignatureName(AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    ASN1Encodable localASN1Encodable = paramAlgorithmIdentifier.getParameters();
    if ((localASN1Encodable != null) && (!DERNull.INSTANCE.equals(localASN1Encodable)) && (paramAlgorithmIdentifier.getObjectId().equals(PKCSObjectIdentifiers.id_RSASSA_PSS)))
    {
      RSASSAPSSparams localRSASSAPSSparams = RSASSAPSSparams.getInstance(localASN1Encodable);
      return getDigestAlgName(localRSASSAPSSparams.getHashAlgorithm().getObjectId()) + "withRSAandMGF1";
    }
    return paramAlgorithmIdentifier.getObjectId().getId();
  }
  
  /* Error */
  private void setSignatureParameters(Signature paramSignature, ASN1Encodable paramASN1Encodable)
    throws NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    // Byte code:
    //   0: aload_2
    //   1: ifnull +65 -> 66
    //   4: getstatic 386	org/spongycastle/asn1/DERNull:INSTANCE	Lorg/spongycastle/asn1/DERNull;
    //   7: aload_2
    //   8: invokevirtual 482	org/spongycastle/asn1/DERNull:equals	(Ljava/lang/Object;)Z
    //   11: ifne +55 -> 66
    //   14: aload_1
    //   15: invokevirtual 509	java/security/Signature:getAlgorithm	()Ljava/lang/String;
    //   18: aload_1
    //   19: invokevirtual 513	java/security/Signature:getProvider	()Ljava/security/Provider;
    //   22: invokestatic 518	java/security/AlgorithmParameters:getInstance	(Ljava/lang/String;Ljava/security/Provider;)Ljava/security/AlgorithmParameters;
    //   25: astore_3
    //   26: aload_3
    //   27: aload_2
    //   28: invokeinterface 522 1 0
    //   33: ldc_w 356
    //   36: invokevirtual 523	org/spongycastle/asn1/ASN1Primitive:getEncoded	(Ljava/lang/String;)[B
    //   39: invokevirtual 526	java/security/AlgorithmParameters:init	([B)V
    //   42: aload_1
    //   43: invokevirtual 509	java/security/Signature:getAlgorithm	()Ljava/lang/String;
    //   46: ldc_w 528
    //   49: invokevirtual 534	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   52: ifeq +14 -> 66
    //   55: aload_1
    //   56: aload_3
    //   57: ldc_w 536
    //   60: invokevirtual 540	java/security/AlgorithmParameters:getParameterSpec	(Ljava/lang/Class;)Ljava/security/spec/AlgorithmParameterSpec;
    //   63: invokevirtual 544	java/security/Signature:setParameter	(Ljava/security/spec/AlgorithmParameterSpec;)V
    //   66: return
    //   67: astore 4
    //   69: new 267	java/security/SignatureException
    //   72: dup
    //   73: new 393	java/lang/StringBuilder
    //   76: dup
    //   77: ldc_w 546
    //   80: invokespecial 396	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   83: aload 4
    //   85: invokevirtual 549	java/io/IOException:getMessage	()Ljava/lang/String;
    //   88: invokevirtual 499	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: invokevirtual 404	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   94: invokespecial 550	java/security/SignatureException:<init>	(Ljava/lang/String;)V
    //   97: athrow
    //   98: astore 5
    //   100: new 267	java/security/SignatureException
    //   103: dup
    //   104: new 393	java/lang/StringBuilder
    //   107: dup
    //   108: ldc_w 552
    //   111: invokespecial 396	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   114: aload 5
    //   116: invokevirtual 553	java/security/GeneralSecurityException:getMessage	()Ljava/lang/String;
    //   119: invokevirtual 499	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: invokevirtual 404	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   125: invokespecial 550	java/security/SignatureException:<init>	(Ljava/lang/String;)V
    //   128: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	129	0	this	PKCS10CertificationRequest
    //   0	129	1	paramSignature	Signature
    //   0	129	2	paramASN1Encodable	ASN1Encodable
    //   25	32	3	localAlgorithmParameters	java.security.AlgorithmParameters
    //   67	17	4	localIOException	IOException
    //   98	17	5	localGeneralSecurityException	java.security.GeneralSecurityException
    // Exception table:
    //   from	to	target	type
    //   26	42	67	java/io/IOException
    //   55	66	98	java/security/GeneralSecurityException
  }
  
  private static ASN1Sequence toDERSequence(byte[] paramArrayOfByte)
  {
    try
    {
      ASN1Sequence localASN1Sequence = (ASN1Sequence)new ASN1InputStream(paramArrayOfByte).readObject();
      return localASN1Sequence;
    }
    catch (Exception localException)
    {
      throw new IllegalArgumentException("badly encoded request");
    }
  }
  
  public byte[] getEncoded()
  {
    try
    {
      byte[] arrayOfByte = getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException.toString());
    }
  }
  
  public PublicKey getPublicKey()
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    return getPublicKey(BouncyCastleProvider.PROVIDER_NAME);
  }
  
  public PublicKey getPublicKey(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    SubjectPublicKeyInfo localSubjectPublicKeyInfo = this.reqInfo.getSubjectPublicKeyInfo();
    localX509EncodedKeySpec = new X509EncodedKeySpec(new DERBitString(localSubjectPublicKeyInfo).getBytes());
    localAlgorithmIdentifier = localSubjectPublicKeyInfo.getAlgorithmId();
    if (paramString == null) {}
    try
    {
      return KeyFactory.getInstance(localAlgorithmIdentifier.getObjectId().getId()).generatePublic(localX509EncodedKeySpec);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      PublicKey localPublicKey;
      if (keyAlgorithms.get(localAlgorithmIdentifier.getObjectId()) == null) {
        break label131;
      }
      String str = (String)keyAlgorithms.get(localAlgorithmIdentifier.getObjectId());
      if (paramString != null) {
        break label120;
      }
      return KeyFactory.getInstance(str).generatePublic(localX509EncodedKeySpec);
      return KeyFactory.getInstance(str, paramString).generatePublic(localX509EncodedKeySpec);
      throw localNoSuchAlgorithmException;
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      throw new InvalidKeyException("error decoding public key");
    }
    localPublicKey = KeyFactory.getInstance(localAlgorithmIdentifier.getObjectId().getId(), paramString).generatePublic(localX509EncodedKeySpec);
    return localPublicKey;
  }
  
  public boolean verify()
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    return verify(BouncyCastleProvider.PROVIDER_NAME);
  }
  
  public boolean verify(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    return verify(getPublicKey(paramString), paramString);
  }
  
  public boolean verify(PublicKey paramPublicKey, String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    if (paramString == null) {}
    for (;;)
    {
      try
      {
        Signature localSignature2 = Signature.getInstance(getSignatureName(this.sigAlgId));
        localObject = localSignature2;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        if (oids.get(this.sigAlgId.getObjectId()) == null) {
          continue;
        }
        str = (String)oids.get(this.sigAlgId.getObjectId());
        if (paramString != null) {
          continue;
        }
        Object localObject = Signature.getInstance(str);
        continue;
        localObject = Signature.getInstance(str, paramString);
        continue;
        throw localNoSuchAlgorithmException;
      }
      setSignatureParameters((Signature)localObject, this.sigAlgId.getParameters());
      ((Signature)localObject).initVerify(paramPublicKey);
      try
      {
        ((Signature)localObject).update(this.reqInfo.getEncoded("DER"));
        return ((Signature)localObject).verify(this.sigBits.getBytes());
      }
      catch (Exception localException)
      {
        Signature localSignature1;
        String str;
        throw new SignatureException("exception encoding TBS cert request - " + localException);
      }
      localSignature1 = Signature.getInstance(getSignatureName(this.sigAlgId), paramString);
      localObject = localSignature1;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.PKCS10CertificationRequest
 * JD-Core Version:    0.7.0.1
 */