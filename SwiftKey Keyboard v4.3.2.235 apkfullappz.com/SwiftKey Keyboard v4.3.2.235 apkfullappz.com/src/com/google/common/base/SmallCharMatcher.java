package com.google.common.base;

final class SmallCharMatcher
  extends CharMatcher
{
  private final boolean containsZero;
  final long filter;
  private final boolean reprobe;
  private final char[] table;
  
  private SmallCharMatcher(char[] paramArrayOfChar, long paramLong, boolean paramBoolean1, boolean paramBoolean2, String paramString)
  {
    super(paramString);
    this.table = paramArrayOfChar;
    this.filter = paramLong;
    this.containsZero = paramBoolean1;
    this.reprobe = paramBoolean2;
  }
  
  static char[] buildTable(int paramInt, char[] paramArrayOfChar, boolean paramBoolean)
  {
    char[] arrayOfChar = new char[paramInt];
    for (int i = 0;; i++)
    {
      int j;
      int k;
      if (i < paramArrayOfChar.length)
      {
        j = paramArrayOfChar[i];
        k = j % paramInt;
        if (k < 0) {
          k += paramInt;
        }
        if ((arrayOfChar[k] != 0) && (!paramBoolean)) {
          arrayOfChar = null;
        }
      }
      else
      {
        return arrayOfChar;
      }
      if (paramBoolean) {
        while (arrayOfChar[k] != 0) {
          k = (k + 1) % paramInt;
        }
      }
      arrayOfChar[k] = j;
    }
  }
  
  private boolean checkFilter(int paramInt)
  {
    return 1L == (1L & this.filter >> paramInt);
  }
  
  static CharMatcher from(char[] paramArrayOfChar, String paramString)
  {
    long l = 0L;
    int i = paramArrayOfChar.length;
    if (paramArrayOfChar[0] == 0) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      int j = paramArrayOfChar.length;
      for (int k = 0; k < j; k++) {
        l |= 1L << paramArrayOfChar[k];
      }
    }
    char[] arrayOfChar = null;
    for (int m = i; m < 128; m++)
    {
      arrayOfChar = buildTable(m, paramArrayOfChar, false);
      if (arrayOfChar != null) {
        break;
      }
    }
    boolean bool2 = false;
    if (arrayOfChar == null)
    {
      arrayOfChar = buildTable(128, paramArrayOfChar, true);
      bool2 = true;
    }
    return new SmallCharMatcher(arrayOfChar, l, bool1, bool2, paramString);
  }
  
  public boolean matches(char paramChar)
  {
    boolean bool2;
    if (paramChar == 0) {
      bool2 = this.containsZero;
    }
    boolean bool1;
    do
    {
      return bool2;
      bool1 = checkFilter(paramChar);
      bool2 = false;
    } while (!bool1);
    int i = paramChar % this.table.length;
    if (i < 0) {
      i += this.table.length;
    }
    for (;;)
    {
      int j = this.table[i];
      bool2 = false;
      if (j == 0) {
        break;
      }
      if (this.table[i] == paramChar) {
        return true;
      }
      boolean bool3 = this.reprobe;
      bool2 = false;
      if (!bool3) {
        break;
      }
      i = (i + 1) % this.table.length;
    }
  }
  
  public CharMatcher precomputed()
  {
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.base.SmallCharMatcher
 * JD-Core Version:    0.7.0.1
 */