package com.touchtype.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import com.touchtype.preferences.TouchTypePreferences;

public final class KeyboardThemePreferenceConfiguration
  extends PreferenceWrapper
{
  public KeyboardThemePreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
    addPreference(2131034796);
  }
  
  public KeyboardThemePreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
    addPreference(2131034794);
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    Context localContext = getContext();
    Resources localResources = localContext.getResources();
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(localContext);
    CheckBoxPreference localCheckBoxPreference1 = (CheckBoxPreference)findPreference(localResources.getString(2131296476));
    CheckBoxPreference localCheckBoxPreference2 = (CheckBoxPreference)findPreference(localResources.getString(2131296477));
    CheckBoxPreference localCheckBoxPreference3 = (CheckBoxPreference)findPreference(localResources.getString(2131296479));
    if (localTouchTypePreferences.isFlagSet("first_run_key"))
    {
      boolean bool1 = localTouchTypePreferences.isBuildConfiguredForArrows();
      boolean bool2 = false;
      if (bool1)
      {
        int i = localResources.getConfiguration().navigation;
        bool2 = false;
        if (i == 1) {
          bool2 = true;
        }
      }
      if (localCheckBoxPreference1 != null) {
        localCheckBoxPreference1.setChecked(bool2);
      }
    }
    PreferenceScreen localPreferenceScreen = getPreferenceScreen();
    if ((!localTouchTypePreferences.isBuildConfiguredForArrows()) && (localPreferenceScreen != null) && (localCheckBoxPreference1 != null)) {
      localPreferenceScreen.removePreference(localCheckBoxPreference1);
    }
    if ((localPreferenceScreen != null) && (localCheckBoxPreference2 != null) && (((0xF & localContext.getResources().getConfiguration().screenLayout) == 2) || ((0xF & localContext.getResources().getConfiguration().screenLayout) == 1))) {
      localPreferenceScreen.removePreference(localCheckBoxPreference2);
    }
    if ((localPreferenceScreen != null) && (localCheckBoxPreference3 != null) && ((0xF & localContext.getResources().getConfiguration().screenLayout) != 4)) {
      localPreferenceScreen.removePreference(localCheckBoxPreference3);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.KeyboardThemePreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */