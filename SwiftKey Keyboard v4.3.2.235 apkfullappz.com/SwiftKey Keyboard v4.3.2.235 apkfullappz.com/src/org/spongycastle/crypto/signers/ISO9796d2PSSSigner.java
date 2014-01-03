package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.SignerWithRecovery;
import org.spongycastle.crypto.digests.RIPEMD128Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSalt;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class ISO9796d2PSSSigner
  implements SignerWithRecovery
{
  public static final int TRAILER_IMPLICIT = 188;
  public static final int TRAILER_RIPEMD128 = 13004;
  public static final int TRAILER_RIPEMD160 = 12748;
  public static final int TRAILER_SHA1 = 13260;
  private byte[] block;
  private AsymmetricBlockCipher cipher;
  private Digest digest;
  private boolean fullMessage;
  private int hLen;
  private int keyBits;
  private byte[] mBuf;
  private int messageLength;
  private SecureRandom random;
  private byte[] recoveredMessage;
  private int saltLength;
  private byte[] standardSalt;
  private int trailer;
  
  public ISO9796d2PSSSigner(AsymmetricBlockCipher paramAsymmetricBlockCipher, Digest paramDigest, int paramInt)
  {
    this(paramAsymmetricBlockCipher, paramDigest, paramInt, false);
  }
  
  public ISO9796d2PSSSigner(AsymmetricBlockCipher paramAsymmetricBlockCipher, Digest paramDigest, int paramInt, boolean paramBoolean)
  {
    this.cipher = paramAsymmetricBlockCipher;
    this.digest = paramDigest;
    this.hLen = paramDigest.getDigestSize();
    this.saltLength = paramInt;
    if (paramBoolean)
    {
      this.trailer = 188;
      return;
    }
    if ((paramDigest instanceof SHA1Digest))
    {
      this.trailer = 13260;
      return;
    }
    if ((paramDigest instanceof RIPEMD160Digest))
    {
      this.trailer = 12748;
      return;
    }
    if ((paramDigest instanceof RIPEMD128Digest))
    {
      this.trailer = 13004;
      return;
    }
    throw new IllegalArgumentException("no valid trailer for digest");
  }
  
  private void ItoOSP(int paramInt, byte[] paramArrayOfByte)
  {
    paramArrayOfByte[0] = ((byte)(paramInt >>> 24));
    paramArrayOfByte[1] = ((byte)(paramInt >>> 16));
    paramArrayOfByte[2] = ((byte)(paramInt >>> 8));
    paramArrayOfByte[3] = ((byte)(paramInt >>> 0));
  }
  
  private void LtoOSP(long paramLong, byte[] paramArrayOfByte)
  {
    paramArrayOfByte[0] = ((byte)(int)(paramLong >>> 56));
    paramArrayOfByte[1] = ((byte)(int)(paramLong >>> 48));
    paramArrayOfByte[2] = ((byte)(int)(paramLong >>> 40));
    paramArrayOfByte[3] = ((byte)(int)(paramLong >>> 32));
    paramArrayOfByte[4] = ((byte)(int)(paramLong >>> 24));
    paramArrayOfByte[5] = ((byte)(int)(paramLong >>> 16));
    paramArrayOfByte[6] = ((byte)(int)(paramLong >>> 8));
    paramArrayOfByte[7] = ((byte)(int)(paramLong >>> 0));
  }
  
  private void clearBlock(byte[] paramArrayOfByte)
  {
    for (int i = 0; i != paramArrayOfByte.length; i++) {
      paramArrayOfByte[i] = 0;
    }
  }
  
  private boolean isSameAs(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    boolean bool = true;
    if (this.messageLength != paramArrayOfByte2.length) {
      bool = false;
    }
    for (int i = 0; i != paramArrayOfByte2.length; i++) {
      if (paramArrayOfByte1[i] != paramArrayOfByte2[i]) {
        bool = false;
      }
    }
    return bool;
  }
  
  private byte[] maskGeneratorFunction1(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    byte[] arrayOfByte1 = new byte[paramInt3];
    byte[] arrayOfByte2 = new byte[this.hLen];
    byte[] arrayOfByte3 = new byte[4];
    int i = 0;
    this.digest.reset();
    while (i < paramInt3 / this.hLen)
    {
      ItoOSP(i, arrayOfByte3);
      this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
      this.digest.update(arrayOfByte3, 0, arrayOfByte3.length);
      this.digest.doFinal(arrayOfByte2, 0);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, i * this.hLen, this.hLen);
      i++;
    }
    if (i * this.hLen < paramInt3)
    {
      ItoOSP(i, arrayOfByte3);
      this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
      this.digest.update(arrayOfByte3, 0, arrayOfByte3.length);
      this.digest.doFinal(arrayOfByte2, 0);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, i * this.hLen, arrayOfByte1.length - i * this.hLen);
    }
    return arrayOfByte1;
  }
  
  public byte[] generateSignature()
    throws CryptoException
  {
    byte[] arrayOfByte1 = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte1, 0);
    byte[] arrayOfByte2 = new byte[8];
    LtoOSP(8 * this.messageLength, arrayOfByte2);
    this.digest.update(arrayOfByte2, 0, arrayOfByte2.length);
    this.digest.update(this.mBuf, 0, this.messageLength);
    this.digest.update(arrayOfByte1, 0, arrayOfByte1.length);
    byte[] arrayOfByte3;
    if (this.standardSalt != null) {
      arrayOfByte3 = this.standardSalt;
    }
    byte[] arrayOfByte4;
    int i;
    for (;;)
    {
      this.digest.update(arrayOfByte3, 0, arrayOfByte3.length);
      arrayOfByte4 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(arrayOfByte4, 0);
      i = 2;
      if (this.trailer == 188) {
        i = 1;
      }
      int j = -1 + (this.block.length - this.messageLength - arrayOfByte3.length - this.hLen - i);
      this.block[j] = 1;
      System.arraycopy(this.mBuf, 0, this.block, j + 1, this.messageLength);
      System.arraycopy(arrayOfByte3, 0, this.block, j + 1 + this.messageLength, arrayOfByte3.length);
      byte[] arrayOfByte5 = maskGeneratorFunction1(arrayOfByte4, 0, arrayOfByte4.length, this.block.length - this.hLen - i);
      for (int k = 0; k != arrayOfByte5.length; k++)
      {
        byte[] arrayOfByte8 = this.block;
        arrayOfByte8[k] = ((byte)(arrayOfByte8[k] ^ arrayOfByte5[k]));
      }
      arrayOfByte3 = new byte[this.saltLength];
      this.random.nextBytes(arrayOfByte3);
    }
    System.arraycopy(arrayOfByte4, 0, this.block, this.block.length - this.hLen - i, this.hLen);
    if (this.trailer == 188) {
      this.block[(-1 + this.block.length)] = -68;
    }
    for (;;)
    {
      byte[] arrayOfByte6 = this.block;
      arrayOfByte6[0] = ((byte)(0x7F & arrayOfByte6[0]));
      byte[] arrayOfByte7 = this.cipher.processBlock(this.block, 0, this.block.length);
      clearBlock(this.mBuf);
      clearBlock(this.block);
      this.messageLength = 0;
      return arrayOfByte7;
      this.block[(-2 + this.block.length)] = ((byte)(this.trailer >>> 8));
      this.block[(-1 + this.block.length)] = ((byte)this.trailer);
    }
  }
  
  public byte[] getRecoveredMessage()
  {
    return this.recoveredMessage;
  }
  
  public boolean hasFullMessage()
  {
    return this.fullMessage;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    int i = this.saltLength;
    RSAKeyParameters localRSAKeyParameters;
    if ((paramCipherParameters instanceof ParametersWithRandom))
    {
      ParametersWithRandom localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
      localRSAKeyParameters = (RSAKeyParameters)localParametersWithRandom.getParameters();
      if (paramBoolean) {
        this.random = localParametersWithRandom.getRandom();
      }
      this.cipher.init(paramBoolean, localRSAKeyParameters);
      this.keyBits = localRSAKeyParameters.getModulus().bitLength();
      this.block = new byte[(7 + this.keyBits) / 8];
      if (this.trailer != 188) {
        break label207;
      }
    }
    label207:
    for (this.mBuf = new byte[-1 + (-1 + (this.block.length - this.digest.getDigestSize() - i))];; this.mBuf = new byte[-2 + (-1 + (this.block.length - this.digest.getDigestSize() - i))])
    {
      reset();
      return;
      if ((paramCipherParameters instanceof ParametersWithSalt))
      {
        ParametersWithSalt localParametersWithSalt = (ParametersWithSalt)paramCipherParameters;
        localRSAKeyParameters = (RSAKeyParameters)localParametersWithSalt.getParameters();
        this.standardSalt = localParametersWithSalt.getSalt();
        i = this.standardSalt.length;
        if (this.standardSalt.length == this.saltLength) {
          break;
        }
        throw new IllegalArgumentException("Fixed salt is of wrong length");
      }
      localRSAKeyParameters = (RSAKeyParameters)paramCipherParameters;
      if (!paramBoolean) {
        break;
      }
      this.random = new SecureRandom();
      break;
    }
  }
  
  public void reset()
  {
    this.digest.reset();
    this.messageLength = 0;
    if (this.mBuf != null) {
      clearBlock(this.mBuf);
    }
    if (this.recoveredMessage != null)
    {
      clearBlock(this.recoveredMessage);
      this.recoveredMessage = null;
    }
    this.fullMessage = false;
  }
  
  public void update(byte paramByte)
  {
    if (this.messageLength < this.mBuf.length)
    {
      byte[] arrayOfByte = this.mBuf;
      int i = this.messageLength;
      this.messageLength = (i + 1);
      arrayOfByte[i] = paramByte;
      return;
    }
    this.digest.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    while ((paramInt2 > 0) && (this.messageLength < this.mBuf.length))
    {
      update(paramArrayOfByte[paramInt1]);
      paramInt1++;
      paramInt2--;
    }
    if (paramInt2 > 0) {
      this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
  
  public void updateWithRecoveredMessage(byte[] paramArrayOfByte)
    throws InvalidCipherTextException
  {
    throw new RuntimeException("not implemented");
  }
  
  public boolean verifySignature(byte[] paramArrayOfByte)
  {
    Object localObject;
    int i;
    byte[] arrayOfByte2;
    byte[] arrayOfByte3;
    for (;;)
    {
      try
      {
        byte[] arrayOfByte1 = this.cipher.processBlock(paramArrayOfByte, 0, paramArrayOfByte.length);
        localObject = arrayOfByte1;
        if (localObject.length < (7 + this.keyBits) / 8)
        {
          byte[] arrayOfByte6 = new byte[(7 + this.keyBits) / 8];
          System.arraycopy(localObject, 0, arrayOfByte6, arrayOfByte6.length - localObject.length, localObject.length);
          clearBlock((byte[])localObject);
          localObject = arrayOfByte6;
        }
        if ((0xBC ^ 0xFF & localObject[(-1 + localObject.length)]) == 0)
        {
          i = 1;
          arrayOfByte2 = new byte[this.hLen];
          this.digest.doFinal(arrayOfByte2, 0);
          arrayOfByte3 = maskGeneratorFunction1((byte[])localObject, localObject.length - this.hLen - i, this.hLen, localObject.length - this.hLen - i);
          int j = 0;
          if (j == arrayOfByte3.length) {
            break;
          }
          localObject[j] = ((byte)(localObject[j] ^ arrayOfByte3[j]));
          j++;
          continue;
        }
        switch ((0xFF & localObject[(-2 + localObject.length)]) << 8 | 0xFF & localObject[(-1 + localObject.length)])
        {
        }
      }
      catch (Exception localException)
      {
        return false;
      }
      throw new IllegalArgumentException("unrecognised hash in signature");
      if (!(this.digest instanceof RIPEMD160Digest))
      {
        throw new IllegalStateException("signer should be initialised with RIPEMD160");
        if (!(this.digest instanceof SHA1Digest))
        {
          throw new IllegalStateException("signer should be initialised with SHA1");
          if (!(this.digest instanceof RIPEMD128Digest)) {
            throw new IllegalStateException("signer should be initialised with RIPEMD128");
          }
        }
      }
      i = 2;
    }
    localObject[0] = ((byte)(0x7F & localObject[0]));
    for (int k = 0; (k != localObject.length) && (localObject[k] != 1); k++) {}
    int m = k + 1;
    if (m >= localObject.length)
    {
      clearBlock((byte[])localObject);
      return false;
    }
    if (m > 1) {}
    byte[] arrayOfByte5;
    int i1;
    for (boolean bool = true;; bool = false)
    {
      this.fullMessage = bool;
      this.recoveredMessage = new byte[arrayOfByte3.length - m - this.saltLength];
      System.arraycopy(localObject, m, this.recoveredMessage, 0, this.recoveredMessage.length);
      byte[] arrayOfByte4 = new byte[8];
      LtoOSP(8 * this.recoveredMessage.length, arrayOfByte4);
      this.digest.update(arrayOfByte4, 0, arrayOfByte4.length);
      if (this.recoveredMessage.length != 0) {
        this.digest.update(this.recoveredMessage, 0, this.recoveredMessage.length);
      }
      this.digest.update(arrayOfByte2, 0, arrayOfByte2.length);
      this.digest.update((byte[])localObject, m + this.recoveredMessage.length, this.saltLength);
      arrayOfByte5 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(arrayOfByte5, 0);
      int n = localObject.length - i - arrayOfByte5.length;
      i1 = 1;
      for (int i2 = 0; i2 != arrayOfByte5.length; i2++) {
        if (arrayOfByte5[i2] != localObject[(n + i2)]) {
          i1 = 0;
        }
      }
    }
    clearBlock((byte[])localObject);
    clearBlock(arrayOfByte5);
    if (i1 == 0)
    {
      this.fullMessage = false;
      clearBlock(this.recoveredMessage);
      return false;
    }
    if (this.messageLength != 0)
    {
      if (!isSameAs(this.mBuf, this.recoveredMessage))
      {
        clearBlock(this.mBuf);
        return false;
      }
      this.messageLength = 0;
    }
    clearBlock(this.mBuf);
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.ISO9796d2PSSSigner
 * JD-Core Version:    0.7.0.1
 */