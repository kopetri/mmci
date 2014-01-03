package com.touchtype_fluency.service;

import android.content.Context;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.storage.FolderDecorator;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.CharacterMap;
import com.touchtype_fluency.ContextCurrentWord;
import com.touchtype_fluency.KeyPressModel;
import com.touchtype_fluency.ModelSetDescription;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.Predictions;
import com.touchtype_fluency.ResultsFilter;
import com.touchtype_fluency.ResultsFilter.CapitalizationHint;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.ResultsFilter.VerbatimMode;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.Sequence.Type;
import com.touchtype_fluency.Session;
import com.touchtype_fluency.TagSelectors;
import com.touchtype_fluency.Tokenizer;
import com.touchtype_fluency.TouchHistory;
import com.touchtype_fluency.Trainer;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackListener;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.ReinstallLanguagePackException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import junit.framework.Assert;

class PredictorImpl
  implements Predictor
{
  private static final String BLACKLIST_PATH = ".blacklist";
  private static final int CONTEXT_LENGTH = 6;
  public static final String DYNAMIC_LEARNING_TAG = "learn-default";
  public static final String[] DYNAMIC_LEARNING_TAGS = { "learn-default" };
  private static final String TAG = "PredictorImpl";
  private static final Set<String> forgetset;
  private final DynamicModelHandler dynamicModelHandler;
  private final FluencyMetrics fluencyMetrics;
  private final FolderDecorator folder;
  private final LanguageLoader languageLoader;
  private LanguagePackListener languagePackListener = new LanguagePackListener()
  {
    public boolean isDeferrable()
    {
      return true;
    }
    
    public boolean onChange(Context paramAnonymousContext)
    {
      if (PredictorImpl.this.session != null)
      {
        PredictorImpl.this.reloadLanguagePacks();
        return false;
      }
      PredictorImpl.this.notifyListeners(false);
      return false;
    }
  };
  private final LanguagePackManager languagePackManager;
  private final Set<PredictorListener> listeners = new CopyOnWriteArraySet();
  private ModelSetDescriptionWrapper modelSetFactory;
  private Optional<Thread> optLoader = new Optional();
  private volatile boolean ready = false;
  private Session session = null;
  private final UserNotificationManager userNotificationManager;
  
  static
  {
    HashSet localHashSet = new HashSet();
    forgetset = localHashSet;
    localHashSet.add("\\d{16}");
  }
  
  public PredictorImpl(FolderDecorator paramFolderDecorator, LanguagePackManager paramLanguagePackManager, LanguageLoader paramLanguageLoader, DynamicModelHandler paramDynamicModelHandler, UserNotificationManager paramUserNotificationManager, FluencyMetrics paramFluencyMetrics)
  {
    this.folder = paramFolderDecorator;
    this.languagePackManager = paramLanguagePackManager;
    this.languageLoader = paramLanguageLoader;
    this.dynamicModelHandler = paramDynamicModelHandler;
    this.userNotificationManager = paramUserNotificationManager;
    this.fluencyMetrics = paramFluencyMetrics;
  }
  
  private void createTemporaryModel()
  {
    Assert.assertNotNull(this.session);
    try
    {
      this.session.load(ModelSetDescription.dynamicTemporary(1, new String[0]));
      return;
    }
    catch (IOException localIOException)
    {
      Assert.fail(localIOException.getMessage());
    }
  }
  
  private File getBlacklistPath()
  {
    return new File(this.folder.getBaseFolder(), ".blacklist");
  }
  
  private static String getEncodingTag(Prediction paramPrediction)
  {
    Iterator localIterator = paramPrediction.getTags().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("encoding:")) {
        return str.substring(9);
      }
    }
    return null;
  }
  
  /* Error */
  private void interruptLoader()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 89	com/touchtype_fluency/service/PredictorImpl:optLoader	Lcom/touchtype_fluency/service/Optional;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 89	com/touchtype_fluency/service/PredictorImpl:optLoader	Lcom/touchtype_fluency/service/Optional;
    //   11: invokevirtual 204	com/touchtype_fluency/service/Optional:isDefined	()Z
    //   14: ifeq +25 -> 39
    //   17: aload_0
    //   18: getfield 89	com/touchtype_fluency/service/PredictorImpl:optLoader	Lcom/touchtype_fluency/service/Optional;
    //   21: invokevirtual 207	com/touchtype_fluency/service/Optional:getValue	()Ljava/lang/Object;
    //   24: checkcast 209	java/lang/Thread
    //   27: astore 4
    //   29: aload 4
    //   31: invokevirtual 212	java/lang/Thread:interrupt	()V
    //   34: aload 4
    //   36: invokevirtual 215	java/lang/Thread:join	()V
    //   39: aload_0
    //   40: getfield 89	com/touchtype_fluency/service/PredictorImpl:optLoader	Lcom/touchtype_fluency/service/Optional;
    //   43: invokevirtual 218	com/touchtype_fluency/service/Optional:clear	()V
    //   46: aload_1
    //   47: monitorexit
    //   48: return
    //   49: astore_3
    //   50: aload_1
    //   51: monitorexit
    //   52: aload_3
    //   53: athrow
    //   54: astore_2
    //   55: goto -9 -> 46
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	this	PredictorImpl
    //   54	1	2	localInterruptedException	InterruptedException
    //   49	4	3	localObject	Object
    //   27	8	4	localThread	Thread
    // Exception table:
    //   from	to	target	type
    //   7	39	49	finally
    //   39	46	49	finally
    //   46	48	49	finally
    //   7	39	54	java/lang/InterruptedException
    //   39	46	54	java/lang/InterruptedException
  }
  
  private boolean isChineseLoaded()
  {
    ModelSetDescription[] arrayOfModelSetDescription = this.session.getLoadedSets();
    int i = arrayOfModelSetDescription.length;
    for (int j = 0; j < i; j++)
    {
      String[] arrayOfString = arrayOfModelSetDescription[j].getUserTags();
      int k = arrayOfString.length;
      for (int m = 0; m < k; m++) {
        if (arrayOfString[m].startsWith("id:zh")) {
          return true;
        }
      }
    }
    return false;
  }
  
  private void learnChineseName(String paramString1, String paramString2, Trainer paramTrainer)
  {
    this.session.getTrainer().addTermMapping(paramString2, paramString1);
    int i = paramString2.indexOf('\'');
    if (i != -1)
    {
      this.session.getTrainer().addTermMapping(paramString2.substring(0, i), paramString1.substring(0, 1));
      this.session.getTrainer().addTermMapping(paramString2.substring(i + 1), paramString1.substring(1));
    }
  }
  
  private boolean learnChineseName(String paramString)
  {
    ResultsFilter.PredictionSearchType localPredictionSearchType = ResultsFilter.PredictionSearchType.PINYIN;
    ResultsFilter localResultsFilter = new ResultsFilter(1, ResultsFilter.CapitalizationHint.LOWER_CASE, ResultsFilter.VerbatimMode.DISABLED, localPredictionSearchType);
    Predictions localPredictions = this.session.getPredictor().getPredictions(new Sequence(), new TouchHistory(paramString), localResultsFilter);
    if (localPredictions.size() > 0)
    {
      Prediction localPrediction = localPredictions.get(0);
      String str = getEncodingTag(localPrediction);
      if ((str != null) && (paramString.contentEquals(localPrediction.getPrediction())))
      {
        learnChineseName(paramString, str, this.session.getTrainer());
        return true;
      }
    }
    return false;
  }
  
  /* Error */
  private void loadCharacterMaps()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 93	com/touchtype_fluency/service/PredictorImpl:session	Lcom/touchtype_fluency/Session;
    //   6: invokestatic 134	junit/framework/Assert:assertNotNull	(Ljava/lang/Object;)V
    //   9: iconst_1
    //   10: istore_2
    //   11: aload_0
    //   12: getfield 99	com/touchtype_fluency/service/PredictorImpl:languageLoader	Lcom/touchtype_fluency/service/LanguageLoader;
    //   15: aload_0
    //   16: getfield 93	com/touchtype_fluency/service/PredictorImpl:session	Lcom/touchtype_fluency/Session;
    //   19: aload_0
    //   20: getfield 97	com/touchtype_fluency/service/PredictorImpl:languagePackManager	Lcom/touchtype_fluency/service/languagepacks/LanguagePackManager;
    //   23: invokevirtual 317	com/touchtype_fluency/service/languagepacks/LanguagePackManager:getEnabledLanguagePacks	()Ljava/util/Vector;
    //   26: aload_0
    //   27: getfield 319	com/touchtype_fluency/service/PredictorImpl:modelSetFactory	Lcom/touchtype_fluency/service/ModelSetDescriptionWrapper;
    //   30: invokevirtual 324	com/touchtype_fluency/service/LanguageLoader:loadCharacterMaps	(Lcom/touchtype_fluency/Session;Ljava/util/List;Lcom/touchtype_fluency/service/ModelSetDescriptionWrapper;)V
    //   33: iload_2
    //   34: ifeq +14 -> 48
    //   37: aload_0
    //   38: getfield 103	com/touchtype_fluency/service/PredictorImpl:userNotificationManager	Lcom/touchtype/social/UserNotificationManager;
    //   41: invokevirtual 329	com/touchtype/social/UserNotificationManager:clearUnableToLoadLanguagePacks	()V
    //   44: aload_0
    //   45: invokespecial 332	com/touchtype_fluency/service/PredictorImpl:setReady	()V
    //   48: aload_0
    //   49: iload_2
    //   50: invokespecial 113	com/touchtype_fluency/service/PredictorImpl:notifyListeners	(Z)V
    //   53: aload_0
    //   54: monitorexit
    //   55: return
    //   56: astore 5
    //   58: aload 5
    //   60: invokevirtual 336	com/touchtype_fluency/service/languagepacks/languagepackmanager/ReinstallLanguagePackException:getLanguagePack	()Lcom/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack;
    //   63: aconst_null
    //   64: invokevirtual 342	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack:download	(Lcom/touchtype_fluency/service/ProgressListener;)V
    //   67: goto -34 -> 33
    //   70: astore_1
    //   71: aload_0
    //   72: monitorexit
    //   73: aload_1
    //   74: athrow
    //   75: astore 4
    //   77: ldc 21
    //   79: new 344	java/lang/StringBuilder
    //   82: dup
    //   83: ldc_w 346
    //   86: invokespecial 347	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   89: aload 4
    //   91: invokevirtual 351	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   94: invokevirtual 354	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   97: invokestatic 359	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   100: aload_0
    //   101: getfield 103	com/touchtype_fluency/service/PredictorImpl:userNotificationManager	Lcom/touchtype/social/UserNotificationManager;
    //   104: invokevirtual 362	com/touchtype/social/UserNotificationManager:unableToLoadLanguagePacks	()V
    //   107: iconst_0
    //   108: istore_2
    //   109: goto -76 -> 33
    //   112: astore_3
    //   113: goto -60 -> 53
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	116	0	this	PredictorImpl
    //   70	4	1	localObject	Object
    //   10	99	2	bool	boolean
    //   112	1	3	localInterruptedException	InterruptedException
    //   75	15	4	localIOException	IOException
    //   56	3	5	localReinstallLanguagePackException	ReinstallLanguagePackException
    // Exception table:
    //   from	to	target	type
    //   11	33	56	com/touchtype_fluency/service/languagepacks/languagepackmanager/ReinstallLanguagePackException
    //   2	9	70	finally
    //   11	33	70	finally
    //   37	48	70	finally
    //   48	53	70	finally
    //   58	67	70	finally
    //   77	107	70	finally
    //   11	33	75	java/io/IOException
    //   11	33	112	java/lang/InterruptedException
  }
  
  private void loadLanguagePacks()
  {
    Assert.assertNotNull(this.session);
    Assert.assertFalse(isReady());
    boolean bool = true;
    try
    {
      this.fluencyMetrics.getLoadLanguages().start();
      this.languageLoader.loadLanguagePacks(this.session, this.languagePackManager.getEnabledLanguagePacks(), this.modelSetFactory);
      this.dynamicModelHandler.loadUserModel(this.session, this.modelSetFactory);
      this.fluencyMetrics.getLoadLanguages().stop();
      if (bool)
      {
        this.userNotificationManager.clearUnableToLoadLanguagePacks();
        createTemporaryModel();
        setReady();
      }
      notifyListeners(bool);
      return;
    }
    catch (StorageNotAvailableException localStorageNotAvailableException)
    {
      for (;;)
      {
        bool = false;
      }
    }
    catch (ReinstallLanguagePackException localReinstallLanguagePackException)
    {
      for (;;)
      {
        localReinstallLanguagePackException.getLanguagePack().download(null);
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e("PredictorImpl", "Exception while loading language packs: " + localIOException);
        this.userNotificationManager.unableToLoadLanguagePacks();
        bool = false;
      }
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  private boolean matchForget(String paramString)
  {
    Iterator localIterator = forgetset.iterator();
    while (localIterator.hasNext()) {
      if (paramString.matches((String)localIterator.next())) {
        return true;
      }
    }
    return false;
  }
  
  private void notifyListeners(boolean paramBoolean)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
    {
      PredictorListener localPredictorListener = (PredictorListener)localIterator.next();
      if (paramBoolean) {
        localPredictorListener.onPredictorReady();
      } else {
        localPredictorListener.onPredictorError();
      }
    }
  }
  
  private void resetTemporaryModel()
  {
    Assert.assertNotNull(this.session);
    this.session.unload(ModelSetDescription.dynamicTemporary(1, new String[0]));
    createTemporaryModel();
  }
  
  private void setReady()
  {
    try
    {
      this.ready = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private void startLoader(Runnable paramRunnable)
  {
    Assert.assertNotNull(this.session);
    synchronized (this.optLoader)
    {
      if ((this.optLoader.isDefined()) && (((Thread)this.optLoader.getValue()).isAlive())) {
        return;
      }
      Thread localThread = new Thread(paramRunnable);
      localThread.start();
      this.optLoader.setValue(localThread);
      return;
    }
  }
  
  /* Error */
  private void unloadLanguagePacks()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 93	com/touchtype_fluency/service/PredictorImpl:session	Lcom/touchtype_fluency/Session;
    //   6: invokestatic 134	junit/framework/Assert:assertNotNull	(Ljava/lang/Object;)V
    //   9: aload_0
    //   10: iconst_0
    //   11: putfield 91	com/touchtype_fluency/service/PredictorImpl:ready	Z
    //   14: aload_0
    //   15: getfield 93	com/touchtype_fluency/service/PredictorImpl:session	Lcom/touchtype_fluency/Session;
    //   18: invokeinterface 235 1 0
    //   23: invokeinterface 424 1 0
    //   28: aload_0
    //   29: getfield 105	com/touchtype_fluency/service/PredictorImpl:fluencyMetrics	Lcom/touchtype_fluency/service/FluencyMetrics;
    //   32: invokevirtual 427	com/touchtype_fluency/service/FluencyMetrics:getUnloadLanguages	()Lcom/touchtype_fluency/service/FluencyMetrics$Metrics;
    //   35: invokevirtual 381	com/touchtype_fluency/service/FluencyMetrics$Metrics:start	()V
    //   38: aload_0
    //   39: aload_0
    //   40: getfield 93	com/touchtype_fluency/service/PredictorImpl:session	Lcom/touchtype_fluency/Session;
    //   43: invokespecial 430	com/touchtype_fluency/service/PredictorImpl:unloadLanguagePacks	(Lcom/touchtype_fluency/Session;)V
    //   46: aload_0
    //   47: getfield 105	com/touchtype_fluency/service/PredictorImpl:fluencyMetrics	Lcom/touchtype_fluency/service/FluencyMetrics;
    //   50: invokevirtual 427	com/touchtype_fluency/service/FluencyMetrics:getUnloadLanguages	()Lcom/touchtype_fluency/service/FluencyMetrics$Metrics;
    //   53: invokevirtual 392	com/touchtype_fluency/service/FluencyMetrics$Metrics:stop	()V
    //   56: aload_0
    //   57: monitorexit
    //   58: return
    //   59: astore_1
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_1
    //   63: athrow
    //   64: astore_2
    //   65: goto -37 -> 28
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	68	0	this	PredictorImpl
    //   59	4	1	localObject	Object
    //   64	1	2	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   2	14	59	finally
    //   14	28	59	finally
    //   28	56	59	finally
    //   14	28	64	java/io/IOException
  }
  
  private void unloadLanguagePacks(Session paramSession)
  {
    paramSession.batchUnload(paramSession.getLoadedSets());
    String[] arrayOfString = paramSession.getTags(TagSelectors.staticModels());
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      StringBuilder localStringBuilder = new StringBuilder("inconsistent static models (still loaded/reloaded by another thread):");
      for (ModelSetDescription localModelSetDescription : paramSession.getLoadedSets())
      {
        localStringBuilder.append(' ');
        localStringBuilder.append(localModelSetDescription.toString());
      }
      LogUtil.e("PredictorImpl", localStringBuilder.toString());
      Assert.fail();
    }
  }
  
  public void addListener(PredictorListener paramPredictorListener)
  {
    this.listeners.add(paramPredictorListener);
  }
  
  public void addToTemporaryModel(String paramString)
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    Sequence localSequence = new Sequence();
    localSequence.append(paramString);
    this.session.getTrainer().addSequence(localSequence, TagSelectors.temporaryDynamicModels());
  }
  
  public void addToUserModel(Sequence paramSequence)
    throws PredictorNotReadyException
  {
    try
    {
      this.session.getTrainer().addSequence(filterForget(paramSequence), TagSelectors.taggedWith("learn-default"));
      saveUserModel();
      resetTemporaryModel();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void addToUserModel(String paramString1, Sequence.Type paramType, String paramString2)
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    learnMappings();
    Sequence localSequence = this.session.getTokenizer().split(paramString1);
    localSequence.setType(paramType);
    localSequence.setFieldHint(paramString2);
    addToUserModel(localSequence);
  }
  
  public void batchAddToUserModel(String paramString1, Sequence.Type paramType, String paramString2)
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    boolean bool1 = false;
    if (paramString2 == "name")
    {
      boolean bool2 = isChineseLoaded();
      bool1 = false;
      if (bool2) {
        bool1 = learnChineseName(paramString1);
      }
    }
    if (!bool1)
    {
      Sequence localSequence = this.session.getTokenizer().split(paramString1);
      localSequence.setType(paramType);
      localSequence.setFieldHint(paramString2);
      this.session.getTrainer().addSequence(filterForget(localSequence), TagSelectors.taggedWith("learn-default"));
    }
  }
  
  public void clearLayoutKeys()
  {
    if (this.session == null) {
      throw new IllegalStateException("Session is null");
    }
    this.session.getPredictor().clearLayoutKeys();
  }
  
  public void clearUserModel()
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    try
    {
      this.dynamicModelHandler.deleteUserModel(this.session, this.modelSetFactory);
      this.dynamicModelHandler.loadUserModel(this.session, this.modelSetFactory);
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e("PredictorImpl", "Exception while clearing user language model: " + localIOException);
      }
    }
    catch (StorageNotAvailableException localStorageNotAvailableException)
    {
      for (;;)
      {
        LogUtil.e("PredictorImpl", "Exception while clearing user language model: " + localStorageNotAvailableException);
      }
    }
  }
  
  public Sequence filterForget(Sequence paramSequence)
  {
    Sequence localSequence = new Sequence();
    localSequence.setType(paramSequence.getType());
    localSequence.setFieldHint(paramSequence.getFieldHint());
    Iterator localIterator = paramSequence.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (!matchForget(str)) {
        localSequence.append(str);
      }
    }
    return localSequence;
  }
  
  public CharacterMap getCharacterMap()
  {
    if (this.session == null) {
      return null;
    }
    return this.session.getPredictor().getCharacterMap();
  }
  
  public KeyPressModel getKeyPressModel()
  {
    if (this.session == null) {
      return null;
    }
    return this.session.getPredictor().getKeyPressModel();
  }
  
  public Predictions getPredictions(Sequence paramSequence, TouchHistory paramTouchHistory, ResultsFilter paramResultsFilter)
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    this.fluencyMetrics.getGetPredictions().start();
    Predictions localPredictions = this.session.getPredictor().getPredictions(paramSequence, paramTouchHistory, paramResultsFilter);
    this.fluencyMetrics.getGetPredictions().stop();
    return localPredictions;
  }
  
  public FluencyPreferences getPreferences()
  {
    if (this.session == null) {
      return null;
    }
    return new FluencyPreferencesImpl(this.session.getParameterSet(), this.folder);
  }
  
  public boolean isReady()
  {
    return this.ready;
  }
  
  public void learnFrom(TouchHistory paramTouchHistory, Prediction paramPrediction)
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    this.session.getTrainer().learnFrom(paramTouchHistory, paramPrediction);
  }
  
  public void learnMappings()
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    this.session.getTrainer().learnMappings();
  }
  
  public void learnMappingsFrom(Prediction paramPrediction, ResultsFilter.PredictionSearchType paramPredictionSearchType)
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    this.session.getTrainer().learnMappingsFrom(paramPrediction, paramPredictionSearchType);
  }
  
  public void mergeUserModel(String paramString)
    throws PredictorNotReadyException
  {
    try
    {
      if (this.session == null) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    try
    {
      this.dynamicModelHandler.mergeUserModel(this.session, this.modelSetFactory, paramString);
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e("PredictorImpl", "Exception while merging user language model: " + localIOException);
      }
    }
    catch (StorageNotAvailableException localStorageNotAvailableException)
    {
      for (;;)
      {
        LogUtil.e("PredictorImpl", "Exception while merging user language model: " + localStorageNotAvailableException);
      }
    }
  }
  
  public void onCreate(Context paramContext, Session paramSession, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper)
  {
    this.modelSetFactory = paramModelSetDescriptionWrapper;
    if ((this.session == null) && (paramSession != null))
    {
      this.session = paramSession;
      paramSession.getTrainer().setBlacklist(getBlacklistPath().getAbsolutePath());
      getPreferences().load(paramContext);
    }
    if (!isReady())
    {
      this.languagePackManager.addListener(this.languagePackListener);
      if (!this.languagePackManager.isReady())
      {
        this.languagePackManager.onCreate();
        return;
      }
      this.languagePackListener.onChange(paramContext);
      return;
    }
    LogUtil.w("PredictorImpl", "onCreate was called twice");
  }
  
  public void onCreate(Session paramSession, Context paramContext)
  {
    onCreate(paramContext, paramSession, new ModelSetDescriptionWrapper());
  }
  
  public void onDestroy()
  {
    if (!this.listeners.isEmpty())
    {
      LogUtil.e("PredictorImpl", "Some PredictorListeners are still listening to a Predictor that is being destroyed");
      this.listeners.clear();
    }
    interruptLoader();
    this.languagePackManager.removeListener(this.languagePackListener);
    this.languagePackManager.onDestroy();
    this.ready = false;
    if (this.session != null)
    {
      this.fluencyMetrics.getUnloadSession().start();
      this.session.dispose();
      this.session = null;
      this.fluencyMetrics.getUnloadSession().stop();
    }
  }
  
  public boolean queryTerm(String paramString)
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    boolean bool = this.session.getPredictor().queryTerm(paramString, TagSelectors.dynamicModels());
    return bool;
  }
  
  public void reloadCharacterMaps()
  {
    startLoader(new Runnable()
    {
      public void run()
      {
        PredictorImpl.this.loadCharacterMaps();
      }
    });
  }
  
  public void reloadLanguagePacks()
  {
    startLoader(new Runnable()
    {
      public void run()
      {
        PredictorImpl.this.unloadLanguagePacks();
        PredictorImpl.this.loadLanguagePacks();
      }
    });
  }
  
  public void removeFromTemporaryModel(String paramString)
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    this.session.getTrainer().removeTerm(paramString, TagSelectors.temporaryDynamicModels());
  }
  
  public void removeListener(PredictorListener paramPredictorListener)
  {
    Assert.assertTrue(this.listeners.remove(paramPredictorListener));
  }
  
  public void removeTerm(String paramString)
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    this.session.getTrainer().removeTerm(paramString);
    saveUserModel();
  }
  
  public void removeTerms(Iterable<String> paramIterable)
    throws PredictorNotReadyException
  {
    if (!isReady()) {
      throw new PredictorNotReadyException();
    }
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      this.session.getTrainer().removeTerm(str);
    }
    saveUserModel();
  }
  
  public void saveUserModel()
    throws PredictorNotReadyException
  {
    try
    {
      if (!isReady()) {
        throw new PredictorNotReadyException();
      }
    }
    finally {}
    try
    {
      this.session.getTrainer().write();
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        this.userNotificationManager.unableToSaveDynamic();
      }
    }
  }
  
  public void setInputType(String paramString)
  {
    if (paramString == null)
    {
      this.session.enableModels(TagSelectors.allModels());
      return;
    }
    ArrayList localArrayList = new ArrayList(2);
    localArrayList.add("learn-default");
    localArrayList.add("input-type:" + paramString);
    this.session.enableModels(TagSelectors.taggedWith(localArrayList));
  }
  
  public void setIntentionalEvents(Set<String> paramSet)
  {
    if (this.session == null) {
      throw new IllegalStateException("Session is null");
    }
    this.session.getPredictor().setIntentionalEvents(paramSet);
  }
  
  public void setLayoutKeys(Set<String> paramSet)
  {
    if (this.session == null) {
      throw new IllegalStateException("Session is null");
    }
    this.session.getPredictor().setLayoutKeys(paramSet);
  }
  
  public Sequence split(String paramString)
  {
    Sequence localSequence;
    if (this.session == null) {
      localSequence = null;
    }
    do
    {
      return localSequence;
      localSequence = this.session.getTokenizer().split(paramString);
    } while (localSequence.size() >= 6);
    localSequence.setType(Sequence.Type.MESSAGE_START);
    return localSequence;
  }
  
  public ContextCurrentWord splitContextCurrentWord(String paramString)
  {
    if (this.session == null) {
      return null;
    }
    return this.session.getTokenizer().splitContextCurrentWord(paramString, 6);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.PredictorImpl
 * JD-Core Version:    0.7.0.1
 */