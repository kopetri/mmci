package com.touchtype.sync.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

abstract class c
  extends j
{
  c(LoggingListener paramLoggingListener)
  {
    super(paramLoggingListener);
  }
  
  final void a(InputStream paramInputStream, String paramString, int paramInt, Map<String, List<String>> paramMap)
  {
    try
    {
      String str = a(paramInputStream);
      if ((paramString != null) && (paramString.startsWith("application/json")))
      {
        a(str);
        return;
      }
      a(paramMap);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      e.c(this.d, "com.touchtype.sync.client", "Error handling sync json result: " + localUnsupportedEncodingException.getMessage());
      a();
      return;
    }
    catch (IOException localIOException)
    {
      e.c(this.d, "com.touchtype.sync.client", "Error handling sync json result: " + localIOException.getMessage());
      a();
    }
  }
  
  abstract void a(String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.c
 * JD-Core Version:    0.7.0.1
 */