package org.spongycastle.crypto;

import java.security.SecureRandom;

public class CipherKeyGenerator
{
  protected SecureRandom random;
  protected int strength;
  
  public byte[] generateKey()
  {
    byte[] arrayOfByte = new byte[this.strength];
    this.random.nextBytes(arrayOfByte);
    return arrayOfByte;
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters)
  {
    this.random = paramKeyGenerationParameters.getRandom();
    this.strength = ((7 + paramKeyGenerationParameters.getStrength()) / 8);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.CipherKeyGenerator
 * JD-Core Version:    0.7.0.1
 */