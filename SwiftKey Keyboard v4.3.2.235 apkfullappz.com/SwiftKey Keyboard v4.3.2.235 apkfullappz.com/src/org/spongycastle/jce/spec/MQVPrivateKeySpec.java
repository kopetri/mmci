package org.spongycastle.jce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.spongycastle.jce.interfaces.MQVPrivateKey;

public class MQVPrivateKeySpec
  implements KeySpec, MQVPrivateKey
{
  private PrivateKey ephemeralPrivateKey;
  private PublicKey ephemeralPublicKey;
  private PrivateKey staticPrivateKey;
  
  public MQVPrivateKeySpec(PrivateKey paramPrivateKey1, PrivateKey paramPrivateKey2)
  {
    this(paramPrivateKey1, paramPrivateKey2, null);
  }
  
  public MQVPrivateKeySpec(PrivateKey paramPrivateKey1, PrivateKey paramPrivateKey2, PublicKey paramPublicKey)
  {
    this.staticPrivateKey = paramPrivateKey1;
    this.ephemeralPrivateKey = paramPrivateKey2;
    this.ephemeralPublicKey = paramPublicKey;
  }
  
  public String getAlgorithm()
  {
    return "ECMQV";
  }
  
  public byte[] getEncoded()
  {
    return null;
  }
  
  public PrivateKey getEphemeralPrivateKey()
  {
    return this.ephemeralPrivateKey;
  }
  
  public PublicKey getEphemeralPublicKey()
  {
    return this.ephemeralPublicKey;
  }
  
  public String getFormat()
  {
    return null;
  }
  
  public PrivateKey getStaticPrivateKey()
  {
    return this.staticPrivateKey;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.spec.MQVPrivateKeySpec
 * JD-Core Version:    0.7.0.1
 */