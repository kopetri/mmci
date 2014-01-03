package org.spongycastle.crypto.signers;

import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class GenericSigner
  implements Signer
{
  private final Digest digest;
  private final AsymmetricBlockCipher engine;
  private boolean forSigning;
  
  public GenericSigner(AsymmetricBlockCipher paramAsymmetricBlockCipher, Digest paramDigest)
  {
    this.engine = paramAsymmetricBlockCipher;
    this.digest = paramDigest;
  }
  
  public byte[] generateSignature()
    throws CryptoException, DataLengthException
  {
    if (!this.forSigning) {
      throw new IllegalStateException("GenericSigner not initialised for signature generation.");
    }
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    return this.engine.processBlock(arrayOfByte, 0, arrayOfByte.length);
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.forSigning = paramBoolean;
    if ((paramCipherParameters instanceof ParametersWithRandom)) {}
    for (AsymmetricKeyParameter localAsymmetricKeyParameter = (AsymmetricKeyParameter)((ParametersWithRandom)paramCipherParameters).getParameters(); (paramBoolean) && (!localAsymmetricKeyParameter.isPrivate()); localAsymmetricKeyParameter = (AsymmetricKeyParameter)paramCipherParameters) {
      throw new IllegalArgumentException("signing requires private key");
    }
    if ((!paramBoolean) && (localAsymmetricKeyParameter.isPrivate())) {
      throw new IllegalArgumentException("verification requires public key");
    }
    reset();
    this.engine.init(paramBoolean, paramCipherParameters);
  }
  
  public void reset()
  {
    this.digest.reset();
  }
  
  public void update(byte paramByte)
  {
    this.digest.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public boolean verifySignature(byte[] paramArrayOfByte)
  {
    if (this.forSigning) {
      throw new IllegalStateException("GenericSigner not initialised for verification");
    }
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    try
    {
      boolean bool = Arrays.constantTimeAreEqual(this.engine.processBlock(paramArrayOfByte, 0, paramArrayOfByte.length), arrayOfByte);
      return bool;
    }
    catch (Exception localException) {}
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.GenericSigner
 * JD-Core Version:    0.7.0.1
 */