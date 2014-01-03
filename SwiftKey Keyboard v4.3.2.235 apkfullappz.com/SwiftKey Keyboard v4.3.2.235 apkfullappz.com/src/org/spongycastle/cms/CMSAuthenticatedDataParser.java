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
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1OctetStringParser;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1SetParser;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.AuthenticatedDataParser;
import org.spongycastle.asn1.cms.CMSAttributes;
import org.spongycastle.asn1.cms.ContentInfoParser;
import org.spongycastle.asn1.cms.OriginatorInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceAlgorithmIdentifierConverter;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.util.Arrays;

public class CMSAuthenticatedDataParser
  extends CMSContentInfoParser
{
  private boolean authAttrNotRead = true;
  private ASN1Set authAttrSet;
  private AttributeTable authAttrs;
  AuthenticatedDataParser authData = new AuthenticatedDataParser((ASN1SequenceParser)this._contentInfo.getContent(16));
  private byte[] mac;
  private AlgorithmIdentifier macAlg;
  private OriginatorInformation originatorInfo;
  RecipientInformationStore recipientInfoStore;
  private boolean unauthAttrNotRead;
  private AttributeTable unauthAttrs;
  
  public CMSAuthenticatedDataParser(InputStream paramInputStream)
    throws CMSException, IOException
  {
    this(paramInputStream, null);
  }
  
  public CMSAuthenticatedDataParser(InputStream paramInputStream, DigestCalculatorProvider paramDigestCalculatorProvider)
    throws CMSException, IOException
  {
    super(paramInputStream);
    OriginatorInfo localOriginatorInfo = this.authData.getOriginatorInfo();
    if (localOriginatorInfo != null) {
      this.originatorInfo = new OriginatorInformation(localOriginatorInfo);
    }
    ASN1Set localASN1Set = ASN1Set.getInstance(this.authData.getRecipientInfos().toASN1Primitive());
    this.macAlg = this.authData.getMacAlgorithm();
    AlgorithmIdentifier localAlgorithmIdentifier = this.authData.getDigestAlgorithm();
    if (localAlgorithmIdentifier != null)
    {
      if (paramDigestCalculatorProvider == null) {
        throw new CMSException("a digest calculator provider is required if authenticated attributes are present");
      }
      CMSProcessableInputStream localCMSProcessableInputStream2 = new CMSProcessableInputStream(((ASN1OctetStringParser)this.authData.getEnapsulatedContentInfo().getContent(4)).getOctetStream());
      try
      {
        CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable localCMSDigestAuthenticatedSecureReadable = new CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable(paramDigestCalculatorProvider.get(localAlgorithmIdentifier), localCMSProcessableInputStream2);
        this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(localASN1Set, this.macAlg, localCMSDigestAuthenticatedSecureReadable, new AuthAttributesProvider()
        {
          public ASN1Set getAuthAttributes()
          {
            try
            {
              ASN1Set localASN1Set = CMSAuthenticatedDataParser.this.getAuthAttrSet();
              return localASN1Set;
            }
            catch (IOException localIOException)
            {
              throw new IllegalStateException("can't parse authenticated attributes!");
            }
          }
        });
        return;
      }
      catch (OperatorCreationException localOperatorCreationException)
      {
        throw new CMSException("unable to create digest calculator: " + localOperatorCreationException.getMessage(), localOperatorCreationException);
      }
    }
    CMSProcessableInputStream localCMSProcessableInputStream1 = new CMSProcessableInputStream(((ASN1OctetStringParser)this.authData.getEnapsulatedContentInfo().getContent(4)).getOctetStream());
    CMSEnvelopedHelper.CMSAuthenticatedSecureReadable localCMSAuthenticatedSecureReadable = new CMSEnvelopedHelper.CMSAuthenticatedSecureReadable(this.macAlg, localCMSProcessableInputStream1);
    this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(localASN1Set, this.macAlg, localCMSAuthenticatedSecureReadable);
  }
  
  public CMSAuthenticatedDataParser(byte[] paramArrayOfByte)
    throws CMSException, IOException
  {
    this(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  public CMSAuthenticatedDataParser(byte[] paramArrayOfByte, DigestCalculatorProvider paramDigestCalculatorProvider)
    throws CMSException, IOException
  {
    this(new ByteArrayInputStream(paramArrayOfByte), paramDigestCalculatorProvider);
  }
  
  private byte[] encodeObj(ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    if (paramASN1Encodable != null) {
      return paramASN1Encodable.toASN1Primitive().getEncoded();
    }
    return null;
  }
  
  private ASN1Set getAuthAttrSet()
    throws IOException
  {
    if ((this.authAttrs == null) && (this.authAttrNotRead))
    {
      ASN1SetParser localASN1SetParser = this.authData.getAuthAttrs();
      if (localASN1SetParser != null) {
        this.authAttrSet = ((ASN1Set)localASN1SetParser.toASN1Primitive());
      }
      this.authAttrNotRead = false;
    }
    return this.authAttrSet;
  }
  
  public AttributeTable getAuthAttrs()
    throws IOException
  {
    if ((this.authAttrs == null) && (this.authAttrNotRead))
    {
      ASN1Set localASN1Set = getAuthAttrSet();
      if (localASN1Set != null) {
        this.authAttrs = new AttributeTable(localASN1Set);
      }
    }
    return this.authAttrs;
  }
  
  public byte[] getContentDigest()
  {
    if (this.authAttrs != null) {
      return ASN1OctetString.getInstance(this.authAttrs.get(CMSAttributes.messageDigest).getAttrValues().getObjectAt(0)).getOctets();
    }
    return null;
  }
  
  public byte[] getMac()
    throws IOException
  {
    if (this.mac == null)
    {
      getAuthAttrs();
      this.mac = this.authData.getMac().getOctets();
    }
    return Arrays.clone(this.mac);
  }
  
  public String getMacAlgOID()
  {
    return this.macAlg.getAlgorithm().toString();
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
    throws IOException
  {
    if ((this.unauthAttrs == null) && (this.unauthAttrNotRead))
    {
      ASN1SetParser localASN1SetParser = this.authData.getUnauthAttrs();
      this.unauthAttrNotRead = false;
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
        this.unauthAttrs = new AttributeTable(new DERSet(localASN1EncodableVector));
      }
    }
    return this.unauthAttrs;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSAuthenticatedDataParser
 * JD-Core Version:    0.7.0.1
 */