package org.spongycastle.crypto.modes.gcm;

public abstract interface GCMMultiplier
{
  public abstract void init(byte[] paramArrayOfByte);
  
  public abstract void multiplyH(byte[] paramArrayOfByte);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.gcm.GCMMultiplier
 * JD-Core Version:    0.7.0.1
 */