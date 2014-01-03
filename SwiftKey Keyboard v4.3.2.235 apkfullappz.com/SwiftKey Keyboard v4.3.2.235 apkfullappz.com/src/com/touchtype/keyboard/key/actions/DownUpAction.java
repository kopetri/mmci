package com.touchtype.keyboard.key.actions;

import android.view.KeyEvent;
import com.touchtype.keyboard.ExtendedKeyEvent;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public final class DownUpAction
  extends ActionDecorator
{
  private final InputEventModel mInputEventModel;
  private final int mKeyCode;
  
  public DownUpAction(InputEventModel paramInputEventModel, int paramInt, Action paramAction)
  {
    super(ActionParams.EMPTY_PARAMS, paramAction);
    this.mInputEventModel = paramInputEventModel;
    this.mKeyCode = paramInt;
  }
  
  protected EnumSet<ActionType> getUsedActions()
  {
    return EnumSet.of(ActionType.DOWN, ActionType.UP, ActionType.CLICK, ActionType.FLOW);
  }
  
  protected void onDown(TouchEvent.Touch paramTouch)
  {
    this.mInputEventModel.onSoftKey(new ExtendedKeyEvent(this.mKeyCode, 0.0F, 0.0F, 0));
  }
  
  protected void onFlowStarted()
  {
    ExtendedKeyEvent localExtendedKeyEvent = new ExtendedKeyEvent(this.mKeyCode, 0.0F, 0.0F, 1);
    int i = 0x20 | localExtendedKeyEvent.getFlags();
    this.mInputEventModel.onSoftKey(KeyEvent.changeFlags(localExtendedKeyEvent, i));
  }
  
  protected void onUp(TouchEvent.Touch paramTouch)
  {
    this.mInputEventModel.onSoftKey(new ExtendedKeyEvent(this.mKeyCode, 0.0F, 0.0F, 1));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.DownUpAction
 * JD-Core Version:    0.7.0.1
 */