package org.spongycastle.jce.spec;

import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.spongycastle.jce.interfaces.MQVPublicKey;

public class MQVPublicKeySpec
  implements KeySpec, MQVPublicKey
{
  private PublicKey ephemeralKey;
  private PublicKey staticKey;
  
  public MQVPublicKeySpec(PublicKey paramPublicKey1, PublicKey paramPublicKey2)
  {
    this.staticKey = paramPublicKey1;
    this.ephemeralKey = paramPublicKey2;
  }
  
  public String getAlgorithm()
  {
    return "ECMQV";
  }
  
  public byte[] getEncoded()
  {
    return null;
  }
  
  public PublicKey getEphemeralKey()
  {
    return this.ephemeralKey;
  }
  
  public String getFormat()
  {
    return null;
  }
  
  public PublicKey getStaticKey()
  {
    return this.staticKey;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.spec.MQVPublicKeySpec
 * JD-Core Version:    0.7.0.1
 */