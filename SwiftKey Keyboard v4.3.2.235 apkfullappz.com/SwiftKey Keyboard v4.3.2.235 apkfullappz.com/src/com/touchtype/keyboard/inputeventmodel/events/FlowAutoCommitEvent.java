package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;

public final class FlowAutoCommitEvent
  extends ConnectionInputEvent
{
  private final Candidate mCandidate;
  private final TouchHistoryProxy mTouchHistory;
  
  public FlowAutoCommitEvent(Candidate paramCandidate, TouchHistoryProxy paramTouchHistoryProxy)
  {
    this.mCandidate = paramCandidate;
    this.mTouchHistory = paramTouchHistoryProxy;
  }
  
  public Candidate getCandidate()
  {
    return this.mCandidate;
  }
  
  public CandidatesUpdateRequestType getEventType()
  {
    return CandidatesUpdateRequestType.FLOW;
  }
  
  public TouchHistoryProxy getTouchHistory()
  {
    return this.mTouchHistory;
  }
  
  public String toString()
  {
    return "FlowAutoCommit(Candidate: " + this.mCandidate + ")";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.FlowAutoCommitEvent
 * JD-Core Version:    0.7.0.1
 */