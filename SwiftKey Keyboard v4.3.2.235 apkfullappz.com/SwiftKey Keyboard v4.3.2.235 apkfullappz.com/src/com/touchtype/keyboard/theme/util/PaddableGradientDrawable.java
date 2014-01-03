package com.touchtype.keyboard.theme.util;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;

public final class PaddableGradientDrawable
  extends GradientDrawable
{
  private float mDashGap;
  private float mDashWidth;
  private Rect mPadding = null;
  private int mStrokeWidth;
  
  public PaddableGradientDrawable() {}
  
  public PaddableGradientDrawable(GradientDrawable.Orientation paramOrientation, int[] paramArrayOfInt)
  {
    super(paramOrientation, paramArrayOfInt);
  }
  
  public boolean getPadding(Rect paramRect)
  {
    if (this.mPadding != null)
    {
      paramRect.set(this.mPadding);
      return true;
    }
    return super.getPadding(paramRect);
  }
  
  public void setColorFilter(int paramInt, PorterDuff.Mode paramMode)
  {
    if (this.mStrokeWidth != 0)
    {
      if (this.mDashWidth != 0.0F) {
        setStroke(this.mStrokeWidth, paramInt, this.mDashWidth, this.mDashGap);
      }
    }
    else {
      return;
    }
    setStroke(this.mStrokeWidth, paramInt);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
  
  public void setPadding(Rect paramRect)
  {
    this.mPadding = paramRect;
  }
  
  public void setStroke(int paramInt1, int paramInt2)
  {
    this.mStrokeWidth = paramInt1;
    super.setStroke(paramInt1, paramInt2);
  }
  
  public void setStroke(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    this.mStrokeWidth = paramInt1;
    this.mDashWidth = paramFloat1;
    this.mDashGap = paramFloat2;
    super.setStroke(paramInt1, paramInt2, paramFloat1, paramFloat2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.PaddableGradientDrawable
 * JD-Core Version:    0.7.0.1
 */