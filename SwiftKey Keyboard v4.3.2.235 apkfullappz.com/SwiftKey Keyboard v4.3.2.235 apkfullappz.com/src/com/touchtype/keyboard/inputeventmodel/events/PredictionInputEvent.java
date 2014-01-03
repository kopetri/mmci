package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype.keyboard.candidates.Candidate;

public final class PredictionInputEvent
  extends TextInputEvent
{
  private Candidate mCandidate;
  
  public PredictionInputEvent(Candidate paramCandidate)
  {
    super(paramCandidate.toString());
    this.mCandidate = paramCandidate;
    markAsInsertingCandidate(paramCandidate);
  }
  
  public Candidate getCandidate()
  {
    return this.mCandidate;
  }
  
  public String toString()
  {
    return "Prediction(" + getText() + ")";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.PredictionInputEvent
 * JD-Core Version:    0.7.0.1
 */