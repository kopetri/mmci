package org.spongycastle.math.ntru.polynomial;

import org.spongycastle.util.Arrays;

public class LongPolynomial2
{
  private long[] coeffs;
  private int numCoeffs;
  
  private LongPolynomial2(int paramInt)
  {
    this.coeffs = new long[paramInt];
  }
  
  public LongPolynomial2(IntegerPolynomial paramIntegerPolynomial)
  {
    this.numCoeffs = paramIntegerPolynomial.coeffs.length;
    this.coeffs = new long[(1 + this.numCoeffs) / 2];
    int i = 0;
    int j = 0;
    while (j < this.numCoeffs)
    {
      int[] arrayOfInt1 = paramIntegerPolynomial.coeffs;
      int k = j + 1;
      for (int m = arrayOfInt1[j]; m < 0; m += 2048) {}
      long l;
      if (k < this.numCoeffs)
      {
        int[] arrayOfInt2 = paramIntegerPolynomial.coeffs;
        j = k + 1;
        l = arrayOfInt2[k];
      }
      while (l < 0L)
      {
        l += 2048L;
        continue;
        l = 0L;
        j = k;
      }
      this.coeffs[i] = (m + (l << 24));
      i++;
    }
  }
  
  private LongPolynomial2(long[] paramArrayOfLong)
  {
    this.coeffs = paramArrayOfLong;
  }
  
  private void add(LongPolynomial2 paramLongPolynomial2)
  {
    if (paramLongPolynomial2.coeffs.length > this.coeffs.length) {
      this.coeffs = Arrays.copyOf(this.coeffs, paramLongPolynomial2.coeffs.length);
    }
    for (int i = 0; i < paramLongPolynomial2.coeffs.length; i++) {
      this.coeffs[i] = (0xFF0007FF & this.coeffs[i] + paramLongPolynomial2.coeffs[i]);
    }
  }
  
  private LongPolynomial2 multRecursive(LongPolynomial2 paramLongPolynomial2)
  {
    long[] arrayOfLong1 = this.coeffs;
    long[] arrayOfLong2 = paramLongPolynomial2.coeffs;
    int i = paramLongPolynomial2.coeffs.length;
    if (i <= 32)
    {
      int i4 = i * 2;
      localLongPolynomial210 = new LongPolynomial2(new long[i4]);
      for (int i5 = 0; i5 < i4; i5++) {
        for (int i6 = Math.max(0, 1 + (i5 - i));; i6++)
        {
          int i7 = i - 1;
          int i8 = Math.min(i5, i7);
          if (i6 > i8) {
            break;
          }
          long l1 = arrayOfLong1[(i5 - i6)] * arrayOfLong2[i6];
          long l2 = l1 & 34342961152L + (0x7FF & l1);
          long l3 = 0x7FF & l1 >>> 48;
          localLongPolynomial210.coeffs[i5] = (0xFF0007FF & l2 + localLongPolynomial210.coeffs[i5]);
          localLongPolynomial210.coeffs[(i5 + 1)] = (0xFF0007FF & l3 + localLongPolynomial210.coeffs[(i5 + 1)]);
        }
      }
    }
    int j = i / 2;
    LongPolynomial2 localLongPolynomial21 = new LongPolynomial2(Arrays.copyOf(arrayOfLong1, j));
    LongPolynomial2 localLongPolynomial22 = new LongPolynomial2(Arrays.copyOfRange(arrayOfLong1, j, i));
    LongPolynomial2 localLongPolynomial23 = new LongPolynomial2(Arrays.copyOf(arrayOfLong2, j));
    LongPolynomial2 localLongPolynomial24 = new LongPolynomial2(Arrays.copyOfRange(arrayOfLong2, j, i));
    LongPolynomial2 localLongPolynomial25 = (LongPolynomial2)localLongPolynomial21.clone();
    localLongPolynomial25.add(localLongPolynomial22);
    LongPolynomial2 localLongPolynomial26 = (LongPolynomial2)localLongPolynomial23.clone();
    localLongPolynomial26.add(localLongPolynomial24);
    LongPolynomial2 localLongPolynomial27 = localLongPolynomial21.multRecursive(localLongPolynomial23);
    LongPolynomial2 localLongPolynomial28 = localLongPolynomial22.multRecursive(localLongPolynomial24);
    LongPolynomial2 localLongPolynomial29 = localLongPolynomial25.multRecursive(localLongPolynomial26);
    localLongPolynomial29.sub(localLongPolynomial27);
    localLongPolynomial29.sub(localLongPolynomial28);
    LongPolynomial2 localLongPolynomial210 = new LongPolynomial2(i * 2);
    for (int k = 0;; k++)
    {
      int m = localLongPolynomial27.coeffs.length;
      if (k >= m) {
        break;
      }
      localLongPolynomial210.coeffs[k] = (0xFF0007FF & localLongPolynomial27.coeffs[k]);
    }
    for (int n = 0;; n++)
    {
      int i1 = localLongPolynomial29.coeffs.length;
      if (n >= i1) {
        break;
      }
      localLongPolynomial210.coeffs[(j + n)] = (0xFF0007FF & localLongPolynomial210.coeffs[(j + n)] + localLongPolynomial29.coeffs[n]);
    }
    for (int i2 = 0;; i2++)
    {
      int i3 = localLongPolynomial28.coeffs.length;
      if (i2 >= i3) {
        break;
      }
      localLongPolynomial210.coeffs[(i2 + j * 2)] = (0xFF0007FF & localLongPolynomial210.coeffs[(i2 + j * 2)] + localLongPolynomial28.coeffs[i2]);
    }
    return localLongPolynomial210;
  }
  
  private void sub(LongPolynomial2 paramLongPolynomial2)
  {
    if (paramLongPolynomial2.coeffs.length > this.coeffs.length) {
      this.coeffs = Arrays.copyOf(this.coeffs, paramLongPolynomial2.coeffs.length);
    }
    for (int i = 0; i < paramLongPolynomial2.coeffs.length; i++) {
      this.coeffs[i] = (0xFF0007FF & 140737496743936L + this.coeffs[i] - paramLongPolynomial2.coeffs[i]);
    }
  }
  
  public Object clone()
  {
    LongPolynomial2 localLongPolynomial2 = new LongPolynomial2((long[])this.coeffs.clone());
    localLongPolynomial2.numCoeffs = this.numCoeffs;
    return localLongPolynomial2;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof LongPolynomial2)) {
      return Arrays.areEqual(this.coeffs, ((LongPolynomial2)paramObject).coeffs);
    }
    return false;
  }
  
  public LongPolynomial2 mult(LongPolynomial2 paramLongPolynomial2)
  {
    int i = this.coeffs.length;
    if ((paramLongPolynomial2.coeffs.length != i) || (this.numCoeffs != paramLongPolynomial2.numCoeffs)) {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    LongPolynomial2 localLongPolynomial21 = multRecursive(paramLongPolynomial2);
    if (localLongPolynomial21.coeffs.length > i)
    {
      if (this.numCoeffs % 2 != 0) {
        break label146;
      }
      for (int n = i; n < localLongPolynomial21.coeffs.length; n++) {
        localLongPolynomial21.coeffs[(n - i)] = (0xFF0007FF & localLongPolynomial21.coeffs[(n - i)] + localLongPolynomial21.coeffs[n]);
      }
      localLongPolynomial21.coeffs = Arrays.copyOf(localLongPolynomial21.coeffs, i);
    }
    for (;;)
    {
      LongPolynomial2 localLongPolynomial22 = new LongPolynomial2(localLongPolynomial21.coeffs);
      localLongPolynomial22.numCoeffs = this.numCoeffs;
      return localLongPolynomial22;
      label146:
      for (int j = i; j < localLongPolynomial21.coeffs.length; j++)
      {
        localLongPolynomial21.coeffs[(j - i)] += (localLongPolynomial21.coeffs[(j - 1)] >> 24);
        localLongPolynomial21.coeffs[(j - i)] += ((0x7FF & localLongPolynomial21.coeffs[j]) << 24);
        long[] arrayOfLong2 = localLongPolynomial21.coeffs;
        int m = j - i;
        arrayOfLong2[m] = (0xFF0007FF & arrayOfLong2[m]);
      }
      localLongPolynomial21.coeffs = Arrays.copyOf(localLongPolynomial21.coeffs, i);
      long[] arrayOfLong1 = localLongPolynomial21.coeffs;
      int k = -1 + localLongPolynomial21.coeffs.length;
      arrayOfLong1[k] = (0x7FF & arrayOfLong1[k]);
    }
  }
  
  public void mult2And(int paramInt)
  {
    long l = (paramInt << 24) + paramInt;
    for (int i = 0; i < this.coeffs.length; i++) {
      this.coeffs[i] = (l & this.coeffs[i] << 1);
    }
  }
  
  public void subAnd(LongPolynomial2 paramLongPolynomial2, int paramInt)
  {
    long l = (paramInt << 24) + paramInt;
    for (int i = 0; i < paramLongPolynomial2.coeffs.length; i++) {
      this.coeffs[i] = (l & 140737496743936L + this.coeffs[i] - paramLongPolynomial2.coeffs[i]);
    }
  }
  
  public IntegerPolynomial toIntegerPolynomial()
  {
    int[] arrayOfInt = new int[this.numCoeffs];
    int i = 0;
    int j = 0;
    int k;
    if (j < this.coeffs.length)
    {
      k = i + 1;
      arrayOfInt[i] = ((int)(0x7FF & this.coeffs[j]));
      if (k >= this.numCoeffs) {
        break label86;
      }
      i = k + 1;
      arrayOfInt[k] = ((int)(0x7FF & this.coeffs[j] >> 24));
    }
    for (;;)
    {
      j++;
      break;
      return new IntegerPolynomial(arrayOfInt);
      label86:
      i = k;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.LongPolynomial2
 * JD-Core Version:    0.7.0.1
 */