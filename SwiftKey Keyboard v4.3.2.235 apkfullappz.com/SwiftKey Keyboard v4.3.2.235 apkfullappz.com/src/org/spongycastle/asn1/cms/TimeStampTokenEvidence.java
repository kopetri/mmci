package org.spongycastle.asn1.cms;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class TimeStampTokenEvidence
  extends ASN1Object
{
  private TimeStampAndCRL[] timeStampAndCRLs;
  
  private TimeStampTokenEvidence(ASN1Sequence paramASN1Sequence)
  {
    this.timeStampAndCRLs = new TimeStampAndCRL[paramASN1Sequence.size()];
    int i = 0;
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      TimeStampAndCRL[] arrayOfTimeStampAndCRL = this.timeStampAndCRLs;
      int j = i + 1;
      arrayOfTimeStampAndCRL[i] = TimeStampAndCRL.getInstance(localEnumeration.nextElement());
      i = j;
    }
  }
  
  public TimeStampTokenEvidence(TimeStampAndCRL paramTimeStampAndCRL)
  {
    this.timeStampAndCRLs = new TimeStampAndCRL[1];
    this.timeStampAndCRLs[0] = paramTimeStampAndCRL;
  }
  
  public TimeStampTokenEvidence(TimeStampAndCRL[] paramArrayOfTimeStampAndCRL)
  {
    this.timeStampAndCRLs = paramArrayOfTimeStampAndCRL;
  }
  
  public static TimeStampTokenEvidence getInstance(Object paramObject)
  {
    if ((paramObject instanceof TimeStampTokenEvidence)) {
      return (TimeStampTokenEvidence)paramObject;
    }
    if (paramObject != null) {
      return new TimeStampTokenEvidence(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static TimeStampTokenEvidence getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i != this.timeStampAndCRLs.length; i++) {
      localASN1EncodableVector.add(this.timeStampAndCRLs[i]);
    }
    return new DERSequence(localASN1EncodableVector);
  }
  
  public TimeStampAndCRL[] toTimeStampAndCRLArray()
  {
    return this.timeStampAndCRLs;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.TimeStampTokenEvidence
 * JD-Core Version:    0.7.0.1
 */