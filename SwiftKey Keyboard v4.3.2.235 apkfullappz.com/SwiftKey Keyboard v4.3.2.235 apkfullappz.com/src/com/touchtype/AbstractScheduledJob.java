package com.touchtype;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.touchtype.preferences.SwiftKeyPreferences;

public abstract class AbstractScheduledJob
  extends BroadcastReceiver
{
  public abstract long getDefaultInterval(Context paramContext);
  
  public String getIntentAction()
  {
    return "com.touchtype.ACTION_SCHEDULED_JOB";
  }
  
  public Intent getJobIntent(Context paramContext)
  {
    Intent localIntent = new Intent(getIntentAction());
    localIntent.setClass(paramContext, getClass());
    return writeStateToIntent(localIntent);
  }
  
  public abstract long getScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences);
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    runJob(paramContext, new JobScheduler());
  }
  
  public abstract void runJob(Context paramContext, JobScheduler paramJobScheduler);
  
  public abstract void setScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences, long paramLong);
  
  public Intent writeStateToIntent(Intent paramIntent)
  {
    return paramIntent;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.AbstractScheduledJob
 * JD-Core Version:    0.7.0.1
 */