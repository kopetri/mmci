package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class SigPolicyQualifierInfo
  extends ASN1Object
{
  private ASN1ObjectIdentifier sigPolicyQualifierId;
  private ASN1Encodable sigQualifier;
  
  public SigPolicyQualifierInfo(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.sigPolicyQualifierId = paramASN1ObjectIdentifier;
    this.sigQualifier = paramASN1Encodable;
  }
  
  private SigPolicyQualifierInfo(ASN1Sequence paramASN1Sequence)
  {
    this.sigPolicyQualifierId = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.sigQualifier = paramASN1Sequence.getObjectAt(1);
  }
  
  public static SigPolicyQualifierInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof SigPolicyQualifierInfo)) {
      return (SigPolicyQualifierInfo)paramObject;
    }
    if (paramObject != null) {
      return new SigPolicyQualifierInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getSigPolicyQualifierId()
  {
    return new ASN1ObjectIdentifier(this.sigPolicyQualifierId.getId());
  }
  
  public ASN1Encodable getSigQualifier()
  {
    return this.sigQualifier;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.sigPolicyQualifierId);
    localASN1EncodableVector.add(this.sigQualifier);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.SigPolicyQualifierInfo
 * JD-Core Version:    0.7.0.1
 */