package org.spongycastle.cms.jcajce;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.cms.SignerId;

public class JcaSignerId
  extends SignerId
{
  public JcaSignerId(X509Certificate paramX509Certificate)
  {
    super(convertPrincipal(paramX509Certificate.getIssuerX500Principal()), paramX509Certificate.getSerialNumber(), CMSUtils.getSubjectKeyId(paramX509Certificate));
  }
  
  public JcaSignerId(X500Principal paramX500Principal, BigInteger paramBigInteger)
  {
    super(convertPrincipal(paramX500Principal), paramBigInteger);
  }
  
  public JcaSignerId(X500Principal paramX500Principal, BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    super(convertPrincipal(paramX500Principal), paramBigInteger, paramArrayOfByte);
  }
  
  private static X500Name convertPrincipal(X500Principal paramX500Principal)
  {
    if (paramX500Principal == null) {
      return null;
    }
    return X500Name.getInstance(paramX500Principal.getEncoded());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JcaSignerId
 * JD-Core Version:    0.7.0.1
 */