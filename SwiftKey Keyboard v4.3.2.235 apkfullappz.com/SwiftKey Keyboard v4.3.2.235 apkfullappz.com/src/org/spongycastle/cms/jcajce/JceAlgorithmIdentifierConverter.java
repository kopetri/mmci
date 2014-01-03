package org.spongycastle.cms.jcajce;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.CMSException;

public class JceAlgorithmIdentifierConverter
{
  private EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  private SecureRandom random;
  
  public AlgorithmParameters getAlgorithmParameters(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws CMSException
  {
    ASN1Encodable localASN1Encodable = paramAlgorithmIdentifier.getParameters();
    if (localASN1Encodable == null) {
      return null;
    }
    try
    {
      AlgorithmParameters localAlgorithmParameters = this.helper.createAlgorithmParameters(paramAlgorithmIdentifier.getAlgorithm());
      localAlgorithmParameters.init(localASN1Encodable.toASN1Primitive().getEncoded(), "ASN.1");
      return localAlgorithmParameters;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new CMSException("can't find parameters for algorithm", localNoSuchAlgorithmException);
    }
    catch (IOException localIOException)
    {
      throw new CMSException("can't parse parameters", localIOException);
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new CMSException("can't find provider for algorithm", localNoSuchProviderException);
    }
  }
  
  public JceAlgorithmIdentifierConverter setProvider(String paramString)
  {
    this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    return this;
  }
  
  public JceAlgorithmIdentifierConverter setProvider(Provider paramProvider)
  {
    this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceAlgorithmIdentifierConverter
 * JD-Core Version:    0.7.0.1
 */