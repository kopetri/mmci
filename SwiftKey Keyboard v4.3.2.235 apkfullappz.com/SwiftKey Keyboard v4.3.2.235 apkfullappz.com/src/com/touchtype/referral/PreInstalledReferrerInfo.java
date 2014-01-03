package com.touchtype.referral;

import android.content.Context;
import com.touchtype.preferences.TouchTypePreferences;

public class PreInstalledReferrerInfo
  implements ReferrerInfo
{
  public String campaign()
  {
    return "sk";
  }
  
  public String medium()
  {
    return "upgrade";
  }
  
  public String source(Context paramContext, ReferralSource paramReferralSource)
  {
    return TouchTypePreferences.getInstance(paramContext).getReferralData();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.referral.PreInstalledReferrerInfo
 * JD-Core Version:    0.7.0.1
 */