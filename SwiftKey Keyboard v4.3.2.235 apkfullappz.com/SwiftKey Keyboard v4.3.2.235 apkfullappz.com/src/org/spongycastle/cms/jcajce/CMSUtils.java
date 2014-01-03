package org.spongycastle.cms.jcajce;

import java.security.Provider;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.TBSCertificateStructure;
import org.spongycastle.asn1.x509.X509Extension;

class CMSUtils
{
  static EnvelopedDataHelper createContentHelper(String paramString)
  {
    if (paramString != null) {
      return new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    }
    return new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  }
  
  static EnvelopedDataHelper createContentHelper(Provider paramProvider)
  {
    if (paramProvider != null) {
      return new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    }
    return new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  }
  
  static IssuerAndSerialNumber getIssuerAndSerialNumber(X509Certificate paramX509Certificate)
    throws CertificateEncodingException
  {
    return new IssuerAndSerialNumber(Certificate.getInstance(paramX509Certificate.getEncoded()).getIssuer(), paramX509Certificate.getSerialNumber());
  }
  
  static byte[] getSubjectKeyId(X509Certificate paramX509Certificate)
  {
    byte[] arrayOfByte = paramX509Certificate.getExtensionValue(X509Extension.subjectKeyIdentifier.getId());
    if (arrayOfByte != null) {
      return ASN1OctetString.getInstance(ASN1OctetString.getInstance(arrayOfByte).getOctets()).getOctets();
    }
    return null;
  }
  
  static TBSCertificateStructure getTBSCertificateStructure(X509Certificate paramX509Certificate)
    throws CertificateEncodingException
  {
    return TBSCertificateStructure.getInstance(paramX509Certificate.getTBSCertificate());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.CMSUtils
 * JD-Core Version:    0.7.0.1
 */