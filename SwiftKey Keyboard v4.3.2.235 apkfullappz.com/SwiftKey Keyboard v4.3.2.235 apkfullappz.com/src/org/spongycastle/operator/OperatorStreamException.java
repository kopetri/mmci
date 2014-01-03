package org.spongycastle.operator;

import java.io.IOException;

public class OperatorStreamException
  extends IOException
{
  private Throwable cause;
  
  public OperatorStreamException(String paramString, Throwable paramThrowable)
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
 * Qualified Name:     org.spongycastle.operator.OperatorStreamException
 * JD-Core Version:    0.7.0.1
 */