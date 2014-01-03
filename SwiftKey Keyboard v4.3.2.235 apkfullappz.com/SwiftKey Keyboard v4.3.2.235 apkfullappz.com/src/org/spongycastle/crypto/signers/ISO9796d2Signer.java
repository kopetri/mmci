package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.util.Hashtable;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.SignerWithRecovery;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.util.Arrays;

public class ISO9796d2Signer
  implements SignerWithRecovery
{
  public static final int TRAILER_IMPLICIT = 188;
  public static final int TRAILER_RIPEMD128 = 13004;
  public static final int TRAILER_RIPEMD160 = 12748;
  public static final int TRAILER_SHA1 = 13260;
  public static final int TRAILER_SHA256 = 13516;
  public static final int TRAILER_SHA384 = 14028;
  public static final int TRAILER_SHA512 = 13772;
  public static final int TRAILER_WHIRLPOOL = 14284;
  private static Hashtable trailerMap;
  private byte[] block;
  private AsymmetricBlockCipher cipher;
  private Digest digest;
  private boolean fullMessage;
  private int keyBits;
  private byte[] mBuf;
  private int messageLength;
  private byte[] preBlock;
  private byte[] preSig;
  private byte[] recoveredMessage;
  private int trailer;
  
  static
  {
    Hashtable localHashtable = new Hashtable();
    trailerMap = localHashtable;
    localHashtable.put("RIPEMD128", new Integer(13004));
    trailerMap.put("RIPEMD160", new Integer(12748));
    trailerMap.put("SHA-1", new Integer(13260));
    trailerMap.put("SHA-256", new Integer(13516));
    trailerMap.put("SHA-384", new Integer(14028));
    trailerMap.put("SHA-512", new Integer(13772));
    trailerMap.put("Whirlpool", new Integer(14284));
  }
  
  public ISO9796d2Signer(AsymmetricBlockCipher paramAsymmetricBlockCipher, Digest paramDigest)
  {
    this(paramAsymmetricBlockCipher, paramDigest, false);
  }
  
  public ISO9796d2Signer(AsymmetricBlockCipher paramAsymmetricBlockCipher, Digest paramDigest, boolean paramBoolean)
  {
    this.cipher = paramAsymmetricBlockCipher;
    this.digest = paramDigest;
    if (paramBoolean)
    {
      this.trailer = 188;
      return;
    }
    Integer localInteger = (Integer)trailerMap.get(paramDigest.getAlgorithmName());
    if (localInteger != null)
    {
      this.trailer = localInteger.intValue();
      return;
    }
    throw new IllegalArgumentException("no valid trailer for digest");
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
    if (this.messageLength > this.mBuf.length)
    {
      if (this.mBuf.length > paramArrayOfByte2.length) {
        bool = false;
      }
      for (int j = 0; j != this.mBuf.length; j++) {
        if (paramArrayOfByte1[j] != paramArrayOfByte2[j]) {
          bool = false;
        }
      }
    }
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
  
  private boolean returnFalse(byte[] paramArrayOfByte)
  {
    clearBlock(this.mBuf);
    clearBlock(paramArrayOfByte);
    return false;
  }
  
  public byte[] generateSignature()
    throws CryptoException
  {
    int i = this.digest.getDigestSize();
    int j;
    int k;
    int n;
    int i1;
    if (this.trailer == 188)
    {
      j = 8;
      k = -1 + (this.block.length - i);
      this.digest.doFinal(this.block, k);
      this.block[(-1 + this.block.length)] = -68;
      int m = 4 + (j + 8 * (i + this.messageLength)) - this.keyBits;
      if (m <= 0) {
        break label231;
      }
      int i4 = this.messageLength - (m + 7) / 8;
      n = 96;
      i1 = k - i4;
      System.arraycopy(this.mBuf, 0, this.block, i1, i4);
    }
    for (;;)
    {
      label128:
      if (i1 - 1 > 0)
      {
        int i2 = i1 - 1;
        for (;;)
        {
          if (i2 != 0)
          {
            this.block[i2] = -69;
            i2--;
            continue;
            j = 16;
            k = -2 + (this.block.length - i);
            this.digest.doFinal(this.block, k);
            this.block[(-2 + this.block.length)] = ((byte)(this.trailer >>> 8));
            this.block[(-1 + this.block.length)] = ((byte)this.trailer);
            break;
            label231:
            n = 64;
            i1 = k - this.messageLength;
            System.arraycopy(this.mBuf, 0, this.block, i1, this.messageLength);
            break label128;
          }
        }
        byte[] arrayOfByte3 = this.block;
        int i3 = i1 - 1;
        arrayOfByte3[i3] = ((byte)(0x1 ^ arrayOfByte3[i3]));
        this.block[0] = 11;
        byte[] arrayOfByte4 = this.block;
        arrayOfByte4[0] = ((byte)(n | arrayOfByte4[0]));
      }
    }
    for (;;)
    {
      byte[] arrayOfByte2 = this.cipher.processBlock(this.block, 0, this.block.length);
      clearBlock(this.mBuf);
      clearBlock(this.block);
      return arrayOfByte2;
      this.block[0] = 10;
      byte[] arrayOfByte1 = this.block;
      arrayOfByte1[0] = ((byte)(n | arrayOfByte1[0]));
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
    RSAKeyParameters localRSAKeyParameters = (RSAKeyParameters)paramCipherParameters;
    this.cipher.init(paramBoolean, localRSAKeyParameters);
    this.keyBits = localRSAKeyParameters.getModulus().bitLength();
    this.block = new byte[(7 + this.keyBits) / 8];
    if (this.trailer == 188) {}
    for (this.mBuf = new byte[-2 + (this.block.length - this.digest.getDigestSize())];; this.mBuf = new byte[-3 + (this.block.length - this.digest.getDigestSize())])
    {
      reset();
      return;
    }
  }
  
  public void reset()
  {
    this.digest.reset();
    this.messageLength = 0;
    clearBlock(this.mBuf);
    if (this.recoveredMessage != null) {
      clearBlock(this.recoveredMessage);
    }
    this.recoveredMessage = null;
    this.fullMessage = false;
  }
  
  public void update(byte paramByte)
  {
    this.digest.update(paramByte);
    if ((this.preSig == null) && (this.messageLength < this.mBuf.length)) {
      this.mBuf[this.messageLength] = paramByte;
    }
    this.messageLength = (1 + this.messageLength);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
    if ((this.preSig == null) && (this.messageLength < this.mBuf.length)) {
      for (int i = 0; (i < paramInt2) && (i + this.messageLength < this.mBuf.length); i++) {
        this.mBuf[(i + this.messageLength)] = paramArrayOfByte[(paramInt1 + i)];
      }
    }
    this.messageLength = (paramInt2 + this.messageLength);
  }
  
  public void updateWithRecoveredMessage(byte[] paramArrayOfByte)
    throws InvalidCipherTextException
  {
    byte[] arrayOfByte = this.cipher.processBlock(paramArrayOfByte, 0, paramArrayOfByte.length);
    if ((0x40 ^ 0xC0 & arrayOfByte[0]) != 0) {
      throw new InvalidCipherTextException("malformed signature");
    }
    if ((0xC ^ 0xF & arrayOfByte[(-1 + arrayOfByte.length)]) != 0) {
      throw new InvalidCipherTextException("malformed signature");
    }
    if ((0xBC ^ 0xFF & arrayOfByte[(-1 + arrayOfByte.length)]) == 0) {}
    int k;
    for (int j = 1;; j = 2)
    {
      for (k = 0; (k != arrayOfByte.length) && ((0xA ^ 0xF & arrayOfByte[k]) != 0); k++) {}
      int i = (0xFF & arrayOfByte[(-2 + arrayOfByte.length)]) << 8 | 0xFF & arrayOfByte[(-1 + arrayOfByte.length)];
      Integer localInteger = (Integer)trailerMap.get(this.digest.getAlgorithmName());
      if (localInteger != null)
      {
        if (i != localInteger.intValue()) {
          throw new IllegalStateException("signer initialised with wrong digest for trailer " + i);
        }
      }
      else {
        throw new IllegalArgumentException("unrecognised hash in signature");
      }
    }
    int m = k + 1;
    int n = arrayOfByte.length - j - this.digest.getDigestSize();
    if (n - m <= 0) {
      throw new InvalidCipherTextException("malformed block");
    }
    if ((0x20 & arrayOfByte[0]) == 0)
    {
      this.fullMessage = true;
      this.recoveredMessage = new byte[n - m];
      System.arraycopy(arrayOfByte, m, this.recoveredMessage, 0, this.recoveredMessage.length);
    }
    for (;;)
    {
      this.preSig = paramArrayOfByte;
      this.preBlock = arrayOfByte;
      this.digest.update(this.recoveredMessage, 0, this.recoveredMessage.length);
      this.messageLength = this.recoveredMessage.length;
      return;
      this.fullMessage = false;
      this.recoveredMessage = new byte[n - m];
      System.arraycopy(arrayOfByte, m, this.recoveredMessage, 0, this.recoveredMessage.length);
    }
  }
  
  public boolean verifySignature(byte[] paramArrayOfByte)
  {
    int i;
    if (this.preSig == null) {
      i = 0;
    }
    byte[] arrayOfByte1;
    for (;;)
    {
      try
      {
        byte[] arrayOfByte3 = this.cipher.processBlock(paramArrayOfByte, 0, paramArrayOfByte.length);
        arrayOfByte1 = arrayOfByte3;
        if ((0x40 ^ 0xC0 & arrayOfByte1[0]) == 0) {
          break;
        }
        return returnFalse(arrayOfByte1);
      }
      catch (Exception localException)
      {
        return false;
      }
      if (!Arrays.areEqual(this.preSig, paramArrayOfByte)) {
        throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
      }
      i = 1;
      arrayOfByte1 = this.preBlock;
      this.preSig = null;
      this.preBlock = null;
    }
    if ((0xC ^ 0xF & arrayOfByte1[(-1 + arrayOfByte1.length)]) != 0) {
      return returnFalse(arrayOfByte1);
    }
    if ((0xBC ^ 0xFF & arrayOfByte1[(-1 + arrayOfByte1.length)]) == 0) {}
    int m;
    for (int k = 1;; k = 2)
    {
      for (m = 0; (m != arrayOfByte1.length) && ((0xA ^ 0xF & arrayOfByte1[m]) != 0); m++) {}
      int j = (0xFF & arrayOfByte1[(-2 + arrayOfByte1.length)]) << 8 | 0xFF & arrayOfByte1[(-1 + arrayOfByte1.length)];
      Integer localInteger = (Integer)trailerMap.get(this.digest.getAlgorithmName());
      if (localInteger != null)
      {
        if (j != localInteger.intValue()) {
          throw new IllegalStateException("signer initialised with wrong digest for trailer " + j);
        }
      }
      else {
        throw new IllegalArgumentException("unrecognised hash in signature");
      }
    }
    int n = m + 1;
    byte[] arrayOfByte2 = new byte[this.digest.getDigestSize()];
    int i1 = arrayOfByte1.length - k - arrayOfByte2.length;
    if (i1 - n <= 0) {
      return returnFalse(arrayOfByte1);
    }
    if ((0x20 & arrayOfByte1[0]) == 0)
    {
      this.fullMessage = true;
      if (this.messageLength > i1 - n) {
        return returnFalse(arrayOfByte1);
      }
      this.digest.reset();
      this.digest.update(arrayOfByte1, n, i1 - n);
      this.digest.doFinal(arrayOfByte2, 0);
      int i5 = 1;
      for (int i6 = 0; i6 != arrayOfByte2.length; i6++)
      {
        int i7 = i1 + i6;
        arrayOfByte1[i7] = ((byte)(arrayOfByte1[i7] ^ arrayOfByte2[i6]));
        if (arrayOfByte1[(i1 + i6)] != 0) {
          i5 = 0;
        }
      }
      if (i5 == 0) {
        return returnFalse(arrayOfByte1);
      }
      this.recoveredMessage = new byte[i1 - n];
      System.arraycopy(arrayOfByte1, n, this.recoveredMessage, 0, this.recoveredMessage.length);
    }
    while ((this.messageLength != 0) && (i == 0) && (!isSameAs(this.mBuf, this.recoveredMessage)))
    {
      return returnFalse(arrayOfByte1);
      this.fullMessage = false;
      this.digest.doFinal(arrayOfByte2, 0);
      int i2 = 1;
      for (int i3 = 0; i3 != arrayOfByte2.length; i3++)
      {
        int i4 = i1 + i3;
        arrayOfByte1[i4] = ((byte)(arrayOfByte1[i4] ^ arrayOfByte2[i3]));
        if (arrayOfByte1[(i1 + i3)] != 0) {
          i2 = 0;
        }
      }
      if (i2 == 0) {
        return returnFalse(arrayOfByte1);
      }
      this.recoveredMessage = new byte[i1 - n];
      System.arraycopy(arrayOfByte1, n, this.recoveredMessage, 0, this.recoveredMessage.length);
    }
    clearBlock(this.mBuf);
    clearBlock(arrayOfByte1);
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.ISO9796d2Signer
 * JD-Core Version:    0.7.0.1
 */