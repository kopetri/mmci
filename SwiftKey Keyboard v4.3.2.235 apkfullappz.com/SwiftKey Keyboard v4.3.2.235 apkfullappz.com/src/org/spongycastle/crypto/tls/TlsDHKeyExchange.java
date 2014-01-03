package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;

class TlsDHKeyExchange
  implements TlsKeyExchange
{
  protected static final BigInteger ONE = BigInteger.valueOf(1L);
  protected static final BigInteger TWO = BigInteger.valueOf(2L);
  protected TlsAgreementCredentials agreementCredentials;
  protected TlsClientContext context;
  protected DHPrivateKeyParameters dhAgreeClientPrivateKey = null;
  protected DHPublicKeyParameters dhAgreeServerPublicKey = null;
  protected int keyExchange;
  protected AsymmetricKeyParameter serverPublicKey = null;
  protected TlsSigner tlsSigner;
  
  TlsDHKeyExchange(TlsClientContext paramTlsClientContext, int paramInt)
  {
    switch (paramInt)
    {
    case 4: 
    case 6: 
    case 8: 
    default: 
      throw new IllegalArgumentException("unsupported key exchange algorithm");
    case 7: 
    case 9: 
      this.tlsSigner = null;
    }
    for (;;)
    {
      this.context = paramTlsClientContext;
      this.keyExchange = paramInt;
      return;
      this.tlsSigner = new TlsRSASigner();
      continue;
      this.tlsSigner = new TlsDSSSigner();
    }
  }
  
  protected boolean areCompatibleParameters(DHParameters paramDHParameters1, DHParameters paramDHParameters2)
  {
    return (paramDHParameters1.getP().equals(paramDHParameters2.getP())) && (paramDHParameters1.getG().equals(paramDHParameters2.getG()));
  }
  
  protected byte[] calculateDHBasicAgreement(DHPublicKeyParameters paramDHPublicKeyParameters, DHPrivateKeyParameters paramDHPrivateKeyParameters)
  {
    return TlsDHUtils.calculateDHBasicAgreement(paramDHPublicKeyParameters, paramDHPrivateKeyParameters);
  }
  
  public void generateClientKeyExchange(OutputStream paramOutputStream)
    throws IOException
  {
    if (this.agreementCredentials == null) {
      generateEphemeralClientKeyExchange(this.dhAgreeServerPublicKey.getParameters(), paramOutputStream);
    }
  }
  
  protected AsymmetricCipherKeyPair generateDHKeyPair(DHParameters paramDHParameters)
  {
    return TlsDHUtils.generateDHKeyPair(this.context.getSecureRandom(), paramDHParameters);
  }
  
  protected void generateEphemeralClientKeyExchange(DHParameters paramDHParameters, OutputStream paramOutputStream)
    throws IOException
  {
    this.dhAgreeClientPrivateKey = TlsDHUtils.generateEphemeralClientKeyExchange(this.context.getSecureRandom(), paramDHParameters, paramOutputStream);
  }
  
  public byte[] generatePremasterSecret()
    throws IOException
  {
    if (this.agreementCredentials != null) {
      return this.agreementCredentials.generateAgreement(this.dhAgreeServerPublicKey);
    }
    return calculateDHBasicAgreement(this.dhAgreeServerPublicKey, this.dhAgreeClientPrivateKey);
  }
  
  public void processClientCredentials(TlsCredentials paramTlsCredentials)
    throws IOException
  {
    if ((paramTlsCredentials instanceof TlsAgreementCredentials)) {
      this.agreementCredentials = ((TlsAgreementCredentials)paramTlsCredentials);
    }
    while ((paramTlsCredentials instanceof TlsSignerCredentials)) {
      return;
    }
    throw new TlsFatalAlert((short)80);
  }
  
  public void processServerCertificate(Certificate paramCertificate)
    throws IOException
  {
    X509CertificateStructure localX509CertificateStructure = paramCertificate.certs[0];
    SubjectPublicKeyInfo localSubjectPublicKeyInfo = localX509CertificateStructure.getSubjectPublicKeyInfo();
    try
    {
      this.serverPublicKey = PublicKeyFactory.createKey(localSubjectPublicKeyInfo);
      if ((this.tlsSigner != null) || (!this.tlsSigner.isValidPublicKey(this.serverPublicKey))) {
        throw new TlsFatalAlert((short)46);
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      try
      {
        this.dhAgreeServerPublicKey = validateDHPublicKey((DHPublicKeyParameters)this.serverPublicKey);
        TlsUtils.validateKeyUsage(localX509CertificateStructure, 8);
        return;
      }
      catch (ClassCastException localClassCastException)
      {
        throw new TlsFatalAlert((short)46);
      }
      localRuntimeException = localRuntimeException;
      throw new TlsFatalAlert((short)43);
    }
    TlsUtils.validateKeyUsage(localX509CertificateStructure, 128);
  }
  
  public void processServerKeyExchange(InputStream paramInputStream)
    throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
  
  public void skipClientCredentials()
    throws IOException
  {
    this.agreementCredentials = null;
  }
  
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
  
  protected DHPublicKeyParameters validateDHPublicKey(DHPublicKeyParameters paramDHPublicKeyParameters)
    throws IOException
  {
    return TlsDHUtils.validateDHPublicKey(paramDHPublicKeyParameters);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsDHKeyExchange
 * JD-Core Version:    0.7.0.1
 */