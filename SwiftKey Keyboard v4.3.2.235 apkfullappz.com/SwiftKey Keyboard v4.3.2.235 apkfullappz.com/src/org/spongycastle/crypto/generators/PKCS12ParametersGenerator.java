package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class PKCS12ParametersGenerator
  extends PBEParametersGenerator
{
  public static final int IV_MATERIAL = 2;
  public static final int KEY_MATERIAL = 1;
  public static final int MAC_MATERIAL = 3;
  private Digest digest;
  private int u;
  private int v;
  
  public PKCS12ParametersGenerator(Digest paramDigest)
  {
    this.digest = paramDigest;
    if ((paramDigest instanceof ExtendedDigest))
    {
      this.u = paramDigest.getDigestSize();
      this.v = ((ExtendedDigest)paramDigest).getByteLength();
      return;
    }
    throw new IllegalArgumentException("Digest " + paramDigest.getAlgorithmName() + " unsupported");
  }
  
  private void adjust(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2)
  {
    int i = 1 + ((0xFF & paramArrayOfByte2[(-1 + paramArrayOfByte2.length)]) + (0xFF & paramArrayOfByte1[(-1 + (paramInt + paramArrayOfByte2.length))]));
    paramArrayOfByte1[(-1 + (paramInt + paramArrayOfByte2.length))] = ((byte)i);
    int j = i >>> 8;
    for (int k = -2 + paramArrayOfByte2.length; k >= 0; k--)
    {
      int m = j + ((0xFF & paramArrayOfByte2[k]) + (0xFF & paramArrayOfByte1[(paramInt + k)]));
      paramArrayOfByte1[(paramInt + k)] = ((byte)m);
      j = m >>> 8;
    }
  }
  
  private byte[] generateDerivedKey(int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte1 = new byte[this.v];
    byte[] arrayOfByte2 = new byte[paramInt2];
    for (int i = 0; i != arrayOfByte1.length; i++) {
      arrayOfByte1[i] = ((byte)paramInt1);
    }
    byte[] arrayOfByte3;
    int i3;
    if ((this.salt != null) && (this.salt.length != 0))
    {
      arrayOfByte3 = new byte[this.v * ((-1 + (this.salt.length + this.v)) / this.v)];
      i3 = 0;
    }
    while (i3 != arrayOfByte3.length)
    {
      arrayOfByte3[i3] = this.salt[(i3 % this.salt.length)];
      i3++;
      continue;
      arrayOfByte3 = new byte[0];
    }
    byte[] arrayOfByte4;
    int i2;
    if ((this.password != null) && (this.password.length != 0))
    {
      arrayOfByte4 = new byte[this.v * ((-1 + (this.password.length + this.v)) / this.v)];
      i2 = 0;
    }
    while (i2 != arrayOfByte4.length)
    {
      arrayOfByte4[i2] = this.password[(i2 % this.password.length)];
      i2++;
      continue;
      arrayOfByte4 = new byte[0];
    }
    byte[] arrayOfByte5 = new byte[arrayOfByte3.length + arrayOfByte4.length];
    System.arraycopy(arrayOfByte3, 0, arrayOfByte5, 0, arrayOfByte3.length);
    System.arraycopy(arrayOfByte4, 0, arrayOfByte5, arrayOfByte3.length, arrayOfByte4.length);
    byte[] arrayOfByte6 = new byte[this.v];
    int j = (-1 + (paramInt2 + this.u)) / this.u;
    int k = 1;
    if (k <= j)
    {
      byte[] arrayOfByte7 = new byte[this.u];
      this.digest.update(arrayOfByte1, 0, arrayOfByte1.length);
      this.digest.update(arrayOfByte5, 0, arrayOfByte5.length);
      this.digest.doFinal(arrayOfByte7, 0);
      for (int m = 1; m < this.iterationCount; m++)
      {
        this.digest.update(arrayOfByte7, 0, arrayOfByte7.length);
        this.digest.doFinal(arrayOfByte7, 0);
      }
      for (int n = 0; n != arrayOfByte6.length; n++) {
        arrayOfByte6[n] = arrayOfByte7[(n % arrayOfByte7.length)];
      }
      for (int i1 = 0; i1 != arrayOfByte5.length / this.v; i1++) {
        adjust(arrayOfByte5, i1 * this.v, arrayOfByte6);
      }
      if (k == j) {
        System.arraycopy(arrayOfByte7, 0, arrayOfByte2, (k - 1) * this.u, arrayOfByte2.length - (k - 1) * this.u);
      }
      for (;;)
      {
        k++;
        break;
        System.arraycopy(arrayOfByte7, 0, arrayOfByte2, (k - 1) * this.u, arrayOfByte7.length);
      }
    }
    return arrayOfByte2;
  }
  
  public CipherParameters generateDerivedMacParameters(int paramInt)
  {
    int i = paramInt / 8;
    return new KeyParameter(generateDerivedKey(3, i), 0, i);
  }
  
  public CipherParameters generateDerivedParameters(int paramInt)
  {
    int i = paramInt / 8;
    return new KeyParameter(generateDerivedKey(1, i), 0, i);
  }
  
  public CipherParameters generateDerivedParameters(int paramInt1, int paramInt2)
  {
    int i = paramInt1 / 8;
    int j = paramInt2 / 8;
    byte[] arrayOfByte1 = generateDerivedKey(1, i);
    byte[] arrayOfByte2 = generateDerivedKey(2, j);
    return new ParametersWithIV(new KeyParameter(arrayOfByte1, 0, i), arrayOfByte2, 0, j);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.PKCS12ParametersGenerator
 * JD-Core Version:    0.7.0.1
 */