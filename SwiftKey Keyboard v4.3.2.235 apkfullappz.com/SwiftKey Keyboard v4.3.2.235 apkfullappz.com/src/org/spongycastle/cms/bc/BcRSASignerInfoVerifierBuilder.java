package org.spongycastle.cms.bc;

import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cms.CMSSignatureAlgorithmNameGenerator;
import org.spongycastle.cms.SignerInformationVerifier;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.operator.DigestAlgorithmIdentifierFinder;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.SignatureAlgorithmIdentifierFinder;
import org.spongycastle.operator.bc.BcRSAContentVerifierProviderBuilder;

public class BcRSASignerInfoVerifierBuilder
{
  private BcRSAContentVerifierProviderBuilder contentVerifierProviderBuilder;
  private DigestCalculatorProvider digestCalculatorProvider;
  private SignatureAlgorithmIdentifierFinder sigAlgIdFinder;
  private CMSSignatureAlgorithmNameGenerator sigAlgNameGen;
  
  public BcRSASignerInfoVerifierBuilder(CMSSignatureAlgorithmNameGenerator paramCMSSignatureAlgorithmNameGenerator, SignatureAlgorithmIdentifierFinder paramSignatureAlgorithmIdentifierFinder, DigestAlgorithmIdentifierFinder paramDigestAlgorithmIdentifierFinder, DigestCalculatorProvider paramDigestCalculatorProvider)
  {
    this.sigAlgNameGen = paramCMSSignatureAlgorithmNameGenerator;
    this.sigAlgIdFinder = paramSignatureAlgorithmIdentifierFinder;
    this.contentVerifierProviderBuilder = new BcRSAContentVerifierProviderBuilder(paramDigestAlgorithmIdentifierFinder);
    this.digestCalculatorProvider = paramDigestCalculatorProvider;
  }
  
  public SignerInformationVerifier build(X509CertificateHolder paramX509CertificateHolder)
    throws OperatorCreationException
  {
    return new SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIdFinder, this.contentVerifierProviderBuilder.build(paramX509CertificateHolder), this.digestCalculatorProvider);
  }
  
  public SignerInformationVerifier build(AsymmetricKeyParameter paramAsymmetricKeyParameter)
    throws OperatorCreationException
  {
    return new SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIdFinder, this.contentVerifierProviderBuilder.build(paramAsymmetricKeyParameter), this.digestCalculatorProvider);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.bc.BcRSASignerInfoVerifierBuilder
 * JD-Core Version:    0.7.0.1
 */