package org.spongycastle.crypto.engines;

import java.lang.reflect.Array;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class AESLightEngine
  implements BlockCipher
{
  private static final int BLOCK_SIZE = 16;
  private static final byte[] S = { 99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, 24, -106, 5, -102, 7, 18, -128, -30, -21, 39, -78, 117, 9, -125, 44, 26, 27, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, 127, 80, 60, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22 };
  private static final byte[] Si = { 82, 9, 106, -43, 48, 54, -91, 56, -65, 64, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, 44, 30, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, -123, -30, -7, 55, -24, 28, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, 27, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, 31, -35, -88, 51, -120, 7, -57, 49, -79, 18, 16, 89, 39, -128, -20, 95, 96, 81, 127, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, 59, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, 83, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125 };
  private static final int m1 = -2139062144;
  private static final int m2 = 2139062143;
  private static final int m3 = 27;
  private static final int[] rcon = { 1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145 };
  private int C0;
  private int C1;
  private int C2;
  private int C3;
  private int ROUNDS;
  private int[][] WorkingKey = null;
  private boolean forEncryption;
  
  private int FFmulX(int paramInt)
  {
    return (0x7F7F7F7F & paramInt) << 1 ^ 27 * ((0x80808080 & paramInt) >>> 7);
  }
  
  private void decryptBlock(int[][] paramArrayOfInt)
  {
    this.C0 ^= paramArrayOfInt[this.ROUNDS][0];
    this.C1 ^= paramArrayOfInt[this.ROUNDS][1];
    this.C2 ^= paramArrayOfInt[this.ROUNDS][2];
    this.C3 ^= paramArrayOfInt[this.ROUNDS][3];
    int i = -1 + this.ROUNDS;
    while (i > 1)
    {
      int i1 = inv_mcol(0xFF & Si[(0xFF & this.C0)] ^ (0xFF & Si[(0xFF & this.C3 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & this.C2 >> 16)]) << 16 ^ Si[(0xFF & this.C1 >> 24)] << 24) ^ paramArrayOfInt[i][0];
      int i2 = inv_mcol(0xFF & Si[(0xFF & this.C1)] ^ (0xFF & Si[(0xFF & this.C0 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & this.C3 >> 16)]) << 16 ^ Si[(0xFF & this.C2 >> 24)] << 24) ^ paramArrayOfInt[i][1];
      int i3 = inv_mcol(0xFF & Si[(0xFF & this.C2)] ^ (0xFF & Si[(0xFF & this.C1 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & this.C0 >> 16)]) << 16 ^ Si[(0xFF & this.C3 >> 24)] << 24) ^ paramArrayOfInt[i][2];
      int i4 = inv_mcol(0xFF & Si[(0xFF & this.C3)] ^ (0xFF & Si[(0xFF & this.C2 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & this.C1 >> 16)]) << 16 ^ Si[(0xFF & this.C0 >> 24)] << 24);
      int i5 = i - 1;
      int i6 = i4 ^ paramArrayOfInt[i][3];
      this.C0 = (inv_mcol(0xFF & Si[(i1 & 0xFF)] ^ (0xFF & Si[(0xFF & i6 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & i3 >> 16)]) << 16 ^ Si[(0xFF & i2 >> 24)] << 24) ^ paramArrayOfInt[i5][0]);
      this.C1 = (inv_mcol(0xFF & Si[(i2 & 0xFF)] ^ (0xFF & Si[(0xFF & i1 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & i6 >> 16)]) << 16 ^ Si[(0xFF & i3 >> 24)] << 24) ^ paramArrayOfInt[i5][1]);
      this.C2 = (inv_mcol(0xFF & Si[(i3 & 0xFF)] ^ (0xFF & Si[(0xFF & i2 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & i1 >> 16)]) << 16 ^ Si[(0xFF & i6 >> 24)] << 24) ^ paramArrayOfInt[i5][2]);
      int i7 = inv_mcol(0xFF & Si[(i6 & 0xFF)] ^ (0xFF & Si[(0xFF & i3 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & i2 >> 16)]) << 16 ^ Si[(0xFF & i1 >> 24)] << 24);
      i = i5 - 1;
      this.C3 = (i7 ^ paramArrayOfInt[i5][3]);
    }
    int j = inv_mcol(0xFF & Si[(0xFF & this.C0)] ^ (0xFF & Si[(0xFF & this.C3 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & this.C2 >> 16)]) << 16 ^ Si[(0xFF & this.C1 >> 24)] << 24) ^ paramArrayOfInt[i][0];
    int k = inv_mcol(0xFF & Si[(0xFF & this.C1)] ^ (0xFF & Si[(0xFF & this.C0 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & this.C3 >> 16)]) << 16 ^ Si[(0xFF & this.C2 >> 24)] << 24) ^ paramArrayOfInt[i][1];
    int m = inv_mcol(0xFF & Si[(0xFF & this.C2)] ^ (0xFF & Si[(0xFF & this.C1 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & this.C0 >> 16)]) << 16 ^ Si[(0xFF & this.C3 >> 24)] << 24) ^ paramArrayOfInt[i][2];
    int n = inv_mcol(0xFF & Si[(0xFF & this.C3)] ^ (0xFF & Si[(0xFF & this.C2 >> 8)]) << 8 ^ (0xFF & Si[(0xFF & this.C1 >> 16)]) << 16 ^ Si[(0xFF & this.C0 >> 24)] << 24) ^ paramArrayOfInt[i][3];
    this.C0 = (0xFF & Si[(j & 0xFF)] ^ (0xFF & Si[(0xFF & n >> 8)]) << 8 ^ (0xFF & Si[(0xFF & m >> 16)]) << 16 ^ Si[(0xFF & k >> 24)] << 24 ^ paramArrayOfInt[0][0]);
    this.C1 = (0xFF & Si[(k & 0xFF)] ^ (0xFF & Si[(0xFF & j >> 8)]) << 8 ^ (0xFF & Si[(0xFF & n >> 16)]) << 16 ^ Si[(0xFF & m >> 24)] << 24 ^ paramArrayOfInt[0][1]);
    this.C2 = (0xFF & Si[(m & 0xFF)] ^ (0xFF & Si[(0xFF & k >> 8)]) << 8 ^ (0xFF & Si[(0xFF & j >> 16)]) << 16 ^ Si[(0xFF & n >> 24)] << 24 ^ paramArrayOfInt[0][2]);
    this.C3 = (0xFF & Si[(n & 0xFF)] ^ (0xFF & Si[(0xFF & m >> 8)]) << 8 ^ (0xFF & Si[(0xFF & k >> 16)]) << 16 ^ Si[(0xFF & j >> 24)] << 24 ^ paramArrayOfInt[0][3]);
  }
  
  private void encryptBlock(int[][] paramArrayOfInt)
  {
    this.C0 ^= paramArrayOfInt[0][0];
    this.C1 ^= paramArrayOfInt[0][1];
    this.C2 ^= paramArrayOfInt[0][2];
    this.C3 ^= paramArrayOfInt[0][3];
    int i = 1;
    while (i < -1 + this.ROUNDS)
    {
      int i3 = mcol(0xFF & S[(0xFF & this.C0)] ^ (0xFF & S[(0xFF & this.C1 >> 8)]) << 8 ^ (0xFF & S[(0xFF & this.C2 >> 16)]) << 16 ^ S[(0xFF & this.C3 >> 24)] << 24) ^ paramArrayOfInt[i][0];
      int i4 = mcol(0xFF & S[(0xFF & this.C1)] ^ (0xFF & S[(0xFF & this.C2 >> 8)]) << 8 ^ (0xFF & S[(0xFF & this.C3 >> 16)]) << 16 ^ S[(0xFF & this.C0 >> 24)] << 24) ^ paramArrayOfInt[i][1];
      int i5 = mcol(0xFF & S[(0xFF & this.C2)] ^ (0xFF & S[(0xFF & this.C3 >> 8)]) << 8 ^ (0xFF & S[(0xFF & this.C0 >> 16)]) << 16 ^ S[(0xFF & this.C1 >> 24)] << 24) ^ paramArrayOfInt[i][2];
      int i6 = mcol(0xFF & S[(0xFF & this.C3)] ^ (0xFF & S[(0xFF & this.C0 >> 8)]) << 8 ^ (0xFF & S[(0xFF & this.C1 >> 16)]) << 16 ^ S[(0xFF & this.C2 >> 24)] << 24);
      int i7 = i + 1;
      int i8 = i6 ^ paramArrayOfInt[i][3];
      this.C0 = (mcol(0xFF & S[(i3 & 0xFF)] ^ (0xFF & S[(0xFF & i4 >> 8)]) << 8 ^ (0xFF & S[(0xFF & i5 >> 16)]) << 16 ^ S[(0xFF & i8 >> 24)] << 24) ^ paramArrayOfInt[i7][0]);
      this.C1 = (mcol(0xFF & S[(i4 & 0xFF)] ^ (0xFF & S[(0xFF & i5 >> 8)]) << 8 ^ (0xFF & S[(0xFF & i8 >> 16)]) << 16 ^ S[(0xFF & i3 >> 24)] << 24) ^ paramArrayOfInt[i7][1]);
      this.C2 = (mcol(0xFF & S[(i5 & 0xFF)] ^ (0xFF & S[(0xFF & i8 >> 8)]) << 8 ^ (0xFF & S[(0xFF & i3 >> 16)]) << 16 ^ S[(0xFF & i4 >> 24)] << 24) ^ paramArrayOfInt[i7][2]);
      int i9 = mcol(0xFF & S[(i8 & 0xFF)] ^ (0xFF & S[(0xFF & i3 >> 8)]) << 8 ^ (0xFF & S[(0xFF & i4 >> 16)]) << 16 ^ S[(0xFF & i5 >> 24)] << 24);
      i = i7 + 1;
      this.C3 = (i9 ^ paramArrayOfInt[i7][3]);
    }
    int j = mcol(0xFF & S[(0xFF & this.C0)] ^ (0xFF & S[(0xFF & this.C1 >> 8)]) << 8 ^ (0xFF & S[(0xFF & this.C2 >> 16)]) << 16 ^ S[(0xFF & this.C3 >> 24)] << 24) ^ paramArrayOfInt[i][0];
    int k = mcol(0xFF & S[(0xFF & this.C1)] ^ (0xFF & S[(0xFF & this.C2 >> 8)]) << 8 ^ (0xFF & S[(0xFF & this.C3 >> 16)]) << 16 ^ S[(0xFF & this.C0 >> 24)] << 24) ^ paramArrayOfInt[i][1];
    int m = mcol(0xFF & S[(0xFF & this.C2)] ^ (0xFF & S[(0xFF & this.C3 >> 8)]) << 8 ^ (0xFF & S[(0xFF & this.C0 >> 16)]) << 16 ^ S[(0xFF & this.C1 >> 24)] << 24) ^ paramArrayOfInt[i][2];
    int n = mcol(0xFF & S[(0xFF & this.C3)] ^ (0xFF & S[(0xFF & this.C0 >> 8)]) << 8 ^ (0xFF & S[(0xFF & this.C1 >> 16)]) << 16 ^ S[(0xFF & this.C2 >> 24)] << 24);
    int i1 = i + 1;
    int i2 = n ^ paramArrayOfInt[i][3];
    this.C0 = (0xFF & S[(j & 0xFF)] ^ (0xFF & S[(0xFF & k >> 8)]) << 8 ^ (0xFF & S[(0xFF & m >> 16)]) << 16 ^ S[(0xFF & i2 >> 24)] << 24 ^ paramArrayOfInt[i1][0]);
    this.C1 = (0xFF & S[(k & 0xFF)] ^ (0xFF & S[(0xFF & m >> 8)]) << 8 ^ (0xFF & S[(0xFF & i2 >> 16)]) << 16 ^ S[(0xFF & j >> 24)] << 24 ^ paramArrayOfInt[i1][1]);
    this.C2 = (0xFF & S[(m & 0xFF)] ^ (0xFF & S[(0xFF & i2 >> 8)]) << 8 ^ (0xFF & S[(0xFF & j >> 16)]) << 16 ^ S[(0xFF & k >> 24)] << 24 ^ paramArrayOfInt[i1][2]);
    this.C3 = (0xFF & S[(i2 & 0xFF)] ^ (0xFF & S[(0xFF & j >> 8)]) << 8 ^ (0xFF & S[(0xFF & k >> 16)]) << 16 ^ S[(0xFF & m >> 24)] << 24 ^ paramArrayOfInt[i1][3]);
  }
  
  private int[][] generateWorkingKey(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    int i = paramArrayOfByte.length / 4;
    if (((i != 4) && (i != 6) && (i != 8)) || (i * 4 != paramArrayOfByte.length)) {
      throw new IllegalArgumentException("Key length not 128/192/256 bits.");
    }
    this.ROUNDS = (i + 6);
    int[] arrayOfInt = { 1 + this.ROUNDS, 4 };
    int[][] arrayOfInt1 = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt);
    int j = 0;
    int k = 0;
    while (k < paramArrayOfByte.length)
    {
      arrayOfInt1[(j >> 2)][(j & 0x3)] = (0xFF & paramArrayOfByte[k] | (0xFF & paramArrayOfByte[(k + 1)]) << 8 | (0xFF & paramArrayOfByte[(k + 2)]) << 16 | paramArrayOfByte[(k + 3)] << 24);
      k += 4;
      j++;
    }
    int m = 1 + this.ROUNDS << 2;
    int n = i;
    if (n < m)
    {
      int i3 = arrayOfInt1[(n - 1 >> 2)][(0x3 & n - 1)];
      if (n % i == 0) {}
      for (i3 = subWord(shift(i3, 8)) ^ rcon[(-1 + n / i)];; i3 = subWord(i3)) {
        do
        {
          arrayOfInt1[(n >> 2)][(n & 0x3)] = (i3 ^ arrayOfInt1[(n - i >> 2)][(0x3 & n - i)]);
          n++;
          break;
        } while ((i <= 6) || (n % i != 4));
      }
    }
    if (!paramBoolean) {
      for (int i1 = 1; i1 < this.ROUNDS; i1++) {
        for (int i2 = 0; i2 < 4; i2++) {
          arrayOfInt1[i1][i2] = inv_mcol(arrayOfInt1[i1][i2]);
        }
      }
    }
    return arrayOfInt1;
  }
  
  private int inv_mcol(int paramInt)
  {
    int i = FFmulX(paramInt);
    int j = FFmulX(i);
    int k = FFmulX(j);
    int m = paramInt ^ k;
    return k ^ i ^ j ^ shift(i ^ m, 8) ^ shift(j ^ m, 16) ^ shift(m, 24);
  }
  
  private int mcol(int paramInt)
  {
    int i = FFmulX(paramInt);
    return i ^ shift(paramInt ^ i, 8) ^ shift(paramInt, 16) ^ shift(paramInt, 24);
  }
  
  private void packBlock(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramInt + 1;
    paramArrayOfByte[paramInt] = ((byte)this.C0);
    int j = i + 1;
    paramArrayOfByte[i] = ((byte)(this.C0 >> 8));
    int k = j + 1;
    paramArrayOfByte[j] = ((byte)(this.C0 >> 16));
    int m = k + 1;
    paramArrayOfByte[k] = ((byte)(this.C0 >> 24));
    int n = m + 1;
    paramArrayOfByte[m] = ((byte)this.C1);
    int i1 = n + 1;
    paramArrayOfByte[n] = ((byte)(this.C1 >> 8));
    int i2 = i1 + 1;
    paramArrayOfByte[i1] = ((byte)(this.C1 >> 16));
    int i3 = i2 + 1;
    paramArrayOfByte[i2] = ((byte)(this.C1 >> 24));
    int i4 = i3 + 1;
    paramArrayOfByte[i3] = ((byte)this.C2);
    int i5 = i4 + 1;
    paramArrayOfByte[i4] = ((byte)(this.C2 >> 8));
    int i6 = i5 + 1;
    paramArrayOfByte[i5] = ((byte)(this.C2 >> 16));
    int i7 = i6 + 1;
    paramArrayOfByte[i6] = ((byte)(this.C2 >> 24));
    int i8 = i7 + 1;
    paramArrayOfByte[i7] = ((byte)this.C3);
    int i9 = i8 + 1;
    paramArrayOfByte[i8] = ((byte)(this.C3 >> 8));
    int i10 = i9 + 1;
    paramArrayOfByte[i9] = ((byte)(this.C3 >> 16));
    paramArrayOfByte[i10] = ((byte)(this.C3 >> 24));
  }
  
  private int shift(int paramInt1, int paramInt2)
  {
    return paramInt1 >>> paramInt2 | paramInt1 << -paramInt2;
  }
  
  private int subWord(int paramInt)
  {
    return 0xFF & S[(paramInt & 0xFF)] | (0xFF & S[(0xFF & paramInt >> 8)]) << 8 | (0xFF & S[(0xFF & paramInt >> 16)]) << 16 | S[(0xFF & paramInt >> 24)] << 24;
  }
  
  private void unpackBlock(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramInt + 1;
    this.C0 = (0xFF & paramArrayOfByte[paramInt]);
    int j = this.C0;
    int k = i + 1;
    this.C0 = (j | (0xFF & paramArrayOfByte[i]) << 8);
    int m = this.C0;
    int n = k + 1;
    this.C0 = (m | (0xFF & paramArrayOfByte[k]) << 16);
    int i1 = this.C0;
    int i2 = n + 1;
    this.C0 = (i1 | paramArrayOfByte[n] << 24);
    int i3 = i2 + 1;
    this.C1 = (0xFF & paramArrayOfByte[i2]);
    int i4 = this.C1;
    int i5 = i3 + 1;
    this.C1 = (i4 | (0xFF & paramArrayOfByte[i3]) << 8);
    int i6 = this.C1;
    int i7 = i5 + 1;
    this.C1 = (i6 | (0xFF & paramArrayOfByte[i5]) << 16);
    int i8 = this.C1;
    int i9 = i7 + 1;
    this.C1 = (i8 | paramArrayOfByte[i7] << 24);
    int i10 = i9 + 1;
    this.C2 = (0xFF & paramArrayOfByte[i9]);
    int i11 = this.C2;
    int i12 = i10 + 1;
    this.C2 = (i11 | (0xFF & paramArrayOfByte[i10]) << 8);
    int i13 = this.C2;
    int i14 = i12 + 1;
    this.C2 = (i13 | (0xFF & paramArrayOfByte[i12]) << 16);
    int i15 = this.C2;
    int i16 = i14 + 1;
    this.C2 = (i15 | paramArrayOfByte[i14] << 24);
    int i17 = i16 + 1;
    this.C3 = (0xFF & paramArrayOfByte[i16]);
    int i18 = this.C3;
    int i19 = i17 + 1;
    this.C3 = (i18 | (0xFF & paramArrayOfByte[i17]) << 8);
    int i20 = this.C3;
    int i21 = i19 + 1;
    this.C3 = (i20 | (0xFF & paramArrayOfByte[i19]) << 16);
    this.C3 |= paramArrayOfByte[i21] << 24;
  }
  
  public String getAlgorithmName()
  {
    return "AES";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof KeyParameter))
    {
      this.WorkingKey = generateWorkingKey(((KeyParameter)paramCipherParameters).getKey(), paramBoolean);
      this.forEncryption = paramBoolean;
      return;
    }
    throw new IllegalArgumentException("invalid parameter passed to AES init - " + paramCipherParameters.getClass().getName());
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if (this.WorkingKey == null) {
      throw new IllegalStateException("AES engine not initialised");
    }
    if (paramInt1 + 16 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + 16 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    if (this.forEncryption)
    {
      unpackBlock(paramArrayOfByte1, paramInt1);
      encryptBlock(this.WorkingKey);
      packBlock(paramArrayOfByte2, paramInt2);
    }
    for (;;)
    {
      return 16;
      unpackBlock(paramArrayOfByte1, paramInt1);
      decryptBlock(this.WorkingKey);
      packBlock(paramArrayOfByte2, paramInt2);
    }
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.AESLightEngine
 * JD-Core Version:    0.7.0.1
 */