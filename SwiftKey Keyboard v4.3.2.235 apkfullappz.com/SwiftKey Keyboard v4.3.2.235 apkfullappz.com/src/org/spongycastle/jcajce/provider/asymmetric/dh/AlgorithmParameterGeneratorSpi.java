package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.generators.DHParametersGenerator;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class AlgorithmParameterGeneratorSpi
  extends java.security.AlgorithmParameterGeneratorSpi
{
  private int l = 0;
  protected SecureRandom random;
  protected int strength = 1024;
  
  protected AlgorithmParameters engineGenerateParameters()
  {
    DHParametersGenerator localDHParametersGenerator = new DHParametersGenerator();
    if (this.random != null) {
      localDHParametersGenerator.init(this.strength, 20, this.random);
    }
    for (;;)
    {
      DHParameters localDHParameters = localDHParametersGenerator.generateParameters();
      try
      {
        AlgorithmParameters localAlgorithmParameters = AlgorithmParameters.getInstance("DH", BouncyCastleProvider.PROVIDER_NAME);
        localAlgorithmParameters.init(new DHParameterSpec(localDHParameters.getP(), localDHParameters.getG(), this.l));
        return localAlgorithmParameters;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException.getMessage());
      }
      localDHParametersGenerator.init(this.strength, 20, new SecureRandom());
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
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.dh.AlgorithmParameterGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */