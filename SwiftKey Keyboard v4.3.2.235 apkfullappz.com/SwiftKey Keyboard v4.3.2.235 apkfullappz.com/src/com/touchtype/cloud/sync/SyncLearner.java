package com.touchtype.cloud.sync;

import com.touchtype.cloud.CloudService;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.Sequence;

public final class SyncLearner
  implements Learner
{
  private CloudService cloudService;
  
  public SyncLearner(CloudService paramCloudService)
  {
    this.cloudService = paramCloudService;
  }
  
  public boolean forgetWord(String paramString)
  {
    this.cloudService.addToBlackList(paramString);
    return true;
  }
  
  public boolean learnPrediction(Candidate paramCandidate, TouchHistoryProxy paramTouchHistoryProxy)
  {
    return false;
  }
  
  public boolean learnPredictionMapping(Candidate paramCandidate, ResultsFilter.PredictionSearchType paramPredictionSearchType)
  {
    return false;
  }
  
  public boolean learnPredictionMappings()
  {
    return false;
  }
  
  public boolean learnWordsInContext(Sequence paramSequence)
  {
    this.cloudService.addTextSequenceToSyncModel(paramSequence);
    return true;
  }
  
  public boolean temporarilyForgetWord(String paramString)
  {
    return false;
  }
  
  public boolean temporarilyLearnWord(String paramString)
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.sync.SyncLearner
 * JD-Core Version:    0.7.0.1
 */