package org.spongycastle.operator;

import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cert.X509CertificateHolder;

public abstract interface ContentVerifierProvider
{
  public abstract ContentVerifier get(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws OperatorCreationException;
  
  public abstract X509CertificateHolder getAssociatedCertificate();
  
  public abstract boolean hasAssociatedCertificate();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.ContentVerifierProvider
 * JD-Core Version:    0.7.0.1
 */