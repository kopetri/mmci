package org.spongycastle.cert.crmf.jcajce;

import java.io.IOException;
import java.security.Provider;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.crmf.CertReqMsg;
import org.spongycastle.asn1.crmf.CertTemplate;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cert.crmf.CRMFException;
import org.spongycastle.cert.crmf.CertificateRequestMessage;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;

public class JcaCertificateRequestMessage
  extends CertificateRequestMessage
{
  private CRMFHelper helper = new CRMFHelper(new DefaultJcaJceHelper());
  
  public JcaCertificateRequestMessage(CertReqMsg paramCertReqMsg)
  {
    super(paramCertReqMsg);
  }
  
  public JcaCertificateRequestMessage(CertificateRequestMessage paramCertificateRequestMessage)
  {
    this(paramCertificateRequestMessage.toASN1Structure());
  }
  
  public PublicKey getPublicKey()
    throws CRMFException
  {
    SubjectPublicKeyInfo localSubjectPublicKeyInfo = getCertTemplate().getPublicKey();
    if (localSubjectPublicKeyInfo != null) {
      return this.helper.toPublicKey(localSubjectPublicKeyInfo);
    }
    return null;
  }
  
  public X500Principal getSubjectX500Principal()
  {
    X500Name localX500Name = getCertTemplate().getSubject();
    if (localX500Name != null) {
      try
      {
        X500Principal localX500Principal = new X500Principal(localX500Name.getEncoded("DER"));
        return localX500Principal;
      }
      catch (IOException localIOException)
      {
        throw new IllegalStateException("unable to construct DER encoding of name: " + localIOException.getMessage());
      }
    }
    return null;
  }
  
  public JcaCertificateRequestMessage setProvider(String paramString)
  {
    this.helper = new CRMFHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JcaCertificateRequestMessage setProvider(Provider paramProvider)
  {
    this.helper = new CRMFHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.jcajce.JcaCertificateRequestMessage
 * JD-Core Version:    0.7.0.1
 */