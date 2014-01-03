package org.spongycastle.jce.spec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

public class ECNamedCurveParameterSpec
  extends ECParameterSpec
{
  private String name;
  
  public ECNamedCurveParameterSpec(String paramString, ECCurve paramECCurve, ECPoint paramECPoint, BigInteger paramBigInteger)
  {
    super(paramECCurve, paramECPoint, paramBigInteger);
    this.name = paramString;
  }
  
  public ECNamedCurveParameterSpec(String paramString, ECCurve paramECCurve, ECPoint paramECPoint, BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    super(paramECCurve, paramECPoint, paramBigInteger1, paramBigInteger2);
    this.name = paramString;
  }
  
  public ECNamedCurveParameterSpec(String paramString, ECCurve paramECCurve, ECPoint paramECPoint, BigInteger paramBigInteger1, BigInteger paramBigInteger2, byte[] paramArrayOfByte)
  {
    super(paramECCurve, paramECPoint, paramBigInteger1, paramBigInteger2, paramArrayOfByte);
    this.name = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.spec.ECNamedCurveParameterSpec
 * JD-Core Version:    0.7.0.1
 */