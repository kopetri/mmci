package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class DHBasicAgreement
  implements BasicAgreement
{
  private DHParameters dhParams;
  private DHPrivateKeyParameters key;
  
  public BigInteger calculateAgreement(CipherParameters paramCipherParameters)
  {
    DHPublicKeyParameters localDHPublicKeyParameters = (DHPublicKeyParameters)paramCipherParameters;
    if (!localDHPublicKeyParameters.getParameters().equals(this.dhParams)) {
      throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
    }
    return localDHPublicKeyParameters.getY().modPow(this.key.getX(), this.dhParams.getP());
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof ParametersWithRandom)) {}
    for (AsymmetricKeyParameter localAsymmetricKeyParameter = (AsymmetricKeyParameter)((ParametersWithRandom)paramCipherParameters).getParameters(); !(localAsymmetricKeyParameter instanceof DHPrivateKeyParameters); localAsymmetricKeyParameter = (AsymmetricKeyParameter)paramCipherParameters) {
      throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
    }
    this.key = ((DHPrivateKeyParameters)localAsymmetricKeyParameter);
    this.dhParams = this.key.getParameters();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.agreement.DHBasicAgreement
 * JD-Core Version:    0.7.0.1
 */