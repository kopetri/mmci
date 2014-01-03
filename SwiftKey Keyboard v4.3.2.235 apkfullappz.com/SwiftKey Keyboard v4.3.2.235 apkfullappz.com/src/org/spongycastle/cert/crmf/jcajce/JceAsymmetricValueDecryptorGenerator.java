package org.spongycastle.cert.crmf.jcajce;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cert.crmf.CRMFException;
import org.spongycastle.cert.crmf.ValueDecryptorGenerator;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.InputDecryptor;

public class JceAsymmetricValueDecryptorGenerator
  implements ValueDecryptorGenerator
{
  private CRMFHelper helper = new CRMFHelper(new DefaultJcaJceHelper());
  private PrivateKey recipientKey;
  
  public JceAsymmetricValueDecryptorGenerator(PrivateKey paramPrivateKey)
  {
    this.recipientKey = paramPrivateKey;
  }
  
  private Key extractSecretKey(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, byte[] paramArrayOfByte)
    throws CRMFException
  {
    try
    {
      Cipher localCipher = this.helper.createCipher(paramAlgorithmIdentifier1.getAlgorithm());
      Key localKey1;
      try
      {
        localCipher.init(4, this.recipientKey);
        Key localKey2 = localCipher.unwrap(paramArrayOfByte, paramAlgorithmIdentifier2.getAlgorithm().getId(), 3);
        localKey1 = localKey2;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        for (;;)
        {
          try
          {
            localCipher.init(2, this.recipientKey);
            SecretKeySpec localSecretKeySpec = new SecretKeySpec(localCipher.doFinal(paramArrayOfByte), paramAlgorithmIdentifier2.getAlgorithm().getId());
            return localSecretKeySpec;
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
        }
      }
      catch (IllegalStateException localIllegalStateException)
      {
        for (;;)
        {
          localKey1 = null;
        }
      }
      catch (UnsupportedOperationException localUnsupportedOperationException)
      {
        for (;;)
        {
          localKey1 = null;
        }
      }
      catch (ProviderException localProviderException)
      {
        for (;;)
        {
          localKey1 = null;
        }
      }
      if (localKey1 == null) {}
      return localKey1;
    }
    catch (InvalidKeyException localInvalidKeyException1)
    {
      throw new CRMFException("key invalid in message.", localInvalidKeyException1);
    }
    catch (IllegalBlockSizeException localIllegalBlockSizeException1)
    {
      throw new CRMFException("illegal blocksize in message.", localIllegalBlockSizeException1);
    }
    catch (BadPaddingException localBadPaddingException1)
    {
      throw new CRMFException("bad padding in message.", localBadPaddingException1);
    }
  }
  
  public InputDecryptor getValueDecryptor(AlgorithmIdentifier paramAlgorithmIdentifier1, final AlgorithmIdentifier paramAlgorithmIdentifier2, byte[] paramArrayOfByte)
    throws CRMFException
  {
    Key localKey = extractSecretKey(paramAlgorithmIdentifier1, paramAlgorithmIdentifier2, paramArrayOfByte);
    new InputDecryptor()
    {
      public AlgorithmIdentifier getAlgorithmIdentifier()
      {
        return paramAlgorithmIdentifier2;
      }
      
      public InputStream getInputStream(InputStream paramAnonymousInputStream)
      {
        return new CipherInputStream(paramAnonymousInputStream, this.val$dataCipher);
      }
    };
  }
  
  public JceAsymmetricValueDecryptorGenerator setProvider(String paramString)
  {
    this.helper = new CRMFHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JceAsymmetricValueDecryptorGenerator setProvider(Provider paramProvider)
  {
    this.helper = new CRMFHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.jcajce.JceAsymmetricValueDecryptorGenerator
 * JD-Core Version:    0.7.0.1
 */