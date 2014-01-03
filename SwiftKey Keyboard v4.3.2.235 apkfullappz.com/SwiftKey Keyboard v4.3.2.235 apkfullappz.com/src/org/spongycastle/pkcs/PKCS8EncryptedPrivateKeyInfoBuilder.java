package org.spongycastle.pkcs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.operator.OutputEncryptor;

public class PKCS8EncryptedPrivateKeyInfoBuilder
{
  private PrivateKeyInfo privateKeyInfo;
  
  public PKCS8EncryptedPrivateKeyInfoBuilder(PrivateKeyInfo paramPrivateKeyInfo)
  {
    this.privateKeyInfo = paramPrivateKeyInfo;
  }
  
  public PKCS8EncryptedPrivateKeyInfo build(OutputEncryptor paramOutputEncryptor)
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      OutputStream localOutputStream = paramOutputEncryptor.getOutputStream(localByteArrayOutputStream);
      localOutputStream.write(this.privateKeyInfo.getEncoded());
      localOutputStream.close();
      PKCS8EncryptedPrivateKeyInfo localPKCS8EncryptedPrivateKeyInfo = new PKCS8EncryptedPrivateKeyInfo(new EncryptedPrivateKeyInfo(paramOutputEncryptor.getAlgorithmIdentifier(), localByteArrayOutputStream.toByteArray()));
      return localPKCS8EncryptedPrivateKeyInfo;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("cannot encode privateKeyInfo");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS8EncryptedPrivateKeyInfoBuilder
 * JD-Core Version:    0.7.0.1
 */