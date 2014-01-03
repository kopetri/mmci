package org.spongycastle.crypto.encodings;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class ISO9796d1Encoding
  implements AsymmetricBlockCipher
{
  private static final BigInteger SIX;
  private static final BigInteger SIXTEEN = BigInteger.valueOf(16L);
  private static byte[] inverse = { 8, 15, 6, 1, 5, 2, 11, 12, 3, 4, 13, 10, 14, 9, 0, 7 };
  private static byte[] shadows;
  private int bitSize;
  private AsymmetricBlockCipher engine;
  private boolean forEncryption;
  private BigInteger modulus;
  private int padBits = 0;
  
  static
  {
    SIX = BigInteger.valueOf(6L);
    shadows = new byte[] { 14, 3, 5, 8, 9, 4, 2, 15, 0, 13, 11, 6, 7, 10, 12, 1 };
  }
  
  public ISO9796d1Encoding(AsymmetricBlockCipher paramAsymmetricBlockCipher)
  {
    this.engine = paramAsymmetricBlockCipher;
  }
  
  private static byte[] convertOutputDecryptOnly(BigInteger paramBigInteger)
  {
    byte[] arrayOfByte1 = paramBigInteger.toByteArray();
    if (arrayOfByte1[0] == 0)
    {
      byte[] arrayOfByte2 = new byte[-1 + arrayOfByte1.length];
      System.arraycopy(arrayOfByte1, 1, arrayOfByte2, 0, arrayOfByte2.length);
      return arrayOfByte2;
    }
    return arrayOfByte1;
  }
  
  private byte[] decodeBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    byte[] arrayOfByte1 = this.engine.processBlock(paramArrayOfByte, paramInt1, paramInt2);
    int i = 1;
    int j = (13 + this.bitSize) / 16;
    BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte1);
    if (localBigInteger1.mod(SIXTEEN).equals(SIX)) {}
    byte[] arrayOfByte2;
    for (BigInteger localBigInteger2 = localBigInteger1;; localBigInteger2 = this.modulus.subtract(localBigInteger1))
    {
      arrayOfByte2 = convertOutputDecryptOnly(localBigInteger2);
      if ((0xF & arrayOfByte2[(-1 + arrayOfByte2.length)]) == 6) {
        break label143;
      }
      throw new InvalidCipherTextException("invalid forcing byte in block");
      if (!this.modulus.subtract(localBigInteger1).mod(SIXTEEN).equals(SIX)) {
        break;
      }
    }
    throw new InvalidCipherTextException("resulting integer iS or (modulus - iS) is not congruent to 6 mod 16");
    label143:
    arrayOfByte2[(-1 + arrayOfByte2.length)] = ((byte)((0xFF & arrayOfByte2[(-1 + arrayOfByte2.length)]) >>> 4 | inverse[((0xFF & arrayOfByte2[(-2 + arrayOfByte2.length)]) >> 4)] << 4));
    arrayOfByte2[0] = ((byte)(shadows[((0xFF & arrayOfByte2[1]) >>> 4)] << 4 | shadows[(0xF & arrayOfByte2[1])]));
    int k = 0;
    int m = 0;
    int n = -1 + arrayOfByte2.length;
    while (n >= arrayOfByte2.length - j * 2)
    {
      int i2 = shadows[((0xFF & arrayOfByte2[n]) >>> 4)] << 4 | shadows[(0xF & arrayOfByte2[n])];
      if ((0xFF & (i2 ^ arrayOfByte2[(n - 1)])) != 0)
      {
        if (k == 0)
        {
          k = 1;
          i = 0xFF & (i2 ^ arrayOfByte2[(n - 1)]);
          m = n - 1;
        }
      }
      else
      {
        n -= 2;
        continue;
      }
      throw new InvalidCipherTextException("invalid tsums in block");
    }
    arrayOfByte2[m] = 0;
    byte[] arrayOfByte3 = new byte[(arrayOfByte2.length - m) / 2];
    for (int i1 = 0; i1 < arrayOfByte3.length; i1++) {
      arrayOfByte3[i1] = arrayOfByte2[(1 + (m + i1 * 2))];
    }
    this.padBits = (i - 1);
    return arrayOfByte3;
  }
  
  private byte[] encodeBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    byte[] arrayOfByte = new byte[(7 + this.bitSize) / 8];
    int i = 1 + this.padBits;
    int j = (13 + this.bitSize) / 16;
    int k = 0;
    if (k < j)
    {
      if (k > j - paramInt2) {
        System.arraycopy(paramArrayOfByte, paramInt1 + paramInt2 - (j - k), arrayOfByte, arrayOfByte.length - j, j - k);
      }
      for (;;)
      {
        k += paramInt2;
        break;
        System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, arrayOfByte.length - (k + paramInt2), paramInt2);
      }
    }
    for (int m = arrayOfByte.length - j * 2; m != arrayOfByte.length; m += 2)
    {
      int i3 = arrayOfByte[(arrayOfByte.length - j + m / 2)];
      arrayOfByte[m] = ((byte)(shadows[((i3 & 0xFF) >>> 4)] << 4 | shadows[(i3 & 0xF)]));
      arrayOfByte[(m + 1)] = i3;
    }
    int n = arrayOfByte.length - paramInt2 * 2;
    arrayOfByte[n] = ((byte)(i ^ arrayOfByte[n]));
    arrayOfByte[(-1 + arrayOfByte.length)] = ((byte)(0x6 | arrayOfByte[(-1 + arrayOfByte.length)] << 4));
    int i1 = 8 - (-1 + this.bitSize) % 8;
    int i2 = 0;
    if (i1 != 8)
    {
      arrayOfByte[0] = ((byte)(arrayOfByte[0] & 255 >>> i1));
      arrayOfByte[0] = ((byte)(arrayOfByte[0] | 128 >>> i1));
    }
    for (;;)
    {
      return this.engine.processBlock(arrayOfByte, i2, arrayOfByte.length - i2);
      arrayOfByte[0] = 0;
      arrayOfByte[1] = ((byte)(0x80 | arrayOfByte[1]));
      i2 = 1;
    }
  }
  
  public int getInputBlockSize()
  {
    int i = this.engine.getInputBlockSize();
    if (this.forEncryption) {
      i = (i + 1) / 2;
    }
    return i;
  }
  
  public int getOutputBlockSize()
  {
    int i = this.engine.getOutputBlockSize();
    if (this.forEncryption) {
      return i;
    }
    return (i + 1) / 2;
  }
  
  public int getPadBits()
  {
    return this.padBits;
  }
  
  public AsymmetricBlockCipher getUnderlyingCipher()
  {
    return this.engine;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof ParametersWithRandom)) {}
    for (RSAKeyParameters localRSAKeyParameters = (RSAKeyParameters)((ParametersWithRandom)paramCipherParameters).getParameters();; localRSAKeyParameters = (RSAKeyParameters)paramCipherParameters)
    {
      this.engine.init(paramBoolean, paramCipherParameters);
      this.modulus = localRSAKeyParameters.getModulus();
      this.bitSize = this.modulus.bitLength();
      this.forEncryption = paramBoolean;
      return;
    }
  }
  
  public byte[] processBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    if (this.forEncryption) {
      return encodeBlock(paramArrayOfByte, paramInt1, paramInt2);
    }
    return decodeBlock(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public void setPadBits(int paramInt)
  {
    if (paramInt > 7) {
      throw new IllegalArgumentException("padBits > 7");
    }
    this.padBits = paramInt;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.encodings.ISO9796d1Encoding
 * JD-Core Version:    0.7.0.1
 */