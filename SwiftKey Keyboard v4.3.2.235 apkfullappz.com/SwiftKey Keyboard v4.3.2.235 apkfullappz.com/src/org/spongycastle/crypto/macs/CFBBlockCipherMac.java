package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.paddings.BlockCipherPadding;

public class CFBBlockCipherMac
  implements Mac
{
  private byte[] buf;
  private int bufOff;
  private MacCFBBlockCipher cipher;
  private byte[] mac;
  private int macSize;
  private BlockCipherPadding padding = null;
  
  public CFBBlockCipherMac(BlockCipher paramBlockCipher)
  {
    this(paramBlockCipher, 8, 8 * paramBlockCipher.getBlockSize() / 2, null);
  }
  
  public CFBBlockCipherMac(BlockCipher paramBlockCipher, int paramInt1, int paramInt2)
  {
    this(paramBlockCipher, paramInt1, paramInt2, null);
  }
  
  public CFBBlockCipherMac(BlockCipher paramBlockCipher, int paramInt1, int paramInt2, BlockCipherPadding paramBlockCipherPadding)
  {
    if (paramInt2 % 8 != 0) {
      throw new IllegalArgumentException("MAC size must be multiple of 8");
    }
    this.mac = new byte[paramBlockCipher.getBlockSize()];
    this.cipher = new MacCFBBlockCipher(paramBlockCipher, paramInt1);
    this.padding = paramBlockCipherPadding;
    this.macSize = (paramInt2 / 8);
    this.buf = new byte[this.cipher.getBlockSize()];
    this.bufOff = 0;
  }
  
  public CFBBlockCipherMac(BlockCipher paramBlockCipher, BlockCipherPadding paramBlockCipherPadding)
  {
    this(paramBlockCipher, 8, 8 * paramBlockCipher.getBlockSize() / 2, paramBlockCipherPadding);
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    int i = this.cipher.getBlockSize();
    if (this.padding == null) {
      while (this.bufOff < i)
      {
        this.buf[this.bufOff] = 0;
        this.bufOff = (1 + this.bufOff);
      }
    }
    this.padding.addPadding(this.buf, this.bufOff);
    this.cipher.processBlock(this.buf, 0, this.mac, 0);
    this.cipher.getMacBlock(this.mac);
    System.arraycopy(this.mac, 0, paramArrayOfByte, paramInt, this.macSize);
    reset();
    return this.macSize;
  }
  
  public String getAlgorithmName()
  {
    return this.cipher.getAlgorithmName();
  }
  
  public int getMacSize()
  {
    return this.macSize;
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    reset();
    this.cipher.init(paramCipherParameters);
  }
  
  public void reset()
  {
    for (int i = 0; i < this.buf.length; i++) {
      this.buf[i] = 0;
    }
    this.bufOff = 0;
    this.cipher.reset();
  }
  
  public void update(byte paramByte)
  {
    if (this.bufOff == this.buf.length)
    {
      this.cipher.processBlock(this.buf, 0, this.mac, 0);
      this.bufOff = 0;
    }
    byte[] arrayOfByte = this.buf;
    int i = this.bufOff;
    this.bufOff = (i + 1);
    arrayOfByte[i] = paramByte;
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramInt2 < 0) {
      throw new IllegalArgumentException("Can't have a negative input length!");
    }
    int i = this.cipher.getBlockSize();
    int j = i - this.bufOff;
    if (paramInt2 > j)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.bufOff, j);
      this.cipher.processBlock(this.buf, 0, this.mac, 0);
      this.bufOff = 0;
      paramInt2 -= j;
      paramInt1 += j;
      while (paramInt2 > i)
      {
        this.cipher.processBlock(paramArrayOfByte, paramInt1, this.mac, 0);
        paramInt2 -= i;
        paramInt1 += i;
      }
    }
    System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.bufOff, paramInt2);
    this.bufOff = (paramInt2 + this.bufOff);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.macs.CFBBlockCipherMac
 * JD-Core Version:    0.7.0.1
 */