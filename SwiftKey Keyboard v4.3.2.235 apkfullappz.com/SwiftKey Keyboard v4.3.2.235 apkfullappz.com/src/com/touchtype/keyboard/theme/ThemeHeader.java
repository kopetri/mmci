package com.touchtype.keyboard.theme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.touchtype.keyboard.theme.util.ThemeLoader.ThemeLoaderException;

public abstract class ThemeHeader
{
  protected final Context mContext;
  protected final Drawable mIcon;
  protected final String mId;
  
  protected ThemeHeader(Context paramContext, String paramString, Drawable paramDrawable)
  {
    this.mContext = paramContext;
    this.mId = paramString;
    this.mIcon = paramDrawable;
  }
  
  public abstract Theme createTheme(Context paramContext)
    throws ThemeLoader.ThemeLoaderException;
  
  public Drawable getIcon()
  {
    return this.mIcon;
  }
  
  public String getId()
  {
    return this.mId;
  }
  
  public abstract String getName();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.ThemeHeader
 * JD-Core Version:    0.7.0.1
 */