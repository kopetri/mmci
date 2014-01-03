package com.touchtype.settings;

import android.annotation.TargetApi;
import android.os.Bundle;

public final class InputMethodsPreferenceSetting
{
  public static class InputMethodsPreferenceActivity
    extends TouchTypeKeyboardSettings.IntentSafePreferenceActivity
  {
    private InputMethodsPreferenceConfiguration mConfig;
    
    protected void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      this.mConfig = new InputMethodsPreferenceConfiguration(this);
      this.mConfig.setup(this);
    }
    
    protected void onResume()
    {
      super.onResume();
      this.mConfig.update();
    }
  }
  
  @TargetApi(11)
  public static class InputMethodsPreferenceFragment
    extends TouchTypeKeyboardSettings.IntentSafePreferenceFragment
  {
    private InputMethodsPreferenceConfiguration mConfig;
    
    public void onActivityCreated(Bundle paramBundle)
    {
      super.onActivityCreated(paramBundle);
      this.mConfig = new InputMethodsPreferenceConfiguration(this);
      this.mConfig.setup((TouchTypeKeyboardSettings)getActivity());
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
 * Qualified Name:     com.touchtype.settings.InputMethodsPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */