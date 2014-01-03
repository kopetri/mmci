package com.touchtype.common.iris;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver
  extends BroadcastReceiver
{
  static Intent alarmIntent(Context paramContext)
  {
    return new Intent(paramContext, AlarmReceiver.class);
  }
  
  protected static void cancelAlarm(Context paramContext)
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, alarmIntent(paramContext), 536870912);
    if (localPendingIntent != null) {
      localPendingIntent.cancel();
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    cancelAlarm(paramContext);
    paramContext.startService(IrisDataSenderService.retryCachedDataIntent(paramContext));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.iris.AlarmReceiver
 * JD-Core Version:    0.7.0.1
 */