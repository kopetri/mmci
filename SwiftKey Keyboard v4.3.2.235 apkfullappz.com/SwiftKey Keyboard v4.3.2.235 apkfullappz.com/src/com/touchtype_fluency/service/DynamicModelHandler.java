package com.touchtype_fluency.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Environment;
import android.preference.PreferenceManager;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.storage.FolderDecorator;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.ModelSetDescription;
import com.touchtype_fluency.ModelSetDescription.Type;
import com.touchtype_fluency.Session;
import com.touchtype_fluency.internal.ModelMerger;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.AndroidLanguagePackModelStorage;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackModelStorage;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class DynamicModelHandler
{
  private static final String PREF_USER_MODEL_WAS_MIGRATED = "user_model_migrated";
  private static final String TAG = DynamicModelHandler.class.getSimpleName();
  private final Context context;
  private final LanguagePackModelStorage languagePackModelStorage;
  private final TouchTypePreferences preferences;
  private final SharedPreferences sharedPreferences;
  private final TouchTypeStats stats;
  
  public DynamicModelHandler(Context paramContext)
  {
    this.context = paramContext;
    this.preferences = TouchTypePreferences.getInstance(paramContext);
    this.stats = this.preferences.getTouchTypeStats();
    this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.languagePackModelStorage = AndroidLanguagePackModelStorage.getInstance(paramContext.getApplicationContext());
  }
  
  private void createAndImportModelsIfNecessary()
    throws IOException
  {
    if (!getUserModelDirectory().exists())
    {
      FileUtils.forceMkdir(getUserModelDirectory());
      if (!this.context.getString(2131296302).contains("[Babel]")) {}
    }
    else
    {
      return;
    }
    boolean bool = false;
    String[] arrayOfString = this.context.getResources().getStringArray(2131623937);
    int i = arrayOfString.length;
    for (int j = 0;; j++) {
      if (j < i)
      {
        bool = importUserModelFiles(arrayOfString[j]);
        if (bool) {
          setPreference("user_model_migrated", true);
        }
      }
      else
      {
        if (bool) {
          break;
        }
        importLegacyModelFile();
        return;
      }
    }
  }
  
  private void deleteAndRecreateCorruptDynamicModel(Session paramSession, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper)
    throws IOException
  {
    LogUtil.w(TAG, "deleting corrupt dynamic model");
    deleteUserModel(paramSession, paramModelSetDescriptionWrapper, false);
    this.stats.incrementStatistic("stats_corrupt_dynamic_model_deletions");
    try
    {
      paramSession.load(getUserModel(paramModelSetDescriptionWrapper));
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      LogUtil.w(TAG, "Attempted to load character maps multiple times");
    }
  }
  
  private File getExistingFile(String paramString1, String paramString2)
  {
    return new File(Environment.getExternalStorageDirectory(), "Android/data/" + paramString1 + "/files/" + paramString2);
  }
  
  private File getLegacyUserModelFile()
  {
    return new File(Environment.getExternalStorageDirectory(), "/swiftkey/dyn.lm4");
  }
  
  private ModelSetDescription getUserModel(ModelSetDescriptionWrapper paramModelSetDescriptionWrapper)
    throws IOException
  {
    return paramModelSetDescriptionWrapper.dynamicWithFile(getUserModelDirectory().getAbsolutePath(), 4, PredictorImpl.DYNAMIC_LEARNING_TAGS, ModelSetDescription.Type.PRIMARY_DYNAMIC_MODEL);
  }
  
  private File getUserModelDirectory()
    throws IOException
  {
    return new File(this.languagePackModelStorage.getDynamicModelDirectory().getBaseFolder(), DynamicModelStorage.USER_LM_FOLDER);
  }
  
  private boolean importLegacyModelFile()
  {
    try
    {
      FileUtils.copyFile(getLegacyUserModelFile(), getUserModelFile());
      return true;
    }
    catch (IOException localIOException) {}
    return false;
  }
  
  /* Error */
  private boolean importUserModelFiles(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: getstatic 237	com/touchtype_fluency/service/DynamicModelStorage:USER_LM_FILE	Ljava/lang/String;
    //   5: invokespecial 239	com/touchtype_fluency/service/DynamicModelHandler:getExistingFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   8: aload_0
    //   9: invokevirtual 230	com/touchtype_fluency/service/DynamicModelHandler:getUserModelFile	()Ljava/io/File;
    //   12: invokestatic 234	org/apache/commons/io/FileUtils:copyFile	(Ljava/io/File;Ljava/io/File;)V
    //   15: aload_0
    //   16: aload_1
    //   17: getstatic 242	com/touchtype_fluency/service/DynamicModelStorage:USER_LM_CONFIG_FILE	Ljava/lang/String;
    //   20: invokespecial 239	com/touchtype_fluency/service/DynamicModelHandler:getExistingFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   23: aload_0
    //   24: invokevirtual 245	com/touchtype_fluency/service/DynamicModelHandler:getUserConfigFile	()Ljava/io/File;
    //   27: invokestatic 234	org/apache/commons/io/FileUtils:copyFile	(Ljava/io/File;Ljava/io/File;)V
    //   30: aload_0
    //   31: aload_1
    //   32: getstatic 248	com/touchtype_fluency/service/DynamicModelStorage:USER_LM_LEARNED_PARAMS_FILE	Ljava/lang/String;
    //   35: invokespecial 239	com/touchtype_fluency/service/DynamicModelHandler:getExistingFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   38: aload_0
    //   39: invokevirtual 251	com/touchtype_fluency/service/DynamicModelHandler:getUserLearnedParamsFile	()Ljava/io/File;
    //   42: invokestatic 234	org/apache/commons/io/FileUtils:copyFile	(Ljava/io/File;Ljava/io/File;)V
    //   45: iconst_1
    //   46: ireturn
    //   47: astore_2
    //   48: iconst_0
    //   49: ireturn
    //   50: astore_3
    //   51: goto -6 -> 45
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	this	DynamicModelHandler
    //   0	54	1	paramString	String
    //   47	1	2	localIOException1	IOException
    //   50	1	3	localIOException2	IOException
    // Exception table:
    //   from	to	target	type
    //   0	30	47	java/io/IOException
    //   30	45	50	java/io/IOException
  }
  
  private void setPreference(String paramString, boolean paramBoolean)
  {
    synchronized (this.sharedPreferences.edit())
    {
      ???.putBoolean(paramString, Boolean.valueOf(paramBoolean).booleanValue());
      ???.commit();
      return;
    }
  }
  
  public void deleteUserModel(Session paramSession, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper)
    throws IOException
  {
    deleteUserModel(paramSession, paramModelSetDescriptionWrapper, true);
  }
  
  public void deleteUserModel(Session paramSession, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper, boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean) {}
    try
    {
      paramSession.unload(getUserModel(paramModelSetDescriptionWrapper));
      getUserModelFile().delete();
      getUserConfigFile().delete();
      getUserLearnedParamsFile().delete();
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e(TAG, localIOException.getMessage(), localIOException);
      }
    }
  }
  
  public File getUserConfigFile()
    throws IOException
  {
    return new File(this.languagePackModelStorage.getDynamicModelDirectory().getBaseFolder(), DynamicModelStorage.USER_LM_CONFIG_FILE);
  }
  
  public File getUserLearnedParamsFile()
  {
    return new File(this.languagePackModelStorage.getDynamicModelDirectory().getBaseFolder(), DynamicModelStorage.USER_LM_LEARNED_PARAMS_FILE);
  }
  
  public File getUserModelFile()
    throws IOException
  {
    return new File(this.languagePackModelStorage.getDynamicModelDirectory().getBaseFolder(), DynamicModelStorage.USER_LM_FILE);
  }
  
  public long getUserModelLastModified()
  {
    try
    {
      long l = getUserModelFile().lastModified();
      return l;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, "User model not available");
    }
    return -1L;
  }
  
  public long getUserModelLength()
  {
    try
    {
      long l = getUserModelFile().length();
      return l;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, "User model not available");
    }
    return -1L;
  }
  
  public void loadUserModel(Session paramSession, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper)
    throws IOException, StorageNotAvailableException
  {
    if (!this.languagePackModelStorage.getDynamicModelDirectory().isAvailable()) {
      throw new StorageNotAvailableException();
    }
    createAndImportModelsIfNecessary();
    try
    {
      paramSession.load(getUserModel(paramModelSetDescriptionWrapper));
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      LogUtil.w(TAG, "Attempted to load character maps multiple times");
      return;
    }
    catch (IOException localIOException)
    {
      if (this.context.getResources().getBoolean(2131492939)) {
        throw new DynamicModelCorruptedException(localIOException);
      }
      deleteAndRecreateCorruptDynamicModel(paramSession, paramModelSetDescriptionWrapper);
    }
  }
  
  public void mergeUserModel(Session paramSession, ModelSetDescriptionWrapper paramModelSetDescriptionWrapper, String paramString)
    throws IOException, StorageNotAvailableException
  {
    if (!this.languagePackModelStorage.getDynamicModelDirectory().isAvailable()) {
      throw new StorageNotAvailableException();
    }
    createAndImportModelsIfNecessary();
    try
    {
      paramSession.unload(getUserModel(paramModelSetDescriptionWrapper));
      ModelMerger.mergeModels(getUserModelFile().getAbsolutePath(), getUserModelFile().getAbsolutePath(), paramString);
      loadUserModel(paramSession, paramModelSetDescriptionWrapper);
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e(TAG, localIOException.getMessage(), localIOException);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.DynamicModelHandler
 * JD-Core Version:    0.7.0.1
 */