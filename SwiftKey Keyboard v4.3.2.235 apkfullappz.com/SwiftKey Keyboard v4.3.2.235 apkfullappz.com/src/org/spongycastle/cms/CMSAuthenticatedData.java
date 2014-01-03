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
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.AuthenticatedData;
import org.spongycastle.asn1.cms.CMSAttributes;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceAlgorithmIdentifierConverter;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.util.Arrays;

public class CMSAuthenticatedData
{
  private ASN1Set authAttrs;
  ContentInfo contentInfo;
  private byte[] mac;
  private AlgorithmIdentifier macAlg;
  private OriginatorInformation originatorInfo;
  RecipientInformationStore recipientInfoStore;
  private ASN1Set unauthAttrs;
  
  public CMSAuthenticatedData(InputStream paramInputStream)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramInputStream));
  }
  
  public CMSAuthenticatedData(InputStream paramInputStream, DigestCalculatorProvider paramDigestCalculatorProvider)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramInputStream), paramDigestCalculatorProvider);
  }
  
  public CMSAuthenticatedData(ContentInfo paramContentInfo)
    throws CMSException
  {
    this(paramContentInfo, null);
  }
  
  public CMSAuthenticatedData(ContentInfo paramContentInfo, DigestCalculatorProvider paramDigestCalculatorProvider)
    throws CMSException
  {
    this.contentInfo = paramContentInfo;
    AuthenticatedData localAuthenticatedData = AuthenticatedData.getInstance(paramContentInfo.getContent());
    if (localAuthenticatedData.getOriginatorInfo() != null) {
      this.originatorInfo = new OriginatorInformation(localAuthenticatedData.getOriginatorInfo());
    }
    ASN1Set localASN1Set = localAuthenticatedData.getRecipientInfos();
    this.macAlg = localAuthenticatedData.getMacAlgorithm();
    this.authAttrs = localAuthenticatedData.getAuthAttrs();
    this.mac = localAuthenticatedData.getMac().getOctets();
    this.unauthAttrs = localAuthenticatedData.getUnauthAttrs();
    CMSProcessableByteArray localCMSProcessableByteArray = new CMSProcessableByteArray(ASN1OctetString.getInstance(localAuthenticatedData.getEncapsulatedContentInfo().getContent()).getOctets());
    if (this.authAttrs != null)
    {
      if (paramDigestCalculatorProvider == null) {
        throw new CMSException("a digest calculator provider is required if authenticated attributes are present");
      }
      try
      {
        CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable localCMSDigestAuthenticatedSecureReadable = new CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable(paramDigestCalculatorProvider.get(localAuthenticatedData.getDigestAlgorithm()), localCMSProcessableByteArray);
        this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(localASN1Set, this.macAlg, localCMSDigestAuthenticatedSecureReadable, new AuthAttributesProvider()
        {
          public ASN1Set getAuthAttributes()
          {
            return CMSAuthenticatedData.this.authAttrs;
          }
        });
        return;
      }
      catch (OperatorCreationException localOperatorCreationException)
      {
        throw new CMSException("unable to create digest calculator: " + localOperatorCreationException.getMessage(), localOperatorCreationException);
      }
    }
    CMSEnvelopedHelper.CMSAuthenticatedSecureReadable localCMSAuthenticatedSecureReadable = new CMSEnvelopedHelper.CMSAuthenticatedSecureReadable(this.macAlg, localCMSProcessableByteArray);
    this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(localASN1Set, this.macAlg, localCMSAuthenticatedSecureReadable);
  }
  
  public CMSAuthenticatedData(byte[] paramArrayOfByte)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramArrayOfByte));
  }
  
  public CMSAuthenticatedData(byte[] paramArrayOfByte, DigestCalculatorProvider paramDigestCalculatorProvider)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramArrayOfByte), paramDigestCalculatorProvider);
  }
  
  private byte[] encodeObj(ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    if (paramASN1Encodable != null) {
      return paramASN1Encodable.toASN1Primitive().getEncoded();
    }
    return null;
  }
  
  public AttributeTable getAuthAttrs()
  {
    if (this.authAttrs == null) {
      return null;
    }
    return new AttributeTable(this.authAttrs);
  }
  
  public byte[] getContentDigest()
  {
    if (this.authAttrs != null) {
      return ASN1OctetString.getInstance(getAuthAttrs().get(CMSAttributes.messageDigest).getAttrValues().getObjectAt(0)).getOctets();
    }
    return null;
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
  
  public byte[] getMac()
  {
    return Arrays.clone(this.mac);
  }
  
  public String getMacAlgOID()
  {
    return this.macAlg.getObjectId().getId();
  }
  
  public byte[] getMacAlgParams()
  {
    try
    {
      byte[] arrayOfByte = encodeObj(this.macAlg.getParameters());
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("exception getting encryption parameters " + localException);
    }
  }
  
  public AlgorithmIdentifier getMacAlgorithm()
  {
    return this.macAlg;
  }
  
  public AlgorithmParameters getMacAlgorithmParameters(String paramString)
    throws CMSException, NoSuchProviderException
  {
    return new JceAlgorithmIdentifierConverter().setProvider(paramString).getAlgorithmParameters(this.macAlg);
  }
  
  public AlgorithmParameters getMacAlgorithmParameters(Provider paramProvider)
    throws CMSException
  {
    return new JceAlgorithmIdentifierConverter().setProvider(paramProvider).getAlgorithmParameters(this.macAlg);
  }
  
  public OriginatorInformation getOriginatorInfo()
  {
    return this.originatorInfo;
  }
  
  public RecipientInformationStore getRecipientInfos()
  {
    return this.recipientInfoStore;
  }
  
  public AttributeTable getUnauthAttrs()
  {
    if (this.unauthAttrs == null) {
      return null;
    }
    return new AttributeTable(this.unauthAttrs);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSAuthenticatedData
 * JD-Core Version:    0.7.0.1
 */