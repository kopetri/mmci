package org.spongycastle.cms.jcajce;

import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RC2CBCParameter;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.CMSAlgorithm;
import org.spongycastle.cms.CMSEnvelopedDataGenerator;
import org.spongycastle.cms.CMSException;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.SymmetricKeyUnwrapper;
import org.spongycastle.operator.jcajce.JceAsymmetricKeyUnwrapper;

class EnvelopedDataHelper
{
  protected static final Map BASE_CIPHER_NAMES = new HashMap();
  protected static final Map CIPHER_ALG_NAMES = new HashMap();
  protected static final Map MAC_ALG_NAMES = new HashMap();
  private static final short[] rc2Ekb = { 93, 190, 155, 139, 17, 153, 110, 77, 89, 243, 133, 166, 63, 183, 131, 197, 228, 115, 107, 58, 104, 90, 192, 71, 160, 100, 52, 12, 241, 208, 82, 165, 185, 30, 150, 67, 65, 216, 212, 44, 219, 248, 7, 119, 42, 202, 235, 239, 16, 28, 22, 13, 56, 114, 47, 137, 193, 249, 128, 196, 109, 174, 48, 61, 206, 32, 99, 254, 230, 26, 199, 184, 80, 232, 36, 23, 252, 37, 111, 187, 106, 163, 68, 83, 217, 162, 1, 171, 188, 182, 31, 152, 238, 154, 167, 45, 79, 158, 142, 172, 224, 198, 73, 70, 41, 244, 148, 138, 175, 225, 91, 195, 179, 123, 87, 209, 124, 156, 237, 135, 64, 140, 226, 203, 147, 20, 201, 97, 46, 229, 204, 246, 94, 168, 92, 214, 117, 141, 98, 149, 88, 105, 118, 161, 74, 181, 85, 9, 120, 51, 130, 215, 221, 121, 245, 27, 11, 222, 38, 33, 40, 116, 4, 151, 86, 223, 60, 240, 55, 57, 220, 255, 6, 164, 234, 66, 8, 218, 180, 113, 176, 207, 18, 122, 78, 250, 108, 29, 132, 0, 200, 127, 145, 69, 170, 43, 194, 177, 143, 213, 186, 242, 173, 25, 178, 103, 54, 247, 15, 10, 146, 125, 227, 157, 233, 144, 62, 35, 39, 102, 19, 236, 129, 21, 189, 34, 191, 159, 126, 169, 81, 75, 76, 251, 2, 211, 112, 134, 49, 231, 59, 5, 3, 84, 96, 72, 101, 24, 210, 205, 95, 50, 136, 14, 53, 253 };
  private static final short[] rc2Table;
  private JcaJceExtHelper helper;
  
