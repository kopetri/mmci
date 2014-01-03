package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.util.AttributeSet;
import com.touchtype.preferences.AutoCompleteMode;
import com.touchtype.preferences.TouchTypePreferences;

public class HardKBAutoCompleteListPreference
  extends AutoCompleteListPreference
{
  public HardKBAutoCompleteListPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected AutoCompleteMode getAutoCompleteMode()
  {
    return TouchTypePreferences.getInstance(getContext()).getHardKBAutoCompleteMode();
  }
  
  protected String getDefaultAutoCompleteMode()
  {
    return findValueOfIndex(TouchTypePreferences.getInstance(getContext()).getHardKBDefaultAutoCompleteMode());
  }
  
  protected boolean setAutoCompleteMode(AutoCompleteMode paramAutoCompleteMode)
  {
    return TouchTypePreferences.getInstance(getContext()).setHardKBAutoCompleteMode(paramAutoCompleteMode);
  }
  
  public boolean shouldDisableDependents()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.HardKBAutoCompleteListPreference
 * JD-Core Version:    0.7.0.1
 */