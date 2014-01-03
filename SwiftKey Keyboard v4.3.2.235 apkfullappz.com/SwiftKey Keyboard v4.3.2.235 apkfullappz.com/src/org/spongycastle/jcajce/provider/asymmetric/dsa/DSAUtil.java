package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;

public class DSAUtil
{
  public static final ASN1ObjectIdentifier[] dsaOids;
  
  static
  {
    ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier = new ASN1ObjectIdentifier[2];
    arrayOfASN1ObjectIdentifier[0] = X9ObjectIdentifiers.id_dsa;
    arrayOfASN1ObjectIdentifier[1] = OIWObjectIdentifiers.dsaWithSHA1;
    dsaOids = arrayOfASN1ObjectIdentifier;
  }
  
  public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey paramPrivateKey)
    throws InvalidKeyException
  {
    if ((paramPrivateKey instanceof DSAPrivateKey))
    {
      DSAPrivateKey localDSAPrivateKey = (DSAPrivateKey)paramPrivateKey;
      return new DSAPrivateKeyParameters(localDSAPrivateKey.getX(), new DSAParameters(localDSAPrivateKey.getParams().getP(), localDSAPrivateKey.getParams().getQ(), localDSAPrivateKey.getParams().getG()));
    }
    throw new InvalidKeyException("can't identify DSA private key.");
  }
  
  public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    if ((paramPublicKey instanceof DSAPublicKey))
    {
      DSAPublicKey localDSAPublicKey = (DSAPublicKey)paramPublicKey;
      return new DSAPublicKeyParameters(localDSAPublicKey.getY(), new DSAParameters(localDSAPublicKey.getParams().getP(), localDSAPublicKey.getParams().getQ(), localDSAPublicKey.getParams().getG()));
    }
    throw new InvalidKeyException("can't identify DSA public key: " + paramPublicKey.getClass().getName());
  }
  
  public static boolean isDsaOid(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    for (int i = 0; i != dsaOids.length; i++) {
      if (paramASN1ObjectIdentifier.equals(dsaOids[i])) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.dsa.DSAUtil
 * JD-Core Version:    0.7.0.1
 */