package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract interface TlsKeyExchange
{
  public abstract void generateClientKeyExchange(OutputStream paramOutputStream)
    throws IOException;
  
  public abstract byte[] generatePremasterSecret()
    throws IOException;
  
  public abstract void processClientCredentials(TlsCredentials paramTlsCredentials)
    throws IOException;
  
  public abstract void processServerCertificate(Certificate paramCertificate)
    throws IOException;
  
  public abstract void processServerKeyExchange(InputStream paramInputStream)
    throws IOException;
  
  public abstract void skipClientCredentials()
    throws IOException;
  
  public abstract void skipServerCertificate()
    throws IOException;
  
  public abstract void skipServerKeyExchange()
    throws IOException;
  
  public abstract void validateCertificateRequest(CertificateRequest paramCertificateRequest)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsKeyExchange
 * JD-Core Version:    0.7.0.1
 */