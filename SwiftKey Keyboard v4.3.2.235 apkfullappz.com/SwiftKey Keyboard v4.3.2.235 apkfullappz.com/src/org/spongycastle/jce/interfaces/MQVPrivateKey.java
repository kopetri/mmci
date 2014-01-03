package org.spongycastle.jce.interfaces;

import java.security.PrivateKey;
import java.security.PublicKey;

public abstract interface MQVPrivateKey
  extends PrivateKey
{
  public abstract PrivateKey getEphemeralPrivateKey();
  
  public abstract PublicKey getEphemeralPublicKey();
  
  public abstract PrivateKey getStaticPrivateKey();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.interfaces.MQVPrivateKey
 * JD-Core Version:    0.7.0.1
 */