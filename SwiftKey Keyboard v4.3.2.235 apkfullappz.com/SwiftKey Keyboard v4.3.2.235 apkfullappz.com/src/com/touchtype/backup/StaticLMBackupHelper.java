package com.touchtype.backup;

import android.content.Context;
import com.touchtype_fluency.service.FluencyService;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

public final class StaticLMBackupHelper
  extends FilesBackupHelper
{
  private final Context context;
  
  public StaticLMBackupHelper(Context paramContext)
  {
    this.context = paramContext;
  }
  
  private void addFile(File paramFile, String paramString1, String paramString2, LinkedHashMap<String, File> paramLinkedHashMap)
  {
    File localFile = new File(paramFile, paramString1);
    paramLinkedHashMap.put(paramString2 + " " + paramString1, localFile);
  }
  
  public LinkedHashMap<String, File> getBackupFiles()
    throws IOException
  {
    LanguagePackManager localLanguagePackManager = BackupUtil.enableFluencyService(this.context).getLanguagePackManager();
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    Iterator localIterator1 = localLanguagePackManager.getEnabledLanguagePacks().iterator();
    while (localIterator1.hasNext())
    {
      LanguagePack localLanguagePack = (LanguagePack)localIterator1.next();
      if (!localLanguagePack.isBroken())
      {
        String str = localLanguagePack.getID();
        File localFile = localLanguagePack.getDirectory();
        Iterator localIterator2 = localLanguagePack.getFiles().iterator();
        while (localIterator2.hasNext()) {
          addFile(localFile, (String)localIterator2.next(), str, localLinkedHashMap);
        }
        addFile(localFile, ".config", str, localLinkedHashMap);
        addFile(localFile, "extraData.json", str, localLinkedHashMap);
      }
    }
    return localLinkedHashMap;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.StaticLMBackupHelper
 * JD-Core Version:    0.7.0.1
 */