package org.spongycastle.math.ntru.euclid;

public class IntEuclidean
{
  public int gcd;
  public int x;
  public int y;
  
  public static IntEuclidean calculate(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = 1;
    int k = 1;
    int i3;
    for (int m = 0; paramInt2 != 0; m = i3)
    {
      int n = paramInt1 / paramInt2;
      int i1 = paramInt1;
      paramInt1 = paramInt2;
      paramInt2 = i1 % paramInt2;
      int i2 = i;
      i = j - n * i;
      j = i2;
      i3 = k;
      k = m - n * k;
    }
    IntEuclidean localIntEuclidean = new IntEuclidean();
    localIntEuclidean.x = j;
    localIntEuclidean.y = m;
    localIntEuclidean.gcd = paramInt1;
    return localIntEuclidean;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.euclid.IntEuclidean
 * JD-Core Version:    0.7.0.1
 */