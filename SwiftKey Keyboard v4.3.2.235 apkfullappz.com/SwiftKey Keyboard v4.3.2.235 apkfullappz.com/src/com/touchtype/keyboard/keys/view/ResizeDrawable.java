package com.touchtype.keyboard.keys.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public abstract class ResizeDrawable
  extends Drawable
{
  public abstract float getWHRatio();
  
  public static final class EmptyDrawable
    extends ResizeDrawable
  {
    public void draw(Canvas paramCanvas) {}
    
    public int getOpacity()
    {
      return 0;
    }
    
    public float getWHRatio()
    {
      return 0.0F;
    }
    
    public void setAlpha(int paramInt) {}
    
    public void setColorFilter(ColorFilter paramColorFilter) {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.keys.view.ResizeDrawable
 * JD-Core Version:    0.7.0.1
 */