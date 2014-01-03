package org.spongycastle.cms.jcajce;

import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cms.CMSSignatureAlgorithmNameGenerator;
import org.spongycastle.cms.DefaultCMSSignatureAlgorithmNameGenerator;
import org.spongycastle.cms.SignerInformationVerifier;
import org.spongycastle.operator.ContentVerifierProvider;
import org.spongycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.SignatureAlgorithmIdentifierFinder;
import org.spongycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.spongycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class JcaSignerInfoVerifierBuilder
{
  private DigestCalculatorProvider digestProvider;
  private Helper helper = new Helper(null);
  private SignatureAlgorithmIdentifierFinder sigAlgIDFinder = new DefaultSignatureAlgorithmIdentifierFinder();
  private CMSSignatureAlgorithmNameGenerator sigAlgNameGen = new DefaultCMSSignatureAlgorithmNameGenerator();
  
  public JcaSignerInfoVerifierBuilder(DigestCalculatorProvider paramDigestCalculatorProvider)
  {
    this.digestProvider = paramDigestCalculatorProvider;
  }
  
  public SignerInformationVerifier build(PublicKey paramPublicKey)
    throws OperatorCreationException
  {
    return new SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIDFinder, this.helper.createContentVerifierProvider(paramPublicKey), this.digestProvider);
  }
  
  public SignerInformationVerifier build(X509Certificate paramX509Certificate)
    throws OperatorCreationException
  {
    return new SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIDFinder, this.helper.createContentVerifierProvider(paramX509Certificate), this.digestProvider);
  }
  
  public SignerInformationVerifier build(X509CertificateHolder paramX509CertificateHolder)
    throws OperatorCreationException, CertificateException
  {
    return new SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIDFinder, this.helper.createContentVerifierProvider(paramX509CertificateHolder), this.digestProvider);
  }
  
  public JcaSignerInfoVerifierBuilder setProvider(String paramString)
  {
    this.helper = new NamedHelper(paramString);
    return this;
  }
  
  public JcaSignerInfoVerifierBuilder setProvider(Provider paramProvider)
  {
    this.helper = new ProviderHelper(paramProvider);
    return this;
  }
  
  public JcaSignerInfoVerifierBuilder setSignatureAlgorithmFinder(SignatureAlgorithmIdentifierFinder paramSignatureAlgorithmIdentifierFinder)
  {
    this.sigAlgIDFinder = paramSignatureAlgorithmIdentifierFinder;
    return this;
  }
  
  public JcaSignerInfoVerifierBuilder setSignatureAlgorithmNameGenerator(CMSSignatureAlgorithmNameGenerator paramCMSSignatureAlgorithmNameGenerator)
  {
    this.sigAlgNameGen = paramCMSSignatureAlgorithmNameGenerator;
    return this;
  }
  
  private class Helper
  {
    private Helper() {}
    
    ContentVerifierProvider createContentVerifierProvider(PublicKey paramPublicKey)
      throws OperatorCreationException
    {
      return new JcaContentVerifierProviderBuilder().build(paramPublicKey);
    }
    
    ContentVerifierProvider createContentVerifierProvider(X509Certificate paramX509Certificate)
      throws OperatorCreationException
    {
      return new JcaContentVerifierProviderBuilder().build(paramX509Certificate);
    }
    
    ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder paramX509CertificateHolder)
      throws OperatorCreationException, CertificateException
    {
      return new JcaContentVerifierProviderBuilder().build(paramX509CertificateHolder);
    }
    
    DigestCalculatorProvider createDigestCalculatorProvider()
      throws OperatorCreationException
    {
      return new JcaDigestCalculatorProviderBuilder().build();
    }
  }
  
  private class NamedHelper
    extends JcaSignerInfoVerifierBuilder.Helper
  {
    private final String providerName;
    
    public NamedHelper(String paramString)
    {
      super(null);
      this.providerName = paramString;
    }
    
    ContentVerifierProvider createContentVerifierProvider(PublicKey paramPublicKey)
      throws OperatorCreationException
    {
      return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(paramPublicKey);
    }
    
    ContentVerifierProvider createContentVerifierProvider(X509Certificate paramX509Certificate)
      throws OperatorCreationException
    {
      return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(paramX509Certificate);
    }
    
    ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder paramX509CertificateHolder)
      throws OperatorCreationException, CertificateException
    {
      return new JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(paramX509CertificateHolder);
    }
    
    DigestCalculatorProvider createDigestCalculatorProvider()
      throws OperatorCreationException
    {
      return new JcaDigestCalculatorProviderBuilder().setProvider(this.providerName).build();
    }
  }
  
  private class ProviderHelper
    extends JcaSignerInfoVerifierBuilder.Helper
  {
    private final Provider provider;
    
    public ProviderHelper(Provider paramProvider)
    {
      super(null);
      this.provider = paramProvider;
    }
    
    ContentVerifierProvider createContentVerifierProvider(PublicKey paramPublicKey)
      throws OperatorCreationException
    {
      return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(paramPublicKey);
    }
    
    ContentVerifierProvider createContentVerifierProvider(X509Certificate paramX509Certificate)
      throws OperatorCreationException
    {
      return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(paramX509Certificate);
    }
    
    ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder paramX509CertificateHolder)
      throws OperatorCreationException, CertificateException
    {
      return new JcaContentVerifierProviderBuilder().setProvider(this.provider).build(paramX509CertificateHolder);
    }
    
    DigestCalculatorProvider createDigestCalculatorProvider()
      throws OperatorCreationException
    {
      return new JcaDigestCalculatorProviderBuilder().setProvider(this.provider).build();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JcaSignerInfoVerifierBuilder
 * JD-Core Version:    0.7.0.1
 */