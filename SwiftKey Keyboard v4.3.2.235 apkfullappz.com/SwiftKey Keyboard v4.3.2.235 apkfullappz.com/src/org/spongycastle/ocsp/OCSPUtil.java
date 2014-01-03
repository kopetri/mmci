package org.spongycastle.ocsp;

import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.util.Strings;

class OCSPUtil
{
  private static Hashtable algorithms = new Hashtable();
  private static Set noParams;
  private static Hashtable oids = new Hashtable();
  
  static
  {
    noParams = new HashSet();
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
    algorithms.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
    algorithms.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
    algorithms.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
    algorithms.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
    algorithms.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
    algorithms.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
    algorithms.put("GOST3411WITHGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    algorithms.put("GOST3411WITHGOST3410-94", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    oids.put(PKCSObjectIdentifiers.md2WithRSAEncryption, "MD2WITHRSA");
    oids.put(PKCSObjectIdentifiers.md5WithRSAEncryption, "MD5WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha1WithRSAEncryption, "SHA1WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
    oids.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160, "RIPEMD160WITHRSA");
    oids.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128, "RIPEMD128WITHRSA");
    oids.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256, "RIPEMD256WITHRSA");
    oids.put(X9ObjectIdentifiers.id_dsa_with_sha1, "SHA1WITHDSA");
    oids.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
    oids.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
    oids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3411WITHGOST3410");
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA1);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA224);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA256);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA384);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA512);
    noParams.add(X9ObjectIdentifiers.id_dsa_with_sha1);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha224);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha256);
  }
  
  static CertStore createCertStoreInstance(String paramString1, CertStoreParameters paramCertStoreParameters, String paramString2)
    throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException
  {
    if (paramString2 == null) {
      return CertStore.getInstance(paramString1, paramCertStoreParameters);
    }
    return CertStore.getInstance(paramString1, paramCertStoreParameters, paramString2);
  }
  
  static MessageDigest createDigestInstance(String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    if (paramString2 == null) {
      return MessageDigest.getInstance(paramString1);
    }
    return MessageDigest.getInstance(paramString1, paramString2);
  }
  
  static Signature createSignatureInstance(String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    if (paramString2 == null) {
      return Signature.getInstance(paramString1);
    }
    return Signature.getInstance(paramString1, paramString2);
  }
  
  static CertificateFactory createX509CertificateFactory(String paramString)
    throws CertificateException, NoSuchProviderException
  {
    if (paramString == null) {
      return CertificateFactory.getInstance("X.509");
    }
    return CertificateFactory.getInstance("X.509", paramString);
  }
  
  static Iterator getAlgNames()
  {
    Enumeration localEnumeration = algorithms.keys();
    ArrayList localArrayList = new ArrayList();
    while (localEnumeration.hasMoreElements()) {
      localArrayList.add(localEnumeration.nextElement());
    }
    return localArrayList.iterator();
  }
  
  static String getAlgorithmName(DERObjectIdentifier paramDERObjectIdentifier)
  {
    if (oids.containsKey(paramDERObjectIdentifier)) {
      return (String)oids.get(paramDERObjectIdentifier);
    }
    return paramDERObjectIdentifier.getId();
  }
  
  static DERObjectIdentifier getAlgorithmOID(String paramString)
  {
    String str = Strings.toUpperCase(paramString);
    if (algorithms.containsKey(str)) {
      return (DERObjectIdentifier)algorithms.get(str);
    }
    return new DERObjectIdentifier(str);
  }
  
  static AlgorithmIdentifier getSigAlgID(DERObjectIdentifier paramDERObjectIdentifier)
  {
    if (noParams.contains(paramDERObjectIdentifier)) {
      return new AlgorithmIdentifier(paramDERObjectIdentifier);
    }
    return new AlgorithmIdentifier(paramDERObjectIdentifier, new DERNull());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.ocsp.OCSPUtil
 * JD-Core Version:    0.7.0.1
 */