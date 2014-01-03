package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTNamedCurves;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.spongycastle.asn1.x9.X962NamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;

public abstract class KeyPairGeneratorSpi
  extends KeyPairGenerator
{
  public KeyPairGeneratorSpi(String paramString)
  {
    super(paramString);
  }
  
  public static class EC
    extends KeyPairGeneratorSpi
  {
    private static Hashtable ecParameters;
    String algorithm;
    int certainty = 50;
    ProviderConfiguration configuration;
    Object ecParams = null;
    ECKeyPairGenerator engine = new ECKeyPairGenerator();
    boolean initialised = false;
    ECKeyGenerationParameters param;
    SecureRandom random = new SecureRandom();
    int strength = 239;
    
    static
    {
      Hashtable localHashtable = new Hashtable();
      ecParameters = localHashtable;
      localHashtable.put(new Integer(192), new ECGenParameterSpec("prime192v1"));
      ecParameters.put(new Integer(239), new ECGenParameterSpec("prime239v1"));
      ecParameters.put(new Integer(256), new ECGenParameterSpec("prime256v1"));
      ecParameters.put(new Integer(224), new ECGenParameterSpec("P-224"));
      ecParameters.put(new Integer(384), new ECGenParameterSpec("P-384"));
      ecParameters.put(new Integer(521), new ECGenParameterSpec("P-521"));
    }
    
    public EC()
    {
      super();
      this.algorithm = "EC";
      this.configuration = BouncyCastleProvider.CONFIGURATION;
    }
    
    public EC(String paramString, ProviderConfiguration paramProviderConfiguration)
    {
      super();
      this.algorithm = paramString;
      this.configuration = paramProviderConfiguration;
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
        BCECPublicKey localBCECPublicKey2 = new BCECPublicKey(this.algorithm, localECPublicKeyParameters, localECParameterSpec1, this.configuration);
        return new KeyPair(localBCECPublicKey2, new BCECPrivateKey(this.algorithm, localECPrivateKeyParameters, localBCECPublicKey2, localECParameterSpec1, this.configuration));
      }
      if (this.ecParams == null) {
        return new KeyPair(new BCECPublicKey(this.algorithm, localECPublicKeyParameters, this.configuration), new BCECPrivateKey(this.algorithm, localECPrivateKeyParameters, this.configuration));
      }
      java.security.spec.ECParameterSpec localECParameterSpec = (java.security.spec.ECParameterSpec)this.ecParams;
      BCECPublicKey localBCECPublicKey1 = new BCECPublicKey(this.algorithm, localECPublicKeyParameters, localECParameterSpec, this.configuration);
      return new KeyPair(localBCECPublicKey1, new BCECPrivateKey(this.algorithm, localECPrivateKeyParameters, localBCECPublicKey1, localECParameterSpec, this.configuration));
    }
    
    public void initialize(int paramInt, SecureRandom paramSecureRandom)
    {
      this.strength = paramInt;
      this.random = paramSecureRandom;
      ECGenParameterSpec localECGenParameterSpec = (ECGenParameterSpec)ecParameters.get(new Integer(paramInt));
      if (localECGenParameterSpec != null) {
        try
        {
          initialize(localECGenParameterSpec, paramSecureRandom);
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
        X9ECParameters localX9ECParameters;
        for (String str = ((ECGenParameterSpec)paramAlgorithmParameterSpec).getName();; str = ((ECNamedCurveGenParameterSpec)paramAlgorithmParameterSpec).getName())
        {
          localX9ECParameters = X962NamedCurves.getByName(str);
          if (localX9ECParameters != null) {
            break;
          }
          localX9ECParameters = SECNamedCurves.getByName(str);
          if (localX9ECParameters == null) {
            localX9ECParameters = NISTNamedCurves.getByName(str);
          }
          if (localX9ECParameters == null) {
            localX9ECParameters = TeleTrusTNamedCurves.getByName(str);
          }
          if (localX9ECParameters != null) {
            break;
          }
          try
          {
            ASN1ObjectIdentifier localASN1ObjectIdentifier = new ASN1ObjectIdentifier(str);
            localX9ECParameters = X962NamedCurves.getByOID(localASN1ObjectIdentifier);
            if (localX9ECParameters == null) {
              localX9ECParameters = SECNamedCurves.getByOID(localASN1ObjectIdentifier);
            }
            if (localX9ECParameters == null) {
              localX9ECParameters = NISTNamedCurves.getByOID(localASN1ObjectIdentifier);
            }
            if (localX9ECParameters == null) {
              localX9ECParameters = TeleTrusTNamedCurves.getByOID(localASN1ObjectIdentifier);
            }
            if (localX9ECParameters != null) {
              break;
            }
            throw new InvalidAlgorithmParameterException("unknown curve OID: " + str);
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            throw new InvalidAlgorithmParameterException("unknown curve name: " + str);
          }
        }
        this.ecParams = new ECNamedCurveSpec(str, localX9ECParameters.getCurve(), localX9ECParameters.getG(), localX9ECParameters.getN(), localX9ECParameters.getH(), null);
        java.security.spec.ECParameterSpec localECParameterSpec1 = (java.security.spec.ECParameterSpec)this.ecParams;
        ECCurve localECCurve1 = EC5Util.convertCurve(localECParameterSpec1.getCurve());
        this.param = new ECKeyGenerationParameters(new ECDomainParameters(localECCurve1, EC5Util.convertPoint(localECCurve1, localECParameterSpec1.getGenerator(), false), localECParameterSpec1.getOrder(), BigInteger.valueOf(localECParameterSpec1.getCofactor())), paramSecureRandom);
        this.engine.init(this.param);
        this.initialised = true;
        return;
      }
      if ((paramAlgorithmParameterSpec == null) && (this.configuration.getEcImplicitlyCa() != null))
      {
        org.spongycastle.jce.spec.ECParameterSpec localECParameterSpec2 = this.configuration.getEcImplicitlyCa();
        this.ecParams = paramAlgorithmParameterSpec;
        this.param = new ECKeyGenerationParameters(new ECDomainParameters(localECParameterSpec2.getCurve(), localECParameterSpec2.getG(), localECParameterSpec2.getN()), paramSecureRandom);
        this.engine.init(this.param);
        this.initialised = true;
        return;
      }
      if ((paramAlgorithmParameterSpec == null) && (this.configuration.getEcImplicitlyCa() == null)) {
        throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
      }
      throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec");
    }
  }
  
  public static class ECDH
    extends KeyPairGeneratorSpi.EC
  {
    public ECDH()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECDHC
    extends KeyPairGeneratorSpi.EC
  {
    public ECDHC()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECDSA
    extends KeyPairGeneratorSpi.EC
  {
    public ECDSA()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECMQV
    extends KeyPairGeneratorSpi.EC
  {
    public ECMQV()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi
 * JD-Core Version:    0.7.0.1
 */