  static
  {
    BASE_CIPHER_NAMES.put(CMSAlgorithm.DES_EDE3_CBC, "DESEDE");
    BASE_CIPHER_NAMES.put(CMSAlgorithm.AES128_CBC, "AES");
    BASE_CIPHER_NAMES.put(CMSAlgorithm.AES192_CBC, "AES");
    BASE_CIPHER_NAMES.put(CMSAlgorithm.AES256_CBC, "AES");
    BASE_CIPHER_NAMES.put(CMSAlgorithm.RC2_CBC, "RC2");
    BASE_CIPHER_NAMES.put(CMSAlgorithm.CAST5_CBC, "CAST5");
    BASE_CIPHER_NAMES.put(CMSAlgorithm.CAMELLIA128_CBC, "Camellia");
    BASE_CIPHER_NAMES.put(CMSAlgorithm.CAMELLIA192_CBC, "Camellia");
    BASE_CIPHER_NAMES.put(CMSAlgorithm.CAMELLIA256_CBC, "Camellia");
    BASE_CIPHER_NAMES.put(CMSAlgorithm.SEED_CBC, "SEED");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.DES_EDE3_CBC, "DESEDE/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.AES128_CBC, "AES/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.AES192_CBC, "AES/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.AES256_CBC, "AES/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(PKCSObjectIdentifiers.rsaEncryption, "RSA/ECB/PKCS1Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.CAST5_CBC, "CAST5/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.CAMELLIA128_CBC, "Camellia/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.CAMELLIA192_CBC, "Camellia/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.CAMELLIA256_CBC, "Camellia/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSAlgorithm.SEED_CBC, "SEED/CBC/PKCS5Padding");
    MAC_ALG_NAMES.put(CMSAlgorithm.DES_EDE3_CBC, "DESEDEMac");
    MAC_ALG_NAMES.put(CMSAlgorithm.AES128_CBC, "AESMac");
    MAC_ALG_NAMES.put(CMSAlgorithm.AES192_CBC, "AESMac");
    MAC_ALG_NAMES.put(CMSAlgorithm.AES256_CBC, "AESMac");
    MAC_ALG_NAMES.put(CMSAlgorithm.RC2_CBC, "RC2Mac");
    rc2Table = new short[] { 189, 86, 234, 242, 162, 241, 172, 42, 176, 147, 209, 156, 27, 51, 253, 208, 48, 4, 182, 220, 125, 223, 50, 75, 247, 203, 69, 155, 49, 187, 33, 90, 65, 159, 225, 217, 74, 77, 158, 218, 160, 104, 44, 195, 39, 95, 128, 54, 62, 238, 251, 149, 26, 254, 206, 168, 52, 169, 19, 240, 166, 63, 216, 12, 120, 36, 175, 35, 82, 193, 103, 23, 245, 102, 144, 231, 232, 7, 184, 96, 72, 230, 30, 83, 243, 146, 164, 114, 140, 8, 21, 110, 134, 0, 132, 250, 244, 127, 138, 66, 25, 246, 219, 205, 20, 141, 80, 18, 186, 60, 6, 78, 236, 179, 53, 17, 161, 136, 142, 43, 148, 153, 183, 113, 116, 211, 228, 191, 58, 222, 150, 14, 188, 10, 237, 119, 252, 55, 107, 3, 121, 137, 98, 198, 215, 192, 210, 124, 106, 139, 34, 163, 91, 5, 93, 2, 117, 213, 97, 227, 24, 143, 85, 81, 173, 31, 11, 94, 133, 229, 194, 87, 99, 202, 61, 108, 180, 197, 204, 112, 178, 145, 89, 13, 71, 32, 200, 79, 88, 224, 1, 226, 22, 56, 196, 111, 59, 15, 101, 70, 190, 126, 45, 123, 130, 249, 64, 181, 29, 115, 248, 235, 38, 199, 135, 151, 37, 84, 177, 40, 170, 152, 157, 165, 100, 109, 122, 212, 16, 129, 68, 239, 73, 214, 174, 46, 221, 118, 92, 47, 167, 28, 201, 9, 105, 154, 131, 207, 41, 57, 185, 233, 76, 255, 67, 171 };
  }
  
  EnvelopedDataHelper(JcaJceExtHelper paramJcaJceExtHelper)
  {
    this.helper = paramJcaJceExtHelper;
  }
  
  static Object execute(JCECallback paramJCECallback)
    throws CMSException
  {
    try
    {
      Object localObject = paramJCECallback.doInJCE();
      return localObject;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new CMSException("can't find algorithm.", localNoSuchAlgorithmException);
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw new CMSException("key invalid in message.", localInvalidKeyException);
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new CMSException("can't find provider.", localNoSuchProviderException);
    }
    catch (NoSuchPaddingException localNoSuchPaddingException)
    {
      throw new CMSException("required padding not supported.", localNoSuchPaddingException);
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      throw new CMSException("algorithm parameters invalid.", localInvalidAlgorithmParameterException);
    }
    catch (InvalidParameterSpecException localInvalidParameterSpecException)
    {
      throw new CMSException("MAC algorithm parameter spec invalid.", localInvalidParameterSpecException);
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
  
  public JceAsymmetricKeyUnwrapper createAsymmetricUnwrapper(AlgorithmIdentifier paramAlgorithmIdentifier, PrivateKey paramPrivateKey)
  {
    return this.helper.createAsymmetricUnwrapper(paramAlgorithmIdentifier, paramPrivateKey);
  }
  
  Cipher createCipher(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CMSException
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
      throw new CMSException("cannot create cipher: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  Cipher createContentCipher(final Key paramKey, final AlgorithmIdentifier paramAlgorithmIdentifier)
    throws CMSException
  {
    (Cipher)execute(new JCECallback()
    {
      public Object doInJCE()
        throws CMSException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException
      {
        Cipher localCipher = EnvelopedDataHelper.this.createCipher(paramAlgorithmIdentifier.getAlgorithm());
        ASN1Encodable localASN1Encodable = paramAlgorithmIdentifier.getParameters();
        String str = paramAlgorithmIdentifier.getAlgorithm().getId();
        if ((localASN1Encodable != null) && (!(localASN1Encodable instanceof ASN1Null))) {
          try
          {
            AlgorithmParameters localAlgorithmParameters = EnvelopedDataHelper.this.createAlgorithmParameters(paramAlgorithmIdentifier.getAlgorithm());
            try
            {
              localAlgorithmParameters.init(localASN1Encodable.toASN1Primitive().getEncoded(), "ASN.1");
              localCipher.init(2, paramKey, localAlgorithmParameters);
              return localCipher;
            }
            catch (IOException localIOException)
            {
              throw new CMSException("error decoding algorithm parameters.", localIOException);
            }
            if (str.equals(CMSEnvelopedDataGenerator.DES_EDE3_CBC)) {
              break label211;
            }
          }
          catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
          {
            if ((str.equals(CMSEnvelopedDataGenerator.DES_EDE3_CBC)) || (str.equals("1.3.6.1.4.1.188.7.1.1.2")) || (str.equals(CMSEnvelopedDataGenerator.AES128_CBC)) || (str.equals(CMSEnvelopedDataGenerator.AES192_CBC)) || (str.equals(CMSEnvelopedDataGenerator.AES256_CBC)))
            {
              localCipher.init(2, paramKey, new IvParameterSpec(ASN1OctetString.getInstance(localASN1Encodable).getOctets()));
              return localCipher;
            }
            throw localNoSuchAlgorithmException;
          }
        } else {
          if ((!str.equals("1.3.6.1.4.1.188.7.1.1.2")) && (!str.equals("1.2.840.113533.7.66.10"))) {
            break label233;
          }
        }
        label211:
        localCipher.init(2, paramKey, new IvParameterSpec(new byte[8]));
        return localCipher;
        label233:
        localCipher.init(2, paramKey);
        return localCipher;
      }
    });
  }
  
  Mac createContentMac(final Key paramKey, final AlgorithmIdentifier paramAlgorithmIdentifier)
    throws CMSException
  {
    (Mac)execute(new JCECallback()
    {
      public Object doInJCE()
        throws CMSException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException
      {
        Mac localMac = EnvelopedDataHelper.this.createMac(paramAlgorithmIdentifier.getAlgorithm());
        ASN1Encodable localASN1Encodable = paramAlgorithmIdentifier.getParameters();
        paramAlgorithmIdentifier.getAlgorithm().getId();
        if ((localASN1Encodable != null) && (!(localASN1Encodable instanceof ASN1Null))) {
          try
          {
            AlgorithmParameters localAlgorithmParameters = EnvelopedDataHelper.this.createAlgorithmParameters(paramAlgorithmIdentifier.getAlgorithm());
            try
            {
              localAlgorithmParameters.init(localASN1Encodable.toASN1Primitive().getEncoded(), "ASN.1");
              localMac.init(paramKey, localAlgorithmParameters.getParameterSpec(IvParameterSpec.class));
              return localMac;
            }
            catch (IOException localIOException)
            {
              throw new CMSException("error decoding algorithm parameters.", localIOException);
            }
            localMac.init(paramKey);
          }
          catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
          {
            throw localNoSuchAlgorithmException;
          }
        }
        return localMac;
      }
    });
  }
  
  KeyAgreement createKeyAgreement(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CMSException
  {
    try
    {
      String str = (String)BASE_CIPHER_NAMES.get(paramASN1ObjectIdentifier);
      if (str != null) {
        try
        {
          KeyAgreement localKeyAgreement2 = this.helper.createKeyAgreement(str);
          return localKeyAgreement2;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
      }
      KeyAgreement localKeyAgreement1 = this.helper.createKeyAgreement(paramASN1ObjectIdentifier.getId());
      return localKeyAgreement1;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CMSException("cannot create key pair generator: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  public KeyFactory createKeyFactory(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CMSException
  {
    try
    {
      String str = (String)BASE_CIPHER_NAMES.get(paramASN1ObjectIdentifier);
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
      throw new CMSException("cannot create key factory: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  public KeyGenerator createKeyGenerator(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CMSException
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
      throw new CMSException("cannot create key generator: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  KeyPairGenerator createKeyPairGenerator(DERObjectIdentifier paramDERObjectIdentifier)
    throws CMSException
  {
    try
    {
      String str = (String)BASE_CIPHER_NAMES.get(paramDERObjectIdentifier);
      if (str != null) {
        try
        {
          KeyPairGenerator localKeyPairGenerator2 = this.helper.createKeyPairGenerator(str);
          return localKeyPairGenerator2;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
      }
      KeyPairGenerator localKeyPairGenerator1 = this.helper.createKeyPairGenerator(paramDERObjectIdentifier.getId());
      return localKeyPairGenerator1;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CMSException("cannot create key pair generator: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  Mac createMac(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CMSException
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
      throw new CMSException("cannot create mac: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  Cipher createRFC3211Wrapper(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CMSException
  {
    String str1 = (String)BASE_CIPHER_NAMES.get(paramASN1ObjectIdentifier);
    if (str1 == null) {
      throw new CMSException("no name for " + paramASN1ObjectIdentifier);
    }
    String str2 = str1 + "RFC3211Wrap";
    try
    {
      Cipher localCipher = this.helper.createCipher(str2);
      return localCipher;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CMSException("cannot create cipher: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  public SymmetricKeyUnwrapper createSymmetricUnwrapper(AlgorithmIdentifier paramAlgorithmIdentifier, SecretKey paramSecretKey)
  {
    return this.helper.createSymmetricUnwrapper(paramAlgorithmIdentifier, paramSecretKey);
  }
  
  /* Error */
  AlgorithmParameters generateParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier, SecretKey paramSecretKey, java.security.SecureRandom paramSecureRandom)
    throws CMSException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 525	org/spongycastle/cms/jcajce/EnvelopedDataHelper:createAlgorithmParameterGenerator	(Lorg/spongycastle/asn1/ASN1ObjectIdentifier;)Ljava/security/AlgorithmParameterGenerator;
    //   5: astore 6
    //   7: aload_1
    //   8: getstatic 530	org/spongycastle/cms/CMSEnvelopedDataGenerator:RC2_CBC	Ljava/lang/String;
    //   11: invokevirtual 534	org/spongycastle/asn1/ASN1ObjectIdentifier:equals	(Ljava/lang/Object;)Z
    //   14: ifeq +40 -> 54
    //   17: bipush 8
    //   19: newarray byte
    //   21: astore 7
    //   23: aload_3
    //   24: aload 7
    //   26: invokevirtual 540	java/security/SecureRandom:nextBytes	([B)V
    //   29: aload 6
    //   31: new 542	javax/crypto/spec/RC2ParameterSpec
    //   34: dup
    //   35: bipush 8
    //   37: aload_2
    //   38: invokeinterface 548 1 0
    //   43: arraylength
    //   44: imul
    //   45: aload 7
    //   47: invokespecial 551	javax/crypto/spec/RC2ParameterSpec:<init>	(I[B)V
    //   50: aload_3
    //   51: invokevirtual 557	java/security/AlgorithmParameterGenerator:init	(Ljava/security/spec/AlgorithmParameterSpec;Ljava/security/SecureRandom;)V
    //   54: aload 6
    //   56: invokevirtual 560	java/security/AlgorithmParameterGenerator:generateParameters	()Ljava/security/AlgorithmParameters;
    //   59: areturn
    //   60: astore 8
    //   62: new 368	org/spongycastle/cms/CMSException
    //   65: dup
    //   66: new 438	java/lang/StringBuilder
    //   69: dup
    //   70: ldc_w 562
    //   73: invokespecial 443	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   76: aload 8
    //   78: invokevirtual 513	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   81: invokevirtual 453	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   84: aload 8
    //   86: invokespecial 391	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   89: athrow
    //   90: astore 5
    //   92: aconst_null
    //   93: areturn
    //   94: astore 4
    //   96: new 368	org/spongycastle/cms/CMSException
    //   99: dup
    //   100: new 438	java/lang/StringBuilder
    //   103: dup
    //   104: ldc_w 564
    //   107: invokespecial 443	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   110: aload 4
    //   112: invokevirtual 513	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   115: invokevirtual 453	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: aload 4
    //   120: invokespecial 391	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   123: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	124	0	this	EnvelopedDataHelper
    //   0	124	1	paramASN1ObjectIdentifier	ASN1ObjectIdentifier
    //   0	124	2	paramSecretKey	SecretKey
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
    throws CMSException
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
        throw new CMSException("cannot encode parameters: " + localIOException.getMessage(), localIOException);
      }
      Object localObject = DERNull.INSTANCE;
    }
  }
  
  public AlgorithmIdentifier getAlgorithmIdentifier(ASN1ObjectIdentifier paramASN1ObjectIdentifier, AlgorithmParameterSpec paramAlgorithmParameterSpec)
  {
    if ((paramAlgorithmParameterSpec instanceof IvParameterSpec)) {
      return new AlgorithmIdentifier(paramASN1ObjectIdentifier, new DEROctetString(((IvParameterSpec)paramAlgorithmParameterSpec).getIV()));
    }
    if ((paramAlgorithmParameterSpec instanceof RC2ParameterSpec))
    {
      RC2ParameterSpec localRC2ParameterSpec = (RC2ParameterSpec)paramAlgorithmParameterSpec;
      int i = ((RC2ParameterSpec)paramAlgorithmParameterSpec).getEffectiveKeyBits();
      if (i != -1)
      {
        if (i < 256) {}
        for (int j = rc2Table[i];; j = i) {
          return new AlgorithmIdentifier(paramASN1ObjectIdentifier, new RC2CBCParameter(j, localRC2ParameterSpec.getIV()));
        }
      }
      return new AlgorithmIdentifier(paramASN1ObjectIdentifier, new RC2CBCParameter(localRC2ParameterSpec.getIV()));
    }
    throw new IllegalStateException("unknown parameter spec: " + paramAlgorithmParameterSpec);
  }
  
  String getBaseCipherName(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    String str = (String)BASE_CIPHER_NAMES.get(paramASN1ObjectIdentifier);
    if (str == null) {
      str = paramASN1ObjectIdentifier.getId();
    }
    return str;
  }
  
  Key getJceKey(ASN1ObjectIdentifier paramASN1ObjectIdentifier, GenericKey paramGenericKey)
  {
    if ((paramGenericKey.getRepresentation() instanceof Key)) {
      return (Key)paramGenericKey.getRepresentation();
    }
    if ((paramGenericKey.getRepresentation() instanceof byte[])) {
      return new SecretKeySpec((byte[])paramGenericKey.getRepresentation(), getBaseCipherName(paramASN1ObjectIdentifier));
    }
    throw new IllegalArgumentException("unknown generic key type");
  }
  
  Key getJceKey(GenericKey paramGenericKey)
  {
    if ((paramGenericKey.getRepresentation() instanceof Key)) {
      return (Key)paramGenericKey.getRepresentation();
    }
    if ((paramGenericKey.getRepresentation() instanceof byte[])) {
      return new SecretKeySpec((byte[])paramGenericKey.getRepresentation(), "ENC");
    }
    throw new IllegalArgumentException("unknown generic key type");
  }
  
  static abstract interface JCECallback
  {
    public abstract Object doInJCE()
      throws CMSException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.EnvelopedDataHelper
 * JD-Core Version:    0.7.0.1
 */