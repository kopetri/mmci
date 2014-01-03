package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;

public class GOST3410KeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private static final BigInteger ZERO = BigInteger.valueOf(0L);
  private GOST3410KeyGenerationParameters param;
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    GOST3410Parameters localGOST3410Parameters = this.param.getParameters();
    SecureRandom localSecureRandom = this.param.getRandom();
    BigInteger localBigInteger1 = localGOST3410Parameters.getQ();
    BigInteger localBigInteger2 = localGOST3410Parameters.getP();
    BigInteger localBigInteger3 = localGOST3410Parameters.getA();
    BigInteger localBigInteger4;
    do
    {
      localBigInteger4 = new BigInteger(256, localSecureRandom);
    } while ((localBigInteger4.equals(ZERO)) || (localBigInteger4.compareTo(localBigInteger1) >= 0));
    return new AsymmetricCipherKeyPair(new GOST3410PublicKeyParameters(localBigInteger3.modPow(localBigInteger4, localBigInteger2), localGOST3410Parameters), new GOST3410PrivateKeyParameters(localBigInteger4, localGOST3410Parameters));
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters)
  {
    this.param = ((GOST3410KeyGenerationParameters)paramKeyGenerationParameters);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.GOST3410KeyPairGenerator
 * JD-Core Version:    0.7.0.1
 */