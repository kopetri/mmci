package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.RC5Parameters;

public class RC532Engine
  implements BlockCipher
{
  private static final int P32 = -1209970333;
  private static final int Q32 = -1640531527;
  private int[] _S = null;
  private int _noRounds = 12;
  private boolean forEncryption;
  
  private int bytesToWord(byte[] paramArrayOfByte, int paramInt)
  {
    return 0xFF & paramArrayOfByte[paramInt] | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 8 | (0xFF & paramArrayOfByte[(paramInt + 2)]) << 16 | (0xFF & paramArrayOfByte[(paramInt + 3)]) << 24;
  }
  
  private int decryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = bytesToWord(paramArrayOfByte1, paramInt1);
    int j = bytesToWord(paramArrayOfByte1, paramInt1 + 4);
    for (int k = this._noRounds; k > 0; k--)
    {
      j = i ^ rotateRight(j - this._S[(1 + k * 2)], i);
      i = j ^ rotateRight(i - this._S[(k * 2)], j);
    }
    wordToBytes(i - this._S[0], paramArrayOfByte2, paramInt2);
    wordToBytes(j - this._S[1], paramArrayOfByte2, paramInt2 + 4);
    return 8;
  }
  
  private int encryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = bytesToWord(paramArrayOfByte1, paramInt1) + this._S[0];
    int j = bytesToWord(paramArrayOfByte1, paramInt1 + 4) + this._S[1];
    for (int k = 1; k <= this._noRounds; k++)
    {
      i = rotateLeft(i ^ j, j) + this._S[(k * 2)];
      j = rotateLeft(j ^ i, i) + this._S[(1 + k * 2)];
    }
    wordToBytes(i, paramArrayOfByte2, paramInt2);
    wordToBytes(j, paramArrayOfByte2, paramInt2 + 4);
    return 8;
  }
  
  private int rotateLeft(int paramInt1, int paramInt2)
  {
    return paramInt1 << (paramInt2 & 0x1F) | paramInt1 >>> 32 - (paramInt2 & 0x1F);
  }
  
  private int rotateRight(int paramInt1, int paramInt2)
  {
    return paramInt1 >>> (paramInt2 & 0x1F) | paramInt1 << 32 - (paramInt2 & 0x1F);
  }
  
  private void setKey(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt1 = new int[(3 + paramArrayOfByte.length) / 4];
    for (int i = 0; i != paramArrayOfByte.length; i++)
    {
      int i4 = i / 4;
      arrayOfInt1[i4] += ((0xFF & paramArrayOfByte[i]) << 8 * (i % 4));
    }
    this._S = new int[2 * (1 + this._noRounds)];
    this._S[0] = -1209970333;
    for (int j = 1; j < this._S.length; j++) {
      this._S[j] = (-1640531527 + this._S[(j - 1)]);
    }
    if (arrayOfInt1.length > this._S.length) {}
    for (int k = 3 * arrayOfInt1.length;; k = 3 * this._S.length)
    {
      int m = 0;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      for (int i3 = 0; i3 < k; i3++)
      {
        int[] arrayOfInt2 = this._S;
        m = rotateLeft(n + (m + this._S[i1]), 3);
        arrayOfInt2[i1] = m;
        n = rotateLeft(n + (m + arrayOfInt1[i2]), m + n);
        arrayOfInt1[i2] = n;
        i1 = (i1 + 1) % this._S.length;
        i2 = (i2 + 1) % arrayOfInt1.length;
      }
    }
  }
  
  private void wordToBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    paramArrayOfByte[paramInt2] = ((byte)paramInt1);
    paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >> 8));
    paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >> 16));
    paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >> 24));
  }
  
  public String getAlgorithmName()
  {
    return "RC5-32";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof RC5Parameters))
    {
      RC5Parameters localRC5Parameters = (RC5Parameters)paramCipherParameters;
      this._noRounds = localRC5Parameters.getRounds();
      setKey(localRC5Parameters.getKey());
    }
    for (;;)
    {
      this.forEncryption = paramBoolean;
      return;
      if (!(paramCipherParameters instanceof KeyParameter)) {
        break;
      }
      setKey(((KeyParameter)paramCipherParameters).getKey());
    }
    throw new IllegalArgumentException("invalid parameter passed to RC532 init - " + paramCipherParameters.getClass().getName());
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
 * Qualified Name:     org.spongycastle.crypto.engines.RC532Engine
 * JD-Core Version:    0.7.0.1
 */