package org.apache.commons.codec.binary;

public final class Base64
  extends BaseNCodec
{
  static final byte[] CHUNK_SEPARATOR = { 13, 10 };
  private static final byte[] DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
  private static final byte[] STANDARD_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
  private static final byte[] URL_SAFE_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
  private int bitWorkArea;
  private final int decodeSize;
  private final byte[] decodeTable;
  private final int encodeSize;
  private final byte[] encodeTable;
  private final byte[] lineSeparator;
  
  public Base64()
  {
    this(0);
  }
  
  public Base64(int paramInt)
  {
    this(paramInt, CHUNK_SEPARATOR);
  }
  
  public Base64(int paramInt, byte[] paramArrayOfByte)
  {
    this(paramInt, paramArrayOfByte, false);
  }
  
  public Base64(int paramInt, byte[] paramArrayOfByte, boolean paramBoolean) {}
  
  public Base64(boolean paramBoolean)
  {
    this(76, CHUNK_SEPARATOR, paramBoolean);
  }
  
  public static byte[] encodeBase64(byte[] paramArrayOfByte)
  {
    return encodeBase64(paramArrayOfByte, false);
  }
  
  public static byte[] encodeBase64(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    return encodeBase64(paramArrayOfByte, paramBoolean, false);
  }
  
  public static byte[] encodeBase64(byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2)
  {
    return encodeBase64(paramArrayOfByte, paramBoolean1, paramBoolean2, 2147483647);
  }
  
  public static byte[] encodeBase64(byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return paramArrayOfByte;
    }
    if (paramBoolean1) {}
    for (Base64 localBase64 = new Base64(paramBoolean2);; localBase64 = new Base64(0, CHUNK_SEPARATOR, paramBoolean2))
    {
      long l = localBase64.getEncodedLength(paramArrayOfByte);
      if (l <= paramInt) {
        break;
      }
      throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + l + ") than the specified maximum size of " + paramInt);
    }
    return localBase64.encode(paramArrayOfByte);
  }
  
  void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.eof) {}
    do
    {
      return;
      if (paramInt2 >= 0) {
        break;
      }
      this.eof = true;
    } while ((this.modulus == 0) && (this.lineLength == 0));
    ensureBufferSize(this.encodeSize);
    int i4 = this.pos;
    switch (this.modulus)
    {
    }
    for (;;)
    {
      this.currentLinePos += this.pos - i4;
      if ((this.lineLength <= 0) || (this.currentLinePos <= 0)) {
        break;
      }
      System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
      this.pos += this.lineSeparator.length;
      return;
      byte[] arrayOfByte9 = this.buffer;
      int i9 = this.pos;
      this.pos = (i9 + 1);
      arrayOfByte9[i9] = this.encodeTable[(0x3F & this.bitWorkArea >> 2)];
      byte[] arrayOfByte10 = this.buffer;
      int i10 = this.pos;
      this.pos = (i10 + 1);
      arrayOfByte10[i10] = this.encodeTable[(0x3F & this.bitWorkArea << 4)];
      if (this.encodeTable == STANDARD_ENCODE_TABLE)
      {
        byte[] arrayOfByte11 = this.buffer;
        int i11 = this.pos;
        this.pos = (i11 + 1);
        arrayOfByte11[i11] = 61;
        byte[] arrayOfByte12 = this.buffer;
        int i12 = this.pos;
        this.pos = (i12 + 1);
        arrayOfByte12[i12] = 61;
        continue;
        byte[] arrayOfByte5 = this.buffer;
        int i5 = this.pos;
        this.pos = (i5 + 1);
        arrayOfByte5[i5] = this.encodeTable[(0x3F & this.bitWorkArea >> 10)];
        byte[] arrayOfByte6 = this.buffer;
        int i6 = this.pos;
        this.pos = (i6 + 1);
        arrayOfByte6[i6] = this.encodeTable[(0x3F & this.bitWorkArea >> 4)];
        byte[] arrayOfByte7 = this.buffer;
        int i7 = this.pos;
        this.pos = (i7 + 1);
        arrayOfByte7[i7] = this.encodeTable[(0x3F & this.bitWorkArea << 2)];
        if (this.encodeTable == STANDARD_ENCODE_TABLE)
        {
          byte[] arrayOfByte8 = this.buffer;
          int i8 = this.pos;
          this.pos = (i8 + 1);
          arrayOfByte8[i8] = 61;
        }
      }
    }
    int i = 0;
    int k;
    for (int j = paramInt1; i < paramInt2; j = k)
    {
      ensureBufferSize(this.encodeSize);
      this.modulus = ((1 + this.modulus) % 3);
      k = j + 1;
      int m = paramArrayOfByte[j];
      if (m < 0) {
        m += 256;
      }
      this.bitWorkArea = (m + (this.bitWorkArea << 8));
      if (this.modulus == 0)
      {
        byte[] arrayOfByte1 = this.buffer;
        int n = this.pos;
        this.pos = (n + 1);
        arrayOfByte1[n] = this.encodeTable[(0x3F & this.bitWorkArea >> 18)];
        byte[] arrayOfByte2 = this.buffer;
        int i1 = this.pos;
        this.pos = (i1 + 1);
        arrayOfByte2[i1] = this.encodeTable[(0x3F & this.bitWorkArea >> 12)];
        byte[] arrayOfByte3 = this.buffer;
        int i2 = this.pos;
        this.pos = (i2 + 1);
        arrayOfByte3[i2] = this.encodeTable[(0x3F & this.bitWorkArea >> 6)];
        byte[] arrayOfByte4 = this.buffer;
        int i3 = this.pos;
        this.pos = (i3 + 1);
        arrayOfByte4[i3] = this.encodeTable[(0x3F & this.bitWorkArea)];
        this.currentLinePos = (4 + this.currentLinePos);
        if ((this.lineLength > 0) && (this.lineLength <= this.currentLinePos))
        {
          System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
          this.pos += this.lineSeparator.length;
          this.currentLinePos = 0;
        }
      }
      i++;
    }
  }
  
  protected boolean isInAlphabet(byte paramByte)
  {
    return (paramByte >= 0) && (paramByte < this.decodeTable.length) && (this.decodeTable[paramByte] != -1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.apache.commons.codec.binary.Base64
 * JD-Core Version:    0.7.0.1
 */