package com.touchtype.social;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.settings.UsageStatsPreferenceConfiguration;
import com.touchtype.settings.UsageStatsPreferenceSetting.UsageStatsPreferenceActivity;
import com.touchtype.util.PackageInfoUtil;
import com.touchtype_fluency.service.personalize.DynamicPersonalizerModel;
import com.touchtype_fluency.service.personalize.ServiceConfiguration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class DayNotificationXMLLoader
{
  private Context mContext;
  private int mDays;
  private Resources mResources;
  
  public DayNotificationXMLLoader(Context paramContext)
  {
    this.mContext = paramContext;
    this.mResources = paramContext.getResources();
  }
  
  private Notification createNotification(int paramInt)
  {
    String str1 = DayXMLLoaderUtilities.valueAtIndex(this.mResources, 2131623948, paramInt);
    Object localObject = DayXMLLoaderUtilities.valueAtIndex(this.mResources, 2131623950, paramInt);
    String str2 = DayXMLLoaderUtilities.valueAtIndex(this.mResources, 2131623951, paramInt);
    if (this.mResources.getBoolean(2131492921)) {
      new Intent();
    }
    Intent localIntent1;
    if (str2.equals(this.mResources.getString(2131296918))) {
      localIntent1 = createNotificationActionIntent(getURLIntent(this.mResources.getString(2131296822)));
    }
    for (;;)
    {
      localIntent1.addFlags(335609856);
      Notification localNotification2 = new Notification(2130838153, str1, System.currentTimeMillis());
      localNotification2.flags = (0x10 | localNotification2.flags);
      localNotification2.setLatestEventInfo(this.mContext, str1, (CharSequence)localObject, PendingIntent.getActivity(this.mContext, 0, localIntent1, 134217728));
      Notification localNotification1 = UserInteractionReceiver.setUserEventNotificationClearIntent(this.mContext, localNotification2, getEventTriggeredKey());
      label304:
      boolean bool;
      do
      {
        String str3;
        do
        {
          return localNotification1;
          if ((str2.equals(this.mResources.getString(2131296919))) && (this.mResources.getBoolean(2131492916)))
          {
            localIntent1 = createNotificationActionIntent(getURLIntent(this.mResources.getString(2131296923)));
            break;
          }
          if (!str2.equals(this.mResources.getString(2131296920))) {
            break label304;
          }
          str3 = getPersonaliserText((String)localObject);
          localNotification1 = null;
        } while (str3 == null);
        localObject = str3;
        Intent localIntent2 = new Intent(this.mResources.getString(2131296753));
        localIntent2.setClassName(this.mContext.getPackageName(), this.mResources.getString(2131296752));
        localIntent1 = createNotificationActionIntent(localIntent2);
        break;
        bool = str2.equals(this.mResources.getString(2131296921));
        localNotification1 = null;
      } while (!bool);
      TouchTypeStats localTouchTypeStats = TouchTypePreferences.getInstance(this.mContext).getTouchTypeStats();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(UsageStatsPreferenceConfiguration.efficiency(localTouchTypeStats));
      localObject = String.format((String)localObject, arrayOfObject);
      localIntent1 = createNotificationActionIntent(new Intent(this.mContext, UsageStatsPreferenceSetting.UsageStatsPreferenceActivity.class));
    }
  }
  
  private Intent createNotificationActionIntent(Intent paramIntent)
  {
    Intent localIntent = new Intent(this.mContext, NotificationActionLauncherAndLogger.class);
    localIntent.putExtra("user_event_triggered_key", getEventTriggeredKey());
    localIntent.putExtra("user_event_triggered_action", 7);
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("notification_intent", paramIntent);
    localIntent.putExtras(localBundle);
    return localIntent;
  }
  
  private StringBuilder createPersonaliserText(PackageInfoUtil paramPackageInfoUtil, StringBuilder paramStringBuilder, HashSet<String> paramHashSet, String paramString1, String paramString2, int paramInt)
  {
    if ((paramPackageInfoUtil.isPackageInstalled(paramString2)) && (!paramHashSet.contains(paramString1))) {
      paramStringBuilder.append(this.mContext.getString(paramInt) + this.mContext.getString(2131297293));
    }
    return paramStringBuilder;
  }
  
  private String getEventTriggeredKey()
  {
    return "DAY_TIP_EVENT-" + this.mDays;
  }
  
  private String getPersonaliserText(String paramString)
  {
    if (!TouchTypePreferences.getInstance(this.mContext).isCloudAccountSetup()) {}
    StringBuilder localStringBuilder;
    do
    {
      return paramString;
      SharedPreferences localSharedPreferences = this.mContext.getSharedPreferences("DynamicPersonalizers", 0);
      HashSet localHashSet1 = new HashSet();
      Iterator localIterator = localSharedPreferences.getAll().keySet().iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        if (DynamicPersonalizerModel.isDynamicPersonalizerKey(str4)) {
          localHashSet1.add(DynamicPersonalizerModel.getServiceFromKey(localSharedPreferences.getString(str4, null)).getName());
        }
      }
      String str1 = ServiceConfiguration.GMAIL.getName();
      String str2 = ServiceConfiguration.FACEBOOK.getName();
      String str3 = ServiceConfiguration.TWITTER.getName();
      HashSet localHashSet2 = new HashSet(Arrays.asList(new String[] { str1, str2, str3 }));
      if (localHashSet1.containsAll(localHashSet2)) {
        return null;
      }
      PackageInfoUtil localPackageInfoUtil = new PackageInfoUtil(this.mContext);
      localStringBuilder = createPersonaliserText(localPackageInfoUtil, createPersonaliserText(localPackageInfoUtil, createPersonaliserText(localPackageInfoUtil, new StringBuilder(), localHashSet1, str1, "com.google.android.gm", 2131297133), localHashSet1, str2, "com.facebook.katana", 2131297139), localHashSet1, str3, "com.twitter.android", 2131297145);
    } while (localStringBuilder.length() <= 1);
    localStringBuilder.replace(localStringBuilder.lastIndexOf(this.mContext.getString(2131297293)), localStringBuilder.length(), ".");
    return this.mContext.getString(2131296917) + " " + localStringBuilder.toString();
  }
  
  private Intent getURLIntent(String paramString)
  {
    return new Intent("android.intent.action.VIEW", Uri.parse(paramString));
  }
  
  private boolean sendNotificationAndLog(Notification paramNotification)
  {
    NotificationManager localNotificationManager = (NotificationManager)this.mContext.getSystemService("notification");
    if ((localNotificationManager != null) && (paramNotification != null))
    {
      localNotificationManager.notify(2130903121, paramNotification);
      TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(this.mContext);
      localTouchTypePreferences.setEventTriggered(getEventTriggeredKey(), 1);
      return localTouchTypePreferences.putInt("day_tip_shown", this.mDays);
    }
    return false;
  }
  
  public boolean loadNotifications(long paramLong)
  {
    this.mDays = DayXMLLoaderUtilities.daysSinceInstall(paramLong);
    if (DayXMLLoaderUtilities.alreadyShown(this.mContext, this.mDays)) {}
    for (;;)
    {
      return false;
      int[] arrayOfInt = this.mResources.getIntArray(2131623942);
      for (int i = 0; i < arrayOfInt.length; i++) {
        if (arrayOfInt[i] == this.mDays)
        {
          sendNotificationAndLog(createNotification(i));
          return true;
        }
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.social.DayNotificationXMLLoader
 * JD-Core Version:    0.7.0.1
 */