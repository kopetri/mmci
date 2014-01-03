package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyState.InterimMenu;
import java.util.EnumSet;

public final class InterimMenuAction
  extends GenericActionDecorator
{
  private final KeyState.InterimMenu mMenu;
  private final KeyState mState;
  
  public InterimMenuAction(KeyState.InterimMenu paramInterimMenu, KeyState paramKeyState, EnumSet<ActionType> paramEnumSet, ActionParams paramActionParams, Action paramAction)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mState = paramKeyState;
    this.mMenu = paramInterimMenu;
  }
  
  protected void act()
  {
    this.mState.setInterimState(this.mMenu);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.InterimMenuAction
 * JD-Core Version:    0.7.0.1
 */