package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.inputeventmodel.BatchEditInProgressException;
import com.touchtype.keyboard.inputeventmodel.Composer;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.TextUtils;
import com.touchtype.keyboard.inputeventmodel.events.CompletionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionlessInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.DeleteInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowAutoCommitEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowBegunEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowCompleteEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowFailedEvent;
import com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.MultipleFlowSamplesEvent;
import com.touchtype.keyboard.inputeventmodel.events.PredictionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SelectionChangedInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SingleFlowSampleEvent;
import com.touchtype.keyboard.inputeventmodel.events.StateChangeInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.TextInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.UpdateShiftStateEvent;
import com.touchtype.keyboard.inputeventmodel.events.VoiceInputEvent;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Punctuator;

public final class InputEventHandlerImpl
  implements InputEventHandler
{
  private CompletionInputEventHandler mCompletionHandler;
  private Composer mComposer;
  private CursorInputEventHandler mCursorHandler;
  private DeleteInputEventHandler mDeleteHandler;
  private FlowAutoCommitEventHandler mFlowAutoCommitHandler;
  private FlowBegunEventHandler mFlowBegunHandler;
  private FlowCompleteEventHandler mFlowCompleteHandler;
  private FlowFailedEventHandler mFlowFailedHandler;
  private KeyInputEventHandler mKeyHandler;
  private KeyboardState mKeyboardState;
  private ConnectionInputEvent mLastInputEventSentMidBatchEdit = null;
  private MultipleFlowSamplesHandler mMultipleSamplesHandler;
  private PredictionInputEventHandler mPredictionHandler;
  private PunctuatorImpl mPunctuator;
  private ShiftStateHandler mShiftStateHandler;
  private SingleFlowSampleHandler mSingleSampleHandler;
  private ConnectionInputEventHandler mStateHandler;
  private TouchTypeStats mStats;
  private TextInputEventHandler mTextHandler;
  private VoiceInputEventHandler mVoiceHandler;
  
  public InputEventHandlerImpl(TouchHistoryManager paramTouchHistoryManager, KeyboardState paramKeyboardState, TouchTypeStats paramTouchTypeStats, TextUtils paramTextUtils, Learner paramLearner, DefaultPredictionProvider paramDefaultPredictionProvider)
  {
    CommonPredictionActions localCommonPredictionActions = new CommonPredictionActions(paramLearner, paramTouchHistoryManager, paramTouchTypeStats, paramKeyboardState);
    this.mKeyboardState = paramKeyboardState;
    this.mStats = paramTouchTypeStats;
    this.mComposer = new Composer(this.mKeyboardState, paramTextUtils);
    this.mPunctuator = new PunctuatorImpl(paramKeyboardState, this.mComposer, paramDefaultPredictionProvider, paramLearner);
    this.mKeyHandler = new KeyInputEventHandler(this.mPunctuator, this.mComposer, paramKeyboardState);
    this.mPredictionHandler = new PredictionInputEventHandler(this.mPunctuator, paramKeyboardState, paramTextUtils, this.mComposer, localCommonPredictionActions);
    this.mDeleteHandler = new DeleteInputEventHandler(paramTouchHistoryManager, paramKeyboardState, paramTouchTypeStats, paramLearner);
    this.mVoiceHandler = new VoiceInputEventHandler(paramTextUtils, this.mPunctuator);
    this.mCursorHandler = new CursorInputEventHandler(paramKeyboardState, paramTouchHistoryManager);
    this.mTextHandler = new TextInputEventHandler(paramKeyboardState);
    this.mCompletionHandler = new CompletionInputEventHandler(this.mPunctuator);
    this.mShiftStateHandler = new ShiftStateHandler(paramKeyboardState, paramTextUtils);
    this.mStateHandler = new StateChangeInputEventHandler(paramKeyboardState);
    this.mSingleSampleHandler = new SingleFlowSampleHandler(paramTouchHistoryManager, this.mStats);
    this.mMultipleSamplesHandler = new MultipleFlowSamplesHandler(paramTouchHistoryManager, this.mStats);
    this.mFlowCompleteHandler = new FlowCompleteEventHandler(paramTouchHistoryManager, paramDefaultPredictionProvider, this.mComposer, localCommonPredictionActions, this.mPunctuator, this.mStats);
    this.mFlowFailedHandler = new FlowFailedEventHandler(paramKeyboardState, this.mComposer, localCommonPredictionActions, this.mPunctuator, this.mStats);
    this.mFlowBegunHandler = new FlowBegunEventHandler(paramDefaultPredictionProvider, this.mPunctuator);
    this.mFlowAutoCommitHandler = new FlowAutoCommitEventHandler(paramTouchHistoryManager, paramKeyboardState, this.mPunctuator, paramTouchTypeStats);
    KeyInputPredictionEventHandler localKeyInputPredictionEventHandler = new KeyInputPredictionEventHandler(paramDefaultPredictionProvider, paramTextUtils, this.mComposer, localCommonPredictionActions);
    this.mPunctuator.setDelegateHandlers(localKeyInputPredictionEventHandler, this.mKeyHandler);
    this.mComposer.setDelegateHandlers(this.mCursorHandler, this.mShiftStateHandler);
    this.mPredictionHandler.setDelegateHandlers(this.mKeyHandler);
    this.mDeleteHandler.setDelegateHandlers(this.mCursorHandler);
    this.mVoiceHandler.setDelegateHandlers(this.mTextHandler);
    this.mCursorHandler.setDelegateHandlers(this.mShiftStateHandler);
    this.mTextHandler.setDelegateHandlers(this.mCursorHandler);
    this.mFlowCompleteHandler.setDelegateHandlers(this.mKeyHandler);
    this.mFlowFailedHandler.setDelegateHandlers(this.mKeyHandler);
    this.mFlowBegunHandler.setDelegateHandlers(this.mKeyHandler);
    this.mFlowAutoCommitHandler.setDelegateHandlers(this.mShiftStateHandler);
  }
  
  private ConnectionInputEventHandler handlerFromEvent(ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    if ((paramConnectionInputEvent instanceof StateChangeInputEvent)) {
      return this.mStateHandler;
    }
    if ((paramConnectionInputEvent instanceof KeyInputEvent)) {
      return this.mKeyHandler;
    }
    if ((paramConnectionInputEvent instanceof PredictionInputEvent)) {
      return this.mPredictionHandler;
    }
    if ((paramConnectionInputEvent instanceof DeleteInputEvent)) {
      return this.mDeleteHandler;
    }
    if ((paramConnectionInputEvent instanceof VoiceInputEvent)) {
      return this.mVoiceHandler;
    }
    if ((paramConnectionInputEvent instanceof SelectionChangedInputEvent)) {
      return this.mCursorHandler;
    }
    if ((paramConnectionInputEvent instanceof TextInputEvent)) {
      return this.mTextHandler;
    }
    if ((paramConnectionInputEvent instanceof CompletionInputEvent)) {
      return this.mCompletionHandler;
    }
    if ((paramConnectionInputEvent instanceof UpdateShiftStateEvent)) {
      return this.mShiftStateHandler;
    }
    if ((paramConnectionInputEvent instanceof FlowCompleteEvent)) {
      return this.mFlowCompleteHandler;
    }
    if ((paramConnectionInputEvent instanceof FlowFailedEvent)) {
      return this.mFlowFailedHandler;
    }
    if ((paramConnectionInputEvent instanceof FlowBegunEvent)) {
      return this.mFlowBegunHandler;
    }
    if ((paramConnectionInputEvent instanceof FlowAutoCommitEvent)) {
      return this.mFlowAutoCommitHandler;
    }
    throw new UnhandledInputEventException("Cannot handle event");
  }
  
  private ConnectionlessInputEventHandler handlerFromEvent(ConnectionlessInputEvent paramConnectionlessInputEvent)
  {
    if ((paramConnectionlessInputEvent instanceof SingleFlowSampleEvent)) {
      return this.mSingleSampleHandler;
    }
    if ((paramConnectionlessInputEvent instanceof MultipleFlowSamplesEvent)) {
      return this.mMultipleSamplesHandler;
    }
    throw new UnhandledInputEventException("Cannot handle event");
  }
  
  private void keyStroke()
  {
    if (this.mKeyboardState.isPredictionEnabled()) {
      this.mStats.keyStroked();
    }
  }
  
  private void runHandlerInBatchEdit(ConnectionInputEventHandler paramConnectionInputEventHandler, InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException, BatchEditInProgressException
  {
    boolean bool = paramConnectionInputEvent instanceof SelectionChangedInputEvent;
    if (paramInputConnectionProxy.beginBatchEdit(bool)) {
      try
      {
        paramConnectionInputEventHandler.handleInput(paramInputConnectionProxy, paramConnectionInputEvent);
        return;
      }
      catch (Exception localException)
      {
        LogUtil.e("InputEventHandlerImpl", localException.getMessage(), localException);
        throw new UnhandledInputEventException(localException);
      }
      finally
      {
        paramInputConnectionProxy.endBatchEdit(bool, paramConnectionInputEvent.getEventType());
      }
    }
    throw new UnhandledInputEventException("Unable to start transaction");
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    if (paramConnectionInputEvent == null) {}
    for (;;)
    {
      try
      {
        LogUtil.w("InputEventHandlerImpl", "Ignoring null InputEvent");
        return;
      }
      finally {}
      ConnectionInputEventHandler localConnectionInputEventHandler = handlerFromEvent(paramConnectionInputEvent);
      if (localConnectionInputEventHandler.logKeyStroke()) {
        keyStroke();
      }
      try
      {
        this.mLastInputEventSentMidBatchEdit = null;
        runHandlerInBatchEdit(localConnectionInputEventHandler, paramInputConnectionProxy, paramConnectionInputEvent);
        if (this.mLastInputEventSentMidBatchEdit == null) {
          continue;
        }
        handleInput(paramInputConnectionProxy, this.mLastInputEventSentMidBatchEdit);
      }
      catch (BatchEditInProgressException localBatchEditInProgressException)
      {
        if (!(paramConnectionInputEvent instanceof SelectionChangedInputEvent)) {
          break;
        }
      }
      this.mLastInputEventSentMidBatchEdit = paramConnectionInputEvent;
    }
    throw new UnhandledInputEventException("Batch edit attempted while one already in progress for: " + paramConnectionInputEvent.toString());
  }
  
  public void handleInput(ConnectionlessInputEvent paramConnectionlessInputEvent)
    throws UnhandledInputEventException
  {
    try
    {
      handlerFromEvent(paramConnectionlessInputEvent).handleInput(paramConnectionlessInputEvent);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void setFluencyPunctuator(Punctuator paramPunctuator)
  {
    this.mPunctuator.setFluencyPunctuator(paramPunctuator);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.InputEventHandlerImpl
 * JD-Core Version:    0.7.0.1
 */