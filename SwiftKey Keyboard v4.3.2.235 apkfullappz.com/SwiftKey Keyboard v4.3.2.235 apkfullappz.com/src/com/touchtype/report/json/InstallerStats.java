package com.touchtype.report.json;

import android.content.Context;
import com.google.gson.annotations.SerializedName;
import com.touchtype.installer.InstallerPreferences;
import com.touchtype.resources.ProductConfiguration;

final class InstallerStats
{
  @SerializedName("cloud")
  private Cloud mCloud;
  @SerializedName("flow")
  private Flow mFlow;
  @SerializedName("progress")
  private String mProgress;
  @SerializedName("stepChooseLang")
  private String mStepChooseLanguage;
  
  public static InstallerStats newInstance(Context paramContext)
  {
    InstallerPreferences localInstallerPreferences = InstallerPreferences.newInstance(paramContext);
    InstallerStats localInstallerStats = new InstallerStats();
    localInstallerStats.mProgress = localInstallerPreferences.getInstallerProgressStat();
    localInstallerStats.mStepChooseLanguage = localInstallerPreferences.getInstallerStepChooseLang();
    if (ProductConfiguration.isFlowBuild(paramContext)) {}
    for (Flow localFlow = Flow.newInstance(localInstallerPreferences);; localFlow = null)
    {
      localInstallerStats.mFlow = localFlow;
      boolean bool = ProductConfiguration.isCloudBuild(paramContext);
      Cloud localCloud = null;
      if (bool) {
        localCloud = Cloud.newInstance(localInstallerPreferences);
      }
      localInstallerStats.mCloud = localCloud;
      return localInstallerStats;
    }
  }
  
  static final class Cloud
  {
    @SerializedName("deviceId")
    private String mDeviceId;
    @SerializedName("enabled")
    private boolean mEnabled;
    @SerializedName("marketingAllowed")
    private boolean mMarketingAllowed;
    @SerializedName("personalisedGmail")
    private Boolean mPersonalisedGmail;
    
    public static Cloud newInstance(InstallerPreferences paramInstallerPreferences)
    {
      Cloud localCloud = new Cloud();
      localCloud.mEnabled = paramInstallerPreferences.getInstallerCloudEnabled();
      localCloud.mMarketingAllowed = paramInstallerPreferences.getInstallerCloudMarketingEnabled();
      localCloud.mDeviceId = paramInstallerPreferences.getInstallerCloudDeviceId();
      if (paramInstallerPreferences.isCloudPersonalisedGmailSetup()) {}
      for (Boolean localBoolean = Boolean.valueOf(paramInstallerPreferences.getInstallerCloudPersonalisedGmail());; localBoolean = null)
      {
        localCloud.mPersonalisedGmail = localBoolean;
        return localCloud;
      }
    }
  }
  
  static final class Flow
  {
    @SerializedName("enabled")
    private boolean mEnabled;
    
    public static Flow newInstance(InstallerPreferences paramInstallerPreferences)
    {
      Flow localFlow = new Flow();
      localFlow.mEnabled = paramInstallerPreferences.getInstallerFlowEnabled();
      return localFlow;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.InstallerStats
 * JD-Core Version:    0.7.0.1
 */