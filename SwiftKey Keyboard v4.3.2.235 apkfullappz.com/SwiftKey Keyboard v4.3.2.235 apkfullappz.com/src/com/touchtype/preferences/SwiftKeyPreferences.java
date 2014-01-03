package com.touchtype.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface SwiftKeyPreferences
  extends SharedPreferences
{
  public abstract boolean areLiveLanguagesEnabled();
  
  public abstract void clearFlag(String paramString);
  
  public abstract AutoCompleteMode getAutoCompleteMode();
  
  public abstract boolean getBoolean(String paramString, boolean paramBoolean);
  
  public abstract String getCloudDeviceId();
  
  public abstract Set<String> getEnabledLocales();
  
  public abstract Map<String, String> getEventsTriggered();
  
  public abstract float getFloat(String paramString, float paramFloat);
  
  public abstract AutoCompleteMode getHardKBAutoCompleteMode();
  
  public abstract int getHardKeyboardLayout();
  
  public abstract int getInt(String paramString, int paramInt);
  
  public abstract boolean getKeyboardDockedState(Context paramContext);
  
  public abstract LayoutData.LayoutMap getKeyboardLayout();
  
  public abstract int getKeyboardLayoutStyle(Context paramContext);
  
  public abstract String getKeyboardTheme();
  
  public abstract long getLong(String paramString, long paramLong);
  
  public abstract int getLongPressTimeOut();
  
  public abstract List<String> getRecentSharingApps();
  
  public abstract String getReferralData();
  
  public abstract Map<String, Integer> getSettingsMenuItemsOpened();
  
  public abstract int getSoundVolume();
  
  public abstract String getString(String paramString1, String paramString2);
  
  public abstract int getSyncFrequency();
  
  public abstract long getSyncScheduledTime();
  
  public abstract TouchTypeStats getTouchTypeStats();
  
  public abstract boolean getUsePCLayoutStyle(boolean paramBoolean);
  
  public abstract int getVibrationDuration();
  
  public abstract boolean isArrowsEnabled();
  
  public abstract boolean isAutoCapitalizationEnabled();
  
  public abstract boolean isCloudAccountSetup();
  
  public abstract boolean isCloudMarketingAllowed();
  
  public abstract boolean isFlagSet(String paramString);
  
  public abstract boolean isFlowEnabled();
  
  public abstract boolean isHardKBAutoCapitalizationEnabled();
  
  public abstract boolean isHardKBPunctuationCompletionEnabled();
  
  public abstract boolean isHardKBSmartPunctuationEnabled();
  
  public abstract boolean isLicenseValid();
  
  public abstract boolean isPredictionEnabled();
  
  public abstract boolean isQuickPeriodOn();
  
  public abstract boolean isSendStatsEnabled();
  
  public abstract boolean isSoundFeedbackEnabled();
  
  public abstract boolean isSyncEnabled();
  
  public abstract boolean isSyncWifiOnly();
  
  public abstract boolean isVibrateEnabled();
  
  public abstract boolean isVoiceEnabled();
  
  public abstract boolean putBoolean(String paramString, boolean paramBoolean);
  
  public abstract boolean putLong(String paramString, long paramLong);
  
  public abstract boolean putString(String paramString1, String paramString2);
  
  public abstract boolean removeKey(String paramString);
  
  public abstract void setCloudAccountIdentifier(String paramString);
  
  public abstract void setCloudAccountIsSetup(boolean paramBoolean);
  
  public abstract void setCloudDeviceId(String paramString);
  
  public abstract void setCloudMarketingAllowed(boolean paramBoolean);
  
  public abstract void setEnabledLocales(Set<String> paramSet);
  
  public abstract void setLiveLanguagesEnabled(boolean paramBoolean);
  
  public abstract void setSyncEnabled(boolean paramBoolean);
  
  public abstract void setSyncFrequency(int paramInt);
  
  public abstract void setSyncScheduledTime(long paramLong);
  
  public abstract void setSyncWifiOnly(boolean paramBoolean);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.SwiftKeyPreferences
 * JD-Core Version:    0.7.0.1
 */