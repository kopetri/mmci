package com.google.common.collect;

public final class Ranges
{
  static <C extends Comparable<?>> Range<C> create(Cut<C> paramCut1, Cut<C> paramCut2)
  {
    return new Range(paramCut1, paramCut2);
  }
  
  public static <C extends Comparable<?>> Range<C> openClosed(C paramC1, C paramC2)
  {
    return create(Cut.aboveValue(paramC1), Cut.aboveValue(paramC2));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.Ranges
 * JD-Core Version:    0.7.0.1
 */