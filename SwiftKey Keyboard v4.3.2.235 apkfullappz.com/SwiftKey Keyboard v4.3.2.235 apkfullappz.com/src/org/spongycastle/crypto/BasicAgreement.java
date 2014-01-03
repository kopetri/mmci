package org.spongycastle.crypto;

import java.math.BigInteger;

public abstract interface BasicAgreement
{
  public abstract BigInteger calculateAgreement(CipherParameters paramCipherParameters);
  
  public abstract void init(CipherParameters paramCipherParameters);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.BasicAgreement
 * JD-Core Version:    0.7.0.1
 */