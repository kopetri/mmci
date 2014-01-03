package org.spongycastle.cert.ocsp;

import java.io.OutputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.ocsp.CertID;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;

public class CertificateID
{
  public static final AlgorithmIdentifier HASH_SHA1 = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
  private final CertID id;
  
  public CertificateID(CertID paramCertID)
  {
    if (paramCertID == null) {
      throw new IllegalArgumentException("'id' cannot be null");
    }
    this.id = paramCertID;
  }
  
  public CertificateID(DigestCalculator paramDigestCalculator, X509CertificateHolder paramX509CertificateHolder, BigInteger paramBigInteger)
    throws OCSPException
  {
    this.id = createCertID(paramDigestCalculator, paramX509CertificateHolder, new ASN1Integer(paramBigInteger));
  }
  
  private static CertID createCertID(DigestCalculator paramDigestCalculator, X509CertificateHolder paramX509CertificateHolder, ASN1Integer paramASN1Integer)
    throws OCSPException
  {
    try
    {
      OutputStream localOutputStream1 = paramDigestCalculator.getOutputStream();
      localOutputStream1.write(paramX509CertificateHolder.toASN1Structure().getSubject().getEncoded("DER"));
      localOutputStream1.close();
      DEROctetString localDEROctetString1 = new DEROctetString(paramDigestCalculator.getDigest());
      SubjectPublicKeyInfo localSubjectPublicKeyInfo = paramX509CertificateHolder.getSubjectPublicKeyInfo();
      OutputStream localOutputStream2 = paramDigestCalculator.getOutputStream();
      localOutputStream2.write(localSubjectPublicKeyInfo.getPublicKeyData().getBytes());
      localOutputStream2.close();
      DEROctetString localDEROctetString2 = new DEROctetString(paramDigestCalculator.getDigest());
      CertID localCertID = new CertID(paramDigestCalculator.getAlgorithmIdentifier(), localDEROctetString1, localDEROctetString2, paramASN1Integer);
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
  
  public ASN1ObjectIdentifier getHashAlgOID()
  {
    return this.id.getHashAlgorithm().getAlgorithm();
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
  
  public boolean matchesIssuer(X509CertificateHolder paramX509CertificateHolder, DigestCalculatorProvider paramDigestCalculatorProvider)
    throws OCSPException
  {
    try
    {
      boolean bool = createCertID(paramDigestCalculatorProvider.get(this.id.getHashAlgorithm()), paramX509CertificateHolder, this.id.getSerialNumber()).equals(this.id);
      return bool;
    }
    catch (OperatorCreationException localOperatorCreationException)
    {
      throw new OCSPException("unable to create digest calculator: " + localOperatorCreationException.getMessage(), localOperatorCreationException);
    }
  }
  
  public CertID toASN1Object()
  {
    return this.id;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.CertificateID
 * JD-Core Version:    0.7.0.1
 */