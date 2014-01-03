package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class DSAPublicKeyParameters
  extends DSAKeyParameters
{
  private BigInteger y;
  
  public DSAPublicKeyParameters(BigInteger paramBigInteger, DSAParameters paramDSAParameters)
  {
    super(false, paramDSAParameters);
    this.y = paramBigInteger;
  }
  
  public BigInteger getY()
  {
    return this.y;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.DSAPublicKeyParameters
 * JD-Core Version:    0.7.0.1
 */