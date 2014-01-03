package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import java.util.EnumSet;
import java.util.List;

public final class CycleAction
  extends GenericActionDecorator
{
  private final List<String> mCycle;
  private final InputEventModel mInputEventModel;
  
  public CycleAction(InputEventModel paramInputEventModel, List<String> paramList, EnumSet<ActionType> paramEnumSet, ActionParams paramActionParams, Action paramAction)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mCycle = paramList;
    this.mInputEventModel = paramInputEventModel;
  }
  
  protected void act()
  {
    this.mInputEventModel.onCycle(this.mCycle);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.CycleAction
 * JD-Core Version:    0.7.0.1
 */