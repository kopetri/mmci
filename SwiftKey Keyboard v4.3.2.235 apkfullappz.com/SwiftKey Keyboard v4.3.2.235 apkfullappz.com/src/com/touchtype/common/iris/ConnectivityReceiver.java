package com.touchtype.common.iris;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.touchtype.util.NetworkUtil;

public class ConnectivityReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (("android.net.conn.CONNECTIVITY_CHANGE".equals(paramIntent.getAction())) && (NetworkUtil.isInternetAvailable(paramContext))) {
      paramContext.startService(IrisDataSenderService.retryCachedDataIntent(paramContext));
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.iris.ConnectivityReceiver
 * JD-Core Version:    0.7.0.1
 */