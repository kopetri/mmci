package org.spongycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.SignerIdentifier;
import org.spongycastle.asn1.cms.SignerInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.spongycastle.operator.DigestAlgorithmIdentifierFinder;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.util.io.TeeOutputStream;

public class SignerInfoGenerator
{
  private byte[] calculatedDigest = null;
  private X509CertificateHolder certHolder;
  private final DigestAlgorithmIdentifierFinder digAlgFinder = new DefaultDigestAlgorithmIdentifierFinder();
  private final DigestCalculator digester;
  private final CMSAttributeTableGenerator sAttrGen;
  private final CMSSignatureEncryptionAlgorithmFinder sigEncAlgFinder;
  private final ContentSigner signer;
  private final SignerIdentifier signerIdentifier;
  private final CMSAttributeTableGenerator unsAttrGen;
  
  SignerInfoGenerator(SignerIdentifier paramSignerIdentifier, ContentSigner paramContentSigner, DigestCalculatorProvider paramDigestCalculatorProvider, CMSSignatureEncryptionAlgorithmFinder paramCMSSignatureEncryptionAlgorithmFinder)
    throws OperatorCreationException
  {
    this(paramSignerIdentifier, paramContentSigner, paramDigestCalculatorProvider, paramCMSSignatureEncryptionAlgorithmFinder, false);
  }
  
  SignerInfoGenerator(SignerIdentifier paramSignerIdentifier, ContentSigner paramContentSigner, DigestCalculatorProvider paramDigestCalculatorProvider, CMSSignatureEncryptionAlgorithmFinder paramCMSSignatureEncryptionAlgorithmFinder, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2)
    throws OperatorCreationException
  {
    this.signerIdentifier = paramSignerIdentifier;
    this.signer = paramContentSigner;
    if (paramDigestCalculatorProvider != null) {}
    for (this.digester = paramDigestCalculatorProvider.get(this.digAlgFinder.find(paramContentSigner.getAlgorithmIdentifier()));; this.digester = null)
    {
      this.sAttrGen = paramCMSAttributeTableGenerator1;
      this.unsAttrGen = paramCMSAttributeTableGenerator2;
      this.sigEncAlgFinder = paramCMSSignatureEncryptionAlgorithmFinder;
      return;
    }
  }
  
  SignerInfoGenerator(SignerIdentifier paramSignerIdentifier, ContentSigner paramContentSigner, DigestCalculatorProvider paramDigestCalculatorProvider, CMSSignatureEncryptionAlgorithmFinder paramCMSSignatureEncryptionAlgorithmFinder, boolean paramBoolean)
    throws OperatorCreationException
  {
    this.signerIdentifier = paramSignerIdentifier;
    this.signer = paramContentSigner;
    if (paramDigestCalculatorProvider != null)
    {
      this.digester = paramDigestCalculatorProvider.get(this.digAlgFinder.find(paramContentSigner.getAlgorithmIdentifier()));
      if (!paramBoolean) {
        break label89;
      }
      this.sAttrGen = null;
    }
    for (this.unsAttrGen = null;; this.unsAttrGen = null)
    {
      this.sigEncAlgFinder = paramCMSSignatureEncryptionAlgorithmFinder;
      return;
      this.digester = null;
      break;
      label89:
      this.sAttrGen = new DefaultSignedAttributeTableGenerator();
    }
  }
  
  public SignerInfoGenerator(SignerInfoGenerator paramSignerInfoGenerator, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2)
  {
    this.signerIdentifier = paramSignerInfoGenerator.signerIdentifier;
    this.signer = paramSignerInfoGenerator.signer;
    this.digester = paramSignerInfoGenerator.digester;
    this.sigEncAlgFinder = paramSignerInfoGenerator.sigEncAlgFinder;
    this.sAttrGen = paramCMSAttributeTableGenerator1;
    this.unsAttrGen = paramCMSAttributeTableGenerator2;
  }
  
  private ASN1Set getAttributeSet(AttributeTable paramAttributeTable)
  {
    if (paramAttributeTable != null) {
      return new DERSet(paramAttributeTable.toASN1EncodableVector());
    }
    return null;
  }
  
