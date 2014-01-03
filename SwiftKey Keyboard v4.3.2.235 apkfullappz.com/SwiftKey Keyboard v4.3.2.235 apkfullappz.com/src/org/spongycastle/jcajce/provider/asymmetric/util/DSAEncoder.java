package org.spongycastle.jcajce.provider.asymmetric.util;

import java.io.IOException;
import java.math.BigInteger;

public abstract interface DSAEncoder
{
  public abstract BigInteger[] decode(byte[] paramArrayOfByte)
    throws IOException;
  
  public abstract byte[] encode(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.util.DSAEncoder
 * JD-Core Version:    0.7.0.1
 */