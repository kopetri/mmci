package org.spongycastle.cert.selector.jcajce;

import java.io.IOException;
import java.security.cert.X509CertSelector;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.cert.selector.X509CertificateHolderSelector;

public class JcaSelectorConverter
{
  public X509CertificateHolderSelector getCertificateHolderSelector(X509CertSelector paramX509CertSelector)
  {
    try
    {
      if (paramX509CertSelector.getSubjectKeyIdentifier() != null) {
        return new X509CertificateHolderSelector(X500Name.getInstance(paramX509CertSelector.getIssuerAsBytes()), paramX509CertSelector.getSerialNumber(), ASN1OctetString.getInstance(paramX509CertSelector.getSubjectKeyIdentifier()).getOctets());
      }
      X509CertificateHolderSelector localX509CertificateHolderSelector = new X509CertificateHolderSelector(X500Name.getInstance(paramX509CertSelector.getIssuerAsBytes()), paramX509CertSelector.getSerialNumber());
      return localX509CertificateHolderSelector;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("unable to convert issuer: " + localIOException.getMessage());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.selector.jcajce.JcaSelectorConverter
 * JD-Core Version:    0.7.0.1
 */