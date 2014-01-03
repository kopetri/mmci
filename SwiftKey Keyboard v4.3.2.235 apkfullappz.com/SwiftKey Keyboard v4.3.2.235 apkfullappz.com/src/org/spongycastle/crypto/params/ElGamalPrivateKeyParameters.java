package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class ElGamalPrivateKeyParameters
  extends ElGamalKeyParameters
{
  private BigInteger x;
  
  public ElGamalPrivateKeyParameters(BigInteger paramBigInteger, ElGamalParameters paramElGamalParameters)
  {
    super(true, paramElGamalParameters);
    this.x = paramBigInteger;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ElGamalPrivateKeyParameters)) {
      return false;
    }
    if (!((ElGamalPrivateKeyParameters)paramObject).getX().equals(this.x)) {
      return false;
    }
    return super.equals(paramObject);
  }
  
  public BigInteger getX()
  {
    return this.x;
  }
  
  public int hashCode()
  {
    return getX().hashCode();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.ElGamalPrivateKeyParameters
 * JD-Core Version:    0.7.0.1
 */