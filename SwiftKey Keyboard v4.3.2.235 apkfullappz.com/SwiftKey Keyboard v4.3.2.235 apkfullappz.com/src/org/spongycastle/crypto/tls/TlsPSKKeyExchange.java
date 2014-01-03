package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;

class TlsPSKKeyExchange
  implements TlsKeyExchange
{
  protected TlsClientContext context;
  protected DHPrivateKeyParameters dhAgreeClientPrivateKey = null;
  protected DHPublicKeyParameters dhAgreeServerPublicKey = null;
  protected int keyExchange;
  protected byte[] premasterSecret;
  protected TlsPSKIdentity pskIdentity;
  protected byte[] psk_identity_hint = null;
  protected RSAKeyParameters rsaServerPublicKey = null;
  
  TlsPSKKeyExchange(TlsClientContext paramTlsClientContext, int paramInt, TlsPSKIdentity paramTlsPSKIdentity)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("unsupported key exchange algorithm");
    }
    this.context = paramTlsClientContext;
    this.keyExchange = paramInt;
    this.pskIdentity = paramTlsPSKIdentity;
  }
  
  public void generateClientKeyExchange(OutputStream paramOutputStream)
    throws IOException
  {
    if ((this.psk_identity_hint == null) || (this.psk_identity_hint.length == 0))
    {
      this.pskIdentity.skipIdentityHint();
      TlsUtils.writeOpaque16(this.pskIdentity.getPSKIdentity(), paramOutputStream);
      if (this.keyExchange != 15) {
        break label79;
      }
      this.premasterSecret = TlsRSAUtils.generateEncryptedPreMasterSecret(this.context, this.rsaServerPublicKey, paramOutputStream);
    }
    label79:
    while (this.keyExchange != 14)
    {
      return;
      this.pskIdentity.notifyIdentityHint(this.psk_identity_hint);
      break;
    }
    this.dhAgreeClientPrivateKey = TlsDHUtils.generateEphemeralClientKeyExchange(this.context.getSecureRandom(), this.dhAgreeServerPublicKey.getParameters(), paramOutputStream);
  }
  
  protected byte[] generateOtherSecret(int paramInt)
  {
    if (this.keyExchange == 14) {
      return TlsDHUtils.calculateDHBasicAgreement(this.dhAgreeServerPublicKey, this.dhAgreeClientPrivateKey);
    }
    if (this.keyExchange == 15) {
      return this.premasterSecret;
    }
    return new byte[paramInt];
  }
  
  public byte[] generatePremasterSecret()
    throws IOException
  {
    byte[] arrayOfByte1 = this.pskIdentity.getPSK();
    byte[] arrayOfByte2 = generateOtherSecret(arrayOfByte1.length);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(4 + arrayOfByte2.length + arrayOfByte1.length);
    TlsUtils.writeOpaque16(arrayOfByte2, localByteArrayOutputStream);
    TlsUtils.writeOpaque16(arrayOfByte1, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }
  
  public void processClientCredentials(TlsCredentials paramTlsCredentials)
    throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
  
  public void processServerCertificate(Certificate paramCertificate)
    throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
  
  public void processServerKeyExchange(InputStream paramInputStream)
    throws IOException
  {
    this.psk_identity_hint = TlsUtils.readOpaque16(paramInputStream);
    if (this.keyExchange == 14)
    {
      byte[] arrayOfByte1 = TlsUtils.readOpaque16(paramInputStream);
      byte[] arrayOfByte2 = TlsUtils.readOpaque16(paramInputStream);
      byte[] arrayOfByte3 = TlsUtils.readOpaque16(paramInputStream);
      BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte1);
      BigInteger localBigInteger2 = new BigInteger(1, arrayOfByte2);
      this.dhAgreeServerPublicKey = TlsDHUtils.validateDHPublicKey(new DHPublicKeyParameters(new BigInteger(1, arrayOfByte3), new DHParameters(localBigInteger1, localBigInteger2)));
      return;
    }
  }
  
  public void skipClientCredentials()
    throws IOException
  {}
  
  public void skipServerCertificate()
    throws IOException
  {}
  
  public void skipServerKeyExchange()
    throws IOException
  {
    this.psk_identity_hint = new byte[0];
  }
  
  public void validateCertificateRequest(CertificateRequest paramCertificateRequest)
    throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsPSKKeyExchange
 * JD-Core Version:    0.7.0.1
 */