package com.touchtype;

import android.content.Context;
import android.content.res.Resources;
import com.touchtype.backup.SafeBackupRequest;
import com.touchtype.preferences.SwiftKeyPreferences;

public class BackupRequestScheduledJob
  extends AbstractScheduledJob
{
  private static final String TAG = BackupRequestScheduledJob.class.getSimpleName();
  
  public long getDefaultInterval(Context paramContext)
  {
    return paramContext.getResources().getInteger(2131558413);
  }
  
  public long getScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    long l = paramSwiftKeyPreferences.getLong("scheduled_backup_request_job_time", 0L);
    if (l != 0L) {
      return l;
    }
    return paramSwiftKeyPreferences.getLong("scheduled_job_time", 0L);
  }
  
  public void runJob(Context paramContext, JobScheduler paramJobScheduler)
  {
    SafeBackupRequest.requestBackup(paramContext);
    paramJobScheduler.scheduleJobDefaultInterval(this, paramContext, true);
  }
  
  public void setScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences, long paramLong)
  {
    paramSwiftKeyPreferences.putLong("scheduled_backup_request_job_time", paramLong);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.BackupRequestScheduledJob
 * JD-Core Version:    0.7.0.1
 */