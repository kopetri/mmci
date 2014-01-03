package org.spongycastle.cert.jcajce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import org.spongycastle.cert.X509CertificateHolder;

public class JcaX509CertificateConverter
{
  private CertHelper helper = new DefaultCertHelper();
  
  public X509Certificate getCertificate(X509CertificateHolder paramX509CertificateHolder)
    throws CertificateException
  {
    try
    {
      X509Certificate localX509Certificate = (X509Certificate)this.helper.getCertificateFactory("X.509").generateCertificate(new ByteArrayInputStream(paramX509CertificateHolder.getEncoded()));
      return localX509Certificate;
    }
    catch (IOException localIOException)
    {
      throw new ExCertificateParsingException("exception parsing certificate: " + localIOException.getMessage(), localIOException);
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new ExCertificateException("cannot find required provider:" + localNoSuchProviderException.getMessage(), localNoSuchProviderException);
    }
  }
  
  public JcaX509CertificateConverter setProvider(String paramString)
  {
    this.helper = new NamedCertHelper(paramString);
    return this;
  }
  
  public JcaX509CertificateConverter setProvider(Provider paramProvider)
  {
    this.helper = new ProviderCertHelper(paramProvider);
    return this;
  }
  
  private class ExCertificateException
    extends CertificateException
  {
    private Throwable cause;
    
    public ExCertificateException(String paramString, Throwable paramThrowable)
    {
      super();
      this.cause = paramThrowable;
    }
    
    public Throwable getCause()
    {
      return this.cause;
    }
  }
  
  private class ExCertificateParsingException
    extends CertificateParsingException
  {
    private Throwable cause;
    
    public ExCertificateParsingException(String paramString, Throwable paramThrowable)
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
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaX509CertificateConverter
 * JD-Core Version:    0.7.0.1
 */