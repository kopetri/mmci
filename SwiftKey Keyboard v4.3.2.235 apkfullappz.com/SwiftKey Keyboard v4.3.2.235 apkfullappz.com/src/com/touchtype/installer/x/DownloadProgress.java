package com.touchtype.installer.x;

import android.content.res.Resources;
import android.widget.Toast;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;

public final class DownloadProgress
  implements Runnable
{
  private XInstaller mInstaller;
  private int mPercent;
  
  DownloadProgress(int paramInt, XInstaller paramXInstaller)
  {
    this.mPercent = paramInt;
    this.mInstaller = paramXInstaller;
  }
  
  public void run()
  {
    if (!this.mInstaller.mDownloadingCancelled)
    {
      this.mInstaller.mDownloadProgressUpdating = true;
      if (this.mInstaller.mActiveView != null)
      {
        this.mInstaller.mActiveView.hideSummary();
        this.mInstaller.mActiveView.showProgress();
        this.mInstaller.mActiveView.hideChevron();
        this.mInstaller.mActiveView.setTitle(this.mInstaller.getResources().getString(2131296361));
        this.mInstaller.mActiveView.setProgress(this.mPercent);
        this.mInstaller.mActiveView.postInvalidate();
      }
      this.mInstaller.mDownloadProgressUpdating = false;
      if (this.mInstaller.mShowMoreLanguagesToast)
      {
        int i = this.mInstaller.mLanguagePackManager.getMaxLanguagePacks();
        if (i > 1)
        {
          String str1 = this.mInstaller.getResources().getString(2131296407);
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = this.mInstaller.mProductName;
          arrayOfObject[1] = Integer.valueOf(i);
          String str2 = String.format(str1, arrayOfObject);
          Toast.makeText(this.mInstaller, str2, 1).show();
        }
        this.mInstaller.mShowMoreLanguagesToast = false;
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.DownloadProgress
 * JD-Core Version:    0.7.0.1
 */