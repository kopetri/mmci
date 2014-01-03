package com.facebook.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import com.facebook.internal.Utility;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class ImageDownloader
{
  private static final int CACHE_READ_QUEUE_MAX_CONCURRENT = 2;
  private static final int DOWNLOAD_QUEUE_MAX_CONCURRENT = 8;
  private static WorkQueue cacheReadQueue = new WorkQueue(2);
  private static WorkQueue downloadQueue;
  private static final Handler handler = new Handler();
  private static final Map<RequestKey, DownloaderContext> pendingRequests = new HashMap();
  
  static
  {
    downloadQueue = new WorkQueue(8);
  }
  
  static boolean cancelRequest(ImageRequest paramImageRequest)
  {
    RequestKey localRequestKey = new RequestKey(paramImageRequest.getImageUrl(), paramImageRequest.getCallerTag());
    synchronized (pendingRequests)
    {
      DownloaderContext localDownloaderContext = (DownloaderContext)pendingRequests.get(localRequestKey);
      boolean bool = false;
      if (localDownloaderContext != null)
      {
        bool = true;
        if (localDownloaderContext.workItem.cancel()) {
          pendingRequests.remove(localRequestKey);
        }
      }
      else
      {
        return bool;
      }
      localDownloaderContext.isCancelled = true;
    }
  }
  
  /* Error */
  private static void download(RequestKey paramRequestKey, Context paramContext)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aconst_null
    //   5: astore 4
    //   7: iconst_1
    //   8: istore 5
    //   10: aload_0
    //   11: getfield 99	com/facebook/widget/ImageDownloader$RequestKey:url	Ljava/net/URL;
    //   14: invokevirtual 105	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   17: checkcast 107	java/net/HttpURLConnection
    //   20: astore_2
    //   21: aload_2
    //   22: iconst_0
    //   23: invokevirtual 111	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   26: aload_2
    //   27: invokevirtual 115	java/net/HttpURLConnection:getResponseCode	()I
    //   30: lookupswitch	default:+34->64, 200:+255->285, 301:+122->152, 302:+122->152
    //   65: invokevirtual 119	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   68: astore_3
    //   69: new 121	java/io/InputStreamReader
    //   72: dup
    //   73: aload_3
    //   74: invokespecial 124	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   77: astore 13
    //   79: sipush 128
    //   82: newarray char
    //   84: astore 14
    //   86: new 126	java/lang/StringBuilder
    //   89: dup
    //   90: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   93: astore 15
    //   95: aload 13
    //   97: aload 14
    //   99: iconst_0
    //   100: aload 14
    //   102: arraylength
    //   103: invokevirtual 131	java/io/InputStreamReader:read	([CII)I
    //   106: istore 16
    //   108: iload 16
    //   110: ifle +193 -> 303
    //   113: aload 15
    //   115: aload 14
    //   117: iconst_0
    //   118: iload 16
    //   120: invokevirtual 135	java/lang/StringBuilder:append	([CII)Ljava/lang/StringBuilder;
    //   123: pop
    //   124: goto -29 -> 95
    //   127: astore 7
    //   129: aload_3
    //   130: invokestatic 141	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   133: aload_2
    //   134: invokestatic 145	com/facebook/internal/Utility:disconnectQuietly	(Ljava/net/URLConnection;)V
    //   137: iload 5
    //   139: ifeq +12 -> 151
    //   142: aload_0
    //   143: aload 7
    //   145: aload 4
    //   147: iconst_0
    //   148: invokestatic 149	com/facebook/widget/ImageDownloader:issueResponse	(Lcom/facebook/widget/ImageDownloader$RequestKey;Ljava/lang/Exception;Landroid/graphics/Bitmap;Z)V
    //   151: return
    //   152: iconst_0
    //   153: istore 5
    //   155: aload_2
    //   156: ldc 151
    //   158: invokevirtual 155	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   161: astore 8
    //   163: aload 8
    //   165: invokestatic 159	com/facebook/internal/Utility:isNullOrEmpty	(Ljava/lang/String;)Z
    //   168: istore 9
    //   170: aconst_null
    //   171: astore 4
    //   173: aconst_null
    //   174: astore 7
    //   176: iconst_0
    //   177: istore 5
    //   179: aconst_null
    //   180: astore_3
    //   181: iload 9
    //   183: ifne +91 -> 274
    //   186: new 101	java/net/URL
    //   189: dup
    //   190: aload 8
    //   192: invokespecial 162	java/net/URL:<init>	(Ljava/lang/String;)V
    //   195: astore 10
    //   197: aload_1
    //   198: aload_0
    //   199: getfield 99	com/facebook/widget/ImageDownloader$RequestKey:url	Ljava/net/URL;
    //   202: aload 10
    //   204: invokestatic 168	com/facebook/widget/UrlRedirectCache:cacheUrlRedirect	(Landroid/content/Context;Ljava/net/URL;Ljava/net/URL;)V
    //   207: aload_0
    //   208: invokestatic 172	com/facebook/widget/ImageDownloader:removePendingRequest	(Lcom/facebook/widget/ImageDownloader$RequestKey;)Lcom/facebook/widget/ImageDownloader$DownloaderContext;
    //   211: astore 11
    //   213: aconst_null
    //   214: astore 4
    //   216: aconst_null
    //   217: astore 7
    //   219: iconst_0
    //   220: istore 5
    //   222: aconst_null
    //   223: astore_3
    //   224: aload 11
    //   226: ifnull +48 -> 274
    //   229: aload 11
    //   231: getfield 93	com/facebook/widget/ImageDownloader$DownloaderContext:isCancelled	Z
    //   234: istore 12
    //   236: aconst_null
    //   237: astore 4
    //   239: aconst_null
    //   240: astore 7
    //   242: iconst_0
    //   243: istore 5
    //   245: aconst_null
    //   246: astore_3
    //   247: iload 12
    //   249: ifne +25 -> 274
    //   252: aload 11
    //   254: getfield 176	com/facebook/widget/ImageDownloader$DownloaderContext:request	Lcom/facebook/widget/ImageRequest;
    //   257: new 55	com/facebook/widget/ImageDownloader$RequestKey
    //   260: dup
    //   261: aload 10
    //   263: aload_0
    //   264: getfield 180	com/facebook/widget/ImageDownloader$RequestKey:tag	Ljava/lang/Object;
    //   267: invokespecial 68	com/facebook/widget/ImageDownloader$RequestKey:<init>	(Ljava/net/URL;Ljava/lang/Object;)V
    //   270: iconst_0
    //   271: invokestatic 184	com/facebook/widget/ImageDownloader:enqueueCacheRead	(Lcom/facebook/widget/ImageRequest;Lcom/facebook/widget/ImageDownloader$RequestKey;Z)V
    //   274: aload_3
    //   275: invokestatic 141	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   278: aload_2
    //   279: invokestatic 145	com/facebook/internal/Utility:disconnectQuietly	(Ljava/net/URLConnection;)V
    //   282: goto -145 -> 137
    //   285: aload_1
    //   286: aload_2
    //   287: invokestatic 190	com/facebook/widget/ImageResponseCache:interceptAndCacheImageStream	(Landroid/content/Context;Ljava/net/HttpURLConnection;)Ljava/io/InputStream;
    //   290: astore_3
    //   291: aload_3
    //   292: invokestatic 196	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   295: astore 4
    //   297: aconst_null
    //   298: astore 7
    //   300: goto -26 -> 274
    //   303: aload 13
    //   305: invokestatic 141	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   308: new 198	com/facebook/FacebookException
    //   311: dup
    //   312: aload 15
    //   314: invokevirtual 202	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   317: invokespecial 203	com/facebook/FacebookException:<init>	(Ljava/lang/String;)V
    //   320: astore 7
    //   322: aconst_null
    //   323: astore 4
    //   325: goto -51 -> 274
    //   328: astore 6
    //   330: aload_3
    //   331: invokestatic 141	com/facebook/internal/Utility:closeQuietly	(Ljava/io/Closeable;)V
    //   334: aload_2
    //   335: invokestatic 145	com/facebook/internal/Utility:disconnectQuietly	(Ljava/net/URLConnection;)V
    //   338: aload 6
    //   340: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	341	0	paramRequestKey	RequestKey
    //   0	341	1	paramContext	Context
    //   1	334	2	localHttpURLConnection	java.net.HttpURLConnection
    //   3	328	3	localInputStream	InputStream
    //   5	319	4	localBitmap	Bitmap
    //   8	236	5	i	int
    //   328	11	6	localObject	Object
    //   127	17	7	localIOException	java.io.IOException
    //   174	147	7	localFacebookException	com.facebook.FacebookException
    //   161	30	8	str	java.lang.String
    //   168	14	9	bool1	boolean
    //   195	67	10	localURL	URL
    //   211	42	11	localDownloaderContext	DownloaderContext
    //   234	14	12	bool2	boolean
    //   77	227	13	localInputStreamReader	java.io.InputStreamReader
    //   84	32	14	arrayOfChar	char[]
    //   93	220	15	localStringBuilder	java.lang.StringBuilder
    //   106	13	16	j	int
    // Exception table:
    //   from	to	target	type
    //   10	64	127	java/io/IOException
    //   64	95	127	java/io/IOException
    //   95	108	127	java/io/IOException
    //   113	124	127	java/io/IOException
    //   155	170	127	java/io/IOException
    //   186	213	127	java/io/IOException
    //   229	236	127	java/io/IOException
    //   252	274	127	java/io/IOException
    //   285	297	127	java/io/IOException
    //   303	322	127	java/io/IOException
    //   10	64	328	finally
    //   64	95	328	finally
    //   95	108	328	finally
    //   113	124	328	finally
    //   155	170	328	finally
    //   186	213	328	finally
    //   229	236	328	finally
    //   252	274	328	finally
    //   285	297	328	finally
    //   303	322	328	finally
  }
  
  static void downloadAsync(ImageRequest paramImageRequest)
  {
    if (paramImageRequest == null) {
      return;
    }
    RequestKey localRequestKey = new RequestKey(paramImageRequest.getImageUrl(), paramImageRequest.getCallerTag());
    for (;;)
    {
      synchronized (pendingRequests)
      {
        DownloaderContext localDownloaderContext = (DownloaderContext)pendingRequests.get(localRequestKey);
        if (localDownloaderContext != null)
        {
          localDownloaderContext.request = paramImageRequest;
          localDownloaderContext.isCancelled = false;
          localDownloaderContext.workItem.moveToFront();
          return;
        }
      }
      enqueueCacheRead(paramImageRequest, localRequestKey, paramImageRequest.isCachedRedirectAllowed());
    }
  }
  
  private static void enqueueCacheRead(ImageRequest paramImageRequest, RequestKey paramRequestKey, boolean paramBoolean)
  {
    enqueueRequest(paramImageRequest, paramRequestKey, cacheReadQueue, new CacheReadWorkItem(paramImageRequest.getContext(), paramRequestKey, paramBoolean));
  }
  
  private static void enqueueDownload(ImageRequest paramImageRequest, RequestKey paramRequestKey)
  {
    enqueueRequest(paramImageRequest, paramRequestKey, downloadQueue, new DownloadImageWorkItem(paramImageRequest.getContext(), paramRequestKey));
  }
  
  private static void enqueueRequest(ImageRequest paramImageRequest, RequestKey paramRequestKey, WorkQueue paramWorkQueue, Runnable paramRunnable)
  {
    synchronized (pendingRequests)
    {
      DownloaderContext localDownloaderContext = new DownloaderContext(null);
      localDownloaderContext.request = paramImageRequest;
      pendingRequests.put(paramRequestKey, localDownloaderContext);
      localDownloaderContext.workItem = paramWorkQueue.addActiveWorkItem(paramRunnable);
      return;
    }
  }
  
  private static void issueResponse(RequestKey paramRequestKey, final Exception paramException, final Bitmap paramBitmap, final boolean paramBoolean)
  {
    DownloaderContext localDownloaderContext = removePendingRequest(paramRequestKey);
    if ((localDownloaderContext != null) && (!localDownloaderContext.isCancelled))
    {
      ImageRequest localImageRequest = localDownloaderContext.request;
      final ImageRequest.Callback localCallback = localImageRequest.getCallback();
      if (localCallback != null) {
        handler.post(new Runnable()
        {
          public void run()
          {
            ImageResponse localImageResponse = new ImageResponse(this.val$request, paramException, paramBoolean, paramBitmap);
            localCallback.onCompleted(localImageResponse);
          }
        });
      }
    }
  }
  
  static void prioritizeRequest(ImageRequest paramImageRequest)
  {
    RequestKey localRequestKey = new RequestKey(paramImageRequest.getImageUrl(), paramImageRequest.getCallerTag());
    synchronized (pendingRequests)
    {
      DownloaderContext localDownloaderContext = (DownloaderContext)pendingRequests.get(localRequestKey);
      if (localDownloaderContext != null) {
        localDownloaderContext.workItem.moveToFront();
      }
      return;
    }
  }
  
  private static void readFromCache(RequestKey paramRequestKey, Context paramContext, boolean paramBoolean)
  {
    InputStream localInputStream = null;
    boolean bool = false;
    if (paramBoolean)
    {
      URL localURL = UrlRedirectCache.getRedirectedUrl(paramContext, paramRequestKey.url);
      localInputStream = null;
      bool = false;
      if (localURL != null)
      {
        localInputStream = ImageResponseCache.getCachedImageStream(localURL, paramContext);
        if (localInputStream == null) {
          break label81;
        }
        bool = true;
      }
    }
    if (!bool) {
      localInputStream = ImageResponseCache.getCachedImageStream(paramRequestKey.url, paramContext);
    }
    if (localInputStream != null)
    {
      Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream);
      Utility.closeQuietly(localInputStream);
      issueResponse(paramRequestKey, null, localBitmap, bool);
    }
    label81:
    DownloaderContext localDownloaderContext;
    do
    {
      return;
      bool = false;
      break;
      localDownloaderContext = removePendingRequest(paramRequestKey);
    } while ((localDownloaderContext == null) || (localDownloaderContext.isCancelled));
    enqueueDownload(localDownloaderContext.request, paramRequestKey);
  }
  
  private static DownloaderContext removePendingRequest(RequestKey paramRequestKey)
  {
    synchronized (pendingRequests)
    {
      DownloaderContext localDownloaderContext = (DownloaderContext)pendingRequests.remove(paramRequestKey);
      return localDownloaderContext;
    }
  }
  
  private static class CacheReadWorkItem
    implements Runnable
  {
    private boolean allowCachedRedirects;
    private Context context;
    private ImageDownloader.RequestKey key;
    
    CacheReadWorkItem(Context paramContext, ImageDownloader.RequestKey paramRequestKey, boolean paramBoolean)
    {
      this.context = paramContext;
      this.key = paramRequestKey;
      this.allowCachedRedirects = paramBoolean;
    }
    
    public void run()
    {
      ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
    }
  }
  
  private static class DownloadImageWorkItem
    implements Runnable
  {
    private Context context;
    private ImageDownloader.RequestKey key;
    
    DownloadImageWorkItem(Context paramContext, ImageDownloader.RequestKey paramRequestKey)
    {
      this.context = paramContext;
      this.key = paramRequestKey;
    }
    
    public void run()
    {
      ImageDownloader.download(this.key, this.context);
    }
  }
  
  private static class DownloaderContext
  {
    boolean isCancelled;
    ImageRequest request;
    WorkQueue.WorkItem workItem;
  }
  
  private static class RequestKey
  {
    private static final int HASH_MULTIPLIER = 37;
    private static final int HASH_SEED = 29;
    Object tag;
    URL url;
    
    RequestKey(URL paramURL, Object paramObject)
    {
      this.url = paramURL;
      this.tag = paramObject;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = false;
      if (paramObject != null)
      {
        boolean bool2 = paramObject instanceof RequestKey;
        bool1 = false;
        if (bool2)
        {
          RequestKey localRequestKey = (RequestKey)paramObject;
          if ((localRequestKey.url != this.url) || (localRequestKey.tag != this.tag)) {
            break label51;
          }
          bool1 = true;
        }
      }
      return bool1;
      label51:
      return false;
    }
    
    public int hashCode()
    {
      return 37 * (1073 + this.url.hashCode()) + this.tag.hashCode();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.widget.ImageDownloader
 * JD-Core Version:    0.7.0.1
 */