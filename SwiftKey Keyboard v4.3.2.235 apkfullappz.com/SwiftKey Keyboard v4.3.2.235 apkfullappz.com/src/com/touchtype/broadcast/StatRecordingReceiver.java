package com.touchtype.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.touchtype.broadcast.internal.IrisSender;
import com.touchtype.broadcast.internal.IrisSender.Interval;
import java.util.List;

public class StatRecordingReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Bundle localBundle = paramIntent.getExtras();
    if (localBundle == null) {
      Log.e("NotificationBroadcastStats", "Invalid stats intent received, internal error");
    }
    for (;;)
    {
      return;
      String str1 = localBundle.getString("intentType");
      String str2 = localBundle.getString("intentUri");
      String str3 = localBundle.getString("statsJson");
      try
      {
        IrisSender.sendAsync(paramContext, "http://userstats.iris.touchtype-fluency.com/v2/foghornstats", str3, IrisSender.Interval.WEEKLY);
        if ((str1 != null) && (str2 != null))
        {
          Intent localIntent = new Intent(str1, Uri.parse(str2));
          localIntent.setFlags(268435456);
          if (paramContext.getPackageManager().queryIntentActivities(localIntent, 0).size() > 0)
          {
            paramContext.startActivity(localIntent);
            return;
          }
        }
      }
      catch (Exception localException)
      {
        for (;;)
        {
          Log.e("NotificationBroadcastStats", "Unable to send stats", localException);
        }
        Log.e("NotificationBroadcastStats", "Received an invalid intent to fire as the result of a notification");
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.broadcast.StatRecordingReceiver
 * JD-Core Version:    0.7.0.1
 */