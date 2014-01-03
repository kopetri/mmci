package com.touchtype.keyboard.inputeventmodel.handlers;

import com.google.common.base.Strings;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy.SelectionDeletionType;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowFailedEvent;
import com.touchtype.keyboard.inputeventmodel.events.PredictionInputEvent;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype.report.LanguageModelMetrics;
import com.touchtype.report.StatsUtil;
import com.touchtype.report.TouchTypeStats;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.service.ImmutableExtractedText;
import java.util.Set;

public final class CommonPredictionActions
{
  private final KeyboardState mKeyboardState;
  private final Learner mLearner;
  private final TouchTypeStats mStats;
  private final TouchHistoryManager mTouchHistoryManager;
  
  public CommonPredictionActions(Learner paramLearner, TouchHistoryManager paramTouchHistoryManager, TouchTypeStats paramTouchTypeStats, KeyboardState paramKeyboardState)
  {
    this.mLearner = paramLearner;
    this.mTouchHistoryManager = paramTouchHistoryManager;
    this.mStats = paramTouchTypeStats;
    this.mKeyboardState = paramKeyboardState;
  }
  
  private void updateFlowStatistics(LanguageModelMetrics paramLanguageModelMetrics, Candidate paramCandidate, String paramString)
  {
    int i = paramCandidate.getNumberOfWords();
    if (i > 1) {
      paramLanguageModelMetrics.incrementFlowThroughSpace();
    }
    paramLanguageModelMetrics.incrementFlowedWordsBy(i);
    paramLanguageModelMetrics.incrementFlowedCharactersBy(paramString.length());
    this.mStats.incrementStatisticBy("stats_words_flowed", i);
    this.mStats.incrementStatisticBy("stats_distance_flowed", this.mStats.getCurrentWordDistanceFlowed());
    this.mStats.resetCurrentFlowingWordHistory();
    Prediction localPrediction = paramCandidate.getPrediction();
    if ((localPrediction != null) && (localPrediction.getTags().contains("prefix"))) {
      paramLanguageModelMetrics.incrementFlowEarlyLift();
    }
  }
  
  private void updateTypeStatistics(LanguageModelMetrics paramLanguageModelMetrics, Candidate paramCandidate, String paramString, CharSequence paramCharSequence)
  {
    if (paramCharSequence.equals(""))
    {
      i = 1;
      this.mStats.incrementStatistic("stats_words_predicted");
      paramLanguageModelMetrics.incrementNextWordPredictions();
      paramLanguageModelMetrics.incrementTotalWordsBy(i);
      paramLanguageModelMetrics.incrementTotalKeystrokesBy(1 + paramCharSequence.length());
      paramLanguageModelMetrics.incrementEnteredCharactersBy(1 + paramString.length());
      return;
    }
    int i = paramCandidate.getNumberOfWords();
    int j;
    if (i == 1)
    {
      j = StatsUtil.computeLevenshteinDistance(paramCharSequence, paramString);
      Prediction localPrediction = paramCandidate.getPrediction();
      if ((localPrediction != null) && (localPrediction.getTags().contains("prefix"))) {
        paramLanguageModelMetrics.incrementPredictedWords();
      }
    }
    for (;;)
    {
      this.mStats.incrementStatisticBy("stats_chars_corrected", j);
      this.mStats.incrementStatisticBy("stats_words_completed", i);
      break;
      if (!paramString.equals(paramCharSequence))
      {
        paramLanguageModelMetrics.incrementCorrectedWords();
        continue;
        j = StatsUtil.computeLevenshteinDistance(paramCharSequence, paramString.replaceAll(" ", ""));
        paramLanguageModelMetrics.incrementSpacesInferenceBy(i - 1);
      }
    }
  }
  
  public void acceptCandidate(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent, Candidate paramCandidate, String paramString, ImmutableExtractedText paramImmutableExtractedText, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramCandidate.isVerbatim()) {
      this.mLearner.temporarilyLearnWord(paramCandidate.toString());
    }
    this.mLearner.learnPrediction(paramCandidate, this.mTouchHistoryManager.getCurrentTouchHistoryMarker().getTouchHistory());
    removeSelectedText(paramInputConnectionProxy, paramImmutableExtractedText);
    String str = paramCandidate.source();
    LanguageModelMetrics localLanguageModelMetrics;
    if (!Strings.isNullOrEmpty(str))
    {
      int i = paramCandidate.version();
      localLanguageModelMetrics = this.mStats.getLanguageMetricsPerSourceAndVersion(str, i);
      localLanguageModelMetrics.incrementSelectedPredictions();
      if (paramBoolean2) {
        updateFlowStatistics(localLanguageModelMetrics, paramCandidate, paramString);
      }
    }
    else
    {
      paramConnectionInputEvent.markAsInsertingCandidate(paramCandidate);
      if ((!(paramConnectionInputEvent instanceof PredictionInputEvent)) && (!(paramConnectionInputEvent instanceof FlowFailedEvent))) {
        break label163;
      }
    }
    label163:
    for (boolean bool = true;; bool = false)
    {
      if (!paramBoolean1) {
        break label169;
      }
      paramInputConnectionProxy.commitCorrection(paramCandidate, bool, paramImmutableExtractedText);
      return;
      updateTypeStatistics(localLanguageModelMetrics, paramCandidate, paramString, paramImmutableExtractedText.getCurrentWord());
      break;
    }
    label169:
    paramInputConnectionProxy.setComposingTextWithCandidate(paramCandidate, bool, paramImmutableExtractedText);
    paramInputConnectionProxy.finishComposingText();
  }
  
  public void removeSelectedText(InputConnectionProxy paramInputConnectionProxy, ImmutableExtractedText paramImmutableExtractedText)
  {
    if (paramImmutableExtractedText.getSelectionStartInField() != paramImmutableExtractedText.getSelectionEndInField())
    {
      if (!this.mKeyboardState.shouldReplaceSelectionWithoutDeleting()) {
        paramInputConnectionProxy.deleteSelection(paramImmutableExtractedText, InputConnectionProxy.SelectionDeletionType.REPLACING_WITH_PREDICTION);
      }
      int i = paramImmutableExtractedText.getSelectionStartInField();
      int j = i - paramImmutableExtractedText.getCurrentWord().length();
      if (j != i) {
        paramInputConnectionProxy.setComposingRegion(j, i, paramImmutableExtractedText);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.CommonPredictionActions
 * JD-Core Version:    0.7.0.1
 */