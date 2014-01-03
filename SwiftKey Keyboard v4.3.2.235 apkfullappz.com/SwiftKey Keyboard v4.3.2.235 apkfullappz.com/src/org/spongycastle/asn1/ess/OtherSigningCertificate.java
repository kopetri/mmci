package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.PolicyInformation;

public class OtherSigningCertificate
  extends ASN1Object
{
  ASN1Sequence certs;
  ASN1Sequence policies;
  
  private OtherSigningCertificate(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.certs = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.policies = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public OtherSigningCertificate(OtherCertID paramOtherCertID)
  {
    this.certs = new DERSequence(paramOtherCertID);
  }
  
  public static OtherSigningCertificate getInstance(Object paramObject)
  {
    if ((paramObject instanceof OtherSigningCertificate)) {
      return (OtherSigningCertificate)paramObject;
    }
    if (paramObject != null) {
      return new OtherSigningCertificate(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public OtherCertID[] getCerts()
  {
    OtherCertID[] arrayOfOtherCertID = new OtherCertID[this.certs.size()];
    for (int i = 0; i != this.certs.size(); i++) {
      arrayOfOtherCertID[i] = OtherCertID.getInstance(this.certs.getObjectAt(i));
    }
    return arrayOfOtherCertID;
  }
  
  public PolicyInformation[] getPolicies()
  {
    PolicyInformation[] arrayOfPolicyInformation;
    if (this.policies == null) {
      arrayOfPolicyInformation = null;
    }
    for (;;)
    {
      return arrayOfPolicyInformation;
      arrayOfPolicyInformation = new PolicyInformation[this.policies.size()];
      for (int i = 0; i != this.policies.size(); i++) {
        arrayOfPolicyInformation[i] = PolicyInformation.getInstance(this.policies.getObjectAt(i));
      }
    }
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certs);
    if (this.policies != null) {
      localASN1EncodableVector.add(this.policies);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ess.OtherSigningCertificate
 * JD-Core Version:    0.7.0.1
 */