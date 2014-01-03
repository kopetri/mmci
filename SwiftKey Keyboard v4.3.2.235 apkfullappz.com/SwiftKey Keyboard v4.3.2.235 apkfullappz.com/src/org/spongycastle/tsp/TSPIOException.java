package org.spongycastle.tsp;

import java.io.IOException;

public class TSPIOException
  extends IOException
{
  Throwable underlyingException;
  
  public TSPIOException(String paramString)
  {
    super(paramString);
  }
  
  public TSPIOException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.underlyingException = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this.underlyingException;
  }
  
  public Exception getUnderlyingException()
  {
    return (Exception)this.underlyingException;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TSPIOException
 * JD-Core Version:    0.7.0.1
 */