package com.touchtype.report.json;

import android.content.Context;
import com.google.gson.annotations.SerializedName;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.resources.ProductConfiguration;

public final class ReferrerInfo
{
  @SerializedName("marketReferrer")
  protected String mAndroidMarketReferrer;
  @SerializedName("marketName")
  protected String mMarketName;
  
  public static ReferrerInfo newInstance(Context paramContext)
  {
    ReferrerInfo localReferrerInfo = new ReferrerInfo();
    localReferrerInfo.mAndroidMarketReferrer = TouchTypePreferences.getInstance(paramContext).getReferralData();
    localReferrerInfo.mMarketName = ProductConfiguration.getMarketName(paramContext);
    return localReferrerInfo;
  }
  
  public String getAndroidMarketReferrer()
  {
    return this.mAndroidMarketReferrer;
  }
  
  public String getMarketName()
  {
    return this.mMarketName;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.ReferrerInfo
 * JD-Core Version:    0.7.0.1
 */