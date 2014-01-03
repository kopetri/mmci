package com.google.android.gms.plus;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.internal.dz;

public final class PlusOneButton
  extends ViewGroup
{
  private final dz gV;
  
  public PlusOneButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.gV = new dz(paramContext, paramAttributeSet);
    addView(this.gV);
    if (isInEditMode()) {
      return;
    }
    setOnPlusOneClickListener(null);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.gV.layout(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    dz localdz = this.gV;
    measureChild(localdz, paramInt1, paramInt2);
    setMeasuredDimension(localdz.getMeasuredWidth(), localdz.getMeasuredHeight());
  }
  
  public void setAnnotation(int paramInt)
  {
    this.gV.setAnnotation(paramInt);
  }
  
  public void setOnPlusOneClickListener(OnPlusOneClickListener paramOnPlusOneClickListener)
  {
    this.gV.setOnPlusOneClickListener(paramOnPlusOneClickListener);
  }
  
  public void setSize(int paramInt)
  {
    this.gV.setSize(paramInt);
  }
  
  public static abstract interface OnPlusOneClickListener
  {
    public abstract void onPlusOneClick(Intent paramIntent);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.plus.PlusOneButton
 * JD-Core Version:    0.7.0.1
 */