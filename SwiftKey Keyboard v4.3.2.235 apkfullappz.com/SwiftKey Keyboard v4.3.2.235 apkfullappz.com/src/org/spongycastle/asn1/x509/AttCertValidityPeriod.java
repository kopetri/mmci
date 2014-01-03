package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;

public class AttCertValidityPeriod
  extends ASN1Object
{
  DERGeneralizedTime notAfterTime;
  DERGeneralizedTime notBeforeTime;
  
  private AttCertValidityPeriod(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.notBeforeTime = DERGeneralizedTime.getInstance(paramASN1Sequence.getObjectAt(0));
    this.notAfterTime = DERGeneralizedTime.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public AttCertValidityPeriod(DERGeneralizedTime paramDERGeneralizedTime1, DERGeneralizedTime paramDERGeneralizedTime2)
  {
    this.notBeforeTime = paramDERGeneralizedTime1;
    this.notAfterTime = paramDERGeneralizedTime2;
  }
  
  public static AttCertValidityPeriod getInstance(Object paramObject)
  {
    if ((paramObject instanceof AttCertValidityPeriod)) {
      return (AttCertValidityPeriod)paramObject;
    }
    if (paramObject != null) {
      return new AttCertValidityPeriod(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public DERGeneralizedTime getNotAfterTime()
  {
    return this.notAfterTime;
  }
  
  public DERGeneralizedTime getNotBeforeTime()
  {
    return this.notBeforeTime;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.notBeforeTime);
    localASN1EncodableVector.add(this.notAfterTime);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.AttCertValidityPeriod
 * JD-Core Version:    0.7.0.1
 */