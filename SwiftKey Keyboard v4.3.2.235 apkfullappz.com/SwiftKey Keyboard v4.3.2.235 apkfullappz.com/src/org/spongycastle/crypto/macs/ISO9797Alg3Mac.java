package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.BlockCipherPadding;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class ISO9797Alg3Mac
  implements Mac
{
  private byte[] buf;
  private int bufOff;
  private BlockCipher cipher;
  private KeyParameter lastKey2;
  private KeyParameter lastKey3;
  private byte[] mac;
  private int macSize;
  private BlockCipherPadding padding;
  
  public ISO9797Alg3Mac(BlockCipher paramBlockCipher)
  {
    this(paramBlockCipher, 8 * paramBlockCipher.getBlockSize(), null);
  }
  
  public ISO9797Alg3Mac(BlockCipher paramBlockCipher, int paramInt)
  {
    this(paramBlockCipher, paramInt, null);
  }
  
  public ISO9797Alg3Mac(BlockCipher paramBlockCipher, int paramInt, BlockCipherPadding paramBlockCipherPadding)
  {
    if (paramInt % 8 != 0) {
      throw new IllegalArgumentException("MAC size must be multiple of 8");
    }
    if (!(paramBlockCipher instanceof DESEngine)) {
      throw new IllegalArgumentException("cipher must be instance of DESEngine");
    }
    this.cipher = new CBCBlockCipher(paramBlockCipher);
    this.padding = paramBlockCipherPadding;
    this.macSize = (paramInt / 8);
    this.mac = new byte[paramBlockCipher.getBlockSize()];
    this.buf = new byte[paramBlockCipher.getBlockSize()];
    this.bufOff = 0;
  }
  
  public ISO9797Alg3Mac(BlockCipher paramBlockCipher, BlockCipherPadding paramBlockCipherPadding)
  {
    this(paramBlockCipher, 8 * paramBlockCipher.getBlockSize(), paramBlockCipherPadding);
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
    if (this.bufOff == i)
    {
      this.cipher.processBlock(this.buf, 0, this.mac, 0);
      this.bufOff = 0;
    }
    this.padding.addPadding(this.buf, this.bufOff);
    this.cipher.processBlock(this.buf, 0, this.mac, 0);
    DESEngine localDESEngine = new DESEngine();
    localDESEngine.init(false, this.lastKey2);
    localDESEngine.processBlock(this.mac, 0, this.mac, 0);
    localDESEngine.init(true, this.lastKey3);
    localDESEngine.processBlock(this.mac, 0, this.mac, 0);
    System.arraycopy(this.mac, 0, paramArrayOfByte, paramInt, this.macSize);
    reset();
    return this.macSize;
  }
  
  public String getAlgorithmName()
  {
    return "ISO9797Alg3";
  }
  
  public int getMacSize()
  {
    return this.macSize;
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    reset();
    if ((!(paramCipherParameters instanceof KeyParameter)) && (!(paramCipherParameters instanceof ParametersWithIV))) {
      throw new IllegalArgumentException("params must be an instance of KeyParameter or ParametersWithIV");
    }
    KeyParameter localKeyParameter1;
    byte[] arrayOfByte;
    KeyParameter localKeyParameter2;
    if ((paramCipherParameters instanceof KeyParameter))
    {
      localKeyParameter1 = (KeyParameter)paramCipherParameters;
      arrayOfByte = localKeyParameter1.getKey();
      if (arrayOfByte.length != 16) {
        break label135;
      }
      localKeyParameter2 = new KeyParameter(arrayOfByte, 0, 8);
      this.lastKey2 = new KeyParameter(arrayOfByte, 8, 8);
      this.lastKey3 = localKeyParameter2;
    }
    for (;;)
    {
      if ((paramCipherParameters instanceof ParametersWithIV))
      {
        this.cipher.init(true, new ParametersWithIV(localKeyParameter2, ((ParametersWithIV)paramCipherParameters).getIV()));
        return;
        localKeyParameter1 = (KeyParameter)((ParametersWithIV)paramCipherParameters).getParameters();
        break;
        label135:
        if (arrayOfByte.length == 24)
        {
          localKeyParameter2 = new KeyParameter(arrayOfByte, 0, 8);
          this.lastKey2 = new KeyParameter(arrayOfByte, 8, 8);
          this.lastKey3 = new KeyParameter(arrayOfByte, 16, 8);
        }
        else
        {
          throw new IllegalArgumentException("Key must be either 112 or 168 bit long");
        }
      }
    }
    this.cipher.init(true, localKeyParameter2);
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
 * Qualified Name:     org.spongycastle.crypto.macs.ISO9797Alg3Mac
 * JD-Core Version:    0.7.0.1
 */