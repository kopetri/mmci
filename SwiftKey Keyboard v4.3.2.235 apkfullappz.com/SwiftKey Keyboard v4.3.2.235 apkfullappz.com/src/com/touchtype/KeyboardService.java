package com.touchtype;

import android.content.res.Resources;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.social.DayNotificationXMLLoader;

public class KeyboardService
  extends TouchTypeSoftKeyboard
{
  private boolean botherMe(SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    boolean bool1 = paramSwiftKeyPreferences.getBoolean("do_not_bother_me", false);
    boolean bool2 = false;
    if (!bool1) {
      bool2 = true;
    }
    return bool2;
  }
  
  protected boolean createNotificationIfRequired()
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(getApplicationContext());
    long l = localTouchTypePreferences.getLong("installation_time", -1L);
    if ((botherMe(localTouchTypePreferences)) && (l != -1L) && (!getResources().getBoolean(2131492920)))
    {
      new DayNotificationXMLLoader(this).loadNotifications(l);
      return true;
    }
    return false;
  }
  
  protected void onLicenseAndNotificationCheck()
  {
    createNotificationIfRequired();
    super.onLicenseAndNotificationCheck();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.KeyboardService
 * JD-Core Version:    0.7.0.1
 */