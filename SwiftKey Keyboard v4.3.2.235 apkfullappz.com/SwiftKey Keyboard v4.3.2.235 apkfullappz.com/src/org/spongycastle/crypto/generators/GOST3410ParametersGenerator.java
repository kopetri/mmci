package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410ValidationParameters;

public class GOST3410ParametersGenerator
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  private SecureRandom init_random;
  private int size;
  private int typeproc;
  
  private int procedure_A(int paramInt1, int paramInt2, BigInteger[] paramArrayOfBigInteger, int paramInt3)
  {
    while ((paramInt1 < 0) || (paramInt1 > 65536)) {
      paramInt1 = this.init_random.nextInt() / 32768;
    }
    while ((paramInt2 < 0) || (paramInt2 > 65536) || (paramInt2 / 2 == 0)) {
      paramInt2 = 1 + this.init_random.nextInt() / 32768;
    }
    BigInteger localBigInteger1 = new BigInteger(Integer.toString(paramInt2));
    BigInteger localBigInteger2 = new BigInteger("19381");
    BigInteger[] arrayOfBigInteger1 = new BigInteger[1];
    arrayOfBigInteger1[0] = new BigInteger(Integer.toString(paramInt1));
    int[] arrayOfInt1 = new int[1];
    arrayOfInt1[0] = paramInt3;
    int i = 0;
    for (int j = 0; arrayOfInt1[j] >= 17; j++)
    {
      int[] arrayOfInt2 = new int[1 + arrayOfInt1.length];
      int i6 = arrayOfInt1.length;
      System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, i6);
      arrayOfInt1 = new int[arrayOfInt2.length];
      int i7 = arrayOfInt2.length;
      System.arraycopy(arrayOfInt2, 0, arrayOfInt1, 0, i7);
      arrayOfInt1[(j + 1)] = (arrayOfInt1[j] / 2);
      i = j + 1;
    }
    BigInteger[] arrayOfBigInteger2 = new BigInteger[i + 1];
    arrayOfBigInteger2[i] = new BigInteger("8003", 16);
    int k = i - 1;
    label676:
    for (int m = 0; m < i; m++)
    {
      int n = arrayOfInt1[k] / 16;
      BigInteger[] arrayOfBigInteger3 = new BigInteger[arrayOfBigInteger1.length];
      int i1 = arrayOfBigInteger1.length;
      System.arraycopy(arrayOfBigInteger1, 0, arrayOfBigInteger3, 0, i1);
      arrayOfBigInteger1 = new BigInteger[n + 1];
      int i2 = arrayOfBigInteger3.length;
      System.arraycopy(arrayOfBigInteger3, 0, arrayOfBigInteger1, 0, i2);
      for (int i3 = 0; i3 < n; i3++) {
        arrayOfBigInteger1[(i3 + 1)] = arrayOfBigInteger1[i3].multiply(localBigInteger2).add(localBigInteger1).mod(TWO.pow(16));
      }
      BigInteger localBigInteger3 = new BigInteger("0");
      for (int i4 = 0; i4 < n; i4++) {
        localBigInteger3 = localBigInteger3.add(arrayOfBigInteger1[i4].multiply(TWO.pow(i4 * 16)));
      }
      arrayOfBigInteger1[0] = arrayOfBigInteger1[n];
      BigInteger localBigInteger4 = TWO.pow(-1 + arrayOfInt1[k]).divide(arrayOfBigInteger2[(k + 1)]).add(TWO.pow(-1 + arrayOfInt1[k]).multiply(localBigInteger3).divide(arrayOfBigInteger2[(k + 1)].multiply(TWO.pow(n * 16))));
      if (localBigInteger4.mod(TWO).compareTo(ONE) == 0) {
        localBigInteger4 = localBigInteger4.add(ONE);
      }
      for (int i5 = 0;; i5 += 2)
      {
        arrayOfBigInteger2[k] = arrayOfBigInteger2[(k + 1)].multiply(localBigInteger4.add(BigInteger.valueOf(i5))).add(ONE);
        if (arrayOfBigInteger2[k].compareTo(TWO.pow(arrayOfInt1[k])) == 1) {
          break;
        }
        if ((TWO.modPow(arrayOfBigInteger2[(k + 1)].multiply(localBigInteger4.add(BigInteger.valueOf(i5))), arrayOfBigInteger2[k]).compareTo(ONE) == 0) && (TWO.modPow(localBigInteger4.add(BigInteger.valueOf(i5)), arrayOfBigInteger2[k]).compareTo(ONE) != 0))
        {
          k--;
          if (k >= 0) {
            break label676;
          }
          paramArrayOfBigInteger[0] = arrayOfBigInteger2[0];
          paramArrayOfBigInteger[1] = arrayOfBigInteger2[1];
          return arrayOfBigInteger1[0].intValue();
        }
      }
    }
    return arrayOfBigInteger1[0].intValue();
  }
  
  private long procedure_Aa(long paramLong1, long paramLong2, BigInteger[] paramArrayOfBigInteger, int paramInt)
  {
    while ((paramLong1 < 0L) || (paramLong1 > 4294967296L)) {
      paramLong1 = 2 * this.init_random.nextInt();
    }
    while ((paramLong2 < 0L) || (paramLong2 > 4294967296L) || (paramLong2 / 2L == 0L)) {
      paramLong2 = 1 + 2 * this.init_random.nextInt();
    }
    BigInteger localBigInteger1 = new BigInteger(Long.toString(paramLong2));
    BigInteger localBigInteger2 = new BigInteger("97781173");
    BigInteger[] arrayOfBigInteger1 = new BigInteger[1];
    arrayOfBigInteger1[0] = new BigInteger(Long.toString(paramLong1));
    int[] arrayOfInt1 = new int[1];
    arrayOfInt1[0] = paramInt;
    int i = 0;
    for (int j = 0; arrayOfInt1[j] >= 33; j++)
    {
      int[] arrayOfInt2 = new int[1 + arrayOfInt1.length];
      int i6 = arrayOfInt1.length;
      System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, i6);
      arrayOfInt1 = new int[arrayOfInt2.length];
      int i7 = arrayOfInt2.length;
      System.arraycopy(arrayOfInt2, 0, arrayOfInt1, 0, i7);
      arrayOfInt1[(j + 1)] = (arrayOfInt1[j] / 2);
      i = j + 1;
    }
    BigInteger[] arrayOfBigInteger2 = new BigInteger[i + 1];
    arrayOfBigInteger2[i] = new BigInteger("8000000B", 16);
    int k = i - 1;
    label690:
    for (int m = 0; m < i; m++)
    {
      int n = arrayOfInt1[k] / 32;
      BigInteger[] arrayOfBigInteger3 = new BigInteger[arrayOfBigInteger1.length];
      int i1 = arrayOfBigInteger1.length;
      System.arraycopy(arrayOfBigInteger1, 0, arrayOfBigInteger3, 0, i1);
      arrayOfBigInteger1 = new BigInteger[n + 1];
      int i2 = arrayOfBigInteger3.length;
      System.arraycopy(arrayOfBigInteger3, 0, arrayOfBigInteger1, 0, i2);
      for (int i3 = 0; i3 < n; i3++) {
        arrayOfBigInteger1[(i3 + 1)] = arrayOfBigInteger1[i3].multiply(localBigInteger2).add(localBigInteger1).mod(TWO.pow(32));
      }
      BigInteger localBigInteger3 = new BigInteger("0");
      for (int i4 = 0; i4 < n; i4++) {
        localBigInteger3 = localBigInteger3.add(arrayOfBigInteger1[i4].multiply(TWO.pow(i4 * 32)));
      }
      arrayOfBigInteger1[0] = arrayOfBigInteger1[n];
      BigInteger localBigInteger4 = TWO.pow(-1 + arrayOfInt1[k]).divide(arrayOfBigInteger2[(k + 1)]).add(TWO.pow(-1 + arrayOfInt1[k]).multiply(localBigInteger3).divide(arrayOfBigInteger2[(k + 1)].multiply(TWO.pow(n * 32))));
      if (localBigInteger4.mod(TWO).compareTo(ONE) == 0) {
        localBigInteger4 = localBigInteger4.add(ONE);
      }
      for (int i5 = 0;; i5 += 2)
      {
        arrayOfBigInteger2[k] = arrayOfBigInteger2[(k + 1)].multiply(localBigInteger4.add(BigInteger.valueOf(i5))).add(ONE);
        if (arrayOfBigInteger2[k].compareTo(TWO.pow(arrayOfInt1[k])) == 1) {
          break;
        }
        if ((TWO.modPow(arrayOfBigInteger2[(k + 1)].multiply(localBigInteger4.add(BigInteger.valueOf(i5))), arrayOfBigInteger2[k]).compareTo(ONE) == 0) && (TWO.modPow(localBigInteger4.add(BigInteger.valueOf(i5)), arrayOfBigInteger2[k]).compareTo(ONE) != 0))
        {
          k--;
          if (k >= 0) {
            break label690;
          }
          paramArrayOfBigInteger[0] = arrayOfBigInteger2[0];
          paramArrayOfBigInteger[1] = arrayOfBigInteger2[1];
          return arrayOfBigInteger1[0].longValue();
        }
      }
    }
    return arrayOfBigInteger1[0].longValue();
  }
  
  private void procedure_B(int paramInt1, int paramInt2, BigInteger[] paramArrayOfBigInteger)
  {
    while ((paramInt1 < 0) || (paramInt1 > 65536)) {
      paramInt1 = this.init_random.nextInt() / 32768;
    }
    while ((paramInt2 < 0) || (paramInt2 > 65536) || (paramInt2 / 2 == 0)) {
      paramInt2 = 1 + this.init_random.nextInt() / 32768;
    }
    BigInteger[] arrayOfBigInteger1 = new BigInteger[2];
    BigInteger localBigInteger1 = new BigInteger(Integer.toString(paramInt2));
    BigInteger localBigInteger2 = new BigInteger("19381");
    int i = procedure_A(paramInt1, paramInt2, arrayOfBigInteger1, 256);
    BigInteger localBigInteger3 = arrayOfBigInteger1[0];
    int j = procedure_A(i, paramInt2, arrayOfBigInteger1, 512);
    BigInteger localBigInteger4 = arrayOfBigInteger1[0];
    BigInteger[] arrayOfBigInteger2 = new BigInteger[65];
    arrayOfBigInteger2[0] = new BigInteger(Integer.toString(j));
    for (int k = 0; k < 64; k++) {
      arrayOfBigInteger2[(k + 1)] = arrayOfBigInteger2[k].multiply(localBigInteger2).add(localBigInteger1).mod(TWO.pow(16));
    }
    BigInteger localBigInteger5 = new BigInteger("0");
    for (int m = 0; m < 64; m++) {
      localBigInteger5 = localBigInteger5.add(arrayOfBigInteger2[m].multiply(TWO.pow(m * 16)));
    }
    arrayOfBigInteger2[0] = arrayOfBigInteger2[64];
    BigInteger localBigInteger6 = TWO.pow(1023).divide(localBigInteger3.multiply(localBigInteger4)).add(TWO.pow(1023).multiply(localBigInteger5).divide(localBigInteger3.multiply(localBigInteger4).multiply(TWO.pow(1024))));
    if (localBigInteger6.mod(TWO).compareTo(ONE) == 0) {
      localBigInteger6 = localBigInteger6.add(ONE);
    }
    for (int n = 0;; n += 2)
    {
      BigInteger localBigInteger7 = localBigInteger3.multiply(localBigInteger4).multiply(localBigInteger6.add(BigInteger.valueOf(n))).add(ONE);
      if (localBigInteger7.compareTo(TWO.pow(1024)) == 1) {
        break;
      }
      if ((TWO.modPow(localBigInteger3.multiply(localBigInteger4).multiply(localBigInteger6.add(BigInteger.valueOf(n))), localBigInteger7).compareTo(ONE) == 0) && (TWO.modPow(localBigInteger3.multiply(localBigInteger6.add(BigInteger.valueOf(n))), localBigInteger7).compareTo(ONE) != 0))
      {
        paramArrayOfBigInteger[0] = localBigInteger7;
        paramArrayOfBigInteger[1] = localBigInteger3;
        return;
      }
    }
  }
  
  private void procedure_Bb(long paramLong1, long paramLong2, BigInteger[] paramArrayOfBigInteger)
  {
    while ((paramLong1 < 0L) || (paramLong1 > 4294967296L)) {
      paramLong1 = 2 * this.init_random.nextInt();
    }
    while ((paramLong2 < 0L) || (paramLong2 > 4294967296L) || (paramLong2 / 2L == 0L)) {
      paramLong2 = 1 + 2 * this.init_random.nextInt();
    }
    BigInteger[] arrayOfBigInteger1 = new BigInteger[2];
    BigInteger localBigInteger1 = new BigInteger(Long.toString(paramLong2));
    BigInteger localBigInteger2 = new BigInteger("97781173");
    long l1 = procedure_Aa(paramLong1, paramLong2, arrayOfBigInteger1, 256);
    BigInteger localBigInteger3 = arrayOfBigInteger1[0];
    long l2 = procedure_Aa(l1, paramLong2, arrayOfBigInteger1, 512);
    BigInteger localBigInteger4 = arrayOfBigInteger1[0];
    BigInteger[] arrayOfBigInteger2 = new BigInteger[33];
    arrayOfBigInteger2[0] = new BigInteger(Long.toString(l2));
    for (int i = 0; i < 32; i++) {
      arrayOfBigInteger2[(i + 1)] = arrayOfBigInteger2[i].multiply(localBigInteger2).add(localBigInteger1).mod(TWO.pow(32));
    }
    BigInteger localBigInteger5 = new BigInteger("0");
    for (int j = 0; j < 32; j++) {
      localBigInteger5 = localBigInteger5.add(arrayOfBigInteger2[j].multiply(TWO.pow(j * 32)));
    }
    arrayOfBigInteger2[0] = arrayOfBigInteger2[32];
    BigInteger localBigInteger6 = TWO.pow(1023).divide(localBigInteger3.multiply(localBigInteger4)).add(TWO.pow(1023).multiply(localBigInteger5).divide(localBigInteger3.multiply(localBigInteger4).multiply(TWO.pow(1024))));
    if (localBigInteger6.mod(TWO).compareTo(ONE) == 0) {
      localBigInteger6 = localBigInteger6.add(ONE);
    }
    for (int k = 0;; k += 2)
    {
      BigInteger localBigInteger7 = localBigInteger3.multiply(localBigInteger4).multiply(localBigInteger6.add(BigInteger.valueOf(k))).add(ONE);
      if (localBigInteger7.compareTo(TWO.pow(1024)) == 1) {
        break;
      }
      if ((TWO.modPow(localBigInteger3.multiply(localBigInteger4).multiply(localBigInteger6.add(BigInteger.valueOf(k))), localBigInteger7).compareTo(ONE) == 0) && (TWO.modPow(localBigInteger3.multiply(localBigInteger6.add(BigInteger.valueOf(k))), localBigInteger7).compareTo(ONE) != 0))
      {
        paramArrayOfBigInteger[0] = localBigInteger7;
        paramArrayOfBigInteger[1] = localBigInteger3;
        return;
      }
    }
  }
  
  private BigInteger procedure_C(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    BigInteger localBigInteger1 = paramBigInteger1.subtract(ONE);
    BigInteger localBigInteger2 = localBigInteger1.divide(paramBigInteger2);
    int i = paramBigInteger1.bitLength();
    BigInteger localBigInteger4;
    do
    {
      BigInteger localBigInteger3;
      do
      {
        localBigInteger3 = new BigInteger(i, this.init_random);
      } while ((localBigInteger3.compareTo(ONE) <= 0) || (localBigInteger3.compareTo(localBigInteger1) >= 0));
      localBigInteger4 = localBigInteger3.modPow(localBigInteger2, paramBigInteger1);
    } while (localBigInteger4.compareTo(ONE) == 0);
    return localBigInteger4;
  }
  
  public GOST3410Parameters generateParameters()
  {
    BigInteger[] arrayOfBigInteger = new BigInteger[2];
    if (this.typeproc == 1)
    {
      int i = this.init_random.nextInt();
      int j = this.init_random.nextInt();
      switch (this.size)
      {
      default: 
        throw new IllegalArgumentException("Ooops! key size 512 or 1024 bit.");
      case 512: 
        procedure_A(i, j, arrayOfBigInteger, 512);
      }
      for (;;)
      {
        BigInteger localBigInteger3 = arrayOfBigInteger[0];
        BigInteger localBigInteger4 = arrayOfBigInteger[1];
        return new GOST3410Parameters(localBigInteger3, localBigInteger4, procedure_C(localBigInteger3, localBigInteger4), new GOST3410ValidationParameters(i, j));
        procedure_B(i, j, arrayOfBigInteger);
      }
    }
    long l1 = this.init_random.nextLong();
    long l2 = this.init_random.nextLong();
    switch (this.size)
    {
    default: 
      throw new IllegalStateException("Ooops! key size 512 or 1024 bit.");
    case 512: 
      procedure_Aa(l1, l2, arrayOfBigInteger, 512);
    }
    for (;;)
    {
      BigInteger localBigInteger1 = arrayOfBigInteger[0];
      BigInteger localBigInteger2 = arrayOfBigInteger[1];
      return new GOST3410Parameters(localBigInteger1, localBigInteger2, procedure_C(localBigInteger1, localBigInteger2), new GOST3410ValidationParameters(l1, l2));
      procedure_Bb(l1, l2, arrayOfBigInteger);
    }
  }
  
  public void init(int paramInt1, int paramInt2, SecureRandom paramSecureRandom)
  {
    this.size = paramInt1;
    this.typeproc = paramInt2;
    this.init_random = paramSecureRandom;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.GOST3410ParametersGenerator
 * JD-Core Version:    0.7.0.1
 */