package com.touchtype.report.json;

import android.content.Context;
import android.provider.Settings.Secure;
import com.google.gson.annotations.SerializedName;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.report.TouchTypeStats;

final class Marketing
{
  @SerializedName("marketReferrer")
  private String mAndroidMarketReferrer;
  @SerializedName("defaultIme")
  private String mDefaultIme;
  @SerializedName("distanceFlowed")
  private float mDistanceFlowed;
  @SerializedName("marketName")
  private String mMarketName;
  @SerializedName("timeKbOpened")
  private long mTimeKbOpened;
  @SerializedName("timeTyping")
  private long mTimeTyping;
  
  public static Marketing newInstance(Context paramContext, SwiftKeyPreferences paramSwiftKeyPreferences, TouchTypeStats paramTouchTypeStats)
  {
    Marketing localMarketing = new Marketing();
    ReferrerInfo localReferrerInfo = ReferrerInfo.newInstance(paramContext);
    localMarketing.mDistanceFlowed = paramTouchTypeStats.getStatisticFloat("stats_distance_flowed");
    localMarketing.mAndroidMarketReferrer = localReferrerInfo.getAndroidMarketReferrer();
    localMarketing.mMarketName = localReferrerInfo.getMarketName();
    localMarketing.mDefaultIme = Settings.Secure.getString(paramContext.getContentResolver(), "default_input_method");
    localMarketing.mTimeKbOpened = paramTouchTypeStats.getStatisticLong("stats_time_keyboard_opened");
    localMarketing.mTimeTyping = paramTouchTypeStats.getStatisticLong("stats_time_spent_typing");
    return localMarketing;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.Marketing
 * JD-Core Version:    0.7.0.1
 */