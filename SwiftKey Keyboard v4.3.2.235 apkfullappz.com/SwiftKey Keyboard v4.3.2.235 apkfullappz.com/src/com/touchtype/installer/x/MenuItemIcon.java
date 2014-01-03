package com.touchtype.installer.x;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

public class MenuItemIcon
  extends MenuItem
{
  public MenuItemIcon(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
    setupView();
    setupInternalState();
  }
  
  protected void setupView()
  {
    LayoutInflater.from(getContext()).inflate(2130903086, this);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.MenuItemIcon
 * JD-Core Version:    0.7.0.1
 */