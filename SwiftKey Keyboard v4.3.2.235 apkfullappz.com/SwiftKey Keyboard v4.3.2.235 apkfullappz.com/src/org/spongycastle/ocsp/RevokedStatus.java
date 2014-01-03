package org.spongycastle.ocsp;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.ocsp.RevokedInfo;
import org.spongycastle.asn1.x509.CRLReason;

public class RevokedStatus
  implements CertificateStatus
{
  RevokedInfo info;
  
  public RevokedStatus(Date paramDate, int paramInt)
  {
    this.info = new RevokedInfo(new DERGeneralizedTime(paramDate), CRLReason.lookup(paramInt));
  }
  
  public RevokedStatus(RevokedInfo paramRevokedInfo)
  {
    this.info = paramRevokedInfo;
  }
  
  public int getRevocationReason()
  {
    if (this.info.getRevocationReason() == null) {
      throw new IllegalStateException("attempt to get a reason where none is available");
    }
    return this.info.getRevocationReason().getValue().intValue();
  }
  
  public Date getRevocationTime()
  {
    try
    {
      Date localDate = this.info.getRevocationTime().getDate();
      return localDate;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException("ParseException:" + localParseException.getMessage());
    }
  }
  
  public boolean hasRevocationReason()
  {
    return this.info.getRevocationReason() != null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.ocsp.RevokedStatus
 * JD-Core Version:    0.7.0.1
 */