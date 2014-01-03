package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.content.res.Resources;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import com.touchtype.preferences.TouchTypePreferences;

public class PCLayoutCheckBoxPreference
  extends CheckBoxPreference
{
  private Context mContext;
  private boolean mIsChecked;
  
  public PCLayoutCheckBoxPreference(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public PCLayoutCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }
  
  public PCLayoutCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
  }
  
  protected void onBindView(View paramView)
  {
    super.onBindView(paramView);
    this.mIsChecked = TouchTypePreferences.getInstance(this.mContext).getUsePCLayoutStyle(this.mContext.getResources().getBoolean(2131492874));
    setChecked(this.mIsChecked);
  }
  
  protected void onClick()
  {
    super.onClick();
    if (!this.mIsChecked) {}
    for (boolean bool = true;; bool = false)
    {
      this.mIsChecked = bool;
      setChecked(this.mIsChecked);
      TouchTypePreferences.getInstance(this.mContext).setUsePCLayoutStyle(this.mIsChecked);
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.PCLayoutCheckBoxPreference
 * JD-Core Version:    0.7.0.1
 */