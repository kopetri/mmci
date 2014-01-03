package org.spongycastle.cert.ocsp;

import java.math.BigInteger;
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
    return OCSPUtils.extractDate(this.info.getRevocationTime());
  }
  
  public boolean hasRevocationReason()
  {
    return this.info.getRevocationReason() != null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.RevokedStatus
 * JD-Core Version:    0.7.0.1
 */