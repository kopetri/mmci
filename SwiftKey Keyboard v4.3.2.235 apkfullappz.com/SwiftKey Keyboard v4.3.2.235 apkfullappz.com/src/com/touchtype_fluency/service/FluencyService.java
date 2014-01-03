package com.touchtype_fluency.service;

import android.content.Context;
import com.touchtype.social.UserNotificationManager;
import com.touchtype_fluency.Session;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutManager;
import java.io.File;

public abstract interface FluencyService
{
  public abstract DynamicModelHandler getDynamicModelHandler();
  
  public abstract File getFolder();
  
  public abstract LanguagePackManager getLanguagePackManager();
  
  public abstract LayoutManager getLayoutManager();
  
  public abstract Predictor getPredictor();
  
  public abstract Session getSession();
  
  public abstract UserNotificationManager getUserNotificationManager();
  
  public abstract boolean isReady();
  
  public abstract void saveFluencyMetrics();
  
  public abstract void showManagementUI(Context paramContext);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.FluencyService
 * JD-Core Version:    0.7.0.1
 */