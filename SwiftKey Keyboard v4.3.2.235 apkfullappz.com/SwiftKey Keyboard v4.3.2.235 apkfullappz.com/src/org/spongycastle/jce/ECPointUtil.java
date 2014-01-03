package org.spongycastle.jce;

import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.F2m;
import org.spongycastle.math.ec.ECCurve.Fp;
import org.spongycastle.math.ec.ECFieldElement;

public class ECPointUtil
{
  public static java.security.spec.ECPoint decodePoint(EllipticCurve paramEllipticCurve, byte[] paramArrayOfByte)
  {
    Object localObject;
    if ((paramEllipticCurve.getField() instanceof ECFieldFp)) {
      localObject = new ECCurve.Fp(((ECFieldFp)paramEllipticCurve.getField()).getP(), paramEllipticCurve.getA(), paramEllipticCurve.getB());
    }
    for (;;)
    {
      org.spongycastle.math.ec.ECPoint localECPoint = ((ECCurve)localObject).decodePoint(paramArrayOfByte);
      return new java.security.spec.ECPoint(localECPoint.getX().toBigInteger(), localECPoint.getY().toBigInteger());
      int[] arrayOfInt = ((ECFieldF2m)paramEllipticCurve.getField()).getMidTermsOfReductionPolynomial();
      if (arrayOfInt.length == 3) {
        localObject = new ECCurve.F2m(((ECFieldF2m)paramEllipticCurve.getField()).getM(), arrayOfInt[2], arrayOfInt[1], arrayOfInt[0], paramEllipticCurve.getA(), paramEllipticCurve.getB());
      } else {
        localObject = new ECCurve.F2m(((ECFieldF2m)paramEllipticCurve.getField()).getM(), arrayOfInt[0], paramEllipticCurve.getA(), paramEllipticCurve.getB());
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.ECPointUtil
 * JD-Core Version:    0.7.0.1
 */