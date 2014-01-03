package com.touchtype.keyboard.inputeventmodel.handlers;

import com.google.common.base.Strings;
import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowAutoCommitEvent;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype.report.LanguageModelMetrics;
import com.touchtype.report.TouchTypeStats;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import junit.framework.Assert;

public final class FlowAutoCommitEventHandler
  implements ConnectionInputEventHandler
{
  private final KeyboardState mKeyboardState;
  private Punctuator mPunctuator;
  private ConnectionInputEventHandler mShiftStateHandler;
  private TouchTypeStats mStats;
  private TouchHistoryManager mTouchHistoryManager;
  
  public FlowAutoCommitEventHandler(TouchHistoryManager paramTouchHistoryManager, KeyboardState paramKeyboardState, Punctuator paramPunctuator, TouchTypeStats paramTouchTypeStats)
  {
    this.mTouchHistoryManager = paramTouchHistoryManager;
    this.mKeyboardState = paramKeyboardState;
    this.mPunctuator = paramPunctuator;
    this.mStats = paramTouchTypeStats;
  }
  
  private void updateStatistics(Candidate paramCandidate, CharSequence paramCharSequence)
  {
    String str = paramCandidate.source();
    if (!Strings.isNullOrEmpty(str))
    {
      int i = paramCandidate.version();
      LanguageModelMetrics localLanguageModelMetrics = this.mStats.getLanguageMetricsPerSourceAndVersion(str, i);
      localLanguageModelMetrics.incrementFlowAutocommit();
      localLanguageModelMetrics.incrementFlowedWordsBy(1);
      localLanguageModelMetrics.incrementFlowedCharactersBy(paramCharSequence.length());
    }
    this.mStats.incrementStatistic("stats_words_flowed");
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    Assert.assertTrue(paramConnectionInputEvent instanceof FlowAutoCommitEvent);
    FlowAutoCommitEvent localFlowAutoCommitEvent = (FlowAutoCommitEvent)paramConnectionInputEvent;
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    Candidate localCandidate = localFlowAutoCommitEvent.getCandidate();
    Prediction localPrediction = localCandidate.getPrediction();
    if (localPrediction.size() < 2) {}
    TouchHistoryMarker localTouchHistoryMarker;
    do
    {
      return;
      localTouchHistoryMarker = this.mTouchHistoryManager.getCurrentTouchHistoryMarker();
    } while (localTouchHistoryMarker.getTouchHistory().getTouchHistory() != localFlowAutoCommitEvent.getTouchHistory().getTouchHistory());
    String str = localPrediction.get(0);
    paramInputConnectionProxy.commitCorrectionFromAutoCommit(str, localTouchTypeExtractedText);
    paramInputConnectionProxy.commitText(this.mPunctuator.getWordSeparator(localTouchTypeExtractedText));
    localTouchHistoryMarker.dropFirstTerms(localPrediction, 1);
    paramInputConnectionProxy.setComposingTextFromAutoCommit("", localTouchTypeExtractedText, localTouchHistoryMarker);
    updateStatistics(localCandidate, str);
    this.mKeyboardState.removeShiftedAtStartFlag();
    this.mShiftStateHandler.handleInput(paramInputConnectionProxy, localFlowAutoCommitEvent);
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
  
  public void setDelegateHandlers(ConnectionInputEventHandler paramConnectionInputEventHandler)
  {
    this.mShiftStateHandler = paramConnectionInputEventHandler;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.FlowAutoCommitEventHandler
 * JD-Core Version:    0.7.0.1
 */