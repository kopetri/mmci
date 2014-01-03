package com.touchtype.backup;

import android.app.backup.BackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.ParcelFileDescriptor;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePacksListConfiguration;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public class SwiftkeyBackupAgent
  extends BackupAgent
{
  public static final String TAG = SwiftkeyBackupAgent.class.getSimpleName();
  private final LinkedHashMap<String, BackupHelper> backupHelpers = new LinkedHashMap();
  private LanguagePacksListConfiguration languagePacksConfiguration;
  private final SDCardLock sdCardLock = new SDCardLock();
  
  private boolean isCloudAccountEnabled()
  {
    return TouchTypePreferences.getInstance(getApplicationContext()).isCloudAccountSetup();
  }
  
  private void onBackup(BackupDataOutput paramBackupDataOutput, String paramString, BackupHelper paramBackupHelper)
    throws IOException
  {
    List localList = paramBackupHelper.backupData();
    try
    {
      Iterator localIterator2 = localList.iterator();
      while (localIterator2.hasNext())
      {
        File localFile = (File)localIterator2.next();
        byte[] arrayOfByte = BackupUtil.readFileContent(localFile);
        String str = BackupUtil.getSubKeyName(localFile);
        writeEntity(paramBackupDataOutput, paramString + str, arrayOfByte);
      }
      Iterator localIterator1;
      Iterator localIterator3;
      return;
    }
    finally
    {
      localIterator1 = localList.iterator();
      while (localIterator1.hasNext()) {
        FileUtils.deleteQuietly((File)localIterator1.next());
      }
      localIterator3 = localList.iterator();
      while (localIterator3.hasNext()) {
        FileUtils.deleteQuietly((File)localIterator3.next());
      }
    }
  }
  
  /* Error */
  private Map<String, Long> readOldState(ParcelFileDescriptor paramParcelFileDescriptor)
    throws IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +11 -> 12
    //   4: new 118	java/util/HashMap
    //   7: dup
    //   8: invokespecial 119	java/util/HashMap:<init>	()V
    //   11: areturn
    //   12: aconst_null
    //   13: astore_2
    //   14: new 121	java/io/ObjectInputStream
    //   17: dup
    //   18: new 123	java/io/FileInputStream
    //   21: dup
    //   22: aload_1
    //   23: invokevirtual 129	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   26: invokespecial 132	java/io/FileInputStream:<init>	(Ljava/io/FileDescriptor;)V
    //   29: invokespecial 135	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   32: astore_3
    //   33: aload_3
    //   34: invokevirtual 138	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   37: checkcast 140	java/util/Map
    //   40: astore 9
    //   42: aload_3
    //   43: invokestatic 146	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   46: aload 9
    //   48: areturn
    //   49: astore 11
    //   51: new 118	java/util/HashMap
    //   54: dup
    //   55: invokespecial 119	java/util/HashMap:<init>	()V
    //   58: astore 5
    //   60: aload_2
    //   61: invokestatic 146	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   64: aload 5
    //   66: areturn
    //   67: astore 10
    //   69: new 118	java/util/HashMap
    //   72: dup
    //   73: invokespecial 119	java/util/HashMap:<init>	()V
    //   76: astore 8
    //   78: aload_2
    //   79: invokestatic 146	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   82: aload 8
    //   84: areturn
    //   85: astore 6
    //   87: aload_2
    //   88: invokestatic 146	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   91: aload 6
    //   93: athrow
    //   94: astore 6
    //   96: aload_3
    //   97: astore_2
    //   98: goto -11 -> 87
    //   101: astore 7
    //   103: aload_3
    //   104: astore_2
    //   105: goto -36 -> 69
    //   108: astore 4
    //   110: aload_3
    //   111: astore_2
    //   112: goto -61 -> 51
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	115	0	this	SwiftkeyBackupAgent
    //   0	115	1	paramParcelFileDescriptor	ParcelFileDescriptor
    //   13	99	2	localObject1	Object
    //   32	79	3	localObjectInputStream	java.io.ObjectInputStream
    //   108	1	4	localClassNotFoundException1	java.lang.ClassNotFoundException
    //   58	7	5	localHashMap1	HashMap
    //   85	7	6	localObject2	Object
    //   94	1	6	localObject3	Object
    //   101	1	7	localEOFException1	java.io.EOFException
    //   76	7	8	localHashMap2	HashMap
    //   40	7	9	localMap	Map
    //   67	1	10	localEOFException2	java.io.EOFException
    //   49	1	11	localClassNotFoundException2	java.lang.ClassNotFoundException
    // Exception table:
    //   from	to	target	type
    //   14	33	49	java/lang/ClassNotFoundException
    //   14	33	67	java/io/EOFException
    //   14	33	85	finally
    //   51	60	85	finally
    //   69	78	85	finally
    //   33	42	94	finally
    //   33	42	101	java/io/EOFException
    //   33	42	108	java/lang/ClassNotFoundException
  }
  
  private void reportSuccessfulRestore()
  {
    TouchTypePreferences.getInstance(getApplicationContext()).getTouchTypeStats().incrementStatistic("stats_successful_restores");
  }
  
  private void restoreEntity(String paramString, byte[] paramArrayOfByte)
    throws IOException
  {
    Iterator localIterator = this.backupHelpers.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      BackupHelper localBackupHelper = (BackupHelper)localEntry.getValue();
      if ((paramString.startsWith(str)) && ((!str.equals("DYNAMIC")) || (!isCloudAccountEnabled())) && ((!str.equals("STATIC")) || (!isCloudAccountEnabled()))) {
        localBackupHelper.restoreData(paramArrayOfByte);
      }
    }
  }
  
  private boolean sameAppVersion(int paramInt)
  {
    try
    {
      int i = getAppVersion();
      boolean bool = false;
      if (paramInt == i) {
        bool = true;
      }
      return bool;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return false;
  }
  
  private boolean stateChanged(Map<String, Long> paramMap)
    throws IOException
  {
    Iterator localIterator = this.backupHelpers.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      BackupHelper localBackupHelper = (BackupHelper)localEntry.getValue();
      Long localLong = (Long)paramMap.get(str);
      long l = localBackupHelper.getState();
      if ((localLong == null) || (l != localLong.longValue())) {
        return true;
      }
    }
    return false;
  }
  
  private void updateBackupHelpers()
  {
    if (isCloudAccountEnabled())
    {
      this.backupHelpers.remove("DYNAMIC");
      this.backupHelpers.remove("STATIC");
    }
    do
    {
      return;
      if (!this.backupHelpers.containsKey("DYNAMIC")) {
        this.backupHelpers.put("DYNAMIC", new DynamicLMBackupHelper(getApplicationContext()));
      }
    } while (this.backupHelpers.containsKey("STATIC"));
    this.backupHelpers.put("STATIC", new StaticLMBackupHelper(getApplicationContext()));
  }
  
  private void writeNewState(ParcelFileDescriptor paramParcelFileDescriptor)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.backupHelpers.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localHashMap.put((String)localEntry.getKey(), Long.valueOf(((BackupHelper)localEntry.getValue()).getState()));
    }
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(new FileOutputStream(paramParcelFileDescriptor.getFileDescriptor()));
    localObjectOutputStream.writeObject(localHashMap);
    localObjectOutputStream.close();
  }
  
  int getAppVersion()
    throws PackageManager.NameNotFoundException
  {
    return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
  }
  
  boolean isBackupEnabled(Context paramContext)
  {
    return BackupUtil.isBackupEnabled(paramContext);
  }
  
  public void onBackup(ParcelFileDescriptor paramParcelFileDescriptor1, BackupDataOutput paramBackupDataOutput, ParcelFileDescriptor paramParcelFileDescriptor2)
    throws IOException
  {
    if (isBackupEnabled(this))
    {
      if (BackupUtil.installerCompleted(this))
      {
        this.sdCardLock.lock();
        try
        {
          if (this.languagePacksConfiguration.isAvailable())
          {
            if (stateChanged(readOldState(paramParcelFileDescriptor1)))
            {
              updateBackupHelpers();
              Iterator localIterator = this.backupHelpers.entrySet().iterator();
              while (localIterator.hasNext())
              {
                Map.Entry localEntry = (Map.Entry)localIterator.next();
                onBackup(paramBackupDataOutput, (String)localEntry.getKey(), (BackupHelper)localEntry.getValue());
              }
            }
            writeNewState(paramParcelFileDescriptor2);
          }
        }
        finally
        {
          this.sdCardLock.unlock();
        }
        for (;;)
        {
          this.sdCardLock.unlock();
          return;
          LogUtil.w(TAG, "Storage not available");
        }
      }
      LogUtil.w(TAG, "Install or restore is not complete");
      return;
    }
    LogUtil.w(TAG, "Backup is disabled");
  }
  
  public void onCreate()
  {
    super.onCreate();
    Context localContext = getApplicationContext();
    this.languagePacksConfiguration = new LanguagePacksListConfiguration(localContext);
    if (!isCloudAccountEnabled())
    {
      this.backupHelpers.put("DYNAMIC", new DynamicLMBackupHelper(localContext));
      this.backupHelpers.put("STATIC", new StaticLMBackupHelper(localContext));
    }
    this.backupHelpers.put("CONFIG", new ConfigBackupHelper(this.languagePacksConfiguration));
    this.backupHelpers.put("PREFERENCES", new PreferencesBackupHelper(localContext));
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.backupHelpers.clear();
  }
  
  public void onRestore(BackupDataInput paramBackupDataInput, int paramInt, ParcelFileDescriptor paramParcelFileDescriptor)
    throws IOException
  {
    if (BackupUtil.isBackupEnabled(this))
    {
      this.sdCardLock.lock();
      try
      {
        if (this.languagePacksConfiguration.isAvailable()) {
          if (sameAppVersion(paramInt))
          {
            while (paramBackupDataInput.readNextHeader())
            {
              String str = paramBackupDataInput.getKey();
              int i = paramBackupDataInput.getDataSize();
              byte[] arrayOfByte = new byte[i];
              paramBackupDataInput.readEntityData(arrayOfByte, 0, i);
              restoreEntity(str, arrayOfByte);
            }
            writeNewState(paramParcelFileDescriptor);
          }
        }
      }
      finally
      {
        this.sdCardLock.unlock();
      }
      for (;;)
      {
        this.sdCardLock.unlock();
        reportSuccessfulRestore();
        return;
        LogUtil.w(TAG, "Data version and app version are different");
        continue;
        LogUtil.w(TAG, "Storage not available");
      }
    }
    LogUtil.w(TAG, "Backup is disabled");
  }
  
  void writeEntity(BackupDataOutput paramBackupDataOutput, String paramString, byte[] paramArrayOfByte)
    throws IOException
  {
    paramBackupDataOutput.writeEntityHeader(paramString, paramArrayOfByte.length);
    paramBackupDataOutput.writeEntityData(paramArrayOfByte, paramArrayOfByte.length);
  }
  
  public static abstract interface BackupHelper
  {
    public abstract List<File> backupData()
      throws IOException;
    
    public abstract long getState()
      throws IOException;
    
    public abstract void restoreData(byte[] paramArrayOfByte)
      throws IOException;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.SwiftkeyBackupAgent
 * JD-Core Version:    0.7.0.1
 */