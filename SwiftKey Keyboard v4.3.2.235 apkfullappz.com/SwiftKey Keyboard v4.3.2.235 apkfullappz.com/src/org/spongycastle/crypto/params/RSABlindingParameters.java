package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;

public class RSABlindingParameters
  implements CipherParameters
{
  private BigInteger blindingFactor;
  private RSAKeyParameters publicKey;
  
  public RSABlindingParameters(RSAKeyParameters paramRSAKeyParameters, BigInteger paramBigInteger)
  {
    if ((paramRSAKeyParameters instanceof RSAPrivateCrtKeyParameters)) {
      throw new IllegalArgumentException("RSA parameters should be for a public key");
    }
    this.publicKey = paramRSAKeyParameters;
    this.blindingFactor = paramBigInteger;
  }
  
  public BigInteger getBlindingFactor()
  {
    return this.blindingFactor;
  }
  
  public RSAKeyParameters getPublicKey()
  {
    return this.publicKey;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.RSABlindingParameters
 * JD-Core Version:    0.7.0.1
 */