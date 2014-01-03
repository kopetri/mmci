package com.touchtype.keyboard.keys.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;

public final class IconDrawable
  extends ResizeDrawable
{
  private final Drawable mDrawable;
  private final TextRendering.HAlign mHorizontalAlign;
  private final boolean mLimitByArea;
  private final float mMaxSize;
  private final boolean mPositionByFullscale;
  private final TextRendering.VAlign mVerticalAlign;
  
  public IconDrawable(Drawable paramDrawable, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mDrawable = paramDrawable;
    this.mHorizontalAlign = paramHAlign;
    this.mVerticalAlign = paramVAlign;
    this.mMaxSize = paramFloat;
    this.mPositionByFullscale = paramBoolean1;
    this.mLimitByArea = paramBoolean2;
  }
  
  private static Rect getIconRect(Drawable paramDrawable, Rect paramRect, float paramFloat, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, boolean paramBoolean)
  {
    Rect localRect = new Rect();
    int i = (int)(paramFloat * paramDrawable.getIntrinsicWidth());
    int j = (int)(paramFloat * paramDrawable.getIntrinsicHeight());
    float f = getScale(paramDrawable, paramRect, 1.0F, false);
    int k = (int)(f * paramDrawable.getIntrinsicWidth());
    int m = (int)(f * paramDrawable.getIntrinsicHeight());
    int n;
    label76:
    int i1;
    if (paramBoolean)
    {
      n = (int)((k - i) / 2.0F);
      i1 = 0;
      if (paramBoolean) {
        i1 = (int)((m - j) / 2.0F);
      }
      switch (1.$SwitchMap$com$touchtype$keyboard$theme$util$TextRendering$HAlign[paramHAlign.ordinal()])
      {
      default: 
        localRect.left = (paramRect.centerX() - i / 2);
        localRect.right = (paramRect.centerX() + i / 2);
      }
    }
    for (;;)
    {
      switch (1.$SwitchMap$com$touchtype$keyboard$theme$util$TextRendering$VAlign[paramVAlign.ordinal()])
      {
      default: 
        localRect.top = (paramRect.centerY() - j / 2);
        localRect.bottom = (paramRect.centerY() + j / 2);
        return localRect;
        n = 0;
        break label76;
        localRect.left = (n + paramRect.left);
        localRect.right = (n + (i + paramRect.left));
        continue;
        localRect.left = (paramRect.right - i - n);
        paramRect.right -= n;
      }
    }
    localRect.top = (i1 + paramRect.top);
    localRect.bottom = (i1 + (j + paramRect.top));
    return localRect;
    localRect.top = (paramRect.bottom - j - i1);
    paramRect.bottom -= i1;
    return localRect;
  }
  
  private static float getScale(Drawable paramDrawable, Rect paramRect, float paramFloat, boolean paramBoolean)
  {
    float f1;
    if (paramBoolean)
    {
      f1 = Math.min(paramFloat, paramFloat * paramDrawable.getIntrinsicHeight() / paramDrawable.getIntrinsicWidth());
      if (!paramBoolean) {
        break label88;
      }
    }
    label88:
    for (float f2 = Math.min(paramFloat, paramFloat * paramDrawable.getIntrinsicWidth() / paramDrawable.getIntrinsicHeight());; f2 = paramFloat)
    {
      return Math.min(1.0F, Math.min(f2 * paramRect.width() / paramDrawable.getIntrinsicWidth(), f1 * paramRect.height() / paramDrawable.getIntrinsicHeight()));
      f1 = paramFloat;
      break;
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    this.mDrawable.draw(paramCanvas);
  }
  
  public int getIntrinsicHeight()
  {
    return this.mDrawable.getIntrinsicHeight();
  }
  
  public int getIntrinsicWidth()
  {
    return this.mDrawable.getIntrinsicWidth();
  }
  
  public int getOpacity()
  {
    return this.mDrawable.getOpacity();
  }
  
  public int[] getState()
  {
    return this.mDrawable.getState();
  }
  
  public float getWHRatio()
  {
    return getIntrinsicWidth() / getIntrinsicHeight();
  }
  
  public void onBoundsChange(Rect paramRect)
  {
    float f = getScale(this.mDrawable, paramRect, this.mMaxSize, this.mLimitByArea);
    Rect localRect = getIconRect(this.mDrawable, paramRect, f, this.mHorizontalAlign, this.mVerticalAlign, this.mPositionByFullscale);
    this.mDrawable.setBounds(localRect);
  }
  
  public void setAlpha(int paramInt)
  {
    this.mDrawable.setAlpha(paramInt);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mDrawable.setColorFilter(paramColorFilter);
  }
  
  public boolean setState(int[] paramArrayOfInt)
  {
    return this.mDrawable.setState(paramArrayOfInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.keys.view.IconDrawable
 * JD-Core Version:    0.7.0.1
 */