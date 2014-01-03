package com.touchtype_fluency.service.http;

import com.touchtype.common.util.FileUtils;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.commons.io.input.CountingInputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

public class Downloader
  implements Runnable
{
  private static final int DEFAULT_BUFFER_SIZE = 4096;
  private static final String FILE_SCHEME = "file";
  private static final String TAG = Downloader.class.getSimpleName();
  private boolean isGZIPEncodingEnabled = false;
  private final File mDestination;
  private File mETagFile = null;
  private final HttpClient mHttpClient;
  private DownloaderProgressListener mListener;
  private final HttpUriRequest mRequest;
  private DownloadStatus mStatus = DownloadStatus.WAITING;
  private String mUpdatedETag;
  private final Object statusMutex = new Object();
  
  public Downloader(HttpUriRequest paramHttpUriRequest, HttpClient paramHttpClient, File paramFile, DownloaderProgressListener paramDownloaderProgressListener)
  {
    this.mListener = paramDownloaderProgressListener;
    this.mDestination = paramFile;
    this.mRequest = paramHttpUriRequest;
    if (paramHttpUriRequest != null)
    {
      Header[] arrayOfHeader = paramHttpUriRequest.getHeaders("Accept-Encoding");
      int i = arrayOfHeader.length;
      for (int j = 0; j < i; j++) {
        if (arrayOfHeader[j].getValue().contains("gzip")) {
          this.isGZIPEncodingEnabled = true;
        }
      }
    }
    this.mHttpClient = paramHttpClient;
  }
  
  public Downloader(HttpUriRequest paramHttpUriRequest, HttpClient paramHttpClient, File paramFile1, DownloaderProgressListener paramDownloaderProgressListener, File paramFile2)
  {
    this(paramHttpUriRequest, paramHttpClient, paramFile1, paramDownloaderProgressListener);
    this.mETagFile = paramFile2;
  }
  
  private boolean cancelIfRequested()
  {
    synchronized (this.statusMutex)
    {
      boolean bool = getStatus().equals(DownloadStatus.CANCEL_REQUEST);
      if (bool) {
        try
        {
          FileUtils.deleteRecursively(this.mDestination);
          setStatus(DownloadStatus.CANCELLED);
          return true;
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            LogUtil.e(TAG, localIOException.getMessage());
          }
        }
      }
    }
    return false;
  }
  
  private void copyInputToOutput(InputStream paramInputStream, OutputStream paramOutputStream, ProgressUpdater paramProgressUpdater)
    throws IOException
  {
    byte[] arrayOfByte = new byte[4096];
    do
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      if (i > 0)
      {
        paramOutputStream.write(arrayOfByte, 0, i);
        if (paramProgressUpdater != null) {
          paramProgressUpdater.update();
        }
      }
    } while (!cancelIfRequested());
    return;
    paramOutputStream.flush();
  }
  
  /* Error */
  private void download(HttpResponse paramHttpResponse, int paramInt, InputStream paramInputStream, Header paramHeader1, Header paramHeader2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aload_0
    //   4: getfield 61	com/touchtype_fluency/service/http/Downloader:mDestination	Ljava/io/File;
    //   7: invokestatic 157	com/google/common/io/Files:createParentDirs	(Ljava/io/File;)V
    //   10: new 159	java/io/FileOutputStream
    //   13: dup
    //   14: aload_0
    //   15: getfield 61	com/touchtype_fluency/service/http/Downloader:mDestination	Ljava/io/File;
    //   18: invokespecial 161	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   21: astore 9
    //   23: aload_0
    //   24: getfield 59	com/touchtype_fluency/service/http/Downloader:mListener	Lcom/touchtype_fluency/service/http/DownloaderProgressListener;
    //   27: ifnull +279 -> 306
    //   30: new 163	org/apache/commons/io/input/CountingInputStream
    //   33: dup
    //   34: aload_3
    //   35: invokespecial 166	org/apache/commons/io/input/CountingInputStream:<init>	(Ljava/io/InputStream;)V
    //   38: astore 10
    //   40: new 142	com/touchtype_fluency/service/http/Downloader$ProgressUpdater
    //   43: dup
    //   44: aload_0
    //   45: aload_0
    //   46: getfield 59	com/touchtype_fluency/service/http/Downloader:mListener	Lcom/touchtype_fluency/service/http/DownloaderProgressListener;
    //   49: iload_2
    //   50: aload 10
    //   52: invokespecial 169	com/touchtype_fluency/service/http/Downloader$ProgressUpdater:<init>	(Lcom/touchtype_fluency/service/http/Downloader;Lcom/touchtype_fluency/service/http/DownloaderProgressListener;ILorg/apache/commons/io/input/CountingInputStream;)V
    //   55: astore 11
    //   57: aload 10
    //   59: astore 12
    //   61: aload_0
    //   62: getfield 53	com/touchtype_fluency/service/http/Downloader:isGZIPEncodingEnabled	Z
    //   65: ifeq +234 -> 299
    //   68: aload 4
    //   70: ifnull +18 -> 88
    //   73: ldc 78
    //   75: aload 4
    //   77: invokeinterface 76 1 0
    //   82: invokevirtual 170	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   85: ifne +23 -> 108
    //   88: aload 5
    //   90: ifnull +209 -> 299
    //   93: ldc 78
    //   95: aload 5
    //   97: invokeinterface 76 1 0
    //   102: invokevirtual 170	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   105: ifeq +194 -> 299
    //   108: new 172	java/util/zip/GZIPInputStream
    //   111: dup
    //   112: aload 12
    //   114: invokespecial 173	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   117: astore 15
    //   119: aload_0
    //   120: aload 15
    //   122: aload 9
    //   124: aload 11
    //   126: invokespecial 175	com/touchtype_fluency/service/http/Downloader:copyInputToOutput	(Ljava/io/InputStream;Ljava/io/OutputStream;Lcom/touchtype_fluency/service/http/Downloader$ProgressUpdater;)V
    //   129: aload 9
    //   131: invokevirtual 176	java/io/FileOutputStream:flush	()V
    //   134: aload_0
    //   135: invokevirtual 97	com/touchtype_fluency/service/http/Downloader:getStatus	()Lcom/touchtype_fluency/service/http/Downloader$DownloadStatus;
    //   138: astore 16
    //   140: getstatic 182	com/touchtype_fluency/service/http/Downloader$1:$SwitchMap$com$touchtype_fluency$service$http$Downloader$DownloadStatus	[I
    //   143: aload 16
    //   145: invokevirtual 186	com/touchtype_fluency/service/http/Downloader$DownloadStatus:ordinal	()I
    //   148: iaload
    //   149: tableswitch	default:+23 -> 172, 1:+33->182, 2:+92->241
    //   173: invokespecial 189	com/touchtype_fluency/service/http/Downloader:onDownloadComplete	()V
    //   176: aload 9
    //   178: invokestatic 195	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   181: return
    //   182: aload_0
    //   183: invokespecial 147	com/touchtype_fluency/service/http/Downloader:cancelIfRequested	()Z
    //   186: pop
    //   187: aload_0
    //   188: invokespecial 198	com/touchtype_fluency/service/http/Downloader:onDownloadCancelled	()V
    //   191: goto -15 -> 176
    //   194: astore 7
    //   196: aload 9
    //   198: astore 6
    //   200: getstatic 40	com/touchtype_fluency/service/http/Downloader:TAG	Ljava/lang/String;
    //   203: new 200	java/lang/StringBuilder
    //   206: dup
    //   207: ldc 202
    //   209: invokespecial 205	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   212: aload 7
    //   214: invokevirtual 120	java/io/IOException:getMessage	()Ljava/lang/String;
    //   217: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: invokevirtual 212	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   223: aload 7
    //   225: invokestatic 216	com/touchtype/util/LogUtil:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   228: aload_0
    //   229: getstatic 222	com/touchtype_fluency/service/http/Downloader$DownloadFailedReason:DOWNLOAD_FAILED	Lcom/touchtype_fluency/service/http/Downloader$DownloadFailedReason;
    //   232: invokespecial 226	com/touchtype_fluency/service/http/Downloader:onDownloadFailed	(Lcom/touchtype_fluency/service/http/Downloader$DownloadFailedReason;)V
    //   235: aload 6
    //   237: invokestatic 195	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   240: return
    //   241: aload_0
    //   242: invokespecial 198	com/touchtype_fluency/service/http/Downloader:onDownloadCancelled	()V
    //   245: goto -69 -> 176
    //   248: astore 8
    //   250: aload 9
    //   252: astore 6
    //   254: aload 6
    //   256: invokestatic 195	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   259: aload 8
    //   261: athrow
    //   262: astore 8
    //   264: goto -10 -> 254
    //   267: astore 8
    //   269: aload 9
    //   271: astore 6
    //   273: aload 12
    //   275: pop
    //   276: goto -22 -> 254
    //   279: astore 7
    //   281: aconst_null
    //   282: astore 6
    //   284: goto -84 -> 200
    //   287: astore 7
    //   289: aload 9
    //   291: astore 6
    //   293: aload 12
    //   295: pop
    //   296: goto -96 -> 200
    //   299: aload 12
    //   301: astore 15
    //   303: goto -184 -> 119
    //   306: aload_3
    //   307: astore 12
    //   309: aconst_null
    //   310: astore 11
    //   312: goto -251 -> 61
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	315	0	this	Downloader
    //   0	315	1	paramHttpResponse	HttpResponse
    //   0	315	2	paramInt	int
    //   0	315	3	paramInputStream	InputStream
    //   0	315	4	paramHeader1	Header
    //   0	315	5	paramHeader2	Header
    //   1	291	6	localObject1	Object
    //   194	30	7	localIOException1	IOException
    //   279	1	7	localIOException2	IOException
    //   287	1	7	localIOException3	IOException
    //   248	12	8	localObject2	Object
    //   262	1	8	localObject3	Object
    //   267	1	8	localObject4	Object
    //   21	269	9	localFileOutputStream	java.io.FileOutputStream
    //   38	20	10	localCountingInputStream	CountingInputStream
    //   55	256	11	localProgressUpdater	ProgressUpdater
    //   59	249	12	localObject5	Object
    //   117	185	15	localObject6	Object
    //   138	6	16	localDownloadStatus	DownloadStatus
    // Exception table:
    //   from	to	target	type
    //   23	57	194	java/io/IOException
    //   119	172	194	java/io/IOException
    //   172	176	194	java/io/IOException
    //   182	191	194	java/io/IOException
    //   241	245	194	java/io/IOException
    //   23	57	248	finally
    //   119	172	248	finally
    //   172	176	248	finally
    //   182	191	248	finally
    //   241	245	248	finally
    //   3	23	262	finally
    //   200	235	262	finally
    //   61	68	267	finally
    //   73	88	267	finally
    //   93	108	267	finally
    //   108	119	267	finally
    //   3	23	279	java/io/IOException
    //   61	68	287	java/io/IOException
    //   73	88	287	java/io/IOException
    //   93	108	287	java/io/IOException
    //   108	119	287	java/io/IOException
  }
  
  private String getETagFromResponse(HttpResponse paramHttpResponse)
  {
    Header localHeader = paramHttpResponse.getFirstHeader("ETag");
    if (localHeader != null) {
      return localHeader.getValue();
    }
    return null;
  }
  
  private void onDownloadCancelled()
  {
    if (this.mListener != null) {
      this.mListener.onCancelled();
    }
  }
  
  private void onDownloadComplete()
  {
    saveETagToFile();
    setStatus(DownloadStatus.COMPLETED);
    if (this.mListener != null) {
      this.mListener.onComplete();
    }
  }
  
  private void onDownloadFailed(DownloadFailedReason paramDownloadFailedReason)
  {
    try
    {
      FileUtils.deleteRecursively(this.mDestination);
      setStatus(DownloadStatus.FAILED);
      if (this.mListener != null) {
        this.mListener.onFailed(paramDownloadFailedReason);
      }
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e(TAG, localIOException.getMessage());
      }
    }
  }
  
  private void onDownloadStarted()
  {
    setStatus(DownloadStatus.IN_PROGRESS);
    if (this.mListener != null) {
      this.mListener.onStarted();
    }
  }
  
  private void saveETagToFile()
  {
    if ((this.mETagFile != null) && (this.mUpdatedETag != null)) {}
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(this.mETagFile);
      localPrintWriter.println(this.mUpdatedETag);
      localPrintWriter.close();
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, "Error saving ETAG to file! " + localIOException.getLocalizedMessage());
      localIOException.printStackTrace();
    }
  }
  
  private void setStatus(DownloadStatus paramDownloadStatus)
  {
    try
    {
      this.mStatus = paramDownloadStatus;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void cancel()
  {
    synchronized (this.statusMutex)
    {
      if (getStatus().equals(DownloadStatus.IN_PROGRESS)) {
        setStatus(DownloadStatus.CANCEL_REQUEST);
      }
      return;
    }
  }
  
  public DownloadStatus getStatus()
  {
    try
    {
      DownloadStatus localDownloadStatus = this.mStatus;
      return localDownloadStatus;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: invokespecial 288	com/touchtype_fluency/service/http/Downloader:onDownloadStarted	()V
    //   6: aload_0
    //   7: getfield 63	com/touchtype_fluency/service/http/Downloader:mRequest	Lorg/apache/http/client/methods/HttpUriRequest;
    //   10: invokeinterface 292 1 0
    //   15: invokevirtual 297	java/net/URI:getScheme	()Ljava/lang/String;
    //   18: ldc 13
    //   20: invokevirtual 170	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   23: ifne +306 -> 329
    //   26: aload_0
    //   27: getfield 86	com/touchtype_fluency/service/http/Downloader:mHttpClient	Lorg/apache/http/client/HttpClient;
    //   30: aload_0
    //   31: getfield 63	com/touchtype_fluency/service/http/Downloader:mRequest	Lorg/apache/http/client/methods/HttpUriRequest;
    //   34: invokeinterface 303 2 0
    //   39: astore 8
    //   41: aload 8
    //   43: invokeinterface 307 1 0
    //   48: invokeinterface 312 1 0
    //   53: istore 9
    //   55: aconst_null
    //   56: astore_1
    //   57: iload 9
    //   59: sipush 200
    //   62: if_icmpne +90 -> 152
    //   65: aload 8
    //   67: invokeinterface 316 1 0
    //   72: astore 10
    //   74: aload 10
    //   76: invokeinterface 322 1 0
    //   81: l2i
    //   82: istore 11
    //   84: aload 10
    //   86: invokeinterface 326 1 0
    //   91: astore_1
    //   92: aload 10
    //   94: invokeinterface 330 1 0
    //   99: astore 12
    //   101: aload 10
    //   103: invokeinterface 333 1 0
    //   108: astore 13
    //   110: aload_0
    //   111: aload_0
    //   112: aload 8
    //   114: invokespecial 335	com/touchtype_fluency/service/http/Downloader:getETagFromResponse	(Lorg/apache/http/HttpResponse;)Ljava/lang/String;
    //   117: putfield 265	com/touchtype_fluency/service/http/Downloader:mUpdatedETag	Ljava/lang/String;
    //   120: aload_0
    //   121: aload 8
    //   123: iload 11
    //   125: aload_1
    //   126: aload 12
    //   128: aload 13
    //   130: invokespecial 337	com/touchtype_fluency/service/http/Downloader:download	(Lorg/apache/http/HttpResponse;ILjava/io/InputStream;Lorg/apache/http/Header;Lorg/apache/http/Header;)V
    //   133: aload_1
    //   134: invokestatic 195	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   137: aload_0
    //   138: getfield 86	com/touchtype_fluency/service/http/Downloader:mHttpClient	Lorg/apache/http/client/HttpClient;
    //   141: invokeinterface 341 1 0
    //   146: invokeinterface 346 1 0
    //   151: return
    //   152: iload 9
    //   154: sipush 304
    //   157: if_icmpne +72 -> 229
    //   160: aload_0
    //   161: aload_0
    //   162: aload 8
    //   164: invokespecial 335	com/touchtype_fluency/service/http/Downloader:getETagFromResponse	(Lorg/apache/http/HttpResponse;)Ljava/lang/String;
    //   167: putfield 265	com/touchtype_fluency/service/http/Downloader:mUpdatedETag	Ljava/lang/String;
    //   170: getstatic 40	com/touchtype_fluency/service/http/Downloader:TAG	Ljava/lang/String;
    //   173: ldc_w 348
    //   176: invokestatic 126	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   179: aload_0
    //   180: invokespecial 189	com/touchtype_fluency/service/http/Downloader:onDownloadComplete	()V
    //   183: aconst_null
    //   184: astore_1
    //   185: goto -52 -> 133
    //   188: astore 4
    //   190: getstatic 40	com/touchtype_fluency/service/http/Downloader:TAG	Ljava/lang/String;
    //   193: aload 4
    //   195: invokevirtual 120	java/io/IOException:getMessage	()Ljava/lang/String;
    //   198: aload 4
    //   200: invokestatic 216	com/touchtype/util/LogUtil:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   203: aload_0
    //   204: getstatic 351	com/touchtype_fluency/service/http/Downloader$DownloadFailedReason:CONNECTION_FAILED	Lcom/touchtype_fluency/service/http/Downloader$DownloadFailedReason;
    //   207: invokespecial 226	com/touchtype_fluency/service/http/Downloader:onDownloadFailed	(Lcom/touchtype_fluency/service/http/Downloader$DownloadFailedReason;)V
    //   210: aload_1
    //   211: invokestatic 195	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   214: aload_0
    //   215: getfield 86	com/touchtype_fluency/service/http/Downloader:mHttpClient	Lorg/apache/http/client/HttpClient;
    //   218: invokeinterface 341 1 0
    //   223: invokeinterface 346 1 0
    //   228: return
    //   229: getstatic 40	com/touchtype_fluency/service/http/Downloader:TAG	Ljava/lang/String;
    //   232: new 200	java/lang/StringBuilder
    //   235: dup
    //   236: ldc_w 353
    //   239: invokespecial 205	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   242: iload 9
    //   244: invokevirtual 356	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   247: invokevirtual 212	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   250: invokestatic 358	com/touchtype/util/LogUtil:w	(Ljava/lang/String;Ljava/lang/String;)V
    //   253: aload_0
    //   254: getstatic 351	com/touchtype_fluency/service/http/Downloader$DownloadFailedReason:CONNECTION_FAILED	Lcom/touchtype_fluency/service/http/Downloader$DownloadFailedReason;
    //   257: invokespecial 226	com/touchtype_fluency/service/http/Downloader:onDownloadFailed	(Lcom/touchtype_fluency/service/http/Downloader$DownloadFailedReason;)V
    //   260: aconst_null
    //   261: astore_1
    //   262: goto -129 -> 133
    //   265: astore_3
    //   266: getstatic 40	com/touchtype_fluency/service/http/Downloader:TAG	Ljava/lang/String;
    //   269: new 200	java/lang/StringBuilder
    //   272: dup
    //   273: ldc_w 360
    //   276: invokespecial 205	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   279: aload_3
    //   280: invokevirtual 361	java/lang/RuntimeException:getMessage	()Ljava/lang/String;
    //   283: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   286: invokevirtual 212	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   289: invokestatic 126	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   292: aload_0
    //   293: getstatic 222	com/touchtype_fluency/service/http/Downloader$DownloadFailedReason:DOWNLOAD_FAILED	Lcom/touchtype_fluency/service/http/Downloader$DownloadFailedReason;
    //   296: invokespecial 226	com/touchtype_fluency/service/http/Downloader:onDownloadFailed	(Lcom/touchtype_fluency/service/http/Downloader$DownloadFailedReason;)V
    //   299: new 286	java/lang/RuntimeException
    //   302: dup
    //   303: aload_3
    //   304: invokespecial 364	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   307: athrow
    //   308: astore_2
    //   309: aload_1
    //   310: invokestatic 195	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   313: aload_0
    //   314: getfield 86	com/touchtype_fluency/service/http/Downloader:mHttpClient	Lorg/apache/http/client/HttpClient;
    //   317: invokeinterface 341 1 0
    //   322: invokeinterface 346 1 0
    //   327: aload_2
    //   328: athrow
    //   329: new 366	java/io/File
    //   332: dup
    //   333: aload_0
    //   334: getfield 63	com/touchtype_fluency/service/http/Downloader:mRequest	Lorg/apache/http/client/methods/HttpUriRequest;
    //   337: invokeinterface 292 1 0
    //   342: invokevirtual 369	java/net/URI:getSchemeSpecificPart	()Ljava/lang/String;
    //   345: invokespecial 370	java/io/File:<init>	(Ljava/lang/String;)V
    //   348: astore 5
    //   350: aload 5
    //   352: invokevirtual 373	java/io/File:length	()J
    //   355: l2i
    //   356: istore 6
    //   358: new 375	java/io/BufferedInputStream
    //   361: dup
    //   362: new 377	java/io/FileInputStream
    //   365: dup
    //   366: aload 5
    //   368: invokespecial 378	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   371: invokespecial 379	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   374: astore 7
    //   376: aload_0
    //   377: aconst_null
    //   378: iload 6
    //   380: aload 7
    //   382: aconst_null
    //   383: aconst_null
    //   384: invokespecial 337	com/touchtype_fluency/service/http/Downloader:download	(Lorg/apache/http/HttpResponse;ILjava/io/InputStream;Lorg/apache/http/Header;Lorg/apache/http/Header;)V
    //   387: aload 7
    //   389: astore_1
    //   390: goto -257 -> 133
    //   393: astore_2
    //   394: aconst_null
    //   395: astore_1
    //   396: goto -87 -> 309
    //   399: astore_2
    //   400: aload 7
    //   402: astore_1
    //   403: goto -94 -> 309
    //   406: astore_3
    //   407: aconst_null
    //   408: astore_1
    //   409: goto -143 -> 266
    //   412: astore_3
    //   413: aload 7
    //   415: astore_1
    //   416: goto -150 -> 266
    //   419: astore 4
    //   421: aconst_null
    //   422: astore_1
    //   423: goto -233 -> 190
    //   426: astore 4
    //   428: aload 7
    //   430: astore_1
    //   431: goto -241 -> 190
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	434	0	this	Downloader
    //   1	430	1	localObject1	Object
    //   308	20	2	localObject2	Object
    //   393	1	2	localObject3	Object
    //   399	1	2	localObject4	Object
    //   265	39	3	localRuntimeException1	java.lang.RuntimeException
    //   406	1	3	localRuntimeException2	java.lang.RuntimeException
    //   412	1	3	localRuntimeException3	java.lang.RuntimeException
    //   188	11	4	localIOException1	IOException
    //   419	1	4	localIOException2	IOException
    //   426	1	4	localIOException3	IOException
    //   348	19	5	localFile	File
    //   356	23	6	i	int
    //   374	55	7	localBufferedInputStream	java.io.BufferedInputStream
    //   39	124	8	localHttpResponse	HttpResponse
    //   53	190	9	j	int
    //   72	30	10	localHttpEntity	org.apache.http.HttpEntity
    //   82	42	11	k	int
    //   99	28	12	localHeader1	Header
    //   108	21	13	localHeader2	Header
    // Exception table:
    //   from	to	target	type
    //   2	55	188	java/io/IOException
    //   65	133	188	java/io/IOException
    //   160	183	188	java/io/IOException
    //   229	260	188	java/io/IOException
    //   329	350	188	java/io/IOException
    //   2	55	265	java/lang/RuntimeException
    //   65	133	265	java/lang/RuntimeException
    //   160	183	265	java/lang/RuntimeException
    //   229	260	265	java/lang/RuntimeException
    //   329	350	265	java/lang/RuntimeException
    //   2	55	308	finally
    //   65	133	308	finally
    //   160	183	308	finally
    //   190	210	308	finally
    //   229	260	308	finally
    //   266	308	308	finally
    //   329	350	308	finally
    //   350	376	393	finally
    //   376	387	399	finally
    //   350	376	406	java/lang/RuntimeException
    //   376	387	412	java/lang/RuntimeException
    //   350	376	419	java/io/IOException
    //   376	387	426	java/io/IOException
  }
  
  public static enum DownloadFailedReason
  {
    static
    {
      DownloadFailedReason[] arrayOfDownloadFailedReason = new DownloadFailedReason[2];
      arrayOfDownloadFailedReason[0] = CONNECTION_FAILED;
      arrayOfDownloadFailedReason[1] = DOWNLOAD_FAILED;
      $VALUES = arrayOfDownloadFailedReason;
    }
    
    private DownloadFailedReason() {}
  }
  
  public static enum DownloadStatus
  {
    static
    {
      IN_PROGRESS = new DownloadStatus("IN_PROGRESS", 1);
      CANCELLED = new DownloadStatus("CANCELLED", 2);
      CANCEL_REQUEST = new DownloadStatus("CANCEL_REQUEST", 3);
      FAILED = new DownloadStatus("FAILED", 4);
      COMPLETED = new DownloadStatus("COMPLETED", 5);
      DownloadStatus[] arrayOfDownloadStatus = new DownloadStatus[6];
      arrayOfDownloadStatus[0] = WAITING;
      arrayOfDownloadStatus[1] = IN_PROGRESS;
      arrayOfDownloadStatus[2] = CANCELLED;
      arrayOfDownloadStatus[3] = CANCEL_REQUEST;
      arrayOfDownloadStatus[4] = FAILED;
      arrayOfDownloadStatus[5] = COMPLETED;
      $VALUES = arrayOfDownloadStatus;
    }
    
    private DownloadStatus() {}
  }
  
  private final class ProgressUpdater
  {
    private static final long UPDATE_DELAY_MS = 50L;
    private final CountingInputStream counter;
    private long lastUpdateTime;
    private final DownloaderProgressListener listener;
    private final int maximum;
    
    ProgressUpdater(DownloaderProgressListener paramDownloaderProgressListener, int paramInt, CountingInputStream paramCountingInputStream)
    {
      this.listener = paramDownloaderProgressListener;
      this.maximum = paramInt;
      this.counter = paramCountingInputStream;
      this.lastUpdateTime = 0L;
    }
    
    void update()
    {
      if (System.currentTimeMillis() - this.lastUpdateTime > 50L)
      {
        this.listener.onProgress(this.counter.getCount(), this.maximum);
        this.lastUpdateTime = System.currentTimeMillis();
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.http.Downloader
 * JD-Core Version:    0.7.0.1
 */