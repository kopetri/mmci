package org.spongycastle.jce.provider;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.KDFParameters;

public class BrokenKDF2BytesGenerator
  implements DerivationFunction
{
  private Digest digest;
  private byte[] iv;
  private byte[] shared;
  
  public BrokenKDF2BytesGenerator(Digest paramDigest)
  {
    this.digest = paramDigest;
  }
  
  public int generateBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws DataLengthException, IllegalArgumentException
  {
    if (paramArrayOfByte.length - paramInt2 < paramInt1) {
      throw new DataLengthException("output buffer too small");
    }
    long l = paramInt2 * 8;
    if (l > 29L * (8 * this.digest.getDigestSize())) {
      new IllegalArgumentException("Output length to large");
    }
    int i = (int)(l / this.digest.getDigestSize());
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    int j = 1;
    if (j <= i)
    {
      this.digest.update(this.shared, 0, this.shared.length);
      this.digest.update((byte)(j & 0xFF));
      this.digest.update((byte)(0xFF & j >> 8));
      this.digest.update((byte)(0xFF & j >> 16));
      this.digest.update((byte)(0xFF & j >> 24));
      this.digest.update(this.iv, 0, this.iv.length);
      this.digest.doFinal(arrayOfByte, 0);
      if (paramInt2 - paramInt1 > arrayOfByte.length)
      {
        System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt1, arrayOfByte.length);
        paramInt1 += arrayOfByte.length;
      }
      for (;;)
      {
        j++;
        break;
        System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt1, paramInt2 - paramInt1);
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
    if (!(paramDerivationParameters instanceof KDFParameters)) {
      throw new IllegalArgumentException("KDF parameters required for KDF2Generator");
    }
    KDFParameters localKDFParameters = (KDFParameters)paramDerivationParameters;
    this.shared = localKDFParameters.getSharedSecret();
    this.iv = localKDFParameters.getIV();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.BrokenKDF2BytesGenerator
 * JD-Core Version:    0.7.0.1
 */