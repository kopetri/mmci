package org.spongycastle.cms.jcajce;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.PasswordRecipient;

public abstract class JcePasswordRecipient
  implements PasswordRecipient
{
  protected EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  private char[] password;
  private int schemeID = 1;
  
  JcePasswordRecipient(char[] paramArrayOfChar)
  {
    this.password = paramArrayOfChar;
  }
  
  protected Key extractSecretKey(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws CMSException
  {
    Cipher localCipher = this.helper.createRFC3211Wrapper(paramAlgorithmIdentifier1.getAlgorithm());
    try
    {
      IvParameterSpec localIvParameterSpec = new IvParameterSpec(ASN1OctetString.getInstance(paramAlgorithmIdentifier1.getParameters()).getOctets());
      localCipher.init(4, new SecretKeySpec(paramArrayOfByte1, localCipher.getAlgorithm()), localIvParameterSpec);
      Key localKey = localCipher.unwrap(paramArrayOfByte2, paramAlgorithmIdentifier2.getAlgorithm().getId(), 3);
      return localKey;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CMSException("cannot process content encryption key: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  public char[] getPassword()
  {
    return this.password;
  }
  
  public int getPasswordConversionScheme()
  {
    return this.schemeID;
  }
  
  public JcePasswordRecipient setPasswordConversionScheme(int paramInt)
  {
    this.schemeID = paramInt;
    return this;
  }
  
  public JcePasswordRecipient setProvider(String paramString)
  {
    this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    return this;
  }
  
  public JcePasswordRecipient setProvider(Provider paramProvider)
  {
    this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JcePasswordRecipient
 * JD-Core Version:    0.7.0.1
 */