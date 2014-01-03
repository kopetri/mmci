package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class OtherKeyAttribute
  extends ASN1Object
{
  private ASN1Encodable keyAttr;
  private ASN1ObjectIdentifier keyAttrId;
  
  public OtherKeyAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.keyAttrId = paramASN1ObjectIdentifier;
    this.keyAttr = paramASN1Encodable;
  }
  
  public OtherKeyAttribute(ASN1Sequence paramASN1Sequence)
  {
    this.keyAttrId = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    this.keyAttr = paramASN1Sequence.getObjectAt(1);
  }
  
  public static OtherKeyAttribute getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof OtherKeyAttribute))) {
      return (OtherKeyAttribute)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new OtherKeyAttribute((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public ASN1Encodable getKeyAttr()
  {
    return this.keyAttr;
  }
  
  public ASN1ObjectIdentifier getKeyAttrId()
  {
    return this.keyAttrId;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.keyAttrId);
    localASN1EncodableVector.add(this.keyAttr);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.OtherKeyAttribute
 * JD-Core Version:    0.7.0.1
 */