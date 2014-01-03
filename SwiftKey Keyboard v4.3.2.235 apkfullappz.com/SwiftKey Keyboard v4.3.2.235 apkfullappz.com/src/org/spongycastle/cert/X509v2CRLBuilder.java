package org.spongycastle.cert;

import java.math.BigInteger;
import java.util.Date;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.ExtensionsGenerator;
import org.spongycastle.asn1.x509.TBSCertList;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.asn1.x509.V2TBSCertListGenerator;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.operator.ContentSigner;

public class X509v2CRLBuilder
{
  private ExtensionsGenerator extGenerator = new ExtensionsGenerator();
  private V2TBSCertListGenerator tbsGen = new V2TBSCertListGenerator();
  
  public X509v2CRLBuilder(X500Name paramX500Name, Date paramDate)
  {
    this.tbsGen.setIssuer(paramX500Name);
    this.tbsGen.setThisUpdate(new Time(paramDate));
  }
  
  public X509v2CRLBuilder addCRL(X509CRLHolder paramX509CRLHolder)
  {
    TBSCertList localTBSCertList = paramX509CRLHolder.toASN1Structure().getTBSCertList();
    if (localTBSCertList != null)
    {
      Enumeration localEnumeration = localTBSCertList.getRevokedCertificateEnumeration();
      while (localEnumeration.hasMoreElements()) {
        this.tbsGen.addCRLEntry(ASN1Sequence.getInstance(((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive()));
      }
    }
    return this;
  }
  
  public X509v2CRLBuilder addCRLEntry(BigInteger paramBigInteger, Date paramDate, int paramInt)
  {
    this.tbsGen.addCRLEntry(new ASN1Integer(paramBigInteger), new Time(paramDate), paramInt);
    return this;
  }
  
  public X509v2CRLBuilder addCRLEntry(BigInteger paramBigInteger, Date paramDate1, int paramInt, Date paramDate2)
  {
    this.tbsGen.addCRLEntry(new ASN1Integer(paramBigInteger), new Time(paramDate1), paramInt, new DERGeneralizedTime(paramDate2));
    return this;
  }
  
  public X509v2CRLBuilder addCRLEntry(BigInteger paramBigInteger, Date paramDate, Extensions paramExtensions)
  {
    this.tbsGen.addCRLEntry(new ASN1Integer(paramBigInteger), new Time(paramDate), paramExtensions);
    return this;
  }
  
  public X509v2CRLBuilder addCRLEntry(BigInteger paramBigInteger, Date paramDate, X509Extensions paramX509Extensions)
  {
    this.tbsGen.addCRLEntry(new ASN1Integer(paramBigInteger), new Time(paramDate), Extensions.getInstance(paramX509Extensions));
    return this;
  }
  
  public X509v2CRLBuilder addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws CertIOException
  {
    CertUtils.addExtension(this.extGenerator, paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
    return this;
  }
  
  public X509CRLHolder build(ContentSigner paramContentSigner)
  {
    this.tbsGen.setSignature(paramContentSigner.getAlgorithmIdentifier());
    if (!this.extGenerator.isEmpty()) {
      this.tbsGen.setExtensions(this.extGenerator.generate());
    }
    return CertUtils.generateFullCRL(paramContentSigner, this.tbsGen.generateTBSCertList());
  }
  
  public X509v2CRLBuilder setNextUpdate(Date paramDate)
  {
    this.tbsGen.setNextUpdate(new Time(paramDate));
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.X509v2CRLBuilder
 * JD-Core Version:    0.7.0.1
 */