package com.touchtype.report.json;

import android.content.Context;
import com.google.gson.annotations.SerializedName;

public final class ForceClose
{
  @SerializedName("deviceInfo")
  private DeviceInfo mDeviceInfo;
  @SerializedName("software")
  private Software mSoftware;
  @SerializedName("stacktrace")
  private String mStacktrace;
  
  public static ForceClose newInstance(Context paramContext, String paramString)
  {
    ForceClose localForceClose = new ForceClose();
    localForceClose.mDeviceInfo = DeviceInfo.newInstance(paramContext);
    localForceClose.mSoftware = Software.newInstance(paramContext);
    localForceClose.mStacktrace = paramString;
    return localForceClose;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.ForceClose
 * JD-Core Version:    0.7.0.1
 */