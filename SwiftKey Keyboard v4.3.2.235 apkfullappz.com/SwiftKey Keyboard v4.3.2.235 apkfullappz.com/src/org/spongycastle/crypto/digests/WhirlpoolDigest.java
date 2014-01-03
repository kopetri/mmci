package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Arrays;

public final class WhirlpoolDigest
  implements ExtendedDigest
{
  private static final int BITCOUNT_ARRAY_SIZE = 32;
  private static final int BYTE_LENGTH = 64;
  private static final long[] C0;
  private static final long[] C1;
  private static final long[] C2;
  private static final long[] C3;
  private static final long[] C4;
  private static final long[] C5;
  private static final long[] C6;
  private static final long[] C7;
  private static final int DIGEST_LENGTH_BYTES = 64;
  private static final short[] EIGHT;
  private static final int REDUCTION_POLYNOMIAL = 285;
  private static final int ROUNDS = 10;
  private static final int[] SBOX = { 24, 35, 198, 232, 135, 184, 1, 79, 54, 166, 210, 245, 121, 111, 145, 82, 96, 188, 155, 142, 163, 12, 123, 53, 29, 224, 215, 194, 46, 75, 254, 87, 21, 119, 55, 229, 159, 240, 74, 218, 88, 201, 41, 10, 177, 160, 107, 133, 189, 93, 16, 244, 203, 62, 5, 103, 228, 39, 65, 139, 167, 125, 149, 216, 251, 238, 124, 102, 221, 23, 71, 158, 202, 45, 191, 7, 173, 90, 131, 51, 99, 2, 170, 113, 200, 25, 73, 217, 242, 227, 91, 136, 154, 38, 50, 176, 233, 15, 213, 128, 190, 205, 52, 72, 255, 122, 144, 95, 32, 104, 26, 174, 180, 84, 147, 34, 100, 241, 115, 18, 64, 8, 195, 236, 219, 161, 141, 61, 151, 0, 207, 43, 118, 130, 214, 27, 181, 175, 106, 80, 69, 243, 48, 239, 63, 85, 162, 234, 101, 186, 47, 192, 222, 28, 253, 77, 146, 117, 6, 138, 178, 230, 14, 31, 98, 212, 168, 150, 249, 197, 37, 89, 132, 114, 57, 76, 94, 120, 56, 140, 209, 165, 226, 97, 179, 33, 156, 30, 67, 199, 252, 4, 81, 153, 109, 13, 250, 223, 126, 36, 59, 171, 206, 17, 143, 78, 183, 235, 60, 129, 148, 247, 185, 19, 44, 211, 231, 110, 196, 3, 86, 68, 127, 169, 42, 187, 193, 83, 220, 11, 157, 108, 49, 116, 246, 70, 172, 137, 20, 225, 22, 58, 105, 9, 112, 182, 208, 237, 204, 66, 152, 164, 40, 92, 248, 134 };
  private long[] _K = new long[8];
  private long[] _L = new long[8];
  private short[] _bitCount = new short[32];
  private long[] _block = new long[8];
  private byte[] _buffer = new byte[64];
  private int _bufferPos = 0;
  private long[] _hash = new long[8];
  private final long[] _rc = new long[11];
  private long[] _state = new long[8];
  
  static
  {
    C0 = new long[256];
    C1 = new long[256];
    C2 = new long[256];
    C3 = new long[256];
    C4 = new long[256];
    C5 = new long[256];
    C6 = new long[256];
    C7 = new long[256];
    short[] arrayOfShort = new short[32];
    EIGHT = arrayOfShort;
    arrayOfShort[31] = 8;
  }
  
  public WhirlpoolDigest()
  {
    for (int i = 0; i < 256; i++)
    {
      int m = SBOX[i];
      int n = maskWithReductionPolynomial(m << 1);
      int i1 = maskWithReductionPolynomial(n << 1);
      int i2 = i1 ^ m;
      int i3 = maskWithReductionPolynomial(i1 << 1);
      int i4 = i3 ^ m;
      C0[i] = packIntoLong(m, m, i1, m, i3, i2, n, i4);
      C1[i] = packIntoLong(i4, m, m, i1, m, i3, i2, n);
      C2[i] = packIntoLong(n, i4, m, m, i1, m, i3, i2);
      C3[i] = packIntoLong(i2, n, i4, m, m, i1, m, i3);
      C4[i] = packIntoLong(i3, i2, n, i4, m, m, i1, m);
      C5[i] = packIntoLong(m, i3, i2, n, i4, m, m, i1);
      C6[i] = packIntoLong(i1, m, i3, i2, n, i4, m, m);
      C7[i] = packIntoLong(m, i1, m, i3, i2, n, i4, m);
    }
    this._rc[0] = 0L;
    for (int j = 1; j <= 10; j++)
    {
      int k = 8 * (j - 1);
      this._rc[j] = (0x0 & C0[k] ^ 0x0 & C1[(k + 1)] ^ 0x0 & C2[(k + 2)] ^ 0x0 & C3[(k + 3)] ^ 0xFF000000 & C4[(k + 4)] ^ 0xFF0000 & C5[(k + 5)] ^ 0xFF00 & C6[(k + 6)] ^ 0xFF & C7[(k + 7)]);
    }
  }
  
  public WhirlpoolDigest(WhirlpoolDigest paramWhirlpoolDigest)
  {
    System.arraycopy(paramWhirlpoolDigest._rc, 0, this._rc, 0, this._rc.length);
    System.arraycopy(paramWhirlpoolDigest._buffer, 0, this._buffer, 0, this._buffer.length);
    this._bufferPos = paramWhirlpoolDigest._bufferPos;
    System.arraycopy(paramWhirlpoolDigest._bitCount, 0, this._bitCount, 0, this._bitCount.length);
    System.arraycopy(paramWhirlpoolDigest._hash, 0, this._hash, 0, this._hash.length);
    System.arraycopy(paramWhirlpoolDigest._K, 0, this._K, 0, this._K.length);
    System.arraycopy(paramWhirlpoolDigest._L, 0, this._L, 0, this._L.length);
    System.arraycopy(paramWhirlpoolDigest._block, 0, this._block, 0, this._block.length);
    System.arraycopy(paramWhirlpoolDigest._state, 0, this._state, 0, this._state.length);
  }
  
  private long bytesToLongFromBuffer(byte[] paramArrayOfByte, int paramInt)
  {
    return (0xFF & paramArrayOfByte[(paramInt + 0)]) << 56 | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 48 | (0xFF & paramArrayOfByte[(paramInt + 2)]) << 40 | (0xFF & paramArrayOfByte[(paramInt + 3)]) << 32 | (0xFF & paramArrayOfByte[(paramInt + 4)]) << 24 | (0xFF & paramArrayOfByte[(paramInt + 5)]) << 16 | (0xFF & paramArrayOfByte[(paramInt + 6)]) << 8 | 0xFF & paramArrayOfByte[(paramInt + 7)];
  }
  
  private void convertLongToByteArray(long paramLong, byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 0; i < 8; i++) {
      paramArrayOfByte[(paramInt + i)] = ((byte)(int)(0xFF & paramLong >> 56 - i * 8));
    }
  }
  
  private byte[] copyBitLength()
  {
    byte[] arrayOfByte = new byte[32];
    for (int i = 0; i < arrayOfByte.length; i++) {
      arrayOfByte[i] = ((byte)(0xFF & this._bitCount[i]));
    }
    return arrayOfByte;
  }
  
  private void finish()
  {
    byte[] arrayOfByte1 = copyBitLength();
    byte[] arrayOfByte2 = this._buffer;
    int i = this._bufferPos;
    this._bufferPos = (i + 1);
    arrayOfByte2[i] = ((byte)(0x80 | arrayOfByte2[i]));
    if (this._bufferPos == this._buffer.length) {
      processFilledBuffer(this._buffer, 0);
    }
    if (this._bufferPos > 32) {
      while (this._bufferPos != 0) {
        update((byte)0);
      }
    }
    while (this._bufferPos <= 32) {
      update((byte)0);
    }
    System.arraycopy(arrayOfByte1, 0, this._buffer, 32, arrayOfByte1.length);
    processFilledBuffer(this._buffer, 0);
  }
  
  private void increment()
  {
    int i = 0;
    for (int j = -1 + this._bitCount.length; j >= 0; j--)
    {
      int k = i + ((0xFF & this._bitCount[j]) + EIGHT[j]);
      i = k >>> 8;
      this._bitCount[j] = ((short)(k & 0xFF));
    }
  }
  
  private int maskWithReductionPolynomial(int paramInt)
  {
    int i = paramInt;
    if (paramInt >= 256L) {
      i = paramInt ^ 0x11D;
    }
    return i;
  }
  
  private long packIntoLong(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    return paramInt1 << 56 ^ paramInt2 << 48 ^ paramInt3 << 40 ^ paramInt4 << 32 ^ paramInt5 << 24 ^ paramInt6 << 16 ^ paramInt7 << 8 ^ paramInt8;
  }
  
  private void processFilledBuffer(byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 0; i < this._state.length; i++) {
      this._block[i] = bytesToLongFromBuffer(this._buffer, i * 8);
    }
    processBlock();
    this._bufferPos = 0;
    Arrays.fill(this._buffer, (byte)0);
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    finish();
    for (int i = 0; i < 8; i++) {
      convertLongToByteArray(this._hash[i], paramArrayOfByte, paramInt + i * 8);
    }
    reset();
    return getDigestSize();
  }
  
  public String getAlgorithmName()
  {
    return "Whirlpool";
  }
  
  public int getByteLength()
  {
    return 64;
  }
  
  public int getDigestSize()
  {
    return 64;
  }
  
  protected void processBlock()
  {
    for (int i = 0; i < 8; i++)
    {
      long[] arrayOfLong19 = this._state;
      long l1 = this._block[i];
      long[] arrayOfLong20 = this._K;
      long l2 = this._hash[i];
      arrayOfLong20[i] = l2;
      arrayOfLong19[i] = (l1 ^ l2);
    }
    for (int j = 1; j <= 10; j++)
    {
      for (int m = 0; m < 8; m++)
      {
        this._L[m] = 0L;
        long[] arrayOfLong11 = this._L;
        arrayOfLong11[m] ^= C0[(0xFF & (int)(this._K[(0x7 & m + 0)] >>> 56))];
        long[] arrayOfLong12 = this._L;
        arrayOfLong12[m] ^= C1[(0xFF & (int)(this._K[(0x7 & m - 1)] >>> 48))];
        long[] arrayOfLong13 = this._L;
        arrayOfLong13[m] ^= C2[(0xFF & (int)(this._K[(0x7 & m - 2)] >>> 40))];
        long[] arrayOfLong14 = this._L;
        arrayOfLong14[m] ^= C3[(0xFF & (int)(this._K[(0x7 & m - 3)] >>> 32))];
        long[] arrayOfLong15 = this._L;
        arrayOfLong15[m] ^= C4[(0xFF & (int)(this._K[(0x7 & m - 4)] >>> 24))];
        long[] arrayOfLong16 = this._L;
        arrayOfLong16[m] ^= C5[(0xFF & (int)(this._K[(0x7 & m - 5)] >>> 16))];
        long[] arrayOfLong17 = this._L;
        arrayOfLong17[m] ^= C6[(0xFF & (int)(this._K[(0x7 & m - 6)] >>> 8))];
        long[] arrayOfLong18 = this._L;
        arrayOfLong18[m] ^= C7[(0xFF & (int)this._K[(0x7 & m - 7)])];
      }
      System.arraycopy(this._L, 0, this._K, 0, this._K.length);
      long[] arrayOfLong2 = this._K;
      arrayOfLong2[0] ^= this._rc[j];
      for (int n = 0; n < 8; n++)
      {
        this._L[n] = this._K[n];
        long[] arrayOfLong3 = this._L;
        arrayOfLong3[n] ^= C0[(0xFF & (int)(this._state[(0x7 & n + 0)] >>> 56))];
        long[] arrayOfLong4 = this._L;
        arrayOfLong4[n] ^= C1[(0xFF & (int)(this._state[(0x7 & n - 1)] >>> 48))];
        long[] arrayOfLong5 = this._L;
        arrayOfLong5[n] ^= C2[(0xFF & (int)(this._state[(0x7 & n - 2)] >>> 40))];
        long[] arrayOfLong6 = this._L;
        arrayOfLong6[n] ^= C3[(0xFF & (int)(this._state[(0x7 & n - 3)] >>> 32))];
        long[] arrayOfLong7 = this._L;
        arrayOfLong7[n] ^= C4[(0xFF & (int)(this._state[(0x7 & n - 4)] >>> 24))];
        long[] arrayOfLong8 = this._L;
        arrayOfLong8[n] ^= C5[(0xFF & (int)(this._state[(0x7 & n - 5)] >>> 16))];
        long[] arrayOfLong9 = this._L;
        arrayOfLong9[n] ^= C6[(0xFF & (int)(this._state[(0x7 & n - 6)] >>> 8))];
        long[] arrayOfLong10 = this._L;
        arrayOfLong10[n] ^= C7[(0xFF & (int)this._state[(0x7 & n - 7)])];
      }
      System.arraycopy(this._L, 0, this._state, 0, this._state.length);
    }
    for (int k = 0; k < 8; k++)
    {
      long[] arrayOfLong1 = this._hash;
      arrayOfLong1[k] ^= this._state[k] ^ this._block[k];
    }
  }
  
  public void reset()
  {
    this._bufferPos = 0;
    Arrays.fill(this._bitCount, (short)0);
    Arrays.fill(this._buffer, (byte)0);
    Arrays.fill(this._hash, 0L);
    Arrays.fill(this._K, 0L);
    Arrays.fill(this._L, 0L);
    Arrays.fill(this._block, 0L);
    Arrays.fill(this._state, 0L);
  }
  
  public void update(byte paramByte)
  {
    this._buffer[this._bufferPos] = paramByte;
    this._bufferPos = (1 + this._bufferPos);
    if (this._bufferPos == this._buffer.length) {
      processFilledBuffer(this._buffer, 0);
    }
    increment();
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    while (paramInt2 > 0)
    {
      update(paramArrayOfByte[paramInt1]);
      paramInt1++;
      paramInt2--;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.digests.WhirlpoolDigest
 * JD-Core Version:    0.7.0.1
 */