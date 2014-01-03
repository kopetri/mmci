package org.spongycastle.tsp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.asn1.cmp.PKIFreeText;
import org.spongycastle.asn1.cmp.PKIStatusInfo;
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.tsp.TimeStampResp;
import org.spongycastle.util.Arrays;

public class TimeStampResponse
{
  TimeStampResp resp;
  TimeStampToken timeStampToken;
  
  public TimeStampResponse(InputStream paramInputStream)
    throws TSPException, IOException
  {
    this(readTimeStampResp(paramInputStream));
  }
  
  public TimeStampResponse(TimeStampResp paramTimeStampResp)
    throws TSPException, IOException
  {
    this.resp = paramTimeStampResp;
    if (paramTimeStampResp.getTimeStampToken() != null) {
      this.timeStampToken = new TimeStampToken(paramTimeStampResp.getTimeStampToken());
    }
  }
  
  public TimeStampResponse(byte[] paramArrayOfByte)
    throws TSPException, IOException
  {
    this(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  private static TimeStampResp readTimeStampResp(InputStream paramInputStream)
    throws IOException, TSPException
  {
    try
    {
      TimeStampResp localTimeStampResp = TimeStampResp.getInstance(new ASN1InputStream(paramInputStream).readObject());
      return localTimeStampResp;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new TSPException("malformed timestamp response: " + localIllegalArgumentException, localIllegalArgumentException);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new TSPException("malformed timestamp response: " + localClassCastException, localClassCastException);
    }
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.resp.getEncoded();
  }
  
  public PKIFailureInfo getFailInfo()
  {
    if (this.resp.getStatus().getFailInfo() != null) {
      return new PKIFailureInfo(this.resp.getStatus().getFailInfo());
    }
    return null;
  }
  
  public int getStatus()
  {
    return this.resp.getStatus().getStatus().intValue();
  }
  
  public String getStatusString()
  {
    if (this.resp.getStatus().getStatusString() != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      PKIFreeText localPKIFreeText = this.resp.getStatus().getStatusString();
      for (int i = 0; i != localPKIFreeText.size(); i++) {
        localStringBuffer.append(localPKIFreeText.getStringAt(i).getString());
      }
      return localStringBuffer.toString();
    }
    return null;
  }
  
  public TimeStampToken getTimeStampToken()
  {
    return this.timeStampToken;
  }
  
  public void validate(TimeStampRequest paramTimeStampRequest)
    throws TSPException
  {
    TimeStampToken localTimeStampToken = getTimeStampToken();
    if (localTimeStampToken != null)
    {
      TimeStampTokenInfo localTimeStampTokenInfo = localTimeStampToken.getTimeStampInfo();
      if ((paramTimeStampRequest.getNonce() != null) && (!paramTimeStampRequest.getNonce().equals(localTimeStampTokenInfo.getNonce()))) {
        throw new TSPValidationException("response contains wrong nonce value.");
      }
      if ((getStatus() != 0) && (getStatus() != 1)) {
        throw new TSPValidationException("time stamp token found in failed request.");
      }
      if (!Arrays.constantTimeAreEqual(paramTimeStampRequest.getMessageImprintDigest(), localTimeStampTokenInfo.getMessageImprintDigest())) {
        throw new TSPValidationException("response for different message imprint digest.");
      }
      if (!localTimeStampTokenInfo.getMessageImprintAlgOID().equals(paramTimeStampRequest.getMessageImprintAlgOID())) {
        throw new TSPValidationException("response for different message imprint algorithm.");
      }
      Attribute localAttribute1 = localTimeStampToken.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificate);
      Attribute localAttribute2 = localTimeStampToken.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
      if ((localAttribute1 == null) && (localAttribute2 == null)) {
        throw new TSPValidationException("no signing certificate attribute present.");
      }
      if ((paramTimeStampRequest.getReqPolicy() != null) && (!paramTimeStampRequest.getReqPolicy().equals(localTimeStampTokenInfo.getPolicy()))) {
        throw new TSPValidationException("TSA policy wrong for request.");
      }
    }
    else if ((getStatus() == 0) || (getStatus() == 1))
    {
      throw new TSPValidationException("no time stamp token found and one expected.");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TimeStampResponse
 * JD-Core Version:    0.7.0.1
 */