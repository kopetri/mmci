package org.spongycastle.crypto;

public abstract interface Wrapper
{
  public abstract String getAlgorithmName();
  
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract byte[] unwrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException;
  
  public abstract byte[] wrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.Wrapper
 * JD-Core Version:    0.7.0.1
 */