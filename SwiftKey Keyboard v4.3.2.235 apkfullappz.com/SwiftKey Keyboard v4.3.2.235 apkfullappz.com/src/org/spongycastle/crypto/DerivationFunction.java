package org.spongycastle.crypto;

public abstract interface DerivationFunction
{
  public abstract int generateBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws DataLengthException, IllegalArgumentException;
  
  public abstract Digest getDigest();
  
  public abstract void init(DerivationParameters paramDerivationParameters);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.DerivationFunction
 * JD-Core Version:    0.7.0.1
 */