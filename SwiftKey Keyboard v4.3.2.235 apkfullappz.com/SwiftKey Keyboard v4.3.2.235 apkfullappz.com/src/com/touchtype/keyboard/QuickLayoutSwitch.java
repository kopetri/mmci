package com.touchtype.keyboard;

import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;

public final class QuickLayoutSwitch
{
  private final boolean mIsCurrent;
  private final int mLayoutIcon;
  private final int mLayoutName;
  private final Runnable mSwitchAction;
  
  public QuickLayoutSwitch(final LayoutData.LayoutMap paramLayoutMap, boolean paramBoolean, final KeyboardSwitcher paramKeyboardSwitcher)
  {
    this.mLayoutName = paramLayoutMap.getNameResourceId();
    this.mLayoutIcon = paramLayoutMap.getIconResourceId();
    this.mIsCurrent = paramBoolean;
    this.mSwitchAction = new Runnable()
    {
      public void run()
      {
        paramKeyboardSwitcher.selectKeyboard(paramLayoutMap);
      }
    };
  }
  
  public void apply()
  {
    this.mSwitchAction.run();
  }
  
  public int getLayoutIconResId()
  {
    return this.mLayoutIcon;
  }
  
  public int getLayoutNameResId()
  {
    return this.mLayoutName;
  }
  
  public boolean isCurrentLayout()
  {
    return this.mIsCurrent;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.QuickLayoutSwitch
 * JD-Core Version:    0.7.0.1
 */