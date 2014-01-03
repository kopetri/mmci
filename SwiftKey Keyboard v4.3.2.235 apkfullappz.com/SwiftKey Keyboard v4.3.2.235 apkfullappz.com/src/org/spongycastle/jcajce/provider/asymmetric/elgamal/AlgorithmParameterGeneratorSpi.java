package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.generators.ElGamalParametersGenerator;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class AlgorithmParameterGeneratorSpi
  extends java.security.AlgorithmParameterGeneratorSpi
{
  private int l = 0;
  protected SecureRandom random;
  protected int strength = 1024;
  
  protected AlgorithmParameters engineGenerateParameters()
  {
    ElGamalParametersGenerator localElGamalParametersGenerator = new ElGamalParametersGenerator();
    if (this.random != null) {
      localElGamalParametersGenerator.init(this.strength, 20, this.random);
    }
    for (;;)
    {
      ElGamalParameters localElGamalParameters = localElGamalParametersGenerator.generateParameters();
      try
      {
        AlgorithmParameters localAlgorithmParameters = AlgorithmParameters.getInstance("ElGamal", BouncyCastleProvider.PROVIDER_NAME);
        localAlgorithmParameters.init(new DHParameterSpec(localElGamalParameters.getP(), localElGamalParameters.getG(), this.l));
        return localAlgorithmParameters;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException.getMessage());
      }
      localElGamalParametersGenerator.init(this.strength, 20, new SecureRandom());
    }
  }
  
  protected void engineInit(int paramInt, SecureRandom paramSecureRandom)
  {
    this.strength = paramInt;
    this.random = paramSecureRandom;
  }
  
  protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidAlgorithmParameterException
  {
    if (!(paramAlgorithmParameterSpec instanceof DHGenParameterSpec)) {
      throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
    }
    DHGenParameterSpec localDHGenParameterSpec = (DHGenParameterSpec)paramAlgorithmParameterSpec;
    this.strength = localDHGenParameterSpec.getPrimeSize();
    this.l = localDHGenParameterSpec.getExponentSize();
    this.random = paramSecureRandom;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.elgamal.AlgorithmParameterGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */