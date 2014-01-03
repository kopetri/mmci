package com.touchtype.keyboard.view;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.widget.PopupWindow;
import com.touchtype.util.LogUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

@SuppressLint({"UseSparseArrays"})
public abstract class DelayedDismissalPopupProvider<T extends PopupWindow>
  implements PopupProvider<T>
{
  private static final String TAG = DelayedDismissalPopupProvider.class.getSimpleName();
  private final Map<Integer, T> mActivePopupPool = new HashMap(5);
  private final Set<T> mCheckedOutPopups = new HashSet(5);
  private final Stack<T> mPopupPool = new Stack();
  private Handler mPopupRecycler = new Handler(Looper.getMainLooper());
  private boolean mRecycled = false;
  
  public DelayedDismissalPopupProvider()
  {
    for (int i = 0; i < 5; i++) {
      this.mPopupPool.push(createPopup());
    }
  }
  
  private void dismiss(T paramT)
  {
    try
    {
      if (paramT.isShowing()) {
        paramT.dismiss();
      }
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      LogUtil.e(TAG, "Exception occurred trying to dismiss a popup: " + localRuntimeException.getLocalizedMessage());
    }
  }
  
  private Runnable getRecycleRunnable(final int paramInt)
  {
    new Runnable()
    {
      public void run()
      {
        PopupWindow localPopupWindow = (PopupWindow)DelayedDismissalPopupProvider.this.mActivePopupPool.remove(Integer.valueOf(paramInt));
        if (localPopupWindow != null)
        {
          DelayedDismissalPopupProvider.this.dismiss(localPopupWindow);
          DelayedDismissalPopupProvider.this.mPopupPool.push(localPopupWindow);
        }
      }
    };
  }
  
  protected abstract T createPopup();
  
  public void detachPopup(T paramT)
  {
    if (paramT == null) {
      return;
    }
    this.mCheckedOutPopups.remove(paramT);
    this.mActivePopupPool.put(Integer.valueOf(paramT.hashCode()), paramT);
    this.mPopupRecycler.postDelayed(getRecycleRunnable(paramT.hashCode()), 5L);
  }
  
  public T getPopup()
    throws IllegalStateException
  {
    if (this.mRecycled) {
      throw new IllegalStateException("Attempt to use a recycled popup provider");
    }
    PopupWindow localPopupWindow;
    if (!this.mActivePopupPool.isEmpty())
    {
      Integer localInteger = (Integer)this.mActivePopupPool.keySet().iterator().next();
      localPopupWindow = (PopupWindow)this.mActivePopupPool.remove(localInteger);
    }
    for (;;)
    {
      this.mCheckedOutPopups.add(localPopupWindow);
      return localPopupWindow;
      if (!this.mPopupPool.empty()) {
        localPopupWindow = (PopupWindow)this.mPopupPool.pop();
      } else {
        localPopupWindow = createPopup();
      }
    }
  }
  
  public void recycle()
  {
    Iterator localIterator1 = this.mCheckedOutPopups.iterator();
    while (localIterator1.hasNext()) {
      dismiss((PopupWindow)localIterator1.next());
    }
    Iterator localIterator2 = this.mActivePopupPool.entrySet().iterator();
    while (localIterator2.hasNext()) {
      dismiss((PopupWindow)((Map.Entry)localIterator2.next()).getValue());
    }
    this.mCheckedOutPopups.clear();
    this.mActivePopupPool.clear();
    this.mPopupPool.clear();
    this.mRecycled = true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.DelayedDismissalPopupProvider
 * JD-Core Version:    0.7.0.1
 */