package com.facebook.widget;

import android.content.Context;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.FileLruCache.Limits;
import java.io.IOException;

class UrlRedirectCache
{
  private static final String REDIRECT_CONTENT_TAG = TAG + "_Redirect";
  static final String TAG = UrlRedirectCache.class.getSimpleName();
  private static volatile FileLruCache urlRedirectCache;
  
  /* Error */
  static void cacheUrlRedirect(Context paramContext, java.net.URL paramURL1, java.net.URL paramURL2)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +7 -> 8
    //   4: aload_2
    //   5: ifnonnull +4 -> 9
    //   8: return
    //   9: aconst_null
    //   10: astore_3
    //   11: aload_0
    //   12: invokestatic 44	com/facebook/widget/UrlRedirectCache:getCache	(Landroid/content/Context;)Lcom/facebook/internal/FileLruCache;
    //   15: aload_1
    //   16: invokevirtual 47	java/net/URL:toString	()Ljava/lang/String;
    //   19: getstatic 35	com/facebook/widget/UrlRedirectCache:REDIRECT_CONTENT_TAG	Ljava/lang/String;
    //   22: invokevirtual 53	com/facebook/internal/FileLruCache:openPutStream	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/OutputStream;
    //   25: astore_3
    //   26: aload_3
    //   27: aload_2
    //   28: invokevirtual 47	java/net/URL:toString	()Ljava/lang/String;
    //   31: invokevirtual 59	java/lang/String:getBytes	()[B
    //   34: invokevirtual 65	java/io/OutputStream:write	([B)V
    //   37: aload_3
    //   38: invokestatic 71	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   41: return
    //   42: astore 5
    //   44: aload_3
    //   45: invokestatic 71	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   48: return
    //   49: astore 4
    //   51: aload_3
    //   52: invokestatic 71	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   55: aload 4
    //   57: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	paramContext	Context
    //   0	58	1	paramURL1	java.net.URL
    //   0	58	2	paramURL2	java.net.URL
    //   10	42	3	localOutputStream	java.io.OutputStream
    //   49	7	4	localObject	Object
    //   42	1	5	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   11	37	42	java/io/IOException
    //   11	37	49	finally
  }
  
  static FileLruCache getCache(Context paramContext)
    throws IOException
  {
    try
    {
      if (urlRedirectCache == null) {
        urlRedirectCache = new FileLruCache(paramContext.getApplicationContext(), TAG, new FileLruCache.Limits());
      }
      FileLruCache localFileLruCache = urlRedirectCache;
      return localFileLruCache;
    }
    finally {}
  }
  
  /* Error */
  static java.net.URL getRedirectedUrl(Context paramContext, java.net.URL paramURL)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: aload_1
    //   7: invokevirtual 47	java/net/URL:toString	()Ljava/lang/String;
    //   10: astore_2
    //   11: aconst_null
    //   12: astore_3
    //   13: aload_0
    //   14: invokestatic 44	com/facebook/widget/UrlRedirectCache:getCache	(Landroid/content/Context;)Lcom/facebook/internal/FileLruCache;
    //   17: astore 7
    //   19: iconst_0
    //   20: istore 8
    //   22: aconst_null
    //   23: astore 9
    //   25: aload 7
    //   27: aload_2
    //   28: getstatic 35	com/facebook/widget/UrlRedirectCache:REDIRECT_CONTENT_TAG	Ljava/lang/String;
    //   31: invokevirtual 93	com/facebook/internal/FileLruCache:get	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/InputStream;
    //   34: astore 12
    //   36: aload 12
    //   38: ifnull +91 -> 129
    //   41: iconst_1
    //   42: istore 8
    //   44: new 95	java/io/InputStreamReader
    //   47: dup
    //   48: aload 12
    //   50: invokespecial 98	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   53: astore_3
    //   54: sipush 128
    //   57: newarray char
    //   59: astore 16
    //   61: new 21	java/lang/StringBuilder
    //   64: dup
    //   65: invokespecial 24	java/lang/StringBuilder:<init>	()V
    //   68: astore 17
    //   70: aload_3
    //   71: aload 16
    //   73: iconst_0
    //   74: aload 16
    //   76: arraylength
    //   77: invokevirtual 102	java/io/InputStreamReader:read	([CII)I
    //   80: istore 18
    //   82: iload 18
    //   84: ifle +25 -> 109
    //   87: aload 17
    //   89: aload 16
    //   91: iconst_0
    //   92: iload 18
    //   94: invokevirtual 105	java/lang/StringBuilder:append	([CII)Ljava/lang/StringBuilder;
    //   97: pop
    //   98: goto -28 -> 70
    //   101: astore 6
    //   103: aload_3
    //   104: invokestatic 71	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   107: aconst_null
    //   108: areturn
    //   109: aload_3
    //   110: invokestatic 71	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   113: aload 17
    //   115: invokevirtual 33	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: astore 20
    //   120: aload 20
    //   122: astore_2
    //   123: aload_3
    //   124: astore 9
    //   126: goto -101 -> 25
    //   129: aconst_null
    //   130: astore 13
    //   132: iload 8
    //   134: ifeq +17 -> 151
    //   137: new 46	java/net/URL
    //   140: dup
    //   141: aload_2
    //   142: invokespecial 108	java/net/URL:<init>	(Ljava/lang/String;)V
    //   145: astore 15
    //   147: aload 15
    //   149: astore 13
    //   151: aload 9
    //   153: invokestatic 71	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   156: aload 9
    //   158: pop
    //   159: aload 13
    //   161: areturn
    //   162: astore 5
    //   164: aload_3
    //   165: invokestatic 71	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   168: aconst_null
    //   169: areturn
    //   170: astore 4
    //   172: aload_3
    //   173: invokestatic 71	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   176: aload 4
    //   178: athrow
    //   179: astore 4
    //   181: aload 9
    //   183: astore_3
    //   184: goto -12 -> 172
    //   187: astore 11
    //   189: aload 9
    //   191: astore_3
    //   192: goto -28 -> 164
    //   195: astore 10
    //   197: aload 9
    //   199: astore_3
    //   200: goto -97 -> 103
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	203	0	paramContext	Context
    //   0	203	1	paramURL	java.net.URL
    //   10	132	2	localObject1	Object
    //   12	188	3	localObject2	Object
    //   170	7	4	localObject3	Object
    //   179	1	4	localObject4	Object
    //   162	1	5	localIOException1	IOException
    //   101	1	6	localMalformedURLException1	java.net.MalformedURLException
    //   17	9	7	localFileLruCache	FileLruCache
    //   20	113	8	i	int
    //   23	175	9	localObject5	Object
    //   195	1	10	localMalformedURLException2	java.net.MalformedURLException
    //   187	1	11	localIOException2	IOException
    //   34	15	12	localInputStream	java.io.InputStream
    //   130	30	13	localObject6	Object
    //   145	3	15	localURL	java.net.URL
    //   59	31	16	arrayOfChar	char[]
    //   68	46	17	localStringBuilder	java.lang.StringBuilder
    //   80	13	18	j	int
    //   118	3	20	str	String
    // Exception table:
    //   from	to	target	type
    //   13	19	101	java/net/MalformedURLException
    //   54	70	101	java/net/MalformedURLException
    //   70	82	101	java/net/MalformedURLException
    //   87	98	101	java/net/MalformedURLException
    //   109	120	101	java/net/MalformedURLException
    //   13	19	162	java/io/IOException
    //   54	70	162	java/io/IOException
    //   70	82	162	java/io/IOException
    //   87	98	162	java/io/IOException
    //   109	120	162	java/io/IOException
    //   13	19	170	finally
    //   54	70	170	finally
    //   70	82	170	finally
    //   87	98	170	finally
    //   109	120	170	finally
    //   25	36	179	finally
    //   44	54	179	finally
    //   137	147	179	finally
    //   25	36	187	java/io/IOException
    //   44	54	187	java/io/IOException
    //   137	147	187	java/io/IOException
    //   25	36	195	java/net/MalformedURLException
    //   44	54	195	java/net/MalformedURLException
    //   137	147	195	java/net/MalformedURLException
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.widget.UrlRedirectCache
 * JD-Core Version:    0.7.0.1
 */