package org.spongycastle.cert.ocsp.jcajce;

import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.spongycastle.cert.jcajce.JcaX509CertificateHolder;
import org.spongycastle.cert.ocsp.CertificateID;
import org.spongycastle.cert.ocsp.OCSPException;
import org.spongycastle.operator.DigestCalculator;

public class JcaCertificateID
  extends CertificateID
{
  public JcaCertificateID(DigestCalculator paramDigestCalculator, X509Certificate paramX509Certificate, BigInteger paramBigInteger)
    throws OCSPException, CertificateEncodingException
  {
    super(paramDigestCalculator, new JcaX509CertificateHolder(paramX509Certificate), paramBigInteger);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.jcajce.JcaCertificateID
 * JD-Core Version:    0.7.0.1
 */