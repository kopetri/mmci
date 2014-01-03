package org.spongycastle.jce.provider;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;

public class DHUtil
{
  public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey paramPrivateKey)
    throws InvalidKeyException
  {
    if ((paramPrivateKey instanceof DHPrivateKey))
    {
      DHPrivateKey localDHPrivateKey = (DHPrivateKey)paramPrivateKey;
      return new DHPrivateKeyParameters(localDHPrivateKey.getX(), new DHParameters(localDHPrivateKey.getParams().getP(), localDHPrivateKey.getParams().getG(), null, localDHPrivateKey.getParams().getL()));
    }
    throw new InvalidKeyException("can't identify DH private key.");
  }
  
  public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    if ((paramPublicKey instanceof DHPublicKey))
    {
      DHPublicKey localDHPublicKey = (DHPublicKey)paramPublicKey;
      return new DHPublicKeyParameters(localDHPublicKey.getY(), new DHParameters(localDHPublicKey.getParams().getP(), localDHPublicKey.getParams().getG(), null, localDHPublicKey.getParams().getL()));
    }
    throw new InvalidKeyException("can't identify DH public key.");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.DHUtil
 * JD-Core Version:    0.7.0.1
 */