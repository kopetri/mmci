package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class DHPrivateKeyParameters
  extends DHKeyParameters
{
  private BigInteger x;
  
  public DHPrivateKeyParameters(BigInteger paramBigInteger, DHParameters paramDHParameters)
  {
    super(true, paramDHParameters);
    this.x = paramBigInteger;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DHPrivateKeyParameters)) {
      return false;
    }
    return (((DHPrivateKeyParameters)paramObject).getX().equals(this.x)) && (super.equals(paramObject));
  }
  
  public BigInteger getX()
  {
    return this.x;
  }
  
  public int hashCode()
  {
    return this.x.hashCode() ^ super.hashCode();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.DHPrivateKeyParameters
 * JD-Core Version:    0.7.0.1
 */