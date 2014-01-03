package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class DHPublicKeyParameters
  extends DHKeyParameters
{
  private BigInteger y;
  
  public DHPublicKeyParameters(BigInteger paramBigInteger, DHParameters paramDHParameters)
  {
    super(false, paramDHParameters);
    this.y = paramBigInteger;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DHPublicKeyParameters)) {
      return false;
    }
    return (((DHPublicKeyParameters)paramObject).getY().equals(this.y)) && (super.equals(paramObject));
  }
  
  public BigInteger getY()
  {
    return this.y;
  }
  
  public int hashCode()
  {
    return this.y.hashCode() ^ super.hashCode();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.DHPublicKeyParameters
 * JD-Core Version:    0.7.0.1
 */