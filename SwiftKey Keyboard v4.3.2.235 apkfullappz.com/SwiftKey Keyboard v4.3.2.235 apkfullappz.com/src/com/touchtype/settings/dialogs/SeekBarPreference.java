package com.touchtype.settings.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.Preference.BaseSavedState;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.touchtype.R.styleable;

public class SeekBarPreference
  extends DialogPreference
  implements View.OnClickListener, SeekBar.OnSeekBarChangeListener
{
  private static final String TAG = SeekBarPreference.class.getSimpleName();
  private static final StringBuilder mainMessageBuilder = new StringBuilder();
  private int mCurrentValue;
  private int mDefaultValue;
  private CharSequence mMainMessage;
  private int mMaximum;
  TextView mMessageTextView;
  private int mMinimum;
  
  public SeekBarPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SeekBarPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.TouchTypeSeekBarPreference, paramInt, 0);
    this.mMinimum = localTypedArray.getInt(2, 0);
    this.mMaximum = localTypedArray.getInt(3, 100);
    this.mMainMessage = localTypedArray.getString(1);
    localTypedArray.recycle();
    setLayoutResource(2130903100);
  }
  
  private int getDefaultValue()
  {
    return this.mDefaultValue;
  }
  
  protected CharSequence buildMessageText(int paramInt)
  {
    StringBuilder localStringBuilder = getMessageBuilder();
    localStringBuilder.setLength(0);
    localStringBuilder.append(String.valueOf(paramInt));
    localStringBuilder.append(getMainMessage());
    return localStringBuilder;
  }
  
  public CharSequence getMainMessage()
  {
    return this.mMainMessage;
  }
  
  protected StringBuilder getMessageBuilder()
  {
    return mainMessageBuilder;
  }
  
  public int getValue()
  {
    return this.mCurrentValue;
  }
  
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    super.onClick(paramDialogInterface, paramInt);
    if (shouldPersist()) {}
    switch (paramInt)
    {
    default: 
      return;
    case -1: 
      persistValue(getValue());
      callChangeListener(Integer.valueOf(getValue()));
      return;
    }
    persistValue(getDefaultValue());
    callChangeListener(Integer.valueOf(getDefaultValue()));
  }
  
  public void onClick(View paramView)
  {
    preview(getValue());
  }
  
  protected View onCreateDialogView()
  {
    View localView = super.onCreateDialogView();
    int i = getPersistedInt(getValue());
    CharSequence localCharSequence = buildMessageText(i);
    this.mMessageTextView = ((TextView)localView.findViewById(2131230927));
    this.mMessageTextView.setText(localCharSequence);
    SeekBar localSeekBar = (SeekBar)localView.findViewById(2131230928);
    localSeekBar.setOnSeekBarChangeListener(this);
    localSeekBar.setOnClickListener(this);
    localSeekBar.setMax(this.mMaximum - this.mMinimum);
    localSeekBar.setProgress(i - this.mMinimum);
    return localView;
  }
  
  protected Object onGetDefaultValue(TypedArray paramTypedArray, int paramInt)
  {
    int i = paramTypedArray.getInt(paramInt, 0);
    setDefaultValue(Integer.valueOf(i));
    return Integer.valueOf(i);
  }
  
  public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      callChangeListener(Integer.valueOf(paramInt));
      this.mMessageTextView.setText(buildMessageText(paramInt + this.mMinimum));
      updateValue(paramInt + this.mMinimum);
    }
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!paramParcelable.getClass().equals(SavedState.class))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    updateValue(localSavedState.currentValue);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Parcelable localParcelable = super.onSaveInstanceState();
    if (isPersistent()) {
      return localParcelable;
    }
    SavedState localSavedState = new SavedState(localParcelable);
    localSavedState.currentValue = getValue();
    return localSavedState;
  }
  
  protected void onSetInitialValue(boolean paramBoolean, Object paramObject)
  {
    if (paramBoolean) {}
    for (int i = getPersistedInt(getValue());; i = ((Integer)paramObject).intValue())
    {
      persistValue(i);
      return;
    }
  }
  
  public void onStartTrackingTouch(SeekBar paramSeekBar) {}
  
  public void onStopTrackingTouch(SeekBar paramSeekBar)
  {
    preview(getValue());
  }
  
  public void persistValue(int paramInt)
  {
    updateValue(paramInt);
    persistInt(paramInt);
    setSummary(Integer.toString(paramInt) + this.mMainMessage);
  }
  
  protected void preview(int paramInt) {}
  
  public void setDefaultValue(Object paramObject)
  {
    super.setDefaultValue(paramObject);
    this.mDefaultValue = ((Integer)paramObject).intValue();
  }
  
  public void updateValue(int paramInt)
  {
    this.mCurrentValue = paramInt;
  }
  
  private static class SavedState
    extends Preference.BaseSavedState
  {
    int currentValue;
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.currentValue);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.SeekBarPreference
 * JD-Core Version:    0.7.0.1
 */