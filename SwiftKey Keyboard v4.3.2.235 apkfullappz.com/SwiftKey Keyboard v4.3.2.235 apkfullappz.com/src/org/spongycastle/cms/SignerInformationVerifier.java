package org.spongycastle.cms;

import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.SignatureAlgorithmIdentifierFinder;

public class SignerInformationVerifier
{
  private DigestCalculatorProvider digestProvider;
  private SignatureAlgorithmIdentifierFinder sigAlgorithmFinder;
  private CMSSignatureAlgorithmNameGenerator sigNameGenerator;
  private ContentVerifierProvider verifierProvider;
  
  public SignerInformationVerifier(CMSSignatureAlgorithmNameGenerator paramCMSSignatureAlgorithmNameGenerator, SignatureAlgorithmIdentifierFinder paramSignatureAlgorithmIdentifierFinder, ContentVerifierProvider paramContentVerifierProvider, DigestCalculatorProvider paramDigestCalculatorProvider)
  {
    this.sigNameGenerator = paramCMSSignatureAlgorithmNameGenerator;
    this.sigAlgorithmFinder = paramSignatureAlgorithmIdentifierFinder;
    this.verifierProvider = paramContentVerifierProvider;
    this.digestProvider = paramDigestCalculatorProvider;
  }
  
  public X509CertificateHolder getAssociatedCertificate()
  {
    return this.verifierProvider.getAssociatedCertificate();
  }
  
  public ContentVerifier getContentVerifier(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2)
    throws OperatorCreationException
  {
    String str = this.sigNameGenerator.getSignatureName(paramAlgorithmIdentifier2, paramAlgorithmIdentifier1);
    return this.verifierProvider.get(this.sigAlgorithmFinder.find(str));
  }
  
  public DigestCalculator getDigestCalculator(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws OperatorCreationException
  {
    return this.digestProvider.get(paramAlgorithmIdentifier);
  }
  
  public boolean hasAssociatedCertificate()
  {
    return this.verifierProvider.hasAssociatedCertificate();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.SignerInformationVerifier
 * JD-Core Version:    0.7.0.1
 */