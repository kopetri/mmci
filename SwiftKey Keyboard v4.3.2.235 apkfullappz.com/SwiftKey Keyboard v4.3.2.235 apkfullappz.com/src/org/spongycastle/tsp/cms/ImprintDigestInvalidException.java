package org.spongycastle.tsp.cms;

import org.spongycastle.tsp.TimeStampToken;

public class ImprintDigestInvalidException
  extends Exception
{
  private TimeStampToken token;
  
  public ImprintDigestInvalidException(String paramString, TimeStampToken paramTimeStampToken)
  {
    super(paramString);
    this.token = paramTimeStampToken;
  }
  
  public TimeStampToken getTimeStampToken()
  {
    return this.token;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.cms.ImprintDigestInvalidException
 * JD-Core Version:    0.7.0.1
 */