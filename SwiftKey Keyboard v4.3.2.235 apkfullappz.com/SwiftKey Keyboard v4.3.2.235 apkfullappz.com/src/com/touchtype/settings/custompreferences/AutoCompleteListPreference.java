package com.touchtype.settings.custompreferences;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import com.touchtype.preferences.AutoCompleteMode;
import com.touchtype.util.LogUtil;
import junit.framework.Assert;

public abstract class AutoCompleteListPreference
  extends ListPreference
{
  private static final String TAG = AutoCompleteListPreference.class.getSimpleName();
  protected String mCurrentAutoCompleteModeKey;
  private final String mDefaultAutoCompleteModeKey = getDefaultAutoCompleteMode();
  
  public AutoCompleteListPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    update(getAutoCompleteMode());
  }
  
  private String findEntryOfIndex(int paramInt)
  {
    if (paramInt < 0)
    {
      LogUtil.w(TAG, "Invalid AutoCompleteMode Key Index, using Default value");
      return valueToEntry(this.mDefaultAutoCompleteModeKey);
    }
    return getEntries()[paramInt].toString();
  }
  
  private void update(AutoCompleteMode paramAutoCompleteMode)
  {
    CharSequence[] arrayOfCharSequence = getEntries();
    if ((arrayOfCharSequence != null) && (paramAutoCompleteMode != null) && (paramAutoCompleteMode.ordinal() < arrayOfCharSequence.length) && (arrayOfCharSequence[paramAutoCompleteMode.ordinal()] != null)) {
      this.mCurrentAutoCompleteModeKey = findValueOfIndex(paramAutoCompleteMode.ordinal());
    }
    setValue(this.mCurrentAutoCompleteModeKey);
    setSummary(valueToEntry(this.mCurrentAutoCompleteModeKey));
    notifyChanged();
    notifyDependencyChange(shouldDisableDependents());
  }
  
  private String valueToEntry(String paramString)
  {
    return findEntryOfIndex(findIndexOfValue(paramString));
  }
  
  protected String findValueOfIndex(int paramInt)
  {
    return getEntryValues()[paramInt].toString();
  }
  
  protected abstract AutoCompleteMode getAutoCompleteMode();
  
  protected abstract String getDefaultAutoCompleteMode();
  
  protected void onDialogClosed(boolean paramBoolean)
  {
    boolean bool1 = true;
    super.onDialogClosed(paramBoolean);
    boolean bool2;
    int i;
    if (paramBoolean)
    {
      this.mCurrentAutoCompleteModeKey = getValue();
      if (this.mCurrentAutoCompleteModeKey == null) {
        break label108;
      }
      bool2 = bool1;
      Assert.assertTrue(bool2);
      if (shouldPersist()) {
        persistString(this.mCurrentAutoCompleteModeKey);
      }
      i = findIndexOfValue(this.mCurrentAutoCompleteModeKey);
      if ((i < 0) || (i >= AutoCompleteMode.values().length)) {
        break label113;
      }
    }
    for (;;)
    {
      Assert.assertTrue(bool1);
      setAutoCompleteMode(AutoCompleteMode.values()[i]);
      setSummary(valueToEntry(this.mCurrentAutoCompleteModeKey));
      notifyDependencyChange(shouldDisableDependents());
      return;
      label108:
      bool2 = false;
      break;
      label113:
      bool1 = false;
    }
  }
  
  protected void onPrepareDialogBuilder(AlertDialog.Builder paramBuilder)
  {
    if (shouldPersist())
    {
      this.mCurrentAutoCompleteModeKey = getPersistedString(this.mDefaultAutoCompleteModeKey);
      notifyDependencyChange(shouldDisableDependents());
    }
    paramBuilder.setTitle(2131296932);
    super.onPrepareDialogBuilder(paramBuilder);
  }
  
  protected void onSetInitialValue(boolean paramBoolean, Object paramObject)
  {
    super.onSetInitialValue(paramBoolean, paramObject);
    if (paramBoolean)
    {
      this.mCurrentAutoCompleteModeKey = getPersistedString(this.mDefaultAutoCompleteModeKey);
      if (this.mCurrentAutoCompleteModeKey == null) {
        break label59;
      }
    }
    label59:
    for (boolean bool = true;; bool = false)
    {
      Assert.assertTrue(bool);
      setSummary(valueToEntry(this.mCurrentAutoCompleteModeKey));
      return;
      this.mCurrentAutoCompleteModeKey = this.mDefaultAutoCompleteModeKey;
      break;
    }
  }
  
  protected abstract boolean setAutoCompleteMode(AutoCompleteMode paramAutoCompleteMode);
  
  public abstract boolean shouldDisableDependents();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.AutoCompleteListPreference
 * JD-Core Version:    0.7.0.1
 */