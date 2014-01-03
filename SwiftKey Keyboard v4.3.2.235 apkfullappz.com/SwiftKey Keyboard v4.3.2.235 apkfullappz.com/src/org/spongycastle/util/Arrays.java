package org.spongycastle.util;

import java.math.BigInteger;

public final class Arrays
{
  public static boolean areEqual(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte1 == paramArrayOfByte2) {}
    for (;;)
    {
      return true;
      if ((paramArrayOfByte1 == null) || (paramArrayOfByte2 == null)) {
        return false;
      }
      if (paramArrayOfByte1.length != paramArrayOfByte2.length) {
        return false;
      }
      for (int i = 0; i != paramArrayOfByte1.length; i++) {
        if (paramArrayOfByte1[i] != paramArrayOfByte2[i]) {
          return false;
        }
      }
    }
  }
  
  public static boolean areEqual(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    if (paramArrayOfChar1 == paramArrayOfChar2) {}
    for (;;)
    {
      return true;
      if ((paramArrayOfChar1 == null) || (paramArrayOfChar2 == null)) {
        return false;
      }
      if (paramArrayOfChar1.length != paramArrayOfChar2.length) {
        return false;
      }
      for (int i = 0; i != paramArrayOfChar1.length; i++) {
        if (paramArrayOfChar1[i] != paramArrayOfChar2[i]) {
          return false;
        }
      }
    }
  }
  
  public static boolean areEqual(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (paramArrayOfInt1 == paramArrayOfInt2) {}
    for (;;)
    {
      return true;
      if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null)) {
        return false;
      }
      if (paramArrayOfInt1.length != paramArrayOfInt2.length) {
        return false;
      }
      for (int i = 0; i != paramArrayOfInt1.length; i++) {
        if (paramArrayOfInt1[i] != paramArrayOfInt2[i]) {
          return false;
        }
      }
    }
  }
  
  public static boolean areEqual(long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    if (paramArrayOfLong1 == paramArrayOfLong2) {}
    for (;;)
    {
      return true;
      if ((paramArrayOfLong1 == null) || (paramArrayOfLong2 == null)) {
        return false;
      }
      if (paramArrayOfLong1.length != paramArrayOfLong2.length) {
        return false;
      }
      for (int i = 0; i != paramArrayOfLong1.length; i++) {
        if (paramArrayOfLong1[i] != paramArrayOfLong2[i]) {
          return false;
        }
      }
    }
  }
  
  public static boolean areEqual(BigInteger[] paramArrayOfBigInteger1, BigInteger[] paramArrayOfBigInteger2)
  {
    if (paramArrayOfBigInteger1 == paramArrayOfBigInteger2) {}
    for (;;)
    {
      return true;
      if ((paramArrayOfBigInteger1 == null) || (paramArrayOfBigInteger2 == null)) {
        return false;
      }
      if (paramArrayOfBigInteger1.length != paramArrayOfBigInteger2.length) {
        return false;
      }
      for (int i = 0; i != paramArrayOfBigInteger1.length; i++) {
        if (!paramArrayOfBigInteger1[i].equals(paramArrayOfBigInteger2[i])) {
          return false;
        }
      }
    }
  }
  
  public static boolean areEqual(boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2)
  {
    if (paramArrayOfBoolean1 == paramArrayOfBoolean2) {}
    for (;;)
    {
      return true;
      if ((paramArrayOfBoolean1 == null) || (paramArrayOfBoolean2 == null)) {
        return false;
      }
      if (paramArrayOfBoolean1.length != paramArrayOfBoolean2.length) {
        return false;
      }
      for (int i = 0; i != paramArrayOfBoolean1.length; i++) {
        if (paramArrayOfBoolean1[i] != paramArrayOfBoolean2[i]) {
          return false;
        }
      }
    }
  }
  
  public static byte[] clone(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
    return arrayOfByte;
  }
  
  public static int[] clone(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      return null;
    }
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramArrayOfInt.length);
    return arrayOfInt;
  }
  
  public static BigInteger[] clone(BigInteger[] paramArrayOfBigInteger)
  {
    if (paramArrayOfBigInteger == null) {
      return null;
    }
    BigInteger[] arrayOfBigInteger = new BigInteger[paramArrayOfBigInteger.length];
    System.arraycopy(paramArrayOfBigInteger, 0, arrayOfBigInteger, 0, paramArrayOfBigInteger.length);
    return arrayOfBigInteger;
  }
  
  public static boolean constantTimeAreEqual(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte1 == paramArrayOfByte2) {}
    int i;
    do
    {
      return true;
      if ((paramArrayOfByte1 == null) || (paramArrayOfByte2 == null)) {
        return false;
      }
      if (paramArrayOfByte1.length != paramArrayOfByte2.length) {
        return false;
      }
      i = 0;
      for (int j = 0; j != paramArrayOfByte1.length; j++) {
        i |= paramArrayOfByte1[j] ^ paramArrayOfByte2[j];
      }
    } while (i == 0);
    return false;
  }
  
  public static byte[] copyOf(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    if (paramInt < paramArrayOfByte.length)
    {
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramInt);
      return arrayOfByte;
    }
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
    return arrayOfByte;
  }
  
  public static int[] copyOf(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = new int[paramInt];
    if (paramInt < paramArrayOfInt.length)
    {
      System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramInt);
      return arrayOfInt;
    }
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramArrayOfInt.length);
    return arrayOfInt;
  }
  
  public static long[] copyOf(long[] paramArrayOfLong, int paramInt)
  {
    long[] arrayOfLong = new long[paramInt];
    if (paramInt < paramArrayOfLong.length)
    {
      System.arraycopy(paramArrayOfLong, 0, arrayOfLong, 0, paramInt);
      return arrayOfLong;
    }
    System.arraycopy(paramArrayOfLong, 0, arrayOfLong, 0, paramArrayOfLong.length);
    return arrayOfLong;
  }
  
  public static BigInteger[] copyOf(BigInteger[] paramArrayOfBigInteger, int paramInt)
  {
    BigInteger[] arrayOfBigInteger = new BigInteger[paramInt];
    if (paramInt < paramArrayOfBigInteger.length)
    {
      System.arraycopy(paramArrayOfBigInteger, 0, arrayOfBigInteger, 0, paramInt);
      return arrayOfBigInteger;
    }
    System.arraycopy(paramArrayOfBigInteger, 0, arrayOfBigInteger, 0, paramArrayOfBigInteger.length);
    return arrayOfBigInteger;
  }
  
  public static byte[] copyOfRange(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = getLength(paramInt1, paramInt2);
    byte[] arrayOfByte = new byte[i];
    if (paramArrayOfByte.length - paramInt1 < i)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramArrayOfByte.length - paramInt1);
      return arrayOfByte;
    }
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, i);
    return arrayOfByte;
  }
  
  public static int[] copyOfRange(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = getLength(paramInt1, paramInt2);
    int[] arrayOfInt = new int[i];
    if (paramArrayOfInt.length - paramInt1 < i)
    {
      System.arraycopy(paramArrayOfInt, paramInt1, arrayOfInt, 0, paramArrayOfInt.length - paramInt1);
      return arrayOfInt;
    }
    System.arraycopy(paramArrayOfInt, paramInt1, arrayOfInt, 0, i);
    return arrayOfInt;
  }
  
  public static long[] copyOfRange(long[] paramArrayOfLong, int paramInt1, int paramInt2)
  {
    int i = getLength(paramInt1, paramInt2);
    long[] arrayOfLong = new long[i];
    if (paramArrayOfLong.length - paramInt1 < i)
    {
      System.arraycopy(paramArrayOfLong, paramInt1, arrayOfLong, 0, paramArrayOfLong.length - paramInt1);
      return arrayOfLong;
    }
    System.arraycopy(paramArrayOfLong, paramInt1, arrayOfLong, 0, i);
    return arrayOfLong;
  }
  
  public static BigInteger[] copyOfRange(BigInteger[] paramArrayOfBigInteger, int paramInt1, int paramInt2)
  {
    int i = getLength(paramInt1, paramInt2);
    BigInteger[] arrayOfBigInteger = new BigInteger[i];
    if (paramArrayOfBigInteger.length - paramInt1 < i)
    {
      System.arraycopy(paramArrayOfBigInteger, paramInt1, arrayOfBigInteger, 0, paramArrayOfBigInteger.length - paramInt1);
      return arrayOfBigInteger;
    }
    System.arraycopy(paramArrayOfBigInteger, paramInt1, arrayOfBigInteger, 0, i);
    return arrayOfBigInteger;
  }
  
  public static void fill(byte[] paramArrayOfByte, byte paramByte)
  {
    for (int i = 0; i < paramArrayOfByte.length; i++) {
      paramArrayOfByte[i] = paramByte;
    }
  }
  
  public static void fill(int[] paramArrayOfInt, int paramInt)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      paramArrayOfInt[i] = paramInt;
    }
  }
  
  public static void fill(long[] paramArrayOfLong, long paramLong)
  {
    for (int i = 0; i < paramArrayOfLong.length; i++) {
      paramArrayOfLong[i] = paramLong;
    }
  }
  
  public static void fill(short[] paramArrayOfShort, short paramShort)
  {
    for (int i = 0; i < paramArrayOfShort.length; i++) {
      paramArrayOfShort[i] = paramShort;
    }
  }
  
  private static int getLength(int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i < 0) {
      throw new IllegalArgumentException(paramInt1 + " > " + paramInt2);
    }
    return i;
  }
  
  public static int hashCode(byte[] paramArrayOfByte)
  {
    int j;
    if (paramArrayOfByte == null) {
      j = 0;
    }
    for (;;)
    {
      return j;
      int i = paramArrayOfByte.length;
      for (j = i + 1;; j = j * 257 ^ paramArrayOfByte[i])
      {
        i--;
        if (i < 0) {
          break;
        }
      }
    }
  }
  
  public static int hashCode(char[] paramArrayOfChar)
  {
    int j;
    if (paramArrayOfChar == null) {
      j = 0;
    }
    for (;;)
    {
      return j;
      int i = paramArrayOfChar.length;
      for (j = i + 1;; j = j * 257 ^ paramArrayOfChar[i])
      {
        i--;
        if (i < 0) {
          break;
        }
      }
    }
  }
  
  public static int hashCode(int[] paramArrayOfInt)
  {
    int j;
    if (paramArrayOfInt == null) {
      j = 0;
    }
    for (;;)
    {
      return j;
      int i = paramArrayOfInt.length;
      for (j = i + 1;; j = j * 257 ^ paramArrayOfInt[i])
      {
        i--;
        if (i < 0) {
          break;
        }
      }
    }
  }
  
  public static int hashCode(BigInteger[] paramArrayOfBigInteger)
  {
    int j;
    if (paramArrayOfBigInteger == null) {
      j = 0;
    }
    for (;;)
    {
      return j;
      int i = paramArrayOfBigInteger.length;
      for (j = i + 1;; j = j * 257 ^ paramArrayOfBigInteger[i].hashCode())
      {
        i--;
        if (i < 0) {
          break;
        }
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.Arrays
 * JD-Core Version:    0.7.0.1
 */