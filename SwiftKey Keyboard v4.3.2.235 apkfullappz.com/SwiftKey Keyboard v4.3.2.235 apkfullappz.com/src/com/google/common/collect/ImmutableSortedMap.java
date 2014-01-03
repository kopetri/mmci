package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.NavigableMap;

public abstract class ImmutableSortedMap<K, V>
  extends ImmutableSortedMapFauxverideShim<K, V>
  implements NavigableMap<K, V>
{
  private static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP = new EmptyImmutableSortedMap(NATURAL_ORDER);
  private static final Comparator<Comparable> NATURAL_ORDER = ;
  private transient ImmutableSortedMap<K, V> descendingMap;
  
  ImmutableSortedMap() {}
  
  ImmutableSortedMap(ImmutableSortedMap<K, V> paramImmutableSortedMap)
  {
    this.descendingMap = paramImmutableSortedMap;
  }
  
  public Map.Entry<K, V> ceilingEntry(K paramK)
  {
    return tailMap(paramK, true).firstEntry();
  }
  
  public K ceilingKey(K paramK)
  {
    return Maps.keyOrNull(ceilingEntry(paramK));
  }
  
  public Comparator<? super K> comparator()
  {
    return keySet().comparator();
  }
  
  public boolean containsValue(Object paramObject)
  {
    return values().contains(paramObject);
  }
  
  abstract ImmutableSortedMap<K, V> createDescendingMap();
  
  public ImmutableSortedSet<K> descendingKeySet()
  {
    return keySet().descendingSet();
  }
  
  public ImmutableSortedMap<K, V> descendingMap()
  {
    ImmutableSortedMap localImmutableSortedMap = this.descendingMap;
    if (localImmutableSortedMap == null)
    {
      localImmutableSortedMap = createDescendingMap();
      this.descendingMap = localImmutableSortedMap;
    }
    return localImmutableSortedMap;
  }
  
  public ImmutableSet<Map.Entry<K, V>> entrySet()
  {
    return super.entrySet();
  }
  
  public Map.Entry<K, V> firstEntry()
  {
    if (isEmpty()) {
      return null;
    }
    return (Map.Entry)entrySet().asList().get(0);
  }
  
  public K firstKey()
  {
    return keySet().first();
  }
  
  public Map.Entry<K, V> floorEntry(K paramK)
  {
    return headMap(paramK, true).lastEntry();
  }
  
  public K floorKey(K paramK)
  {
    return Maps.keyOrNull(floorEntry(paramK));
  }
  
  public ImmutableSortedMap<K, V> headMap(K paramK)
  {
    return headMap(paramK, false);
  }
  
  public abstract ImmutableSortedMap<K, V> headMap(K paramK, boolean paramBoolean);
  
  public Map.Entry<K, V> higherEntry(K paramK)
  {
    return tailMap(paramK, false).firstEntry();
  }
  
  public K higherKey(K paramK)
  {
    return Maps.keyOrNull(higherEntry(paramK));
  }
  
  boolean isPartialView()
  {
    return (keySet().isPartialView()) || (values().isPartialView());
  }
  
  public abstract ImmutableSortedSet<K> keySet();
  
  public Map.Entry<K, V> lastEntry()
  {
    if (isEmpty()) {
      return null;
    }
    return (Map.Entry)entrySet().asList().get(-1 + size());
  }
  
  public K lastKey()
  {
    return keySet().last();
  }
  
  public Map.Entry<K, V> lowerEntry(K paramK)
  {
    return headMap(paramK, false).lastEntry();
  }
  
  public K lowerKey(K paramK)
  {
    return Maps.keyOrNull(lowerEntry(paramK));
  }
  
  public ImmutableSortedSet<K> navigableKeySet()
  {
    return keySet();
  }
  
  public final Map.Entry<K, V> pollFirstEntry()
  {
    throw new UnsupportedOperationException();
  }
  
  public final Map.Entry<K, V> pollLastEntry()
  {
    throw new UnsupportedOperationException();
  }
  
  public int size()
  {
    return values().size();
  }
  
  public ImmutableSortedMap<K, V> subMap(K paramK1, K paramK2)
  {
    return subMap(paramK1, true, paramK2, false);
  }
  
  public ImmutableSortedMap<K, V> subMap(K paramK1, boolean paramBoolean1, K paramK2, boolean paramBoolean2)
  {
    Preconditions.checkNotNull(paramK1);
    Preconditions.checkNotNull(paramK2);
    if (comparator().compare(paramK1, paramK2) <= 0) {}
    for (boolean bool = true;; bool = false)
    {
      Preconditions.checkArgument(bool, "expected fromKey <= toKey but %s > %s", new Object[] { paramK1, paramK2 });
      return headMap(paramK2, paramBoolean2).tailMap(paramK1, paramBoolean1);
    }
  }
  
  public ImmutableSortedMap<K, V> tailMap(K paramK)
  {
    return tailMap(paramK, true);
  }
  
  public abstract ImmutableSortedMap<K, V> tailMap(K paramK, boolean paramBoolean);
  
  public abstract ImmutableCollection<V> values();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ImmutableSortedMap
 * JD-Core Version:    0.7.0.1
 */