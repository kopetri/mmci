package com.touchtype_fluency.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.CharacterMap;
import com.touchtype_fluency.Predictor;
import com.touchtype_fluency.Punctuator;
import com.touchtype_fluency.Session;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePacks;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.ReinstallLanguagePackException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LanguageLoader
{
  private static final String TAG = LanguageLoader.class.getSimpleName();
  private static final LanguagePackAction characterMapReloader = new CharacterMapLoader(true);
  private static final LanguagePackAction languageModelLoader = new LanguagePackAction()
  {
    public void before(Context paramAnonymousContext, SharedPreferences paramAnonymousSharedPreferences, Session paramAnonymousSession)
      throws IOException
    {}
    
    public void run(Session paramAnonymousSession, LanguagePack paramAnonymousLanguagePack, ModelSetDescriptionWrapper paramAnonymousModelSetDescriptionWrapper)
      throws IOException
    {
      File localFile = paramAnonymousLanguagePack.getDirectory();
      try
      {
        paramAnonymousSession.load(paramAnonymousModelSetDescriptionWrapper.fromFile(localFile.getAbsolutePath()));
        paramAnonymousLanguagePack.setLoadingFailed(false);
        return;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        for (;;)
        {
          LogUtil.w(LanguageLoader.TAG, "Attempted to load character maps multiple times", localIllegalStateException);
        }
      }
    }
  };
  private static final LanguagePackAction liveLanguageModelLoader = new LanguagePackAction()
  {
    public void before(Context paramAnonymousContext, SharedPreferences paramAnonymousSharedPreferences, Session paramAnonymousSession)
      throws IOException
    {}
    
    public void run(Session paramAnonymousSession, LanguagePack paramAnonymousLanguagePack, ModelSetDescriptionWrapper paramAnonymousModelSetDescriptionWrapper)
      throws IOException
    {
      if ((paramAnonymousLanguagePack.hasLiveLanguageSupport()) && (paramAnonymousLanguagePack.isLiveLoadable())) {
        paramAnonymousSession.load(paramAnonymousModelSetDescriptionWrapper.fromFile(paramAnonymousLanguagePack.getLiveDirectory().getAbsolutePath()));
      }
    }
  };
  private static final Set<LanguagePackAction> loadCharacterMapActions;
  private static final Set<LanguagePackAction> loadLanguagePackActions;
  private static final Set<LanguagePackAction> loadLanguagePackAndLiveLanguagePackActions;
  private static final LanguagePackAction punctuationLoader = new LanguagePackAction()
  {
    private static final String OLD_PUNCTUATION_FILE = "/punctuation.json";
    private static final String PUNCTUATION_FILE = "/punctuation-SDK1.2.json";
    
    public void before(Context paramAnonymousContext, SharedPreferences paramAnonymousSharedPreferences, Session paramAnonymousSession)
      throws IOException
    {
      Punctuator localPunctuator = paramAnonymousSession.getPunctuator();
      localPunctuator.resetRules();
      localPunctuator.addRules(paramAnonymousContext.getResources().openRawResource(2131099655));
    }
    
    public void run(Session paramAnonymousSession, LanguagePack paramAnonymousLanguagePack, ModelSetDescriptionWrapper paramAnonymousModelSetDescriptionWrapper)
      throws IOException
    {
      if (new File(paramAnonymousLanguagePack.getDirectory(), "/punctuation-SDK1.2.json").exists()) {
        return;
      }
      Punctuator localPunctuator = paramAnonymousSession.getPunctuator();
      String str = paramAnonymousLanguagePack.getDirectory() + "/punctuation.json";
      try
      {
        localPunctuator.addRulesFromFile(str);
        return;
      }
      catch (Exception localException)
      {
        LogUtil.e(LanguageLoader.TAG, "punctuationLoader: Failed to load punctuation rules for language \"" + paramAnonymousLanguagePack.getName() + ": (" + localException.getClass().toString() + ") " + localException.getMessage(), localException);
        LogUtil.e(LanguageLoader.TAG, "Falling back to default");
        localPunctuator.addRules("{ \"lang\" : \"" + paramAnonymousLanguagePack.getID() + "\", \"dependency\": \"default\" }");
      }
    }
  };
  private final Context mContext;
  private final SharedPreferences mSharedPreferences;
  
  static
  {
    HashSet localHashSet1 = new HashSet();
    localHashSet1.add(languageModelLoader);
    localHashSet1.add(characterMapReloader);
    localHashSet1.add(punctuationLoader);
    loadLanguagePackActions = localHashSet1;
    HashSet localHashSet2 = new HashSet();
    localHashSet2.add(languageModelLoader);
    localHashSet2.add(liveLanguageModelLoader);
    localHashSet2.add(characterMapReloader);
    localHashSet2.add(punctuationLoader);
    loadLanguagePackAndLiveLanguagePackActions = localHashSet2;
    HashSet localHashSet3 = new HashSet();
    localHashSet3.add(characterMapReloader);
    loadCharacterMapActions = localHashSet3;
  }
  
  public LanguageLoader(Context paramContext)
  {
    this.mContext = paramContext;
    this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
  }
  
  private static void loadAllAccentsCharacterMapIfEnabled(CharacterMap paramCharacterMap, Resources paramResources, SharedPreferences paramSharedPreferences)
    throws IOException
  {
    if (paramSharedPreferences.getBoolean(paramResources.getString(2131296319), paramResources.getBoolean(2131492930))) {
      paramCharacterMap.addLanguage(paramResources.openRawResource(2131099650));
    }
  }
  
  private void loadLanguagePacks(Session paramSession, List<LanguagePack> paramList, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper, Iterable<LanguagePackAction> paramIterable)
    throws ReinstallLanguagePackException, IOException, InterruptedException
  {
    Iterator localIterator1 = paramIterable.iterator();
    while (localIterator1.hasNext()) {
      ((LanguagePackAction)localIterator1.next()).before(this.mContext, this.mSharedPreferences, paramSession);
    }
    Iterator localIterator2 = paramList.iterator();
    while (localIterator2.hasNext())
    {
      synchronized ((LanguagePack)localIterator2.next())
      {
        ???.setLoadingFailed(true);
        if (!???.isLoadable()) {
          break label165;
        }
        Iterator localIterator3 = paramIterable.iterator();
        while (localIterator3.hasNext())
        {
          ((LanguagePackAction)localIterator3.next()).run(paramSession, ???, paramModelSetDescriptionWrapper);
          if (Thread.interrupted()) {
            throw new InterruptedException();
          }
        }
      }
      ???.setLoadingFailed(false);
      continue;
      label165:
      if (???.isPreinstalled()) {
        throw new ReinstallLanguagePackException(???);
      }
      throw new IOException("missing language " + ???.getName());
    }
  }
  
  public void loadCharacterMaps(Session paramSession, List<LanguagePack> paramList, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper)
    throws ReinstallLanguagePackException, IOException, InterruptedException
  {
    loadLanguagePacks(paramSession, paramList, paramModelSetDescriptionWrapper, loadCharacterMapActions);
  }
  
  public void loadLanguagePacks(Session paramSession, List<LanguagePack> paramList, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper)
    throws ReinstallLanguagePackException, IOException, InterruptedException
  {
    if (LanguagePacks.liveLanguagesEnabled(this.mSharedPreferences)) {}
    for (Set localSet = loadLanguagePackAndLiveLanguagePackActions;; localSet = loadLanguagePackActions)
    {
      loadLanguagePacks(paramSession, paramList, paramModelSetDescriptionWrapper, localSet);
      return;
    }
  }
  
  private static class CharacterMapLoader
    implements LanguageLoader.LanguagePackAction
  {
    private final boolean mReload;
    
    public CharacterMapLoader(boolean paramBoolean)
    {
      this.mReload = paramBoolean;
    }
    
    public void before(Context paramContext, SharedPreferences paramSharedPreferences, Session paramSession)
      throws IOException
    {
      CharacterMap localCharacterMap = paramSession.getPredictor().getCharacterMap();
      localCharacterMap.resetLanguages();
      LanguageLoader.loadAllAccentsCharacterMapIfEnabled(localCharacterMap, paramContext.getResources(), paramSharedPreferences);
    }
    
    public void run(Session paramSession, LanguagePack paramLanguagePack, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper)
      throws IOException
    {
      CharacterMap localCharacterMap;
      String str;
      if (this.mReload)
      {
        localCharacterMap = paramSession.getPredictor().getCharacterMap();
        str = paramLanguagePack.getDirectory() + "/charactermap.json";
      }
      try
      {
        localCharacterMap.addLanguageFromFile(str);
        return;
      }
      catch (Exception localException)
      {
        LogUtil.e(LanguageLoader.TAG, "characterMapLoader: Failed to load character map for language \"" + paramLanguagePack.getName() + ": (" + localException.getClass().toString() + ") " + localException.getMessage(), localException);
      }
    }
  }
  
  private static abstract interface LanguagePackAction
  {
    public abstract void before(Context paramContext, SharedPreferences paramSharedPreferences, Session paramSession)
      throws IOException;
    
    public abstract void run(Session paramSession, LanguagePack paramLanguagePack, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper)
      throws IOException;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.LanguageLoader
 * JD-Core Version:    0.7.0.1
 */