package com.touchtype.report.json;

import android.content.Context;
import android.content.res.Resources;
import com.google.gson.annotations.SerializedName;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype_fluency.service.FluencyMetrics;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;

public final class UserStatsReport
{
  @SerializedName("accuracy")
  private final Accuracy mAccuracy;
  @SerializedName("debug")
  private final Debug mDebug;
  @SerializedName("deviceInfo")
  private final DeviceInfo mDeviceInfo;
  @SerializedName("marketing")
  private final Marketing mMarketing;
  @SerializedName("performance")
  private final Performance mPerformance;
  @SerializedName("preferences")
  private final Preferences mPreferences;
  @SerializedName("software")
  private final Software mSoftware;
  @SerializedName("usage")
  private final Usage mUsage;
  
  public UserStatsReport(Context paramContext, FluencyMetrics paramFluencyMetrics, LanguagePackManager paramLanguagePackManager)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    TouchTypeStats localTouchTypeStats = localTouchTypePreferences.getTouchTypeStats();
    this.mAccuracy = Accuracy.newInstance(localTouchTypeStats);
    this.mDeviceInfo = DeviceInfo.newInstance(paramContext);
    this.mMarketing = Marketing.newInstance(paramContext, localTouchTypePreferences, localTouchTypeStats);
    this.mPerformance = Performance.newInstance(paramContext, paramFluencyMetrics, paramLanguagePackManager);
    this.mPreferences = Preferences.newInstance(paramContext, localTouchTypePreferences);
    this.mSoftware = Software.newInstance(paramContext);
    this.mUsage = Usage.newInstance(paramContext, localTouchTypePreferences, localTouchTypeStats);
    if (paramContext.getResources().getBoolean(2131492916)) {}
    for (Debug localDebug = Debug.newInstance(localTouchTypePreferences, localTouchTypeStats);; localDebug = null)
    {
      this.mDebug = localDebug;
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.UserStatsReport
 * JD-Core Version:    0.7.0.1
 */