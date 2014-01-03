package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.RC2Parameters;

public class RC2Engine
  implements BlockCipher
{
  private static final int BLOCK_SIZE = 8;
  private static byte[] piTable = { -39, 120, -7, -60, 25, -35, -75, -19, 40, -23, -3, 121, 74, -96, -40, -99, -58, 126, 55, -125, 43, 118, 83, -114, 98, 76, 100, -120, 68, -117, -5, -94, 23, -102, 89, -11, -121, -77, 79, 19, 97, 69, 109, -115, 9, -127, 125, 50, -67, -113, 64, -21, -122, -73, 123, 11, -16, -107, 33, 34, 92, 107, 78, -126, 84, -42, 101, -109, -50, 96, -78, 28, 115, 86, -64, 20, -89, -116, -15, -36, 18, 117, -54, 31, 59, -66, -28, -47, 66, 61, -44, 48, -93, 60, -74, 38, 111, -65, 14, -38, 70, 105, 7, 87, 39, -14, 29, -101, -68, -108, 67, 3, -8, 17, -57, -10, -112, -17, 62, -25, 6, -61, -43, 47, -56, 102, 30, -41, 8, -24, -22, -34, -128, 82, -18, -9, -124, -86, 114, -84, 53, 77, 106, 42, -106, 26, -46, 113, 90, 21, 73, 116, 75, -97, -48, 94, 4, 24, -92, -20, -62, -32, 65, 110, 15, 81, -53, -52, 36, -111, -81, 80, -95, -12, 112, 57, -103, 124, 58, -123, 35, -72, -76, 122, -4, 2, 54, 91, 37, 85, -105, 49, 45, 93, -6, -104, -29, -118, -110, -82, 5, -33, 41, 16, 103, 108, -70, -55, -45, 0, -26, -49, -31, -98, -88, 44, 99, 22, 1, 63, 88, -30, -119, -87, 13, 56, 52, 27, -85, 51, -1, -80, -69, 72, 12, 95, -71, -79, -51, 46, -59, -13, -37, 71, -27, -91, -100, 119, 10, -90, 32, 104, -2, 127, -63, -83 };
  private boolean encrypting;
  private int[] workingKey;
  
  private void decryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = ((0xFF & paramArrayOfByte1[(paramInt1 + 7)]) << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 6)]);
    int j = ((0xFF & paramArrayOfByte1[(paramInt1 + 5)]) << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 4)]);
    int k = ((0xFF & paramArrayOfByte1[(paramInt1 + 3)]) << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 2)]);
    int m = ((0xFF & paramArrayOfByte1[(paramInt1 + 1)]) << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 0)]);
    for (int n = 60; n >= 44; n -= 4)
    {
      i = rotateWordLeft(i, 11) - ((m & (j ^ 0xFFFFFFFF)) + (k & j) + this.workingKey[(n + 3)]);
      j = rotateWordLeft(j, 13) - ((i & (k ^ 0xFFFFFFFF)) + (m & k) + this.workingKey[(n + 2)]);
      k = rotateWordLeft(k, 14) - ((j & (m ^ 0xFFFFFFFF)) + (i & m) + this.workingKey[(n + 1)]);
      m = rotateWordLeft(m, 15) - ((k & (i ^ 0xFFFFFFFF)) + (j & i) + this.workingKey[n]);
    }
    int i1 = i - this.workingKey[(j & 0x3F)];
    int i2 = j - this.workingKey[(k & 0x3F)];
    int i3 = k - this.workingKey[(m & 0x3F)];
    int i4 = m - this.workingKey[(i1 & 0x3F)];
    for (int i5 = 40; i5 >= 20; i5 -= 4)
    {
      i1 = rotateWordLeft(i1, 11) - ((i4 & (i2 ^ 0xFFFFFFFF)) + (i3 & i2) + this.workingKey[(i5 + 3)]);
      i2 = rotateWordLeft(i2, 13) - ((i1 & (i3 ^ 0xFFFFFFFF)) + (i4 & i3) + this.workingKey[(i5 + 2)]);
      i3 = rotateWordLeft(i3, 14) - ((i2 & (i4 ^ 0xFFFFFFFF)) + (i1 & i4) + this.workingKey[(i5 + 1)]);
      i4 = rotateWordLeft(i4, 15) - ((i3 & (i1 ^ 0xFFFFFFFF)) + (i2 & i1) + this.workingKey[i5]);
    }
    int i6 = i1 - this.workingKey[(i2 & 0x3F)];
    int i7 = i2 - this.workingKey[(i3 & 0x3F)];
    int i8 = i3 - this.workingKey[(i4 & 0x3F)];
    int i9 = i4 - this.workingKey[(i6 & 0x3F)];
    for (int i10 = 16; i10 >= 0; i10 -= 4)
    {
      i6 = rotateWordLeft(i6, 11) - ((i9 & (i7 ^ 0xFFFFFFFF)) + (i8 & i7) + this.workingKey[(i10 + 3)]);
      i7 = rotateWordLeft(i7, 13) - ((i6 & (i8 ^ 0xFFFFFFFF)) + (i9 & i8) + this.workingKey[(i10 + 2)]);
      i8 = rotateWordLeft(i8, 14) - ((i7 & (i9 ^ 0xFFFFFFFF)) + (i6 & i9) + this.workingKey[(i10 + 1)]);
      i9 = rotateWordLeft(i9, 15) - ((i8 & (i6 ^ 0xFFFFFFFF)) + (i7 & i6) + this.workingKey[i10]);
    }
    paramArrayOfByte2[(paramInt2 + 0)] = ((byte)i9);
    paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(i9 >> 8));
    paramArrayOfByte2[(paramInt2 + 2)] = ((byte)i8);
    paramArrayOfByte2[(paramInt2 + 3)] = ((byte)(i8 >> 8));
    paramArrayOfByte2[(paramInt2 + 4)] = ((byte)i7);
    paramArrayOfByte2[(paramInt2 + 5)] = ((byte)(i7 >> 8));
    paramArrayOfByte2[(paramInt2 + 6)] = ((byte)i6);
    paramArrayOfByte2[(paramInt2 + 7)] = ((byte)(i6 >> 8));
  }
  
  private void encryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = ((0xFF & paramArrayOfByte1[(paramInt1 + 7)]) << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 6)]);
    int j = ((0xFF & paramArrayOfByte1[(paramInt1 + 5)]) << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 4)]);
    int k = ((0xFF & paramArrayOfByte1[(paramInt1 + 3)]) << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 2)]);
    int m = ((0xFF & paramArrayOfByte1[(paramInt1 + 1)]) << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 0)]);
    for (int n = 0; n <= 16; n += 4)
    {
      m = rotateWordLeft(m + (k & (i ^ 0xFFFFFFFF)) + (j & i) + this.workingKey[n], 1);
      k = rotateWordLeft(k + (j & (m ^ 0xFFFFFFFF)) + (i & m) + this.workingKey[(n + 1)], 2);
      j = rotateWordLeft(j + (i & (k ^ 0xFFFFFFFF)) + (m & k) + this.workingKey[(n + 2)], 3);
      i = rotateWordLeft(i + (m & (j ^ 0xFFFFFFFF)) + (k & j) + this.workingKey[(n + 3)], 5);
    }
    int i1 = m + this.workingKey[(i & 0x3F)];
    int i2 = k + this.workingKey[(i1 & 0x3F)];
    int i3 = j + this.workingKey[(i2 & 0x3F)];
    int i4 = i + this.workingKey[(i3 & 0x3F)];
    for (int i5 = 20; i5 <= 40; i5 += 4)
    {
      i1 = rotateWordLeft(i1 + (i2 & (i4 ^ 0xFFFFFFFF)) + (i3 & i4) + this.workingKey[i5], 1);
      i2 = rotateWordLeft(i2 + (i3 & (i1 ^ 0xFFFFFFFF)) + (i4 & i1) + this.workingKey[(i5 + 1)], 2);
      i3 = rotateWordLeft(i3 + (i4 & (i2 ^ 0xFFFFFFFF)) + (i1 & i2) + this.workingKey[(i5 + 2)], 3);
      i4 = rotateWordLeft(i4 + (i1 & (i3 ^ 0xFFFFFFFF)) + (i2 & i3) + this.workingKey[(i5 + 3)], 5);
    }
    int i6 = i1 + this.workingKey[(i4 & 0x3F)];
    int i7 = i2 + this.workingKey[(i6 & 0x3F)];
    int i8 = i3 + this.workingKey[(i7 & 0x3F)];
    int i9 = i4 + this.workingKey[(i8 & 0x3F)];
    for (int i10 = 44; i10 < 64; i10 += 4)
    {
      i6 = rotateWordLeft(i6 + (i7 & (i9 ^ 0xFFFFFFFF)) + (i8 & i9) + this.workingKey[i10], 1);
      i7 = rotateWordLeft(i7 + (i8 & (i6 ^ 0xFFFFFFFF)) + (i9 & i6) + this.workingKey[(i10 + 1)], 2);
      i8 = rotateWordLeft(i8 + (i9 & (i7 ^ 0xFFFFFFFF)) + (i6 & i7) + this.workingKey[(i10 + 2)], 3);
      i9 = rotateWordLeft(i9 + (i6 & (i8 ^ 0xFFFFFFFF)) + (i7 & i8) + this.workingKey[(i10 + 3)], 5);
    }
    paramArrayOfByte2[(paramInt2 + 0)] = ((byte)i6);
    paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(i6 >> 8));
    paramArrayOfByte2[(paramInt2 + 2)] = ((byte)i7);
    paramArrayOfByte2[(paramInt2 + 3)] = ((byte)(i7 >> 8));
    paramArrayOfByte2[(paramInt2 + 4)] = ((byte)i8);
    paramArrayOfByte2[(paramInt2 + 5)] = ((byte)(i8 >> 8));
    paramArrayOfByte2[(paramInt2 + 6)] = ((byte)i9);
    paramArrayOfByte2[(paramInt2 + 7)] = ((byte)(i9 >> 8));
  }
  
  private int[] generateWorkingKey(byte[] paramArrayOfByte, int paramInt)
  {
    int[] arrayOfInt1 = new int[''];
    for (int i = 0; i != paramArrayOfByte.length; i++) {
      arrayOfInt1[i] = (0xFF & paramArrayOfByte[i]);
    }
    int j = paramArrayOfByte.length;
    int i2;
    int i3;
    if (j < 128)
    {
      i2 = 0;
      i3 = arrayOfInt1[(j - 1)];
    }
    for (;;)
    {
      byte[] arrayOfByte = piTable;
      int i4 = i2 + 1;
      i3 = 0xFF & arrayOfByte[(0xFF & i3 + arrayOfInt1[i2])];
      int i5 = j + 1;
      arrayOfInt1[j] = i3;
      if (i5 >= 128)
      {
        int k = paramInt + 7 >> 3;
        int m = 0xFF & piTable[(arrayOfInt1[(128 - k)] & 255 >> (0x7 & -paramInt))];
        arrayOfInt1[(128 - k)] = m;
        for (int n = -1 + (128 - k); n >= 0; n--)
        {
          m = 0xFF & piTable[(m ^ arrayOfInt1[(n + k)])];
          arrayOfInt1[n] = m;
        }
        int[] arrayOfInt2 = new int[64];
        for (int i1 = 0; i1 != arrayOfInt2.length; i1++) {
          arrayOfInt2[i1] = (arrayOfInt1[(i1 * 2)] + (arrayOfInt1[(1 + i1 * 2)] << 8));
        }
        return arrayOfInt2;
      }
      i2 = i4;
      j = i5;
    }
  }
  
  private int rotateWordLeft(int paramInt1, int paramInt2)
  {
    int i = paramInt1 & 0xFFFF;
    return i << paramInt2 | i >> 16 - paramInt2;
  }
  
  public String getAlgorithmName()
  {
    return "RC2";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.encrypting = paramBoolean;
    if ((paramCipherParameters instanceof RC2Parameters))
    {
      RC2Parameters localRC2Parameters = (RC2Parameters)paramCipherParameters;
      this.workingKey = generateWorkingKey(localRC2Parameters.getKey(), localRC2Parameters.getEffectiveKeyBits());
      return;
    }
    if ((paramCipherParameters instanceof KeyParameter))
    {
      byte[] arrayOfByte = ((KeyParameter)paramCipherParameters).getKey();
      this.workingKey = generateWorkingKey(arrayOfByte, 8 * arrayOfByte.length);
      return;
    }
    throw new IllegalArgumentException("invalid parameter passed to RC2 init - " + paramCipherParameters.getClass().getName());
  }
  
  public final int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if (this.workingKey == null) {
      throw new IllegalStateException("RC2 engine not initialised");
    }
    if (paramInt1 + 8 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + 8 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    if (this.encrypting) {
      encryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
    for (;;)
    {
      return 8;
      decryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RC2Engine
 * JD-Core Version:    0.7.0.1
 */