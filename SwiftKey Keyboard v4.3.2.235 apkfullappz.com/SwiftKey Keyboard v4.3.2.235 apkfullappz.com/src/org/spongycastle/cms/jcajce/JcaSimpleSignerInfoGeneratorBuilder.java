package org.spongycastle.cms.jcajce;

import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.cert.jcajce.JcaX509CertificateHolder;
import org.spongycastle.cms.CMSAttributeTableGenerator;
import org.spongycastle.cms.DefaultSignedAttributeTableGenerator;
import org.spongycastle.cms.SignerInfoGenerator;
import org.spongycastle.cms.SignerInfoGeneratorBuilder;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder;
import org.spongycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class JcaSimpleSignerInfoGeneratorBuilder
{
  private boolean hasNoSignedAttributes;
  private Helper helper = new Helper(null);
  private CMSAttributeTableGenerator signedGen;
  private CMSAttributeTableGenerator unsignedGen;
  
  public JcaSimpleSignerInfoGeneratorBuilder()
    throws OperatorCreationException
  {}
  
  private SignerInfoGeneratorBuilder configureAndBuild()
    throws OperatorCreationException
  {
    SignerInfoGeneratorBuilder localSignerInfoGeneratorBuilder = new SignerInfoGeneratorBuilder(this.helper.createDigestCalculatorProvider());
    localSignerInfoGeneratorBuilder.setDirectSignature(this.hasNoSignedAttributes);
    localSignerInfoGeneratorBuilder.setSignedAttributeGenerator(this.signedGen);
    localSignerInfoGeneratorBuilder.setUnsignedAttributeGenerator(this.unsignedGen);
    return localSignerInfoGeneratorBuilder;
  }
  
  public SignerInfoGenerator build(String paramString, PrivateKey paramPrivateKey, X509Certificate paramX509Certificate)
    throws OperatorCreationException, CertificateEncodingException
  {
    ContentSigner localContentSigner = this.helper.createContentSigner(paramString, paramPrivateKey);
    return configureAndBuild().build(localContentSigner, new JcaX509CertificateHolder(paramX509Certificate));
  }
  
  public SignerInfoGenerator build(String paramString, PrivateKey paramPrivateKey, byte[] paramArrayOfByte)
    throws OperatorCreationException, CertificateEncodingException
  {
    ContentSigner localContentSigner = this.helper.createContentSigner(paramString, paramPrivateKey);
    return configureAndBuild().build(localContentSigner, paramArrayOfByte);
  }
  
  public JcaSimpleSignerInfoGeneratorBuilder setDirectSignature(boolean paramBoolean)
  {
    this.hasNoSignedAttributes = paramBoolean;
    return this;
  }
  
  public JcaSimpleSignerInfoGeneratorBuilder setProvider(String paramString)
    throws OperatorCreationException
  {
    this.helper = new NamedHelper(paramString);
    return this;
  }
  
  public JcaSimpleSignerInfoGeneratorBuilder setProvider(Provider paramProvider)
    throws OperatorCreationException
  {
    this.helper = new ProviderHelper(paramProvider);
    return this;
  }
  
  public JcaSimpleSignerInfoGeneratorBuilder setSignedAttributeGenerator(AttributeTable paramAttributeTable)
  {
    this.signedGen = new DefaultSignedAttributeTableGenerator(paramAttributeTable);
    return this;
  }
  
  public JcaSimpleSignerInfoGeneratorBuilder setSignedAttributeGenerator(CMSAttributeTableGenerator paramCMSAttributeTableGenerator)
  {
    this.signedGen = paramCMSAttributeTableGenerator;
    return this;
  }
  
  public JcaSimpleSignerInfoGeneratorBuilder setUnsignedAttributeGenerator(CMSAttributeTableGenerator paramCMSAttributeTableGenerator)
  {
    this.unsignedGen = paramCMSAttributeTableGenerator;
    return this;
  }
  
  private class Helper
  {
    private Helper() {}
    
    ContentSigner createContentSigner(String paramString, PrivateKey paramPrivateKey)
      throws OperatorCreationException
    {
      return new JcaContentSignerBuilder(paramString).build(paramPrivateKey);
    }
    
    DigestCalculatorProvider createDigestCalculatorProvider()
      throws OperatorCreationException
    {
      return new JcaDigestCalculatorProviderBuilder().build();
    }
  }
  
  private class NamedHelper
    extends JcaSimpleSignerInfoGeneratorBuilder.Helper
  {
    private final String providerName;
    
    public NamedHelper(String paramString)
    {
      super(null);
      this.providerName = paramString;
    }
    
    ContentSigner createContentSigner(String paramString, PrivateKey paramPrivateKey)
      throws OperatorCreationException
    {
      return new JcaContentSignerBuilder(paramString).setProvider(this.providerName).build(paramPrivateKey);
    }
    
    DigestCalculatorProvider createDigestCalculatorProvider()
      throws OperatorCreationException
    {
      return new JcaDigestCalculatorProviderBuilder().setProvider(this.providerName).build();
    }
  }
  
  private class ProviderHelper
    extends JcaSimpleSignerInfoGeneratorBuilder.Helper
  {
    private final Provider provider;
    
    public ProviderHelper(Provider paramProvider)
    {
      super(null);
      this.provider = paramProvider;
    }
    
    ContentSigner createContentSigner(String paramString, PrivateKey paramPrivateKey)
      throws OperatorCreationException
    {
      return new JcaContentSignerBuilder(paramString).setProvider(this.provider).build(paramPrivateKey);
    }
    
    DigestCalculatorProvider createDigestCalculatorProvider()
      throws OperatorCreationException
    {
      return new JcaDigestCalculatorProviderBuilder().setProvider(this.provider).build();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder
 * JD-Core Version:    0.7.0.1
 */