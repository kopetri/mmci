package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import com.touchtype.storage.FolderDecorator;
import java.io.IOException;

public abstract interface LanguagePackModelStorage
{
  public abstract boolean areAllModelsAvailable();
  
  public abstract FolderDecorator getDynamicModelDirectory();
  
  public abstract FolderDecorator getPreinstallDirectory();
  
  public abstract FolderDecorator getStaticModelDirectory();
  
  public abstract boolean isConfigurationAvailable(String paramString);
  
  public abstract String loadConfiguration(String paramString)
    throws IOException;
  
  public abstract void saveConfiguration(String paramString1, String paramString2)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackModelStorage
 * JD-Core Version:    0.7.0.1
 */