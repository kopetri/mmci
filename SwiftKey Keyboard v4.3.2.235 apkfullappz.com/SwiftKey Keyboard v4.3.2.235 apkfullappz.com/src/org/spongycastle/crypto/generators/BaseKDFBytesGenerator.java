package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.ISO18033KDFParameters;
import org.spongycastle.crypto.params.KDFParameters;

public class BaseKDFBytesGenerator
  implements DerivationFunction
{
  private int counterStart;
  private Digest digest;
  private byte[] iv;
  private byte[] shared;
  
  protected BaseKDFBytesGenerator(int paramInt, Digest paramDigest)
  {
    this.counterStart = paramInt;
    this.digest = paramDigest;
  }
  
  public int generateBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws DataLengthException, IllegalArgumentException
  {
    if (paramArrayOfByte.length - paramInt2 < paramInt1) {
      throw new DataLengthException("output buffer too small");
    }
    long l = paramInt2;
    int i = this.digest.getDigestSize();
    if (l > 8589934591L) {
      throw new IllegalArgumentException("Output length too large");
    }
    int j = (int)((l + i - 1L) / i);
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    int k = this.counterStart;
    int m = 0;
    if (m < j)
    {
      this.digest.update(this.shared, 0, this.shared.length);
      this.digest.update((byte)(k >> 24));
      this.digest.update((byte)(k >> 16));
      this.digest.update((byte)(k >> 8));
      this.digest.update((byte)k);
      if (this.iv != null) {
        this.digest.update(this.iv, 0, this.iv.length);
      }
      this.digest.doFinal(arrayOfByte, 0);
      if (paramInt2 > i)
      {
        System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt1, i);
        paramInt1 += i;
        paramInt2 -= i;
      }
      for (;;)
      {
        k++;
        m++;
        break;
        System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt1, paramInt2);
      }
    }
    this.digest.reset();
    return paramInt2;
  }
  
  public Digest getDigest()
  {
    return this.digest;
  }
  
  public void init(DerivationParameters paramDerivationParameters)
  {
    if ((paramDerivationParameters instanceof KDFParameters))
    {
      KDFParameters localKDFParameters = (KDFParameters)paramDerivationParameters;
      this.shared = localKDFParameters.getSharedSecret();
      this.iv = localKDFParameters.getIV();
      return;
    }
    if ((paramDerivationParameters instanceof ISO18033KDFParameters))
    {
      this.shared = ((ISO18033KDFParameters)paramDerivationParameters).getSeed();
      this.iv = null;
      return;
    }
    throw new IllegalArgumentException("KDF parameters required for KDF2Generator");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.BaseKDFBytesGenerator
 * JD-Core Version:    0.7.0.1
 */