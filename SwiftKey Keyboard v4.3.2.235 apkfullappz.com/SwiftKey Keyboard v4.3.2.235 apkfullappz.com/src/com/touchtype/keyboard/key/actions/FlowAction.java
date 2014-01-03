package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.view.fx.FlowEventBroadcaster;
import com.touchtype.keyboard.view.touch.FlowEvent;
import java.util.EnumSet;
import java.util.List;

public final class FlowAction
  extends ActionDecorator
{
  private final InputEventModel mInputEventModel;
  
  public FlowAction(InputEventModel paramInputEventModel, ActionParams paramActionParams, Action paramAction)
  {
    super(paramActionParams, paramAction);
    this.mInputEventModel = paramInputEventModel;
  }
  
  protected EnumSet<ActionType> getUsedActions()
  {
    return EnumSet.of(ActionType.FLOW);
  }
  
  protected void onFlow(List<FlowEvent> paramList)
  {
    this.mInputEventModel.onContinuousInputSamples(paramList);
  }
  
  protected void onFlowComplete(FlowEvent paramFlowEvent)
  {
    FlowEventBroadcaster.get().broadcastEvent(paramFlowEvent);
    this.mInputEventModel.onContinuousInputSample(paramFlowEvent);
    this.mInputEventModel.onFlowComplete();
  }
  
  protected void onFlowStarted()
  {
    this.mInputEventModel.onFlowBegun(true);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.FlowAction
 * JD-Core Version:    0.7.0.1
 */