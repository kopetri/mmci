package org.spongycastle.jce.provider;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.jce.interfaces.ElGamalPrivateKey;
import org.spongycastle.jce.interfaces.ElGamalPublicKey;
import org.spongycastle.jce.spec.ElGamalParameterSpec;

public class ElGamalUtil
{
  public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey paramPrivateKey)
    throws InvalidKeyException
  {
    if ((paramPrivateKey instanceof ElGamalPrivateKey))
    {
      ElGamalPrivateKey localElGamalPrivateKey = (ElGamalPrivateKey)paramPrivateKey;
      return new ElGamalPrivateKeyParameters(localElGamalPrivateKey.getX(), new ElGamalParameters(localElGamalPrivateKey.getParameters().getP(), localElGamalPrivateKey.getParameters().getG()));
    }
    if ((paramPrivateKey instanceof DHPrivateKey))
    {
      DHPrivateKey localDHPrivateKey = (DHPrivateKey)paramPrivateKey;
      return new ElGamalPrivateKeyParameters(localDHPrivateKey.getX(), new ElGamalParameters(localDHPrivateKey.getParams().getP(), localDHPrivateKey.getParams().getG()));
    }
    throw new InvalidKeyException("can't identify private key for El Gamal.");
  }
  
  public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    if ((paramPublicKey instanceof ElGamalPublicKey))
    {
      ElGamalPublicKey localElGamalPublicKey = (ElGamalPublicKey)paramPublicKey;
      return new ElGamalPublicKeyParameters(localElGamalPublicKey.getY(), new ElGamalParameters(localElGamalPublicKey.getParameters().getP(), localElGamalPublicKey.getParameters().getG()));
    }
    if ((paramPublicKey instanceof DHPublicKey))
    {
      DHPublicKey localDHPublicKey = (DHPublicKey)paramPublicKey;
      return new ElGamalPublicKeyParameters(localDHPublicKey.getY(), new ElGamalParameters(localDHPublicKey.getParams().getP(), localDHPublicKey.getParams().getG()));
    }
    throw new InvalidKeyException("can't identify public key for El Gamal.");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.ElGamalUtil
 * JD-Core Version:    0.7.0.1
 */