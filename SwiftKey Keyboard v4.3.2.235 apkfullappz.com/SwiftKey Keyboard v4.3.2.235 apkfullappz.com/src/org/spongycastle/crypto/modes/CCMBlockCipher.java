package org.spongycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class CCMBlockCipher
  implements AEADBlockCipher
{
  private byte[] associatedText;
  private int blockSize;
  private BlockCipher cipher;
  private ByteArrayOutputStream data = new ByteArrayOutputStream();
  private boolean forEncryption;
  private CipherParameters keyParam;
  private byte[] macBlock;
  private int macSize;
  private byte[] nonce;
  
  public CCMBlockCipher(BlockCipher paramBlockCipher)
  {
    this.cipher = paramBlockCipher;
    this.blockSize = paramBlockCipher.getBlockSize();
    this.macBlock = new byte[this.blockSize];
    if (this.blockSize != 16) {
      throw new IllegalArgumentException("cipher required with a block size of 16.");
    }
  }
  
  private int calculateMac(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
  {
    CBCBlockCipherMac localCBCBlockCipherMac = new CBCBlockCipherMac(this.cipher, 8 * this.macSize);
    localCBCBlockCipherMac.init(this.keyParam);
    byte[] arrayOfByte = new byte[16];
    if (hasAssociatedText()) {
      arrayOfByte[0] = ((byte)(0x40 | arrayOfByte[0]));
    }
    arrayOfByte[0] = ((byte)(arrayOfByte[0] | (0x7 & (-2 + localCBCBlockCipherMac.getMacSize()) / 2) << 3));
    arrayOfByte[0] = ((byte)(arrayOfByte[0] | 0x7 & -1 + (15 - this.nonce.length)));
    System.arraycopy(this.nonce, 0, arrayOfByte, 1, this.nonce.length);
    int i = paramInt2;
    for (int j = 1; i > 0; j++)
    {
      arrayOfByte[(arrayOfByte.length - j)] = ((byte)(i & 0xFF));
      i >>>= 8;
    }
    localCBCBlockCipherMac.update(arrayOfByte, 0, arrayOfByte.length);
    if (hasAssociatedText())
    {
      if (this.associatedText.length < 65280)
      {
        localCBCBlockCipherMac.update((byte)(this.associatedText.length >> 8));
        localCBCBlockCipherMac.update((byte)this.associatedText.length);
      }
      for (int k = 2;; k = 6)
      {
        localCBCBlockCipherMac.update(this.associatedText, 0, this.associatedText.length);
        int m = (k + this.associatedText.length) % 16;
        if (m == 0) {
          break;
        }
        for (int n = 0; n != 16 - m; n++) {
          localCBCBlockCipherMac.update((byte)0);
        }
        localCBCBlockCipherMac.update((byte)-1);
        localCBCBlockCipherMac.update((byte)-2);
        localCBCBlockCipherMac.update((byte)(this.associatedText.length >> 24));
        localCBCBlockCipherMac.update((byte)(this.associatedText.length >> 16));
        localCBCBlockCipherMac.update((byte)(this.associatedText.length >> 8));
        localCBCBlockCipherMac.update((byte)this.associatedText.length);
      }
    }
    localCBCBlockCipherMac.update(paramArrayOfByte1, paramInt1, paramInt2);
    return localCBCBlockCipherMac.doFinal(paramArrayOfByte2, 0);
  }
  
  private boolean hasAssociatedText()
  {
    return (this.associatedText != null) && (this.associatedText.length != 0);
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
    throws IllegalStateException, InvalidCipherTextException
  {
    byte[] arrayOfByte1 = this.data.toByteArray();
    byte[] arrayOfByte2 = processPacket(arrayOfByte1, 0, arrayOfByte1.length);
    System.arraycopy(arrayOfByte2, 0, paramArrayOfByte, paramInt, arrayOfByte2.length);
    reset();
    return arrayOfByte2.length;
  }
  
  public String getAlgorithmName()
  {
    return this.cipher.getAlgorithmName() + "/CCM";
  }
  
  public byte[] getMac()
  {
    byte[] arrayOfByte = new byte[this.macSize];
    System.arraycopy(this.macBlock, 0, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }
  
  public int getOutputSize(int paramInt)
  {
    if (this.forEncryption) {
      return paramInt + this.data.size() + this.macSize;
    }
    return paramInt + this.data.size() - this.macSize;
  }
  
  public BlockCipher getUnderlyingCipher()
  {
    return this.cipher;
  }
  
  public int getUpdateOutputSize(int paramInt)
  {
    return 0;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
    throws IllegalArgumentException
  {
    this.forEncryption = paramBoolean;
    if ((paramCipherParameters instanceof AEADParameters))
    {
      AEADParameters localAEADParameters = (AEADParameters)paramCipherParameters;
      this.nonce = localAEADParameters.getNonce();
      this.associatedText = localAEADParameters.getAssociatedText();
      this.macSize = (localAEADParameters.getMacSize() / 8);
      this.keyParam = localAEADParameters.getKey();
      return;
    }
    if ((paramCipherParameters instanceof ParametersWithIV))
    {
      ParametersWithIV localParametersWithIV = (ParametersWithIV)paramCipherParameters;
      this.nonce = localParametersWithIV.getIV();
      this.associatedText = null;
      this.macSize = (this.macBlock.length / 2);
      this.keyParam = localParametersWithIV.getParameters();
      return;
    }
    throw new IllegalArgumentException("invalid parameters passed to CCM");
  }
  
  public int processByte(byte paramByte, byte[] paramArrayOfByte, int paramInt)
    throws DataLengthException, IllegalStateException
  {
    this.data.write(paramByte);
    return 0;
  }
  
  public int processBytes(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws DataLengthException, IllegalStateException
  {
    this.data.write(paramArrayOfByte1, paramInt1, paramInt2);
    return 0;
  }
  
  public byte[] processPacket(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IllegalStateException, InvalidCipherTextException
  {
    if (this.keyParam == null) {
      throw new IllegalStateException("CCM cipher unitialized.");
    }
    SICBlockCipher localSICBlockCipher = new SICBlockCipher(this.cipher);
    byte[] arrayOfByte1 = new byte[this.blockSize];
    arrayOfByte1[0] = ((byte)(0x7 & -1 + (15 - this.nonce.length)));
    System.arraycopy(this.nonce, 0, arrayOfByte1, 1, this.nonce.length);
    localSICBlockCipher.init(this.forEncryption, new ParametersWithIV(this.keyParam, arrayOfByte1));
    byte[] arrayOfByte2;
    if (this.forEncryption)
    {
      int n = paramInt1;
      int i1 = 0;
      arrayOfByte2 = new byte[paramInt2 + this.macSize];
      calculateMac(paramArrayOfByte, paramInt1, paramInt2, this.macBlock);
      localSICBlockCipher.processBlock(this.macBlock, 0, this.macBlock, 0);
      while (n < paramInt2 - this.blockSize)
      {
        localSICBlockCipher.processBlock(paramArrayOfByte, n, arrayOfByte2, i1);
        i1 += this.blockSize;
        n += this.blockSize;
      }
      byte[] arrayOfByte5 = new byte[this.blockSize];
      System.arraycopy(paramArrayOfByte, n, arrayOfByte5, 0, paramInt2 - n);
      localSICBlockCipher.processBlock(arrayOfByte5, 0, arrayOfByte5, 0);
      System.arraycopy(arrayOfByte5, 0, arrayOfByte2, i1, paramInt2 - n);
      int i2 = i1 + (paramInt2 - n);
      System.arraycopy(this.macBlock, 0, arrayOfByte2, i2, arrayOfByte2.length - i2);
    }
    byte[] arrayOfByte4;
    do
    {
      return arrayOfByte2;
      int i = paramInt1;
      arrayOfByte2 = new byte[paramInt2 - this.macSize];
      System.arraycopy(paramArrayOfByte, paramInt1 + paramInt2 - this.macSize, this.macBlock, 0, this.macSize);
      localSICBlockCipher.processBlock(this.macBlock, 0, this.macBlock, 0);
      int m;
      for (int j = this.macSize;; j++)
      {
        int k = this.macBlock.length;
        m = 0;
        if (j == k) {
          break;
        }
        this.macBlock[j] = 0;
      }
      while (m < arrayOfByte2.length - this.blockSize)
      {
        localSICBlockCipher.processBlock(paramArrayOfByte, i, arrayOfByte2, m);
        m += this.blockSize;
        i += this.blockSize;
      }
      byte[] arrayOfByte3 = new byte[this.blockSize];
      System.arraycopy(paramArrayOfByte, i, arrayOfByte3, 0, arrayOfByte2.length - m);
      localSICBlockCipher.processBlock(arrayOfByte3, 0, arrayOfByte3, 0);
      System.arraycopy(arrayOfByte3, 0, arrayOfByte2, m, arrayOfByte2.length - m);
      arrayOfByte4 = new byte[this.blockSize];
      calculateMac(arrayOfByte2, 0, arrayOfByte2.length, arrayOfByte4);
    } while (Arrays.constantTimeAreEqual(this.macBlock, arrayOfByte4));
    throw new InvalidCipherTextException("mac check in CCM failed");
  }
  
  public void reset()
  {
    this.cipher.reset();
    this.data.reset();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.modes.CCMBlockCipher
 * JD-Core Version:    0.7.0.1
 */