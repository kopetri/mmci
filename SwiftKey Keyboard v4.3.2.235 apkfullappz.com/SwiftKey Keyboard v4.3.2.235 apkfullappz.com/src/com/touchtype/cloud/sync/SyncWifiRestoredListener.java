package com.touchtype.cloud.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.touchtype.JobScheduler;
import com.touchtype.cloud.CloudService;
import com.touchtype.util.NetworkUtil;

public class SyncWifiRestoredListener
  extends BroadcastReceiver
{
  private boolean requestedSync = false;
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) && (!this.requestedSync) && (NetworkUtil.isWifiActive(paramContext)))
    {
      this.requestedSync = true;
      new JobScheduler().cancelJob(new SyncScheduledJob(), paramContext);
      Intent localIntent = new Intent(paramContext, CloudService.class);
      localIntent.setAction("CloudService.performSync");
      localIntent.putExtra("CloudService.wifiRestoredSync", true);
      paramContext.startService(localIntent);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.sync.SyncWifiRestoredListener
 * JD-Core Version:    0.7.0.1
 */