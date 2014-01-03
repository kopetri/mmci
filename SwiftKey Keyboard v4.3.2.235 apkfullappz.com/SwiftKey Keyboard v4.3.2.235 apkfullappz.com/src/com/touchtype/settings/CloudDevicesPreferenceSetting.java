package com.touchtype.settings;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public final class CloudDevicesPreferenceSetting
{
  public static class CloudDevicesPreferenceActivity
    extends PreferenceActivity
  {
    private CloudDevicesPreferenceConfiguration config;
    
    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.config = ((CloudDevicesPreferenceConfiguration)getLastNonConfigurationInstance());
      if (this.config == null) {
        this.config = new CloudDevicesPreferenceConfiguration(this);
      }
      for (;;)
      {
        this.config.addPreference(2131034791);
        this.config.setup(this);
        return;
        this.config.setActivity(this);
      }
    }
    
    protected Dialog onCreateDialog(int paramInt, Bundle paramBundle)
    {
      return this.config.onCreateDialog(paramInt, paramBundle);
    }
    
    protected void onDestroy()
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
  public static class CloudDevicesPreferenceFragment
    extends TouchTypeKeyboardSettings.PersistentConfigPreferenceFragment<CloudDevicesPreferenceConfiguration>
  {
    private CloudDevicesPreferenceConfiguration config;
    
    public CloudDevicesPreferenceConfiguration createNewConfig()
    {
      return new CloudDevicesPreferenceConfiguration(this);
    }
    
    public String getFragmentTag()
    {
      return "cloudDevicesPreferenceConfigFragment";
    }
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      this.config.setup((TouchTypeKeyboardSettings)getActivity());
      setCurrentFragment(this);
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.config = ((CloudDevicesPreferenceConfiguration)fetchPreferenceConfig());
      this.config.addPreference(2131034791);
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
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.CloudDevicesPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */