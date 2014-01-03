package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.events.ConnectionlessInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SingleFlowSampleEvent;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.report.TouchTypeStats;

public final class SingleFlowSampleHandler
  implements ConnectionlessInputEventHandler
{
  private TouchTypeStats mStats;
  private TouchHistoryManager mTouchHistoryManager;
  
  public SingleFlowSampleHandler(TouchHistoryManager paramTouchHistoryManager, TouchTypeStats paramTouchTypeStats)
  {
    this.mTouchHistoryManager = paramTouchHistoryManager;
    this.mStats = paramTouchTypeStats;
  }
  
  public void handleInput(ConnectionlessInputEvent paramConnectionlessInputEvent)
    throws UnhandledInputEventException
  {
    if (!(paramConnectionlessInputEvent instanceof SingleFlowSampleEvent)) {
      throw new UnhandledInputEventException("ContinuousInputSampleHandler is misconfigured, received: " + paramConnectionlessInputEvent.getClass().getName());
    }
    FlowEvent localFlowEvent = ((SingleFlowSampleEvent)paramConnectionlessInputEvent).getEvent();
    this.mStats.addToCurrentFlowingWordHistory(localFlowEvent.getRawPoint());
    this.mTouchHistoryManager.addContinuousTouchSample(localFlowEvent.toPoint());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.SingleFlowSampleHandler
 * JD-Core Version:    0.7.0.1
 */