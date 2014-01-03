package org.spongycastle.asn1.tsp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cmp.PKIStatusInfo;
import org.spongycastle.asn1.cms.ContentInfo;

public class TimeStampResp
  extends ASN1Object
{
  PKIStatusInfo pkiStatusInfo;
  ContentInfo timeStampToken;
  
  private TimeStampResp(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.pkiStatusInfo = PKIStatusInfo.getInstance(localEnumeration.nextElement());
    if (localEnumeration.hasMoreElements()) {
      this.timeStampToken = ContentInfo.getInstance(localEnumeration.nextElement());
    }
  }
  
  public TimeStampResp(PKIStatusInfo paramPKIStatusInfo, ContentInfo paramContentInfo)
  {
    this.pkiStatusInfo = paramPKIStatusInfo;
    this.timeStampToken = paramContentInfo;
  }
  
  public static TimeStampResp getInstance(Object paramObject)
  {
    if ((paramObject instanceof TimeStampResp)) {
      return (TimeStampResp)paramObject;
    }
    if (paramObject != null) {
      return new TimeStampResp(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public PKIStatusInfo getStatus()
  {
    return this.pkiStatusInfo;
  }
  
  public ContentInfo getTimeStampToken()
  {
    return this.timeStampToken;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.pkiStatusInfo);
    if (this.timeStampToken != null) {
      localASN1EncodableVector.add(this.timeStampToken);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.tsp.TimeStampResp
 * JD-Core Version:    0.7.0.1
 */