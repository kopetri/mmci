package com.touchtype;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;

public class JobScheduler
{
  private static final String TAG = JobScheduler.class.getSimpleName();
  
  private static AlarmManager getAlarmManager(Context paramContext)
  {
    return (AlarmManager)paramContext.getSystemService("alarm");
  }
  
  private static PendingIntent getPendingIntentForJob(AbstractScheduledJob paramAbstractScheduledJob, Context paramContext)
  {
    return PendingIntent.getBroadcast(paramContext, 0, paramAbstractScheduledJob.getJobIntent(paramContext), 1073741824);
  }
  
  public void cancelJob(AbstractScheduledJob paramAbstractScheduledJob, Context paramContext)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext.getApplicationContext());
    getAlarmManager(paramContext).cancel(getPendingIntentForJob(paramAbstractScheduledJob, paramContext));
    paramAbstractScheduledJob.setScheduledJobTime(localTouchTypePreferences, 0L);
  }
  
  public void scheduleJob(AbstractScheduledJob paramAbstractScheduledJob, Context paramContext, boolean paramBoolean, long paramLong)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext.getApplicationContext());
    if (localTouchTypePreferences != null)
    {
      long l1 = paramAbstractScheduledJob.getScheduledJobTime(localTouchTypePreferences);
      long l2 = System.currentTimeMillis();
      if ((l1 == 0L) || (paramBoolean))
      {
        l1 = l2 + paramLong;
        paramAbstractScheduledJob.setScheduledJobTime(localTouchTypePreferences, l1);
      }
      setAlarm(paramAbstractScheduledJob, paramContext, l1);
      return;
    }
    LogUtil.w(TAG, "preferences cannot be null.");
  }
  
  public void scheduleJobDefaultInterval(AbstractScheduledJob paramAbstractScheduledJob, Context paramContext, boolean paramBoolean)
  {
    scheduleJob(paramAbstractScheduledJob, paramContext, paramBoolean, paramAbstractScheduledJob.getDefaultInterval(paramContext));
  }
  
  protected void setAlarm(AbstractScheduledJob paramAbstractScheduledJob, Context paramContext, long paramLong)
  {
    getAlarmManager(paramContext).set(1, paramLong, getPendingIntentForJob(paramAbstractScheduledJob, paramContext));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.JobScheduler
 * JD-Core Version:    0.7.0.1
 */