package com.touchtype.settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Vibrator;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;

public final class KeyboardFeedbackPreferenceConfiguration
  extends PreferenceWrapper
{
  public KeyboardFeedbackPreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
    addPreference(2131034795);
  }
  
  public KeyboardFeedbackPreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
    addPreference(2131034795);
  }
  
  @TargetApi(11)
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    Context localContext = getContext();
    if (Build.VERSION.SDK_INT >= 11)
    {
      Vibrator localVibrator = (Vibrator)localContext.getSystemService("vibrator");
      if ((localVibrator != null) && (!localVibrator.hasVibrator()))
      {
        PreferenceCategory localPreferenceCategory = (PreferenceCategory)findPreference(localContext.getResources().getString(2131296972));
        if (localPreferenceCategory != null) {
          removePreference(localPreferenceCategory);
        }
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.KeyboardFeedbackPreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */