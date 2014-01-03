package com.google.common.math;

import com.google.common.base.Preconditions;
import java.math.RoundingMode;

public final class LongMath
{
  static final int[] BIGGEST_BINOMIALS = { 2147483647, 2147483647, 2147483647, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, 206, 169, 143, 125, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66 };
  static final int[] BIGGEST_SIMPLE_BINOMIALS = { 2147483647, 2147483647, 2147483647, 2642246, 86251, 11724, 3218, 1313, 684, 419, 287, 214, 169, 139, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61 };
  static final long[] FACTORIALS;
  static final long[] HALF_POWERS_OF_10;
  static final byte[] MAX_LOG10_FOR_LEADING_ZEROS = { 19, 18, 18, 18, 18, 17, 17, 17, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 13, 13, 13, 12, 12, 12, 12, 11, 11, 11, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0 };
  static final long[] POWERS_OF_10 = { 1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L };
  
  static
  {
    HALF_POWERS_OF_10 = new long[] { 3L, 31L, 316L, 3162L, 31622L, 316227L, 3162277L, 31622776L, 316227766L, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L };
    FACTORIALS = new long[] { 1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L };
  }
  
  public static long divide(long paramLong1, long paramLong2, RoundingMode paramRoundingMode)
  {
    Preconditions.checkNotNull(paramRoundingMode);
    long l1 = paramLong1 / paramLong2;
    long l2 = paramLong1 - paramLong2 * l1;
    if (l2 == 0L) {
      return l1;
    }
    int i = 0x1 | (int)((paramLong1 ^ paramLong2) >> 63);
    boolean bool;
    switch (1.$SwitchMap$java$math$RoundingMode[paramRoundingMode.ordinal()])
    {
    default: 
      throw new AssertionError();
    case 1: 
      if (l2 == 0L)
      {
        bool = true;
        MathPreconditions.checkRoundingUnnecessary(bool);
      }
    case 2: 
    case 4: 
      for (j = 0; j != 0; j = 1)
      {
        return l1 + i;
        bool = false;
        break label114;
      }
    case 5: 
      if (i > 0) {}
      for (j = 1;; j = 0) {
        break;
      }
    case 3: 
      label114:
      if (i < 0) {}
      for (j = 1;; j = 0) {
        break;
      }
    }
    long l3 = Math.abs(l2);
    long l4 = l3 - (Math.abs(paramLong2) - l3);
    if (l4 == 0L)
    {
      int k;
      label217:
      int m;
      if (paramRoundingMode == RoundingMode.HALF_UP)
      {
        k = 1;
        if (paramRoundingMode != RoundingMode.HALF_EVEN) {
          break label259;
        }
        m = 1;
        label228:
        if ((1L & l1) == 0L) {
          break label265;
        }
      }
      label259:
      label265:
      for (int n = 1;; n = 0)
      {
        j = k | n & m;
        break;
        k = 0;
        break label217;
        m = 0;
        break label228;
      }
    }
    if (l4 > 0L) {}
    for (int j = 1;; j = 0) {
      break;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.math.LongMath
 * JD-Core Version:    0.7.0.1
 */