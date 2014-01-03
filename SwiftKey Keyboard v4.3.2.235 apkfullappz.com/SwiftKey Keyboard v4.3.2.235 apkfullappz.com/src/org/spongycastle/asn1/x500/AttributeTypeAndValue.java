package org.spongycastle.asn1.x500;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class AttributeTypeAndValue
  extends ASN1Object
{
  private ASN1ObjectIdentifier type;
  private ASN1Encodable value;
  
  public AttributeTypeAndValue(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.type = paramASN1ObjectIdentifier;
    this.value = paramASN1Encodable;
  }
  
  private AttributeTypeAndValue(ASN1Sequence paramASN1Sequence)
  {
    this.type = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    this.value = paramASN1Sequence.getObjectAt(1);
  }
  
  public static AttributeTypeAndValue getInstance(Object paramObject)
  {
    if ((paramObject instanceof AttributeTypeAndValue)) {
      return (AttributeTypeAndValue)paramObject;
    }
    if (paramObject != null) {
      return new AttributeTypeAndValue(ASN1Sequence.getInstance(paramObject));
    }
    throw new IllegalArgumentException("null value in getInstance()");
  }
  
  public ASN1ObjectIdentifier getType()
  {
    return this.type;
  }
  
  public ASN1Encodable getValue()
  {
    return this.value;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.type);
    localASN1EncodableVector.add(this.value);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x500.AttributeTypeAndValue
 * JD-Core Version:    0.7.0.1
 */