package com.touchtype.keyboard.theme.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public final class TextSizeLimiter
{
  private float mUpperTextSizeLimit;
  
  public TextSizeLimiter(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    this.mUpperTextSizeLimit = TypedValue.applyDimension(1, paramContext.getResources().getDimension(2131361810), localDisplayMetrics);
  }
  
  public float getUpperTextSizeLimit()
  {
    return this.mUpperTextSizeLimit;
  }
  
  public boolean isTextNeedsLimiting(float paramFloat)
  {
    return (this.mUpperTextSizeLimit > 0.0F) && (paramFloat > this.mUpperTextSizeLimit);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.TextSizeLimiter
 * JD-Core Version:    0.7.0.1
 */