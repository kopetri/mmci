package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.touchtype.util.DeviceUtils;

public class KeyboardSizeLimiter
  implements SizeLimiter
{
  private static final String TAG = KeyboardSizeLimiter.class.getSimpleName();
  private final Context mContext;
  private final boolean mIsDocked;
  private final SizeLimiter.ResizeState mState;
  
  public KeyboardSizeLimiter(Context paramContext, KeyboardState paramKeyboardState, boolean paramBoolean) {}
  
  private KeyboardSizeLimiter(Context paramContext, SizeLimiter.ResizeState paramResizeState, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mState = paramResizeState;
    this.mIsDocked = paramBoolean;
  }
  
  public int[] getLimits()
  {
    DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
    int[] arrayOfInt = new int[3];
    Resources localResources = this.mContext.getResources();
    boolean bool = DeviceUtils.isDeviceInLandscape(this.mContext);
    int i;
    double d1;
    double d2;
    if (this.mState == SizeLimiter.ResizeState.FULL)
    {
      i = localDisplayMetrics.heightPixels;
      if (bool) {
        if (this.mIsDocked)
        {
          d1 = localResources.getFraction(2131361921, 1, 1);
          d2 = localResources.getFraction(2131361922, 1, 1);
        }
      }
    }
    for (;;)
    {
      arrayOfInt[0] = ((int)(d1 * i));
      arrayOfInt[1] = ((int)(d2 * i));
      arrayOfInt[2] = ((int)(0.5D * (arrayOfInt[0] + arrayOfInt[1])));
      return arrayOfInt;
      d1 = localResources.getFraction(2131361923, 1, 1);
      d2 = localResources.getFraction(2131361924, 1, 1);
      continue;
      d1 = localResources.getFraction(2131361925, 1, 1);
      d2 = localResources.getFraction(2131361926, 1, 1);
      continue;
      if (this.mState == SizeLimiter.ResizeState.SPLIT)
      {
        if (this.mIsDocked)
        {
          i = localDisplayMetrics.heightPixels;
          if (bool)
          {
            d1 = localResources.getFraction(2131361921, 1, 1);
            d2 = localResources.getFraction(2131361922, 1, 1);
          }
          else
          {
            d1 = localResources.getFraction(2131361925, 1, 1);
            d2 = localResources.getFraction(2131361926, 1, 1);
          }
        }
        else
        {
          i = localDisplayMetrics.widthPixels;
          if (bool)
          {
            d1 = localResources.getFraction(2131361927, 1, 1);
            d2 = localResources.getFraction(2131361928, 1, 1);
          }
          else
          {
            d1 = localResources.getFraction(2131361929, 1, 1);
            d2 = localResources.getFraction(2131361930, 1, 1);
          }
        }
      }
      else
      {
        i = localDisplayMetrics.widthPixels;
        if (bool)
        {
          d1 = localResources.getFraction(2131361931, 1, 1);
          d2 = localResources.getFraction(2131361932, 1, 1);
        }
        else
        {
          d1 = localResources.getFraction(2131361933, 1, 1);
          d2 = localResources.getFraction(2131361934, 1, 1);
        }
      }
    }
  }
  
  public int getSizeForScale(int paramInt)
  {
    int[] arrayOfInt = getLimits();
    return arrayOfInt[0] + paramInt * ((arrayOfInt[1] - arrayOfInt[0]) / 4);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.KeyboardSizeLimiter
 * JD-Core Version:    0.7.0.1
 */