package org.spongycastle.math.ntru.polynomial;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.math.ntru.util.ArrayEncoder;
import org.spongycastle.math.ntru.util.Util;
import org.spongycastle.util.Arrays;

public class SparseTernaryPolynomial
  implements TernaryPolynomial
{
  private static final int BITS_PER_INDEX = 11;
  private int N;
  private int[] negOnes;
  private int[] ones;
  
  SparseTernaryPolynomial(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    this.N = paramInt;
    this.ones = paramArrayOfInt1;
    this.negOnes = paramArrayOfInt2;
  }
  
  public SparseTernaryPolynomial(IntegerPolynomial paramIntegerPolynomial)
  {
    this(paramIntegerPolynomial.coeffs);
  }
  
  public SparseTernaryPolynomial(int[] paramArrayOfInt)
  {
    this.N = paramArrayOfInt.length;
    this.ones = new int[this.N];
    this.negOnes = new int[this.N];
    int i = 0;
    int j = 0;
    int k = 0;
    if (k < this.N)
    {
      int m = paramArrayOfInt[k];
      switch (m)
      {
      default: 
        throw new IllegalArgumentException("Illegal value: " + m + ", must be one of {-1, 0, 1}");
      case 1: 
        int[] arrayOfInt2 = this.ones;
        int i1 = i + 1;
        arrayOfInt2[i] = k;
        i = i1;
      }
      for (;;)
      {
        k++;
        break;
        int[] arrayOfInt1 = this.negOnes;
        int n = j + 1;
        arrayOfInt1[j] = k;
        j = n;
      }
    }
    this.ones = Arrays.copyOf(this.ones, i);
    this.negOnes = Arrays.copyOf(this.negOnes, j);
  }
  
  public static SparseTernaryPolynomial fromBinary(InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    int i = 32 - Integer.numberOfLeadingZeros(2047);
    return new SparseTernaryPolynomial(paramInt1, ArrayEncoder.decodeModQ(Util.readFullLength(paramInputStream, (7 + paramInt2 * i) / 8), paramInt2, 2048), ArrayEncoder.decodeModQ(Util.readFullLength(paramInputStream, (7 + paramInt3 * i) / 8), paramInt3, 2048));
  }
  
  public static SparseTernaryPolynomial generateRandom(int paramInt1, int paramInt2, int paramInt3, SecureRandom paramSecureRandom)
  {
    return new SparseTernaryPolynomial(Util.generateRandomTernary(paramInt1, paramInt2, paramInt3, paramSecureRandom));
  }
  
  public void clear()
  {
    for (int i = 0; i < this.ones.length; i++) {
      this.ones[i] = 0;
    }
    for (int j = 0; j < this.negOnes.length; j++) {
      this.negOnes[j] = 0;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    SparseTernaryPolynomial localSparseTernaryPolynomial;
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      localSparseTernaryPolynomial = (SparseTernaryPolynomial)paramObject;
      if (this.N != localSparseTernaryPolynomial.N) {
        return false;
      }
      if (!Arrays.areEqual(this.negOnes, localSparseTernaryPolynomial.negOnes)) {
        return false;
      }
    } while (Arrays.areEqual(this.ones, localSparseTernaryPolynomial.ones));
    return false;
  }
  
  public int[] getNegOnes()
  {
    return this.negOnes;
  }
  
  public int[] getOnes()
  {
    return this.ones;
  }
  
  public int hashCode()
  {
    return 31 * (31 * (31 + this.N) + Arrays.hashCode(this.negOnes)) + Arrays.hashCode(this.ones);
  }
  
  public BigIntPolynomial mult(BigIntPolynomial paramBigIntPolynomial)
  {
    BigInteger[] arrayOfBigInteger1 = paramBigIntPolynomial.coeffs;
    if (arrayOfBigInteger1.length != this.N) {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    BigInteger[] arrayOfBigInteger2 = new BigInteger[this.N];
    for (int i = 0; i < this.N; i++) {
      arrayOfBigInteger2[i] = BigInteger.ZERO;
    }
    for (int j = 0; j != this.ones.length; j++)
    {
      int i2 = this.ones[j];
      int i3 = -1 + this.N - i2;
      for (int i4 = -1 + this.N; i4 >= 0; i4--)
      {
        arrayOfBigInteger2[i4] = arrayOfBigInteger2[i4].add(arrayOfBigInteger1[i3]);
        i3--;
        if (i3 < 0) {
          i3 = -1 + this.N;
        }
      }
    }
    for (int k = 0; k != this.negOnes.length; k++)
    {
      int m = this.negOnes[k];
      int n = -1 + this.N - m;
      for (int i1 = -1 + this.N; i1 >= 0; i1--)
      {
        arrayOfBigInteger2[i1] = arrayOfBigInteger2[i1].subtract(arrayOfBigInteger1[n]);
        n--;
        if (n < 0) {
          n = -1 + this.N;
        }
      }
    }
    return new BigIntPolynomial(arrayOfBigInteger2);
  }
  
  public IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial)
  {
    int[] arrayOfInt1 = paramIntegerPolynomial.coeffs;
    if (arrayOfInt1.length != this.N) {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    int[] arrayOfInt2 = new int[this.N];
    for (int i = 0; i != this.ones.length; i++)
    {
      int i1 = this.ones[i];
      int i2 = -1 + this.N - i1;
      for (int i3 = -1 + this.N; i3 >= 0; i3--)
      {
        arrayOfInt2[i3] += arrayOfInt1[i2];
        i2--;
        if (i2 < 0) {
          i2 = -1 + this.N;
        }
      }
    }
    for (int j = 0; j != this.negOnes.length; j++)
    {
      int k = this.negOnes[j];
      int m = -1 + this.N - k;
      for (int n = -1 + this.N; n >= 0; n--)
      {
        arrayOfInt2[n] -= arrayOfInt1[m];
        m--;
        if (m < 0) {
          m = -1 + this.N;
        }
      }
    }
    return new IntegerPolynomial(arrayOfInt2);
  }
  
  public IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial, int paramInt)
  {
    IntegerPolynomial localIntegerPolynomial = mult(paramIntegerPolynomial);
    localIntegerPolynomial.mod(paramInt);
    return localIntegerPolynomial;
  }
  
  public int size()
  {
    return this.N;
  }
  
  public byte[] toBinary()
  {
    byte[] arrayOfByte1 = ArrayEncoder.encodeModQ(this.ones, 2048);
    byte[] arrayOfByte2 = ArrayEncoder.encodeModQ(this.negOnes, 2048);
    byte[] arrayOfByte3 = Arrays.copyOf(arrayOfByte1, arrayOfByte1.length + arrayOfByte2.length);
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length, arrayOfByte2.length);
    return arrayOfByte3;
  }
  
  public IntegerPolynomial toIntegerPolynomial()
  {
    int[] arrayOfInt = new int[this.N];
    for (int i = 0; i != this.ones.length; i++) {
      arrayOfInt[this.ones[i]] = 1;
    }
    for (int j = 0; j != this.negOnes.length; j++) {
      arrayOfInt[this.negOnes[j]] = -1;
    }
    return new IntegerPolynomial(arrayOfInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.SparseTernaryPolynomial
 * JD-Core Version:    0.7.0.1
 */