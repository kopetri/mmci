package org.spongycastle.jce.provider;

import java.util.Date;

class CertStatus
{
  public static final int UNDETERMINED = 12;
  public static final int UNREVOKED = 11;
  int certStatus = 11;
  Date revocationDate = null;
  
  public int getCertStatus()
  {
    return this.certStatus;
  }
  
  public Date getRevocationDate()
  {
    return this.revocationDate;
  }
  
  public void setCertStatus(int paramInt)
  {
    this.certStatus = paramInt;
  }
  
  public void setRevocationDate(Date paramDate)
  {
    this.revocationDate = paramDate;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.CertStatus
 * JD-Core Version:    0.7.0.1
 */