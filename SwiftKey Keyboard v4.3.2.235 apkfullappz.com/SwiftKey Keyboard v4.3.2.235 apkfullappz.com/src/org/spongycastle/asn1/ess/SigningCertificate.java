package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.PolicyInformation;

public class SigningCertificate
  extends ASN1Object
{
  ASN1Sequence certs;
  ASN1Sequence policies;
  
  private SigningCertificate(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.certs = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.policies = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public SigningCertificate(ESSCertID paramESSCertID)
  {
    this.certs = new DERSequence(paramESSCertID);
  }
  
  public static SigningCertificate getInstance(Object paramObject)
  {
    if ((paramObject instanceof SigningCertificate)) {
      return (SigningCertificate)paramObject;
    }
    if (paramObject != null) {
      return new SigningCertificate(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ESSCertID[] getCerts()
  {
    ESSCertID[] arrayOfESSCertID = new ESSCertID[this.certs.size()];
    for (int i = 0; i != this.certs.size(); i++) {
      arrayOfESSCertID[i] = ESSCertID.getInstance(this.certs.getObjectAt(i));
    }
    return arrayOfESSCertID;
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
 * Qualified Name:     org.spongycastle.asn1.ess.SigningCertificate
 * JD-Core Version:    0.7.0.1
 */