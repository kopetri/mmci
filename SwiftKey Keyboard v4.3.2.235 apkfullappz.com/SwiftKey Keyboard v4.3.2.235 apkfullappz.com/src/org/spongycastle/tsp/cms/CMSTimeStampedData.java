package org.spongycastle.tsp.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.Evidence;
import org.spongycastle.asn1.cms.TimeStampAndCRL;
import org.spongycastle.asn1.cms.TimeStampTokenEvidence;
import org.spongycastle.asn1.cms.TimeStampedData;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.CMSSignedData;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.tsp.TimeStampToken;

public class CMSTimeStampedData
{
  private ContentInfo contentInfo;
  private TimeStampedData timeStampedData;
  private TimeStampDataUtil util;
  
  public CMSTimeStampedData(InputStream paramInputStream)
    throws IOException
  {
    try
    {
      initialize(ContentInfo.getInstance(new ASN1InputStream(paramInputStream).readObject()));
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IOException("Malformed content: " + localClassCastException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new IOException("Malformed content: " + localIllegalArgumentException);
    }
  }
  
  public CMSTimeStampedData(ContentInfo paramContentInfo)
  {
    initialize(paramContentInfo);
  }
  
  public CMSTimeStampedData(byte[] paramArrayOfByte)
    throws IOException
  {
    this(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  private void initialize(ContentInfo paramContentInfo)
  {
    this.contentInfo = paramContentInfo;
    if (CMSObjectIdentifiers.timestampedData.equals(paramContentInfo.getContentType()))
    {
      this.timeStampedData = TimeStampedData.getInstance(paramContentInfo.getContent());
      this.util = new TimeStampDataUtil(this.timeStampedData);
      return;
    }
    throw new IllegalArgumentException("Malformed content - type must be " + CMSObjectIdentifiers.timestampedData.getId());
  }
  
  public CMSTimeStampedData addTimeStamp(TimeStampToken paramTimeStampToken)
    throws CMSException
  {
    TimeStampAndCRL[] arrayOfTimeStampAndCRL1 = this.util.getTimeStamps();
    TimeStampAndCRL[] arrayOfTimeStampAndCRL2 = new TimeStampAndCRL[1 + arrayOfTimeStampAndCRL1.length];
    System.arraycopy(arrayOfTimeStampAndCRL1, 0, arrayOfTimeStampAndCRL2, 0, arrayOfTimeStampAndCRL1.length);
    arrayOfTimeStampAndCRL2[arrayOfTimeStampAndCRL1.length] = new TimeStampAndCRL(paramTimeStampToken.toCMSSignedData().getContentInfo());
    return new CMSTimeStampedData(new ContentInfo(CMSObjectIdentifiers.timestampedData, new TimeStampedData(this.timeStampedData.getDataUri(), this.timeStampedData.getMetaData(), this.timeStampedData.getContent(), new Evidence(new TimeStampTokenEvidence(arrayOfTimeStampAndCRL2)))));
  }
  
  public byte[] calculateNextHash(DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    return this.util.calculateNextHash(paramDigestCalculator);
  }
  
  public byte[] getContent()
  {
    if (this.timeStampedData.getContent() != null) {
      return this.timeStampedData.getContent().getOctets();
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
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.contentInfo.getEncoded();
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
    return this.util.getMessageImprintDigestCalculator(paramDigestCalculatorProvider);
  }
  
  public AttributeTable getOtherMetaData()
  {
    return this.util.getOtherMetaData();
  }
  
  public TimeStampToken[] getTimeStampTokens()
    throws CMSException
  {
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
    this.util.validate(paramDigestCalculatorProvider, paramArrayOfByte);
  }
  
  public void validate(DigestCalculatorProvider paramDigestCalculatorProvider, byte[] paramArrayOfByte, TimeStampToken paramTimeStampToken)
    throws ImprintDigestInvalidException, CMSException
  {
    this.util.validate(paramDigestCalculatorProvider, paramArrayOfByte, paramTimeStampToken);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.cms.CMSTimeStampedData
 * JD-Core Version:    0.7.0.1
 */