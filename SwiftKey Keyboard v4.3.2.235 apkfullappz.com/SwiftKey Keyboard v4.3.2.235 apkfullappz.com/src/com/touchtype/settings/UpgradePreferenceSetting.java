package com.touchtype.settings;

import android.os.Bundle;
import com.touchtype.Launcher;
import com.touchtype.referral.ReferralSource;

public final class UpgradePreferenceSetting
{
  public static class UpgradePreferenceActivity
    extends TouchTypeKeyboardSettings.IntentSafePreferenceActivity
  {
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      Launcher.launchAndroidMarket(this, ReferralSource.SETTINGS);
      finish();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.UpgradePreferenceSetting
 * JD-Core Version:    0.7.0.1
 */