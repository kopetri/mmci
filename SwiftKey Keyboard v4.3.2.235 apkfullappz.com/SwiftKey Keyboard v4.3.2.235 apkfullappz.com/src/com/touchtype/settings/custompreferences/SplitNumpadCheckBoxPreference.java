package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.content.res.Resources;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import com.touchtype.preferences.TouchTypePreferences;

public class SplitNumpadCheckBoxPreference
  extends CheckBoxPreference
{
  private Context mContext;
  private boolean mIsChecked;
  
  public SplitNumpadCheckBoxPreference(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public SplitNumpadCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }
  
  public SplitNumpadCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
  }
  
  protected void onBindView(View paramView)
  {
    super.onBindView(paramView);
    this.mIsChecked = TouchTypePreferences.getInstance(this.mContext).getShowSplitNumpad(this.mContext, this.mContext.getResources().getBoolean(2131492877));
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
      TouchTypePreferences.getInstance(this.mContext).setshowSplitNumpad(this.mIsChecked);
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.SplitNumpadCheckBoxPreference
 * JD-Core Version:    0.7.0.1
 */