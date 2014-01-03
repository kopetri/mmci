package org.spongycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.cms.AuthEnvelopedData;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.EncryptedContentInfo;
import org.spongycastle.asn1.cms.OriginatorInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

class CMSAuthEnvelopedData
{
  private ASN1Set authAttrs;
  private AlgorithmIdentifier authEncAlg;
  ContentInfo contentInfo;
  private byte[] mac;
  private OriginatorInfo originator;
  RecipientInformationStore recipientInfoStore;
  private ASN1Set unauthAttrs;
  
  public CMSAuthEnvelopedData(InputStream paramInputStream)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramInputStream));
  }
  
  public CMSAuthEnvelopedData(ContentInfo paramContentInfo)
    throws CMSException
  {
    this.contentInfo = paramContentInfo;
    AuthEnvelopedData localAuthEnvelopedData = AuthEnvelopedData.getInstance(paramContentInfo.getContent());
    this.originator = localAuthEnvelopedData.getOriginatorInfo();
    ASN1Set localASN1Set = localAuthEnvelopedData.getRecipientInfos();
    this.authEncAlg = localAuthEnvelopedData.getAuthEncryptedContentInfo().getContentEncryptionAlgorithm();
    CMSSecureReadable local1 = new CMSSecureReadable()
    {
      public InputStream getInputStream()
        throws IOException, CMSException
      {
        return null;
      }
    };
    this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(localASN1Set, this.authEncAlg, local1);
    this.authAttrs = localAuthEnvelopedData.getAuthAttrs();
    this.mac = localAuthEnvelopedData.getMac().getOctets();
    this.unauthAttrs = localAuthEnvelopedData.getUnauthAttrs();
  }
  
  public CMSAuthEnvelopedData(byte[] paramArrayOfByte)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramArrayOfByte));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSAuthEnvelopedData
 * JD-Core Version:    0.7.0.1
 */