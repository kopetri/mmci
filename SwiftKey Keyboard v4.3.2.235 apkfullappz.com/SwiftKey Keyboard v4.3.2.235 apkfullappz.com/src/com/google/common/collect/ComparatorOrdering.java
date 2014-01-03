package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

final class ComparatorOrdering<T>
  extends Ordering<T>
  implements Serializable
{
  final Comparator<T> comparator;
  
  ComparatorOrdering(Comparator<T> paramComparator)
  {
    this.comparator = ((Comparator)Preconditions.checkNotNull(paramComparator));
  }
  
  public int compare(T paramT1, T paramT2)
  {
    return this.comparator.compare(paramT1, paramT2);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof ComparatorOrdering))
    {
      ComparatorOrdering localComparatorOrdering = (ComparatorOrdering)paramObject;
      return this.comparator.equals(localComparatorOrdering.comparator);
    }
    return false;
  }
  
  public int hashCode()
  {
    return this.comparator.hashCode();
  }
  
  public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> paramIterable)
  {
    Object[] arrayOfObject = (Object[])Iterables.toArray(paramIterable);
    int i = arrayOfObject.length;
    for (int j = 0; j < i; j++) {
      Preconditions.checkNotNull(arrayOfObject[j]);
    }
    Arrays.sort(arrayOfObject, this.comparator);
    return ImmutableList.asImmutableList(arrayOfObject);
  }
  
  public String toString()
  {
    return this.comparator.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ComparatorOrdering
 * JD-Core Version:    0.7.0.1
 */