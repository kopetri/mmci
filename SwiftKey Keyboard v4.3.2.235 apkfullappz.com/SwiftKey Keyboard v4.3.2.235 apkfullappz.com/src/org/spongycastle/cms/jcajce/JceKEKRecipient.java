package org.spongycastle.cms.jcajce;

import java.security.Key;
import java.security.Provider;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.KEKRecipient;
import org.spongycastle.operator.OperatorException;
import org.spongycastle.operator.SymmetricKeyUnwrapper;

public abstract class JceKEKRecipient
  implements KEKRecipient
{
  protected EnvelopedDataHelper contentHelper = this.helper;
  protected EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  private SecretKey recipientKey;
  
  public JceKEKRecipient(SecretKey paramSecretKey)
  {
    this.recipientKey = paramSecretKey;
  }
  
  protected Key extractSecretKey(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, byte[] paramArrayOfByte)
    throws CMSException
  {
    SymmetricKeyUnwrapper localSymmetricKeyUnwrapper = this.helper.createSymmetricUnwrapper(paramAlgorithmIdentifier1, this.recipientKey);
    try
    {
      Key localKey = this.helper.getJceKey(paramAlgorithmIdentifier2.getAlgorithm(), localSymmetricKeyUnwrapper.generateUnwrappedKey(paramAlgorithmIdentifier2, paramArrayOfByte));
      return localKey;
    }
    catch (OperatorException localOperatorException)
    {
      throw new CMSException("exception unwrapping key: " + localOperatorException.getMessage(), localOperatorException);
    }
  }
  
  public JceKEKRecipient setContentProvider(String paramString)
  {
    this.contentHelper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    return this;
  }
  
  public JceKEKRecipient setContentProvider(Provider paramProvider)
  {
    this.contentHelper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    return this;
  }
  
  public JceKEKRecipient setProvider(String paramString)
  {
    this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    this.contentHelper = this.helper;
    return this;
  }
  
  public JceKEKRecipient setProvider(Provider paramProvider)
  {
    this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    this.contentHelper = this.helper;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceKEKRecipient
 * JD-Core Version:    0.7.0.1
 */