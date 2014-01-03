package com.touchtype.installer.x;

import android.content.Context;
import android.os.Handler;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackListener;
import java.util.Vector;

public final class InstallerLanguagePackListener
  implements LanguagePackListener
{
  private XInstaller mInstaller;
  
  public InstallerLanguagePackListener(XInstaller paramXInstaller)
  {
    this.mInstaller = paramXInstaller;
  }
  
  public boolean isDeferrable()
  {
    return false;
  }
  
  public boolean onChange(Context paramContext)
  {
    if ((!this.mInstaller.mLanguagePackManager.isReady()) || (this.mInstaller.mLanguagePackManager.getLanguagePacks() == null) || ((this.mInstaller.mLanguagePackManager.getLanguagePacks().size() == 0) && (!this.mInstaller.mDownloadingLanguagesInProgress) && (this.mInstaller.mLanguagePackWatcher != null)))
    {
      this.mInstaller.mLanguagePackWatcherTimedTask = new LanguagePackDetector(this.mInstaller);
      this.mInstaller.mLanguagePackWatcher.post(this.mInstaller.mLanguagePackWatcherTimedTask);
    }
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.InstallerLanguagePackListener
 * JD-Core Version:    0.7.0.1
 */