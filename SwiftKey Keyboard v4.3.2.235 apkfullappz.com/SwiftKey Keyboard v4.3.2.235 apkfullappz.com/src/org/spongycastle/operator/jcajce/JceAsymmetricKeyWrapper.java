package org.spongycastle.operator.jcajce;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Provider;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.AsymmetricKeyWrapper;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OperatorException;

public class JceAsymmetricKeyWrapper
  extends AsymmetricKeyWrapper
{
  private Map extraMappings = new HashMap();
  private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
  private PublicKey publicKey;
  private SecureRandom random;
  
  public JceAsymmetricKeyWrapper(PublicKey paramPublicKey)
  {
    super(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()).getAlgorithm());
    this.publicKey = paramPublicKey;
  }
  
  public JceAsymmetricKeyWrapper(X509Certificate paramX509Certificate)
  {
    this(paramX509Certificate.getPublicKey());
  }
  
  public byte[] generateWrappedKey(GenericKey paramGenericKey)
    throws OperatorException
  {
    Cipher localCipher = this.helper.createAsymmetricWrapper(getAlgorithmIdentifier().getAlgorithm(), this.extraMappings);
    try
    {
      localCipher.init(3, this.publicKey, this.random);
      byte[] arrayOfByte2 = localCipher.wrap(OperatorUtils.getJceKey(paramGenericKey));
      localObject = arrayOfByte2;
    }
    catch (ProviderException localProviderException)
    {
      for (;;)
      {
        localObject = null;
      }
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      for (;;)
      {
        localObject = null;
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      for (;;)
      {
        localObject = null;
      }
    }
    catch (GeneralSecurityException localGeneralSecurityException1)
    {
      for (;;)
      {
        Object localObject = null;
      }
    }
    if (localObject == null) {}
    try
    {
      localCipher.init(1, this.publicKey, this.random);
      byte[] arrayOfByte1 = localCipher.doFinal(OperatorUtils.getJceKey(paramGenericKey).getEncoded());
      localObject = arrayOfByte1;
      return localObject;
    }
    catch (GeneralSecurityException localGeneralSecurityException2)
    {
      throw new OperatorException("unable to encrypt contents key", localGeneralSecurityException2);
    }
  }
  
  public JceAsymmetricKeyWrapper setAlgorithmMapping(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
  {
    this.extraMappings.put(paramASN1ObjectIdentifier, paramString);
    return this;
  }
  
  public JceAsymmetricKeyWrapper setProvider(String paramString)
  {
    this.helper = new OperatorHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JceAsymmetricKeyWrapper setProvider(Provider paramProvider)
  {
    this.helper = new OperatorHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
  
  public JceAsymmetricKeyWrapper setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.jcajce.JceAsymmetricKeyWrapper
 * JD-Core Version:    0.7.0.1
 */