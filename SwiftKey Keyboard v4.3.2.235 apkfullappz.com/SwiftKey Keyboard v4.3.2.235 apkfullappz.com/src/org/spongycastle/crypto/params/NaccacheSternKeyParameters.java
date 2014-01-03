package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class NaccacheSternKeyParameters
  extends AsymmetricKeyParameter
{
  private BigInteger g;
  int lowerSigmaBound;
  private BigInteger n;
  
  public NaccacheSternKeyParameters(boolean paramBoolean, BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt)
  {
    super(paramBoolean);
    this.g = paramBigInteger1;
    this.n = paramBigInteger2;
    this.lowerSigmaBound = paramInt;
  }
  
  public BigInteger getG()
  {
    return this.g;
  }
  
  public int getLowerSigmaBound()
  {
    return this.lowerSigmaBound;
  }
  
  public BigInteger getModulus()
  {
    return this.n;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NaccacheSternKeyParameters
 * JD-Core Version:    0.7.0.1
 */