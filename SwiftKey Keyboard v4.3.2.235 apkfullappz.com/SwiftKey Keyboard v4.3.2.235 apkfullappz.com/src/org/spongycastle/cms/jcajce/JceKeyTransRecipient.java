package org.spongycastle.cms.jcajce;

import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.KeyTransRecipient;
import org.spongycastle.operator.OperatorException;
import org.spongycastle.operator.jcajce.JceAsymmetricKeyUnwrapper;

public abstract class JceKeyTransRecipient
  implements KeyTransRecipient
{
  protected EnvelopedDataHelper contentHelper = this.helper;
  protected Map extraMappings = new HashMap();
  protected EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  private PrivateKey recipientKey;
  
  public JceKeyTransRecipient(PrivateKey paramPrivateKey)
  {
    this.recipientKey = paramPrivateKey;
  }
  
  protected Key extractSecretKey(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, byte[] paramArrayOfByte)
    throws CMSException
  {
    JceAsymmetricKeyUnwrapper localJceAsymmetricKeyUnwrapper = this.helper.createAsymmetricUnwrapper(paramAlgorithmIdentifier1, this.recipientKey);
    if (!this.extraMappings.isEmpty())
    {
      Iterator localIterator = this.extraMappings.keySet().iterator();
      while (localIterator.hasNext())
      {
        ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localIterator.next();
        localJceAsymmetricKeyUnwrapper.setAlgorithmMapping(localASN1ObjectIdentifier, (String)this.extraMappings.get(localASN1ObjectIdentifier));
      }
    }
    try
    {
      Key localKey = this.helper.getJceKey(paramAlgorithmIdentifier2.getAlgorithm(), localJceAsymmetricKeyUnwrapper.generateUnwrappedKey(paramAlgorithmIdentifier2, paramArrayOfByte));
      return localKey;
    }
    catch (OperatorException localOperatorException)
    {
      throw new CMSException("exception unwrapping key: " + localOperatorException.getMessage(), localOperatorException);
    }
  }
  
  public JceKeyTransRecipient setAlgorithmMapping(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
  {
    this.extraMappings.put(paramASN1ObjectIdentifier, paramString);
    return this;
  }
  
  public JceKeyTransRecipient setContentProvider(String paramString)
  {
    this.contentHelper = CMSUtils.createContentHelper(paramString);
    return this;
  }
  
  public JceKeyTransRecipient setContentProvider(Provider paramProvider)
  {
    this.contentHelper = CMSUtils.createContentHelper(paramProvider);
    return this;
  }
  
  public JceKeyTransRecipient setProvider(String paramString)
  {
    this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    this.contentHelper = this.helper;
    return this;
  }
  
  public JceKeyTransRecipient setProvider(Provider paramProvider)
  {
    this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    this.contentHelper = this.helper;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceKeyTransRecipient
 * JD-Core Version:    0.7.0.1
 */