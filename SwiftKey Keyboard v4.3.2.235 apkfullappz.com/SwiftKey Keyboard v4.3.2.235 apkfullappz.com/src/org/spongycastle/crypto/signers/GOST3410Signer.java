package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.GOST3410KeyParameters;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class GOST3410Signer
  implements DSA
{
  GOST3410KeyParameters key;
  SecureRandom random;
  
  public BigInteger[] generateSignature(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    for (int i = 0; i != arrayOfByte.length; i++) {
      arrayOfByte[i] = paramArrayOfByte[(-1 + arrayOfByte.length - i)];
    }
    BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte);
    GOST3410Parameters localGOST3410Parameters = this.key.getParameters();
    BigInteger localBigInteger2;
    do
    {
      localBigInteger2 = new BigInteger(localGOST3410Parameters.getQ().bitLength(), this.random);
    } while (localBigInteger2.compareTo(localGOST3410Parameters.getQ()) >= 0);
    BigInteger localBigInteger3 = localGOST3410Parameters.getA().modPow(localBigInteger2, localGOST3410Parameters.getP()).mod(localGOST3410Parameters.getQ());
    return new BigInteger[] { localBigInteger3, localBigInteger2.multiply(localBigInteger1).add(((GOST3410PrivateKeyParameters)this.key).getX().multiply(localBigInteger3)).mod(localGOST3410Parameters.getQ()) };
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (paramBoolean)
    {
      if ((paramCipherParameters instanceof ParametersWithRandom))
      {
        ParametersWithRandom localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
        this.random = localParametersWithRandom.getRandom();
        this.key = ((GOST3410PrivateKeyParameters)localParametersWithRandom.getParameters());
        return;
      }
      this.random = new SecureRandom();
      this.key = ((GOST3410PrivateKeyParameters)paramCipherParameters);
      return;
    }
    this.key = ((GOST3410PublicKeyParameters)paramCipherParameters);
  }
  
  public boolean verifySignature(byte[] paramArrayOfByte, BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    for (int i = 0; i != arrayOfByte.length; i++) {
      arrayOfByte[i] = paramArrayOfByte[(-1 + arrayOfByte.length - i)];
    }
    BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte);
    GOST3410Parameters localGOST3410Parameters = this.key.getParameters();
    BigInteger localBigInteger2 = BigInteger.valueOf(0L);
    if ((localBigInteger2.compareTo(paramBigInteger1) >= 0) || (localGOST3410Parameters.getQ().compareTo(paramBigInteger1) <= 0)) {}
    while ((localBigInteger2.compareTo(paramBigInteger2) >= 0) || (localGOST3410Parameters.getQ().compareTo(paramBigInteger2) <= 0)) {
      return false;
    }
    BigInteger localBigInteger3 = localBigInteger1.modPow(localGOST3410Parameters.getQ().subtract(new BigInteger("2")), localGOST3410Parameters.getQ());
    BigInteger localBigInteger4 = paramBigInteger2.multiply(localBigInteger3).mod(localGOST3410Parameters.getQ());
    BigInteger localBigInteger5 = localGOST3410Parameters.getQ().subtract(paramBigInteger1).multiply(localBigInteger3).mod(localGOST3410Parameters.getQ());
    return localGOST3410Parameters.getA().modPow(localBigInteger4, localGOST3410Parameters.getP()).multiply(((GOST3410PublicKeyParameters)this.key).getY().modPow(localBigInteger5, localGOST3410Parameters.getP())).mod(localGOST3410Parameters.getP()).mod(localGOST3410Parameters.getQ()).equals(paramBigInteger1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.GOST3410Signer
 * JD-Core Version:    0.7.0.1
 */