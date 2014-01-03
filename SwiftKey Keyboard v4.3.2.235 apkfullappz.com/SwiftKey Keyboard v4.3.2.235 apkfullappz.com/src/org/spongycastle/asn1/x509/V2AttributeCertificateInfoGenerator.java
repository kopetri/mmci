package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;

public class V2AttributeCertificateInfoGenerator
{
  private ASN1EncodableVector attributes = new ASN1EncodableVector();
  private DERGeneralizedTime endDate;
  private Extensions extensions;
  private Holder holder;
  private AttCertIssuer issuer;
  private DERBitString issuerUniqueID;
  private ASN1Integer serialNumber;
  private AlgorithmIdentifier signature;
  private DERGeneralizedTime startDate;
  private ASN1Integer version = new ASN1Integer(1);
  
  public void addAttribute(String paramString, ASN1Encodable paramASN1Encodable)
  {
    this.attributes.add(new Attribute(new ASN1ObjectIdentifier(paramString), new DERSet(paramASN1Encodable)));
  }
  
  public void addAttribute(Attribute paramAttribute)
  {
    this.attributes.add(paramAttribute);
  }
  
  public AttributeCertificateInfo generateAttributeCertificateInfo()
  {
    if ((this.serialNumber == null) || (this.signature == null) || (this.issuer == null) || (this.startDate == null) || (this.endDate == null) || (this.holder == null) || (this.attributes == null)) {
      throw new IllegalStateException("not all mandatory fields set in V2 AttributeCertificateInfo generator");
    }
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(this.holder);
    localASN1EncodableVector.add(this.issuer);
    localASN1EncodableVector.add(this.signature);
    localASN1EncodableVector.add(this.serialNumber);
    localASN1EncodableVector.add(new AttCertValidityPeriod(this.startDate, this.endDate));
    localASN1EncodableVector.add(new DERSequence(this.attributes));
    if (this.issuerUniqueID != null) {
      localASN1EncodableVector.add(this.issuerUniqueID);
    }
    if (this.extensions != null) {
      localASN1EncodableVector.add(this.extensions);
    }
    return AttributeCertificateInfo.getInstance(new DERSequence(localASN1EncodableVector));
  }
  
  public void setEndDate(DERGeneralizedTime paramDERGeneralizedTime)
  {
    this.endDate = paramDERGeneralizedTime;
  }
  
  public void setExtensions(Extensions paramExtensions)
  {
    this.extensions = paramExtensions;
  }
  
  public void setExtensions(X509Extensions paramX509Extensions)
  {
    this.extensions = Extensions.getInstance(paramX509Extensions.toASN1Primitive());
  }
  
  public void setHolder(Holder paramHolder)
  {
    this.holder = paramHolder;
  }
  
  public void setIssuer(AttCertIssuer paramAttCertIssuer)
  {
    this.issuer = paramAttCertIssuer;
  }
  
  public void setIssuerUniqueID(DERBitString paramDERBitString)
  {
    this.issuerUniqueID = paramDERBitString;
  }
  
  public void setSerialNumber(ASN1Integer paramASN1Integer)
  {
    this.serialNumber = paramASN1Integer;
  }
  
  public void setSignature(AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    this.signature = paramAlgorithmIdentifier;
  }
  
  public void setStartDate(DERGeneralizedTime paramDERGeneralizedTime)
  {
    this.startDate = paramDERGeneralizedTime;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.V2AttributeCertificateInfoGenerator
 * JD-Core Version:    0.7.0.1
 */