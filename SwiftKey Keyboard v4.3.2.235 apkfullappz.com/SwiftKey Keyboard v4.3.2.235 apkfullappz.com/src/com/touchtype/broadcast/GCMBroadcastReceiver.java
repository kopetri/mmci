package com.touchtype.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.touchtype.broadcast.internal.UserNotifier;

public class GCMBroadcastReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Bundle localBundle = paramIntent.getExtras();
    if (localBundle == null) {
      return;
    }
    String str = localBundle.getString("type");
    if ("notification".equals(str))
    {
      new StringBuilder("Notification received: ").append(localBundle.toString()).toString();
      UserNotifier.showOrSchedule(paramContext, localBundle);
    }
    for (;;)
    {
      setResultCode(-1);
      return;
      if ("notificationCancel".equals(str)) {
        UserNotifier.cancel(paramContext, localBundle);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.broadcast.GCMBroadcastReceiver
 * JD-Core Version:    0.7.0.1
 */