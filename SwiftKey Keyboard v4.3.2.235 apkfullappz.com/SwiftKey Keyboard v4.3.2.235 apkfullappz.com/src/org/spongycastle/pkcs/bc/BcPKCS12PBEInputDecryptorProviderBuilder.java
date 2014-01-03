package org.spongycastle.pkcs.bc;

import java.io.InputStream;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.generators.PKCS12ParametersGenerator;
import org.spongycastle.crypto.io.CipherInputStream;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.InputDecryptor;
import org.spongycastle.operator.InputDecryptorProvider;

public class BcPKCS12PBEInputDecryptorProviderBuilder
{
  private ExtendedDigest digest;
  
  public BcPKCS12PBEInputDecryptorProviderBuilder()
  {
    this(new SHA1Digest());
  }
  
  public BcPKCS12PBEInputDecryptorProviderBuilder(ExtendedDigest paramExtendedDigest)
  {
    this.digest = paramExtendedDigest;
  }
  
  public InputDecryptorProvider build(final char[] paramArrayOfChar)
  {
    new InputDecryptorProvider()
    {
      public InputDecryptor get(final AlgorithmIdentifier paramAnonymousAlgorithmIdentifier)
      {
        final PaddedBufferedBlockCipher localPaddedBufferedBlockCipher = PKCS12PBEUtils.getEngine(paramAnonymousAlgorithmIdentifier.getAlgorithm());
        PKCS12PBEParams localPKCS12PBEParams = PKCS12PBEParams.getInstance(paramAnonymousAlgorithmIdentifier.getParameters());
        localPaddedBufferedBlockCipher.init(false, PKCS12PBEUtils.createCipherParameters(paramAnonymousAlgorithmIdentifier.getAlgorithm(), BcPKCS12PBEInputDecryptorProviderBuilder.this.digest, localPaddedBufferedBlockCipher.getBlockSize(), localPKCS12PBEParams, paramArrayOfChar));
        new InputDecryptor()
        {
          public AlgorithmIdentifier getAlgorithmIdentifier()
          {
            return paramAnonymousAlgorithmIdentifier;
          }
          
          public InputStream getInputStream(InputStream paramAnonymous2InputStream)
          {
            return new CipherInputStream(paramAnonymous2InputStream, localPaddedBufferedBlockCipher);
          }
          
          public GenericKey getKey()
          {
            return new GenericKey(PKCS12ParametersGenerator.PKCS12PasswordToBytes(BcPKCS12PBEInputDecryptorProviderBuilder.1.this.val$password));
          }
        };
      }
    };
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.bc.BcPKCS12PBEInputDecryptorProviderBuilder
 * JD-Core Version:    0.7.0.1
 */