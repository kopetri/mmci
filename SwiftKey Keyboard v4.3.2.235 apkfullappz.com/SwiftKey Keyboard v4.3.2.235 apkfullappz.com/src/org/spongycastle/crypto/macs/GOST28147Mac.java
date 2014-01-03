package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithSBox;

public class GOST28147Mac
  implements Mac
{
  private byte[] S = { 9, 6, 3, 2, 8, 11, 1, 7, 10, 4, 14, 15, 12, 0, 13, 5, 3, 7, 14, 9, 8, 10, 15, 0, 5, 2, 6, 12, 11, 4, 13, 1, 14, 4, 6, 2, 11, 3, 13, 8, 12, 15, 5, 10, 0, 7, 1, 9, 14, 7, 10, 12, 13, 1, 3, 9, 0, 2, 11, 4, 15, 8, 5, 6, 11, 5, 1, 9, 8, 13, 15, 0, 14, 4, 2, 3, 12, 7, 10, 6, 3, 10, 13, 12, 1, 2, 0, 11, 7, 5, 9, 4, 8, 15, 14, 6, 1, 13, 2, 9, 7, 10, 6, 0, 8, 12, 4, 5, 15, 3, 11, 14, 11, 10, 15, 5, 0, 12, 14, 8, 6, 2, 3, 9, 1, 7, 13, 4 };
  private int blockSize = 8;
  private byte[] buf = new byte[this.blockSize];
  private int bufOff = 0;
  private boolean firstStep = true;
  private byte[] mac = new byte[this.blockSize];
  private int macSize = 4;
  private int[] workingKey = null;
  
  private byte[] CM5func(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte1.length - paramInt];
    System.arraycopy(paramArrayOfByte1, paramInt, arrayOfByte, 0, paramArrayOfByte2.length);
    for (int i = 0; i != paramArrayOfByte2.length; i++) {
      arrayOfByte[i] = ((byte)(arrayOfByte[i] ^ paramArrayOfByte2[i]));
    }
    return arrayOfByte;
  }
  
  private int bytesToint(byte[] paramArrayOfByte, int paramInt)
  {
    return (0xFF000000 & paramArrayOfByte[(paramInt + 3)] << 24) + (0xFF0000 & paramArrayOfByte[(paramInt + 2)] << 16) + (0xFF00 & paramArrayOfByte[(paramInt + 1)] << 8) + (0xFF & paramArrayOfByte[paramInt]);
  }
  
  private int[] generateWorkingKey(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 32) {
      throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
    }
    int[] arrayOfInt = new int[8];
    for (int i = 0; i != 8; i++) {
      arrayOfInt[i] = bytesToint(paramArrayOfByte, i * 4);
    }
    return arrayOfInt;
  }
  
  private void gost28147MacFunc(int[] paramArrayOfInt, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = bytesToint(paramArrayOfByte1, paramInt1);
    int j = bytesToint(paramArrayOfByte1, paramInt1 + 4);
    for (int k = 0; k < 2; k++) {
      for (int m = 0; m < 8; m++)
      {
        int n = i;
        i = j ^ gost28147_mainStep(i, paramArrayOfInt[m]);
        j = n;
      }
    }
    intTobytes(i, paramArrayOfByte2, paramInt2);
    intTobytes(j, paramArrayOfByte2, paramInt2 + 4);
  }
  
  private int gost28147_mainStep(int paramInt1, int paramInt2)
  {
    int i = paramInt2 + paramInt1;
    int j = (this.S[(0 + (0xF & i >> 0))] << 0) + (this.S[(16 + (0xF & i >> 4))] << 4) + (this.S[(32 + (0xF & i >> 8))] << 8) + (this.S[(48 + (0xF & i >> 12))] << 12) + (this.S[(64 + (0xF & i >> 16))] << 16) + (this.S[(80 + (0xF & i >> 20))] << 20) + (this.S[(96 + (0xF & i >> 24))] << 24) + (this.S[(112 + (0xF & i >> 28))] << 28);
    return j << 11 | j >>> 21;
  }
  
  private void intTobytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
    paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
    paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
    paramArrayOfByte[paramInt2] = ((byte)paramInt1);
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
    throws DataLengthException, IllegalStateException
  {
    while (this.bufOff < this.blockSize)
    {
      this.buf[this.bufOff] = 0;
      this.bufOff = (1 + this.bufOff);
    }
    byte[] arrayOfByte = new byte[this.buf.length];
    System.arraycopy(this.buf, 0, arrayOfByte, 0, this.mac.length);
    if (this.firstStep) {
      this.firstStep = false;
    }
    for (;;)
    {
      gost28147MacFunc(this.workingKey, arrayOfByte, 0, this.mac, 0);
      System.arraycopy(this.mac, this.mac.length / 2 - this.macSize, paramArrayOfByte, paramInt, this.macSize);
      reset();
      return this.macSize;
      arrayOfByte = CM5func(this.buf, 0, this.mac);
    }
  }
  
  public String getAlgorithmName()
  {
    return "GOST28147Mac";
  }
  
  public int getMacSize()
  {
    return this.macSize;
  }
  
  public void init(CipherParameters paramCipherParameters)
    throws IllegalArgumentException
  {
    reset();
    this.buf = new byte[this.blockSize];
    if ((paramCipherParameters instanceof ParametersWithSBox))
    {
      ParametersWithSBox localParametersWithSBox = (ParametersWithSBox)paramCipherParameters;
      System.arraycopy(localParametersWithSBox.getSBox(), 0, this.S, 0, localParametersWithSBox.getSBox().length);
      if (localParametersWithSBox.getParameters() != null) {
        this.workingKey = generateWorkingKey(((KeyParameter)localParametersWithSBox.getParameters()).getKey());
      }
      return;
    }
    if ((paramCipherParameters instanceof KeyParameter))
    {
      this.workingKey = generateWorkingKey(((KeyParameter)paramCipherParameters).getKey());
      return;
    }
    throw new IllegalArgumentException("invalid parameter passed to GOST28147 init - " + paramCipherParameters.getClass().getName());
  }
  
  public void reset()
  {
    for (int i = 0; i < this.buf.length; i++) {
      this.buf[i] = 0;
    }
    this.bufOff = 0;
    this.firstStep = true;
  }
  
  public void update(byte paramByte)
    throws IllegalStateException
  {
    byte[] arrayOfByte2;
    if (this.bufOff == this.buf.length)
    {
      arrayOfByte2 = new byte[this.buf.length];
      System.arraycopy(this.buf, 0, arrayOfByte2, 0, this.mac.length);
      if (!this.firstStep) {
        break label92;
      }
      this.firstStep = false;
    }
    for (;;)
    {
      gost28147MacFunc(this.workingKey, arrayOfByte2, 0, this.mac, 0);
      this.bufOff = 0;
      byte[] arrayOfByte1 = this.buf;
      int i = this.bufOff;
      this.bufOff = (i + 1);
      arrayOfByte1[i] = paramByte;
      return;
      label92:
      arrayOfByte2 = CM5func(this.buf, 0, this.mac);
    }
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (paramInt2 < 0) {
      throw new IllegalArgumentException("Can't have a negative input length!");
    }
    int i = this.blockSize - this.bufOff;
    if (paramInt2 > i)
    {
      System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.bufOff, i);
      byte[] arrayOfByte1 = new byte[this.buf.length];
      System.arraycopy(this.buf, 0, arrayOfByte1, 0, this.mac.length);
      if (this.firstStep) {
        this.firstStep = false;
      }
      for (;;)
      {
        gost28147MacFunc(this.workingKey, arrayOfByte1, 0, this.mac, 0);
        this.bufOff = 0;
        paramInt2 -= i;
        paramInt1 += i;
        while (paramInt2 > this.blockSize)
        {
          byte[] arrayOfByte2 = CM5func(paramArrayOfByte, paramInt1, this.mac);
          gost28147MacFunc(this.workingKey, arrayOfByte2, 0, this.mac, 0);
          paramInt2 -= this.blockSize;
          paramInt1 += this.blockSize;
        }
        arrayOfByte1 = CM5func(this.buf, 0, this.mac);
      }
    }
    System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.bufOff, paramInt2);
    this.bufOff = (paramInt2 + this.bufOff);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.macs.GOST28147Mac
 * JD-Core Version:    0.7.0.1
 */