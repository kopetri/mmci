package org.spongycastle.jcajce.provider.asymmetric.ecgost;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.ec.EC5Util;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;

public class KeyPairGeneratorSpi
  extends KeyPairGenerator
{
  String algorithm = "ECGOST3410";
  Object ecParams = null;
  ECKeyPairGenerator engine = new ECKeyPairGenerator();
  boolean initialised = false;
  ECKeyGenerationParameters param;
  SecureRandom random = null;
  int strength = 239;
  
  public KeyPairGeneratorSpi()
  {
    super("ECGOST3410");
  }
  
  public KeyPair generateKeyPair()
  {
    if (!this.initialised) {
      throw new IllegalStateException("EC Key Pair Generator not initialised");
    }
    AsymmetricCipherKeyPair localAsymmetricCipherKeyPair = this.engine.generateKeyPair();
    ECPublicKeyParameters localECPublicKeyParameters = (ECPublicKeyParameters)localAsymmetricCipherKeyPair.getPublic();
    ECPrivateKeyParameters localECPrivateKeyParameters = (ECPrivateKeyParameters)localAsymmetricCipherKeyPair.getPrivate();
    if ((this.ecParams instanceof org.spongycastle.jce.spec.ECParameterSpec))
    {
      org.spongycastle.jce.spec.ECParameterSpec localECParameterSpec1 = (org.spongycastle.jce.spec.ECParameterSpec)this.ecParams;
      BCECGOST3410PublicKey localBCECGOST3410PublicKey2 = new BCECGOST3410PublicKey(this.algorithm, localECPublicKeyParameters, localECParameterSpec1);
      return new KeyPair(localBCECGOST3410PublicKey2, new BCECGOST3410PrivateKey(this.algorithm, localECPrivateKeyParameters, localBCECGOST3410PublicKey2, localECParameterSpec1));
    }
    if (this.ecParams == null) {
      return new KeyPair(new BCECGOST3410PublicKey(this.algorithm, localECPublicKeyParameters), new BCECGOST3410PrivateKey(this.algorithm, localECPrivateKeyParameters));
    }
    java.security.spec.ECParameterSpec localECParameterSpec = (java.security.spec.ECParameterSpec)this.ecParams;
    BCECGOST3410PublicKey localBCECGOST3410PublicKey1 = new BCECGOST3410PublicKey(this.algorithm, localECPublicKeyParameters, localECParameterSpec);
    return new KeyPair(localBCECGOST3410PublicKey1, new BCECGOST3410PrivateKey(this.algorithm, localECPrivateKeyParameters, localBCECGOST3410PublicKey1, localECParameterSpec));
  }
  
  public void initialize(int paramInt, SecureRandom paramSecureRandom)
  {
    this.strength = paramInt;
    this.random = paramSecureRandom;
    if (this.ecParams != null) {
      try
      {
        initialize((ECGenParameterSpec)this.ecParams, paramSecureRandom);
        return;
      }
      catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
      {
        throw new InvalidParameterException("key size not configurable.");
      }
    }
    throw new InvalidParameterException("unknown key size.");
  }
  
  public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidAlgorithmParameterException
  {
    if ((paramAlgorithmParameterSpec instanceof org.spongycastle.jce.spec.ECParameterSpec))
    {
      org.spongycastle.jce.spec.ECParameterSpec localECParameterSpec4 = (org.spongycastle.jce.spec.ECParameterSpec)paramAlgorithmParameterSpec;
      this.ecParams = paramAlgorithmParameterSpec;
      this.param = new ECKeyGenerationParameters(new ECDomainParameters(localECParameterSpec4.getCurve(), localECParameterSpec4.getG(), localECParameterSpec4.getN()), paramSecureRandom);
      this.engine.init(this.param);
      this.initialised = true;
      return;
    }
    if ((paramAlgorithmParameterSpec instanceof java.security.spec.ECParameterSpec))
    {
      java.security.spec.ECParameterSpec localECParameterSpec3 = (java.security.spec.ECParameterSpec)paramAlgorithmParameterSpec;
      this.ecParams = paramAlgorithmParameterSpec;
      ECCurve localECCurve2 = EC5Util.convertCurve(localECParameterSpec3.getCurve());
      this.param = new ECKeyGenerationParameters(new ECDomainParameters(localECCurve2, EC5Util.convertPoint(localECCurve2, localECParameterSpec3.getGenerator(), false), localECParameterSpec3.getOrder(), BigInteger.valueOf(localECParameterSpec3.getCofactor())), paramSecureRandom);
      this.engine.init(this.param);
      this.initialised = true;
      return;
    }
    if (((paramAlgorithmParameterSpec instanceof ECGenParameterSpec)) || ((paramAlgorithmParameterSpec instanceof ECNamedCurveGenParameterSpec)))
    {
      if ((paramAlgorithmParameterSpec instanceof ECGenParameterSpec)) {}
      ECDomainParameters localECDomainParameters;
      for (String str = ((ECGenParameterSpec)paramAlgorithmParameterSpec).getName();; str = ((ECNamedCurveGenParameterSpec)paramAlgorithmParameterSpec).getName())
      {
        localECDomainParameters = ECGOST3410NamedCurves.getByName(str);
        if (localECDomainParameters != null) {
          break;
        }
        throw new InvalidAlgorithmParameterException("unknown curve name: " + str);
      }
      this.ecParams = new ECNamedCurveSpec(str, localECDomainParameters.getCurve(), localECDomainParameters.getG(), localECDomainParameters.getN(), localECDomainParameters.getH(), localECDomainParameters.getSeed());
      java.security.spec.ECParameterSpec localECParameterSpec1 = (java.security.spec.ECParameterSpec)this.ecParams;
      ECCurve localECCurve1 = EC5Util.convertCurve(localECParameterSpec1.getCurve());
      this.param = new ECKeyGenerationParameters(new ECDomainParameters(localECCurve1, EC5Util.convertPoint(localECCurve1, localECParameterSpec1.getGenerator(), false), localECParameterSpec1.getOrder(), BigInteger.valueOf(localECParameterSpec1.getCofactor())), paramSecureRandom);
      this.engine.init(this.param);
      this.initialised = true;
      return;
    }
    if ((paramAlgorithmParameterSpec == null) && (BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa() != null))
    {
      org.spongycastle.jce.spec.ECParameterSpec localECParameterSpec2 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
      this.ecParams = paramAlgorithmParameterSpec;
      this.param = new ECKeyGenerationParameters(new ECDomainParameters(localECParameterSpec2.getCurve(), localECParameterSpec2.getG(), localECParameterSpec2.getN()), paramSecureRandom);
      this.engine.init(this.param);
      this.initialised = true;
      return;
    }
    if ((paramAlgorithmParameterSpec == null) && (BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa() == null)) {
      throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
    }
    throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec: " + paramAlgorithmParameterSpec.getClass().getName());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.ecgost.KeyPairGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */