package com.touchtype.keyboard.view.fx;

import com.touchtype.keyboard.view.touch.FlowEvent;

public final class FlowEventBroadcaster
{
  private static FlowEventBroadcaster mInstance = null;
  private FlowStrokeObserver mObserver = null;
  
  public static FlowEventBroadcaster get()
  {
    if (mInstance == null) {
      mInstance = new FlowEventBroadcaster();
    }
    return mInstance;
  }
  
  public void broadcastEvent(FlowEvent paramFlowEvent)
  {
    if (this.mObserver != null) {
      this.mObserver.onFlowEvent(paramFlowEvent);
    }
  }
  
  public void setObserver(FlowStrokeObserver paramFlowStrokeObserver)
  {
    this.mObserver = paramFlowStrokeObserver;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.fx.FlowEventBroadcaster
 * JD-Core Version:    0.7.0.1
 */