package org.spongycastle.tsp.cms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.spongycastle.asn1.BEROctetString;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.Evidence;
import org.spongycastle.asn1.cms.TimeStampAndCRL;
import org.spongycastle.asn1.cms.TimeStampTokenEvidence;
import org.spongycastle.asn1.cms.TimeStampedData;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.CMSSignedData;
import org.spongycastle.tsp.TimeStampToken;
import org.spongycastle.util.io.Streams;

public class CMSTimeStampedDataGenerator
  extends CMSTimeStampedGenerator
{
  public CMSTimeStampedData generate(TimeStampToken paramTimeStampToken)
    throws CMSException
  {
    return generate(paramTimeStampToken, null);
  }
  
  public CMSTimeStampedData generate(TimeStampToken paramTimeStampToken, InputStream paramInputStream)
    throws CMSException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    if (paramInputStream != null) {}
    try
    {
      Streams.pipeAll(paramInputStream, localByteArrayOutputStream);
      int i = localByteArrayOutputStream.size();
      BEROctetString localBEROctetString = null;
      if (i != 0) {
        localBEROctetString = new BEROctetString(localByteArrayOutputStream.toByteArray());
      }
      TimeStampAndCRL localTimeStampAndCRL = new TimeStampAndCRL(paramTimeStampToken.toCMSSignedData().toASN1Structure());
      URI localURI = this.dataUri;
      DERIA5String localDERIA5String = null;
      if (localURI != null) {
        localDERIA5String = new DERIA5String(this.dataUri.toString());
      }
      return new CMSTimeStampedData(new ContentInfo(CMSObjectIdentifiers.timestampedData, new TimeStampedData(localDERIA5String, this.metaData, localBEROctetString, new Evidence(new TimeStampTokenEvidence(localTimeStampAndCRL)))));
    }
    catch (IOException localIOException)
    {
      throw new CMSException("exception encapsulating content: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public CMSTimeStampedData generate(TimeStampToken paramTimeStampToken, byte[] paramArrayOfByte)
    throws CMSException
  {
    return generate(paramTimeStampToken, new ByteArrayInputStream(paramArrayOfByte));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.cms.CMSTimeStampedDataGenerator
 * JD-Core Version:    0.7.0.1
 */