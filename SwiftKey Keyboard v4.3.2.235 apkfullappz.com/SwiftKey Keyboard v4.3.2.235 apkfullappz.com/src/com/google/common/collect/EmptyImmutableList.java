package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.List;

final class EmptyImmutableList
  extends ImmutableList<Object>
{
  static final EmptyImmutableList INSTANCE = new EmptyImmutableList();
  
  public boolean contains(Object paramObject)
  {
    return false;
  }
  
  public boolean containsAll(Collection<?> paramCollection)
  {
    return paramCollection.isEmpty();
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof List)) {
      return ((List)paramObject).isEmpty();
    }
    return false;
  }
  
  public Object get(int paramInt)
  {
    Preconditions.checkElementIndex(paramInt, 0);
    throw new AssertionError("unreachable");
  }
  
  public int hashCode()
  {
    return 1;
  }
  
  public int indexOf(Object paramObject)
  {
    return -1;
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
    return listIterator();
  }
  
  public int lastIndexOf(Object paramObject)
  {
    return -1;
  }
  
  public UnmodifiableListIterator<Object> listIterator()
  {
    return Iterators.EMPTY_LIST_ITERATOR;
  }
  
  public UnmodifiableListIterator<Object> listIterator(int paramInt)
  {
    Preconditions.checkPositionIndex(paramInt, 0);
    return Iterators.EMPTY_LIST_ITERATOR;
  }
  
  public int size()
  {
    return 0;
  }
  
  public ImmutableList<Object> subList(int paramInt1, int paramInt2)
  {
    Preconditions.checkPositionIndexes(paramInt1, paramInt2, 0);
    return this;
  }
  
  public Object[] toArray()
  {
    return ObjectArrays.EMPTY_ARRAY;
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    if (paramArrayOfT.length > 0) {
      paramArrayOfT[0] = null;
    }
    return paramArrayOfT;
  }
  
  public String toString()
  {
    return "[]";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.EmptyImmutableList
 * JD-Core Version:    0.7.0.1
 */