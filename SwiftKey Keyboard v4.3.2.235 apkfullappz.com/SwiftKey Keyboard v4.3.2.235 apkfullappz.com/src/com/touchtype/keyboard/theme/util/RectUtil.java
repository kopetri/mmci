package com.touchtype.keyboard.theme.util;

import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public final class RectUtil
{
  public static Rect add(Rect paramRect1, Rect paramRect2)
  {
    return new Rect(paramRect1.left + paramRect2.left, paramRect1.top + paramRect2.top, paramRect1.right + paramRect2.right, paramRect1.bottom + paramRect2.bottom);
  }
  
  public static RectF div(RectF paramRectF1, RectF paramRectF2)
  {
    if ((paramRectF2.width() == 0.0F) || (paramRectF2.height() == 0.0F)) {
      throw new IllegalArgumentException("Divisor cannot have 0 height or width");
    }
    return new RectF(paramRectF1.left / paramRectF2.width(), paramRectF1.top / paramRectF2.height(), paramRectF1.right / paramRectF2.width(), paramRectF1.bottom / paramRectF2.height());
  }
  
  public static boolean equals(Rect paramRect, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return (paramRect.bottom == paramInt1) && (paramRect.left == paramInt2) && (paramRect.right == paramInt3) && (paramRect.top == paramInt4);
  }
  
  public static Rect expandToPad(Rect paramRect1, Rect paramRect2)
  {
    return new Rect(paramRect1.left - paramRect2.left, paramRect1.top - paramRect2.top, paramRect1.right + paramRect2.right, paramRect1.bottom + paramRect2.bottom);
  }
  
  public static Rect getPadding(Drawable paramDrawable)
  {
    Rect localRect = new Rect();
    paramDrawable.getPadding(localRect);
    return localRect;
  }
  
  public static Rect shrinkToPad(Rect paramRect1, Rect paramRect2)
  {
    return new Rect(paramRect1.left + paramRect2.left, paramRect1.top + paramRect2.top, paramRect1.right - paramRect2.right, paramRect1.bottom - paramRect2.bottom);
  }
  
  public static RectF shrinkToPad(RectF paramRectF1, RectF paramRectF2)
  {
    return new RectF(paramRectF1.left + paramRectF2.left, paramRectF1.top + paramRectF2.top, paramRectF1.right - paramRectF2.right, paramRectF1.bottom - paramRectF2.bottom);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.RectUtil
 * JD-Core Version:    0.7.0.1
 */