package org.spongycastle.math.ntru.polynomial;

import java.math.BigInteger;

public class Resultant
{
  public BigInteger res;
  public BigIntPolynomial rho;
  
  Resultant(BigIntPolynomial paramBigIntPolynomial, BigInteger paramBigInteger)
  {
    this.rho = paramBigIntPolynomial;
    this.res = paramBigInteger;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.Resultant
 * JD-Core Version:    0.7.0.1
 */