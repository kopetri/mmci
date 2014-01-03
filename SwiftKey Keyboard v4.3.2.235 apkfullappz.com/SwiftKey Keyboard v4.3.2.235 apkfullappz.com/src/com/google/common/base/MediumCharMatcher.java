package com.google.common.base;

final class MediumCharMatcher
  extends CharMatcher
{
  private final boolean containsZero;
  private final long filter;
  private final char[] table;
  
  private MediumCharMatcher(char[] paramArrayOfChar, long paramLong, boolean paramBoolean, String paramString)
  {
    super(paramString);
    this.table = paramArrayOfChar;
    this.filter = paramLong;
    this.containsZero = paramBoolean;
  }
  
  private boolean checkFilter(int paramInt)
  {
    return 1L == (1L & this.filter >> paramInt);
  }
  
  static int chooseTableSize(int paramInt)
  {
    int i;
    if (paramInt == 1) {
      i = 2;
    }
    for (;;)
    {
      return i;
      i = Integer.highestOneBit(paramInt - 1) << 1;
      while (0.5D * i < paramInt) {
        i <<= 1;
      }
    }
  }
  
  static CharMatcher from(char[] paramArrayOfChar, String paramString)
  {
    long l = 0L;
    int i = paramArrayOfChar.length;
    if (paramArrayOfChar[0] == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int j = paramArrayOfChar.length;
      for (int k = 0; k < j; k++) {
        l |= 1L << paramArrayOfChar[k];
      }
    }
    char[] arrayOfChar = new char[chooseTableSize(i)];
    int m = -1 + arrayOfChar.length;
    int n = paramArrayOfChar.length;
    int i1 = 0;
    if (i1 < n)
    {
      int i2 = paramArrayOfChar[i1];
      for (int i3 = i2 & m;; i3 = m & i3 + 1) {
        if (arrayOfChar[i3] == 0)
        {
          arrayOfChar[i3] = i2;
          i1++;
          break;
        }
      }
    }
    return new MediumCharMatcher(arrayOfChar, l, bool, paramString);
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
    char c = -1 + this.table.length;
    int i = paramChar & c;
    int j = i;
    do
    {
      int k = this.table[j];
      bool2 = false;
      if (k == 0) {
        break;
      }
      if (this.table[j] == paramChar) {
        return true;
      }
      j = c & j + 1;
    } while (j != i);
    return false;
  }
  
  public CharMatcher precomputed()
  {
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.base.MediumCharMatcher
 * JD-Core Version:    0.7.0.1
 */