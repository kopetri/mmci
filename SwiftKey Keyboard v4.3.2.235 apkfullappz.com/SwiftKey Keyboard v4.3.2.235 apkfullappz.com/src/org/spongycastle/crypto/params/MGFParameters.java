package org.spongycastle.crypto.params;

import org.spongycastle.crypto.DerivationParameters;

public class MGFParameters
  implements DerivationParameters
{
  byte[] seed;
  
  public MGFParameters(byte[] paramArrayOfByte)
  {
    this(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public MGFParameters(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.seed = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, this.seed, 0, paramInt2);
  }
  
  public byte[] getSeed()
  {
    return this.seed;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.MGFParameters
 * JD-Core Version:    0.7.0.1
 */