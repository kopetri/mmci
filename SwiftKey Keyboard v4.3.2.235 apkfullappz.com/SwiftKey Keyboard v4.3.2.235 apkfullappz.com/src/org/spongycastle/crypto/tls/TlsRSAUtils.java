package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class TlsRSAUtils
{
  public static byte[] generateEncryptedPreMasterSecret(TlsClientContext paramTlsClientContext, RSAKeyParameters paramRSAKeyParameters, OutputStream paramOutputStream)
    throws IOException
  {
    boolean bool = true;
    byte[] arrayOfByte1 = new byte[48];
    paramTlsClientContext.getSecureRandom().nextBytes(arrayOfByte1);
    TlsUtils.writeVersion(paramTlsClientContext.getClientVersion(), arrayOfByte1, 0);
    PKCS1Encoding localPKCS1Encoding = new PKCS1Encoding(new RSABlindedEngine());
    localPKCS1Encoding.init(bool, new ParametersWithRandom(paramRSAKeyParameters, paramTlsClientContext.getSecureRandom()));
    for (;;)
    {
      try
      {
        if (paramTlsClientContext.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion())
        {
          byte[] arrayOfByte2 = localPKCS1Encoding.processBlock(arrayOfByte1, 0, arrayOfByte1.length);
          if (bool)
          {
            TlsUtils.writeOpaque16(arrayOfByte2, paramOutputStream);
            return arrayOfByte1;
          }
          paramOutputStream.write(arrayOfByte2);
          return arrayOfByte1;
        }
      }
      catch (InvalidCipherTextException localInvalidCipherTextException)
      {
        throw new TlsFatalAlert((short)80);
      }
      bool = false;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsRSAUtils
 * JD-Core Version:    0.7.0.1
 */