package org.spongycastle.cms.jcajce;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cert.jcajce.JcaX509CertificateHolder;
import org.spongycastle.cms.CMSAttributeTableGenerator;
import org.spongycastle.cms.SignerInfoGenerator;
import org.spongycastle.cms.SignerInfoGeneratorBuilder;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;

public class JcaSignerInfoGeneratorBuilder
{
  private SignerInfoGeneratorBuilder builder;
  
  public JcaSignerInfoGeneratorBuilder(DigestCalculatorProvider paramDigestCalculatorProvider)
  {
    this.builder = new SignerInfoGeneratorBuilder(paramDigestCalculatorProvider);
  }
  
  public SignerInfoGenerator build(ContentSigner paramContentSigner, X509Certificate paramX509Certificate)
    throws OperatorCreationException, CertificateEncodingException
  {
    return build(paramContentSigner, new JcaX509CertificateHolder(paramX509Certificate));
  }
  
  public SignerInfoGenerator build(ContentSigner paramContentSigner, X509CertificateHolder paramX509CertificateHolder)
    throws OperatorCreationException
  {
    return this.builder.build(paramContentSigner, paramX509CertificateHolder);
  }
  
  public SignerInfoGenerator build(ContentSigner paramContentSigner, byte[] paramArrayOfByte)
    throws OperatorCreationException
  {
    return this.builder.build(paramContentSigner, paramArrayOfByte);
  }
  
  public JcaSignerInfoGeneratorBuilder setDirectSignature(boolean paramBoolean)
  {
    this.builder.setDirectSignature(paramBoolean);
    return this;
  }
  
  public JcaSignerInfoGeneratorBuilder setSignedAttributeGenerator(CMSAttributeTableGenerator paramCMSAttributeTableGenerator)
  {
    this.builder.setSignedAttributeGenerator(paramCMSAttributeTableGenerator);
    return this;
  }
  
  public JcaSignerInfoGeneratorBuilder setUnsignedAttributeGenerator(CMSAttributeTableGenerator paramCMSAttributeTableGenerator)
  {
    this.builder.setUnsignedAttributeGenerator(paramCMSAttributeTableGenerator);
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder
 * JD-Core Version:    0.7.0.1
 */