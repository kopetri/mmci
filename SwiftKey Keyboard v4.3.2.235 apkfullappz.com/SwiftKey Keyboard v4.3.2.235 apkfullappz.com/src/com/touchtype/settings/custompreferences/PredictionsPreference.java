package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.util.AttributeSet;
import com.touchtype.preferences.TouchTypePreferences;

public class PredictionsPreference
  extends TopMenuPreference
  implements SharedPreferences.OnSharedPreferenceChangeListener
{
  private final TouchTypePreferences mPrefs;
  private final Resources mResources;
  
  public PredictionsPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PredictionsPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mResources = paramContext.getResources();
    this.mPrefs = TouchTypePreferences.getInstance(paramContext);
    this.mPrefs.registerOnSharedPreferenceChangeListener(this);
    updateSummary();
  }
  
  private void updateSummary()
  {
    if (this.mPrefs.isPredictionEnabled())
    {
      setSummary(this.mResources.getString(2131296486));
      return;
    }
    setSummary(this.mResources.getString(2131296487));
  }
  
  public void onPrepareForRemoval()
  {
    super.onPrepareForRemoval();
    this.mPrefs.unregisterOnSharedPreferenceChangeListener(this);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if (paramString.equals(this.mResources.getString(2131296489))) {
      updateSummary();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.PredictionsPreference
 * JD-Core Version:    0.7.0.1
 */