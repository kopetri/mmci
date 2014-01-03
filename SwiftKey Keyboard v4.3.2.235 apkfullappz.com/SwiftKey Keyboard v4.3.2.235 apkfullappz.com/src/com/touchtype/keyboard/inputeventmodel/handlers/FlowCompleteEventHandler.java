package com.touchtype.keyboard.inputeventmodel.handlers;

import com.google.common.base.Strings;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.Composer;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowCompleteEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager;
import com.touchtype.report.LanguageModelMetrics;
import com.touchtype.report.TouchTypeStats;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class FlowCompleteEventHandler
  implements ConnectionInputEventHandler
{
  private CommonPredictionActions mCommon;
  private Composer mComposer;
  private DefaultPredictionProvider mDefaultPredictionProvider;
  private ConnectionInputEventHandler mKeyInputEventHandler;
  private final Punctuator mPunctuator;
  private TouchTypeStats mStats;
  private TouchHistoryManager mTouchHistoryManager;
  
  public FlowCompleteEventHandler(TouchHistoryManager paramTouchHistoryManager, DefaultPredictionProvider paramDefaultPredictionProvider, Composer paramComposer, CommonPredictionActions paramCommonPredictionActions, Punctuator paramPunctuator, TouchTypeStats paramTouchTypeStats)
  {
    this.mTouchHistoryManager = paramTouchHistoryManager;
    this.mDefaultPredictionProvider = paramDefaultPredictionProvider;
    this.mComposer = paramComposer;
    this.mCommon = paramCommonPredictionActions;
    this.mPunctuator = paramPunctuator;
    this.mStats = paramTouchTypeStats;
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    if (!(paramConnectionInputEvent instanceof FlowCompleteEvent)) {
      throw new UnhandledInputEventException("Unrecognised Completion InputEvent:" + paramConnectionInputEvent.toString());
    }
    FlowCompleteEvent localFlowCompleteEvent = (FlowCompleteEvent)paramConnectionInputEvent;
    this.mTouchHistoryManager.flushBufferedPredictionRequests();
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    Candidate localCandidate = this.mDefaultPredictionProvider.getDefaultPrediction(true, CandidatesUpdateRequestType.FLOW);
    String str1 = localCandidate.toString();
    if (str1.length() != 0)
    {
      CommonPredictionActions localCommonPredictionActions = this.mCommon;
      if ((!str1.equals(localTouchTypeExtractedText.getCurrentWord())) && (!localTouchTypeExtractedText.getCurrentWord().equals(""))) {}
      for (boolean bool = true;; bool = false)
      {
        localCommonPredictionActions.acceptCandidate(paramInputConnectionProxy, localFlowCompleteEvent, localCandidate, str1, localTouchTypeExtractedText, bool, true);
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
        return;
      }
    }
    this.mCommon.removeSelectedText(paramInputConnectionProxy, localTouchTypeExtractedText);
    localFlowCompleteEvent.setFlowFailed();
    this.mTouchHistoryManager.flowFailed();
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
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.FlowCompleteEventHandler
 * JD-Core Version:    0.7.0.1
 */