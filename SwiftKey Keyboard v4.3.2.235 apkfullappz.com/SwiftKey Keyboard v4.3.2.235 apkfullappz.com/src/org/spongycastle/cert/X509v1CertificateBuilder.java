package org.spongycastle.cert;

import java.math.BigInteger;
import java.util.Date;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.asn1.x509.V1TBSCertificateGenerator;
import org.spongycastle.operator.ContentSigner;

public class X509v1CertificateBuilder
{
  private V1TBSCertificateGenerator tbsGen;
  
  public X509v1CertificateBuilder(X500Name paramX500Name1, BigInteger paramBigInteger, Date paramDate1, Date paramDate2, X500Name paramX500Name2, SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    if (paramX500Name1 == null) {
      throw new IllegalArgumentException("issuer must not be null");
    }
    if (paramSubjectPublicKeyInfo == null) {
      throw new IllegalArgumentException("publicKeyInfo must not be null");
    }
    this.tbsGen = new V1TBSCertificateGenerator();
    this.tbsGen.setSerialNumber(new ASN1Integer(paramBigInteger));
    this.tbsGen.setIssuer(paramX500Name1);
    this.tbsGen.setStartDate(new Time(paramDate1));
    this.tbsGen.setEndDate(new Time(paramDate2));
    this.tbsGen.setSubject(paramX500Name2);
    this.tbsGen.setSubjectPublicKeyInfo(paramSubjectPublicKeyInfo);
  }
  
  public X509CertificateHolder build(ContentSigner paramContentSigner)
  {
    this.tbsGen.setSignature(paramContentSigner.getAlgorithmIdentifier());
    return CertUtils.generateFullCert(paramContentSigner, this.tbsGen.generateTBSCertificate());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.X509v1CertificateBuilder
 * JD-Core Version:    0.7.0.1
 */