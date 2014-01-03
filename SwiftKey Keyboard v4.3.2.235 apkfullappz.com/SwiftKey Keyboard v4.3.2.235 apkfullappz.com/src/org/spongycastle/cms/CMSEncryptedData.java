package org.spongycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.EncryptedContentInfo;
import org.spongycastle.asn1.cms.EncryptedData;
import org.spongycastle.operator.InputDecryptor;
import org.spongycastle.operator.InputDecryptorProvider;

public class CMSEncryptedData
{
  private ContentInfo contentInfo;
  private EncryptedData encryptedData;
  
  public CMSEncryptedData(ContentInfo paramContentInfo)
  {
    this.contentInfo = paramContentInfo;
    this.encryptedData = EncryptedData.getInstance(paramContentInfo.getContent());
  }
  
  public byte[] getContent(InputDecryptorProvider paramInputDecryptorProvider)
    throws CMSException
  {
    try
    {
      byte[] arrayOfByte = CMSUtils.streamToByteArray(getContentStream(paramInputDecryptorProvider).getContentStream());
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("unable to parse internal stream: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public CMSTypedStream getContentStream(InputDecryptorProvider paramInputDecryptorProvider)
    throws CMSException
  {
    EncryptedContentInfo localEncryptedContentInfo = this.encryptedData.getEncryptedContentInfo();
    InputDecryptor localInputDecryptor = paramInputDecryptorProvider.get(localEncryptedContentInfo.getContentEncryptionAlgorithm());
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localEncryptedContentInfo.getEncryptedContent().getOctets());
    return new CMSTypedStream(localEncryptedContentInfo.getContentType(), localInputDecryptor.getInputStream(localByteArrayInputStream));
  }
  
  public ContentInfo toASN1Structure()
  {
    return this.contentInfo;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSEncryptedData
 * JD-Core Version:    0.7.0.1
 */