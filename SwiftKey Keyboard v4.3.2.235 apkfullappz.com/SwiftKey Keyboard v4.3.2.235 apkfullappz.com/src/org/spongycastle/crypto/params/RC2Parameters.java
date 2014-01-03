package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class RC2Parameters
  implements CipherParameters
{
  private int bits;
  private byte[] key;
  
  public RC2Parameters(byte[] paramArrayOfByte) {}
  
  public RC2Parameters(byte[] paramArrayOfByte, int paramInt)
  {
    this.key = new byte[paramArrayOfByte.length];
    this.bits = paramInt;
    System.arraycopy(paramArrayOfByte, 0, this.key, 0, paramArrayOfByte.length);
  }
  
  public int getEffectiveKeyBits()
  {
    return this.bits;
  }
  
  public byte[] getKey()
  {
    return this.key;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.RC2Parameters
 * JD-Core Version:    0.7.0.1
 */