package com.google.common.collect;

import java.util.Map.Entry;

abstract class ImmutableMapKeySet<K, V>
  extends TransformedImmutableSet<Map.Entry<K, V>, K>
{
  ImmutableMapKeySet(ImmutableSet<Map.Entry<K, V>> paramImmutableSet)
  {
    super(paramImmutableSet);
  }
  
  ImmutableMapKeySet(ImmutableSet<Map.Entry<K, V>> paramImmutableSet, int paramInt)
  {
    super(paramImmutableSet, paramInt);
  }
  
  public boolean contains(Object paramObject)
  {
    return map().containsKey(paramObject);
  }
  
  ImmutableList<K> createAsList()
  {
    new ImmutableAsList()
    {
      ImmutableCollection<K> delegateCollection()
      {
        return ImmutableMapKeySet.this;
      }
      
      public K get(int paramAnonymousInt)
      {
        return ((Map.Entry)this.val$entryList.get(paramAnonymousInt)).getKey();
      }
    };
  }
  
  boolean isPartialView()
  {
    return true;
  }
  
  abstract ImmutableMap<K, V> map();
  
  K transform(Map.Entry<K, V> paramEntry)
  {
    return paramEntry.getKey();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ImmutableMapKeySet
 * JD-Core Version:    0.7.0.1
 */