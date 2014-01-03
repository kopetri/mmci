package org.spongycastle.operator.jcajce;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.AsymmetricKeyUnwrapper;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OperatorException;

public class JceAsymmetricKeyUnwrapper
  extends AsymmetricKeyUnwrapper
{
  private Map extraMappings = new HashMap();
  private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
  private PrivateKey privKey;
  
  public JceAsymmetricKeyUnwrapper(AlgorithmIdentifier paramAlgorithmIdentifier, PrivateKey paramPrivateKey)
  {
    super(paramAlgorithmIdentifier);
    this.privKey = paramPrivateKey;
  }
  
  public GenericKey generateUnwrappedKey(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
    throws OperatorException
  {
    for (;;)
    {
      try
      {
        Cipher localCipher = this.helper.createAsymmetricWrapper(getAlgorithmIdentifier().getAlgorithm(), this.extraMappings);
        Key localKey1;
        try
        {
          localCipher.init(4, this.privKey);
          Key localKey2 = localCipher.unwrap(paramArrayOfByte, this.helper.getKeyAlgorithmName(paramAlgorithmIdentifier.getAlgorithm()), 3);
          localKey1 = localKey2;
        }
        catch (GeneralSecurityException localGeneralSecurityException)
        {
          try
          {
            localCipher.init(2, this.privKey);
            localObject = new SecretKeySpec(localCipher.doFinal(paramArrayOfByte), paramAlgorithmIdentifier.getAlgorithm().getId());
            GenericKey localGenericKey = new GenericKey(localObject);
            return localGenericKey;
          }
          catch (BadPaddingException localBadPaddingException2)
          {
            continue;
          }
          catch (IllegalBlockSizeException localIllegalBlockSizeException2)
          {
            continue;
          }
          catch (InvalidKeyException localInvalidKeyException2)
          {
            continue;
          }
          localGeneralSecurityException = localGeneralSecurityException;
          localKey1 = null;
          continue;
        }
        catch (IllegalStateException localIllegalStateException)
        {
          localKey1 = null;
          continue;
        }
        catch (UnsupportedOperationException localUnsupportedOperationException)
        {
          localKey1 = null;
          continue;
        }
        catch (ProviderException localProviderException)
        {
          localKey1 = null;
          continue;
        }
        if (localKey1 == null) {}
        Object localObject = localKey1;
      }
      catch (InvalidKeyException localInvalidKeyException1)
      {
        throw new OperatorException("key invalid: " + localInvalidKeyException1.getMessage(), localInvalidKeyException1);
      }
      catch (IllegalBlockSizeException localIllegalBlockSizeException1)
      {
        throw new OperatorException("illegal blocksize: " + localIllegalBlockSizeException1.getMessage(), localIllegalBlockSizeException1);
      }
      catch (BadPaddingException localBadPaddingException1)
      {
        throw new OperatorException("bad padding: " + localBadPaddingException1.getMessage(), localBadPaddingException1);
      }
    }
  }
  
  public JceAsymmetricKeyUnwrapper setAlgorithmMapping(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
  {
    this.extraMappings.put(paramASN1ObjectIdentifier, paramString);
    return this;
  }
  
  public JceAsymmetricKeyUnwrapper setProvider(String paramString)
  {
    this.helper = new OperatorHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JceAsymmetricKeyUnwrapper setProvider(Provider paramProvider)
  {
    this.helper = new OperatorHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.jcajce.JceAsymmetricKeyUnwrapper
 * JD-Core Version:    0.7.0.1
 */