package com.google.common.collect;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public abstract class ImmutableCollection<E>
  implements Serializable, Collection<E>
{
  static final ImmutableCollection<Object> EMPTY_IMMUTABLE_COLLECTION = new EmptyImmutableCollection(null);
  private transient ImmutableList<E> asList;
  
  public final boolean add(E paramE)
  {
    throw new UnsupportedOperationException();
  }
  
  public final boolean addAll(Collection<? extends E> paramCollection)
  {
    throw new UnsupportedOperationException();
  }
  
  public ImmutableList<E> asList()
  {
    ImmutableList localImmutableList = this.asList;
    if (localImmutableList == null)
    {
      localImmutableList = createAsList();
      this.asList = localImmutableList;
    }
    return localImmutableList;
  }
  
  public final void clear()
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean contains(Object paramObject)
  {
    return (paramObject != null) && (Iterators.contains(iterator(), paramObject));
  }
  
  public boolean containsAll(Collection<?> paramCollection)
  {
    return Collections2.containsAllImpl(this, paramCollection);
  }
  
  ImmutableList<E> createAsList()
  {
    switch (size())
    {
    default: 
      return new RegularImmutableAsList(this, toArray());
    case 0: 
      return ImmutableList.of();
    }
    return ImmutableList.of(iterator().next());
  }
  
  public boolean isEmpty()
  {
    return size() == 0;
  }
  
  abstract boolean isPartialView();
  
  public abstract UnmodifiableIterator<E> iterator();
  
  public final boolean remove(Object paramObject)
  {
    throw new UnsupportedOperationException();
  }
  
  public final boolean removeAll(Collection<?> paramCollection)
  {
    throw new UnsupportedOperationException();
  }
  
  public final boolean retainAll(Collection<?> paramCollection)
  {
    throw new UnsupportedOperationException();
  }
  
  public Object[] toArray()
  {
    return ObjectArrays.toArrayImpl(this);
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    return ObjectArrays.toArrayImpl(this, paramArrayOfT);
  }
  
  public String toString()
  {
    return Collections2.toStringImpl(this);
  }
  
  public static abstract class Builder<E>
  {
    static int expandedCapacity(int paramInt1, int paramInt2)
    {
      if (paramInt2 < 0) {
        throw new AssertionError("cannot store more than MAX_VALUE elements");
      }
      int i = 1 + (paramInt1 + (paramInt1 >> 1));
      if (i < paramInt2) {
        i = Integer.highestOneBit(paramInt2 - 1) << 1;
      }
      if (i < 0) {
        i = 2147483647;
      }
      return i;
    }
    
    public abstract Builder<E> add(E paramE);
    
    public Builder<E> addAll(Iterable<? extends E> paramIterable)
    {
      Iterator localIterator = paramIterable.iterator();
      while (localIterator.hasNext()) {
        add(localIterator.next());
      }
      return this;
    }
    
    public Builder<E> addAll(Iterator<? extends E> paramIterator)
    {
      while (paramIterator.hasNext()) {
        add(paramIterator.next());
      }
      return this;
    }
  }
  
  private static final class EmptyImmutableCollection
    extends ImmutableCollection<Object>
  {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    
    public boolean contains(Object paramObject)
    {
      return false;
    }
    
    ImmutableList<Object> createAsList()
    {
      return ImmutableList.of();
    }
    
    public boolean isEmpty()
    {
      return true;
    }
    
    boolean isPartialView()
    {
      return false;
    }
    
    public UnmodifiableIterator<Object> iterator()
    {
      return Iterators.EMPTY_LIST_ITERATOR;
    }
    
    public int size()
    {
      return 0;
    }
    
    public Object[] toArray()
    {
      return EMPTY_ARRAY;
    }
    
    public <T> T[] toArray(T[] paramArrayOfT)
    {
      if (paramArrayOfT.length > 0) {
        paramArrayOfT[0] = null;
      }
      return paramArrayOfT;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ImmutableCollection
 * JD-Core Version:    0.7.0.1
 */