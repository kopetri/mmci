package org.spongycastle.jcajce.provider.asymmetric.util;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public abstract class BaseKeyFactorySpi
  extends KeyFactorySpi
  implements AsymmetricKeyInfoConverter
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
      catch (IOException localIOException)
      {
        throw new InvalidKeySpecException("encoded key spec not recognised");
      }
    }
    throw new InvalidKeySpecException("key spec not recognised");
  }
  
  protected PublicKey engineGeneratePublic(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof X509EncodedKeySpec)) {
      try
      {
        PublicKey localPublicKey = generatePublic(SubjectPublicKeyInfo.getInstance(((X509EncodedKeySpec)paramKeySpec).getEncoded()));
        return localPublicKey;
      }
      catch (IOException localIOException)
      {
        throw new InvalidKeySpecException("encoded key spec not recognised");
      }
    }
    throw new InvalidKeySpecException("key spec not recognised");
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
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi
 * JD-Core Version:    0.7.0.1
 */