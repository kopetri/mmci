package com.touchtype.social;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import com.touchtype.Launcher;
import com.touchtype.cloud.CloudSetupActivity;
import com.touchtype.preferences.PrioritisedChooserActivity;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.referral.ReferralSource;
import com.touchtype.settings.InputMethodsPreferenceSetting.InputMethodsPreferenceActivity;
import com.touchtype.settings.InputMethodsPreferenceSetting.InputMethodsPreferenceFragment;
import com.touchtype.settings.LanguagePreferenceSetting.LanguagePreferenceActivity;
import com.touchtype.settings.RecentPersonalizersPreferenceSetting.RecentPersonalizersPreferenceActivity;
import com.touchtype.settings.TouchTypeKeyboardSettings;
import com.touchtype.util.LogUtil;
import java.util.ArrayList;
import java.util.Iterator;

public class UserNotificationManager
{
  private static final String TAG = UserNotificationManager.class.getSimpleName();
  private static UserNotificationManager instance;
  private PendingIntent mCloudServicesIntent;
  private Notification mCloudServicesNotification = null;
  private Context mContext;
  private PendingIntent mHardKBSettingsIntent;
  private Notification mHardKBSettingsNotification = null;
  private String mInstallerContext;
  private PendingIntent mInstallerIntent;
  private Notification mInstallerNotification = null;
  private PendingIntent mLanguageIntent;
  private Notification mLanguageNotification = null;
  private ArrayList<Integer> mLanguageNotifications = new ArrayList();
  private boolean mLanguageNotificationsDisabled;
  private PendingIntent mPersonalizationIntent;
  private Notification mPersonalizationNotification = null;
  private PendingIntent mSettingsIntent;
  private Notification mSettingsNotification = null;
  private PendingIntent mUpdateAvailableIntent;
  private Notification mUpdateAvailableNotification = null;
  
