package com.touchtype.keyboard.keys.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.text.TextPaint;
import com.touchtype.keyboard.theme.util.TextRendering;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.TextRenderInfo;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import com.touchtype.keyboard.theme.util.TextSizeLimiter;
import java.util.Set;

public class MainTextKeyDrawable
  extends ResizeDrawable
{
  private static final String TAG = MainTextKeyDrawable.class.getSimpleName();
  private final TextRendering.HAlign mHorizontalAlign;
  private final boolean mKeepBaseline;
  private final boolean mKeepMinimumHeight;
  private final Set<String> mLinkSet;
  private final float mMaxSize;
  protected final String mText;
  protected final TextPaint mTextPaint;
  protected TextRendering.TextRenderInfo mTextRenderInfo;
  private final TextSizeLimiter mTextSizeLimiter;
  private final TextRendering.VAlign mVerticalAlign;
  private float mWHRatio = -1.0F;
  
  public MainTextKeyDrawable(String paramString, TextPaint paramTextPaint, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat, TextSizeLimiter paramTextSizeLimiter)
  {
    this(paramString, paramTextPaint, paramHAlign, paramVAlign, paramFloat, null, paramTextSizeLimiter);
  }
  
  public MainTextKeyDrawable(String paramString, TextPaint paramTextPaint, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat, Set<String> paramSet, TextSizeLimiter paramTextSizeLimiter) {}
  
  public MainTextKeyDrawable(String paramString, TextPaint paramTextPaint, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat, Set<String> paramSet, boolean paramBoolean1, boolean paramBoolean2, TextSizeLimiter paramTextSizeLimiter)
  {
    this.mText = paramString;
    this.mTextPaint = paramTextPaint;
    this.mTextPaint.setAlpha(255);
    this.mTextPaint.setTextAlign(Paint.Align.LEFT);
    this.mTextPaint.setAntiAlias(true);
    this.mMaxSize = paramFloat;
    this.mHorizontalAlign = paramHAlign;
    this.mVerticalAlign = paramVAlign;
    this.mLinkSet = paramSet;
    this.mKeepBaseline = paramBoolean1;
    this.mKeepMinimumHeight = paramBoolean2;
    this.mTextSizeLimiter = paramTextSizeLimiter;
  }
  
  private static Rect getMaxBounds(Rect paramRect, float paramFloat, TextRendering.VAlign paramVAlign)
  {
    if (paramFloat > 1.0F) {
      paramFloat = 1.0F;
    }
    switch (1.$SwitchMap$com$touchtype$keyboard$theme$util$TextRendering$VAlign[paramVAlign.ordinal()])
    {
    default: 
      int i = (int)Math.floor(paramFloat * paramRect.height() / 2.0F);
      return new Rect(paramRect.left, paramRect.centerY() - i, paramRect.right, i + paramRect.centerY());
    case 1: 
      return new Rect(paramRect.left, paramRect.top, paramRect.right, paramRect.top + (int)Math.floor(paramFloat * paramRect.height()));
    }
    return new Rect(paramRect.left, paramRect.bottom - (int)Math.floor(paramFloat * paramRect.height()), paramRect.right, paramRect.bottom);
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (this.mTextRenderInfo == null) {
      return;
    }
    paramCanvas.save();
    paramCanvas.clipRect(getBounds());
    this.mTextPaint.setTextSize(this.mTextRenderInfo.size);
    paramCanvas.drawText(this.mText, this.mTextRenderInfo.x, this.mTextRenderInfo.y, this.mTextPaint);
    paramCanvas.restore();
  }
  
  public int getOpacity()
  {
    return this.mTextPaint.getAlpha();
  }
  
  public float getWHRatio()
  {
    boolean bool1 = true;
    Rect localRect;
    if (this.mWHRatio == -1.0F)
    {
      localRect = new Rect();
      if (this.mLinkSet == null)
      {
        this.mTextPaint.getTextBounds(this.mText, 0, this.mText.length(), localRect);
        localRect.top = ((int)Math.min(localRect.top, this.mTextPaint.ascent()));
        localRect.bottom = ((int)Math.max(localRect.bottom, this.mTextPaint.descent()));
        this.mWHRatio = (localRect.width() / localRect.height());
      }
    }
    else
    {
      return this.mWHRatio;
    }
    TextPaint localTextPaint = this.mTextPaint;
    boolean bool2;
    if (this.mLinkSet != null)
    {
      bool2 = bool1;
      label122:
      if (this.mLinkSet != null) {
        break label150;
      }
    }
    for (;;)
    {
      localRect = TextRendering.getLinkSetBounds(localTextPaint, bool2, bool1, this.mLinkSet);
      break;
      bool2 = false;
      break label122;
      label150:
      bool1 = false;
    }
  }
  
  public void onBoundsChange(Rect paramRect)
  {
    Rect localRect = getMaxBounds(paramRect, this.mMaxSize, this.mVerticalAlign);
    this.mTextRenderInfo = TextRendering.fitTextInBox(this.mText, this.mTextPaint, localRect, this.mHorizontalAlign, this.mVerticalAlign, this.mKeepBaseline, this.mKeepMinimumHeight, this.mLinkSet, this.mTextSizeLimiter);
  }
  
  public void setAlpha(int paramInt)
  {
    this.mTextPaint.setAlpha(paramInt);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.keys.view.MainTextKeyDrawable
 * JD-Core Version:    0.7.0.1
 */