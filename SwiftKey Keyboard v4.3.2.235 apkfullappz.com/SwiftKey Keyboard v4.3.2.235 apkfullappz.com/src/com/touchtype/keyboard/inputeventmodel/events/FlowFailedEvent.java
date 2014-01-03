package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;

public final class FlowFailedEvent
  extends ConnectionInputEvent
{
  private final Candidate mCandidate;
  
  public FlowFailedEvent(Candidate paramCandidate)
  {
    this.mCandidate = paramCandidate;
  }
  
  public Candidate getCandidate()
  {
    return this.mCandidate;
  }
  
  public CandidatesUpdateRequestType getEventType()
  {
    return CandidatesUpdateRequestType.FLOW_FAILED;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.FlowFailedEvent
 * JD-Core Version:    0.7.0.1
 */