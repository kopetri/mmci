package com.touchtype.keyboard.inputeventmodel.touchhistory;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.ExtractedText;
import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.concurrent.BackgroundExecutor;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy.SelectionDeletionType;
import com.touchtype.keyboard.inputeventmodel.KeyPressModel;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.PredictionRequestManager;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.ResultsFilter.CapitalizationHint;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.TouchHistory.ShiftState;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TokenizationProvider;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public class TouchHistoryManagerImpl
  implements InputConnectionProxy, TouchHistoryManager
{
  static final String TAG = TouchHistoryManagerImpl.class.getSimpleName();
  int mBatchEditOperations = -1;
  TouchHistoryMarker mCurrentFlowMarker = null;
  boolean mDontGetWordsForLearning = true;
  final BackgroundExecutor mExecutor;
  final HistoryText mHistoryText;
  KeyPressModel mKeyPressModel;
  KeyboardState mKeyboardState;
  PredictionRequestManager mPredictionRequestManager;
  boolean mResyncRequired = false;
  private final TokenizationProvider mTokenizationProvider;
  
  public TouchHistoryManagerImpl(PredictionRequestManager paramPredictionRequestManager, KeyPressModel paramKeyPressModel, KeyboardState paramKeyboardState, BackgroundExecutor paramBackgroundExecutor, TokenizationProvider paramTokenizationProvider, HistoryText paramHistoryText)
  {
    this.mPredictionRequestManager = paramPredictionRequestManager;
    this.mKeyPressModel = paramKeyPressModel;
    this.mKeyboardState = paramKeyboardState;
    this.mExecutor = paramBackgroundExecutor;
    this.mTokenizationProvider = paramTokenizationProvider;
    this.mHistoryText = paramHistoryText;
  }
  
  private TouchTypeExtractedText boundsCheckedExtractedText(ExtractedText paramExtractedText)
  {
    TouchTypeExtractedText localTouchTypeExtractedText = new TouchTypeExtractedText(paramExtractedText, this.mTokenizationProvider);
    if (localTouchTypeExtractedText.text == null) {
      localTouchTypeExtractedText.text = "";
    }
    if (localTouchTypeExtractedText.selectionEnd < 0) {
      localTouchTypeExtractedText.selectionEnd = 0;
    }
    while (localTouchTypeExtractedText.selectionEnd <= localTouchTypeExtractedText.text.length()) {
      return localTouchTypeExtractedText;
    }
    localTouchTypeExtractedText.selectionEnd = localTouchTypeExtractedText.text.length();
    return localTouchTypeExtractedText;
  }
  
  private TouchHistoryMarker cleanTouchHistory(TouchHistoryMarker paramTouchHistoryMarker, CharSequence paramCharSequence)
  {
    int i = this.mHistoryText.getStart(paramTouchHistoryMarker);
    CharSequence localCharSequence = this.mHistoryText.getCurrentWord();
    this.mHistoryText.removeHistoryMarkersWithSameStart(i, paramTouchHistoryMarker);
    if ((paramTouchHistoryMarker.modifiedByPredictionButNotYetCharacterised()) && (!paramCharSequence.toString().equals(localCharSequence.toString()))) {
      paramTouchHistoryMarker = replaceHistoryMarkerWithOneFromText(paramTouchHistoryMarker, i, localCharSequence);
    }
    return paramTouchHistoryMarker;
  }
  
  private String comparableRegion(ExtractedText paramExtractedText)
  {
    int i = this.mHistoryText.text.length();
    int j = paramExtractedText.startOffset - this.mHistoryText.startOffset;
    int k = Math.max(0, Math.min(i, j + 0));
    int m = Math.max(0, Math.min(i, j + paramExtractedText.text.length()));
    return this.mHistoryText.text.toString().substring(k, m);
  }
  
  private boolean inBatchEdit()
  {
    if (this.mBatchEditOperations >= 0) {}
    for (boolean bool = true;; bool = false)
    {
      if (!bool) {
        LogUtil.w(TAG, "endBatchEdit before beginBatchEdit!");
      }
      return bool;
    }
  }
  
  private void notifyBatchEditOperation()
  {
    this.mBatchEditOperations = (1 + this.mBatchEditOperations);
  }
  
  private TouchHistoryMarker replaceHistoryMarkerWithOneFromText(TouchHistoryMarker paramTouchHistoryMarker, int paramInt, CharSequence paramCharSequence)
  {
    this.mHistoryText.removeMarker(paramTouchHistoryMarker);
    TouchHistoryMarker localTouchHistoryMarker = paramTouchHistoryMarker.characterise(paramCharSequence);
    saveHistoryMarker(localTouchHistoryMarker, paramInt);
    return localTouchHistoryMarker;
  }
  
  private void saveHistoryMarker(TouchHistoryMarker paramTouchHistoryMarker, int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    int i = Math.min(this.mHistoryText.text.length(), this.mHistoryText.getSelectionStartInText());
    int j = Math.min(i, paramInt);
    try
    {
      this.mHistoryText.setMarker(paramTouchHistoryMarker, j, i);
      return;
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      Object[] arrayOfObject = new Object[6];
      arrayOfObject[0] = Integer.valueOf(j);
      arrayOfObject[1] = Integer.valueOf(i);
      arrayOfObject[2] = Integer.valueOf(paramInt);
      arrayOfObject[3] = Integer.valueOf(this.mHistoryText.getSelectionStartInText());
      arrayOfObject[4] = this.mHistoryText.getText();
      arrayOfObject[5] = Integer.valueOf(this.mHistoryText.getText().length());
      throw new RuntimeException(String.format("Could not set TouchHistoryMarker span [%d -> %d] (requested [%d -> %d] on text '%s' with length %d)", arrayOfObject), localArrayIndexOutOfBoundsException);
    }
  }
  
  private TouchHistory.ShiftState shiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    switch (1.$SwitchMap$com$touchtype$keyboard$service$TouchTypeSoftKeyboard$ShiftState[paramShiftState.ordinal()])
    {
    default: 
      return TouchHistory.ShiftState.UNSHIFTED;
    case 2: 
      return TouchHistory.ShiftState.SHIFTED;
    }
    return TouchHistory.ShiftState.SHIFTED;
  }
  
  private void trimTouchHistory()
  {
    this.mHistoryText.stripNegativeWidthSpans();
    SpannableStringBuilder localSpannableStringBuilder = this.mHistoryText.getTextSpannable();
    int i = localSpannableStringBuilder.toString().length();
    if (i <= 0) {}
    for (;;)
    {
      return;
      Object[] arrayOfObject = localSpannableStringBuilder.getSpans(0, i, Object.class);
      int j = -10 + arrayOfObject.length;
      for (int k = 0; k <= j; k++) {
        localSpannableStringBuilder.removeSpan(arrayOfObject[k]);
      }
    }
  }
  
  private void updateHistoryMarkerFromMultiWordPredictionEvent(TouchHistoryMarker paramTouchHistoryMarker, boolean paramBoolean, int paramInt, Prediction paramPrediction)
  {
    int i = paramPrediction.size();
    String str = paramPrediction.get(i - 1);
    int j = paramInt + (paramPrediction.getPrediction().length() - str.length());
    if (paramBoolean)
    {
      replaceHistoryMarkerWithOneFromText(paramTouchHistoryMarker, j, str);
      return;
    }
    ResultsFilter.CapitalizationHint localCapitalizationHint = this.mKeyboardState.getCapitalisationState("");
    if (localCapitalizationHint == ResultsFilter.CapitalizationHint.INITIAL_UPPER_CASE) {
      localCapitalizationHint = ResultsFilter.CapitalizationHint.LOWER_CASE;
    }
    this.mHistoryText.removeMarker(paramTouchHistoryMarker);
    paramTouchHistoryMarker.setModifiedByPrediction("", localCapitalizationHint);
    paramTouchHistoryMarker.dropFirstTerms(paramPrediction, i - 1);
    saveHistoryMarker(paramTouchHistoryMarker, j);
  }
  
  private void updateHistoryMarkerFromSingleWordPrediction(TouchHistoryMarker paramTouchHistoryMarker, boolean paramBoolean, int paramInt, String paramString1, String paramString2)
  {
    if (paramBoolean)
    {
      replaceHistoryMarkerWithOneFromText(paramTouchHistoryMarker, paramInt, paramString2);
      return;
    }
    if (!paramString1.equals(paramString2)) {
      paramTouchHistoryMarker.setModifiedByPrediction(paramString1, this.mKeyboardState.getCapitalisationState(paramString1));
    }
    saveHistoryMarker(paramTouchHistoryMarker, paramInt);
  }
  
  public void addContinuousTouchSample(Point paramPoint)
  {
    if (this.mCurrentFlowMarker == null)
    {
      this.mCurrentFlowMarker = this.mHistoryText.getCurrentHistoryMarker();
      if (this.mCurrentFlowMarker != null) {
        break label108;
      }
      CharSequence localCharSequence2 = this.mHistoryText.getCurrentWord();
      int j = this.mHistoryText.getSelectionStartInText() - localCharSequence2.length();
      this.mCurrentFlowMarker = createTouchHistoryMarkerFromText(localCharSequence2);
      saveHistoryMarker(this.mCurrentFlowMarker, j);
    }
    for (;;)
    {
      this.mCurrentFlowMarker.appendSample(paramPoint);
      this.mPredictionRequestManager.requestBufferedPrediction(CandidatesUpdateRequestType.FLOW, this.mCurrentFlowMarker, getContext(), getCurrentWord().toString());
      return;
      label108:
      if (this.mCurrentFlowMarker.modifiedByPredictionButNotYetCharacterised())
      {
        CharSequence localCharSequence1 = this.mHistoryText.getCurrentWord();
        int i = this.mHistoryText.getSelectionStartInText() - localCharSequence1.length();
        this.mCurrentFlowMarker = replaceHistoryMarkerWithOneFromText(this.mCurrentFlowMarker, i, localCharSequence1);
      }
      else
      {
        this.mCurrentFlowMarker.dropSamples();
      }
    }
  }
  
  public boolean beginBatchEdit(boolean paramBoolean)
  {
    this.mBatchEditOperations = 0;
    return true;
  }
  
  public boolean clearMetaKeyStates(int paramInt)
  {
    return true;
  }
  
  public boolean commitCompletion(CompletionInfo paramCompletionInfo)
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    this.mHistoryText.setCurrentWord(paramCompletionInfo.getText());
    return true;
  }
  
  public boolean commitCorrection(Candidate paramCandidate, boolean paramBoolean, ImmutableExtractedText paramImmutableExtractedText)
  {
    setComposingTextWithCandidate(paramCandidate, paramBoolean, paramImmutableExtractedText);
    return finishComposingText();
  }
  
  public boolean commitCorrectionFromAutoCommit(String paramString, ImmutableExtractedText paramImmutableExtractedText)
  {
    setComposingTextFromAutoCommit(paramString, paramImmutableExtractedText, createTouchHistoryMarkerFromText(paramString));
    return finishComposingText();
  }
  
  public boolean commitText(CharSequence paramCharSequence)
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    TouchHistoryMarker localTouchHistoryMarker = getCurrentTouchHistoryMarker();
    int i = this.mHistoryText.getStart(localTouchHistoryMarker);
    this.mHistoryText.removeHistoryMarkersWithSameStart(i, localTouchHistoryMarker);
    this.mHistoryText.insertText(paramCharSequence);
    return true;
  }
  
  public TouchHistoryMarker createTouchHistoryMarkerFromText(CharSequence paramCharSequence)
  {
    return new TouchHistoryMarker(paramCharSequence, this.mKeyPressModel.getModelId().intValue(), this.mExecutor);
  }
  
  public boolean deleteSelection(ImmutableExtractedText paramImmutableExtractedText, InputConnectionProxy.SelectionDeletionType paramSelectionDeletionType)
  {
    if ((this.mKeyboardState.doesntSupportMoveCursor()) && (paramSelectionDeletionType.equals(InputConnectionProxy.SelectionDeletionType.NO_REPLACEMENT))) {
      sendDownUpKeyEvents(67);
    }
    int i;
    do
    {
      return true;
      i = paramImmutableExtractedText.getSelectionEndInField() - paramImmutableExtractedText.getSelectionStartInField();
    } while ((setSelection(paramImmutableExtractedText.getSelectionEndInField(), paramImmutableExtractedText.getSelectionEndInField())) && (deleteSurroundingText(i, 0)));
    return false;
  }
  
  public boolean deleteSurroundingText(int paramInt1, int paramInt2)
  {
    if (!inBatchEdit()) {}
    TouchHistoryMarker localTouchHistoryMarker2;
    int j;
    do
    {
      TouchHistoryMarker localTouchHistoryMarker1;
      int i;
      do
      {
        return true;
        notifyBatchEditOperation();
        localTouchHistoryMarker1 = getCurrentTouchHistoryMarker();
        this.mHistoryText.removeMarker(localTouchHistoryMarker1);
        if (paramInt1 < 0) {
          paramInt1 = 0;
        }
        if (paramInt2 < 0) {
          paramInt2 = 0;
        }
        HistoryText localHistoryText = this.mHistoryText;
        localHistoryText.selectionEnd = (paramInt2 + localHistoryText.selectionEnd);
        i = Math.min(paramInt1 + paramInt2, this.mHistoryText.getSelectionStartInText());
      } while (i <= 0);
      this.mHistoryText.deleteCharacters(i);
      if (localTouchHistoryMarker1.modifiedByPredictionButNotYetCharacterised())
      {
        TouchHistoryMarker localTouchHistoryMarker3 = localTouchHistoryMarker1.characterise("");
        localTouchHistoryMarker3.setDeletionPerformedAfterModification(TouchHistoryMarker.DeletionPerformed.FULL);
        saveHistoryMarker(localTouchHistoryMarker3, this.mHistoryText.getSelectionStartInText());
        return true;
      }
      localTouchHistoryMarker2 = getCurrentTouchHistoryMarker();
      j = this.mHistoryText.getEnd(localTouchHistoryMarker2);
    } while ((j != this.mHistoryText.getStart(localTouchHistoryMarker2)) && (j == this.mHistoryText.getSelectionStartInText()));
    this.mHistoryText.removeMarker(localTouchHistoryMarker2);
    return true;
  }
  
  public void dontGetWordsForLearningUntilResync()
  {
    this.mDontGetWordsForLearning = true;
  }
  
  public boolean endBatchEdit(boolean paramBoolean, CandidatesUpdateRequestType paramCandidatesUpdateRequestType)
  {
    if (this.mBatchEditOperations > 0)
    {
      if ((paramCandidatesUpdateRequestType != CandidatesUpdateRequestType.FLOW) && (!paramBoolean)) {
        this.mCurrentFlowMarker = null;
      }
      trimTouchHistory();
      if (this.mKeyboardState.isPredictionEnabled()) {
        this.mPredictionRequestManager.requestImmediatePrediction(paramCandidatesUpdateRequestType, getCurrentTouchHistoryMarker(), getContext(), getCurrentWord());
      }
    }
    this.mBatchEditOperations = -1;
    return true;
  }
  
  public boolean extractedTextWorks()
  {
    return false;
  }
  
  public boolean finishComposingText()
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    return true;
  }
  
  public void fixWebKitInputConnection(boolean paramBoolean) {}
  
  public void flowFailed()
  {
    TouchHistoryMarker localTouchHistoryMarker = this.mHistoryText.getCurrentHistoryMarker();
    if (localTouchHistoryMarker != null) {
      localTouchHistoryMarker.dropSamples();
    }
    this.mBatchEditOperations = (1 + this.mBatchEditOperations);
  }
  
  public void flushBufferedPredictionRequests()
  {
    this.mPredictionRequestManager.flushBufferedPredictionRequests();
  }
  
  public Sequence getContext()
  {
    Sequence localSequence = this.mHistoryText.getContext();
    localSequence.setFieldHint(this.mKeyboardState.hintForCurrentField());
    return localSequence;
  }
  
  public TouchHistoryMarker getCurrentTouchHistoryMarker()
  {
    TouchHistoryMarker localTouchHistoryMarker = this.mHistoryText.getCurrentHistoryMarker();
    if ((localTouchHistoryMarker == null) || (localTouchHistoryMarker.getTouchHistory() == null) || (this.mKeyPressModel.getModelId().intValue() != localTouchHistoryMarker.getKeyPressModelId())) {
      localTouchHistoryMarker = createTouchHistoryMarkerFromText(this.mHistoryText.getCurrentWord());
    }
    if (this.mHistoryText.getStart(localTouchHistoryMarker) < 0) {
      saveHistoryMarker(localTouchHistoryMarker, this.mHistoryText.getSelectionStartInText() - this.mHistoryText.getCurrentWord().length());
    }
    return localTouchHistoryMarker;
  }
  
  public String getCurrentWord()
  {
    return this.mHistoryText.getCurrentWord().toString();
  }
  
  public CursorMarker getCursorMarker(ImmutableExtractedText paramImmutableExtractedText, int paramInt)
  {
    int i = this.mHistoryText.boundsCheckedOffset();
    int j = paramInt - i;
    CursorMarker localCursorMarker = this.mHistoryText.getCursorMarker(j, this.mKeyboardState.dontUseJumpingCursorMarkers());
    localCursorMarker.setOffset(i);
    return localCursorMarker;
  }
  
  public TouchTypeExtractedText getTouchTypeExtractedText(boolean paramBoolean)
  {
    return this.mHistoryText;
  }
  
  public LazySequence getWordsForLearning()
  {
    if (!this.mKeyboardState.isPredictionEnabled()) {
      return new EmptyLazySequenceForLearning();
    }
    if (this.mDontGetWordsForLearning) {
      return new EmptyLazySequenceForLearning();
    }
    return new OrdinaryLazySequenceForLearning(this.mHistoryText.getText(), this.mHistoryText.startOffset, this.mKeyboardState.hintForCurrentField(), this.mTokenizationProvider);
  }
  
  public boolean isHistoryTextEmpty()
  {
    return this.mHistoryText.textEmpty();
  }
  
  public boolean nonZeroLengthSelectionMade(int paramInt1, int paramInt2)
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    int i = paramInt1 - this.mHistoryText.boundsCheckedOffset();
    int j = paramInt2 - this.mHistoryText.boundsCheckedOffset();
    this.mHistoryText.boundsCheckedSetSelectionStart(i);
    this.mHistoryText.boundsCheckedSetSelectionEnd(j);
    return true;
  }
  
  public void resetAllHistory(ExtractedText paramExtractedText)
  {
    this.mHistoryText.setText("");
    this.mHistoryText.removeAllMarkers();
    HistoryText localHistoryText1 = this.mHistoryText;
    this.mHistoryText.selectionEnd = 0;
    localHistoryText1.selectionStart = 0;
    HistoryText localHistoryText2 = this.mHistoryText;
    this.mHistoryText.partialEndOffset = 0;
    localHistoryText2.partialStartOffset = 0;
    this.mHistoryText.startOffset = 0;
    if (paramExtractedText != null) {
      syncTextHistoryWith(paramExtractedText);
    }
  }
  
  public void resetContinuousTouchSamples()
  {
    TouchHistoryMarker localTouchHistoryMarker = this.mHistoryText.getCurrentHistoryMarker();
    if (localTouchHistoryMarker != null) {
      localTouchHistoryMarker.dropSamples();
    }
  }
  
  public boolean resync()
  {
    return false;
  }
  
  public boolean resyncRequired()
  {
    return this.mResyncRequired;
  }
  
  public void sendDownUpKeyEvents(int paramInt)
  {
    this.mResyncRequired = true;
  }
  
  public void sendKeyChar(char paramChar)
  {
    this.mResyncRequired = true;
  }
  
  public boolean setComposingRegion(int paramInt1, int paramInt2, ImmutableExtractedText paramImmutableExtractedText)
  {
    if (!inBatchEdit()) {}
    int i;
    int j;
    TouchHistoryMarker localTouchHistoryMarker1;
    TouchHistoryMarker localTouchHistoryMarker4;
    do
    {
      do
      {
        do
        {
          return true;
          notifyBatchEditOperation();
          i = paramInt1 - this.mHistoryText.boundsCheckedOffset();
          j = paramInt2 - this.mHistoryText.boundsCheckedOffset();
          this.mHistoryText.boundsCheckedSetSelectionStart(j);
          this.mHistoryText.boundsCheckedSetSelectionEnd(j);
        } while ((this.mBatchEditOperations == 1) || (this.mHistoryText.getHistoryMarker(i, j) != null));
        localTouchHistoryMarker1 = this.mHistoryText.getHistoryMarkerEndingAt(j);
      } while (localTouchHistoryMarker1 == null);
      if (j - i == 1)
      {
        localTouchHistoryMarker1.dropLastPress();
        int n = this.mHistoryText.getStart(localTouchHistoryMarker1);
        this.mHistoryText.setMarker(localTouchHistoryMarker1, n, Math.max(i, n));
        this.mHistoryText.setMarker(createTouchHistoryMarkerFromText(this.mHistoryText.getText().substring(i, j)), i, j);
        return true;
      }
      if (j == i) {
        break;
      }
      TouchHistoryMarker localTouchHistoryMarker3 = this.mHistoryText.getHistoryMarker(i, j - 2);
      localTouchHistoryMarker4 = this.mHistoryText.getHistoryMarker(j - 1, j);
      if ((localTouchHistoryMarker3 != null) && (localTouchHistoryMarker4 != null))
      {
        this.mHistoryText.removeMarker(localTouchHistoryMarker4);
        if (localTouchHistoryMarker3.modifiedByPredictionButNotYetCharacterised()) {
          localTouchHistoryMarker3 = replaceHistoryMarkerWithOneFromText(localTouchHistoryMarker3, i, this.mHistoryText.getText().substring(i, j - 2));
        }
        localTouchHistoryMarker3.appendHistory(createTouchHistoryMarkerFromText(this.mHistoryText.getText().substring(j - 2, j - 1)));
        localTouchHistoryMarker3.appendHistory(localTouchHistoryMarker4);
        saveHistoryMarker(localTouchHistoryMarker3, i);
        return true;
      }
      if (localTouchHistoryMarker3 != null) {
        this.mHistoryText.removeMarker(localTouchHistoryMarker3);
      }
    } while (localTouchHistoryMarker4 == null);
    this.mHistoryText.removeMarker(localTouchHistoryMarker4);
    return true;
    if ((j == i) && (localTouchHistoryMarker1.deletionPerformedAfterModification() == TouchHistoryMarker.DeletionPerformed.PARTIAL))
    {
      int k = this.mHistoryText.getStart(localTouchHistoryMarker1);
      if (k != -1)
      {
        int m = j - 1;
        TouchHistoryMarker localTouchHistoryMarker2 = localTouchHistoryMarker1.characterise(this.mHistoryText.getText().substring(k, m));
        this.mHistoryText.setMarker(localTouchHistoryMarker2, k, m);
      }
    }
    this.mHistoryText.removeMarker(localTouchHistoryMarker1);
    return true;
  }
  
  public boolean setComposingTextByAppendingCharacter(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText, char paramChar)
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    TouchHistoryMarker localTouchHistoryMarker = cleanTouchHistory(getCurrentTouchHistoryMarker(), paramCharSequence);
    this.mHistoryText.setCurrentWord(paramCharSequence);
    int i = this.mHistoryText.getSelectionStartInText();
    int j = i - paramCharSequence.length();
    if (this.mKeyboardState.isKeyPressModellingEnabled()) {
      localTouchHistoryMarker.getTouchHistory().addCharacter(Character.valueOf(paramChar));
    }
    for (;;)
    {
      saveHistoryMarker(localTouchHistoryMarker, j);
      return true;
      localTouchHistoryMarker.setTouchHistory(this.mHistoryText.getText().substring(j, i));
    }
  }
  
  public boolean setComposingTextByAppendingCharacter(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText, Point paramPoint)
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    TouchHistoryMarker localTouchHistoryMarker = cleanTouchHistory(getCurrentTouchHistoryMarker(), paramCharSequence);
    this.mHistoryText.setCurrentWord(paramCharSequence);
    int i = this.mHistoryText.getSelectionEndInText();
    int j = i - paramCharSequence.length();
    if (this.mKeyboardState.isKeyPressModellingEnabled()) {
      localTouchHistoryMarker.getTouchHistory().addPress(paramPoint, shiftState(this.mKeyboardState.getShiftState()));
    }
    for (;;)
    {
      saveHistoryMarker(localTouchHistoryMarker, j);
      return true;
      localTouchHistoryMarker.setTouchHistory(this.mHistoryText.getText().substring(j, i));
    }
  }
  
  public boolean setComposingTextByDeletingLastCharacter(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText)
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    TouchHistoryMarker localTouchHistoryMarker = cleanTouchHistory(getCurrentTouchHistoryMarker(), paramCharSequence);
    this.mHistoryText.setCurrentWord(paramCharSequence);
    int i = this.mHistoryText.getSelectionEndInText() - paramCharSequence.length();
    localTouchHistoryMarker.dropLastPress();
    if (paramCharSequence.length() == 0) {}
    for (TouchHistoryMarker.DeletionPerformed localDeletionPerformed = TouchHistoryMarker.DeletionPerformed.FULL;; localDeletionPerformed = TouchHistoryMarker.DeletionPerformed.PARTIAL)
    {
      localTouchHistoryMarker.setDeletionPerformedAfterModification(localDeletionPerformed);
      saveHistoryMarker(localTouchHistoryMarker, i);
      return true;
    }
  }
  
  public boolean setComposingTextFromAutoCommit(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText, TouchHistoryMarker paramTouchHistoryMarker)
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    this.mHistoryText.removeMarker(getCurrentTouchHistoryMarker());
    this.mHistoryText.setCurrentWord(paramCharSequence);
    int i = this.mHistoryText.getSelectionStartInText();
    this.mHistoryText.setMarker(paramTouchHistoryMarker, i - paramCharSequence.length(), i);
    return true;
  }
  
  public boolean setComposingTextWithCandidate(Candidate paramCandidate, boolean paramBoolean, ImmutableExtractedText paramImmutableExtractedText)
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    String str = paramCandidate.toString();
    CharSequence localCharSequence = this.mHistoryText.getCurrentWord();
    int i = this.mHistoryText.getSelectionStartInText() - localCharSequence.length();
    TouchHistoryMarker localTouchHistoryMarker = cleanTouchHistory(getCurrentTouchHistoryMarker(), str);
    this.mHistoryText.setCurrentWord(str);
    if ((paramCandidate != null) && (paramCandidate.getPrediction() != null) && (paramCandidate.getPrediction().size() > 1))
    {
      updateHistoryMarkerFromMultiWordPredictionEvent(localTouchHistoryMarker, paramBoolean, i, paramCandidate.getPrediction());
      return true;
    }
    updateHistoryMarkerFromSingleWordPrediction(localTouchHistoryMarker, paramBoolean, i, localCharSequence.toString(), str.toString());
    return true;
  }
  
  public boolean setSelection(int paramInt1, int paramInt2)
  {
    if (!inBatchEdit()) {
      return true;
    }
    notifyBatchEditOperation();
    int i = paramInt1 - this.mHistoryText.boundsCheckedOffset();
    int j = paramInt2 - this.mHistoryText.boundsCheckedOffset();
    this.mHistoryText.boundsCheckedSetSelectionStart(i);
    this.mHistoryText.boundsCheckedSetSelectionEnd(j);
    return true;
  }
  
  public boolean syncTextHistoryWith(ExtractedText paramExtractedText)
  {
    boolean bool = true;
    TouchTypeExtractedText localTouchTypeExtractedText = boundsCheckedExtractedText(paramExtractedText);
    String str = comparableRegion(localTouchTypeExtractedText);
    CharSequence localCharSequence = localTouchTypeExtractedText.text;
    if (this.mHistoryText.selectionEnd == -1)
    {
      resetAllHistory(localTouchTypeExtractedText);
      bool = false;
    }
    int i;
    if ((!localCharSequence.toString().contentEquals(str)) || ((!TextUtils.isEmpty(this.mHistoryText.text)) && (TextUtils.isEmpty(localCharSequence))))
    {
      i = localCharSequence.toString().indexOf(str);
      if ((i == -1) || (TextUtils.isEmpty(localCharSequence)) || (TextUtils.isEmpty(str)))
      {
        this.mHistoryText.setText(localCharSequence);
        HistoryText.copyCursorPosition(localTouchTypeExtractedText, this.mHistoryText);
        bool = false;
      }
    }
    for (;;)
    {
      if (this.mHistoryText.selectionEnd > this.mHistoryText.text.length())
      {
        HistoryText localHistoryText1 = this.mHistoryText;
        HistoryText localHistoryText2 = this.mHistoryText;
        int j = localTouchTypeExtractedText.selectionEnd;
        localHistoryText2.selectionEnd = j;
        localHistoryText1.selectionStart = j;
      }
      this.mResyncRequired = false;
      this.mDontGetWordsForLearning = false;
      return bool;
      this.mHistoryText.startOffset = Math.min(this.mHistoryText.startOffset, localTouchTypeExtractedText.startOffset);
      int k = localTouchTypeExtractedText.startOffset - this.mHistoryText.startOffset;
      if (i > 0) {
        this.mHistoryText.getTextSpannable().insert(k, localCharSequence, 0, i);
      }
      int m = i + str.length();
      if (m < localCharSequence.length())
      {
        int n = k + m;
        this.mHistoryText.getTextSpannable().insert(n, localCharSequence, m, localCharSequence.length());
      }
      this.mHistoryText.selectionStart = (k + localTouchTypeExtractedText.selectionStart);
      this.mHistoryText.selectionEnd = (k + localTouchTypeExtractedText.selectionEnd);
      continue;
      this.mHistoryText.selectionStart = (localTouchTypeExtractedText.selectionStart + localTouchTypeExtractedText.startOffset - this.mHistoryText.startOffset);
      this.mHistoryText.selectionEnd = (localTouchTypeExtractedText.selectionEnd + localTouchTypeExtractedText.startOffset - this.mHistoryText.startOffset);
    }
  }
  
  public boolean textBeforeCursorWorks()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManagerImpl
 * JD-Core Version:    0.7.0.1
 */