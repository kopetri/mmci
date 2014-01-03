package org.spongycastle.jce.provider;

import org.spongycastle.jce.exception.ExtException;

public class AnnotatedException
  extends Exception
  implements ExtException
{
  private Throwable _underlyingException;
  
  AnnotatedException(String paramString)
  {
    this(paramString, null);
  }
  
  AnnotatedException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this._underlyingException = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this._underlyingException;
  }
  
  Throwable getUnderlyingException()
  {
    return this._underlyingException;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.AnnotatedException
 * JD-Core Version:    0.7.0.1
 */