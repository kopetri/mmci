package com.touchtype;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype_fluency.service.FluencyServiceImpl;

public class UserStatsScheduledJob
  extends AbstractScheduledJob
{
  private Intent getServiceIntent(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, FluencyServiceImpl.class);
    localIntent.setAction("com.touchtype.REPORT_TO_IRIS");
    return localIntent;
  }
  
  private boolean isSendStatsEnabled(Context paramContext)
  {
    return TouchTypePreferences.getInstance(paramContext).isSendStatsEnabled();
  }
  
  public long getDefaultInterval(Context paramContext)
  {
    return paramContext.getResources().getInteger(2131558412);
  }
  
  public long getScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    long l = paramSwiftKeyPreferences.getLong("user_stats_scheduled_job_time", 0L);
    if (l != 0L) {
      return l;
    }
    return paramSwiftKeyPreferences.getLong("scheduled_job_time", 0L);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    super.onReceive(paramContext, paramIntent);
  }
  
  public void runJob(Context paramContext, JobScheduler paramJobScheduler)
  {
    if (isSendStatsEnabled(paramContext)) {
      sendStatsReport(paramContext);
    }
    paramJobScheduler.scheduleJobDefaultInterval(this, paramContext, true);
  }
  
  protected void sendStatsReport(Context paramContext)
  {
    paramContext.startService(getServiceIntent(paramContext));
  }
  
  public void setScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences, long paramLong)
  {
    paramSwiftKeyPreferences.putLong("user_stats_scheduled_job_time", paramLong);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.UserStatsScheduledJob
 * JD-Core Version:    0.7.0.1
 */