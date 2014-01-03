package org.spongycastle.crypto.generators;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DESParameters;

public class DESKeyGenerator
  extends CipherKeyGenerator
{
  public byte[] generateKey()
  {
    byte[] arrayOfByte = new byte[8];
    do
    {
      this.random.nextBytes(arrayOfByte);
      DESParameters.setOddParity(arrayOfByte);
    } while (DESParameters.isWeakKey(arrayOfByte, 0));
    return arrayOfByte;
  }
  
  public void init(KeyGenerationParameters paramKeyGenerationParameters)
  {
    super.init(paramKeyGenerationParameters);
    if ((this.strength == 0) || (this.strength == 7)) {
      this.strength = 8;
    }
    while (this.strength == 8) {
      return;
    }
    throw new IllegalArgumentException("DES key must be 64 bits long.");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.DESKeyGenerator
 * JD-Core Version:    0.7.0.1
 */