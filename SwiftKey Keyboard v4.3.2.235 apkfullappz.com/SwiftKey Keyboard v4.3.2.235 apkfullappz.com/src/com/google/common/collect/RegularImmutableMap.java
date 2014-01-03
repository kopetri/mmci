package com.google.common.collect;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import java.util.Map.Entry;

final class RegularImmutableMap<K, V>
  extends ImmutableMap<K, V>
{
  private final transient LinkedEntry<K, V>[] entries;
  private final transient int keySetHashCode;
  private final transient int mask;
  private final transient LinkedEntry<K, V>[] table;
  
  RegularImmutableMap(Map.Entry<?, ?>... paramVarArgs)
  {
    int i = paramVarArgs.length;
    this.entries = createEntryArray(i);
    int j = chooseTableSize(i);
    this.table = createEntryArray(j);
    this.mask = (j - 1);
    int k = 0;
    for (int m = 0; m < i; m++)
    {
      Map.Entry<?, ?> localEntry = paramVarArgs[m];
      Object localObject = localEntry.getKey();
      int n = localObject.hashCode();
      k += n;
      int i1 = Hashing.smear(n) & this.mask;
      LinkedEntry localLinkedEntry1 = this.table[i1];
      LinkedEntry localLinkedEntry2 = newLinkedEntry(localObject, localEntry.getValue(), localLinkedEntry1);
      this.table[i1] = localLinkedEntry2;
      this.entries[m] = localLinkedEntry2;
      if (localLinkedEntry1 != null)
      {
        if (!localObject.equals(localLinkedEntry1.getKey())) {}
        for (boolean bool = true;; bool = false)
        {
          Preconditions.checkArgument(bool, "duplicate key: %s", new Object[] { localObject });
          localLinkedEntry1 = localLinkedEntry1.next();
          break;
        }
      }
    }
    this.keySetHashCode = k;
  }
  
  private static int chooseTableSize(int paramInt)
  {
    int i = Integer.highestOneBit(paramInt);
    if (paramInt / i > 1.2D)
    {
      i <<= 1;
      if (i <= 0) {
        break label48;
      }
    }
    label48:
    for (boolean bool = true;; bool = false)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      Preconditions.checkArgument(bool, "table too large: %s", arrayOfObject);
      return i;
    }
  }
  
  private LinkedEntry<K, V>[] createEntryArray(int paramInt)
  {
    return new LinkedEntry[paramInt];
  }
  
  private static <K, V> LinkedEntry<K, V> newLinkedEntry(K paramK, V paramV, LinkedEntry<K, V> paramLinkedEntry)
  {
    if (paramLinkedEntry == null) {
      return new TerminalEntry(paramK, paramV);
    }
    return new NonTerminalEntry(paramK, paramV, paramLinkedEntry);
  }
  
  public boolean containsValue(Object paramObject)
  {
    if (paramObject == null) {}
    for (;;)
    {
      return false;
      LinkedEntry[] arrayOfLinkedEntry = this.entries;
      int i = arrayOfLinkedEntry.length;
      for (int j = 0; j < i; j++) {
        if (arrayOfLinkedEntry[j].getValue().equals(paramObject)) {
          return true;
        }
      }
    }
  }
  
  ImmutableSet<Map.Entry<K, V>> createEntrySet()
  {
    return new EntrySet(null);
  }
  
  ImmutableSet<K> createKeySet()
  {
    new ImmutableMapKeySet(entrySet(), this.keySetHashCode)
    {
      ImmutableMap<K, V> map()
      {
        return RegularImmutableMap.this;
      }
    };
  }
  
  public V get(Object paramObject)
  {
    if (paramObject == null) {}
    for (;;)
    {
      return null;
      int i = Hashing.smear(paramObject.hashCode()) & this.mask;
      for (LinkedEntry localLinkedEntry = this.table[i]; localLinkedEntry != null; localLinkedEntry = localLinkedEntry.next()) {
        if (paramObject.equals(localLinkedEntry.getKey())) {
          return localLinkedEntry.getValue();
        }
      }
    }
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
    return this.entries.length;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = Collections2.newStringBuilderForCollection(size()).append('{');
    Collections2.STANDARD_JOINER.appendTo(localStringBuilder, this.entries);
    return '}';
  }
  
  private final class EntrySet
    extends ImmutableMapEntrySet<K, V>
  {
    private EntrySet() {}
    
    ImmutableList<Map.Entry<K, V>> createAsList()
    {
      return new RegularImmutableAsList(this, RegularImmutableMap.this.entries);
    }
    
    public UnmodifiableIterator<Map.Entry<K, V>> iterator()
    {
      return asList().iterator();
    }
    
    ImmutableMap<K, V> map()
    {
      return RegularImmutableMap.this;
    }
  }
  
  private static abstract interface LinkedEntry<K, V>
    extends Map.Entry<K, V>
  {
    public abstract LinkedEntry<K, V> next();
  }
  
  private static final class NonTerminalEntry<K, V>
    extends ImmutableEntry<K, V>
    implements RegularImmutableMap.LinkedEntry<K, V>
  {
    final RegularImmutableMap.LinkedEntry<K, V> next;
    
    NonTerminalEntry(K paramK, V paramV, RegularImmutableMap.LinkedEntry<K, V> paramLinkedEntry)
    {
      super(paramV);
      this.next = paramLinkedEntry;
    }
    
    public RegularImmutableMap.LinkedEntry<K, V> next()
    {
      return this.next;
    }
  }
  
  private static final class TerminalEntry<K, V>
    extends ImmutableEntry<K, V>
    implements RegularImmutableMap.LinkedEntry<K, V>
  {
    TerminalEntry(K paramK, V paramV)
    {
      super(paramV);
    }
    
    public RegularImmutableMap.LinkedEntry<K, V> next()
    {
      return null;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.RegularImmutableMap
 * JD-Core Version:    0.7.0.1
 */