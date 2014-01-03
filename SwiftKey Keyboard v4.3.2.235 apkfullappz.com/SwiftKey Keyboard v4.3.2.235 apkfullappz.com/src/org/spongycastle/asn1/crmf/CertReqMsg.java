package org.spongycastle.asn1.crmf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class CertReqMsg
  extends ASN1Object
{
  private CertRequest certReq;
  private ProofOfPossession pop;
  private ASN1Sequence regInfo;
  
  private CertReqMsg(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.certReq = CertRequest.getInstance(localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if (((localObject instanceof ASN1TaggedObject)) || ((localObject instanceof ProofOfPossession))) {
        this.pop = ProofOfPossession.getInstance(localObject);
      } else {
        this.regInfo = ASN1Sequence.getInstance(localObject);
      }
    }
  }
  
  public CertReqMsg(CertRequest paramCertRequest, ProofOfPossession paramProofOfPossession, AttributeTypeAndValue[] paramArrayOfAttributeTypeAndValue)
  {
    if (paramCertRequest == null) {
      throw new IllegalArgumentException("'certReq' cannot be null");
    }
    this.certReq = paramCertRequest;
    this.pop = paramProofOfPossession;
    if (paramArrayOfAttributeTypeAndValue != null) {
      this.regInfo = new DERSequence(paramArrayOfAttributeTypeAndValue);
    }
  }
  
  private void addOptional(ASN1EncodableVector paramASN1EncodableVector, ASN1Encodable paramASN1Encodable)
  {
    if (paramASN1Encodable != null) {
      paramASN1EncodableVector.add(paramASN1Encodable);
    }
  }
  
  public static CertReqMsg getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertReqMsg)) {
      return (CertReqMsg)paramObject;
    }
    if (paramObject != null) {
      return new CertReqMsg(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CertRequest getCertReq()
  {
    return this.certReq;
  }
  
  public ProofOfPossession getPop()
  {
    return this.pop;
  }
  
  public ProofOfPossession getPopo()
  {
    return this.pop;
  }
  
  public AttributeTypeAndValue[] getRegInfo()
  {
    AttributeTypeAndValue[] arrayOfAttributeTypeAndValue;
    if (this.regInfo == null) {
      arrayOfAttributeTypeAndValue = null;
    }
    for (;;)
    {
      return arrayOfAttributeTypeAndValue;
      arrayOfAttributeTypeAndValue = new AttributeTypeAndValue[this.regInfo.size()];
      for (int i = 0; i != arrayOfAttributeTypeAndValue.length; i++) {
        arrayOfAttributeTypeAndValue[i] = AttributeTypeAndValue.getInstance(this.regInfo.getObjectAt(i));
      }
    }
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certReq);
    addOptional(localASN1EncodableVector, this.pop);
    addOptional(localASN1EncodableVector, this.regInfo);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.CertReqMsg
 * JD-Core Version:    0.7.0.1
 */