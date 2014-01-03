package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.spongycastle.jce.interfaces.GOST3410Params;
import org.spongycastle.jce.interfaces.GOST3410PrivateKey;
import org.spongycastle.jce.interfaces.GOST3410PublicKey;
import org.spongycastle.jce.spec.GOST3410PrivateKeySpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeySpec;

public class KeyFactorySpi
  extends BaseKeyFactorySpi
{
  protected PrivateKey engineGeneratePrivate(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof GOST3410PrivateKeySpec)) {
      return new BCGOST3410PrivateKey((GOST3410PrivateKeySpec)paramKeySpec);
    }
    return super.engineGeneratePrivate(paramKeySpec);
  }
  
  protected PublicKey engineGeneratePublic(KeySpec paramKeySpec)
    throws InvalidKeySpecException
  {
    if ((paramKeySpec instanceof GOST3410PublicKeySpec)) {
      return new BCGOST3410PublicKey((GOST3410PublicKeySpec)paramKeySpec);
    }
    return super.engineGeneratePublic(paramKeySpec);
  }
  
  protected KeySpec engineGetKeySpec(Key paramKey, Class paramClass)
    throws InvalidKeySpecException
  {
    if ((paramClass.isAssignableFrom(GOST3410PublicKeySpec.class)) && ((paramKey instanceof GOST3410PublicKey)))
    {
      GOST3410PublicKey localGOST3410PublicKey = (GOST3410PublicKey)paramKey;
      GOST3410PublicKeyParameterSetSpec localGOST3410PublicKeyParameterSetSpec2 = localGOST3410PublicKey.getParameters().getPublicKeyParameters();
      return new GOST3410PublicKeySpec(localGOST3410PublicKey.getY(), localGOST3410PublicKeyParameterSetSpec2.getP(), localGOST3410PublicKeyParameterSetSpec2.getQ(), localGOST3410PublicKeyParameterSetSpec2.getA());
    }
    if ((paramClass.isAssignableFrom(GOST3410PrivateKeySpec.class)) && ((paramKey instanceof GOST3410PrivateKey)))
    {
      GOST3410PrivateKey localGOST3410PrivateKey = (GOST3410PrivateKey)paramKey;
      GOST3410PublicKeyParameterSetSpec localGOST3410PublicKeyParameterSetSpec1 = localGOST3410PrivateKey.getParameters().getPublicKeyParameters();
      return new GOST3410PrivateKeySpec(localGOST3410PrivateKey.getX(), localGOST3410PublicKeyParameterSetSpec1.getP(), localGOST3410PublicKeyParameterSetSpec1.getQ(), localGOST3410PublicKeyParameterSetSpec1.getA());
    }
    return super.engineGetKeySpec(paramKey, paramClass);
  }
  
  protected Key engineTranslateKey(Key paramKey)
    throws InvalidKeyException
  {
    if ((paramKey instanceof GOST3410PublicKey)) {
      return new BCGOST3410PublicKey((GOST3410PublicKey)paramKey);
    }
    if ((paramKey instanceof GOST3410PrivateKey)) {
      return new BCGOST3410PrivateKey((GOST3410PrivateKey)paramKey);
    }
    throw new InvalidKeyException("key type unknown");
  }
  
  public PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramPrivateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
    if (localASN1ObjectIdentifier.equals(CryptoProObjectIdentifiers.gostR3410_94)) {
      return new BCGOST3410PrivateKey(paramPrivateKeyInfo);
    }
    throw new IOException("algorithm identifier " + localASN1ObjectIdentifier + " in key not recognised");
  }
  
  public PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws IOException
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramSubjectPublicKeyInfo.getAlgorithm().getAlgorithm();
    if (localASN1ObjectIdentifier.equals(CryptoProObjectIdentifiers.gostR3410_94)) {
      return new BCGOST3410PublicKey(paramSubjectPublicKeyInfo);
    }
    throw new IOException("algorithm identifier " + localASN1ObjectIdentifier + " in key not recognised");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.gost.KeyFactorySpi
 * JD-Core Version:    0.7.0.1
 */