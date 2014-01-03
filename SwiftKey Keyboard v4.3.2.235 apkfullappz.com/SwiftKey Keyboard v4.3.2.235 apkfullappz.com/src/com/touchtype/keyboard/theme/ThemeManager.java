package com.touchtype.keyboard.theme;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.widget.Toast;
import com.touchtype.keyboard.theme.util.PrecompiledThemeRegister;
import com.touchtype.keyboard.theme.util.ThemeLoader.ThemeLoaderException;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;
import com.touchtype.util.WeakHashSet;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public final class ThemeManager
{
  private static ThemeManager instance;
  private Map<String, ThemeHeader> mAvailableThemes = null;
  private Context mContext;
  private Theme mCurrentTheme;
  private final Theme mDefaultTheme;
  private final ThemeHeader mDefaultThemeHeader;
  private Set<OnThemeChangedListener> mListeners = new WeakHashSet();
  private final Map<String, PrecompiledThemeHeader> mPrecompiledThemes;
  
  private ThemeManager(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mPrecompiledThemes = getPrecompiledThemes(this.mContext);
    try
    {
      PrecompiledThemeHeader localPrecompiledThemeHeader = getDefaultThemeHeader();
      Theme localTheme = localPrecompiledThemeHeader.createTheme(this.mContext);
      this.mDefaultThemeHeader = localPrecompiledThemeHeader;
      this.mDefaultTheme = localTheme;
      return;
    }
    catch (ThemeLoader.ThemeLoaderException localThemeLoaderException)
    {
      throw new RuntimeException(localThemeLoaderException);
    }
  }
  
  private Theme fetchThemeHandler(ThemeHeader paramThemeHeader)
  {
    try
    {
      Theme localTheme3 = paramThemeHeader.createTheme(this.mContext);
      return localTheme3;
    }
    catch (ThemeLoader.ThemeLoaderException localThemeLoaderException)
    {
      String str3 = this.mContext.getString(2131296820);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramThemeHeader.getName();
      arrayOfObject2[1] = this.mDefaultThemeHeader.getName();
      String str4 = String.format(str3, arrayOfObject2);
      LogUtil.e("ThemeManager", str4);
      Toast.makeText(this.mContext, str4, 0).show();
      Theme localTheme2 = this.mDefaultTheme;
      setTheme(this.mDefaultTheme.getId());
      return localTheme2;
    }
    catch (NullPointerException localNullPointerException)
    {
      String str1 = this.mContext.getString(2131296821);
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mDefaultThemeHeader.getName();
      String str2 = String.format(str1, arrayOfObject1);
      LogUtil.e("ThemeManager", str2);
      Toast.makeText(this.mContext, str2, 0).show();
      Theme localTheme1 = this.mDefaultTheme;
      setTheme(this.mDefaultTheme.getId());
      return localTheme1;
    }
  }
  
  private PrecompiledThemeHeader getDefaultThemeHeader()
  {
    return (PrecompiledThemeHeader)this.mPrecompiledThemes.get(this.mContext.getResources().getString(2131296791));
  }
  
  public static ThemeManager getInstance(Context paramContext)
  {
    try
    {
      if (instance == null) {
        instance = new ThemeManager(paramContext);
      }
      ThemeManager localThemeManager = instance;
      return localThemeManager;
    }
    finally {}
  }
  
  public static File getInternalThemesDir(Context paramContext)
  {
    File localFile = new File(paramContext.getFilesDir(), "themes");
    localFile.mkdirs();
    return localFile;
  }
  
  private static Map<String, PrecompiledThemeHeader> getPrecompiledThemes(Context paramContext)
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    Iterator localIterator = PrecompiledThemeRegister.getPrecompiledThemes(paramContext).keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localLinkedHashMap.put(str, new PrecompiledThemeHeader(paramContext, str));
    }
    return localLinkedHashMap;
  }
  
  private ThemeHeader getThemeHeader(String paramString)
  {
    return (ThemeHeader)getAvailableThemes().get(paramString);
  }
  
  public static File getThemesDir(Context paramContext)
  {
    File localFile = Environment.getExternalStorageDirectory();
    String str = paramContext.getString(2131297234);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramContext.getPackageName();
    return new File(localFile, String.format(str, arrayOfObject));
  }
  
  private boolean loadThemeToInternal(DownloadedThemeHeader paramDownloadedThemeHeader)
  {
    try
    {
      FileUtils.cleanDirectory(getInternalThemesDir(this.mContext));
      FileUtils.copyFile(Theme.getExternalThemeFile(this.mContext, paramDownloadedThemeHeader), Theme.getInternalThemeFile(this.mContext, paramDownloadedThemeHeader));
      FileUtils.copyDirectory(Theme.getExternalAssetDir(this.mContext, paramDownloadedThemeHeader), Theme.getInternalAssetDir(this.mContext, paramDownloadedThemeHeader));
      return true;
    }
    catch (IOException localIOException)
    {
      LogUtil.e("ThemeManager", localIOException.getMessage(), localIOException);
    }
    return false;
  }
  
  private static Map<String, ThemeHeader> mergeMaps(Map<String, ThemeHeader> paramMap, Map<String, PrecompiledThemeHeader> paramMap1)
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    if (paramMap != null) {
      localLinkedHashMap.putAll(paramMap);
    }
    localLinkedHashMap.putAll(paramMap1);
    return localLinkedHashMap;
  }
  
  private void notifyOnThemeChangedListeners()
  {
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnThemeChangedListener)localIterator.next()).onThemeChanged();
    }
  }
  
  private String updateThemeId()
  {
    return TouchTypePreferences.getInstance(this.mContext).getKeyboardTheme();
  }
  
  public void addListener(OnThemeChangedListener paramOnThemeChangedListener)
  {
    this.mListeners.add(paramOnThemeChangedListener);
  }
  
  public Map<String, ThemeHeader> getAvailableThemes()
  {
    this.mAvailableThemes = mergeMaps(null, this.mPrecompiledThemes);
    return this.mAvailableThemes;
  }
  
  public Theme getThemeHandler()
  {
    if (this.mCurrentTheme == null) {
      this.mCurrentTheme = fetchThemeHandler(getThemeHeader(updateThemeId()));
    }
    return this.mCurrentTheme;
  }
  
  public String getThemeId()
  {
    return getThemeHandler().getId();
  }
  
  public void removeListener(OnThemeChangedListener paramOnThemeChangedListener)
  {
    this.mListeners.remove(paramOnThemeChangedListener);
  }
  
  public void setTheme(String paramString)
  {
    if ((this.mCurrentTheme == null) || (!this.mCurrentTheme.getId().equals(paramString)))
    {
      ThemeHeader localThemeHeader = getThemeHeader(paramString);
      if ((localThemeHeader instanceof DownloadedThemeHeader)) {
        loadThemeToInternal((DownloadedThemeHeader)localThemeHeader);
      }
      this.mCurrentTheme = fetchThemeHandler(localThemeHeader);
    }
    TouchTypePreferences.getInstance(this.mContext).setKeyboardTheme(this.mCurrentTheme.getId());
    notifyOnThemeChangedListeners();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.ThemeManager
 * JD-Core Version:    0.7.0.1
 */