package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class SignaturePolicyId
  extends ASN1Object
{
  private OtherHashAlgAndValue sigPolicyHash;
  private ASN1ObjectIdentifier sigPolicyId;
  private SigPolicyQualifiers sigPolicyQualifiers;
  
  public SignaturePolicyId(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OtherHashAlgAndValue paramOtherHashAlgAndValue)
  {
    this(paramASN1ObjectIdentifier, paramOtherHashAlgAndValue, null);
  }
  
  public SignaturePolicyId(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OtherHashAlgAndValue paramOtherHashAlgAndValue, SigPolicyQualifiers paramSigPolicyQualifiers)
  {
    this.sigPolicyId = paramASN1ObjectIdentifier;
    this.sigPolicyHash = paramOtherHashAlgAndValue;
    this.sigPolicyQualifiers = paramSigPolicyQualifiers;
  }
  
  private SignaturePolicyId(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() != 2) && (paramASN1Sequence.size() != 3)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.sigPolicyId = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.sigPolicyHash = OtherHashAlgAndValue.getInstance(paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() == 3) {
      this.sigPolicyQualifiers = SigPolicyQualifiers.getInstance(paramASN1Sequence.getObjectAt(2));
    }
  }
  
  public static SignaturePolicyId getInstance(Object paramObject)
  {
    if ((paramObject instanceof SignaturePolicyId)) {
      return (SignaturePolicyId)paramObject;
    }
    if (paramObject != null) {
      return new SignaturePolicyId(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public OtherHashAlgAndValue getSigPolicyHash()
  {
    return this.sigPolicyHash;
  }
  
  public ASN1ObjectIdentifier getSigPolicyId()
  {
    return new ASN1ObjectIdentifier(this.sigPolicyId.getId());
  }
  
  public SigPolicyQualifiers getSigPolicyQualifiers()
  {
    return this.sigPolicyQualifiers;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.sigPolicyId);
    localASN1EncodableVector.add(this.sigPolicyHash);
    if (this.sigPolicyQualifiers != null) {
      localASN1EncodableVector.add(this.sigPolicyQualifiers);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.SignaturePolicyId
 * JD-Core Version:    0.7.0.1
 */