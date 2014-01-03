package org.spongycastle.crypto;

public abstract interface Signer
{
  public abstract byte[] generateSignature()
    throws CryptoException, DataLengthException;
  
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract void reset();
  
  public abstract void update(byte paramByte);
  
  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract boolean verifySignature(byte[] paramArrayOfByte);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.Signer
 * JD-Core Version:    0.7.0.1
 */