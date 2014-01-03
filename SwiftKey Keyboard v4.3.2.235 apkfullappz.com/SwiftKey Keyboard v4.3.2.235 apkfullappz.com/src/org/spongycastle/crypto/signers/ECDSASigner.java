package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;

public class ECDSASigner
  implements DSA, ECConstants
{
  ECKeyParameters key;
  SecureRandom random;
  
  private BigInteger calculateE(BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    int i = paramBigInteger.bitLength();
    int j = 8 * paramArrayOfByte.length;
    if (i >= j) {
      return new BigInteger(1, paramArrayOfByte);
    }
    return new BigInteger(1, paramArrayOfByte).shiftRight(j - i);
  }
  
  public BigInteger[] generateSignature(byte[] paramArrayOfByte)
  {
    BigInteger localBigInteger1 = this.key.getParameters().getN();
    BigInteger localBigInteger2 = calculateE(localBigInteger1, paramArrayOfByte);
    BigInteger localBigInteger4;
    BigInteger localBigInteger6;
    do
    {
      int i = localBigInteger1.bitLength();
      BigInteger localBigInteger3;
      do
      {
        do
        {
          localBigInteger3 = new BigInteger(i, this.random);
        } while ((localBigInteger3.equals(ZERO)) || (localBigInteger3.compareTo(localBigInteger1) >= 0));
        localBigInteger4 = this.key.getParameters().getG().multiply(localBigInteger3).getX().toBigInteger().mod(localBigInteger1);
      } while (localBigInteger4.equals(ZERO));
      BigInteger localBigInteger5 = ((ECPrivateKeyParameters)this.key).getD();
      localBigInteger6 = localBigInteger3.modInverse(localBigInteger1).multiply(localBigInteger2.add(localBigInteger5.multiply(localBigInteger4))).mod(localBigInteger1);
    } while (localBigInteger6.equals(ZERO));
    return new BigInteger[] { localBigInteger4, localBigInteger6 };
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (paramBoolean)
    {
      if ((paramCipherParameters instanceof ParametersWithRandom))
      {
        ParametersWithRandom localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
        this.random = localParametersWithRandom.getRandom();
        this.key = ((ECPrivateKeyParameters)localParametersWithRandom.getParameters());
        return;
      }
      this.random = new SecureRandom();
      this.key = ((ECPrivateKeyParameters)paramCipherParameters);
      return;
    }
    this.key = ((ECPublicKeyParameters)paramCipherParameters);
  }
  
  public boolean verifySignature(byte[] paramArrayOfByte, BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    BigInteger localBigInteger1 = this.key.getParameters().getN();
    BigInteger localBigInteger2 = calculateE(localBigInteger1, paramArrayOfByte);
    if ((paramBigInteger1.compareTo(ONE) < 0) || (paramBigInteger1.compareTo(localBigInteger1) >= 0)) {}
    while ((paramBigInteger2.compareTo(ONE) < 0) || (paramBigInteger2.compareTo(localBigInteger1) >= 0)) {
      return false;
    }
    BigInteger localBigInteger3 = paramBigInteger2.modInverse(localBigInteger1);
    BigInteger localBigInteger4 = localBigInteger2.multiply(localBigInteger3).mod(localBigInteger1);
    BigInteger localBigInteger5 = paramBigInteger1.multiply(localBigInteger3).mod(localBigInteger1);
    return ECAlgorithms.sumOfTwoMultiplies(this.key.getParameters().getG(), localBigInteger4, ((ECPublicKeyParameters)this.key).getQ(), localBigInteger5).getX().toBigInteger().mod(localBigInteger1).equals(paramBigInteger1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.ECDSASigner
 * JD-Core Version:    0.7.0.1
 */