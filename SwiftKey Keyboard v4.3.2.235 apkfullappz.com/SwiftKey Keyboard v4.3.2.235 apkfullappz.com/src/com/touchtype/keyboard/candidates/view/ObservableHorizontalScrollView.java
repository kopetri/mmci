package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import com.touchtype.util.WeakHashSet;
import java.util.Iterator;
import java.util.Set;

public class ObservableHorizontalScrollView
  extends HorizontalScrollView
{
  private final Set<OnHorizontalScrollListener> mScrollListeners = new WeakHashSet(1);
  
  public ObservableHorizontalScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void addOnHorizontalScrollListener(OnHorizontalScrollListener paramOnHorizontalScrollListener)
  {
    this.mScrollListeners.add(paramOnHorizontalScrollListener);
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((paramInt3 != paramInt1) || (paramInt4 != paramInt2))
    {
      Iterator localIterator = this.mScrollListeners.iterator();
      while (localIterator.hasNext()) {
        ((OnHorizontalScrollListener)localIterator.next()).onScroll(paramInt1, paramInt2);
      }
    }
  }
  
  public static abstract interface OnHorizontalScrollListener
  {
    public abstract void onScroll(int paramInt1, int paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.ObservableHorizontalScrollView
 * JD-Core Version:    0.7.0.1
 */