package com.touchtype.cloud.sync;

import android.content.Context;
import android.content.Intent;
import com.touchtype.AbstractScheduledJob;
import com.touchtype.JobScheduler;
import com.touchtype.cloud.CloudService;
import com.touchtype.cloud.CloudUtils;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;

public class SyncScheduledJob
  extends AbstractScheduledJob
{
  public long getDefaultInterval(Context paramContext)
  {
    return CloudUtils.getSyncInterval(TouchTypePreferences.getInstance(paramContext).getSyncFrequency());
  }
  
  public long getScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    return paramSwiftKeyPreferences.getSyncScheduledTime();
  }
  
  public void runJob(Context paramContext, JobScheduler paramJobScheduler)
  {
    TouchTypePreferences.getInstance(paramContext).setSyncScheduledTime(0L);
    Intent localIntent = new Intent(paramContext, CloudService.class);
    localIntent.setAction("CloudService.performSync");
    paramContext.startService(localIntent);
  }
  
  public void setScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences, long paramLong)
  {
    paramSwiftKeyPreferences.setSyncScheduledTime(paramLong);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.sync.SyncScheduledJob
 * JD-Core Version:    0.7.0.1
 */