package org.spongycastle.cert.ocsp;

import java.math.BigInteger;
import java.util.Date;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ocsp.ResponseData;
import org.spongycastle.asn1.ocsp.SingleResponse;
import org.spongycastle.asn1.x509.Extensions;

public class RespData
{
  private ResponseData data;
  
  public RespData(ResponseData paramResponseData)
  {
    this.data = paramResponseData;
  }
  
  public Date getProducedAt()
  {
    return OCSPUtils.extractDate(this.data.getProducedAt());
  }
  
  public RespID getResponderId()
  {
    return new RespID(this.data.getResponderID());
  }
  
  public Extensions getResponseExtensions()
  {
    return this.data.getResponseExtensions();
  }
  
  public SingleResp[] getResponses()
  {
    ASN1Sequence localASN1Sequence = this.data.getResponses();
    SingleResp[] arrayOfSingleResp = new SingleResp[localASN1Sequence.size()];
    for (int i = 0; i != arrayOfSingleResp.length; i++) {
      arrayOfSingleResp[i] = new SingleResp(SingleResponse.getInstance(localASN1Sequence.getObjectAt(i)));
    }
    return arrayOfSingleResp;
  }
  
  public int getVersion()
  {
    return 1 + this.data.getVersion().getValue().intValue();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.RespData
 * JD-Core Version:    0.7.0.1
 */