package org.spongycastle.crypto.prng;

public class VMPCRandomGenerator
  implements RandomGenerator
{
  private byte[] P = { -69, 44, 98, 127, -75, -86, -44, 13, -127, -2, -78, -126, -53, -96, -95, 8, 24, 113, 86, -24, 73, 2, 16, -60, -34, 53, -91, -20, -128, 18, -72, 105, -38, 47, 117, -52, -94, 9, 54, 3, 97, 45, -3, -32, -35, 5, 67, -112, -83, -56, -31, -81, 87, -101, 76, -40, 81, -82, 80, -123, 60, 10, -28, -13, -100, 38, 35, 83, -55, -125, -105, 70, -79, -103, 100, 49, 119, -43, 29, -42, 120, -67, 94, -80, -118, 34, 56, -8, 104, 43, 42, -59, -45, -9, -68, 111, -33, 4, -27, -107, 62, 37, -122, -90, 11, -113, -15, 36, 14, -41, 64, -77, -49, 126, 6, 21, -102, 77, 28, -93, -37, 50, -110, 88, 17, 39, -12, 89, -48, 78, 106, 23, 91, -84, -1, 7, -64, 101, 121, -4, -57, -51, 118, 66, 93, -25, 58, 52, 122, 48, 40, 15, 115, 1, -7, -47, -46, 25, -23, -111, -71, 90, -19, 65, 109, -76, -61, -98, -65, 99, -6, 31, 51, 96, 71, -119, -16, -106, 26, 95, -109, 61, 55, 75, -39, -88, -63, 27, -10, 57, -117, -73, 12, 32, -50, -120, 110, -74, 116, -114, -115, 22, 41, -14, -121, -11, -21, 112, -29, -5, 85, -97, -58, 68, 74, 69, 125, -30, 107, 92, 108, 102, -87, -116, -18, -124, 19, -89, 30, -99, -36, 103, 72, -70, 46, -26, -92, -85, 124, -108, 0, 33, -17, -22, -66, -54, 114, 79, 82, -104, 63, -62, 20, 123, 59, 84 };
  private byte n = 0;
  private byte s = -66;
  
  public void addSeedMaterial(long paramLong)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[3] = ((byte)(int)(0xFF & paramLong));
    arrayOfByte[2] = ((byte)(int)((0xFF00 & paramLong) >> 8));
    arrayOfByte[1] = ((byte)(int)((0xFF0000 & paramLong) >> 16));
    arrayOfByte[0] = ((byte)(int)((0xFF000000 & paramLong) >> 24));
    addSeedMaterial(arrayOfByte);
  }
  
  public void addSeedMaterial(byte[] paramArrayOfByte)
  {
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      this.s = this.P[(0xFF & this.s + this.P[(0xFF & this.n)] + paramArrayOfByte[i])];
      int j = this.P[(0xFF & this.n)];
      this.P[(0xFF & this.n)] = this.P[(0xFF & this.s)];
      this.P[(0xFF & this.s)] = j;
      this.n = ((byte)(0xFF & 1 + this.n));
    }
  }
  
  public void nextBytes(byte[] paramArrayOfByte)
  {
    nextBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void nextBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    arrayOfByte = this.P;
    int i = paramInt1 + paramInt2;
    int j = paramInt1;
    for (;;)
    {
      if (j != i) {}
      try
      {
        this.s = this.P[(0xFF & this.s + this.P[(0xFF & this.n)])];
        paramArrayOfByte[j] = this.P[(0xFF & 1 + this.P[(0xFF & this.P[(0xFF & this.s)])])];
        int k = this.P[(0xFF & this.n)];
        this.P[(0xFF & this.n)] = this.P[(0xFF & this.s)];
        this.P[(0xFF & this.s)] = k;
        this.n = ((byte)(0xFF & 1 + this.n));
        j++;
      }
      finally {}
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.prng.VMPCRandomGenerator
 * JD-Core Version:    0.7.0.1
 */