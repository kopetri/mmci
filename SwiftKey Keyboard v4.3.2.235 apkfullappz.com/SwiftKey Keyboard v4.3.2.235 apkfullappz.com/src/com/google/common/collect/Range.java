package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;

public final class Range<C extends Comparable>
  implements Predicate<C>, Serializable
{
  final Cut<C> lowerBound;
  final Cut<C> upperBound;
  
  Range(Cut<C> paramCut1, Cut<C> paramCut2)
  {
    if (paramCut1.compareTo(paramCut2) > 0) {
      throw new IllegalArgumentException("Invalid range: " + toString(paramCut1, paramCut2));
    }
    this.lowerBound = paramCut1;
    this.upperBound = paramCut2;
  }
  
  static int compareOrThrow(Comparable paramComparable1, Comparable paramComparable2)
  {
    return paramComparable1.compareTo(paramComparable2);
  }
  
  private static String toString(Cut<?> paramCut1, Cut<?> paramCut2)
  {
    StringBuilder localStringBuilder = new StringBuilder(16);
    paramCut1.describeAsLowerBound(localStringBuilder);
    localStringBuilder.append('‥');
    paramCut2.describeAsUpperBound(localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public boolean apply(C paramC)
  {
    return contains(paramC);
  }
  
  public boolean contains(C paramC)
  {
    Preconditions.checkNotNull(paramC);
    return (this.lowerBound.isLessThan(paramC)) && (!this.upperBound.isLessThan(paramC));
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Range;
    boolean bool2 = false;
    if (bool1)
    {
      Range localRange = (Range)paramObject;
      boolean bool3 = this.lowerBound.equals(localRange.lowerBound);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.upperBound.equals(localRange.upperBound);
        bool2 = false;
        if (bool4) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public int hashCode()
  {
    return 31 * this.lowerBound.hashCode() + this.upperBound.hashCode();
  }
  
  public boolean isEmpty()
  {
    return this.lowerBound.equals(this.upperBound);
  }
  
  public C lowerEndpoint()
  {
    return this.lowerBound.endpoint();
  }
  
  public String toString()
  {
    return toString(this.lowerBound, this.upperBound);
  }
  
  public C upperEndpoint()
  {
    return this.upperBound.endpoint();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.Range
 * JD-Core Version:    0.7.0.1
 */