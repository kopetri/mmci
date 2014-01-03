package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.Iterator;

public abstract class FluentIterable<E>
  implements Iterable<E>
{
  private final Iterable<E> iterable;
  
  protected FluentIterable()
  {
    this.iterable = this;
  }
  
  FluentIterable(Iterable<E> paramIterable)
  {
    this.iterable = ((Iterable)Preconditions.checkNotNull(paramIterable));
  }
  
  public static <E> FluentIterable<E> from(final Iterable<E> paramIterable)
  {
    if ((paramIterable instanceof FluentIterable)) {
      return (FluentIterable)paramIterable;
    }
    new FluentIterable(paramIterable)
    {
      public Iterator<E> iterator()
      {
        return paramIterable.iterator();
      }
    };
  }
  
  public final FluentIterable<E> filter(Predicate<? super E> paramPredicate)
  {
    return from(Iterables.filter(this.iterable, paramPredicate));
  }
  
  public final ImmutableSet<E> toImmutableSet()
  {
    return ImmutableSet.copyOf(this.iterable);
  }
  
  public String toString()
  {
    return Iterables.toString(this.iterable);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.FluentIterable
 * JD-Core Version:    0.7.0.1
 */