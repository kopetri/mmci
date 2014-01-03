package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;

public class ECNRSigner
  implements DSA
{
  private boolean forSigning;
  private ECKeyParameters key;
  private SecureRandom random;
  
  public BigInteger[] generateSignature(byte[] paramArrayOfByte)
  {
    if (!this.forSigning) {
      throw new IllegalStateException("not initialised for signing");
    }
    BigInteger localBigInteger1 = ((ECPrivateKeyParameters)this.key).getParameters().getN();
    int i = localBigInteger1.bitLength();
    BigInteger localBigInteger2 = new BigInteger(1, paramArrayOfByte);
    int j = localBigInteger2.bitLength();
    ECPrivateKeyParameters localECPrivateKeyParameters = (ECPrivateKeyParameters)this.key;
    if (j > i) {
      throw new DataLengthException("input too large for ECNR key.");
    }
    AsymmetricCipherKeyPair localAsymmetricCipherKeyPair;
    BigInteger localBigInteger3;
    do
    {
      ECKeyPairGenerator localECKeyPairGenerator = new ECKeyPairGenerator();
      localECKeyPairGenerator.init(new ECKeyGenerationParameters(localECPrivateKeyParameters.getParameters(), this.random));
      localAsymmetricCipherKeyPair = localECKeyPairGenerator.generateKeyPair();
      localBigInteger3 = ((ECPublicKeyParameters)localAsymmetricCipherKeyPair.getPublic()).getQ().getX().toBigInteger().add(localBigInteger2).mod(localBigInteger1);
    } while (localBigInteger3.equals(ECConstants.ZERO));
    BigInteger localBigInteger4 = localECPrivateKeyParameters.getD();
    return new BigInteger[] { localBigInteger3, ((ECPrivateKeyParameters)localAsymmetricCipherKeyPair.getPrivate()).getD().subtract(localBigInteger3.multiply(localBigInteger4)).mod(localBigInteger1) };
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.forSigning = paramBoolean;
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
    if (this.forSigning) {
      throw new IllegalStateException("not initialised for verifying");
    }
    ECPublicKeyParameters localECPublicKeyParameters = (ECPublicKeyParameters)this.key;
    BigInteger localBigInteger1 = localECPublicKeyParameters.getParameters().getN();
    int i = localBigInteger1.bitLength();
    BigInteger localBigInteger2 = new BigInteger(1, paramArrayOfByte);
    if (localBigInteger2.bitLength() > i) {
      throw new DataLengthException("input too large for ECNR key.");
    }
    if ((paramBigInteger1.compareTo(ECConstants.ONE) < 0) || (paramBigInteger1.compareTo(localBigInteger1) >= 0)) {}
    while ((paramBigInteger2.compareTo(ECConstants.ZERO) < 0) || (paramBigInteger2.compareTo(localBigInteger1) >= 0)) {
      return false;
    }
    return paramBigInteger1.subtract(ECAlgorithms.sumOfTwoMultiplies(localECPublicKeyParameters.getParameters().getG(), paramBigInteger2, localECPublicKeyParameters.getQ(), paramBigInteger1).getX().toBigInteger()).mod(localBigInteger1).equals(localBigInteger2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.ECNRSigner
 * JD-Core Version:    0.7.0.1
 */