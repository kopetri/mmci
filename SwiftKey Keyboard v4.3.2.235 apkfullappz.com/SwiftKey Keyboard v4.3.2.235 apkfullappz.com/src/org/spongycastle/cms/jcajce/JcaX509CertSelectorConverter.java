package org.spongycastle.cms.jcajce;

import java.security.cert.X509CertSelector;
import org.spongycastle.cms.KeyTransRecipientId;
import org.spongycastle.cms.SignerId;

public class JcaX509CertSelectorConverter
  extends org.spongycastle.cert.selector.jcajce.JcaX509CertSelectorConverter
{
  public X509CertSelector getCertSelector(KeyTransRecipientId paramKeyTransRecipientId)
  {
    return doConversion(paramKeyTransRecipientId.getIssuer(), paramKeyTransRecipientId.getSerialNumber(), paramKeyTransRecipientId.getSubjectKeyIdentifier());
  }
  
  public X509CertSelector getCertSelector(SignerId paramSignerId)
  {
    return doConversion(paramSignerId.getIssuer(), paramSignerId.getSerialNumber(), paramSignerId.getSubjectKeyIdentifier());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JcaX509CertSelectorConverter
 * JD-Core Version:    0.7.0.1
 */