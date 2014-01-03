package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.DSAKeyParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class DSASigner
  implements DSA
{
  DSAKeyParameters key;
  SecureRandom random;
  
  private BigInteger calculateE(BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    if (paramBigInteger.bitLength() >= 8 * paramArrayOfByte.length) {
      return new BigInteger(1, paramArrayOfByte);
    }
    byte[] arrayOfByte = new byte[paramBigInteger.bitLength() / 8];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, arrayOfByte.length);
    return new BigInteger(1, arrayOfByte);
  }
  
  public BigInteger[] generateSignature(byte[] paramArrayOfByte)
  {
    DSAParameters localDSAParameters = this.key.getParameters();
    BigInteger localBigInteger1 = calculateE(localDSAParameters.getQ(), paramArrayOfByte);
    int i = localDSAParameters.getQ().bitLength();
    BigInteger localBigInteger2;
    do
    {
      localBigInteger2 = new BigInteger(i, this.random);
    } while (localBigInteger2.compareTo(localDSAParameters.getQ()) >= 0);
    BigInteger localBigInteger3 = localDSAParameters.getG().modPow(localBigInteger2, localDSAParameters.getP()).mod(localDSAParameters.getQ());
    return new BigInteger[] { localBigInteger3, localBigInteger2.modInverse(localDSAParameters.getQ()).multiply(localBigInteger1.add(((DSAPrivateKeyParameters)this.key).getX().multiply(localBigInteger3))).mod(localDSAParameters.getQ()) };
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (paramBoolean)
    {
      if ((paramCipherParameters instanceof ParametersWithRandom))
      {
        ParametersWithRandom localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
        this.random = localParametersWithRandom.getRandom();
        this.key = ((DSAPrivateKeyParameters)localParametersWithRandom.getParameters());
        return;
      }
      this.random = new SecureRandom();
      this.key = ((DSAPrivateKeyParameters)paramCipherParameters);
      return;
    }
    this.key = ((DSAPublicKeyParameters)paramCipherParameters);
  }
  
  public boolean verifySignature(byte[] paramArrayOfByte, BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    DSAParameters localDSAParameters = this.key.getParameters();
    BigInteger localBigInteger1 = calculateE(localDSAParameters.getQ(), paramArrayOfByte);
    BigInteger localBigInteger2 = BigInteger.valueOf(0L);
    if ((localBigInteger2.compareTo(paramBigInteger1) >= 0) || (localDSAParameters.getQ().compareTo(paramBigInteger1) <= 0)) {}
    while ((localBigInteger2.compareTo(paramBigInteger2) >= 0) || (localDSAParameters.getQ().compareTo(paramBigInteger2) <= 0)) {
      return false;
    }
    BigInteger localBigInteger3 = paramBigInteger2.modInverse(localDSAParameters.getQ());
    BigInteger localBigInteger4 = localBigInteger1.multiply(localBigInteger3).mod(localDSAParameters.getQ());
    BigInteger localBigInteger5 = paramBigInteger1.multiply(localBigInteger3).mod(localDSAParameters.getQ());
    return localDSAParameters.getG().modPow(localBigInteger4, localDSAParameters.getP()).multiply(((DSAPublicKeyParameters)this.key).getY().modPow(localBigInteger5, localDSAParameters.getP())).mod(localDSAParameters.getP()).mod(localDSAParameters.getQ()).equals(paramBigInteger1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.DSASigner
 * JD-Core Version:    0.7.0.1
 */