package com.touchtype.keyboard.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.ViewGroup;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.preferences.TouchTypePreferences;

public final class FloatingFullPane
  extends FullPane
{
  public FloatingFullPane(Context paramContext, KeyboardChoreographer paramKeyboardChoreographer, ViewGroup paramViewGroup, RibbonStateHandler paramRibbonStateHandler, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher)
  {
    super(paramContext, paramKeyboardChoreographer, paramViewGroup, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
  }
  
  protected int defaultKeyboardYPosition()
  {
    return defaultFloatingY();
  }
  
  protected String getPositionPrefsKey()
  {
    return "pref_keyboard_position_vertical_floating";
  }
  
  protected KeyboardState getState()
  {
    return KeyboardState.FULL_FLOATING;
  }
  
  protected boolean isDocked()
  {
    return false;
  }
  
  protected void movePostResize()
  {
    movePaneTo(this.mPaneView, 0, kbToPaneY(getStoredKeyboardBounds().top), kbToPaneWidth(0), getPreferredPaneHeight());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.FloatingFullPane
 * JD-Core Version:    0.7.0.1
 */