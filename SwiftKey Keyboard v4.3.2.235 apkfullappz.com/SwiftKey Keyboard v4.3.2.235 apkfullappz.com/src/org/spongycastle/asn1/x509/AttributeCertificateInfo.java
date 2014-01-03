package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class AttributeCertificateInfo
  extends ASN1Object
{
  private AttCertValidityPeriod attrCertValidityPeriod;
  private ASN1Sequence attributes;
  private Extensions extensions;
  private Holder holder;
  private AttCertIssuer issuer;
  private DERBitString issuerUniqueID;
  private ASN1Integer serialNumber;
  private AlgorithmIdentifier signature;
  private ASN1Integer version;
  
  private AttributeCertificateInfo(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() < 7) || (paramASN1Sequence.size() > 9)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.version = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    this.holder = Holder.getInstance(paramASN1Sequence.getObjectAt(1));
    this.issuer = AttCertIssuer.getInstance(paramASN1Sequence.getObjectAt(2));
    this.signature = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(3));
    this.serialNumber = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(4));
    this.attrCertValidityPeriod = AttCertValidityPeriod.getInstance(paramASN1Sequence.getObjectAt(5));
    this.attributes = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(6));
    int i = 7;
    if (i < paramASN1Sequence.size())
    {
      ASN1Encodable localASN1Encodable = paramASN1Sequence.getObjectAt(i);
      if ((localASN1Encodable instanceof DERBitString)) {
        this.issuerUniqueID = DERBitString.getInstance(paramASN1Sequence.getObjectAt(i));
      }
      for (;;)
      {
        i++;
        break;
        if (((localASN1Encodable instanceof ASN1Sequence)) || ((localASN1Encodable instanceof Extensions))) {
          this.extensions = Extensions.getInstance(paramASN1Sequence.getObjectAt(i));
        }
      }
    }
  }
  
  public static AttributeCertificateInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof AttributeCertificateInfo)) {
      return (AttributeCertificateInfo)paramObject;
    }
    if (paramObject != null) {
      return new AttributeCertificateInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static AttributeCertificateInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AttCertValidityPeriod getAttrCertValidityPeriod()
  {
    return this.attrCertValidityPeriod;
  }
  
  public ASN1Sequence getAttributes()
  {
    return this.attributes;
  }
  
  public Extensions getExtensions()
  {
    return this.extensions;
  }
  
  public Holder getHolder()
  {
    return this.holder;
  }
  
  public AttCertIssuer getIssuer()
  {
    return this.issuer;
  }
  
  public DERBitString getIssuerUniqueID()
  {
    return this.issuerUniqueID;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public AlgorithmIdentifier getSignature()
  {
    return this.signature;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(this.holder);
    localASN1EncodableVector.add(this.issuer);
    localASN1EncodableVector.add(this.signature);
    localASN1EncodableVector.add(this.serialNumber);
    localASN1EncodableVector.add(this.attrCertValidityPeriod);
    localASN1EncodableVector.add(this.attributes);
    if (this.issuerUniqueID != null) {
      localASN1EncodableVector.add(this.issuerUniqueID);
    }
    if (this.extensions != null) {
      localASN1EncodableVector.add(this.extensions);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.AttributeCertificateInfo
 * JD-Core Version:    0.7.0.1
 */