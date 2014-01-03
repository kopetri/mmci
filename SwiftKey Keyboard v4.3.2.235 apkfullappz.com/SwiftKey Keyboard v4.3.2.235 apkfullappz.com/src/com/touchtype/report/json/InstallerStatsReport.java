package com.touchtype.report.json;

import android.content.Context;
import com.google.gson.annotations.SerializedName;

public final class InstallerStatsReport
{
  @SerializedName("deviceInfo")
  private final DeviceInfo mDeviceInfo;
  @SerializedName("installer")
  private final InstallerStats mInstaller;
  @SerializedName("marketing")
  private final ReferrerInfo mReferrerInfo;
  @SerializedName("software")
  private final Software mSoftware;
  
  public InstallerStatsReport(Context paramContext)
  {
    this.mInstaller = InstallerStats.newInstance(paramContext);
    this.mDeviceInfo = DeviceInfo.newInstance(paramContext);
    this.mSoftware = Software.newInstance(paramContext);
    this.mReferrerInfo = ReferrerInfo.newInstance(paramContext);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.InstallerStatsReport
 * JD-Core Version:    0.7.0.1
 */