package com.touchtype.keyboard.inputeventmodel.touchhistory;

import com.touchtype_fluency.Sequence;

public final class EmptyLazySequenceForLearning
  implements LazySequence
{
  public Sequence get()
  {
    return new Sequence();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.touchhistory.EmptyLazySequenceForLearning
 * JD-Core Version:    0.7.0.1
 */