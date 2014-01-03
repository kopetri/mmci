package com.touchtype.keyboard.view;

import android.content.Context;
import android.graphics.Typeface;
import java.util.concurrent.ConcurrentHashMap;

public final class AppTypeFaceCache
{
  private static final String TAG = AppTypeFaceCache.class.getSimpleName();
  private static ConcurrentHashMap<CharSequence, Typeface> typeFaceMap = new ConcurrentHashMap(7);
  
  public static Typeface getFont(Context paramContext, CharSequence paramCharSequence, Typeface paramTypeface)
  {
    if (paramCharSequence == null) {
      return paramTypeface;
    }
    Typeface localTypeface = (Typeface)typeFaceMap.get(paramCharSequence);
    if (localTypeface == null)
    {
      localTypeface = Typeface.createFromAsset(paramContext.getAssets(), paramCharSequence.toString());
      typeFaceMap.put(paramCharSequence, localTypeface);
    }
    return localTypeface;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.AppTypeFaceCache
 * JD-Core Version:    0.7.0.1
 */