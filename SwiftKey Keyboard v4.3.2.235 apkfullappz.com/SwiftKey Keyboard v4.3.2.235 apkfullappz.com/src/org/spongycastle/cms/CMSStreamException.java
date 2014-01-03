package org.spongycastle.cms;

import java.io.IOException;

public class CMSStreamException
  extends IOException
{
  private final Throwable underlying;
  
  CMSStreamException(String paramString)
  {
    super(paramString);
    this.underlying = null;
  }
  
  CMSStreamException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.underlying = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this.underlying;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSStreamException
 * JD-Core Version:    0.7.0.1
 */