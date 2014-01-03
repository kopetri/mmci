package org.spongycastle.cert.ocsp;

public class OCSPException
  extends Exception
{
  private Throwable cause;
  
  public OCSPException(String paramString)
  {
    super(paramString);
  }
  
  public OCSPException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.cause = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this.cause;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.OCSPException
 * JD-Core Version:    0.7.0.1
 */