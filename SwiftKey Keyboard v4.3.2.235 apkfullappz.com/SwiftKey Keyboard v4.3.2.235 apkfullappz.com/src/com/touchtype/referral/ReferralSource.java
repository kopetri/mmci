package com.touchtype.referral;

public enum ReferralSource
{
  static
  {
    EXPIRY_WARNING = new ReferralSource("EXPIRY_WARNING", 1);
    TRIAL_EXPIRED = new ReferralSource("TRIAL_EXPIRED", 2);
    WRONG_PACKAGE_FOR_DEVICE = new ReferralSource("WRONG_PACKAGE_FOR_DEVICE", 3);
    BETA_EXPIRED = new ReferralSource("BETA_EXPIRED", 4);
    ReferralSource[] arrayOfReferralSource = new ReferralSource[5];
    arrayOfReferralSource[0] = SETTINGS;
    arrayOfReferralSource[1] = EXPIRY_WARNING;
    arrayOfReferralSource[2] = TRIAL_EXPIRED;
    arrayOfReferralSource[3] = WRONG_PACKAGE_FOR_DEVICE;
    arrayOfReferralSource[4] = BETA_EXPIRED;
    $VALUES = arrayOfReferralSource;
  }
  
  private ReferralSource() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.referral.ReferralSource
 * JD-Core Version:    0.7.0.1
 */