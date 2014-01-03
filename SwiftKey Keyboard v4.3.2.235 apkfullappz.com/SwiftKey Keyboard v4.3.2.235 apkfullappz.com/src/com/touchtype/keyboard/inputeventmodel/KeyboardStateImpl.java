package com.touchtype.keyboard.inputeventmodel;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import com.touchtype.TouchTypeUtilities;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.events.HardKeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype.keyboard.service.MetaKeyKeyListenerProxy;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.preferences.AutoCompleteMode;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.util.PackageInfoUtil;
import com.touchtype_fluency.ResultsFilter.CapitalizationHint;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.service.ImmutableExtractedText;
import junit.framework.Assert;

public class KeyboardStateImpl
  implements KeyboardState
{
  private static final String TAG = KeyboardStateImpl.class.getSimpleName();
  private boolean mAllowMoveCursorForInsertingPredictions = false;
  private boolean mAlwaysComposeWordByWord = false;
  private int mApiCompatibilityLevel = Build.VERSION.SDK_INT;
  private boolean mCompletionMode = false;
  private boolean mCursorMovementUpdatesSelection = true;
  private boolean mDeleteKeyDeletesTwoCharacters = false;
  private boolean mDisableQuickPeriod = false;
  private boolean mDoesntSupportMoveCursor;
  private boolean mDontUseJumpingCursorMarkers;
  private boolean mDumbInputMode = false;
  private int mEditorClass = 0;
  private int mEditorFlags = 0;
  private boolean mEditorIsMultiLineField = false;
  private boolean mEditorIsPasswordField = false;
  private boolean mEditorIsSearchField = false;
  private boolean mEditorIsUrlField = false;
  private int mEditorVariant = 0;
  private int mEnabledLanguagePacks = 0;
  private boolean mEvaluateInputShownUsedInsteadOfUpdateSelection = false;
  private boolean mExtractedTextWorks = false;
  private boolean mIsHardCapsLockOn = false;
  private boolean mIsMenuKeyPressed = false;
  private boolean mKeyPressModellingEnabled = true;
  private boolean mLayoutCyclingEnabled = false;
  private boolean mLicenseValid;
  private final ListenerManager mListenerManager;
  private final MetaKeyKeyListenerProxy mMetaKeyKeyListener;
  private boolean mMovingOverTrailingSpaceDoesntWork = false;
  private boolean mNeverAutocomplete = false;
  private boolean mNeverSetComposingRegion = false;
  private boolean mOverrideSpaceAssistance = false;
  private boolean mPredictionEnabled = false;
  private final SwiftKeyPreferences mPreferences;
  private TouchTypeSoftKeyboard.ShiftState mPreviousShiftState = TouchTypeSoftKeyboard.ShiftState.UNSHIFTED;
  private ResultsFilter.PredictionSearchType mSearchType = ResultsFilter.PredictionSearchType.NORMAL;
  private boolean mSetComposingRegionOnlyBeforeEdits = false;
  private KeyboardState.ShiftMode mShiftMode = KeyboardState.ShiftMode.NONE;
  private TouchTypeSoftKeyboard.ShiftState mShiftState = TouchTypeSoftKeyboard.ShiftState.UNSHIFTED;
  private boolean mShiftedAtStartOfFlow = false;
  private boolean mShouldFixWebKitInputConnection = false;
  private boolean mShouldReplaceSelectionWithoutDeleting = false;
  private KeyboardState.ChordedShift mSoftChordShift = KeyboardState.ChordedShift.NONE;
  private boolean mSoftShiftPressed = false;
  private boolean mSpaceAllowedToInsertPredictionAfterZeroWidthSpace = true;
  private boolean mStorageAvailable;
  private boolean mTextBeforeCursorWorks = false;
  private boolean mTextBoxSwitchingUpdatesSelection = true;
  private boolean mUseShortTextBeforeAfterCursor = false;
  private boolean mUseTelexConversionLayer = false;
  private boolean mUseTransactionsForSelectionEvents = true;
  private boolean mUseZeroWidthSpace = false;
  private boolean mUsingHardKB = false;
  
  public KeyboardStateImpl(ListenerManager paramListenerManager, SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    this.mListenerManager = paramListenerManager;
    this.mPreferences = paramSwiftKeyPreferences;
    this.mStorageAvailable = TouchTypeUtilities.isStorageAvailable();
    this.mMetaKeyKeyListener = new MetaKeyKeyListenerProxy();
  }
  
  private static KeyboardState.ShiftMode getNewShiftMode(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    KeyboardState.ShiftMode localShiftMode = KeyboardState.ShiftMode.NONE;
    if (paramBoolean) {
      switch (paramInt1)
      {
      }
    }
    for (;;)
    {
      Assert.assertNotNull(localShiftMode);
      return localShiftMode;
      if ((paramInt3 & 0x1000) != 0) {
        localShiftMode = KeyboardState.ShiftMode.CHARACTERS;
      } else if (((paramInt3 & 0x2000) != 0) || (paramInt2 == 96) || (paramInt2 == 112)) {
        localShiftMode = KeyboardState.ShiftMode.WORDS;
      } else if (((paramInt3 & 0x4000) != 0) || (paramInt2 == 64) || (paramInt2 == 80) || (paramInt2 == 48) || (paramInt2 == 160) || ((0x20000 & paramInt3) != 0) || ((0x40000 & paramInt3) != 0)) {
        localShiftMode = KeyboardState.ShiftMode.SENTENCES;
      }
    }
  }
  
  private TouchTypeSoftKeyboard.ShiftState getNewShiftState(KeyboardState.ShiftMode paramShiftMode, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    TouchTypeSoftKeyboard.ShiftState localShiftState;
    if (this.mIsHardCapsLockOn) {
      localShiftState = TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED;
    }
    for (;;)
    {
      Assert.assertNotNull(localShiftState);
      return localShiftState;
      switch (1.$SwitchMap$com$touchtype$keyboard$inputeventmodel$KeyboardState$ShiftMode[paramShiftMode.ordinal()])
      {
      default: 
        if ((paramInt1 == 128) || (paramInt1 == 224) || ((paramInt2 > 0) && (!paramBoolean))) {
          localShiftState = TouchTypeSoftKeyboard.ShiftState.UNSHIFTED;
        }
        break;
      case 1: 
        localShiftState = TouchTypeSoftKeyboard.ShiftState.UNSHIFTED;
        break;
      case 2: 
        localShiftState = TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED;
        continue;
        localShiftState = TouchTypeSoftKeyboard.ShiftState.SHIFTED;
      }
    }
  }
  
  private void resetHardCapsLock()
  {
    if (this.mIsHardCapsLockOn) {
      this.mIsHardCapsLockOn = false;
    }
  }
  
  private void startChording()
  {
    switch (1.$SwitchMap$com$touchtype$keyboard$service$TouchTypeSoftKeyboard$ShiftState[this.mShiftState.ordinal()])
    {
    default: 
      return;
    case 1: 
      this.mSoftChordShift = KeyboardState.ChordedShift.LOWER;
      return;
    case 2: 
      this.mSoftChordShift = KeyboardState.ChordedShift.UPPER;
      setShiftState(TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED);
      return;
    }
    this.mSoftChordShift = KeyboardState.ChordedShift.UPPER;
  }
  
  private void updateCapsLockState(KeyEvent paramKeyEvent)
  {
    if (Build.VERSION.SDK_INT >= 11) {
      updateCapsLockStateHoneycombOnwards(paramKeyEvent);
    }
  }
  
  @TargetApi(11)
  private void updateCapsLockStateHoneycombOnwards(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyCode() == 115)
    {
      if (!this.mIsHardCapsLockOn) {}
      for (boolean bool = true;; bool = false)
      {
        this.mIsHardCapsLockOn = bool;
        return;
      }
    }
    this.mIsHardCapsLockOn = paramKeyEvent.isCapsLockOn();
  }
  
  private void updateMenuKeyState(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyCode() == 82) {
      if (paramKeyEvent.getAction() != 0) {
        break label24;
      }
    }
    label24:
    for (boolean bool = true;; bool = false)
    {
      this.mIsMenuKeyPressed = bool;
      return;
    }
  }
  
  public boolean allowMoveCursorForInsertingPredictions()
  {
    return this.mAllowMoveCursorForInsertingPredictions;
  }
  
  public boolean backspaceWordDeleteToStart()
  {
    return this.mEditorIsPasswordField;
  }
  
  public boolean composeTextWordByWord()
  {
    return (this.mAlwaysComposeWordByWord) || (isPredictionEnabled());
  }
  
  public boolean cursorMovementUpdatesSelection()
  {
    return this.mCursorMovementUpdatesSelection;
  }
  
  public boolean deleteKeyDeletesTwoCharacters()
  {
    return this.mDeleteKeyDeletesTwoCharacters;
  }
  
  public boolean doesntSupportMoveCursor()
  {
    return this.mDoesntSupportMoveCursor;
  }
  
  public boolean dontUseJumpingCursorMarkers()
  {
    return this.mDontUseJumpingCursorMarkers;
  }
  
  public void enabledLanguagePacksChanged(int paramInt)
  {
    this.mEnabledLanguagePacks = paramInt;
  }
  
  public boolean evaluateInputShownUsedInsteadOfUpdateSelection()
  {
    return this.mEvaluateInputShownUsedInsteadOfUpdateSelection;
  }
  
  public boolean extractedTextWorks()
  {
    return this.mExtractedTextWorks;
  }
  
  public int getApiCompatibiltyLevel()
  {
    return this.mApiCompatibilityLevel;
  }
  
  public AutoCompleteMode getAutocompleteMode()
  {
    if (this.mUsingHardKB) {
      return this.mPreferences.getHardKBAutoCompleteMode();
    }
    return this.mPreferences.getAutoCompleteMode();
  }
  
  public ResultsFilter.CapitalizationHint getCapitalisationState(ResultsFilter.CapitalizationHint paramCapitalizationHint)
  {
    ResultsFilter.CapitalizationHint localCapitalizationHint = ResultsFilter.CapitalizationHint.LOWER_CASE;
    TouchTypeSoftKeyboard.ShiftState localShiftState = getShiftState();
    if ((localShiftState == TouchTypeSoftKeyboard.ShiftState.UNSHIFTED) && (paramCapitalizationHint != ResultsFilter.CapitalizationHint.LOWER_CASE)) {
      localCapitalizationHint = ResultsFilter.CapitalizationHint.INITIAL_UPPER_CASE;
    }
    do
    {
      return localCapitalizationHint;
      if ((localShiftState == TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED) || (getShiftMode() == KeyboardState.ShiftMode.CHARACTERS)) {
        return ResultsFilter.CapitalizationHint.UPPER_CASE;
      }
    } while (localShiftState != TouchTypeSoftKeyboard.ShiftState.SHIFTED);
    return ResultsFilter.CapitalizationHint.INITIAL_UPPER_CASE;
  }
  
  public ResultsFilter.CapitalizationHint getCapitalisationState(String paramString)
  {
    ResultsFilter.CapitalizationHint localCapitalizationHint = ResultsFilter.CapitalizationHint.LOWER_CASE;
    TouchTypeSoftKeyboard.ShiftState localShiftState = getShiftState();
    if ((localShiftState == TouchTypeSoftKeyboard.ShiftState.UNSHIFTED) && (paramString != null)) {
      if (((paramString.length() <= 0) || (!Character.isUpperCase(paramString.charAt(0)))) && (!this.mShiftedAtStartOfFlow)) {}
    }
    for (;;)
    {
      localCapitalizationHint = ResultsFilter.CapitalizationHint.INITIAL_UPPER_CASE;
      do
      {
        do
        {
          return localCapitalizationHint;
          if ((localShiftState == TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED) || (getShiftMode() == KeyboardState.ShiftMode.CHARACTERS)) {
            return ResultsFilter.CapitalizationHint.UPPER_CASE;
          }
        } while (localShiftState != TouchTypeSoftKeyboard.ShiftState.SHIFTED);
        if (this.mMetaKeyKeyListener.getMetaState(1) != 1) {
          break;
        }
      } while ((paramString == null) || (paramString.length() <= 0) || (!Character.isUpperCase(paramString.charAt(0))));
    }
  }
  
  public TouchTypeSoftKeyboard.ShiftState getInitialShiftState()
  {
    return getNewShiftState(this.mShiftMode, this.mEditorVariant, 0, false);
  }
  
  public int getMetaState()
  {
    return this.mMetaKeyKeyListener.getMetaState();
  }
  
  public PredictionsAvailability getPredictionsAvailability()
  {
    if ((this.mPreferences.isPredictionEnabled()) && (this.mPredictionEnabled))
    {
      if (this.mStorageAvailable) {
        return PredictionsAvailability.ENABLED;
      }
      return PredictionsAvailability.UNAVAILABLE_NO_SD_CARD;
    }
    return PredictionsAvailability.DISABLED;
  }
  
  public ResultsFilter.PredictionSearchType getSearchType()
  {
    return this.mSearchType;
  }
  
  public KeyboardState.ShiftMode getShiftMode()
  {
    return this.mShiftMode;
  }
  
  public TouchTypeSoftKeyboard.ShiftState getShiftState()
  {
    return this.mShiftState;
  }
  
  public String hintForCurrentField()
  {
    switch (this.mEditorVariant)
    {
    default: 
      return "";
    case 32: 
    case 208: 
      return "email";
    case 96: 
      return "name";
    case 64: 
      return "short_message";
    }
    return "uri";
  }
  
  public boolean isAutoCapitalizationEnabled()
  {
    if (this.mUsingHardKB) {
      return this.mPreferences.isHardKBAutoCapitalizationEnabled();
    }
    return this.mPreferences.isAutoCapitalizationEnabled();
  }
  
  public boolean isCompletionModeEnabled()
  {
    return this.mCompletionMode;
  }
  
  public boolean isDumbInputMode()
  {
    return this.mDumbInputMode;
  }
  
  public boolean isEditSpacingAssistanceDisabled()
  {
    return this.mOverrideSpaceAssistance;
  }
  
  public boolean isHardCapsLockOn()
  {
    return this.mIsHardCapsLockOn;
  }
  
  public boolean isHardKBSmartPunctuationEnabled()
  {
    return this.mPreferences.isHardKBSmartPunctuationEnabled();
  }
  
  public boolean isKeyPressModellingEnabled()
  {
    return this.mKeyPressModellingEnabled;
  }
  
  public boolean isLayoutCyclingEnabled()
  {
    return this.mLayoutCyclingEnabled;
  }
  
  public boolean isLicenseValid()
  {
    return this.mLicenseValid;
  }
  
  public boolean isMenuKeyPressed()
  {
    return this.mIsMenuKeyPressed;
  }
  
  public boolean isMultiLineField()
  {
    return this.mEditorIsMultiLineField;
  }
  
  public boolean isPredictionEnabled()
  {
    return getPredictionsAvailability() == PredictionsAvailability.ENABLED;
  }
  
  public boolean isQuickPeriodOn()
  {
    return (this.mPreferences.isQuickPeriodOn()) && (!this.mDisableQuickPeriod);
  }
  
  public boolean isSearchField()
  {
    return this.mEditorIsSearchField;
  }
  
  public boolean isSoftShiftPressed()
  {
    return this.mSoftShiftPressed;
  }
  
  public boolean isUrlBar()
  {
    return this.mEditorIsUrlField;
  }
  
  public boolean movingOverTrailingSpaceDoesntWork()
  {
    return this.mMovingOverTrailingSpaceDoesntWork;
  }
  
  public boolean neverSetComposingRegion()
  {
    if ((this.mAlwaysComposeWordByWord) && (this.mEditorClass != 1)) {}
    for (int i = 1;; i = 0)
    {
      boolean bool;
      if (!this.mNeverSetComposingRegion)
      {
        bool = false;
        if (i == 0) {}
      }
      else
      {
        bool = true;
      }
      return bool;
    }
  }
  
  public void notifyNewEditorInfo(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.mExtractedTextWorks = paramBoolean1;
    this.mTextBeforeCursorWorks = paramBoolean2;
    this.mUsingHardKB = paramBoolean4;
    EditorInfoUtils.AnnotatedEditorInfo localAnnotatedEditorInfo = EditorInfoUtils.correctEditorInfo(paramEditorInfo, paramPackageInfoUtil);
    EditorInfo localEditorInfo = localAnnotatedEditorInfo.editorInfo;
    this.mApiCompatibilityLevel = localAnnotatedEditorInfo.apiCompatibilityLevel;
    this.mOverrideSpaceAssistance = localAnnotatedEditorInfo.disablePredictionSpaceReuse;
    this.mCursorMovementUpdatesSelection = localAnnotatedEditorInfo.cursorMovementUpdatesSelection;
    this.mSetComposingRegionOnlyBeforeEdits = localAnnotatedEditorInfo.setComposingRegionOnlyBeforeEdits;
    this.mNeverSetComposingRegion = localAnnotatedEditorInfo.neverSetComposingRegion;
    this.mMovingOverTrailingSpaceDoesntWork = localAnnotatedEditorInfo.movingOverTrailingSpaceDoesntWork;
    this.mUseTransactionsForSelectionEvents = localAnnotatedEditorInfo.useTransactionsForSelectionEvents;
    this.mUseShortTextBeforeAfterCursor = localAnnotatedEditorInfo.useShortTextBeforeAfterCursor;
    this.mTextBoxSwitchingUpdatesSelection = localAnnotatedEditorInfo.textBoxSwitchingUpdatesSelection;
    this.mDoesntSupportMoveCursor = localAnnotatedEditorInfo.doesntSupportMoveCursor;
    this.mDontUseJumpingCursorMarkers = localAnnotatedEditorInfo.dontUseJumpingCursorMarkers;
    this.mEditorIsUrlField = localAnnotatedEditorInfo.isUrlBar;
    this.mEvaluateInputShownUsedInsteadOfUpdateSelection = localAnnotatedEditorInfo.evaluateInputShownUsedInsteadOfUpdateSelection;
    this.mShouldReplaceSelectionWithoutDeleting = localAnnotatedEditorInfo.shouldReplaceSelectionWithoutDeleting;
    this.mAllowMoveCursorForInsertingPredictions = localAnnotatedEditorInfo.allowMoveCursorForInsertingPredictions;
    this.mShouldFixWebKitInputConnection = localAnnotatedEditorInfo.shouldFixWebKitInputConnection;
    this.mDeleteKeyDeletesTwoCharacters = localAnnotatedEditorInfo.deleteKeyDeletesTwoCharacters;
    this.mSpaceAllowedToInsertPredictionAfterZeroWidthSpace = localAnnotatedEditorInfo.spaceAllowedToInsertPredictionAfterZeroWidthSpace;
    removeShiftedAtStartFlag();
    boolean bool1;
    boolean bool2;
    label293:
    boolean bool3;
    label313:
    boolean bool4;
    label475:
    int i;
    label501:
    int j;
    label512:
    boolean bool5;
    label525:
    KeyboardState.ShiftMode localShiftMode;
    int k;
    int m;
    boolean bool6;
    if (EditorInfoUtils.isValidEditorInfo(localEditorInfo))
    {
      this.mDumbInputMode = false;
      this.mEditorClass = (0xF & localEditorInfo.inputType);
      this.mEditorVariant = (0xFF0 & localEditorInfo.inputType);
      this.mEditorFlags = (0xFFF000 & localEditorInfo.inputType);
      if ((0xFF & localEditorInfo.imeOptions) == 3)
      {
        bool1 = true;
        this.mEditorIsSearchField = bool1;
        if ((this.mEditorVariant != 128) && (this.mEditorVariant != 224)) {
          break label601;
        }
        bool2 = true;
        this.mEditorIsPasswordField = bool2;
        if ((0x60000 & this.mEditorFlags) == 0) {
          break label607;
        }
        bool3 = true;
        this.mEditorIsMultiLineField = bool3;
        this.mShiftMode = getNewShiftMode(isAutoCapitalizationEnabled(), this.mEditorClass, this.mEditorVariant, this.mEditorFlags);
        if ((!isLicenseValid()) || (this.mPreferences.getInt("pref_install_state", 0) != -1) || (this.mEditorClass != 1) || (this.mEditorVariant == 32) || (this.mEditorVariant == 128) || (this.mEditorVariant == 144) || (this.mEditorVariant == 208) || (this.mEditorVariant == 224) || (((0x10000 & this.mEditorFlags) != 0) && (Build.VERSION.SDK_INT < 9)) || ((!this.mExtractedTextWorks) && ((!this.mTextBeforeCursorWorks) || (!localAnnotatedEditorInfo.enablePredictionsWhenOnlyTextBeforeCursorWorks))) || (localAnnotatedEditorInfo.forceDisablePredictions)) {
          break label613;
        }
        bool4 = true;
        this.mPredictionEnabled = bool4;
        if ((this.mEditorClass != 2) || (this.mEditorVariant != 16)) {
          break label619;
        }
        i = 1;
        if (this.mEditorClass != 3) {
          break label625;
        }
        j = 1;
        if ((i != 0) || (j != 0)) {
          break label631;
        }
        bool5 = true;
        this.mLayoutCyclingEnabled = bool5;
        localShiftMode = this.mShiftMode;
        k = this.mEditorVariant;
        m = localEditorInfo.initialSelStart;
        if (localEditorInfo.initialCapsMode == 0) {
          break label637;
        }
        bool6 = true;
      }
    }
    label561:
    for (TouchTypeSoftKeyboard.ShiftState localShiftState = getNewShiftState(localShiftMode, k, m, bool6);; localShiftState = TouchTypeSoftKeyboard.ShiftState.UNSHIFTED)
    {
      this.mListenerManager.notifyPredictionsEnabledListener(getPredictionsAvailability());
      setShiftState(localShiftState);
      return;
      bool1 = false;
      break;
      label601:
      bool2 = false;
      break label293;
      label607:
      bool3 = false;
      break label313;
      label613:
      bool4 = false;
      break label475;
      label619:
      i = 0;
      break label501;
      label625:
      j = 0;
      break label512;
      label631:
      bool5 = false;
      break label525;
      label637:
      bool6 = false;
      break label561;
      this.mDumbInputMode = true;
      this.mEditorClass = 0;
      this.mEditorVariant = 0;
      this.mEditorFlags = 0;
      this.mEditorIsSearchField = false;
      this.mEditorIsUrlField = false;
      this.mEditorIsPasswordField = false;
      this.mPredictionEnabled = false;
      this.mLayoutCyclingEnabled = false;
      this.mShiftMode = KeyboardState.ShiftMode.NONE;
    }
  }
  
  public void onCreate(Context paramContext)
  {
    this.mLicenseValid = this.mPreferences.isLicenseValid();
  }
  
  public void removeShiftedAtStartFlag()
  {
    this.mShiftedAtStartOfFlow = false;
  }
  
  public void setAlwaysComposeWordByWord(boolean paramBoolean)
  {
    this.mAlwaysComposeWordByWord = paramBoolean;
  }
  
  public boolean setComposingRegionOnlyBeforeEdits()
  {
    return this.mSetComposingRegionOnlyBeforeEdits;
  }
  
  public void setDisableQuickPeriod(boolean paramBoolean)
  {
    this.mDisableQuickPeriod = paramBoolean;
  }
  
  public void setDumbInputMode(boolean paramBoolean)
  {
    this.mDumbInputMode = paramBoolean;
  }
  
  public void setKeyPressModellingEnabled(boolean paramBoolean)
  {
    this.mKeyPressModellingEnabled = paramBoolean;
  }
  
  public void setNeverAutocomplete(boolean paramBoolean)
  {
    this.mNeverAutocomplete = paramBoolean;
  }
  
  public void setSearchType(ResultsFilter.PredictionSearchType paramPredictionSearchType)
  {
    if (paramPredictionSearchType != null) {
      this.mSearchType = paramPredictionSearchType;
    }
  }
  
  public void setShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    this.mPreviousShiftState = this.mShiftState;
    if (this.mIsHardCapsLockOn) {
      paramShiftState = TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED;
    }
    this.mShiftState = paramShiftState;
    this.mListenerManager.notifyShiftStateChangedListeners(this.mShiftState);
  }
  
  public void setStorageAvailable(boolean paramBoolean)
  {
    this.mStorageAvailable = paramBoolean;
    this.mListenerManager.notifyPredictionsEnabledListener(getPredictionsAvailability());
  }
  
  public void setUseTelexConversionLayer(boolean paramBoolean)
  {
    this.mUseTelexConversionLayer = paramBoolean;
  }
  
  public void setUseZeroWidthSpace(boolean paramBoolean)
  {
    this.mUseZeroWidthSpace = paramBoolean;
  }
  
  public boolean shouldAutocomplete(KeyInputEvent paramKeyInputEvent, ImmutableExtractedText paramImmutableExtractedText)
  {
    this.mUsingHardKB = (paramKeyInputEvent instanceof HardKeyInputEvent);
    int i = paramKeyInputEvent.getCharacter();
    AutoCompleteMode localAutoCompleteMode = getAutocompleteMode();
    int j = paramImmutableExtractedText.getCurrentWord().length();
    if ((paramImmutableExtractedText.isLastCharacterWhitespace()) || (paramImmutableExtractedText.getLastCharacter() == 0)) {}
    for (int k = 1; (!this.mNeverAutocomplete) && (isLicenseValid()) && (isPredictionEnabled()) && (!isCompletionModeEnabled()) && (!this.mDumbInputMode) && (!paramKeyInputEvent.insertingPrediction()) && (((!(paramKeyInputEvent instanceof SoftKeyInputEvent)) && (i != 32)) || (((localAutoCompleteMode == AutoCompleteMode.AUTOCOMPLETEMODE_ENABLED_WITH_AUTOSELECT) && ((j > 0) || ((i == 32) && (k != 0) && ((i != 32) || (!paramImmutableExtractedText.isLastCharacterZeroWidthSpace()) || (this.mSpaceAllowedToInsertPredictionAfterZeroWidthSpace))))) || ((localAutoCompleteMode == AutoCompleteMode.AUTOCOMPLETEMODE_ENABLED_WHEN_WORD_STARTED) && (j > 0)) || (((paramKeyInputEvent instanceof HardKeyInputEvent)) && (i != 32) && (j > 0) && (this.mPreferences.isHardKBPunctuationCompletionEnabled())))); k = 0) {
      return true;
    }
    return false;
  }
  
  public boolean shouldFixWebKitInputConnection()
  {
    return this.mShouldFixWebKitInputConnection;
  }
  
  public boolean shouldReplaceSelectionWithoutDeleting()
  {
    return this.mShouldReplaceSelectionWithoutDeleting;
  }
  
  public boolean textBeforeCursorWorks()
  {
    return this.mTextBeforeCursorWorks;
  }
  
  public void updateMetaStateOnFlowBegun()
  {
    if ((this.mSoftShiftPressed) && (this.mSoftChordShift == KeyboardState.ChordedShift.NONE))
    {
      startChording();
      return;
    }
    if (this.mShiftState == TouchTypeSoftKeyboard.ShiftState.SHIFTED)
    {
      this.mShiftedAtStartOfFlow = true;
      setShiftState(TouchTypeSoftKeyboard.ShiftState.UNSHIFTED);
      return;
    }
    this.mShiftedAtStartOfFlow = false;
  }
  
  public void updateMetaStateOnFlowComplete(CandidatesUpdateRequestType paramCandidatesUpdateRequestType)
  {
    if (this.mShiftedAtStartOfFlow)
    {
      this.mShiftedAtStartOfFlow = false;
      if (paramCandidatesUpdateRequestType == CandidatesUpdateRequestType.FLOW_FAILED) {
        setShiftState(TouchTypeSoftKeyboard.ShiftState.SHIFTED);
      }
    }
  }
  
  public boolean updateMetaStateOnHardKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool1 = this.mIsHardCapsLockOn;
    updateMenuKeyState(paramKeyEvent);
    updateCapsLockState(paramKeyEvent);
    TouchTypeSoftKeyboard.ShiftState localShiftState = getShiftState();
    boolean bool2 = this.mIsHardCapsLockOn;
    boolean bool3 = false;
    if (!bool2)
    {
      if ((InputEventFactoryImpl.isFullKeyboard(paramKeyEvent)) || (!KeyEvent.isModifierKey(paramInt)))
      {
        boolean bool4 = this.mIsHardCapsLockOn;
        bool3 = false;
        if (bool1 == bool4) {}
      }
      else
      {
        bool3 = this.mMetaKeyKeyListener.onKeyDown(paramInt, paramKeyEvent);
      }
    }
    else {
      switch (this.mMetaKeyKeyListener.getMetaState(1))
      {
      default: 
        localShiftState = TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED;
      }
    }
    for (;;)
    {
      if (localShiftState != getShiftState()) {
        setShiftState(localShiftState);
      }
      return bool3;
      localShiftState = TouchTypeSoftKeyboard.ShiftState.UNSHIFTED;
      continue;
      localShiftState = TouchTypeSoftKeyboard.ShiftState.SHIFTED;
    }
  }
  
  public boolean updateMetaStateOnHardKeyUp(int paramInt, KeyEvent paramKeyEvent, InputConnectionProxy paramInputConnectionProxy)
  {
    updateMenuKeyState(paramKeyEvent);
    if (KeyEvent.isModifierKey(paramInt)) {}
    for (boolean bool = this.mMetaKeyKeyListener.onKeyUp(paramInt, paramKeyEvent);; bool = false)
    {
      int i = this.mMetaKeyKeyListener.getMetaState(1);
      int j = 0;
      if (i == 0) {
        j = 1;
      }
      if (this.mMetaKeyKeyListener.getMetaState(2) == 0) {
        j |= 0x2;
      }
      if (this.mMetaKeyKeyListener.getMetaState(4) == 0) {
        j |= 0x4;
      }
      paramInputConnectionProxy.clearMetaKeyStates(j);
      return bool;
      this.mMetaKeyKeyListener.adjustMetaAfterKeypress();
    }
  }
  
  public void updateMetaStateOnSoftKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    resetHardCapsLock();
    TouchTypeSoftKeyboard.ShiftState localShiftState = this.mShiftState;
    if (paramInt == -1) {
      this.mSoftShiftPressed = true;
    }
    switch (1.$SwitchMap$com$touchtype$keyboard$service$TouchTypeSoftKeyboard$ShiftState[localShiftState.ordinal()])
    {
    default: 
      return;
    case 1: 
      if (getShiftMode() == KeyboardState.ShiftMode.CHARACTERS)
      {
        setShiftState(TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED);
        return;
      }
      setShiftState(TouchTypeSoftKeyboard.ShiftState.SHIFTED);
      return;
    case 2: 
      setShiftState(TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED);
      return;
    }
    setShiftState(TouchTypeSoftKeyboard.ShiftState.UNSHIFTED);
  }
  
  public void updateMetaStateOnSoftKeyUp(int paramInt, KeyEvent paramKeyEvent, InputConnectionProxy paramInputConnectionProxy)
  {
    if (paramInt == -1)
    {
      this.mSoftShiftPressed = false;
      if (this.mSoftChordShift != KeyboardState.ChordedShift.NONE) {
        if (this.mSoftChordShift == KeyboardState.ChordedShift.UPPER)
        {
          setShiftState(TouchTypeSoftKeyboard.ShiftState.UNSHIFTED);
          this.mSoftChordShift = KeyboardState.ChordedShift.NONE;
        }
      }
    }
    while ((!this.mSoftShiftPressed) || (this.mSoftChordShift != KeyboardState.ChordedShift.NONE))
    {
      do
      {
        for (;;)
        {
          return;
          setShiftState(TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED);
        }
      } while ((0x20 & paramKeyEvent.getFlags()) != 32);
      setShiftState(this.mPreviousShiftState);
      return;
    }
    startChording();
  }
  
  public boolean useShortTextBeforeAfterCursor()
  {
    return this.mUseShortTextBeforeAfterCursor;
  }
  
  public boolean useTelexConversionLayer()
  {
    return (this.mUseTelexConversionLayer) && (!this.mEditorIsPasswordField);
  }
  
  public boolean useTransactionsForSelectionEvents()
  {
    return this.mUseTransactionsForSelectionEvents;
  }
  
  public boolean useZeroWidthSpace()
  {
    return this.mUseZeroWidthSpace;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.KeyboardStateImpl
 * JD-Core Version:    0.7.0.1
 */