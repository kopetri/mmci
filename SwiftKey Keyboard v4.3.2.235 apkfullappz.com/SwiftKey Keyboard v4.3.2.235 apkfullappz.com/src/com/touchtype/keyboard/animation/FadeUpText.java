package com.touchtype.keyboard.animation;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.touchtype.R.styleable;
import com.touchtype.keyboard.view.AppTypeFaceCache;

public class FadeUpText
  extends PopupWindow
{
  private AnimatorSet mAnimator;
  private int mFadeDy;
  
  static
  {
    if (!FadeUpText.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public void setContentView(View paramView)
  {
    if ((this.mAnimator != null) && (this.mAnimator.isRunning())) {
      this.mAnimator.end();
    }
    dismiss();
    super.setContentView(paramView);
  }
  
  public void showAtLocation(View paramView, int paramInt1, int paramInt2, int paramInt3)
  {
    super.showAtLocation(paramView, paramInt1, paramInt2, paramInt3 - this.mFadeDy);
    this.mAnimator.start();
  }
  
  public static class TextContainer
    extends TextView
  {
    private int shadowColor;
    private float shadowOffsetX;
    private float shadowOffsetY;
    private float shadowRadius;
    
    public TextContainer(Context paramContext)
    {
      this(paramContext, null);
    }
    
    public TextContainer(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public TextContainer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
      setFont(paramContext, paramAttributeSet, paramInt);
    }
    
    private void setFont(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyPreviewTextView, paramInt, 0);
      setTypeface(AppTypeFaceCache.getFont(paramContext, localTypedArray.getText(1), Typeface.DEFAULT));
      localTypedArray.recycle();
    }
    
    public int getShadowColor()
    {
      return this.shadowColor;
    }
    
    public float getShadowRadius()
    {
      return this.shadowRadius;
    }
    
    public int getTextColor()
    {
      return getCurrentTextColor();
    }
    
    public int getTopMargin()
    {
      return ((RelativeLayout.LayoutParams)getLayoutParams()).topMargin;
    }
    
    public void setShadowColor(int paramInt)
    {
      setShadowLayer(this.shadowRadius, this.shadowOffsetX, this.shadowOffsetY, paramInt);
    }
    
    public void setShadowLayer(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
    {
      super.setShadowLayer(paramFloat1, paramFloat2, paramFloat3, paramInt);
      this.shadowRadius = paramFloat1;
      this.shadowOffsetX = paramFloat2;
      this.shadowOffsetY = paramFloat3;
      this.shadowColor = paramInt;
    }
    
    public void setShadowRadius(float paramFloat)
    {
      setShadowLayer(paramFloat, this.shadowOffsetX, this.shadowOffsetY, this.shadowColor);
    }
    
    public void setTopMargin(int paramInt)
    {
      RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
      localLayoutParams.topMargin = paramInt;
      setLayoutParams(localLayoutParams);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.animation.FadeUpText
 * JD-Core Version:    0.7.0.1
 */