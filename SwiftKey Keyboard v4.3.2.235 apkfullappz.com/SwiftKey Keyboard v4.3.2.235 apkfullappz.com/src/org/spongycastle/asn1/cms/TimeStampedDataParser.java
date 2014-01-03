package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetStringParser;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERIA5String;

public class TimeStampedDataParser
{
  private ASN1OctetStringParser content;
  private DERIA5String dataUri;
  private MetaData metaData;
  private ASN1SequenceParser parser;
  private Evidence temporalEvidence;
  private ASN1Integer version;
  
  private TimeStampedDataParser(ASN1SequenceParser paramASN1SequenceParser)
    throws IOException
  {
    this.parser = paramASN1SequenceParser;
    this.version = ASN1Integer.getInstance(paramASN1SequenceParser.readObject());
    ASN1Encodable localASN1Encodable = paramASN1SequenceParser.readObject();
    if ((localASN1Encodable instanceof DERIA5String))
    {
      this.dataUri = DERIA5String.getInstance(localASN1Encodable);
      localASN1Encodable = paramASN1SequenceParser.readObject();
    }
    if (((localASN1Encodable instanceof MetaData)) || ((localASN1Encodable instanceof ASN1SequenceParser)))
    {
      this.metaData = MetaData.getInstance(localASN1Encodable.toASN1Primitive());
      localASN1Encodable = paramASN1SequenceParser.readObject();
    }
    if ((localASN1Encodable instanceof ASN1OctetStringParser)) {
      this.content = ((ASN1OctetStringParser)localASN1Encodable);
    }
  }
  
  public static TimeStampedDataParser getInstance(Object paramObject)
    throws IOException
  {
    if ((paramObject instanceof ASN1Sequence)) {
      return new TimeStampedDataParser(((ASN1Sequence)paramObject).parser());
    }
    if ((paramObject instanceof ASN1SequenceParser)) {
      return new TimeStampedDataParser((ASN1SequenceParser)paramObject);
    }
    return null;
  }
  
  public ASN1OctetStringParser getContent()
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
    throws IOException
  {
    if (this.temporalEvidence == null) {
      this.temporalEvidence = Evidence.getInstance(this.parser.readObject().toASN1Primitive());
    }
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
 * Qualified Name:     org.spongycastle.asn1.cms.TimeStampedDataParser
 * JD-Core Version:    0.7.0.1
 */