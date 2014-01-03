package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;

public abstract interface GOST3410PublicKey
  extends PublicKey, GOST3410Key
{
  public abstract BigInteger getY();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.interfaces.GOST3410PublicKey
 * JD-Core Version:    0.7.0.1
 */