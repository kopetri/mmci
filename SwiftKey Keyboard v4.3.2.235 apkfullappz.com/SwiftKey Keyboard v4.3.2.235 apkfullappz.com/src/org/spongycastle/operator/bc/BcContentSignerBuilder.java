package org.spongycastle.operator.bc;

import java.io.OutputStream;
import java.security.SecureRandom;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.RuntimeOperatorException;

public abstract class BcContentSignerBuilder
{
  private AlgorithmIdentifier digAlgId;
  private SecureRandom random;
  private AlgorithmIdentifier sigAlgId;
  
  public BcContentSignerBuilder(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2)
  {
    this.sigAlgId = paramAlgorithmIdentifier1;
    this.digAlgId = paramAlgorithmIdentifier2;
  }
  
  public ContentSigner build(AsymmetricKeyParameter paramAsymmetricKeyParameter)
    throws OperatorCreationException
  {
    final Signer localSigner = createSigner(this.sigAlgId, this.digAlgId);
    if (this.random != null) {
      localSigner.init(true, new ParametersWithRandom(paramAsymmetricKeyParameter, this.random));
    }
    for (;;)
    {
      new ContentSigner()
      {
        private BcSignerOutputStream stream = new BcSignerOutputStream(localSigner);
        
        public AlgorithmIdentifier getAlgorithmIdentifier()
        {
          return BcContentSignerBuilder.this.sigAlgId;
        }
        
        public OutputStream getOutputStream()
        {
          return this.stream;
        }
        
        public byte[] getSignature()
        {
          try
          {
            byte[] arrayOfByte = this.stream.getSignature();
            return arrayOfByte;
          }
          catch (CryptoException localCryptoException)
          {
            throw new RuntimeOperatorException("exception obtaining signature: " + localCryptoException.getMessage(), localCryptoException);
          }
        }
      };
      localSigner.init(true, paramAsymmetricKeyParameter);
    }
  }
  
  protected abstract Signer createSigner(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2)
    throws OperatorCreationException;
  
  public BcContentSignerBuilder setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.bc.BcContentSignerBuilder
 * JD-Core Version:    0.7.0.1
 */