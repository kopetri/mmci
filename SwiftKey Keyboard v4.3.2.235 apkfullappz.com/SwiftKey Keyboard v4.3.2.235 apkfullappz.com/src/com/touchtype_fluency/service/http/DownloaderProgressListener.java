package com.touchtype_fluency.service.http;

public abstract interface DownloaderProgressListener
{
  public abstract void onCancelled();
  
  public abstract void onComplete();
  
  public abstract void onFailed(Downloader.DownloadFailedReason paramDownloadFailedReason);
  
  public abstract void onProgress(int paramInt1, int paramInt2);
  
  public abstract void onStarted();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.http.DownloaderProgressListener
 * JD-Core Version:    0.7.0.1
 */