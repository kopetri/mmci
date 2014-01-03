package com.touchtype.settings;

import android.os.Handler;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;

public final class LanguageManagerReadyPoller
  implements Runnable
{
  private LanguagePreferenceConfiguration mLanguagePreferenceConfiguration;
  private int mRetries = 0;
  
  public LanguageManagerReadyPoller(LanguagePreferenceConfiguration paramLanguagePreferenceConfiguration)
  {
    this.mLanguagePreferenceConfiguration = paramLanguagePreferenceConfiguration;
  }
  
  public void run()
  {
    LanguagePackManager localLanguagePackManager = this.mLanguagePreferenceConfiguration.getLanguagePackManager();
    boolean bool = false;
    if (localLanguagePackManager != null) {
      bool = localLanguagePackManager.isReady();
    }
    if (!bool)
    {
      if (this.mRetries < 5)
      {
        this.mLanguagePreferenceConfiguration.getHandler().postDelayed(this, 1000L);
        this.mRetries = (1 + this.mRetries);
        return;
      }
      this.mLanguagePreferenceConfiguration.showLPMFailure();
      return;
    }
    this.mLanguagePreferenceConfiguration.createWidgetsImpl();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.LanguageManagerReadyPoller
 * JD-Core Version:    0.7.0.1
 */