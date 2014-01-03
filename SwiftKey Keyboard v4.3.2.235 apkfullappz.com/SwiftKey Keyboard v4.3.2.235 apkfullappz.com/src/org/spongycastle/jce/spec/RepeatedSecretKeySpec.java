package org.spongycastle.jce.spec;

import javax.crypto.SecretKey;

public class RepeatedSecretKeySpec
  implements SecretKey
{
  private String algorithm;
  
  public RepeatedSecretKeySpec(String paramString)
  {
    this.algorithm = paramString;
  }
  
  public String getAlgorithm()
  {
    return this.algorithm;
  }
  
  public byte[] getEncoded()
  {
    return null;
  }
  
  public String getFormat()
  {
    return null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.spec.RepeatedSecretKeySpec
 * JD-Core Version:    0.7.0.1
 */