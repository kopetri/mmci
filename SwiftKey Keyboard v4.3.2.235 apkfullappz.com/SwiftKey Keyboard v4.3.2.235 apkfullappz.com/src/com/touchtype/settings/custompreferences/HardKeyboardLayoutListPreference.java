package com.touchtype.settings.custompreferences;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.Resources;
import android.preference.ListPreference;
import android.util.AttributeSet;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;

public class HardKeyboardLayoutListPreference
  extends ListPreference
{
  private static final String TAG = HardKeyboardLayoutListPreference.class.getSimpleName();
  private final int[] mAvailableLayouts = getAvailableHardKeyboardLayouts();
  private int mCurrentLayout;
  private final int mDefaultLayout = getDefaultHardKeyboardLayout();
  private final String[] mLayoutNames;
  
  public HardKeyboardLayoutListPreference(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public HardKeyboardLayoutListPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mLayoutNames = paramContext.getResources().getStringArray(2131623954);
    this.mCurrentLayout = this.mDefaultLayout;
    updateSummary(this.mCurrentLayout);
  }
  
  private int[] getAvailableHardKeyboardLayouts()
  {
    return getContext().getResources().getIntArray(2131623941);
  }
  
  private int getDefaultHardKeyboardLayout()
  {
    return getContext().getResources().getInteger(2131558425);
  }
  
  private void updateSummary(int paramInt)
  {
    setSummary(this.mLayoutNames[paramInt]);
  }
  
  public int getCurrentLayout()
  {
    int i = this.mDefaultLayout;
    String str;
    if (shouldPersist()) {
      str = getPersistedString(Integer.toString(this.mDefaultLayout));
    }
    try
    {
      int j = Integer.parseInt(str);
      i = j;
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      LogUtil.e(TAG, "Invalid hard keyboard layout key: " + str, localNumberFormatException);
    }
    return i;
  }
  
  protected void onDialogClosed(boolean paramBoolean)
  {
    super.onDialogClosed(paramBoolean);
    if (!paramBoolean) {
      return;
    }
    this.mCurrentLayout = getCurrentLayout();
    if (shouldPersist()) {
      persistString(Integer.toString(this.mCurrentLayout));
    }
    updateSummary(this.mCurrentLayout);
    TouchTypePreferences.getInstance(getContext()).setHardKeyboardLayout(this.mCurrentLayout);
  }
  
  protected void onPrepareDialogBuilder(AlertDialog.Builder paramBuilder)
  {
    if (shouldPersist()) {
      this.mCurrentLayout = getCurrentLayout();
    }
    String[] arrayOfString1 = new String[this.mAvailableLayouts.length];
    String[] arrayOfString2 = new String[this.mAvailableLayouts.length];
    for (int i = 0; i < this.mAvailableLayouts.length; i++)
    {
      arrayOfString1[i] = this.mLayoutNames[this.mAvailableLayouts[i]];
      arrayOfString2[i] = Integer.toString(this.mAvailableLayouts[i]);
    }
    setEntries(arrayOfString1);
    setEntryValues(arrayOfString2);
    setValue(Integer.toString(this.mCurrentLayout));
    updateSummary(this.mCurrentLayout);
    super.onPrepareDialogBuilder(paramBuilder);
  }
  
  protected void onSetInitialValue(boolean paramBoolean, Object paramObject)
  {
    super.onSetInitialValue(paramBoolean, paramObject);
    if (paramBoolean) {}
    for (int i = getCurrentLayout();; i = this.mDefaultLayout)
    {
      this.mCurrentLayout = i;
      updateSummary(this.mCurrentLayout);
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.HardKeyboardLayoutListPreference
 * JD-Core Version:    0.7.0.1
 */