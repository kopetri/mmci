package com.touchtype_fluency.service.languagepacks.layouts;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class LayoutManagerImpl
  implements LanguagePackListener, LayoutManager
{
  private static final String TAG = "LayoutManager";
  private final String mEnabledLanguagesSetKey;
  private final LanguagePackManager mLanguagePackManager;
  Map<LayoutData.LayoutMap, Set<LanguagePack>> mLayoutMap = null;
  private Set<LayoutChangeListener> mListeners = new HashSet();
  private SharedPreferences mSharedPreferences;
  
  public LayoutManagerImpl(LanguagePackManager paramLanguagePackManager, Context paramContext)
  {
    this.mLanguagePackManager = paramLanguagePackManager;
    this.mLanguagePackManager.addListener(this);
    this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.mEnabledLanguagesSetKey = paramContext.getString(2131297209);
  }
  
  private Set<String> getStringSet()
  {
    return new HashSet(Arrays.asList(this.mSharedPreferences.getString(this.mEnabledLanguagesSetKey, "").split("\n")));
  }
  
  private void notifyLayoutChangeListeners()
  {
    Map localMap = getLayoutMap();
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext()) {
      ((LayoutChangeListener)localIterator.next()).onLayoutChanged(localMap);
    }
  }
  
  private void putStringSet(Set<String> paramSet)
  {
    SharedPreferences.Editor localEditor = this.mSharedPreferences.edit();
    String str1 = new String();
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext()) {
      str1 = str1 + (String)localIterator.next() + "\n";
    }
    String str2 = str1.trim();
    try
    {
      localEditor.putString(this.mEnabledLanguagesSetKey, str2);
      localEditor.commit();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void addListener(LayoutChangeListener paramLayoutChangeListener)
  {
    this.mListeners.add(paramLayoutChangeListener);
  }
  
  public Map<LayoutData.LayoutMap, Set<LanguagePack>> getLayoutMap()
  {
    this.mLayoutMap = new HashMap();
    Iterator localIterator = this.mLanguagePackManager.getEnabledLanguagePacks().iterator();
    while (localIterator.hasNext())
    {
      LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
      LayoutData.LayoutMap localLayoutMap = this.mLanguagePackManager.getCurrentLayout(localLanguagePack);
      if (this.mLayoutMap.get(localLayoutMap) == null)
      {
        HashSet localHashSet = new HashSet();
        localHashSet.add(localLanguagePack);
        this.mLayoutMap.put(localLayoutMap, localHashSet);
      }
      else
      {
        ((Set)this.mLayoutMap.get(localLayoutMap)).add(localLanguagePack);
      }
    }
    return this.mLayoutMap;
  }
  
  public boolean isDeferrable()
  {
    return false;
  }
  
  public boolean onChange(Context paramContext)
  {
    Set localSet = getStringSet();
    HashSet localHashSet = new HashSet();
    Iterator localIterator = this.mLanguagePackManager.getEnabledLanguagePacks().iterator();
    while (localIterator.hasNext())
    {
      LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
      localHashSet.add(localLanguagePack.getLanguage() + "_" + localLanguagePack.getCountry());
    }
    if (!localSet.equals(localHashSet)) {
      putStringSet(localHashSet);
    }
    notifyLayoutChangeListeners();
    return false;
  }
  
  public void removeListener(LayoutChangeListener paramLayoutChangeListener)
  {
    this.mListeners.remove(paramLayoutChangeListener);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.layouts.LayoutManagerImpl
 * JD-Core Version:    0.7.0.1
 */