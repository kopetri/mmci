package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class MQVPrivateParameters
  implements CipherParameters
{
  private ECPrivateKeyParameters ephemeralPrivateKey;
  private ECPublicKeyParameters ephemeralPublicKey;
  private ECPrivateKeyParameters staticPrivateKey;
  
  public MQVPrivateParameters(ECPrivateKeyParameters paramECPrivateKeyParameters1, ECPrivateKeyParameters paramECPrivateKeyParameters2)
  {
    this(paramECPrivateKeyParameters1, paramECPrivateKeyParameters2, null);
  }
  
  public MQVPrivateParameters(ECPrivateKeyParameters paramECPrivateKeyParameters1, ECPrivateKeyParameters paramECPrivateKeyParameters2, ECPublicKeyParameters paramECPublicKeyParameters)
  {
    this.staticPrivateKey = paramECPrivateKeyParameters1;
    this.ephemeralPrivateKey = paramECPrivateKeyParameters2;
    this.ephemeralPublicKey = paramECPublicKeyParameters;
  }
  
  public ECPrivateKeyParameters getEphemeralPrivateKey()
  {
    return this.ephemeralPrivateKey;
  }
  
  public ECPublicKeyParameters getEphemeralPublicKey()
  {
    return this.ephemeralPublicKey;
  }
  
  public ECPrivateKeyParameters getStaticPrivateKey()
  {
    return this.staticPrivateKey;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.MQVPrivateParameters
 * JD-Core Version:    0.7.0.1
 */