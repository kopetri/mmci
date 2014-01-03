package com.touchtype.keyboard.key;

import android.graphics.RectF;
import com.touchtype.keyboard.theme.util.RectUtil;

public final class KeyArea
{
  public int mEdgeFlags;
  private final RectF mPadding;
  private final RectF mTrueBounds;
  
  public KeyArea(RectF paramRectF, int paramInt)
  {
    this(paramRectF, new RectF(), paramInt);
  }
  
  public KeyArea(RectF paramRectF1, RectF paramRectF2, int paramInt)
  {
    this.mTrueBounds = paramRectF1;
    this.mPadding = paramRectF2;
    this.mEdgeFlags = paramInt;
  }
  
  private static RectF containsRect(RectF paramRectF, int paramInt)
  {
    int i = 1;
    if ((paramInt & 0xF) == 0) {
      return paramRectF;
    }
    int j;
    int k;
    label28:
    int m;
    label37:
    label44:
    float f1;
    label52:
    float f2;
    label61:
    float f3;
    if ((paramInt & 0x1) > 0)
    {
      j = i;
      if ((paramInt & 0x4) <= 0) {
        break label99;
      }
      k = i;
      if ((paramInt & 0x2) <= 0) {
        break label105;
      }
      m = i;
      if ((paramInt & 0x8) <= 0) {
        break label111;
      }
      if (j == 0) {
        break label116;
      }
      f1 = -2.147484E+009F;
      if (k == 0) {
        break label125;
      }
      f2 = -2.147484E+009F;
      if (m == 0) {
        break label134;
      }
      f3 = 2.147484E+009F;
      label70:
      if (i == 0) {
        break label143;
      }
    }
    label134:
    label143:
    for (float f4 = 2.147484E+009F;; f4 = paramRectF.bottom)
    {
      return new RectF(f1, f2, f3, f4);
      j = 0;
      break;
      label99:
      k = 0;
      break label28;
      label105:
      m = 0;
      break label37;
      label111:
      i = 0;
      break label44;
      label116:
      f1 = paramRectF.left;
      break label52;
      label125:
      f2 = paramRectF.top;
      break label61;
      f3 = paramRectF.right;
      break label70;
    }
  }
  
  private void updatePadding(RectF paramRectF)
  {
    float f1 = this.mTrueBounds.width() / paramRectF.width();
    float f2 = this.mTrueBounds.height() / paramRectF.height();
    this.mPadding.set(f1 * this.mPadding.left, f2 * this.mPadding.top, f1 * this.mPadding.right, f2 * this.mPadding.bottom);
  }
  
  public boolean contains(float paramFloat1, float paramFloat2)
  {
    return containsRect(this.mTrueBounds, this.mEdgeFlags).contains(paramFloat1, paramFloat2);
  }
  
  public RectF getBounds()
  {
    return this.mTrueBounds;
  }
  
  public RectF getDrawBounds()
  {
    return RectUtil.shrinkToPad(this.mTrueBounds, this.mPadding);
  }
  
  public RectF getPadding()
  {
    return RectUtil.div(this.mPadding, this.mTrueBounds);
  }
  
  public void set(RectF paramRectF)
  {
    if ((paramRectF.width() == 0.0F) || (paramRectF.height() == 0.0F)) {
      throw new IllegalArgumentException("Illegal RectF - must have height and width > 0");
    }
    updatePadding(paramRectF);
    this.mTrueBounds.set(paramRectF);
  }
  
  public String toString()
  {
    return getDrawBounds().toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.KeyArea
 * JD-Core Version:    0.7.0.1
 */