package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.F2m;
import org.spongycastle.math.ec.ECCurve.Fp;
import org.spongycastle.math.ec.ECFieldElement;

public class EC5Util
{
  public static EllipticCurve convertCurve(ECCurve paramECCurve, byte[] paramArrayOfByte)
  {
    if ((paramECCurve instanceof ECCurve.Fp)) {
      return new EllipticCurve(new ECFieldFp(((ECCurve.Fp)paramECCurve).getQ()), paramECCurve.getA().toBigInteger(), paramECCurve.getB().toBigInteger(), null);
    }
    ECCurve.F2m localF2m = (ECCurve.F2m)paramECCurve;
    if (localF2m.isTrinomial())
    {
      int[] arrayOfInt2 = new int[1];
      arrayOfInt2[0] = localF2m.getK1();
      return new EllipticCurve(new ECFieldF2m(localF2m.getM(), arrayOfInt2), paramECCurve.getA().toBigInteger(), paramECCurve.getB().toBigInteger(), null);
    }
    int[] arrayOfInt1 = new int[3];
    arrayOfInt1[0] = localF2m.getK3();
    arrayOfInt1[1] = localF2m.getK2();
    arrayOfInt1[2] = localF2m.getK1();
    return new EllipticCurve(new ECFieldF2m(localF2m.getM(), arrayOfInt1), paramECCurve.getA().toBigInteger(), paramECCurve.getB().toBigInteger(), null);
  }
  
  public static ECCurve convertCurve(EllipticCurve paramEllipticCurve)
  {
    ECField localECField = paramEllipticCurve.getField();
    BigInteger localBigInteger1 = paramEllipticCurve.getA();
    BigInteger localBigInteger2 = paramEllipticCurve.getB();
    if ((localECField instanceof ECFieldFp)) {
      return new ECCurve.Fp(((ECFieldFp)localECField).getP(), localBigInteger1, localBigInteger2);
    }
    ECFieldF2m localECFieldF2m = (ECFieldF2m)localECField;
    int i = localECFieldF2m.getM();
    int[] arrayOfInt = ECUtil.convertMidTerms(localECFieldF2m.getMidTermsOfReductionPolynomial());
    return new ECCurve.F2m(i, arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], localBigInteger1, localBigInteger2);
  }
  
  public static org.spongycastle.math.ec.ECPoint convertPoint(java.security.spec.ECParameterSpec paramECParameterSpec, java.security.spec.ECPoint paramECPoint, boolean paramBoolean)
  {
    return convertPoint(convertCurve(paramECParameterSpec.getCurve()), paramECPoint, paramBoolean);
  }
  
  public static org.spongycastle.math.ec.ECPoint convertPoint(ECCurve paramECCurve, java.security.spec.ECPoint paramECPoint, boolean paramBoolean)
  {
    return paramECCurve.createPoint(paramECPoint.getAffineX(), paramECPoint.getAffineY(), paramBoolean);
  }
  
  public static java.security.spec.ECParameterSpec convertSpec(EllipticCurve paramEllipticCurve, org.spongycastle.jce.spec.ECParameterSpec paramECParameterSpec)
  {
    if ((paramECParameterSpec instanceof ECNamedCurveParameterSpec)) {
      return new ECNamedCurveSpec(((ECNamedCurveParameterSpec)paramECParameterSpec).getName(), paramEllipticCurve, new java.security.spec.ECPoint(paramECParameterSpec.getG().getX().toBigInteger(), paramECParameterSpec.getG().getY().toBigInteger()), paramECParameterSpec.getN(), paramECParameterSpec.getH());
    }
    return new java.security.spec.ECParameterSpec(paramEllipticCurve, new java.security.spec.ECPoint(paramECParameterSpec.getG().getX().toBigInteger(), paramECParameterSpec.getG().getY().toBigInteger()), paramECParameterSpec.getN(), paramECParameterSpec.getH().intValue());
  }
  
  public static org.spongycastle.jce.spec.ECParameterSpec convertSpec(java.security.spec.ECParameterSpec paramECParameterSpec, boolean paramBoolean)
  {
    ECCurve localECCurve = convertCurve(paramECParameterSpec.getCurve());
    return new org.spongycastle.jce.spec.ECParameterSpec(localECCurve, convertPoint(localECCurve, paramECParameterSpec.getGenerator(), paramBoolean), paramECParameterSpec.getOrder(), BigInteger.valueOf(paramECParameterSpec.getCofactor()), paramECParameterSpec.getCurve().getSeed());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.ec.EC5Util
 * JD-Core Version:    0.7.0.1
 */