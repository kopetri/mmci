package org.spongycastle.cms;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AttributeCertificate;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.jce.interfaces.GOST3410PrivateKey;
import org.spongycastle.util.Store;
import org.spongycastle.x509.X509AttributeCertificate;
import org.spongycastle.x509.X509Store;

public class CMSSignedGenerator
{
  public static final String DATA = CMSObjectIdentifiers.data.getId();
  public static final String DIGEST_GOST3411;
  public static final String DIGEST_MD5;
  public static final String DIGEST_RIPEMD128;
  public static final String DIGEST_RIPEMD160;
  public static final String DIGEST_RIPEMD256;
  public static final String DIGEST_SHA1 = OIWObjectIdentifiers.idSHA1.getId();
  public static final String DIGEST_SHA224 = NISTObjectIdentifiers.id_sha224.getId();
  public static final String DIGEST_SHA256 = NISTObjectIdentifiers.id_sha256.getId();
  public static final String DIGEST_SHA384 = NISTObjectIdentifiers.id_sha384.getId();
  public static final String DIGEST_SHA512 = NISTObjectIdentifiers.id_sha512.getId();
  private static final Map EC_ALGORITHMS;
  public static final String ENCRYPTION_DSA;
  public static final String ENCRYPTION_ECDSA;
  private static final String ENCRYPTION_ECDSA_WITH_SHA1;
  private static final String ENCRYPTION_ECDSA_WITH_SHA224;
  private static final String ENCRYPTION_ECDSA_WITH_SHA256;
  private static final String ENCRYPTION_ECDSA_WITH_SHA384;
  private static final String ENCRYPTION_ECDSA_WITH_SHA512;
  public static final String ENCRYPTION_ECGOST3410;
  public static final String ENCRYPTION_GOST3410;
  public static final String ENCRYPTION_RSA;
  public static final String ENCRYPTION_RSA_PSS;
  private static final Set NO_PARAMS;
  protected List _signers = new ArrayList();
  protected List certs = new ArrayList();
  protected List crls = new ArrayList();
  protected Map digests = new HashMap();
  protected final SecureRandom rand;
  protected List signerGens = new ArrayList();
  
