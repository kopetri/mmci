package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.PolicyInformation;

public class SigningCertificateV2
  extends ASN1Object
{
  ASN1Sequence certs;
  ASN1Sequence policies;
  
  private SigningCertificateV2(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.certs = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.policies = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public SigningCertificateV2(ESSCertIDv2[] paramArrayOfESSCertIDv2)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i < paramArrayOfESSCertIDv2.length; i++) {
      localASN1EncodableVector.add(paramArrayOfESSCertIDv2[i]);
    }
    this.certs = new DERSequence(localASN1EncodableVector);
  }
  
  public SigningCertificateV2(ESSCertIDv2[] paramArrayOfESSCertIDv2, PolicyInformation[] paramArrayOfPolicyInformation)
  {
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    for (int i = 0; i < paramArrayOfESSCertIDv2.length; i++) {
      localASN1EncodableVector1.add(paramArrayOfESSCertIDv2[i]);
    }
    this.certs = new DERSequence(localASN1EncodableVector1);
    if (paramArrayOfPolicyInformation != null)
    {
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      for (int j = 0; j < paramArrayOfPolicyInformation.length; j++) {
        localASN1EncodableVector2.add(paramArrayOfPolicyInformation[j]);
      }
      this.policies = new DERSequence(localASN1EncodableVector2);
    }
  }
  
  public static SigningCertificateV2 getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof SigningCertificateV2))) {
      return (SigningCertificateV2)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new SigningCertificateV2((ASN1Sequence)paramObject);
    }
    return null;
  }
  
  public ESSCertIDv2[] getCerts()
  {
    ESSCertIDv2[] arrayOfESSCertIDv2 = new ESSCertIDv2[this.certs.size()];
    for (int i = 0; i != this.certs.size(); i++) {
      arrayOfESSCertIDv2[i] = ESSCertIDv2.getInstance(this.certs.getObjectAt(i));
    }
    return arrayOfESSCertIDv2;
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
 * Qualified Name:     org.spongycastle.asn1.ess.SigningCertificateV2
 * JD-Core Version:    0.7.0.1
 */