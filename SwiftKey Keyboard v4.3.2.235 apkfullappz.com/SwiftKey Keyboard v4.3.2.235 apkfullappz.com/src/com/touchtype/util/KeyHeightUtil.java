package com.touchtype.util;

import android.content.Context;
import android.content.res.Resources;
import com.touchtype.keyboard.view.KeyboardChoreographer;
import com.touchtype.preferences.TouchTypePreferences;

public class KeyHeightUtil
{
  private static final String TAG = KeyHeightUtil.class.getSimpleName();
  
  public static int getCurrentKeyHeight(Context paramContext)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    int i = localTouchTypePreferences.getKeyboardLayoutStyle(paramContext);
    boolean bool = localTouchTypePreferences.getKeyboardDockedState(paramContext);
    Resources localResources = paramContext.getResources();
    float f1 = localResources.getFraction(2131361911, 1, 1);
    float f2 = localResources.getFraction(2131361912, 1, 1);
    int j;
    switch (i)
    {
    default: 
      if (bool) {
        j = 2131361804;
      }
      break;
    }
    for (;;)
    {
      int k = localResources.getDimensionPixelSize(j);
      return Math.round((f1 + localTouchTypePreferences.getKeyboardScale(paramContext, KeyboardChoreographer.getCurrentStateName(paramContext)) / 4.0F * (f2 - f1)) * k);
      if (bool)
      {
        j = 2131361805;
      }
      else
      {
        f1 = localResources.getFraction(2131361913, 1, 1);
        f2 = localResources.getFraction(2131361914, 1, 1);
        j = 2131361808;
        continue;
        if (bool)
        {
          j = 2131361806;
        }
        else
        {
          f1 = localResources.getFraction(2131361913, 1, 1);
          f2 = localResources.getFraction(2131361914, 1, 1);
          j = 2131361807;
          continue;
          j = 2131361809;
        }
      }
    }
  }
  
  public static float getRibbonRelativeHeight(Context paramContext)
  {
    String str = paramContext.getResources().getString(2131296315);
    try
    {
      float f = Float.valueOf(str).floatValue();
      return f;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      LogUtil.w(TAG, String.format("Unable to parse candidate_key_relative_scale value of %s, using default value", new Object[] { str }));
    }
    return 0.8F;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.KeyHeightUtil
 * JD-Core Version:    0.7.0.1
 */