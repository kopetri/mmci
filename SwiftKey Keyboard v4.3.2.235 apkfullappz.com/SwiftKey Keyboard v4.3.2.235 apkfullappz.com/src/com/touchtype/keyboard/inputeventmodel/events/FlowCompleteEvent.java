package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;

public final class FlowCompleteEvent
  extends ConnectionInputEvent
{
  private boolean mFlowFailed = false;
  
  public CandidatesUpdateRequestType getEventType()
  {
    if (this.mFlowFailed) {
      return CandidatesUpdateRequestType.FLOW_FAILED;
    }
    return CandidatesUpdateRequestType.FLOW_SUCCEEDED;
  }
  
  public void setFlowFailed()
  {
    this.mFlowFailed = true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.FlowCompleteEvent
 * JD-Core Version:    0.7.0.1
 */