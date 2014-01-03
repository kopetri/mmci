package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class CertificatePolicies
  extends ASN1Object
{
  private final PolicyInformation[] policyInformation;
  
  private CertificatePolicies(ASN1Sequence paramASN1Sequence)
  {
    this.policyInformation = new PolicyInformation[paramASN1Sequence.size()];
    for (int i = 0; i != paramASN1Sequence.size(); i++) {
      this.policyInformation[i] = PolicyInformation.getInstance(paramASN1Sequence.getObjectAt(i));
    }
  }
  
  public CertificatePolicies(PolicyInformation paramPolicyInformation)
  {
    this.policyInformation = new PolicyInformation[] { paramPolicyInformation };
  }
  
  public CertificatePolicies(PolicyInformation[] paramArrayOfPolicyInformation)
  {
    this.policyInformation = paramArrayOfPolicyInformation;
  }
  
  public static CertificatePolicies getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertificatePolicies)) {
      return (CertificatePolicies)paramObject;
    }
    if (paramObject != null) {
      return new CertificatePolicies(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static CertificatePolicies getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public PolicyInformation[] getPolicyInformation()
  {
    PolicyInformation[] arrayOfPolicyInformation = new PolicyInformation[this.policyInformation.length];
    System.arraycopy(this.policyInformation, 0, arrayOfPolicyInformation, 0, this.policyInformation.length);
    return arrayOfPolicyInformation;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERSequence(this.policyInformation);
  }
  
  public String toString()
  {
    String str = null;
    for (int i = 0; i < this.policyInformation.length; i++)
    {
      if (str != null) {
        str = str + ", ";
      }
      str = str + this.policyInformation[i];
    }
    return "CertificatePolicies: " + str;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.CertificatePolicies
 * JD-Core Version:    0.7.0.1
 */