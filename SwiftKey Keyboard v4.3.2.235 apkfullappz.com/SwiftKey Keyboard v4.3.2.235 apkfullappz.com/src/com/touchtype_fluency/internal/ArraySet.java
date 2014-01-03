package com.touchtype_fluency.internal;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArraySet<E>
  extends AbstractSet<E>
{
  public static final ArraySet<?> EMPTY;
  private final Comparator<? super E> mComparator;
  private final E[] mElements;
  
  static
  {
    if (!ArraySet.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      EMPTY = new ArraySet();
      return;
    }
  }
  
  public ArraySet()
  {
    this.mComparator = null;
    this.mElements = null;
  }
  
  public ArraySet(Collection<? extends E> paramCollection)
  {
    this.mComparator = null;
    if (paramCollection.isEmpty()) {}
    for (Object[] arrayOfObject = null;; arrayOfObject = sort((Object[])paramCollection.toArray(), null))
    {
      this.mElements = arrayOfObject;
      return;
    }
  }
  
  public ArraySet(Collection<? extends E> paramCollection, Comparator<? super E> paramComparator)
  {
    this.mComparator = paramComparator;
    if (paramCollection.isEmpty()) {}
    for (Object[] arrayOfObject = null;; arrayOfObject = sort((Object[])paramCollection.toArray(), paramComparator))
    {
      this.mElements = arrayOfObject;
      return;
    }
  }
  
  public ArraySet(E[] paramArrayOfE)
  {
    this.mComparator = null;
    int i = paramArrayOfE.length;
    Object[] arrayOfObject = null;
    if (i == 0) {}
    for (;;)
    {
      this.mElements = arrayOfObject;
      return;
      arrayOfObject = sort(paramArrayOfE, null);
    }
  }
  
  public ArraySet(E[] paramArrayOfE, Comparator<? super E> paramComparator)
  {
    this.mComparator = paramComparator;
    if (paramArrayOfE.length == 0) {}
    for (Object[] arrayOfObject = null;; arrayOfObject = sort(paramArrayOfE, paramComparator))
    {
      this.mElements = arrayOfObject;
      return;
    }
  }
  
  private ArraySet(E[] paramArrayOfE, Comparator<? super E> paramComparator, boolean paramBoolean)
  {
    this.mComparator = paramComparator;
    this.mElements = paramArrayOfE;
  }
  
  private ArraySet(E[] paramArrayOfE, boolean paramBoolean)
  {
    this.mComparator = null;
    this.mElements = paramArrayOfE;
  }
  
  public static <E extends Comparable<? super E>> ArraySet<E> fromSortedArray(E[] paramArrayOfE)
  {
    for (int i = 1; i < paramArrayOfE.length; i++) {
      if (paramArrayOfE[(i - 1)].compareTo(paramArrayOfE[i]) >= 0) {
        return new ArraySet(paramArrayOfE);
      }
    }
    return new ArraySet(paramArrayOfE, true);
  }
  
  public static <E> ArraySet<E> fromSortedArray(E[] paramArrayOfE, Comparator<? super E> paramComparator)
  {
    for (int i = 1; i < paramArrayOfE.length; i++) {
      if (paramComparator.compare(paramArrayOfE[(i - 1)], paramArrayOfE[i]) >= 0) {
        return new ArraySet(paramArrayOfE, paramComparator);
      }
    }
    return new ArraySet(paramArrayOfE, paramComparator, true);
  }
  
  private static <E> E[] removeDuplicates(E[] paramArrayOfE, Comparator<? super E> paramComparator)
  {
    assert (paramArrayOfE.length != 0);
    E ? = paramArrayOfE[0];
    int i = 1;
    Object localObject = ?;
    int j = 1;
    if (j < paramArrayOfE.length)
    {
      E ? = paramArrayOfE[j];
      boolean bool;
      if (paramComparator != null) {
        if (paramComparator.compare(localObject, ?) == 0) {
          bool = true;
        }
      }
      for (;;)
      {
        if (!bool)
        {
          int k = i + 1;
          paramArrayOfE[i] = ?;
          i = k;
        }
        j++;
        localObject = ?;
        break;
        bool = false;
        continue;
        if (localObject != null) {
          bool = localObject.equals(?);
        } else if (? == null) {
          bool = true;
        } else {
          bool = false;
        }
      }
    }
    if (i == paramArrayOfE.length) {
      return paramArrayOfE;
    }
    return Arrays.copyOf(paramArrayOfE, i);
  }
  
  private static <E> E[] sort(E[] paramArrayOfE, Comparator<? super E> paramComparator)
  {
    if (paramComparator != null) {
      Arrays.sort(paramArrayOfE, paramComparator);
    }
    for (;;)
    {
      return removeDuplicates(paramArrayOfE, paramComparator);
      Arrays.sort(paramArrayOfE);
    }
  }
  
  public boolean contains(Object paramObject)
  {
    try
    {
      if (this.mElements == null) {
        return false;
      }
      if (this.mComparator != null)
      {
        if (Arrays.binarySearch(this.mElements, paramObject, this.mComparator) >= 0) {
          return true;
        }
      }
      else
      {
        int i = Arrays.binarySearch(this.mElements, paramObject);
        if (i >= 0) {
          return true;
        }
      }
    }
    catch (ClassCastException localClassCastException) {}
    return false;
  }
  
  public boolean containsAll(Collection<?> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext()) {
      if (!contains(localIterator.next())) {
        return false;
      }
    }
    return true;
  }
  
  public boolean isEmpty()
  {
    return (this.mElements == null) || (this.mElements.length == 0);
  }
  
  public Iterator<E> iterator()
  {
    if (this.mElements == null) {
      return new EmptySetIterator();
    }
    return new NonEmptySetIterator();
  }
  
  public int size()
  {
    if (this.mElements == null) {
      return 0;
    }
    return this.mElements.length;
  }
  
  public Object[] toArray()
  {
    if (this.mElements == null) {
      return new Object[0];
    }
    return Arrays.copyOf(this.mElements, this.mElements.length);
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    if (this.mElements == null) {
      Arrays.fill(paramArrayOfT, null);
    }
    for (;;)
    {
      return paramArrayOfT;
      if (this.mElements.length > paramArrayOfT.length) {
        break;
      }
      for (int i = 0; i < this.mElements.length; i++) {
        paramArrayOfT[i] = this.mElements[i];
      }
      for (int j = this.mElements.length; j < paramArrayOfT.length; j++) {
        paramArrayOfT[j] = null;
      }
    }
    return (Object[])Arrays.copyOf(this.mElements, this.mElements.length, paramArrayOfT.getClass());
  }
  
  private static class EmptySetIterator<T>
    implements Iterator<T>
  {
    public boolean hasNext()
    {
      return false;
    }
    
    public T next()
    {
      throw new NoSuchElementException("next() from an empty set");
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException("Cannot remove() from an ArraySet");
    }
  }
  
  private class NonEmptySetIterator
    implements Iterator<E>
  {
    private int mIndex = 0;
    
    public NonEmptySetIterator() {}
    
    public boolean hasNext()
    {
      return this.mIndex < ArraySet.this.mElements.length;
    }
    
    public E next()
    {
      if (this.mIndex < ArraySet.this.mElements.length)
      {
        Object[] arrayOfObject = ArraySet.this.mElements;
        int i = this.mIndex;
        this.mIndex = (i + 1);
        return arrayOfObject[i];
      }
      throw new NoSuchElementException("No more elements to return from next()");
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException("Cannot remove() from an ArraySet");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.ArraySet
 * JD-Core Version:    0.7.0.1
 */