package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class StatusListPreference
  extends ListPreference
{
  public StatusListPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void setValue(String paramString)
  {
    super.setValue(paramString);
    setSummary(getEntry());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.StatusListPreference
 * JD-Core Version:    0.7.0.1
 */