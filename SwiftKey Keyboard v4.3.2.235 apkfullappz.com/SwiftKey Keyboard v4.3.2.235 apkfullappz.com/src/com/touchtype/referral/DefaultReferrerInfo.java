package com.touchtype.referral;

import android.content.Context;

public class DefaultReferrerInfo
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
    switch (1.$SwitchMap$com$touchtype$referral$ReferralSource[paramReferralSource.ordinal()])
    {
    default: 
      return "unknown";
    case 1: 
      return "settings";
    case 2: 
      return "expirywarning";
    case 3: 
      return "trialexpired";
    case 4: 
      return "wrongpackage";
    }
    return "betaexpired";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.referral.DefaultReferrerInfo
 * JD-Core Version:    0.7.0.1
 */