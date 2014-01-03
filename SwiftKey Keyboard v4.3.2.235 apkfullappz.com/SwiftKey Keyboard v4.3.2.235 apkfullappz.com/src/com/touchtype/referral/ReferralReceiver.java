package com.touchtype.referral;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReferralReceiver
  extends BroadcastReceiver
{
  private static final String TAG = ReferralReceiver.class.getSimpleName();
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    localTouchTypePreferences.putBoolean("installed_from_playstore", true);
    Bundle localBundle = paramIntent.getExtras();
    if (localBundle == null) {
      return;
    }
    String str1 = localBundle.getString("referrer");
    LogUtil.w(TAG, "Got " + localBundle.keySet().size() + " items in the Bundle");
    Iterator localIterator = localBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str3 = (String)localIterator.next();
      LogUtil.w(TAG, "Intent item = " + str3);
    }
    if (Strings.isNullOrEmpty(str1))
    {
      LogUtil.e(TAG, "No referrer string found!");
      return;
    }
    HashMap localHashMap = new HashMap();
    try
    {
      String[] arrayOfString1 = paramIntent.getStringExtra("referrer").split("&");
      int i = arrayOfString1.length;
      for (int j = 0; j < i; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split("=");
        localHashMap.put(URLDecoder.decode(arrayOfString2[0], Charsets.UTF_8.name()), URLDecoder.decode(arrayOfString2[1], Charsets.UTF_8.name()));
      }
      str2 = (String)localHashMap.get("utm_source");
    }
    catch (Exception localException)
    {
      LogUtil.e(TAG, "Error parsing referral string! " + str1);
      return;
    }
    String str2;
    LogUtil.w(TAG, "ReferrerString: " + str1);
    LogUtil.w(TAG, "UTM_SOURCE: " + str2);
    localTouchTypePreferences.setReferralData(str2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.referral.ReferralReceiver
 * JD-Core Version:    0.7.0.1
 */