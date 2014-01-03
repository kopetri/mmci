package org.spongycastle.crypto;

import java.math.BigInteger;

public abstract interface DSA
{
  public abstract BigInteger[] generateSignature(byte[] paramArrayOfByte);
  
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract boolean verifySignature(byte[] paramArrayOfByte, BigInteger paramBigInteger1, BigInteger paramBigInteger2);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.DSA
 * JD-Core Version:    0.7.0.1
 */