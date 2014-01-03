package org.spongycastle.operator.jcajce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PSSParameterSpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.kisa.KISAObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.ntt.NTTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.jcajce.JcaJceHelper;
import org.spongycastle.operator.OperatorCreationException;

class OperatorHelper
{
  private static final Map asymmetricWrapperAlgNames;
  private static final Map oids = new HashMap();
  private static final Map symmetricKeyAlgNames;
  private static final Map symmetricWrapperAlgNames;
  private JcaJceHelper helper;
  
  static
  {
    asymmetricWrapperAlgNames = new HashMap();
    symmetricWrapperAlgNames = new HashMap();
    symmetricKeyAlgNames = new HashMap();
    oids.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
    oids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3411WITHGOST3410");
    oids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "GOST3411WITHECGOST3410");
    oids.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
    oids.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.2"), "MD2WITHRSA");
    oids.put(new ASN1ObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
    oids.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
    oids.put(OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
    oids.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
    oids.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
    asymmetricWrapperAlgNames.put(PKCSObjectIdentifiers.rsaEncryption, "RSA/ECB/PKCS1Padding");
    symmetricWrapperAlgNames.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap, "DESEDEWrap");
    symmetricWrapperAlgNames.put(PKCSObjectIdentifiers.id_alg_CMSRC2wrap, "RC2Wrap");
    symmetricWrapperAlgNames.put(NISTObjectIdentifiers.id_aes128_wrap, "AESWrap");
    symmetricWrapperAlgNames.put(NISTObjectIdentifiers.id_aes192_wrap, "AESWrap");
    symmetricWrapperAlgNames.put(NISTObjectIdentifiers.id_aes256_wrap, "AESWrap");
    symmetricWrapperAlgNames.put(NTTObjectIdentifiers.id_camellia128_wrap, "CamelliaWrap");
    symmetricWrapperAlgNames.put(NTTObjectIdentifiers.id_camellia192_wrap, "CamelliaWrap");
    symmetricWrapperAlgNames.put(NTTObjectIdentifiers.id_camellia256_wrap, "CamelliaWrap");
    symmetricWrapperAlgNames.put(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap, "SEEDWrap");
    symmetricWrapperAlgNames.put(PKCSObjectIdentifiers.des_EDE3_CBC, "DESede");
    symmetricKeyAlgNames.put(NISTObjectIdentifiers.aes, "AES");
    symmetricKeyAlgNames.put(NISTObjectIdentifiers.id_aes128_CBC, "AES");
    symmetricKeyAlgNames.put(NISTObjectIdentifiers.id_aes192_CBC, "AES");
    symmetricKeyAlgNames.put(NISTObjectIdentifiers.id_aes256_CBC, "AES");
    symmetricKeyAlgNames.put(PKCSObjectIdentifiers.des_EDE3_CBC, "DESede");
    symmetricKeyAlgNames.put(PKCSObjectIdentifiers.RC2_CBC, "RC2");
  }
  
  OperatorHelper(JcaJceHelper paramJcaJceHelper)
  {
    this.helper = paramJcaJceHelper;
  }
  
  private static String getDigestAlgName(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    if (PKCSObjectIdentifiers.md5.equals(paramASN1ObjectIdentifier)) {
      return "MD5";
    }
    if (OIWObjectIdentifiers.idSHA1.equals(paramASN1ObjectIdentifier)) {
      return "SHA1";
    }
    if (NISTObjectIdentifiers.id_sha224.equals(paramASN1ObjectIdentifier)) {
      return "SHA224";
    }
    if (NISTObjectIdentifiers.id_sha256.equals(paramASN1ObjectIdentifier)) {
      return "SHA256";
    }
    if (NISTObjectIdentifiers.id_sha384.equals(paramASN1ObjectIdentifier)) {
      return "SHA384";
    }
    if (NISTObjectIdentifiers.id_sha512.equals(paramASN1ObjectIdentifier)) {
      return "SHA512";
    }
    if (TeleTrusTObjectIdentifiers.ripemd128.equals(paramASN1ObjectIdentifier)) {
      return "RIPEMD128";
    }
    if (TeleTrusTObjectIdentifiers.ripemd160.equals(paramASN1ObjectIdentifier)) {
      return "RIPEMD160";
    }
    if (TeleTrusTObjectIdentifiers.ripemd256.equals(paramASN1ObjectIdentifier)) {
      return "RIPEMD256";
    }
    if (CryptoProObjectIdentifiers.gostR3411.equals(paramASN1ObjectIdentifier)) {
      return "GOST3411";
    }
    return paramASN1ObjectIdentifier.getId();
  }
  
