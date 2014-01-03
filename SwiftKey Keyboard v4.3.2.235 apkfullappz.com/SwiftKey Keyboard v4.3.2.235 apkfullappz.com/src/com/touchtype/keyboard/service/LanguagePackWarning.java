package com.touchtype.keyboard.service;

import com.touchtype.social.UserNotificationManager;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import java.util.Vector;

class LanguagePackWarning
{
  private static final String TAG = LanguagePackWarning.class.getSimpleName();
  private boolean mDeferNotification;
  private boolean mIsPreinstalled;
  private boolean mPredictorReady;
  private FluencyServiceProxy mServiceProxy;
  
  public LanguagePackWarning(FluencyServiceProxy paramFluencyServiceProxy, boolean paramBoolean)
  {
    this.mServiceProxy = paramFluencyServiceProxy;
    this.mIsPreinstalled = paramBoolean;
  }
  
  private void doNotification()
  {
    LanguagePackManager localLanguagePackManager = this.mServiceProxy.getLanguagePackManager();
    if (localLanguagePackManager != null)
    {
      if ((localLanguagePackManager.getEnabledLanguagePacks().size() == 0) && (!this.mIsPreinstalled)) {
        this.mServiceProxy.getUserNotificationManager().displayLanguageNotification(2131297066);
      }
      return;
    }
    LogUtil.w(TAG, "LanguagePackManager was null");
  }
  
  public void keyboardVisible()
  {
    if (this.mPredictorReady)
    {
      doNotification();
      return;
    }
    this.mDeferNotification = true;
  }
  
  public void predictorConnected()
  {
    this.mPredictorReady = true;
    if (this.mDeferNotification)
    {
      doNotification();
      this.mDeferNotification = false;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.service.LanguagePackWarning
 * JD-Core Version:    0.7.0.1
 */