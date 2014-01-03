package org.spongycastle.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class RSAKeyGenerationParameters
  extends KeyGenerationParameters
{
  private int certainty;
  private BigInteger publicExponent;
  
  public RSAKeyGenerationParameters(BigInteger paramBigInteger, SecureRandom paramSecureRandom, int paramInt1, int paramInt2)
  {
    super(paramSecureRandom, paramInt1);
    if (paramInt1 < 12) {
      throw new IllegalArgumentException("key strength too small");
    }
    if (!paramBigInteger.testBit(0)) {
      throw new IllegalArgumentException("public exponent cannot be even");
    }
    this.publicExponent = paramBigInteger;
    this.certainty = paramInt2;
  }
  
  public int getCertainty()
  {
    return this.certainty;
  }
  
  public BigInteger getPublicExponent()
  {
    return this.publicExponent;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.RSAKeyGenerationParameters
 * JD-Core Version:    0.7.0.1
 */