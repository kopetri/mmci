package org.spongycastle.operator.bc;

import java.security.SecureRandom;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.operator.AsymmetricKeyWrapper;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OperatorException;

public abstract class BcAsymmetricKeyWrapper
  extends AsymmetricKeyWrapper
{
  private AsymmetricKeyParameter publicKey;
  private SecureRandom random;
  
  public BcAsymmetricKeyWrapper(AlgorithmIdentifier paramAlgorithmIdentifier, AsymmetricKeyParameter paramAsymmetricKeyParameter)
  {
    super(paramAlgorithmIdentifier);
    this.publicKey = paramAsymmetricKeyParameter;
  }
  
  protected abstract AsymmetricBlockCipher createAsymmetricWrapper(ASN1ObjectIdentifier paramASN1ObjectIdentifier);
  
  public byte[] generateWrappedKey(GenericKey paramGenericKey)
    throws OperatorException
  {
    AsymmetricBlockCipher localAsymmetricBlockCipher = createAsymmetricWrapper(getAlgorithmIdentifier().getAlgorithm());
    AsymmetricKeyParameter localAsymmetricKeyParameter = this.publicKey;
    if (this.random != null) {
      new ParametersWithRandom(localAsymmetricKeyParameter, this.random);
    }
    try
    {
      byte[] arrayOfByte1 = OperatorUtils.getKeyBytes(paramGenericKey);
      localAsymmetricBlockCipher.init(true, this.publicKey);
      byte[] arrayOfByte2 = localAsymmetricBlockCipher.processBlock(arrayOfByte1, 0, arrayOfByte1.length);
      return arrayOfByte2;
    }
    catch (InvalidCipherTextException localInvalidCipherTextException)
    {
      throw new OperatorException("unable to encrypt contents key", localInvalidCipherTextException);
    }
  }
  
  public BcAsymmetricKeyWrapper setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.bc.BcAsymmetricKeyWrapper
 * JD-Core Version:    0.7.0.1
 */