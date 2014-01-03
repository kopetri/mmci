package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;

public class SICBlockCipher
  implements BlockCipher
{
  private byte[] IV;
  private final int blockSize;
  private final BlockCipher cipher;
  private byte[] counter;
  private byte[] counterOut;
  
  public SICBlockCipher(BlockCipher paramBlockCipher)
  {
    this.cipher = paramBlockCipher;
    this.blockSize = this.cipher.getBlockSize();
    this.IV = new byte[this.blockSize];
    this.counter = new byte[this.blockSize];
    this.counterOut = new byte[this.blockSize];
  }
  
  public String getAlgorithmName()
  {
    return this.cipher.getAlgorithmName() + "/SIC";
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
    if ((paramCipherParameters instanceof ParametersWithIV))
    {
      ParametersWithIV localParametersWithIV = (ParametersWithIV)paramCipherParameters;
      System.arraycopy(localParametersWithIV.getIV(), 0, this.IV, 0, this.IV.length);
      reset();
      if (localParametersWithIV.getParameters() != null) {
        this.cipher.init(true, localParametersWithIV.getParameters());
      }
      return;
    }
    throw new IllegalArgumentException("SIC mode requires ParametersWithIV");
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
    for (int i = 0; i < this.counterOut.length; i++) {
      paramArrayOfByte2[(paramInt2 + i)] = ((byte)(this.counterOut[i] ^ paramArrayOfByte1[(paramInt1 + i)]));
    }
    int j = 1;
    int k = -1 + this.counter.length;
    if (k >= 0)
    {
      int m = j + (0xFF & this.counter[k]);
      if (m > 255) {}
      for (j = 1;; j = 0)
      {
        this.counter[k] = ((byte)m);
        k--;
        break;
      }
    }
    return this.counter.length;
  }
  
  public void reset()
  {
    System.arraycopy(this.IV, 0, this.counter, 0, this.counter.length);
    this.cipher.reset();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.SICBlockCipher
 * JD-Core Version:    0.7.0.1
 */