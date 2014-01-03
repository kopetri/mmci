package com.touchtype.report.json;

import android.content.Context;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;
import com.touchtype.licensing.LicenseeUtil;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype_fluency.SwiftKeySDK;

final class Software
{
  @SerializedName("app")
  private App mApp;
  @SerializedName("sdk")
  private Sdk mSdk;
  
  public static Software newInstance(Context paramContext)
  {
    Software localSoftware = new Software();
    localSoftware.mApp = App.newInstance(paramContext);
    localSoftware.mSdk = Sdk.newInstance(paramContext);
    return localSoftware;
  }
  
  static final class App
  {
    @SerializedName("name")
    private String mName;
    @SerializedName("vendorId")
    private String mVendorId;
    
    public static App newInstance(Context paramContext)
    {
      App localApp = new App();
      String str = LicenseeUtil.getCustomer(paramContext);
      localApp.mName = ProductConfiguration.getProductName(paramContext);
      if (Strings.isNullOrEmpty(str)) {
        str = null;
      }
      localApp.mVendorId = str;
      return localApp;
    }
  }
  
  static final class Sdk
  {
    @SerializedName("sourceHash")
    private String mSourceHash;
    @SerializedName("version")
    private String mVersion;
    
    public static Sdk newInstance(Context paramContext)
    {
      Sdk localSdk = new Sdk();
      localSdk.mVersion = SwiftKeySDK.getVersion();
      localSdk.mSourceHash = SwiftKeySDK.getSourceVersion();
      return localSdk;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.Software
 * JD-Core Version:    0.7.0.1
 */