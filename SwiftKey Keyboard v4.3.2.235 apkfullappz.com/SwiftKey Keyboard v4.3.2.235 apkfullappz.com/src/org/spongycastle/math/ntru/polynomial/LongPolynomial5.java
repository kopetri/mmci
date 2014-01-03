package org.spongycastle.math.ntru.polynomial;

import java.lang.reflect.Array;
import org.spongycastle.util.Arrays;

public class LongPolynomial5
{
  private long[] coeffs;
  private int numCoeffs;
  
  public LongPolynomial5(IntegerPolynomial paramIntegerPolynomial)
  {
    this.numCoeffs = paramIntegerPolynomial.coeffs.length;
    this.coeffs = new long[(4 + this.numCoeffs) / 5];
    int i = 0;
    int j = 0;
    for (int k = 0; k < this.numCoeffs; k++)
    {
      long[] arrayOfLong = this.coeffs;
      arrayOfLong[i] |= paramIntegerPolynomial.coeffs[k] << j;
      j += 12;
      if (j >= 60)
      {
        j = 0;
        i++;
      }
    }
  }
  
  private LongPolynomial5(long[] paramArrayOfLong, int paramInt)
  {
    this.coeffs = paramArrayOfLong;
    this.numCoeffs = paramInt;
  }
  
  public LongPolynomial5 mult(TernaryPolynomial paramTernaryPolynomial)
  {
    int[] arrayOfInt1 = { 5, -1 + (this.coeffs.length + (4 + paramTernaryPolynomial.size()) / 5) };
    long[][] arrayOfLong = (long[][])Array.newInstance(Long.TYPE, arrayOfInt1);
    int[] arrayOfInt2 = paramTernaryPolynomial.getOnes();
    for (int i = 0; i != arrayOfInt2.length; i++)
    {
      int i14 = arrayOfInt2[i];
      int i15 = i14 / 5;
      int i16 = i14 - i15 * 5;
      for (int i17 = 0; i17 < this.coeffs.length; i17++)
      {
        arrayOfLong[i16][i15] = (0xFF7FF7FF & arrayOfLong[i16][i15] + this.coeffs[i17]);
        i15++;
      }
    }
    int[] arrayOfInt3 = paramTernaryPolynomial.getNegOnes();
    for (int j = 0; j != arrayOfInt3.length; j++)
    {
      int i10 = arrayOfInt3[j];
      int i11 = i10 / 5;
      int i12 = i10 - i11 * 5;
      for (int i13 = 0; i13 < this.coeffs.length; i13++)
      {
        arrayOfLong[i12][i11] = (0xFF7FF7FF & 576601524159907840L + arrayOfLong[i12][i11] - this.coeffs[i13]);
        i11++;
      }
    }
    long[] arrayOfLong1 = Arrays.copyOf(arrayOfLong[0], 1 + arrayOfLong[0].length);
    for (int k = 1; k <= 4; k++)
    {
      int i5 = k * 12;
      int i6 = 60 - i5;
      long l4 = (1L << i6) - 1L;
      int i7 = arrayOfLong[k].length;
      for (int i8 = 0; i8 < i7; i8++)
      {
        long l5 = arrayOfLong[k][i8] >> i6;
        long l6 = l4 & arrayOfLong[k][i8];
        arrayOfLong1[i8] = (0xFF7FF7FF & arrayOfLong1[i8] + (l6 << i5));
        int i9 = i8 + 1;
        arrayOfLong1[i9] = (0xFF7FF7FF & l5 + arrayOfLong1[i9]);
      }
    }
    int m = 12 * (this.numCoeffs % 5);
    int n = -1 + this.coeffs.length;
    if (n < arrayOfLong1.length)
    {
      long l1;
      if (n == -1 + this.coeffs.length) {
        if (this.numCoeffs == 5) {
          l1 = 0L;
        }
      }
      label426:
      for (int i1 = 0;; i1 = n * 5 - this.numCoeffs)
      {
        int i2 = i1 / 5;
        int i3 = i1 - i2 * 5;
        long l2 = l1 << i3 * 12;
        long l3 = l1 >> 12 * (5 - i3);
        arrayOfLong1[i2] = (0xFF7FF7FF & l2 + arrayOfLong1[i2]);
        int i4 = i2 + 1;
        if (i4 < this.coeffs.length) {
          arrayOfLong1[i4] = (0xFF7FF7FF & l3 + arrayOfLong1[i4]);
        }
        n++;
        break;
        l1 = arrayOfLong1[n] >> m;
        break label426;
        l1 = arrayOfLong1[n];
      }
    }
    LongPolynomial5 localLongPolynomial5 = new LongPolynomial5(arrayOfLong1, this.numCoeffs);
    return localLongPolynomial5;
  }
  
  public IntegerPolynomial toIntegerPolynomial()
  {
    int[] arrayOfInt = new int[this.numCoeffs];
    int i = 0;
    int j = 0;
    for (int k = 0; k < this.numCoeffs; k++)
    {
      arrayOfInt[k] = ((int)(0x7FF & this.coeffs[i] >> j));
      j += 12;
      if (j >= 60)
      {
        j = 0;
        i++;
      }
    }
    return new IntegerPolynomial(arrayOfInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.LongPolynomial5
 * JD-Core Version:    0.7.0.1
 */