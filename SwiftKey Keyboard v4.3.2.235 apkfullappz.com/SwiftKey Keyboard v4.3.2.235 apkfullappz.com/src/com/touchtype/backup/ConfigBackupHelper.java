package com.touchtype.backup;

import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePacksListConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ConfigBackupHelper
  extends CachedStateBackupHelper
{
  private static final String TAG = ConfigBackupHelper.class.getSimpleName();
  private final LanguagePacksListConfiguration languagePacksConfiguration;
  
  public ConfigBackupHelper(LanguagePacksListConfiguration paramLanguagePacksListConfiguration)
  {
    this.languagePacksConfiguration = paramLanguagePacksListConfiguration;
  }
  
  protected List<File> backupDataInternal()
    throws IOException
  {
    try
    {
      List localList = Collections.singletonList(BackupUtil.writeFileContent(this.languagePacksConfiguration.loadConfiguration().getBytes("UTF-8")));
      return localList;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, localIOException.getMessage(), localIOException);
    }
    return Collections.emptyList();
  }
  
  protected long getStateInternal()
    throws IOException
  {
    try
    {
      int i = this.languagePacksConfiguration.loadConfiguration().hashCode();
      return i;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, localIOException.getMessage(), localIOException);
    }
    return 0L;
  }
  
  protected long restoreDataInternal(byte[] paramArrayOfByte)
    throws IOException
  {
    String str = new String(paramArrayOfByte, "UTF-8");
    this.languagePacksConfiguration.saveConfiguration(str);
    return str.hashCode();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.ConfigBackupHelper
 * JD-Core Version:    0.7.0.1
 */