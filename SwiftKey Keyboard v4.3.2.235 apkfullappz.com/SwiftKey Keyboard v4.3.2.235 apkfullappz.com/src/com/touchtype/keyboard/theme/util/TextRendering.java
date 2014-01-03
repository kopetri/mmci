package com.touchtype.keyboard.theme.util;

import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.SparseArray;
import com.google.common.base.Objects;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import junit.framework.Assert;

public final class TextRendering
{
  private static final SparseArray<Rect> mMaxBoundsCache = new SparseArray();
  
  private static Rect calcMaxBounds(TextPaint paramTextPaint, boolean paramBoolean1, boolean paramBoolean2, Set<String> paramSet)
  {
    Rect localRect1 = new Rect();
    Rect localRect2 = new Rect();
    float f = paramTextPaint.getTextSize();
    paramTextPaint.setTextSize(100.0F);
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.length() != 0)
      {
        paramTextPaint.getTextBounds(str, 0, str.length(), localRect2);
        if (paramBoolean2)
        {
          localRect2.top = Math.min(localRect2.top, (int)Math.ceil(paramTextPaint.ascent()));
          localRect2.bottom = Math.max(localRect2.bottom, (int)Math.floor(paramTextPaint.descent()));
        }
        if (!paramBoolean1) {
          localRect2.offsetTo(0, 0);
        }
        localRect1.union(localRect2);
      }
    }
    paramTextPaint.setTextSize(f);
    return localRect1;
  }
  
  public static TextRenderInfo fitTextInBox(String paramString, TextPaint paramTextPaint, Rect paramRect, HAlign paramHAlign, VAlign paramVAlign, boolean paramBoolean1, boolean paramBoolean2, Set<String> paramSet, TextSizeLimiter paramTextSizeLimiter)
  {
    Assert.assertEquals(Paint.Align.LEFT, paramTextPaint.getTextAlign());
    if (paramSet == null)
    {
      paramSet = new HashSet();
      paramSet.add(paramString);
    }
    TextRenderInfo localTextRenderInfo = new TextRenderInfo();
    float f1 = paramTextPaint.getTextSize();
    Rect localRect1 = getMaxBounds(paramTextPaint, paramBoolean1, paramBoolean2, paramSet);
    float f2 = Math.min(paramRect.width() / localRect1.width(), paramRect.height() / localRect1.height());
    localTextRenderInfo.size = ((float)Math.floor(100.0F * f2));
    if (paramTextSizeLimiter.isTextNeedsLimiting(localTextRenderInfo.size))
    {
      float f3 = paramTextSizeLimiter.getUpperTextSizeLimit();
      f2 *= f3 / localTextRenderInfo.size;
      localTextRenderInfo.size = f3;
    }
    paramTextPaint.setTextSize(localTextRenderInfo.size);
    localRect1.left = ((int)Math.floor(f2 * localRect1.left));
    localRect1.top = ((int)Math.floor(f2 * localRect1.top));
    localRect1.right = ((int)Math.ceil(f2 * localRect1.right));
    localRect1.bottom = ((int)Math.ceil(f2 * localRect1.bottom));
    Rect localRect2 = new Rect();
    paramTextPaint.getTextBounds(paramString, 0, paramString.length(), localRect2);
    if (paramBoolean2)
    {
      localRect2.top = Math.min(localRect2.top, (int)Math.floor(paramTextPaint.ascent()));
      localRect2.bottom = Math.max(localRect2.bottom, (int)Math.ceil(paramTextPaint.descent()));
    }
    Rect localRect3;
    switch (1.$SwitchMap$com$touchtype$keyboard$theme$util$TextRendering$HAlign[paramHAlign.ordinal()])
    {
    default: 
      localTextRenderInfo.x = (Math.round(paramRect.centerX() - localRect2.width() / 2) - localRect2.left);
      if (paramBoolean1)
      {
        localRect3 = localRect1;
        label364:
        switch (1.$SwitchMap$com$touchtype$keyboard$theme$util$TextRendering$VAlign[paramVAlign.ordinal()])
        {
        default: 
          localTextRenderInfo.y = (Math.round(paramRect.centerY() - localRect3.height() / 2) - localRect3.top);
        }
      }
      break;
    }
    for (;;)
    {
      paramTextPaint.setTextSize(f1);
      return localTextRenderInfo;
      localTextRenderInfo.x = paramRect.left;
      break;
      localTextRenderInfo.x = (paramRect.right - localRect2.right);
      break;
      localRect3 = localRect2;
      break label364;
      localTextRenderInfo.y = (paramRect.top - localRect3.top);
      continue;
      localTextRenderInfo.y = (paramRect.bottom - localRect3.bottom);
    }
  }
  
  public static Rect getLinkSetBounds(TextPaint paramTextPaint, boolean paramBoolean1, boolean paramBoolean2, Set<String> paramSet)
  {
    return getMaxBounds(paramTextPaint, paramBoolean1, paramBoolean2, paramSet);
  }
  
  private static Rect getMaxBounds(TextPaint paramTextPaint, boolean paramBoolean1, boolean paramBoolean2, Set<String> paramSet)
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = paramTextPaint.getTypeface();
    arrayOfObject[1] = Boolean.valueOf(paramBoolean1);
    arrayOfObject[2] = Boolean.valueOf(paramBoolean2);
    arrayOfObject[3] = paramSet;
    int i = Objects.hashCode(arrayOfObject);
    Rect localRect = (Rect)mMaxBoundsCache.get(i);
    if (localRect == null)
    {
      localRect = calcMaxBounds(paramTextPaint, paramBoolean1, paramBoolean2, paramSet);
      mMaxBoundsCache.put(i, localRect);
    }
    return new Rect(localRect);
  }
  
  public static void resetCache()
  {
    mMaxBoundsCache.clear();
  }
  
  public static enum HAlign
  {
    static
    {
      CENTRE = new HAlign("CENTRE", 1);
      RIGHT = new HAlign("RIGHT", 2);
      HAlign[] arrayOfHAlign = new HAlign[3];
      arrayOfHAlign[0] = LEFT;
      arrayOfHAlign[1] = CENTRE;
      arrayOfHAlign[2] = RIGHT;
      $VALUES = arrayOfHAlign;
    }
    
    private HAlign() {}
  }
  
  public static final class TextRenderInfo
  {
    public float size;
    public int x;
    public int y;
  }
  
  public static enum VAlign
  {
    static
    {
      CENTRE = new VAlign("CENTRE", 1);
      BOTTOM = new VAlign("BOTTOM", 2);
      VAlign[] arrayOfVAlign = new VAlign[3];
      arrayOfVAlign[0] = TOP;
      arrayOfVAlign[1] = CENTRE;
      arrayOfVAlign[2] = BOTTOM;
      $VALUES = arrayOfVAlign;
    }
    
    private VAlign() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.util.TextRendering
 * JD-Core Version:    0.7.0.1
 */