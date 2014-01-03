package com.touchtype.settings;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public final class FluencyPreferenceSetting
{
  public static class FluencyPreferenceActivity
    extends TouchTypeKeyboardSettings.IntentSafePreferenceActivity
  {
    private FluencyPreferenceConfiguration mConfig;
    
    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = new FluencyPreferenceConfiguration(this);
      this.mConfig.setup(this);
    }
    
    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
      paramMenu.add(2131296677);
      return super.onCreateOptionsMenu(paramMenu);
    }
    
    protected void onDestroy()
    {
      super.onDestroy();
      this.mConfig.destroy();
    }
    
    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
      this.mConfig.resetPreferences();
      return true;
    }
    
    protected void onResume()
    {
      super.onResume();
      this.mConfig.update();
    }
  }
  
  @TargetApi(11)
  public static class FluencyPreferenceFragment
    extends TouchTypeKeyboardSettings.IntentSafePreferenceFragment
  {
    private FluencyPreferenceConfiguration mConfig;
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      setHasOptionsMenu(true);
      this.mConfig.setup((TouchTypeKeyboardSettings)getActivity());
    }
    
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = new FluencyPreferenceConfiguration(this);
    }
    
    public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
    {
      paramMenu.add(2131296677);
      super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    }
    
    public void onDestroy()
    {
      super.onDestroy();
      this.mConfig.destroy();
    }
    
    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
      this.mConfig.resetPreferences();
      return true;
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
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.FluencyPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */