package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.io.SignerInputStream;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECCurve;

class TlsECDHEKeyExchange
  extends TlsECDHKeyExchange
{
  TlsECDHEKeyExchange(TlsClientContext paramTlsClientContext, int paramInt)
  {
    super(paramTlsClientContext, paramInt);
  }
  
  protected Signer initSigner(TlsSigner paramTlsSigner, SecurityParameters paramSecurityParameters)
  {
    Signer localSigner = paramTlsSigner.createVerifyer(this.serverPublicKey);
    localSigner.update(paramSecurityParameters.clientRandom, 0, paramSecurityParameters.clientRandom.length);
    localSigner.update(paramSecurityParameters.serverRandom, 0, paramSecurityParameters.serverRandom.length);
    return localSigner;
  }
  
  public void processClientCredentials(TlsCredentials paramTlsCredentials)
    throws IOException
  {
    if (!(paramTlsCredentials instanceof TlsSignerCredentials)) {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public void processServerKeyExchange(InputStream paramInputStream)
    throws IOException
  {
    SecurityParameters localSecurityParameters = this.context.getSecurityParameters();
    Signer localSigner = initSigner(this.tlsSigner, localSecurityParameters);
    SignerInputStream localSignerInputStream = new SignerInputStream(paramInputStream, localSigner);
    ECDomainParameters localECDomainParameters;
    byte[] arrayOfByte;
    if (TlsUtils.readUint8(localSignerInputStream) == 3)
    {
      localECDomainParameters = NamedCurve.getECParameters(TlsUtils.readUint16(localSignerInputStream));
      arrayOfByte = TlsUtils.readOpaque8(localSignerInputStream);
      if (!localSigner.verifySignature(TlsUtils.readOpaque16(paramInputStream))) {
        throw new TlsFatalAlert((short)42);
      }
    }
    else
    {
      throw new TlsFatalAlert((short)40);
    }
    this.ecAgreeServerPublicKey = validateECPublicKey(new ECPublicKeyParameters(localECDomainParameters.getCurve().decodePoint(arrayOfByte), localECDomainParameters));
  }
  
  public void skipServerKeyExchange()
    throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
  
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
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsECDHEKeyExchange
 * JD-Core Version:    0.7.0.1
 */