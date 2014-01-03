package org.spongycastle.openssl;

import java.io.IOException;

public class PEMException
  extends IOException
{
  Exception underlying;
  
  public PEMException(String paramString)
  {
    super(paramString);
  }
  
  public PEMException(String paramString, Exception paramException)
  {
    super(paramString);
    this.underlying = paramException;
  }
  
  public Throwable getCause()
  {
    return this.underlying;
  }
  
  public Exception getUnderlyingException()
  {
    return this.underlying;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.openssl.PEMException
 * JD-Core Version:    0.7.0.1
 */