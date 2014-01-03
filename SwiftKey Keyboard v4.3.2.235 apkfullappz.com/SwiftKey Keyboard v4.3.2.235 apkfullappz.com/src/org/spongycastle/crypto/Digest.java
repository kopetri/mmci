package org.spongycastle.crypto;

public abstract interface Digest
{
  public abstract int doFinal(byte[] paramArrayOfByte, int paramInt);
  
  public abstract String getAlgorithmName();
  
  public abstract int getDigestSize();
  
  public abstract void reset();
  
  public abstract void update(byte paramByte);
  
  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.Digest
 * JD-Core Version:    0.7.0.1
 */