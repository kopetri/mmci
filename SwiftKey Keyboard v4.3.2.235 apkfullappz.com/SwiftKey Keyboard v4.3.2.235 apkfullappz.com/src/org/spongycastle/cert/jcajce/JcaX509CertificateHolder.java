package org.spongycastle.cert.jcajce;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.cert.X509CertificateHolder;

public class JcaX509CertificateHolder
  extends X509CertificateHolder
{
  public JcaX509CertificateHolder(X509Certificate paramX509Certificate)
    throws CertificateEncodingException
  {
    super(Certificate.getInstance(paramX509Certificate.getEncoded()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaX509CertificateHolder
 * JD-Core Version:    0.7.0.1
 */