package org.spongycastle.cert.crmf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.EnvelopedData;
import org.spongycastle.asn1.crmf.CRMFObjectIdentifiers;
import org.spongycastle.asn1.crmf.EncryptedKey;
import org.spongycastle.asn1.crmf.PKIArchiveOptions;
import org.spongycastle.cms.CMSEnvelopedData;
import org.spongycastle.cms.CMSException;

public class PKIArchiveControl
  implements Control
{
  public static final int archiveRemGenPrivKey = 2;
  public static final int encryptedPrivKey = 0;
  public static final int keyGenParameters = 1;
  private static final ASN1ObjectIdentifier type = CRMFObjectIdentifiers.id_regCtrl_pkiArchiveOptions;
  private final PKIArchiveOptions pkiArchiveOptions;
  
  public PKIArchiveControl(PKIArchiveOptions paramPKIArchiveOptions)
  {
    this.pkiArchiveOptions = paramPKIArchiveOptions;
  }
  
  public int getArchiveType()
  {
    return this.pkiArchiveOptions.getType();
  }
  
  public CMSEnvelopedData getEnvelopedData()
    throws CRMFException
  {
    try
    {
      EnvelopedData localEnvelopedData = EnvelopedData.getInstance(EncryptedKey.getInstance(this.pkiArchiveOptions.getValue()).getValue());
      CMSEnvelopedData localCMSEnvelopedData = new CMSEnvelopedData(new ContentInfo(CMSObjectIdentifiers.envelopedData, localEnvelopedData));
      return localCMSEnvelopedData;
    }
    catch (CMSException localCMSException)
    {
      throw new CRMFException("CMS parsing error: " + localCMSException.getMessage(), localCMSException.getCause());
    }
    catch (Exception localException)
    {
      throw new CRMFException("CRMF parsing error: " + localException.getMessage(), localException);
    }
  }
  
  public ASN1ObjectIdentifier getType()
  {
    return type;
  }
  
  public ASN1Encodable getValue()
  {
    return this.pkiArchiveOptions;
  }
  
  public boolean isEnvelopedData()
  {
    return !EncryptedKey.getInstance(this.pkiArchiveOptions.getValue()).isEncryptedValue();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.PKIArchiveControl
 * JD-Core Version:    0.7.0.1
 */