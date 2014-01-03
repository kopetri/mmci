package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSABlindingParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class RSABlindingEngine
  implements AsymmetricBlockCipher
{
  private BigInteger blindingFactor;
  private RSACoreEngine core = new RSACoreEngine();
  private boolean forEncryption;
  private RSAKeyParameters key;
  
  private BigInteger blindMessage(BigInteger paramBigInteger)
  {
    return paramBigInteger.multiply(this.blindingFactor.modPow(this.key.getExponent(), this.key.getModulus())).mod(this.key.getModulus());
  }
  
  private BigInteger unblindMessage(BigInteger paramBigInteger)
  {
    BigInteger localBigInteger = this.key.getModulus();
    return paramBigInteger.multiply(this.blindingFactor.modInverse(localBigInteger)).mod(localBigInteger);
  }
  
  public int getInputBlockSize()
  {
    return this.core.getInputBlockSize();
  }
  
  public int getOutputBlockSize()
  {
    return this.core.getOutputBlockSize();
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof ParametersWithRandom)) {}
    for (RSABlindingParameters localRSABlindingParameters = (RSABlindingParameters)((ParametersWithRandom)paramCipherParameters).getParameters();; localRSABlindingParameters = (RSABlindingParameters)paramCipherParameters)
    {
      this.core.init(paramBoolean, localRSABlindingParameters.getPublicKey());
      this.forEncryption = paramBoolean;
      this.key = localRSABlindingParameters.getPublicKey();
      this.blindingFactor = localRSABlindingParameters.getBlindingFactor();
      return;
    }
  }
  
  public byte[] processBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    BigInteger localBigInteger1 = this.core.convertInput(paramArrayOfByte, paramInt1, paramInt2);
    if (this.forEncryption) {}
    for (BigInteger localBigInteger2 = blindMessage(localBigInteger1);; localBigInteger2 = unblindMessage(localBigInteger1)) {
      return this.core.convertOutput(localBigInteger2);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RSABlindingEngine
 * JD-Core Version:    0.7.0.1
 */