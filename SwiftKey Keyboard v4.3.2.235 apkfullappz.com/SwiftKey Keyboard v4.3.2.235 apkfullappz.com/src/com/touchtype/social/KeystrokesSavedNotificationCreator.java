package com.touchtype.social;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.util.LogUtil;
import java.util.ArrayList;

public class KeystrokesSavedNotificationCreator
  implements UserEventNotificationCreator
{
  private static final String TAG = KeystrokesSavedNotificationCreator.class.getSimpleName();
  private int milestoneIndex;
  
  public KeystrokesSavedNotificationCreator(int paramInt)
  {
    this.milestoneIndex = paramInt;
  }
  
  private boolean changeActionToShare(Context paramContext, String paramString)
  {
    Resources localResources = paramContext.getResources();
    int i;
    if ((!localResources.getBoolean(2131492915)) && (paramString.equals("share_ignore_turnoff")))
    {
      i = 1;
      if ((!TouchTypePreferences.getInstance(paramContext).getBoolean("app_rated", false)) || (!paramString.equals("rate"))) {
        break label85;
      }
    }
    label85:
    for (int j = 1;; j = 0)
    {
      boolean bool;
      if ((!localResources.getBoolean(2131492916)) && (i == 0))
      {
        bool = false;
        if (j == 0) {}
      }
      else
      {
        bool = true;
      }
      return bool;
      i = 0;
      break;
    }
  }
  
  private Intent createNotificationActionIntent(Context paramContext, Intent paramIntent, String paramString, int paramInt)
  {
    Intent localIntent = new Intent(paramContext, NotificationActionLauncherAndLogger.class);
    localIntent.putExtra("user_event_triggered_key", paramString);
    localIntent.putExtra("user_event_triggered_action", paramInt);
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("notification_intent", paramIntent);
    localIntent.putExtras(localBundle);
    return localIntent;
  }
  
  private Intent createShareIgnoreTurnOffIntent(Context paramContext, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(paramContext, UserInteractionActivity.class);
    localIntent.setAction(EventTriggeredUserInteractions.UserEvent.KEYSTROKE_SAVING_EVENT.name());
    localIntent.putExtra("user_event_triggered_key", paramString2);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Integer.valueOf(0));
    localArrayList.add(Integer.valueOf(1));
    localIntent.putIntegerArrayListExtra("actions", localArrayList);
    localIntent.putExtra("milestone_string", paramString1);
    localIntent.putExtra("share_body", getShareBody(paramContext.getResources(), paramString1));
    localIntent.addFlags(268435456);
    return localIntent;
  }
  
  protected static int findMilestone(int paramInt, int[] paramArrayOfInt)
  {
    for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] < paramInt); i++) {}
    return i - 1;
  }
  
  private String getShareBody(Resources paramResources, String paramString)
  {
    String str = paramResources.getString(2131297217);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramResources.getString(2131296303);
    arrayOfObject[1] = paramString;
    return String.format(str, arrayOfObject);
  }
  
  private String getStringValueAtIndex(Resources paramResources, int paramInt)
  {
    return paramResources.getStringArray(paramInt)[this.milestoneIndex];
  }
  
  public static void initiateKeystrokesNotificationIfRequired(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    if (localResources.getBoolean(2131492918)) {}
    TouchTypePreferences localTouchTypePreferences;
    int i;
    int k;
    do
    {
      int[] arrayOfInt;
      int j;
      do
      {
        return;
        localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
        TouchTypeStats localTouchTypeStats = localTouchTypePreferences.getTouchTypeStats();
        if (localTouchTypeStats == null)
        {
          LogUtil.w(TAG, "stats is null");
          return;
        }
        i = localTouchTypeStats.getKeyStrokesSaved();
        arrayOfInt = localResources.getIntArray(2131623944);
        j = findMilestone(i, arrayOfInt);
      } while (j == -1);
      k = arrayOfInt[j] + localResources.getInteger(2131558461);
      if (triggerKeystrokeSavedNotification(localTouchTypePreferences, i, arrayOfInt, j, k))
      {
        new EventTriggeredUserInteractions(paramContext.getApplicationContext()).createRunner(new KeystrokesSavedNotificationCreator(j));
        return;
      }
    } while (i <= k);
    localTouchTypePreferences.setEventTriggered(EventTriggeredUserInteractions.UserEvent.KEYSTROKE_SAVING_EVENT.name(), -1);
  }
  
  protected static boolean triggerKeystrokeSavedNotification(TouchTypePreferences paramTouchTypePreferences, int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3)
  {
    int i;
    if (paramTouchTypePreferences.getEventTriggered(EventTriggeredUserInteractions.UserEvent.KEYSTROKE_SAVING_EVENT.name()) == -1)
    {
      i = 1;
      if ((paramInt1 <= paramArrayOfInt[paramInt2]) || (paramInt1 >= paramInt3)) {
        break label51;
      }
    }
    label51:
    for (int j = 1;; j = 0)
    {
      if ((i == 0) || (j == 0)) {
        break label57;
      }
      return true;
      i = 0;
      break;
    }
    label57:
    return false;
  }
  
  public Notification createNotification(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    String str1 = getStringValueAtIndex(localResources, 2131623955);
    String str2 = getStringValueAtIndex(localResources, 2131623956);
    String str3 = getStringValueAtIndex(localResources, 2131623957);
    if (changeActionToShare(paramContext, str3)) {
      str3 = localResources.getString(2131297229);
    }
    String str4 = "";
    String str5 = getEventTriggeredKey(localResources);
    Intent localIntent;
    if (localResources.getBoolean(2131492919)) {
      localIntent = new Intent();
    }
    for (;;)
    {
      localIntent.addFlags(67174400);
      Notification localNotification = new Notification(2130838153, str2, System.currentTimeMillis());
      localNotification.flags = (0x10 | localNotification.flags);
      localNotification.setLatestEventInfo(paramContext, str2, str4, PendingIntent.getActivity(paramContext, 0, localIntent, 134217728));
      return UserInteractionReceiver.setUserEventNotificationClearIntent(paramContext, localNotification, str5);
      if (str3.equals(localResources.getString(2131297229)))
      {
        if (localResources.getBoolean(2131492914))
        {
          str4 = localResources.getString(2131297219);
          localIntent = createNotificationActionIntent(paramContext, UserNotificationManager.shareAction(paramContext, getShareBody(localResources, str1)), str5, 2);
        }
        else
        {
          localIntent = new Intent();
        }
      }
      else if (str3.equals(localResources.getString(2131297231)))
      {
        str4 = localResources.getString(2131297218);
        localIntent = createShareIgnoreTurnOffIntent(paramContext, str1, str5);
      }
      else
      {
        if (!str3.equals(localResources.getString(2131297230))) {
          break;
        }
        str4 = localResources.getString(2131297220);
        localIntent = createNotificationActionIntent(paramContext, UserNotificationManager.rateAction(paramContext), str5, 6);
      }
    }
    return null;
  }
  
  public String getEventTriggeredKey(Resources paramResources)
  {
    return getEventTriggeredName() + "-" + paramResources.getIntArray(2131623944)[this.milestoneIndex];
  }
  
  public String getEventTriggeredName()
  {
    return EventTriggeredUserInteractions.UserEvent.KEYSTROKE_SAVING_EVENT.name();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.social.KeystrokesSavedNotificationCreator
 * JD-Core Version:    0.7.0.1
 */