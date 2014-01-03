package com.touchtype.keyboard.keys.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import com.touchtype.keyboard.LanguageSwitchData;
import com.touchtype.keyboard.LanguageSwitchData.LanguageSwitchEntry;

public final class SlidingLayoutDrawable
  extends Drawable
{
  private final String TAG = "SlidingLayoutDrawable";
  private int mDiff;
  private String[] mDisplayStrings;
  private int mHeight = 100;
  private final Drawable mLeftDrawable;
  private final Drawable mRightDrawable;
  private final float mScale;
  private String[] mShortStrings;
  private String[] mStrings;
  private final TextPaint mTextPaint;
  private final int mTouchThreshold;
  private boolean mVisible;
  private int mWidth;
  
  public SlidingLayoutDrawable(float paramFloat, TextPaint paramTextPaint, ResizeDrawable paramResizeDrawable1, ResizeDrawable paramResizeDrawable2, int paramInt, LanguageSwitchData paramLanguageSwitchData)
  {
    this.mTouchThreshold = paramInt;
    this.mLeftDrawable = paramResizeDrawable1;
    this.mRightDrawable = paramResizeDrawable2;
    this.mTextPaint = paramTextPaint;
    this.mTextPaint.setTextAlign(Paint.Align.CENTER);
    this.mTextPaint.setAntiAlias(true);
    this.mDisplayStrings = new String[3];
    this.mStrings = getFullNames(paramLanguageSwitchData);
    this.mShortStrings = getShortNames(paramLanguageSwitchData);
    this.mScale = paramFloat;
  }
  
  private float chooseDisplayStringAndScale(int paramInt1, int paramInt2)
  {
    float f1 = dipToPx(18);
    int i = getTextWidth(this.mStrings[paramInt1], f1);
    float f2 = Math.min(1.0F, paramInt2 / i);
    if (f2 * f1 >= dipToPx(16))
    {
      this.mDisplayStrings[paramInt1] = this.mStrings[paramInt1];
      return f2 * f1;
    }
    int j = getTextWidth(this.mShortStrings[paramInt1], f1);
    float f3 = Math.min(1.0F, paramInt2 / j);
    this.mDisplayStrings[paramInt1] = this.mShortStrings[paramInt1];
    return f3 * f1;
  }
  
  private int dipToPx(int paramInt)
  {
    return (int)(0.5F + paramInt * this.mScale);
  }
  
  private Rect getArrowBounds()
  {
    int i = (int)(this.mScale * this.mLeftDrawable.getIntrinsicWidth());
    int j = (int)(this.mScale * this.mLeftDrawable.getIntrinsicHeight());
    return new Rect(0, (getBounds().height() - j) / 2, i, (j + getBounds().height()) / 2);
  }
  
  private String[] getFullNames(LanguageSwitchData paramLanguageSwitchData)
  {
    String[] arrayOfString = new String[3];
    arrayOfString[0] = paramLanguageSwitchData.getPrevLanguageSwitchEntry().getFullLabel();
    arrayOfString[1] = paramLanguageSwitchData.getCurrentLanguageSwitchEntry().getFullLabel();
    arrayOfString[2] = paramLanguageSwitchData.getNextLanguageSwitchEntry().getFullLabel();
    return arrayOfString;
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
  
  private String[] getShortNames(LanguageSwitchData paramLanguageSwitchData)
  {
    String[] arrayOfString = new String[3];
    arrayOfString[0] = paramLanguageSwitchData.getPrevLanguageSwitchEntry().getShortLabel();
    arrayOfString[1] = paramLanguageSwitchData.getCurrentLanguageSwitchEntry().getShortLabel();
    arrayOfString[2] = paramLanguageSwitchData.getNextLanguageSwitchEntry().getShortLabel();
    return arrayOfString;
  }
  
  private int getTextWidth(String paramString, float paramFloat)
  {
    if (paramString == null) {
      return 0;
    }
    Rect localRect = new Rect();
    this.mTextPaint.setTextSize(paramFloat);
    this.mTextPaint.getTextBounds(paramString, 0, paramString.length(), localRect);
    return localRect.width();
  }
  
  public void draw(Canvas paramCanvas)
  {
    TextPaint localTextPaint = this.mTextPaint;
    int i = this.mWidth;
    int j = this.mHeight;
    int k = this.mDiff;
    Drawable localDrawable1 = this.mLeftDrawable;
    Drawable localDrawable2 = this.mRightDrawable;
    paramCanvas.save();
    paramCanvas.clipRect(localDrawable1.getIntrinsicWidth(), 0, i - localDrawable2.getIntrinsicWidth(), j);
    float f1 = (j - localTextPaint.ascent() - localTextPaint.descent()) / 2.0F;
    float f2 = localTextPaint.getTextSize();
    paramCanvas.drawText(this.mDisplayStrings[1], k + i / 2, f1, localTextPaint);
    paramCanvas.drawText(this.mDisplayStrings[2], k - f2, f1, localTextPaint);
    paramCanvas.drawText(this.mDisplayStrings[0], f2 + (k + i), f1, localTextPaint);
    localDrawable1.setBounds(getLeftArrowBounds());
    localDrawable2.setBounds(getRightArrowBounds());
    paramCanvas.restore();
    paramCanvas.save();
    paramCanvas.clipRect(0, 0, i, j);
    localDrawable1.draw(paramCanvas);
    localDrawable2.draw(paramCanvas);
    paramCanvas.restore();
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public void onBoundsChange(Rect paramRect)
  {
    this.mWidth = paramRect.width();
    this.mHeight = paramRect.height();
    float f1 = this.mLeftDrawable.getIntrinsicWidth() / this.mLeftDrawable.getIntrinsicHeight() * this.mHeight;
    float f2 = this.mRightDrawable.getIntrinsicWidth() / this.mRightDrawable.getIntrinsicHeight() * this.mHeight;
    int i = (int)f1 + (int)f2;
    int j = this.mWidth - i - i / 2;
    float f3 = Math.min(Math.min(chooseDisplayStringAndScale(0, j), chooseDisplayStringAndScale(1, j)), chooseDisplayStringAndScale(2, j));
    this.mTextPaint.setTextSize(f3);
  }
  
  public void setAlpha(int paramInt) {}
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mLeftDrawable.setColorFilter(paramColorFilter);
    this.mRightDrawable.setColorFilter(paramColorFilter);
  }
  
  public void setDrag(int paramInt)
  {
    this.mDiff = paramInt;
    if (Math.abs(this.mDiff) > this.mTouchThreshold) {
      this.mVisible = true;
    }
    int i = Math.round(this.mWidth / 2 + this.mTextPaint.getTextSize());
    if (this.mDiff > i) {
      this.mDiff = i;
    }
    if (this.mDiff < -i) {
      this.mDiff = (-i);
    }
    invalidateSelf();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.keys.view.SlidingLayoutDrawable
 * JD-Core Version:    0.7.0.1
 */