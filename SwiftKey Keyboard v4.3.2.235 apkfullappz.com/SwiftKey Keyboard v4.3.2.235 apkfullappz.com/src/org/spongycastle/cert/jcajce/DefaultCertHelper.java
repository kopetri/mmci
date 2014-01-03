package org.spongycastle.cert.jcajce;

import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

class DefaultCertHelper
  extends CertHelper
{
  protected CertificateFactory createCertificateFactory(String paramString)
    throws CertificateException
  {
    return CertificateFactory.getInstance(paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.DefaultCertHelper
 * JD-Core Version:    0.7.0.1
 */