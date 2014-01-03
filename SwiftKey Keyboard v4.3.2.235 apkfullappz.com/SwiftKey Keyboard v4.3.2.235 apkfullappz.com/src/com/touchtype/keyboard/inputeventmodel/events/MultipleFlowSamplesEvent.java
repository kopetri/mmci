package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.LegacyTouchUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MultipleFlowSamplesEvent
  implements ConnectionlessInputEvent
{
  private final List<FlowEvent> mEvents;
  private final LegacyTouchUtils mLTUs;
  
  public MultipleFlowSamplesEvent(List<FlowEvent> paramList, LegacyTouchUtils paramLegacyTouchUtils)
  {
    this.mEvents = paramList;
    this.mLTUs = paramLegacyTouchUtils;
  }
  
  public boolean equals(Object paramObject)
  {
    return hashCode() == paramObject.hashCode();
  }
  
  public List<FlowEvent> getEvents()
  {
    ArrayList localArrayList = new ArrayList(this.mEvents.size());
    Iterator localIterator = this.mEvents.iterator();
    while (localIterator.hasNext())
    {
      FlowEvent localFlowEvent = (FlowEvent)localIterator.next();
      localArrayList.add(this.mLTUs.transformApproxAspectRatio(localFlowEvent));
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return "MultipleFlowSamplesEvent " + this.mEvents.size();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.MultipleFlowSamplesEvent
 * JD-Core Version:    0.7.0.1
 */