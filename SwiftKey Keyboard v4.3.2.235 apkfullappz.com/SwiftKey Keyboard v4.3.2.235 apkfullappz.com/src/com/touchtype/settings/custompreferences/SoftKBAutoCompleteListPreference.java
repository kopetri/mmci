package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.util.AttributeSet;
import com.touchtype.preferences.AutoCompleteMode;
import com.touchtype.preferences.TouchTypePreferences;

public class SoftKBAutoCompleteListPreference
  extends AutoCompleteListPreference
{
  public SoftKBAutoCompleteListPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected AutoCompleteMode getAutoCompleteMode()
  {
    return TouchTypePreferences.getInstance(getContext()).getAutoCompleteMode();
  }
  
  protected String getDefaultAutoCompleteMode()
  {
    return findValueOfIndex(TouchTypePreferences.getInstance(getContext()).getDefaultAutoCompleteMode());
  }
  
  protected boolean setAutoCompleteMode(AutoCompleteMode paramAutoCompleteMode)
  {
    return TouchTypePreferences.getInstance(getContext()).setAutoCompleteMode(paramAutoCompleteMode);
  }
  
  public boolean shouldDisableDependents()
  {
    return findIndexOfValue(this.mCurrentAutoCompleteModeKey) == AutoCompleteMode.AUTOCOMPLETEMODE_ENABLED_WITH_AUTOSELECT.ordinal();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.SoftKBAutoCompleteListPreference
 * JD-Core Version:    0.7.0.1
 */