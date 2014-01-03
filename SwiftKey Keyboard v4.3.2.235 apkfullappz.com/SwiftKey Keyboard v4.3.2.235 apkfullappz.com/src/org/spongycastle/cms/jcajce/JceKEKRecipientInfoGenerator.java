package org.spongycastle.cms.jcajce;

import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.cms.KEKIdentifier;
import org.spongycastle.cms.KEKRecipientInfoGenerator;
import org.spongycastle.operator.jcajce.JceSymmetricKeyWrapper;

public class JceKEKRecipientInfoGenerator
  extends KEKRecipientInfoGenerator
{
  public JceKEKRecipientInfoGenerator(KEKIdentifier paramKEKIdentifier, SecretKey paramSecretKey)
  {
    super(paramKEKIdentifier, new JceSymmetricKeyWrapper(paramSecretKey));
  }
  
  public JceKEKRecipientInfoGenerator(byte[] paramArrayOfByte, SecretKey paramSecretKey)
  {
    this(new KEKIdentifier(paramArrayOfByte, null, null), paramSecretKey);
  }
  
  public JceKEKRecipientInfoGenerator setProvider(String paramString)
  {
    ((JceSymmetricKeyWrapper)this.wrapper).setProvider(paramString);
    return this;
  }
  
  public JceKEKRecipientInfoGenerator setProvider(Provider paramProvider)
  {
    ((JceSymmetricKeyWrapper)this.wrapper).setProvider(paramProvider);
    return this;
  }
  
  public JceKEKRecipientInfoGenerator setSecureRandom(SecureRandom paramSecureRandom)
  {
    ((JceSymmetricKeyWrapper)this.wrapper).setSecureRandom(paramSecureRandom);
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceKEKRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */