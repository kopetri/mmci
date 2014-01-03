package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class CBCBlockCipher
  implements BlockCipher
{
  private byte[] IV;
  private int blockSize;
  private byte[] cbcNextV;
  private byte[] cbcV;
  private BlockCipher cipher = null;
  private boolean encrypting;
  
  public CBCBlockCipher(BlockCipher paramBlockCipher)
  {
    this.cipher = paramBlockCipher;
    this.blockSize = paramBlockCipher.getBlockSize();
    this.IV = new byte[this.blockSize];
    this.cbcV = new byte[this.blockSize];
    this.cbcNextV = new byte[this.blockSize];
  }
  
  private int decryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (paramInt1 + this.blockSize > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    System.arraycopy(paramArrayOfByte1, paramInt1, this.cbcNextV, 0, this.blockSize);
    int i = this.cipher.processBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    for (int j = 0; j < this.blockSize; j++)
    {
      int k = paramInt2 + j;
      paramArrayOfByte2[k] = ((byte)(paramArrayOfByte2[k] ^ this.cbcV[j]));
    }
    byte[] arrayOfByte = this.cbcV;
    this.cbcV = this.cbcNextV;
    this.cbcNextV = arrayOfByte;
    return i;
  }
  
  private int encryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (paramInt1 + this.blockSize > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    for (int i = 0; i < this.blockSize; i++)
    {
      byte[] arrayOfByte = this.cbcV;
      arrayOfByte[i] = ((byte)(arrayOfByte[i] ^ paramArrayOfByte1[(paramInt1 + i)]));
    }
    int j = this.cipher.processBlock(this.cbcV, 0, paramArrayOfByte2, paramInt2);
    System.arraycopy(paramArrayOfByte2, paramInt2, this.cbcV, 0, this.cbcV.length);
    return j;
  }
  
  public String getAlgorithmName()
  {
    return this.cipher.getAlgorithmName() + "/CBC";
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
    boolean bool = this.encrypting;
    this.encrypting = paramBoolean;
    if ((paramCipherParameters instanceof ParametersWithIV))
    {
      ParametersWithIV localParametersWithIV = (ParametersWithIV)paramCipherParameters;
      byte[] arrayOfByte = localParametersWithIV.getIV();
      if (arrayOfByte.length != this.blockSize) {
        throw new IllegalArgumentException("initialisation vector must be the same length as block size");
      }
      System.arraycopy(arrayOfByte, 0, this.IV, 0, arrayOfByte.length);
      reset();
      if (localParametersWithIV.getParameters() != null) {
        this.cipher.init(paramBoolean, localParametersWithIV.getParameters());
      }
    }
    do
    {
      do
      {
        return;
      } while (bool == paramBoolean);
      throw new IllegalArgumentException("cannot change encrypting state without providing key.");
      reset();
      if (paramCipherParameters != null)
      {
        this.cipher.init(paramBoolean, paramCipherParameters);
        return;
      }
    } while (bool == paramBoolean);
    throw new IllegalArgumentException("cannot change encrypting state without providing key.");
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (this.encrypting) {
      return encryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
    return decryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
  }
  
  public void reset()
  {
    System.arraycopy(this.IV, 0, this.cbcV, 0, this.IV.length);
    Arrays.fill(this.cbcNextV, (byte)0);
    this.cipher.reset();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.CBCBlockCipher
 * JD-Core Version:    0.7.0.1
 */