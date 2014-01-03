package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

public abstract class ImmutableList<E>
  extends ImmutableCollection<E>
  implements List<E>, RandomAccess
{
  static <E> ImmutableList<E> asImmutableList(Object[] paramArrayOfObject)
  {
    switch (paramArrayOfObject.length)
    {
    default: 
      return construct(paramArrayOfObject);
    case 0: 
      return of();
    }
    return new SingletonImmutableList(paramArrayOfObject[0]);
  }
  
  public static <E> Builder<E> builder()
  {
    return new Builder();
  }
  
  private static <E> ImmutableList<E> construct(Object... paramVarArgs)
  {
    for (int i = 0; i < paramVarArgs.length; i++) {
      ObjectArrays.checkElementNotNull(paramVarArgs[i], i);
    }
    return new RegularImmutableList(paramVarArgs);
  }
  
  public static <E> ImmutableList<E> copyOf(E[] paramArrayOfE)
  {
    switch (paramArrayOfE.length)
    {
    default: 
      return construct((Object[])paramArrayOfE.clone());
    case 0: 
      return of();
    }
    return new SingletonImmutableList(paramArrayOfE[0]);
  }
  
  public static <E> ImmutableList<E> of()
  {
    return EmptyImmutableList.INSTANCE;
  }
  
  public static <E> ImmutableList<E> of(E paramE)
  {
    return new SingletonImmutableList(paramE);
  }
  
  public final void add(int paramInt, E paramE)
  {
    throw new UnsupportedOperationException();
  }
  
  public final boolean addAll(int paramInt, Collection<? extends E> paramCollection)
  {
    throw new UnsupportedOperationException();
  }
  
  public ImmutableList<E> asList()
  {
    return this;
  }
  
  public boolean contains(Object paramObject)
  {
    return indexOf(paramObject) >= 0;
  }
  
  public boolean equals(Object paramObject)
  {
    return Lists.equalsImpl(this, paramObject);
  }
  
  public int hashCode()
  {
    return Lists.hashCodeImpl(this);
  }
  
  public int indexOf(Object paramObject)
  {
    if (paramObject == null) {
      return -1;
    }
    return Lists.indexOfImpl(this, paramObject);
  }
  
  public UnmodifiableIterator<E> iterator()
  {
    return listIterator();
  }
  
  public int lastIndexOf(Object paramObject)
  {
    if (paramObject == null) {
      return -1;
    }
    return Lists.lastIndexOfImpl(this, paramObject);
  }
  
  public UnmodifiableListIterator<E> listIterator()
  {
    return listIterator(0);
  }
  
  public UnmodifiableListIterator<E> listIterator(int paramInt)
  {
    new AbstractIndexedListIterator(size(), paramInt)
    {
      protected E get(int paramAnonymousInt)
      {
        return ImmutableList.this.get(paramAnonymousInt);
      }
    };
  }
  
  public final E remove(int paramInt)
  {
    throw new UnsupportedOperationException();
  }
  
  public final E set(int paramInt, E paramE)
  {
    throw new UnsupportedOperationException();
  }
  
  public ImmutableList<E> subList(int paramInt1, int paramInt2)
  {
    Preconditions.checkPositionIndexes(paramInt1, paramInt2, size());
    switch (paramInt2 - paramInt1)
    {
    default: 
      return subListUnchecked(paramInt1, paramInt2);
    case 0: 
      return of();
    }
    return of(get(paramInt1));
  }
  
  ImmutableList<E> subListUnchecked(int paramInt1, int paramInt2)
  {
    return new SubList(paramInt1, paramInt2 - paramInt1);
  }
  
  public static final class Builder<E>
    extends ImmutableCollection.Builder<E>
  {
    private Object[] contents;
    private int size;
    
    public Builder()
    {
      this(4);
    }
    
    Builder(int paramInt)
    {
      this.contents = new Object[paramInt];
      this.size = 0;
    }
    
    public Builder<E> add(E paramE)
    {
      Preconditions.checkNotNull(paramE);
      expandFor(1);
      Object[] arrayOfObject = this.contents;
      int i = this.size;
      this.size = (i + 1);
      arrayOfObject[i] = paramE;
      return this;
    }
    
    public Builder<E> addAll(Iterable<? extends E> paramIterable)
    {
      if ((paramIterable instanceof Collection)) {
        expandFor(((Collection)paramIterable).size());
      }
      super.addAll(paramIterable);
      return this;
    }
    
    public Builder<E> addAll(Iterator<? extends E> paramIterator)
    {
      super.addAll(paramIterator);
      return this;
    }
    
    public ImmutableList<E> build()
    {
      switch (this.size)
      {
      default: 
        if (this.size == this.contents.length) {
          return new RegularImmutableList(this.contents);
        }
        break;
      case 0: 
        return ImmutableList.of();
      case 1: 
        return ImmutableList.of(this.contents[0]);
      }
      return new RegularImmutableList(ObjectArrays.arraysCopyOf(this.contents, this.size));
    }
    
    Builder<E> expandFor(int paramInt)
    {
      int i = paramInt + this.size;
      if (this.contents.length < i) {
        this.contents = ObjectArrays.arraysCopyOf(this.contents, expandedCapacity(this.contents.length, i));
      }
      return this;
    }
  }
  
  final class SubList
    extends ImmutableList<E>
  {
    final transient int length;
    final transient int offset;
    
    SubList(int paramInt1, int paramInt2)
    {
      this.offset = paramInt1;
      this.length = paramInt2;
    }
    
    public E get(int paramInt)
    {
      Preconditions.checkElementIndex(paramInt, this.length);
      return ImmutableList.this.get(paramInt + this.offset);
    }
    
    boolean isPartialView()
    {
      return true;
    }
    
    public int size()
    {
      return this.length;
    }
    
    public ImmutableList<E> subList(int paramInt1, int paramInt2)
    {
      Preconditions.checkPositionIndexes(paramInt1, paramInt2, this.length);
      return ImmutableList.this.subList(paramInt1 + this.offset, paramInt2 + this.offset);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ImmutableList
 * JD-Core Version:    0.7.0.1
 */