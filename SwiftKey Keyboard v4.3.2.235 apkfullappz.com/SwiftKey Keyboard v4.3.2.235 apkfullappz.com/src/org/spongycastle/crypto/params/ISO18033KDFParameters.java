package org.spongycastle.crypto.params;

import org.spongycastle.crypto.DerivationParameters;

public class ISO18033KDFParameters
  implements DerivationParameters
{
  byte[] seed;
  
  public ISO18033KDFParameters(byte[] paramArrayOfByte)
  {
    this.seed = paramArrayOfByte;
  }
  
  public byte[] getSeed()
  {
    return this.seed;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.ISO18033KDFParameters
 * JD-Core Version:    0.7.0.1
 */