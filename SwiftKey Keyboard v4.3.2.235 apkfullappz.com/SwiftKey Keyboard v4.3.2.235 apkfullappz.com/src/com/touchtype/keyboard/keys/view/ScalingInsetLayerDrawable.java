package com.touchtype.keyboard.keys.view;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.touchtype.keyboard.theme.util.RectUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class ScalingInsetLayerDrawable
  extends LayerDrawable
{
  @SuppressLint({"UseSparseArrays"})
  private final Map<Integer, Inset> mInsets = new HashMap();
  
  public ScalingInsetLayerDrawable(Drawable[] paramArrayOfDrawable)
  {
    super(paramArrayOfDrawable);
  }
  
  public boolean getPadding(Rect paramRect)
  {
    if (getNumberOfLayers() == 0) {
      paramRect.set(0, 0, 0, 0);
    }
    do
    {
      return false;
      for (int i = 0; i < getNumberOfLayers(); i++)
      {
        Drawable localDrawable = getDrawable(i);
        Rect localRect = new Rect();
        localDrawable.getPadding(localRect);
        paramRect.left = Math.max(localRect.left, paramRect.left);
        paramRect.bottom = Math.max(localRect.bottom, paramRect.bottom);
        paramRect.top = Math.max(localRect.top, paramRect.top);
        paramRect.right = Math.max(localRect.right, paramRect.right);
      }
    } while (RectUtil.equals(paramRect, 0, 0, 0, 0));
    return true;
  }
  
  public void onBoundsChange(Rect paramRect)
  {
    Iterator localIterator = this.mInsets.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Rect localRect = ((Inset)localEntry.getValue()).getScaledInset(paramRect);
      setLayerInset(((Integer)localEntry.getKey()).intValue(), localRect.left, localRect.top, localRect.right, localRect.bottom);
    }
    super.onBoundsChange(paramRect);
  }
  
  public void setScalingInset(int paramInt, Inset paramInset)
  {
    this.mInsets.put(Integer.valueOf(paramInt), paramInset);
  }
  
  public static final class Inset
  {
    private final Rect absoluteInset;
    private final RectF linearInset;
    
    public Inset()
    {
      this(new RectF(), new Rect());
    }
    
    public Inset(RectF paramRectF, Rect paramRect)
    {
      this.linearInset = paramRectF;
      this.absoluteInset = paramRect;
    }
    
    private Rect scaleInset(Rect paramRect)
    {
      return new Rect(Math.round(this.linearInset.left * paramRect.width()), Math.round(this.linearInset.top * paramRect.height()), Math.round(this.linearInset.right * paramRect.width()), Math.round(this.linearInset.bottom * paramRect.height()));
    }
    
    public Rect getScaledInset(Rect paramRect)
    {
      return RectUtil.add(scaleInset(paramRect), this.absoluteInset);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.keys.view.ScalingInsetLayerDrawable
 * JD-Core Version:    0.7.0.1
 */