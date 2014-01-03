package com.touchtype_fluency.service.languagepacks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import com.touchtype.backup.SafeBackupRequest;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.MultipleDownloadListener;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.DownloadRequiredException;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackListener;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePacks;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.MaximumLanguagesException;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;
import junit.framework.Assert;

public class LanguagePackManager
{
  private static final String TAG = LanguagePackManager.class.getSimpleName();
  private final Context mContext;
  private final LanguagePacks mLanguagePacks;
  private final SwiftKeyPreferences mTouchTypePreferences;
  private int maxLanguagePacks;
  
  public LanguagePackManager(Context paramContext)
  {
    this.mContext = paramContext;
    this.mTouchTypePreferences = TouchTypePreferences.getInstance(this.mContext);
    this.mLanguagePacks = new LanguagePacks(paramContext);
    this.maxLanguagePacks = paramContext.getResources().getInteger(2131558409);
  }
  
  public static boolean liveLanguagesEnabled(SharedPreferences paramSharedPreferences)
  {
    return paramSharedPreferences.getBoolean("pref_screen_live_language_key", false);
  }
  
  private void setEnabledLanguageLocales(LanguagePack paramLanguagePack, boolean paramBoolean)
  {
    this.mTouchTypePreferences.putBoolean(paramLanguagePack.getID(), paramBoolean);
    String str = LanguagePackUtil.getLocaleString(paramLanguagePack);
    Set localSet = this.mTouchTypePreferences.getEnabledLocales();
    if (paramBoolean) {}
    for (boolean bool = localSet.add(str);; bool = localSet.remove(str))
    {
      if (bool) {
        this.mTouchTypePreferences.setEnabledLocales(localSet);
      }
      return;
    }
  }
  
  private void setSelectedLayout(LanguagePack paramLanguagePack, String paramString)
  {
    this.mTouchTypePreferences.putString(LanguagePackUtil.getLayoutPreferenceIdForLanguage(paramLanguagePack), paramString);
  }
  
