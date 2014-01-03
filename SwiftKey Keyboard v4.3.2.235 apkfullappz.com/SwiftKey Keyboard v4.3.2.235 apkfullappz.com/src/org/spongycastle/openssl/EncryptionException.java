package org.spongycastle.openssl;

import java.io.IOException;

public class EncryptionException
  extends IOException
{
  private Throwable cause;
  
  public EncryptionException(String paramString)
  {
    super(paramString);
  }
  
  public EncryptionException(String paramString, Throwable paramThrowable)
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
 * Qualified Name:     org.spongycastle.openssl.EncryptionException
 * JD-Core Version:    0.7.0.1
 */