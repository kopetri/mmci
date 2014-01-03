package android.support.v4.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

final class ScrollerCompatGingerbread
{
  public static void abortAnimation(Object paramObject)
  {
    ((OverScroller)paramObject).abortAnimation();
  }
  
  public static boolean computeScrollOffset(Object paramObject)
  {
    return ((OverScroller)paramObject).computeScrollOffset();
  }
  
  public static Object createScroller(Context paramContext, Interpolator paramInterpolator)
  {
    if (paramInterpolator != null) {
      return new OverScroller(paramContext, paramInterpolator);
    }
    return new OverScroller(paramContext);
  }
  
  public static int getCurrX(Object paramObject)
  {
    return ((OverScroller)paramObject).getCurrX();
  }
  
  public static int getCurrY(Object paramObject)
  {
    return ((OverScroller)paramObject).getCurrY();
  }
  
  public static int getFinalX(Object paramObject)
  {
    return ((OverScroller)paramObject).getFinalX();
  }
  
  public static int getFinalY(Object paramObject)
  {
    return ((OverScroller)paramObject).getFinalY();
  }
  
  public static boolean isFinished(Object paramObject)
  {
    return ((OverScroller)paramObject).isFinished();
  }
  
  public static void startScroll(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    ((OverScroller)paramObject).startScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.ScrollerCompatGingerbread
 * JD-Core Version:    0.7.0.1
 */