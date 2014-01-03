package org.spongycastle.crypto.modes.gcm;

import org.spongycastle.util.Arrays;

public class Tables1kGCMExponentiator
  implements GCMExponentiator
{
  byte[][] lookupPowX2 = new byte[64][];
  
  public void exponentiateX(long paramLong, byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = GCMUtil.oneAsBytes();
    int i = 1;
    while (paramLong > 0L)
    {
      if ((1L & paramLong) != 0L) {
        GCMUtil.multiply(arrayOfByte, this.lookupPowX2[i]);
      }
      i++;
      paramLong >>>= 1;
    }
    System.arraycopy(arrayOfByte, 0, paramArrayOfByte, 0, 16);
  }
  
  public void init(byte[] paramArrayOfByte)
  {
    this.lookupPowX2[0] = GCMUtil.oneAsBytes();
    this.lookupPowX2[1] = Arrays.clone(paramArrayOfByte);
    for (int i = 2; i != 64; i++)
    {
      byte[] arrayOfByte = Arrays.clone(this.lookupPowX2[(i - 1)]);
      GCMUtil.multiply(arrayOfByte, arrayOfByte);
      this.lookupPowX2[i] = arrayOfByte;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.gcm.Tables1kGCMExponentiator
 * JD-Core Version:    0.7.0.1
 */