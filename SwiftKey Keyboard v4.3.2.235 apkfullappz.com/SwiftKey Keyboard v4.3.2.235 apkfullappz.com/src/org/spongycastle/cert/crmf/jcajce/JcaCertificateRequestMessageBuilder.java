package org.spongycastle.cert.crmf.jcajce;

import java.math.BigInteger;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cert.crmf.CertificateRequestMessageBuilder;

public class JcaCertificateRequestMessageBuilder
  extends CertificateRequestMessageBuilder
{
  public JcaCertificateRequestMessageBuilder(BigInteger paramBigInteger)
  {
    super(paramBigInteger);
  }
  
  public JcaCertificateRequestMessageBuilder setAuthInfoSender(X500Principal paramX500Principal)
  {
    if (paramX500Principal != null) {
      setAuthInfoSender(new GeneralName(X500Name.getInstance(paramX500Principal.getEncoded())));
    }
    return this;
  }
  
  public JcaCertificateRequestMessageBuilder setIssuer(X500Principal paramX500Principal)
  {
    if (paramX500Principal != null) {
      setIssuer(X500Name.getInstance(paramX500Principal.getEncoded()));
    }
    return this;
  }
  
  public JcaCertificateRequestMessageBuilder setPublicKey(PublicKey paramPublicKey)
  {
    setPublicKey(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()));
    return this;
  }
  
  public JcaCertificateRequestMessageBuilder setSubject(X500Principal paramX500Principal)
  {
    if (paramX500Principal != null) {
      setSubject(X500Name.getInstance(paramX500Principal.getEncoded()));
    }
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.jcajce.JcaCertificateRequestMessageBuilder
 * JD-Core Version:    0.7.0.1
 */