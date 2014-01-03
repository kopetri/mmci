package org.spongycastle.crypto;

import java.security.SecureRandom;

public class KeyGenerationParameters
{
  private SecureRandom random;
  private int strength;
  
  public KeyGenerationParameters(SecureRandom paramSecureRandom, int paramInt)
  {
    this.random = paramSecureRandom;
    this.strength = paramInt;
  }
  
  public SecureRandom getRandom()
  {
    return this.random;
  }
  
  public int getStrength()
  {
    return this.strength;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.KeyGenerationParameters
 * JD-Core Version:    0.7.0.1
 */