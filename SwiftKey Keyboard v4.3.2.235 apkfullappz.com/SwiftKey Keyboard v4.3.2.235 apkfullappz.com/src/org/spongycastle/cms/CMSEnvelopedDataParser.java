package org.spongycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchProviderException;
import java.security.Provider;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetStringParser;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1SetParser;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.ContentInfoParser;
import org.spongycastle.asn1.cms.EncryptedContentInfoParser;
import org.spongycastle.asn1.cms.EnvelopedDataParser;
import org.spongycastle.asn1.cms.OriginatorInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceAlgorithmIdentifierConverter;

public class CMSEnvelopedDataParser
  extends CMSContentInfoParser
{
  private boolean attrNotRead = true;
  private AlgorithmIdentifier encAlg;
  EnvelopedDataParser envelopedData = new EnvelopedDataParser((ASN1SequenceParser)this._contentInfo.getContent(16));
  private OriginatorInformation originatorInfo;
  RecipientInformationStore recipientInfoStore;
  private AttributeTable unprotectedAttributes;
  
  public CMSEnvelopedDataParser(InputStream paramInputStream)
    throws CMSException, IOException
  {
    super(paramInputStream);
    OriginatorInfo localOriginatorInfo = this.envelopedData.getOriginatorInfo();
    if (localOriginatorInfo != null) {
      this.originatorInfo = new OriginatorInformation(localOriginatorInfo);
    }
    ASN1Set localASN1Set = ASN1Set.getInstance(this.envelopedData.getRecipientInfos().toASN1Primitive());
    EncryptedContentInfoParser localEncryptedContentInfoParser = this.envelopedData.getEncryptedContentInfo();
    this.encAlg = localEncryptedContentInfoParser.getContentEncryptionAlgorithm();
    CMSProcessableInputStream localCMSProcessableInputStream = new CMSProcessableInputStream(((ASN1OctetStringParser)localEncryptedContentInfoParser.getEncryptedContent(4)).getOctetStream());
    CMSEnvelopedHelper.CMSEnvelopedSecureReadable localCMSEnvelopedSecureReadable = new CMSEnvelopedHelper.CMSEnvelopedSecureReadable(this.encAlg, localCMSProcessableInputStream);
    this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(localASN1Set, this.encAlg, localCMSEnvelopedSecureReadable);
  }
  
  public CMSEnvelopedDataParser(byte[] paramArrayOfByte)
    throws CMSException, IOException
  {
    this(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  private byte[] encodeObj(ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    if (paramASN1Encodable != null) {
      return paramASN1Encodable.toASN1Primitive().getEncoded();
    }
    return null;
  }
  
  public AlgorithmIdentifier getContentEncryptionAlgorithm()
  {
    return this.encAlg;
  }
  
  public String getEncryptionAlgOID()
  {
    return this.encAlg.getAlgorithm().toString();
  }
  
  public byte[] getEncryptionAlgParams()
  {
    try
    {
      byte[] arrayOfByte = encodeObj(this.encAlg.getParameters());
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("exception getting encryption parameters " + localException);
    }
  }
  
  public AlgorithmParameters getEncryptionAlgorithmParameters(String paramString)
    throws CMSException, NoSuchProviderException
  {
    return new JceAlgorithmIdentifierConverter().setProvider(paramString).getAlgorithmParameters(this.encAlg);
  }
  
  public AlgorithmParameters getEncryptionAlgorithmParameters(Provider paramProvider)
    throws CMSException
  {
    return new JceAlgorithmIdentifierConverter().setProvider(paramProvider).getAlgorithmParameters(this.encAlg);
  }
  
  public OriginatorInformation getOriginatorInfo()
  {
    return this.originatorInfo;
  }
  
  public RecipientInformationStore getRecipientInfos()
  {
    return this.recipientInfoStore;
  }
  
  public AttributeTable getUnprotectedAttributes()
    throws IOException
  {
    if ((this.unprotectedAttributes == null) && (this.attrNotRead))
    {
      ASN1SetParser localASN1SetParser = this.envelopedData.getUnprotectedAttrs();
      this.attrNotRead = false;
      if (localASN1SetParser != null)
      {
        ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
        for (;;)
        {
          ASN1Encodable localASN1Encodable = localASN1SetParser.readObject();
          if (localASN1Encodable == null) {
            break;
          }
          localASN1EncodableVector.add(((ASN1SequenceParser)localASN1Encodable).toASN1Primitive());
        }
        this.unprotectedAttributes = new AttributeTable(new DERSet(localASN1EncodableVector));
      }
    }
    return this.unprotectedAttributes;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSEnvelopedDataParser
 * JD-Core Version:    0.7.0.1
 */