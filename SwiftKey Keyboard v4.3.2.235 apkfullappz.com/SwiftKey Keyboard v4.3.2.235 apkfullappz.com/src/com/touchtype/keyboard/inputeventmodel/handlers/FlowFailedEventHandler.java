package com.touchtype.keyboard.inputeventmodel.handlers;

import com.google.common.base.Strings;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.inputeventmodel.Composer;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowFailedEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype.report.LanguageModelMetrics;
import com.touchtype.report.TouchTypeStats;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import junit.framework.Assert;

public class FlowFailedEventHandler
  implements ConnectionInputEventHandler
{
  private static final String TAG = FlowFailedEventHandler.class.getSimpleName();
  private final CommonPredictionActions mCommon;
  private final Composer mComposer;
  private ConnectionInputEventHandler mKeyInputEventHandler;
  private final KeyboardState mKeyboardState;
  private final Punctuator mPunctuator;
  private final TouchTypeStats mStats;
  
  public FlowFailedEventHandler(KeyboardState paramKeyboardState, Composer paramComposer, CommonPredictionActions paramCommonPredictionActions, Punctuator paramPunctuator, TouchTypeStats paramTouchTypeStats)
  {
    this.mKeyboardState = paramKeyboardState;
    this.mComposer = paramComposer;
    this.mCommon = paramCommonPredictionActions;
    this.mPunctuator = paramPunctuator;
    this.mStats = paramTouchTypeStats;
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    Assert.assertTrue(paramConnectionInputEvent instanceof FlowFailedEvent);
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    Candidate localCandidate = ((FlowFailedEvent)paramConnectionInputEvent).getCandidate();
    String str1 = localCandidate.toString();
    CommonPredictionActions localCommonPredictionActions = this.mCommon;
    if (!str1.equals(localTouchTypeExtractedText.getCurrentWord())) {}
    for (boolean bool = true;; bool = false)
    {
      localCommonPredictionActions.acceptCandidate(paramInputConnectionProxy, paramConnectionInputEvent, localCandidate, str1, localTouchTypeExtractedText, bool, true);
      if (!this.mComposer.tryAndReuseSpace(paramInputConnectionProxy, localTouchTypeExtractedText))
      {
        String str2 = localCandidate.source();
        if (!Strings.isNullOrEmpty(str2))
        {
          int i = localCandidate.version();
          this.mStats.getLanguageMetricsPerSourceAndVersion(str2, i).incrementFlowedCharactersBy(1);
        }
        SoftKeyInputEvent localSoftKeyInputEvent = new SoftKeyInputEvent(this.mPunctuator.getWordSeparator(localTouchTypeExtractedText).charAt(0), false);
        this.mKeyInputEventHandler.handleInput(paramInputConnectionProxy, localSoftKeyInputEvent.markAsInsertingCandidate(localCandidate));
      }
      this.mKeyboardState.removeShiftedAtStartFlag();
      return;
    }
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
  
  public void setDelegateHandlers(ConnectionInputEventHandler paramConnectionInputEventHandler)
  {
    this.mKeyInputEventHandler = paramConnectionInputEventHandler;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.FlowFailedEventHandler
 * JD-Core Version:    0.7.0.1
 */