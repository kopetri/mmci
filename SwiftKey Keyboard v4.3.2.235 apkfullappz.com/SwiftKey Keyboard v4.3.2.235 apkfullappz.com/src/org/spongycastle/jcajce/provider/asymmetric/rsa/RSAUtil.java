package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSAUtil
{
  public static final ASN1ObjectIdentifier[] rsaOids;
  
  static
  {
    ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier = new ASN1ObjectIdentifier[4];
    arrayOfASN1ObjectIdentifier[0] = PKCSObjectIdentifiers.rsaEncryption;
    arrayOfASN1ObjectIdentifier[1] = X509ObjectIdentifiers.id_ea_rsa;
    arrayOfASN1ObjectIdentifier[2] = PKCSObjectIdentifiers.id_RSAES_OAEP;
    arrayOfASN1ObjectIdentifier[3] = PKCSObjectIdentifiers.id_RSASSA_PSS;
    rsaOids = arrayOfASN1ObjectIdentifier;
  }
  
  static RSAKeyParameters generatePrivateKeyParameter(RSAPrivateKey paramRSAPrivateKey)
  {
    if ((paramRSAPrivateKey instanceof RSAPrivateCrtKey))
    {
      RSAPrivateCrtKey localRSAPrivateCrtKey = (RSAPrivateCrtKey)paramRSAPrivateKey;
      return new RSAPrivateCrtKeyParameters(localRSAPrivateCrtKey.getModulus(), localRSAPrivateCrtKey.getPublicExponent(), localRSAPrivateCrtKey.getPrivateExponent(), localRSAPrivateCrtKey.getPrimeP(), localRSAPrivateCrtKey.getPrimeQ(), localRSAPrivateCrtKey.getPrimeExponentP(), localRSAPrivateCrtKey.getPrimeExponentQ(), localRSAPrivateCrtKey.getCrtCoefficient());
    }
    return new RSAKeyParameters(true, paramRSAPrivateKey.getModulus(), paramRSAPrivateKey.getPrivateExponent());
  }
  
  static RSAKeyParameters generatePublicKeyParameter(RSAPublicKey paramRSAPublicKey)
  {
    return new RSAKeyParameters(false, paramRSAPublicKey.getModulus(), paramRSAPublicKey.getPublicExponent());
  }
  
  public static boolean isRsaOid(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    for (int i = 0; i != rsaOids.length; i++) {
      if (paramASN1ObjectIdentifier.equals(rsaOids[i])) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.rsa.RSAUtil
 * JD-Core Version:    0.7.0.1
 */