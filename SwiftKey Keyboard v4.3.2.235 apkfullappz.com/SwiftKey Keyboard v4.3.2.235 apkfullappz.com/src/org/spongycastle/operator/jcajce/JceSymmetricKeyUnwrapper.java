package org.spongycastle.operator.jcajce;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OperatorException;
import org.spongycastle.operator.SymmetricKeyUnwrapper;

public class JceSymmetricKeyUnwrapper
  extends SymmetricKeyUnwrapper
{
  private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
  private SecretKey secretKey;
  
  public JceSymmetricKeyUnwrapper(AlgorithmIdentifier paramAlgorithmIdentifier, SecretKey paramSecretKey)
  {
    super(paramAlgorithmIdentifier);
    this.secretKey = paramSecretKey;
  }
  
  public GenericKey generateUnwrappedKey(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
    throws OperatorException
  {
    try
    {
      Cipher localCipher = this.helper.createSymmetricWrapper(getAlgorithmIdentifier().getAlgorithm());
      localCipher.init(4, this.secretKey);
      GenericKey localGenericKey = new GenericKey(localCipher.unwrap(paramArrayOfByte, this.helper.getKeyAlgorithmName(paramAlgorithmIdentifier.getAlgorithm()), 3));
      return localGenericKey;
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw new OperatorException("key invalid in message.", localInvalidKeyException);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new OperatorException("can't find algorithm.", localNoSuchAlgorithmException);
    }
  }
  
  public JceSymmetricKeyUnwrapper setProvider(String paramString)
  {
    this.helper = new OperatorHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JceSymmetricKeyUnwrapper setProvider(Provider paramProvider)
  {
    this.helper = new OperatorHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.jcajce.JceSymmetricKeyUnwrapper
 * JD-Core Version:    0.7.0.1
 */