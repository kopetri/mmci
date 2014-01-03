package org.spongycastle.crypto.tls;

import java.io.IOException;

public abstract interface TlsAuthentication
{
  public abstract TlsCredentials getClientCredentials(CertificateRequest paramCertificateRequest)
    throws IOException;
  
  public abstract void notifyServerCertificate(Certificate paramCertificate)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsAuthentication
 * JD-Core Version:    0.7.0.1
 */