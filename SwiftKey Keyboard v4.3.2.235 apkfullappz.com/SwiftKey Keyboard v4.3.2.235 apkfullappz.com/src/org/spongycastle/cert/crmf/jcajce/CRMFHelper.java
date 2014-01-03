package org.spongycastle.cert.crmf.jcajce;

import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.iana.IANAObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.cert.crmf.CRMFException;
import org.spongycastle.cms.CMSAlgorithm;
import org.spongycastle.cms.CMSEnvelopedDataGenerator;
import org.spongycastle.jcajce.JcaJceHelper;

class CRMFHelper
{
  protected static final Map BASE_CIPHER_NAMES = new HashMap();
  protected static final Map CIPHER_ALG_NAMES = new HashMap();
  protected static final Map DIGEST_ALG_NAMES = new HashMap();
  protected static final Map KEY_ALG_NAMES = new HashMap();
  protected static final Map MAC_ALG_NAMES = new HashMap();
  private JcaJceHelper helper;
  
  static
  {
    BASE_CIPHER_NAMES.put(PKCSObjectIdentifiers.des_EDE3_CBC, "DESEDE");
    BASE_CIPHER_NAMES.put(NISTObjectIdentifiers.id_aes128_CBC, "AES");
    BASE_CIPHER_NAMES.put(NISTObjectIdentifiers.id_aes192_CBC, "AES");
    BASE_CIPHER_NAMES.put(NISTObjectIdentifiers.id_aes256_CBC, "AES");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.DES_EDE3_CBC, "DESEDE/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.AES128_CBC, "AES/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.AES192_CBC, "AES/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.AES256_CBC, "AES/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(new ASN1ObjectIdentifier(PKCSObjectIdentifiers.rsaEncryption.getId()), "RSA/ECB/PKCS1Padding");
    DIGEST_ALG_NAMES.put(OIWObjectIdentifiers.idSHA1, "SHA1");
    DIGEST_ALG_NAMES.put(NISTObjectIdentifiers.id_sha224, "SHA224");
    DIGEST_ALG_NAMES.put(NISTObjectIdentifiers.id_sha256, "SHA256");
    DIGEST_ALG_NAMES.put(NISTObjectIdentifiers.id_sha384, "SHA384");
    DIGEST_ALG_NAMES.put(NISTObjectIdentifiers.id_sha512, "SHA512");
    MAC_ALG_NAMES.put(IANAObjectIdentifiers.hmacSHA1, "HMACSHA1");
    MAC_ALG_NAMES.put(PKCSObjectIdentifiers.id_hmacWithSHA1, "HMACSHA1");
    MAC_ALG_NAMES.put(PKCSObjectIdentifiers.id_hmacWithSHA224, "HMACSHA224");
    MAC_ALG_NAMES.put(PKCSObjectIdentifiers.id_hmacWithSHA256, "HMACSHA256");
    MAC_ALG_NAMES.put(PKCSObjectIdentifiers.id_hmacWithSHA384, "HMACSHA384");
    MAC_ALG_NAMES.put(PKCSObjectIdentifiers.id_hmacWithSHA512, "HMACSHA512");
    KEY_ALG_NAMES.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
    KEY_ALG_NAMES.put(X9ObjectIdentifiers.id_dsa, "DSA");
  }
  
  CRMFHelper(JcaJceHelper paramJcaJceHelper)
  {
    this.helper = paramJcaJceHelper;
  }
  
  static Object execute(JCECallback paramJCECallback)
    throws CRMFException
  {
    try
    {
      Object localObject = paramJCECallback.doInJCE();
      return localObject;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new CRMFException("can't find algorithm.", localNoSuchAlgorithmException);
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw new CRMFException("key invalid in message.", localInvalidKeyException);
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new CRMFException("can't find provider.", localNoSuchProviderException);
    }
    catch (NoSuchPaddingException localNoSuchPaddingException)
    {
      throw new CRMFException("required padding not supported.", localNoSuchPaddingException);
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      throw new CRMFException("algorithm parameters invalid.", localInvalidAlgorithmParameterException);
    }
    catch (InvalidParameterSpecException localInvalidParameterSpecException)
    {
      throw new CRMFException("MAC algorithm parameter spec invalid.", localInvalidParameterSpecException);
    }
  }
  
