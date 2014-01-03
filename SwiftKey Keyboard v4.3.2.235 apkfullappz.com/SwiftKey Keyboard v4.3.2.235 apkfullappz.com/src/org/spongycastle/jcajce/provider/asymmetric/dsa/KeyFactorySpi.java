package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;

public class KeyFactorySpi
  extends BaseKeyFactorySpi
{
  protected PrivateKey engineGeneratePrivate(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof DSAPrivateKeySpec)) {
      return new BCDSAPrivateKey((DSAPrivateKeySpec)paramKeySpec);
    }
    return super.engineGeneratePrivate(paramKeySpec);
  }
  
  protected PublicKey engineGeneratePublic(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof DSAPublicKeySpec)) {
      return new BCDSAPublicKey((DSAPublicKeySpec)paramKeySpec);
    }
    return super.engineGeneratePublic(paramKeySpec);
  }
  
  protected KeySpec engineGetKeySpec(Key paramKey, Class paramClass)
    throws InvalidKeySpecException
  {
    if ((paramClass.isAssignableFrom(DSAPublicKeySpec.class)) && ((paramKey instanceof DSAPublicKey)))
    {
      DSAPublicKey localDSAPublicKey = (DSAPublicKey)paramKey;
      return new DSAPublicKeySpec(localDSAPublicKey.getY(), localDSAPublicKey.getParams().getP(), localDSAPublicKey.getParams().getQ(), localDSAPublicKey.getParams().getG());
    }
    if ((paramClass.isAssignableFrom(DSAPrivateKeySpec.class)) && ((paramKey instanceof DSAPrivateKey)))
    {
      DSAPrivateKey localDSAPrivateKey = (DSAPrivateKey)paramKey;
      return new DSAPrivateKeySpec(localDSAPrivateKey.getX(), localDSAPrivateKey.getParams().getP(), localDSAPrivateKey.getParams().getQ(), localDSAPrivateKey.getParams().getG());
    }
    return super.engineGetKeySpec(paramKey, paramClass);
  }
  
  protected Key engineTranslateKey(Key paramKey)
    throws InvalidKeyException
  {
    if ((paramKey instanceof DSAPublicKey)) {
      return new BCDSAPublicKey((DSAPublicKey)paramKey);
    }
    if ((paramKey instanceof DSAPrivateKey)) {
      return new BCDSAPrivateKey((DSAPrivateKey)paramKey);
    }
    throw new InvalidKeyException("key type unknown");
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramPrivateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
    if (DSAUtil.isDsaOid(localASN1ObjectIdentifier)) {
      return new BCDSAPrivateKey(paramPrivateKeyInfo);
    }
    throw new IOException("algorithm identifier " + localASN1ObjectIdentifier + " in key not recognised");
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws IOException
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramSubjectPublicKeyInfo.getAlgorithm().getAlgorithm();
    if (DSAUtil.isDsaOid(localASN1ObjectIdentifier)) {
      return new BCDSAPublicKey(paramSubjectPublicKeyInfo);
    }
    throw new IOException("algorithm identifier " + localASN1ObjectIdentifier + " in key not recognised");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi
 * JD-Core Version:    0.7.0.1
 */