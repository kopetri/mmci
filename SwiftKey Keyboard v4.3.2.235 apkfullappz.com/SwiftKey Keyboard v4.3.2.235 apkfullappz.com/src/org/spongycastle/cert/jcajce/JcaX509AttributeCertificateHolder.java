package org.spongycastle.cert.jcajce;

import java.io.IOException;
import org.spongycastle.asn1.x509.AttributeCertificate;
import org.spongycastle.cert.X509AttributeCertificateHolder;
import org.spongycastle.x509.X509AttributeCertificate;

public class JcaX509AttributeCertificateHolder
  extends X509AttributeCertificateHolder
{
  public JcaX509AttributeCertificateHolder(X509AttributeCertificate paramX509AttributeCertificate)
    throws IOException
  {
    super(AttributeCertificate.getInstance(paramX509AttributeCertificate.getEncoded()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaX509AttributeCertificateHolder
 * JD-Core Version:    0.7.0.1
 */