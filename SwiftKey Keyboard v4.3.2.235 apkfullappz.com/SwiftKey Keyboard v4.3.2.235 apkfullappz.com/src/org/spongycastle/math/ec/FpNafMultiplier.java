package org.spongycastle.math.ec;

import java.math.BigInteger;

class FpNafMultiplier
  implements ECMultiplier
{
  public ECPoint multiply(ECPoint paramECPoint, BigInteger paramBigInteger, PreCompInfo paramPreCompInfo)
  {
    BigInteger localBigInteger = paramBigInteger.multiply(BigInteger.valueOf(3L));
    ECPoint localECPoint1 = paramECPoint.negate();
    ECPoint localECPoint2 = paramECPoint;
    int i = -2 + localBigInteger.bitLength();
    if (i > 0)
    {
      localECPoint2 = localECPoint2.twice();
      boolean bool = localBigInteger.testBit(i);
      if (bool != paramBigInteger.testBit(i)) {
        if (!bool) {
          break label86;
        }
      }
      label86:
      for (ECPoint localECPoint3 = paramECPoint;; localECPoint3 = localECPoint1)
      {
        localECPoint2 = localECPoint2.add(localECPoint3);
        i--;
        break;
      }
    }
    return localECPoint2;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ec.FpNafMultiplier
 * JD-Core Version:    0.7.0.1
 */