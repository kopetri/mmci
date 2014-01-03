package org.spongycastle.math.ec;

import java.math.BigInteger;

class ReferenceMultiplier
  implements ECMultiplier
{
  public ECPoint multiply(ECPoint paramECPoint, BigInteger paramBigInteger, PreCompInfo paramPreCompInfo)
  {
    ECPoint localECPoint = paramECPoint.getCurve().getInfinity();
    int i = paramBigInteger.bitLength();
    for (int j = 0; j < i; j++)
    {
      if (paramBigInteger.testBit(j)) {
        localECPoint = localECPoint.add(paramECPoint);
      }
      paramECPoint = paramECPoint.twice();
    }
    return localECPoint;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ec.ReferenceMultiplier
 * JD-Core Version:    0.7.0.1
 */