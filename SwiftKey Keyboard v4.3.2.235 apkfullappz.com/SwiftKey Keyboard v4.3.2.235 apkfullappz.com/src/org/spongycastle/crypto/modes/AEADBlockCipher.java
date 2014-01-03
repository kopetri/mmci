package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;

public abstract interface AEADBlockCipher
{
  public abstract int doFinal(byte[] paramArrayOfByte, int paramInt)
    throws IllegalStateException, InvalidCipherTextException;
  
  public abstract String getAlgorithmName();
  
  public abstract byte[] getMac();
  
  public abstract int getOutputSize(int paramInt);
  
  public abstract BlockCipher getUnderlyingCipher();
  
  public abstract int getUpdateOutputSize(int paramInt);
  
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters)
    throws IllegalArgumentException;
  
  public abstract int processByte(byte paramByte, byte[] paramArrayOfByte, int paramInt)
    throws DataLengthException;
  
  public abstract int processBytes(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws DataLengthException;
  
  public abstract void reset();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.AEADBlockCipher
 * JD-Core Version:    0.7.0.1
 */