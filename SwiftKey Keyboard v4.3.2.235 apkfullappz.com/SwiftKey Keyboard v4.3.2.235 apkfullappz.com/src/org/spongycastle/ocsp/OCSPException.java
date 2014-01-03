package org.spongycastle.ocsp;

public class OCSPException
  extends Exception
{
  Exception e;
  
  public OCSPException(String paramString)
  {
    super(paramString);
  }
  
  public OCSPException(String paramString, Exception paramException)
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
 * Qualified Name:     org.spongycastle.ocsp.OCSPException
 * JD-Core Version:    0.7.0.1
 */