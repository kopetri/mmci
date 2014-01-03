package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.LegacyTouchUtils;

public final class SingleFlowSampleEvent
  implements ConnectionlessInputEvent
{
  private final FlowEvent mEvent;
  private final LegacyTouchUtils mLTUs;
  
  public SingleFlowSampleEvent(FlowEvent paramFlowEvent, LegacyTouchUtils paramLegacyTouchUtils)
  {
    this.mEvent = paramFlowEvent;
    this.mLTUs = paramLegacyTouchUtils;
  }
  
  public boolean equals(Object paramObject)
  {
    return hashCode() == paramObject.hashCode();
  }
  
  public FlowEvent getEvent()
  {
    return this.mLTUs.transformApproxAspectRatio(this.mEvent);
  }
  
  public String toString()
  {
    return "ContinuousInputSampleEvent(" + this.mEvent.toPoint() + ")";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.SingleFlowSampleEvent
 * JD-Core Version:    0.7.0.1
 */