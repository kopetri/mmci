package org.spongycastle.pkcs.jcajce;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.operator.OutputEncryptor;
import org.spongycastle.pkcs.PKCS12SafeBagBuilder;
import org.spongycastle.pkcs.PKCSIOException;

public class JcaPKCS12SafeBagBuilder
  extends PKCS12SafeBagBuilder
{
  public JcaPKCS12SafeBagBuilder(PrivateKey paramPrivateKey)
  {
    super(PrivateKeyInfo.getInstance(paramPrivateKey.getEncoded()));
  }
  
  public JcaPKCS12SafeBagBuilder(PrivateKey paramPrivateKey, OutputEncryptor paramOutputEncryptor)
  {
    super(PrivateKeyInfo.getInstance(paramPrivateKey.getEncoded()), paramOutputEncryptor);
  }
  
  public JcaPKCS12SafeBagBuilder(X509Certificate paramX509Certificate)
    throws IOException
  {
    super(convertCert(paramX509Certificate));
  }
  
  private static Certificate convertCert(X509Certificate paramX509Certificate)
    throws IOException
  {
    try
    {
      Certificate localCertificate = Certificate.getInstance(paramX509Certificate.getEncoded());
      return localCertificate;
    }
    catch (CertificateEncodingException localCertificateEncodingException)
    {
      throw new PKCSIOException("cannot encode certificate: " + localCertificateEncodingException.getMessage(), localCertificateEncodingException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.jcajce.JcaPKCS12SafeBagBuilder
 * JD-Core Version:    0.7.0.1
 */