package com.touchtype.report.json;

import android.content.Context;
import android.content.res.Resources;
import com.google.gson.annotations.SerializedName;
import com.touchtype.keyboard.inputeventmodel.keytranslation.KeyTranslationLayerImpl;
import com.touchtype.preferences.AutoCompleteMode;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import java.util.Map;

final class Preferences
{
  @SerializedName("activeLayout")
  private String mActiveLayout;
  @SerializedName("activeTheme")
  private String mActiveTheme;
  @SerializedName("arrowKeys")
  private boolean mArrowKeys;
  @SerializedName("audioHapticFeedback")
  private AudioHapticFeedback mAudioHapticFeedback;
  @SerializedName("autocaps")
  private boolean mAutocaps;
  @SerializedName("cloud")
  private Cloud mCloud;
  @SerializedName("docked")
  private String mDocked;
  @SerializedName("flowSetting")
  private Boolean mFlowSetting;
  @SerializedName("hwKeyboard")
  private HardwareKeyboard mHwKeyboard;
  @SerializedName("layoutStyle")
  private String mLayoutStyle;
  @SerializedName("longpressTime")
  private int mLongpressTime;
  @SerializedName("nonDefaultSizeCount")
  private int mNonDefaultSizeCount;
  @SerializedName("pcLayoutEnabled")
  private boolean mPcLayoutEnabled;
  @SerializedName("quickDot")
  private boolean mQuickDot;
  @SerializedName("showForeignChars")
  private boolean mShowForeignChars;
  @SerializedName("spaceBarBehaviour")
  private String mSpaceBarBehaviour;
  @SerializedName("tipsAchievements")
  private boolean mTipsAchievements;
  @SerializedName("voiceControl")
  private boolean mVoiceControl;
  
  static String getAutoCompleteMode(AutoCompleteMode paramAutoCompleteMode)
  {
    switch (1.$SwitchMap$com$touchtype$preferences$AutoCompleteMode[paramAutoCompleteMode.ordinal()])
    {
    default: 
      return "none";
    case 1: 
      return "word";
    case 2: 
      return "auto";
    }
    return "none";
  }
  
  static String getKeyboardDockedPreference(Context paramContext, SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    if (paramSwiftKeyPreferences.getKeyboardDockedState(paramContext)) {
      return "docked";
    }
    return "undocked";
  }
  
  static String getKeyboardLayoutStylePreference(Context paramContext, SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    switch (paramSwiftKeyPreferences.getKeyboardLayoutStyle(paramContext))
    {
    default: 
      return "";
    case 1: 
      return "regular";
    case 2: 
      return "split";
    }
    return "compact";
  }
  
