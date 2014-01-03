package com.touchtype.cloud.sync;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.touchtype.sync.client.CompletionListener;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.ModelSetDescription;
import com.touchtype_fluency.ModelSetDescription.Type;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.Sequence.Type;
import com.touchtype_fluency.Session;
import com.touchtype_fluency.TagSelector;
import com.touchtype_fluency.TagSelectors;
import com.touchtype_fluency.Tokenizer;
import com.touchtype_fluency.Trainer;
import com.touchtype_fluency.internal.ModelMerger;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.PredictorNotReadyException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class SyncDeltaManager
{
  private static final String[] SYNC_MODEL_TAGS = { "sync-model" };
  private final FluencyServiceProxy fluencyServiceProxy;
  private final File keyboardDeltaModelDirectory;
  private final File pushDeltaModelDirectory;
  private final File syncFilesDirectory;
  private final File userModelDirectory;
  
  public SyncDeltaManager(FluencyServiceProxy paramFluencyServiceProxy, File paramFile1, File paramFile2, File paramFile3, File paramFile4)
  {
    this.fluencyServiceProxy = paramFluencyServiceProxy;
    this.syncFilesDirectory = paramFile1;
    this.userModelDirectory = paramFile2;
    this.keyboardDeltaModelDirectory = paramFile3;
    this.pushDeltaModelDirectory = paramFile4;
  }
  
  private static ModelSetDescription createModelSetDescription(File paramFile)
  {
    return ModelSetDescription.dynamicWithFile(paramFile.toString(), 4, SYNC_MODEL_TAGS, ModelSetDescription.Type.OTHER_DYNAMIC_MODEL);
  }
  
  private static void ensureModelExists(Session paramSession, File paramFile)
    throws IOException
  {
    ModelSetDescription localModelSetDescription = loadSyncModel(paramSession, paramFile);
    paramSession.getTrainer().write();
    paramSession.unload(localModelSetDescription);
  }
  
  private BlackList getBlackListFromFile(File paramFile)
    throws IOException
  {
    BlackList localBlackList;
    if (!paramFile.exists()) {
      localBlackList = new BlackList();
    }
    for (;;)
    {
      return localBlackList;
      try
      {
        localBlackList = (BlackList)new Gson().fromJson(Files.toString(paramFile, Charsets.UTF_8), BlackList.class);
        if (localBlackList != null) {
          continue;
        }
        return new BlackList();
      }
      catch (JsonSyntaxException localJsonSyntaxException)
      {
        for (;;)
        {
          LogUtil.e("SyncDeltaManager", localJsonSyntaxException.getMessage(), localJsonSyntaxException);
          localBlackList = null;
        }
      }
    }
  }
  
  private String getKeyboardDeltaModelFullPath()
  {
    return this.keyboardDeltaModelDirectory.getAbsolutePath() + "/dynamic.lm";
  }
  
  private boolean learnIntoKeyboardDelta(Learner paramLearner)
  {
    try
    {
      ModelSetDescription localModelSetDescription = loadSyncModel(this.fluencyServiceProxy.getSession(), this.keyboardDeltaModelDirectory);
      try
      {
        TagSelector localTagSelector = TagSelectors.taggedWith("sync-model");
        paramLearner.learn(this.fluencyServiceProxy, localTagSelector);
        this.fluencyServiceProxy.getSession().getTrainer().write(localTagSelector);
        return true;
      }
      finally
      {
        this.fluencyServiceProxy.getSession().unload(localModelSetDescription);
      }
      return false;
    }
    catch (IOException localIOException)
    {
      LogUtil.e("SyncDeltaManager", "Got IOException when adding text to sync model " + localIOException.getMessage());
    }
  }
  
  private static ModelSetDescription loadSyncModel(Session paramSession, File paramFile)
    throws IOException
  {
    try
    {
      ModelSetDescription localModelSetDescription2 = ModelSetDescription.fromFile(paramFile.toString());
      paramSession.load(localModelSetDescription2);
      return localModelSetDescription2;
    }
    catch (IOException localIOException)
    {
      if (!paramFile.isDirectory()) {
        paramFile.mkdir();
      }
      ModelSetDescription localModelSetDescription1 = createModelSetDescription(paramFile);
      paramSession.load(localModelSetDescription1);
      return localModelSetDescription1;
    }
  }
  
  private static ModelSetDescription mergeModelSetDescription(ModelSetDescription paramModelSetDescription1, ModelSetDescription paramModelSetDescription2, File paramFile)
    throws IllegalStateException, IOException
  {
    return ModelSetDescription.merge(paramModelSetDescription1, paramModelSetDescription2, paramFile.toString());
  }
  
  public boolean addLMToDeltaLM(String paramString)
  {
    for (;;)
    {
      try
      {
        ensureModelExists(this.fluencyServiceProxy.getSession(), this.keyboardDeltaModelDirectory);
        ModelMerger.mergeModels(getKeyboardDeltaModelFullPath(), getKeyboardDeltaModelFullPath(), paramString);
        bool = true;
        return bool;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        LogUtil.e("SyncDeltaManager", "Got Illegal State Exception while copying dynamic model into keyboard delta : " + localIllegalStateException.getMessage());
      }
      catch (IOException localIOException)
      {
        LogUtil.e("SyncDeltaManager", "Got IO exception while copying dynamic model into keyboard delta : " + localIOException.getMessage());
      }
      finally {}
      boolean bool = false;
    }
  }
  
  public boolean addMultipleTextToKeyboardDelta(final List<String> paramList1, final Sequence.Type paramType, final List<String> paramList2)
  {
    try
    {
      boolean bool = learnIntoKeyboardDelta(new Learner()
      {
        public void learn(FluencyServiceProxy paramAnonymousFluencyServiceProxy, TagSelector paramAnonymousTagSelector)
        {
          Iterator localIterator = paramList1.iterator();
          while (localIterator.hasNext())
          {
            String str1 = (String)localIterator.next();
            Sequence localSequence = paramAnonymousFluencyServiceProxy.getSession().getTokenizer().split(str1);
            localSequence.setType(paramType);
            String str2 = (String)paramList2.get(paramList1.indexOf(str1));
            if (str2 != null) {
              localSequence.setFieldHint(str2);
            }
            paramAnonymousFluencyServiceProxy.getSession().getTrainer().addSequence(paramAnonymousFluencyServiceProxy.getPredictor().filterForget(localSequence), paramAnonymousTagSelector);
          }
        }
      });
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public boolean addTextSequenceToKeyboardDelta(final Sequence paramSequence)
  {
    try
    {
      boolean bool = learnIntoKeyboardDelta(new Learner()
      {
        public void learn(FluencyServiceProxy paramAnonymousFluencyServiceProxy, TagSelector paramAnonymousTagSelector)
        {
          paramAnonymousFluencyServiceProxy.getSession().getTrainer().addSequence(paramAnonymousFluencyServiceProxy.getPredictor().filterForget(paramSequence), paramAnonymousTagSelector);
        }
      });
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void addToBlackList(final String paramString)
  {
    try
    {
      File localFile = new File(this.syncFilesDirectory, "stop_words.json");
      BlackList localBlackList = getBlackListFromFile(localFile);
      localBlackList.stopWords.add(paramString);
      Files.write(new Gson().toJson(localBlackList).getBytes(), localFile);
      this.fluencyServiceProxy.runWhenConnected(new Runnable()
      {
        public void run()
        {
          try
          {
            if (SyncDeltaManager.this.keyboardDeltaModelDirectory.exists())
            {
              ModelSetDescription localModelSetDescription2 = SyncDeltaManager.loadSyncModel(SyncDeltaManager.this.fluencyServiceProxy.getSession(), SyncDeltaManager.this.keyboardDeltaModelDirectory);
              SyncDeltaManager.this.fluencyServiceProxy.getSession().getTrainer().removeTerm(paramString, TagSelectors.taggedWith("sync-model"));
              SyncDeltaManager.this.fluencyServiceProxy.getSession().unload(localModelSetDescription2);
            }
            if (SyncDeltaManager.this.pushDeltaModelDirectory.exists())
            {
              ModelSetDescription localModelSetDescription1 = SyncDeltaManager.loadSyncModel(SyncDeltaManager.this.fluencyServiceProxy.getSession(), SyncDeltaManager.this.pushDeltaModelDirectory);
              SyncDeltaManager.this.fluencyServiceProxy.getSession().getTrainer().removeTerm(paramString, TagSelectors.taggedWith("sync-model"));
              SyncDeltaManager.this.fluencyServiceProxy.getSession().unload(localModelSetDescription1);
            }
            return;
          }
          catch (IOException localIOException)
          {
            LogUtil.e("SyncDeltaManager", "Error while loading sync model:" + localIOException.getMessage());
          }
        }
      });
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e("SyncDeltaManager", "Error while writing blacklist file:" + localIOException.getMessage());
      }
    }
    finally {}
  }
  
  public void clearBlackListWords()
  {
    try
    {
      new File(this.syncFilesDirectory, "stop_words.json").delete();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public void clearKeyboardModel()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 33	com/touchtype/cloud/sync/SyncDeltaManager:keyboardDeltaModelDirectory	Ljava/io/File;
    //   6: invokestatic 281	com/touchtype/common/util/FileUtils:deleteRecursively	(Ljava/io/File;)V
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: astore_3
    //   13: ldc 117
    //   15: new 129	java/lang/StringBuilder
    //   18: dup
    //   19: ldc_w 283
    //   22: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   25: aload_3
    //   26: invokevirtual 286	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   29: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   32: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   35: goto -26 -> 9
    //   38: astore_2
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_2
    //   42: athrow
    //   43: astore_1
    //   44: ldc 117
    //   46: new 129	java/lang/StringBuilder
    //   49: dup
    //   50: ldc_w 283
    //   53: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   56: aload_1
    //   57: invokevirtual 286	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   60: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   63: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   66: goto -57 -> 9
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	69	0	this	SyncDeltaManager
    //   43	14	1	localIOException	IOException
    //   38	4	2	localObject	Object
    //   12	14	3	localFileNotFoundException	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   2	9	12	java/io/FileNotFoundException
    //   2	9	38	finally
    //   13	35	38	finally
    //   44	66	38	finally
    //   2	9	43	java/io/IOException
  }
  
  /* Error */
  public void clearPushModel()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 35	com/touchtype/cloud/sync/SyncDeltaManager:pushDeltaModelDirectory	Ljava/io/File;
    //   6: invokestatic 281	com/touchtype/common/util/FileUtils:deleteRecursively	(Ljava/io/File;)V
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: astore_3
    //   13: ldc 117
    //   15: new 129	java/lang/StringBuilder
    //   18: dup
    //   19: ldc_w 289
    //   22: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   25: aload_3
    //   26: invokevirtual 286	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   29: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   32: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   35: goto -26 -> 9
    //   38: astore_2
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_2
    //   42: athrow
    //   43: astore_1
    //   44: ldc 117
    //   46: new 129	java/lang/StringBuilder
    //   49: dup
    //   50: ldc_w 289
    //   53: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   56: aload_1
    //   57: invokevirtual 286	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   60: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   63: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   66: goto -57 -> 9
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	69	0	this	SyncDeltaManager
    //   43	14	1	localIOException	IOException
    //   38	4	2	localObject	Object
    //   12	14	3	localFileNotFoundException	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   2	9	12	java/io/FileNotFoundException
    //   2	9	38	finally
    //   13	35	38	finally
    //   44	66	38	finally
    //   2	9	43	java/io/IOException
  }
  
  public void clearState()
  {
    try
    {
      clearBlackListWords();
      clearPushModel();
      clearKeyboardModel();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public void copyUserModel()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 31	com/touchtype/cloud/sync/SyncDeltaManager:userModelDirectory	Ljava/io/File;
    //   6: invokevirtual 92	java/io/File:exists	()Z
    //   9: istore_2
    //   10: iload_2
    //   11: ifeq +61 -> 72
    //   14: aload_0
    //   15: getfield 27	com/touchtype/cloud/sync/SyncDeltaManager:fluencyServiceProxy	Lcom/touchtype_fluency/service/FluencyServiceProxy;
    //   18: invokevirtual 148	com/touchtype_fluency/service/FluencyServiceProxy:getSession	()Lcom/touchtype_fluency/Session;
    //   21: aload_0
    //   22: getfield 31	com/touchtype/cloud/sync/SyncDeltaManager:userModelDirectory	Ljava/io/File;
    //   25: invokestatic 199	com/touchtype/cloud/sync/SyncDeltaManager:ensureModelExists	(Lcom/touchtype_fluency/Session;Ljava/io/File;)V
    //   28: aload_0
    //   29: getfield 27	com/touchtype/cloud/sync/SyncDeltaManager:fluencyServiceProxy	Lcom/touchtype_fluency/service/FluencyServiceProxy;
    //   32: invokevirtual 148	com/touchtype_fluency/service/FluencyServiceProxy:getSession	()Lcom/touchtype_fluency/Session;
    //   35: aload_0
    //   36: getfield 35	com/touchtype/cloud/sync/SyncDeltaManager:pushDeltaModelDirectory	Ljava/io/File;
    //   39: invokestatic 199	com/touchtype/cloud/sync/SyncDeltaManager:ensureModelExists	(Lcom/touchtype_fluency/Session;Ljava/io/File;)V
    //   42: aload_0
    //   43: getfield 35	com/touchtype/cloud/sync/SyncDeltaManager:pushDeltaModelDirectory	Ljava/io/File;
    //   46: invokestatic 187	com/touchtype/cloud/sync/SyncDeltaManager:createModelSetDescription	(Ljava/io/File;)Lcom/touchtype_fluency/ModelSetDescription;
    //   49: astore 5
    //   51: aload_0
    //   52: getfield 31	com/touchtype/cloud/sync/SyncDeltaManager:userModelDirectory	Ljava/io/File;
    //   55: invokestatic 187	com/touchtype/cloud/sync/SyncDeltaManager:createModelSetDescription	(Ljava/io/File;)Lcom/touchtype_fluency/ModelSetDescription;
    //   58: astore 6
    //   60: aload 6
    //   62: aload 5
    //   64: aload_0
    //   65: getfield 35	com/touchtype/cloud/sync/SyncDeltaManager:pushDeltaModelDirectory	Ljava/io/File;
    //   68: invokestatic 299	com/touchtype/cloud/sync/SyncDeltaManager:mergeModelSetDescription	(Lcom/touchtype_fluency/ModelSetDescription;Lcom/touchtype_fluency/ModelSetDescription;Ljava/io/File;)Lcom/touchtype_fluency/ModelSetDescription;
    //   71: pop
    //   72: aload_0
    //   73: monitorexit
    //   74: return
    //   75: astore_3
    //   76: ldc 117
    //   78: new 129	java/lang/StringBuilder
    //   81: dup
    //   82: ldc_w 301
    //   85: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   88: aload_3
    //   89: invokevirtual 169	java/io/IOException:getMessage	()Ljava/lang/String;
    //   92: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   98: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   101: goto -73 -> 28
    //   104: astore_1
    //   105: aload_0
    //   106: monitorexit
    //   107: aload_1
    //   108: athrow
    //   109: astore 4
    //   111: ldc 117
    //   113: new 129	java/lang/StringBuilder
    //   116: dup
    //   117: ldc_w 303
    //   120: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   123: aload 4
    //   125: invokevirtual 169	java/io/IOException:getMessage	()Ljava/lang/String;
    //   128: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   137: goto -95 -> 42
    //   140: astore 8
    //   142: ldc 117
    //   144: new 129	java/lang/StringBuilder
    //   147: dup
    //   148: ldc_w 305
    //   151: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   154: aload 8
    //   156: invokevirtual 210	java/lang/IllegalStateException:getMessage	()Ljava/lang/String;
    //   159: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   165: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   168: goto -96 -> 72
    //   171: astore 7
    //   173: ldc 117
    //   175: new 129	java/lang/StringBuilder
    //   178: dup
    //   179: ldc_w 307
    //   182: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   185: aload 7
    //   187: invokevirtual 169	java/io/IOException:getMessage	()Ljava/lang/String;
    //   190: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   193: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   196: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   199: goto -127 -> 72
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	202	0	this	SyncDeltaManager
    //   104	4	1	localObject	Object
    //   9	2	2	bool	boolean
    //   75	14	3	localIOException1	IOException
    //   109	15	4	localIOException2	IOException
    //   49	14	5	localModelSetDescription1	ModelSetDescription
    //   58	3	6	localModelSetDescription2	ModelSetDescription
    //   171	15	7	localIOException3	IOException
    //   140	15	8	localIllegalStateException	IllegalStateException
    // Exception table:
    //   from	to	target	type
    //   14	28	75	java/io/IOException
    //   2	10	104	finally
    //   14	28	104	finally
    //   28	42	104	finally
    //   42	60	104	finally
    //   60	72	104	finally
    //   76	101	104	finally
    //   111	137	104	finally
    //   142	168	104	finally
    //   173	199	104	finally
    //   28	42	109	java/io/IOException
    //   60	72	140	java/lang/IllegalStateException
    //   60	72	171	java/io/IOException
  }
  
  public Set<String> getBlackListWords()
  {
    try
    {
      File localFile = new File(this.syncFilesDirectory, "stop_words.json");
      try
      {
        BlackList localBlackList2 = getBlackListFromFile(localFile);
        localBlackList1 = localBlackList2;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          Set localSet;
          BlackList localBlackList1 = new BlackList();
          LogUtil.w("SyncDeltaManager", "Error while reading blacklist file:" + localIOException.getMessage());
        }
      }
      localSet = Collections.unmodifiableSet(localBlackList1.stopWords);
      return localSet;
    }
    finally {}
  }
  
  public void mergeServerDelta(final File paramFile, final Collection<String> paramCollection, final CompletionListener paramCompletionListener)
  {
    try
    {
      this.fluencyServiceProxy.runWhenConnected(new Runnable()
      {
        public void run()
        {
          Predictor localPredictor = SyncDeltaManager.this.fluencyServiceProxy.getPredictor();
          if (localPredictor != null) {
            try
            {
              localPredictor.mergeUserModel(paramFile.getAbsolutePath());
              if (paramCollection != null) {
                localPredictor.removeTerms(paramCollection);
              }
              paramCompletionListener.onComplete(true, true);
              return;
            }
            catch (PredictorNotReadyException localPredictorNotReadyException)
            {
              LogUtil.e("SyncDeltaManager", "Unable to merge in pulled sync delta as Predictor not ready");
              paramCompletionListener.onComplete(false, true);
              return;
            }
          }
          LogUtil.w("SyncDeltaManager", "Unable to merge in server sync delta as the Predictor is null");
        }
      });
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public boolean prepareDeltaForPush()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 33	com/touchtype/cloud/sync/SyncDeltaManager:keyboardDeltaModelDirectory	Ljava/io/File;
    //   6: invokevirtual 92	java/io/File:exists	()Z
    //   9: ifeq +229 -> 238
    //   12: aload_0
    //   13: getfield 27	com/touchtype/cloud/sync/SyncDeltaManager:fluencyServiceProxy	Lcom/touchtype_fluency/service/FluencyServiceProxy;
    //   16: invokevirtual 331	com/touchtype_fluency/service/FluencyServiceProxy:isReady	()Z
    //   19: istore_3
    //   20: iconst_0
    //   21: istore_2
    //   22: iload_3
    //   23: ifeq +78 -> 101
    //   26: aload_0
    //   27: getfield 33	com/touchtype/cloud/sync/SyncDeltaManager:keyboardDeltaModelDirectory	Ljava/io/File;
    //   30: invokestatic 187	com/touchtype/cloud/sync/SyncDeltaManager:createModelSetDescription	(Ljava/io/File;)Lcom/touchtype_fluency/ModelSetDescription;
    //   33: astore 4
    //   35: aload_0
    //   36: getfield 35	com/touchtype/cloud/sync/SyncDeltaManager:pushDeltaModelDirectory	Ljava/io/File;
    //   39: invokestatic 187	com/touchtype/cloud/sync/SyncDeltaManager:createModelSetDescription	(Ljava/io/File;)Lcom/touchtype_fluency/ModelSetDescription;
    //   42: astore 5
    //   44: aload_0
    //   45: getfield 27	com/touchtype/cloud/sync/SyncDeltaManager:fluencyServiceProxy	Lcom/touchtype_fluency/service/FluencyServiceProxy;
    //   48: invokevirtual 148	com/touchtype_fluency/service/FluencyServiceProxy:getSession	()Lcom/touchtype_fluency/Session;
    //   51: aload_0
    //   52: getfield 35	com/touchtype/cloud/sync/SyncDeltaManager:pushDeltaModelDirectory	Ljava/io/File;
    //   55: invokestatic 199	com/touchtype/cloud/sync/SyncDeltaManager:ensureModelExists	(Lcom/touchtype_fluency/Session;Ljava/io/File;)V
    //   58: aload_0
    //   59: getfield 27	com/touchtype/cloud/sync/SyncDeltaManager:fluencyServiceProxy	Lcom/touchtype_fluency/service/FluencyServiceProxy;
    //   62: invokevirtual 148	com/touchtype_fluency/service/FluencyServiceProxy:getSession	()Lcom/touchtype_fluency/Session;
    //   65: aload_0
    //   66: getfield 33	com/touchtype/cloud/sync/SyncDeltaManager:keyboardDeltaModelDirectory	Ljava/io/File;
    //   69: invokestatic 199	com/touchtype/cloud/sync/SyncDeltaManager:ensureModelExists	(Lcom/touchtype_fluency/Session;Ljava/io/File;)V
    //   72: aload 4
    //   74: aload 5
    //   76: aload_0
    //   77: getfield 35	com/touchtype/cloud/sync/SyncDeltaManager:pushDeltaModelDirectory	Ljava/io/File;
    //   80: invokestatic 299	com/touchtype/cloud/sync/SyncDeltaManager:mergeModelSetDescription	(Lcom/touchtype_fluency/ModelSetDescription;Lcom/touchtype_fluency/ModelSetDescription;Ljava/io/File;)Lcom/touchtype_fluency/ModelSetDescription;
    //   83: astore 10
    //   85: iconst_0
    //   86: istore_2
    //   87: aload 10
    //   89: ifnull +12 -> 101
    //   92: aload_0
    //   93: getfield 33	com/touchtype/cloud/sync/SyncDeltaManager:keyboardDeltaModelDirectory	Ljava/io/File;
    //   96: invokestatic 281	com/touchtype/common/util/FileUtils:deleteRecursively	(Ljava/io/File;)V
    //   99: iconst_1
    //   100: istore_2
    //   101: aload_0
    //   102: monitorexit
    //   103: iload_2
    //   104: ireturn
    //   105: astore 6
    //   107: ldc 117
    //   109: new 129	java/lang/StringBuilder
    //   112: dup
    //   113: ldc_w 333
    //   116: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   119: aload 6
    //   121: invokevirtual 169	java/io/IOException:getMessage	()Ljava/lang/String;
    //   124: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   130: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   133: goto -75 -> 58
    //   136: astore_1
    //   137: aload_0
    //   138: monitorexit
    //   139: aload_1
    //   140: athrow
    //   141: astore 7
    //   143: ldc 117
    //   145: new 129	java/lang/StringBuilder
    //   148: dup
    //   149: ldc_w 335
    //   152: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   155: aload 7
    //   157: invokevirtual 169	java/io/IOException:getMessage	()Ljava/lang/String;
    //   160: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   169: goto -97 -> 72
    //   172: astore 9
    //   174: ldc 117
    //   176: new 129	java/lang/StringBuilder
    //   179: dup
    //   180: ldc_w 337
    //   183: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   186: aload 9
    //   188: invokevirtual 210	java/lang/IllegalStateException:getMessage	()Ljava/lang/String;
    //   191: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   197: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   200: iconst_0
    //   201: istore_2
    //   202: goto -101 -> 101
    //   205: astore 8
    //   207: ldc 117
    //   209: new 129	java/lang/StringBuilder
    //   212: dup
    //   213: ldc_w 339
    //   216: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   219: aload 8
    //   221: invokevirtual 169	java/io/IOException:getMessage	()Ljava/lang/String;
    //   224: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   230: invokestatic 172	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   233: iconst_0
    //   234: istore_2
    //   235: goto -134 -> 101
    //   238: iconst_1
    //   239: istore_2
    //   240: goto -139 -> 101
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	243	0	this	SyncDeltaManager
    //   136	4	1	localObject	Object
    //   21	219	2	bool1	boolean
    //   19	4	3	bool2	boolean
    //   33	40	4	localModelSetDescription1	ModelSetDescription
    //   42	33	5	localModelSetDescription2	ModelSetDescription
    //   105	15	6	localIOException1	IOException
    //   141	15	7	localIOException2	IOException
    //   205	15	8	localIOException3	IOException
    //   172	15	9	localIllegalStateException	IllegalStateException
    //   83	5	10	localModelSetDescription3	ModelSetDescription
    // Exception table:
    //   from	to	target	type
    //   44	58	105	java/io/IOException
    //   2	20	136	finally
    //   26	44	136	finally
    //   44	58	136	finally
    //   58	72	136	finally
    //   72	85	136	finally
    //   92	99	136	finally
    //   107	133	136	finally
    //   143	169	136	finally
    //   174	200	136	finally
    //   207	233	136	finally
    //   58	72	141	java/io/IOException
    //   72	85	172	java/lang/IllegalStateException
    //   92	99	172	java/lang/IllegalStateException
    //   72	85	205	java/io/IOException
    //   92	99	205	java/io/IOException
  }
  
  class BlackList
  {
    Set<String> stopWords = new HashSet();
    
    BlackList() {}
  }
  
  private static abstract interface Learner
  {
    public abstract void learn(FluencyServiceProxy paramFluencyServiceProxy, TagSelector paramTagSelector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.sync.SyncDeltaManager
 * JD-Core Version:    0.7.0.1
 */