package com.touchtype.sync.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

abstract class j
{
  protected final LoggingListener d;
  
  j(LoggingListener paramLoggingListener)
  {
    this.d = paramLoggingListener;
  }
  
  static String a(InputStream paramInputStream)
    throws UnsupportedEncodingException, IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, "UTF-8"));
    StringBuilder localStringBuilder = new StringBuilder();
    for (;;)
    {
      String str = localBufferedReader.readLine();
      if (str == null) {
        break;
      }
      localStringBuilder.append(str).append("\n");
    }
    return localStringBuilder.toString();
  }
  
  private void a(URL paramURL, InputStream paramInputStream, String paramString, int paramInt, Map<String, List<String>> paramMap)
    throws IOException
  {
    try
    {
      String str = a(paramInputStream);
      if (paramString.startsWith("application/json"))
      {
        e.c(this.d, "com.touchtype.sync.client", "Connection to Sync Server at " + paramURL.toString() + " returned json response : " + str);
        a(paramInt, str);
        return;
      }
      a(paramInt, null);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      e.c(this.d, "com.touchtype.sync.client", "Error handling sync json error response: " + localUnsupportedEncodingException.getMessage());
      a();
    }
  }
  
  private void b(InputStream paramInputStream)
  {
    if (paramInputStream != null) {}
    try
    {
      paramInputStream.close();
      return;
    }
    catch (IOException localIOException)
    {
      e.c(this.d, "com.touchtype.sync.client", "Error when closing input stream: " + localIOException.getMessage());
    }
  }
  
  abstract void a();
  
  abstract void a(int paramInt, String paramString);
  
  void a(InputStream paramInputStream, String paramString, int paramInt, Map<String, List<String>> paramMap)
  {
    a(paramMap);
  }
  
  protected final void a(InputStream paramInputStream, String paramString, int paramInt, Map<String, List<String>> paramMap, URL paramURL)
  {
    if ((paramInt != 200) || (paramInputStream != null)) {}
    for (;;)
    {
      try
      {
        a(paramInputStream, paramString, paramInt, null);
        return;
        a(null);
        continue;
      }
      catch (IOException localIOException)
      {
        e.c(this.d, "com.touchtype.sync.client", "Error handling sync response: " + localIOException.getMessage());
        a();
        return;
        if (paramInt == 204)
        {
          a(null);
          continue;
        }
      }
      finally
      {
        b(paramInputStream);
      }
      if (paramInt == 400)
      {
        if (paramInputStream != null) {
          a(paramURL, paramInputStream, paramString, paramInt, null);
        } else {
          a(paramInt, null);
        }
      }
      else
      {
        e.c(this.d, "com.touchtype.sync.client", "Connection to Sync server at " + paramURL.toString() + " returned error code: " + paramInt);
        a(paramInt, null);
      }
    }
  }
  
  protected final void a(HttpURLConnection paramHttpURLConnection)
  {
    Object localObject1 = null;
    for (;;)
    {
      int i;
      Map localMap;
      try
      {
        i = paramHttpURLConnection.getResponseCode();
        localMap = Collections.unmodifiableMap(paramHttpURLConnection.getHeaderFields());
        localObject1 = null;
        if (i != 200) {
          continue;
        }
        String str = paramHttpURLConnection.getContentEncoding();
        if (str == null) {
          continue;
        }
        boolean bool = str.startsWith("gzip");
        localObject1 = null;
        if (!bool) {
          continue;
        }
        localObject1 = new GZIPInputStream(paramHttpURLConnection.getInputStream());
        if (localObject1 == null) {
          continue;
        }
        a((InputStream)localObject1, paramHttpURLConnection.getContentType(), i, localMap);
      }
      catch (IOException localIOException)
      {
        e.c(this.d, "com.touchtype.sync.client", "Error handling sync response: " + localIOException.getMessage());
        a();
        return;
        if (i != 204) {
          break label174;
        }
        a(localMap);
        localObject1 = null;
        continue;
      }
      finally
      {
        b((InputStream)localObject1);
      }
      b((InputStream)localObject1);
      return;
      localObject1 = paramHttpURLConnection.getInputStream();
      continue;
      a(localMap);
      continue;
      label174:
      if (i == 400)
      {
        localObject1 = paramHttpURLConnection.getErrorStream();
        if (localObject1 != null) {
          a(paramHttpURLConnection.getURL(), (InputStream)localObject1, paramHttpURLConnection.getContentType(), i, localMap);
        } else {
          a(i, null);
        }
      }
      else
      {
        e.c(this.d, "com.touchtype.sync.client", "Connection to Sync server at " + paramHttpURLConnection.getURL().toString() + " returned error code: " + paramHttpURLConnection.getResponseCode());
        a(i, null);
        localObject1 = null;
      }
    }
  }
  
  abstract void a(Map<String, List<String>> paramMap);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.j
 * JD-Core Version:    0.7.0.1
 */