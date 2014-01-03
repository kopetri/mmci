package org.spongycastle.crypto.tls;

import org.spongycastle.asn1.x509.X509CertificateStructure;

public abstract interface CertificateVerifyer
{
  public abstract boolean isValid(X509CertificateStructure[] paramArrayOfX509CertificateStructure);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.CertificateVerifyer
 * JD-Core Version:    0.7.0.1
 */