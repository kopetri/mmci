package com.touchtype.backup;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public final class PreferencesBackupHelper
  extends FilesBackupHelper
{
  private final Context context;
  
  public PreferencesBackupHelper(Context paramContext)
  {
    this.context = paramContext;
  }
  
  private void setRestoreFlag()
  {
    PreferenceManager.getDefaultSharedPreferences(this.context).edit().putBoolean("restored_from_backup", true).commit();
  }
  
  protected LinkedHashMap<String, File> getBackupFiles()
  {
    File localFile = this.context.getFilesDir().getParentFile();
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    localLinkedHashMap.put("shared", new File(localFile, "shared_prefs/" + this.context.getPackageName() + "_preferences.xml"));
    localLinkedHashMap.put("events", new File(localFile, "shared_prefs/events.xml"));
    localLinkedHashMap.put("stats_settings_opens", new File(localFile, "shared_prefs/stats_settings_opens.xml"));
    localLinkedHashMap.put("personalizer", new File(localFile, "shared_prefs/personalizer_service.xml"));
    return localLinkedHashMap;
  }
  
  public void restoreData(byte[] paramArrayOfByte)
    throws IOException
  {
    super.restoreData(paramArrayOfByte);
    setRestoreFlag();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.PreferencesBackupHelper
 * JD-Core Version:    0.7.0.1
 */