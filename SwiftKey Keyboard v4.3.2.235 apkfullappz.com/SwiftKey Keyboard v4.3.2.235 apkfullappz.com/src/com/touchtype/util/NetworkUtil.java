package com.touchtype.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;

public final class NetworkUtil
{
  private static final String TAG = NetworkUtil.class.getSimpleName();
  
  public static NetworkInfo.DetailedState getConnectionDetailedState(Context paramContext)
  {
    NetworkInfo localNetworkInfo = getNetworkInfo(paramContext);
    if (localNetworkInfo == null) {
      return NetworkInfo.DetailedState.DISCONNECTED;
    }
    return localNetworkInfo.getDetailedState();
  }
  
  public static NetworkState getInternetAvailability(Context paramContext)
  {
    try
    {
      NetworkState localNetworkState = isConnected(getNetworkInfo(paramContext));
      return localNetworkState;
    }
    catch (SecurityException localSecurityException)
    {
      LogUtil.e(TAG, "Permission ACCESS_NETWORK_STATE to read network state is denied.", localSecurityException);
    }
    return NetworkState.IMPLIED;
  }
  
  private static NetworkInfo getNetworkInfo(Context paramContext)
  {
    return ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
  }
  
  private static NetworkState isConnected(NetworkInfo paramNetworkInfo)
  {
    if ((paramNetworkInfo == null) || (!paramNetworkInfo.isConnected())) {
      return NetworkState.NONE;
    }
    switch (paramNetworkInfo.getType())
    {
    default: 
      return NetworkState.OTHER;
    case 0: 
      return NetworkState.MOBILE;
    }
    return NetworkState.WIFI;
  }
  
  public static boolean isInternetAvailable(Context paramContext)
  {
    return getInternetAvailability(paramContext) != NetworkState.NONE;
  }
  
  public static boolean isReallyConnected(Context paramContext)
  {
    return getConnectionDetailedState(paramContext) == NetworkInfo.DetailedState.CONNECTED;
  }
  
  public static boolean isWifiActive(Context paramContext)
  {
    NetworkInfo localNetworkInfo = getNetworkInfo(paramContext);
    return (localNetworkInfo != null) && (localNetworkInfo.getType() == 1);
  }
  
  public static enum NetworkState
  {
    static
    {
      IMPLIED = new NetworkState("IMPLIED", 1);
      WIFI = new NetworkState("WIFI", 2);
      MOBILE = new NetworkState("MOBILE", 3);
      OTHER = new NetworkState("OTHER", 4);
      NetworkState[] arrayOfNetworkState = new NetworkState[5];
      arrayOfNetworkState[0] = NONE;
      arrayOfNetworkState[1] = IMPLIED;
      arrayOfNetworkState[2] = WIFI;
      arrayOfNetworkState[3] = MOBILE;
      arrayOfNetworkState[4] = OTHER;
      $VALUES = arrayOfNetworkState;
    }
    
    private NetworkState() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.NetworkUtil
 * JD-Core Version:    0.7.0.1
 */