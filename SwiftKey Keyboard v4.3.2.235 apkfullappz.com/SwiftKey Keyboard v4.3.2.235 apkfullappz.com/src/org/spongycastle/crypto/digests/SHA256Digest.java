package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.util.Pack;

public class SHA256Digest
  extends GeneralDigest
{
  private static final int DIGEST_LENGTH = 32;
  static final int[] K = { 1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998 };
  private int H1;
  private int H2;
  private int H3;
  private int H4;
  private int H5;
  private int H6;
  private int H7;
  private int H8;
  private int[] X = new int[64];
  private int xOff;
  
  public SHA256Digest()
  {
    reset();
  }
  
  public SHA256Digest(SHA256Digest paramSHA256Digest)
  {
    super(paramSHA256Digest);
    this.H1 = paramSHA256Digest.H1;
    this.H2 = paramSHA256Digest.H2;
    this.H3 = paramSHA256Digest.H3;
    this.H4 = paramSHA256Digest.H4;
    this.H5 = paramSHA256Digest.H5;
    this.H6 = paramSHA256Digest.H6;
    this.H7 = paramSHA256Digest.H7;
    this.H8 = paramSHA256Digest.H8;
    System.arraycopy(paramSHA256Digest.X, 0, this.X, 0, paramSHA256Digest.X.length);
    this.xOff = paramSHA256Digest.xOff;
  }
  
  private int Ch(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt1 & paramInt2 ^ paramInt3 & (paramInt1 ^ 0xFFFFFFFF);
  }
  
  private int Maj(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt1 & paramInt2 ^ paramInt1 & paramInt3 ^ paramInt2 & paramInt3;
  }
  
  private int Sum0(int paramInt)
  {
    return (paramInt >>> 2 | paramInt << 30) ^ (paramInt >>> 13 | paramInt << 19) ^ (paramInt >>> 22 | paramInt << 10);
  }
  
  private int Sum1(int paramInt)
  {
    return (paramInt >>> 6 | paramInt << 26) ^ (paramInt >>> 11 | paramInt << 21) ^ (paramInt >>> 25 | paramInt << 7);
  }
  
  private int Theta0(int paramInt)
  {
    return (paramInt >>> 7 | paramInt << 25) ^ (paramInt >>> 18 | paramInt << 14) ^ paramInt >>> 3;
  }
  
  private int Theta1(int paramInt)
  {
    return (paramInt >>> 17 | paramInt << 15) ^ (paramInt >>> 19 | paramInt << 13) ^ paramInt >>> 10;
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    finish();
    Pack.intToBigEndian(this.H1, paramArrayOfByte, paramInt);
    Pack.intToBigEndian(this.H2, paramArrayOfByte, paramInt + 4);
    Pack.intToBigEndian(this.H3, paramArrayOfByte, paramInt + 8);
    Pack.intToBigEndian(this.H4, paramArrayOfByte, paramInt + 12);
    Pack.intToBigEndian(this.H5, paramArrayOfByte, paramInt + 16);
    Pack.intToBigEndian(this.H6, paramArrayOfByte, paramInt + 20);
    Pack.intToBigEndian(this.H7, paramArrayOfByte, paramInt + 24);
    Pack.intToBigEndian(this.H8, paramArrayOfByte, paramInt + 28);
    reset();
    return 32;
  }
  
  public String getAlgorithmName()
  {
    return "SHA-256";
  }
  
  public int getDigestSize()
  {
    return 32;
  }
  
  protected void processBlock()
  {
    for (int i = 16; i <= 63; i++) {
      this.X[i] = (Theta1(this.X[(i - 2)]) + this.X[(i - 7)] + Theta0(this.X[(i - 15)]) + this.X[(i - 16)]);
    }
    int j = this.H1;
    int k = this.H2;
    int m = this.H3;
    int n = this.H4;
    int i1 = this.H5;
    int i2 = this.H6;
    int i3 = this.H7;
    int i4 = this.H8;
    int i5 = 0;
    for (int i6 = 0; i6 < 8; i6++)
    {
      int i8 = i4 + (Sum1(i1) + Ch(i1, i2, i3) + K[i5] + this.X[i5]);
      int i9 = n + i8;
      int i10 = i8 + (Sum0(j) + Maj(j, k, m));
      int i11 = i5 + 1;
      int i12 = i3 + (Sum1(i9) + Ch(i9, i1, i2) + K[i11] + this.X[i11]);
      int i13 = m + i12;
      int i14 = i12 + (Sum0(i10) + Maj(i10, j, k));
      int i15 = i11 + 1;
      int i16 = i2 + (Sum1(i13) + Ch(i13, i9, i1) + K[i15] + this.X[i15]);
      int i17 = k + i16;
      int i18 = i16 + (Sum0(i14) + Maj(i14, i10, j));
      int i19 = i15 + 1;
      int i20 = i1 + (Sum1(i17) + Ch(i17, i13, i9) + K[i19] + this.X[i19]);
      int i21 = j + i20;
      int i22 = i20 + (Sum0(i18) + Maj(i18, i14, i10));
      int i23 = i19 + 1;
      int i24 = i9 + (Sum1(i21) + Ch(i21, i17, i13) + K[i23] + this.X[i23]);
      i4 = i10 + i24;
      n = i24 + (Sum0(i22) + Maj(i22, i18, i14));
      int i25 = i23 + 1;
      int i26 = i13 + (Sum1(i4) + Ch(i4, i21, i17) + K[i25] + this.X[i25]);
      i3 = i14 + i26;
      m = i26 + (Sum0(n) + Maj(n, i22, i18));
      int i27 = i25 + 1;
      int i28 = i17 + (Sum1(i3) + Ch(i3, i4, i21) + K[i27] + this.X[i27]);
      i2 = i18 + i28;
      k = i28 + (Sum0(m) + Maj(m, n, i22));
      int i29 = i27 + 1;
      int i30 = i21 + (Sum1(i2) + Ch(i2, i3, i4) + K[i29] + this.X[i29]);
      i1 = i22 + i30;
      j = i30 + (Sum0(k) + Maj(k, m, n));
      i5 = i29 + 1;
    }
    this.H1 = (j + this.H1);
    this.H2 = (k + this.H2);
    this.H3 = (m + this.H3);
    this.H4 = (n + this.H4);
    this.H5 = (i1 + this.H5);
    this.H6 = (i2 + this.H6);
    this.H7 = (i3 + this.H7);
    this.H8 = (i4 + this.H8);
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
    this.H1 = 1779033703;
    this.H2 = -1150833019;
    this.H3 = 1013904242;
    this.H4 = -1521486534;
    this.H5 = 1359893119;
    this.H6 = -1694144372;
    this.H7 = 528734635;
    this.H8 = 1541459225;
    this.xOff = 0;
    for (int i = 0; i != this.X.length; i++) {
      this.X[i] = 0;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.digests.SHA256Digest
 * JD-Core Version:    0.7.0.1
 */