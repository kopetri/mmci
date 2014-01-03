package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class TlsBlockCipher
  implements TlsCipher
{
  protected TlsClientContext context;
  protected BlockCipher decryptCipher;
  protected BlockCipher encryptCipher;
  protected TlsMac readMac;
  protected TlsMac writeMac;
  
  public TlsBlockCipher(TlsClientContext paramTlsClientContext, BlockCipher paramBlockCipher1, BlockCipher paramBlockCipher2, Digest paramDigest1, Digest paramDigest2, int paramInt)
  {
    this.context = paramTlsClientContext;
    this.encryptCipher = paramBlockCipher1;
    this.decryptCipher = paramBlockCipher2;
    byte[] arrayOfByte = TlsUtils.calculateKeyBlock(paramTlsClientContext, paramInt * 2 + paramDigest1.getDigestSize() + paramDigest2.getDigestSize() + paramBlockCipher1.getBlockSize() + paramBlockCipher2.getBlockSize());
    this.writeMac = new TlsMac(paramTlsClientContext, paramDigest1, arrayOfByte, 0, paramDigest1.getDigestSize());
    int i = 0 + paramDigest1.getDigestSize();
    this.readMac = new TlsMac(paramTlsClientContext, paramDigest2, arrayOfByte, i, paramDigest2.getDigestSize());
    int j = i + paramDigest2.getDigestSize();
    initCipher(true, paramBlockCipher1, arrayOfByte, paramInt, j, j + paramInt * 2);
    int k = j + paramInt;
    initCipher(false, paramBlockCipher2, arrayOfByte, paramInt, k, k + paramInt + paramBlockCipher1.getBlockSize());
  }
  
  protected int chooseExtraPadBlocks(SecureRandom paramSecureRandom, int paramInt)
  {
    return Math.min(lowestBitSet(paramSecureRandom.nextInt()), paramInt);
  }
  
  public byte[] decodeCiphertext(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = 1 + this.readMac.getSize();
    int j = this.decryptCipher.getBlockSize();
    if (paramInt2 < i) {
      throw new TlsFatalAlert((short)50);
    }
    if (paramInt2 % j != 0) {
      throw new TlsFatalAlert((short)21);
    }
    int k = 0;
    while (k < paramInt2)
    {
      this.decryptCipher.processBlock(paramArrayOfByte, k + paramInt1, paramArrayOfByte, k + paramInt1);
      k += j;
    }
    int m = -1 + (paramInt1 + paramInt2);
    int n = paramArrayOfByte[m];
    int i1 = n & 0xFF;
    int i2;
    int i4;
    if (this.context.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion())
    {
      i2 = 1;
      int i3 = paramInt2 - i;
      if (i2 == 0) {
        i3 = Math.min(i3, j);
      }
      if (i1 <= i3) {
        break label256;
      }
      i4 = 1;
      i1 = 0;
    }
    int i7;
    for (;;)
    {
      i7 = paramInt2 - i - i1;
      byte[] arrayOfByte1 = this.readMac.calculateMac(paramShort, paramArrayOfByte, paramInt1, i7);
      byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
      System.arraycopy(paramArrayOfByte, paramInt1 + i7, arrayOfByte2, 0, arrayOfByte1.length);
      if (!Arrays.constantTimeAreEqual(arrayOfByte1, arrayOfByte2)) {
        i4 = 1;
      }
      if (i4 == 0) {
        break label317;
      }
      throw new TlsFatalAlert((short)20);
      i2 = 0;
      break;
      label256:
      i4 = 0;
      if (i2 != 0)
      {
        int i5 = 0;
        for (int i6 = m - i1; i6 < m; i6++) {
          i5 = (byte)(i5 | n ^ paramArrayOfByte[i6]);
        }
        i4 = 0;
        if (i5 != 0)
        {
          i4 = 1;
          i1 = 0;
        }
      }
    }
    label317:
    byte[] arrayOfByte3 = new byte[i7];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte3, 0, i7);
    return arrayOfByte3;
  }
  
  public byte[] encodePlaintext(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = this.encryptCipher.getBlockSize();
    int j = i - (1 + (paramInt2 + this.writeMac.getSize())) % i;
    int k = j;
    if (this.context.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion()) {}
    int n;
    byte[] arrayOfByte1;
    for (int m = 1;; m = 0)
    {
      if (m != 0)
      {
        int i4 = (255 - j) / i;
        k = j + i * chooseExtraPadBlocks(this.context.getSecureRandom(), i4);
      }
      n = 1 + (k + (paramInt2 + this.writeMac.getSize()));
      arrayOfByte1 = new byte[n];
      System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte1, 0, paramInt2);
      byte[] arrayOfByte2 = this.writeMac.calculateMac(paramShort, paramArrayOfByte, paramInt1, paramInt2);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, paramInt2, arrayOfByte2.length);
      int i1 = paramInt2 + arrayOfByte2.length;
      for (int i2 = 0; i2 <= k; i2++) {
        arrayOfByte1[(i2 + i1)] = ((byte)k);
      }
    }
    int i3 = 0;
    while (i3 < n)
    {
      this.encryptCipher.processBlock(arrayOfByte1, i3, arrayOfByte1, i3);
      i3 += i;
    }
    return arrayOfByte1;
  }
  
  public TlsMac getReadMac()
  {
    return this.readMac;
  }
  
  public TlsMac getWriteMac()
  {
    return this.writeMac;
  }
  
  protected void initCipher(boolean paramBoolean, BlockCipher paramBlockCipher, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    paramBlockCipher.init(paramBoolean, new ParametersWithIV(new KeyParameter(paramArrayOfByte, paramInt2, paramInt1), paramArrayOfByte, paramInt3, paramBlockCipher.getBlockSize()));
  }
  
  protected int lowestBitSet(int paramInt)
  {
    int i;
    if (paramInt == 0) {
      i = 32;
    }
    for (;;)
    {
      return i;
      i = 0;
      while ((paramInt & 0x1) == 0)
      {
        i++;
        paramInt >>= 1;
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsBlockCipher
 * JD-Core Version:    0.7.0.1
 */