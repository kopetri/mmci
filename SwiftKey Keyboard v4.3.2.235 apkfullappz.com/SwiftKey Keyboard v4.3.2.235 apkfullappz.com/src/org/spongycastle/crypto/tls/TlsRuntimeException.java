package org.spongycastle.crypto.tls;

public class TlsRuntimeException
  extends RuntimeException
{
  private static final long serialVersionUID = 1928023487348344086L;
  Throwable e;
  
  public TlsRuntimeException(String paramString)
  {
    super(paramString);
  }
  
  public TlsRuntimeException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.e = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this.e;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsRuntimeException
 * JD-Core Version:    0.7.0.1
 */