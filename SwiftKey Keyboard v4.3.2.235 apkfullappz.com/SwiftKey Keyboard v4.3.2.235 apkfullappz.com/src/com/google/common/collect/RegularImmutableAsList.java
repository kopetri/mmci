package com.google.common.collect;

final class RegularImmutableAsList<E>
  extends ImmutableAsList<E>
{
  private final ImmutableCollection<E> delegate;
  private final ImmutableList<? extends E> delegateList;
  
  RegularImmutableAsList(ImmutableCollection<E> paramImmutableCollection, ImmutableList<? extends E> paramImmutableList)
  {
    this.delegate = paramImmutableCollection;
    this.delegateList = paramImmutableList;
  }
  
  RegularImmutableAsList(ImmutableCollection<E> paramImmutableCollection, Object[] paramArrayOfObject)
  {
    this(paramImmutableCollection, ImmutableList.asImmutableList(paramArrayOfObject));
  }
  
  ImmutableCollection<E> delegateCollection()
  {
    return this.delegate;
  }
  
  public boolean equals(Object paramObject)
  {
    return this.delegateList.equals(paramObject);
  }
  
  public E get(int paramInt)
  {
    return this.delegateList.get(paramInt);
  }
  
  public int hashCode()
  {
    return this.delegateList.hashCode();
  }
  
  public int indexOf(Object paramObject)
  {
    return this.delegateList.indexOf(paramObject);
  }
  
  public int lastIndexOf(Object paramObject)
  {
    return this.delegateList.lastIndexOf(paramObject);
  }
  
  public UnmodifiableListIterator<E> listIterator(int paramInt)
  {
    return this.delegateList.listIterator(paramInt);
  }
  
  public Object[] toArray()
  {
    return this.delegateList.toArray();
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    return this.delegateList.toArray(paramArrayOfT);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.RegularImmutableAsList
 * JD-Core Version:    0.7.0.1
 */