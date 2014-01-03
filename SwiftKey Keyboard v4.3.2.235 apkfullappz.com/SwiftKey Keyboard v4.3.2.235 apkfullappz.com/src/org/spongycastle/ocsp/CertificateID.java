package org.spongycastle.ocsp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.ocsp.CertID;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jce.PrincipalUtil;
import org.spongycastle.jce.X509Principal;

public class CertificateID
{
  public static final String HASH_SHA1 = "1.3.14.3.2.26";
  private final CertID id;
  
  public CertificateID(String paramString, X509Certificate paramX509Certificate, BigInteger paramBigInteger)
    throws OCSPException
  {
    this(paramString, paramX509Certificate, paramBigInteger, "SC");
  }
  
  public CertificateID(String paramString1, X509Certificate paramX509Certificate, BigInteger paramBigInteger, String paramString2)
    throws OCSPException
  {
    this.id = createCertID(new AlgorithmIdentifier(new DERObjectIdentifier(paramString1), DERNull.INSTANCE), paramX509Certificate, new ASN1Integer(paramBigInteger), paramString2);
  }
  
  public CertificateID(CertID paramCertID)
  {
    if (paramCertID == null) {
      throw new IllegalArgumentException("'id' cannot be null");
    }
    this.id = paramCertID;
  }
  
  private static CertID createCertID(AlgorithmIdentifier paramAlgorithmIdentifier, X509Certificate paramX509Certificate, ASN1Integer paramASN1Integer, String paramString)
    throws OCSPException
  {
    try
    {
      MessageDigest localMessageDigest = OCSPUtil.createDigestInstance(paramAlgorithmIdentifier.getAlgorithm().getId(), paramString);
      localMessageDigest.update(PrincipalUtil.getSubjectX509Principal(paramX509Certificate).getEncoded());
      DEROctetString localDEROctetString = new DEROctetString(localMessageDigest.digest());
      localMessageDigest.update(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(paramX509Certificate.getPublicKey().getEncoded()).readObject()).getPublicKeyData().getBytes());
      CertID localCertID = new CertID(paramAlgorithmIdentifier, localDEROctetString, new DEROctetString(localMessageDigest.digest()), paramASN1Integer);
      return localCertID;
    }
    catch (Exception localException)
    {
      throw new OCSPException("problem creating ID: " + localException, localException);
    }
  }
  
  public static CertificateID deriveCertificateID(CertificateID paramCertificateID, BigInteger paramBigInteger)
  {
    return new CertificateID(new CertID(paramCertificateID.id.getHashAlgorithm(), paramCertificateID.id.getIssuerNameHash(), paramCertificateID.id.getIssuerKeyHash(), new ASN1Integer(paramBigInteger)));
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof CertificateID)) {
      return false;
    }
    CertificateID localCertificateID = (CertificateID)paramObject;
    return this.id.toASN1Primitive().equals(localCertificateID.id.toASN1Primitive());
  }
  
  public String getHashAlgOID()
  {
    return this.id.getHashAlgorithm().getObjectId().getId();
  }
  
  public byte[] getIssuerKeyHash()
  {
    return this.id.getIssuerKeyHash().getOctets();
  }
  
  public byte[] getIssuerNameHash()
  {
    return this.id.getIssuerNameHash().getOctets();
  }
  
  public BigInteger getSerialNumber()
  {
    return this.id.getSerialNumber().getValue();
  }
  
  public int hashCode()
  {
    return this.id.toASN1Primitive().hashCode();
  }
  
  public boolean matchesIssuer(X509Certificate paramX509Certificate, String paramString)
    throws OCSPException
  {
    return createCertID(this.id.getHashAlgorithm(), paramX509Certificate, this.id.getSerialNumber(), paramString).equals(this.id);
  }
  
  public CertID toASN1Object()
  {
    return this.id;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.ocsp.CertificateID
 * JD-Core Version:    0.7.0.1
 */