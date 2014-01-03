package org.spongycastle.crypto.modes.gcm;

import org.spongycastle.crypto.util.Pack;
import org.spongycastle.util.Arrays;

abstract class GCMUtil
{
  static int[] asInts(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt = new int[4];
    arrayOfInt[0] = Pack.bigEndianToInt(paramArrayOfByte, 0);
    arrayOfInt[1] = Pack.bigEndianToInt(paramArrayOfByte, 4);
    arrayOfInt[2] = Pack.bigEndianToInt(paramArrayOfByte, 8);
    arrayOfInt[3] = Pack.bigEndianToInt(paramArrayOfByte, 12);
    return arrayOfInt;
  }
  
  static void multiply(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    byte[] arrayOfByte1 = Arrays.clone(paramArrayOfByte1);
    byte[] arrayOfByte2 = new byte[16];
    for (int i = 0; i < 16; i++)
    {
      int j = paramArrayOfByte2[i];
      int k = 7;
      if (k >= 0)
      {
        if ((j & 1 << k) != 0) {
          xor(arrayOfByte2, arrayOfByte1);
        }
        if ((0x1 & arrayOfByte1[15]) != 0) {}
        for (int m = 1;; m = 0)
        {
          shiftRight(arrayOfByte1);
          if (m != 0) {
            arrayOfByte1[0] = ((byte)(0xFFFFFFE1 ^ arrayOfByte1[0]));
          }
          k--;
          break;
        }
      }
    }
    System.arraycopy(arrayOfByte2, 0, paramArrayOfByte1, 0, 16);
  }
  
  static void multiplyP(int[] paramArrayOfInt)
  {
    if ((0x1 & paramArrayOfInt[3]) != 0) {}
    for (int i = 1;; i = 0)
    {
      shiftRight(paramArrayOfInt);
      if (i != 0) {
        paramArrayOfInt[0] = (0xE1000000 ^ paramArrayOfInt[0]);
      }
      return;
    }
  }
  
  static void multiplyP8(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[3];
    shiftRightN(paramArrayOfInt, 8);
    for (int j = 7; j >= 0; j--) {
      if ((i & 1 << j) != 0) {
        paramArrayOfInt[0] ^= -520093696 >>> 7 - j;
      }
    }
  }
  
  static byte[] oneAsBytes()
  {
    byte[] arrayOfByte = new byte[16];
    arrayOfByte[0] = -128;
    return arrayOfByte;
  }
  
  static int[] oneAsInts()
  {
    int[] arrayOfInt = new int[4];
    arrayOfInt[0] = -2147483648;
    return arrayOfInt;
  }
  
  static void shiftRight(byte[] paramArrayOfByte)
  {
    int i = 0;
    int k;
    for (int j = 0;; j = (k & 0x1) << 7)
    {
      k = 0xFF & paramArrayOfByte[i];
      paramArrayOfByte[i] = ((byte)(j | k >>> 1));
      i++;
      if (i == 16) {
        break;
      }
    }
  }
  
  static void shiftRight(int[] paramArrayOfInt)
  {
    int i = 0;
    int k;
    for (int j = 0;; j = k << 31)
    {
      k = paramArrayOfInt[i];
      paramArrayOfInt[i] = (j | k >>> 1);
      i++;
      if (i == 4) {
        break;
      }
    }
  }
  
  static void shiftRightN(int[] paramArrayOfInt, int paramInt)
  {
    int i = 0;
    int k;
    for (int j = 0;; j = k << 32 - paramInt)
    {
      k = paramArrayOfInt[i];
      paramArrayOfInt[i] = (j | k >>> paramInt);
      i++;
      if (i == 4) {
        break;
      }
    }
  }
  
  static void xor(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    for (int i = 15; i >= 0; i--) {
      paramArrayOfByte1[i] = ((byte)(paramArrayOfByte1[i] ^ paramArrayOfByte2[i]));
    }
  }
  
  static void xor(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    for (int i = 3; i >= 0; i--) {
      paramArrayOfInt1[i] ^= paramArrayOfInt2[i];
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.gcm.GCMUtil
 * JD-Core Version:    0.7.0.1
 */