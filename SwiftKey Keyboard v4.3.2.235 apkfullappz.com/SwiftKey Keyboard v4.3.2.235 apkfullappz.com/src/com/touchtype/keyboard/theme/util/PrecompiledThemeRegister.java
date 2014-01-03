package com.touchtype.keyboard.theme.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import com.touchtype.util.LogUtil;
import java.util.LinkedHashMap;
import java.util.Map;

public final class PrecompiledThemeRegister
{
  private static PrecompiledThemeRegister mInstance;
  private final Map<String, Integer> mThemeRegister = new LinkedHashMap();
  
  private PrecompiledThemeRegister(Context paramContext)
  {
    String[] arrayOfString = paramContext.getResources().getStringArray(2131623938);
    TypedArray localTypedArray = paramContext.getResources().obtainTypedArray(2131623939);
    if (arrayOfString.length != localTypedArray.length()) {
      throw new RuntimeException("Unmatched theme ids - internal and resource id arrays must be of equal length");
    }
    int i = 0;
    if (i < arrayOfString.length)
    {
      int j = localTypedArray.getResourceId(i, -2147483648);
      if (j != -2147483648) {
        this.mThemeRegister.put(arrayOfString[i], Integer.valueOf(j));
      }
      for (;;)
      {
        i++;
        break;
        LogUtil.w("PrecompiledThemeRegister", "Unable to find resource id for theme: " + arrayOfString[i]);
      }
    }
    localTypedArray.recycle();
  }
  
  private static PrecompiledThemeRegister getInstance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new PrecompiledThemeRegister(paramContext);
    }
    return mInstance;
  }
  
  public static Map<String, Integer> getPrecompiledThemes(Context paramContext)
  {
    return getInstance(paramContext).mThemeRegister;
  }
  
  public static int toThemeResId(Context paramContext, String paramString)
  {
    return ((Integer)getInstance(paramContext).mThemeRegister.get(paramString)).intValue();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.PrecompiledThemeRegister
 * JD-Core Version:    0.7.0.1
 */