package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class PreferenceUpdateLanguagesButton
  extends Preference
{
  private Button mButton;
  private View.OnClickListener mButtonOnClickListener;
  
  public PreferenceUpdateLanguagesButton(Context paramContext, int paramInt, View.OnClickListener paramOnClickListener)
  {
    super(paramContext);
    this.mButtonOnClickListener = paramOnClickListener;
    setOrder(paramInt);
  }
  
  public PreferenceUpdateLanguagesButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public PreferenceUpdateLanguagesButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected View onCreateView(ViewGroup paramViewGroup)
  {
    View localView = ((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(2130903119, paramViewGroup, false);
    this.mButton = ((Button)localView.findViewById(2131230933));
    this.mButton.setOnClickListener(this.mButtonOnClickListener);
    return localView;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.PreferenceUpdateLanguagesButton
 * JD-Core Version:    0.7.0.1
 */