package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class RC5Parameters
  implements CipherParameters
{
  private byte[] key;
  private int rounds;
  
  public RC5Parameters(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length > 255) {
      throw new IllegalArgumentException("RC5 key length can be no greater than 255");
    }
    this.key = new byte[paramArrayOfByte.length];
    this.rounds = paramInt;
    System.arraycopy(paramArrayOfByte, 0, this.key, 0, paramArrayOfByte.length);
  }
  
  public byte[] getKey()
  {
    return this.key;
  }
  
  public int getRounds()
  {
    return this.rounds;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.RC5Parameters
 * JD-Core Version:    0.7.0.1
 */