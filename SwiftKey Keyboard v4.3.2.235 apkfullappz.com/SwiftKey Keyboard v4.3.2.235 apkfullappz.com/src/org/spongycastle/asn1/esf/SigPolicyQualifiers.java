package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class SigPolicyQualifiers
  extends ASN1Object
{
  ASN1Sequence qualifiers;
  
  private SigPolicyQualifiers(ASN1Sequence paramASN1Sequence)
  {
    this.qualifiers = paramASN1Sequence;
  }
  
  public SigPolicyQualifiers(SigPolicyQualifierInfo[] paramArrayOfSigPolicyQualifierInfo)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i < paramArrayOfSigPolicyQualifierInfo.length; i++) {
      localASN1EncodableVector.add(paramArrayOfSigPolicyQualifierInfo[i]);
    }
    this.qualifiers = new DERSequence(localASN1EncodableVector);
  }
  
  public static SigPolicyQualifiers getInstance(Object paramObject)
  {
    if ((paramObject instanceof SigPolicyQualifiers)) {
      return (SigPolicyQualifiers)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new SigPolicyQualifiers(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public SigPolicyQualifierInfo getInfoAt(int paramInt)
  {
    return SigPolicyQualifierInfo.getInstance(this.qualifiers.getObjectAt(paramInt));
  }
  
  public int size()
  {
    return this.qualifiers.size();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.qualifiers;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.SigPolicyQualifiers
 * JD-Core Version:    0.7.0.1
 */