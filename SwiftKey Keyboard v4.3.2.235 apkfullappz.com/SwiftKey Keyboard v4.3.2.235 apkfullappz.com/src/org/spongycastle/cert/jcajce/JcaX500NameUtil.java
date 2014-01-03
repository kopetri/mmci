package org.spongycastle.cert.jcajce;

import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.X500NameStyle;

public class JcaX500NameUtil
{
  public static X500Name getIssuer(X509Certificate paramX509Certificate)
  {
    return X500Name.getInstance(paramX509Certificate.getIssuerX500Principal().getEncoded());
  }
  
  public static X500Name getIssuer(X500NameStyle paramX500NameStyle, X509Certificate paramX509Certificate)
  {
    return X500Name.getInstance(paramX500NameStyle, paramX509Certificate.getIssuerX500Principal().getEncoded());
  }
  
  public static X500Name getSubject(X509Certificate paramX509Certificate)
  {
    return X500Name.getInstance(paramX509Certificate.getSubjectX500Principal().getEncoded());
  }
  
  public static X500Name getSubject(X500NameStyle paramX500NameStyle, X509Certificate paramX509Certificate)
  {
    return X500Name.getInstance(paramX500NameStyle, paramX509Certificate.getSubjectX500Principal().getEncoded());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaX500NameUtil
 * JD-Core Version:    0.7.0.1
 */