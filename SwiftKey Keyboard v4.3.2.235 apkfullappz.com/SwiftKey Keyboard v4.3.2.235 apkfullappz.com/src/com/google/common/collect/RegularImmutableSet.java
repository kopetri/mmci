package com.google.common.collect;

final class RegularImmutableSet<E>
  extends ImmutableSet.ArrayImmutableSet<E>
{
  private final transient int hashCode;
  private final transient int mask;
  final transient Object[] table;
  
  RegularImmutableSet(Object[] paramArrayOfObject1, int paramInt1, Object[] paramArrayOfObject2, int paramInt2)
  {
    super(paramArrayOfObject1);
    this.table = paramArrayOfObject2;
    this.mask = paramInt2;
    this.hashCode = paramInt1;
  }
  
  public boolean contains(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    for (int i = Hashing.smear(paramObject.hashCode());; i++)
    {
      Object localObject = this.table[(i & this.mask)];
      if (localObject == null) {
        break;
      }
      if (localObject.equals(paramObject)) {
        return true;
      }
    }
  }
  
  public int hashCode()
  {
    return this.hashCode;
  }
  
  boolean isHashCodeFast()
  {
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.RegularImmutableSet
 * JD-Core Version:    0.7.0.1
 */