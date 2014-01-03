package com.touchtype.preferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.touchtype.keyboard.view.KeyboardState;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.settings.InputMethodsPreferenceConfiguration;
import com.touchtype.util.DeviceUtils;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TouchTypePreferences
  implements SwiftKeyPreferences
{
  private static final String TAG;
  private static TouchTypePreferences instance;
  private final Map<String, Object> cache;
  private final SharedPreferences eventsPreferences;
  private final Resources mResources;
  private final TouchTypeStats mTouchTypeStats;
  private final SharedPreferences settingsOpenedPreferences;
  private final SharedPreferences sharedPreferences;
  
  static
  {
    if (!TouchTypePreferences.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      TAG = TouchTypePreferences.class.getSimpleName();
      return;
    }
  }
  
  private TouchTypePreferences(Context paramContext)
  {
    this.mResources = paramContext.getResources();
    this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.eventsPreferences = paramContext.getSharedPreferences("events", 0);
    this.settingsOpenedPreferences = paramContext.getSharedPreferences("stats_settings_opens", 0);
    this.cache = new HashMap();
    this.mTouchTypeStats = new TouchTypeStats(this, paramContext);
  }
  
  @TargetApi(9)
  private boolean commitOrApply(SharedPreferences.Editor paramEditor)
  {
    if (Build.VERSION.SDK_INT >= 9)
    {
      paramEditor.apply();
      return true;
    }
    return paramEditor.commit();
  }
  
  private AutoCompleteMode correctAutoCompleteValues(int paramInt)
  {
    if (paramInt < AutoCompleteMode.values().length) {
      return AutoCompleteMode.values()[paramInt];
    }
    AutoCompleteMode localAutoCompleteMode = AutoCompleteMode.values()[getDefaultAutoCompleteMode()];
    switch (paramInt)
    {
    default: 
      return localAutoCompleteMode;
    case 2131165688: 
    case 2131231224: 
      setAutoCompleteMode(AutoCompleteMode.AUTOCOMPLETEMODE_DISABLED);
      return AutoCompleteMode.AUTOCOMPLETEMODE_DISABLED;
    case 2131165689: 
    case 2131231225: 
      setAutoCompleteMode(AutoCompleteMode.AUTOCOMPLETEMODE_ENABLED_WHEN_WORD_STARTED);
      return AutoCompleteMode.AUTOCOMPLETEMODE_ENABLED_WHEN_WORD_STARTED;
    }
    setAutoCompleteMode(AutoCompleteMode.AUTOCOMPLETEMODE_ENABLED_WITH_AUTOSELECT);
    return AutoCompleteMode.AUTOCOMPLETEMODE_ENABLED_WITH_AUTOSELECT;
  }
  
  public static TouchTypePreferences getInstance(Context paramContext)
  {
    try
    {
      if (instance == null) {
        instance = new TouchTypePreferences(paramContext);
      }
      TouchTypePreferences localTouchTypePreferences = instance;
      return localTouchTypePreferences;
    }
    finally {}
  }
  
  private boolean getPreLockScreenDockStateLandscape(Context paramContext)
  {
    return getBoolean("pref_pre_lock_screen_override_dock_state_landscape", true);
  }
  
  private boolean getPreLockScreenDockStatePortrait(Context paramContext)
  {
    return getBoolean("pref_pre_lock_screen_override_dock_state_portrait", true);
  }
  
  private String getScaleKey(Context paramContext, String paramString)
  {
    return paramString + orientationSuffix(paramContext);
  }
  
  private String orientationSuffix(Context paramContext)
  {
    return orientationSuffix(paramContext, DeviceUtils.isDeviceInLandscape(paramContext));
  }
  
  private String orientationSuffix(Context paramContext, boolean paramBoolean)
  {
    if (paramBoolean) {
      return "_landscape";
    }
    return "_portrait";
  }
  
  public boolean areLiveLanguagesEnabled()
  {
    return this.sharedPreferences.getBoolean("pref_screen_live_language_key", false);
  }
  
  public void clearCache()
  {
    this.cache.clear();
  }
  
  public void clearFlag(String paramString)
  {
    putBoolean(paramString, false);
  }
  
  public boolean contains(String paramString)
  {
    return this.sharedPreferences.contains(paramString);
  }
  
  public SharedPreferences.Editor edit()
  {
    return this.sharedPreferences.edit();
  }
  
  public Map<String, ?> getAll()
  {
    return this.sharedPreferences.getAll();
  }
  
  public AutoCompleteMode getAutoCompleteMode()
  {
    return correctAutoCompleteValues(getInt(this.mResources.getString(2131296765), getDefaultAutoCompleteMode()));
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    Object localObject = this.cache.get(paramString);
    if (localObject == null)
    {
      localObject = Boolean.valueOf(this.sharedPreferences.getBoolean(paramString, paramBoolean));
      this.cache.put(paramString, localObject);
    }
    assert ((localObject instanceof Boolean));
    return ((Boolean)localObject).booleanValue();
  }
  
  public String getCloudDeviceId()
  {
    return getString("cloud_account_device_id", "");
  }
  
  public float[] getCurrentCandidatePadding(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    float f1;
    if (getKeyboardLayoutStyle(paramContext) == 1) {
      f1 = localResources.getFraction(2131361935, 1, 1);
    }
    for (float f2 = localResources.getFraction(2131361937, 1, 1);; f2 = localResources.getFraction(2131361938, 1, 1))
    {
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[1] = f1;
      arrayOfFloat[0] = f2;
      return arrayOfFloat;
      f1 = localResources.getFraction(2131361936, 1, 1);
    }
  }
  
  public String getCurrentKeyboardStateByName(Context paramContext)
  {
    boolean bool = getKeyboardDockedState(paramContext);
    int i = getKeyboardLayoutStyle(paramContext);
    if (bool)
    {
      switch (i)
      {
      default: 
        return KeyboardState.FULL_DOCKED.getName();
      case 3: 
        return KeyboardState.COMPACT_DOCKED.getName();
      }
      return KeyboardState.SPLIT_DOCKED.getName();
    }
    switch (i)
    {
    default: 
      return KeyboardState.FULL_FLOATING.getName();
    case 3: 
      return KeyboardState.COMPACT_FLOATING.getName();
    }
    return KeyboardState.SPLIT_FLOATING.getName();
  }
  
  public int getDefaultAutoCompleteMode()
  {
    return this.mResources.getInteger(2131558426);
  }
  
  public int getDockedCompactHandedness(Context paramContext)
  {
    return getInt("pref_compact_docked_handedness" + orientationSuffix(paramContext), 0);
  }
  
  public Set<String> getEnabledLocales()
  {
    return Sets.newHashSet(Splitter.on(",").omitEmptyStrings().split(getString("list_enabled_locales", "")));
  }
  
  public int getEventTriggered(String paramString)
  {
    return this.eventsPreferences.getInt(paramString, -1);
  }
  
  public Map<String, String> getEventsTriggered()
  {
    HashMap localHashMap = Maps.newHashMap();
    Iterator localIterator = this.eventsPreferences.getAll().entrySet().iterator();
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = "";
      switch (((Integer)localEntry.getValue()).intValue())
      {
      }
      for (;;)
      {
        localHashMap.put(localEntry.getKey(), str);
        break;
        str = "not_run";
        continue;
        str = "run";
        continue;
        str = "shown";
        continue;
        str = "shared";
        continue;
        str = "ignored";
        continue;
        str = "do_not_bother";
        continue;
        str = "cleared";
        continue;
        str = "rated";
        continue;
        str = "selected";
      }
    }
    return localHashMap;
  }
  
  public float getFloat(String paramString, float paramFloat)
  {
    Object localObject = this.cache.get(paramString);
    if (localObject == null)
    {
      localObject = Float.valueOf(this.sharedPreferences.getFloat(paramString, paramFloat));
      this.cache.put(paramString, localObject);
    }
    assert ((localObject instanceof Float));
    return ((Float)localObject).floatValue();
  }
  
  public AutoCompleteMode getHardKBAutoCompleteMode()
  {
    return correctAutoCompleteValues(getInt(this.mResources.getString(2131296767), getHardKBDefaultAutoCompleteMode()));
  }
  
  public int getHardKBDefaultAutoCompleteMode()
  {
    return this.mResources.getInteger(2131558427);
  }
  
  public int getHardKeyboardLayout()
  {
    int i = this.mResources.getInteger(2131558425);
    if (InputMethodsPreferenceConfiguration.hideOldLayoutSelector()) {
      return i;
    }
    return getInt(this.mResources.getString(2131296781), i);
  }
  
  public int getInt(String paramString, int paramInt)
  {
    Object localObject = this.cache.get(paramString);
    if (localObject == null)
    {
      localObject = Integer.valueOf(this.sharedPreferences.getInt(paramString, paramInt));
      this.cache.put(paramString, localObject);
    }
    assert ((localObject instanceof Integer));
    return ((Integer)localObject).intValue();
  }
  
  public boolean getKeyboardDockedState(Context paramContext)
  {
    return getBoolean("pref_keyboard_layout_docked_state" + orientationSuffix(paramContext), true);
  }
  
  public boolean getKeyboardDockedState(Context paramContext, boolean paramBoolean)
  {
    return getBoolean("pref_keyboard_layout_docked_state" + orientationSuffix(paramContext, paramBoolean), true);
  }
  
  public LayoutData.LayoutMap getKeyboardLayout()
  {
    String str = getString("pref_keyboard_layoutlist_key", null);
    if (str == null)
    {
      String[] arrayOfString = this.mResources.getString(2131296318).split("_");
      if (arrayOfString.length == 2) {}
      for (LayoutData.LayoutMap localLayoutMap2 = LayoutData.getLayoutFromLanguage(arrayOfString[0], arrayOfString[1]);; localLayoutMap2 = LayoutData.getLayoutForCurrentLocale())
      {
        setKeyboardLayout(localLayoutMap2);
        LogUtil.w(TAG, "Could not find layout, defaulted to " + localLayoutMap2.getLayoutName());
        return localLayoutMap2;
      }
    }
    LayoutData.LayoutMap localLayoutMap1 = LayoutData.get(str);
    if (localLayoutMap1 == null)
    {
      LogUtil.w(TAG, "Couldn't get layout from preference with name " + str + ". Using QWERTY instead");
      localLayoutMap1 = LayoutData.LayoutMap.QWERTY;
    }
    return localLayoutMap1;
  }
  
  public int getKeyboardLayoutResourceId(Context paramContext, boolean paramBoolean)
  {
    return getKeyboardLayout().getLayoutResId(getUsePCLayoutStyle(paramContext.getResources().getBoolean(2131492874)));
  }
  
  public int getKeyboardLayoutStyle(Context paramContext)
  {
    return getInt("pref_keyboard_layout_landscape_style_key" + orientationSuffix(paramContext), 1);
  }
  
  public int getKeyboardPosition(Context paramContext, String paramString, int paramInt)
  {
    return getInt(paramString + orientationSuffix(paramContext), paramInt);
  }
  
  public int getKeyboardScale(Context paramContext, String paramString)
  {
    return getInt(getScaleKey(paramContext, paramString), 2);
  }
  
  public String getKeyboardTheme()
  {
    String str1 = this.mResources.getString(2131296791);
    String str2 = getString("pref_keyboard_theme_key", "NO_THEME");
    if (str2.equals("NO_THEME")) {
      str2 = str1;
    }
    for (;;)
    {
      return str2;
      try
      {
        int i = Integer.parseInt(str2);
        str2 = this.mResources.getStringArray(2131623947)[i];
        putString("pref_keyboard_theme_key", str2);
        label65:
        str3 = getString("pref_keyboard_themelist_key", null);
        if (str3 == null) {
          continue;
        }
        if (str3.equalsIgnoreCase("Dark"))
        {
          str2 = this.mResources.getString(2131296794);
          putString("pref_keyboard_theme_key", str2);
          putString("pref_keyboard_themelist_key", null);
          return str2;
        }
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        for (;;)
        {
          String str3;
          str2 = str1;
          continue;
          if (str3.equalsIgnoreCase("Light")) {
            str2 = this.mResources.getString(2131296802);
          } else if (str3.equalsIgnoreCase("Neon")) {
            str2 = this.mResources.getString(2131296798);
          }
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        break label65;
      }
    }
  }
  
  public long getLiveLanguageLastUpdateTime()
  {
    return getLong("live_language_last_update_time", 0L);
  }
  
  public boolean getLockScreenDockOverride(Context paramContext)
  {
    return getBoolean("pref_lock_screen_dock_override", false);
  }
  
  public long getLong(String paramString, long paramLong)
  {
    Object localObject = this.cache.get(paramString);
    if (localObject == null)
    {
      localObject = Long.valueOf(this.sharedPreferences.getLong(paramString, paramLong));
      this.cache.put(paramString, localObject);
    }
    assert ((localObject instanceof Long));
    return ((Long)localObject).longValue();
  }
  
  public int getLongPressTimeOut()
  {
    return getInt("long_press_timeout", this.mResources.getInteger(2131558403));
  }
  
  public List<String> getRecentSharingApps()
  {
    String str = getString("social_recent_sharing_apps", "");
    if (Strings.isNullOrEmpty(str)) {
      return new ArrayList();
    }
    return new ArrayList(Arrays.asList(str.split(",")));
  }
  
  public String getReferralData()
  {
    return getString("pref_referrer", null);
  }
  
  public Map<String, Integer> getSettingsMenuItemsOpened()
  {
    return this.settingsOpenedPreferences.getAll();
  }
  
  public boolean getShowSplitNumpad(Context paramContext, boolean paramBoolean)
  {
    if ((!DeviceUtils.isDeviceInLandscape(paramContext)) || ((0xF & paramContext.getResources().getConfiguration().screenLayout) != 4)) {
      return false;
    }
    return getBoolean("pref_keyboard_show_split_numpad", paramBoolean);
  }
  
  public int getSoundVolume()
  {
    return getInt("pref_sound_feedback_slider_key", this.mResources.getInteger(2131558418));
  }
  
  public String getString(String paramString1, String paramString2)
  {
    Object localObject = this.cache.get(paramString1);
    if (localObject == null)
    {
      localObject = this.sharedPreferences.getString(paramString1, paramString2);
      this.cache.put(paramString1, localObject);
    }
    assert ((localObject instanceof String));
    return (String)localObject;
  }
  
  @TargetApi(11)
  public Set<String> getStringSet(String paramString, Set<String> paramSet)
  {
    Object localObject = this.cache.get(paramString);
    if (localObject == null)
    {
      localObject = this.sharedPreferences.getStringSet(paramString, paramSet);
      this.cache.put(paramString, localObject);
    }
    assert ((localObject instanceof Set));
    return (Set)localObject;
  }
  
  public int getSyncFailuresCount()
  {
    return getInt("sync_failures_count", 0);
  }
  
  public int getSyncFrequency()
  {
    return Integer.parseInt(this.sharedPreferences.getString("pref_sync_frequency_key", String.valueOf(2)));
  }
  
  public long getSyncScheduledTime()
  {
    return getLong("sync_scheduled_time", 0L);
  }
  
  public TouchTypeStats getTouchTypeStats()
  {
    return this.mTouchTypeStats;
  }
  
  public boolean getUseAlternateView(boolean paramBoolean)
  {
    return getBoolean("pref_keyboard_show_alternate_view", paramBoolean);
  }
  
  public boolean getUsePCLayoutStyle(boolean paramBoolean)
  {
    return getBoolean("pref_keyboard_use_pc_layout_key", paramBoolean);
  }
  
  public int getVibrationDuration()
  {
    int i = 2131558421;
    if (Build.VERSION.SDK_INT >= 14) {
      i = 2131558422;
    }
    return getInt("pref_vibration_slider_key", this.mResources.getInteger(i));
  }
  
  public boolean isArrowsEnabled()
  {
    return getBoolean("pref_arrows_key", this.mResources.getBoolean(2131492873));
  }
  
  public boolean isAutoCapitalizationEnabled()
  {
    return getBoolean("pref_auto_caps", this.mResources.getBoolean(2131492880));
  }
  
  public boolean isBuildConfiguredForArrows()
  {
    return getBoolean("arrows_build_override_key", this.mResources.getBoolean(2131492879));
  }
  
  public boolean isCloudAccountSetup()
  {
    return getBoolean("cloud_account_setup", false);
  }
  
  public boolean isCloudMarketingAllowed()
  {
    return getBoolean(this.mResources.getString(2131296530), false);
  }
  
  public boolean isFlagSet(String paramString)
  {
    return getBoolean(paramString, true);
  }
  
  public boolean isFlowEnabled()
  {
    return (this.mResources.getBoolean(2131492922)) && (this.sharedPreferences.getBoolean("pref_flow_switch_key", this.mResources.getBoolean(2131492870)));
  }
  
  public boolean isHardKBAutoCapitalizationEnabled()
  {
    return getBoolean(this.mResources.getString(2131296768), this.mResources.getBoolean(2131492893));
  }
  
  public boolean isHardKBPunctuationCompletionEnabled()
  {
    return getBoolean(this.mResources.getString(2131296774), this.mResources.getBoolean(2131492894));
  }
  
  public boolean isHardKBSmartPunctuationEnabled()
  {
    return getBoolean(this.mResources.getString(2131296771), this.mResources.getBoolean(2131492895));
  }
  
  public boolean isHardKeyboardSettingsNotificationEnabled()
  {
    return getBoolean(this.mResources.getString(2131296766), this.mResources.getBoolean(2131492896));
  }
  
  public boolean isLicenseValid()
  {
    return getBoolean("license_valid", true);
  }
  
  public boolean isPredictionEnabled()
  {
    return (!this.mResources.getBoolean(2131492923)) || (this.sharedPreferences.getBoolean("pref_predictions_switch_key", this.mResources.getBoolean(2131492869)));
  }
  
  public boolean isQuickPeriodOn()
  {
    return getBoolean(this.mResources.getString(2131296690), this.mResources.getBoolean(2131492886));
  }
  
  public boolean isSendErrorEnabled()
  {
    return getBoolean("send_errors_key", this.mResources.getBoolean(2131492865));
  }
  
  public boolean isSendStatsEnabled()
  {
    return getBoolean("pref_send_updates_key", this.mResources.getBoolean(2131492864));
  }
  
  public boolean isSoundFeedbackEnabled()
  {
    return getBoolean("pref_sound_feedback_on_key", this.mResources.getBoolean(2131492871));
  }
  
  public boolean isSwipeDownEnabled()
  {
    return getBoolean("pref_swipe_down_key", this.mResources.getBoolean(2131492881));
  }
  
  public boolean isSwipeUpEnabled()
  {
    return getBoolean("pref_swipe_up_key", this.mResources.getBoolean(2131492882));
  }
  
  public boolean isSyncEnabled()
  {
    return this.sharedPreferences.getBoolean("pref_sync_enabled_key", false);
  }
  
  public boolean isSyncPendingPull()
  {
    return getBoolean("sync_pending_pull", false);
  }
  
  public boolean isSyncWifiOnly()
  {
    return this.sharedPreferences.getBoolean("pref_sync_wifi_only_key", false);
  }
  
  public boolean isVibrateEnabled()
  {
    return getBoolean("pref_vibrate_on_key", this.mResources.getBoolean(2131492872));
  }
  
  public boolean isVoiceEnabled()
  {
    return getBoolean("pref_voice_enabled", this.mResources.getBoolean(2131492868));
  }
  
  public boolean putBoolean(String paramString, boolean paramBoolean)
  {
    this.cache.put(paramString, Boolean.valueOf(paramBoolean));
    synchronized (this.sharedPreferences.edit())
    {
      ???.putBoolean(paramString, paramBoolean);
      boolean bool = commitOrApply(???);
      return bool;
    }
  }
  
  public boolean putInt(String paramString, int paramInt)
  {
    this.cache.put(paramString, Integer.valueOf(paramInt));
    synchronized (this.sharedPreferences.edit())
    {
      ???.putInt(paramString, paramInt);
      boolean bool = commitOrApply(???);
      return bool;
    }
  }
  
  public boolean putLong(String paramString, long paramLong)
  {
    this.cache.put(paramString, Long.valueOf(paramLong));
    synchronized (this.sharedPreferences.edit())
    {
      ???.putLong(paramString, paramLong);
      boolean bool = commitOrApply(???);
      return bool;
    }
  }
  
  public boolean putString(String paramString1, String paramString2)
  {
    this.cache.put(paramString1, paramString2);
    synchronized (this.sharedPreferences.edit())
    {
      ???.putString(paramString1, paramString2);
      boolean bool = commitOrApply(???);
      return bool;
    }
  }
  
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    this.sharedPreferences.registerOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
  }
  
  public boolean removeKey(String paramString)
  {
    if (this.cache.containsKey(paramString)) {
      this.cache.remove(paramString);
    }
    synchronized (this.sharedPreferences.edit())
    {
      ???.remove(paramString);
      boolean bool = commitOrApply(???);
      return bool;
    }
  }
  
  public void restorePreLockScreenDockState(Context paramContext)
  {
    setKeyboardDockedState(paramContext, getPreLockScreenDockStatePortrait(paramContext), false);
    setKeyboardDockedState(paramContext, getPreLockScreenDockStateLandscape(paramContext), true);
  }
  
  public void saveStatistics()
  {
    this.mTouchTypeStats.saveStatistics();
  }
  
  public boolean setAutoCompleteMode(AutoCompleteMode paramAutoCompleteMode)
  {
    return putInt(this.mResources.getString(2131296765), paramAutoCompleteMode.ordinal());
  }
  
  public void setCloudAccountIdentifier(String paramString)
  {
    putString("cloud_account_identifier", paramString);
  }
  
  public void setCloudAccountIsSetup(boolean paramBoolean)
  {
    putBoolean("cloud_account_setup", paramBoolean);
  }
  
  public void setCloudDeviceId(String paramString)
  {
    putString("cloud_account_device_id", paramString);
  }
  
  public void setCloudMarketingAllowed(boolean paramBoolean)
  {
    putBoolean(this.mResources.getString(2131296530), paramBoolean);
  }
  
  public void setDockedCompactHandedness(Context paramContext, int paramInt)
  {
    putInt("pref_compact_docked_handedness" + orientationSuffix(paramContext), paramInt);
  }
  
  public void setEnabledLocales(Set<String> paramSet)
  {
    putString("list_enabled_locales", Joiner.on(",").join(paramSet));
  }
  
  public boolean setEventTriggered(String paramString, int paramInt)
  {
    return this.eventsPreferences.edit().putInt(paramString, paramInt).commit();
  }
  
  public boolean setHardKBAutoCompleteMode(AutoCompleteMode paramAutoCompleteMode)
  {
    return putInt(this.mResources.getString(2131296767), paramAutoCompleteMode.ordinal());
  }
  
  public void setHardKeyboardLayout(int paramInt)
  {
    putInt(this.mResources.getString(2131296781), paramInt);
  }
  
  public void setKeyboardDockedState(Context paramContext, boolean paramBoolean)
  {
    putBoolean("pref_keyboard_layout_docked_state" + orientationSuffix(paramContext), paramBoolean);
  }
  
  public void setKeyboardDockedState(Context paramContext, boolean paramBoolean1, boolean paramBoolean2)
  {
    putBoolean("pref_keyboard_layout_docked_state" + orientationSuffix(paramContext, paramBoolean2), paramBoolean1);
  }
  
  public void setKeyboardLayout(LayoutData.LayoutMap paramLayoutMap)
  {
    putString("pref_keyboard_layoutlist_key", paramLayoutMap.getLayoutName());
  }
  
  public void setKeyboardLayoutStyle(Context paramContext, int paramInt)
  {
    putInt("pref_keyboard_layout_landscape_style_key" + orientationSuffix(paramContext), paramInt);
  }
  
  public void setKeyboardPosition(Context paramContext, String paramString, int paramInt)
  {
    putInt(paramString + orientationSuffix(paramContext), paramInt);
  }
  
  public void setKeyboardScale(Context paramContext, String paramString, int paramInt)
  {
    putInt(getScaleKey(paramContext, paramString), paramInt);
  }
  
  public void setKeyboardTheme(String paramString)
  {
    putString("pref_keyboard_theme_key", paramString);
  }
  
  public void setLiveLanguagesEnabled(boolean paramBoolean)
  {
    putBoolean("pref_screen_live_language_key", paramBoolean);
  }
  
  public void setLockScreenDockOverride(Context paramContext, boolean paramBoolean)
  {
    putBoolean("pref_lock_screen_dock_override", paramBoolean);
    if (paramBoolean)
    {
      putBoolean("pref_pre_lock_screen_override_dock_state_portrait", getKeyboardDockedState(paramContext, false));
      putBoolean("pref_pre_lock_screen_override_dock_state_landscape", getKeyboardDockedState(paramContext, true));
      setKeyboardDockedState(paramContext, true, false);
      setKeyboardDockedState(paramContext, true, true);
    }
  }
  
  public boolean setRecentSharingApps(List<String> paramList)
  {
    return putString("social_recent_sharing_apps", Joiner.on(",").join(paramList));
  }
  
  public void setReferralData(String paramString)
  {
    putString("pref_referrer", paramString);
  }
  
  public boolean setSettingsMenuItemsOpened(String paramString)
  {
    int i = this.settingsOpenedPreferences.getInt(paramString, 0);
    return this.settingsOpenedPreferences.edit().putInt(paramString, i + 1).commit();
  }
  
  public void setSyncEnabled(boolean paramBoolean)
  {
    putBoolean("pref_sync_enabled_key", paramBoolean);
  }
  
  public void setSyncFailuresCount(int paramInt)
  {
    putInt("sync_failures_count", paramInt);
  }
  
  public void setSyncFrequency(int paramInt)
  {
    putString("pref_sync_frequency_key", String.valueOf(paramInt));
  }
  
  public void setSyncPendingPull(boolean paramBoolean)
  {
    putBoolean("sync_pending_pull", paramBoolean);
  }
  
  public void setSyncScheduledTime(long paramLong)
  {
    putLong("sync_scheduled_time", paramLong);
  }
  
  public void setSyncWifiOnly(boolean paramBoolean)
  {
    putBoolean("pref_sync_wifi_only_key", paramBoolean);
  }
  
  public void setUseAlternateView(boolean paramBoolean)
  {
    putBoolean("pref_keyboard_show_alternate_view", paramBoolean);
  }
  
  public void setUsePCLayoutStyle(boolean paramBoolean)
  {
    putBoolean("pref_keyboard_use_pc_layout_key", paramBoolean);
  }
  
  public void setWereLiveLanguagesEnabled(boolean paramBoolean)
  {
    putBoolean("pref_screen_last_live_language_key", paramBoolean);
  }
  
  public void setshowSplitNumpad(boolean paramBoolean)
  {
    putBoolean("pref_keyboard_show_split_numpad", paramBoolean);
  }
  
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
  }
  
  public boolean useSpecializedEmailKeyboard()
  {
    return this.sharedPreferences.getBoolean("use_specialized_email_keyboard", this.mResources.getBoolean(2131492876));
  }
  
  public boolean useSpecializedURLKeyboard()
  {
    return this.sharedPreferences.getBoolean("use_specialized_url_keyboard", this.mResources.getBoolean(2131492878));
  }
  
  public boolean wereLiveLanguagesEnabled()
  {
    return this.sharedPreferences.getBoolean("pref_screen_last_live_language_key", false);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.TouchTypePreferences
 * JD-Core Version:    0.7.0.1
 */