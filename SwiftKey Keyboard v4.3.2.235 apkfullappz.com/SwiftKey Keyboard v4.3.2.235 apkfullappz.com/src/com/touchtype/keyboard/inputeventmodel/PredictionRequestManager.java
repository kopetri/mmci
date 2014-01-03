package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype_fluency.Sequence;

public abstract interface PredictionRequestManager
{
  public abstract void flushBufferedPredictionRequests();
  
  public abstract void requestBufferedPrediction(CandidatesUpdateRequestType paramCandidatesUpdateRequestType, TouchHistoryMarker paramTouchHistoryMarker, Sequence paramSequence, String paramString);
  
  public abstract void requestImmediatePrediction(CandidatesUpdateRequestType paramCandidatesUpdateRequestType, TouchHistoryMarker paramTouchHistoryMarker, Sequence paramSequence, String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.PredictionRequestManager
 * JD-Core Version:    0.7.0.1
 */