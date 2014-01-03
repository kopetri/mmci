package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class DefaultTlsSignerCredentials
  implements TlsSignerCredentials
{
  protected Certificate clientCert;
  protected AsymmetricKeyParameter clientPrivateKey;
  protected TlsSigner clientSigner;
  protected TlsClientContext context;
  
  public DefaultTlsSignerCredentials(TlsClientContext paramTlsClientContext, Certificate paramCertificate, AsymmetricKeyParameter paramAsymmetricKeyParameter)
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
    if ((paramAsymmetricKeyParameter instanceof RSAKeyParameters)) {
      this.clientSigner = new TlsRSASigner();
    }
    for (;;)
    {
      this.context = paramTlsClientContext;
      this.clientCert = paramCertificate;
      this.clientPrivateKey = paramAsymmetricKeyParameter;
      return;
      if ((paramAsymmetricKeyParameter instanceof DSAPrivateKeyParameters))
      {
        this.clientSigner = new TlsDSSSigner();
      }
      else
      {
        if (!(paramAsymmetricKeyParameter instanceof ECPrivateKeyParameters)) {
          break;
        }
        this.clientSigner = new TlsECDSASigner();
      }
    }
    throw new IllegalArgumentException("'clientPrivateKey' type not supported: " + paramAsymmetricKeyParameter.getClass().getName());
  }
  
  public byte[] generateCertificateSignature(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      byte[] arrayOfByte = this.clientSigner.calculateRawSignature(this.context.getSecureRandom(), this.clientPrivateKey, paramArrayOfByte);
      return arrayOfByte;
    }
    catch (CryptoException localCryptoException)
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public Certificate getCertificate()
  {
    return this.clientCert;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.DefaultTlsSignerCredentials
 * JD-Core Version:    0.7.0.1
 */