package org.spongycastle.i18n;

import java.util.Locale;

public class LocalizedException
  extends Exception
{
  private Throwable cause;
  protected ErrorBundle message;
  
  public LocalizedException(ErrorBundle paramErrorBundle)
  {
    super(paramErrorBundle.getText(Locale.getDefault()));
    this.message = paramErrorBundle;
  }
  
  public LocalizedException(ErrorBundle paramErrorBundle, Throwable paramThrowable)
  {
    super(paramErrorBundle.getText(Locale.getDefault()));
    this.message = paramErrorBundle;
    this.cause = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this.cause;
  }
  
  public ErrorBundle getErrorMessage()
  {
    return this.message;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.i18n.LocalizedException
 * JD-Core Version:    0.7.0.1
 */