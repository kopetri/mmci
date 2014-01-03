package org.spongycastle.cms.jcajce;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.cms.KeyAgreeRecipientId;

public class JceKeyAgreeRecipientId
  extends KeyAgreeRecipientId
{
  public JceKeyAgreeRecipientId(X509Certificate paramX509Certificate)
  {
    this(paramX509Certificate.getIssuerX500Principal(), paramX509Certificate.getSerialNumber());
  }
  
  public JceKeyAgreeRecipientId(X500Principal paramX500Principal, BigInteger paramBigInteger)
  {
    super(X500Name.getInstance(paramX500Principal.getEncoded()), paramBigInteger);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceKeyAgreeRecipientId
 * JD-Core Version:    0.7.0.1
 */