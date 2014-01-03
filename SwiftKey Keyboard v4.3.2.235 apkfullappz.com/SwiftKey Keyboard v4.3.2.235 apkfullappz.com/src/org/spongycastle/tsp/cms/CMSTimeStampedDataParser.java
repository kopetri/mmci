package org.spongycastle.tsp.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetStringParser;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.ContentInfoParser;
import org.spongycastle.asn1.cms.TimeStampedDataParser;
import org.spongycastle.cms.CMSContentInfoParser;
import org.spongycastle.cms.CMSException;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.tsp.TimeStampToken;
import org.spongycastle.util.io.Streams;

public class CMSTimeStampedDataParser
  extends CMSContentInfoParser
{
  private TimeStampedDataParser timeStampedData;
  private TimeStampDataUtil util;
  
  public CMSTimeStampedDataParser(InputStream paramInputStream)
    throws CMSException
  {
    super(paramInputStream);
    initialize(this._contentInfo);
  }
  
  public CMSTimeStampedDataParser(byte[] paramArrayOfByte)
    throws CMSException
  {
    this(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  private void initialize(ContentInfoParser paramContentInfoParser)
    throws CMSException
  {
    try
    {
      if (CMSObjectIdentifiers.timestampedData.equals(paramContentInfoParser.getContentType()))
      {
        this.timeStampedData = TimeStampedDataParser.getInstance(paramContentInfoParser.getContent(16));
        return;
      }
      throw new IllegalArgumentException("Malformed content - type must be " + CMSObjectIdentifiers.timestampedData.getId());
    }
    catch (IOException localIOException)
    {
      throw new CMSException("parsing exception: " + localIOException.getMessage(), localIOException);
    }
  }
  
  private void parseTimeStamps()
    throws CMSException
  {
    try
    {
      if (this.util == null)
      {
        InputStream localInputStream = getContent();
        if (localInputStream != null) {
          Streams.drain(localInputStream);
        }
        this.util = new TimeStampDataUtil(this.timeStampedData);
      }
      return;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("unable to parse evidence block: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public byte[] calculateNextHash(DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    return this.util.calculateNextHash(paramDigestCalculator);
  }
  
  public InputStream getContent()
  {
    if (this.timeStampedData.getContent() != null) {
      return this.timeStampedData.getContent().getOctetStream();
    }
    return null;
  }
  
  public URI getDataUri()
    throws URISyntaxException
  {
    DERIA5String localDERIA5String = this.timeStampedData.getDataUri();
    if (localDERIA5String != null) {
      return new URI(localDERIA5String.getString());
    }
    return null;
  }
  
  public String getFileName()
  {
    return this.util.getFileName();
  }
  
  public String getMediaType()
  {
    return this.util.getMediaType();
  }
  
  public DigestCalculator getMessageImprintDigestCalculator(DigestCalculatorProvider paramDigestCalculatorProvider)
    throws OperatorCreationException
  {
    try
    {
      parseTimeStamps();
      return this.util.getMessageImprintDigestCalculator(paramDigestCalculatorProvider);
    }
    catch (CMSException localCMSException)
    {
      throw new OperatorCreationException("unable to extract algorithm ID: " + localCMSException.getMessage(), localCMSException);
    }
  }
  
  public AttributeTable getOtherMetaData()
  {
    return this.util.getOtherMetaData();
  }
  
  public TimeStampToken[] getTimeStampTokens()
    throws CMSException
  {
    parseTimeStamps();
    return this.util.getTimeStampTokens();
  }
  
  public void initialiseMessageImprintDigestCalculator(DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    this.util.initialiseMessageImprintDigestCalculator(paramDigestCalculator);
  }
  
  public void validate(DigestCalculatorProvider paramDigestCalculatorProvider, byte[] paramArrayOfByte)
    throws ImprintDigestInvalidException, CMSException
  {
    parseTimeStamps();
    this.util.validate(paramDigestCalculatorProvider, paramArrayOfByte);
  }
  
  public void validate(DigestCalculatorProvider paramDigestCalculatorProvider, byte[] paramArrayOfByte, TimeStampToken paramTimeStampToken)
    throws ImprintDigestInvalidException, CMSException
  {
    parseTimeStamps();
    this.util.validate(paramDigestCalculatorProvider, paramArrayOfByte, paramTimeStampToken);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.cms.CMSTimeStampedDataParser
 * JD-Core Version:    0.7.0.1
 */