  static String getSyncFrequency(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "weekly";
    case 0: 
      return "realtime";
    case 1: 
      return "daily";
    }
    return "weekly";
  }
  
  public static Preferences newInstance(Context paramContext, SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    Preferences localPreferences = new Preferences();
    localPreferences.mAudioHapticFeedback = AudioHapticFeedback.newInstance(paramSwiftKeyPreferences);
    localPreferences.mHwKeyboard = HardwareKeyboard.newInstance(paramContext, paramSwiftKeyPreferences);
    localPreferences.mSpaceBarBehaviour = getAutoCompleteMode(paramSwiftKeyPreferences.getAutoCompleteMode());
    localPreferences.mQuickDot = paramSwiftKeyPreferences.isQuickPeriodOn();
    localPreferences.mAutocaps = paramSwiftKeyPreferences.isAutoCapitalizationEnabled();
    localPreferences.mVoiceControl = paramSwiftKeyPreferences.isVoiceEnabled();
    localPreferences.mLongpressTime = paramSwiftKeyPreferences.getLongPressTimeOut();
    localPreferences.mTipsAchievements = paramSwiftKeyPreferences.getBoolean(paramContext.getString(2131296740), paramContext.getResources().getBoolean(2131492883));
    localPreferences.mArrowKeys = paramSwiftKeyPreferences.isArrowsEnabled();
    localPreferences.mActiveTheme = paramSwiftKeyPreferences.getKeyboardTheme();
    localPreferences.mActiveLayout = paramSwiftKeyPreferences.getKeyboardLayout().getLayoutName();
    localPreferences.mLayoutStyle = getKeyboardLayoutStylePreference(paramContext, paramSwiftKeyPreferences);
    localPreferences.mDocked = getKeyboardDockedPreference(paramContext, paramSwiftKeyPreferences);
    localPreferences.mNonDefaultSizeCount = paramSwiftKeyPreferences.getInt("stats_shortcut_resize_uses", 0);
    localPreferences.mShowForeignChars = paramSwiftKeyPreferences.getBoolean(paramContext.getString(2131296319), paramContext.getResources().getBoolean(2131492930));
    if (ProductConfiguration.isCloudBuild(paramContext)) {}
    for (Cloud localCloud = Cloud.newInstance(paramSwiftKeyPreferences);; localCloud = null)
    {
      localPreferences.mCloud = localCloud;
      boolean bool = ProductConfiguration.isFlowBuild(paramContext);
      Boolean localBoolean = null;
      if (bool) {
        localBoolean = Boolean.valueOf(paramSwiftKeyPreferences.isFlowEnabled());
      }
      localPreferences.mFlowSetting = localBoolean;
      localPreferences.mPcLayoutEnabled = paramSwiftKeyPreferences.getBoolean("pref_keyboard_use_pc_layout_key", paramContext.getResources().getBoolean(2131492874));
      return localPreferences;
    }
  }
  
  static final class AudioHapticFeedback
  {
    @SerializedName("feedback")
    private boolean mFeedback;
    @SerializedName("feedbackDuration")
    private int mFeedbackDuration;
    @SerializedName("keypressSound")
    private boolean mKeypressSound;
    @SerializedName("volume")
    private int mVolume;
    
    public static AudioHapticFeedback newInstance(SwiftKeyPreferences paramSwiftKeyPreferences)
    {
      AudioHapticFeedback localAudioHapticFeedback = new AudioHapticFeedback();
      localAudioHapticFeedback.mVolume = paramSwiftKeyPreferences.getSoundVolume();
      localAudioHapticFeedback.mKeypressSound = paramSwiftKeyPreferences.isSoundFeedbackEnabled();
      localAudioHapticFeedback.mFeedback = paramSwiftKeyPreferences.isVibrateEnabled();
      localAudioHapticFeedback.mFeedbackDuration = paramSwiftKeyPreferences.getVibrationDuration();
      return localAudioHapticFeedback;
    }
  }
  
  static final class Cloud
  {
    @SerializedName("accountSetup")
    private boolean mAccountSetup;
    @SerializedName("deviceId")
    private String mDeviceId;
    @SerializedName("marketingAllowed")
    private boolean mMarketingAllowed;
    @SerializedName("setupFromUpdate")
    private boolean mSetupFromUpdate;
    @SerializedName("syncEnabled")
    private boolean mSyncEnabled;
    @SerializedName("syncFreq")
    private String mSyncFreq;
    @SerializedName("syncWifiOnly")
    private boolean mSyncWifiOnly;
    
    public static Cloud newInstance(SwiftKeyPreferences paramSwiftKeyPreferences)
    {
      Cloud localCloud = new Cloud();
      localCloud.mAccountSetup = paramSwiftKeyPreferences.isCloudAccountSetup();
      localCloud.mSyncEnabled = paramSwiftKeyPreferences.isSyncEnabled();
      localCloud.mSyncFreq = Preferences.getSyncFrequency(paramSwiftKeyPreferences.getSyncFrequency());
      localCloud.mSyncWifiOnly = paramSwiftKeyPreferences.isSyncWifiOnly();
      localCloud.mSetupFromUpdate = paramSwiftKeyPreferences.getBoolean("cloud_account_setup_from_update", false);
      localCloud.mDeviceId = paramSwiftKeyPreferences.getCloudDeviceId();
      localCloud.mMarketingAllowed = paramSwiftKeyPreferences.isCloudMarketingAllowed();
      return localCloud;
    }
  }
  
  static final class HardwareKeyboard
  {
    @SerializedName("autocaps")
    private boolean mAutocaps;
    @SerializedName("layout")
    private String mLayout;
    @SerializedName("punctuationCompletion")
    private boolean mPunctuationCompletion;
    @SerializedName("smartPunctuation")
    private boolean mSmartPunctuation;
    @SerializedName("spaceBarBehaviour")
    private String mSpaceBarBehaviour;
    
    public static HardwareKeyboard newInstance(Context paramContext, SwiftKeyPreferences paramSwiftKeyPreferences)
    {
      HardwareKeyboard localHardwareKeyboard = new HardwareKeyboard();
      localHardwareKeyboard.mAutocaps = paramSwiftKeyPreferences.isHardKBAutoCapitalizationEnabled();
      localHardwareKeyboard.mPunctuationCompletion = paramSwiftKeyPreferences.isHardKBPunctuationCompletionEnabled();
      localHardwareKeyboard.mSmartPunctuation = paramSwiftKeyPreferences.isHardKBSmartPunctuationEnabled();
      localHardwareKeyboard.mSpaceBarBehaviour = Preferences.getAutoCompleteMode(paramSwiftKeyPreferences.getHardKBAutoCompleteMode());
      localHardwareKeyboard.mLayout = ((String)KeyTranslationLayerImpl.TRANSLATOR_LAYOUT.get(Integer.valueOf(paramSwiftKeyPreferences.getHardKeyboardLayout())));
      return localHardwareKeyboard;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.Preferences
 * JD-Core Version:    0.7.0.1
 */