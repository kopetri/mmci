package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;

final class ReverseOrdering<T>
  extends Ordering<T>
  implements Serializable
{
  final Ordering<? super T> forwardOrder;
  
  ReverseOrdering(Ordering<? super T> paramOrdering)
  {
    this.forwardOrder = ((Ordering)Preconditions.checkNotNull(paramOrdering));
  }
  
  public int compare(T paramT1, T paramT2)
  {
    return this.forwardOrder.compare(paramT2, paramT1);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof ReverseOrdering))
    {
      ReverseOrdering localReverseOrdering = (ReverseOrdering)paramObject;
      return this.forwardOrder.equals(localReverseOrdering.forwardOrder);
    }
    return false;
  }
  
  public int hashCode()
  {
    return -this.forwardOrder.hashCode();
  }
  
  public <S extends T> Ordering<S> reverse()
  {
    return this.forwardOrder;
  }
  
  public String toString()
  {
    return this.forwardOrder + ".reverse()";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ReverseOrdering
 * JD-Core Version:    0.7.0.1
 */