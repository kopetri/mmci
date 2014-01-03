package org.spongycastle.math.ntru.polynomial;

public abstract interface Polynomial
{
  public abstract BigIntPolynomial mult(BigIntPolynomial paramBigIntPolynomial);
  
  public abstract IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial);
  
  public abstract IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial, int paramInt);
  
  public abstract IntegerPolynomial toIntegerPolynomial();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.polynomial.Polynomial
 * JD-Core Version:    0.7.0.1
 */