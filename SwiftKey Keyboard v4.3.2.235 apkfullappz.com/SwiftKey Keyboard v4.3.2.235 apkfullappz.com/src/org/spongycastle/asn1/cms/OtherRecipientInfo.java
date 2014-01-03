package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class OtherRecipientInfo
  extends ASN1Object
{
  private ASN1ObjectIdentifier oriType;
  private ASN1Encodable oriValue;
  
  public OtherRecipientInfo(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.oriType = paramASN1ObjectIdentifier;
    this.oriValue = paramASN1Encodable;
  }
  
  public OtherRecipientInfo(ASN1Sequence paramASN1Sequence)
  {
    this.oriType = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.oriValue = paramASN1Sequence.getObjectAt(1);
  }
  
  public static OtherRecipientInfo getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof OtherRecipientInfo))) {
      return (OtherRecipientInfo)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new OtherRecipientInfo((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid OtherRecipientInfo: " + paramObject.getClass().getName());
  }
  
  public static OtherRecipientInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1ObjectIdentifier getType()
  {
    return this.oriType;
  }
  
  public ASN1Encodable getValue()
  {
    return this.oriValue;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.oriType);
    localASN1EncodableVector.add(this.oriValue);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.OtherRecipientInfo
 * JD-Core Version:    0.7.0.1
 */