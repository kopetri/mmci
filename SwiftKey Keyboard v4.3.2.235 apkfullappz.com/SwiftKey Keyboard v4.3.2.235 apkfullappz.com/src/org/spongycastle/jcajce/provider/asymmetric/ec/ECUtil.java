package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.nist.NISTNamedCurves;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.spongycastle.asn1.x9.X962NamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.interfaces.ECPrivateKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECParameterSpec;

public class ECUtil
{
  static int[] convertMidTerms(int[] paramArrayOfInt)
  {
    int[] arrayOfInt = new int[3];
    if (paramArrayOfInt.length == 1)
    {
      arrayOfInt[0] = paramArrayOfInt[0];
      return arrayOfInt;
    }
    if (paramArrayOfInt.length != 3) {
      throw new IllegalArgumentException("Only Trinomials and pentanomials supported");
    }
    if ((paramArrayOfInt[0] < paramArrayOfInt[1]) && (paramArrayOfInt[0] < paramArrayOfInt[2]))
    {
      arrayOfInt[0] = paramArrayOfInt[0];
      if (paramArrayOfInt[1] < paramArrayOfInt[2])
      {
        arrayOfInt[1] = paramArrayOfInt[1];
        arrayOfInt[2] = paramArrayOfInt[2];
        return arrayOfInt;
      }
      arrayOfInt[1] = paramArrayOfInt[2];
      arrayOfInt[2] = paramArrayOfInt[1];
      return arrayOfInt;
    }
    if (paramArrayOfInt[1] < paramArrayOfInt[2])
    {
      arrayOfInt[0] = paramArrayOfInt[1];
      if (paramArrayOfInt[0] < paramArrayOfInt[2])
      {
        arrayOfInt[1] = paramArrayOfInt[0];
        arrayOfInt[2] = paramArrayOfInt[2];
        return arrayOfInt;
      }
      arrayOfInt[1] = paramArrayOfInt[2];
      arrayOfInt[2] = paramArrayOfInt[0];
      return arrayOfInt;
    }
    arrayOfInt[0] = paramArrayOfInt[2];
    if (paramArrayOfInt[0] < paramArrayOfInt[1])
    {
      arrayOfInt[1] = paramArrayOfInt[0];
      arrayOfInt[2] = paramArrayOfInt[1];
      return arrayOfInt;
    }
    arrayOfInt[1] = paramArrayOfInt[1];
    arrayOfInt[2] = paramArrayOfInt[0];
    return arrayOfInt;
  }
  
  public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey paramPrivateKey)
    throws InvalidKeyException
  {
    if ((paramPrivateKey instanceof ECPrivateKey))
    {
      ECPrivateKey localECPrivateKey = (ECPrivateKey)paramPrivateKey;
      ECParameterSpec localECParameterSpec = localECPrivateKey.getParameters();
      if (localECParameterSpec == null) {
        localECParameterSpec = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
      }
      return new ECPrivateKeyParameters(localECPrivateKey.getD(), new ECDomainParameters(localECParameterSpec.getCurve(), localECParameterSpec.getG(), localECParameterSpec.getN(), localECParameterSpec.getH(), localECParameterSpec.getSeed()));
    }
    throw new InvalidKeyException("can't identify EC private key.");
  }
  
  public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    if ((paramPublicKey instanceof org.spongycastle.jce.interfaces.ECPublicKey))
    {
      org.spongycastle.jce.interfaces.ECPublicKey localECPublicKey1 = (org.spongycastle.jce.interfaces.ECPublicKey)paramPublicKey;
      ECParameterSpec localECParameterSpec2 = localECPublicKey1.getParameters();
      if (localECParameterSpec2 == null)
      {
        ECParameterSpec localECParameterSpec3 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
        return new ECPublicKeyParameters(((BCECPublicKey)localECPublicKey1).engineGetQ(), new ECDomainParameters(localECParameterSpec3.getCurve(), localECParameterSpec3.getG(), localECParameterSpec3.getN(), localECParameterSpec3.getH(), localECParameterSpec3.getSeed()));
      }
      return new ECPublicKeyParameters(localECPublicKey1.getQ(), new ECDomainParameters(localECParameterSpec2.getCurve(), localECParameterSpec2.getG(), localECParameterSpec2.getN(), localECParameterSpec2.getH(), localECParameterSpec2.getSeed()));
    }
    if ((paramPublicKey instanceof java.security.interfaces.ECPublicKey))
    {
      java.security.interfaces.ECPublicKey localECPublicKey = (java.security.interfaces.ECPublicKey)paramPublicKey;
      ECParameterSpec localECParameterSpec1 = EC5Util.convertSpec(localECPublicKey.getParams(), false);
      return new ECPublicKeyParameters(EC5Util.convertPoint(localECPublicKey.getParams(), localECPublicKey.getW(), false), new ECDomainParameters(localECParameterSpec1.getCurve(), localECParameterSpec1.getG(), localECParameterSpec1.getN(), localECParameterSpec1.getH(), localECParameterSpec1.getSeed()));
    }
    throw new InvalidKeyException("cannot identify EC public key.");
  }
  
  public static String getCurveName(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    String str = X962NamedCurves.getName(paramASN1ObjectIdentifier);
    if (str == null)
    {
      str = SECNamedCurves.getName(paramASN1ObjectIdentifier);
      if (str == null) {
        str = NISTNamedCurves.getName(paramASN1ObjectIdentifier);
      }
      if (str == null) {
        str = TeleTrusTNamedCurves.getName(paramASN1ObjectIdentifier);
      }
      if (str == null) {
        str = ECGOST3410NamedCurves.getName(paramASN1ObjectIdentifier);
      }
    }
    return str;
  }
  
  public static X9ECParameters getNamedCurveByOid(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    X9ECParameters localX9ECParameters = X962NamedCurves.getByOID(paramASN1ObjectIdentifier);
    if (localX9ECParameters == null)
    {
      localX9ECParameters = SECNamedCurves.getByOID(paramASN1ObjectIdentifier);
      if (localX9ECParameters == null) {
        localX9ECParameters = NISTNamedCurves.getByOID(paramASN1ObjectIdentifier);
      }
      if (localX9ECParameters == null) {
        localX9ECParameters = TeleTrusTNamedCurves.getByOID(paramASN1ObjectIdentifier);
      }
    }
    return localX9ECParameters;
  }
  
  public static ASN1ObjectIdentifier getNamedCurveOid(String paramString)
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = X962NamedCurves.getOID(paramString);
    if (localASN1ObjectIdentifier == null)
    {
      localASN1ObjectIdentifier = SECNamedCurves.getOID(paramString);
      if (localASN1ObjectIdentifier == null) {
        localASN1ObjectIdentifier = NISTNamedCurves.getOID(paramString);
      }
      if (localASN1ObjectIdentifier == null) {
        localASN1ObjectIdentifier = TeleTrusTNamedCurves.getOID(paramString);
      }
      if (localASN1ObjectIdentifier == null) {
        localASN1ObjectIdentifier = ECGOST3410NamedCurves.getOID(paramString);
      }
    }
    return localASN1ObjectIdentifier;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.ec.ECUtil
 * JD-Core Version:    0.7.0.1
 */