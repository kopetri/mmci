package org.spongycastle.math.ec;

import java.math.BigInteger;
import org.spongycastle.util.Arrays;

class IntArray
{
  private int[] m_ints;
  
  public IntArray(int paramInt)
  {
    this.m_ints = new int[paramInt];
  }
  
  public IntArray(BigInteger paramBigInteger)
  {
    this(paramBigInteger, 0);
  }
  
  public IntArray(BigInteger paramBigInteger, int paramInt)
  {
    if (paramBigInteger.signum() == -1) {
      throw new IllegalArgumentException("Only positive Integers allowed");
    }
    if (paramBigInteger.equals(ECConstants.ZERO)) {
      this.m_ints = new int[] { 0 };
    }
    for (;;)
    {
      return;
      byte[] arrayOfByte = paramBigInteger.toByteArray();
      int i = arrayOfByte.length;
      int j = arrayOfByte[0];
      int k = 0;
      if (j == 0)
      {
        i--;
        k = 1;
      }
      int m = (i + 3) / 4;
      if (m < paramInt) {}
      int i2;
      int i3;
      for (this.m_ints = new int[paramInt];; this.m_ints = new int[m])
      {
        n = m - 1;
        int i1 = k + i % 4;
        i2 = k;
        i3 = 0;
        if (k >= i1) {
          break label200;
        }
        while (i2 < i1)
        {
          int i11 = i3 << 8;
          int i12 = arrayOfByte[i2];
          if (i12 < 0) {
            i12 += 256;
          }
          i3 = i11 | i12;
          i2++;
        }
      }
      int[] arrayOfInt = this.m_ints;
      int i10 = n - 1;
      arrayOfInt[n] = i3;
      int n = i10;
      label200:
      while (n >= 0)
      {
        int i4 = 0;
        int i5 = 0;
        int i8;
        for (int i6 = i2; i5 < 4; i6 = i8)
        {
          int i7 = i4 << 8;
          i8 = i6 + 1;
          int i9 = arrayOfByte[i6];
          if (i9 < 0) {
            i9 += 256;
          }
          i4 = i7 | i9;
          i5++;
        }
        this.m_ints[n] = i4;
        n--;
        i2 = i6;
      }
    }
  }
  
  public IntArray(int[] paramArrayOfInt)
  {
    this.m_ints = paramArrayOfInt;
  }
  
  private int[] resizedInts(int paramInt)
  {
    int[] arrayOfInt = new int[paramInt];
    int i = this.m_ints.length;
    if (i < paramInt) {}
    for (int j = i;; j = paramInt)
    {
      System.arraycopy(this.m_ints, 0, arrayOfInt, 0, j);
      return arrayOfInt;
    }
  }
  
  public void addShifted(IntArray paramIntArray, int paramInt)
  {
    int i = paramIntArray.getUsedLength();
    int j = i + paramInt;
    if (j > this.m_ints.length) {
      this.m_ints = resizedInts(j);
    }
    for (int k = 0; k < i; k++)
    {
      int[] arrayOfInt = this.m_ints;
      int m = k + paramInt;
      arrayOfInt[m] ^= paramIntArray.m_ints[k];
    }
  }
  
  public int bitLength()
  {
    int i = getUsedLength();
    int m;
    if (i == 0) {
      m = 0;
    }
    for (;;)
    {
      return m;
      int j = i - 1;
      int k = this.m_ints[j];
      m = 1 + (j << 5);
      if ((0xFFFF0000 & k) != 0) {
        if ((0xFF000000 & k) != 0)
        {
          m += 24;
          k >>>= 24;
        }
      }
      while (k != 1)
      {
        m++;
        k >>>= 1;
        continue;
        m += 16;
        k >>>= 16;
        continue;
        if (k > 255)
        {
          m += 8;
          k >>>= 8;
        }
      }
    }
  }
  
  public Object clone()
  {
    return new IntArray(Arrays.clone(this.m_ints));
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof IntArray)) {}
    IntArray localIntArray;
    int i;
    do
    {
      return false;
      localIntArray = (IntArray)paramObject;
      i = getUsedLength();
    } while (localIntArray.getUsedLength() != i);
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label59;
      }
      if (this.m_ints[j] != localIntArray.m_ints[j]) {
        break;
      }
    }
    label59:
    return true;
  }
  
  public void flipBit(int paramInt)
  {
    int i = paramInt >> 5;
    int j = 1 << (paramInt & 0x1F);
    int[] arrayOfInt = this.m_ints;
    arrayOfInt[i] = (j ^ arrayOfInt[i]);
  }
  
  public int getLength()
  {
    return this.m_ints.length;
  }
  
  public int getUsedLength()
  {
    int i = this.m_ints.length;
    if (i <= 0) {
      return 0;
    }
    if (this.m_ints[0] != 0)
    {
      int[] arrayOfInt2;
      do
      {
        arrayOfInt2 = this.m_ints;
        i--;
      } while (arrayOfInt2[i] == 0);
      return i + 1;
    }
    do
    {
      int[] arrayOfInt1 = this.m_ints;
      i--;
      if (arrayOfInt1[i] != 0) {
        return i + 1;
      }
    } while (i > 0);
    return 0;
  }
  
  public int hashCode()
  {
    int i = getUsedLength();
    int j = 1;
    for (int k = 0; k < i; k++) {
      j = j * 31 + this.m_ints[k];
    }
    return j;
  }
  
  public boolean isZero()
  {
    boolean bool;
    if (this.m_ints.length != 0)
    {
      int i = this.m_ints[0];
      bool = false;
      if (i == 0)
      {
        int j = getUsedLength();
        bool = false;
        if (j != 0) {}
      }
    }
    else
    {
      bool = true;
    }
    return bool;
  }
  
  public IntArray multiply(IntArray paramIntArray, int paramInt)
  {
    int i = paramInt + 31 >> 5;
    if (this.m_ints.length < i) {
      this.m_ints = resizedInts(i);
    }
    IntArray localIntArray1 = new IntArray(paramIntArray.resizedInts(1 + paramIntArray.getLength()));
    IntArray localIntArray2 = new IntArray(31 + (paramInt + paramInt) >> 5);
    int j = 1;
    for (int k = 0; k < 32; k++)
    {
      for (int m = 0; m < i; m++) {
        if ((j & this.m_ints[m]) != 0) {
          localIntArray2.addShifted(localIntArray1, m);
        }
      }
      j <<= 1;
      localIntArray1.shiftLeft();
    }
    return localIntArray2;
  }
  
  public void reduce(int paramInt, int[] paramArrayOfInt)
  {
    for (int i = -2 + (paramInt + paramInt); i >= paramInt; i--) {
      if (testBit(i))
      {
        int j = i - paramInt;
        flipBit(j);
        flipBit(i);
        int k = paramArrayOfInt.length;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          flipBit(j + paramArrayOfInt[k]);
        }
      }
    }
    this.m_ints = resizedInts(paramInt + 31 >> 5);
  }
  
  public void setBit(int paramInt)
  {
    int i = paramInt >> 5;
    int j = 1 << (paramInt & 0x1F);
    int[] arrayOfInt = this.m_ints;
    arrayOfInt[i] = (j | arrayOfInt[i]);
  }
  
  public IntArray shiftLeft(int paramInt)
  {
    int i = getUsedLength();
    if (i == 0) {}
    while (paramInt == 0) {
      return this;
    }
    if (paramInt > 31) {
      throw new IllegalArgumentException("shiftLeft() for max 31 bits , " + paramInt + "bit shift is not possible");
    }
    int[] arrayOfInt = new int[i + 1];
    int j = 32 - paramInt;
    arrayOfInt[0] = (this.m_ints[0] << paramInt);
    for (int k = 1; k < i; k++) {
      arrayOfInt[k] = (this.m_ints[k] << paramInt | this.m_ints[(k - 1)] >>> j);
    }
    arrayOfInt[i] = (this.m_ints[(i - 1)] >>> j);
    return new IntArray(arrayOfInt);
  }
  
  public void shiftLeft()
  {
    int i = getUsedLength();
    if (i == 0) {
      return;
    }
    if (this.m_ints[(i - 1)] < 0)
    {
      i++;
      if (i > this.m_ints.length) {
        this.m_ints = resizedInts(1 + this.m_ints.length);
      }
    }
    int j = 0;
    int k = 0;
    label52:
    if (k < i) {
      if (this.m_ints[k] >= 0) {
        break label114;
      }
    }
    label114:
    for (int m = 1;; m = 0)
    {
      int[] arrayOfInt1 = this.m_ints;
      arrayOfInt1[k] <<= 1;
      if (j != 0)
      {
        int[] arrayOfInt2 = this.m_ints;
        arrayOfInt2[k] = (0x1 | arrayOfInt2[k]);
      }
      j = m;
      k++;
      break label52;
      break;
    }
  }
  
  public IntArray square(int paramInt)
  {
    int[] arrayOfInt = { 0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85 };
    int i = paramInt + 31 >> 5;
    if (this.m_ints.length < i) {
      this.m_ints = resizedInts(i);
    }
    IntArray localIntArray = new IntArray(i + i);
    for (int j = 0; j < i; j++)
    {
      int k = 0;
      for (int m = 0; m < 4; m++) {
        k = k >>> 8 | arrayOfInt[(0xF & this.m_ints[j] >>> m * 4)] << 24;
      }
      localIntArray.m_ints[(j + j)] = k;
      int n = 0;
      int i1 = this.m_ints[j] >>> 16;
      for (int i2 = 0; i2 < 4; i2++) {
        n = n >>> 8 | arrayOfInt[(0xF & i1 >>> i2 * 4)] << 24;
      }
      localIntArray.m_ints[(1 + (j + j))] = n;
    }
    return localIntArray;
  }
  
  public boolean testBit(int paramInt)
  {
    int i = paramInt >> 5;
    return (1 << (paramInt & 0x1F) & this.m_ints[i]) != 0;
  }
  
  public BigInteger toBigInteger()
  {
    int i = getUsedLength();
    if (i == 0) {
      return ECConstants.ZERO;
    }
    int j = this.m_ints[(i - 1)];
    byte[] arrayOfByte1 = new byte[4];
    int k = 0;
    int m = 3;
    int n = 0;
    int i8;
    if (m >= 0)
    {
      int i7 = (byte)(j >>> m * 8);
      if ((k == 0) && (i7 == 0)) {
        break label204;
      }
      k = 1;
      i8 = n + 1;
      arrayOfByte1[n] = i7;
    }
    for (;;)
    {
      m--;
      n = i8;
      break;
      byte[] arrayOfByte2 = new byte[n + 4 * (i - 1)];
      for (int i1 = 0; i1 < n; i1++) {
        arrayOfByte2[i1] = arrayOfByte1[i1];
      }
      int i2 = i - 2;
      int i5;
      for (int i3 = n; i2 >= 0; i3 = i5)
      {
        int i4 = 3;
        int i6;
        for (i5 = i3; i4 >= 0; i5 = i6)
        {
          i6 = i5 + 1;
          arrayOfByte2[i5] = ((byte)(this.m_ints[i2] >>> i4 * 8));
          i4--;
        }
        i2--;
      }
      return new BigInteger(1, arrayOfByte2);
      label204:
      i8 = n;
    }
  }
  
  public String toString()
  {
    int i = getUsedLength();
    if (i == 0) {
      return "0";
    }
    StringBuffer localStringBuffer = new StringBuffer(Integer.toBinaryString(this.m_ints[(i - 1)]));
    for (int j = i - 2; j >= 0; j--)
    {
      String str = Integer.toBinaryString(this.m_ints[j]);
      for (int k = str.length(); k < 8; k++) {
        str = "0" + str;
      }
      localStringBuffer.append(str);
    }
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ec.IntArray
 * JD-Core Version:    0.7.0.1
 */