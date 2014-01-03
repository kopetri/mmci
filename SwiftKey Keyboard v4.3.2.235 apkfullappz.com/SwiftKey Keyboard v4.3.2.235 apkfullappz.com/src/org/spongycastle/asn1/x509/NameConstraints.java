package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class NameConstraints
  extends ASN1Object
{
  private ASN1Sequence excluded;
  private ASN1Sequence permitted;
  
  public NameConstraints(Vector paramVector1, Vector paramVector2)
  {
    if (paramVector1 != null) {
      this.permitted = createSequence(paramVector1);
    }
    if (paramVector2 != null) {
      this.excluded = createSequence(paramVector2);
    }
  }
  
  private NameConstraints(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(localEnumeration.nextElement());
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        break;
      case 0: 
        this.permitted = ASN1Sequence.getInstance(localASN1TaggedObject, false);
        break;
      case 1: 
        this.excluded = ASN1Sequence.getInstance(localASN1TaggedObject, false);
      }
    }
  }
  
  private DERSequence createSequence(Vector paramVector)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements()) {
      localASN1EncodableVector.add((GeneralSubtree)localEnumeration.nextElement());
    }
    return new DERSequence(localASN1EncodableVector);
  }
  
  public static NameConstraints getInstance(Object paramObject)
  {
    if ((paramObject instanceof NameConstraints)) {
      return (NameConstraints)paramObject;
    }
    if (paramObject != null) {
      return new NameConstraints(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Sequence getExcludedSubtrees()
  {
    return this.excluded;
  }
  
  public ASN1Sequence getPermittedSubtrees()
  {
    return this.permitted;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.permitted != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.permitted));
    }
    if (this.excluded != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.excluded));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.NameConstraints
 * JD-Core Version:    0.7.0.1
 */