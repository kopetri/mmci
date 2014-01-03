package org.spongycastle.pkcs.jcajce;

import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.pkcs.PKCS10CertificationRequestBuilder;

public class JcaPKCS10CertificationRequestBuilder
  extends PKCS10CertificationRequestBuilder
{
  public JcaPKCS10CertificationRequestBuilder(X500Principal paramX500Principal, PublicKey paramPublicKey)
  {
    super(X500Name.getInstance(paramX500Principal.getEncoded()), SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()));
  }
  
  public JcaPKCS10CertificationRequestBuilder(X500Name paramX500Name, PublicKey paramPublicKey)
  {
    super(paramX500Name, SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder
 * JD-Core Version:    0.7.0.1
 */