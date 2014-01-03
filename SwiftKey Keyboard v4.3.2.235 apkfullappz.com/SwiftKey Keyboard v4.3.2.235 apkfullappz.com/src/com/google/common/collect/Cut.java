package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.io.Serializable;

abstract class Cut<C extends Comparable>
  implements Serializable, Comparable<Cut<C>>
{
  final C endpoint;
  
  Cut(C paramC)
  {
    this.endpoint = paramC;
  }
  
  static <C extends Comparable> Cut<C> aboveAll()
  {
    return AboveAll.INSTANCE;
  }
  
  static <C extends Comparable> Cut<C> aboveValue(C paramC)
  {
    return new AboveValue(paramC);
  }
  
  static <C extends Comparable> Cut<C> belowAll()
  {
    return BelowAll.INSTANCE;
  }
  
  public int compareTo(Cut<C> paramCut)
  {
    int i;
    if (paramCut == belowAll()) {
      i = 1;
    }
    do
    {
      return i;
      if (paramCut == aboveAll()) {
        return -1;
      }
      i = Range.compareOrThrow(this.endpoint, paramCut.endpoint);
    } while (i != 0);
    return Booleans.compare(this instanceof AboveValue, paramCut instanceof AboveValue);
  }
  
  abstract void describeAsLowerBound(StringBuilder paramStringBuilder);
  
  abstract void describeAsUpperBound(StringBuilder paramStringBuilder);
  
  C endpoint()
  {
    return this.endpoint;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Cut;
    boolean bool2 = false;
    Cut localCut;
    if (bool1) {
      localCut = (Cut)paramObject;
    }
    try
    {
      int i = compareTo(localCut);
      bool2 = false;
      if (i == 0) {
        bool2 = true;
      }
      return bool2;
    }
    catch (ClassCastException localClassCastException) {}
    return false;
  }
  
  abstract boolean isLessThan(C paramC);
  
  private static final class AboveAll
    extends Cut<Comparable<?>>
  {
    private static final AboveAll INSTANCE = new AboveAll();
    
    private AboveAll()
    {
      super();
    }
    
    public int compareTo(Cut<Comparable<?>> paramCut)
    {
      if (paramCut == this) {
        return 0;
      }
      return 1;
    }
    
    void describeAsLowerBound(StringBuilder paramStringBuilder)
    {
      throw new AssertionError();
    }
    
    void describeAsUpperBound(StringBuilder paramStringBuilder)
    {
      paramStringBuilder.append("+∞)");
    }
    
    Comparable<?> endpoint()
    {
      throw new IllegalStateException("range unbounded on this side");
    }
    
    boolean isLessThan(Comparable<?> paramComparable)
    {
      return false;
    }
  }
  
  private static final class AboveValue<C extends Comparable>
    extends Cut<C>
  {
    AboveValue(C paramC)
    {
      super();
    }
    
    void describeAsLowerBound(StringBuilder paramStringBuilder)
    {
      paramStringBuilder.append('(').append(this.endpoint);
    }
    
    void describeAsUpperBound(StringBuilder paramStringBuilder)
    {
      paramStringBuilder.append(this.endpoint).append(']');
    }
    
    public int hashCode()
    {
      return 0xFFFFFFFF ^ this.endpoint.hashCode();
    }
    
    boolean isLessThan(C paramC)
    {
      return Range.compareOrThrow(this.endpoint, paramC) < 0;
    }
  }
  
  private static final class BelowAll
    extends Cut<Comparable<?>>
  {
    private static final BelowAll INSTANCE = new BelowAll();
    
    private BelowAll()
    {
      super();
    }
    
    public int compareTo(Cut<Comparable<?>> paramCut)
    {
      if (paramCut == this) {
        return 0;
      }
      return -1;
    }
    
    void describeAsLowerBound(StringBuilder paramStringBuilder)
    {
      paramStringBuilder.append("(-∞");
    }
    
    void describeAsUpperBound(StringBuilder paramStringBuilder)
    {
      throw new AssertionError();
    }
    
    Comparable<?> endpoint()
    {
      throw new IllegalStateException("range unbounded on this side");
    }
    
    boolean isLessThan(Comparable<?> paramComparable)
    {
      return true;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.Cut
 * JD-Core Version:    0.7.0.1
 */