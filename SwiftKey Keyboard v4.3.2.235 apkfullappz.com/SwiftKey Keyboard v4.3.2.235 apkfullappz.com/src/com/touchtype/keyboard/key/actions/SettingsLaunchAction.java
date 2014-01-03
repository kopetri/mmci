package com.touchtype.keyboard.key.actions;

import android.content.Context;
import com.touchtype.Launcher;
import java.util.EnumSet;

public final class SettingsLaunchAction
  extends GenericActionDecorator
{
  private final Context mContext;
  
  public SettingsLaunchAction(Context paramContext, EnumSet<ActionType> paramEnumSet, ActionParams paramActionParams, Action paramAction)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mContext = paramContext;
  }
  
  protected void act()
  {
    Launcher.launchSettings(this.mContext);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.SettingsLaunchAction
 * JD-Core Version:    0.7.0.1
 */