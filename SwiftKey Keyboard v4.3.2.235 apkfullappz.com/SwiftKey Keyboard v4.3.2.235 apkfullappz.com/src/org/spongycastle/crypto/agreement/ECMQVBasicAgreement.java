package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.MQVPrivateParameters;
import org.spongycastle.crypto.params.MQVPublicParameters;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;

public class ECMQVBasicAgreement
  implements BasicAgreement
{
  MQVPrivateParameters privParams;
  
  private ECPoint calculateMqvAgreement(ECDomainParameters paramECDomainParameters, ECPrivateKeyParameters paramECPrivateKeyParameters1, ECPrivateKeyParameters paramECPrivateKeyParameters2, ECPublicKeyParameters paramECPublicKeyParameters1, ECPublicKeyParameters paramECPublicKeyParameters2, ECPublicKeyParameters paramECPublicKeyParameters3)
  {
    BigInteger localBigInteger1 = paramECDomainParameters.getN();
    int i = (1 + localBigInteger1.bitLength()) / 2;
    BigInteger localBigInteger2 = ECConstants.ONE.shiftLeft(i);
    if (paramECPublicKeyParameters1 == null) {}
    ECPoint localECPoint2;
    for (ECPoint localECPoint1 = paramECDomainParameters.getG().multiply(paramECPrivateKeyParameters2.getD());; localECPoint1 = paramECPublicKeyParameters1.getQ())
    {
      BigInteger localBigInteger3 = localECPoint1.getX().toBigInteger().mod(localBigInteger2).setBit(i);
      BigInteger localBigInteger4 = paramECPrivateKeyParameters1.getD().multiply(localBigInteger3).mod(localBigInteger1).add(paramECPrivateKeyParameters2.getD()).mod(localBigInteger1);
      BigInteger localBigInteger5 = paramECPublicKeyParameters3.getQ().getX().toBigInteger().mod(localBigInteger2).setBit(i);
      BigInteger localBigInteger6 = paramECDomainParameters.getH().multiply(localBigInteger4).mod(localBigInteger1);
      localECPoint2 = ECAlgorithms.sumOfTwoMultiplies(paramECPublicKeyParameters2.getQ(), localBigInteger5.multiply(localBigInteger6).mod(localBigInteger1), paramECPublicKeyParameters3.getQ(), localBigInteger6);
      if (!localECPoint2.isInfinity()) {
        break;
      }
      throw new IllegalStateException("Infinity is not a valid agreement value for MQV");
    }
    return localECPoint2;
  }
  
  public BigInteger calculateAgreement(CipherParameters paramCipherParameters)
  {
    MQVPublicParameters localMQVPublicParameters = (MQVPublicParameters)paramCipherParameters;
    ECPrivateKeyParameters localECPrivateKeyParameters = this.privParams.getStaticPrivateKey();
    return calculateMqvAgreement(localECPrivateKeyParameters.getParameters(), localECPrivateKeyParameters, this.privParams.getEphemeralPrivateKey(), this.privParams.getEphemeralPublicKey(), localMQVPublicParameters.getStaticPublicKey(), localMQVPublicParameters.getEphemeralPublicKey()).getX().toBigInteger();
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    this.privParams = ((MQVPrivateParameters)paramCipherParameters);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.agreement.ECMQVBasicAgreement
 * JD-Core Version:    0.7.0.1
 */