package org.spongycastle.cert.selector.jcajce;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.X509Extension;
import org.spongycastle.cert.selector.X509CertificateHolderSelector;

public class JcaX509CertificateHolderSelector
  extends X509CertificateHolderSelector
{
  public JcaX509CertificateHolderSelector(X509Certificate paramX509Certificate)
  {
    super(convertPrincipal(paramX509Certificate.getIssuerX500Principal()), paramX509Certificate.getSerialNumber(), getSubjectKeyId(paramX509Certificate));
  }
  
  public JcaX509CertificateHolderSelector(X500Principal paramX500Principal, BigInteger paramBigInteger)
  {
    super(convertPrincipal(paramX500Principal), paramBigInteger);
  }
  
  public JcaX509CertificateHolderSelector(X500Principal paramX500Principal, BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    super(convertPrincipal(paramX500Principal), paramBigInteger, paramArrayOfByte);
  }
  
  private static X500Name convertPrincipal(X500Principal paramX500Principal)
  {
    if (paramX500Principal == null) {
      return null;
    }
    return X500Name.getInstance(paramX500Principal.getEncoded());
  }
  
  private static byte[] getSubjectKeyId(X509Certificate paramX509Certificate)
  {
    byte[] arrayOfByte = paramX509Certificate.getExtensionValue(X509Extension.subjectKeyIdentifier.getId());
    if (arrayOfByte != null) {
      return ASN1OctetString.getInstance(ASN1OctetString.getInstance(arrayOfByte).getOctets()).getOctets();
    }
    return null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.selector.jcajce.JcaX509CertificateHolderSelector
 * JD-Core Version:    0.7.0.1
 */