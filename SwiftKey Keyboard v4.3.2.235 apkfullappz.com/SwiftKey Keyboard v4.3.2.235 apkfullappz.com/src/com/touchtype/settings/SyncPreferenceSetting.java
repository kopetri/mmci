package com.touchtype.settings;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public final class SyncPreferenceSetting
{
  public static class SyncPreferenceActivity
    extends PreferenceActivity
  {
    private SyncPreferenceConfiguration config;
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.config = ((SyncPreferenceConfiguration)getLastNonConfigurationInstance());
      if (this.config == null) {
        this.config = new SyncPreferenceConfiguration(this);
      }
      for (;;)
      {
        this.config.addPreference(2131034803);
        this.config.setup(this);
        return;
        this.config.setActivity(this);
      }
    }
    
    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
      return this.config.onCreateOptionsMenu(paramMenu);
    }
    
    protected void onDestroy()
    {
      if (this.config != null)
      {
        this.config.cleanup();
        this.config = null;
      }
      super.onDestroy();
    }
    
    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
      return this.config.onOptionsItemSelected(paramMenuItem);
    }
    
    public boolean onPrepareOptionsMenu(Menu paramMenu)
    {
      return this.config.onPrepareOptionsMenu(paramMenu);
    }
    
    protected void onResume()
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
  public static class SyncPreferenceFragment
    extends TouchTypeKeyboardSettings.PersistentConfigPreferenceFragment<SyncPreferenceConfiguration>
  {
    private SyncPreferenceConfiguration config;
    
    public SyncPreferenceConfiguration createNewConfig()
    {
      return new SyncPreferenceConfiguration(this);
    }
    
    public String getFragmentTag()
    {
      return "syncPreferenceConfigFragment";
    }
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      this.config.setup((TouchTypeKeyboardSettings)getActivity());
      setHasOptionsMenu(true);
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.config = ((SyncPreferenceConfiguration)fetchPreferenceConfig());
      this.config.addPreference(2131034803);
    }
    
    public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
    {
      this.config.onCreateOptionsMenu(paramMenu);
    }
    
    public void onDestroy()
    {
      if (this.config != null) {
        this.config.cleanup();
      }
      super.onDestroy();
    }
    
    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
      return this.config.onOptionsItemSelected(paramMenuItem);
    }
    
    public void onPrepareOptionsMenu(Menu paramMenu)
    {
      this.config.onPrepareOptionsMenu(paramMenu);
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
 * Qualified Name:     com.touchtype.settings.SyncPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */