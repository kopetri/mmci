package com.touchtype.referral;

import android.content.Context;

public abstract interface ReferrerInfo
{
  public abstract String campaign();
  
  public abstract String medium();
  
  public abstract String source(Context paramContext, ReferralSource paramReferralSource);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.referral.ReferrerInfo
 * JD-Core Version:    0.7.0.1
 */