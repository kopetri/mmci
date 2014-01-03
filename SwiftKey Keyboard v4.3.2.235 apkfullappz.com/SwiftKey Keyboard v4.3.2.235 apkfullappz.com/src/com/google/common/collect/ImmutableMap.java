package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class ImmutableMap<K, V>
  implements Serializable, Map<K, V>
{
  private transient ImmutableSet<Map.Entry<K, V>> entrySet;
  private transient ImmutableSet<K> keySet;
  private transient ImmutableCollection<V> values;
  
  public static <K, V> Builder<K, V> builder()
  {
    return new Builder();
  }
  
  public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> paramMap)
  {
    if (((paramMap instanceof ImmutableMap)) && (!(paramMap instanceof ImmutableSortedMap)))
    {
      ImmutableMap localImmutableMap = (ImmutableMap)paramMap;
      if (!localImmutableMap.isPartialView()) {
        return localImmutableMap;
      }
    }
    Map.Entry[] arrayOfEntry = (Map.Entry[])paramMap.entrySet().toArray(new Map.Entry[0]);
    int i;
    switch (arrayOfEntry.length)
    {
    default: 
      i = 0;
    }
    while (i < arrayOfEntry.length)
    {
      arrayOfEntry[i] = entryOf(arrayOfEntry[i].getKey(), arrayOfEntry[i].getValue());
      i++;
      continue;
      return of();
      return new SingletonImmutableMap(entryOf(arrayOfEntry[0].getKey(), arrayOfEntry[0].getValue()));
    }
    return new RegularImmutableMap(arrayOfEntry);
  }
  
  static <K, V> Map.Entry<K, V> entryOf(K paramK, V paramV)
  {
    Preconditions.checkNotNull(paramK, "null key in entry: null=%s", new Object[] { paramV });
    Preconditions.checkNotNull(paramV, "null value in entry: %s=null", new Object[] { paramK });
    return Maps.immutableEntry(paramK, paramV);
  }
  
  public static <K, V> ImmutableMap<K, V> of()
  {
    return EmptyImmutableMap.INSTANCE;
  }
  
  public static <K, V> ImmutableMap<K, V> of(K paramK, V paramV)
  {
    return new SingletonImmutableMap(Preconditions.checkNotNull(paramK), Preconditions.checkNotNull(paramV));
  }
  
  public static <K, V> ImmutableMap<K, V> of(K paramK1, V paramV1, K paramK2, V paramV2, K paramK3, V paramV3, K paramK4, V paramV4, K paramK5, V paramV5)
  {
    Map.Entry[] arrayOfEntry = new Map.Entry[5];
    arrayOfEntry[0] = entryOf(paramK1, paramV1);
    arrayOfEntry[1] = entryOf(paramK2, paramV2);
    arrayOfEntry[2] = entryOf(paramK3, paramV3);
    arrayOfEntry[3] = entryOf(paramK4, paramV4);
    arrayOfEntry[4] = entryOf(paramK5, paramV5);
    return new RegularImmutableMap(arrayOfEntry);
  }
  
  public final void clear()
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean containsKey(Object paramObject)
  {
    return get(paramObject) != null;
  }
  
  public boolean containsValue(Object paramObject)
  {
    return (paramObject != null) && (Maps.containsValueImpl(this, paramObject));
  }
  
  abstract ImmutableSet<Map.Entry<K, V>> createEntrySet();
  
  ImmutableSet<K> createKeySet()
  {
    new ImmutableMapKeySet(entrySet())
    {
      ImmutableMap<K, V> map()
      {
        return ImmutableMap.this;
      }
    };
  }
  
  ImmutableCollection<V> createValues()
  {
    new ImmutableMapValues()
    {
      ImmutableMap<K, V> map()
      {
        return ImmutableMap.this;
      }
    };
  }
  
  public ImmutableSet<Map.Entry<K, V>> entrySet()
  {
    ImmutableSet localImmutableSet = this.entrySet;
    if (localImmutableSet == null)
    {
      localImmutableSet = createEntrySet();
      this.entrySet = localImmutableSet;
    }
    return localImmutableSet;
  }
  
  public boolean equals(Object paramObject)
  {
    return Maps.equalsImpl(this, paramObject);
  }
  
  public abstract V get(Object paramObject);
  
  public int hashCode()
  {
    return entrySet().hashCode();
  }
  
  public boolean isEmpty()
  {
    return size() == 0;
  }
  
  abstract boolean isPartialView();
  
  public ImmutableSet<K> keySet()
  {
    ImmutableSet localImmutableSet = this.keySet;
    if (localImmutableSet == null)
    {
      localImmutableSet = createKeySet();
      this.keySet = localImmutableSet;
    }
    return localImmutableSet;
  }
  
  public final V put(K paramK, V paramV)
  {
    throw new UnsupportedOperationException();
  }
  
  public final void putAll(Map<? extends K, ? extends V> paramMap)
  {
    throw new UnsupportedOperationException();
  }
  
  public final V remove(Object paramObject)
  {
    throw new UnsupportedOperationException();
  }
  
  public String toString()
  {
    return Maps.toStringImpl(this);
  }
  
  public ImmutableCollection<V> values()
  {
    ImmutableCollection localImmutableCollection = this.values;
    if (localImmutableCollection == null)
    {
      localImmutableCollection = createValues();
      this.values = localImmutableCollection;
    }
    return localImmutableCollection;
  }
  
  public static final class Builder<K, V>
  {
    final ArrayList<Map.Entry<K, V>> entries = Lists.newArrayList();
    
    private static <K, V> ImmutableMap<K, V> fromEntryList(List<Map.Entry<K, V>> paramList)
    {
      switch (paramList.size())
      {
      default: 
        return new RegularImmutableMap((Map.Entry[])paramList.toArray(new Map.Entry[paramList.size()]));
      case 0: 
        return ImmutableMap.of();
      }
      return new SingletonImmutableMap((Map.Entry)Iterables.getOnlyElement(paramList));
    }
    
    public ImmutableMap<K, V> build()
    {
      return fromEntryList(this.entries);
    }
    
    public Builder<K, V> put(K paramK, V paramV)
    {
      this.entries.add(ImmutableMap.entryOf(paramK, paramV));
      return this;
    }
    
    public Builder<K, V> putAll(Map<? extends K, ? extends V> paramMap)
    {
      this.entries.ensureCapacity(this.entries.size() + paramMap.size());
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        put(localEntry.getKey(), localEntry.getValue());
      }
      return this;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ImmutableMap
 * JD-Core Version:    0.7.0.1
 */