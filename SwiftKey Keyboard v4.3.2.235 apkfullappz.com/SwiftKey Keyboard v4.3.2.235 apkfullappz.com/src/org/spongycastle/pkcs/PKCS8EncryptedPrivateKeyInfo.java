package org.spongycastle.pkcs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.operator.InputDecryptor;
import org.spongycastle.operator.InputDecryptorProvider;
import org.spongycastle.util.io.Streams;

public class PKCS8EncryptedPrivateKeyInfo
{
  private EncryptedPrivateKeyInfo encryptedPrivateKeyInfo;
  
  public PKCS8EncryptedPrivateKeyInfo(EncryptedPrivateKeyInfo paramEncryptedPrivateKeyInfo)
  {
    this.encryptedPrivateKeyInfo = paramEncryptedPrivateKeyInfo;
  }
  
  public PrivateKeyInfo decryptPrivateKeyInfo(InputDecryptorProvider paramInputDecryptorProvider)
    throws PKCSException
  {
    InputDecryptor localInputDecryptor = paramInputDecryptorProvider.get(this.encryptedPrivateKeyInfo.getEncryptionAlgorithm());
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(this.encryptedPrivateKeyInfo.getEncryptedData());
    try
    {
      PrivateKeyInfo localPrivateKeyInfo = PrivateKeyInfo.getInstance(Streams.readAll(localInputDecryptor.getInputStream(localByteArrayInputStream)));
      return localPrivateKeyInfo;
    }
    catch (Exception localException)
    {
      throw new PKCSException("unable to read encrypted data: " + localException.getMessage(), localException);
    }
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.encryptedPrivateKeyInfo.getEncoded();
  }
  
  public EncryptedPrivateKeyInfo toASN1Structure()
  {
    return this.encryptedPrivateKeyInfo;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS8EncryptedPrivateKeyInfo
 * JD-Core Version:    0.7.0.1
 */