package org.spongycastle.cert.jcajce;

import java.util.Date;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.cert.X509v2CRLBuilder;

public class JcaX509v2CRLBuilder
  extends X509v2CRLBuilder
{
  public JcaX509v2CRLBuilder(X500Principal paramX500Principal, Date paramDate)
  {
    super(X500Name.getInstance(paramX500Principal.getEncoded()), paramDate);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaX509v2CRLBuilder
 * JD-Core Version:    0.7.0.1
 */