package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Iterator;

abstract class TransformedIterator<F, T>
  implements Iterator<T>
{
  final Iterator<? extends F> backingIterator;
  
  TransformedIterator(Iterator<? extends F> paramIterator)
  {
    this.backingIterator = ((Iterator)Preconditions.checkNotNull(paramIterator));
  }
  
  public final boolean hasNext()
  {
    return this.backingIterator.hasNext();
  }
  
  public final T next()
  {
    return transform(this.backingIterator.next());
  }
  
  public final void remove()
  {
    this.backingIterator.remove();
  }
  
  abstract T transform(F paramF);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.TransformedIterator
 * JD-Core Version:    0.7.0.1
 */