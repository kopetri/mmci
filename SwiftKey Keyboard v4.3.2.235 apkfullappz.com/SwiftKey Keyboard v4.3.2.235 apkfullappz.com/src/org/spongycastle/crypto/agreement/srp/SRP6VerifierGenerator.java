package org.spongycastle.crypto.agreement.srp;

import java.math.BigInteger;
import org.spongycastle.crypto.Digest;

public class SRP6VerifierGenerator
{
  protected BigInteger N;
  protected Digest digest;
  protected BigInteger g;
  
  public BigInteger generateVerifier(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
  {
    BigInteger localBigInteger = SRP6Util.calculateX(this.digest, this.N, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3);
    return this.g.modPow(localBigInteger, this.N);
  }
  
  public void init(BigInteger paramBigInteger1, BigInteger paramBigInteger2, Digest paramDigest)
  {
    this.N = paramBigInteger1;
    this.g = paramBigInteger2;
    this.digest = paramDigest;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.agreement.srp.SRP6VerifierGenerator
 * JD-Core Version:    0.7.0.1
 */