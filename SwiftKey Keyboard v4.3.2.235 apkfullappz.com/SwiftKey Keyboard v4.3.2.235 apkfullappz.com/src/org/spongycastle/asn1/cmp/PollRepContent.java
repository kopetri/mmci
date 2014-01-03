package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class PollRepContent
  extends ASN1Object
{
  private ASN1Integer certReqId;
  private ASN1Integer checkAfter;
  private PKIFreeText reason;
  
  private PollRepContent(ASN1Sequence paramASN1Sequence)
  {
    this.certReqId = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    this.checkAfter = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() > 2) {
      this.reason = PKIFreeText.getInstance(paramASN1Sequence.getObjectAt(2));
    }
  }
  
  public static PollRepContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof PollRepContent)) {
      return (PollRepContent)paramObject;
    }
    if (paramObject != null) {
      return new PollRepContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Integer getCertReqId()
  {
    return this.certReqId;
  }
  
  public ASN1Integer getCheckAfter()
  {
    return this.checkAfter;
  }
  
  public PKIFreeText getReason()
  {
    return this.reason;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certReqId);
    localASN1EncodableVector.add(this.checkAfter);
    if (this.reason != null) {
      localASN1EncodableVector.add(this.reason);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.PollRepContent
 * JD-Core Version:    0.7.0.1
 */