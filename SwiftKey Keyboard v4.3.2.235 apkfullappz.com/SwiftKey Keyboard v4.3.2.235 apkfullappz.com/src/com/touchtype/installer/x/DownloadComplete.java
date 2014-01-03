package com.touchtype.installer.x;

import android.content.res.Resources;
import android.widget.Toast;
import com.touchtype.TouchTypeUtilities;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;

public final class DownloadComplete
  implements Runnable
{
  private boolean mCancelled;
  private boolean mDigestOk;
  private XInstaller mInstaller;
  private boolean mSuccess;
  
  DownloadComplete(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, XInstaller paramXInstaller)
  {
    this.mSuccess = paramBoolean1;
    this.mCancelled = paramBoolean2;
    this.mInstaller = paramXInstaller;
    this.mDigestOk = paramBoolean3;
  }
  
  public void run()
  {
    this.mInstaller.mActiveView.showSummary();
    this.mInstaller.mActiveView.hideProgress();
    this.mInstaller.mActiveView.setTitle(this.mInstaller.getResources().getString(2131296359));
    this.mInstaller.mDownloadingLanguagePackInProgress = false;
    this.mInstaller.mDownloadingCancelled = this.mCancelled;
    if (this.mCancelled)
    {
      this.mInstaller.mActiveView.setSummary(this.mInstaller.getResources().getString(2131296365));
      Toast.makeText(this.mInstaller, 2131296422, 1).show();
    }
    for (;;)
    {
      this.mInstaller.mLanguageProgressListener = null;
      return;
      if (!this.mDigestOk)
      {
        this.mInstaller.mActiveView.setSummary(this.mInstaller.getResources().getString(2131296365));
        Toast.makeText(this.mInstaller, 2131296421, 1).show();
        this.mInstaller.mLanguagePackManager.downloadConfiguration();
      }
      else if (!this.mSuccess)
      {
        this.mInstaller.mActiveView.setSummary(this.mInstaller.getResources().getString(2131296368));
        this.mInstaller.showDialogFragment(3);
      }
      else
      {
        this.mInstaller.enableLanguagePack(false);
        if (this.mInstaller.getCurrentFrame() == 0)
        {
          this.mInstaller.nextFrame();
          if (TouchTypeUtilities.isTouchTypeEnabled(this.mInstaller)) {
            this.mInstaller.nextFrame();
          }
          if (TouchTypeUtilities.checkIMEStatus(this.mInstaller))
          {
            this.mInstaller.nextFrame();
            if (!this.mInstaller.getResources().getBoolean(2131492889))
            {
              this.mInstaller.nextFrame();
              if (!this.mInstaller.getResources().getBoolean(2131492922)) {
                this.mInstaller.nextFrame();
              }
            }
          }
        }
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.DownloadComplete
 * JD-Core Version:    0.7.0.1
 */