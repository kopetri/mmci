package com.touchtype.social;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.touchtype.preferences.TouchTypePreferences;

public class UserInteractionReceiver
  extends BroadcastReceiver
{
  private static final String TAG = UserInteractionReceiver.class.getSimpleName();
  
  public static Notification setUserEventNotificationClearIntent(Context paramContext, Notification paramNotification, String paramString)
  {
    Intent localIntent = new Intent(paramContext, UserInteractionReceiver.class);
    localIntent.setAction("com.touchtype.CLEARED");
    localIntent.putExtra("user_event_triggered_key", paramString);
    paramNotification.deleteIntent = PendingIntent.getBroadcast(paramContext, 0, localIntent, 134217728);
    return paramNotification;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    String str = paramIntent.getAction();
    if ((str != null) && (str.equals("com.touchtype.CLEARED")) && (paramIntent.getExtras() != null))
    {
      if (localNotificationManager != null) {
        localNotificationManager.cancel(2130903121);
      }
      TouchTypePreferences.getInstance(paramContext).setEventTriggered(paramIntent.getStringExtra("user_event_triggered_key"), 5);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.social.UserInteractionReceiver
 * JD-Core Version:    0.7.0.1
 */