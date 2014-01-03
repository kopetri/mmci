package com.touchtype.broadcast.internal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.touchtype.broadcast.StatRecordingReceiver;
import com.touchtype.broadcast.data.NotificationStat;
import com.touchtype.broadcast.data.NotificationStat.Action;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class UserNotifier
{
  public static final void cancel(Context paramContext, Bundle paramBundle)
  {
    int i = BundleUtil.getInt(paramBundle, "messageId", 0);
    notificationManager(paramContext).cancel("Foghorn", i);
  }
  
  private static final PendingIntent dismissIntent(Context paramContext, Bundle paramBundle)
  {
    return PendingIntent.getBroadcast(paramContext, 0, statIntent(paramContext, paramBundle, NotificationStat.Action.DISMISSED), 268435456);
  }
  
  private static void displayNotification(Context paramContext, Bundle paramBundle)
  {
    String str1 = paramBundle.getString("title");
    String str2 = paramBundle.getString("text");
    int i = BundleUtil.getInt(paramBundle, "messageId", 0);
    if ((str1 == null) || (str2 == null))
    {
      Log.e("NotificationBroadcastUserNotifier", "Received invalid notification, must have title and text. Showing nothing.");
      return;
    }
    paramContext.sendBroadcast(statIntent(paramContext, paramBundle, NotificationStat.Action.DISPLAYED));
    setLastMessageTime(paramContext);
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(paramContext);
    localBuilder.setContentTitle(str1).setContentText(str2).setContentIntent(selectIntent(paramContext, paramBundle)).setDeleteIntent(dismissIntent(paramContext, paramBundle)).setSmallIcon(icon(paramContext, paramBundle)).setAutoCancel(true);
    notificationManager(paramContext).notify("Foghorn", i, localBuilder.getNotification());
  }
  
  private static final boolean hasExpired(Context paramContext, Bundle paramBundle)
  {
    if (!paramBundle.containsKey("expiresAt")) {
      return false;
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.ENGLISH);
    try
    {
      boolean bool = DateProvider.now().after(localSimpleDateFormat.parse(BundleUtil.getString(paramBundle, "expiresAt", "")));
      return bool;
    }
    catch (ParseException localParseException)
    {
      Log.e("NotificationBroadcastUserNotifier", "Received a bad expiry date in a cloud notification", localParseException);
    }
    return false;
  }
  
  private static final int icon(Context paramContext, Bundle paramBundle)
  {
    String str1 = BundleUtil.getString(paramBundle, "iconPackage", "android");
    String str2 = BundleUtil.getString(paramBundle, "iconResource", "ic_dialog_info");
    int i = paramContext.getResources().getIdentifier(str2, "drawable", str1);
    if (i != 0) {
      return i;
    }
    Log.e("NotificationBroadcastUserNotifier", "Notification attempted to draw a non existent icon, showing default");
    return 17301659;
  }
  
  private static final boolean isEmergency(Bundle paramBundle)
  {
    return BundleUtil.getString(paramBundle, "spamoverride", "nope").equals("emergency");
  }
  
  private static final boolean isRightProduct(Context paramContext, Bundle paramBundle)
  {
    try
    {
      String str1 = paramContext.getPackageName();
      int i = paramContext.getPackageManager().getPackageInfo(str1, 0).versionCode;
      String str2 = BundleUtil.getString(paramBundle, "targetPackage", str1);
      int j = BundleUtil.getInt(paramBundle, "targetMinVersion", i);
      int k = BundleUtil.getInt(paramBundle, "targetMaxVersion", i);
      boolean bool1 = str2.equals(str1);
      boolean bool2 = false;
      if (bool1)
      {
        bool2 = false;
        if (i >= j)
        {
          bool2 = false;
          if (i <= k) {
            bool2 = true;
          }
        }
      }
      return bool2;
    }
    catch (Exception localException)
    {
      Log.w("NotificationBroadcastUserNotifier", "Error comparing incoming message with package name/version. Could be due to a malformed message.");
    }
    return false;
  }
  
  private static final boolean isSpam(Context paramContext)
  {
    return DateProvider.now().getTime() - settings(paramContext).getLong("LastMessageTime", 0L) < 604800000L;
  }
  
  public static final NotificationManager notificationManager(Context paramContext)
  {
    return (NotificationManager)paramContext.getSystemService("notification");
  }
  
  private static final boolean receivesPublicity(Context paramContext)
  {
    return settings(paramContext).getBoolean("ReceivesPublicity", true);
  }
  
  private static final PendingIntent selectIntent(Context paramContext, Bundle paramBundle)
  {
    Intent localIntent = statIntent(paramContext, paramBundle, NotificationStat.Action.FOLLOWED);
    localIntent.putExtra("intentType", paramBundle.getString("intentType"));
    localIntent.putExtra("intentUri", paramBundle.getString("intentUri"));
    return PendingIntent.getBroadcast(paramContext, BundleUtil.getInt(paramBundle, "messageId", 0), localIntent, 268435456);
  }
  
  private static final void setLastMessageTime(Context paramContext)
  {
    long l = DateProvider.now().getTime();
    settings(paramContext).edit().putLong("LastMessageTime", l).commit();
  }
  
  private static final SharedPreferences settings(Context paramContext)
  {
    return paramContext.getSharedPreferences("com.touchtype.broadcast", 0);
  }
  
  public static final void showOrSchedule(Context paramContext, Bundle paramBundle)
  {
    if (!isRightProduct(paramContext, paramBundle)) {
      return;
    }
    if (hasExpired(paramContext, paramBundle))
    {
      paramContext.sendBroadcast(statIntent(paramContext, paramBundle, NotificationStat.Action.EXPIRED));
      return;
    }
    if ((isSpam(paramContext)) && (!isEmergency(paramBundle)))
    {
      paramContext.sendBroadcast(statIntent(paramContext, paramBundle, NotificationStat.Action.SPAM));
      return;
    }
    if ((!receivesPublicity(paramContext)) && (!isEmergency(paramBundle)))
    {
      paramContext.sendBroadcast(statIntent(paramContext, paramBundle, NotificationStat.Action.PUBLICITY_REFUSED));
      return;
    }
    displayNotification(paramContext, paramBundle);
  }
  
  private static final Intent statIntent(Context paramContext, Bundle paramBundle, NotificationStat.Action paramAction)
  {
    int i = BundleUtil.getInt(paramBundle, "messageId", 0);
    NotificationStat localNotificationStat = new NotificationStat(i, paramAction);
    Bundle localBundle = new Bundle();
    localBundle.putInt("messageId", i);
    localBundle.putString("statsJson", localNotificationStat.toString());
    Intent localIntent = new Intent(paramContext, StatRecordingReceiver.class);
    localIntent.putExtras(localBundle);
    return localIntent;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.broadcast.internal.UserNotifier
 * JD-Core Version:    0.7.0.1
 */