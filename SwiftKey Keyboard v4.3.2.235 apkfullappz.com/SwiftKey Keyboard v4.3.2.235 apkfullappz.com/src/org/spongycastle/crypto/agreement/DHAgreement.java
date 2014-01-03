package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.generators.DHKeyPairGenerator;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHKeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class DHAgreement
{
  private DHParameters dhParams;
  private DHPrivateKeyParameters key;
  private BigInteger privateValue;
  private SecureRandom random;
  
  public BigInteger calculateAgreement(DHPublicKeyParameters paramDHPublicKeyParameters, BigInteger paramBigInteger)
  {
    if (!paramDHPublicKeyParameters.getParameters().equals(this.dhParams)) {
      throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
    }
    BigInteger localBigInteger = this.dhParams.getP();
    return paramBigInteger.modPow(this.key.getX(), localBigInteger).multiply(paramDHPublicKeyParameters.getY().modPow(this.privateValue, localBigInteger)).mod(localBigInteger);
  }
  
  public BigInteger calculateMessage()
  {
    DHKeyPairGenerator localDHKeyPairGenerator = new DHKeyPairGenerator();
    localDHKeyPairGenerator.init(new DHKeyGenerationParameters(this.random, this.dhParams));
    AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = localDHKeyPairGenerator.generateKeyPair();
    this.privateValue = ((DHPrivateKeyParameters)localAsymmetricCipherKeyPair.getPrivate()).getX();
    return ((DHPublicKeyParameters)localAsymmetricCipherKeyPair.getPublic()).getY();
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    ParametersWithRandom localParametersWithRandom;
    if ((paramCipherParameters instanceof ParametersWithRandom))
    {
      localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
      this.random = localParametersWithRandom.getRandom();
    }
    for (AsymmetricKeyParameter localAsymmetricKeyParameter = (AsymmetricKeyParameter)localParametersWithRandom.getParameters(); !(localAsymmetricKeyParameter instanceof DHPrivateKeyParameters); localAsymmetricKeyParameter = (AsymmetricKeyParameter)paramCipherParameters)
    {
      throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
      this.random = new SecureRandom();
    }
    this.key = ((DHPrivateKeyParameters)localAsymmetricKeyParameter);
    this.dhParams = this.key.getParameters();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.agreement.DHAgreement
 * JD-Core Version:    0.7.0.1
 */