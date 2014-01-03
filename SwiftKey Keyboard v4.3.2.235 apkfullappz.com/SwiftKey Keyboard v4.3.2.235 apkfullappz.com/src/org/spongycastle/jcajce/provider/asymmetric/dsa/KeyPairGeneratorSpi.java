package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.DSAKeyPairGenerator;
import org.spongycastle.crypto.generators.DSAParametersGenerator;
import org.spongycastle.crypto.params.DSAKeyGenerationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;

public class KeyPairGeneratorSpi
  extends KeyPairGenerator
{
  int certainty = 20;
  DSAKeyPairGenerator engine = new DSAKeyPairGenerator();
  boolean initialised = false;
  DSAKeyGenerationParameters param;
  SecureRandom random = new SecureRandom();
  int strength = 1024;
  
  public KeyPairGeneratorSpi()
  {
    super("DSA");
  }
  
  public KeyPair generateKeyPair()
  {
    if (!this.initialised)
    {
      DSAParametersGenerator localDSAParametersGenerator = new DSAParametersGenerator();
      localDSAParametersGenerator.init(this.strength, this.certainty, this.random);
      this.param = new DSAKeyGenerationParameters(this.random, localDSAParametersGenerator.generateParameters());
      this.engine.init(this.param);
      this.initialised = true;
    }
    AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = this.engine.generateKeyPair();
    DSAPublicKeyParameters localDSAPublicKeyParameters = (DSAPublicKeyParameters)localAsymmetricCipherKeyPair.getPublic();
    DSAPrivateKeyParameters localDSAPrivateKeyParameters = (DSAPrivateKeyParameters)localAsymmetricCipherKeyPair.getPrivate();
    return new KeyPair(new BCDSAPublicKey(localDSAPublicKeyParameters), new BCDSAPrivateKey(localDSAPrivateKeyParameters));
  }
  
  public void initialize(int paramInt, SecureRandom paramSecureRandom)
  {
    if ((paramInt < 512) || (paramInt > 1024) || (paramInt % 64 != 0)) {
      throw new InvalidParameterException("strength must be from 512 - 1024 and a multiple of 64");
    }
    this.strength = paramInt;
    this.random = paramSecureRandom;
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidAlgorithmParameterException
  {
    if (!(paramAlgorithmParameterSpec instanceof DSAParameterSpec)) {
      throw new InvalidAlgorithmParameterException("parameter object not a DSAParameterSpec");
    }
    DSAParameterSpec localDSAParameterSpec = (DSAParameterSpec)paramAlgorithmParameterSpec;
    this.param = new DSAKeyGenerationParameters(paramSecureRandom, new DSAParameters(localDSAParameterSpec.getP(), localDSAParameterSpec.getQ(), localDSAParameterSpec.getG()));
    this.engine.init(this.param);
    this.initialised = true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.dsa.KeyPairGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */