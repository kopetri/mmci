package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class GOST3410PrivateKeyParameters
  extends GOST3410KeyParameters
{
  private BigInteger x;
  
  public GOST3410PrivateKeyParameters(BigInteger paramBigInteger, GOST3410Parameters paramGOST3410Parameters)
  {
    super(true, paramGOST3410Parameters);
    this.x = paramBigInteger;
  }
  
  public BigInteger getX()
  {
    return this.x;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.GOST3410PrivateKeyParameters
 * JD-Core Version:    0.7.0.1
 */