package org.spongycastle.crypto.tls;

import java.io.IOException;

public class LegacyTlsClient
  extends DefaultTlsClient
{
  protected CertificateVerifyer verifyer;
  
  public LegacyTlsClient(CertificateVerifyer paramCertificateVerifyer)
  {
    this.verifyer = paramCertificateVerifyer;
  }
  
  public TlsAuthentication getAuthentication()
    throws IOException
  {
    return new LegacyTlsAuthentication(this.verifyer);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.LegacyTlsClient
 * JD-Core Version:    0.7.0.1
 */