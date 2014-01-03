package com.touchtype.settings;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public final class CloudPreferenceSetting
{
  public static class CloudPreferenceActivity
    extends PreferenceActivity
  {
    private CloudPreferenceConfiguration config;
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.config = ((CloudPreferenceConfiguration)getLastNonConfigurationInstance());
      if (this.config == null) {
        this.config = new CloudPreferenceConfiguration(this);
      }
      for (;;)
      {
        this.config.addPreference(2131034792);
        this.config.setup(this);
        return;
        this.config.setActivity(this);
      }
    }
    
    public void onDestroy()
    {
      super.onDestroy();
      this.config.cleanup();
    }
    
    public void onResume()
    {
      super.onResume();
      this.config.onResume();
    }
    
    public Object onRetainNonConfigurationInstance()
    {
      return this.config;
    }
  }
  
  @TargetApi(11)
  public static class CloudPreferenceFragment
    extends TouchTypeKeyboardSettings.PersistentConfigPreferenceFragment<CloudPreferenceConfiguration>
  {
    private CloudPreferenceConfiguration config;
    
    public CloudPreferenceConfiguration createNewConfig()
    {
      return new CloudPreferenceConfiguration(this);
    }
    
    public String getFragmentTag()
    {
      return "cloudPreferenceConfigFragment";
    }
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      this.config.setup((TouchTypeKeyboardSettings)getActivity());
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.config = ((CloudPreferenceConfiguration)fetchPreferenceConfig());
      this.config.addPreference(2131034790);
    }
    
    public void onDestroy()
    {
      if (this.config != null) {
        this.config.cleanup();
      }
      super.onDestroy();
    }
    
    public void onResume()
    {
      super.onResume();
      this.config.onResume();
    }
    
    public void onStart()
    {
      super.onStart();
      setCurrentFragment(this);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.CloudPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */