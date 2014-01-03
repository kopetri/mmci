package org.spongycastle.cert.ocsp.jcajce;

import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cert.ocsp.OCSPException;
import org.spongycastle.cert.ocsp.RespID;
import org.spongycastle.operator.DigestCalculator;

public class JcaRespID
  extends RespID
{
  public JcaRespID(PublicKey paramPublicKey, DigestCalculator paramDigestCalculator)
    throws OCSPException
  {
    super(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()), paramDigestCalculator);
  }
  
  public JcaRespID(X500Principal paramX500Principal)
  {
    super(X500Name.getInstance(paramX500Principal.getEncoded()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.jcajce.JcaRespID
 * JD-Core Version:    0.7.0.1
 */