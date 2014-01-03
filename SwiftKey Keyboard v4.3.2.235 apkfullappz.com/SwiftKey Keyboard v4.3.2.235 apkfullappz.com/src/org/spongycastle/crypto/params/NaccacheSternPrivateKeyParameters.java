package org.spongycastle.crypto.params;

import java.math.BigInteger;
import java.util.Vector;

public class NaccacheSternPrivateKeyParameters
  extends NaccacheSternKeyParameters
{
  private BigInteger phi_n;
  private Vector smallPrimes;
  
  public NaccacheSternPrivateKeyParameters(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt, Vector paramVector, BigInteger paramBigInteger3)
  {
    super(true, paramBigInteger1, paramBigInteger2, paramInt);
    this.smallPrimes = paramVector;
    this.phi_n = paramBigInteger3;
  }
  
  public BigInteger getPhi_n()
  {
    return this.phi_n;
  }
  
  public Vector getSmallPrimes()
  {
    return this.smallPrimes;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NaccacheSternPrivateKeyParameters
 * JD-Core Version:    0.7.0.1
 */