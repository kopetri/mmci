package com.touchtype.broadcast;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.touchtype.broadcast.data.CloudId;
import com.touchtype.broadcast.internal.IrisSender;
import com.touchtype.broadcast.internal.IrisSender.Interval;
import java.util.Date;

public final class NotificationRegistrar
{
  private static final int appVersion(Context paramContext)
  {
    try
    {
      int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new RuntimeException("Could not get package name: " + localNameNotFoundException);
    }
  }
  
  public static void receivePublicityMessages(Context paramContext, boolean paramBoolean)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("com.touchtype.broadcast", 0);
    if (localSharedPreferences.getBoolean("ReceivesPublicity", true) != paramBoolean) {
      localSharedPreferences.edit().putBoolean("ReceivesPublicity", paramBoolean).commit();
    }
  }
  
  public static void register(Context paramContext, Class<?> paramClass)
  {
    final int i = appVersion(paramContext);
    final SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("com.touchtype.broadcast", 0);
    final String str = localSharedPreferences.getString("GcmRegistrationId", "none");
    if ((i == localSharedPreferences.getInt("AppVersion", -1)) && (!shouldCheck(localSharedPreferences)))
    {
      new StringBuilder("Using pre-existing GCM registration ID: ").append(localSharedPreferences.getString("GcmRegistrationId", "None")).toString();
      return;
    }
    IrisSender.setStatsSendingIntent(paramClass);
    new AsyncTask()
    {
      protected String doInBackground(Void... paramAnonymousVarArgs)
      {
        GoogleCloudMessaging localGoogleCloudMessaging = GoogleCloudMessaging.getInstance(this.val$context);
        try
        {
          String str = localGoogleCloudMessaging.register(new String[] { "1018135770358" });
          return str;
        }
        catch (Exception localException)
        {
          Log.e("NotificationRegistrar", "GCID registration failed", localException);
        }
        return null;
      }
      
      protected void onPostExecute(String paramAnonymousString)
      {
        if (paramAnonymousString == null) {
          return;
        }
        if (!str.equals(paramAnonymousString)) {}
        try
        {
          IrisSender.sendAsync(this.val$context, "http://userstats.iris.touchtype-fluency.com/v2/foghorngid", new CloudId(paramAnonymousString), IrisSender.Interval.NOW);
          new StringBuilder("Registered a GCM registration ID with the server: ").append(paramAnonymousString).toString();
          localSharedPreferences.edit().putString("GcmRegistrationId", paramAnonymousString).putInt("AppVersion", i).commit();
          NotificationRegistrar.setLastSuccessfulGcidCheck(localSharedPreferences);
          return;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            Log.e("NotificationRegistrar", localException.getStackTrace().toString());
          }
        }
      }
    }.execute(new Void[] { null, null, null });
  }
  
  private static final void setLastSuccessfulGcidCheck(SharedPreferences paramSharedPreferences)
  {
    paramSharedPreferences.edit().putLong("LastCheckedId", new Date().getTime());
  }
  
  private static final boolean shouldCheck(SharedPreferences paramSharedPreferences)
  {
    return new Date().getTime() - paramSharedPreferences.getLong("LastCheckedId", 0L) > 604800000L;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.broadcast.NotificationRegistrar
 * JD-Core Version:    0.7.0.1
 */