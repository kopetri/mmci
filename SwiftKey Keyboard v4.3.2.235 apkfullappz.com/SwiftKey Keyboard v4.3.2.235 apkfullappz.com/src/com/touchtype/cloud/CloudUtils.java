package com.touchtype.cloud;

import android.content.Context;
import android.os.Build;
import android.util.Pair;
import java.util.HashMap;
import java.util.Map;

public final class CloudUtils
{
  private static final Map<Pair<String, String>, String> deviceNameMap;
  
  static
  {
    HashMap localHashMap = new HashMap();
    deviceNameMap = localHashMap;
    localHashMap.put(new Pair("samsung", "GT-I9100"), "Samsung Galaxy S2");
    deviceNameMap.put(new Pair("samsung", "GT-I9300"), "Samsung Galaxy S3");
    deviceNameMap.put(new Pair("samsung", "GT-I9505"), "Samsung Galaxy S4");
    deviceNameMap.put(new Pair("samsung", "SHV-E300S"), "Samsung Galaxy S4");
    deviceNameMap.put(new Pair("samsung", "GT-N7000"), "Samsung Galaxy Note");
    deviceNameMap.put(new Pair("samsung", "GT-N7100"), "Samsung Galaxy Note 2");
    deviceNameMap.put(new Pair("samsung", "GT-P7510"), "Samsung Galaxy Tab 10.1");
    deviceNameMap.put(new Pair("samsung", "GT-S5830"), "Samsung Galaxy Ace");
    deviceNameMap.put(new Pair("Sony", "C6603"), "Sony Xperia Z");
  }
  
  public static String getDefaultDeviceName()
  {
    Pair localPair = new Pair(Build.MANUFACTURER, Build.MODEL);
    if (deviceNameMap.containsKey(localPair)) {
      return (String)deviceNameMap.get(localPair);
    }
    return Build.MODEL;
  }
  
  public static long getDelayRequiredUntilNextSync(long paramLong)
  {
    long l = System.currentTimeMillis() - paramLong;
    if (l > getSyncInterval(0)) {
      return 0L;
    }
    return getSyncInterval(0) - l;
  }
  
  public static String getGoogleAuthClientId(Context paramContext)
  {
    return paramContext.getString(2131296326);
  }
  
  public static String getGoogleAuthScope(Context paramContext)
  {
    String str = paramContext.getString(2131296327);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramContext.getString(2131296326);
    return String.format(str, arrayOfObject);
  }
  
  public static long getSyncInterval(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return -1L;
    case 0: 
      return 3600000L;
    case 1: 
      return 86400000L;
    }
    return 604800000L;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.CloudUtils
 * JD-Core Version:    0.7.0.1
 */