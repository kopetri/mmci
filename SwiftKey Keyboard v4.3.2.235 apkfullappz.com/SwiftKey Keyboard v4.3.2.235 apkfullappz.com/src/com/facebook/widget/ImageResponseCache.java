package com.facebook.widget;

import android.content.Context;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.FileLruCache.Limits;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class ImageResponseCache
{
  static final String TAG = ImageResponseCache.class.getSimpleName();
  private static volatile FileLruCache imageCache;
  
  static FileLruCache getCache(Context paramContext)
    throws IOException
  {
    try
    {
      if (imageCache == null) {
        imageCache = new FileLruCache(paramContext.getApplicationContext(), TAG, new FileLruCache.Limits());
      }
      FileLruCache localFileLruCache = imageCache;
      return localFileLruCache;
    }
    finally {}
  }
  
  static InputStream getCachedImageStream(URL paramURL, Context paramContext)
  {
    Object localObject = null;
    if (paramURL != null)
    {
      boolean bool = isCDNURL(paramURL);
      localObject = null;
      if (!bool) {}
    }
    try
    {
      InputStream localInputStream = getCache(paramContext).get(paramURL.toString());
      localObject = localInputStream;
      return localObject;
    }
    catch (IOException localIOException)
    {
      Logger.log(LoggingBehavior.CACHE, 5, TAG, localIOException.toString());
    }
    return null;
  }
  
  static InputStream interceptAndCacheImageStream(Context paramContext, HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    int i = paramHttpURLConnection.getResponseCode();
    Object localObject = null;
    URL localURL;
    if (i == 200)
    {
      localURL = paramHttpURLConnection.getURL();
      localObject = paramHttpURLConnection.getInputStream();
      if (!isCDNURL(localURL)) {}
    }
    try
    {
      InputStream localInputStream = getCache(paramContext).interceptAndPut(localURL.toString(), new BufferedHttpInputStream((InputStream)localObject, paramHttpURLConnection));
      localObject = localInputStream;
      return localObject;
    }
    catch (IOException localIOException) {}
    return localObject;
  }
  
  private static boolean isCDNURL(URL paramURL)
  {
    if (paramURL != null)
    {
      String str = paramURL.getHost();
      if (str.endsWith("fbcdn.net")) {}
      while ((str.startsWith("fbcdn")) && (str.endsWith("akamaihd.net"))) {
        return true;
      }
    }
    return false;
  }
  
  private static class BufferedHttpInputStream
    extends BufferedInputStream
  {
    HttpURLConnection connection;
    
    BufferedHttpInputStream(InputStream paramInputStream, HttpURLConnection paramHttpURLConnection)
    {
      super(8192);
      this.connection = paramHttpURLConnection;
    }
    
    public void close()
      throws IOException
    {
      super.close();
      Utility.disconnectQuietly(this.connection);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.widget.ImageResponseCache
 * JD-Core Version:    0.7.0.1
 */