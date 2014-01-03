package com.touchtype.keyboard.view;

import java.util.Iterator;

public final class RightPaneCandidateOrderingProvider
  implements CandidateOrderingProvider
{
  public Iterator<Integer> getIterator(final int paramInt1, final int paramInt2)
  {
    new Iterator()
    {
      private int mCallIndex;
      private final int mCandsToShow = paramInt2 - Math.min(paramInt2, paramInt1) + Math.min(paramInt2, 1);
      
      public boolean hasNext()
      {
        return this.mCallIndex < this.mCandsToShow;
      }
      
      public Integer next()
      {
        if (this.mCallIndex == 0)
        {
          this.mCallIndex = (1 + this.mCallIndex);
          return Integer.valueOf(0);
        }
        int i = this.mCallIndex;
        this.mCallIndex = (i + 1);
        return Integer.valueOf(-1 + (i + this.mCandsToShow));
      }
      
      public void remove() {}
    };
  }
  
  public int getTopCandidateKeyIndex(int paramInt)
  {
    return 0;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.RightPaneCandidateOrderingProvider
 * JD-Core Version:    0.7.0.1
 */