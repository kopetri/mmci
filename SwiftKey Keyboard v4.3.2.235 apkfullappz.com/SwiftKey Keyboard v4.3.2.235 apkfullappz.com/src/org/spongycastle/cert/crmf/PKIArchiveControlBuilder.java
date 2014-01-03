package org.spongycastle.cert.crmf;

import java.io.IOException;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.EnvelopedData;
import org.spongycastle.asn1.crmf.CRMFObjectIdentifiers;
import org.spongycastle.asn1.crmf.EncKeyWithID;
import org.spongycastle.asn1.crmf.EncryptedKey;
import org.spongycastle.asn1.crmf.PKIArchiveOptions;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.cms.CMSEnvelopedData;
import org.spongycastle.cms.CMSEnvelopedDataGenerator;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.CMSProcessableByteArray;
import org.spongycastle.cms.RecipientInfoGenerator;
import org.spongycastle.operator.OutputEncryptor;

public class PKIArchiveControlBuilder
{
  private CMSEnvelopedDataGenerator envGen;
  private CMSProcessableByteArray keyContent;
  
  public PKIArchiveControlBuilder(PrivateKeyInfo paramPrivateKeyInfo, GeneralName paramGeneralName)
  {
    EncKeyWithID localEncKeyWithID = new EncKeyWithID(paramPrivateKeyInfo, paramGeneralName);
    try
    {
      this.keyContent = new CMSProcessableByteArray(CRMFObjectIdentifiers.id_ct_encKeyWithID, localEncKeyWithID.getEncoded());
      this.envGen = new CMSEnvelopedDataGenerator();
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("unable to encode key and general name info");
    }
  }
  
  public PKIArchiveControlBuilder addRecipientGenerator(RecipientInfoGenerator paramRecipientInfoGenerator)
  {
    this.envGen.addRecipientInfoGenerator(paramRecipientInfoGenerator);
    return this;
  }
  
  public PKIArchiveControl build(OutputEncryptor paramOutputEncryptor)
    throws CMSException
  {
    return new PKIArchiveControl(new PKIArchiveOptions(new EncryptedKey(EnvelopedData.getInstance(this.envGen.generate(this.keyContent, paramOutputEncryptor).getContentInfo().getContent()))));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.PKIArchiveControlBuilder
 * JD-Core Version:    0.7.0.1
 */