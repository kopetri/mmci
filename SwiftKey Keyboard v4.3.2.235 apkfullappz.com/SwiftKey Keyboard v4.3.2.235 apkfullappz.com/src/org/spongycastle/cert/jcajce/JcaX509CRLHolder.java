package org.spongycastle.cert.jcajce;

import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.cert.X509CRLHolder;

public class JcaX509CRLHolder
  extends X509CRLHolder
{
  public JcaX509CRLHolder(X509CRL paramX509CRL)
    throws CRLException
  {
    super(CertificateList.getInstance(paramX509CRL.getEncoded()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaX509CRLHolder
 * JD-Core Version:    0.7.0.1
 */