  private UserNotificationManager(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void createUserEventNotification(UserEventNotificationCreator paramUserEventNotificationCreator)
  {
    postNotification(2130903121, paramUserEventNotificationCreator.createNotification(this.mContext));
    TouchTypePreferences.getInstance(this.mContext).setEventTriggered(paramUserEventNotificationCreator.getEventTriggeredKey(this.mContext.getResources()), 1);
  }
  
  private void displayNotification(int paramInt1, int paramInt2)
  {
    Notification localNotification1 = null;
    PendingIntent localPendingIntent = null;
    Object localObject = null;
    switch (paramInt2)
    {
    }
    for (;;)
    {
      if (localNotification1 != null)
      {
        localNotification1.setLatestEventInfo(this.mContext, this.mContext.getText(2131296302), (CharSequence)localObject, localPendingIntent);
        postNotification(paramInt1, localNotification1);
      }
      do
      {
        do
        {
          return;
          localObject = this.mContext.getText(paramInt1);
          if (this.mUpdateAvailableNotification == null)
          {
            Intent localIntent7 = new Intent("android.intent.action.VIEW", Launcher.getMarketUri(this.mContext, this.mContext.getPackageName(), ReferralSource.SETTINGS));
            this.mUpdateAvailableIntent = PendingIntent.getActivity(this.mContext, 0, localIntent7, 0);
            this.mUpdateAvailableNotification = new Notification(2130838153, (CharSequence)localObject, System.currentTimeMillis());
            Notification localNotification8 = this.mUpdateAvailableNotification;
            localNotification8.flags = (0x10 | localNotification8.flags);
          }
          localNotification1 = this.mUpdateAvailableNotification;
          localPendingIntent = this.mUpdateAvailableIntent;
          break;
        } while (this.mLanguageNotificationsDisabled);
        localObject = this.mContext.getText(paramInt1);
        if (this.mLanguageNotification == null)
        {
          Intent localIntent6 = getIntentForNotification(paramInt2);
          this.mLanguageIntent = PendingIntent.getActivity(this.mContext, 0, localIntent6, 0);
          this.mLanguageNotification = new Notification(2130838153, (CharSequence)localObject, System.currentTimeMillis());
          Notification localNotification7 = this.mLanguageNotification;
          localNotification7.flags = (0x10 | localNotification7.flags);
        }
        localNotification1 = this.mLanguageNotification;
        localPendingIntent = this.mLanguageIntent;
        this.mLanguageNotifications.add(Integer.valueOf(paramInt1));
        break;
      } while (this.mLanguageNotificationsDisabled);
      localObject = this.mContext.getText(paramInt1);
      if (this.mSettingsNotification == null)
      {
        Intent localIntent5 = getIntentForNotification(paramInt2);
        this.mSettingsIntent = PendingIntent.getActivity(this.mContext, 0, localIntent5, 0);
        this.mSettingsNotification = new Notification(2130838153, (CharSequence)localObject, System.currentTimeMillis());
        Notification localNotification6 = this.mSettingsNotification;
        localNotification6.flags = (0x10 | localNotification6.flags);
      }
      localNotification1 = this.mSettingsNotification;
      localPendingIntent = this.mSettingsIntent;
      this.mLanguageNotifications.add(Integer.valueOf(paramInt1));
      continue;
      localObject = this.mContext.getText(paramInt1);
      if (this.mPersonalizationNotification == null)
      {
        Intent localIntent4 = getIntentForNotification(paramInt2);
        this.mPersonalizationIntent = PendingIntent.getActivity(this.mContext, 0, localIntent4, 0);
        this.mPersonalizationNotification = new Notification(2130838153, (CharSequence)localObject, System.currentTimeMillis());
        Notification localNotification5 = this.mPersonalizationNotification;
        localNotification5.flags = (0x10 | localNotification5.flags);
      }
      localNotification1 = this.mPersonalizationNotification;
      localPendingIntent = this.mPersonalizationIntent;
      continue;
      localObject = this.mContext.getString(2131297302);
      if (this.mInstallerNotification == null)
      {
        String str = this.mContext.getPackageName();
        Intent localIntent3 = new Intent();
        localIntent3.setClassName(str, "com.touchtype.Installer");
        localIntent3.addFlags(67174400);
        this.mInstallerIntent = PendingIntent.getActivity(this.mContext, 0, localIntent3, 0);
        this.mInstallerNotification = new Notification(2130838153, (CharSequence)localObject, System.currentTimeMillis());
        Notification localNotification4 = this.mInstallerNotification;
        localNotification4.flags = (0x10 | localNotification4.flags);
      }
      localNotification1 = this.mInstallerNotification;
      localPendingIntent = this.mInstallerIntent;
      continue;
      localObject = this.mContext.getString(paramInt1);
      Intent localIntent2;
      if (this.mHardKBSettingsNotification == null)
      {
        if (Build.VERSION.SDK_INT < 11) {
          break label774;
        }
        localIntent2 = new Intent(this.mContext, TouchTypeKeyboardSettings.class);
        localIntent2.addFlags(67108864);
        localIntent2.putExtra(":android:show_fragment", InputMethodsPreferenceSetting.InputMethodsPreferenceFragment.class.getName());
        localIntent2.putExtra(this.mContext.getString(2131296460), this.mContext.getString(2131296662));
      }
      for (;;)
      {
        this.mHardKBSettingsIntent = PendingIntent.getActivity(this.mContext, 0, localIntent2, 0);
        this.mHardKBSettingsNotification = new Notification(2130838153, (CharSequence)localObject, System.currentTimeMillis());
        Notification localNotification3 = this.mHardKBSettingsNotification;
        localNotification3.flags = (0x10 | localNotification3.flags);
        localNotification1 = this.mHardKBSettingsNotification;
        localPendingIntent = this.mHardKBSettingsIntent;
        break;
        label774:
        localIntent2 = new Intent(this.mContext, InputMethodsPreferenceSetting.InputMethodsPreferenceActivity.class);
      }
      localObject = this.mContext.getText(paramInt1);
      if (this.mCloudServicesNotification == null)
      {
        Intent localIntent1 = getIntentForNotification(paramInt2);
        this.mCloudServicesIntent = PendingIntent.getActivity(this.mContext, 0, localIntent1, 0);
        this.mCloudServicesNotification = new Notification(2130838153, (CharSequence)localObject, System.currentTimeMillis());
        Notification localNotification2 = this.mCloudServicesNotification;
        localNotification2.flags = (0x10 | localNotification2.flags);
      }
      localNotification1 = this.mCloudServicesNotification;
      localPendingIntent = this.mCloudServicesIntent;
    }
  }
  
  public static UserNotificationManager getInstance(Context paramContext)
  {
    try
    {
      if (instance == null) {
        instance = new UserNotificationManager(paramContext.getApplicationContext());
      }
      UserNotificationManager localUserNotificationManager = instance;
      return localUserNotificationManager;
    }
    finally {}
  }
  
  private Intent getIntentForNotification(int paramInt)
  {
    if (isInstallerContext())
    {
      Intent localIntent = new Intent("com.touchtype.installer.LAUNCH_INSTALLER");
      localIntent.addFlags(67174400);
      return localIntent;
    }
    Object localObject;
    if ((paramInt == 1) || (paramInt == 4)) {
      localObject = LanguagePreferenceSetting.LanguagePreferenceActivity.class;
    }
    for (;;)
    {
      return new Intent(this.mContext, (Class)localObject);
      if (paramInt == 2) {
        localObject = RecentPersonalizersPreferenceSetting.RecentPersonalizersPreferenceActivity.class;
      } else if (paramInt == 6) {
        localObject = CloudSetupActivity.class;
      } else {
        localObject = TouchTypeKeyboardSettings.class;
      }
    }
  }
  
  private NotificationManager getNotificationManager()
  {
    return (NotificationManager)this.mContext.getSystemService("notification");
  }
  
  private void postNotification(int paramInt, Notification paramNotification)
  {
    NotificationManager localNotificationManager = getNotificationManager();
    if ((localNotificationManager != null) && (paramNotification != null))
    {
      localNotificationManager.notify(paramInt, paramNotification);
      return;
    }
    LogUtil.w(TAG, "Null Notification Manager service or null notification");
  }
  
  public static Intent rateAction(Context paramContext)
  {
    String str = paramContext.getString(2131296871);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramContext.getPackageName();
    return new Intent("android.intent.action.VIEW", Uri.parse(String.format(str, arrayOfObject)));
  }
  
  public static Intent shareAction(Context paramContext, String paramString)
  {
    Resources localResources = paramContext.getResources();
    String str1 = localResources.getString(2131296303);
    String str2 = String.format(localResources.getString(2131297069), new Object[] { str1 });
    String str3 = String.format(localResources.getString(2131297068), new Object[] { str1 });
    Intent localIntent1 = new Intent("android.intent.action.SEND");
    localIntent1.setType("text/plain");
    localIntent1.putExtra("android.intent.extra.SUBJECT", str2);
    localIntent1.putExtra("android.intent.extra.TEMPLATE", str2);
    localIntent1.putExtra("android.intent.extra.TEXT", paramString);
    Intent localIntent2 = PrioritisedChooserActivity.createChooser(paramContext, localIntent1, str3);
    localIntent2.setFlags(268435456);
    return localIntent2;
  }
  
  public void clearNotification(int paramInt)
  {
    NotificationManager localNotificationManager = getNotificationManager();
    if (localNotificationManager != null) {
      try
      {
        localNotificationManager.cancel(paramInt);
        return;
      }
      catch (NullPointerException localNullPointerException)
      {
        LogUtil.w(TAG, "NPE from Notification Manager service");
        return;
      }
    }
    LogUtil.w(TAG, "Null Notification Manager service");
  }
  
  public void clearUnableToLoadLanguagePacks()
  {
    clearNotification(2131297083);
  }
  
  public void disableNotifications()
  {
    NotificationManager localNotificationManager = getNotificationManager();
    if (localNotificationManager != null)
    {
      Iterator localIterator = this.mLanguageNotifications.iterator();
      while (localIterator.hasNext()) {
        localNotificationManager.cancel(((Integer)localIterator.next()).intValue());
      }
    }
    LogUtil.w(TAG, "Null Notification Manager service");
    this.mLanguageNotifications.clear();
    this.mLanguageNotificationsDisabled = true;
  }
  
  public void displayHardKeyboardSettingsNotification(int paramInt)
  {
    displayNotification(paramInt, 5);
  }
  
  public void displayInstallerNotification()
  {
    displayNotification(2131297302, 3);
  }
  
  public void displayLanguageNotification(int paramInt)
  {
    displayNotification(paramInt, 1);
  }
  
  public void displayPersonalizationNotification(int paramInt)
  {
    displayNotification(paramInt, 2);
  }
  
  public void displayReEnableCloudServicesNotification()
  {
    displayNotification(2131297244, 6);
  }
  
  public void displaySettingsNotification(int paramInt)
  {
    displayNotification(paramInt, 4);
  }
  
  public void displayUserEventNotification(UserEventNotificationCreator paramUserEventNotificationCreator)
  {
    createUserEventNotification(paramUserEventNotificationCreator);
  }
  
  public void downloadFailed()
  {
    displayNotification(2131297085, 1);
  }
  
  public void enableNotifications()
  {
    this.mLanguageNotificationsDisabled = false;
  }
  
  public boolean isInstallerContext()
  {
    return this.mInstallerContext != null;
  }
  
  public void onDestroy()
  {
    NotificationManager localNotificationManager = getNotificationManager();
    if (localNotificationManager != null) {
      localNotificationManager.cancelAll();
    }
    for (;;)
    {
      this.mLanguageNotification = null;
      this.mSettingsNotification = null;
      this.mPersonalizationNotification = null;
      this.mInstallerNotification = null;
      this.mHardKBSettingsNotification = null;
      this.mLanguageIntent = null;
      this.mSettingsIntent = null;
      this.mPersonalizationIntent = null;
      this.mInstallerIntent = null;
      this.mHardKBSettingsIntent = null;
      this.mUpdateAvailableIntent = null;
      this.mUpdateAvailableNotification = null;
      return;
      LogUtil.w(TAG, "Null Notification Manager service");
    }
  }
  
  public void setInstallerContext(String paramString)
  {
    this.mInstallerContext = paramString;
  }
  
  public void unableToLoadLanguagePacks()
  {
    displayNotification(2131297083, 1);
  }
  
  public void unableToSaveDynamic()
  {
    displayNotification(2131297084, 1);
  }
  
  public void updateAvailable()
  {
    displayNotification(2131297086, 1);
  }
  
  public void updatedAppAvaialble()
  {
    displayNotification(2131297294, 7);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.social.UserNotificationManager
 * JD-Core Version:    0.7.0.1
 */