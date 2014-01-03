package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;

public class Attribute
  extends ASN1Object
{
  private ASN1ObjectIdentifier attrType;
  private ASN1Set attrValues;
  
  public Attribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Set paramASN1Set)
  {
    this.attrType = paramASN1ObjectIdentifier;
    this.attrValues = paramASN1Set;
  }
  
  private Attribute(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.attrType = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.attrValues = ASN1Set.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static Attribute getInstance(Object paramObject)
  {
    if ((paramObject instanceof Attribute)) {
      return (Attribute)paramObject;
    }
    if (paramObject != null) {
      return new Attribute(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getAttrType()
  {
    return new ASN1ObjectIdentifier(this.attrType.getId());
  }
  
  public ASN1Set getAttrValues()
  {
    return this.attrValues;
  }
  
  public ASN1Encodable[] getAttributeValues()
  {
    return this.attrValues.toArray();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.attrType);
    localASN1EncodableVector.add(this.attrValues);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.Attribute
 * JD-Core Version:    0.7.0.1
 */