package com.touchtype.keyboard.candidates.view;

import com.touchtype.util.WeakHashSet;
import java.util.Iterator;
import java.util.Set;

public final class AsianCandidatePaneTracker
{
  private int mLeftCandidates;
  private final int mMaxCandidates;
  private int mRightCandidates;
  private final Set<OnLeftPaneUpdatedListener> mUpdateListeners;
  
  public AsianCandidatePaneTracker(int paramInt)
  {
    this.mMaxCandidates = paramInt;
    this.mUpdateListeners = new WeakHashSet(1);
  }
  
  private void notifyListeners()
  {
    Iterator localIterator = this.mUpdateListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnLeftPaneUpdatedListener)localIterator.next()).onPaneUpdated();
    }
  }
  
  public void addOnLeftPaneUpdatedListener(OnLeftPaneUpdatedListener paramOnLeftPaneUpdatedListener)
  {
    this.mUpdateListeners.add(paramOnLeftPaneUpdatedListener);
  }
  
  public int getLeftCandidates()
  {
    return this.mLeftCandidates;
  }
  
  public int getRightCandidates()
  {
    return this.mRightCandidates;
  }
  
  public void updateLeft(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.mMaxCandidates)) {}
    int i;
    int j;
    do
    {
      return;
      i = this.mLeftCandidates;
      j = this.mRightCandidates;
      this.mLeftCandidates = paramInt;
      this.mRightCandidates = (this.mMaxCandidates - this.mLeftCandidates);
    } while ((this.mLeftCandidates == i) && (this.mRightCandidates == j));
    notifyListeners();
  }
  
  public static abstract interface OnLeftPaneUpdatedListener
  {
    public abstract void onPaneUpdated();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.AsianCandidatePaneTracker
 * JD-Core Version:    0.7.0.1
 */