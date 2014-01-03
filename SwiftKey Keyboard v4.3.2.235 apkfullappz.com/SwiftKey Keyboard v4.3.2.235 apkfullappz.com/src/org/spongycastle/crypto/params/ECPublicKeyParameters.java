package org.spongycastle.crypto.params;

import org.spongycastle.math.ec.ECPoint;

public class ECPublicKeyParameters
  extends ECKeyParameters
{
  ECPoint Q;
  
  public ECPublicKeyParameters(ECPoint paramECPoint, ECDomainParameters paramECDomainParameters)
  {
    super(false, paramECDomainParameters);
    this.Q = paramECPoint;
  }
  
  public ECPoint getQ()
  {
    return this.Q;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.ECPublicKeyParameters
 * JD-Core Version:    0.7.0.1
 */