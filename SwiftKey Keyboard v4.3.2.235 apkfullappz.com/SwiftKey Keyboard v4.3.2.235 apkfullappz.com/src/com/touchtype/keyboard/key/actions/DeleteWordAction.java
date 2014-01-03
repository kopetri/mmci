package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import java.util.EnumSet;

public final class DeleteWordAction
  extends GenericActionDecorator
{
  private final InputEventModel mInputEventModel;
  
  public DeleteWordAction(InputEventModel paramInputEventModel, EnumSet<ActionType> paramEnumSet, ActionParams paramActionParams, Action paramAction)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mInputEventModel = paramInputEventModel;
  }
  
  protected void act()
  {
    this.mInputEventModel.handleDeleteLastWord(0, this.mTypes);
  }
  
  protected void onRepeat(int paramInt)
  {
    this.mInputEventModel.handleDeleteLastWord(paramInt, this.mTypes);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.DeleteWordAction
 * JD-Core Version:    0.7.0.1
 */