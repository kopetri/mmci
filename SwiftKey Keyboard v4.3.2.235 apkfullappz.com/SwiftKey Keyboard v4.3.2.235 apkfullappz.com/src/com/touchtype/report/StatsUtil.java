package com.touchtype.report;

import java.lang.reflect.Array;

public final class StatsUtil
{
  public static int computeLevenshteinDistance(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    int i = 1 + paramCharSequence1.length();
    int j = 1 + paramCharSequence2.length();
    int[] arrayOfInt = { i, j };
    int[][] arrayOfInt1 = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt);
    for (int k = 0; k < i; k++) {
      arrayOfInt1[k][0] = k;
    }
    for (int m = 0; m < j; m++) {
      arrayOfInt1[0][m] = m;
    }
    for (int n = 1; n < j; n++)
    {
      int i1 = 1;
      if (i1 < i)
      {
        if (paramCharSequence1.charAt(i1 - 1) == paramCharSequence2.charAt(n - 1)) {
          arrayOfInt1[i1][n] = arrayOfInt1[(i1 - 1)][(n - 1)];
        }
        for (;;)
        {
          i1++;
          break;
          arrayOfInt1[i1][n] = Math.min(1 + arrayOfInt1[(i1 - 1)][n], Math.min(1 + arrayOfInt1[i1][(n - 1)], 1 + arrayOfInt1[(i1 - 1)][(n - 1)]));
        }
      }
    }
    return arrayOfInt1[(i - 1)][(j - 1)];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.StatsUtil
 * JD-Core Version:    0.7.0.1
 */