package com.google.common.collect;

import java.util.Iterator;

abstract class TransformedImmutableSet<D, E>
  extends ImmutableSet<E>
{
  final int hashCode;
  final ImmutableCollection<D> source;
  
  TransformedImmutableSet(ImmutableCollection<D> paramImmutableCollection)
  {
    this.source = paramImmutableCollection;
    this.hashCode = Sets.hashCodeImpl(this);
  }
  
  TransformedImmutableSet(ImmutableCollection<D> paramImmutableCollection, int paramInt)
  {
    this.source = paramImmutableCollection;
    this.hashCode = paramInt;
  }
  
  public final int hashCode()
  {
    return this.hashCode;
  }
  
  public boolean isEmpty()
  {
    return false;
  }
  
  boolean isHashCodeFast()
  {
    return true;
  }
  
  public UnmodifiableIterator<E> iterator()
  {
    new UnmodifiableIterator()
    {
      public boolean hasNext()
      {
        return this.val$backingIterator.hasNext();
      }
      
      public E next()
      {
        return TransformedImmutableSet.this.transform(this.val$backingIterator.next());
      }
    };
  }
  
  public int size()
  {
    return this.source.size();
  }
  
  public Object[] toArray()
  {
    return toArray(new Object[size()]);
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    return ObjectArrays.toArrayImpl(this, paramArrayOfT);
  }
  
  abstract E transform(D paramD);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.TransformedImmutableSet
 * JD-Core Version:    0.7.0.1
 */