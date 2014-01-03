package com.touchtype.keyboard.inputeventmodel;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import com.touchtype.keyboard.BufferedInputListener;
import com.touchtype.keyboard.InputFilterListener;
import com.touchtype.keyboard.KeyboardBehaviour;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidateStateHandler;
import com.touchtype.keyboard.candidates.CandidatesUpdater;
import com.touchtype.keyboard.candidates.UpdatedCandidatesListener;
import com.touchtype.keyboard.concurrent.BackgroundExecutor;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowAutoCommitEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowCompleteEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowFailedEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.handlers.InputEventHandler;
import com.touchtype.keyboard.inputeventmodel.handlers.InputEventHandlerImpl;
import com.touchtype.keyboard.inputeventmodel.keytranslation.KeyTranslationLayer;
import com.touchtype.keyboard.inputeventmodel.keytranslation.KeyTranslationLayerImpl;
import com.touchtype.keyboard.inputeventmodel.listeners.OnCandidatesUpdateRequestListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnFlowStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnShiftStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.PredictionsAvailabilityListener;
import com.touchtype.keyboard.inputeventmodel.touchhistory.HistoryText;
import com.touchtype.keyboard.inputeventmodel.touchhistory.LazySequence;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManagerImpl;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.LegacyTouchUtils;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.util.LogUtil;
import com.touchtype.util.PackageInfoUtil;
import com.touchtype_fluency.Punctuator;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.TokenizationProvider;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputEventModelImpl
  implements InputEventModel, OnCandidatesUpdateRequestListener
{
  private static final String TAG = InputEventModelImpl.class.getSimpleName();
  CandidateStateHandler mCandidateStateHandler;
  CandidatesUpdater mCandidatesUpdater;
  ChromeBrowserWorkaround mChromeBrowserWorkaround;
  private Set<EditorInfoField> mFieldsWhereCursorBeforeTextWorks = new HashSet();
  private Set<EditorInfoField> mFieldsWhereExtractedTextWorks = new HashSet();
  private boolean mFlowBegun = false;
  InputConnectionProxy mInputConnectionProxy;
  InputEventFactory mInputEventFactory;
  InputEventHandler mInputEventHandler;
  MinimalInputMethodService mInputMethodService;
  KeyPressModel mKeyPressModel;
  KeyTranslationLayer mKeyTranslationLayer;
  KeyboardState mKeyboardState;
  Learner mLearner;
  ListenerManager mListenerManager;
  PredictionsRequester mPredictionsRequester;
  TouchTypeStats mStats;
  TouchHistoryManager mTouchHistoryManager;
  
  public InputEventModelImpl(InputEventFactory paramInputEventFactory, InputEventHandler paramInputEventHandler, InputConnectionProxy paramInputConnectionProxy, ListenerManager paramListenerManager, KeyboardState paramKeyboardState, TouchHistoryManager paramTouchHistoryManager, CandidatesUpdater paramCandidatesUpdater, CandidateStateHandler paramCandidateStateHandler, PredictionsRequester paramPredictionsRequester, KeyPressModel paramKeyPressModel, KeyTranslationLayer paramKeyTranslationLayer, TouchTypeStats paramTouchTypeStats, Learner paramLearner, MinimalInputMethodService paramMinimalInputMethodService, ChromeBrowserWorkaround paramChromeBrowserWorkaround)
  {
    this.mInputEventFactory = paramInputEventFactory;
    this.mInputEventHandler = paramInputEventHandler;
    this.mInputConnectionProxy = paramInputConnectionProxy;
    this.mListenerManager = paramListenerManager;
    this.mKeyboardState = paramKeyboardState;
    this.mTouchHistoryManager = paramTouchHistoryManager;
    this.mCandidatesUpdater = paramCandidatesUpdater;
    this.mCandidateStateHandler = paramCandidateStateHandler;
    this.mPredictionsRequester = paramPredictionsRequester;
    this.mKeyPressModel = paramKeyPressModel;
    this.mKeyTranslationLayer = paramKeyTranslationLayer;
    this.mStats = paramTouchTypeStats;
    this.mLearner = paramLearner;
    this.mInputMethodService = paramMinimalInputMethodService;
    this.mChromeBrowserWorkaround = paramChromeBrowserWorkaround;
  }
  
  public static InputEventModelImpl createDefault(Context paramContext, MinimalInputMethodService paramMinimalInputMethodService, BackgroundExecutor paramBackgroundExecutor, ListenerManager paramListenerManager, CandidatesUpdater paramCandidatesUpdater, CandidateStateHandler paramCandidateStateHandler, Learner paramLearner, TokenizationProvider paramTokenizationProvider, KeyPressModel paramKeyPressModel, TouchTypePreferences paramTouchTypePreferences, LegacyTouchUtils paramLegacyTouchUtils)
  {
    TextUtilsImpl localTextUtilsImpl = TextUtilsImpl.createDefault(paramContext);
    TouchTypeStats localTouchTypeStats = paramTouchTypePreferences.getTouchTypeStats();
    KeyboardStateImpl localKeyboardStateImpl = new KeyboardStateImpl(paramListenerManager, paramTouchTypePreferences);
    KeyTranslationLayerImpl localKeyTranslationLayerImpl = new KeyTranslationLayerImpl(paramTouchTypePreferences);
    AndroidInputConnectionWrapper localAndroidInputConnectionWrapper = new AndroidInputConnectionWrapper(paramMinimalInputMethodService, localKeyboardStateImpl, paramTokenizationProvider);
    TouchHistoryManagerImpl localTouchHistoryManagerImpl = new TouchHistoryManagerImpl(new PredictionRequestManagerImpl(paramListenerManager, paramCandidatesUpdater, localKeyboardStateImpl), paramKeyPressModel, localKeyboardStateImpl, paramBackgroundExecutor, paramTokenizationProvider, new HistoryText(new ExtractedText(), paramTokenizationProvider));
    HashSet localHashSet = new HashSet();
    localHashSet.add(new StatsLoggerImpl(localTouchTypeStats));
    InputConnectionDelegateImpl localInputConnectionDelegateImpl = new InputConnectionDelegateImpl(localAndroidInputConnectionWrapper, localTouchHistoryManagerImpl, localHashSet, localKeyboardStateImpl);
    boolean bool = ProductConfiguration.isWatchBuild(paramContext);
    DefaultCandidatesUpdateRequestFactory localDefaultCandidatesUpdateRequestFactory = new DefaultCandidatesUpdateRequestFactory(localKeyboardStateImpl, localTouchHistoryManagerImpl, bool);
    paramCandidateStateHandler.setCandidatesUpdater(paramCandidatesUpdater);
    paramCandidateStateHandler.setRequestFactory(localDefaultCandidatesUpdateRequestFactory);
    PredictionsRequester localPredictionsRequester = new PredictionsRequester(paramCandidatesUpdater, localDefaultCandidatesUpdateRequestFactory, paramListenerManager);
    InputEventFactoryImpl localInputEventFactoryImpl = new InputEventFactoryImpl(localInputConnectionDelegateImpl, localKeyTranslationLayerImpl, paramLegacyTouchUtils);
    InputEventHandlerImpl localInputEventHandlerImpl = new InputEventHandlerImpl(localTouchHistoryManagerImpl, localKeyboardStateImpl, localTouchTypeStats, localTextUtilsImpl, paramLearner, paramCandidateStateHandler);
    ChromeBrowserWorkaround localChromeBrowserWorkaround = new ChromeBrowserWorkaround(paramMinimalInputMethodService, localPredictionsRequester);
    return new InputEventModelImpl(localInputEventFactoryImpl, localInputEventHandlerImpl, localInputConnectionDelegateImpl, paramListenerManager, localKeyboardStateImpl, localTouchHistoryManagerImpl, paramCandidatesUpdater, paramCandidateStateHandler, localPredictionsRequester, paramKeyPressModel, localKeyTranslationLayerImpl, localTouchTypeStats, paramLearner, paramMinimalInputMethodService, localChromeBrowserWorkaround);
  }
  
  private boolean extractedTextWorksHere(EditorInfoField paramEditorInfoField)
  {
    if (this.mInputConnectionProxy.extractedTextWorks()) {
      this.mFieldsWhereExtractedTextWorks.add(paramEditorInfoField);
    }
    return this.mFieldsWhereExtractedTextWorks.contains(paramEditorInfoField);
  }
  
  private void incrementKeyCountStats(KeyEvent paramKeyEvent)
  {
    String str1 = paramKeyEvent.getCharacters();
    int i = paramKeyEvent.getKeyCode();
    String str2;
    if (str1 != null) {
      str2 = str1;
    }
    for (;;)
    {
      this.mStats.incrementKeyCount(str2);
      return;
      if (i > 0) {
        str2 = (char)i;
      } else if (i < 0) {
        str2 = Integer.toString(i);
      } else {
        str2 = "invalid";
      }
    }
  }
  
  private void learnFromFieldText(Sequence paramSequence)
  {
    if ((paramSequence.size() <= 100) && (paramSequence.size() != 0)) {
      this.mLearner.learnWordsInContext(paramSequence);
    }
  }
  
  private static void logAbortedEvent(String paramString, InputEventModelException paramInputEventModelException)
  {
    String str = TAG;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = paramInputEventModelException.toString();
    arrayOfObject[2] = paramInputEventModelException.getClass().toString();
    LogUtil.e(str, String.format("%s event aborted: %s (%s)", arrayOfObject));
  }
  
  private void resetComposingText()
  {
    this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createResetComposingTextEvent(-1));
  }
  
  private boolean textBeforeCursorWorksHere(EditorInfoField paramEditorInfoField)
  {
    if (this.mInputConnectionProxy.textBeforeCursorWorks()) {
      this.mFieldsWhereCursorBeforeTextWorks.add(paramEditorInfoField);
    }
    return this.mFieldsWhereCursorBeforeTextWorks.contains(paramEditorInfoField);
  }
  
  public void addBufferedInputListener(BufferedInputListener paramBufferedInputListener)
  {
    this.mListenerManager.addBufferedInputListener(paramBufferedInputListener);
  }
  
  public void addCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener)
  {
    this.mListenerManager.addCandidateUpdateListener(paramOnCandidatesUpdateRequestListener);
  }
  
  public void addFlowStateChangedListener(OnFlowStateChangedListener paramOnFlowStateChangedListener)
  {
    this.mListenerManager.addFlowStateChangedListener(paramOnFlowStateChangedListener);
  }
  
  public void addInputFilterListener(InputFilterListener paramInputFilterListener, int paramInt)
  {
    this.mListenerManager.addInputFilterListener(paramInputFilterListener, paramInt);
  }
  
  public void addPredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener)
  {
    this.mListenerManager.addPredictionsEnabledListener(paramPredictionsAvailabilityListener);
  }
  
  public void addShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener)
  {
    this.mListenerManager.addShiftStateChangedListener(paramOnShiftStateChangedListener);
  }
  
  public void addUpdatedCandidatesListener(UpdatedCandidatesListener paramUpdatedCandidatesListener, int paramInt)
  {
    this.mPredictionsRequester.addUpdatedCandidatesListener(paramUpdatedCandidatesListener, paramInt);
  }
  
  public void autoCommitFlow(Candidate paramCandidate, TouchHistoryProxy paramTouchHistoryProxy)
  {
    try
    {
      this.mInputEventHandler.handleInput(this.mInputConnectionProxy, new FlowAutoCommitEvent(paramCandidate, paramTouchHistoryProxy));
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("autoCommitFlow", localInputEventModelException);
    }
  }
  
  public void autoCommitUpToFailedFlow(Candidate paramCandidate)
  {
    try
    {
      this.mInputEventHandler.handleInput(this.mInputConnectionProxy, new FlowFailedEvent(paramCandidate));
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("autoCommitFlow", localInputEventModelException);
    }
  }
  
  public void commitBuffer() {}
  
  public void cycleCandidates()
  {
    if (isPredictionEnabled()) {
      this.mCandidateStateHandler.cycleCandidates();
    }
  }
  
  public void enabledLanguagePacksChanged(int paramInt)
  {
    this.mKeyboardState.enabledLanguagePacksChanged(paramInt);
  }
  
  public boolean evaluateInputShownUsedInsteadOfUpdateSelection()
  {
    return this.mKeyboardState.evaluateInputShownUsedInsteadOfUpdateSelection();
  }
  
  public KeyPressModel getKeyPressModel()
  {
    return this.mKeyPressModel;
  }
  
  public TouchTypeSoftKeyboard.ShiftState getShiftState()
  {
    return this.mKeyboardState.getShiftState();
  }
  
  public TouchTypeExtractedText getTouchTypeExtractedText(boolean paramBoolean)
  {
    try
    {
      TouchTypeExtractedText localTouchTypeExtractedText = this.mInputConnectionProxy.getTouchTypeExtractedText(paramBoolean);
      return localTouchTypeExtractedText;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("getTouchTypeExtractedText", localInputEventModelException);
    }
    return null;
  }
  
  public void handleCandidatesUpdateRequest()
  {
    if (isPredictionEnabled()) {
      this.mChromeBrowserWorkaround.recordCurrentWordContext();
    }
  }
  
  public void handleDeleteLastWord(int paramInt, EnumSet<ActionType> paramEnumSet)
  {
    try
    {
      this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createDeleteLastWordEvent(paramEnumSet));
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("handleDeleteLastWord", localInputEventModelException);
    }
  }
  
  public void handleVoiceInput(CharSequence paramCharSequence)
  {
    try
    {
      this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createEventFromVoiceInput(paramCharSequence));
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("handleVoiceInput", localInputEventModelException);
    }
  }
  
  public boolean isLayoutCyclingEnabled()
  {
    return this.mKeyboardState.isLayoutCyclingEnabled();
  }
  
  public boolean isPredictionEnabled()
  {
    return this.mKeyboardState.isPredictionEnabled();
  }
  
  public boolean isSearchField()
  {
    return this.mKeyboardState.isSearchField();
  }
  
  public void onCompletionAccepted(CompletionInfo paramCompletionInfo)
  {
    try
    {
      this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createEventFromCompletion(paramCompletionInfo));
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onCompletionAccepted", localInputEventModelException);
    }
  }
  
  public void onContinuousInputSample(FlowEvent paramFlowEvent)
  {
    try
    {
      if (this.mFlowBegun) {
        this.mInputEventHandler.handleInput(this.mInputEventFactory.createEventFromPoint(paramFlowEvent));
      }
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onContinuousInputSample", localInputEventModelException);
    }
  }
  
  public void onContinuousInputSamples(List<FlowEvent> paramList)
  {
    try
    {
      if (this.mFlowBegun) {
        this.mInputEventHandler.handleInput(this.mInputEventFactory.createEventFromPoints(paramList));
      }
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onContinuousInputSamples", localInputEventModelException);
    }
  }
  
  public void onCreate(Context paramContext)
  {
    this.mKeyboardState.onCreate(paramContext);
    this.mKeyTranslationLayer.onCreate(paramContext);
    this.mListenerManager.addCandidateUpdateListener(this);
    addShiftStateChangedListener(this.mPredictionsRequester);
    this.mPredictionsRequester.setPredictionsAvailability(this.mKeyboardState.getPredictionsAvailability());
    addPredictionsEnabledListener(this.mPredictionsRequester);
  }
  
  public void onCycle(List<String> paramList)
  {
    throw new UnsupportedOperationException("IEM does not yet support cycling input");
  }
  
  public void onDestroy(Context paramContext)
  {
    removePredictionsEnabledListener(this.mPredictionsRequester);
    removeShiftStateChangedListener(this.mPredictionsRequester);
    this.mListenerManager.removeCandidateUpdateListener(this);
  }
  
  public void onEnterKey()
  {
    if (!this.mKeyboardState.isMultiLineField())
    {
      learnFromFieldText(this.mTouchHistoryManager.getWordsForLearning().get());
      this.mTouchHistoryManager.dontGetWordsForLearningUntilResync();
    }
    try
    {
      this.mInputEventHandler.handleInput(this.mInputConnectionProxy, new SoftKeyInputEvent('\n', true));
      selectionUpdated(-1, -1, -1, -1, -1, -1);
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      Log.e("onEnterKey", localInputEventModelException.getMessage(), localInputEventModelException);
      logAbortedEvent("onEnterKey", localInputEventModelException);
    }
  }
  
  public void onFinishInput()
  {
    learnFromFieldText(this.mTouchHistoryManager.getWordsForLearning().get());
    this.mTouchHistoryManager.dontGetWordsForLearningUntilResync();
  }
  
  public void onFinishInputView()
  {
    learnFromFieldText(this.mTouchHistoryManager.getWordsForLearning().get());
    this.mTouchHistoryManager.dontGetWordsForLearningUntilResync();
  }
  
  public void onFlowBegun(boolean paramBoolean)
  {
    if (paramBoolean) {}
    try
    {
      this.mKeyboardState.updateMetaStateOnFlowBegun();
      this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createFlowBegunEvent());
      this.mFlowBegun = true;
      this.mListenerManager.notifyFlowStateChangedListeners(true);
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("OnFlowBegun", localInputEventModelException);
    }
  }
  
  public void onFlowComplete()
  {
    try
    {
      if (this.mFlowBegun)
      {
        FlowCompleteEvent localFlowCompleteEvent = this.mInputEventFactory.createFlowCompleteEvent();
        this.mInputEventHandler.handleInput(this.mInputConnectionProxy, localFlowCompleteEvent);
        this.mKeyboardState.updateMetaStateOnFlowComplete(localFlowCompleteEvent.getEventType());
        this.mFlowBegun = false;
      }
      this.mListenerManager.notifyFlowStateChangedListeners(false);
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onFlowComplete", localInputEventModelException);
    }
  }
  
  public boolean onHardKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    try
    {
      this.mKeyboardState.updateMetaStateOnHardKeyDown(paramInt, paramKeyEvent);
      ConnectionInputEvent localConnectionInputEvent = this.mInputEventFactory.createEventFromHardKey(paramKeyEvent, this.mKeyboardState.getMetaState(), this.mKeyboardState.isMenuKeyPressed());
      boolean bool = false;
      if (localConnectionInputEvent != null)
      {
        this.mInputEventHandler.handleInput(this.mInputConnectionProxy, localConnectionInputEvent);
        paramKeyEvent.startTracking();
        bool = true;
      }
      return bool;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onHardKeyDown", localInputEventModelException);
    }
    return false;
  }
  
  public boolean onHardKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = this.mKeyboardState.getMetaState();
    paramKeyEvent.getUnicodeChar(i);
    try
    {
      this.mKeyboardState.updateMetaStateOnHardKeyUp(paramInt, paramKeyEvent, this.mInputConnectionProxy);
      if (this.mInputEventFactory.createEventFromHardKey(paramKeyEvent, i, this.mKeyboardState.isMenuKeyPressed()) != null) {
        return true;
      }
      resetComposingText();
      return false;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onHardKeyUp", localInputEventModelException);
    }
    return false;
  }
  
  public void onInputFilterInput(int paramInt, String paramString) {}
  
  public void onKeyboardChanged(KeyboardBehaviour paramKeyboardBehaviour)
  {
    this.mTouchHistoryManager.resetAllHistory(getTouchTypeExtractedText(false));
    this.mKeyboardState.setAlwaysComposeWordByWord(paramKeyboardBehaviour.getAlwaysComposeWordByWord());
    this.mKeyboardState.setNeverAutocomplete(paramKeyboardBehaviour.getNeverAutocomplete());
    this.mKeyboardState.setDisableQuickPeriod(paramKeyboardBehaviour.getDisableQuickPeriod());
    this.mKeyboardState.setUseTelexConversionLayer(paramKeyboardBehaviour.getUseTelexConversionLayer());
    this.mKeyboardState.setUseZeroWidthSpace(paramKeyboardBehaviour.getUseZeroWidthSpace());
    KeyboardState localKeyboardState = this.mKeyboardState;
    boolean bool1 = paramKeyboardBehaviour.getUseTelexConversionLayer();
    boolean bool2 = false;
    if (!bool1) {
      bool2 = true;
    }
    localKeyboardState.setKeyPressModellingEnabled(bool2);
    this.mKeyboardState.setSearchType(paramKeyboardBehaviour.getSearchType());
    this.mCandidatesUpdater.setUsingZeroWidthSpace(paramKeyboardBehaviour.getUseZeroWidthSpace());
    this.mCandidatesUpdater.setCharacterMapLayout(paramKeyboardBehaviour.getCharacterMapLayout());
    this.mCandidatesUpdater.setInputType(paramKeyboardBehaviour.getInputType());
  }
  
  public void onKeyboardHidden()
  {
    this.mKeyPressModel.onKeyboardHidden();
  }
  
  public void onPredictionSelected(Candidate paramCandidate)
  {
    if (isPredictionEnabled()) {}
    try
    {
      this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createEventFromPrediction(paramCandidate));
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onPredictionSelected", localInputEventModelException);
    }
  }
  
  public void onSoftKey(KeyEvent paramKeyEvent)
  {
    try
    {
      if (paramKeyEvent.getAction() == 0)
      {
        this.mKeyboardState.updateMetaStateOnSoftKeyDown(paramKeyEvent.getKeyCode(), paramKeyEvent);
        return;
      }
      this.mKeyboardState.updateMetaStateOnSoftKeyUp(paramKeyEvent.getKeyCode(), paramKeyEvent, this.mInputConnectionProxy);
      switch (paramKeyEvent.getKeyCode())
      {
      default: 
        this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createEventFromSoftKey(paramKeyEvent));
        incrementKeyCountStats(paramKeyEvent);
        return;
      }
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onKey", localInputEventModelException);
      return;
    }
    this.mInputConnectionProxy.sendDownUpKeyEvents(paramKeyEvent.getKeyCode());
    if (!this.mKeyboardState.cursorMovementUpdatesSelection()) {
      selectionUpdated(-1, -1, -1, -1, -1, -1);
    }
  }
  
  public void onStartInput(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      LazySequence localLazySequence = this.mTouchHistoryManager.getWordsForLearning();
      this.mTouchHistoryManager.dontGetWordsForLearningUntilResync();
      EditorInfoField localEditorInfoField = new EditorInfoField(paramEditorInfo.fieldId, paramEditorInfo.packageName);
      this.mKeyboardState.notifyNewEditorInfo(paramEditorInfo, paramPackageInfoUtil, extractedTextWorksHere(localEditorInfoField), textBeforeCursorWorksHere(localEditorInfoField), paramBoolean1, paramBoolean2);
      try
      {
        boolean bool = this.mInputConnectionProxy.resync();
        if ((paramBoolean1) && (!bool)) {
          learnFromFieldText(localLazySequence.get());
        }
        if (this.mKeyboardState.shouldFixWebKitInputConnection()) {
          this.mInputConnectionProxy.fixWebKitInputConnection(paramBoolean1);
        }
        resetComposingText();
        updateShiftState();
        this.mChromeBrowserWorkaround.onStartInput(paramEditorInfo.packageName);
        return;
      }
      catch (ExtractedTextUnavailableException localExtractedTextUnavailableException)
      {
        if (paramBoolean1) {
          learnFromFieldText(localLazySequence.get());
        }
        throw localExtractedTextUnavailableException;
      }
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onStartInput", localInputEventModelException);
    }
  }
  
  public void onStartInputView(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      if ((!this.mKeyboardState.extractedTextWorks()) || (!this.mKeyboardState.textBeforeCursorWorks()))
      {
        EditorInfoField localEditorInfoField = new EditorInfoField(paramEditorInfo.fieldId, paramEditorInfo.packageName);
        boolean bool1 = extractedTextWorksHere(localEditorInfoField);
        boolean bool2 = textBeforeCursorWorksHere(localEditorInfoField);
        if ((bool1 != this.mKeyboardState.extractedTextWorks()) || (bool2 != this.mKeyboardState.textBeforeCursorWorks())) {
          this.mKeyboardState.notifyNewEditorInfo(paramEditorInfo, paramPackageInfoUtil, bool1, bool2, paramBoolean1, paramBoolean2);
        }
      }
      resetComposingText();
      updateShiftState();
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("onStartInputView", localInputEventModelException);
    }
  }
  
  public void refreshPredictions(boolean paramBoolean)
  {
    this.mPredictionsRequester.invalidateCandidates(paramBoolean);
  }
  
  public void removeCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener)
  {
    this.mListenerManager.removeCandidateUpdateListener(paramOnCandidatesUpdateRequestListener);
  }
  
  public void removePredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener)
  {
    this.mListenerManager.removePredictionsEnabledListener(paramPredictionsAvailabilityListener);
  }
  
  public void removeShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener)
  {
    this.mListenerManager.removeShiftStateChangedListener(paramOnShiftStateChangedListener);
  }
  
  public void selectionUpdated(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if ((paramInt3 == 0) && (paramInt4 == 0) && (paramInt5 == -1) && (paramInt6 == -1)) {}
    try
    {
      if (!this.mTouchHistoryManager.isHistoryTextEmpty())
      {
        LazySequence localLazySequence = this.mTouchHistoryManager.getWordsForLearning();
        this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createSelectionChangedEvent(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
        if (this.mTouchHistoryManager.isHistoryTextEmpty())
        {
          learnFromFieldText(localLazySequence.get());
          this.mTouchHistoryManager.dontGetWordsForLearningUntilResync();
        }
      }
      else
      {
        this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createSelectionChangedEvent(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
        return;
      }
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("selectionUpdated", localInputEventModelException);
    }
  }
  
  public void setFluencyPunctuator(Punctuator paramPunctuator)
  {
    this.mInputEventHandler.setFluencyPunctuator(paramPunctuator);
  }
  
  public void setStorageAvailable(boolean paramBoolean)
  {
    this.mKeyboardState.setStorageAvailable(paramBoolean);
  }
  
  public void updateKeyPressModel(Set<String> paramSet1, Set<String> paramSet2, FluencyServiceProxy paramFluencyServiceProxy, boolean paramBoolean)
  {
    if (paramBoolean) {}
    try
    {
      this.mKeyPressModel.onKeyboardHidden();
      for (;;)
      {
        paramFluencyServiceProxy.runWhenConnected(new Runnable()
        {
          public void run()
          {
            InputEventModelImpl.this.refreshPredictions(true);
          }
        });
        return;
        this.mKeyPressModel.onKeyboardVisible(paramSet1, paramSet2);
      }
    }
    catch (InputEventModelException localInputEventModelException)
    {
      for (;;)
      {
        logAbortedEvent("usingKeyboard", localInputEventModelException);
      }
    }
  }
  
  public void updateShiftState()
  {
    try
    {
      this.mInputEventHandler.handleInput(this.mInputConnectionProxy, this.mInputEventFactory.createUpdateShiftStateEvent());
      return;
    }
    catch (InputEventModelException localInputEventModelException)
    {
      logAbortedEvent("updateShiftState", localInputEventModelException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.InputEventModelImpl
 * JD-Core Version:    0.7.0.1
 */