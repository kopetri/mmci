package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class PolicyInformation
  extends ASN1Object
{
  private ASN1ObjectIdentifier policyIdentifier;
  private ASN1Sequence policyQualifiers;
  
  public PolicyInformation(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.policyIdentifier = paramASN1ObjectIdentifier;
  }
  
  public PolicyInformation(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Sequence paramASN1Sequence)
  {
    this.policyIdentifier = paramASN1ObjectIdentifier;
    this.policyQualifiers = paramASN1Sequence;
  }
  
  private PolicyInformation(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.policyIdentifier = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.policyQualifiers = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public static PolicyInformation getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof PolicyInformation))) {
      return (PolicyInformation)paramObject;
    }
    return new PolicyInformation(ASN1Sequence.getInstance(paramObject));
  }
  
  public ASN1ObjectIdentifier getPolicyIdentifier()
  {
    return this.policyIdentifier;
  }
  
  public ASN1Sequence getPolicyQualifiers()
  {
    return this.policyQualifiers;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.policyIdentifier);
    if (this.policyQualifiers != null) {
      localASN1EncodableVector.add(this.policyQualifiers);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.PolicyInformation
 * JD-Core Version:    0.7.0.1
 */