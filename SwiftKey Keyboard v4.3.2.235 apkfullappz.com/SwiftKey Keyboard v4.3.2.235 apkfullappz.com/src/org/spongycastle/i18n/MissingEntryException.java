package org.spongycastle.i18n;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

public class MissingEntryException
  extends RuntimeException
{
  private String debugMsg;
  protected final String key;
  protected final ClassLoader loader;
  protected final Locale locale;
  protected final String resource;
  
  public MissingEntryException(String paramString1, String paramString2, String paramString3, Locale paramLocale, ClassLoader paramClassLoader)
  {
    super(paramString1);
    this.resource = paramString2;
    this.key = paramString3;
    this.locale = paramLocale;
    this.loader = paramClassLoader;
  }
  
  public MissingEntryException(String paramString1, Throwable paramThrowable, String paramString2, String paramString3, Locale paramLocale, ClassLoader paramClassLoader)
  {
    super(paramString1, paramThrowable);
    this.resource = paramString2;
    this.key = paramString3;
    this.locale = paramLocale;
    this.loader = paramClassLoader;
  }
  
  public ClassLoader getClassLoader()
  {
    return this.loader;
  }
  
  public String getDebugMsg()
  {
    if (this.debugMsg == null)
    {
      this.debugMsg = ("Can not find entry " + this.key + " in resource file " + this.resource + " for the locale " + this.locale + ".");
      if ((this.loader instanceof URLClassLoader))
      {
        URL[] arrayOfURL = ((URLClassLoader)this.loader).getURLs();
        this.debugMsg += " The following entries in the classpath were searched: ";
        for (int i = 0; i != arrayOfURL.length; i++) {
          this.debugMsg = (this.debugMsg + arrayOfURL[i] + " ");
        }
      }
    }
    return this.debugMsg;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public Locale getLocale()
  {
    return this.locale;
  }
  
  public String getResource()
  {
    return this.resource;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.i18n.MissingEntryException
 * JD-Core Version:    0.7.0.1
 */