  private Map getBaseParameters(DERObjectIdentifier paramDERObjectIdentifier, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    HashMap localHashMap = new HashMap();
    if (paramDERObjectIdentifier != null) {
      localHashMap.put("contentType", paramDERObjectIdentifier);
    }
    localHashMap.put("digestAlgID", paramAlgorithmIdentifier);
    localHashMap.put("digest", paramArrayOfByte.clone());
    return localHashMap;
  }
  
  public SignerInfo generate(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CMSException
  {
    for (;;)
    {
      try
      {
        if (this.sAttrGen != null)
        {
          localAlgorithmIdentifier1 = this.digester.getAlgorithmIdentifier();
          this.calculatedDigest = this.digester.getDigest();
          Map localMap2 = getBaseParameters(paramASN1ObjectIdentifier, this.digester.getAlgorithmIdentifier(), this.calculatedDigest);
          localASN1Set1 = getAttributeSet(this.sAttrGen.getAttributes(Collections.unmodifiableMap(localMap2)));
          OutputStream localOutputStream = this.signer.getOutputStream();
          localOutputStream.write(localASN1Set1.getEncoded("DER"));
          localOutputStream.close();
          byte[] arrayOfByte = this.signer.getSignature();
          CMSAttributeTableGenerator localCMSAttributeTableGenerator = this.unsAttrGen;
          ASN1Set localASN1Set2 = null;
          if (localCMSAttributeTableGenerator != null)
          {
            Map localMap1 = getBaseParameters(paramASN1ObjectIdentifier, localAlgorithmIdentifier1, this.calculatedDigest);
            localMap1.put("encryptedDigest", arrayOfByte.clone());
            localASN1Set2 = getAttributeSet(this.unsAttrGen.getAttributes(Collections.unmodifiableMap(localMap1)));
          }
          AlgorithmIdentifier localAlgorithmIdentifier2 = this.sigEncAlgFinder.findEncryptionAlgorithm(this.signer.getAlgorithmIdentifier());
          return new SignerInfo(this.signerIdentifier, localAlgorithmIdentifier1, localASN1Set1, localAlgorithmIdentifier2, new DEROctetString(arrayOfByte), localASN1Set2);
        }
        if (this.digester != null)
        {
          localAlgorithmIdentifier1 = this.digester.getAlgorithmIdentifier();
          this.calculatedDigest = this.digester.getDigest();
          localASN1Set1 = null;
          continue;
        }
        AlgorithmIdentifier localAlgorithmIdentifier1 = this.digAlgFinder.find(this.signer.getAlgorithmIdentifier());
      }
      catch (IOException localIOException)
      {
        throw new CMSException("encoding error.", localIOException);
      }
      this.calculatedDigest = null;
      ASN1Set localASN1Set1 = null;
    }
  }
  
  public X509CertificateHolder getAssociatedCertificate()
  {
    return this.certHolder;
  }
  
  public byte[] getCalculatedDigest()
  {
    if (this.calculatedDigest != null) {
      return (byte[])this.calculatedDigest.clone();
    }
    return null;
  }
  
  public OutputStream getCalculatingOutputStream()
  {
    if (this.digester != null)
    {
      if (this.sAttrGen == null) {
        return new TeeOutputStream(this.digester.getOutputStream(), this.signer.getOutputStream());
      }
      return this.digester.getOutputStream();
    }
    return this.signer.getOutputStream();
  }
  
  public AlgorithmIdentifier getDigestAlgorithm()
  {
    if (this.digester != null) {
      return this.digester.getAlgorithmIdentifier();
    }
    return this.digAlgFinder.find(this.signer.getAlgorithmIdentifier());
  }
  
  public CMSAttributeTableGenerator getSignedAttributeTableGenerator()
  {
    return this.sAttrGen;
  }
  
  public CMSAttributeTableGenerator getUnsignedAttributeTableGenerator()
  {
    return this.unsAttrGen;
  }
  
  public boolean hasAssociatedCertificate()
  {
    return this.certHolder != null;
  }
  
  void setAssociatedCertificate(X509CertificateHolder paramX509CertificateHolder)
  {
    this.certHolder = paramX509CertificateHolder;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.SignerInfoGenerator
 * JD-Core Version:    0.7.0.1
 */