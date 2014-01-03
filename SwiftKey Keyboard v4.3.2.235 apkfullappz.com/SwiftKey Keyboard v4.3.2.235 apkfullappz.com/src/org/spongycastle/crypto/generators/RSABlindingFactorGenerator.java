package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSABlindingFactorGenerator
{
  private static BigInteger ONE = BigInteger.valueOf(1L);
  private static BigInteger ZERO = BigInteger.valueOf(0L);
  private RSAKeyParameters key;
  private SecureRandom random;
  
  public BigInteger generateBlindingFactor()
  {
    if (this.key == null) {
      throw new IllegalStateException("generator not initialised");
    }
    BigInteger localBigInteger1 = this.key.getModulus();
    int i = -1 + localBigInteger1.bitLength();
    BigInteger localBigInteger2;
    BigInteger localBigInteger3;
    do
    {
      localBigInteger2 = new BigInteger(i, this.random);
      localBigInteger3 = localBigInteger2.gcd(localBigInteger1);
    } while ((localBigInteger2.equals(ZERO)) || (localBigInteger2.equals(ONE)) || (!localBigInteger3.equals(ONE)));
    return localBigInteger2;
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    ParametersWithRandom localParametersWithRandom;
    if ((paramCipherParameters instanceof ParametersWithRandom))
    {
      localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
      this.key = ((RSAKeyParameters)localParametersWithRandom.getParameters());
    }
    for (this.random = localParametersWithRandom.getRandom(); (this.key instanceof RSAPrivateCrtKeyParameters); this.random = new SecureRandom())
    {
      throw new IllegalArgumentException("generator requires RSA public key");
      this.key = ((RSAKeyParameters)paramCipherParameters);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.RSABlindingFactorGenerator
 * JD-Core Version:    0.7.0.1
 */