package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.ElGamalKeyPairGenerator;
import org.spongycastle.crypto.generators.ElGamalParametersGenerator;
import org.spongycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ElGamalParameterSpec;

public class KeyPairGeneratorSpi
  extends KeyPairGenerator
{
  int certainty = 20;
  ElGamalKeyPairGenerator engine = new ElGamalKeyPairGenerator();
  boolean initialised = false;
  ElGamalKeyGenerationParameters param;
  SecureRandom random = new SecureRandom();
  int strength = 1024;
  
  public KeyPairGeneratorSpi()
  {
    super("ElGamal");
  }
  
  public KeyPair generateKeyPair()
  {
    DHParameterSpec localDHParameterSpec;
    if (!this.initialised)
    {
      localDHParameterSpec = BouncyCastleProvider.CONFIGURATION.getDHDefaultParameters();
      if ((localDHParameterSpec == null) || (localDHParameterSpec.getP().bitLength() != this.strength)) {
        break label138;
      }
    }
    label138:
    ElGamalParametersGenerator localElGamalParametersGenerator;
    for (this.param = new ElGamalKeyGenerationParameters(this.random, new ElGamalParameters(localDHParameterSpec.getP(), localDHParameterSpec.getG(), localDHParameterSpec.getL()));; this.param = new ElGamalKeyGenerationParameters(this.random, localElGamalParametersGenerator.generateParameters()))
    {
      this.engine.init(this.param);
      this.initialised = true;
      AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = this.engine.generateKeyPair();
      ElGamalPublicKeyParameters localElGamalPublicKeyParameters = (ElGamalPublicKeyParameters)localAsymmetricCipherKeyPair.getPublic();
      ElGamalPrivateKeyParameters localElGamalPrivateKeyParameters = (ElGamalPrivateKeyParameters)localAsymmetricCipherKeyPair.getPrivate();
      return new KeyPair(new BCElGamalPublicKey(localElGamalPublicKeyParameters), new BCElGamalPrivateKey(localElGamalPrivateKeyParameters));
      localElGamalParametersGenerator = new ElGamalParametersGenerator();
      localElGamalParametersGenerator.init(this.strength, this.certainty, this.random);
    }
  }
  
  public void initialize(int paramInt, SecureRandom paramSecureRandom)
  {
    this.strength = paramInt;
    this.random = paramSecureRandom;
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidAlgorithmParameterException
  {
    if ((!(paramAlgorithmParameterSpec instanceof ElGamalParameterSpec)) && (!(paramAlgorithmParameterSpec instanceof DHParameterSpec))) {
      throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec or an ElGamalParameterSpec");
    }
    ElGamalParameterSpec localElGamalParameterSpec;
    if ((paramAlgorithmParameterSpec instanceof ElGamalParameterSpec)) {
      localElGamalParameterSpec = (ElGamalParameterSpec)paramAlgorithmParameterSpec;
    }
    DHParameterSpec localDHParameterSpec;
    for (this.param = new ElGamalKeyGenerationParameters(paramSecureRandom, new ElGamalParameters(localElGamalParameterSpec.getP(), localElGamalParameterSpec.getG()));; this.param = new ElGamalKeyGenerationParameters(paramSecureRandom, new ElGamalParameters(localDHParameterSpec.getP(), localDHParameterSpec.getG(), localDHParameterSpec.getL())))
    {
      this.engine.init(this.param);
      this.initialised = true;
      return;
      localDHParameterSpec = (DHParameterSpec)paramAlgorithmParameterSpec;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.elgamal.KeyPairGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */