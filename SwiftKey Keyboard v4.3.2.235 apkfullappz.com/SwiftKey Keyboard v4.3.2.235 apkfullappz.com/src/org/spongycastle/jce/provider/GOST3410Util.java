package org.spongycastle.jce.provider;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;
import org.spongycastle.jce.interfaces.GOST3410Params;
import org.spongycastle.jce.interfaces.GOST3410PrivateKey;
import org.spongycastle.jce.interfaces.GOST3410PublicKey;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class GOST3410Util
{
  public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey paramPrivateKey)
    throws InvalidKeyException
  {
    if ((paramPrivateKey instanceof GOST3410PrivateKey))
    {
      GOST3410PrivateKey localGOST3410PrivateKey = (GOST3410PrivateKey)paramPrivateKey;
      GOST3410PublicKeyParameterSetSpec localGOST3410PublicKeyParameterSetSpec = localGOST3410PrivateKey.getParameters().getPublicKeyParameters();
      return new GOST3410PrivateKeyParameters(localGOST3410PrivateKey.getX(), new GOST3410Parameters(localGOST3410PublicKeyParameterSetSpec.getP(), localGOST3410PublicKeyParameterSetSpec.getQ(), localGOST3410PublicKeyParameterSetSpec.getA()));
    }
    throw new InvalidKeyException("can't identify GOST3410 private key.");
  }
  
  public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    if ((paramPublicKey instanceof GOST3410PublicKey))
    {
      GOST3410PublicKey localGOST3410PublicKey = (GOST3410PublicKey)paramPublicKey;
      GOST3410PublicKeyParameterSetSpec localGOST3410PublicKeyParameterSetSpec = localGOST3410PublicKey.getParameters().getPublicKeyParameters();
      return new GOST3410PublicKeyParameters(localGOST3410PublicKey.getY(), new GOST3410Parameters(localGOST3410PublicKeyParameterSetSpec.getP(), localGOST3410PublicKeyParameterSetSpec.getQ(), localGOST3410PublicKeyParameterSetSpec.getA()));
    }
    throw new InvalidKeyException("can't identify GOST3410 public key: " + paramPublicKey.getClass().getName());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.GOST3410Util
 * JD-Core Version:    0.7.0.1
 */