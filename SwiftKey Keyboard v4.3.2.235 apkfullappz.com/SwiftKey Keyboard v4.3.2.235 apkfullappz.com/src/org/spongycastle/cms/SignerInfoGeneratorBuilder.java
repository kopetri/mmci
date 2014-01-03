package org.spongycastle.cms;

import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.cms.SignerIdentifier;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;

public class SignerInfoGeneratorBuilder
{
  private DigestCalculatorProvider digestProvider;
  private boolean directSignature;
  private CMSSignatureEncryptionAlgorithmFinder sigEncAlgFinder;
  private CMSAttributeTableGenerator signedGen;
  private CMSAttributeTableGenerator unsignedGen;
  
  public SignerInfoGeneratorBuilder(DigestCalculatorProvider paramDigestCalculatorProvider)
  {
    this(paramDigestCalculatorProvider, new DefaultCMSSignatureEncryptionAlgorithmFinder());
  }
  
  public SignerInfoGeneratorBuilder(DigestCalculatorProvider paramDigestCalculatorProvider, CMSSignatureEncryptionAlgorithmFinder paramCMSSignatureEncryptionAlgorithmFinder)
  {
    this.digestProvider = paramDigestCalculatorProvider;
    this.sigEncAlgFinder = paramCMSSignatureEncryptionAlgorithmFinder;
  }
  
  private SignerInfoGenerator createGenerator(ContentSigner paramContentSigner, SignerIdentifier paramSignerIdentifier)
    throws OperatorCreationException
  {
    if (this.directSignature) {
      return new SignerInfoGenerator(paramSignerIdentifier, paramContentSigner, this.digestProvider, this.sigEncAlgFinder, true);
    }
    if ((this.signedGen != null) || (this.unsignedGen != null))
    {
      if (this.signedGen == null) {
        this.signedGen = new DefaultSignedAttributeTableGenerator();
      }
      return new SignerInfoGenerator(paramSignerIdentifier, paramContentSigner, this.digestProvider, this.sigEncAlgFinder, this.signedGen, this.unsignedGen);
    }
    return new SignerInfoGenerator(paramSignerIdentifier, paramContentSigner, this.digestProvider, this.sigEncAlgFinder);
  }
  
  public SignerInfoGenerator build(ContentSigner paramContentSigner, X509CertificateHolder paramX509CertificateHolder)
    throws OperatorCreationException
  {
    SignerInfoGenerator localSignerInfoGenerator = createGenerator(paramContentSigner, new SignerIdentifier(new IssuerAndSerialNumber(paramX509CertificateHolder.toASN1Structure())));
    localSignerInfoGenerator.setAssociatedCertificate(paramX509CertificateHolder);
    return localSignerInfoGenerator;
  }
  
  public SignerInfoGenerator build(ContentSigner paramContentSigner, byte[] paramArrayOfByte)
    throws OperatorCreationException
  {
    return createGenerator(paramContentSigner, new SignerIdentifier(new DEROctetString(paramArrayOfByte)));
  }
  
  public SignerInfoGeneratorBuilder setDirectSignature(boolean paramBoolean)
  {
    this.directSignature = paramBoolean;
    return this;
  }
  
  public SignerInfoGeneratorBuilder setSignedAttributeGenerator(CMSAttributeTableGenerator paramCMSAttributeTableGenerator)
  {
    this.signedGen = paramCMSAttributeTableGenerator;
    return this;
  }
  
  public SignerInfoGeneratorBuilder setUnsignedAttributeGenerator(CMSAttributeTableGenerator paramCMSAttributeTableGenerator)
  {
    this.unsignedGen = paramCMSAttributeTableGenerator;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.SignerInfoGeneratorBuilder
 * JD-Core Version:    0.7.0.1
 */