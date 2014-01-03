package org.spongycastle.crypto;

public abstract interface AsymmetricBlockCipher
{
  public abstract int getInputBlockSize();
  
  public abstract int getOutputBlockSize();
  
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract byte[] processBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.AsymmetricBlockCipher
 * JD-Core Version:    0.7.0.1
 */