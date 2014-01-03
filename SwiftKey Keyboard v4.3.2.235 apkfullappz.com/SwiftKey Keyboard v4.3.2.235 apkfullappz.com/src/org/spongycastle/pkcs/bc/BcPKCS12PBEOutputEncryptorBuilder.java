package org.spongycastle.pkcs.bc;

import java.io.OutputStream;
import java.security.SecureRandom;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.generators.PKCS12ParametersGenerator;
import org.spongycastle.crypto.io.CipherOutputStream;
import org.spongycastle.crypto.paddings.PKCS7Padding;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OutputEncryptor;

public class BcPKCS12PBEOutputEncryptorBuilder
{
  private ASN1ObjectIdentifier algorithm;
  private ExtendedDigest digest;
  private BufferedBlockCipher engine;
  private SecureRandom random;
  
  public BcPKCS12PBEOutputEncryptorBuilder(ASN1ObjectIdentifier paramASN1ObjectIdentifier, BlockCipher paramBlockCipher)
  {
    this(paramASN1ObjectIdentifier, paramBlockCipher, new SHA1Digest());
  }
  
  public BcPKCS12PBEOutputEncryptorBuilder(ASN1ObjectIdentifier paramASN1ObjectIdentifier, BlockCipher paramBlockCipher, ExtendedDigest paramExtendedDigest)
  {
    this.algorithm = paramASN1ObjectIdentifier;
    this.engine = new PaddedBufferedBlockCipher(paramBlockCipher, new PKCS7Padding());
    this.digest = paramExtendedDigest;
  }
  
  public OutputEncryptor build(final char[] paramArrayOfChar)
  {
    if (this.random == null) {
      this.random = new SecureRandom();
    }
    byte[] arrayOfByte = new byte[20];
    this.random.nextBytes(arrayOfByte);
    final PKCS12PBEParams localPKCS12PBEParams = new PKCS12PBEParams(arrayOfByte, 1024);
    CipherParameters localCipherParameters = PKCS12PBEUtils.createCipherParameters(this.algorithm, this.digest, this.engine.getBlockSize(), localPKCS12PBEParams, paramArrayOfChar);
    this.engine.init(true, localCipherParameters);
    new OutputEncryptor()
    {
      public AlgorithmIdentifier getAlgorithmIdentifier()
      {
        return new AlgorithmIdentifier(BcPKCS12PBEOutputEncryptorBuilder.this.algorithm, localPKCS12PBEParams);
      }
      
      public GenericKey getKey()
      {
        return new GenericKey(PKCS12ParametersGenerator.PKCS12PasswordToBytes(paramArrayOfChar));
      }
      
      public OutputStream getOutputStream(OutputStream paramAnonymousOutputStream)
      {
        return new CipherOutputStream(paramAnonymousOutputStream, BcPKCS12PBEOutputEncryptorBuilder.this.engine);
      }
    };
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.bc.BcPKCS12PBEOutputEncryptorBuilder
 * JD-Core Version:    0.7.0.1
 */