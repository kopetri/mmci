package com.touchtype.settings;

import android.annotation.TargetApi;
import android.os.Bundle;

public final class AdvancedPreferenceSetting
{
  public static class AdvancedPreferenceActivity
    extends TouchTypeKeyboardSettings.IntentSafePreferenceActivity
  {
    private AdvancedPreferenceConfiguration mConfig;
    
    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = new AdvancedPreferenceConfiguration(this);
      this.mConfig.setup(this);
    }
    
    protected void onResume()
    {
      super.onResume();
      this.mConfig.update();
    }
    
    public void onStart()
    {
      super.onStart();
      this.mConfig.onStart();
    }
    
    public void onStop()
    {
      super.onStop();
      this.mConfig.onStop();
    }
  }
  
  @TargetApi(11)
  public static class AdvancedPreferenceFragment
    extends TouchTypeKeyboardSettings.IntentSafePreferenceFragment
  {
    private AdvancedPreferenceConfiguration mConfig;
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      this.mConfig.setup((TouchTypeKeyboardSettings)getActivity());
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = new AdvancedPreferenceConfiguration(this);
    }
    
    public void onResume()
    {
      super.onResume();
      this.mConfig.update();
    }
    
    public void onStart()
    {
      super.onStart();
      setCurrentFragment(this);
      this.mConfig.onStart();
    }
    
    public void onStop()
    {
      super.onStop();
      this.mConfig.onStop();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.AdvancedPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */