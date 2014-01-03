package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DSAKeyGenerationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.util.BigIntegers;

public class DSAKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private DSAKeyGenerationParameters param;
  
  private static BigInteger calculatePublicKey(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3)
  {
    return paramBigInteger2.modPow(paramBigInteger3, paramBigInteger1);
  }
  
  private static BigInteger generatePrivateKey(BigInteger paramBigInteger, SecureRandom paramSecureRandom)
  {
    return BigIntegers.createRandomInRange(ONE, paramBigInteger.subtract(ONE), paramSecureRandom);
  }
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    DSAParameters localDSAParameters = this.param.getParameters();
    BigInteger localBigInteger = generatePrivateKey(localDSAParameters.getQ(), this.param.getRandom());
    return new AsymmetricCipherKeyPair(new DSAPublicKeyParameters(calculatePublicKey(localDSAParameters.getP(), localDSAParameters.getG(), localBigInteger), localDSAParameters), new DSAPrivateKeyParameters(localBigInteger, localDSAParameters));
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters)
  {
    this.param = ((DSAKeyGenerationParameters)paramKeyGenerationParameters);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.DSAKeyPairGenerator
 * JD-Core Version:    0.7.0.1
 */