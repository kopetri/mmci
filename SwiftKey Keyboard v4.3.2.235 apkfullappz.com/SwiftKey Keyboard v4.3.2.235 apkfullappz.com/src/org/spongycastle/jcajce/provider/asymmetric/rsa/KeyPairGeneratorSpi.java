package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.RSAKeyPairGenerator;
import org.spongycastle.crypto.params.RSAKeyGenerationParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class KeyPairGeneratorSpi
  extends KeyPairGenerator
{
  static final BigInteger defaultPublicExponent = BigInteger.valueOf(65537L);
  static final int defaultTests = 12;
  RSAKeyPairGenerator engine;
  RSAKeyGenerationParameters param;
  
  public KeyPairGeneratorSpi()
  {
    super("RSA");
    this.engine = new RSAKeyPairGenerator();
    this.param = new RSAKeyGenerationParameters(defaultPublicExponent, new SecureRandom(), 2048, 12);
    this.engine.init(this.param);
  }
  
  public KeyPairGeneratorSpi(String paramString)
  {
    super(paramString);
  }
  
  public KeyPair generateKeyPair()
  {
    AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = this.engine.generateKeyPair();
    RSAKeyParameters localRSAKeyParameters = (RSAKeyParameters)localAsymmetricCipherKeyPair.getPublic();
    RSAPrivateCrtKeyParameters localRSAPrivateCrtKeyParameters = (RSAPrivateCrtKeyParameters)localAsymmetricCipherKeyPair.getPrivate();
    return new KeyPair(new BCRSAPublicKey(localRSAKeyParameters), new BCRSAPrivateCrtKey(localRSAPrivateCrtKeyParameters));
  }
  
  public void initialize(int paramInt, SecureRandom paramSecureRandom)
  {
    this.param = new RSAKeyGenerationParameters(defaultPublicExponent, paramSecureRandom, paramInt, 12);
    this.engine.init(this.param);
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidAlgorithmParameterException
  {
    if (!(paramAlgorithmParameterSpec instanceof RSAKeyGenParameterSpec)) {
      throw new InvalidAlgorithmParameterException("parameter object not a RSAKeyGenParameterSpec");
    }
    RSAKeyGenParameterSpec localRSAKeyGenParameterSpec = (RSAKeyGenParameterSpec)paramAlgorithmParameterSpec;
    this.param = new RSAKeyGenerationParameters(localRSAKeyGenParameterSpec.getPublicExponent(), paramSecureRandom, localRSAKeyGenParameterSpec.getKeysize(), 12);
    this.engine.init(this.param);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */