package com.touchtype.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import com.touchtype.TouchTypeUtilities;
import com.touchtype.util.EnvironmentInfoUtil;
import junit.framework.Assert;

public class InputMethodsPreferenceConfiguration
  extends PreferenceWrapper
{
  private static final String TAG = InputMethodsPreferenceConfiguration.class.getSimpleName();
  private final Resources resources = getContext().getResources();
  
  public InputMethodsPreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
    addPreference(2131034793);
  }
  
  public InputMethodsPreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
    addPreference(2131034793);
  }
  
  private static boolean blacklistedSpecialPreferences()
  {
    String str1 = EnvironmentInfoUtil.getBrandInfo();
    String str2 = EnvironmentInfoUtil.getModelInfo();
    return (str1.contains("asus")) && (str2.contains("Transformer Prime TF201"));
  }
  
  public static boolean hideOldLayoutSelector()
  {
    return (!blacklistedSpecialPreferences()) && ((Build.VERSION.SDK_INT < 11) || (Build.VERSION.SDK_INT >= 16));
  }
  
  private void updateHardKBPreferences()
  {
    PreferenceCategory localPreferenceCategory = (PreferenceCategory)findPreference(this.resources.getString(2131296665));
    if (localPreferenceCategory != null)
    {
      if (this.resources.getConfiguration().keyboard != 2)
      {
        localPreferenceCategory.removeAll();
        Preference localPreference = new Preference(getContext());
        localPreference.setLayoutResource(2130903100);
        localPreference.setTitle(this.resources.getString(2131296667));
        localPreference.setSummary(this.resources.getString(2131296668));
        localPreference.setEnabled(false);
        localPreferenceCategory.addPreference(localPreference);
      }
    }
    else {
      return;
    }
    updateLayoutPreferences(localPreferenceCategory);
  }
  
  private void updateLayoutPreferences(PreferenceCategory paramPreferenceCategory)
  {
    Preference localPreference1 = findPreference(this.resources.getString(2131296748));
    if (localPreference1 != null)
    {
      if ((Build.VERSION.SDK_INT >= 16) && (!blacklistedSpecialPreferences())) {
        break label69;
      }
      paramPreferenceCategory.removePreference(localPreference1);
    }
    for (;;)
    {
      if (hideOldLayoutSelector())
      {
        Preference localPreference2 = findPreference(this.resources.getString(2131296747));
        if (localPreference2 != null) {
          paramPreferenceCategory.removePreference(localPreference2);
        }
      }
      return;
      label69:
      localPreference1.setIntent(TouchTypeUtilities.getIMSettingsIntent());
      localPreference1.setSummary(this.resources.getString(2131296749));
    }
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    Assert.assertNotNull("Has the advanced preference screen been removed?", getPreferenceScreen());
    updateHardKBPreferences();
  }
  
  public void update()
  {
    updateHardKBPreferences();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.InputMethodsPreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */