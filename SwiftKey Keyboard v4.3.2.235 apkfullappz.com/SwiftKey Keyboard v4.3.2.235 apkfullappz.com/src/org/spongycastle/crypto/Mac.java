package org.spongycastle.crypto;

public abstract interface Mac
{
  public abstract int doFinal(byte[] paramArrayOfByte, int paramInt)
    throws DataLengthException, IllegalStateException;
  
  public abstract String getAlgorithmName();
  
  public abstract int getMacSize();
  
  public abstract void init(CipherParameters paramCipherParameters)
    throws IllegalArgumentException;
  
  public abstract void reset();
  
  public abstract void update(byte paramByte)
    throws IllegalStateException;
  
  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws DataLengthException, IllegalStateException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.Mac
 * JD-Core Version:    0.7.0.1
 */