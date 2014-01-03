package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.agreement.DHBasicAgreement;
import org.spongycastle.crypto.generators.DHBasicKeyPairGenerator;
import org.spongycastle.crypto.params.DHKeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.util.BigIntegers;

public class TlsDHUtils
{
  static final BigInteger ONE = BigInteger.valueOf(1L);
  static final BigInteger TWO = BigInteger.valueOf(2L);
  
  public static byte[] calculateDHBasicAgreement(DHPublicKeyParameters paramDHPublicKeyParameters, DHPrivateKeyParameters paramDHPrivateKeyParameters)
  {
    DHBasicAgreement localDHBasicAgreement = new DHBasicAgreement();
    localDHBasicAgreement.init(paramDHPrivateKeyParameters);
    return BigIntegers.asUnsignedByteArray(localDHBasicAgreement.calculateAgreement(paramDHPublicKeyParameters));
  }
  
  public static AsymmetricCipherKeyPair generateDHKeyPair(SecureRandom paramSecureRandom, DHParameters paramDHParameters)
  {
    DHBasicKeyPairGenerator localDHBasicKeyPairGenerator = new DHBasicKeyPairGenerator();
    localDHBasicKeyPairGenerator.init(new DHKeyGenerationParameters(paramSecureRandom, paramDHParameters));
    return localDHBasicKeyPairGenerator.generateKeyPair();
  }
  
  public static DHPrivateKeyParameters generateEphemeralClientKeyExchange(SecureRandom paramSecureRandom, DHParameters paramDHParameters, OutputStream paramOutputStream)
    throws IOException
  {
    AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = generateDHKeyPair(paramSecureRandom, paramDHParameters);
    DHPrivateKeyParameters localDHPrivateKeyParameters = (DHPrivateKeyParameters)localAsymmetricCipherKeyPair.getPrivate();
    TlsUtils.writeOpaque16(BigIntegers.asUnsignedByteArray(((DHPublicKeyParameters)localAsymmetricCipherKeyPair.getPublic()).getY()), paramOutputStream);
    return localDHPrivateKeyParameters;
  }
  
  public static DHPublicKeyParameters validateDHPublicKey(DHPublicKeyParameters paramDHPublicKeyParameters)
    throws IOException
  {
    BigInteger localBigInteger1 = paramDHPublicKeyParameters.getY();
    DHParameters localDHParameters = paramDHPublicKeyParameters.getParameters();
    BigInteger localBigInteger2 = localDHParameters.getP();
    BigInteger localBigInteger3 = localDHParameters.getG();
    if (!localBigInteger2.isProbablePrime(2)) {
      throw new TlsFatalAlert((short)47);
    }
    if ((localBigInteger3.compareTo(TWO) < 0) || (localBigInteger3.compareTo(localBigInteger2.subtract(TWO)) > 0)) {
      throw new TlsFatalAlert((short)47);
    }
    if ((localBigInteger1.compareTo(TWO) < 0) || (localBigInteger1.compareTo(localBigInteger2.subtract(ONE)) > 0)) {
      throw new TlsFatalAlert((short)47);
    }
    return paramDHPublicKeyParameters;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsDHUtils
 * JD-Core Version:    0.7.0.1
 */