package org.spongycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.spongycastle.crypto.util.Pack;

public class Tables8kGCMMultiplier
  implements GCMMultiplier
{
  private final int[][][] M = (int[][][])Array.newInstance([I.class, new int[] { 32, 16 });
  
  public void init(byte[] paramArrayOfByte)
  {
    this.M[0][0] = new int[4];
    this.M[1][0] = new int[4];
    this.M[1][8] = GCMUtil.asInts(paramArrayOfByte);
    int i = 4;
    while (i > 0)
    {
      int[] arrayOfInt5 = new int[4];
      System.arraycopy(this.M[1][(i + i)], 0, arrayOfInt5, 0, 4);
      GCMUtil.multiplyP(arrayOfInt5);
      this.M[1][i] = arrayOfInt5;
      i >>= 1;
    }
    int[] arrayOfInt1 = new int[4];
    System.arraycopy(this.M[1][1], 0, arrayOfInt1, 0, 4);
    GCMUtil.multiplyP(arrayOfInt1);
    this.M[0][8] = arrayOfInt1;
    int j = 4;
    while (j > 0)
    {
      int[] arrayOfInt4 = new int[4];
      System.arraycopy(this.M[0][(j + j)], 0, arrayOfInt4, 0, 4);
      GCMUtil.multiplyP(arrayOfInt4);
      this.M[0][j] = arrayOfInt4;
      j >>= 1;
    }
    int k = 0;
    for (;;)
    {
      int m = 2;
      while (m < 16)
      {
        for (int i1 = 1; i1 < m; i1++)
        {
          int[] arrayOfInt3 = new int[4];
          System.arraycopy(this.M[k][m], 0, arrayOfInt3, 0, 4);
          GCMUtil.xor(arrayOfInt3, this.M[k][i1]);
          this.M[k][(m + i1)] = arrayOfInt3;
        }
        m += m;
      }
      k++;
      if (k == 32) {
        return;
      }
      if (k > 1)
      {
        this.M[k][0] = new int[4];
        int n = 8;
        while (n > 0)
        {
          int[] arrayOfInt2 = new int[4];
          System.arraycopy(this.M[(k - 2)][n], 0, arrayOfInt2, 0, 4);
          GCMUtil.multiplyP8(arrayOfInt2);
          this.M[k][n] = arrayOfInt2;
          n >>= 1;
        }
      }
    }
  }
  
  public void multiplyH(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt1 = new int[4];
    for (int i = 15; i >= 0; i--)
    {
      int[] arrayOfInt2 = this.M[(i + i)][(0xF & paramArrayOfByte[i])];
      arrayOfInt1[0] ^= arrayOfInt2[0];
      arrayOfInt1[1] ^= arrayOfInt2[1];
      arrayOfInt1[2] ^= arrayOfInt2[2];
      arrayOfInt1[3] ^= arrayOfInt2[3];
      int[] arrayOfInt3 = this.M[(1 + (i + i))][((0xF0 & paramArrayOfByte[i]) >>> 4)];
      arrayOfInt1[0] ^= arrayOfInt3[0];
      arrayOfInt1[1] ^= arrayOfInt3[1];
      arrayOfInt1[2] ^= arrayOfInt3[2];
      arrayOfInt1[3] ^= arrayOfInt3[3];
    }
    Pack.intToBigEndian(arrayOfInt1, paramArrayOfByte, 0);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.gcm.Tables8kGCMMultiplier
 * JD-Core Version:    0.7.0.1
 */