package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.touchtype.R.styleable;

public class TopMenuPreference
  extends Preference
{
  private Drawable mIcon;
  
  public TopMenuPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public TopMenuPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mIcon = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.TopMenuPreference, paramInt, 0).getDrawable(0);
  }
  
  public Drawable getIcon()
  {
    return this.mIcon;
  }
  
  public void onBindView(View paramView)
  {
    super.onBindView(paramView);
    ImageView localImageView = (ImageView)paramView.findViewById(2131230904);
    if ((localImageView != null) && (this.mIcon != null)) {
      localImageView.setImageDrawable(this.mIcon);
    }
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    if (((paramDrawable == null) && (this.mIcon != null)) || ((paramDrawable != null) && (!paramDrawable.equals(this.mIcon))))
    {
      this.mIcon = paramDrawable;
      notifyChanged();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.TopMenuPreference
 * JD-Core Version:    0.7.0.1
 */