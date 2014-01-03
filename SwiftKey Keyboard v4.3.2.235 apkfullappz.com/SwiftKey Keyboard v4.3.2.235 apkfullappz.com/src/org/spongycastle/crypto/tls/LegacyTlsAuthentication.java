package org.spongycastle.crypto.tls;

import java.io.IOException;

public class LegacyTlsAuthentication
  implements TlsAuthentication
{
  protected CertificateVerifyer verifyer;
  
  public LegacyTlsAuthentication(CertificateVerifyer paramCertificateVerifyer)
  {
    this.verifyer = paramCertificateVerifyer;
  }
  
  public TlsCredentials getClientCredentials(CertificateRequest paramCertificateRequest)
    throws IOException
  {
    return null;
  }
  
  public void notifyServerCertificate(Certificate paramCertificate)
    throws IOException
  {
    if (!this.verifyer.isValid(paramCertificate.getCerts())) {
      throw new TlsFatalAlert((short)90);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.LegacyTlsAuthentication
 * JD-Core Version:    0.7.0.1
 */