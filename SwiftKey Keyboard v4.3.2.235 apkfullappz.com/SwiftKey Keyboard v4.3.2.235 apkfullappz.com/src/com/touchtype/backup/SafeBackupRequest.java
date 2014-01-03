package com.touchtype.backup;

import android.content.Context;
import com.touchtype.util.LogUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class SafeBackupRequest
{
  private static final String TAG = SafeBackupRequest.class.getSimpleName();
  
  public static void requestBackup(Context paramContext)
  {
    if (BackupUtil.isBackupEnabled(paramContext))
    {
      if (BackupUtil.installerCompleted(paramContext)) {
        requestBackwardsCompatibleBackup(paramContext);
      }
    }
    else {
      return;
    }
    LogUtil.w(TAG, "Install or restore is not complete");
  }
  
  public static void requestBackupIfNewVersion(Context paramContext)
  {
    if (BackupUtil.identifyNewVersion(paramContext)) {
      requestBackup(paramContext);
    }
  }
  
  private static void requestBackwardsCompatibleBackup(Context paramContext)
  {
    try
    {
      Class.forName("android.app.backup.BackupManager");
      Class localClass = Class.forName("android.app.backup.BackupManager");
      Object localObject = localClass.getConstructor(new Class[] { Context.class }).newInstance(new Object[] { paramContext });
      localClass.getMethod("dataChanged", new Class[0]).invoke(localObject, new Object[0]);
      return;
    }
    catch (Exception localException) {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.SafeBackupRequest
 * JD-Core Version:    0.7.0.1
 */