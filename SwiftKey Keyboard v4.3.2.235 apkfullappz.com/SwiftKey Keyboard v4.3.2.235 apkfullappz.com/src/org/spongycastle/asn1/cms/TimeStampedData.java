package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERIA5String;

public class TimeStampedData
  extends ASN1Object
{
  private ASN1OctetString content;
  private DERIA5String dataUri;
  private MetaData metaData;
  private Evidence temporalEvidence;
  private ASN1Integer version;
  
  private TimeStampedData(ASN1Sequence paramASN1Sequence)
  {
    this.version = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    int i = 1;
    if ((paramASN1Sequence.getObjectAt(1) instanceof DERIA5String))
    {
      i++;
      this.dataUri = DERIA5String.getInstance(paramASN1Sequence.getObjectAt(1));
    }
    if (((paramASN1Sequence.getObjectAt(i) instanceof MetaData)) || ((paramASN1Sequence.getObjectAt(i) instanceof ASN1Sequence)))
    {
      int j = i + 1;
      this.metaData = MetaData.getInstance(paramASN1Sequence.getObjectAt(i));
      i = j;
    }
    if ((paramASN1Sequence.getObjectAt(i) instanceof ASN1OctetString))
    {
      int k = i + 1;
      this.content = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(i));
      i = k;
    }
    this.temporalEvidence = Evidence.getInstance(paramASN1Sequence.getObjectAt(i));
  }
  
  public TimeStampedData(DERIA5String paramDERIA5String, MetaData paramMetaData, ASN1OctetString paramASN1OctetString, Evidence paramEvidence)
  {
    this.version = new ASN1Integer(1);
    this.dataUri = paramDERIA5String;
    this.metaData = paramMetaData;
    this.content = paramASN1OctetString;
    this.temporalEvidence = paramEvidence;
  }
  
  public static TimeStampedData getInstance(Object paramObject)
  {
    if ((paramObject instanceof TimeStampedData)) {
      return (TimeStampedData)paramObject;
    }
    if (paramObject != null) {
      return new TimeStampedData(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1OctetString getContent()
  {
    return this.content;
  }
  
  public DERIA5String getDataUri()
  {
    return this.dataUri;
  }
  
  public MetaData getMetaData()
  {
    return this.metaData;
  }
  
  public Evidence getTemporalEvidence()
  {
    return this.temporalEvidence;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    if (this.dataUri != null) {
      localASN1EncodableVector.add(this.dataUri);
    }
    if (this.metaData != null) {
      localASN1EncodableVector.add(this.metaData);
    }
    if (this.content != null) {
      localASN1EncodableVector.add(this.content);
    }
    localASN1EncodableVector.add(this.temporalEvidence);
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.TimeStampedData
 * JD-Core Version:    0.7.0.1
 */