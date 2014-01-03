package com.touchtype.keyboard.key.actions;

import android.content.Context;
import android.content.res.Resources;
import com.touchtype.keyboard.ExtendedKeyEvent;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyState.OptionState;
import com.touchtype.keyboard.key.KeyState.StateType;
import com.touchtype.keyboard.key.KeyStateListener;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import java.util.EnumSet;
import junit.framework.Assert;

public final class OptionsKeyAction
  extends ActionDecorator
  implements KeyStateListener
{
  private Context mContext;
  private final InputEventModel mInputEventModel;
  private final KeyboardSwitcher mKeyboardSwitcher;
  private KeyState.OptionState mOptionState;
  private final int mSmileyLayoutId;
  
  public OptionsKeyAction(InputEventModel paramInputEventModel, KeyboardSwitcher paramKeyboardSwitcher, int paramInt, KeyState paramKeyState, ActionParams paramActionParams, Context paramContext, Action paramAction)
  {
    super(paramActionParams, paramAction);
    Assert.assertFalse(paramAction instanceof BloopAction);
    this.mInputEventModel = paramInputEventModel;
    this.mKeyboardSwitcher = paramKeyboardSwitcher;
    this.mSmileyLayoutId = paramInt;
    paramKeyState.addListener(KeyState.StateType.OPTIONS, this);
    this.mOptionState = paramKeyState.getOptionState();
    this.mContext = paramContext;
  }
  
  private void insertEnter()
  {
    this.mInputEventModel.onEnterKey();
  }
  
  public EnumSet<ActionType> getUsedActions()
  {
    return EnumSet.of(ActionType.CLICK, ActionType.DOWN, ActionType.LONGPRESS);
  }
  
  protected void onClick(TouchEvent.Touch paramTouch)
  {
    if (KeyState.OptionState.SMILEY == this.mOptionState)
    {
      this.mInputEventModel.onSoftKey(ExtendedKeyEvent.createKeyEventFromText(":-) ", paramTouch.getX(), paramTouch.getY()));
      return;
    }
    insertEnter();
  }
  
  protected void onDown(TouchEvent.Touch paramTouch)
  {
    new BloopAction(this.mContext, this).act();
  }
  
  public void onKeyStateChanged(KeyState paramKeyState)
  {
    this.mOptionState = paramKeyState.getOptionState();
  }
  
  protected void onLongPress()
  {
    int i = 1;
    boolean bool = this.mContext.getResources().getBoolean(2131492936);
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(this.mContext);
    if ((localTouchTypePreferences.getUsePCLayoutStyle(false)) && (localTouchTypePreferences.getKeyboardLayoutStyle(this.mContext) == i)) {}
    while (((this.mOptionState == KeyState.OptionState.SMILEY) || (this.mOptionState == KeyState.OptionState.DONE)) && (bool) && (i == 0))
    {
      new BloopAction(this.mContext, this).act();
      this.mKeyboardSwitcher.selectKeyboard(this.mSmileyLayoutId, false);
      return;
      i = 0;
    }
    new BloopAction(this.mContext, this).act();
    insertEnter();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.OptionsKeyAction
 * JD-Core Version:    0.7.0.1
 */