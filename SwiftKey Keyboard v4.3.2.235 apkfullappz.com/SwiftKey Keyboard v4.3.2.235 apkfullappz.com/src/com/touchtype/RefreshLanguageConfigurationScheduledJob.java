package com.touchtype;

import android.content.Context;
import android.content.res.Resources;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.FluencyServiceImpl;

public class RefreshLanguageConfigurationScheduledJob
  extends AbstractScheduledJob
{
  private static final String TAG = RefreshLanguageConfigurationScheduledJob.class.getSimpleName();
  
  private int intervalResourceId(Context paramContext)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext.getApplicationContext());
    if ((localTouchTypePreferences != null) && (localTouchTypePreferences.areLiveLanguagesEnabled())) {
      return 2131558416;
    }
    return 2131558415;
  }
  
  public long getDefaultInterval(Context paramContext)
  {
    return paramContext.getResources().getInteger(intervalResourceId(paramContext));
  }
  
  public long getScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    long l = paramSwiftKeyPreferences.getLong("scheduled_refresh_language_configuration_job_time", 0L);
    if (l != 0L) {
      return l;
    }
    return paramSwiftKeyPreferences.getLong("scheduled_job_time", 0L);
  }
  
  protected void refreshLanguageConfiguration(Context paramContext)
  {
    FluencyServiceImpl.startServiceForAction("com.touchtype.REFRESH_CONFIGURATION", paramContext);
  }
  
  public void runJob(Context paramContext, JobScheduler paramJobScheduler)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext.getApplicationContext());
    if (localTouchTypePreferences != null) {
      if (localTouchTypePreferences.isLicenseValid()) {
        refreshLanguageConfiguration(paramContext);
      }
    }
    for (;;)
    {
      paramJobScheduler.scheduleJobDefaultInterval(this, paramContext, true);
      return;
      LogUtil.w(TAG, "preferences cannot be null.");
    }
  }
  
  public void setScheduledJobTime(SwiftKeyPreferences paramSwiftKeyPreferences, long paramLong)
  {
    paramSwiftKeyPreferences.putLong("scheduled_refresh_language_configuration_job_time", paramLong);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.RefreshLanguageConfigurationScheduledJob
 * JD-Core Version:    0.7.0.1
 */