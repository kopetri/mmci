package org.spongycastle.crypto.params;

import org.spongycastle.crypto.DerivationParameters;

public class KDFParameters
  implements DerivationParameters
{
  byte[] iv;
  byte[] shared;
  
  public KDFParameters(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.shared = paramArrayOfByte1;
    this.iv = paramArrayOfByte2;
  }
  
  public byte[] getIV()
  {
    return this.iv;
  }
  
  public byte[] getSharedSecret()
  {
    return this.shared;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.KDFParameters
 * JD-Core Version:    0.7.0.1
 */