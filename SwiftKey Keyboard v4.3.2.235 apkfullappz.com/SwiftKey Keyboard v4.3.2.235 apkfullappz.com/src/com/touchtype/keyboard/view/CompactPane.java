package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.ViewGroup;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.DeviceUtils;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;

public abstract class CompactPane
  extends SingularPane
{
  public CompactPane(Context paramContext, KeyboardChoreographer paramKeyboardChoreographer, ViewGroup paramViewGroup, RibbonStateHandler paramRibbonStateHandler, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher)
  {
    super(paramContext, paramKeyboardChoreographer, paramViewGroup, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
  }
  
  protected abstract int[] getAspectRatioResourceIds();
  
  protected int[] getCorrectKeyboardSize()
  {
    Resources localResources = this.mContext.getResources();
    boolean bool = DeviceUtils.isDeviceInLandscape(this.mContext);
    int i = this.mKeyboardViewContainer.getPreferredHeight();
    float f = this.mKeyboardViewContainer.getTotalRowWeight();
    int[] arrayOfInt = getAspectRatioResourceIds();
    if (bool) {}
    for (int j = arrayOfInt[1];; j = arrayOfInt[0]) {
      return new int[] { Math.min((int)(localResources.getFraction(j, 1, 1) * i * (4.0F / f)), paneToKbWidth(this.mChoreographer.getWindowWidth())), i };
    }
  }
  
  protected int getLayoutResId()
  {
    return this.mPreferences.getKeyboardLayout().getCompactLayoutResId();
  }
  
  protected Rect getStoredKeyboardBounds()
  {
    int[] arrayOfInt1 = getCorrectKeyboardSize();
    int[] arrayOfInt2 = getStoredKeyboardPos();
    return new Rect(arrayOfInt2[0], arrayOfInt2[1], arrayOfInt2[0] + arrayOfInt1[0], arrayOfInt2[1] + arrayOfInt1[1]);
  }
  
  protected abstract int[] getStoredKeyboardPos();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.CompactPane
 * JD-Core Version:    0.7.0.1
 */