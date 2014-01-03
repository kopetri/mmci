package com.touchtype_fluency.service.personalize;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PersonalizationToggleReceiver
  extends BroadcastReceiver
{
  private static final int PERSONALIZATION_DISABLE_PERIOD = 86400000;
  private static final String TAG = PersonalizationToggleReceiver.class.getSimpleName();
  
  private SharedPreferences getPreferences(Context paramContext)
  {
    return paramContext.getSharedPreferences("personalizer_service", 0);
  }
  
  private void setAlarm(Context paramContext, long paramLong)
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent().setAction("com.touchtype_fluency.service.personalize.ACTION_ENABLE_PERSONALIZATION").setClass(paramContext, PersonalizationToggleReceiver.class), 1073741824);
    ((AlarmManager)paramContext.getSystemService("alarm")).set(1, paramLong, localPendingIntent);
  }
  
  public void checkAlarmSet(Context paramContext)
  {
    SharedPreferences localSharedPreferences = getPreferences(paramContext);
    long l1;
    long l2;
    if (localSharedPreferences.getBoolean("enabled", false))
    {
      l1 = localSharedPreferences.getLong("enable_time", 0L);
      l2 = System.currentTimeMillis();
      if (l1 == 0L) {
        setAlarm(paramContext, l2);
      }
    }
    else
    {
      return;
    }
    if (l1 <= l2)
    {
      setAlarm(paramContext, l2);
      return;
    }
    setAlarm(paramContext, l1);
  }
  
  public void disablePersonalization(Context paramContext)
  {
    SharedPreferences localSharedPreferences = getPreferences(paramContext);
    long l;
    if (!localSharedPreferences.getBoolean("enabled", false)) {
      l = 86400000L + System.currentTimeMillis();
    }
    synchronized (localSharedPreferences.edit())
    {
      ???.putBoolean("enabled", true);
      ???.putLong("enable_time", l);
      ???.commit();
      setAlarm(paramContext, l);
      return;
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    synchronized (getPreferences(paramContext).edit())
    {
      ???.putBoolean("enabled", false);
      ???.putLong("enable_time", 0L);
      ???.commit();
      paramContext.startService(new Intent().setAction("com.touchtype_fluency.service.personalize.ACTION_ENABLE_PERSONALIZATION").setClass(paramContext, PersonalizerService.class));
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.PersonalizationToggleReceiver
 * JD-Core Version:    0.7.0.1
 */