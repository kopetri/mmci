package com.touchtype.keyboard.keys.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

public final class SpacebarLanguageDrawable
  extends ResizeDrawable
{
  private String mCurrentLanguage;
  private String mCurrentLanguageShort;
  private String mDisplayText;
  private final Drawable mLeftDrawable;
  private final Drawable mRightDrawable;
  private float mScale;
  private final TextPaint mTextPaint;
  
  public SpacebarLanguageDrawable(float paramFloat, TextPaint paramTextPaint, ResizeDrawable paramResizeDrawable1, ResizeDrawable paramResizeDrawable2, String paramString1, String paramString2)
  {
    this.mLeftDrawable = paramResizeDrawable1;
    this.mRightDrawable = paramResizeDrawable2;
    setCurrentLanguage(paramString1, paramString2);
    this.mTextPaint = new TextPaint();
    this.mTextPaint.set(paramTextPaint);
    this.mTextPaint.setTextAlign(Paint.Align.CENTER);
    this.mTextPaint.setAlpha(128);
    this.mTextPaint.setAntiAlias(true);
    this.mTextPaint.setTextSize(dipToPx(18, paramFloat));
  }
  
  private float arrowScaling()
  {
    float f = getBounds().width() / (this.mLeftDrawable.getIntrinsicWidth() + this.mRightDrawable.getIntrinsicWidth());
    if (f < 1.0F) {
      return f;
    }
    return 1.0F;
  }
  
  private String calculateStringToDisplay()
  {
    if (textFitsTheDrawable(this.mCurrentLanguage)) {
      return this.mCurrentLanguage;
    }
    if (requiredScaling(this.mCurrentLanguage) > 0.5F)
    {
      this.mScale = requiredScaling(this.mCurrentLanguage);
      return this.mCurrentLanguage;
    }
    if (textFitsTheDrawable(this.mCurrentLanguageShort)) {
      return this.mCurrentLanguageShort;
    }
    if (requiredScaling(this.mCurrentLanguageShort) > 0.5F)
    {
      this.mScale = requiredScaling(this.mCurrentLanguageShort);
      return this.mCurrentLanguageShort;
    }
    this.mScale = arrowScaling();
    return null;
  }
  
  private int dipToPx(int paramInt, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramInt);
  }
  
  private Rect getArrowBounds()
  {
    int i = (int)(this.mScale * this.mLeftDrawable.getIntrinsicWidth());
    int j = (int)(this.mScale * this.mLeftDrawable.getIntrinsicHeight());
    return new Rect(0, (getBounds().height() - j) / 2, i, (j + getBounds().height()) / 2);
  }
  
  private Rect getLeftArrowBounds()
  {
    Rect localRect1 = getBounds();
    Rect localRect2 = getArrowBounds();
    localRect2.offset(localRect1.left, localRect1.top);
    return localRect2;
  }
  
  private Rect getRightArrowBounds()
  {
    Rect localRect = getLeftArrowBounds();
    int i = (int)(this.mScale * this.mRightDrawable.getIntrinsicWidth());
    localRect.offset(getBounds().width() - i, 0);
    return localRect;
  }
  
  private int getTextWidth(String paramString)
  {
    if (paramString == null) {
      return 0;
    }
    Rect localRect = new Rect();
    this.mTextPaint.getTextBounds(paramString, 0, paramString.length(), localRect);
    return localRect.width();
  }
  
  private float requiredScaling(String paramString)
  {
    int i = getTextWidth(paramString);
    if ((this.mLeftDrawable == null) || (this.mRightDrawable == null)) {
      return 0.0F;
    }
    int j = this.mLeftDrawable.getIntrinsicWidth() + this.mRightDrawable.getIntrinsicWidth();
    return (0 + getBounds().width() - i) / j;
  }
  
  private boolean textFitsTheDrawable(String paramString)
  {
    int i = getTextWidth(paramString);
    if ((this.mLeftDrawable == null) || (this.mRightDrawable == null)) {}
    while (getBounds().width() - this.mLeftDrawable.getIntrinsicWidth() - this.mRightDrawable.getIntrinsicWidth() - i <= 0) {
      return false;
    }
    return true;
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect = getBounds();
    paramCanvas.save();
    paramCanvas.clipRect(localRect);
    int i = this.mLeftDrawable.getOpacity();
    int j = this.mRightDrawable.getOpacity();
    this.mLeftDrawable.setAlpha(128);
    this.mRightDrawable.setAlpha(128);
    this.mLeftDrawable.draw(paramCanvas);
    this.mRightDrawable.draw(paramCanvas);
    this.mLeftDrawable.setAlpha(i);
    this.mRightDrawable.setAlpha(j);
    if (this.mDisplayText != null)
    {
      float f = localRect.top + (localRect.height() - this.mTextPaint.ascent() - this.mTextPaint.descent()) / 2.0F;
      paramCanvas.drawText(this.mDisplayText, localRect.left + localRect.width() / 2, f, this.mTextPaint);
    }
    paramCanvas.restore();
  }
  
  public int getIntrinsicHeight()
  {
    return getBounds().height();
  }
  
  public int getIntrinsicWidth()
  {
    return getBounds().width();
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public float getWHRatio()
  {
    return 0.0F;
  }
  
  public void onBoundsChange(Rect paramRect)
  {
    this.mScale = 1.0F;
    this.mDisplayText = calculateStringToDisplay();
    this.mLeftDrawable.setBounds(getLeftArrowBounds());
    this.mRightDrawable.setBounds(getRightArrowBounds());
  }
  
  public void setAlpha(int paramInt) {}
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mLeftDrawable.setColorFilter(paramColorFilter);
    this.mRightDrawable.setColorFilter(paramColorFilter);
  }
  
  public void setCurrentLanguage(String paramString1, String paramString2)
  {
    this.mCurrentLanguage = paramString1;
    this.mCurrentLanguageShort = paramString2;
  }
  
  public void setTextColor(int paramInt)
  {
    this.mTextPaint.setColor(paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.keys.view.SpacebarLanguageDrawable
 * JD-Core Version:    0.7.0.1
 */