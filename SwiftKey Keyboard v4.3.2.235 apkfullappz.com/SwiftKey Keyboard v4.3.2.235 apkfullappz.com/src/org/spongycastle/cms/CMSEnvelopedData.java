package org.spongycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchProviderException;
import java.security.Provider;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.EncryptedContentInfo;
import org.spongycastle.asn1.cms.EnvelopedData;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceAlgorithmIdentifierConverter;

public class CMSEnvelopedData
{
  ContentInfo contentInfo;
  private AlgorithmIdentifier encAlg;
  private OriginatorInformation originatorInfo;
  RecipientInformationStore recipientInfoStore;
  private ASN1Set unprotectedAttributes;
  
  public CMSEnvelopedData(InputStream paramInputStream)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramInputStream));
  }
  
  public CMSEnvelopedData(ContentInfo paramContentInfo)
    throws CMSException
  {
    this.contentInfo = paramContentInfo;
    try
    {
      EnvelopedData localEnvelopedData = EnvelopedData.getInstance(paramContentInfo.getContent());
      if (localEnvelopedData.getOriginatorInfo() != null) {
        this.originatorInfo = new OriginatorInformation(localEnvelopedData.getOriginatorInfo());
      }
      ASN1Set localASN1Set = localEnvelopedData.getRecipientInfos();
      EncryptedContentInfo localEncryptedContentInfo = localEnvelopedData.getEncryptedContentInfo();
      this.encAlg = localEncryptedContentInfo.getContentEncryptionAlgorithm();
      CMSProcessableByteArray localCMSProcessableByteArray = new CMSProcessableByteArray(localEncryptedContentInfo.getEncryptedContent().getOctets());
      CMSEnvelopedHelper.CMSEnvelopedSecureReadable localCMSEnvelopedSecureReadable = new CMSEnvelopedHelper.CMSEnvelopedSecureReadable(this.encAlg, localCMSProcessableByteArray);
      this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(localASN1Set, this.encAlg, localCMSEnvelopedSecureReadable);
      this.unprotectedAttributes = localEnvelopedData.getUnprotectedAttrs();
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CMSException("Malformed content.", localClassCastException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("Malformed content.", localIllegalArgumentException);
    }
  }
  
  public CMSEnvelopedData(byte[] paramArrayOfByte)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramArrayOfByte));
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
  
  public ContentInfo getContentInfo()
  {
    return this.contentInfo;
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.contentInfo.getEncoded();
  }
  
  public String getEncryptionAlgOID()
  {
    return this.encAlg.getAlgorithm().getId();
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
  {
    if (this.unprotectedAttributes == null) {
      return null;
    }
    return new AttributeTable(this.unprotectedAttributes);
  }
  
  public ContentInfo toASN1Structure()
  {
    return this.contentInfo;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSEnvelopedData
 * JD-Core Version:    0.7.0.1
 */