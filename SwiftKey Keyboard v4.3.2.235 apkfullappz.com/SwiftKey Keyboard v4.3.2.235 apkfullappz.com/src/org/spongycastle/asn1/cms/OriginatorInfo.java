package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class OriginatorInfo
  extends ASN1Object
{
  private ASN1Set certs;
  private ASN1Set crls;
  
  private OriginatorInfo(ASN1Sequence paramASN1Sequence)
  {
    ASN1TaggedObject localASN1TaggedObject;
    switch (paramASN1Sequence.size())
    {
    default: 
      throw new IllegalArgumentException("OriginatorInfo too big");
    case 1: 
      localASN1TaggedObject = (ASN1TaggedObject)paramASN1Sequence.getObjectAt(0);
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("Bad tag in OriginatorInfo: " + localASN1TaggedObject.getTagNo());
      case 0: 
        this.certs = ASN1Set.getInstance(localASN1TaggedObject, false);
      }
    case 0: 
      return;
      this.crls = ASN1Set.getInstance(localASN1TaggedObject, false);
      return;
    }
    this.certs = ASN1Set.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(0), false);
    this.crls = ASN1Set.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(1), false);
  }
  
  public OriginatorInfo(ASN1Set paramASN1Set1, ASN1Set paramASN1Set2)
  {
    this.certs = paramASN1Set1;
    this.crls = paramASN1Set2;
  }
  
  public static OriginatorInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof OriginatorInfo)) {
      return (OriginatorInfo)paramObject;
    }
    if (paramObject != null) {
      return new OriginatorInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static OriginatorInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1Set getCRLs()
  {
    return this.crls;
  }
  
  public ASN1Set getCertificates()
  {
    return this.certs;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.certs != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.certs));
    }
    if (this.crls != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.crls));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.OriginatorInfo
 * JD-Core Version:    0.7.0.1
 */