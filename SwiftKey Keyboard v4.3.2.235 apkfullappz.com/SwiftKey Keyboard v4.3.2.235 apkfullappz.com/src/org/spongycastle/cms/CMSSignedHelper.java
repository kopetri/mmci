package org.spongycastle.cms;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.eac.EACObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.x509.NoSuchStoreException;
import org.spongycastle.x509.X509CollectionStoreParameters;
import org.spongycastle.x509.X509Store;
import org.spongycastle.x509.X509V2AttributeCertificate;

class CMSSignedHelper
{
  static final CMSSignedHelper INSTANCE = new CMSSignedHelper();
  private static final Map digestAlgs;
  private static final Map digestAliases;
  private static final Map encryptionAlgs = new HashMap();
  
  static
  {
    digestAlgs = new HashMap();
    digestAliases = new HashMap();
    addEntries(NISTObjectIdentifiers.dsa_with_sha224, "SHA224", "DSA");
    addEntries(NISTObjectIdentifiers.dsa_with_sha256, "SHA256", "DSA");
    addEntries(NISTObjectIdentifiers.dsa_with_sha384, "SHA384", "DSA");
    addEntries(NISTObjectIdentifiers.dsa_with_sha512, "SHA512", "DSA");
    addEntries(OIWObjectIdentifiers.dsaWithSHA1, "SHA1", "DSA");
    addEntries(OIWObjectIdentifiers.md4WithRSA, "MD4", "RSA");
    addEntries(OIWObjectIdentifiers.md4WithRSAEncryption, "MD4", "RSA");
    addEntries(OIWObjectIdentifiers.md5WithRSA, "MD5", "RSA");
    addEntries(OIWObjectIdentifiers.sha1WithRSA, "SHA1", "RSA");
    addEntries(PKCSObjectIdentifiers.md2WithRSAEncryption, "MD2", "RSA");
    addEntries(PKCSObjectIdentifiers.md4WithRSAEncryption, "MD4", "RSA");
    addEntries(PKCSObjectIdentifiers.md5WithRSAEncryption, "MD5", "RSA");
    addEntries(PKCSObjectIdentifiers.sha1WithRSAEncryption, "SHA1", "RSA");
    addEntries(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224", "RSA");
    addEntries(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256", "RSA");
    addEntries(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384", "RSA");
    addEntries(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512", "RSA");
    addEntries(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1", "ECDSA");
    addEntries(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224", "ECDSA");
    addEntries(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256", "ECDSA");
    addEntries(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384", "ECDSA");
    addEntries(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512", "ECDSA");
    addEntries(X9ObjectIdentifiers.id_dsa_with_sha1, "SHA1", "DSA");
    addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_1, "SHA1", "ECDSA");
    addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_224, "SHA224", "ECDSA");
    addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_256, "SHA256", "ECDSA");
    addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_384, "SHA384", "ECDSA");
    addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_512, "SHA512", "ECDSA");
    addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_1, "SHA1", "RSA");
    addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_256, "SHA256", "RSA");
    addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_1, "SHA1", "RSAandMGF1");
    addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_256, "SHA256", "RSAandMGF1");
    encryptionAlgs.put(X9ObjectIdentifiers.id_dsa.getId(), "DSA");
    encryptionAlgs.put(PKCSObjectIdentifiers.rsaEncryption.getId(), "RSA");
    encryptionAlgs.put(TeleTrusTObjectIdentifiers.teleTrusTRSAsignatureAlgorithm, "RSA");
    encryptionAlgs.put(X509ObjectIdentifiers.id_ea_rsa.getId(), "RSA");
    encryptionAlgs.put(CMSSignedDataGenerator.ENCRYPTION_RSA_PSS, "RSAandMGF1");
    encryptionAlgs.put(CryptoProObjectIdentifiers.gostR3410_94.getId(), "GOST3410");
    encryptionAlgs.put(CryptoProObjectIdentifiers.gostR3410_2001.getId(), "ECGOST3410");
    encryptionAlgs.put("1.3.6.1.4.1.5849.1.6.2", "ECGOST3410");
    encryptionAlgs.put("1.3.6.1.4.1.5849.1.1.5", "GOST3410");
    encryptionAlgs.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001.getId(), "ECGOST3410");
    encryptionAlgs.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94.getId(), "GOST3410");
    digestAlgs.put(PKCSObjectIdentifiers.md2.getId(), "MD2");
    digestAlgs.put(PKCSObjectIdentifiers.md4.getId(), "MD4");
    digestAlgs.put(PKCSObjectIdentifiers.md5.getId(), "MD5");
    digestAlgs.put(OIWObjectIdentifiers.idSHA1.getId(), "SHA1");
    digestAlgs.put(NISTObjectIdentifiers.id_sha224.getId(), "SHA224");
    digestAlgs.put(NISTObjectIdentifiers.id_sha256.getId(), "SHA256");
    digestAlgs.put(NISTObjectIdentifiers.id_sha384.getId(), "SHA384");
    digestAlgs.put(NISTObjectIdentifiers.id_sha512.getId(), "SHA512");
    digestAlgs.put(TeleTrusTObjectIdentifiers.ripemd128.getId(), "RIPEMD128");
    digestAlgs.put(TeleTrusTObjectIdentifiers.ripemd160.getId(), "RIPEMD160");
    digestAlgs.put(TeleTrusTObjectIdentifiers.ripemd256.getId(), "RIPEMD256");
    digestAlgs.put(CryptoProObjectIdentifiers.gostR3411.getId(), "GOST3411");
    digestAlgs.put("1.3.6.1.4.1.5849.1.2.1", "GOST3411");
    digestAliases.put("SHA1", new String[] { "SHA-1" });
    digestAliases.put("SHA224", new String[] { "SHA-224" });
    digestAliases.put("SHA256", new String[] { "SHA-256" });
    digestAliases.put("SHA384", new String[] { "SHA-384" });
    digestAliases.put("SHA512", new String[] { "SHA-512" });
  }
  
  /* Error */
  private void addCRLsFromSet(List paramList, ASN1Set paramASN1Set, Provider paramProvider)
    throws CMSException
  {
    // Byte code:
    //   0: aload_3
    //   1: ifnull +87 -> 88
    //   4: ldc_w 286
    //   7: aload_3
    //   8: invokestatic 292	java/security/cert/CertificateFactory:getInstance	(Ljava/lang/String;Ljava/security/Provider;)Ljava/security/cert/CertificateFactory;
    //   11: astore 11
    //   13: aload 11
    //   15: astore 6
    //   17: aload_2
    //   18: invokevirtual 298	org/spongycastle/asn1/ASN1Set:getObjects	()Ljava/util/Enumeration;
    //   21: astore 7
    //   23: aload 7
    //   25: invokeinterface 304 1 0
    //   30: ifeq +103 -> 133
    //   33: aload_1
    //   34: aload 6
    //   36: new 306	java/io/ByteArrayInputStream
    //   39: dup
    //   40: aload 7
    //   42: invokeinterface 310 1 0
    //   47: checkcast 312	org/spongycastle/asn1/ASN1Encodable
    //   50: invokeinterface 316 1 0
    //   55: invokevirtual 322	org/spongycastle/asn1/ASN1Primitive:getEncoded	()[B
    //   58: invokespecial 325	java/io/ByteArrayInputStream:<init>	([B)V
    //   61: invokevirtual 329	java/security/cert/CertificateFactory:generateCRL	(Ljava/io/InputStream;)Ljava/security/cert/CRL;
    //   64: invokeinterface 335 2 0
    //   69: pop
    //   70: goto -47 -> 23
    //   73: astore 9
    //   75: new 278	org/spongycastle/cms/CMSException
    //   78: dup
    //   79: ldc_w 337
    //   82: aload 9
    //   84: invokespecial 340	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   87: athrow
    //   88: ldc_w 286
    //   91: invokestatic 343	java/security/cert/CertificateFactory:getInstance	(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;
    //   94: astore 5
    //   96: aload 5
    //   98: astore 6
    //   100: goto -83 -> 17
    //   103: astore 4
    //   105: new 278	org/spongycastle/cms/CMSException
    //   108: dup
    //   109: ldc_w 345
    //   112: aload 4
    //   114: invokespecial 340	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   117: athrow
    //   118: astore 8
    //   120: new 278	org/spongycastle/cms/CMSException
    //   123: dup
    //   124: ldc_w 337
    //   127: aload 8
    //   129: invokespecial 340	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   132: athrow
    //   133: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	134	0	this	CMSSignedHelper
    //   0	134	1	paramList	List
    //   0	134	2	paramASN1Set	ASN1Set
    //   0	134	3	paramProvider	Provider
    //   103	10	4	localCertificateException	java.security.cert.CertificateException
    //   94	3	5	localCertificateFactory1	java.security.cert.CertificateFactory
    //   15	84	6	localObject	Object
    //   21	20	7	localEnumeration	Enumeration
    //   118	10	8	localCRLException	java.security.cert.CRLException
    //   73	10	9	localIOException	IOException
    //   11	3	11	localCertificateFactory2	java.security.cert.CertificateFactory
    // Exception table:
    //   from	to	target	type
    //   33	70	73	java/io/IOException
    //   4	13	103	java/security/cert/CertificateException
    //   88	96	103	java/security/cert/CertificateException
    //   33	70	118	java/security/cert/CRLException
  }
  
  /* Error */
  private void addCertsFromSet(List paramList, ASN1Set paramASN1Set, Provider paramProvider)
    throws CMSException
  {
    // Byte code:
    //   0: aload_3
    //   1: ifnull +99 -> 100
    //   4: ldc_w 286
    //   7: aload_3
    //   8: invokestatic 292	java/security/cert/CertificateFactory:getInstance	(Ljava/lang/String;Ljava/security/Provider;)Ljava/security/cert/CertificateFactory;
    //   11: astore 12
    //   13: aload 12
    //   15: astore 6
    //   17: aload_2
    //   18: invokevirtual 298	org/spongycastle/asn1/ASN1Set:getObjects	()Ljava/util/Enumeration;
    //   21: astore 7
    //   23: aload 7
    //   25: invokeinterface 304 1 0
    //   30: ifeq +115 -> 145
    //   33: aload 7
    //   35: invokeinterface 310 1 0
    //   40: checkcast 312	org/spongycastle/asn1/ASN1Encodable
    //   43: invokeinterface 316 1 0
    //   48: astore 10
    //   50: aload 10
    //   52: instanceof 348
    //   55: ifeq -32 -> 23
    //   58: aload_1
    //   59: aload 6
    //   61: new 306	java/io/ByteArrayInputStream
    //   64: dup
    //   65: aload 10
    //   67: invokevirtual 322	org/spongycastle/asn1/ASN1Primitive:getEncoded	()[B
    //   70: invokespecial 325	java/io/ByteArrayInputStream:<init>	([B)V
    //   73: invokevirtual 352	java/security/cert/CertificateFactory:generateCertificate	(Ljava/io/InputStream;)Ljava/security/cert/Certificate;
    //   76: invokeinterface 335 2 0
    //   81: pop
    //   82: goto -59 -> 23
    //   85: astore 9
    //   87: new 278	org/spongycastle/cms/CMSException
    //   90: dup
    //   91: ldc_w 354
    //   94: aload 9
    //   96: invokespecial 340	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   99: athrow
    //   100: ldc_w 286
    //   103: invokestatic 343	java/security/cert/CertificateFactory:getInstance	(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;
    //   106: astore 5
    //   108: aload 5
    //   110: astore 6
    //   112: goto -95 -> 17
    //   115: astore 4
    //   117: new 278	org/spongycastle/cms/CMSException
    //   120: dup
    //   121: ldc_w 345
    //   124: aload 4
    //   126: invokespecial 340	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   129: athrow
    //   130: astore 8
    //   132: new 278	org/spongycastle/cms/CMSException
    //   135: dup
    //   136: ldc_w 354
    //   139: aload 8
    //   141: invokespecial 340	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   144: athrow
    //   145: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	146	0	this	CMSSignedHelper
    //   0	146	1	paramList	List
    //   0	146	2	paramASN1Set	ASN1Set
    //   0	146	3	paramProvider	Provider
    //   115	10	4	localCertificateException1	java.security.cert.CertificateException
    //   106	3	5	localCertificateFactory1	java.security.cert.CertificateFactory
    //   15	96	6	localObject	Object
    //   21	13	7	localEnumeration	Enumeration
    //   130	10	8	localCertificateException2	java.security.cert.CertificateException
    //   85	10	9	localIOException	IOException
    //   48	18	10	localASN1Primitive	ASN1Primitive
    //   11	3	12	localCertificateFactory2	java.security.cert.CertificateFactory
    // Exception table:
    //   from	to	target	type
    //   33	82	85	java/io/IOException
    //   4	13	115	java/security/cert/CertificateException
    //   100	108	115	java/security/cert/CertificateException
    //   33	82	130	java/security/cert/CertificateException
  }
  
  private static void addEntries(DERObjectIdentifier paramDERObjectIdentifier, String paramString1, String paramString2)
  {
    digestAlgs.put(paramDERObjectIdentifier.getId(), paramString1);
    encryptionAlgs.put(paramDERObjectIdentifier.getId(), paramString2);
  }
  
  X509Store createAttributeStore(String paramString, Provider paramProvider, ASN1Set paramASN1Set)
    throws NoSuchStoreException, CMSException
  {
    ArrayList localArrayList = new ArrayList();
    if (paramASN1Set != null)
    {
      Enumeration localEnumeration = paramASN1Set.getObjects();
      while (localEnumeration.hasMoreElements()) {
        try
        {
          ASN1Primitive localASN1Primitive = ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive();
          if ((localASN1Primitive instanceof ASN1TaggedObject))
          {
            ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localASN1Primitive;
            if (localASN1TaggedObject.getTagNo() == 2) {
              localArrayList.add(new X509V2AttributeCertificate(ASN1Sequence.getInstance(localASN1TaggedObject, false).getEncoded()));
            }
          }
        }
        catch (IOException localIOException)
        {
          throw new CMSException("can't re-encode attribute certificate!", localIOException);
        }
      }
    }
    try
    {
      X509Store localX509Store = X509Store.getInstance("AttributeCertificate/" + paramString, new X509CollectionStoreParameters(localArrayList), paramProvider);
      return localX509Store;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("can't setup the X509Store", localIllegalArgumentException);
    }
  }
  
  X509Store createCRLsStore(String paramString, Provider paramProvider, ASN1Set paramASN1Set)
    throws NoSuchStoreException, CMSException
  {
    ArrayList localArrayList = new ArrayList();
    if (paramASN1Set != null) {
      addCRLsFromSet(localArrayList, paramASN1Set, paramProvider);
    }
    try
    {
      X509Store localX509Store = X509Store.getInstance("CRL/" + paramString, new X509CollectionStoreParameters(localArrayList), paramProvider);
      return localX509Store;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("can't setup the X509Store", localIllegalArgumentException);
    }
  }
  
  CertStore createCertStore(String paramString, Provider paramProvider, ASN1Set paramASN1Set1, ASN1Set paramASN1Set2)
    throws CMSException, NoSuchAlgorithmException
  {
    ArrayList localArrayList = new ArrayList();
    if (paramASN1Set1 != null) {
      addCertsFromSet(localArrayList, paramASN1Set1, paramProvider);
    }
    if (paramASN1Set2 != null) {
      addCRLsFromSet(localArrayList, paramASN1Set2, paramProvider);
    }
    if (paramProvider != null) {}
    try
    {
      return CertStore.getInstance(paramString, new CollectionCertStoreParameters(localArrayList), paramProvider);
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      CertStore localCertStore;
      throw new CMSException("can't setup the CertStore", localInvalidAlgorithmParameterException);
    }
    localCertStore = CertStore.getInstance(paramString, new CollectionCertStoreParameters(localArrayList));
    return localCertStore;
  }
  
  X509Store createCertificateStore(String paramString, Provider paramProvider, ASN1Set paramASN1Set)
    throws NoSuchStoreException, CMSException
  {
    ArrayList localArrayList = new ArrayList();
    if (paramASN1Set != null) {
      addCertsFromSet(localArrayList, paramASN1Set, paramProvider);
    }
    try
    {
      X509Store localX509Store = X509Store.getInstance("Certificate/" + paramString, new X509CollectionStoreParameters(localArrayList), paramProvider);
      return localX509Store;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("can't setup the X509Store", localIllegalArgumentException);
    }
  }
  
  AlgorithmIdentifier fixAlgID(AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    if (paramAlgorithmIdentifier.getParameters() == null) {
      paramAlgorithmIdentifier = new AlgorithmIdentifier(paramAlgorithmIdentifier.getObjectId(), DERNull.INSTANCE);
    }
    return paramAlgorithmIdentifier;
  }
  
  String getDigestAlgName(String paramString)
  {
    String str = (String)digestAlgs.get(paramString);
    if (str != null) {
      return str;
    }
    return paramString;
  }
  
  String getEncryptionAlgName(String paramString)
  {
    String str = (String)encryptionAlgs.get(paramString);
    if (str != null) {
      return str;
    }
    return paramString;
  }
  
  void setSigningDigestAlgorithmMapping(DERObjectIdentifier paramDERObjectIdentifier, String paramString)
  {
    digestAlgs.put(paramDERObjectIdentifier.getId(), paramString);
  }
  
  void setSigningEncryptionAlgorithmMapping(DERObjectIdentifier paramDERObjectIdentifier, String paramString)
  {
    encryptionAlgs.put(paramDERObjectIdentifier.getId(), paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSSignedHelper
 * JD-Core Version:    0.7.0.1
 */