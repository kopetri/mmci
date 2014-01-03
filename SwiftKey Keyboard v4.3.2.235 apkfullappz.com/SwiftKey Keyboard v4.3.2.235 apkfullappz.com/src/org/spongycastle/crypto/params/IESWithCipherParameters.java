package org.spongycastle.crypto.params;

public class IESWithCipherParameters
  extends IESParameters
{
  private int cipherKeySize;
  
  public IESWithCipherParameters(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
  {
    super(paramArrayOfByte1, paramArrayOfByte2, paramInt1);
    this.cipherKeySize = paramInt2;
  }
  
  public int getCipherKeySize()
  {
    return this.cipherKeySize;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.IESWithCipherParameters
 * JD-Core Version:    0.7.0.1
 */