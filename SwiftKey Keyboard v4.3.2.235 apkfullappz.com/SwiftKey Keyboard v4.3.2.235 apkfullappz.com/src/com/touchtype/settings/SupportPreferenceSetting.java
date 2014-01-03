package com.touchtype.settings;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Bundle;

public final class SupportPreferenceSetting
{
  public static class SupportPreferenceActivity
    extends TouchTypeKeyboardSettings.IntentSafePreferenceActivity
  {
    private SupportPreferenceConfiguration mConfig;
    
    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = ((SupportPreferenceConfiguration)getLastNonConfigurationInstance());
      if (this.mConfig == null) {
        this.mConfig = new SupportPreferenceConfiguration(this);
      }
      for (;;)
      {
        this.mConfig.addPreference(2131034802);
        this.mConfig.setup(this);
        return;
        this.mConfig.setActivity(this);
      }
    }
    
    protected Dialog onCreateDialog(int paramInt, Bundle paramBundle)
    {
      return this.mConfig.onCreateDialog(paramInt, paramBundle);
    }
    
    public Object onRetainNonConfigurationInstance()
    {
      return this.mConfig;
    }
  }
  
  @TargetApi(11)
  public static class SupportPreferenceFragment
    extends TouchTypeKeyboardSettings.PersistentConfigPreferenceFragment<SupportPreferenceConfiguration>
  {
    private SupportPreferenceConfiguration mConfig;
    
    public SupportPreferenceConfiguration createNewConfig()
    {
      return new SupportPreferenceConfiguration(this);
    }
    
    public String getFragmentTag()
    {
      return "supportPreferenceConfigFragment";
    }
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      this.mConfig.setup((TouchTypeKeyboardSettings)getActivity());
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = ((SupportPreferenceConfiguration)fetchPreferenceConfig());
      this.mConfig.addPreference(2131034802);
    }
    
    public void onStart()
    {
      super.onStart();
      setCurrentFragment(this);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.SupportPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */