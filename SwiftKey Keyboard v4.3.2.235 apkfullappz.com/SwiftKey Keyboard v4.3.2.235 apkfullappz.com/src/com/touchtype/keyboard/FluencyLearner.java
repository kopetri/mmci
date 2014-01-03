package com.touchtype.keyboard;

import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.concurrent.BackgroundExecutor;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.PredictorNotReadyException;

public class FluencyLearner
  implements Learner
{
  private static final String TAG = FluencyLearner.class.getSimpleName();
  private final BackgroundExecutor mExecutor;
  private final Predictor mPredictor;
  
  public FluencyLearner(Predictor paramPredictor, BackgroundExecutor paramBackgroundExecutor)
  {
    this.mPredictor = paramPredictor;
    this.mExecutor = paramBackgroundExecutor;
  }
  
  public boolean forgetWord(final String paramString)
  {
    this.mExecutor.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          FluencyLearner.this.mPredictor.removeTerm(paramString);
          return;
        }
        catch (PredictorNotReadyException localPredictorNotReadyException) {}
      }
    });
    return true;
  }
  
  public boolean learnPrediction(final Candidate paramCandidate, final TouchHistoryProxy paramTouchHistoryProxy)
  {
    if (paramCandidate.getPrediction() != null) {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          try
          {
            FluencyLearner.this.mPredictor.learnFrom(paramTouchHistoryProxy.getTouchHistory(), paramCandidate.getPrediction());
            return;
          }
          catch (PredictorNotReadyException localPredictorNotReadyException) {}
        }
      });
    }
    return true;
  }
  
  public boolean learnPredictionMapping(final Candidate paramCandidate, final ResultsFilter.PredictionSearchType paramPredictionSearchType)
  {
    this.mExecutor.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          FluencyLearner.this.mPredictor.learnMappingsFrom(paramCandidate.getPrediction(), paramPredictionSearchType);
          return;
        }
        catch (PredictorNotReadyException localPredictorNotReadyException) {}
      }
    });
    return true;
  }
  
  public boolean learnPredictionMappings()
  {
    this.mExecutor.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          FluencyLearner.this.mPredictor.learnMappings();
          return;
        }
        catch (PredictorNotReadyException localPredictorNotReadyException) {}
      }
    });
    return true;
  }
  
  public boolean learnWordsInContext(final Sequence paramSequence)
  {
    this.mExecutor.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          FluencyLearner.this.mPredictor.addToUserModel(paramSequence);
          return;
        }
        catch (PredictorNotReadyException localPredictorNotReadyException) {}
      }
    });
    return true;
  }
  
  public boolean temporarilyForgetWord(final String paramString)
  {
    this.mExecutor.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          FluencyLearner.this.mPredictor.removeFromTemporaryModel(paramString);
          return;
        }
        catch (PredictorNotReadyException localPredictorNotReadyException) {}
      }
    });
    return true;
  }
  
  public boolean temporarilyLearnWord(final String paramString)
  {
    this.mExecutor.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          FluencyLearner.this.mPredictor.addToTemporaryModel(paramString);
          return;
        }
        catch (PredictorNotReadyException localPredictorNotReadyException) {}
      }
    });
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.FluencyLearner
 * JD-Core Version:    0.7.0.1
 */