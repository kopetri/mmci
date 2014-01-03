package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Comparator;

public abstract class Ordering<T>
  implements Comparator<T>
{
  public static <T> Ordering<T> from(Comparator<T> paramComparator)
  {
    if ((paramComparator instanceof Ordering)) {
      return (Ordering)paramComparator;
    }
    return new ComparatorOrdering(paramComparator);
  }
  
  public static <C extends Comparable> Ordering<C> natural()
  {
    return NaturalOrdering.INSTANCE;
  }
  
  public abstract int compare(T paramT1, T paramT2);
  
  public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> paramIterable)
  {
    Object[] arrayOfObject = (Object[])Iterables.toArray(paramIterable);
    int i = arrayOfObject.length;
    for (int j = 0; j < i; j++) {
      Preconditions.checkNotNull(arrayOfObject[j]);
    }
    Arrays.sort(arrayOfObject, this);
    return ImmutableList.asImmutableList(arrayOfObject);
  }
  
  public <S extends T> Ordering<S> reverse()
  {
    return new ReverseOrdering(this);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.Ordering
 * JD-Core Version:    0.7.0.1
 */