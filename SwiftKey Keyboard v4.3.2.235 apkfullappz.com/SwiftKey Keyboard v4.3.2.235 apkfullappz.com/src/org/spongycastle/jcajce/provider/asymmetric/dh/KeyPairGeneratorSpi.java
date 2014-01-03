package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.DHBasicKeyPairGenerator;
import org.spongycastle.crypto.generators.DHParametersGenerator;
import org.spongycastle.crypto.params.DHKeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class KeyPairGeneratorSpi
  extends KeyPairGenerator
{
  private static Hashtable params = new Hashtable();
  int certainty = 20;
  DHBasicKeyPairGenerator engine = new DHBasicKeyPairGenerator();
  boolean initialised = false;
  DHKeyGenerationParameters param;
  SecureRandom random = new SecureRandom();
  int strength = 1024;
  
  public KeyPairGeneratorSpi()
  {
    super("DH");
  }
  
  public KeyPair generateKeyPair()
  {
    Integer localInteger;
    if (!this.initialised)
    {
      localInteger = new Integer(this.strength);
      if (!params.containsKey(localInteger)) {
        break label114;
      }
      this.param = ((DHKeyGenerationParameters)params.get(localInteger));
    }
    for (;;)
    {
      this.engine.init(this.param);
      this.initialised = true;
      AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = this.engine.generateKeyPair();
      DHPublicKeyParameters localDHPublicKeyParameters = (DHPublicKeyParameters)localAsymmetricCipherKeyPair.getPublic();
      DHPrivateKeyParameters localDHPrivateKeyParameters = (DHPrivateKeyParameters)localAsymmetricCipherKeyPair.getPrivate();
      return new KeyPair(new BCDHPublicKey(localDHPublicKeyParameters), new BCDHPrivateKey(localDHPrivateKeyParameters));
      label114:
      DHParameterSpec localDHParameterSpec = BouncyCastleProvider.CONFIGURATION.getDHDefaultParameters();
      if ((localDHParameterSpec != null) && (localDHParameterSpec.getP().bitLength() == this.strength))
      {
        this.param = new DHKeyGenerationParameters(this.random, new DHParameters(localDHParameterSpec.getP(), localDHParameterSpec.getG(), null, localDHParameterSpec.getL()));
      }
      else
      {
        DHParametersGenerator localDHParametersGenerator = new DHParametersGenerator();
        localDHParametersGenerator.init(this.strength, this.certainty, this.random);
        this.param = new DHKeyGenerationParameters(this.random, localDHParametersGenerator.generateParameters());
        params.put(localInteger, this.param);
      }
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
    if (!(paramAlgorithmParameterSpec instanceof DHParameterSpec)) {
      throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec");
    }
    DHParameterSpec localDHParameterSpec = (DHParameterSpec)paramAlgorithmParameterSpec;
    this.param = new DHKeyGenerationParameters(paramSecureRandom, new DHParameters(localDHParameterSpec.getP(), localDHParameterSpec.getG(), null, localDHParameterSpec.getL()));
    this.engine.init(this.param);
    this.initialised = true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.dh.KeyPairGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */