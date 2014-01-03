package com.touchtype.keyboard.theme.util;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.util.TypedValue;
import com.touchtype.util.LogUtil;

public final class ColorFilterUtil
{
  private static final String TAG = ColorFilterUtil.class.getSimpleName();
  
  public static ColorFilter parseCMCF(String paramString)
  {
    if (paramString == null) {}
    String[] arrayOfString;
    do
    {
      return null;
      arrayOfString = paramString.split(",");
    } while (arrayOfString.length != 20);
    try
    {
      float[] arrayOfFloat = new float[20];
      for (int i = 0; i < arrayOfString.length; i++) {
        arrayOfFloat[i] = Float.parseFloat(arrayOfString[i]);
      }
      ColorMatrixColorFilter localColorMatrixColorFilter = new ColorMatrixColorFilter(arrayOfFloat);
      return localColorMatrixColorFilter;
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return null;
  }
  
  public static ColorFilter parseColorFilter(String paramString)
  {
    ColorFilter localColorFilter;
    if (paramString == null) {
      localColorFilter = null;
    }
    do
    {
      return localColorFilter;
      try
      {
        PorterDuffColorFilter localPorterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor(paramString), PorterDuff.Mode.SRC_IN);
        return localPorterDuffColorFilter;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        localColorFilter = parseCMCF(paramString);
      }
    } while (localColorFilter != null);
    LogUtil.w(TAG, "Unable to load colorFilter");
    return localColorFilter;
  }
  
  public static ColorFilterContainer parseColorFilterContainer(TypedArray paramTypedArray, int paramInt)
  {
    TypedValue localTypedValue = paramTypedArray.peekValue(paramInt);
    if (localTypedValue == null) {
      return ColorFilterContainer.NULL_FILTER_CONTAINER;
    }
    if (localTypedValue.resourceId != 0)
    {
      switch (localTypedValue.type)
      {
      default: 
        return ColorFilterContainer.StateListColorFilter.createStateListColorFilter(paramTypedArray.getResources(), localTypedValue.resourceId);
      }
      return wrapColorFilter(parseColorFilter(paramTypedArray.getResources().getString(localTypedValue.resourceId)));
    }
    if (localTypedValue.type == 3) {
      return wrapColorFilter(parseColorFilter(localTypedValue.string.toString()));
    }
    LogUtil.w(TAG, "Failed to parse ColorFilter. No filter will be used");
    return ColorFilterContainer.NULL_FILTER_CONTAINER;
  }
  
  public static ColorFilterContainer parseColorFilterContainer(String paramString)
  {
    ColorFilter localColorFilter = parseColorFilter(paramString);
    if (localColorFilter == null) {
      return ColorFilterContainer.NULL_FILTER_CONTAINER;
    }
    return wrapColorFilter(localColorFilter);
  }
  
  private static ColorFilterContainer wrapColorFilter(ColorFilter paramColorFilter)
  {
    if (paramColorFilter == null) {
      return ColorFilterContainer.NULL_FILTER_CONTAINER;
    }
    return new ColorFilterContainer.BasicColorFilterContainer(paramColorFilter);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.ColorFilterUtil
 * JD-Core Version:    0.7.0.1
 */