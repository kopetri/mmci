package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.NTRUEncryptionParameters;
import org.spongycastle.util.Arrays;

public class IndexGenerator
{
  private int N;
  private BitString buf;
  private int c;
  private int counter;
  private int hLen;
  private Digest hashAlg;
  private boolean initialized;
  private int minCallsR;
  private int remLen;
  private byte[] seed;
  private int totLen;
  
  IndexGenerator(byte[] paramArrayOfByte, NTRUEncryptionParameters paramNTRUEncryptionParameters)
  {
    this.seed = paramArrayOfByte;
    this.N = paramNTRUEncryptionParameters.N;
    this.c = paramNTRUEncryptionParameters.c;
    this.minCallsR = paramNTRUEncryptionParameters.minCallsR;
    this.totLen = 0;
    this.remLen = 0;
    this.counter = 0;
    this.hashAlg = paramNTRUEncryptionParameters.hashAlg;
    this.hLen = this.hashAlg.getDigestSize();
    this.initialized = false;
  }
  
  private void appendHash(BitString paramBitString, byte[] paramArrayOfByte)
  {
    this.hashAlg.update(this.seed, 0, this.seed.length);
    putInt(this.hashAlg, this.counter);
    this.hashAlg.doFinal(paramArrayOfByte, 0);
    paramBitString.appendBits(paramArrayOfByte);
  }
  
  private static byte[] copyOf(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    if (paramInt < paramArrayOfByte.length) {}
    for (;;)
    {
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramInt);
      return arrayOfByte;
      paramInt = paramArrayOfByte.length;
    }
  }
  
  private void putInt(Digest paramDigest, int paramInt)
  {
    paramDigest.update((byte)(paramInt >> 24));
    paramDigest.update((byte)(paramInt >> 16));
    paramDigest.update((byte)(paramInt >> 8));
    paramDigest.update((byte)paramInt);
  }
  
  int nextIndex()
  {
    if (!this.initialized)
    {
      this.buf = new BitString();
      byte[] arrayOfByte2 = new byte[this.hashAlg.getDigestSize()];
      while (this.counter < this.minCallsR)
      {
        appendHash(this.buf, arrayOfByte2);
        this.counter = (1 + this.counter);
      }
      this.totLen = (8 * this.minCallsR * this.hLen);
      this.remLen = this.totLen;
      this.initialized = true;
    }
    this.totLen += this.c;
    BitString localBitString = this.buf.getTrailing(this.remLen);
    if (this.remLen < this.c)
    {
      int j = this.c - this.remLen;
      int k = this.counter + (-1 + (j + this.hLen)) / this.hLen;
      byte[] arrayOfByte1 = new byte[this.hashAlg.getDigestSize()];
      while (this.counter < k)
      {
        appendHash(localBitString, arrayOfByte1);
        this.counter = (1 + this.counter);
        if (j > 8 * this.hLen) {
          j -= 8 * this.hLen;
        }
      }
      this.remLen = (8 * this.hLen - j);
      this.buf = new BitString();
      this.buf.appendBits(arrayOfByte1);
    }
    for (;;)
    {
      int i = localBitString.getLeadingAsInt(this.c);
      if (i >= (1 << this.c) - (1 << this.c) % this.N) {
        break;
      }
      return i % this.N;
      this.remLen -= this.c;
    }
  }
  
  public static class BitString
  {
    byte[] bytes = new byte[4];
    int lastByteBits;
    int numBytes;
    
    public void appendBits(byte paramByte)
    {
      if (this.numBytes == this.bytes.length) {
        this.bytes = IndexGenerator.copyOf(this.bytes, 2 * this.bytes.length);
      }
      if (this.numBytes == 0)
      {
        this.numBytes = 1;
        this.bytes[0] = paramByte;
        this.lastByteBits = 8;
        return;
      }
      if (this.lastByteBits == 8)
      {
        byte[] arrayOfByte3 = this.bytes;
        int m = this.numBytes;
        this.numBytes = (m + 1);
        arrayOfByte3[m] = paramByte;
        return;
      }
      int i = 8 - this.lastByteBits;
      byte[] arrayOfByte1 = this.bytes;
      int j = -1 + this.numBytes;
      arrayOfByte1[j] = ((byte)(arrayOfByte1[j] | (paramByte & 0xFF) << this.lastByteBits));
      byte[] arrayOfByte2 = this.bytes;
      int k = this.numBytes;
      this.numBytes = (k + 1);
      arrayOfByte2[k] = ((byte)((paramByte & 0xFF) >> i));
    }
    
    void appendBits(byte[] paramArrayOfByte)
    {
      for (int i = 0; i != paramArrayOfByte.length; i++) {
        appendBits(paramArrayOfByte[i]);
      }
    }
    
    public byte[] getBytes()
    {
      return Arrays.clone(this.bytes);
    }
    
    public int getLeadingAsInt(int paramInt)
    {
      int i = 8 * (-1 + this.numBytes) + this.lastByteBits - paramInt;
      int j = i / 8;
      int k = i % 8;
      int m = (0xFF & this.bytes[j]) >>> k;
      int n = 8 - k;
      for (int i1 = j + 1; i1 < this.numBytes; i1++)
      {
        m |= (0xFF & this.bytes[i1]) << n;
        n += 8;
      }
      return m;
    }
    
    public BitString getTrailing(int paramInt)
    {
      BitString localBitString = new BitString();
      localBitString.numBytes = ((paramInt + 7) / 8);
      localBitString.bytes = new byte[localBitString.numBytes];
      for (int i = 0; i < localBitString.numBytes; i++) {
        localBitString.bytes[i] = this.bytes[i];
      }
      localBitString.lastByteBits = (paramInt % 8);
      if (localBitString.lastByteBits == 0)
      {
        localBitString.lastByteBits = 8;
        return localBitString;
      }
      int j = 32 - localBitString.lastByteBits;
      localBitString.bytes[(-1 + localBitString.numBytes)] = ((byte)(localBitString.bytes[(-1 + localBitString.numBytes)] << j >>> j));
      return localBitString;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.IndexGenerator
 * JD-Core Version:    0.7.0.1
 */