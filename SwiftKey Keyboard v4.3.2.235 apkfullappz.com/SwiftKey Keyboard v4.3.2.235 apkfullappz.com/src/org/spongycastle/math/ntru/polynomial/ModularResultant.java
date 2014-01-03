package org.spongycastle.math.ntru.polynomial;

import java.math.BigInteger;
import org.spongycastle.math.ntru.euclid.BigIntEuclidean;

public class ModularResultant
  extends Resultant
{
  BigInteger modulus;
  
  ModularResultant(BigIntPolynomial paramBigIntPolynomial, BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    super(paramBigIntPolynomial, paramBigInteger1);
    this.modulus = paramBigInteger2;
  }
  
  static ModularResultant combineRho(ModularResultant paramModularResultant1, ModularResultant paramModularResultant2)
  {
    BigInteger localBigInteger1 = paramModularResultant1.modulus;
    BigInteger localBigInteger2 = paramModularResultant2.modulus;
    BigInteger localBigInteger3 = localBigInteger1.multiply(localBigInteger2);
    BigIntEuclidean localBigIntEuclidean = BigIntEuclidean.calculate(localBigInteger2, localBigInteger1);
    BigIntPolynomial localBigIntPolynomial1 = (BigIntPolynomial)paramModularResultant1.rho.clone();
    localBigIntPolynomial1.mult(localBigIntEuclidean.x.multiply(localBigInteger2));
    BigIntPolynomial localBigIntPolynomial2 = (BigIntPolynomial)paramModularResultant2.rho.clone();
    localBigIntPolynomial2.mult(localBigIntEuclidean.y.multiply(localBigInteger1));
    localBigIntPolynomial1.add(localBigIntPolynomial2);
    localBigIntPolynomial1.mod(localBigInteger3);
    return new ModularResultant(localBigIntPolynomial1, null, localBigInteger3);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.ModularResultant
 * JD-Core Version:    0.7.0.1
 */