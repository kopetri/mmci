package com.touchtype.util;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public final class WeakHashSet<T>
  extends AbstractSet<T>
{
  private WeakHashMap<T, Boolean> mHashMap;
  
  public WeakHashSet()
  {
    this.mHashMap = new WeakHashMap();
  }
  
  public WeakHashSet(int paramInt)
  {
    this.mHashMap = new WeakHashMap(paramInt);
  }
  
  public boolean add(T paramT)
  {
    return this.mHashMap.put(paramT, Boolean.TRUE) == null;
  }
  
  public void clear()
  {
    this.mHashMap.clear();
  }
  
  public Iterator<T> iterator()
  {
    return this.mHashMap.keySet().iterator();
  }
  
  public boolean remove(Object paramObject)
  {
    return this.mHashMap.remove(paramObject) != null;
  }
  
  public int size()
  {
    return this.mHashMap.size();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.WeakHashSet
 * JD-Core Version:    0.7.0.1
 */