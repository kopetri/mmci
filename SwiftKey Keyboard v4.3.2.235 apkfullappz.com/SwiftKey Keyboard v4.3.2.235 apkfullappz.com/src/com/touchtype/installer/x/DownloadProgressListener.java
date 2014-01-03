package com.touchtype.installer.x;

import com.touchtype.TouchTypeUtilities;
import com.touchtype_fluency.service.ProgressListener;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;

public final class DownloadProgressListener
  implements ProgressListener
{
  private int mCurrentProgress = -1;
  private final XInstaller mInstaller;
  
  public DownloadProgressListener(XInstaller paramXInstaller)
  {
    this.mInstaller = paramXInstaller;
  }
  
  public void onComplete(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    if (this.mInstaller != null)
    {
      TouchTypeUtilities.storePreference(this.mInstaller, "installer_downloaded_language", this.mInstaller.mSelectedLanguage.getName());
      this.mInstaller.runOnUiThread(new DownloadComplete(paramBoolean1, paramBoolean2, paramBoolean3, this.mInstaller));
    }
  }
  
  public void onProgress(int paramInt1, int paramInt2)
  {
    int i = paramInt1 * 100 / paramInt2;
    if ((i != this.mCurrentProgress) && (!this.mInstaller.mDownloadProgressUpdating) && (!this.mInstaller.mDownloadingCancelled)) {
      this.mInstaller.runOnUiThread(new DownloadProgress(i, this.mInstaller));
    }
    this.mCurrentProgress = i;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.DownloadProgressListener
 * JD-Core Version:    0.7.0.1
 */