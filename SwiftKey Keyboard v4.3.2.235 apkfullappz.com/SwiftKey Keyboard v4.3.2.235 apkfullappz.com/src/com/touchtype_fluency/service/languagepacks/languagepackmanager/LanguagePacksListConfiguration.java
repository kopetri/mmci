package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import android.content.Context;
import com.touchtype.storage.FolderDecorator;
import java.io.IOException;

public class LanguagePacksListConfiguration
{
  private final Context context;
  
  public LanguagePacksListConfiguration(Context paramContext)
  {
    this.context = paramContext;
  }
  
  public boolean isAvailable()
  {
    return AndroidLanguagePackModelStorage.getInstance(this.context).getStaticModelDirectory().isAvailable();
  }
  
  public String loadConfiguration()
    throws IOException
  {
    return AndroidLanguagePackModelStorage.getInstance(this.context).loadConfiguration("languagePacks.json");
  }
  
  public void saveConfiguration(String paramString)
    throws IOException
  {
    AndroidLanguagePackModelStorage.getInstance(this.context).saveConfiguration(paramString, "languagePacks.json");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePacksListConfiguration
 * JD-Core Version:    0.7.0.1
 */