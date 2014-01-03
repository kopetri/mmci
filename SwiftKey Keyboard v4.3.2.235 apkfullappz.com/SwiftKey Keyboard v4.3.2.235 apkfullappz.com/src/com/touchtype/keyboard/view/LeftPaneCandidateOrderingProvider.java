package com.touchtype.keyboard.view;

import java.util.Iterator;

public final class LeftPaneCandidateOrderingProvider
  implements CandidateOrderingProvider
{
  public Iterator<Integer> getIterator(final int paramInt1, final int paramInt2)
  {
    new Iterator()
    {
      private int mCallIndex;
      private int mCandsShown = 0;
      private final int mCandsToShow = Math.min(paramInt2, paramInt1);
      
      public boolean hasNext()
      {
        return this.mCallIndex < paramInt1;
      }
      
      public Integer next()
      {
        Integer localInteger;
        if (paramInt1 == 1) {
          if (paramInt2 > 1) {
            localInteger = Integer.valueOf(1);
          }
        }
        for (;;)
        {
          this.mCallIndex = (1 + this.mCallIndex);
          return localInteger;
          localInteger = null;
          continue;
          if ((this.mCallIndex < paramInt1 - this.mCandsToShow) || (this.mCandsShown == this.mCandsToShow))
          {
            localInteger = null;
          }
          else if (this.mCallIndex == -1 + paramInt1)
          {
            localInteger = Integer.valueOf(0);
            this.mCandsShown = (1 + this.mCandsShown);
          }
          else
          {
            localInteger = Integer.valueOf(1 + this.mCandsShown);
            this.mCandsShown = (1 + this.mCandsShown);
          }
        }
      }
      
      public void remove() {}
    };
  }
  
  public int getTopCandidateKeyIndex(int paramInt)
  {
    if (paramInt == 1) {
      return -1;
    }
    return paramInt - 1;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.LeftPaneCandidateOrderingProvider
 * JD-Core Version:    0.7.0.1
 */