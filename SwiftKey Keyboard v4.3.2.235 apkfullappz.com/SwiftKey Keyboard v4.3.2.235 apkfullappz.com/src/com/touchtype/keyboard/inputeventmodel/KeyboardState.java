package com.touchtype.keyboard.inputeventmodel;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.preferences.AutoCompleteMode;
import com.touchtype.util.PackageInfoUtil;
import com.touchtype_fluency.ResultsFilter.CapitalizationHint;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.service.ImmutableExtractedText;

public abstract interface KeyboardState
{
  public abstract boolean allowMoveCursorForInsertingPredictions();
  
  public abstract boolean backspaceWordDeleteToStart();
  
  public abstract boolean composeTextWordByWord();
  
  public abstract boolean cursorMovementUpdatesSelection();
  
  public abstract boolean deleteKeyDeletesTwoCharacters();
  
  public abstract boolean doesntSupportMoveCursor();
  
  public abstract boolean dontUseJumpingCursorMarkers();
  
  public abstract void enabledLanguagePacksChanged(int paramInt);
  
  public abstract boolean evaluateInputShownUsedInsteadOfUpdateSelection();
  
  public abstract boolean extractedTextWorks();
  
  public abstract int getApiCompatibiltyLevel();
  
  public abstract AutoCompleteMode getAutocompleteMode();
  
  public abstract ResultsFilter.CapitalizationHint getCapitalisationState(ResultsFilter.CapitalizationHint paramCapitalizationHint);
  
  public abstract ResultsFilter.CapitalizationHint getCapitalisationState(String paramString);
  
  public abstract TouchTypeSoftKeyboard.ShiftState getInitialShiftState();
  
  public abstract int getMetaState();
  
  public abstract PredictionsAvailability getPredictionsAvailability();
  
  public abstract ResultsFilter.PredictionSearchType getSearchType();
  
  public abstract ShiftMode getShiftMode();
  
  public abstract TouchTypeSoftKeyboard.ShiftState getShiftState();
  
  public abstract String hintForCurrentField();
  
  public abstract boolean isDumbInputMode();
  
  public abstract boolean isEditSpacingAssistanceDisabled();
  
  public abstract boolean isHardCapsLockOn();
  
  public abstract boolean isHardKBSmartPunctuationEnabled();
  
  public abstract boolean isKeyPressModellingEnabled();
  
  public abstract boolean isLayoutCyclingEnabled();
  
  public abstract boolean isMenuKeyPressed();
  
  public abstract boolean isMultiLineField();
  
  public abstract boolean isPredictionEnabled();
  
  public abstract boolean isQuickPeriodOn();
  
  public abstract boolean isSearchField();
  
  public abstract boolean isSoftShiftPressed();
  
  public abstract boolean isUrlBar();
  
  public abstract boolean movingOverTrailingSpaceDoesntWork();
  
  public abstract boolean neverSetComposingRegion();
  
  public abstract void notifyNewEditorInfo(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
  
  public abstract void onCreate(Context paramContext);
  
  public abstract void removeShiftedAtStartFlag();
  
  public abstract void setAlwaysComposeWordByWord(boolean paramBoolean);
  
  public abstract boolean setComposingRegionOnlyBeforeEdits();
  
  public abstract void setDisableQuickPeriod(boolean paramBoolean);
  
  public abstract void setDumbInputMode(boolean paramBoolean);
  
  public abstract void setKeyPressModellingEnabled(boolean paramBoolean);
  
  public abstract void setNeverAutocomplete(boolean paramBoolean);
  
  public abstract void setSearchType(ResultsFilter.PredictionSearchType paramPredictionSearchType);
  
  public abstract void setShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState);
  
  public abstract void setStorageAvailable(boolean paramBoolean);
  
  public abstract void setUseTelexConversionLayer(boolean paramBoolean);
  
  public abstract void setUseZeroWidthSpace(boolean paramBoolean);
  
  public abstract boolean shouldAutocomplete(KeyInputEvent paramKeyInputEvent, ImmutableExtractedText paramImmutableExtractedText);
  
  public abstract boolean shouldFixWebKitInputConnection();
  
  public abstract boolean shouldReplaceSelectionWithoutDeleting();
  
  public abstract boolean textBeforeCursorWorks();
  
  public abstract void updateMetaStateOnFlowBegun();
  
  public abstract void updateMetaStateOnFlowComplete(CandidatesUpdateRequestType paramCandidatesUpdateRequestType);
  
  public abstract boolean updateMetaStateOnHardKeyDown(int paramInt, KeyEvent paramKeyEvent);
  
  public abstract boolean updateMetaStateOnHardKeyUp(int paramInt, KeyEvent paramKeyEvent, InputConnectionProxy paramInputConnectionProxy);
  
  public abstract void updateMetaStateOnSoftKeyDown(int paramInt, KeyEvent paramKeyEvent);
  
  public abstract void updateMetaStateOnSoftKeyUp(int paramInt, KeyEvent paramKeyEvent, InputConnectionProxy paramInputConnectionProxy);
  
  public abstract boolean useShortTextBeforeAfterCursor();
  
  public abstract boolean useTelexConversionLayer();
  
  public abstract boolean useTransactionsForSelectionEvents();
  
  public abstract boolean useZeroWidthSpace();
  
  public static enum ChordedShift
  {
    static
    {
      LOWER = new ChordedShift("LOWER", 2);
      ChordedShift[] arrayOfChordedShift = new ChordedShift[3];
      arrayOfChordedShift[0] = NONE;
      arrayOfChordedShift[1] = UPPER;
      arrayOfChordedShift[2] = LOWER;
      $VALUES = arrayOfChordedShift;
    }
    
    private ChordedShift() {}
  }
  
  public static enum ShiftMode
  {
    static
    {
      CHARACTERS = new ShiftMode("CHARACTERS", 1);
      WORDS = new ShiftMode("WORDS", 2);
      SENTENCES = new ShiftMode("SENTENCES", 3);
      ShiftMode[] arrayOfShiftMode = new ShiftMode[4];
      arrayOfShiftMode[0] = NONE;
      arrayOfShiftMode[1] = CHARACTERS;
      arrayOfShiftMode[2] = WORDS;
      arrayOfShiftMode[3] = SENTENCES;
      $VALUES = arrayOfShiftMode;
    }
    
    private ShiftMode() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.KeyboardState
 * JD-Core Version:    0.7.0.1
 */