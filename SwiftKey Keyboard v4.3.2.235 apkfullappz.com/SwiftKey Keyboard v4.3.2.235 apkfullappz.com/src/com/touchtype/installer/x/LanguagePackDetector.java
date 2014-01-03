package com.touchtype.installer.x;

import android.os.Handler;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import java.util.Vector;

public final class LanguagePackDetector
  implements Runnable
{
  private final XInstaller mInstaller;
  
  public LanguagePackDetector(XInstaller paramXInstaller)
  {
    this.mInstaller = paramXInstaller;
  }
  
  public void run()
  {
    if (this.mInstaller.mLanguagePackWatcher == null)
    {
      LogUtil.w(XInstaller.TAG, "LanguagePackWatcher is null - re-creating");
      this.mInstaller.mLanguagePackWatcher = new Handler();
    }
    while ((this.mInstaller.mLanguagePackManager != null) && (this.mInstaller.mLanguagePackManager.getLanguagePacks() != null) && (this.mInstaller.mLanguagePackManager.getLanguagePacks().size() > 0))
    {
      this.mInstaller.mDownloadingLanguagesInProgress = false;
      this.mInstaller.setDownloadingInProgress();
      return;
      this.mInstaller.mLanguagePackWatcher.removeCallbacks(this.mInstaller.mLanguagePackWatcherTimedTask);
    }
    if (this.mInstaller.mLanguagePackRetryCount < 5L)
    {
      XInstaller localXInstaller = this.mInstaller;
      localXInstaller.mLanguagePackRetryCount = (1 + localXInstaller.mLanguagePackRetryCount);
      this.mInstaller.mDownloadingLanguagesInProgress = true;
      this.mInstaller.setDownloadingInProgress();
      this.mInstaller.mLanguagePackManager.onCreate();
      this.mInstaller.mLanguagePackManager.downloadConfiguration();
      LogUtil.w(XInstaller.TAG, "retry language pack downloads");
      this.mInstaller.mLanguagePackWatcher.postDelayed(this, 7000L);
      return;
    }
    this.mInstaller.mDownloadingLanguagesInProgress = false;
    this.mInstaller.showDialogFragment(2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.LanguagePackDetector
 * JD-Core Version:    0.7.0.1
 */