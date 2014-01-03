package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;

class TlsRSAKeyExchange
  implements TlsKeyExchange
{
  protected TlsClientContext context;
  protected byte[] premasterSecret;
  protected RSAKeyParameters rsaServerPublicKey = null;
  protected AsymmetricKeyParameter serverPublicKey = null;
  
  TlsRSAKeyExchange(TlsClientContext paramTlsClientContext)
  {
    this.context = paramTlsClientContext;
  }
  
  public void generateClientKeyExchange(OutputStream paramOutputStream)
    throws IOException
  {
    this.premasterSecret = TlsRSAUtils.generateEncryptedPreMasterSecret(this.context, this.rsaServerPublicKey, paramOutputStream);
  }
  
  public byte[] generatePremasterSecret()
    throws IOException
  {
    byte[] arrayOfByte = this.premasterSecret;
    this.premasterSecret = null;
    return arrayOfByte;
  }
  
  public void processClientCredentials(TlsCredentials paramTlsCredentials)
    throws IOException
  {
    if (!(paramTlsCredentials instanceof TlsSignerCredentials)) {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public void processServerCertificate(Certificate paramCertificate)
    throws IOException
  {
    X509CertificateStructure localX509CertificateStructure = paramCertificate.certs[0];
    SubjectPublicKeyInfo localSubjectPublicKeyInfo = localX509CertificateStructure.getSubjectPublicKeyInfo();
    try
    {
      this.serverPublicKey = PublicKeyFactory.createKey(localSubjectPublicKeyInfo);
      if (this.serverPublicKey.isPrivate()) {
        throw new TlsFatalAlert((short)80);
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      throw new TlsFatalAlert((short)43);
    }
    this.rsaServerPublicKey = validateRSAPublicKey((RSAKeyParameters)this.serverPublicKey);
    TlsUtils.validateKeyUsage(localX509CertificateStructure, 32);
  }
  
  public void processServerKeyExchange(InputStream paramInputStream)
    throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
  
  public void skipClientCredentials()
    throws IOException
  {}
  
  public void skipServerCertificate()
    throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
  
  public void skipServerKeyExchange()
    throws IOException
  {}
  
  public void validateCertificateRequest(CertificateRequest paramCertificateRequest)
    throws IOException
  {
    short[] arrayOfShort = paramCertificateRequest.getCertificateTypes();
    for (int i = 0; i < arrayOfShort.length; i++) {
      switch (arrayOfShort[i])
      {
      default: 
        throw new TlsFatalAlert((short)47);
      }
    }
  }
  
  protected RSAKeyParameters validateRSAPublicKey(RSAKeyParameters paramRSAKeyParameters)
    throws IOException
  {
    if (!paramRSAKeyParameters.getExponent().isProbablePrime(2)) {
      throw new TlsFatalAlert((short)47);
    }
    return paramRSAKeyParameters;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsRSAKeyExchange
 * JD-Core Version:    0.7.0.1
 */