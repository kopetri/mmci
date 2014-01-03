package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.params.ElGamalParameters;

public class ElGamalParametersGenerator
{
  private int certainty;
  private SecureRandom random;
  private int size;
  
  public ElGamalParameters generateParameters()
  {
    BigInteger[] arrayOfBigInteger = DHParametersHelper.generateSafePrimes(this.size, this.certainty, this.random);
    BigInteger localBigInteger = arrayOfBigInteger[0];
    return new ElGamalParameters(localBigInteger, DHParametersHelper.selectGenerator(localBigInteger, arrayOfBigInteger[1], this.random));
  }
  
  public void init(int paramInt1, int paramInt2, SecureRandom paramSecureRandom)
  {
    this.size = paramInt1;
    this.certainty = paramInt2;
    this.random = paramSecureRandom;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.ElGamalParametersGenerator
 * JD-Core Version:    0.7.0.1
 */