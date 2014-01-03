package org.spongycastle.crypto.engines;

import java.lang.reflect.Array;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class RijndaelEngine
  implements BlockCipher
{
  private static final int MAXKC = 64;
  private static final int MAXROUNDS = 14;
  private static final byte[] S = { 99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, 24, -106, 5, -102, 7, 18, -128, -30, -21, 39, -78, 117, 9, -125, 44, 26, 27, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, 127, 80, 60, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22 };
  private static final byte[] Si = { 82, 9, 106, -43, 48, 54, -91, 56, -65, 64, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, 44, 30, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, -123, -30, -7, 55, -24, 28, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, 27, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, 31, -35, -88, 51, -120, 7, -57, 49, -79, 18, 16, 89, 39, -128, -20, 95, 96, 81, 127, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, 59, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, 83, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125 };
  private static final byte[] aLogtable;
  private static final byte[] logtable = { 0, 0, 25, 1, 50, 2, 26, -58, 75, -57, 27, 104, 51, -18, -33, 3, 100, 4, -32, 14, 52, -115, -127, -17, 76, 113, 8, -56, -8, 105, 28, -63, 125, -62, 29, -75, -7, -71, 39, 106, 77, -28, -90, 114, -102, -55, 9, 120, 101, 47, -118, 5, 33, 15, -31, 36, 18, -16, -126, 69, 53, -109, -38, -114, -106, -113, -37, -67, 54, -48, -50, -108, 19, 92, -46, -15, 64, 70, -125, 56, 102, -35, -3, 48, -65, 6, -117, 98, -77, 37, -30, -104, 34, -120, -111, 16, 126, 110, 72, -61, -93, -74, 30, 66, 58, 107, 40, 84, -6, -123, 61, -70, 43, 121, 10, 21, -101, -97, 94, -54, 78, -44, -84, -27, -13, 115, -89, 87, -81, 88, -88, 80, -12, -22, -42, 116, 79, -82, -23, -43, -25, -26, -83, -24, 44, -41, 117, 122, -21, 22, 11, -11, 89, -53, 95, -80, -100, -87, 81, -96, 127, 12, -10, 111, 23, -60, 73, -20, -40, 67, 31, 45, -92, 118, 123, -73, -52, -69, 62, 90, -5, 96, -79, -122, 59, 82, -95, 108, -86, 85, 41, -99, -105, -78, -121, -112, 97, -66, -36, -4, -68, -107, -49, -51, 55, 63, 91, -47, 83, 57, -124, 60, 65, -94, 109, 71, 20, 42, -98, 93, 86, -14, -45, -85, 68, 17, -110, -39, 35, 32, 46, -119, -76, 124, -72, 38, 119, -103, -29, -91, 103, 74, -19, -34, -59, 49, -2, 24, 13, 99, -116, -128, -64, -9, 112, 7 };
  private static final int[] rcon = { 1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145 };
  static byte[][] shifts0 = { { 0, 8, 16, 24 }, { 0, 8, 16, 24 }, { 0, 8, 16, 24 }, { 0, 8, 16, 32 }, { 0, 8, 24, 32 } };
  static byte[][] shifts1 = { { 0, 24, 16, 8 }, { 0, 32, 24, 16 }, { 0, 40, 32, 24 }, { 0, 48, 40, 24 }, { 0, 56, 40, 32 } };
  private long A0;
  private long A1;
  private long A2;
  private long A3;
  private int BC;
  private long BC_MASK;
  private int ROUNDS;
  private int blockBits;
  private boolean forEncryption;
  private byte[] shifts0SC;
  private byte[] shifts1SC;
  private long[][] workingKey;
  
  static
  {
    aLogtable = new byte[] { 0, 3, 5, 15, 17, 51, 85, -1, 26, 46, 114, -106, -95, -8, 19, 53, 95, -31, 56, 72, -40, 115, -107, -92, -9, 2, 6, 10, 30, 34, 102, -86, -27, 52, 92, -28, 55, 89, -21, 38, 106, -66, -39, 112, -112, -85, -26, 49, 83, -11, 4, 12, 20, 60, 68, -52, 79, -47, 104, -72, -45, 110, -78, -51, 76, -44, 103, -87, -32, 59, 77, -41, 98, -90, -15, 8, 24, 40, 120, -120, -125, -98, -71, -48, 107, -67, -36, 127, -127, -104, -77, -50, 73, -37, 118, -102, -75, -60, 87, -7, 16, 48, 80, -16, 11, 29, 39, 105, -69, -42, 97, -93, -2, 25, 43, 125, -121, -110, -83, -20, 47, 113, -109, -82, -23, 32, 96, -96, -5, 22, 58, 78, -46, 109, -73, -62, 93, -25, 50, 86, -6, 21, 63, 65, -61, 94, -30, 61, 71, -55, 64, -64, 91, -19, 44, 116, -100, -65, -38, 117, -97, -70, -43, 100, -84, -17, 42, 126, -126, -99, -68, -33, 122, -114, -119, -128, -101, -74, -63, 88, -24, 35, 101, -81, -22, 37, 111, -79, -56, 67, -59, 84, -4, 31, 33, 99, -91, -12, 7, 9, 27, 45, 119, -103, -80, -53, 70, -54, 69, -49, 74, -34, 121, -117, -122, -111, -88, -29, 62, 66, -58, 81, -13, 14, 18, 54, 90, -18, 41, 123, -115, -116, -113, -118, -123, -108, -89, -14, 13, 23, 57, 75, -35, 124, -124, -105, -94, -3, 28, 36, 108, -76, -57, 82, -10, 1, 3, 5, 15, 17, 51, 85, -1, 26, 46, 114, -106, -95, -8, 19, 53, 95, -31, 56, 72, -40, 115, -107, -92, -9, 2, 6, 10, 30, 34, 102, -86, -27, 52, 92, -28, 55, 89, -21, 38, 106, -66, -39, 112, -112, -85, -26, 49, 83, -11, 4, 12, 20, 60, 68, -52, 79, -47, 104, -72, -45, 110, -78, -51, 76, -44, 103, -87, -32, 59, 77, -41, 98, -90, -15, 8, 24, 40, 120, -120, -125, -98, -71, -48, 107, -67, -36, 127, -127, -104, -77, -50, 73, -37, 118, -102, -75, -60, 87, -7, 16, 48, 80, -16, 11, 29, 39, 105, -69, -42, 97, -93, -2, 25, 43, 125, -121, -110, -83, -20, 47, 113, -109, -82, -23, 32, 96, -96, -5, 22, 58, 78, -46, 109, -73, -62, 93, -25, 50, 86, -6, 21, 63, 65, -61, 94, -30, 61, 71, -55, 64, -64, 91, -19, 44, 116, -100, -65, -38, 117, -97, -70, -43, 100, -84, -17, 42, 126, -126, -99, -68, -33, 122, -114, -119, -128, -101, -74, -63, 88, -24, 35, 101, -81, -22, 37, 111, -79, -56, 67, -59, 84, -4, 31, 33, 99, -91, -12, 7, 9, 27, 45, 119, -103, -80, -53, 70, -54, 69, -49, 74, -34, 121, -117, -122, -111, -88, -29, 62, 66, -58, 81, -13, 14, 18, 54, 90, -18, 41, 123, -115, -116, -113, -118, -123, -108, -89, -14, 13, 23, 57, 75, -35, 124, -124, -105, -94, -3, 28, 36, 108, -76, -57, 82, -10, 1 };
  }
  
  public RijndaelEngine()
  {
    this(128);
  }
  
  public RijndaelEngine(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("unknown blocksize to Rijndael");
    case 128: 
      this.BC = 32;
      this.BC_MASK = 4294967295L;
      this.shifts0SC = shifts0[0];
      this.shifts1SC = shifts1[0];
    }
    for (;;)
    {
      this.blockBits = paramInt;
      return;
      this.BC = 40;
      this.BC_MASK = 1099511627775L;
      this.shifts0SC = shifts0[1];
      this.shifts1SC = shifts1[1];
      continue;
      this.BC = 48;
      this.BC_MASK = 281474976710655L;
      this.shifts0SC = shifts0[2];
      this.shifts1SC = shifts1[2];
      continue;
      this.BC = 56;
      this.BC_MASK = 72057594037927935L;
      this.shifts0SC = shifts0[3];
      this.shifts1SC = shifts1[3];
      continue;
      this.BC = 64;
      this.BC_MASK = -1L;
      this.shifts0SC = shifts0[4];
      this.shifts1SC = shifts1[4];
    }
  }
  
  private void InvMixColumn()
  {
    long l1 = 0L;
    long l2 = l1;
    long l3 = l1;
    long l4 = l1;
    int i = 0;
    if (i < this.BC)
    {
      int j = (int)(0xFF & this.A0 >> i);
      int k = (int)(0xFF & this.A1 >> i);
      int m = (int)(0xFF & this.A2 >> i);
      int n = (int)(0xFF & this.A3 >> i);
      int i1;
      label99:
      int i2;
      label120:
      int i3;
      if (j != 0)
      {
        i1 = 0xFF & logtable[(j & 0xFF)];
        if (k == 0) {
          break label330;
        }
        i2 = 0xFF & logtable[(k & 0xFF)];
        if (m == 0) {
          break label336;
        }
        i3 = 0xFF & logtable[(m & 0xFF)];
        label141:
        if (n == 0) {
          break label342;
        }
      }
      label330:
      label336:
      label342:
      for (int i4 = 0xFF & logtable[(n & 0xFF)];; i4 = -1)
      {
        l4 |= (0xFF & (mul0xe(i1) ^ mul0xb(i2) ^ mul0xd(i3) ^ mul0x9(i4))) << i;
        l3 |= (0xFF & (mul0xe(i2) ^ mul0xb(i3) ^ mul0xd(i4) ^ mul0x9(i1))) << i;
        l2 |= (0xFF & (mul0xe(i3) ^ mul0xb(i4) ^ mul0xd(i1) ^ mul0x9(i2))) << i;
        l1 |= (0xFF & (mul0xe(i4) ^ mul0xb(i1) ^ mul0xd(i2) ^ mul0x9(i3))) << i;
        i += 8;
        break;
        i1 = -1;
        break label99;
        i2 = -1;
        break label120;
        i3 = -1;
        break label141;
      }
    }
    this.A0 = l4;
    this.A1 = l3;
    this.A2 = l2;
    this.A3 = l1;
  }
  
  private void KeyAddition(long[] paramArrayOfLong)
  {
    this.A0 ^= paramArrayOfLong[0];
    this.A1 ^= paramArrayOfLong[1];
    this.A2 ^= paramArrayOfLong[2];
    this.A3 ^= paramArrayOfLong[3];
  }
  
  private void MixColumn()
  {
    long l1 = 0L;
    long l2 = l1;
    long l3 = l1;
    long l4 = l1;
    for (int i = 0; i < this.BC; i += 8)
    {
      int j = (int)(0xFF & this.A0 >> i);
      int k = (int)(0xFF & this.A1 >> i);
      int m = (int)(0xFF & this.A2 >> i);
      int n = (int)(0xFF & this.A3 >> i);
      l4 |= (0xFF & (n ^ m ^ mul0x2(j) ^ mul0x3(k))) << i;
      l3 |= (0xFF & (j ^ n ^ mul0x2(k) ^ mul0x3(m))) << i;
      l2 |= (0xFF & (k ^ j ^ mul0x2(m) ^ mul0x3(n))) << i;
      l1 |= (0xFF & (m ^ k ^ mul0x2(n) ^ mul0x3(j))) << i;
    }
    this.A0 = l4;
    this.A1 = l3;
    this.A2 = l2;
    this.A3 = l1;
  }
  
  private void ShiftRow(byte[] paramArrayOfByte)
  {
    this.A1 = shift(this.A1, paramArrayOfByte[1]);
    this.A2 = shift(this.A2, paramArrayOfByte[2]);
    this.A3 = shift(this.A3, paramArrayOfByte[3]);
  }
  
  private void Substitution(byte[] paramArrayOfByte)
  {
    this.A0 = applyS(this.A0, paramArrayOfByte);
    this.A1 = applyS(this.A1, paramArrayOfByte);
    this.A2 = applyS(this.A2, paramArrayOfByte);
    this.A3 = applyS(this.A3, paramArrayOfByte);
  }
  
  private long applyS(long paramLong, byte[] paramArrayOfByte)
  {
    long l = 0L;
    for (int i = 0; i < this.BC; i += 8) {
      l |= (0xFF & paramArrayOfByte[((int)(0xFF & paramLong >> i))]) << i;
    }
    return l;
  }
  
  private void decryptBlock(long[][] paramArrayOfLong)
  {
    KeyAddition(paramArrayOfLong[this.ROUNDS]);
    Substitution(Si);
    ShiftRow(this.shifts1SC);
    for (int i = -1 + this.ROUNDS; i > 0; i--)
    {
      KeyAddition(paramArrayOfLong[i]);
      InvMixColumn();
      Substitution(Si);
      ShiftRow(this.shifts1SC);
    }
    KeyAddition(paramArrayOfLong[0]);
  }
  
  private void encryptBlock(long[][] paramArrayOfLong)
  {
    KeyAddition(paramArrayOfLong[0]);
    for (int i = 1; i < this.ROUNDS; i++)
    {
      Substitution(S);
      ShiftRow(this.shifts0SC);
      MixColumn();
      KeyAddition(paramArrayOfLong[i]);
    }
    Substitution(S);
    ShiftRow(this.shifts0SC);
    KeyAddition(paramArrayOfLong[this.ROUNDS]);
  }
  
  private long[][] generateWorkingKey(byte[] paramArrayOfByte)
  {
    int i = 8 * paramArrayOfByte.length;
    int[] arrayOfInt1 = { 4, 64 };
    byte[][] arrayOfByte = (byte[][])Array.newInstance(Byte.TYPE, arrayOfInt1);
    int[] arrayOfInt2 = { 15, 4 };
    long[][] arrayOfLong = (long[][])Array.newInstance(Long.TYPE, arrayOfInt2);
    int j;
    switch (i)
    {
    default: 
      throw new IllegalArgumentException("Key length not 128/160/192/224/256 bits.");
    case 128: 
      j = 4;
      if (i < this.blockBits) {
        break;
      }
    }
    for (this.ROUNDS = (j + 6);; this.ROUNDS = (6 + this.BC / 8))
    {
      int k = 0;
      int m = 0;
      while (m < paramArrayOfByte.length)
      {
        byte[] arrayOfByte7 = arrayOfByte[(m % 4)];
        int i17 = m / 4;
        int i18 = k + 1;
        arrayOfByte7[i17] = paramArrayOfByte[k];
        m++;
        k = i18;
      }
      j = 5;
      break;
      j = 6;
      break;
      j = 7;
      break;
      j = 8;
      break;
    }
    int n = 0;
    int i1 = 0;
    int i2;
    for (;;)
    {
      i2 = 0;
      if (i1 >= j) {
        break;
      }
      int i15 = (1 + this.ROUNDS) * (this.BC / 8);
      i2 = 0;
      if (n >= i15) {
        break;
      }
      for (int i16 = 0; i16 < 4; i16++)
      {
        long[] arrayOfLong2 = arrayOfLong[(n / (this.BC / 8))];
        arrayOfLong2[i16] |= (0xFF & arrayOfByte[i16][i1]) << n * 8 % this.BC;
      }
      i1++;
      n++;
    }
    for (;;)
    {
      i2 = i5;
      if (n >= (1 + this.ROUNDS) * (this.BC / 8)) {
        break;
      }
      for (int i3 = 0; i3 < 4; i3++)
      {
        byte[] arrayOfByte6 = arrayOfByte[i3];
        arrayOfByte6[0] = ((byte)(arrayOfByte6[0] ^ S[(0xFF & arrayOfByte[((i3 + 1) % 4)][(j - 1)])]));
      }
      byte[] arrayOfByte1 = arrayOfByte[0];
      int i4 = arrayOfByte1[0];
      int[] arrayOfInt3 = rcon;
      int i5 = i2 + 1;
      arrayOfByte1[0] = ((byte)(i4 ^ arrayOfInt3[i2]));
      if (j <= 6) {
        for (int i13 = 1; i13 < j; i13++) {
          for (int i14 = 0; i14 < 4; i14++)
          {
            byte[] arrayOfByte5 = arrayOfByte[i14];
            arrayOfByte5[i13] = ((byte)(arrayOfByte5[i13] ^ arrayOfByte[i14][(i13 - 1)]));
          }
        }
      }
      for (int i6 = 1; i6 < 4; i6++) {
        for (int i12 = 0; i12 < 4; i12++)
        {
          byte[] arrayOfByte4 = arrayOfByte[i12];
          arrayOfByte4[i6] = ((byte)(arrayOfByte4[i6] ^ arrayOfByte[i12][(i6 - 1)]));
        }
      }
      for (int i7 = 0; i7 < 4; i7++)
      {
        byte[] arrayOfByte3 = arrayOfByte[i7];
        arrayOfByte3[4] = ((byte)(arrayOfByte3[4] ^ S[(0xFF & arrayOfByte[i7][3])]));
      }
      for (int i8 = 5; i8 < j; i8++) {
        for (int i11 = 0; i11 < 4; i11++)
        {
          byte[] arrayOfByte2 = arrayOfByte[i11];
          arrayOfByte2[i8] = ((byte)(arrayOfByte2[i8] ^ arrayOfByte[i11][(i8 - 1)]));
        }
      }
      int i9 = 0;
      while ((i9 < j) && (n < (1 + this.ROUNDS) * (this.BC / 8)))
      {
        for (int i10 = 0; i10 < 4; i10++)
        {
          long[] arrayOfLong1 = arrayOfLong[(n / (this.BC / 8))];
          arrayOfLong1[i10] |= (0xFF & arrayOfByte[i10][i9]) << n * 8 % this.BC;
        }
        i9++;
        n++;
      }
    }
    return arrayOfLong;
  }
  
  private byte mul0x2(int paramInt)
  {
    if (paramInt != 0) {
      return aLogtable[(25 + (0xFF & logtable[paramInt]))];
    }
    return 0;
  }
  
  private byte mul0x3(int paramInt)
  {
    if (paramInt != 0) {
      return aLogtable[(1 + (0xFF & logtable[paramInt]))];
    }
    return 0;
  }
  
  private byte mul0x9(int paramInt)
  {
    if (paramInt >= 0) {
      return aLogtable[(paramInt + 199)];
    }
    return 0;
  }
  
  private byte mul0xb(int paramInt)
  {
    if (paramInt >= 0) {
      return aLogtable[(paramInt + 104)];
    }
    return 0;
  }
  
  private byte mul0xd(int paramInt)
  {
    if (paramInt >= 0) {
      return aLogtable[(paramInt + 238)];
    }
    return 0;
  }
  
  private byte mul0xe(int paramInt)
  {
    if (paramInt >= 0) {
      return aLogtable[(paramInt + 223)];
    }
    return 0;
  }
  
  private void packBlock(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramInt;
    for (int j = 0; j != this.BC; j += 8)
    {
      int k = i + 1;
      paramArrayOfByte[i] = ((byte)(int)(this.A0 >> j));
      int m = k + 1;
      paramArrayOfByte[k] = ((byte)(int)(this.A1 >> j));
      int n = m + 1;
      paramArrayOfByte[m] = ((byte)(int)(this.A2 >> j));
      i = n + 1;
      paramArrayOfByte[n] = ((byte)(int)(this.A3 >> j));
    }
  }
  
  private long shift(long paramLong, int paramInt)
  {
    return (paramLong >>> paramInt | paramLong << this.BC - paramInt) & this.BC_MASK;
  }
  
  private void unpackBlock(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramInt + 1;
    this.A0 = (0xFF & paramArrayOfByte[paramInt]);
    int j = i + 1;
    this.A1 = (0xFF & paramArrayOfByte[i]);
    int k = j + 1;
    this.A2 = (0xFF & paramArrayOfByte[j]);
    int m = k + 1;
    this.A3 = (0xFF & paramArrayOfByte[k]);
    int n = 8;
    int i1 = m;
    while (n != this.BC)
    {
      long l1 = this.A0;
      int i2 = i1 + 1;
      this.A0 = (l1 | (0xFF & paramArrayOfByte[i1]) << n);
      long l2 = this.A1;
      int i3 = i2 + 1;
      this.A1 = (l2 | (0xFF & paramArrayOfByte[i2]) << n);
      long l3 = this.A2;
      int i4 = i3 + 1;
      this.A2 = (l3 | (0xFF & paramArrayOfByte[i3]) << n);
      long l4 = this.A3;
      i1 = i4 + 1;
      this.A3 = (l4 | (0xFF & paramArrayOfByte[i4]) << n);
      n += 8;
    }
  }
  
  public String getAlgorithmName()
  {
    return "Rijndael";
  }
  
  public int getBlockSize()
  {
    return this.BC / 2;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof KeyParameter))
    {
      this.workingKey = generateWorkingKey(((KeyParameter)paramCipherParameters).getKey());
      this.forEncryption = paramBoolean;
      return;
    }
    throw new IllegalArgumentException("invalid parameter passed to Rijndael init - " + paramCipherParameters.getClass().getName());
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if (this.workingKey == null) {
      throw new IllegalStateException("Rijndael engine not initialised");
    }
    if (paramInt1 + this.BC / 2 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + this.BC / 2 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    if (this.forEncryption)
    {
      unpackBlock(paramArrayOfByte1, paramInt1);
      encryptBlock(this.workingKey);
      packBlock(paramArrayOfByte2, paramInt2);
    }
    for (;;)
    {
      return this.BC / 2;
      unpackBlock(paramArrayOfByte1, paramInt1);
      decryptBlock(this.workingKey);
      packBlock(paramArrayOfByte2, paramInt2);
    }
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RijndaelEngine
 * JD-Core Version:    0.7.0.1
 */