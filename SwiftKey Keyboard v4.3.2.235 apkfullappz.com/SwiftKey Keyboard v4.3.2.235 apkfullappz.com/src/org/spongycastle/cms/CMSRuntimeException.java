package org.spongycastle.cms;

public class CMSRuntimeException
  extends RuntimeException
{
  Exception e;
  
  public CMSRuntimeException(String paramString)
  {
    super(paramString);
  }
  
  public CMSRuntimeException(String paramString, Exception paramException)
  {
    super(paramString);
    this.e = paramException;
  }
  
  public Throwable getCause()
  {
    return this.e;
  }
  
  public Exception getUnderlyingException()
  {
    return this.e;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSRuntimeException
 * JD-Core Version:    0.7.0.1
 */