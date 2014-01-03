package com.touchtype_fluency.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.touchtype.util.LogUtil;
import com.touchtype.util.NetworkUtil;
import com.touchtype_fluency.service.FluencyServiceImpl;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ConnectivityReceiver
  extends BroadcastReceiver
{
  private static final Set<ConnectivityListener> LISTENERS = new CopyOnWriteArraySet();
  private static final String TAG = ConnectivityReceiver.class.getSimpleName();
  
  public static boolean addListener(ConnectivityListener paramConnectivityListener)
  {
    return LISTENERS.add(paramConnectivityListener);
  }
  
  public static int numberOfListeners()
  {
    return LISTENERS.size();
  }
  
  public static boolean removeListener(ConnectivityListener paramConnectivityListener)
  {
    return LISTENERS.remove(paramConnectivityListener);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) && (NetworkUtil.isInternetAvailable(paramContext)))
    {
      if (NetworkUtil.isReallyConnected(paramContext)) {
        break label52;
      }
      LogUtil.e(TAG, "Not really connected!" + NetworkUtil.getConnectionDetailedState(paramContext));
    }
    for (;;)
    {
      return;
      label52:
      FluencyServiceImpl.rerunActionsNowConnected(paramContext);
      Iterator localIterator = LISTENERS.iterator();
      while (localIterator.hasNext()) {
        ((ConnectivityListener)localIterator.next()).handleConnectionAvailable();
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.receiver.ConnectivityReceiver
 * JD-Core Version:    0.7.0.1
 */