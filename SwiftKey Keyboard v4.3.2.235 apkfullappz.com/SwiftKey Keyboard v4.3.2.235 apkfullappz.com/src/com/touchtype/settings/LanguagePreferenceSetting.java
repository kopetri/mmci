package com.touchtype.settings;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public final class LanguagePreferenceSetting
{
  public static class LanguagePreferenceActivity
    extends PreferenceActivity
  {
    private LanguagePreferenceConfiguration mLanguagePreferenceConfiguration;
    
    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mLanguagePreferenceConfiguration = new LanguagePreferenceConfiguration(this);
      this.mLanguagePreferenceConfiguration.setup(this);
    }
    
    public Dialog onCreateDialog(int paramInt)
    {
      return this.mLanguagePreferenceConfiguration.onCreateDialog(paramInt);
    }
    
    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
      return this.mLanguagePreferenceConfiguration.onCreateOptionsMenu(paramMenu);
    }
    
    protected void onDestroy()
    {
      this.mLanguagePreferenceConfiguration.onDestroy();
      super.onDestroy();
    }
    
    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
      return this.mLanguagePreferenceConfiguration.onOptionsItemSelected(paramMenuItem);
    }
    
    protected void onPause()
    {
      this.mLanguagePreferenceConfiguration.onPause();
      super.onPause();
    }
    
    public boolean onPrepareOptionsMenu(Menu paramMenu)
    {
      return this.mLanguagePreferenceConfiguration.onPrepareOptionsMenu(paramMenu);
    }
    
    protected void onResume()
    {
      super.onResume();
      this.mLanguagePreferenceConfiguration.onResume();
    }
  }
  
  @TargetApi(11)
  public static class LanguagePreferenceFragment
    extends TouchTypeKeyboardSettings.IntentSafePreferenceFragment
  {
    private LanguagePreferenceConfiguration mConfiguration;
    
    public LanguagePreferenceConfiguration getConfiguration()
    {
      return this.mConfiguration;
    }
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      this.mConfiguration.setup((TouchTypeKeyboardSettings)getActivity());
      setHasOptionsMenu(true);
      setCurrentFragment(this);
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfiguration = new LanguagePreferenceConfiguration(this);
    }
    
    public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
    {
      this.mConfiguration.onCreateOptionsMenu(paramMenu);
    }
    
    public void onDestroy()
    {
      if (this.mConfiguration != null) {
        this.mConfiguration.onDestroy();
      }
      super.onDestroy();
    }
    
    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
      return this.mConfiguration.onOptionsItemSelected(paramMenuItem);
    }
    
    public void onPause()
    {
      this.mConfiguration.onPause();
      super.onPause();
    }
    
    public void onPrepareOptionsMenu(Menu paramMenu)
    {
      this.mConfiguration.onPrepareOptionsMenu(paramMenu);
    }
    
    public void onResume()
    {
      super.onResume();
      this.mConfiguration.onResume();
    }
    
    public void onStart()
    {
      super.onStart();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.LanguagePreferenceSetting
 * JD-Core Version:    0.7.0.1
 */