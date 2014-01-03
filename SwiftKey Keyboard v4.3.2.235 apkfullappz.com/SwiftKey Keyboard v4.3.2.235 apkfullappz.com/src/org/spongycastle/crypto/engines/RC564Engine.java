package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.RC5Parameters;

public class RC564Engine
  implements BlockCipher
{
  private static final long P64 = -5196783011329398165L;
  private static final long Q64 = -7046029254386353131L;
  private static final int bytesPerWord = 8;
  private static final int wordSize = 64;
  private long[] _S = null;
  private int _noRounds = 12;
  private boolean forEncryption;
  
  private long bytesToWord(byte[] paramArrayOfByte, int paramInt)
  {
    long l = 0L;
    for (int i = 7; i >= 0; i--) {
      l = (l << 8) + (0xFF & paramArrayOfByte[(i + paramInt)]);
    }
    return l;
  }
  
  private int decryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    long l1 = bytesToWord(paramArrayOfByte1, paramInt1);
    long l2 = bytesToWord(paramArrayOfByte1, paramInt1 + 8);
    for (int i = this._noRounds; i > 0; i--)
    {
      l2 = l1 ^ rotateRight(l2 - this._S[(1 + i * 2)], l1);
      l1 = l2 ^ rotateRight(l1 - this._S[(i * 2)], l2);
    }
    wordToBytes(l1 - this._S[0], paramArrayOfByte2, paramInt2);
    wordToBytes(l2 - this._S[1], paramArrayOfByte2, paramInt2 + 8);
    return 16;
  }
  
  private int encryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    long l1 = bytesToWord(paramArrayOfByte1, paramInt1) + this._S[0];
    long l2 = bytesToWord(paramArrayOfByte1, paramInt1 + 8) + this._S[1];
    for (int i = 1; i <= this._noRounds; i++)
    {
      l1 = rotateLeft(l1 ^ l2, l2) + this._S[(i * 2)];
      l2 = rotateLeft(l2 ^ l1, l1) + this._S[(1 + i * 2)];
    }
    wordToBytes(l1, paramArrayOfByte2, paramInt2);
    wordToBytes(l2, paramArrayOfByte2, paramInt2 + 8);
    return 16;
  }
  
  private long rotateLeft(long paramLong1, long paramLong2)
  {
    return paramLong1 << (int)(paramLong2 & 0x3F) | paramLong1 >>> (int)(64L - (0x3F & paramLong2));
  }
  
  private long rotateRight(long paramLong1, long paramLong2)
  {
    return paramLong1 >>> (int)(paramLong2 & 0x3F) | paramLong1 << (int)(64L - (0x3F & paramLong2));
  }
  
  private void setKey(byte[] paramArrayOfByte)
  {
    long[] arrayOfLong1 = new long[(7 + paramArrayOfByte.length) / 8];
    for (int i = 0; i != paramArrayOfByte.length; i++)
    {
      int i2 = i / 8;
      arrayOfLong1[i2] += ((0xFF & paramArrayOfByte[i]) << 8 * (i % 8));
    }
    this._S = new long[2 * (1 + this._noRounds)];
    this._S[0] = -5196783011329398165L;
    for (int j = 1; j < this._S.length; j++) {
      this._S[j] = (-7046029254386353131L + this._S[(j - 1)]);
    }
    if (arrayOfLong1.length > this._S.length) {}
    for (int k = 3 * arrayOfLong1.length;; k = 3 * this._S.length)
    {
      long l1 = 0L;
      long l2 = 0L;
      int m = 0;
      int n = 0;
      for (int i1 = 0; i1 < k; i1++)
      {
        long[] arrayOfLong2 = this._S;
        l1 = rotateLeft(l2 + (l1 + this._S[m]), 3L);
        arrayOfLong2[m] = l1;
        l2 = rotateLeft(l2 + (l1 + arrayOfLong1[n]), l1 + l2);
        arrayOfLong1[n] = l2;
        m = (m + 1) % this._S.length;
        n = (n + 1) % arrayOfLong1.length;
      }
    }
  }
  
  private void wordToBytes(long paramLong, byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 0; i < 8; i++)
    {
      paramArrayOfByte[(i + paramInt)] = ((byte)(int)paramLong);
      paramLong >>>= 8;
    }
  }
  
  public String getAlgorithmName()
  {
    return "RC5-64";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (!(paramCipherParameters instanceof RC5Parameters)) {
      throw new IllegalArgumentException("invalid parameter passed to RC564 init - " + paramCipherParameters.getClass().getName());
    }
    RC5Parameters localRC5Parameters = (RC5Parameters)paramCipherParameters;
    this.forEncryption = paramBoolean;
    this._noRounds = localRC5Parameters.getRounds();
    setKey(localRC5Parameters.getKey());
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if (this.forEncryption) {
      return encryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
    return decryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RC564Engine
 * JD-Core Version:    0.7.0.1
 */