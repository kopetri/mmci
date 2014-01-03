package org.spongycastle.cert.cmp;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.cmp.RevDetails;
import org.spongycastle.asn1.crmf.CertTemplateBuilder;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class RevocationDetailsBuilder
{
  private CertTemplateBuilder templateBuilder = new CertTemplateBuilder();
  
  public RevocationDetails build()
  {
    return new RevocationDetails(new RevDetails(this.templateBuilder.build()));
  }
  
  public RevocationDetailsBuilder setIssuer(X500Name paramX500Name)
  {
    if (paramX500Name != null) {
      this.templateBuilder.setIssuer(paramX500Name);
    }
    return this;
  }
  
  public RevocationDetailsBuilder setPublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    if (paramSubjectPublicKeyInfo != null) {
      this.templateBuilder.setPublicKey(paramSubjectPublicKeyInfo);
    }
    return this;
  }
  
  public RevocationDetailsBuilder setSerialNumber(BigInteger paramBigInteger)
  {
    if (paramBigInteger != null) {
      this.templateBuilder.setSerialNumber(new ASN1Integer(paramBigInteger));
    }
    return this;
  }
  
  public RevocationDetailsBuilder setSubject(X500Name paramX500Name)
  {
    if (paramX500Name != null) {
      this.templateBuilder.setSubject(paramX500Name);
    }
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.cmp.RevocationDetailsBuilder
 * JD-Core Version:    0.7.0.1
 */