package org.spongycastle.cert.jcajce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import org.spongycastle.cert.X509CRLHolder;

public class JcaX509CRLConverter
{
  private CertHelper helper = new DefaultCertHelper();
  
  public X509CRL getCRL(X509CRLHolder paramX509CRLHolder)
    throws CRLException
  {
    try
    {
      X509CRL localX509CRL = (X509CRL)this.helper.getCertificateFactory("X.509").generateCRL(new ByteArrayInputStream(paramX509CRLHolder.getEncoded()));
      return localX509CRL;
    }
    catch (IOException localIOException)
    {
      throw new ExCRLException("exception parsing certificate: " + localIOException.getMessage(), localIOException);
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new ExCRLException("cannot find required provider:" + localNoSuchProviderException.getMessage(), localNoSuchProviderException);
    }
    catch (CertificateException localCertificateException)
    {
      throw new ExCRLException("cannot create factory: " + localCertificateException.getMessage(), localCertificateException);
    }
  }
  
  public JcaX509CRLConverter setProvider(String paramString)
  {
    this.helper = new NamedCertHelper(paramString);
    return this;
  }
  
  public JcaX509CRLConverter setProvider(Provider paramProvider)
  {
    this.helper = new ProviderCertHelper(paramProvider);
    return this;
  }
  
  private class ExCRLException
    extends CRLException
  {
    private Throwable cause;
    
    public ExCRLException(String paramString, Throwable paramThrowable)
    {
      super();
      this.cause = paramThrowable;
    }
    
    public Throwable getCause()
    {
      return this.cause;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaX509CRLConverter
 * JD-Core Version:    0.7.0.1
 */