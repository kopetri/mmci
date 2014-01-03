package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class RFC3394WrapEngine
  implements Wrapper
{
  private BlockCipher engine;
  private boolean forWrapping;
  private byte[] iv = { -90, -90, -90, -90, -90, -90, -90, -90 };
  private KeyParameter param;
  
  public RFC3394WrapEngine(BlockCipher paramBlockCipher)
  {
    this.engine = paramBlockCipher;
  }
  
  public String getAlgorithmName()
  {
    return this.engine.getAlgorithmName();
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.forWrapping = paramBoolean;
    if ((paramCipherParameters instanceof ParametersWithRandom)) {
      paramCipherParameters = ((ParametersWithRandom)paramCipherParameters).getParameters();
    }
    if ((paramCipherParameters instanceof KeyParameter)) {
      this.param = ((KeyParameter)paramCipherParameters);
    }
    do
    {
      do
      {
        return;
      } while (!(paramCipherParameters instanceof ParametersWithIV));
      this.iv = ((ParametersWithIV)paramCipherParameters).getIV();
      this.param = ((KeyParameter)((ParametersWithIV)paramCipherParameters).getParameters());
    } while (this.iv.length == 8);
    throw new IllegalArgumentException("IV not equal to 8");
  }
  
  public byte[] unwrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    if (this.forWrapping) {
      throw new IllegalStateException("not set for unwrapping");
    }
    int i = paramInt2 / 8;
    if (i * 8 != paramInt2) {
      throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
    }
    byte[] arrayOfByte1 = new byte[paramInt2 - this.iv.length];
    byte[] arrayOfByte2 = new byte[this.iv.length];
    byte[] arrayOfByte3 = new byte[8 + this.iv.length];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte2, 0, this.iv.length);
    System.arraycopy(paramArrayOfByte, this.iv.length, arrayOfByte1, 0, paramInt2 - this.iv.length);
    this.engine.init(false, this.param);
    int j = i - 1;
    for (int k = 5; k >= 0; k--) {
      for (int m = j; m > 0; m--)
      {
        System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, this.iv.length);
        System.arraycopy(arrayOfByte1, 8 * (m - 1), arrayOfByte3, this.iv.length, 8);
        int n = m + j * k;
        for (int i1 = 1; n != 0; i1++)
        {
          int i2 = (byte)n;
          int i3 = this.iv.length - i1;
          arrayOfByte3[i3] = ((byte)(i2 ^ arrayOfByte3[i3]));
          n >>>= 8;
        }
        this.engine.processBlock(arrayOfByte3, 0, arrayOfByte3, 0);
        System.arraycopy(arrayOfByte3, 0, arrayOfByte2, 0, 8);
        System.arraycopy(arrayOfByte3, 8, arrayOfByte1, 8 * (m - 1), 8);
      }
    }
    if (!Arrays.constantTimeAreEqual(arrayOfByte2, this.iv)) {
      throw new InvalidCipherTextException("checksum failed");
    }
    return arrayOfByte1;
  }
  
  public byte[] wrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (!this.forWrapping) {
      throw new IllegalStateException("not set for wrapping");
    }
    int i = paramInt2 / 8;
    if (i * 8 != paramInt2) {
      throw new DataLengthException("wrap data must be a multiple of 8 bytes");
    }
    byte[] arrayOfByte1 = new byte[paramInt2 + this.iv.length];
    byte[] arrayOfByte2 = new byte[8 + this.iv.length];
    System.arraycopy(this.iv, 0, arrayOfByte1, 0, this.iv.length);
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, this.iv.length, paramInt2);
    this.engine.init(true, this.param);
    for (int j = 0; j != 6; j++) {
      for (int k = 1; k <= i; k++)
      {
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, this.iv.length);
        System.arraycopy(arrayOfByte1, k * 8, arrayOfByte2, this.iv.length, 8);
        this.engine.processBlock(arrayOfByte2, 0, arrayOfByte2, 0);
        int m = k + i * j;
        for (int n = 1; m != 0; n++)
        {
          int i1 = (byte)m;
          int i2 = this.iv.length - n;
          arrayOfByte2[i2] = ((byte)(i1 ^ arrayOfByte2[i2]));
          m >>>= 8;
        }
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, 8);
        System.arraycopy(arrayOfByte2, 8, arrayOfByte1, k * 8, 8);
      }
    }
    return arrayOfByte1;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RFC3394WrapEngine
 * JD-Core Version:    0.7.0.1
 */