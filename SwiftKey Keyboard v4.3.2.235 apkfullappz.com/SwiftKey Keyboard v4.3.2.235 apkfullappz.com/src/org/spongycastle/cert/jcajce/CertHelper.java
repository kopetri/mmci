package org.spongycastle.cert.jcajce;

import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

abstract class CertHelper
{
  protected abstract CertificateFactory createCertificateFactory(String paramString)
    throws CertificateException, NoSuchProviderException;
  
  public CertificateFactory getCertificateFactory(String paramString)
    throws NoSuchProviderException, CertificateException
  {
    return createCertificateFactory(paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.CertHelper
 * JD-Core Version:    0.7.0.1
 */