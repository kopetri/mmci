package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.util.Pack;

public class SHA1Digest
  extends GeneralDigest
{
  private static final int DIGEST_LENGTH = 20;
  private static final int Y1 = 1518500249;
  private static final int Y2 = 1859775393;
  private static final int Y3 = -1894007588;
  private static final int Y4 = -899497514;
  private int H1;
  private int H2;
  private int H3;
  private int H4;
  private int H5;
  private int[] X = new int[80];
  private int xOff;
  
  public SHA1Digest()
  {
    reset();
  }
  
  public SHA1Digest(SHA1Digest paramSHA1Digest)
  {
    super(paramSHA1Digest);
    this.H1 = paramSHA1Digest.H1;
    this.H2 = paramSHA1Digest.H2;
    this.H3 = paramSHA1Digest.H3;
    this.H4 = paramSHA1Digest.H4;
    this.H5 = paramSHA1Digest.H5;
    System.arraycopy(paramSHA1Digest.X, 0, this.X, 0, paramSHA1Digest.X.length);
    this.xOff = paramSHA1Digest.xOff;
  }
  
  private int f(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt1 & paramInt2 | paramInt3 & (paramInt1 ^ 0xFFFFFFFF);
  }
  
  private int g(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt1 & paramInt2 | paramInt1 & paramInt3 | paramInt2 & paramInt3;
  }
  
  private int h(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt3 ^ paramInt1 ^ paramInt2;
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    finish();
    Pack.intToBigEndian(this.H1, paramArrayOfByte, paramInt);
    Pack.intToBigEndian(this.H2, paramArrayOfByte, paramInt + 4);
    Pack.intToBigEndian(this.H3, paramArrayOfByte, paramInt + 8);
    Pack.intToBigEndian(this.H4, paramArrayOfByte, paramInt + 12);
    Pack.intToBigEndian(this.H5, paramArrayOfByte, paramInt + 16);
    reset();
    return 20;
  }
  
  public String getAlgorithmName()
  {
    return "SHA-1";
  }
  
  public int getDigestSize()
  {
    return 20;
  }
  
  protected void processBlock()
  {
    for (int i = 16; i < 80; i++)
    {
      int i68 = this.X[(i - 3)] ^ this.X[(i - 8)] ^ this.X[(i - 14)] ^ this.X[(i - 16)];
      this.X[i] = (i68 << 1 | i68 >>> 31);
    }
    int j = this.H1;
    int k = this.H2;
    int m = this.H3;
    int n = this.H4;
    int i1 = this.H5;
    int i2 = 0;
    int i67;
    for (int i3 = 0; i2 < 4; i3 = i67)
    {
      int i53 = (j << 5 | j >>> 27) + f(k, m, n);
      int[] arrayOfInt16 = this.X;
      int i54 = i3 + 1;
      int i55 = i1 + (1518500249 + (i53 + arrayOfInt16[i3]));
      int i56 = k << 30 | k >>> 2;
      int i57 = (i55 << 5 | i55 >>> 27) + f(j, i56, m);
      int[] arrayOfInt17 = this.X;
      int i58 = i54 + 1;
      int i59 = n + (1518500249 + (i57 + arrayOfInt17[i54]));
      int i60 = j << 30 | j >>> 2;
      int i61 = (i59 << 5 | i59 >>> 27) + f(i55, i60, i56);
      int[] arrayOfInt18 = this.X;
      int i62 = i58 + 1;
      int i63 = m + (1518500249 + (i61 + arrayOfInt18[i58]));
      i1 = i55 << 30 | i55 >>> 2;
      int i64 = (i63 << 5 | i63 >>> 27) + f(i59, i1, i60);
      int[] arrayOfInt19 = this.X;
      int i65 = i62 + 1;
      k = i56 + (1518500249 + (i64 + arrayOfInt19[i62]));
      n = i59 << 30 | i59 >>> 2;
      int i66 = (k << 5 | k >>> 27) + f(i63, n, i1);
      int[] arrayOfInt20 = this.X;
      i67 = i65 + 1;
      j = i60 + (1518500249 + (i66 + arrayOfInt20[i65]));
      m = i63 << 30 | i63 >>> 2;
      i2++;
    }
    int i4 = 0;
    while (i4 < 4)
    {
      int i38 = (j << 5 | j >>> 27) + h(k, m, n);
      int[] arrayOfInt11 = this.X;
      int i39 = i3 + 1;
      int i40 = i1 + (1859775393 + (i38 + arrayOfInt11[i3]));
      int i41 = k << 30 | k >>> 2;
      int i42 = (i40 << 5 | i40 >>> 27) + h(j, i41, m);
      int[] arrayOfInt12 = this.X;
      int i43 = i39 + 1;
      int i44 = n + (1859775393 + (i42 + arrayOfInt12[i39]));
      int i45 = j << 30 | j >>> 2;
      int i46 = (i44 << 5 | i44 >>> 27) + h(i40, i45, i41);
      int[] arrayOfInt13 = this.X;
      int i47 = i43 + 1;
      int i48 = m + (1859775393 + (i46 + arrayOfInt13[i43]));
      i1 = i40 << 30 | i40 >>> 2;
      int i49 = (i48 << 5 | i48 >>> 27) + h(i44, i1, i45);
      int[] arrayOfInt14 = this.X;
      int i50 = i47 + 1;
      k = i41 + (1859775393 + (i49 + arrayOfInt14[i47]));
      n = i44 << 30 | i44 >>> 2;
      int i51 = (k << 5 | k >>> 27) + h(i48, n, i1);
      int[] arrayOfInt15 = this.X;
      int i52 = i50 + 1;
      j = i45 + (1859775393 + (i51 + arrayOfInt15[i50]));
      m = i48 << 30 | i48 >>> 2;
      i4++;
      i3 = i52;
    }
    int i5 = 0;
    while (i5 < 4)
    {
      int i23 = (j << 5 | j >>> 27) + g(k, m, n);
      int[] arrayOfInt6 = this.X;
      int i24 = i3 + 1;
      int i25 = i1 + (-1894007588 + (i23 + arrayOfInt6[i3]));
      int i26 = k << 30 | k >>> 2;
      int i27 = (i25 << 5 | i25 >>> 27) + g(j, i26, m);
      int[] arrayOfInt7 = this.X;
      int i28 = i24 + 1;
      int i29 = n + (-1894007588 + (i27 + arrayOfInt7[i24]));
      int i30 = j << 30 | j >>> 2;
      int i31 = (i29 << 5 | i29 >>> 27) + g(i25, i30, i26);
      int[] arrayOfInt8 = this.X;
      int i32 = i28 + 1;
      int i33 = m + (-1894007588 + (i31 + arrayOfInt8[i28]));
      i1 = i25 << 30 | i25 >>> 2;
      int i34 = (i33 << 5 | i33 >>> 27) + g(i29, i1, i30);
      int[] arrayOfInt9 = this.X;
      int i35 = i32 + 1;
      k = i26 + (-1894007588 + (i34 + arrayOfInt9[i32]));
      n = i29 << 30 | i29 >>> 2;
      int i36 = (k << 5 | k >>> 27) + g(i33, n, i1);
      int[] arrayOfInt10 = this.X;
      int i37 = i35 + 1;
      j = i30 + (-1894007588 + (i36 + arrayOfInt10[i35]));
      m = i33 << 30 | i33 >>> 2;
      i5++;
      i3 = i37;
    }
    int i6 = 0;
    while (i6 <= 3)
    {
      int i8 = (j << 5 | j >>> 27) + h(k, m, n);
      int[] arrayOfInt1 = this.X;
      int i9 = i3 + 1;
      int i10 = i1 + (-899497514 + (i8 + arrayOfInt1[i3]));
      int i11 = k << 30 | k >>> 2;
      int i12 = (i10 << 5 | i10 >>> 27) + h(j, i11, m);
      int[] arrayOfInt2 = this.X;
      int i13 = i9 + 1;
      int i14 = n + (-899497514 + (i12 + arrayOfInt2[i9]));
      int i15 = j << 30 | j >>> 2;
      int i16 = (i14 << 5 | i14 >>> 27) + h(i10, i15, i11);
      int[] arrayOfInt3 = this.X;
      int i17 = i13 + 1;
      int i18 = m + (-899497514 + (i16 + arrayOfInt3[i13]));
      i1 = i10 << 30 | i10 >>> 2;
      int i19 = (i18 << 5 | i18 >>> 27) + h(i14, i1, i15);
      int[] arrayOfInt4 = this.X;
      int i20 = i17 + 1;
      k = i11 + (-899497514 + (i19 + arrayOfInt4[i17]));
      n = i14 << 30 | i14 >>> 2;
      int i21 = (k << 5 | k >>> 27) + h(i18, n, i1);
      int[] arrayOfInt5 = this.X;
      int i22 = i20 + 1;
      j = i15 + (-899497514 + (i21 + arrayOfInt5[i20]));
      m = i18 << 30 | i18 >>> 2;
      i6++;
      i3 = i22;
    }
    this.H1 = (j + this.H1);
    this.H2 = (k + this.H2);
    this.H3 = (m + this.H3);
    this.H4 = (n + this.H4);
    this.H5 = (i1 + this.H5);
    this.xOff = 0;
    for (int i7 = 0; i7 < 16; i7++) {
      this.X[i7] = 0;
    }
  }
  
  protected void processLength(long paramLong)
  {
    if (this.xOff > 14) {
      processBlock();
    }
    this.X[14] = ((int)(paramLong >>> 32));
    this.X[15] = ((int)(0xFFFFFFFF & paramLong));
  }
  
  protected void processWord(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte[paramInt] << 24;
    int j = paramInt + 1;
    int k = i | (0xFF & paramArrayOfByte[j]) << 16;
    int m = j + 1;
    int n = k | (0xFF & paramArrayOfByte[m]) << 8 | 0xFF & paramArrayOfByte[(m + 1)];
    this.X[this.xOff] = n;
    int i1 = 1 + this.xOff;
    this.xOff = i1;
    if (i1 == 16) {
      processBlock();
    }
  }
  
  public void reset()
  {
    super.reset();
    this.H1 = 1732584193;
    this.H2 = -271733879;
    this.H3 = -1732584194;
    this.H4 = 271733878;
    this.H5 = -1009589776;
    this.xOff = 0;
    for (int i = 0; i != this.X.length; i++) {
      this.X[i] = 0;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.digests.SHA1Digest
 * JD-Core Version:    0.7.0.1
 */