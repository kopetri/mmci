package org.spongycastle.cert;

import java.math.BigInteger;
import java.util.Date;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.ExtensionsGenerator;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.TBSCertificate;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.asn1.x509.V3TBSCertificateGenerator;
import org.spongycastle.operator.ContentSigner;

public class X509v3CertificateBuilder
{
  private ExtensionsGenerator extGenerator;
  private V3TBSCertificateGenerator tbsGen = new V3TBSCertificateGenerator();
  
  public X509v3CertificateBuilder(X500Name paramX500Name1, BigInteger paramBigInteger, Date paramDate1, Date paramDate2, X500Name paramX500Name2, SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    this.tbsGen.setSerialNumber(new ASN1Integer(paramBigInteger));
    this.tbsGen.setIssuer(paramX500Name1);
    this.tbsGen.setStartDate(new Time(paramDate1));
    this.tbsGen.setEndDate(new Time(paramDate2));
    this.tbsGen.setSubject(paramX500Name2);
    this.tbsGen.setSubjectPublicKeyInfo(paramSubjectPublicKeyInfo);
    this.extGenerator = new ExtensionsGenerator();
  }
  
  public X509v3CertificateBuilder addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws CertIOException
  {
    CertUtils.addExtension(this.extGenerator, paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
    return this;
  }
  
  public X509CertificateHolder build(ContentSigner paramContentSigner)
  {
    this.tbsGen.setSignature(paramContentSigner.getAlgorithmIdentifier());
    if (!this.extGenerator.isEmpty()) {
      this.tbsGen.setExtensions(this.extGenerator.generate());
    }
    return CertUtils.generateFullCert(paramContentSigner, this.tbsGen.generateTBSCertificate());
  }
  
  public X509v3CertificateBuilder copyAndAddExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, X509CertificateHolder paramX509CertificateHolder)
  {
    Extension localExtension = paramX509CertificateHolder.toASN1Structure().getTBSCertificate().getExtensions().getExtension(paramASN1ObjectIdentifier);
    if (localExtension == null) {
      throw new NullPointerException("extension " + paramASN1ObjectIdentifier + " not present");
    }
    this.extGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, localExtension.getExtnValue().getOctets());
    return this;
  }
  
  public X509v3CertificateBuilder setIssuerUniqueID(boolean[] paramArrayOfBoolean)
  {
    this.tbsGen.setIssuerUniqueID(CertUtils.booleanToBitString(paramArrayOfBoolean));
    return this;
  }
  
  public X509v3CertificateBuilder setSubjectUniqueID(boolean[] paramArrayOfBoolean)
  {
    this.tbsGen.setSubjectUniqueID(CertUtils.booleanToBitString(paramArrayOfBoolean));
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.X509v3CertificateBuilder
 * JD-Core Version:    0.7.0.1
 */