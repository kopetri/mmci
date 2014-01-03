package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.util.AttributeSet;

public class QuickPeriodCheckBoxPreference
  extends CheckBoxPreference
{
  public QuickPeriodCheckBoxPreference(Context paramContext)
  {
    super(paramContext);
  }
  
  public QuickPeriodCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public QuickPeriodCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void onDependencyChanged(Preference paramPreference, boolean paramBoolean)
  {
    if (paramBoolean) {
      setSummary(2131296693);
    }
    for (;;)
    {
      super.onDependencyChanged(paramPreference, paramBoolean);
      return;
      setSummary(2131296692);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.QuickPeriodCheckBoxPreference
 * JD-Core Version:    0.7.0.1
 */