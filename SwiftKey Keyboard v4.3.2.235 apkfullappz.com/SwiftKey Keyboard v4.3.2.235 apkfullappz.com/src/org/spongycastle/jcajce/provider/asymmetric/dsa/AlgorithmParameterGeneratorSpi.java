package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import org.spongycastle.crypto.generators.DSAParametersGenerator;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class AlgorithmParameterGeneratorSpi
  extends java.security.AlgorithmParameterGeneratorSpi
{
  protected SecureRandom random;
  protected int strength = 1024;
  
  protected AlgorithmParameters engineGenerateParameters()
  {
    DSAParametersGenerator localDSAParametersGenerator = new DSAParametersGenerator();
    if (this.random != null) {
      localDSAParametersGenerator.init(this.strength, 20, this.random);
    }
    for (;;)
    {
      DSAParameters localDSAParameters = localDSAParametersGenerator.generateParameters();
      try
      {
        AlgorithmParameters localAlgorithmParameters = AlgorithmParameters.getInstance("DSA", BouncyCastleProvider.PROVIDER_NAME);
        localAlgorithmParameters.init(new DSAParameterSpec(localDSAParameters.getP(), localDSAParameters.getQ(), localDSAParameters.getG()));
        return localAlgorithmParameters;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException.getMessage());
      }
      localDSAParametersGenerator.init(this.strength, 20, new SecureRandom());
    }
  }
  
  protected void engineInit(int paramInt, SecureRandom paramSecureRandom)
  {
    if ((paramInt < 512) || (paramInt > 1024) || (paramInt % 64 != 0)) {
      throw new InvalidParameterException("strength must be from 512 - 1024 and a multiple of 64");
    }
    this.strength = paramInt;
    this.random = paramSecureRandom;
  }
  
  protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidAlgorithmParameterException
  {
    throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DSA parameter generation.");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.dsa.AlgorithmParameterGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */