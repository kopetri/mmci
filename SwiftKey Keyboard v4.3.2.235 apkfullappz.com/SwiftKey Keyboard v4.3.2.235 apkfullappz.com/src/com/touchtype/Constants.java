package com.touchtype;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Constants
{
  public static final class Installer
  {
    public static final Map<Integer, String> INSTALLER_STEPS = ;
    
    private static Map<Integer, String> createMap()
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put(Integer.valueOf(-2), "accept_eula");
      localHashMap.put(Integer.valueOf(0), "choose_language");
      localHashMap.put(Integer.valueOf(1), "enable_swiftkey");
      localHashMap.put(Integer.valueOf(2), "set_as_default");
      localHashMap.put(Integer.valueOf(4), "set_flow_enabled");
      localHashMap.put(Integer.valueOf(3), "enable_cloud_services");
      localHashMap.put(Integer.valueOf(5), "steps_done");
      return Collections.unmodifiableMap(localHashMap);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.Constants
 * JD-Core Version:    0.7.0.1
 */