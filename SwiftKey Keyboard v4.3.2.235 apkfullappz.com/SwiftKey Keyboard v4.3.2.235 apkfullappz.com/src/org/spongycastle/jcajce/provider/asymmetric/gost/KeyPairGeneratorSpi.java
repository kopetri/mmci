package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.GOST3410KeyPairGenerator;
import org.spongycastle.crypto.params.GOST3410KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class KeyPairGeneratorSpi
  extends KeyPairGenerator
{
  GOST3410KeyPairGenerator engine = new GOST3410KeyPairGenerator();
  GOST3410ParameterSpec gost3410Params;
  boolean initialised = false;
  GOST3410KeyGenerationParameters param;
  SecureRandom random = null;
  int strength = 1024;
  
  public KeyPairGeneratorSpi()
  {
    super("GOST3410");
  }
  
  private void init(GOST3410ParameterSpec paramGOST3410ParameterSpec, SecureRandom paramSecureRandom)
  {
    GOST3410PublicKeyParameterSetSpec localGOST3410PublicKeyParameterSetSpec = paramGOST3410ParameterSpec.getPublicKeyParameters();
    this.param = new GOST3410KeyGenerationParameters(paramSecureRandom, new GOST3410Parameters(localGOST3410PublicKeyParameterSetSpec.getP(), localGOST3410PublicKeyParameterSetSpec.getQ(), localGOST3410PublicKeyParameterSetSpec.getA()));
    this.engine.init(this.param);
    this.initialised = true;
    this.gost3410Params = paramGOST3410ParameterSpec;
  }
  
  public KeyPair generateKeyPair()
  {
    if (!this.initialised) {
      init(new GOST3410ParameterSpec(CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_A.getId()), new SecureRandom());
    }
    AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = this.engine.generateKeyPair();
    GOST3410PublicKeyParameters localGOST3410PublicKeyParameters = (GOST3410PublicKeyParameters)localAsymmetricCipherKeyPair.getPublic();
    GOST3410PrivateKeyParameters localGOST3410PrivateKeyParameters = (GOST3410PrivateKeyParameters)localAsymmetricCipherKeyPair.getPrivate();
    return new KeyPair(new BCGOST3410PublicKey(localGOST3410PublicKeyParameters, this.gost3410Params), new BCGOST3410PrivateKey(localGOST3410PrivateKeyParameters, this.gost3410Params));
  }
  
  public void initialize(int paramInt, SecureRandom paramSecureRandom)
  {
    this.strength = paramInt;
    this.random = paramSecureRandom;
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidAlgorithmParameterException
  {
    if (!(paramAlgorithmParameterSpec instanceof GOST3410ParameterSpec)) {
      throw new InvalidAlgorithmParameterException("parameter object not a GOST3410ParameterSpec");
    }
    init((GOST3410ParameterSpec)paramAlgorithmParameterSpec, paramSecureRandom);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.gost.KeyPairGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */