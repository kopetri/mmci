package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.params.DHParameters;

public class DHParametersGenerator
{
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  private int certainty;
  private SecureRandom random;
  private int size;
  
  public DHParameters generateParameters()
  {
    BigInteger[] arrayOfBigInteger = DHParametersHelper.generateSafePrimes(this.size, this.certainty, this.random);
    BigInteger localBigInteger1 = arrayOfBigInteger[0];
    BigInteger localBigInteger2 = arrayOfBigInteger[1];
    return new DHParameters(localBigInteger1, DHParametersHelper.selectGenerator(localBigInteger1, localBigInteger2, this.random), localBigInteger2, TWO, null);
  }
  
  public void init(int paramInt1, int paramInt2, SecureRandom paramSecureRandom)
  {
    this.size = paramInt1;
    this.certainty = paramInt2;
    this.random = paramSecureRandom;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.DHParametersGenerator
 * JD-Core Version:    0.7.0.1
 */