package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.generators.GOST3410ParametersGenerator;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public abstract class AlgorithmParameterGeneratorSpi
  extends java.security.AlgorithmParameterGeneratorSpi
{
  protected SecureRandom random;
  protected int strength = 1024;
  
  protected AlgorithmParameters engineGenerateParameters()
  {
    GOST3410ParametersGenerator localGOST3410ParametersGenerator = new GOST3410ParametersGenerator();
    if (this.random != null) {
      localGOST3410ParametersGenerator.init(this.strength, 2, this.random);
    }
    for (;;)
    {
      GOST3410Parameters localGOST3410Parameters = localGOST3410ParametersGenerator.generateParameters();
      try
      {
        AlgorithmParameters localAlgorithmParameters = AlgorithmParameters.getInstance("GOST3410", BouncyCastleProvider.PROVIDER_NAME);
        localAlgorithmParameters.init(new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(localGOST3410Parameters.getP(), localGOST3410Parameters.getQ(), localGOST3410Parameters.getA())));
        return localAlgorithmParameters;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException.getMessage());
      }
      localGOST3410ParametersGenerator.init(this.strength, 2, new SecureRandom());
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
    throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for GOST3410 parameter generation.");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.gost.AlgorithmParameterGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */