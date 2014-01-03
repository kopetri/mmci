package org.spongycastle.crypto.prng;

public abstract interface RandomGenerator
{
  public abstract void addSeedMaterial(long paramLong);
  
  public abstract void addSeedMaterial(byte[] paramArrayOfByte);
  
  public abstract void nextBytes(byte[] paramArrayOfByte);
  
  public abstract void nextBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.prng.RandomGenerator
 * JD-Core Version:    0.7.0.1
 */