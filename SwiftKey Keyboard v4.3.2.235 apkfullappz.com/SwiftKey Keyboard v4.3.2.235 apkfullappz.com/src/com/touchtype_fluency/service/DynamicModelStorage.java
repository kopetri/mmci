package com.touchtype_fluency.service;

import android.content.Context;
import com.touchtype.storage.FolderDecorator;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.AndroidLanguagePackModelStorage;
import java.io.File;

public class DynamicModelStorage
{
  public static final int DYNAMIC_LM_ORDER = 4;
  public static final String USER_LM_CONFIG_FILE = USER_LM_FOLDER + ".config";
  public static final String USER_LM_FILE;
  public static final String USER_LM_FOLDER = "user" + File.separator;
  public static final String USER_LM_LEARNED_PARAMS_FILE = USER_LM_FOLDER + "learned.json";
  private final Context context;
  
  static
  {
    USER_LM_FILE = USER_LM_FOLDER + "dynamic.lm";
  }
  
  public DynamicModelStorage(Context paramContext)
  {
    this.context = paramContext;
  }
  
  public File getDynamicModelStorageLocation()
  {
    return AndroidLanguagePackModelStorage.getInstance(this.context).getDynamicModelDirectory().getBaseFolder();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.DynamicModelStorage
 * JD-Core Version:    0.7.0.1
 */