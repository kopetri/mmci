package com.touchtype.keyboard.theme.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public final class DensityIndependentBitmapDrawableFactory
{
  public static BitmapDrawable create()
  {
    return new BitmapDrawable();
  }
  
  public static BitmapDrawable create(Bitmap paramBitmap)
  {
    return new BitmapDrawable(paramBitmap);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.DensityIndependentBitmapDrawableFactory
 * JD-Core Version:    0.7.0.1
 */