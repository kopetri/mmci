package org.spongycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.spongycastle.crypto.util.Pack;

public class Tables64kGCMMultiplier
  implements GCMMultiplier
{
  private final int[][][] M = (int[][][])Array.newInstance([I.class, new int[] { 16, 256 });
  
  public void init(byte[] paramArrayOfByte)
  {
    this.M[0][0] = new int[4];
    this.M[0][''] = GCMUtil.asInts(paramArrayOfByte);
    int i = 64;
    while (i > 0)
    {
      int[] arrayOfInt3 = new int[4];
      System.arraycopy(this.M[0][(i + i)], 0, arrayOfInt3, 0, 4);
      GCMUtil.multiplyP(arrayOfInt3);
      this.M[0][i] = arrayOfInt3;
      i >>= 1;
    }
    int j = 0;
    for (;;)
    {
      int k = 2;
      while (k < 256)
      {
        for (int n = 1; n < k; n++)
        {
          int[] arrayOfInt2 = new int[4];
          System.arraycopy(this.M[j][k], 0, arrayOfInt2, 0, 4);
          GCMUtil.xor(arrayOfInt2, this.M[j][n]);
          this.M[j][(k + n)] = arrayOfInt2;
        }
        k += k;
      }
      j++;
      if (j == 16) {
        return;
      }
      this.M[j][0] = new int[4];
      int m = 128;
      while (m > 0)
      {
        int[] arrayOfInt1 = new int[4];
        System.arraycopy(this.M[(j - 1)][m], 0, arrayOfInt1, 0, 4);
        GCMUtil.multiplyP8(arrayOfInt1);
        this.M[j][m] = arrayOfInt1;
        m >>= 1;
      }
    }
  }
  
  public void multiplyH(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt1 = new int[4];
    for (int i = 15; i >= 0; i--)
    {
      int[] arrayOfInt2 = this.M[i][(0xFF & paramArrayOfByte[i])];
      arrayOfInt1[0] ^= arrayOfInt2[0];
      arrayOfInt1[1] ^= arrayOfInt2[1];
      arrayOfInt1[2] ^= arrayOfInt2[2];
      arrayOfInt1[3] ^= arrayOfInt2[3];
    }
    Pack.intToBigEndian(arrayOfInt1, paramArrayOfByte, 0);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.gcm.Tables64kGCMMultiplier
 * JD-Core Version:    0.7.0.1
 */