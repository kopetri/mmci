package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CertReqMessages
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private CertReqMessages(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public CertReqMessages(CertReqMsg paramCertReqMsg)
  {
    this.content = new DERSequence(paramCertReqMsg);
  }
  
  public CertReqMessages(CertReqMsg[] paramArrayOfCertReqMsg)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i < paramArrayOfCertReqMsg.length; i++) {
      localASN1EncodableVector.add(paramArrayOfCertReqMsg[i]);
    }
    this.content = new DERSequence(localASN1EncodableVector);
  }
  
  public static CertReqMessages getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertReqMessages)) {
      return (CertReqMessages)paramObject;
    }
    if (paramObject != null) {
      return new CertReqMessages(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
  
  public CertReqMsg[] toCertReqMsgArray()
  {
    CertReqMsg[] arrayOfCertReqMsg = new CertReqMsg[this.content.size()];
    for (int i = 0; i != arrayOfCertReqMsg.length; i++) {
      arrayOfCertReqMsg[i] = CertReqMsg.getInstance(this.content.getObjectAt(i));
    }
    return arrayOfCertReqMsg;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.CertReqMessages
 * JD-Core Version:    0.7.0.1
 */