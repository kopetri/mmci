package org.spongycastle.cert.crmf.jcajce;

import java.security.PrivateKey;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.cert.crmf.PKIArchiveControlBuilder;

public class JcaPKIArchiveControlBuilder
  extends PKIArchiveControlBuilder
{
  public JcaPKIArchiveControlBuilder(PrivateKey paramPrivateKey, X500Principal paramX500Principal)
  {
    this(paramPrivateKey, X500Name.getInstance(paramX500Principal.getEncoded()));
  }
  
  public JcaPKIArchiveControlBuilder(PrivateKey paramPrivateKey, X500Name paramX500Name)
  {
    this(paramPrivateKey, new GeneralName(paramX500Name));
  }
  
  public JcaPKIArchiveControlBuilder(PrivateKey paramPrivateKey, GeneralName paramGeneralName)
  {
    super(PrivateKeyInfo.getInstance(paramPrivateKey.getEncoded()), paramGeneralName);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.jcajce.JcaPKIArchiveControlBuilder
 * JD-Core Version:    0.7.0.1
 */