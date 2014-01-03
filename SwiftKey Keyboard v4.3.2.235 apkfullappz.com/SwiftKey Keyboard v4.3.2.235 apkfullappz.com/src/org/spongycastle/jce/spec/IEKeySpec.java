package org.spongycastle.jce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.spongycastle.jce.interfaces.IESKey;

public class IEKeySpec
  implements KeySpec, IESKey
{
  private PrivateKey privKey;
  private PublicKey pubKey;
  
  public IEKeySpec(PrivateKey paramPrivateKey, PublicKey paramPublicKey)
  {
    this.privKey = paramPrivateKey;
    this.pubKey = paramPublicKey;
  }
  
  public String getAlgorithm()
  {
    return "IES";
  }
  
  public byte[] getEncoded()
  {
    return null;
  }
  
  public String getFormat()
  {
    return null;
  }
  
  public PrivateKey getPrivate()
  {
    return this.privKey;
  }
  
  public PublicKey getPublic()
  {
    return this.pubKey;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.spec.IEKeySpec
 * JD-Core Version:    0.7.0.1
 */