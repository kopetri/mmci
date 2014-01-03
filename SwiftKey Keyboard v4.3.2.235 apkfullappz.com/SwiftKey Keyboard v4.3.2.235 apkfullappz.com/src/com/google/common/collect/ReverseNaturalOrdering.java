package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;

final class ReverseNaturalOrdering
  extends Ordering<Comparable>
  implements Serializable
{
  static final ReverseNaturalOrdering INSTANCE = new ReverseNaturalOrdering();
  
  public int compare(Comparable paramComparable1, Comparable paramComparable2)
  {
    Preconditions.checkNotNull(paramComparable1);
    if (paramComparable1 == paramComparable2) {
      return 0;
    }
    return paramComparable2.compareTo(paramComparable1);
  }
  
  public <S extends Comparable> Ordering<S> reverse()
  {
    return Ordering.natural();
  }
  
  public String toString()
  {
    return "Ordering.natural().reverse()";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.ReverseNaturalOrdering
 * JD-Core Version:    0.7.0.1
 */