  private static String getSignatureName(AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    ASN1Encodable localASN1Encodable = paramAlgorithmIdentifier.getParameters();
    if ((localASN1Encodable != null) && (!DERNull.INSTANCE.equals(localASN1Encodable)) && (paramAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.id_RSASSA_PSS)))
    {
      RSASSAPSSparams localRSASSAPSSparams = RSASSAPSSparams.getInstance(localASN1Encodable);
      return getDigestAlgName(localRSASSAPSSparams.getHashAlgorithm().getAlgorithm()) + "WITHRSAANDMGF1";
    }
    if (oids.containsKey(paramAlgorithmIdentifier.getAlgorithm())) {
      return (String)oids.get(paramAlgorithmIdentifier.getAlgorithm());
    }
    return paramAlgorithmIdentifier.getAlgorithm().getId();
  }
  
  public X509Certificate convertCertificate(X509CertificateHolder paramX509CertificateHolder)
    throws CertificateException
  {
    try
    {
      X509Certificate localX509Certificate = (X509Certificate)this.helper.createCertificateFactory("X.509").generateCertificate(new ByteArrayInputStream(paramX509CertificateHolder.getEncoded()));
      return localX509Certificate;
    }
    catch (IOException localIOException)
    {
      throw new OpCertificateException("cannot get encoded form of certificate: " + localIOException.getMessage(), localIOException);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new OpCertificateException("cannot create certificate factory: " + localNoSuchAlgorithmException.getMessage(), localNoSuchAlgorithmException);
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new OpCertificateException("cannot find factory provider: " + localNoSuchProviderException.getMessage(), localNoSuchProviderException);
    }
  }
  
  Cipher createAsymmetricWrapper(ASN1ObjectIdentifier paramASN1ObjectIdentifier, Map paramMap)
    throws OperatorCreationException
  {
    try
    {
      boolean bool1 = paramMap.isEmpty();
      String str = null;
      if (!bool1) {
        str = (String)paramMap.get(paramASN1ObjectIdentifier);
      }
      if (str == null) {
        str = (String)asymmetricWrapperAlgNames.get(paramASN1ObjectIdentifier);
      }
      if (str != null) {
        try
        {
          Cipher localCipher3 = this.helper.createCipher(str);
          return localCipher3;
        }
        catch (NoSuchAlgorithmException localNoSuchAlgorithmException1)
        {
          boolean bool2 = str.equals("RSA/ECB/PKCS1Padding");
          if (bool2) {
            try
            {
              Cipher localCipher2 = this.helper.createCipher("RSA/NONE/PKCS1Padding");
              return localCipher2;
            }
            catch (NoSuchAlgorithmException localNoSuchAlgorithmException2) {}
          }
        }
      }
      Cipher localCipher1 = this.helper.createCipher(paramASN1ObjectIdentifier.getId());
      return localCipher1;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new OperatorCreationException("cannot create cipher: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  MessageDigest createDigest(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws GeneralSecurityException
  {
    try
    {
      MessageDigest localMessageDigest = this.helper.createDigest(getDigestAlgName(paramAlgorithmIdentifier.getAlgorithm()));
      return localMessageDigest;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      if (oids.get(paramAlgorithmIdentifier.getAlgorithm()) != null)
      {
        String str = (String)oids.get(paramAlgorithmIdentifier.getAlgorithm());
        return this.helper.createDigest(str);
      }
      throw localNoSuchAlgorithmException;
    }
  }
  
  public Signature createRawSignature(AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    try
    {
      String str1 = getSignatureName(paramAlgorithmIdentifier);
      String str2 = "NONE" + str1.substring(str1.indexOf("WITH"));
      Signature localSignature = this.helper.createSignature(str2);
      if (paramAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.id_RSASSA_PSS))
      {
        AlgorithmParameters localAlgorithmParameters = this.helper.createAlgorithmParameters(str2);
        localAlgorithmParameters.init(paramAlgorithmIdentifier.getParameters().toASN1Primitive().getEncoded(), "ASN.1");
        localSignature.setParameter((PSSParameterSpec)localAlgorithmParameters.getParameterSpec(PSSParameterSpec.class));
      }
      return localSignature;
    }
    catch (Exception localException) {}
    return null;
  }
  
  Signature createSignature(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws GeneralSecurityException
  {
    try
    {
      Signature localSignature = this.helper.createSignature(getSignatureName(paramAlgorithmIdentifier));
      return localSignature;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      if (oids.get(paramAlgorithmIdentifier.getAlgorithm()) != null)
      {
        String str = (String)oids.get(paramAlgorithmIdentifier.getAlgorithm());
        return this.helper.createSignature(str);
      }
      throw localNoSuchAlgorithmException;
    }
  }
  
  Cipher createSymmetricWrapper(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws OperatorCreationException
  {
    try
    {
      String str = (String)symmetricWrapperAlgNames.get(paramASN1ObjectIdentifier);
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
      throw new OperatorCreationException("cannot create cipher: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  String getKeyAlgorithmName(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    String str = (String)symmetricKeyAlgNames.get(paramASN1ObjectIdentifier);
    if (str != null) {
      return str;
    }
    return paramASN1ObjectIdentifier.getId();
  }
  
  private static class OpCertificateException
    extends CertificateException
  {
    private Throwable cause;
    
    public OpCertificateException(String paramString, Throwable paramThrowable)
    {
      super();
      this.cause = paramThrowable;
    }
    
    public Throwable getCause()
    {
      return this.cause;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.jcajce.OperatorHelper
 * JD-Core Version:    0.7.0.1
 */