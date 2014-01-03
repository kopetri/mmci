package com.touchtype.keyboard.view;

import android.content.Context;
import android.view.ViewGroup;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.preferences.TouchTypePreferences;

public final class FloatingCompactPane
  extends CompactPane
{
  public FloatingCompactPane(Context paramContext, KeyboardChoreographer paramKeyboardChoreographer, ViewGroup paramViewGroup, RibbonStateHandler paramRibbonStateHandler, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher)
  {
    super(paramContext, paramKeyboardChoreographer, paramViewGroup, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
  }
  
  private int getDefaultCompactWidth()
  {
    int i = this.mPreferences.getKeyboardScale(this.mContext, getState().getName());
    return new KeyboardSizeLimiter(this.mContext, getState(), false).getSizeForScale(i);
  }
  
  public void exitResizeMode()
  {
    this.mPreferences.setKeyboardPosition(this.mContext, "pref_keyboard_position_horizontal_compact", this.mPaneView.getLeft());
    super.exitResizeMode();
  }
  
  protected int[] getAspectRatioResourceIds()
  {
    return new int[] { 2131361917, 2131361918 };
  }
  
  protected KeyboardState getState()
  {
    return KeyboardState.COMPACT_FLOATING;
  }
  
  protected int[] getStoredKeyboardPos()
  {
    return new int[] { this.mPreferences.getKeyboardPosition(this.mContext, "pref_keyboard_position_horizontal_compact", this.mChoreographer.getWindowWidth() - getDefaultCompactWidth()), this.mPreferences.getKeyboardPosition(this.mContext, "pref_keyboard_position_vertical_floating", defaultFloatingY()) };
  }
  
  protected boolean isDocked()
  {
    return false;
  }
  
  protected void movePostResize()
  {
    int[] arrayOfInt1 = getCorrectPaneSize();
    int[] arrayOfInt2 = getStoredKeyboardPos();
    movePaneTo(this.mPaneView, kbToPaneX(arrayOfInt2[0]), kbToPaneY(arrayOfInt2[1]), arrayOfInt1[0], arrayOfInt1[1]);
  }
  
  protected void savePosition()
  {
    this.mPreferences.setKeyboardPosition(this.mContext, "pref_keyboard_position_vertical_floating", getTopOfKeyboard());
    this.mPreferences.setKeyboardPosition(this.mContext, "pref_keyboard_position_horizontal_compact", getLeftOfKeyboard());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.FloatingCompactPane
 * JD-Core Version:    0.7.0.1
 */