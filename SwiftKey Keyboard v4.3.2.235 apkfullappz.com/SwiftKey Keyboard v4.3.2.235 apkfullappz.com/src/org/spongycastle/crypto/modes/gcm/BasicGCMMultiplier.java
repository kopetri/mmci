package org.spongycastle.crypto.modes.gcm;

import org.spongycastle.util.Arrays;

public class BasicGCMMultiplier
  implements GCMMultiplier
{
  private byte[] H;
  
  public void init(byte[] paramArrayOfByte)
  {
    this.H = Arrays.clone(paramArrayOfByte);
  }
  
  public void multiplyH(byte[] paramArrayOfByte)
  {
    GCMUtil.multiply(paramArrayOfByte, this.H);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.gcm.BasicGCMMultiplier
 * JD-Core Version:    0.7.0.1
 */