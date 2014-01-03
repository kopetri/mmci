package com.touchtype.settings;

import android.annotation.TargetApi;
import android.os.Bundle;

public final class PredictionsPreferenceSetting
{
  public static class PredictionsPreferenceActivity
    extends TouchTypeKeyboardSettings.IntentSafePreferenceActivity
  {
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      addPreferencesFromResource(2131034801);
    }
  }
  
  @TargetApi(11)
  public static class PredictionsPreferenceFragment
    extends TouchTypeKeyboardSettings.IntentSafePreferenceFragment
  {
    public void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      addPreferencesFromResource(2131034801);
    }
    
    public void onStart()
    {
      super.onStart();
      setCurrentFragment(this);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.PredictionsPreferenceSetting
 * JD-Core Version:    0.7.0.1
 */