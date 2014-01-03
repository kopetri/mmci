package org.spongycastle.pkcs.jcajce;

import java.security.PrivateKey;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.pkcs.PKCS8EncryptedPrivateKeyInfoBuilder;

public class JcaPKCS8EncryptedPrivateKeyInfoBuilder
  extends PKCS8EncryptedPrivateKeyInfoBuilder
{
  public JcaPKCS8EncryptedPrivateKeyInfoBuilder(PrivateKey paramPrivateKey)
  {
    super(PrivateKeyInfo.getInstance(paramPrivateKey.getEncoded()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.jcajce.JcaPKCS8EncryptedPrivateKeyInfoBuilder
 * JD-Core Version:    0.7.0.1
 */