package org.spongycastle.asn1.crmf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Time;

public class OptionalValidity
  extends ASN1Object
{
  private Time notAfter;
  private Time notBefore;
  
  private OptionalValidity(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localEnumeration.nextElement();
      if (localASN1TaggedObject.getTagNo() == 0) {
        this.notBefore = Time.getInstance(localASN1TaggedObject, true);
      } else {
        this.notAfter = Time.getInstance(localASN1TaggedObject, true);
      }
    }
  }
  
  public static OptionalValidity getInstance(Object paramObject)
  {
    if ((paramObject instanceof OptionalValidity)) {
      return (OptionalValidity)paramObject;
    }
    if (paramObject != null) {
      return new OptionalValidity(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.notBefore != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.notBefore));
    }
    if (this.notAfter != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.notAfter));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.OptionalValidity
 * JD-Core Version:    0.7.0.1
 */