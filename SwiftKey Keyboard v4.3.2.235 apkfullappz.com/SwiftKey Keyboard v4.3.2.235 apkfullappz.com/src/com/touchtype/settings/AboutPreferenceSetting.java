package com.touchtype.settings;

import android.annotation.TargetApi;
import android.os.Bundle;

public final class AboutPreferenceSetting
{
  public static class AboutPreferenceActivity
    extends TouchTypeKeyboardSettings.IntentSafePreferenceActivity
  {
    private AboutPreferenceConfiguration mConfig;
    
    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = ((AboutPreferenceConfiguration)getLastNonConfigurationInstance());
      if (this.mConfig == null) {
        this.mConfig = new AboutPreferenceConfiguration(this);
      }
      for (;;)
      {
        this.mConfig.addPreference(2131034788);
        this.mConfig.setup(this);
        return;
        this.mConfig.setActivity(this);
      }
    }
    
    public void onDestroy()
    {
      super.onDestroy();
      this.mConfig.cleanup();
    }
    
    public void onResume()
    {
      super.onResume();
      this.mConfig.onResume();
    }
    
    public Object onRetainNonConfigurationInstance()
    {
      return this.mConfig;
    }
  }
  
  @TargetApi(11)
  public static class AboutPreferenceFragment
    extends TouchTypeKeyboardSettings.PersistentConfigPreferenceFragment<AboutPreferenceConfiguration>
  {
    private AboutPreferenceConfiguration mConfig;
    
    public AboutPreferenceConfiguration createNewConfig()
    {
      return new AboutPreferenceConfiguration(this);
    }
    
    public String getFragmentTag()
    {
      return "aboutPreferenceConfigFragment";
    }
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      this.mConfig.setup((TouchTypeKeyboardSettings)getActivity());
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = ((AboutPreferenceConfiguration)fetchPreferenceConfig());
      this.mConfig.addPreference(2131034788);
    }
    
    public void onDestroy()
    {
      if (this.mConfig != null) {
        this.mConfig.cleanup();
      }
      super.onDestroy();
    }
    
    public void onResume()
    {
      super.onResume();
      this.mConfig.onResume();
    }
    
    public void onStart()
    {
      super.onStart();
      setCurrentFragment(this);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.AboutPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */