package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import com.touchtype.storage.FolderDecorator;
import com.touchtype.storage.TmpDirectoryHandler;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.ProgressListener;
import com.touchtype_fluency.service.http.Downloader;
import com.touchtype_fluency.service.http.Downloader.DownloadFailedReason;
import com.touchtype_fluency.service.http.Downloader.DownloadStatus;
import com.touchtype_fluency.service.http.DownloaderProgressListener;
import com.touchtype_fluency.service.http.SSLClientFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;

public class LanguagePackDownloader
  implements Runnable
{
  private static final int DOWNLOAD_PERCENT = 90;
  private static final int RESET_CURRENT = -1;
  private static final String TAG = LanguagePackDownloader.class.getSimpleName();
  private static final int TOTAL_PERCENT = 100;
  private static final int UNZIP_PERCENT = 10;
  private boolean mCancelRequest = false;
  private int mCurrentMax = -1;
  private int mCurrentProgress = -1;
  private boolean mDownloadAndUnzipInProgress = false;
  private final DownloadAndUnzipListener mDownloadAndUnzipListener;
  private boolean mDownloadCancelled = false;
  private Downloader mDownloader;
  private List<String> mFiles;
  private final String mId;
  private LanguagePack mLanguagePack;
  private final FolderDecorator mLanguagePackFolder;
  private final ProgressListener mListener;
  private final String mSha1;
  private final TmpDirectoryHandler mTemporaryDirectoryHandler;
  private final String mUrl;
  private File tempDir;
  private File tempFile;
  
  public LanguagePackDownloader(String paramString1, String paramString2, ProgressListener paramProgressListener, TmpDirectoryHandler paramTmpDirectoryHandler, FolderDecorator paramFolderDecorator, String paramString3, LanguagePack paramLanguagePack)
  {
    this.mId = paramString1;
    this.mUrl = paramString2;
    this.mListener = paramProgressListener;
    this.mTemporaryDirectoryHandler = paramTmpDirectoryHandler;
    this.mSha1 = paramString3;
    this.mFiles = new ArrayList();
    this.mLanguagePackFolder = paramFolderDecorator;
    this.mDownloadAndUnzipListener = new DownloadAndUnzipListener(null);
    this.mLanguagePack = paramLanguagePack;
  }
  
  private void calculateRelativeProgress(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.mCurrentMax = 100;
    int i = normalise(paramInt1, paramInt2);
    if (paramBoolean) {}
    for (int j = 90 + i * 10 / 100;; j = i * 90 / 100)
    {
      this.mCurrentProgress = j;
      this.mListener.onProgress(this.mCurrentProgress, this.mCurrentMax);
      return;
    }
  }
  
  private void finishDownloadLPProcess(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.mDownloadAndUnzipInProgress = false;
    this.mListener.onComplete(paramBoolean1, this.mDownloadCancelled, paramBoolean2, paramBoolean3);
  }
  
  private int normalise(int paramInt1, int paramInt2)
  {
    if (paramInt2 > 0) {
      return paramInt1 * 100 / paramInt2;
    }
    return 0;
  }
  
  /* Error */
  private void verifyAndUnzip(File paramFile1, File paramFile2)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: iconst_0
    //   3: istore 4
    //   5: iconst_0
    //   6: istore 5
    //   8: new 152	com/touchtype_fluency/service/util/ChecksumVerifier$SHA1
    //   11: dup
    //   12: invokespecial 153	com/touchtype_fluency/service/util/ChecksumVerifier$SHA1:<init>	()V
    //   15: aload_0
    //   16: getfield 78	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mSha1	Ljava/lang/String;
    //   19: aload_2
    //   20: invokevirtual 157	com/touchtype_fluency/service/util/ChecksumVerifier$SHA1:verifyChecksum	(Ljava/lang/String;Ljava/io/File;)Z
    //   23: istore 4
    //   25: iconst_0
    //   26: istore_3
    //   27: iconst_0
    //   28: istore 5
    //   30: iload 4
    //   32: ifeq +181 -> 213
    //   35: aload_0
    //   36: new 159	com/touchtype_fluency/service/util/Unzipper
    //   39: dup
    //   40: aload_2
    //   41: invokespecial 162	com/touchtype_fluency/service/util/Unzipper:<init>	(Ljava/io/File;)V
    //   44: aload_1
    //   45: aload_0
    //   46: getfield 92	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mDownloadAndUnzipListener	Lcom/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader$DownloadAndUnzipListener;
    //   49: invokevirtual 166	com/touchtype_fluency/service/util/Unzipper:unZip	(Ljava/io/File;Lcom/touchtype_fluency/service/ProgressListener;)Ljava/util/List;
    //   52: putfield 83	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mFiles	Ljava/util/List;
    //   55: aload_0
    //   56: getfield 83	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mFiles	Ljava/util/List;
    //   59: ldc 168
    //   61: invokeinterface 174 2 0
    //   66: pop
    //   67: aload_0
    //   68: getfield 64	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mCancelRequest	Z
    //   71: ifeq +28 -> 99
    //   74: aload_0
    //   75: iconst_1
    //   76: putfield 66	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mDownloadCancelled	Z
    //   79: aload_0
    //   80: getfield 76	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mTemporaryDirectoryHandler	Lcom/touchtype/storage/TmpDirectoryHandler;
    //   83: invokeinterface 180 1 0
    //   88: pop
    //   89: aload_0
    //   90: iload_3
    //   91: iload 4
    //   93: iload 5
    //   95: invokespecial 121	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:finishDownloadLPProcess	(ZZZ)V
    //   98: return
    //   99: new 182	java/io/File
    //   102: dup
    //   103: aload_0
    //   104: getfield 85	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mLanguagePackFolder	Lcom/touchtype/storage/FolderDecorator;
    //   107: invokeinterface 188 1 0
    //   112: aload_0
    //   113: getfield 70	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mId	Ljava/lang/String;
    //   116: invokespecial 191	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   119: astore 11
    //   121: aload_0
    //   122: getfield 94	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mLanguagePack	Lcom/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack;
    //   125: astore 12
    //   127: aload 12
    //   129: monitorenter
    //   130: aload 11
    //   132: invokestatic 197	org/apache/commons/io/FileUtils:deleteQuietly	(Ljava/io/File;)Z
    //   135: pop
    //   136: aload_2
    //   137: invokestatic 197	org/apache/commons/io/FileUtils:deleteQuietly	(Ljava/io/File;)Z
    //   140: pop
    //   141: aload_1
    //   142: aload 11
    //   144: invokestatic 200	org/apache/commons/io/FileUtils:moveDirectory	(Ljava/io/File;Ljava/io/File;)V
    //   147: aload 12
    //   149: monitorexit
    //   150: iconst_1
    //   151: istore_3
    //   152: iconst_0
    //   153: istore 5
    //   155: goto -76 -> 79
    //   158: astore 13
    //   160: aload 12
    //   162: monitorexit
    //   163: aload 13
    //   165: athrow
    //   166: astore 8
    //   168: getstatic 54	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:TAG	Ljava/lang/String;
    //   171: new 202	java/lang/StringBuilder
    //   174: dup
    //   175: ldc 204
    //   177: invokespecial 207	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   180: aload 8
    //   182: invokevirtual 210	java/util/zip/ZipException:getMessage	()Ljava/lang/String;
    //   185: invokevirtual 214	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: invokevirtual 217	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   191: invokestatic 223	com/touchtype/util/LogUtil:w	(Ljava/lang/String;Ljava/lang/String;)V
    //   194: aload_0
    //   195: getfield 76	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mTemporaryDirectoryHandler	Lcom/touchtype/storage/TmpDirectoryHandler;
    //   198: invokeinterface 180 1 0
    //   203: pop
    //   204: aload_0
    //   205: iload_3
    //   206: iconst_0
    //   207: iload 5
    //   209: invokespecial 121	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:finishDownloadLPProcess	(ZZZ)V
    //   212: return
    //   213: iconst_1
    //   214: istore 5
    //   216: iconst_0
    //   217: istore 4
    //   219: iconst_0
    //   220: istore_3
    //   221: goto -142 -> 79
    //   224: astore 6
    //   226: getstatic 54	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:TAG	Ljava/lang/String;
    //   229: new 202	java/lang/StringBuilder
    //   232: dup
    //   233: ldc 225
    //   235: invokespecial 207	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   238: aload 6
    //   240: invokevirtual 226	java/io/IOException:getMessage	()Ljava/lang/String;
    //   243: invokevirtual 214	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   246: invokevirtual 217	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   249: invokestatic 223	com/touchtype/util/LogUtil:w	(Ljava/lang/String;Ljava/lang/String;)V
    //   252: aload_0
    //   253: getfield 76	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:mTemporaryDirectoryHandler	Lcom/touchtype/storage/TmpDirectoryHandler;
    //   256: invokeinterface 180 1 0
    //   261: pop
    //   262: aload_0
    //   263: iload_3
    //   264: iload 4
    //   266: iload 5
    //   268: invokespecial 121	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackDownloader:finishDownloadLPProcess	(ZZZ)V
    //   271: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	272	0	this	LanguagePackDownloader
    //   0	272	1	paramFile1	File
    //   0	272	2	paramFile2	File
    //   1	263	3	bool1	boolean
    //   3	262	4	bool2	boolean
    //   6	261	5	bool3	boolean
    //   224	15	6	localIOException	IOException
    //   166	15	8	localZipException	java.util.zip.ZipException
    //   119	24	11	localFile	File
    //   158	6	13	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   130	150	158	finally
    //   8	25	166	java/util/zip/ZipException
    //   35	79	166	java/util/zip/ZipException
    //   79	98	166	java/util/zip/ZipException
    //   99	130	166	java/util/zip/ZipException
    //   160	166	166	java/util/zip/ZipException
    //   8	25	224	java/io/IOException
    //   35	79	224	java/io/IOException
    //   79	98	224	java/io/IOException
    //   99	130	224	java/io/IOException
    //   160	166	224	java/io/IOException
  }
  
  public void cancel()
  {
    this.mCancelRequest = true;
    if (this.mDownloader != null) {
      this.mDownloader.cancel();
    }
  }
  
  public int getCurrentMax()
  {
    return this.mCurrentMax;
  }
  
  public int getCurrentProgress()
  {
    return this.mCurrentProgress;
  }
  
  public List<String> getFiles()
  {
    return this.mFiles;
  }
  
  public boolean isInProgress()
  {
    return this.mDownloadAndUnzipInProgress;
  }
  
  public void resetProgress()
  {
    this.mCurrentMax = -1;
    this.mCurrentProgress = -1;
  }
  
  public void run()
  {
    try
    {
      this.mDownloadAndUnzipInProgress = true;
      this.tempDir = this.mTemporaryDirectoryHandler.getOrCreateTmpRoot();
      this.tempFile = File.createTempFile("tmpLanguageFile", ".zip", this.tempDir);
      HttpGet localHttpGet = new HttpGet(this.mUrl);
      HttpClient localHttpClient = SSLClientFactory.createHttpClient(new BasicHttpParams());
      if (!this.mCancelRequest)
      {
        this.mDownloader = new Downloader(localHttpGet, localHttpClient, this.tempFile, this.mDownloadAndUnzipListener);
        this.mDownloader.run();
        return;
      }
      this.mDownloadCancelled = true;
      this.mTemporaryDirectoryHandler.deleteRoot();
      finishDownloadLPProcess(false, false, false);
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.w(TAG, "Error creating temporary files, tidying up: " + localIOException.getMessage());
      this.mTemporaryDirectoryHandler.deleteRoot();
      finishDownloadLPProcess(false, false, false);
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      LogUtil.w(TAG, "Runtime Exception, tidying up: " + localRuntimeException.getMessage());
      this.mTemporaryDirectoryHandler.deleteRoot();
      finishDownloadLPProcess(false, false, false);
      throw new RuntimeException(localRuntimeException);
    }
  }
  
  private final class DownloadAndUnzipListener
    implements ProgressListener, DownloaderProgressListener
  {
    private boolean unzipping = false;
    
    private DownloadAndUnzipListener() {}
    
    public void onCancelled()
    {
      LogUtil.w(LanguagePackDownloader.TAG, "Download cancelled, tidying up.");
      LanguagePackDownloader.access$402(LanguagePackDownloader.this, true);
      LanguagePackDownloader.this.mTemporaryDirectoryHandler.deleteRoot();
      LanguagePackDownloader.this.finishDownloadLPProcess(false, false, false);
    }
    
    public void onComplete()
    {
      if (LanguagePackDownloader.this.mCancelRequest)
      {
        LogUtil.w(LanguagePackDownloader.TAG, "Download cancelled on complete, tidying up.");
        LanguagePackDownloader.access$402(LanguagePackDownloader.this, true);
        LanguagePackDownloader.this.mTemporaryDirectoryHandler.deleteRoot();
        LanguagePackDownloader.this.finishDownloadLPProcess(false, false, false);
      }
      while (!LanguagePackDownloader.this.mDownloader.getStatus().equals(Downloader.DownloadStatus.COMPLETED)) {
        return;
      }
      this.unzipping = true;
      LanguagePackDownloader.this.verifyAndUnzip(LanguagePackDownloader.this.tempDir, LanguagePackDownloader.this.tempFile);
    }
    
    public void onComplete(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {}
    
    public void onFailed(Downloader.DownloadFailedReason paramDownloadFailedReason)
    {
      if (paramDownloadFailedReason.equals(Downloader.DownloadFailedReason.CONNECTION_FAILED)) {
        LogUtil.w(LanguagePackDownloader.TAG, "Url " + LanguagePackDownloader.this.mUrl.toString() + " not found, tidying up;");
      }
      for (boolean bool = true;; bool = false)
      {
        LanguagePackDownloader.this.mTemporaryDirectoryHandler.deleteRoot();
        LanguagePackDownloader.this.finishDownloadLPProcess(false, false, bool);
        return;
        LogUtil.w(LanguagePackDownloader.TAG, "Error during download, tidying up.");
      }
    }
    
    public void onProgress(int paramInt1, int paramInt2)
    {
      LanguagePackDownloader.this.calculateRelativeProgress(paramInt1, paramInt2, this.unzipping);
    }
    
    public void onStarted() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackDownloader
 * JD-Core Version:    0.7.0.1
 */