package org.spongycastle.cms.jcajce;

import java.security.PrivateKey;
import java.security.Provider;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.SymmetricKeyUnwrapper;
import org.spongycastle.operator.jcajce.JceAsymmetricKeyUnwrapper;
import org.spongycastle.operator.jcajce.JceSymmetricKeyUnwrapper;

class ProviderJcaJceExtHelper
  extends ProviderJcaJceHelper
  implements JcaJceExtHelper
{
  public ProviderJcaJceExtHelper(Provider paramProvider)
  {
    super(paramProvider);
  }
  
  public JceAsymmetricKeyUnwrapper createAsymmetricUnwrapper(AlgorithmIdentifier paramAlgorithmIdentifier, PrivateKey paramPrivateKey)
  {
    return new JceAsymmetricKeyUnwrapper(paramAlgorithmIdentifier, paramPrivateKey).setProvider(this.provider);
  }
  
  public SymmetricKeyUnwrapper createSymmetricUnwrapper(AlgorithmIdentifier paramAlgorithmIdentifier, SecretKey paramSecretKey)
  {
    return new JceSymmetricKeyUnwrapper(paramAlgorithmIdentifier, paramSecretKey).setProvider(this.provider);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.ProviderJcaJceExtHelper
 * JD-Core Version:    0.7.0.1
 */