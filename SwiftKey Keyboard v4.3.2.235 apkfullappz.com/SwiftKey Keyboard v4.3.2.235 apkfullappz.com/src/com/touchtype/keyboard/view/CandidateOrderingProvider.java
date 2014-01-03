package com.touchtype.keyboard.view;

import java.util.Iterator;

public abstract interface CandidateOrderingProvider
{
  public abstract Iterator<Integer> getIterator(int paramInt1, int paramInt2);
  
  public abstract int getTopCandidateKeyIndex(int paramInt);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.CandidateOrderingProvider
 * JD-Core Version:    0.7.0.1
 */