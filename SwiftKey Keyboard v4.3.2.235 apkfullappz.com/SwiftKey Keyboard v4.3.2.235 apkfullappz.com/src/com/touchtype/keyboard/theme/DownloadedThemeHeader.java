package com.touchtype.keyboard.theme;

import android.content.Context;
import com.touchtype.keyboard.theme.util.ThemeLoader;
import com.touchtype.keyboard.theme.util.ThemeLoader.ThemeLoaderException;

public final class DownloadedThemeHeader
  extends ThemeHeader
{
  protected final String mAssetDir;
  protected final String mDirectory;
  protected final String mName;
  
  public Theme createTheme(Context paramContext)
    throws ThemeLoader.ThemeLoaderException
  {
    return ThemeLoader.loadThemeHandler(paramContext, Theme.getInternalThemeFile(paramContext, this), Theme.getInternalAssetDir(paramContext, this));
  }
  
  public String getAssetDir()
  {
    return this.mAssetDir;
  }
  
  public String getDirectory()
  {
    return this.mDirectory;
  }
  
  public String getName()
  {
    return this.mName;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.DownloadedThemeHeader
 * JD-Core Version:    0.7.0.1
 */