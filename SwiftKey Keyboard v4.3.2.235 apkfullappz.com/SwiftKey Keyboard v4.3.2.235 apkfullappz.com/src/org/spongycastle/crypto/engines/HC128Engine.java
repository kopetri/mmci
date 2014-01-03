package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class HC128Engine
  implements StreamCipher
{
  private byte[] buf = new byte[4];
  private int cnt = 0;
  private int idx = 0;
  private boolean initialised;
  private byte[] iv;
  private byte[] key;
  private int[] p = new int[512];
  private int[] q = new int[512];
  
  private static int dim(int paramInt1, int paramInt2)
  {
    return mod512(paramInt1 - paramInt2);
  }
  
  private static int f1(int paramInt)
  {
    return rotateRight(paramInt, 7) ^ rotateRight(paramInt, 18) ^ paramInt >>> 3;
  }
  
  private static int f2(int paramInt)
  {
    return rotateRight(paramInt, 17) ^ rotateRight(paramInt, 19) ^ paramInt >>> 10;
  }
  
  private int g1(int paramInt1, int paramInt2, int paramInt3)
  {
    return (rotateRight(paramInt1, 10) ^ rotateRight(paramInt3, 23)) + rotateRight(paramInt2, 8);
  }
  
  private int g2(int paramInt1, int paramInt2, int paramInt3)
  {
    return (rotateLeft(paramInt1, 10) ^ rotateLeft(paramInt3, 23)) + rotateLeft(paramInt2, 8);
  }
  
  private byte getByte()
  {
    if (this.idx == 0)
    {
      int i = step();
      this.buf[0] = ((byte)(i & 0xFF));
      int j = i >> 8;
      this.buf[1] = ((byte)(j & 0xFF));
      int k = j >> 8;
      this.buf[2] = ((byte)(k & 0xFF));
      int m = k >> 8;
      this.buf[3] = ((byte)(m & 0xFF));
    }
    byte b = this.buf[this.idx];
    this.idx = (0x3 & 1 + this.idx);
    return b;
  }
  
  private int h1(int paramInt)
  {
    return this.q[(paramInt & 0xFF)] + this.q[(256 + (0xFF & paramInt >> 16))];
  }
  
  private int h2(int paramInt)
  {
    return this.p[(paramInt & 0xFF)] + this.p[(256 + (0xFF & paramInt >> 16))];
  }
  
  private void init()
  {
    if (this.key.length != 16) {
      throw new IllegalArgumentException("The key must be 128 bits long");
    }
    this.cnt = 0;
    int[] arrayOfInt = new int[1280];
    for (int i = 0; i < 16; i++)
    {
      int i2 = i >> 2;
      arrayOfInt[i2] |= (0xFF & this.key[i]) << 8 * (i & 0x3);
    }
    System.arraycopy(arrayOfInt, 0, arrayOfInt, 4, 4);
    for (int j = 0; (j < this.iv.length) && (j < 16); j++)
    {
      int i1 = 8 + (j >> 2);
      arrayOfInt[i1] |= (0xFF & this.iv[j]) << 8 * (j & 0x3);
    }
    System.arraycopy(arrayOfInt, 8, arrayOfInt, 12, 4);
    for (int k = 16; k < 1280; k++) {
      arrayOfInt[k] = (k + (f2(arrayOfInt[(k - 2)]) + arrayOfInt[(k - 7)] + f1(arrayOfInt[(k - 15)]) + arrayOfInt[(k - 16)]));
    }
    System.arraycopy(arrayOfInt, 256, this.p, 0, 512);
    System.arraycopy(arrayOfInt, 768, this.q, 0, 512);
    for (int m = 0; m < 512; m++) {
      this.p[m] = step();
    }
    for (int n = 0; n < 512; n++) {
      this.q[n] = step();
    }
    this.cnt = 0;
  }
  
  private static int mod1024(int paramInt)
  {
    return paramInt & 0x3FF;
  }
  
  private static int mod512(int paramInt)
  {
    return paramInt & 0x1FF;
  }
  
  private static int rotateLeft(int paramInt1, int paramInt2)
  {
    return paramInt1 << paramInt2 | paramInt1 >>> -paramInt2;
  }
  
  private static int rotateRight(int paramInt1, int paramInt2)
  {
    return paramInt1 >>> paramInt2 | paramInt1 << -paramInt2;
  }
  
  private int step()
  {
    int i = mod512(this.cnt);
    if (this.cnt < 512)
    {
      int[] arrayOfInt2 = this.p;
      arrayOfInt2[i] += g1(this.p[dim(i, 3)], this.p[dim(i, 10)], this.p[dim(i, 511)]);
    }
    for (int j = h1(this.p[dim(i, 12)]) ^ this.p[i];; j = h2(this.q[dim(i, 12)]) ^ this.q[i])
    {
      this.cnt = mod1024(1 + this.cnt);
      return j;
      int[] arrayOfInt1 = this.q;
      arrayOfInt1[i] += g2(this.q[dim(i, 3)], this.q[dim(i, 10)], this.q[dim(i, 511)]);
    }
  }
  
  public String getAlgorithmName()
  {
    return "HC-128";
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
    throws IllegalArgumentException
  {
    CipherParameters localCipherParameters = paramCipherParameters;
    if ((paramCipherParameters instanceof ParametersWithIV))
    {
      this.iv = ((ParametersWithIV)paramCipherParameters).getIV();
      localCipherParameters = ((ParametersWithIV)paramCipherParameters).getParameters();
    }
    while ((localCipherParameters instanceof KeyParameter))
    {
      this.key = ((KeyParameter)localCipherParameters).getKey();
      init();
      this.initialised = true;
      return;
      this.iv = new byte[0];
    }
    throw new IllegalArgumentException("Invalid parameter passed to HC128 init - " + paramCipherParameters.getClass().getName());
  }
  
  public void processBytes(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws DataLengthException
  {
    if (!this.initialised) {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    if (paramInt1 + paramInt2 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt3 + paramInt2 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    for (int i = 0; i < paramInt2; i++) {
      paramArrayOfByte2[(paramInt3 + i)] = ((byte)(paramArrayOfByte1[(paramInt1 + i)] ^ getByte()));
    }
  }
  
  public void reset()
  {
    this.idx = 0;
    init();
  }
  
  public byte returnByte(byte paramByte)
  {
    return (byte)(paramByte ^ getByte());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.HC128Engine
 * JD-Core Version:    0.7.0.1
 */