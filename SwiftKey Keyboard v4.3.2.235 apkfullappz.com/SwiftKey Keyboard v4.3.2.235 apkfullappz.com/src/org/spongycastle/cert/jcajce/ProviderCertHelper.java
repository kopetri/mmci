package org.spongycastle.cert.jcajce;

import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

class ProviderCertHelper
  extends CertHelper
{
  private final Provider provider;
  
  ProviderCertHelper(Provider paramProvider)
  {
    this.provider = paramProvider;
  }
  
  protected CertificateFactory createCertificateFactory(String paramString)
    throws CertificateException
  {
    return CertificateFactory.getInstance(paramString, this.provider);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.ProviderCertHelper
 * JD-Core Version:    0.7.0.1
 */