package org.spongycastle.util;

public class StoreException
  extends RuntimeException
{
  private Throwable _e;
  
  public StoreException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this._e = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this._e;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.StoreException
 * JD-Core Version:    0.7.0.1
 */