  AlgorithmParameterGenerator createAlgorithmParameterGenerator(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws GeneralSecurityException
  {
    String str = (String)BASE_CIPHER_NAMES.get(paramASN1ObjectIdentifier);
    if (str != null) {
      try
      {
        AlgorithmParameterGenerator localAlgorithmParameterGenerator = this.helper.createAlgorithmParameterGenerator(str);
        return localAlgorithmParameterGenerator;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
    }
    return this.helper.createAlgorithmParameterGenerator(paramASN1ObjectIdentifier.getId());
  }
  
  AlgorithmParameters createAlgorithmParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    String str = (String)BASE_CIPHER_NAMES.get(paramASN1ObjectIdentifier);
    if (str != null) {
      try
      {
        AlgorithmParameters localAlgorithmParameters = this.helper.createAlgorithmParameters(str);
        return localAlgorithmParameters;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
    }
    return this.helper.createAlgorithmParameters(paramASN1ObjectIdentifier.getId());
  }
  
  Cipher createCipher(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CRMFException
  {
    try
    {
      String str = (String)CIPHER_ALG_NAMES.get(paramASN1ObjectIdentifier);
      if (str != null) {
        try
        {
          Cipher localCipher2 = this.helper.createCipher(str);
          return localCipher2;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
      }
      Cipher localCipher1 = this.helper.createCipher(paramASN1ObjectIdentifier.getId());
      return localCipher1;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CRMFException("cannot create cipher: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  Cipher createContentCipher(final Key paramKey, final AlgorithmIdentifier paramAlgorithmIdentifier)
    throws CRMFException
  {
    (Cipher)execute(new JCECallback()
    {
      public Object doInJCE()
        throws CRMFException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException
      {
        Cipher localCipher = CRMFHelper.this.createCipher(paramAlgorithmIdentifier.getAlgorithm());
        ASN1Primitive localASN1Primitive = (ASN1Primitive)paramAlgorithmIdentifier.getParameters();
        String str = paramAlgorithmIdentifier.getAlgorithm().getId();
        if ((localASN1Primitive != null) && (!(localASN1Primitive instanceof ASN1Null))) {
          try
          {
            AlgorithmParameters localAlgorithmParameters = CRMFHelper.this.createAlgorithmParameters(paramAlgorithmIdentifier.getAlgorithm());
            try
            {
              localAlgorithmParameters.init(localASN1Primitive.getEncoded(), "ASN.1");
              localCipher.init(2, paramKey, localAlgorithmParameters);
              return localCipher;
            }
            catch (IOException localIOException)
            {
              throw new CRMFException("error decoding algorithm parameters.", localIOException);
            }
            if (str.equals(CMSEnvelopedDataGenerator.DES_EDE3_CBC)) {
              break label209;
            }
          }
          catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
          {
            if ((str.equals(CMSEnvelopedDataGenerator.DES_EDE3_CBC)) || (str.equals("1.3.6.1.4.1.188.7.1.1.2")) || (str.equals(CMSEnvelopedDataGenerator.AES128_CBC)) || (str.equals(CMSEnvelopedDataGenerator.AES192_CBC)) || (str.equals(CMSEnvelopedDataGenerator.AES256_CBC)))
            {
              localCipher.init(2, paramKey, new IvParameterSpec(ASN1OctetString.getInstance(localASN1Primitive).getOctets()));
              return localCipher;
            }
            throw localNoSuchAlgorithmException;
          }
        } else {
          if ((!str.equals("1.3.6.1.4.1.188.7.1.1.2")) && (!str.equals("1.2.840.113533.7.66.10"))) {
            break label231;
          }
        }
        label209:
        localCipher.init(2, paramKey, new IvParameterSpec(new byte[8]));
        return localCipher;
        label231:
        localCipher.init(2, paramKey);
        return localCipher;
      }
    });
  }
  
  MessageDigest createDigest(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CRMFException
  {
    try
    {
      String str = (String)DIGEST_ALG_NAMES.get(paramASN1ObjectIdentifier);
      if (str != null) {
        try
        {
          MessageDigest localMessageDigest2 = this.helper.createDigest(str);
          return localMessageDigest2;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
      }
      MessageDigest localMessageDigest1 = this.helper.createDigest(paramASN1ObjectIdentifier.getId());
      return localMessageDigest1;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CRMFException("cannot create cipher: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  KeyFactory createKeyFactory(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CRMFException
  {
    try
    {
      String str = (String)KEY_ALG_NAMES.get(paramASN1ObjectIdentifier);
      if (str != null) {
        try
        {
          KeyFactory localKeyFactory2 = this.helper.createKeyFactory(str);
          return localKeyFactory2;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
      }
      KeyFactory localKeyFactory1 = this.helper.createKeyFactory(paramASN1ObjectIdentifier.getId());
      return localKeyFactory1;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CRMFException("cannot create cipher: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  public KeyGenerator createKeyGenerator(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CRMFException
  {
    try
    {
      String str = (String)BASE_CIPHER_NAMES.get(paramASN1ObjectIdentifier);
      if (str != null) {
        try
        {
          KeyGenerator localKeyGenerator2 = this.helper.createKeyGenerator(str);
          return localKeyGenerator2;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
      }
      KeyGenerator localKeyGenerator1 = this.helper.createKeyGenerator(paramASN1ObjectIdentifier.getId());
      return localKeyGenerator1;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CRMFException("cannot create key generator: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  Mac createMac(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CRMFException
  {
    try
    {
      String str = (String)MAC_ALG_NAMES.get(paramASN1ObjectIdentifier);
      if (str != null) {
        try
        {
          Mac localMac2 = this.helper.createMac(str);
          return localMac2;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
      }
      Mac localMac1 = this.helper.createMac(paramASN1ObjectIdentifier.getId());
      return localMac1;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CRMFException("cannot create mac: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  /* Error */
  AlgorithmParameters generateParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier, javax.crypto.SecretKey paramSecretKey, java.security.SecureRandom paramSecureRandom)
    throws CRMFException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 274	org/spongycastle/cert/crmf/jcajce/CRMFHelper:createAlgorithmParameterGenerator	(Lorg/spongycastle/asn1/ASN1ObjectIdentifier;)Ljava/security/AlgorithmParameterGenerator;
    //   5: astore 6
    //   7: aload_1
    //   8: getstatic 280	org/spongycastle/cms/CMSEnvelopedDataGenerator:RC2_CBC	Ljava/lang/String;
    //   11: invokevirtual 284	org/spongycastle/asn1/ASN1ObjectIdentifier:equals	(Ljava/lang/Object;)Z
    //   14: ifeq +40 -> 54
    //   17: bipush 8
    //   19: newarray byte
    //   21: astore 7
    //   23: aload_3
    //   24: aload 7
    //   26: invokevirtual 290	java/security/SecureRandom:nextBytes	([B)V
    //   29: aload 6
    //   31: new 292	javax/crypto/spec/RC2ParameterSpec
    //   34: dup
    //   35: bipush 8
    //   37: aload_2
    //   38: invokeinterface 298 1 0
    //   43: arraylength
    //   44: imul
    //   45: aload 7
    //   47: invokespecial 301	javax/crypto/spec/RC2ParameterSpec:<init>	(I[B)V
    //   50: aload_3
    //   51: invokevirtual 307	java/security/AlgorithmParameterGenerator:init	(Ljava/security/spec/AlgorithmParameterSpec;Ljava/security/SecureRandom;)V
    //   54: aload 6
    //   56: invokevirtual 310	java/security/AlgorithmParameterGenerator:generateParameters	()Ljava/security/AlgorithmParameters;
    //   59: areturn
    //   60: astore 8
    //   62: new 162	org/spongycastle/cert/crmf/CRMFException
    //   65: dup
    //   66: new 222	java/lang/StringBuilder
    //   69: dup
    //   70: ldc_w 312
    //   73: invokespecial 225	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   76: aload 8
    //   78: invokevirtual 315	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   81: invokevirtual 235	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   84: aload 8
    //   86: invokespecial 185	org/spongycastle/cert/crmf/CRMFException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   89: athrow
    //   90: astore 5
    //   92: aconst_null
    //   93: areturn
    //   94: astore 4
    //   96: new 162	org/spongycastle/cert/crmf/CRMFException
    //   99: dup
    //   100: new 222	java/lang/StringBuilder
    //   103: dup
    //   104: ldc_w 317
    //   107: invokespecial 225	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   110: aload 4
    //   112: invokevirtual 315	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   115: invokevirtual 235	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: aload 4
    //   120: invokespecial 185	org/spongycastle/cert/crmf/CRMFException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   123: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	124	0	this	CRMFHelper
    //   0	124	1	paramASN1ObjectIdentifier	ASN1ObjectIdentifier
    //   0	124	2	paramSecretKey	javax.crypto.SecretKey
    //   0	124	3	paramSecureRandom	java.security.SecureRandom
    //   94	25	4	localGeneralSecurityException	GeneralSecurityException
    //   90	1	5	localNoSuchAlgorithmException	NoSuchAlgorithmException
    //   5	50	6	localAlgorithmParameterGenerator	AlgorithmParameterGenerator
    //   21	25	7	arrayOfByte	byte[]
    //   60	25	8	localInvalidAlgorithmParameterException	InvalidAlgorithmParameterException
    // Exception table:
    //   from	to	target	type
    //   29	54	60	java/security/InvalidAlgorithmParameterException
    //   0	29	90	java/security/NoSuchAlgorithmException
    //   29	54	90	java/security/NoSuchAlgorithmException
    //   54	60	90	java/security/NoSuchAlgorithmException
    //   62	90	90	java/security/NoSuchAlgorithmException
    //   0	29	94	java/security/GeneralSecurityException
    //   29	54	94	java/security/GeneralSecurityException
    //   54	60	94	java/security/GeneralSecurityException
    //   62	90	94	java/security/GeneralSecurityException
  }
  
  AlgorithmIdentifier getAlgorithmIdentifier(ASN1ObjectIdentifier paramASN1ObjectIdentifier, AlgorithmParameters paramAlgorithmParameters)
    throws CRMFException
  {
    if (paramAlgorithmParameters != null) {}
    for (;;)
    {
      try
      {
        ASN1Primitive localASN1Primitive = ASN1Primitive.fromByteArray(paramAlgorithmParameters.getEncoded("ASN.1"));
        localObject = localASN1Primitive;
        return new AlgorithmIdentifier(paramASN1ObjectIdentifier, (ASN1Encodable)localObject);
      }
      catch (IOException localIOException)
      {
        throw new CRMFException("cannot encode parameters: " + localIOException.getMessage(), localIOException);
      }
      Object localObject = DERNull.INSTANCE;
    }
  }
  
  PublicKey toPublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws CRMFException
  {
    X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(new DERBitString(paramSubjectPublicKeyInfo).getBytes());
    AlgorithmIdentifier localAlgorithmIdentifier = paramSubjectPublicKeyInfo.getAlgorithmId();
    try
    {
      PublicKey localPublicKey = createKeyFactory(localAlgorithmIdentifier.getAlgorithm()).generatePublic(localX509EncodedKeySpec);
      return localPublicKey;
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      throw new CRMFException("invalid key: " + localInvalidKeySpecException.getMessage(), localInvalidKeySpecException);
    }
  }
  
  static abstract interface JCECallback
  {
    public abstract Object doInJCE()
      throws CRMFException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.jcajce.CRMFHelper
 * JD-Core Version:    0.7.0.1
 */