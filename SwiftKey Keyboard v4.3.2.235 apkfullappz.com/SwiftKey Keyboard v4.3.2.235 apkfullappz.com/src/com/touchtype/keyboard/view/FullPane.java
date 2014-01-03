package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.ViewGroup;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;

public abstract class FullPane
  extends SingularPane
{
  public FullPane(Context paramContext, KeyboardChoreographer paramKeyboardChoreographer, ViewGroup paramViewGroup, RibbonStateHandler paramRibbonStateHandler, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher)
  {
    super(paramContext, paramKeyboardChoreographer, paramViewGroup, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
  }
  
  protected abstract int defaultKeyboardYPosition();
  
  protected int[] getCorrectKeyboardSize()
  {
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = paneToKbWidth(this.mChoreographer.getWindowWidth());
    arrayOfInt[1] = this.mKeyboardViewContainer.getPreferredHeight();
    return arrayOfInt;
  }
  
  protected int getLayoutResId()
  {
    boolean bool = this.mPreferences.getUsePCLayoutStyle(this.mContext.getResources().getBoolean(2131492874));
    return this.mPreferences.getKeyboardLayout().getLayoutResId(bool);
  }
  
  protected abstract String getPositionPrefsKey();
  
  protected Rect getStoredKeyboardBounds()
  {
    int i = getStoredKeyboardHeight();
    int j = this.mPreferences.getKeyboardPosition(this.mContext, getPositionPrefsKey(), defaultKeyboardYPosition());
    return new Rect(paneToKbX(0), j, paneToKbX(0) + paneToKbWidth(this.mChoreographer.getWindowWidth()), j + i);
  }
  
  protected int getStoredKeyboardHeight()
  {
    int i = this.mPreferences.getKeyboardScale(this.mContext, getState().getName());
    return new KeyboardSizeLimiter(this.mContext, getState(), false).getSizeForScale(i);
  }
  
  protected void savePosition()
  {
    this.mPreferences.setKeyboardPosition(this.mContext, getPositionPrefsKey(), getTopOfKeyboard());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.FullPane
 * JD-Core Version:    0.7.0.1
 */