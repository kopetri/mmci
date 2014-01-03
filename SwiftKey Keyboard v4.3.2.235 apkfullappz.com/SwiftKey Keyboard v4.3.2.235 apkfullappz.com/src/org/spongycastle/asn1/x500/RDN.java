package org.spongycastle.asn1.x500;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;

public class RDN
  extends ASN1Object
{
  private ASN1Set values;
  
  public RDN(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramASN1ObjectIdentifier);
    localASN1EncodableVector.add(paramASN1Encodable);
    this.values = new DERSet(new DERSequence(localASN1EncodableVector));
  }
  
  private RDN(ASN1Set paramASN1Set)
  {
    this.values = paramASN1Set;
  }
  
  public RDN(AttributeTypeAndValue paramAttributeTypeAndValue)
  {
    this.values = new DERSet(paramAttributeTypeAndValue);
  }
  
  public RDN(AttributeTypeAndValue[] paramArrayOfAttributeTypeAndValue)
  {
    this.values = new DERSet(paramArrayOfAttributeTypeAndValue);
  }
  
  public static RDN getInstance(Object paramObject)
  {
    if ((paramObject instanceof RDN)) {
      return (RDN)paramObject;
    }
    if (paramObject != null) {
      return new RDN(ASN1Set.getInstance(paramObject));
    }
    return null;
  }
  
  public AttributeTypeAndValue getFirst()
  {
    if (this.values.size() == 0) {
      return null;
    }
    return AttributeTypeAndValue.getInstance(this.values.getObjectAt(0));
  }
  
  public AttributeTypeAndValue[] getTypesAndValues()
  {
    AttributeTypeAndValue[] arrayOfAttributeTypeAndValue = new AttributeTypeAndValue[this.values.size()];
    for (int i = 0; i != arrayOfAttributeTypeAndValue.length; i++) {
      arrayOfAttributeTypeAndValue[i] = AttributeTypeAndValue.getInstance(this.values.getObjectAt(i));
    }
    return arrayOfAttributeTypeAndValue;
  }
  
  public boolean isMultiValued()
  {
    return this.values.size() > 1;
  }
  
  public int size()
  {
    return this.values.size();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.values;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x500.RDN
 * JD-Core Version:    0.7.0.1
 */