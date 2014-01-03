package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;

public class ElGamalKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private ElGamalKeyGenerationParameters param;
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    DHKeyGeneratorHelper localDHKeyGeneratorHelper = DHKeyGeneratorHelper.INSTANCE;
    ElGamalParameters localElGamalParameters = this.param.getParameters();
    DHParameters localDHParameters = new DHParameters(localElGamalParameters.getP(), localElGamalParameters.getG(), null, localElGamalParameters.getL());
    BigInteger localBigInteger = localDHKeyGeneratorHelper.calculatePrivate(localDHParameters, this.param.getRandom());
    return new AsymmetricCipherKeyPair(new ElGamalPublicKeyParameters(localDHKeyGeneratorHelper.calculatePublic(localDHParameters, localBigInteger), localElGamalParameters), new ElGamalPrivateKeyParameters(localBigInteger, localElGamalParameters));
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters)
  {
    this.param = ((ElGamalKeyGenerationParameters)paramKeyGenerationParameters);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.ElGamalKeyPairGenerator
 * JD-Core Version:    0.7.0.1
 */