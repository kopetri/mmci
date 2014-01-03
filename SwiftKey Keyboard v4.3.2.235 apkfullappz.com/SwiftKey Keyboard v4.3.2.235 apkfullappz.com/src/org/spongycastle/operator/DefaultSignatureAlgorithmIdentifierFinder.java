package org.spongycastle.operator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.util.Strings;

public class DefaultSignatureAlgorithmIdentifierFinder
  implements SignatureAlgorithmIdentifierFinder
{
  private static final ASN1ObjectIdentifier ENCRYPTION_DSA;
  private static final ASN1ObjectIdentifier ENCRYPTION_ECDSA;
  private static final ASN1ObjectIdentifier ENCRYPTION_ECGOST3410;
  private static final ASN1ObjectIdentifier ENCRYPTION_GOST3410;
  private static final ASN1ObjectIdentifier ENCRYPTION_RSA;
  private static final ASN1ObjectIdentifier ENCRYPTION_RSA_PSS;
  private static Map algorithms = new HashMap();
  private static Map digestOids;
  private static Set noParams = new HashSet();
  private static Map params = new HashMap();
  private static Set pkcs15RsaEncryption = new HashSet();
  
  static
  {
    digestOids = new HashMap();
    ENCRYPTION_RSA = PKCSObjectIdentifiers.rsaEncryption;
    ENCRYPTION_DSA = X9ObjectIdentifiers.id_dsa_with_sha1;
    ENCRYPTION_ECDSA = X9ObjectIdentifiers.ecdsa_with_SHA1;
    ENCRYPTION_RSA_PSS = PKCSObjectIdentifiers.id_RSASSA_PSS;
    ENCRYPTION_GOST3410 = CryptoProObjectIdentifiers.gostR3410_94;
    ENCRYPTION_ECGOST3410 = CryptoProObjectIdentifiers.gostR3410_2001;
    algorithms.put("MD2WITHRSAENCRYPTION", PKCSObjectIdentifiers.md2WithRSAEncryption);
    algorithms.put("MD2WITHRSA", PKCSObjectIdentifiers.md2WithRSAEncryption);
    algorithms.put("MD5WITHRSAENCRYPTION", PKCSObjectIdentifiers.md5WithRSAEncryption);
    algorithms.put("MD5WITHRSA", PKCSObjectIdentifiers.md5WithRSAEncryption);
    algorithms.put("SHA1WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha1WithRSAEncryption);
    algorithms.put("SHA1WITHRSA", PKCSObjectIdentifiers.sha1WithRSAEncryption);
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
    algorithms.put("RIPEMD160WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
    algorithms.put("RIPEMD160WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
    algorithms.put("RIPEMD128WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
    algorithms.put("RIPEMD128WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
    algorithms.put("RIPEMD256WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
    algorithms.put("RIPEMD256WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
    algorithms.put("SHA1WITHDSA", X9ObjectIdentifiers.id_dsa_with_sha1);
    algorithms.put("DSAWITHSHA1", X9ObjectIdentifiers.id_dsa_with_sha1);
    algorithms.put("SHA224WITHDSA", NISTObjectIdentifiers.dsa_with_sha224);
    algorithms.put("SHA256WITHDSA", NISTObjectIdentifiers.dsa_with_sha256);
    algorithms.put("SHA384WITHDSA", NISTObjectIdentifiers.dsa_with_sha384);
    algorithms.put("SHA512WITHDSA", NISTObjectIdentifiers.dsa_with_sha512);
    algorithms.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
    algorithms.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
    algorithms.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
    algorithms.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
    algorithms.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
    algorithms.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
    algorithms.put("GOST3411WITHGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    algorithms.put("GOST3411WITHGOST3410-94", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    algorithms.put("GOST3411WITHECGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    algorithms.put("GOST3411WITHECGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    algorithms.put("GOST3411WITHGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA1);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA224);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA256);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA384);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA512);
    noParams.add(X9ObjectIdentifiers.id_dsa_with_sha1);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha224);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha256);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha384);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha512);
    noParams.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    noParams.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha1WithRSAEncryption);
    pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha224WithRSAEncryption);
    pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha256WithRSAEncryption);
    pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha384WithRSAEncryption);
    pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha512WithRSAEncryption);
    pkcs15RsaEncryption.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
    pkcs15RsaEncryption.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
    pkcs15RsaEncryption.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
    AlgorithmIdentifier localAlgorithmIdentifier1 = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, new DERNull());
    params.put("SHA1WITHRSAANDMGF1", createPSSParams(localAlgorithmIdentifier1, 20));
    AlgorithmIdentifier localAlgorithmIdentifier2 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, new DERNull());
    params.put("SHA224WITHRSAANDMGF1", createPSSParams(localAlgorithmIdentifier2, 28));
    AlgorithmIdentifier localAlgorithmIdentifier3 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, new DERNull());
    params.put("SHA256WITHRSAANDMGF1", createPSSParams(localAlgorithmIdentifier3, 32));
    AlgorithmIdentifier localAlgorithmIdentifier4 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, new DERNull());
    params.put("SHA384WITHRSAANDMGF1", createPSSParams(localAlgorithmIdentifier4, 48));
    AlgorithmIdentifier localAlgorithmIdentifier5 = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, new DERNull());
    params.put("SHA512WITHRSAANDMGF1", createPSSParams(localAlgorithmIdentifier5, 64));
    digestOids.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, NISTObjectIdentifiers.id_sha224);
    digestOids.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, NISTObjectIdentifiers.id_sha256);
    digestOids.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, NISTObjectIdentifiers.id_sha384);
    digestOids.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, NISTObjectIdentifiers.id_sha512);
    digestOids.put(PKCSObjectIdentifiers.md2WithRSAEncryption, PKCSObjectIdentifiers.md2);
    digestOids.put(PKCSObjectIdentifiers.md4WithRSAEncryption, PKCSObjectIdentifiers.md4);
    digestOids.put(PKCSObjectIdentifiers.md5WithRSAEncryption, PKCSObjectIdentifiers.md5);
    digestOids.put(PKCSObjectIdentifiers.sha1WithRSAEncryption, OIWObjectIdentifiers.idSHA1);
    digestOids.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128, TeleTrusTObjectIdentifiers.ripemd128);
    digestOids.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160, TeleTrusTObjectIdentifiers.ripemd160);
    digestOids.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256, TeleTrusTObjectIdentifiers.ripemd256);
    digestOids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, CryptoProObjectIdentifiers.gostR3411);
    digestOids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, CryptoProObjectIdentifiers.gostR3411);
  }
  
  private static RSASSAPSSparams createPSSParams(AlgorithmIdentifier paramAlgorithmIdentifier, int paramInt)
  {
    return new RSASSAPSSparams(paramAlgorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, paramAlgorithmIdentifier), new ASN1Integer(paramInt), new ASN1Integer(1));
  }
  
  private static AlgorithmIdentifier generate(String paramString)
  {
    String str = Strings.toUpperCase(paramString);
    ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)algorithms.get(str);
    if (localASN1ObjectIdentifier == null) {
      throw new IllegalArgumentException("Unknown signature type requested: " + str);
    }
    AlgorithmIdentifier localAlgorithmIdentifier;
    if (noParams.contains(localASN1ObjectIdentifier)) {
      localAlgorithmIdentifier = new AlgorithmIdentifier(localASN1ObjectIdentifier);
    }
    for (;;)
    {
      if (pkcs15RsaEncryption.contains(localASN1ObjectIdentifier)) {
        new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, new DERNull());
      }
      if (!localAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.id_RSASSA_PSS)) {
        break;
      }
      ((RSASSAPSSparams)localAlgorithmIdentifier.getParameters()).getHashAlgorithm();
      return localAlgorithmIdentifier;
      if (params.containsKey(str)) {
        localAlgorithmIdentifier = new AlgorithmIdentifier(localASN1ObjectIdentifier, (ASN1Encodable)params.get(str));
      } else {
        localAlgorithmIdentifier = new AlgorithmIdentifier(localASN1ObjectIdentifier, DERNull.INSTANCE);
      }
    }
    new AlgorithmIdentifier((ASN1ObjectIdentifier)digestOids.get(localASN1ObjectIdentifier), new DERNull());
    return localAlgorithmIdentifier;
  }
  
  public AlgorithmIdentifier find(String paramString)
  {
    return generate(paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.DefaultSignatureAlgorithmIdentifierFinder
 * JD-Core Version:    0.7.0.1
 */