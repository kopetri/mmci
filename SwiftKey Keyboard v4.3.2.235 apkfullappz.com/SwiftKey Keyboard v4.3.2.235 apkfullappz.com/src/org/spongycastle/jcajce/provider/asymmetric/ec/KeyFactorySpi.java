package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECParameterSpec;

public class KeyFactorySpi
  extends BaseKeyFactorySpi
  implements AsymmetricKeyInfoConverter
{
  String algorithm;
  ProviderConfiguration configuration;
  
  KeyFactorySpi(String paramString, ProviderConfiguration paramProviderConfiguration)
  {
    this.algorithm = paramString;
    this.configuration = paramProviderConfiguration;
  }
  
  protected PrivateKey engineGeneratePrivate(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof org.spongycastle.jce.spec.ECPrivateKeySpec)) {
      return new BCECPrivateKey(this.algorithm, (org.spongycastle.jce.spec.ECPrivateKeySpec)paramKeySpec, this.configuration);
    }
    if ((paramKeySpec instanceof java.security.spec.ECPrivateKeySpec)) {
      return new BCECPrivateKey(this.algorithm, (java.security.spec.ECPrivateKeySpec)paramKeySpec, this.configuration);
    }
    return super.engineGeneratePrivate(paramKeySpec);
  }
  
  protected PublicKey engineGeneratePublic(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof org.spongycastle.jce.spec.ECPublicKeySpec)) {
      return new BCECPublicKey(this.algorithm, (org.spongycastle.jce.spec.ECPublicKeySpec)paramKeySpec, this.configuration);
    }
    if ((paramKeySpec instanceof java.security.spec.ECPublicKeySpec)) {
      return new BCECPublicKey(this.algorithm, (java.security.spec.ECPublicKeySpec)paramKeySpec, this.configuration);
    }
    return super.engineGeneratePublic(paramKeySpec);
  }
  
  protected KeySpec engineGetKeySpec(Key paramKey, Class paramClass)
    throws InvalidKeySpecException
  {
    if ((paramClass.isAssignableFrom(java.security.spec.ECPublicKeySpec.class)) && ((paramKey instanceof ECPublicKey)))
    {
      ECPublicKey localECPublicKey2 = (ECPublicKey)paramKey;
      if (localECPublicKey2.getParams() != null) {
        return new java.security.spec.ECPublicKeySpec(localECPublicKey2.getW(), localECPublicKey2.getParams());
      }
      ECParameterSpec localECParameterSpec4 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
      return new java.security.spec.ECPublicKeySpec(localECPublicKey2.getW(), EC5Util.convertSpec(EC5Util.convertCurve(localECParameterSpec4.getCurve(), localECParameterSpec4.getSeed()), localECParameterSpec4));
    }
    if ((paramClass.isAssignableFrom(java.security.spec.ECPrivateKeySpec.class)) && ((paramKey instanceof ECPrivateKey)))
    {
      ECPrivateKey localECPrivateKey2 = (ECPrivateKey)paramKey;
      if (localECPrivateKey2.getParams() != null) {
        return new java.security.spec.ECPrivateKeySpec(localECPrivateKey2.getS(), localECPrivateKey2.getParams());
      }
      ECParameterSpec localECParameterSpec3 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
      return new java.security.spec.ECPrivateKeySpec(localECPrivateKey2.getS(), EC5Util.convertSpec(EC5Util.convertCurve(localECParameterSpec3.getCurve(), localECParameterSpec3.getSeed()), localECParameterSpec3));
    }
    if ((paramClass.isAssignableFrom(org.spongycastle.jce.spec.ECPublicKeySpec.class)) && ((paramKey instanceof ECPublicKey)))
    {
      ECPublicKey localECPublicKey1 = (ECPublicKey)paramKey;
      if (localECPublicKey1.getParams() != null) {
        return new org.spongycastle.jce.spec.ECPublicKeySpec(EC5Util.convertPoint(localECPublicKey1.getParams(), localECPublicKey1.getW(), false), EC5Util.convertSpec(localECPublicKey1.getParams(), false));
      }
      ECParameterSpec localECParameterSpec2 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
      return new org.spongycastle.jce.spec.ECPublicKeySpec(EC5Util.convertPoint(localECPublicKey1.getParams(), localECPublicKey1.getW(), false), localECParameterSpec2);
    }
    if ((paramClass.isAssignableFrom(org.spongycastle.jce.spec.ECPrivateKeySpec.class)) && ((paramKey instanceof ECPrivateKey)))
    {
      ECPrivateKey localECPrivateKey1 = (ECPrivateKey)paramKey;
      if (localECPrivateKey1.getParams() != null) {
        return new org.spongycastle.jce.spec.ECPrivateKeySpec(localECPrivateKey1.getS(), EC5Util.convertSpec(localECPrivateKey1.getParams(), false));
      }
      ECParameterSpec localECParameterSpec1 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
      return new org.spongycastle.jce.spec.ECPrivateKeySpec(localECPrivateKey1.getS(), localECParameterSpec1);
    }
    return super.engineGetKeySpec(paramKey, paramClass);
  }
  
  protected Key engineTranslateKey(Key paramKey)
    throws InvalidKeyException
  {
    if ((paramKey instanceof ECPublicKey)) {
      return new BCECPublicKey((ECPublicKey)paramKey, this.configuration);
    }
    if ((paramKey instanceof ECPrivateKey)) {
      return new BCECPrivateKey((ECPrivateKey)paramKey, this.configuration);
    }
    throw new InvalidKeyException("key type unknown");
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramPrivateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
    if (localASN1ObjectIdentifier.equals(X9ObjectIdentifiers.id_ecPublicKey)) {
      return new BCECPrivateKey(this.algorithm, paramPrivateKeyInfo, this.configuration);
    }
    throw new IOException("algorithm identifier " + localASN1ObjectIdentifier + " in key not recognised");
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws IOException
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramSubjectPublicKeyInfo.getAlgorithm().getAlgorithm();
    if (localASN1ObjectIdentifier.equals(X9ObjectIdentifiers.id_ecPublicKey)) {
      return new BCECPublicKey(this.algorithm, paramSubjectPublicKeyInfo, this.configuration);
    }
    throw new IOException("algorithm identifier " + localASN1ObjectIdentifier + " in key not recognised");
  }
  
  public static class EC
    extends KeyFactorySpi
  {
    public EC()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECDH
    extends KeyFactorySpi
  {
    public ECDH()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECDHC
    extends KeyFactorySpi
  {
    public ECDHC()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECDSA
    extends KeyFactorySpi
  {
    public ECDSA()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECGOST3410
    extends KeyFactorySpi
  {
    public ECGOST3410()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECMQV
    extends KeyFactorySpi
  {
    public ECMQV()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi
 * JD-Core Version:    0.7.0.1
 */