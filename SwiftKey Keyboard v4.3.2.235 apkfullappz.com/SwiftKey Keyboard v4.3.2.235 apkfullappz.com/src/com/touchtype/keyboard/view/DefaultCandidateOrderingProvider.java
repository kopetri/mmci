package com.touchtype.keyboard.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class DefaultCandidateOrderingProvider
  implements CandidateOrderingProvider
{
  public Iterator<Integer> getIterator(final int paramInt1, final int paramInt2)
  {
    new Iterator()
    {
      private int mCallIndex = 0;
      private final List<Integer> mCandidatePlacement = new ArrayList(paramInt1);
      private final int mCandsToShow = Math.min(paramInt2, paramInt1);
      private final int mFrontOffset = (paramInt1 - this.mCandsToShow) / 2;
      
      private void generateCandidatePlacement()
      {
        for (int i = 0; i < paramInt1; i++) {
          this.mCandidatePlacement.add(null);
        }
        for (int j = 0; j < this.mCandsToShow; j++) {
          this.mCandidatePlacement.set(keyForCandidate(j).intValue(), Integer.valueOf(j));
        }
      }
      
      private Integer keyForCandidate(int paramAnonymousInt)
      {
        int i = -1;
        if (paramAnonymousInt == 0) {
          i = paramInt1 / 2;
        }
        while (i == -1)
        {
          return null;
          if (paramAnonymousInt <= this.mCandsToShow / 2) {
            i = paramAnonymousInt - 1 + this.mFrontOffset;
          } else if (paramAnonymousInt < this.mCandsToShow) {
            i = paramAnonymousInt + this.mFrontOffset;
          }
        }
        return Integer.valueOf(i);
      }
      
      public boolean hasNext()
      {
        return this.mCallIndex < paramInt1;
      }
      
      public Integer next()
      {
        if (this.mCallIndex == 0) {
          generateCandidatePlacement();
        }
        List localList = this.mCandidatePlacement;
        int i = this.mCallIndex;
        this.mCallIndex = (i + 1);
        return (Integer)localList.get(i);
      }
      
      public void remove() {}
    };
  }
  
  public int getTopCandidateKeyIndex(int paramInt)
  {
    return paramInt / 2;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.DefaultCandidateOrderingProvider
 * JD-Core Version:    0.7.0.1
 */