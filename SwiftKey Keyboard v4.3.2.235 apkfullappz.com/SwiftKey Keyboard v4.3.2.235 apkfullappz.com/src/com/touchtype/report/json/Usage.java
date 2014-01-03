package com.touchtype.report.json;

import android.content.Context;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype_fluency.service.DynamicModelHandler;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class Usage
{
  @SerializedName("dmLastModified")
  private long mDynamicModelLastModified;
  @SerializedName("firstRun")
  private long mFirstRun;
  @SerializedName("keyboard")
  private Keyboard mKeyboard;
  @SerializedName("restores")
  private int mRestores;
  @SerializedName("settings")
  private Settings mSettings;
  @SerializedName("shared")
  private Shared mShared;
  @SerializedName("shortcutPopup")
  private ShortcutPopup mShortcutPopup;
  @SerializedName("swipe")
  private Swipe mSwipe;
  @SerializedName("symbols")
  private Symbols mSymbols;
  @SerializedName("voiceRecognition")
  private VoiceRecognition mVoiceRecognition;
  
  static int[] convertStringsToInts(String[] paramArrayOfString)
  {
    if (paramArrayOfString == null)
    {
      arrayOfInt = new int[0];
      return arrayOfInt;
    }
    int[] arrayOfInt = new int[paramArrayOfString.length];
    int i = 0;
    while (i < paramArrayOfString.length) {
      try
      {
        arrayOfInt[i] = Integer.valueOf(paramArrayOfString[i]).intValue();
        i++;
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    return new int[0];
  }
  
  public static Usage newInstance(Context paramContext, SwiftKeyPreferences paramSwiftKeyPreferences, TouchTypeStats paramTouchTypeStats)
  {
    Usage localUsage = new Usage();
    DynamicModelHandler localDynamicModelHandler = new DynamicModelHandler(paramContext);
    localUsage.mKeyboard = Keyboard.newInstance(paramSwiftKeyPreferences, paramTouchTypeStats);
    localUsage.mShared = Shared.newInstance(paramSwiftKeyPreferences, paramTouchTypeStats);
    localUsage.mShortcutPopup = ShortcutPopup.newInstance(paramTouchTypeStats);
    localUsage.mSettings = Settings.newInstance(paramSwiftKeyPreferences, paramTouchTypeStats);
    localUsage.mSwipe = Swipe.newInstance(paramTouchTypeStats);
    localUsage.mSymbols = Symbols.newInstance(paramTouchTypeStats);
    localUsage.mVoiceRecognition = VoiceRecognition.newInstance(paramTouchTypeStats);
    localUsage.mRestores = paramTouchTypeStats.getStatisticInt("stats_successful_restores");
    localUsage.mDynamicModelLastModified = localDynamicModelHandler.getUserModelLastModified();
    localUsage.mFirstRun = (paramSwiftKeyPreferences.getLong("first_run_time", 0L) / 1000L);
    return localUsage;
  }
  
  static Map<String, Integer> zipToMap(String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    if ((paramArrayOfString == null) || (paramArrayOfInt == null)) {
      return null;
    }
    int i = Math.min(paramArrayOfString.length, paramArrayOfInt.length);
    ImmutableMap.Builder localBuilder = ImmutableMap.builder();
    for (int j = 0; j < i; j++) {
      localBuilder.put(paramArrayOfString[j], Integer.valueOf(paramArrayOfInt[j]));
    }
    return localBuilder.build();
  }
  
  static final class Keyboard
  {
    @SerializedName("backToCloseKbUses")
    private int mBackToclosekbUses;
    @SerializedName("dockedLandscapeCompactOpenedCount")
    private int mDockedLandscapeCompactOpenedCount;
    @SerializedName("dockedLandscapeFullOpenedCount")
    private int mDockedLandscapeFullOpenedCount;
    @SerializedName("dockedLandscapeSplitOpenedCount")
    private int mDockedLandscapeSplitOpenedCount;
    @SerializedName("dockedPortraitCompactOpenedCount")
    private int mDockedPortraitCompactOpenedCount;
    @SerializedName("dockedPortraitFullOpenedCount")
    private int mDockedPortraitFullOpenedCount;
    @SerializedName("dockedPortraitSplitOpenedCount")
    private int mDockedPortraitSplitOpenedCount;
    @SerializedName("dragDocked")
    private int mDragDocked;
    @SerializedName("eventTriggered")
    private Map<String, String> mEventTriggeredInteractions;
    @SerializedName("floatingLandscapeCompactOpenedCount")
    private int mFloatingLandscapeCompactOpenedCount;
    @SerializedName("floatingLandscapeFullOpenedCount")
    private int mFloatingLandscapeFullOpenedCount;
    @SerializedName("floatingLandscapeSplitOpenedCount")
    private int mFloatingLandscapeSplitOpenedCount;
    @SerializedName("floatingPortraitCompactOpenedCount")
    private int mFloatingPortraitCompactOpenedCount;
    @SerializedName("floatingPortraitFullOpenedCount")
    private int mFloatingPortraitFullOpenedCount;
    @SerializedName("floatingPortraitSplitOpenedCount")
    private int mFloatingPortraitSplitOpenedCount;
    @SerializedName("landscape")
    private int mLandscape;
    @SerializedName("languageLayoutChanges")
    private JsonObject mLanguageLayoutChanges;
    @SerializedName("modeChangeImmediate")
    private int mModeChangeImmediate;
    @SerializedName("movedToBottom")
    private int mMovedToBottom;
    @SerializedName("movedToMiddle")
    private int mMovedToMiddle;
    @SerializedName("movedToTop")
    private int mMovedToTop;
    @SerializedName("opens")
    private int mOpens;
    @SerializedName("portrait")
    private int mPortrait;
    @SerializedName("predictionsOpens")
    private int mPredictionOpens;
    @SerializedName("switchedFullCompact")
    private int mSwitchedFullCompact;
    
    public static Keyboard newInstance(SwiftKeyPreferences paramSwiftKeyPreferences, TouchTypeStats paramTouchTypeStats)
    {
      Keyboard localKeyboard = new Keyboard();
      localKeyboard.mBackToclosekbUses = paramTouchTypeStats.getStatisticInt("stats_back_toclosekb_uses");
      localKeyboard.mPredictionOpens = paramTouchTypeStats.getStatisticInt("stats_predictions_opens");
      localKeyboard.mOpens = paramTouchTypeStats.getStatisticInt("stats_keyboard_opens");
      JsonObject localJsonObject = new JsonParser().parse(paramTouchTypeStats.getLanguageLayoutChangesString()).getAsJsonObject();
      if (localJsonObject.entrySet().isEmpty()) {
        localJsonObject = null;
      }
      localKeyboard.mLanguageLayoutChanges = localJsonObject;
      localKeyboard.mLandscape = paramTouchTypeStats.getStatisticInt("stats_orientation_landscape");
      localKeyboard.mPortrait = paramTouchTypeStats.getStatisticInt("stats_orientation_portrait");
      Map localMap1 = paramSwiftKeyPreferences.getEventsTriggered();
      boolean bool = localMap1.isEmpty();
      Map localMap2 = null;
      if (bool) {}
      for (;;)
      {
        localKeyboard.mEventTriggeredInteractions = localMap2;
        localKeyboard.mMovedToTop = paramTouchTypeStats.getStatisticInt("stats_keyboard_moved_to_top");
        localKeyboard.mMovedToMiddle = paramTouchTypeStats.getStatisticInt("stats_keyboard_moved_to_middle");
        localKeyboard.mMovedToBottom = paramTouchTypeStats.getStatisticInt("stats_keyboard_moved_to_bottom");
        localKeyboard.mSwitchedFullCompact = paramTouchTypeStats.getStatisticInt("stats_keyboard_switched_full_compact");
        localKeyboard.mDragDocked = paramTouchTypeStats.getStatisticInt("stats_keyboard_drag_docked");
        localKeyboard.mDockedLandscapeFullOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_docked_landscape_full_opened_count");
        localKeyboard.mDockedLandscapeSplitOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_docked_landscape_split_opened_count");
        localKeyboard.mDockedLandscapeCompactOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_docked_landscape_compact_opened_count");
        localKeyboard.mDockedPortraitFullOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_docked_portrait_full_opened_count");
        localKeyboard.mDockedPortraitSplitOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_docked_portrait_split_opened_count");
        localKeyboard.mDockedPortraitCompactOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_docked_portrait_compact_opened_count");
        localKeyboard.mFloatingLandscapeFullOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_floating_landscape_full_opened_count");
        localKeyboard.mFloatingLandscapeSplitOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_floating_landscape_split_opened_count");
        localKeyboard.mFloatingLandscapeCompactOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_floating_landscape_compact_opened_count");
        localKeyboard.mFloatingPortraitFullOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_floating_portrait_full_opened_count");
        localKeyboard.mFloatingPortraitSplitOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_floating_portrait_split_opened_count");
        localKeyboard.mFloatingPortraitCompactOpenedCount = paramTouchTypeStats.getStatisticInt("stats_keyboard_floating_portrait_compact_opened_count");
        localKeyboard.mModeChangeImmediate = paramTouchTypeStats.getStatisticInt("stats_keyboard_mode_change_immediate");
        return localKeyboard;
        localMap2 = localMap1;
      }
    }
  }
  
  static final class Settings
  {
    @SerializedName("itemsOpened")
    private Map<String, Integer> mItemsOpened;
    @SerializedName("opens")
    private int mOpens;
    
    public static Settings newInstance(SwiftKeyPreferences paramSwiftKeyPreferences, TouchTypeStats paramTouchTypeStats)
    {
      Settings localSettings = new Settings();
      localSettings.mOpens = paramTouchTypeStats.getStatisticInt("stats_settings_opens");
      Map localMap = paramSwiftKeyPreferences.getSettingsMenuItemsOpened();
      if (localMap.isEmpty()) {
        localMap = null;
      }
      localSettings.mItemsOpened = localMap;
      return localSettings;
    }
  }
  
  static final class Shared
  {
    @SerializedName("distanceFlowed")
    private int mDistanceFlowed;
    @SerializedName("efficiency")
    private int mEfficiency;
    @SerializedName("heatmap")
    private int mHeatmap;
    @SerializedName("keystrokesSaved")
    private int mKeystrokesSaved;
    @SerializedName("lettersCorrected")
    private int mLettersCorrected;
    @SerializedName("mostRecentApps")
    private List<String> mMostRecentApps;
    @SerializedName("wordsCompleted")
    private int mWordsCompleted;
    @SerializedName("wordsFlowed")
    private int mWordsFlowed;
    @SerializedName("wordsPredicted")
    private int mWordsPredicted;
    
    public static Shared newInstance(SwiftKeyPreferences paramSwiftKeyPreferences, TouchTypeStats paramTouchTypeStats)
    {
      Shared localShared = new Shared();
      localShared.mDistanceFlowed = paramTouchTypeStats.getStatisticInt("stats_shared_distance_flowed");
      localShared.mEfficiency = paramTouchTypeStats.getStatisticInt("stats_shared_efficiency");
      localShared.mHeatmap = paramTouchTypeStats.getStatisticInt("stats_shared_heatmap");
      localShared.mKeystrokesSaved = paramTouchTypeStats.getStatisticInt("stats_shared_keystrokes_saved");
      localShared.mLettersCorrected = paramTouchTypeStats.getStatisticInt("stats_shared_letters_corrected");
      localShared.mWordsCompleted = paramTouchTypeStats.getStatisticInt("stats_shared_words_completed");
      localShared.mWordsFlowed = paramTouchTypeStats.getStatisticInt("stats_shared_words_flowed");
      localShared.mWordsPredicted = paramTouchTypeStats.getStatisticInt("stats_shared_words_predicted");
      List localList = paramSwiftKeyPreferences.getRecentSharingApps();
      if (localList.isEmpty()) {
        localList = null;
      }
      localShared.mMostRecentApps = localList;
      return localShared;
    }
  }
  
  static final class ShortcutPopup
  {
    @SerializedName("closes")
    private int mCloses;
    @SerializedName("compactUses")
    private int mCompactUses;
    @SerializedName("dockUses")
    private int mDockUses;
    @SerializedName("fullUses")
    private int mFullUses;
    @SerializedName("inputMethodUses")
    private int mInputMethodUses;
    @SerializedName("resizeUses")
    private int mResizeUses;
    @SerializedName("settingsUses")
    private int mSettingsUses;
    @SerializedName("shareUses")
    private int mShareUses;
    @SerializedName("splitUses")
    private int mSplitUses;
    @SerializedName("supportUses")
    private int mSupportUses;
    @SerializedName("tutorialUses")
    private int mTutorialUses;
    @SerializedName("undockUses")
    private int mUndockUses;
    @SerializedName("voiceUses")
    private int mVoiceUses;
    @SerializedName("webUses")
    private int mWebUses;
    
    public static ShortcutPopup newInstance(TouchTypeStats paramTouchTypeStats)
    {
      ShortcutPopup localShortcutPopup = new ShortcutPopup();
      localShortcutPopup.mCloses = paramTouchTypeStats.getStatisticInt("stats_shortcut_popup_closes");
      localShortcutPopup.mInputMethodUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_popup_input_method_uses");
      localShortcutPopup.mSettingsUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_popup_settings_uses");
      localShortcutPopup.mShareUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_popup_share_uses");
      localShortcutPopup.mSupportUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_popup_support_uses");
      localShortcutPopup.mTutorialUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_popup_tutorial_uses");
      localShortcutPopup.mVoiceUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_popup_voice_uses");
      localShortcutPopup.mWebUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_popup_web_uses");
      localShortcutPopup.mResizeUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_resize_uses");
      localShortcutPopup.mUndockUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_undock_uses");
      localShortcutPopup.mDockUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_dock_uses");
      localShortcutPopup.mFullUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_full_uses");
      localShortcutPopup.mSplitUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_split_uses");
      localShortcutPopup.mCompactUses = paramTouchTypeStats.getStatisticInt("stats_shortcut_compact_uses");
      return localShortcutPopup;
    }
  }
  
  static final class Swipe
  {
    @SerializedName("downUses")
    private int mDownUses;
    @SerializedName("leftUses")
    private int mLeftUses;
    @SerializedName("spacebarGestureUses")
    private int mSpacebarGestureUses;
    @SerializedName("spacebarScrollUses")
    private int mSpacebarScrollUses;
    @SerializedName("upUses")
    private int mUpUses;
    
    public static Swipe newInstance(TouchTypeStats paramTouchTypeStats)
    {
      Swipe localSwipe = new Swipe();
      localSwipe.mDownUses = paramTouchTypeStats.getStatisticInt("stats_swipedown_uses");
      localSwipe.mLeftUses = paramTouchTypeStats.getStatisticInt("stats_swipeleft_uses");
      localSwipe.mUpUses = paramTouchTypeStats.getStatisticInt("stats_swipeup_uses");
      localSwipe.mSpacebarGestureUses = paramTouchTypeStats.getStatisticInt("stats_swipe_spacebar_gesture_uses");
      localSwipe.mSpacebarScrollUses = paramTouchTypeStats.getStatisticInt("stats_swipe_spacebar_scroll_uses");
      return localSwipe;
    }
  }
  
  static final class Symbols
  {
    @SerializedName("primaryOpens")
    private int mPrimaryOpens;
    @SerializedName("secondaryOpens")
    private int mSecondaryOpens;
    
    public static Symbols newInstance(TouchTypeStats paramTouchTypeStats)
    {
      Symbols localSymbols = new Symbols();
      localSymbols.mPrimaryOpens = paramTouchTypeStats.getStatisticInt("stats_symbols_primary_opens");
      localSymbols.mSecondaryOpens = paramTouchTypeStats.getStatisticInt("stats_symbols_secondary_opens");
      return localSymbols;
    }
  }
  
  static final class VoiceRecognition
  {
    @SerializedName("predictions")
    private Map<String, Integer> mPredictions;
    @SerializedName("uses")
    private int mUses;
    
    public static VoiceRecognition newInstance(TouchTypeStats paramTouchTypeStats)
    {
      VoiceRecognition localVoiceRecognition = new VoiceRecognition();
      localVoiceRecognition.mUses = paramTouchTypeStats.getStatisticInt("stats_voice_recognition_uses");
      localVoiceRecognition.mPredictions = Usage.zipToMap(new String[] { "top", "mid", "bot" }, Usage.convertStringsToInts(paramTouchTypeStats.getVoicePredictionsStatistic().split(",")));
      return localVoiceRecognition;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.Usage
 * JD-Core Version:    0.7.0.1
 */