  public void addListener(LanguagePackListener paramLanguagePackListener)
  {
    try
    {
      this.mLanguagePacks.addListener(paramLanguagePackListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public boolean canAddLanguagePack()
  {
    return getMaxLanguagePacks() > getEnabledLanguagePacks().size();
  }
  
  public void deleteLanguage(LanguagePack paramLanguagePack)
    throws IOException
  {
    setEnabledLanguageLocales(paramLanguagePack, false);
    this.mLanguagePacks.deleteLanguage(paramLanguagePack);
  }
  
  public void disableAllLanguagePacks(boolean paramBoolean)
    throws DownloadRequiredException, MaximumLanguagesException
  {
    Iterator localIterator = getEnabledLanguagePacks().iterator();
    while (localIterator.hasNext()) {
      enableLanguage((LanguagePack)localIterator.next(), false, paramBoolean);
    }
  }
  
  public void downloadConfiguration()
  {
    this.mLanguagePacks.downloadConfiguration();
  }
  
  public LanguagePack enableDefaultLanguage()
  {
    LanguagePack localLanguagePack;
    if ((this.mLanguagePacks.hasPreinstalledLanguagePacks()) && (getEnabledLanguagePacks().isEmpty()) && (!getLanguagePacks().isEmpty()) && (!this.mTouchTypePreferences.getBoolean("languages_preinstalled", false)))
    {
      Locale localLocale = Locale.getDefault();
      localLanguagePack = this.mLanguagePacks.findLanguage(localLocale.getLanguage(), localLocale.getCountry());
      if (localLanguagePack == null) {}
    }
    try
    {
      enableLanguage(localLanguagePack, true);
      return localLanguagePack;
    }
    catch (MaximumLanguagesException localMaximumLanguagesException)
    {
      Assert.fail();
      return null;
    }
    catch (DownloadRequiredException localDownloadRequiredException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "Extracting preinstalled language pack");
        this.mTouchTypePreferences.putBoolean("languages_preinstalled", true);
      }
    }
  }
  
  public void enableLanguage(LanguagePack paramLanguagePack, boolean paramBoolean)
    throws DownloadRequiredException, MaximumLanguagesException
  {
    enableLanguage(paramLanguagePack, paramBoolean, true);
  }
  
  public void enableLanguage(LanguagePack paramLanguagePack, boolean paramBoolean1, boolean paramBoolean2)
    throws DownloadRequiredException, MaximumLanguagesException
  {
    if ((paramBoolean1) && (!canAddLanguagePack()))
    {
      if (getMaxLanguagePacks() == 1) {
        disableAllLanguagePacks(false);
      }
    }
    else
    {
      this.mLanguagePacks.enableLanguage(paramLanguagePack, paramBoolean1, paramBoolean2);
      setEnabledLanguageLocales(paramLanguagePack, paramBoolean1);
      SafeBackupRequest.requestBackup(this.mContext);
      return;
    }
    throw new MaximumLanguagesException(getMaxLanguagePacks());
  }
  
  public void forciblyUpdateConfiguration()
  {
    this.mLanguagePacks.forciblyUpdateConfiguration();
  }
  
  public LayoutData.LayoutMap getCurrentLayout(LanguagePack paramLanguagePack)
  {
    String str1 = paramLanguagePack.getLanguage();
    String str2 = paramLanguagePack.getCountry();
    String str3 = this.mTouchTypePreferences.getString(LanguagePackUtil.getLayoutPreferenceIdForLanguage(paramLanguagePack), null);
    if (str3 == null)
    {
      LayoutData.LayoutMap localLayoutMap2 = LayoutData.getLayoutFromLanguage(str1, str2);
      setSelectedLayout(paramLanguagePack, localLayoutMap2.getLayoutName());
      return localLayoutMap2;
    }
    LayoutData.LayoutMap localLayoutMap1 = LayoutData.get(str3);
    if (localLayoutMap1 == null)
    {
      LogUtil.w(TAG, "Failed to get layout from preference with name " + str3 + ". Using QWERTY");
      localLayoutMap1 = LayoutData.LayoutMap.QWERTY;
    }
    return localLayoutMap1;
  }
  
  public Vector<String> getDownloadedLanguagePackIDs()
  {
    return this.mLanguagePacks.getDownloadedLanguagePackIDs();
  }
  
  public Vector<LanguagePack> getDownloadedLanguagePacks()
  {
    return this.mLanguagePacks.getDownloadedLanguagePacks();
  }
  
  public Vector<String> getEnabledLanguagePackIDs()
  {
    return this.mLanguagePacks.getEnabledLanguagePackIDs();
  }
  
  public Vector<LanguagePack> getEnabledLanguagePacks()
  {
    return this.mLanguagePacks.getEnabledLanguagePacks();
  }
  
  public List<String> getExtraPunctuationCharsFromEnabledLPs()
  {
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    Iterator localIterator = getEnabledLanguagePacks().iterator();
    while (localIterator.hasNext())
    {
      List localList = ((LanguagePack)localIterator.next()).getExtraPuncCharsIfExtendedLatinLayout();
      if (localList != null) {
        for (int i = 0; i < localList.size(); i++)
        {
          String str = (String)localList.get(i);
          if (!localLinkedHashSet.contains(str)) {
            localLinkedHashSet.add(str);
          }
        }
      }
    }
    return new ArrayList(localLinkedHashSet);
  }
  
  public Vector<String> getLanguagePackIDs()
  {
    return this.mLanguagePacks.getLanguagePackIDs();
  }
  
  public Vector<LanguagePack> getLanguagePacks()
  {
    return this.mLanguagePacks.getLanguagePacks();
  }
  
  public int getMaxLanguagePacks()
  {
    return this.maxLanguagePacks;
  }
  
  public List<LanguagePack> getSortedLanguagePacks()
  {
    return this.mLanguagePacks.getSortedLanguagePacks();
  }
  
  public boolean isDownloadConfigurationSuccess()
  {
    return this.mLanguagePacks.isDownloadConfigurationSuccess();
  }
  
  public boolean isLanguagePackEnabled(String paramString, boolean paramBoolean)
  {
    return this.mTouchTypePreferences.getBoolean(paramString, paramBoolean);
  }
  
  public boolean isReady()
  {
    return this.mLanguagePacks.isReady();
  }
  
  protected boolean liveLanguagesEnabled()
  {
    return liveLanguagesEnabled(this.mTouchTypePreferences);
  }
  
  public void notifyListeners()
  {
    try
    {
      this.mLanguagePacks.notifyListeners();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void onCreate()
  {
    this.mLanguagePacks.onCreate();
  }
  
  public void onDestroy()
  {
    this.mLanguagePacks.onDestroy();
  }
  
  public void removeListener(LanguagePackListener paramLanguagePackListener)
  {
    try
    {
      this.mLanguagePacks.removeListener(paramLanguagePackListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void scheduledDownloadConfiguration(MultipleDownloadListener paramMultipleDownloadListener)
  {
    this.mLanguagePacks.scheduledDownloadConfiguration(paramMultipleDownloadListener);
  }
  
  public void setCurrentLayout(LanguagePack paramLanguagePack, String paramString, boolean paramBoolean)
  {
    setSelectedLayout(paramLanguagePack, paramString);
    if (paramBoolean) {
      notifyListeners();
    }
  }
  
  public void startDeferringNotifications()
  {
    try
    {
      this.mLanguagePacks.startDeferringNotifications();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void stopDeferringNotifications()
  {
    try
    {
      this.mLanguagePacks.stopDeferringNotifications();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.LanguagePackManager
 * JD-Core Version:    0.7.0.1
 */