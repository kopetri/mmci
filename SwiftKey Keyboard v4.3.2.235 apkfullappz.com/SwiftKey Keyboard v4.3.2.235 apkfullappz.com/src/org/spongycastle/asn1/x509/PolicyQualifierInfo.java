package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;

public class PolicyQualifierInfo
  extends ASN1Object
{
  private ASN1ObjectIdentifier policyQualifierId;
  private ASN1Encodable qualifier;
  
  public PolicyQualifierInfo(String paramString)
  {
    this.policyQualifierId = PolicyQualifierId.id_qt_cps;
    this.qualifier = new DERIA5String(paramString);
  }
  
  public PolicyQualifierInfo(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.policyQualifierId = paramASN1ObjectIdentifier;
    this.qualifier = paramASN1Encodable;
  }
  
  public PolicyQualifierInfo(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.policyQualifierId = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.qualifier = paramASN1Sequence.getObjectAt(1);
  }
  
  public static PolicyQualifierInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof PolicyQualifierInfo)) {
      return (PolicyQualifierInfo)paramObject;
    }
    if (paramObject != null) {
      return new PolicyQualifierInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getPolicyQualifierId()
  {
    return this.policyQualifierId;
  }
  
  public ASN1Encodable getQualifier()
  {
    return this.qualifier;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.policyQualifierId);
    localASN1EncodableVector.add(this.qualifier);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.PolicyQualifierInfo
 * JD-Core Version:    0.7.0.1
 */