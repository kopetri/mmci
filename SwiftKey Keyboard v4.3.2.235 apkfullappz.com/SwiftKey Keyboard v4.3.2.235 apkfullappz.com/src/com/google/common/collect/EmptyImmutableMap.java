package com.google.common.collect;

import java.util.Map;
import java.util.Map.Entry;

final class EmptyImmutableMap
  extends ImmutableMap<Object, Object>
{
  static final EmptyImmutableMap INSTANCE = new EmptyImmutableMap();
  
  public boolean containsKey(Object paramObject)
  {
    return false;
  }
  
  public boolean containsValue(Object paramObject)
  {
    return false;
  }
  
  ImmutableSet<Map.Entry<Object, Object>> createEntrySet()
  {
    throw new AssertionError("should never be called");
  }
  
  public ImmutableSet<Map.Entry<Object, Object>> entrySet()
  {
    return ImmutableSet.of();
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Map)) {
      return ((Map)paramObject).isEmpty();
    }
    return false;
  }
  
  public Object get(Object paramObject)
  {
    return null;
  }
  
  public int hashCode()
  {
    return 0;
  }
  
  public boolean isEmpty()
  {
    return true;
  }
  
  boolean isPartialView()
  {
    return false;
  }
  
  public ImmutableSet<Object> keySet()
  {
    return ImmutableSet.of();
  }
  
  public int size()
  {
    return 0;
  }
  
  public String toString()
  {
    return "{}";
  }
  
  public ImmutableCollection<Object> values()
  {
    return ImmutableCollection.EMPTY_IMMUTABLE_COLLECTION;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.EmptyImmutableMap
 * JD-Core Version:    0.7.0.1
 */