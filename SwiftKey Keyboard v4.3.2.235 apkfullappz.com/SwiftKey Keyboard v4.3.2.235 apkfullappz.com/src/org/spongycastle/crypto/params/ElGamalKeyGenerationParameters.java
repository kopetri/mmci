package org.spongycastle.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class ElGamalKeyGenerationParameters
  extends KeyGenerationParameters
{
  private ElGamalParameters params;
  
  public ElGamalKeyGenerationParameters(SecureRandom paramSecureRandom, ElGamalParameters paramElGamalParameters)
  {
    super(paramSecureRandom, getStrength(paramElGamalParameters));
    this.params = paramElGamalParameters;
  }
  
  static int getStrength(ElGamalParameters paramElGamalParameters)
  {
    if (paramElGamalParameters.getL() != 0) {
      return paramElGamalParameters.getL();
    }
    return paramElGamalParameters.getP().bitLength();
  }
  
  public ElGamalParameters getParameters()
  {
    return this.params;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.ElGamalKeyGenerationParameters
 * JD-Core Version:    0.7.0.1
 */