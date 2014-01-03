package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class Controls
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private Controls(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public Controls(AttributeTypeAndValue paramAttributeTypeAndValue)
  {
    this.content = new DERSequence(paramAttributeTypeAndValue);
  }
  
  public Controls(AttributeTypeAndValue[] paramArrayOfAttributeTypeAndValue)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i < paramArrayOfAttributeTypeAndValue.length; i++) {
      localASN1EncodableVector.add(paramArrayOfAttributeTypeAndValue[i]);
    }
    this.content = new DERSequence(localASN1EncodableVector);
  }
  
  public static Controls getInstance(Object paramObject)
  {
    if ((paramObject instanceof Controls)) {
      return (Controls)paramObject;
    }
    if (paramObject != null) {
      return new Controls(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
  
  public AttributeTypeAndValue[] toAttributeTypeAndValueArray()
  {
    AttributeTypeAndValue[] arrayOfAttributeTypeAndValue = new AttributeTypeAndValue[this.content.size()];
    for (int i = 0; i != arrayOfAttributeTypeAndValue.length; i++) {
      arrayOfAttributeTypeAndValue[i] = AttributeTypeAndValue.getInstance(this.content.getObjectAt(i));
    }
    return arrayOfAttributeTypeAndValue;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.Controls
 * JD-Core Version:    0.7.0.1
 */