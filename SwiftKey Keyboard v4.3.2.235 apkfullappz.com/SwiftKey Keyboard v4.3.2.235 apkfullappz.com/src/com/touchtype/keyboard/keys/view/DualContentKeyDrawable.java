package com.touchtype.keyboard.keys.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;

public final class DualContentKeyDrawable
  extends ResizeDrawable
{
  private ResizeDrawable mBottomDrawable;
  private float mRatio;
  private ResizeDrawable mTopDrawable;
  
  public DualContentKeyDrawable(ResizeDrawable paramResizeDrawable1, ResizeDrawable paramResizeDrawable2, float paramFloat)
  {
    this.mTopDrawable = paramResizeDrawable1;
    this.mBottomDrawable = paramResizeDrawable2;
    this.mRatio = paramFloat;
  }
  
  private void rescale(Rect paramRect)
  {
    int i = paramRect.bottom - (int)Math.abs(paramRect.height() * this.mRatio);
    Rect localRect1 = new Rect(paramRect.left, i, paramRect.right, paramRect.bottom);
    Rect localRect2 = new Rect(paramRect.left, paramRect.top, paramRect.right, i);
    float f1 = (1.0F - this.mRatio) / this.mRatio;
    float f2 = Math.min(paramRect.width() / (this.mBottomDrawable.getWHRatio() + 2.0F * f1 * this.mTopDrawable.getWHRatio()), paramRect.height());
    Rect localRect3 = new Rect(paramRect.left, (int)(paramRect.bottom - f2), paramRect.right, paramRect.bottom);
    Rect localRect4 = new Rect(paramRect.left, paramRect.top, paramRect.right, (int)(paramRect.top + f2 * f1));
    if (localRect3.height() > localRect1.height())
    {
      this.mBottomDrawable.setBounds(localRect3);
      this.mTopDrawable.setBounds(localRect4);
      return;
    }
    this.mBottomDrawable.setBounds(localRect1);
    this.mTopDrawable.setBounds(localRect2);
  }
  
  public void draw(Canvas paramCanvas)
  {
    if ((this.mBottomDrawable == null) || (this.mTopDrawable == null)) {
      return;
    }
    this.mTopDrawable.draw(paramCanvas);
    this.mBottomDrawable.draw(paramCanvas);
  }
  
  public int getOpacity()
  {
    return 0;
  }
  
  public float getWHRatio()
  {
    return 0.0F;
  }
  
  public void onBoundsChange(Rect paramRect)
  {
    if ((this.mBottomDrawable == null) || (this.mTopDrawable == null)) {
      return;
    }
    rescale(paramRect);
  }
  
  public void setAlpha(int paramInt)
  {
    if ((this.mBottomDrawable == null) || (this.mTopDrawable == null)) {
      return;
    }
    this.mBottomDrawable.setAlpha(paramInt);
    this.mTopDrawable.setAlpha(paramInt);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    if ((this.mBottomDrawable == null) || (this.mTopDrawable == null)) {
      return;
    }
    this.mBottomDrawable.setColorFilter(paramColorFilter);
    this.mTopDrawable.setColorFilter(paramColorFilter);
  }
  
  public boolean setState(int[] paramArrayOfInt)
  {
    if ((this.mBottomDrawable == null) || (this.mTopDrawable == null)) {}
    boolean bool1;
    boolean bool2;
    do
    {
      return false;
      bool1 = this.mTopDrawable.setState(paramArrayOfInt);
      bool2 = this.mBottomDrawable.setState(paramArrayOfInt);
    } while ((!bool1) && (!bool2));
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.keys.view.DualContentKeyDrawable
 * JD-Core Version:    0.7.0.1
 */