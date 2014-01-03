package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.io.SignerInputStream;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;

class TlsDHEKeyExchange
  extends TlsDHKeyExchange
{
  TlsDHEKeyExchange(TlsClientContext paramTlsClientContext, int paramInt)
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
  
  public void processServerKeyExchange(InputStream paramInputStream)
    throws IOException
  {
    SecurityParameters localSecurityParameters = this.context.getSecurityParameters();
    Signer localSigner = initSigner(this.tlsSigner, localSecurityParameters);
    SignerInputStream localSignerInputStream = new SignerInputStream(paramInputStream, localSigner);
    byte[] arrayOfByte1 = TlsUtils.readOpaque16(localSignerInputStream);
    byte[] arrayOfByte2 = TlsUtils.readOpaque16(localSignerInputStream);
    byte[] arrayOfByte3 = TlsUtils.readOpaque16(localSignerInputStream);
    if (!localSigner.verifySignature(TlsUtils.readOpaque16(paramInputStream))) {
      throw new TlsFatalAlert((short)42);
    }
    BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte1);
    BigInteger localBigInteger2 = new BigInteger(1, arrayOfByte2);
    this.dhAgreeServerPublicKey = validateDHPublicKey(new DHPublicKeyParameters(new BigInteger(1, arrayOfByte3), new DHParameters(localBigInteger1, localBigInteger2)));
  }
  
  public void skipServerKeyExchange()
    throws IOException
  {
    throw new TlsFatalAlert((short)10);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsDHEKeyExchange
 * JD-Core Version:    0.7.0.1
 */