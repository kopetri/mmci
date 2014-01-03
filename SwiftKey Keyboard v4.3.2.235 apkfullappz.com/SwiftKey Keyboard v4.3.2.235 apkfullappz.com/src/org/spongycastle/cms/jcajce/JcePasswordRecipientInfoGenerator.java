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
import org.spongycastle.cms.PasswordRecipientInfoGenerator;
import org.spongycastle.operator.GenericKey;

public class JcePasswordRecipientInfoGenerator
  extends PasswordRecipientInfoGenerator
{
  private EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  
  public JcePasswordRecipientInfoGenerator(ASN1ObjectIdentifier paramASN1ObjectIdentifier, char[] paramArrayOfChar)
  {
    super(paramASN1ObjectIdentifier, paramArrayOfChar);
  }
  
  public byte[] generateEncryptedBytes(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte, GenericKey paramGenericKey)
    throws CMSException
  {
    Key localKey = this.helper.getJceKey(paramGenericKey);
    Cipher localCipher = this.helper.createRFC3211Wrapper(paramAlgorithmIdentifier.getAlgorithm());
    try
    {
      IvParameterSpec localIvParameterSpec = new IvParameterSpec(ASN1OctetString.getInstance(paramAlgorithmIdentifier.getParameters()).getOctets());
      localCipher.init(3, new SecretKeySpec(paramArrayOfByte, localCipher.getAlgorithm()), localIvParameterSpec);
      byte[] arrayOfByte = localCipher.wrap(localKey);
      return arrayOfByte;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new CMSException("cannot process content encryption key: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  public JcePasswordRecipientInfoGenerator setProvider(String paramString)
  {
    this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    return this;
  }
  
  public JcePasswordRecipientInfoGenerator setProvider(Provider paramProvider)
  {
    this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JcePasswordRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */