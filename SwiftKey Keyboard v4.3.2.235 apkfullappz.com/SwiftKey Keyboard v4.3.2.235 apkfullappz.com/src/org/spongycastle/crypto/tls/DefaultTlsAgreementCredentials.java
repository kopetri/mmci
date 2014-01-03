package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.agreement.DHBasicAgreement;
import org.spongycastle.crypto.agreement.ECDHBasicAgreement;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.util.BigIntegers;

public class DefaultTlsAgreementCredentials
  implements TlsAgreementCredentials
{
  protected BasicAgreement basicAgreement;
  protected Certificate clientCert;
  protected AsymmetricKeyParameter clientPrivateKey;
  
  public DefaultTlsAgreementCredentials(Certificate paramCertificate, AsymmetricKeyParameter paramAsymmetricKeyParameter)
  {
    if (paramCertificate == null) {
      throw new IllegalArgumentException("'clientCertificate' cannot be null");
    }
    if (paramCertificate.certs.length == 0) {
      throw new IllegalArgumentException("'clientCertificate' cannot be empty");
    }
    if (paramAsymmetricKeyParameter == null) {
      throw new IllegalArgumentException("'clientPrivateKey' cannot be null");
    }
    if (!paramAsymmetricKeyParameter.isPrivate()) {
      throw new IllegalArgumentException("'clientPrivateKey' must be private");
    }
    if ((paramAsymmetricKeyParameter instanceof DHPrivateKeyParameters)) {}
    for (this.basicAgreement = new DHBasicAgreement();; this.basicAgreement = new ECDHBasicAgreement())
    {
      this.clientCert = paramCertificate;
      this.clientPrivateKey = paramAsymmetricKeyParameter;
      return;
      if (!(paramAsymmetricKeyParameter instanceof ECPrivateKeyParameters)) {
        break;
      }
    }
    throw new IllegalArgumentException("'clientPrivateKey' type not supported: " + paramAsymmetricKeyParameter.getClass().getName());
  }
  
  public byte[] generateAgreement(AsymmetricKeyParameter paramAsymmetricKeyParameter)
  {
    this.basicAgreement.init(this.clientPrivateKey);
    return BigIntegers.asUnsignedByteArray(this.basicAgreement.calculateAgreement(paramAsymmetricKeyParameter));
  }
  
  public Certificate getCertificate()
  {
    return this.clientCert;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.DefaultTlsAgreementCredentials
 * JD-Core Version:    0.7.0.1
 */