  static
  {
    DIGEST_MD5 = PKCSObjectIdentifiers.md5.getId();
    DIGEST_GOST3411 = CryptoProObjectIdentifiers.gostR3411.getId();
    DIGEST_RIPEMD128 = TeleTrusTObjectIdentifiers.ripemd128.getId();
    DIGEST_RIPEMD160 = TeleTrusTObjectIdentifiers.ripemd160.getId();
    DIGEST_RIPEMD256 = TeleTrusTObjectIdentifiers.ripemd256.getId();
    ENCRYPTION_RSA = PKCSObjectIdentifiers.rsaEncryption.getId();
    ENCRYPTION_DSA = X9ObjectIdentifiers.id_dsa_with_sha1.getId();
    ENCRYPTION_ECDSA = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
    ENCRYPTION_RSA_PSS = PKCSObjectIdentifiers.id_RSASSA_PSS.getId();
    ENCRYPTION_GOST3410 = CryptoProObjectIdentifiers.gostR3410_94.getId();
    ENCRYPTION_ECGOST3410 = CryptoProObjectIdentifiers.gostR3410_2001.getId();
    ENCRYPTION_ECDSA_WITH_SHA1 = X9ObjectIdentifiers.ecdsa_with_SHA1.getId();
    ENCRYPTION_ECDSA_WITH_SHA224 = X9ObjectIdentifiers.ecdsa_with_SHA224.getId();
    ENCRYPTION_ECDSA_WITH_SHA256 = X9ObjectIdentifiers.ecdsa_with_SHA256.getId();
    ENCRYPTION_ECDSA_WITH_SHA384 = X9ObjectIdentifiers.ecdsa_with_SHA384.getId();
    ENCRYPTION_ECDSA_WITH_SHA512 = X9ObjectIdentifiers.ecdsa_with_SHA512.getId();
    NO_PARAMS = new HashSet();
    EC_ALGORITHMS = new HashMap();
    NO_PARAMS.add(ENCRYPTION_DSA);
    NO_PARAMS.add(ENCRYPTION_ECDSA);
    NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA1);
    NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA224);
    NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA256);
    NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA384);
    NO_PARAMS.add(ENCRYPTION_ECDSA_WITH_SHA512);
    EC_ALGORITHMS.put(DIGEST_SHA1, ENCRYPTION_ECDSA_WITH_SHA1);
    EC_ALGORITHMS.put(DIGEST_SHA224, ENCRYPTION_ECDSA_WITH_SHA224);
    EC_ALGORITHMS.put(DIGEST_SHA256, ENCRYPTION_ECDSA_WITH_SHA256);
    EC_ALGORITHMS.put(DIGEST_SHA384, ENCRYPTION_ECDSA_WITH_SHA384);
    EC_ALGORITHMS.put(DIGEST_SHA512, ENCRYPTION_ECDSA_WITH_SHA512);
  }
  
  protected CMSSignedGenerator()
  {
    this(new SecureRandom());
  }
  
  protected CMSSignedGenerator(SecureRandom paramSecureRandom)
  {
    this.rand = paramSecureRandom;
  }
  
  public void addAttributeCertificates(Store paramStore)
    throws CMSException
  {
    this.certs.addAll(CMSUtils.getAttributeCertificatesFromStore(paramStore));
  }
  
  public void addAttributeCertificates(X509Store paramX509Store)
    throws CMSException
  {
    try
    {
      Iterator localIterator = paramX509Store.getMatches(null).iterator();
      while (localIterator.hasNext())
      {
        X509AttributeCertificate localX509AttributeCertificate = (X509AttributeCertificate)localIterator.next();
        this.certs.add(new DERTaggedObject(false, 2, AttributeCertificate.getInstance(ASN1Primitive.fromByteArray(localX509AttributeCertificate.getEncoded()))));
      }
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("error processing attribute certs", localIllegalArgumentException);
    }
    catch (IOException localIOException)
    {
      throw new CMSException("error processing attribute certs", localIOException);
    }
  }
  
  public void addCRLs(Store paramStore)
    throws CMSException
  {
    this.crls.addAll(CMSUtils.getCRLsFromStore(paramStore));
  }
  
  public void addCertificates(Store paramStore)
    throws CMSException
  {
    this.certs.addAll(CMSUtils.getCertificatesFromStore(paramStore));
  }
  
  public void addCertificatesAndCRLs(CertStore paramCertStore)
    throws CertStoreException, CMSException
  {
    this.certs.addAll(CMSUtils.getCertificatesFromStore(paramCertStore));
    this.crls.addAll(CMSUtils.getCRLsFromStore(paramCertStore));
  }
  
  public void addSignerInfoGenerator(SignerInfoGenerator paramSignerInfoGenerator)
  {
    this.signerGens.add(paramSignerInfoGenerator);
  }
  
  public void addSigners(SignerInformationStore paramSignerInformationStore)
  {
    Iterator localIterator = paramSignerInformationStore.getSigners().iterator();
    while (localIterator.hasNext()) {
      this._signers.add(localIterator.next());
    }
  }
  
  protected ASN1Set getAttributeSet(AttributeTable paramAttributeTable)
  {
    if (paramAttributeTable != null) {
      return new DERSet(paramAttributeTable.toASN1EncodableVector());
    }
    return null;
  }
  
  protected Map getBaseParameters(DERObjectIdentifier paramDERObjectIdentifier, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("contentType", paramDERObjectIdentifier);
    localHashMap.put("digestAlgID", paramAlgorithmIdentifier);
    localHashMap.put("digest", paramArrayOfByte.clone());
    return localHashMap;
  }
  
  protected String getEncOID(PrivateKey paramPrivateKey, String paramString)
  {
    String str;
    if (((paramPrivateKey instanceof RSAPrivateKey)) || ("RSA".equalsIgnoreCase(paramPrivateKey.getAlgorithm()))) {
      str = ENCRYPTION_RSA;
    }
    boolean bool;
    do
    {
      do
      {
        do
        {
          return str;
          if ((!(paramPrivateKey instanceof DSAPrivateKey)) && (!"DSA".equalsIgnoreCase(paramPrivateKey.getAlgorithm()))) {
            break;
          }
          str = ENCRYPTION_DSA;
        } while (paramString.equals(DIGEST_SHA1));
        throw new IllegalArgumentException("can't mix DSA with anything but SHA1");
        if ((!"ECDSA".equalsIgnoreCase(paramPrivateKey.getAlgorithm())) && (!"EC".equalsIgnoreCase(paramPrivateKey.getAlgorithm()))) {
          break;
        }
        str = (String)EC_ALGORITHMS.get(paramString);
      } while (str != null);
      throw new IllegalArgumentException("can't mix ECDSA with anything but SHA family digests");
      if (((paramPrivateKey instanceof GOST3410PrivateKey)) || ("GOST3410".equalsIgnoreCase(paramPrivateKey.getAlgorithm()))) {
        return ENCRYPTION_GOST3410;
      }
      bool = "ECGOST3410".equalsIgnoreCase(paramPrivateKey.getAlgorithm());
      str = null;
    } while (!bool);
    return ENCRYPTION_ECGOST3410;
  }
  
  public Map getGeneratedDigests()
  {
    return new HashMap(this.digests);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSSignedGenerator
 * JD-Core Version:    0.7.0.1
 */