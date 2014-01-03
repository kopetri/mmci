package org.spongycastle.tsp;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.tsp.Accuracy;
import org.spongycastle.asn1.tsp.MessageImprint;
import org.spongycastle.asn1.tsp.TSTInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;

public class TimeStampTokenInfo
{
  Date genTime;
  TSTInfo tstInfo;
  
  TimeStampTokenInfo(TSTInfo paramTSTInfo)
    throws TSPException, IOException
  {
    this.tstInfo = paramTSTInfo;
    try
    {
      this.genTime = paramTSTInfo.getGenTime().getDate();
      return;
    }
    catch (ParseException localParseException)
    {
      throw new TSPException("unable to parse genTime field");
    }
  }
  
  public Accuracy getAccuracy()
  {
    return this.tstInfo.getAccuracy();
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.tstInfo.getEncoded();
  }
  
  public Date getGenTime()
  {
    return this.genTime;
  }
  
  public GenTimeAccuracy getGenTimeAccuracy()
  {
    if (getAccuracy() != null) {
      return new GenTimeAccuracy(getAccuracy());
    }
    return null;
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    return this.tstInfo.getMessageImprint().getHashAlgorithm();
  }
  
  public ASN1ObjectIdentifier getMessageImprintAlgOID()
  {
    return this.tstInfo.getMessageImprint().getHashAlgorithm().getAlgorithm();
  }
  
  public byte[] getMessageImprintDigest()
  {
    return this.tstInfo.getMessageImprint().getHashedMessage();
  }
  
  public BigInteger getNonce()
  {
    if (this.tstInfo.getNonce() != null) {
      return this.tstInfo.getNonce().getValue();
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getPolicy()
  {
    return this.tstInfo.getPolicy();
  }
  
  public BigInteger getSerialNumber()
  {
    return this.tstInfo.getSerialNumber().getValue();
  }
  
  public GeneralName getTsa()
  {
    return this.tstInfo.getTsa();
  }
  
  public boolean isOrdered()
  {
    return this.tstInfo.getOrdering().isTrue();
  }
  
  public TSTInfo toASN1Structure()
  {
    return this.tstInfo;
  }
  
  public TSTInfo toTSTInfo()
  {
    return this.tstInfo;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TimeStampTokenInfo
 * JD-Core Version:    0.7.0.1
 */