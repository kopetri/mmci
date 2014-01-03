package com.touchtype.backup;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import com.google.common.io.Files;
import com.touchtype.installer.InstallerPreferences;
import com.touchtype.util.EnvironmentInfoUtil;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.FluencyService;
import com.touchtype_fluency.service.FluencyServiceProxy;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

final class BackupUtil
{
  private static final String TAG = BackupUtil.class.getSimpleName();
  
  public static File backupSingleFile(String paramString, File paramFile)
    throws IOException
  {
    if (!paramFile.exists()) {
      return null;
    }
    long l = paramFile.length();
    if (l > 5000000L) {
      throw new IOException("Size of file " + paramFile.getAbsolutePath() + " exceeds allowed limit of 5000000");
    }
    File localFile = createTempFile(paramString);
    RandomAccessFile localRandomAccessFile = new RandomAccessFile(localFile, "rw");
    String str = paramFile.getAbsolutePath();
    localRandomAccessFile.writeInt(str.length());
    localRandomAccessFile.writeBytes(str);
    localRandomAccessFile.writeInt((int)l);
    writeFileDataToRAF(paramFile, localRandomAccessFile);
    localRandomAccessFile.close();
    return localFile;
  }
  
  private static File createTempFile(String paramString)
    throws IOException
  {
    return File.createTempFile(paramString + " - ", null);
  }
  
  public static FluencyService enableFluencyService(Context paramContext)
    throws IOException
  {
    CountDownLatch localCountDownLatch = new CountDownLatch(1);
    FluencyServiceProxy local1 = new FluencyServiceProxy()
    {
      protected void onServiceConnected()
      {
        this.val$latch.countDown();
      }
    };
    local1.onCreate(paramContext);
    try
    {
      localCountDownLatch.await(8000L, TimeUnit.MILLISECONDS);
      if (localCountDownLatch.getCount() > 0L) {
        throw new IOException("Service is not active, giving up.");
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        Thread.currentThread().interrupt();
      }
    }
    return local1;
  }
  
  public static long getState(List<File> paramList)
  {
    long l = 0L;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      File localFile = (File)localIterator.next();
      if (localFile.exists()) {
        l = 31L * l + localFile.lastModified();
      }
    }
    return l;
  }
  
  public static String getSubKeyName(File paramFile)
    throws IOException
  {
    String str = paramFile.getName();
    int i = str.indexOf(" - ");
    if (i == -1) {
      throw new IOException("This is not a backup file");
    }
    if (i == 0) {
      return "";
    }
    return " - " + str.substring(0, i);
  }
  
  private static String getVersion(Context paramContext)
  {
    PackageInfo localPackageInfo = EnvironmentInfoUtil.getPackageInfo(paramContext);
    if (localPackageInfo != null) {
      return localPackageInfo.versionName;
    }
    return "";
  }
  
  public static boolean identifyNewVersion(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    String str1 = localSharedPreferences.getString("app_version", "");
    String str2 = getVersion(paramContext);
    if (!str1.equals(str2))
    {
      localSharedPreferences.edit().putString("app_version", str2).commit();
      return true;
    }
    return false;
  }
  
  public static boolean installerCompleted(Context paramContext)
  {
    InstallerPreferences localInstallerPreferences = InstallerPreferences.newInstance(paramContext);
    return (localInstallerPreferences.isInstallComplete(paramContext)) && (localInstallerPreferences.isRestoreComplete(paramContext));
  }
  
  public static boolean isBackupEnabled(Context paramContext)
  {
    return paramContext.getResources().getBoolean(2131492867);
  }
  
  public static byte[] readFileContent(File paramFile)
    throws IOException
  {
    if (paramFile.length() > 5000000L) {
      throw new IOException("Size of file " + paramFile.getAbsolutePath() + " exceeds allowed limit of 5000000");
    }
    return Files.toByteArray(paramFile);
  }
  
  private static void readFileDataFromRAF(RandomAccessFile paramRandomAccessFile, String paramString, int paramInt)
    throws IOException
  {
    new File(paramString).getParentFile().mkdirs();
    if (paramInt == 0)
    {
      if (!new File(paramString).createNewFile()) {
        LogUtil.e(TAG, "Unable to create empty file: " + paramString);
      }
      return;
    }
    BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramString));
    byte[] arrayOfByte = new byte[4000];
    int i = 0;
    while (i < paramInt)
    {
      int j = Math.min(4000, paramInt - i);
      paramRandomAccessFile.read(arrayOfByte, 0, j);
      localBufferedOutputStream.write(arrayOfByte, 0, j);
      i += j;
    }
    localBufferedOutputStream.close();
  }
  
  public static long restoreMultipleFiles(byte[] paramArrayOfByte)
    throws IOException
  {
    ArrayList localArrayList = new ArrayList();
    File localFile = writeFileContent(paramArrayOfByte);
    RandomAccessFile localRandomAccessFile = new RandomAccessFile(localFile, "rw");
    int i = 0;
    for (;;)
    {
      long l = i;
      try
      {
        if (l < localRandomAccessFile.length())
        {
          int j = localRandomAccessFile.readInt();
          int k = i + 4;
          byte[] arrayOfByte = new byte[j];
          localRandomAccessFile.read(arrayOfByte);
          String str = new String(arrayOfByte, "UTF-8");
          int m = k + j;
          int n = localRandomAccessFile.readInt();
          int i1 = m + 4;
          readFileDataFromRAF(localRandomAccessFile, str, n);
          i = i1 + n;
          localArrayList.add(new File(str));
        }
      }
      finally
      {
        localRandomAccessFile.close();
        localFile.delete();
      }
    }
    localRandomAccessFile.close();
    localFile.delete();
    return getState(localArrayList);
  }
  
  private static File writeFileContent(String paramString, byte[] paramArrayOfByte)
    throws IOException
  {
    File localFile = createTempFile(paramString);
    Files.write(paramArrayOfByte, localFile);
    return localFile;
  }
  
  public static File writeFileContent(byte[] paramArrayOfByte)
    throws IOException
  {
    return writeFileContent("", paramArrayOfByte);
  }
  
  private static void writeFileDataToRAF(File paramFile, RandomAccessFile paramRandomAccessFile)
    throws IOException
  {
    byte[] arrayOfByte = new byte[4000];
    BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
    for (;;)
    {
      int i = localBufferedInputStream.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      paramRandomAccessFile.write(arrayOfByte, 0, i);
    }
    localBufferedInputStream.close();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.BackupUtil
 * JD-Core Version:    0.7.0.1
 */