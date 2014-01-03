package com.google.android.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GCMBroadcastReceiver
  extends BroadcastReceiver
{
  private static boolean mReceiverSet = false;
  
  static final String getDefaultIntentServiceClassName(Context paramContext)
  {
    return paramContext.getPackageName() + ".GCMIntentService";
  }
  
  protected String getGCMIntentServiceClassName(Context paramContext)
  {
    return getDefaultIntentServiceClassName(paramContext);
  }
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    new StringBuilder("onReceive: ").append(paramIntent.getAction()).toString();
    if (!mReceiverSet)
    {
      mReceiverSet = true;
      String str2 = getClass().getName();
      if (!str2.equals(GCMBroadcastReceiver.class.getName())) {
        GCMRegistrar.setRetryReceiverClassName(str2);
      }
    }
    String str1 = getGCMIntentServiceClassName(paramContext);
    new StringBuilder("GCM IntentService class: ").append(str1).toString();
    GCMBaseIntentService.runIntentInService(paramContext, paramIntent, str1);
    setResult(-1, null, null);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gcm.GCMBroadcastReceiver
 * JD-Core Version:    0.7.0.1
 */