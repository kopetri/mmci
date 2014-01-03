package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.spongycastle.jcajce.provider.asymmetric.util.ExtendedInvalidKeySpecException;

public class KeyFactorySpi
  extends BaseKeyFactorySpi
{
  protected PrivateKey engineGeneratePrivate(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof PKCS8EncodedKeySpec)) {
      try
      {
        PrivateKey localPrivateKey = generatePrivate(PrivateKeyInfo.getInstance(((PKCS8EncodedKeySpec)paramKeySpec).getEncoded()));
        return localPrivateKey;
      }
      catch (Exception localException1)
      {
        try
        {
          BCRSAPrivateCrtKey localBCRSAPrivateCrtKey = new BCRSAPrivateCrtKey(org.spongycastle.asn1.pkcs.RSAPrivateKey.getInstance(((PKCS8EncodedKeySpec)paramKeySpec).getEncoded()));
          return localBCRSAPrivateCrtKey;
        }
        catch (Exception localException2)
        {
          throw new ExtendedInvalidKeySpecException("unable to process key spec: " + localException1.toString(), localException1);
        }
      }
    }
    if ((paramKeySpec instanceof RSAPrivateCrtKeySpec)) {
      return new BCRSAPrivateCrtKey((RSAPrivateCrtKeySpec)paramKeySpec);
    }
    if ((paramKeySpec instanceof RSAPrivateKeySpec)) {
      return new BCRSAPrivateKey((RSAPrivateKeySpec)paramKeySpec);
    }
    throw new InvalidKeySpecException("Unknown KeySpec type: " + paramKeySpec.getClass().getName());
  }
  
  protected PublicKey engineGeneratePublic(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof RSAPublicKeySpec)) {
      return new BCRSAPublicKey((RSAPublicKeySpec)paramKeySpec);
    }
    return super.engineGeneratePublic(paramKeySpec);
  }
  
  protected KeySpec engineGetKeySpec(Key paramKey, Class paramClass)
    throws InvalidKeySpecException
  {
    if ((paramClass.isAssignableFrom(RSAPublicKeySpec.class)) && ((paramKey instanceof RSAPublicKey)))
    {
      RSAPublicKey localRSAPublicKey = (RSAPublicKey)paramKey;
      return new RSAPublicKeySpec(localRSAPublicKey.getModulus(), localRSAPublicKey.getPublicExponent());
    }
    if ((paramClass.isAssignableFrom(RSAPrivateKeySpec.class)) && ((paramKey instanceof java.security.interfaces.RSAPrivateKey)))
    {
      java.security.interfaces.RSAPrivateKey localRSAPrivateKey = (java.security.interfaces.RSAPrivateKey)paramKey;
      return new RSAPrivateKeySpec(localRSAPrivateKey.getModulus(), localRSAPrivateKey.getPrivateExponent());
    }
    if ((paramClass.isAssignableFrom(RSAPrivateCrtKeySpec.class)) && ((paramKey instanceof RSAPrivateCrtKey)))
    {
      RSAPrivateCrtKey localRSAPrivateCrtKey = (RSAPrivateCrtKey)paramKey;
      return new RSAPrivateCrtKeySpec(localRSAPrivateCrtKey.getModulus(), localRSAPrivateCrtKey.getPublicExponent(), localRSAPrivateCrtKey.getPrivateExponent(), localRSAPrivateCrtKey.getPrimeP(), localRSAPrivateCrtKey.getPrimeQ(), localRSAPrivateCrtKey.getPrimeExponentP(), localRSAPrivateCrtKey.getPrimeExponentQ(), localRSAPrivateCrtKey.getCrtCoefficient());
    }
    return super.engineGetKeySpec(paramKey, paramClass);
  }
  
  protected Key engineTranslateKey(Key paramKey)
    throws InvalidKeyException
  {
    if ((paramKey instanceof RSAPublicKey)) {
      return new BCRSAPublicKey((RSAPublicKey)paramKey);
    }
    if ((paramKey instanceof RSAPrivateCrtKey)) {
      return new BCRSAPrivateCrtKey((RSAPrivateCrtKey)paramKey);
    }
    if ((paramKey instanceof java.security.interfaces.RSAPrivateKey)) {
      return new BCRSAPrivateKey((java.security.interfaces.RSAPrivateKey)paramKey);
    }
    throw new InvalidKeyException("key type unknown");
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramPrivateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
    if (RSAUtil.isRsaOid(localASN1ObjectIdentifier)) {
      return new BCRSAPrivateCrtKey(paramPrivateKeyInfo);
    }
    throw new IOException("algorithm identifier " + localASN1ObjectIdentifier + " in key not recognised");
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws IOException
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramSubjectPublicKeyInfo.getAlgorithm().getAlgorithm();
    if (RSAUtil.isRsaOid(localASN1ObjectIdentifier)) {
      return new BCRSAPublicKey(paramSubjectPublicKeyInfo);
    }
    throw new IOException("algorithm identifier " + localASN1ObjectIdentifier + " in key not recognised");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi
 * JD-Core Version:    0.7.0.1
 */