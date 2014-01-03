package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;

public class NullEngine
  implements BlockCipher
{
  protected static final int BLOCK_SIZE = 1;
  private boolean initialised;
  
  public String getAlgorithmName()
  {
    return "Null";
  }
  
  public int getBlockSize()
  {
    return 1;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
    throws IllegalArgumentException
  {
    this.initialised = true;
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (!this.initialised) {
      throw new IllegalStateException("Null engine not initialised");
    }
    if (paramInt1 + 1 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + 1 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    for (int i = 0; i <= 0; i++) {
      paramArrayOfByte2[(paramInt2 + 0)] = paramArrayOfByte1[(paramInt1 + 0)];
    }
    return 1;
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.NullEngine
 * JD-Core Version:    0.7.0.1
 */