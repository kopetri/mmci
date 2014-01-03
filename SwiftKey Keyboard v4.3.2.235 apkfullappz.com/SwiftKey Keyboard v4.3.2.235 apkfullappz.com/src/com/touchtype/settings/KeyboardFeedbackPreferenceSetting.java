package com.touchtype.settings;

import android.annotation.TargetApi;
import android.os.Bundle;

public final class KeyboardFeedbackPreferenceSetting
{
  public static class KeyboardFeedbackPreferenceActivity
    extends TouchTypeKeyboardSettings.IntentSafePreferenceActivity
  {
    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      new KeyboardFeedbackPreferenceConfiguration(this).setup(this);
    }
  }
  
  @TargetApi(11)
  public static class KeyboardFeedbackPreferenceFragment
    extends TouchTypeKeyboardSettings.IntentSafePreferenceFragment
  {
    private KeyboardFeedbackPreferenceConfiguration mConfig;
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      this.mConfig.setup((TouchTypeKeyboardSettings)getActivity());
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = new KeyboardFeedbackPreferenceConfiguration(this);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.KeyboardFeedbackPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */