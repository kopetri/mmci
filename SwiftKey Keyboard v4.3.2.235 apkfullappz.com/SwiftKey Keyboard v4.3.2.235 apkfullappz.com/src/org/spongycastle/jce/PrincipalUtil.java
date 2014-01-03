package org.spongycastle.jce;

import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x509.TBSCertList;
import org.spongycastle.asn1.x509.TBSCertificateStructure;
import org.spongycastle.asn1.x509.X509Name;

public class PrincipalUtil
{
  public static X509Principal getIssuerX509Principal(X509CRL paramX509CRL)
    throws CRLException
  {
    try
    {
      X509Principal localX509Principal = new X509Principal(X509Name.getInstance(TBSCertList.getInstance(ASN1Primitive.fromByteArray(paramX509CRL.getTBSCertList())).getIssuer()));
      return localX509Principal;
    }
    catch (IOException localIOException)
    {
      throw new CRLException(localIOException.toString());
    }
  }
  
  public static X509Principal getIssuerX509Principal(X509Certificate paramX509Certificate)
    throws CertificateEncodingException
  {
    try
    {
      X509Principal localX509Principal = new X509Principal(X509Name.getInstance(TBSCertificateStructure.getInstance(ASN1Primitive.fromByteArray(paramX509Certificate.getTBSCertificate())).getIssuer()));
      return localX509Principal;
    }
    catch (IOException localIOException)
    {
      throw new CertificateEncodingException(localIOException.toString());
    }
  }
  
  public static X509Principal getSubjectX509Principal(X509Certificate paramX509Certificate)
    throws CertificateEncodingException
  {
    try
    {
      X509Principal localX509Principal = new X509Principal(X509Name.getInstance(TBSCertificateStructure.getInstance(ASN1Primitive.fromByteArray(paramX509Certificate.getTBSCertificate())).getSubject()));
      return localX509Principal;
    }
    catch (IOException localIOException)
    {
      throw new CertificateEncodingException(localIOException.toString());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.PrincipalUtil
 * JD-Core Version:    0.7.0.1
 */