package com.google.common.base;

import java.util.Iterator;

public final class Splitter
{
  private final int limit;
  private final boolean omitEmptyStrings;
  private final Strategy strategy;
  private final CharMatcher trimmer;
  
  private Splitter(Strategy paramStrategy)
  {
    this(paramStrategy, false, CharMatcher.NONE, 2147483647);
  }
  
  private Splitter(Strategy paramStrategy, boolean paramBoolean, CharMatcher paramCharMatcher, int paramInt)
  {
    this.strategy = paramStrategy;
    this.omitEmptyStrings = paramBoolean;
    this.trimmer = paramCharMatcher;
    this.limit = paramInt;
  }
  
  public static Splitter on(String paramString)
  {
    if (paramString.length() != 0) {}
    for (boolean bool = true;; bool = false)
    {
      Preconditions.checkArgument(bool, "The separator may not be the empty string.");
      new Splitter(new Strategy()
      {
        public Splitter.SplittingIterator iterator(Splitter paramAnonymousSplitter, CharSequence paramAnonymousCharSequence)
        {
          new Splitter.SplittingIterator(paramAnonymousSplitter, paramAnonymousCharSequence)
          {
            public int separatorEnd(int paramAnonymous2Int)
            {
              return paramAnonymous2Int + Splitter.2.this.val$separator.length();
            }
            
            public int separatorStart(int paramAnonymous2Int)
            {
              int i = Splitter.2.this.val$separator.length();
              int j = paramAnonymous2Int;
              int k = this.toSplit.length() - i;
              while (j <= k)
              {
                for (int m = 0;; m++)
                {
                  if (m >= i) {
                    return j;
                  }
                  if (this.toSplit.charAt(m + j) != Splitter.2.this.val$separator.charAt(m)) {
                    break;
                  }
                }
                j++;
              }
              j = -1;
              return j;
            }
          };
        }
      });
    }
  }
  
  private Iterator<String> spliterator(CharSequence paramCharSequence)
  {
    return this.strategy.iterator(this, paramCharSequence);
  }
  
  public Splitter omitEmptyStrings()
  {
    return new Splitter(this.strategy, true, this.trimmer, this.limit);
  }
  
  public Iterable<String> split(final CharSequence paramCharSequence)
  {
    Preconditions.checkNotNull(paramCharSequence);
    new Iterable()
    {
      public Iterator<String> iterator()
      {
        return Splitter.this.spliterator(paramCharSequence);
      }
      
      public String toString()
      {
        return ']';
      }
    };
  }
  
  private static abstract class SplittingIterator
    extends AbstractIterator<String>
  {
    int limit;
    int offset = 0;
    final boolean omitEmptyStrings;
    final CharSequence toSplit;
    final CharMatcher trimmer;
    
    protected SplittingIterator(Splitter paramSplitter, CharSequence paramCharSequence)
    {
      this.trimmer = paramSplitter.trimmer;
      this.omitEmptyStrings = paramSplitter.omitEmptyStrings;
      this.limit = paramSplitter.limit;
      this.toSplit = paramCharSequence;
    }
    
    protected String computeNext()
    {
      int i = this.offset;
      while (this.offset != -1)
      {
        int j = i;
        int k = separatorStart(this.offset);
        int m;
        if (k == -1) {
          m = this.toSplit.length();
        }
        for (this.offset = -1;; this.offset = separatorEnd(k))
        {
          if (this.offset != i) {
            break label102;
          }
          this.offset = (1 + this.offset);
          if (this.offset < this.toSplit.length()) {
            break;
          }
          this.offset = -1;
          break;
          m = k;
        }
        label102:
        while ((j < m) && (this.trimmer.matches(this.toSplit.charAt(j)))) {
          j++;
        }
        while ((m > j) && (this.trimmer.matches(this.toSplit.charAt(m - 1)))) {
          m--;
        }
        if ((this.omitEmptyStrings) && (j == m))
        {
          i = this.offset;
        }
        else
        {
          if (this.limit == 1)
          {
            m = this.toSplit.length();
            this.offset = -1;
            while ((m > j) && (this.trimmer.matches(this.toSplit.charAt(m - 1)))) {
              m--;
            }
          }
          this.limit = (-1 + this.limit);
          return this.toSplit.subSequence(j, m).toString();
        }
      }
      return (String)endOfData();
    }
    
    abstract int separatorEnd(int paramInt);
    
    abstract int separatorStart(int paramInt);
  }
  
  private static abstract interface Strategy
  {
    public abstract Iterator<String> iterator(Splitter paramSplitter, CharSequence paramCharSequence);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.base.Splitter
 * JD-Core Version:    0.7.0.1
 */