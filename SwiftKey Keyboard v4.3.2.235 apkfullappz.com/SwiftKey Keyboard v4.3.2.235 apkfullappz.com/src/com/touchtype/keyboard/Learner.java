package com.touchtype.keyboard;

import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.Sequence;

public abstract interface Learner
{
  public abstract boolean forgetWord(String paramString);
  
  public abstract boolean learnPrediction(Candidate paramCandidate, TouchHistoryProxy paramTouchHistoryProxy);
  
  public abstract boolean learnPredictionMapping(Candidate paramCandidate, ResultsFilter.PredictionSearchType paramPredictionSearchType);
  
  public abstract boolean learnPredictionMappings();
  
  public abstract boolean learnWordsInContext(Sequence paramSequence);
  
  public abstract boolean temporarilyForgetWord(String paramString);
  
  public abstract boolean temporarilyLearnWord(String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.Learner
 * JD-Core Version:    0.7.0.1
 */