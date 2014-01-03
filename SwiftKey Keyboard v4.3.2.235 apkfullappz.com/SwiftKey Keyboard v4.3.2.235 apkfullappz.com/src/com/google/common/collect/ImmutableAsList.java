package com.google.common.collect;

abstract class ImmutableAsList<E>
  extends ImmutableList<E>
{
  public boolean contains(Object paramObject)
  {
    return delegateCollection().contains(paramObject);
  }
  
  abstract ImmutableCollection<E> delegateCollection();
  
  public boolean isEmpty()
  {
    return delegateCollection().isEmpty();
  }
  
  boolean isPartialView()
  {
    return delegateCollection().isPartialView();
  }
  
  public int size()
  {
    return delegateCollection().size();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ImmutableAsList
 * JD-Core Version:    0.7.0.1
 */