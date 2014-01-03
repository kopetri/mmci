package org.spongycastle.math.ntru.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;

public class ArrayEncoder
{
  private static final int[] BIT1_TABLE = { 1, 1, 1, 0, 0, 0, 1, 0, 1 };
  private static final int[] BIT2_TABLE = { 1, 1, 1, 1, 0, 0, 0, 1, 0 };
  private static final int[] BIT3_TABLE = { 1, 0, 1, 0, 0, 1, 1, 1, 0 };
  private static final int[] COEFF1_TABLE = { 0, 0, 0, 1, 1, 1, -1, -1 };
  private static final int[] COEFF2_TABLE = { 0, 1, -1, 0, 1, -1, 0, 1 };
  
  public static int[] decodeMod3Sves(byte[] paramArrayOfByte, int paramInt)
  {
    int[] arrayOfInt = new int[paramInt];
    int i = 0;
    int i2;
    for (int j = 0;; j = i2) {
      if (j < 8 * paramArrayOfByte.length)
      {
        int k = j + 1;
        int m = getBit(paramArrayOfByte, j);
        int n = k + 1;
        int i1 = getBit(paramArrayOfByte, k);
        i2 = n + 1;
        int i3 = getBit(paramArrayOfByte, n) + (m * 4 + i1 * 2);
        int i4 = i + 1;
        arrayOfInt[i] = COEFF1_TABLE[i3];
        i = i4 + 1;
        arrayOfInt[i4] = COEFF2_TABLE[i3];
        if (i <= paramInt - 2) {}
      }
      else
      {
        return arrayOfInt;
      }
    }
  }
  
  public static int[] decodeMod3Tight(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    return decodeMod3Tight(Util.readFullLength(paramInputStream, (int)Math.ceil(paramInt * Math.log(3.0D) / Math.log(2.0D) / 8.0D)), paramInt);
  }
  
  public static int[] decodeMod3Tight(byte[] paramArrayOfByte, int paramInt)
  {
    BigInteger localBigInteger = new BigInteger(1, paramArrayOfByte);
    int[] arrayOfInt = new int[paramInt];
    for (int i = 0; i < paramInt; i++)
    {
      arrayOfInt[i] = (-1 + localBigInteger.mod(BigInteger.valueOf(3L)).intValue());
      if (arrayOfInt[i] > 1) {
        arrayOfInt[i] = (-3 + arrayOfInt[i]);
      }
      localBigInteger = localBigInteger.divide(BigInteger.valueOf(3L));
    }
    return arrayOfInt;
  }
  
  public static int[] decodeModQ(InputStream paramInputStream, int paramInt1, int paramInt2)
    throws IOException
  {
    return decodeModQ(Util.readFullLength(paramInputStream, (7 + paramInt1 * (31 - Integer.numberOfLeadingZeros(paramInt2))) / 8), paramInt1, paramInt2);
  }
  
  public static int[] decodeModQ(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[paramInt1];
    int i = 31 - Integer.numberOfLeadingZeros(paramInt2);
    int j = paramInt1 * i;
    int k = 0;
    for (int m = 0; m < j; m++)
    {
      if ((m > 0) && (m % i == 0)) {
        k++;
      }
      int n = getBit(paramArrayOfByte, m);
      arrayOfInt[k] += (n << m % i);
    }
    return arrayOfInt;
  }
  
  public static byte[] encodeMod3Sves(int[] paramArrayOfInt)
  {
    byte[] arrayOfByte = new byte[(7 + (1 + 3 * paramArrayOfInt.length) / 2) / 8];
    int i = 0;
    int j = 0;
    int k = 0;
    if (k < 2 * (paramArrayOfInt.length / 2))
    {
      int m = k + 1;
      int n = 1 + paramArrayOfInt[k];
      k = m + 1;
      int i1 = 1 + paramArrayOfInt[m];
      if ((n == 0) && (i1 == 0)) {
        throw new IllegalStateException("Illegal encoding!");
      }
      int i2 = i1 + n * 3;
      int[] arrayOfInt = new int[3];
      arrayOfInt[0] = BIT1_TABLE[i2];
      arrayOfInt[1] = BIT2_TABLE[i2];
      arrayOfInt[2] = BIT3_TABLE[i2];
      int i3 = 0;
      label130:
      if (i3 < 3)
      {
        arrayOfByte[j] = ((byte)(arrayOfByte[j] | arrayOfInt[i3] << i));
        if (i != 7) {
          break label168;
        }
        i = 0;
        j++;
      }
      for (;;)
      {
        i3++;
        break label130;
        break;
        label168:
        i++;
      }
    }
    return arrayOfByte;
  }
  
  public static byte[] encodeMod3Tight(int[] paramArrayOfInt)
  {
    BigInteger localBigInteger = BigInteger.ZERO;
    for (int i = -1 + paramArrayOfInt.length; i >= 0; i--) {
      localBigInteger = localBigInteger.multiply(BigInteger.valueOf(3L)).add(BigInteger.valueOf(1 + paramArrayOfInt[i]));
    }
    int j = (7 + BigInteger.valueOf(3L).pow(paramArrayOfInt.length).bitLength()) / 8;
    byte[] arrayOfByte1 = localBigInteger.toByteArray();
    if (arrayOfByte1.length < j)
    {
      byte[] arrayOfByte2 = new byte[j];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, j - arrayOfByte1.length, arrayOfByte1.length);
      return arrayOfByte2;
    }
    if (arrayOfByte1.length > j) {
      arrayOfByte1 = Arrays.copyOfRange(arrayOfByte1, 1, arrayOfByte1.length);
    }
    return arrayOfByte1;
  }
  
  public static byte[] encodeModQ(int[] paramArrayOfInt, int paramInt)
  {
    int i = 31 - Integer.numberOfLeadingZeros(paramInt);
    byte[] arrayOfByte = new byte[(7 + i * paramArrayOfInt.length) / 8];
    int j = 0;
    int k = 0;
    for (int m = 0; m < paramArrayOfInt.length; m++)
    {
      int n = 0;
      if (n < i)
      {
        int i1 = 0x1 & paramArrayOfInt[m] >> n;
        arrayOfByte[k] = ((byte)(arrayOfByte[k] | i1 << j));
        if (j == 7)
        {
          j = 0;
          k++;
        }
        for (;;)
        {
          n++;
          break;
          j++;
        }
      }
    }
    return arrayOfByte;
  }
  
  private static int getBit(byte[] paramArrayOfByte, int paramInt)
  {
    return 0x1 & (0xFF & paramArrayOfByte[(paramInt / 8)]) >> paramInt % 8;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.util.ArrayEncoder
 * JD-Core Version:    0.7.0.1
 */