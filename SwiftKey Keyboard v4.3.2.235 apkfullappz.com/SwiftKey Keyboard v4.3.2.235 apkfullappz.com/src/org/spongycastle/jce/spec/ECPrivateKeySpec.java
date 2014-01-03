package org.spongycastle.jce.spec;

import java.math.BigInteger;

public class ECPrivateKeySpec
  extends ECKeySpec
{
  private BigInteger d;
  
  public ECPrivateKeySpec(BigInteger paramBigInteger, ECParameterSpec paramECParameterSpec)
  {
    super(paramECParameterSpec);
    this.d = paramBigInteger;
  }
  
  public BigInteger getD()
  {
    return this.d;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.spec.ECPrivateKeySpec
 * JD-Core Version:    0.7.0.1
 */