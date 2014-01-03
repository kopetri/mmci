package com.touchtype.keyboard.theme;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import com.touchtype.R.styleable;
import com.touchtype.keyboard.theme.util.DrawableLoader;
import com.touchtype.keyboard.theme.util.PrecompiledThemeRegister;
import com.touchtype.keyboard.theme.util.ThemeLoader.ThemeLoaderException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class PrecompiledThemeHeader
  extends ThemeHeader
{
  private final int mResId;
  private final Map<String, Integer> mStyleIds;
  
  public PrecompiledThemeHeader(Context paramContext, String paramString)
  {
    super(paramContext, paramString, getIcon(paramContext, paramString));
    this.mStyleIds = loadStyleIds(paramContext, paramString);
    this.mResId = PrecompiledThemeRegister.toThemeResId(paramContext, paramString);
  }
  
  private static Drawable getIcon(Context paramContext, String paramString)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(PrecompiledThemeRegister.toThemeResId(paramContext, paramString), R.styleable.ThemeProperties);
    Drawable localDrawable = localTypedArray.getDrawable(2);
    localTypedArray.recycle();
    return localDrawable;
  }
  
  private static Map<String, Integer> loadStyleIds(Context paramContext, String paramString)
  {
    HashMap localHashMap = new HashMap();
    TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(PrecompiledThemeRegister.toThemeResId(paramContext, paramString), R.styleable.ThemeProperties);
    int i = localTypedArray1.getResourceId(13, -1);
    localTypedArray1.recycle();
    TypedArray localTypedArray2 = paramContext.obtainStyledAttributes(i, R.styleable.keyStyles);
    Iterator localIterator = Arrays.asList(paramContext.getResources().getStringArray(2131623958)).iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      int j = localTypedArray2.getResourceId(toStyleResId(str), -1);
      if (j != -1) {
        localHashMap.put(str, Integer.valueOf(j));
      }
    }
    localTypedArray2.recycle();
    return localHashMap;
  }
  
  private static int toStyleResId(String paramString)
  {
    if (paramString.equals("baseStyle")) {}
    do
    {
      return 0;
      if (paramString.equals("functionStyle")) {
        return 1;
      }
      if (paramString.equals("specialStyle")) {
        return 2;
      }
      if (paramString.equals("candidateStyle")) {
        return 3;
      }
      if (paramString.equals("lssbStyle")) {
        return 4;
      }
      if (paramString.equals("topCandidateStyle")) {
        return 5;
      }
    } while (!paramString.equals("miniKeyboardKeyStyle"));
    return 6;
  }
  
  public Theme createTheme(Context paramContext)
    throws ThemeLoader.ThemeLoaderException
  {
    ThemeProperties localThemeProperties = new ThemeProperties(paramContext, this.mResId);
    HashMap localHashMap = new HashMap();
    Iterator localIterator = Arrays.asList(paramContext.getResources().getStringArray(2131623958)).iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      KeyStyle.StyleId localStyleId2 = KeyStyle.parseStyleId(paramContext.getResources(), str);
      localHashMap.put(localStyleId2, new KeyStyle(paramContext, localStyleId2, getKeyStyleResId(str)));
    }
    Map localMap = DrawableLoader.loadIconsFromResources(paramContext, this.mResId);
    for (KeyStyle.StyleId localStyleId1 : KeyStyle.StyleId.values()) {
      if (!localHashMap.containsKey(localStyleId1)) {
        throw new ThemeLoader.ThemeLoaderException("Missing KeyStyle: " + localStyleId1);
      }
    }
    return new Theme(localThemeProperties, localHashMap, localMap, paramContext);
  }
  
  public int getKeyStyleResId(String paramString)
  {
    return ((Integer)this.mStyleIds.get(paramString)).intValue();
  }
  
  public String getName()
  {
    TypedArray localTypedArray = this.mContext.obtainStyledAttributes(PrecompiledThemeRegister.toThemeResId(this.mContext, this.mId), R.styleable.ThemeProperties);
    String str = localTypedArray.getString(1);
    localTypedArray.recycle();
    return str;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.PrecompiledThemeHeader
 * JD-Core Version:    0.7.0.1
 */