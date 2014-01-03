package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

public final class Iterables
{
  public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> paramIterable)
  {
    Preconditions.checkNotNull(paramIterable);
    new FluentIterable()
    {
      public Iterator<T> iterator()
      {
        return Iterators.concat(Iterables.iterators(this.val$inputs));
      }
    };
  }
  
  public static <T> Iterable<T> concat(Iterable<? extends T> paramIterable1, Iterable<? extends T> paramIterable2)
  {
    Preconditions.checkNotNull(paramIterable1);
    Preconditions.checkNotNull(paramIterable2);
    return concat(Arrays.asList(new Iterable[] { paramIterable1, paramIterable2 }));
  }
  
  public static <T> Iterable<T> filter(Iterable<T> paramIterable, final Predicate<? super T> paramPredicate)
  {
    Preconditions.checkNotNull(paramIterable);
    Preconditions.checkNotNull(paramPredicate);
    new FluentIterable()
    {
      public Iterator<T> iterator()
      {
        return Iterators.filter(this.val$unfiltered.iterator(), paramPredicate);
      }
    };
  }
  
  public static <T> T getFirst(Iterable<? extends T> paramIterable, T paramT)
  {
    return Iterators.getNext(paramIterable.iterator(), paramT);
  }
  
  public static <T> T getOnlyElement(Iterable<T> paramIterable)
  {
    return Iterators.getOnlyElement(paramIterable.iterator());
  }
  
  private static <T> UnmodifiableIterator<Iterator<? extends T>> iterators(Iterable<? extends Iterable<? extends T>> paramIterable)
  {
    new UnmodifiableIterator()
    {
      public boolean hasNext()
      {
        return this.val$iterableIterator.hasNext();
      }
      
      public Iterator<? extends T> next()
      {
        return ((Iterable)this.val$iterableIterator.next()).iterator();
      }
    };
  }
  
  public static <T> boolean removeIf(Iterable<T> paramIterable, Predicate<? super T> paramPredicate)
  {
    if (((paramIterable instanceof RandomAccess)) && ((paramIterable instanceof List))) {
      return removeIfFromRandomAccessList((List)paramIterable, (Predicate)Preconditions.checkNotNull(paramPredicate));
    }
    return Iterators.removeIf(paramIterable.iterator(), paramPredicate);
  }
  
  private static <T> boolean removeIfFromRandomAccessList(List<T> paramList, Predicate<? super T> paramPredicate)
  {
    int i = 0;
    int j = 0;
    for (;;)
    {
      if (i < paramList.size())
      {
        Object localObject = paramList.get(i);
        if ((paramPredicate.apply(localObject)) || (i > j)) {}
        try
        {
          paramList.set(j, localObject);
          j++;
          i++;
        }
        catch (UnsupportedOperationException localUnsupportedOperationException)
        {
          slowRemoveIfForRemainingElements(paramList, paramPredicate, j, i);
        }
      }
    }
    do
    {
      return true;
      paramList.subList(j, paramList.size()).clear();
    } while (i != j);
    return false;
  }
  
  private static <T> void slowRemoveIfForRemainingElements(List<T> paramList, Predicate<? super T> paramPredicate, int paramInt1, int paramInt2)
  {
    for (int i = -1 + paramList.size(); i > paramInt2; i--) {
      if (paramPredicate.apply(paramList.get(i))) {
        paramList.remove(i);
      }
    }
    for (int j = paramInt2 - 1; j >= paramInt1; j--) {
      paramList.remove(j);
    }
  }
  
  static Object[] toArray(Iterable<?> paramIterable)
  {
    return toCollection(paramIterable).toArray();
  }
  
  private static <E> Collection<E> toCollection(Iterable<E> paramIterable)
  {
    if ((paramIterable instanceof Collection)) {
      return (Collection)paramIterable;
    }
    return Lists.newArrayList(paramIterable.iterator());
  }
  
  public static String toString(Iterable<?> paramIterable)
  {
    return Iterators.toString(paramIterable.iterator());
  }
  
  public static <F, T> Iterable<T> transform(Iterable<F> paramIterable, final Function<? super F, ? extends T> paramFunction)
  {
    Preconditions.checkNotNull(paramIterable);
    Preconditions.checkNotNull(paramFunction);
    new FluentIterable()
    {
      public Iterator<T> iterator()
      {
        return Iterators.transform(this.val$fromIterable.iterator(), paramFunction);
      }
    };
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.Iterables
 * JD-Core Version:    0.7.0.1
 */