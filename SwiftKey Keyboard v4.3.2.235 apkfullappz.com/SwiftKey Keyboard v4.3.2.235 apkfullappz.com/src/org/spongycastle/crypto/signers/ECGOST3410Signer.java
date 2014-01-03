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

public class ECGOST3410Signer
  implements DSA
{
  ECKeyParameters key;
  SecureRandom random;
  
  public BigInteger[] generateSignature(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    for (int i = 0; i != arrayOfByte.length; i++) {
      arrayOfByte[i] = paramArrayOfByte[(-1 + arrayOfByte.length - i)];
    }
    BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte);
    BigInteger localBigInteger2 = this.key.getParameters().getN();
    BigInteger localBigInteger4;
    BigInteger localBigInteger6;
    do
    {
      BigInteger localBigInteger3;
      do
      {
        do
        {
          localBigInteger3 = new BigInteger(localBigInteger2.bitLength(), this.random);
        } while (localBigInteger3.equals(ECConstants.ZERO));
        localBigInteger4 = this.key.getParameters().getG().multiply(localBigInteger3).getX().toBigInteger().mod(localBigInteger2);
      } while (localBigInteger4.equals(ECConstants.ZERO));
      BigInteger localBigInteger5 = ((ECPrivateKeyParameters)this.key).getD();
      localBigInteger6 = localBigInteger3.multiply(localBigInteger1).add(localBigInteger5.multiply(localBigInteger4)).mod(localBigInteger2);
    } while (localBigInteger6.equals(ECConstants.ZERO));
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
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    for (int i = 0; i != arrayOfByte.length; i++) {
      arrayOfByte[i] = paramArrayOfByte[(-1 + arrayOfByte.length - i)];
    }
    BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte);
    BigInteger localBigInteger2 = this.key.getParameters().getN();
    if ((paramBigInteger1.compareTo(ECConstants.ONE) < 0) || (paramBigInteger1.compareTo(localBigInteger2) >= 0)) {}
    while ((paramBigInteger2.compareTo(ECConstants.ONE) < 0) || (paramBigInteger2.compareTo(localBigInteger2) >= 0)) {
      return false;
    }
    BigInteger localBigInteger3 = localBigInteger1.modInverse(localBigInteger2);
    BigInteger localBigInteger4 = paramBigInteger2.multiply(localBigInteger3).mod(localBigInteger2);
    BigInteger localBigInteger5 = localBigInteger2.subtract(paramBigInteger1).multiply(localBigInteger3).mod(localBigInteger2);
    return ECAlgorithms.sumOfTwoMultiplies(this.key.getParameters().getG(), localBigInteger4, ((ECPublicKeyParameters)this.key).getQ(), localBigInteger5).getX().toBigInteger().mod(localBigInteger2).equals(paramBigInteger1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.ECGOST3410Signer
 * JD-Core Version:    0.7.0.1
 */