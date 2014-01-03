package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.CertificateList;

public class TimeStampAndCRL
  extends ASN1Object
{
  private CertificateList crl;
  private ContentInfo timeStamp;
  
  private TimeStampAndCRL(ASN1Sequence paramASN1Sequence)
  {
    this.timeStamp = ContentInfo.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() == 2) {
      this.crl = CertificateList.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public TimeStampAndCRL(ContentInfo paramContentInfo)
  {
    this.timeStamp = paramContentInfo;
  }
  
  public static TimeStampAndCRL getInstance(Object paramObject)
  {
    if ((paramObject instanceof TimeStampAndCRL)) {
      return (TimeStampAndCRL)paramObject;
    }
    if (paramObject != null) {
      return new TimeStampAndCRL(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CertificateList getCRL()
  {
    return this.crl;
  }
  
  public CertificateList getCertificateList()
  {
    return this.crl;
  }
  
  public ContentInfo getTimeStampToken()
  {
    return this.timeStamp;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.timeStamp);
    if (this.crl != null) {
      localASN1EncodableVector.add(this.crl);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.TimeStampAndCRL
 * JD-Core Version:    0.7.0.1
 */