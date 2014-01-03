package com.touchtype.social;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;

public class NotificationActionLauncherAndLogger
  extends Activity
{
  private static final String TAG = NotificationActionLauncherAndLogger.class.getSimpleName();
  
  private boolean appRated(TouchTypePreferences paramTouchTypePreferences)
  {
    return paramTouchTypePreferences.getBoolean("app_rated", false);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(this);
    Intent localIntent1 = getIntent();
    String str = localIntent1.getStringExtra("user_event_triggered_key");
    int i = localIntent1.getIntExtra("user_event_triggered_action", 0);
    if (i == 6)
    {
      if (appRated(localTouchTypePreferences)) {
        return;
      }
      localTouchTypePreferences.putBoolean("app_rated", true);
    }
    localTouchTypePreferences.setEventTriggered(str, i);
    Intent localIntent2 = (Intent)localIntent1.getExtras().getParcelable("notification_intent");
    try
    {
      startActivity(localIntent2);
      finish();
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "Cannot find activity to handle the intent.", localActivityNotFoundException);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.social.NotificationActionLauncherAndLogger
 * JD-Core Version:    0.7.0.1
 */