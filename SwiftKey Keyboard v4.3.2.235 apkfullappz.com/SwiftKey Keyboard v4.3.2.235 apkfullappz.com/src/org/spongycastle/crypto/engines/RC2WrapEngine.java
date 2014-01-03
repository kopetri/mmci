package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class RC2WrapEngine
  implements Wrapper
{
  private static final byte[] IV2 = { 74, -35, -94, 44, 121, -24, 33, 5 };
  byte[] digest = new byte[20];
  private CBCBlockCipher engine;
  private boolean forWrapping;
  private byte[] iv;
  private CipherParameters param;
  private ParametersWithIV paramPlusIV;
  Digest sha1 = new SHA1Digest();
  private SecureRandom sr;
  
  private byte[] calculateCMSKeyChecksum(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[8];
    this.sha1.update(paramArrayOfByte, 0, paramArrayOfByte.length);
    this.sha1.doFinal(this.digest, 0);
    System.arraycopy(this.digest, 0, arrayOfByte, 0, 8);
    return arrayOfByte;
  }
  
  private boolean checkCMSKeyChecksum(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return Arrays.constantTimeAreEqual(calculateCMSKeyChecksum(paramArrayOfByte1), paramArrayOfByte2);
  }
  
  public String getAlgorithmName()
  {
    return "RC2";
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.forWrapping = paramBoolean;
    this.engine = new CBCBlockCipher(new RC2Engine());
    if ((paramCipherParameters instanceof ParametersWithRandom))
    {
      ParametersWithRandom localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
      this.sr = localParametersWithRandom.getRandom();
      paramCipherParameters = localParametersWithRandom.getParameters();
    }
    while ((paramCipherParameters instanceof ParametersWithIV))
    {
      this.paramPlusIV = ((ParametersWithIV)paramCipherParameters);
      this.iv = this.paramPlusIV.getIV();
      this.param = this.paramPlusIV.getParameters();
      if (this.forWrapping)
      {
        if ((this.iv != null) && (this.iv.length == 8)) {
          return;
        }
        throw new IllegalArgumentException("IV is not 8 octets");
        this.sr = new SecureRandom();
      }
      else
      {
        throw new IllegalArgumentException("You should not supply an IV for unwrapping");
      }
    }
    this.param = paramCipherParameters;
    if (this.forWrapping)
    {
      this.iv = new byte[8];
      this.sr.nextBytes(this.iv);
      this.paramPlusIV = new ParametersWithIV(this.param, this.iv);
    }
  }
  
  public byte[] unwrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    if (this.forWrapping) {
      throw new IllegalStateException("Not set for unwrapping");
    }
    if (paramArrayOfByte == null) {
      throw new InvalidCipherTextException("Null pointer as ciphertext");
    }
    if (paramInt2 % this.engine.getBlockSize() != 0) {
      throw new InvalidCipherTextException("Ciphertext not multiple of " + this.engine.getBlockSize());
    }
    ParametersWithIV localParametersWithIV = new ParametersWithIV(this.param, IV2);
    this.engine.init(false, localParametersWithIV);
    byte[] arrayOfByte1 = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte1, 0, paramInt2);
    for (int i = 0; i < arrayOfByte1.length / this.engine.getBlockSize(); i++)
    {
      int n = i * this.engine.getBlockSize();
      this.engine.processBlock(arrayOfByte1, n, arrayOfByte1, n);
    }
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
    for (int j = 0; j < arrayOfByte1.length; j++) {
      arrayOfByte2[j] = arrayOfByte1[(arrayOfByte1.length - (j + 1))];
    }
    this.iv = new byte[8];
    byte[] arrayOfByte3 = new byte[-8 + arrayOfByte2.length];
    System.arraycopy(arrayOfByte2, 0, this.iv, 0, 8);
    System.arraycopy(arrayOfByte2, 8, arrayOfByte3, 0, -8 + arrayOfByte2.length);
    this.paramPlusIV = new ParametersWithIV(this.param, this.iv);
    this.engine.init(false, this.paramPlusIV);
    byte[] arrayOfByte4 = new byte[arrayOfByte3.length];
    System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 0, arrayOfByte3.length);
    for (int k = 0; k < arrayOfByte4.length / this.engine.getBlockSize(); k++)
    {
      int m = k * this.engine.getBlockSize();
      this.engine.processBlock(arrayOfByte4, m, arrayOfByte4, m);
    }
    byte[] arrayOfByte5 = new byte[-8 + arrayOfByte4.length];
    byte[] arrayOfByte6 = new byte[8];
    System.arraycopy(arrayOfByte4, 0, arrayOfByte5, 0, -8 + arrayOfByte4.length);
    System.arraycopy(arrayOfByte4, -8 + arrayOfByte4.length, arrayOfByte6, 0, 8);
    if (!checkCMSKeyChecksum(arrayOfByte5, arrayOfByte6)) {
      throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
    }
    if (arrayOfByte5.length - (1 + (0xFF & arrayOfByte5[0])) > 7) {
      throw new InvalidCipherTextException("too many pad bytes (" + (arrayOfByte5.length - (1 + (0xFF & arrayOfByte5[0]))) + ")");
    }
    byte[] arrayOfByte7 = new byte[arrayOfByte5[0]];
    System.arraycopy(arrayOfByte5, 1, arrayOfByte7, 0, arrayOfByte7.length);
    return arrayOfByte7;
  }
  
  public byte[] wrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (!this.forWrapping) {
      throw new IllegalStateException("Not initialized for wrapping");
    }
    int i = paramInt2 + 1;
    if (i % 8 != 0) {
      i += 8 - i % 8;
    }
    byte[] arrayOfByte1 = new byte[i];
    arrayOfByte1[0] = ((byte)paramInt2);
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte1, 1, paramInt2);
    byte[] arrayOfByte2 = new byte[-1 + (arrayOfByte1.length - paramInt2)];
    if (arrayOfByte2.length > 0)
    {
      this.sr.nextBytes(arrayOfByte2);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, paramInt2 + 1, arrayOfByte2.length);
    }
    byte[] arrayOfByte3 = calculateCMSKeyChecksum(arrayOfByte1);
    byte[] arrayOfByte4 = new byte[arrayOfByte1.length + arrayOfByte3.length];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte4, 0, arrayOfByte1.length);
    System.arraycopy(arrayOfByte3, 0, arrayOfByte4, arrayOfByte1.length, arrayOfByte3.length);
    byte[] arrayOfByte5 = new byte[arrayOfByte4.length];
    System.arraycopy(arrayOfByte4, 0, arrayOfByte5, 0, arrayOfByte4.length);
    int j = arrayOfByte4.length / this.engine.getBlockSize();
    if (arrayOfByte4.length % this.engine.getBlockSize() != 0) {
      throw new IllegalStateException("Not multiple of block length");
    }
    this.engine.init(true, this.paramPlusIV);
    for (int k = 0; k < j; k++)
    {
      int i2 = k * this.engine.getBlockSize();
      this.engine.processBlock(arrayOfByte5, i2, arrayOfByte5, i2);
    }
    byte[] arrayOfByte6 = new byte[this.iv.length + arrayOfByte5.length];
    System.arraycopy(this.iv, 0, arrayOfByte6, 0, this.iv.length);
    System.arraycopy(arrayOfByte5, 0, arrayOfByte6, this.iv.length, arrayOfByte5.length);
    byte[] arrayOfByte7 = new byte[arrayOfByte6.length];
    for (int m = 0; m < arrayOfByte6.length; m++) {
      arrayOfByte7[m] = arrayOfByte6[(arrayOfByte6.length - (m + 1))];
    }
    ParametersWithIV localParametersWithIV = new ParametersWithIV(this.param, IV2);
    this.engine.init(true, localParametersWithIV);
    for (int n = 0; n < j + 1; n++)
    {
      int i1 = n * this.engine.getBlockSize();
      this.engine.processBlock(arrayOfByte7, i1, arrayOfByte7, i1);
    }
    return arrayOfByte7;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RC2WrapEngine
 * JD-Core Version:    0.7.0.1
 */