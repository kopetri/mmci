package org.spongycastle.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class LocaleString
  extends LocalizedMessage
{
  public LocaleString(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public LocaleString(String paramString1, String paramString2, String paramString3)
    throws NullPointerException, UnsupportedEncodingException
  {
    super(paramString1, paramString2, paramString3);
  }
  
  public String getLocaleString(Locale paramLocale)
  {
    return getEntry(null, paramLocale, null);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.i18n.LocaleString
 * JD-Core Version:    0.7.0.1
 */