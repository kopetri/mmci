package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.util.BigIntegers;

public class RSABlindedEngine
  implements AsymmetricBlockCipher
{
  private static BigInteger ONE = BigInteger.valueOf(1L);
  private RSACoreEngine core = new RSACoreEngine();
  private RSAKeyParameters key;
  private SecureRandom random;
  
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
    this.core.init(paramBoolean, paramCipherParameters);
    if ((paramCipherParameters instanceof ParametersWithRandom))
    {
      ParametersWithRandom localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
      this.key = ((RSAKeyParameters)localParametersWithRandom.getParameters());
      this.random = localParametersWithRandom.getRandom();
      return;
    }
    this.key = ((RSAKeyParameters)paramCipherParameters);
    this.random = new SecureRandom();
  }
  
  public byte[] processBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.key == null) {
      throw new IllegalStateException("RSA engine not initialised");
    }
    BigInteger localBigInteger1 = this.core.convertInput(paramArrayOfByte, paramInt1, paramInt2);
    BigInteger localBigInteger2;
    if ((this.key instanceof RSAPrivateCrtKeyParameters))
    {
      RSAPrivateCrtKeyParameters localRSAPrivateCrtKeyParameters = (RSAPrivateCrtKeyParameters)this.key;
      BigInteger localBigInteger3 = localRSAPrivateCrtKeyParameters.getPublicExponent();
      if (localBigInteger3 != null)
      {
        BigInteger localBigInteger4 = localRSAPrivateCrtKeyParameters.getModulus();
        BigInteger localBigInteger5 = BigIntegers.createRandomInRange(ONE, localBigInteger4.subtract(ONE), this.random);
        BigInteger localBigInteger6 = localBigInteger5.modPow(localBigInteger3, localBigInteger4).multiply(localBigInteger1).mod(localBigInteger4);
        localBigInteger2 = this.core.processBlock(localBigInteger6).multiply(localBigInteger5.modInverse(localBigInteger4)).mod(localBigInteger4);
      }
    }
    for (;;)
    {
      return this.core.convertOutput(localBigInteger2);
      localBigInteger2 = this.core.processBlock(localBigInteger1);
      continue;
      localBigInteger2 = this.core.processBlock(localBigInteger1);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RSABlindedEngine
 * JD-Core Version:    0.7.0.1
 */