package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class IESParameters
  implements CipherParameters
{
  private byte[] derivation;
  private byte[] encoding;
  private int macKeySize;
  
  public IESParameters(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    this.derivation = paramArrayOfByte1;
    this.encoding = paramArrayOfByte2;
    this.macKeySize = paramInt;
  }
  
  public byte[] getDerivationV()
  {
    return this.derivation;
  }
  
  public byte[] getEncodingV()
  {
    return this.encoding;
  }
  
  public int getMacKeySize()
  {
    return this.macKeySize;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.IESParameters
 * JD-Core Version:    0.7.0.1
 */