package org.spongycastle.tsp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.cmp.PKIFreeText;
import org.spongycastle.asn1.cmp.PKIStatusInfo;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.tsp.TimeStampResp;
import org.spongycastle.cms.CMSSignedData;

public class TimeStampResponseGenerator
{
  private Set acceptedAlgorithms;
  private Set acceptedExtensions;
  private Set acceptedPolicies;
  int failInfo;
  int status;
  ASN1EncodableVector statusStrings;
  private TimeStampTokenGenerator tokenGenerator;
  
  public TimeStampResponseGenerator(TimeStampTokenGenerator paramTimeStampTokenGenerator, Set paramSet)
  {
    this(paramTimeStampTokenGenerator, paramSet, null, null);
  }
  
  public TimeStampResponseGenerator(TimeStampTokenGenerator paramTimeStampTokenGenerator, Set paramSet1, Set paramSet2)
  {
    this(paramTimeStampTokenGenerator, paramSet1, paramSet2, null);
  }
  
  public TimeStampResponseGenerator(TimeStampTokenGenerator paramTimeStampTokenGenerator, Set paramSet1, Set paramSet2, Set paramSet3)
  {
    this.tokenGenerator = paramTimeStampTokenGenerator;
    this.acceptedAlgorithms = convert(paramSet1);
    this.acceptedPolicies = convert(paramSet2);
    this.acceptedExtensions = convert(paramSet3);
    this.statusStrings = new ASN1EncodableVector();
  }
  
  private void addStatusString(String paramString)
  {
    this.statusStrings.add(new DERUTF8String(paramString));
  }
  
  private Set convert(Set paramSet)
  {
    if (paramSet == null) {
      return paramSet;
    }
    HashSet localHashSet = new HashSet(paramSet.size());
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof String)) {
        localHashSet.add(new ASN1ObjectIdentifier((String)localObject));
      } else {
        localHashSet.add(localObject);
      }
    }
    return localHashSet;
  }
  
  private PKIStatusInfo getPKIStatusInfo()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new DERInteger(this.status));
    if (this.statusStrings.size() > 0) {
      localASN1EncodableVector.add(PKIFreeText.getInstance(new DERSequence(this.statusStrings)));
    }
    if (this.failInfo != 0) {
      localASN1EncodableVector.add(new FailInfo(this.failInfo));
    }
    return PKIStatusInfo.getInstance(new DERSequence(localASN1EncodableVector));
  }
  
  private void setFailInfoField(int paramInt)
  {
    this.failInfo = (paramInt | this.failInfo);
  }
  
  public TimeStampResponse generate(TimeStampRequest paramTimeStampRequest, BigInteger paramBigInteger, Date paramDate)
    throws TSPException
  {
    TimeStampResp localTimeStampResp;
    if (paramDate == null) {
      try
      {
        throw new TSPValidationException("The time source is not available.", 512);
      }
      catch (TSPValidationException localTSPValidationException)
      {
        this.status = 2;
        setFailInfoField(localTSPValidationException.getFailureCode());
        addStatusString(localTSPValidationException.getMessage());
        localTimeStampResp = new TimeStampResp(getPKIStatusInfo(), null);
      }
    }
    for (;;)
    {
      try
      {
        TimeStampResponse localTimeStampResponse = new TimeStampResponse(localTimeStampResp);
        return localTimeStampResponse;
      }
      catch (IOException localIOException2)
      {
        PKIStatusInfo localPKIStatusInfo;
        throw new TSPException("created badly formatted response!");
      }
      paramTimeStampRequest.validate(this.acceptedAlgorithms, this.acceptedPolicies, this.acceptedExtensions);
      this.status = 0;
      addStatusString("Operation Okay");
      localPKIStatusInfo = getPKIStatusInfo();
      try
      {
        ContentInfo localContentInfo = ContentInfo.getInstance(new ASN1InputStream(new ByteArrayInputStream(this.tokenGenerator.generate(paramTimeStampRequest, paramBigInteger, paramDate).toCMSSignedData().getEncoded())).readObject());
        localTimeStampResp = new TimeStampResp(localPKIStatusInfo, localContentInfo);
      }
      catch (IOException localIOException1)
      {
        throw new TSPException("Timestamp token received cannot be converted to ContentInfo", localIOException1);
      }
    }
  }
  
  public TimeStampResponse generate(TimeStampRequest paramTimeStampRequest, BigInteger paramBigInteger, Date paramDate, String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, TSPException
  {
    TimeStampResp localTimeStampResp;
    if (paramDate == null) {
      try
      {
        throw new TSPValidationException("The time source is not available.", 512);
      }
      catch (TSPValidationException localTSPValidationException)
      {
        this.status = 2;
        setFailInfoField(localTSPValidationException.getFailureCode());
        addStatusString(localTSPValidationException.getMessage());
        localTimeStampResp = new TimeStampResp(getPKIStatusInfo(), null);
      }
    }
    for (;;)
    {
      try
      {
        TimeStampResponse localTimeStampResponse = new TimeStampResponse(localTimeStampResp);
        return localTimeStampResponse;
      }
      catch (IOException localIOException2)
      {
        PKIStatusInfo localPKIStatusInfo;
        throw new TSPException("created badly formatted response!");
      }
      paramTimeStampRequest.validate(this.acceptedAlgorithms, this.acceptedPolicies, this.acceptedExtensions, paramString);
      this.status = 0;
      addStatusString("Operation Okay");
      localPKIStatusInfo = getPKIStatusInfo();
      try
      {
        ContentInfo localContentInfo = ContentInfo.getInstance(new ASN1InputStream(new ByteArrayInputStream(this.tokenGenerator.generate(paramTimeStampRequest, paramBigInteger, paramDate, paramString).toCMSSignedData().getEncoded())).readObject());
        localTimeStampResp = new TimeStampResp(localPKIStatusInfo, localContentInfo);
      }
      catch (IOException localIOException1)
      {
        throw new TSPException("Timestamp token received cannot be converted to ContentInfo", localIOException1);
      }
    }
  }
  
  public TimeStampResponse generateFailResponse(int paramInt1, int paramInt2, String paramString)
    throws TSPException
  {
    this.status = paramInt1;
    setFailInfoField(paramInt2);
    if (paramString != null) {
      addStatusString(paramString);
    }
    TimeStampResp localTimeStampResp = new TimeStampResp(getPKIStatusInfo(), null);
    try
    {
      TimeStampResponse localTimeStampResponse = new TimeStampResponse(localTimeStampResp);
      return localTimeStampResponse;
    }
    catch (IOException localIOException)
    {
      throw new TSPException("created badly formatted response!");
    }
  }
  
  class FailInfo
    extends DERBitString
  {
    FailInfo(int paramInt)
    {
      super(getPadBits(paramInt));
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TimeStampResponseGenerator
 * JD-Core Version:    0.7.0.1
 */