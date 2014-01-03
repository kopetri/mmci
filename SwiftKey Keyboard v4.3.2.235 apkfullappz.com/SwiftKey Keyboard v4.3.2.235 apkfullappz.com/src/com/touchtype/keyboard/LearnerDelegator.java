package com.touchtype.keyboard;

import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.Sequence;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class LearnerDelegator
  implements Learner
{
  private Set<Learner> mLearners = new HashSet();
  
  public void addLearner(Learner paramLearner)
  {
    this.mLearners.add(paramLearner);
  }
  
  public boolean forgetWord(String paramString)
  {
    boolean bool = false;
    Iterator localIterator = this.mLearners.iterator();
    while (localIterator.hasNext()) {
      bool |= ((Learner)localIterator.next()).forgetWord(paramString);
    }
    return bool;
  }
  
  public boolean learnPrediction(Candidate paramCandidate, TouchHistoryProxy paramTouchHistoryProxy)
  {
    boolean bool = false;
    Iterator localIterator = this.mLearners.iterator();
    while (localIterator.hasNext()) {
      bool |= ((Learner)localIterator.next()).learnPrediction(paramCandidate, paramTouchHistoryProxy);
    }
    return bool;
  }
  
  public boolean learnPredictionMapping(Candidate paramCandidate, ResultsFilter.PredictionSearchType paramPredictionSearchType)
  {
    boolean bool = false;
    Iterator localIterator = this.mLearners.iterator();
    while (localIterator.hasNext()) {
      bool |= ((Learner)localIterator.next()).learnPredictionMapping(paramCandidate, paramPredictionSearchType);
    }
    return bool;
  }
  
  public boolean learnPredictionMappings()
  {
    boolean bool = false;
    Iterator localIterator = this.mLearners.iterator();
    while (localIterator.hasNext()) {
      bool |= ((Learner)localIterator.next()).learnPredictionMappings();
    }
    return bool;
  }
  
  public boolean learnWordsInContext(Sequence paramSequence)
  {
    boolean bool = false;
    Iterator localIterator = this.mLearners.iterator();
    while (localIterator.hasNext()) {
      bool |= ((Learner)localIterator.next()).learnWordsInContext(paramSequence);
    }
    return bool;
  }
  
  public void removeLearner(Learner paramLearner)
  {
    this.mLearners.remove(paramLearner);
  }
  
  public boolean temporarilyForgetWord(String paramString)
  {
    boolean bool = false;
    Iterator localIterator = this.mLearners.iterator();
    while (localIterator.hasNext()) {
      bool |= ((Learner)localIterator.next()).temporarilyForgetWord(paramString);
    }
    return bool;
  }
  
  public boolean temporarilyLearnWord(String paramString)
  {
    boolean bool = false;
    Iterator localIterator = this.mLearners.iterator();
    while (localIterator.hasNext()) {
      bool |= ((Learner)localIterator.next()).temporarilyLearnWord(paramString);
    }
    return bool;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.LearnerDelegator
 * JD-Core Version:    0.7.0.1
 */