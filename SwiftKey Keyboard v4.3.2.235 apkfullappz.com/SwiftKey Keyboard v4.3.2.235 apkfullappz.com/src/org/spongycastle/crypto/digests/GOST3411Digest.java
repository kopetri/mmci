package org.spongycastle.crypto.digests;

import java.lang.reflect.Array;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.engines.GOST28147Engine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.crypto.util.Pack;
import org.spongycastle.util.Arrays;

public class GOST3411Digest
  implements ExtendedDigest
{
  private static final byte[] C2 = { 0, -1, 0, -1, 0, -1, 0, -1, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1 };
  private static final int DIGEST_LENGTH = 32;
  private byte[][] C;
  private byte[] H = new byte[32];
  private byte[] K;
  private byte[] L = new byte[32];
  private byte[] M = new byte[32];
  byte[] S;
  private byte[] Sum = new byte[32];
  byte[] U;
  byte[] V;
  byte[] W;
  byte[] a;
  private long byteCount;
  private BlockCipher cipher;
  private byte[] sBox;
  short[] wS;
  short[] w_S;
  private byte[] xBuf;
  private int xBufOff;
  
  public GOST3411Digest()
  {
    int[] arrayOfInt = { 4, 32 };
    this.C = ((byte[][])Array.newInstance(Byte.TYPE, arrayOfInt));
    this.xBuf = new byte[32];
    this.cipher = new GOST28147Engine();
    this.K = new byte[32];
    this.a = new byte[8];
    this.wS = new short[16];
    this.w_S = new short[16];
    this.S = new byte[32];
    this.U = new byte[32];
    this.V = new byte[32];
    this.W = new byte[32];
    this.sBox = GOST28147Engine.getSBox("D-A");
    this.cipher.init(true, new ParametersWithSBox(null, this.sBox));
    reset();
  }
  
  public GOST3411Digest(GOST3411Digest paramGOST3411Digest)
  {
    int[] arrayOfInt = { 4, 32 };
    this.C = ((byte[][])Array.newInstance(Byte.TYPE, arrayOfInt));
    this.xBuf = new byte[32];
    this.cipher = new GOST28147Engine();
    this.K = new byte[32];
    this.a = new byte[8];
    this.wS = new short[16];
    this.w_S = new short[16];
    this.S = new byte[32];
    this.U = new byte[32];
    this.V = new byte[32];
    this.W = new byte[32];
    this.sBox = paramGOST3411Digest.sBox;
    this.cipher.init(true, new ParametersWithSBox(null, this.sBox));
    reset();
    System.arraycopy(paramGOST3411Digest.H, 0, this.H, 0, paramGOST3411Digest.H.length);
    System.arraycopy(paramGOST3411Digest.L, 0, this.L, 0, paramGOST3411Digest.L.length);
    System.arraycopy(paramGOST3411Digest.M, 0, this.M, 0, paramGOST3411Digest.M.length);
    System.arraycopy(paramGOST3411Digest.Sum, 0, this.Sum, 0, paramGOST3411Digest.Sum.length);
    System.arraycopy(paramGOST3411Digest.C[1], 0, this.C[1], 0, paramGOST3411Digest.C[1].length);
    System.arraycopy(paramGOST3411Digest.C[2], 0, this.C[2], 0, paramGOST3411Digest.C[2].length);
    System.arraycopy(paramGOST3411Digest.C[3], 0, this.C[3], 0, paramGOST3411Digest.C[3].length);
    System.arraycopy(paramGOST3411Digest.xBuf, 0, this.xBuf, 0, paramGOST3411Digest.xBuf.length);
    this.xBufOff = paramGOST3411Digest.xBufOff;
    this.byteCount = paramGOST3411Digest.byteCount;
  }
  
  public GOST3411Digest(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt = { 4, 32 };
    this.C = ((byte[][])Array.newInstance(Byte.TYPE, arrayOfInt));
    this.xBuf = new byte[32];
    this.cipher = new GOST28147Engine();
    this.K = new byte[32];
    this.a = new byte[8];
    this.wS = new short[16];
    this.w_S = new short[16];
    this.S = new byte[32];
    this.U = new byte[32];
    this.V = new byte[32];
    this.W = new byte[32];
    this.sBox = Arrays.clone(paramArrayOfByte);
    this.cipher.init(true, new ParametersWithSBox(null, this.sBox));
    reset();
  }
  
  private byte[] A(byte[] paramArrayOfByte)
  {
    for (int i = 0; i < 8; i++) {
      this.a[i] = ((byte)(paramArrayOfByte[i] ^ paramArrayOfByte[(i + 8)]));
    }
    System.arraycopy(paramArrayOfByte, 8, paramArrayOfByte, 0, 24);
    System.arraycopy(this.a, 0, paramArrayOfByte, 24, 8);
    return paramArrayOfByte;
  }
  
  private void E(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, byte[] paramArrayOfByte3, int paramInt2)
  {
    this.cipher.init(true, new KeyParameter(paramArrayOfByte1));
    this.cipher.processBlock(paramArrayOfByte3, paramInt2, paramArrayOfByte2, paramInt1);
  }
  
  private byte[] P(byte[] paramArrayOfByte)
  {
    for (int i = 0; i < 8; i++)
    {
      this.K[(i * 4)] = paramArrayOfByte[i];
      this.K[(1 + i * 4)] = paramArrayOfByte[(i + 8)];
      this.K[(2 + i * 4)] = paramArrayOfByte[(i + 16)];
      this.K[(3 + i * 4)] = paramArrayOfByte[(i + 24)];
    }
    return this.K;
  }
  
  private void cpyBytesToShort(byte[] paramArrayOfByte, short[] paramArrayOfShort)
  {
    for (int i = 0; i < paramArrayOfByte.length / 2; i++) {
      paramArrayOfShort[i] = ((short)(0xFF00 & paramArrayOfByte[(1 + i * 2)] << 8 | 0xFF & paramArrayOfByte[(i * 2)]));
    }
  }
  
  private void cpyShortToBytes(short[] paramArrayOfShort, byte[] paramArrayOfByte)
  {
    for (int i = 0; i < paramArrayOfByte.length / 2; i++)
    {
      paramArrayOfByte[(1 + i * 2)] = ((byte)(paramArrayOfShort[i] >> 8));
      paramArrayOfByte[(i * 2)] = ((byte)paramArrayOfShort[i]);
    }
  }
  
  private void finish()
  {
    Pack.longToLittleEndian(8L * this.byteCount, this.L, 0);
    while (this.xBufOff != 0) {
      update((byte)0);
    }
    processBlock(this.L, 0);
    processBlock(this.Sum, 0);
  }
  
  private void fw(byte[] paramArrayOfByte)
  {
    cpyBytesToShort(paramArrayOfByte, this.wS);
    this.w_S[15] = ((short)(this.wS[0] ^ this.wS[1] ^ this.wS[2] ^ this.wS[3] ^ this.wS[12] ^ this.wS[15]));
    System.arraycopy(this.wS, 1, this.w_S, 0, 15);
    cpyShortToBytes(this.w_S, paramArrayOfByte);
  }
  
  private void sumByteArray(byte[] paramArrayOfByte)
  {
    int i = 0;
    for (int j = 0; j != this.Sum.length; j++)
    {
      int k = i + ((0xFF & this.Sum[j]) + (0xFF & paramArrayOfByte[j]));
      this.Sum[j] = ((byte)k);
      i = k >>> 8;
    }
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    finish();
    System.arraycopy(this.H, 0, paramArrayOfByte, paramInt, this.H.length);
    reset();
    return 32;
  }
  
  public String getAlgorithmName()
  {
    return "GOST3411";
  }
  
  public int getByteLength()
  {
    return 32;
  }
  
  public int getDigestSize()
  {
    return 32;
  }
  
  protected void processBlock(byte[] paramArrayOfByte, int paramInt)
  {
    System.arraycopy(paramArrayOfByte, paramInt, this.M, 0, 32);
    System.arraycopy(this.H, 0, this.U, 0, 32);
    System.arraycopy(this.M, 0, this.V, 0, 32);
    for (int i = 0; i < 32; i++) {
      this.W[i] = ((byte)(this.U[i] ^ this.V[i]));
    }
    E(P(this.W), this.S, 0, this.H, 0);
    for (int j = 1; j < 4; j++)
    {
      byte[] arrayOfByte = A(this.U);
      for (int i2 = 0; i2 < 32; i2++) {
        this.U[i2] = ((byte)(arrayOfByte[i2] ^ this.C[j][i2]));
      }
      this.V = A(A(this.V));
      for (int i3 = 0; i3 < 32; i3++) {
        this.W[i3] = ((byte)(this.U[i3] ^ this.V[i3]));
      }
      E(P(this.W), this.S, j * 8, this.H, j * 8);
    }
    for (int k = 0; k < 12; k++) {
      fw(this.S);
    }
    for (int m = 0; m < 32; m++) {
      this.S[m] = ((byte)(this.S[m] ^ this.M[m]));
    }
    fw(this.S);
    for (int n = 0; n < 32; n++) {
      this.S[n] = ((byte)(this.H[n] ^ this.S[n]));
    }
    for (int i1 = 0; i1 < 61; i1++) {
      fw(this.S);
    }
    System.arraycopy(this.S, 0, this.H, 0, this.H.length);
  }
  
  public void reset()
  {
    this.byteCount = 0L;
    this.xBufOff = 0;
    for (int i = 0; i < this.H.length; i++) {
      this.H[i] = 0;
    }
    for (int j = 0; j < this.L.length; j++) {
      this.L[j] = 0;
    }
    for (int k = 0; k < this.M.length; k++) {
      this.M[k] = 0;
    }
    for (int m = 0; m < this.C[1].length; m++) {
      this.C[1][m] = 0;
    }
    for (int n = 0; n < this.C[3].length; n++) {
      this.C[3][n] = 0;
    }
    for (int i1 = 0; i1 < this.Sum.length; i1++) {
      this.Sum[i1] = 0;
    }
    for (int i2 = 0; i2 < this.xBuf.length; i2++) {
      this.xBuf[i2] = 0;
    }
    System.arraycopy(C2, 0, this.C[2], 0, C2.length);
  }
  
  public void update(byte paramByte)
  {
    byte[] arrayOfByte = this.xBuf;
    int i = this.xBufOff;
    this.xBufOff = (i + 1);
    arrayOfByte[i] = paramByte;
    if (this.xBufOff == this.xBuf.length)
    {
      sumByteArray(this.xBuf);
      processBlock(this.xBuf, 0);
      this.xBufOff = 0;
    }
    this.byteCount = (1L + this.byteCount);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    while ((this.xBufOff != 0) && (paramInt2 > 0))
    {
      update(paramArrayOfByte[paramInt1]);
      paramInt1++;
      paramInt2--;
    }
    while (paramInt2 > this.xBuf.length)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, this.xBuf, 0, this.xBuf.length);
      sumByteArray(this.xBuf);
      processBlock(this.xBuf, 0);
      paramInt1 += this.xBuf.length;
      paramInt2 -= this.xBuf.length;
      this.byteCount += this.xBuf.length;
    }
    while (paramInt2 > 0)
    {
      update(paramArrayOfByte[paramInt1]);
      paramInt1++;
      paramInt2--;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.digests.GOST3411Digest
 * JD-Core Version:    0.7.0.1
 */