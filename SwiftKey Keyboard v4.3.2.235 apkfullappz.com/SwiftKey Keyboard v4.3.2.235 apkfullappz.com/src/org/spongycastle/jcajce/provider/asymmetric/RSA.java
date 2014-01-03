package org.spongycastle.jcajce.provider.asymmetric;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class RSA
{
  private static final String PREFIX = "org.spongycastle.jcajce.provider.asymmetric.rsa.";
  
  public static class Mappings
    extends AsymmetricAlgorithmProvider
  {
    private void addDigestSignature(ConfigurableProvider paramConfigurableProvider, String paramString1, String paramString2, ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    {
      String str1 = paramString1 + "WITHRSA";
      String str2 = paramString1 + "withRSA";
      String str3 = paramString1 + "WithRSA";
      String str4 = paramString1 + "/RSA";
      String str5 = paramString1 + "WITHRSAENCRYPTION";
      String str6 = paramString1 + "withRSAEncryption";
      String str7 = paramString1 + "WithRSAEncryption";
      paramConfigurableProvider.addAlgorithm("Signature." + str1, paramString2);
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + str2, str1);
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + str3, str1);
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + str5, str1);
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + str6, str1);
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + str7, str1);
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + str4, str1);
      if (paramASN1ObjectIdentifier != null)
      {
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + paramASN1ObjectIdentifier, str1);
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.OID." + paramASN1ObjectIdentifier, str1);
      }
    }
    
    public void configure(ConfigurableProvider paramConfigurableProvider)
    {
      paramConfigurableProvider.addAlgorithm("AlgorithmParameters.OAEP", "org.spongycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$OAEP");
      paramConfigurableProvider.addAlgorithm("AlgorithmParameters.PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.RSAPSS", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.RSASSA-PSS", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA224withRSA/PSS", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA256withRSA/PSS", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA384withRSA/PSS", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA512withRSA/PSS", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA224WITHRSAANDMGF1", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA256WITHRSAANDMGF1", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA384WITHRSAANDMGF1", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA512WITHRSAANDMGF1", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.RAWRSAPSS", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.NONEWITHRSAPSS", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.NONEWITHRSASSA-PSS", "PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.NONEWITHRSAANDMGF1", "PSS");
      paramConfigurableProvider.addAlgorithm("Cipher.RSA", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$NoPadding");
      paramConfigurableProvider.addAlgorithm("Cipher.RSA/RAW", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$NoPadding");
      paramConfigurableProvider.addAlgorithm("Cipher.RSA/PKCS1", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding");
      paramConfigurableProvider.addAlgorithm("Cipher.1.2.840.113549.1.1.1", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding");
      paramConfigurableProvider.addAlgorithm("Cipher.2.5.8.1.1", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding");
      paramConfigurableProvider.addAlgorithm("Cipher.RSA/1", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding_PrivateOnly");
      paramConfigurableProvider.addAlgorithm("Cipher.RSA/2", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding_PublicOnly");
      paramConfigurableProvider.addAlgorithm("Cipher.RSA/OAEP", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$OAEPPadding");
      paramConfigurableProvider.addAlgorithm("Cipher." + PKCSObjectIdentifiers.id_RSAES_OAEP, "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$OAEPPadding");
      paramConfigurableProvider.addAlgorithm("Cipher.RSA/ISO9796-1", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$ISO9796d1Padding");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//RAW", "RSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//NOPADDING", "RSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//PKCS1PADDING", "RSA/PKCS1");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//OAEPPADDING", "RSA/OAEP");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//ISO9796-1PADDING", "RSA/ISO9796-1");
      paramConfigurableProvider.addAlgorithm("KeyFactory.RSA", "org.spongycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi");
      paramConfigurableProvider.addAlgorithm("KeyPairGenerator.RSA", "org.spongycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi");
      KeyFactorySpi localKeyFactorySpi = new KeyFactorySpi();
      registerOid(paramConfigurableProvider, PKCSObjectIdentifiers.rsaEncryption, "RSA", localKeyFactorySpi);
      registerOid(paramConfigurableProvider, X509ObjectIdentifiers.id_ea_rsa, "RSA", localKeyFactorySpi);
      registerOid(paramConfigurableProvider, PKCSObjectIdentifiers.id_RSAES_OAEP, "RSA", localKeyFactorySpi);
      registerOid(paramConfigurableProvider, PKCSObjectIdentifiers.id_RSASSA_PSS, "RSA", localKeyFactorySpi);
      registerOidAlgorithmParameters(paramConfigurableProvider, PKCSObjectIdentifiers.rsaEncryption, "RSA");
      registerOidAlgorithmParameters(paramConfigurableProvider, X509ObjectIdentifiers.id_ea_rsa, "RSA");
      registerOidAlgorithmParameters(paramConfigurableProvider, PKCSObjectIdentifiers.id_RSAES_OAEP, "OAEP");
      registerOidAlgorithmParameters(paramConfigurableProvider, PKCSObjectIdentifiers.id_RSASSA_PSS, "PSS");
      paramConfigurableProvider.addAlgorithm("Signature.RSASSA-PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$PSSwithRSA");
      paramConfigurableProvider.addAlgorithm("Signature." + PKCSObjectIdentifiers.id_RSASSA_PSS, "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$PSSwithRSA");
      paramConfigurableProvider.addAlgorithm("Signature.OID." + PKCSObjectIdentifiers.id_RSASSA_PSS, "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$PSSwithRSA");
      paramConfigurableProvider.addAlgorithm("Signature.SHA224withRSA/PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA224withRSA");
      paramConfigurableProvider.addAlgorithm("Signature.SHA256withRSA/PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA256withRSA");
      paramConfigurableProvider.addAlgorithm("Signature.SHA384withRSA/PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA384withRSA");
      paramConfigurableProvider.addAlgorithm("Signature.SHA512withRSA/PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA512withRSA");
      paramConfigurableProvider.addAlgorithm("Signature.RSA", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$noneRSA");
      paramConfigurableProvider.addAlgorithm("Signature.RAWRSASSA-PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$nonePSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.RAWRSA", "RSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSA", "RSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.RAWRSAPSS", "RAWRSASSA-PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSAPSS", "RAWRSASSA-PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSASSA-PSS", "RAWRSASSA-PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSAANDMGF1", "RAWRSASSA-PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.RSAPSS", "RSASSA-PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA224withRSAandMGF1", "SHA224withRSA/PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA256withRSAandMGF1", "SHA256withRSA/PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA384withRSAandMGF1", "SHA384withRSA/PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA512withRSAandMGF1", "SHA512withRSA/PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA224WITHRSAANDMGF1", "SHA224withRSA/PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA256WITHRSAANDMGF1", "SHA256withRSA/PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA384WITHRSAANDMGF1", "SHA384withRSA/PSS");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA512WITHRSAANDMGF1", "SHA512withRSA/PSS");
      if (paramConfigurableProvider.hasAlgorithm("MessageDigest", "MD2")) {
        addDigestSignature(paramConfigurableProvider, "MD2", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD2", PKCSObjectIdentifiers.md2WithRSAEncryption);
      }
      if (paramConfigurableProvider.hasAlgorithm("MessageDigest", "MD2")) {
        addDigestSignature(paramConfigurableProvider, "MD4", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD4", PKCSObjectIdentifiers.md4WithRSAEncryption);
      }
      if (paramConfigurableProvider.hasAlgorithm("MessageDigest", "MD2"))
      {
        addDigestSignature(paramConfigurableProvider, "MD5", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD5", PKCSObjectIdentifiers.md5WithRSAEncryption);
        paramConfigurableProvider.addAlgorithm("Signature.MD5withRSA/ISO9796-2", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$MD5WithRSAEncryption");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.MD5WithRSA/ISO9796-2", "MD5withRSA/ISO9796-2");
      }
      if (paramConfigurableProvider.hasAlgorithm("MessageDigest", "SHA1"))
      {
        paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA1withRSA/PSS", "PSS");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA1WITHRSAANDMGF1", "PSS");
        paramConfigurableProvider.addAlgorithm("Signature.SHA1withRSA/PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA1withRSA");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1withRSAandMGF1", "SHA1withRSA/PSS");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WITHRSAANDMGF1", "SHA1withRSA/PSS");
        addDigestSignature(paramConfigurableProvider, "SHA1", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA1", PKCSObjectIdentifiers.sha1WithRSAEncryption);
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WithRSA/ISO9796-2", "SHA1withRSA/ISO9796-2");
        paramConfigurableProvider.addAlgorithm("Signature.SHA1withRSA/ISO9796-2", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$SHA1WithRSAEncryption");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.OID." + OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
      }
      addDigestSignature(paramConfigurableProvider, "SHA224", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA224", PKCSObjectIdentifiers.sha224WithRSAEncryption);
      addDigestSignature(paramConfigurableProvider, "SHA256", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA256", PKCSObjectIdentifiers.sha256WithRSAEncryption);
      addDigestSignature(paramConfigurableProvider, "SHA384", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA384", PKCSObjectIdentifiers.sha384WithRSAEncryption);
      addDigestSignature(paramConfigurableProvider, "SHA512", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA512", PKCSObjectIdentifiers.sha512WithRSAEncryption);
      if (paramConfigurableProvider.hasAlgorithm("MessageDigest", "RIPEMD128"))
      {
        addDigestSignature(paramConfigurableProvider, "RIPEMD128", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD128", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
        addDigestSignature(paramConfigurableProvider, "RMD128", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD128", null);
      }
      if (paramConfigurableProvider.hasAlgorithm("MessageDigest", "RIPEMD160"))
      {
        addDigestSignature(paramConfigurableProvider, "RIPEMD160", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD160", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
        addDigestSignature(paramConfigurableProvider, "RMD160", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD160", null);
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.RIPEMD160WithRSA/ISO9796-2", "RIPEMD160withRSA/ISO9796-2");
        paramConfigurableProvider.addAlgorithm("Signature.RIPEMD160withRSA/ISO9796-2", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$RIPEMD160WithRSAEncryption");
      }
      if (paramConfigurableProvider.hasAlgorithm("MessageDigest", "RIPEMD256"))
      {
        addDigestSignature(paramConfigurableProvider, "RIPEMD256", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD256", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
        addDigestSignature(paramConfigurableProvider, "RMD256", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD256", null);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.RSA
 * JD-Core Version:    0.7.0.1
 */