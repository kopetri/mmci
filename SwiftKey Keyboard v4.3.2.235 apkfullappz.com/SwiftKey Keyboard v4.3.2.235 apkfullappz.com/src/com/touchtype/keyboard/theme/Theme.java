package com.touchtype.keyboard.theme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.keyboard.theme.renderer.DefaultThemeRenderer;
import com.touchtype.keyboard.theme.renderer.RecolourableSeamlessThemeRenderer;
import com.touchtype.keyboard.theme.renderer.RecolourableThemeRenderer;
import com.touchtype.keyboard.theme.renderer.SeamlessThemeRenderer;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.theme.util.ThemeLoader.ThemeLoaderException;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.util.Map;

public class Theme
{
  public static final String TAG = Theme.class.getSimpleName();
  private final Context mContext;
  private final Map<KeyIcon, Drawable> mIcons;
  private final ThemeProperties mProperties;
  private final ThemeRenderer mRenderer;
  private final Map<KeyStyle.StyleId, KeyStyle> mStyles;
  
  public Theme(ThemeProperties paramThemeProperties, Map<KeyStyle.StyleId, KeyStyle> paramMap, Map<KeyIcon, Drawable> paramMap1, Context paramContext)
    throws ThemeLoader.ThemeLoaderException
  {
    this.mProperties = paramThemeProperties;
    this.mStyles = paramMap;
    this.mIcons = paramMap1;
    this.mContext = paramContext;
    String str1 = this.mProperties.getRendererId();
    if (str1 == null)
    {
      String str2 = "Theme: " + this.mProperties.getName() + " has null rendererId.";
      LogUtil.e(TAG, str2);
      throw new ThemeLoader.ThemeLoaderException(str2);
    }
    if (str1.equalsIgnoreCase("DEFAULT"))
    {
      this.mRenderer = new DefaultThemeRenderer(this.mStyles, this.mIcons, this.mContext);
      return;
    }
    if (str1.equalsIgnoreCase("RECOLOURABLE"))
    {
      this.mRenderer = new RecolourableThemeRenderer(this.mStyles, this.mIcons, this.mContext);
      return;
    }
    if (str1.equalsIgnoreCase("RECOLOURABLE_SEAMLESS"))
    {
      this.mRenderer = new RecolourableSeamlessThemeRenderer(this.mStyles, this.mIcons, this.mContext);
      return;
    }
    if (str1.equalsIgnoreCase("SEAMLESS"))
    {
      this.mRenderer = new SeamlessThemeRenderer(this.mStyles, this.mIcons, this.mContext);
      return;
    }
    LogUtil.e(TAG, "Theme: " + this.mProperties.getName() + " has invalid rendererId: " + str1);
    throw new ThemeLoader.ThemeLoaderException();
  }
  
  public static File getExternalAssetDir(Context paramContext, DownloadedThemeHeader paramDownloadedThemeHeader)
  {
    return new File(getExternalAssetsDir(paramContext), paramDownloadedThemeHeader.getAssetDir());
  }
  
  public static File getExternalAssetsDir(Context paramContext)
  {
    return new File(ThemeManager.getThemesDir(paramContext), paramContext.getString(2131297233));
  }
  
  public static File getExternalThemeFile(Context paramContext, DownloadedThemeHeader paramDownloadedThemeHeader)
  {
    return new File(ThemeManager.getThemesDir(paramContext), paramDownloadedThemeHeader.getDirectory());
  }
  
  public static File getInternalAssetDir(Context paramContext, DownloadedThemeHeader paramDownloadedThemeHeader)
  {
    return new File(getInternalAssetsDir(paramContext), paramDownloadedThemeHeader.getAssetDir());
  }
  
  private static File getInternalAssetsDir(Context paramContext)
  {
    return new File(ThemeManager.getInternalThemesDir(paramContext), paramContext.getString(2131297233));
  }
  
  public static File getInternalThemeFile(Context paramContext, DownloadedThemeHeader paramDownloadedThemeHeader)
  {
    return new File(ThemeManager.getInternalThemesDir(paramContext), paramDownloadedThemeHeader.getDirectory());
  }
  
  public int getFlowInkHeadColor()
  {
    return this.mProperties.getFlowInkHeadColor();
  }
  
  public int getFlowInkTailColor()
  {
    return this.mProperties.getFlowInkTailColor();
  }
  
  public String getId()
  {
    return this.mProperties.getId();
  }
  
  public ThemeProperties getProperties()
  {
    return this.mProperties;
  }
  
  public ThemeRenderer getRenderer()
  {
    return this.mRenderer;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.Theme
 * JD-Core Version:    0.7.0.1
 */