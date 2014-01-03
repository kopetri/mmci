package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.NavigableSet;

public abstract class ImmutableSortedSet<E>
  extends ImmutableSortedSetFauxverideShim<E>
  implements SortedIterable<E>, NavigableSet<E>
{
  private static final ImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new EmptyImmutableSortedSet(NATURAL_ORDER);
  private static final Comparator<Comparable> NATURAL_ORDER = ;
  final transient Comparator<? super E> comparator;
  transient ImmutableSortedSet<E> descendingSet;
  
  ImmutableSortedSet(Comparator<? super E> paramComparator)
  {
    this.comparator = paramComparator;
  }
  
  private static <E> ImmutableSortedSet<E> emptySet()
  {
    return NATURAL_EMPTY_SET;
  }
  
  static <E> ImmutableSortedSet<E> emptySet(Comparator<? super E> paramComparator)
  {
    if (NATURAL_ORDER.equals(paramComparator)) {
      return emptySet();
    }
    return new EmptyImmutableSortedSet(paramComparator);
  }
  
  public E ceiling(E paramE)
  {
    return Iterables.getFirst(tailSet(paramE, true), null);
  }
  
  public Comparator<? super E> comparator()
  {
    return this.comparator;
  }
  
  abstract ImmutableSortedSet<E> createDescendingSet();
  
  public UnmodifiableIterator<E> descendingIterator()
  {
    return descendingSet().iterator();
  }
  
  public ImmutableSortedSet<E> descendingSet()
  {
    ImmutableSortedSet localImmutableSortedSet = this.descendingSet;
    if (localImmutableSortedSet == null)
    {
      localImmutableSortedSet = createDescendingSet();
      this.descendingSet = localImmutableSortedSet;
      localImmutableSortedSet.descendingSet = this;
    }
    return localImmutableSortedSet;
  }
  
  public E floor(E paramE)
  {
    return Iterables.getFirst(headSet(paramE, true).descendingSet(), null);
  }
  
  public ImmutableSortedSet<E> headSet(E paramE)
  {
    return headSet(paramE, false);
  }
  
  public ImmutableSortedSet<E> headSet(E paramE, boolean paramBoolean)
  {
    return headSetImpl(Preconditions.checkNotNull(paramE), paramBoolean);
  }
  
  abstract ImmutableSortedSet<E> headSetImpl(E paramE, boolean paramBoolean);
  
  public E higher(E paramE)
  {
    return Iterables.getFirst(tailSet(paramE, false), null);
  }
  
  public abstract UnmodifiableIterator<E> iterator();
  
  public E lower(E paramE)
  {
    return Iterables.getFirst(headSet(paramE, false).descendingSet(), null);
  }
  
  public final E pollFirst()
  {
    throw new UnsupportedOperationException();
  }
  
  public final E pollLast()
  {
    throw new UnsupportedOperationException();
  }
  
  public ImmutableSortedSet<E> subSet(E paramE1, E paramE2)
  {
    return subSet(paramE1, true, paramE2, false);
  }
  
  public ImmutableSortedSet<E> subSet(E paramE1, boolean paramBoolean1, E paramE2, boolean paramBoolean2)
  {
    Preconditions.checkNotNull(paramE1);
    Preconditions.checkNotNull(paramE2);
    if (this.comparator.compare(paramE1, paramE2) <= 0) {}
    for (boolean bool = true;; bool = false)
    {
      Preconditions.checkArgument(bool);
      return subSetImpl(paramE1, paramBoolean1, paramE2, paramBoolean2);
    }
  }
  
  abstract ImmutableSortedSet<E> subSetImpl(E paramE1, boolean paramBoolean1, E paramE2, boolean paramBoolean2);
  
  public ImmutableSortedSet<E> tailSet(E paramE)
  {
    return tailSet(paramE, true);
  }
  
  public ImmutableSortedSet<E> tailSet(E paramE, boolean paramBoolean)
  {
    return tailSetImpl(Preconditions.checkNotNull(paramE), paramBoolean);
  }
  
  abstract ImmutableSortedSet<E> tailSetImpl(E paramE, boolean paramBoolean);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ImmutableSortedSet
 * JD-Core Version:    0.7.0.1
 */