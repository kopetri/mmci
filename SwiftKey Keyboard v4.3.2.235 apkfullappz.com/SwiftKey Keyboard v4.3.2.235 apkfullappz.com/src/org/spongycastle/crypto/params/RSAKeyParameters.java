package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class RSAKeyParameters
  extends AsymmetricKeyParameter
{
  private BigInteger exponent;
  private BigInteger modulus;
  
  public RSAKeyParameters(boolean paramBoolean, BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    super(paramBoolean);
    this.modulus = paramBigInteger1;
    this.exponent = paramBigInteger2;
  }
  
  public BigInteger getExponent()
  {
    return this.exponent;
  }
  
  public BigInteger getModulus()
  {
    return this.modulus;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.RSAKeyParameters
 * JD-Core Version:    0.7.0.1
 */