package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;

public class PGPCFBBlockCipher
  implements BlockCipher
{
  private byte[] FR;
  private byte[] FRE;
  private byte[] IV;
  private int blockSize;
  private BlockCipher cipher;
  private int count;
  private boolean forEncryption;
  private boolean inlineIv;
  private byte[] tmp;
  
  public PGPCFBBlockCipher(BlockCipher paramBlockCipher, boolean paramBoolean)
  {
    this.cipher = paramBlockCipher;
    this.inlineIv = paramBoolean;
    this.blockSize = paramBlockCipher.getBlockSize();
    this.IV = new byte[this.blockSize];
    this.FR = new byte[this.blockSize];
    this.FRE = new byte[this.blockSize];
    this.tmp = new byte[this.blockSize];
  }
  
  private int decryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (paramInt1 + this.blockSize > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + this.blockSize > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    this.cipher.processBlock(this.FR, 0, this.FRE, 0);
    for (int i = 0; i < this.blockSize; i++) {
      paramArrayOfByte2[(paramInt2 + i)] = encryptByte(paramArrayOfByte1[(paramInt1 + i)], i);
    }
    for (int j = 0; j < this.blockSize; j++) {
      this.FR[j] = paramArrayOfByte1[(paramInt1 + j)];
    }
    return this.blockSize;
  }
  
  private int decryptBlockWithIV(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (paramInt1 + this.blockSize > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + this.blockSize > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    if (this.count == 0)
    {
      for (int k = 0; k < this.blockSize; k++) {
        this.FR[k] = paramArrayOfByte1[(paramInt1 + k)];
      }
      this.cipher.processBlock(this.FR, 0, this.FRE, 0);
      this.count += this.blockSize;
      return 0;
    }
    if (this.count == this.blockSize)
    {
      System.arraycopy(paramArrayOfByte1, paramInt1, this.tmp, 0, this.blockSize);
      System.arraycopy(this.FR, 2, this.FR, 0, -2 + this.blockSize);
      this.FR[(-2 + this.blockSize)] = this.tmp[0];
      this.FR[(-1 + this.blockSize)] = this.tmp[1];
      this.cipher.processBlock(this.FR, 0, this.FRE, 0);
      for (int j = 0; j < -2 + this.blockSize; j++) {
        paramArrayOfByte2[(paramInt2 + j)] = encryptByte(this.tmp[(j + 2)], j);
      }
      System.arraycopy(this.tmp, 2, this.FR, 0, -2 + this.blockSize);
      this.count = (2 + this.count);
      return -2 + this.blockSize;
    }
    if (this.count >= 2 + this.blockSize)
    {
      System.arraycopy(paramArrayOfByte1, paramInt1, this.tmp, 0, this.blockSize);
      paramArrayOfByte2[(paramInt2 + 0)] = encryptByte(this.tmp[0], -2 + this.blockSize);
      paramArrayOfByte2[(paramInt2 + 1)] = encryptByte(this.tmp[1], -1 + this.blockSize);
      System.arraycopy(this.tmp, 0, this.FR, -2 + this.blockSize, 2);
      this.cipher.processBlock(this.FR, 0, this.FRE, 0);
      for (int i = 0; i < -2 + this.blockSize; i++) {
        paramArrayOfByte2[(2 + (paramInt2 + i))] = encryptByte(this.tmp[(i + 2)], i);
      }
      System.arraycopy(this.tmp, 2, this.FR, 0, -2 + this.blockSize);
    }
    return this.blockSize;
  }
  
  private int encryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (paramInt1 + this.blockSize > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + this.blockSize > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    this.cipher.processBlock(this.FR, 0, this.FRE, 0);
    for (int i = 0; i < this.blockSize; i++) {
      paramArrayOfByte2[(paramInt2 + i)] = encryptByte(paramArrayOfByte1[(paramInt1 + i)], i);
    }
    for (int j = 0; j < this.blockSize; j++) {
      this.FR[j] = paramArrayOfByte2[(paramInt2 + j)];
    }
    return this.blockSize;
  }
  
  private int encryptBlockWithIV(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (paramInt1 + this.blockSize > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + this.blockSize > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    if (this.count == 0)
    {
      this.cipher.processBlock(this.FR, 0, this.FRE, 0);
      for (int j = 0; j < this.blockSize; j++) {
        paramArrayOfByte2[(paramInt2 + j)] = encryptByte(this.IV[j], j);
      }
      System.arraycopy(paramArrayOfByte2, paramInt2, this.FR, 0, this.blockSize);
      this.cipher.processBlock(this.FR, 0, this.FRE, 0);
      paramArrayOfByte2[(paramInt2 + this.blockSize)] = encryptByte(this.IV[(-2 + this.blockSize)], 0);
      paramArrayOfByte2[(1 + (paramInt2 + this.blockSize))] = encryptByte(this.IV[(-1 + this.blockSize)], 1);
      System.arraycopy(paramArrayOfByte2, paramInt2 + 2, this.FR, 0, this.blockSize);
      this.cipher.processBlock(this.FR, 0, this.FRE, 0);
      for (int k = 0; k < this.blockSize; k++) {
        paramArrayOfByte2[(k + (2 + (paramInt2 + this.blockSize)))] = encryptByte(paramArrayOfByte1[(paramInt1 + k)], k);
      }
      System.arraycopy(paramArrayOfByte2, 2 + (paramInt2 + this.blockSize), this.FR, 0, this.blockSize);
      this.count += 2 + 2 * this.blockSize;
      return 2 + 2 * this.blockSize;
    }
    if (this.count >= 2 + this.blockSize)
    {
      this.cipher.processBlock(this.FR, 0, this.FRE, 0);
      for (int i = 0; i < this.blockSize; i++) {
        paramArrayOfByte2[(paramInt2 + i)] = encryptByte(paramArrayOfByte1[(paramInt1 + i)], i);
      }
      System.arraycopy(paramArrayOfByte2, paramInt2, this.FR, 0, this.blockSize);
    }
    return this.blockSize;
  }
  
  private byte encryptByte(byte paramByte, int paramInt)
  {
    return (byte)(paramByte ^ this.FRE[paramInt]);
  }
  
  public String getAlgorithmName()
  {
    if (this.inlineIv) {
      return this.cipher.getAlgorithmName() + "/PGPCFBwithIV";
    }
    return this.cipher.getAlgorithmName() + "/PGPCFB";
  }
  
  public int getBlockSize()
  {
    return this.cipher.getBlockSize();
  }
  
  public BlockCipher getUnderlyingCipher()
  {
    return this.cipher;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
    throws IllegalArgumentException
  {
    this.forEncryption = paramBoolean;
    if ((paramCipherParameters instanceof ParametersWithIV))
    {
      ParametersWithIV localParametersWithIV = (ParametersWithIV)paramCipherParameters;
      byte[] arrayOfByte = localParametersWithIV.getIV();
      if (arrayOfByte.length < this.IV.length)
      {
        System.arraycopy(arrayOfByte, 0, this.IV, this.IV.length - arrayOfByte.length, arrayOfByte.length);
        for (int i = 0; i < this.IV.length - arrayOfByte.length; i++) {
          this.IV[i] = 0;
        }
      }
      System.arraycopy(arrayOfByte, 0, this.IV, 0, this.IV.length);
      reset();
      this.cipher.init(true, localParametersWithIV.getParameters());
      return;
    }
    reset();
    this.cipher.init(true, paramCipherParameters);
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (this.inlineIv)
    {
      if (this.forEncryption) {
        return encryptBlockWithIV(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
      }
      return decryptBlockWithIV(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
    if (this.forEncryption) {
      return encryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
    return decryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
  }
  
  public void reset()
  {
    this.count = 0;
    int i = 0;
    if (i != this.FR.length)
    {
      if (this.inlineIv) {
        this.FR[i] = 0;
      }
      for (;;)
      {
        i++;
        break;
        this.FR[i] = this.IV[i];
      }
    }
    this.cipher.reset();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.PGPCFBBlockCipher
 * JD-Core Version:    0.7.0.1
 */