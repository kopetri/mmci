package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.agreement.ECDHBasicAgreement;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.BigIntegers;

class TlsECDHKeyExchange
  implements TlsKeyExchange
{
  protected TlsAgreementCredentials agreementCredentials;
  protected TlsClientContext context;
  protected ECPrivateKeyParameters ecAgreeClientPrivateKey = null;
  protected ECPublicKeyParameters ecAgreeServerPublicKey;
  protected int keyExchange;
  protected AsymmetricKeyParameter serverPublicKey;
  protected TlsSigner tlsSigner;
  
  TlsECDHKeyExchange(TlsClientContext paramTlsClientContext, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("unsupported key exchange algorithm");
    case 19: 
      this.tlsSigner = new TlsRSASigner();
    }
    for (;;)
    {
      this.context = paramTlsClientContext;
      this.keyExchange = paramInt;
      return;
      this.tlsSigner = new TlsECDSASigner();
      continue;
      this.tlsSigner = null;
    }
  }
  
  protected boolean areOnSameCurve(ECDomainParameters paramECDomainParameters1, ECDomainParameters paramECDomainParameters2)
  {
    return (paramECDomainParameters1.getCurve().equals(paramECDomainParameters2.getCurve())) && (paramECDomainParameters1.getG().equals(paramECDomainParameters2.getG())) && (paramECDomainParameters1.getN().equals(paramECDomainParameters2.getN())) && (paramECDomainParameters1.getH().equals(paramECDomainParameters2.getH()));
  }
  
  protected byte[] calculateECDHBasicAgreement(ECPublicKeyParameters paramECPublicKeyParameters, ECPrivateKeyParameters paramECPrivateKeyParameters)
  {
    ECDHBasicAgreement localECDHBasicAgreement = new ECDHBasicAgreement();
    localECDHBasicAgreement.init(paramECPrivateKeyParameters);
    return BigIntegers.asUnsignedByteArray(localECDHBasicAgreement.calculateAgreement(paramECPublicKeyParameters));
  }
  
  protected byte[] externalizeKey(ECPublicKeyParameters paramECPublicKeyParameters)
    throws IOException
  {
    return paramECPublicKeyParameters.getQ().getEncoded();
  }
  
  public void generateClientKeyExchange(OutputStream paramOutputStream)
    throws IOException
  {
    if (this.agreementCredentials == null) {
      generateEphemeralClientKeyExchange(this.ecAgreeServerPublicKey.getParameters(), paramOutputStream);
    }
  }
  
  protected AsymmetricCipherKeyPair generateECKeyPair(ECDomainParameters paramECDomainParameters)
  {
    ECKeyPairGenerator localECKeyPairGenerator = new ECKeyPairGenerator();
    localECKeyPairGenerator.init(new ECKeyGenerationParameters(paramECDomainParameters, this.context.getSecureRandom()));
    return localECKeyPairGenerator.generateKeyPair();
  }
  
  protected void generateEphemeralClientKeyExchange(ECDomainParameters paramECDomainParameters, OutputStream paramOutputStream)
    throws IOException
  {
    AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = generateECKeyPair(paramECDomainParameters);
    this.ecAgreeClientPrivateKey = ((ECPrivateKeyParameters)localAsymmetricCipherKeyPair.getPrivate());
    TlsUtils.writeOpaque8(externalizeKey((ECPublicKeyParameters)localAsymmetricCipherKeyPair.getPublic()), paramOutputStream);
  }
  
  public byte[] generatePremasterSecret()
    throws IOException
  {
    if (this.agreementCredentials != null) {
      return this.agreementCredentials.generateAgreement(this.ecAgreeServerPublicKey);
    }
    return calculateECDHBasicAgreement(this.ecAgreeServerPublicKey, this.ecAgreeClientPrivateKey);
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
        this.ecAgreeServerPublicKey = validateECPublicKey((ECPublicKeyParameters)this.serverPublicKey);
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
  
  protected ECPublicKeyParameters validateECPublicKey(ECPublicKeyParameters paramECPublicKeyParameters)
    throws IOException
  {
    return paramECPublicKeyParameters;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsECDHKeyExchange
 * JD-Core Version:    0.7.0.1
 */