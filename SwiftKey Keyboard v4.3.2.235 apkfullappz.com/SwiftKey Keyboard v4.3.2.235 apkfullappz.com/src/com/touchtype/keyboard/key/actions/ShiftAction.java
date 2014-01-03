package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.ExtendedKeyEvent;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public final class ShiftAction
  extends GenericActionDecorator
{
  private final InputEventModel mInputEventModel;
  
  public ShiftAction(InputEventModel paramInputEventModel, EnumSet<ActionType> paramEnumSet, ActionParams paramActionParams, Action paramAction)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mInputEventModel = paramInputEventModel;
  }
  
  protected void act()
  {
    this.mInputEventModel.onSoftKey(new ExtendedKeyEvent(-1, 0.0F, 0.0F, 0));
    this.mInputEventModel.onSoftKey(new ExtendedKeyEvent(-1, 0.0F, 0.0F, 1));
  }
  
  protected void act(TouchEvent.Touch paramTouch)
  {
    this.mInputEventModel.onSoftKey(new ExtendedKeyEvent(-1, paramTouch.getX(), paramTouch.getY(), 0));
    this.mInputEventModel.onSoftKey(new ExtendedKeyEvent(-1, paramTouch.getX(), paramTouch.getY(), 1));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.ShiftAction
 * JD-Core Version:    0.7.0.1
 */