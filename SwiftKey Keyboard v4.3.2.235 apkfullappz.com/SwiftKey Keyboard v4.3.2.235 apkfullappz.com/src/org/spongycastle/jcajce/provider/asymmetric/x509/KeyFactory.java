package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class KeyFactory
  extends KeyFactorySpi
{
  protected PrivateKey engineGeneratePrivate(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof PKCS8EncodedKeySpec)) {
      try
      {
        PrivateKeyInfo localPrivateKeyInfo = PrivateKeyInfo.getInstance(((PKCS8EncodedKeySpec)paramKeySpec).getEncoded());
        PrivateKey localPrivateKey = BouncyCastleProvider.getPrivateKey(localPrivateKeyInfo);
        if (localPrivateKey != null) {
          return localPrivateKey;
        }
        throw new InvalidKeySpecException("no factory found for OID: " + localPrivateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm());
      }
      catch (Exception localException)
      {
        throw new InvalidKeySpecException(localException.toString());
      }
    }
    throw new InvalidKeySpecException("Unknown KeySpec type: " + paramKeySpec.getClass().getName());
  }
  
  protected PublicKey engineGeneratePublic(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof X509EncodedKeySpec)) {
      try
      {
        SubjectPublicKeyInfo localSubjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(((X509EncodedKeySpec)paramKeySpec).getEncoded());
        PublicKey localPublicKey = BouncyCastleProvider.getPublicKey(localSubjectPublicKeyInfo);
        if (localPublicKey != null) {
          return localPublicKey;
        }
        throw new InvalidKeySpecException("no factory found for OID: " + localSubjectPublicKeyInfo.getAlgorithm().getAlgorithm());
      }
      catch (Exception localException)
      {
        throw new InvalidKeySpecException(localException.toString());
      }
    }
    throw new InvalidKeySpecException("Unknown KeySpec type: " + paramKeySpec.getClass().getName());
  }
  
  protected KeySpec engineGetKeySpec(Key paramKey, Class paramClass)
    throws InvalidKeySpecException
  {
    if ((paramClass.isAssignableFrom(PKCS8EncodedKeySpec.class)) && (paramKey.getFormat().equals("PKCS#8"))) {
      return new PKCS8EncodedKeySpec(paramKey.getEncoded());
    }
    if ((paramClass.isAssignableFrom(X509EncodedKeySpec.class)) && (paramKey.getFormat().equals("X.509"))) {
      return new X509EncodedKeySpec(paramKey.getEncoded());
    }
    throw new InvalidKeySpecException("not implemented yet " + paramKey + " " + paramClass);
  }
  
  protected Key engineTranslateKey(Key paramKey)
    throws InvalidKeyException
  {
    throw new InvalidKeyException("not implemented yet " + paramKey);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.x509.KeyFactory
 * JD-Core Version:    0.7.0.1
 */