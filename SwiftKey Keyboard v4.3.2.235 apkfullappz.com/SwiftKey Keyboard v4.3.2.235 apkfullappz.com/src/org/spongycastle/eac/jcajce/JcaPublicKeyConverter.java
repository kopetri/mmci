package org.spongycastle.eac.jcajce;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.eac.EACObjectIdentifiers;
import org.spongycastle.asn1.eac.ECDSAPublicKey;
import org.spongycastle.asn1.eac.PublicKeyDataObject;
import org.spongycastle.eac.EACException;
import org.spongycastle.jce.spec.ECPublicKeySpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.Fp;

public class JcaPublicKeyConverter
{
  private EACHelper helper = new DefaultEACHelper();
  
  private static ECCurve convertCurve(EllipticCurve paramEllipticCurve)
  {
    ECField localECField = paramEllipticCurve.getField();
    BigInteger localBigInteger1 = paramEllipticCurve.getA();
    BigInteger localBigInteger2 = paramEllipticCurve.getB();
    if ((localECField instanceof ECFieldFp)) {
      return new ECCurve.Fp(((ECFieldFp)localECField).getP(), localBigInteger1, localBigInteger2);
    }
    throw new IllegalStateException("not implemented yet!!!");
  }
  
  private static org.spongycastle.math.ec.ECPoint convertPoint(ECCurve paramECCurve, java.security.spec.ECPoint paramECPoint, boolean paramBoolean)
  {
    return paramECCurve.createPoint(paramECPoint.getAffineX(), paramECPoint.getAffineY(), paramBoolean);
  }
  
  private PublicKey getECPublicKeyPublicKey(ECDSAPublicKey paramECDSAPublicKey)
    throws EACException, InvalidKeySpecException
  {
    org.spongycastle.jce.spec.ECParameterSpec localECParameterSpec = getParams(paramECDSAPublicKey);
    ECPublicKeySpec localECPublicKeySpec = new ECPublicKeySpec(localECParameterSpec.getCurve().decodePoint(paramECDSAPublicKey.getPublicPointY()), localECParameterSpec);
    try
    {
      KeyFactory localKeyFactory = this.helper.createKeyFactory("ECDSA");
      return localKeyFactory.generatePublic(localECPublicKeySpec);
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new EACException("cannot find provider: " + localNoSuchProviderException.getMessage(), localNoSuchProviderException);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new EACException("cannot find algorithm ECDSA: " + localNoSuchAlgorithmException.getMessage(), localNoSuchAlgorithmException);
    }
  }
  
  private org.spongycastle.jce.spec.ECParameterSpec getParams(ECDSAPublicKey paramECDSAPublicKey)
  {
    if (!paramECDSAPublicKey.hasParameters()) {
      throw new IllegalArgumentException("Public key does not contains EC Params");
    }
    ECCurve.Fp localFp = new ECCurve.Fp(paramECDSAPublicKey.getPrimeModulusP(), paramECDSAPublicKey.getFirstCoefA(), paramECDSAPublicKey.getSecondCoefB());
    return new org.spongycastle.jce.spec.ECParameterSpec(localFp, localFp.decodePoint(paramECDSAPublicKey.getBasePointG()), paramECDSAPublicKey.getOrderOfBasePointR(), paramECDSAPublicKey.getCofactorF());
  }
  
  public PublicKey getKey(PublicKeyDataObject paramPublicKeyDataObject)
    throws EACException, InvalidKeySpecException
  {
    if (paramPublicKeyDataObject.getUsage().on(EACObjectIdentifiers.id_TA_ECDSA)) {
      return getECPublicKeyPublicKey((ECDSAPublicKey)paramPublicKeyDataObject);
    }
    org.spongycastle.asn1.eac.RSAPublicKey localRSAPublicKey = (org.spongycastle.asn1.eac.RSAPublicKey)paramPublicKeyDataObject;
    RSAPublicKeySpec localRSAPublicKeySpec = new RSAPublicKeySpec(localRSAPublicKey.getModulus(), localRSAPublicKey.getPublicExponent());
    try
    {
      PublicKey localPublicKey = this.helper.createKeyFactory("RSA").generatePublic(localRSAPublicKeySpec);
      return localPublicKey;
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new EACException("cannot find provider: " + localNoSuchProviderException.getMessage(), localNoSuchProviderException);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new EACException("cannot find algorithm ECDSA: " + localNoSuchAlgorithmException.getMessage(), localNoSuchAlgorithmException);
    }
  }
  
  public PublicKeyDataObject getPublicKeyDataObject(ASN1ObjectIdentifier paramASN1ObjectIdentifier, PublicKey paramPublicKey)
  {
    if ((paramPublicKey instanceof java.security.interfaces.RSAPublicKey))
    {
      java.security.interfaces.RSAPublicKey localRSAPublicKey = (java.security.interfaces.RSAPublicKey)paramPublicKey;
      return new org.spongycastle.asn1.eac.RSAPublicKey(paramASN1ObjectIdentifier, localRSAPublicKey.getModulus(), localRSAPublicKey.getPublicExponent());
    }
    ECPublicKey localECPublicKey = (ECPublicKey)paramPublicKey;
    java.security.spec.ECParameterSpec localECParameterSpec = localECPublicKey.getParams();
    return new ECDSAPublicKey(paramASN1ObjectIdentifier, ((ECFieldFp)localECParameterSpec.getCurve().getField()).getP(), localECParameterSpec.getCurve().getA(), localECParameterSpec.getCurve().getB(), convertPoint(convertCurve(localECParameterSpec.getCurve()), localECParameterSpec.getGenerator(), false).getEncoded(), localECParameterSpec.getOrder(), convertPoint(convertCurve(localECParameterSpec.getCurve()), localECPublicKey.getW(), false).getEncoded(), localECParameterSpec.getCofactor());
  }
  
  public JcaPublicKeyConverter setProvider(String paramString)
  {
    this.helper = new NamedEACHelper(paramString);
    return this;
  }
  
  public JcaPublicKeyConverter setProvider(Provider paramProvider)
  {
    this.helper = new ProviderEACHelper(paramProvider);
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.eac.jcajce.JcaPublicKeyConverter
 * JD-Core Version:    0.7.0.1
 */