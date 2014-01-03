package org.spongycastle.crypto.tls;

public abstract interface TlsPSKIdentity
{
  public abstract byte[] getPSK();
  
  public abstract byte[] getPSKIdentity();
  
  public abstract void notifyIdentityHint(byte[] paramArrayOfByte);
  
  public abstract void skipIdentityHint();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsPSKIdentity
 * JD-Core Version:    0.7.0.1
 */