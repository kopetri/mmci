package com.touchtype.settings;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.touchtype.cloud.CloudSetupActivity;
import com.touchtype.preferences.TouchTypePreferences;

public final class RecentPersonalizersPreferenceSetting
{
  public static class RecentPersonalizersPreferenceActivity
    extends PreferenceActivity
  {
    private boolean mIsCloudSetup;
    private RecentPersonalizersPreferenceConfiguration mPersonalizationConfiguration;
    
    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
      if ((this.mIsCloudSetup) && (this.mPersonalizationConfiguration != null)) {
        this.mPersonalizationConfiguration.onActivityResult(this, paramInt1, paramInt2, paramIntent);
      }
    }
    
    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mIsCloudSetup = TouchTypePreferences.getInstance(this).isCloudAccountSetup();
      if (this.mIsCloudSetup)
      {
        this.mPersonalizationConfiguration = ((RecentPersonalizersPreferenceConfiguration)getLastNonConfigurationInstance());
        if (this.mPersonalizationConfiguration == null) {
          this.mPersonalizationConfiguration = new RecentPersonalizersPreferenceConfiguration(this);
        }
        for (;;)
        {
          this.mPersonalizationConfiguration.addPreference(2131034800);
          this.mPersonalizationConfiguration.setup(this);
          return;
          this.mPersonalizationConfiguration.setActivity(this);
        }
      }
      startActivity(new Intent(this, CloudSetupActivity.class));
      finish();
    }
    
    public void onNewIntent(Intent paramIntent)
    {
      if ((this.mIsCloudSetup) && (this.mPersonalizationConfiguration != null)) {
        this.mPersonalizationConfiguration.onNewIntent(paramIntent);
      }
    }
    
    public void onResume()
    {
      super.onResume();
      if ((this.mIsCloudSetup) && (this.mPersonalizationConfiguration != null)) {
        this.mPersonalizationConfiguration.onResume();
      }
    }
    
    public Object onRetainNonConfigurationInstance()
    {
      return this.mPersonalizationConfiguration;
    }
    
    public void onStart()
    {
      super.onStart();
      if ((this.mIsCloudSetup) && (this.mPersonalizationConfiguration != null)) {
        this.mPersonalizationConfiguration.onStart();
      }
    }
    
    protected void onStop()
    {
      super.onStop();
      if ((this.mIsCloudSetup) && (this.mPersonalizationConfiguration != null)) {
        this.mPersonalizationConfiguration.onStop();
      }
    }
  }
  
  @TargetApi(11)
  public static class RecentPersonalizersPreferenceFragment
    extends TouchTypeKeyboardSettings.PersistentConfigPreferenceFragment<RecentPersonalizersPreferenceConfiguration>
  {
    private RecentPersonalizersPreferenceConfiguration mConfiguration;
    private boolean mIsCloudSetup;
    
    public RecentPersonalizersPreferenceConfiguration createNewConfig()
    {
      return new RecentPersonalizersPreferenceConfiguration(this);
    }
    
    public String getFragmentTag()
    {
      return "recentPersonalizersPreferenceConfigFragment";
    }
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      if ((this.mIsCloudSetup) && (this.mConfiguration != null)) {
        this.mConfiguration.setup((TouchTypeKeyboardSettings)getActivity());
      }
    }
    
    public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
      if ((this.mIsCloudSetup) && (this.mConfiguration != null)) {
        this.mConfiguration.onActivityResult(getActivity(), paramInt1, paramInt2, paramIntent);
      }
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mIsCloudSetup = TouchTypePreferences.getInstance(getActivity()).isCloudAccountSetup();
      if (this.mIsCloudSetup)
      {
        this.mConfiguration = ((RecentPersonalizersPreferenceConfiguration)fetchPreferenceConfig());
        this.mConfiguration.addPreference(2131034800);
        return;
      }
      startActivity(new Intent(getActivity(), CloudSetupActivity.class));
      getActivity().finish();
    }
    
    public void onNewIntent(Intent paramIntent)
    {
      if ((this.mIsCloudSetup) && (this.mConfiguration != null)) {
        this.mConfiguration.onNewIntent(paramIntent);
      }
    }
    
    public void onResume()
    {
      super.onResume();
      if ((this.mIsCloudSetup) && (this.mConfiguration != null)) {
        this.mConfiguration.onResume();
      }
    }
    
    public void onStart()
    {
      super.onStart();
      if ((this.mIsCloudSetup) && (this.mConfiguration != null)) {
        this.mConfiguration.onStart();
      }
      setCurrentFragment(this);
    }
    
    public void onStop()
    {
      super.onStop();
      if ((this.mIsCloudSetup) && (this.mConfiguration != null)) {
        this.mConfiguration.onStop();
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.RecentPersonalizersPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */