package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class RC6Engine
  implements BlockCipher
{
  private static final int LGW = 5;
  private static final int P32 = -1209970333;
  private static final int Q32 = -1640531527;
  private static final int _noRounds = 20;
  private static final int bytesPerWord = 4;
  private static final int wordSize = 32;
  private int[] _S = null;
  private boolean forEncryption;
  
  private int bytesToWord(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0;
    for (int j = 3; j >= 0; j--) {
      i = (i << 8) + (0xFF & paramArrayOfByte[(j + paramInt)]);
    }
    return i;
  }
  
  private int decryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = bytesToWord(paramArrayOfByte1, paramInt1);
    int j = bytesToWord(paramArrayOfByte1, paramInt1 + 4);
    int k = bytesToWord(paramArrayOfByte1, paramInt1 + 8);
    int m = bytesToWord(paramArrayOfByte1, paramInt1 + 12);
    int n = k - this._S[43];
    int i1 = i - this._S[42];
    for (int i2 = 20; i2 > 0; i2--)
    {
      int i5 = m;
      m = n;
      int i6 = j;
      j = i1;
      int i7 = rotateLeft(j * (1 + j * 2), 5);
      int i8 = rotateLeft(m * (1 + m * 2), 5);
      n = i8 ^ rotateRight(i6 - this._S[(1 + i2 * 2)], i7);
      i1 = i7 ^ rotateRight(i5 - this._S[(i2 * 2)], i8);
    }
    int i3 = m - this._S[1];
    int i4 = j - this._S[0];
    wordToBytes(i1, paramArrayOfByte2, paramInt2);
    wordToBytes(i4, paramArrayOfByte2, paramInt2 + 4);
    wordToBytes(n, paramArrayOfByte2, paramInt2 + 8);
    wordToBytes(i3, paramArrayOfByte2, paramInt2 + 12);
    return 16;
  }
  
  private int encryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = bytesToWord(paramArrayOfByte1, paramInt1);
    int j = bytesToWord(paramArrayOfByte1, paramInt1 + 4);
    int k = bytesToWord(paramArrayOfByte1, paramInt1 + 8);
    int m = bytesToWord(paramArrayOfByte1, paramInt1 + 12);
    int n = j + this._S[0];
    int i1 = m + this._S[1];
    for (int i2 = 1; i2 <= 20; i2++)
    {
      int i5 = rotateLeft(n * (1 + n * 2), 5);
      int i6 = rotateLeft(i1 * (1 + i1 * 2), 5);
      int i7 = rotateLeft(i ^ i5, i6) + this._S[(i2 * 2)];
      int i8 = rotateLeft(k ^ i6, i5) + this._S[(1 + i2 * 2)];
      i = n;
      n = i8;
      k = i1;
      i1 = i7;
    }
    int i3 = i + this._S[42];
    int i4 = k + this._S[43];
    wordToBytes(i3, paramArrayOfByte2, paramInt2);
    wordToBytes(n, paramArrayOfByte2, paramInt2 + 4);
    wordToBytes(i4, paramArrayOfByte2, paramInt2 + 8);
    wordToBytes(i1, paramArrayOfByte2, paramInt2 + 12);
    return 16;
  }
  
  private int rotateLeft(int paramInt1, int paramInt2)
  {
    return paramInt1 << paramInt2 | paramInt1 >>> -paramInt2;
  }
  
  private int rotateRight(int paramInt1, int paramInt2)
  {
    return paramInt1 >>> paramInt2 | paramInt1 << -paramInt2;
  }
  
  private void setKey(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt1 = new int[(-1 + (4 + paramArrayOfByte.length)) / 4];
    for (int i = -1 + paramArrayOfByte.length; i >= 0; i--) {
      arrayOfInt1[(i / 4)] = ((arrayOfInt1[(i / 4)] << 8) + (0xFF & paramArrayOfByte[i]));
    }
    this._S = new int[44];
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
    for (int i = 0; i < 4; i++)
    {
      paramArrayOfByte[(i + paramInt2)] = ((byte)paramInt1);
      paramInt1 >>>= 8;
    }
  }
  
  public String getAlgorithmName()
  {
    return "RC6";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (!(paramCipherParameters instanceof KeyParameter)) {
      throw new IllegalArgumentException("invalid parameter passed to RC6 init - " + paramCipherParameters.getClass().getName());
    }
    KeyParameter localKeyParameter = (KeyParameter)paramCipherParameters;
    this.forEncryption = paramBoolean;
    setKey(localKeyParameter.getKey());
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = getBlockSize();
    if (this._S == null) {
      throw new IllegalStateException("RC6 engine not initialised");
    }
    if (paramInt1 + i > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + i > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    if (this.forEncryption) {
      return encryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
    return decryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RC6Engine
 * JD-Core Version:    0.7.0.1
 */