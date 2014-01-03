package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public abstract class ImmutableSet<E>
  extends ImmutableCollection<E>
  implements Set<E>
{
  private static final int CUTOFF = (int)Math.floor(751619276.79999995D);
  
  public static <E> Builder<E> builder()
  {
    return new Builder();
  }
  
  static int chooseTableSize(int paramInt)
  {
    int i = 1073741824;
    if (paramInt < CUTOFF)
    {
      i = Integer.highestOneBit(paramInt - 1) << 1;
      while (0.7D * i < paramInt) {
        i <<= 1;
      }
    }
    if (paramInt < i) {}
    for (boolean bool = true;; bool = false)
    {
      Preconditions.checkArgument(bool, "collection too large");
      return i;
    }
  }
  
  private static <E> ImmutableSet<E> construct(int paramInt, Object... paramVarArgs)
  {
    int i;
    Object[] arrayOfObject1;
    int j;
    int k;
    int m;
    int n;
    Object localObject2;
    int i2;
    label79:
    Object localObject3;
    int i4;
    switch (paramInt)
    {
    default: 
      i = chooseTableSize(paramInt);
      arrayOfObject1 = new Object[i];
      j = i - 1;
      k = 0;
      m = 0;
      n = 0;
      if (m < paramInt)
      {
        localObject2 = ObjectArrays.checkElementNotNull(paramVarArgs[m], m);
        int i1 = localObject2.hashCode();
        i2 = Hashing.smear(i1);
        int i3 = i2 & j;
        localObject3 = arrayOfObject1[i3];
        if (localObject3 == null)
        {
          i4 = n + 1;
          paramVarArgs[n] = localObject2;
          arrayOfObject1[i3] = localObject2;
          k += i1;
        }
      }
      break;
    }
    for (;;)
    {
      m++;
      n = i4;
      break;
      return of();
      return of(paramVarArgs[0]);
      if (!localObject3.equals(localObject2))
      {
        i2++;
        break label79;
        Arrays.fill(paramVarArgs, n, paramInt, null);
        if (n == 1)
        {
          Object localObject1 = paramVarArgs[0];
          SingletonImmutableSet localSingletonImmutableSet = new SingletonImmutableSet(localObject1, k);
          return localSingletonImmutableSet;
        }
        if (i != chooseTableSize(n)) {
          return construct(n, paramVarArgs);
        }
        if (n < paramVarArgs.length) {}
        for (Object[] arrayOfObject2 = ObjectArrays.arraysCopyOf(paramVarArgs, n);; arrayOfObject2 = paramVarArgs)
        {
          RegularImmutableSet localRegularImmutableSet = new RegularImmutableSet(arrayOfObject2, k, arrayOfObject1, j);
          return localRegularImmutableSet;
        }
      }
      i4 = n;
    }
  }
  
  private static <E> ImmutableSet<E> copyFromCollection(Collection<? extends E> paramCollection)
  {
    Object[] arrayOfObject = paramCollection.toArray();
    switch (arrayOfObject.length)
    {
    default: 
      return construct(arrayOfObject.length, arrayOfObject);
    case 0: 
      return of();
    }
    return of(arrayOfObject[0]);
  }
  
  public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> paramIterable)
  {
    if ((paramIterable instanceof Collection)) {
      return copyOf(Collections2.cast(paramIterable));
    }
    return copyOf(paramIterable.iterator());
  }
  
  public static <E> ImmutableSet<E> copyOf(Collection<? extends E> paramCollection)
  {
    if (((paramCollection instanceof ImmutableSet)) && (!(paramCollection instanceof ImmutableSortedSet)))
    {
      ImmutableSet localImmutableSet = (ImmutableSet)paramCollection;
      if (!localImmutableSet.isPartialView()) {
        return localImmutableSet;
      }
    }
    return copyFromCollection(paramCollection);
  }
  
  public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> paramIterator)
  {
    if (!paramIterator.hasNext()) {
      return of();
    }
    Object localObject = paramIterator.next();
    if (!paramIterator.hasNext()) {
      return of(localObject);
    }
    return new Builder().add(localObject).addAll(paramIterator).build();
  }
  
  public static <E> ImmutableSet<E> of()
  {
    return EmptyImmutableSet.INSTANCE;
  }
  
  public static <E> ImmutableSet<E> of(E paramE)
  {
    return new SingletonImmutableSet(paramE);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (((paramObject instanceof ImmutableSet)) && (isHashCodeFast()) && (((ImmutableSet)paramObject).isHashCodeFast()) && (hashCode() != paramObject.hashCode())) {
      return false;
    }
    return Sets.equalsImpl(this, paramObject);
  }
  
  public int hashCode()
  {
    return Sets.hashCodeImpl(this);
  }
  
  boolean isHashCodeFast()
  {
    return false;
  }
  
  public abstract UnmodifiableIterator<E> iterator();
  
  static abstract class ArrayImmutableSet<E>
    extends ImmutableSet<E>
  {
    final transient Object[] elements;
    
    ArrayImmutableSet(Object[] paramArrayOfObject)
    {
      this.elements = paramArrayOfObject;
    }
    
    public boolean containsAll(Collection<?> paramCollection)
    {
      if (paramCollection == this) {}
      for (;;)
      {
        return true;
        if (!(paramCollection instanceof ArrayImmutableSet)) {
          return super.containsAll(paramCollection);
        }
        if (paramCollection.size() > size()) {
          return false;
        }
        Object[] arrayOfObject = ((ArrayImmutableSet)paramCollection).elements;
        int i = arrayOfObject.length;
        for (int j = 0; j < i; j++) {
          if (!contains(arrayOfObject[j])) {
            return false;
          }
        }
      }
    }
    
    ImmutableList<E> createAsList()
    {
      return new RegularImmutableAsList(this, this.elements);
    }
    
    public boolean isEmpty()
    {
      return false;
    }
    
    boolean isPartialView()
    {
      return false;
    }
    
    public UnmodifiableIterator<E> iterator()
    {
      return asList().iterator();
    }
    
    public int size()
    {
      return this.elements.length;
    }
    
    public Object[] toArray()
    {
      return asList().toArray();
    }
    
    public <T> T[] toArray(T[] paramArrayOfT)
    {
      return asList().toArray(paramArrayOfT);
    }
  }
  
  public static final class Builder<E>
    extends ImmutableCollection.Builder<E>
  {
    Object[] contents;
    int size;
    
    public Builder()
    {
      this(4);
    }
    
    Builder(int paramInt)
    {
      if (paramInt >= 0) {}
      for (boolean bool = true;; bool = false)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(paramInt);
        Preconditions.checkArgument(bool, "capacity must be >= 0 but was %s", arrayOfObject);
        this.contents = new Object[paramInt];
        this.size = 0;
        return;
      }
    }
    
    public Builder<E> add(E paramE)
    {
      expandFor(1);
      Object[] arrayOfObject = this.contents;
      int i = this.size;
      this.size = (i + 1);
      arrayOfObject[i] = Preconditions.checkNotNull(paramE);
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
    
    public ImmutableSet<E> build()
    {
      ImmutableSet localImmutableSet = ImmutableSet.construct(this.size, this.contents);
      this.size = localImmutableSet.size();
      return localImmutableSet;
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
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ImmutableSet
 * JD-Core Version:    0.7.0.1
 */