package org.spongycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.spongycastle.asn1.BEROctetString;
import org.spongycastle.asn1.BERSet;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.EncryptedContentInfo;
import org.spongycastle.asn1.cms.EncryptedData;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.operator.OutputEncryptor;

public class CMSEncryptedDataGenerator
  extends CMSEncryptedGenerator
{
  private CMSEncryptedData doGenerate(CMSTypedData paramCMSTypedData, OutputEncryptor paramOutputEncryptor)
    throws CMSException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      OutputStream localOutputStream = paramOutputEncryptor.getOutputStream(localByteArrayOutputStream);
      paramCMSTypedData.write(localOutputStream);
      localOutputStream.close();
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      AlgorithmIdentifier localAlgorithmIdentifier = paramOutputEncryptor.getAlgorithmIdentifier();
      BEROctetString localBEROctetString = new BEROctetString(arrayOfByte);
      EncryptedContentInfo localEncryptedContentInfo = new EncryptedContentInfo(paramCMSTypedData.getContentType(), localAlgorithmIdentifier, localBEROctetString);
      CMSAttributeTableGenerator localCMSAttributeTableGenerator = this.unprotectedAttributeGenerator;
      BERSet localBERSet = null;
      if (localCMSAttributeTableGenerator != null) {
        localBERSet = new BERSet(this.unprotectedAttributeGenerator.getAttributes(new HashMap()).toASN1EncodableVector());
      }
      return new CMSEncryptedData(new ContentInfo(CMSObjectIdentifiers.encryptedData, new EncryptedData(localEncryptedContentInfo, localBERSet)));
    }
    catch (IOException localIOException)
    {
      throw new CMSException("");
    }
  }
  
  public CMSEncryptedData generate(CMSTypedData paramCMSTypedData, OutputEncryptor paramOutputEncryptor)
    throws CMSException
  {
    return doGenerate(paramCMSTypedData, paramOutputEncryptor);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSEncryptedDataGenerator
 * JD-Core Version:    0.7.0.1
 */