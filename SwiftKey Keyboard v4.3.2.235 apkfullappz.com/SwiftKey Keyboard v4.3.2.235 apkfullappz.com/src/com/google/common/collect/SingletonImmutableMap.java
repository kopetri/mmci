package com.google.common.collect;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class SingletonImmutableMap<K, V>
  extends ImmutableMap<K, V>
{
  final transient K singleKey;
  final transient V singleValue;
  
  SingletonImmutableMap(K paramK, V paramV)
  {
    this.singleKey = paramK;
    this.singleValue = paramV;
  }
  
  SingletonImmutableMap(Map.Entry<K, V> paramEntry)
  {
    this(paramEntry.getKey(), paramEntry.getValue());
  }
  
  public boolean containsKey(Object paramObject)
  {
    return this.singleKey.equals(paramObject);
  }
  
  public boolean containsValue(Object paramObject)
  {
    return this.singleValue.equals(paramObject);
  }
  
  ImmutableSet<Map.Entry<K, V>> createEntrySet()
  {
    return ImmutableSet.of(Maps.immutableEntry(this.singleKey, this.singleValue));
  }
  
  ImmutableSet<K> createKeySet()
  {
    return ImmutableSet.of(this.singleKey);
  }
  
  ImmutableCollection<V> createValues()
  {
    return ImmutableList.of(this.singleValue);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    Map.Entry localEntry;
    do
    {
      return true;
      if (!(paramObject instanceof Map)) {
        break;
      }
      Map localMap = (Map)paramObject;
      if (localMap.size() != 1) {
        break;
      }
      localEntry = (Map.Entry)localMap.entrySet().iterator().next();
    } while ((this.singleKey.equals(localEntry.getKey())) && (this.singleValue.equals(localEntry.getValue())));
    return false;
    return false;
  }
  
  public V get(Object paramObject)
  {
    if (this.singleKey.equals(paramObject)) {
      return this.singleValue;
    }
    return null;
  }
  
  public int hashCode()
  {
    return this.singleKey.hashCode() ^ this.singleValue.hashCode();
  }
  
  public boolean isEmpty()
  {
    return false;
  }
  
  boolean isPartialView()
  {
    return false;
  }
  
  public int size()
  {
    return 1;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.SingletonImmutableMap
 * JD-Core Version:    0.7.0.1
 */