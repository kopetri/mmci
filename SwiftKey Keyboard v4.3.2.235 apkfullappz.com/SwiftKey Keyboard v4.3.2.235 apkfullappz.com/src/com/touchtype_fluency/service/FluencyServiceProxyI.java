package com.touchtype_fluency.service;

import com.touchtype_fluency.Predictions;
import com.touchtype_fluency.ResultsFilter;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.TouchHistory;

public abstract interface FluencyServiceProxyI
  extends FluencyService
{
  public abstract Predictions getPredictions(Sequence paramSequence, TouchHistory paramTouchHistory, ResultsFilter paramResultsFilter)
    throws PredictorNotReadyException;
  
  public abstract void runWhenConnected(Runnable paramRunnable);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.FluencyServiceProxyI
 * JD-Core Version:    0.7.0.1
 */