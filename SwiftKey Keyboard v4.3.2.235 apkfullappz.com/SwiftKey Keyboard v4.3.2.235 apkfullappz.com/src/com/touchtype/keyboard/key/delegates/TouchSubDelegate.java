package com.touchtype.keyboard.key.delegates;

import com.touchtype.keyboard.key.actions.ActionType;
import java.util.EnumSet;

public abstract class TouchSubDelegate
  implements KeyTouchDelegate
{
  public abstract boolean hasFired(EnumSet<ActionType> paramEnumSet);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.TouchSubDelegate
 * JD-Core Version:    0.7.0.1
 */