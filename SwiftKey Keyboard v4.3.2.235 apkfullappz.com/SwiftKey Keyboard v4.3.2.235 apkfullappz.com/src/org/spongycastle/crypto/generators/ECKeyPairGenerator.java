package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECPoint;

public class ECKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator, ECConstants
{
  ECDomainParameters params;
  SecureRandom random;
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    BigInteger localBigInteger1 = this.params.getN();
    int i = localBigInteger1.bitLength();
    BigInteger localBigInteger2;
    do
    {
      localBigInteger2 = new BigInteger(i, this.random);
    } while ((localBigInteger2.equals(ZERO)) || (localBigInteger2.compareTo(localBigInteger1) >= 0));
    return new AsymmetricCipherKeyPair(new ECPublicKeyParameters(this.params.getG().multiply(localBigInteger2), this.params), new ECPrivateKeyParameters(localBigInteger2, this.params));
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters)
  {
    ECKeyGenerationParameters localECKeyGenerationParameters = (ECKeyGenerationParameters)paramKeyGenerationParameters;
    this.random = localECKeyGenerationParameters.getRandom();
    this.params = localECKeyGenerationParameters.getDomainParameters();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.ECKeyPairGenerator
 * JD-Core Version:    0.7.0.1
 */