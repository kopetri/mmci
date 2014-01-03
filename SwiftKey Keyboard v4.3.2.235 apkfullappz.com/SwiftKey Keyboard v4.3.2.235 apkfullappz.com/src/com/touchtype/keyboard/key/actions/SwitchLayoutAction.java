package com.touchtype.keyboard.key.actions;

import android.content.Context;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.preferences.TouchTypePreferences;
import java.util.EnumSet;

public class SwitchLayoutAction
  extends GenericActionDecorator
{
  private static final String TAG = SwitchLayoutAction.class.getSimpleName();
  private final Context mContext;
  private final int mLayoutId;
  private final TouchTypePreferences mPreferences;
  private final KeyboardSwitcher mSwitcher;
  
  public SwitchLayoutAction(Context paramContext, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher, int paramInt, EnumSet<ActionType> paramEnumSet, ActionParams paramActionParams, Action paramAction)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mContext = paramContext;
    this.mPreferences = paramTouchTypePreferences;
    this.mSwitcher = paramKeyboardSwitcher;
    this.mLayoutId = paramInt;
  }
  
  protected void act()
  {
    this.mSwitcher.selectKeyboard(this.mLayoutId, false);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.SwitchLayoutAction
 * JD-Core Version:    0.7.0.1
 */