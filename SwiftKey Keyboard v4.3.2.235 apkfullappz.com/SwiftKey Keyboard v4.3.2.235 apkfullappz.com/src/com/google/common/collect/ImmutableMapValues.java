package com.google.common.collect;

import java.util.Map.Entry;

abstract class ImmutableMapValues<K, V>
  extends ImmutableCollection<V>
{
  public boolean contains(Object paramObject)
  {
    return map().containsValue(paramObject);
  }
  
  ImmutableList<V> createAsList()
  {
    new ImmutableAsList()
    {
      ImmutableCollection<V> delegateCollection()
      {
        return ImmutableMapValues.this;
      }
      
      public V get(int paramAnonymousInt)
      {
        return ((Map.Entry)this.val$entryList.get(paramAnonymousInt)).getValue();
      }
    };
  }
  
  boolean isPartialView()
  {
    return true;
  }
  
  public UnmodifiableIterator<V> iterator()
  {
    return Maps.valueIterator(map().entrySet().iterator());
  }
  
  abstract ImmutableMap<K, V> map();
  
  public int size()
  {
    return map().size();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ImmutableMapValues
 * JD-Core Version:    0.7.0.1
 */