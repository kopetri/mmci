package com.touchtype_fluency.service.personalize.tasks;

import com.touchtype_fluency.service.http.Downloader;
import com.touchtype_fluency.service.http.Downloader.DownloadFailedReason;
import com.touchtype_fluency.service.http.Downloader.DownloadStatus;
import com.touchtype_fluency.service.http.DownloaderProgressListener;
import com.touchtype_fluency.service.http.SSLClientFactory;
import com.touchtype_fluency.service.personalize.PersonalizationFailedReason;
import java.io.File;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;

class DownloadTask
  extends PersonalizationTask
{
  private static final int RETRIES = 5;
  private File mDestination;
  private DownloaderProgressListener mDownloaderListener;
  private MergeTask mNextTask;
  
  public DownloadTask(PersonalizationTaskExecutor paramPersonalizationTaskExecutor, PersonalizationTaskListener paramPersonalizationTaskListener, File paramFile, MergeTask paramMergeTask)
  {
    super(paramPersonalizationTaskExecutor, paramPersonalizationTaskListener, 5);
    this.mNextTask = paramMergeTask;
    this.mDestination = paramFile;
  }
  
  private void success()
  {
    this.mNextTask.setLocation(this.mDestination.getAbsolutePath());
    schedule(this.mNextTask, 0);
  }
  
  public void compute()
    throws TaskFailException
  {
    String str = getLocation();
    this.mDownloaderListener = new DownloaderProgressListener()
    {
      public void onCancelled() {}
      
      public void onComplete()
      {
        DownloadTask.this.success();
      }
      
      public void onFailed(Downloader.DownloadFailedReason paramAnonymousDownloadFailedReason) {}
      
      public void onProgress(int paramAnonymousInt1, int paramAnonymousInt2) {}
      
      public void onStarted() {}
    };
    HttpGet localHttpGet = new HttpGet(str);
    localHttpGet.addHeader("Accept-Encoding", "gzip,deflate");
    Downloader localDownloader = new Downloader(localHttpGet, SSLClientFactory.createHttpClient(new BasicHttpParams()), this.mDestination, this.mDownloaderListener);
    localDownloader.run();
    if (localDownloader.getStatus().equals(Downloader.DownloadStatus.FAILED)) {
      throw new TaskFailException("Failed to download the file.", PersonalizationFailedReason.OTHER);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.DownloadTask
 * JD-Core Version:    0.7.0.1
 */