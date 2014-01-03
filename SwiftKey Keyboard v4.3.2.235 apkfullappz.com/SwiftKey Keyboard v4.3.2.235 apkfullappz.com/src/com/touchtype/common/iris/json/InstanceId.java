package com.touchtype.common.iris.json;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.google.gson.annotations.SerializedName;
import com.touchtype.util.DeviceUtils;
import com.touchtype.util.EnvironmentInfoUtil;
import com.touchtype.util.InstallationId;

final class InstanceId
{
  @SerializedName("deviceId")
  private String mDeviceId;
  @SerializedName("installId")
  private String mInstallId;
  @SerializedName("productId")
  private String mProductId;
  @SerializedName("productVersion")
  private String mProductVersion;
  @SerializedName("timestamp")
  private long mTimestamp;
  
  public static InstanceId newInstance(Context paramContext)
  {
    InstanceId localInstanceId = new InstanceId();
    PackageInfo localPackageInfo = EnvironmentInfoUtil.getPackageInfo(paramContext);
    String str1;
    if (localPackageInfo == null)
    {
      str1 = "";
      localInstanceId.mProductId = str1;
      if (localPackageInfo != null) {
        break label76;
      }
    }
    label76:
    for (String str2 = "";; str2 = localPackageInfo.versionName)
    {
      localInstanceId.mProductVersion = str2;
      localInstanceId.mDeviceId = DeviceUtils.getDeviceId(paramContext);
      localInstanceId.mInstallId = InstallationId.getId(paramContext);
      localInstanceId.mTimestamp = (System.currentTimeMillis() / 1000L);
      return localInstanceId;
      str1 = localPackageInfo.packageName;
      break;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.iris.json.InstanceId
 * JD-Core Version:    0.7.0.1
 */