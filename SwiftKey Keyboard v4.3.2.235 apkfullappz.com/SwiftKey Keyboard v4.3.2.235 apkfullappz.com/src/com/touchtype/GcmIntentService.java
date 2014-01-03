package com.touchtype;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.touchtype.cloud.CloudService;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;

public class GcmIntentService
  extends IntentService
{
  private static final String TAG = GcmIntentService.class.getSimpleName();
  
  public GcmIntentService()
  {
    super("GcmIntentService");
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    for (;;)
    {
      try
      {
        Bundle localBundle = paramIntent.getExtras();
        String str = GoogleCloudMessaging.getInstance(this).getMessageType(paramIntent);
        if (!localBundle.isEmpty())
        {
          if ("send_error".equals(str)) {
            LogUtil.e(TAG, "GCM MESSAGE ERROR!");
          }
        }
        else {
          return;
        }
        if ("deleted_messages".equals(str))
        {
          LogUtil.e(TAG, "GCM MESSAGE DELETE!");
          continue;
        }
        if (!"gcm".equals(str)) {
          continue;
        }
      }
      finally
      {
        GcmBroadcastReceiver.completeWakefulIntent(paramIntent);
      }
      onMessage(getApplicationContext(), paramIntent);
    }
  }
  
  protected void onMessage(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getStringExtra("collapse_key");
    Intent localIntent;
    if ((!TextUtils.isEmpty(str)) && (str.equals("sync")))
    {
      TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
      localIntent = new Intent(paramContext, CloudService.class);
      if (localTouchTypePreferences.getSyncFrequency() != 0) {
        break label71;
      }
      localTouchTypePreferences.setSyncPendingPull(true);
      localIntent.setAction("CloudService.performSync");
    }
    for (;;)
    {
      startService(localIntent);
      return;
      label71:
      localIntent.setAction("CloudService.unregisterFromGcm");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.GcmIntentService
 * JD-Core Version:    0.7.0.1
 */