package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;

public class ISAACEngine
  implements StreamCipher
{
  private int a = 0;
  private int b = 0;
  private int c = 0;
  private int[] engineState = null;
  private int index = 0;
  private boolean initialised = false;
  private byte[] keyStream = new byte[1024];
  private int[] results = null;
  private final int sizeL = 8;
  private final int stateArraySize = 256;
  private byte[] workingKey = null;
  
  private int byteToIntLittle(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramInt + 1;
    int j = 0xFF & paramArrayOfByte[paramInt];
    int k = i + 1;
    int m = j | (0xFF & paramArrayOfByte[i]) << 8;
    int n = k + 1;
    return m | (0xFF & paramArrayOfByte[k]) << 16 | paramArrayOfByte[n] << 24;
  }
  
  private byte[] intToByteLittle(int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    arrayOfByte[3] = ((byte)paramInt);
    arrayOfByte[2] = ((byte)(paramInt >>> 8));
    arrayOfByte[1] = ((byte)(paramInt >>> 16));
    arrayOfByte[0] = ((byte)(paramInt >>> 24));
    return arrayOfByte;
  }
  
  private byte[] intToByteLittle(int[] paramArrayOfInt)
  {
    byte[] arrayOfByte = new byte[4 * paramArrayOfInt.length];
    int i = 0;
    for (int j = 0; i < paramArrayOfInt.length; j += 4)
    {
      System.arraycopy(intToByteLittle(paramArrayOfInt[i]), 0, arrayOfByte, j, 4);
      i++;
    }
    return arrayOfByte;
  }
  
  private void isaac()
  {
    int i = this.b;
    int j = 1 + this.c;
    this.c = j;
    this.b = (i + j);
    int k = 0;
    if (k < 256)
    {
      int m = this.engineState[k];
      switch (k & 0x3)
      {
      }
      for (;;)
      {
        this.a += this.engineState[(0xFF & k + 128)];
        int[] arrayOfInt1 = this.engineState;
        int n = this.engineState[(0xFF & m >>> 2)] + this.a + this.b;
        arrayOfInt1[k] = n;
        int[] arrayOfInt2 = this.results;
        int i1 = m + this.engineState[(0xFF & n >>> 10)];
        this.b = i1;
        arrayOfInt2[k] = i1;
        k++;
        break;
        this.a ^= this.a << 13;
        continue;
        this.a ^= this.a >>> 6;
        continue;
        this.a ^= this.a << 2;
        continue;
        this.a ^= this.a >>> 16;
      }
    }
  }
  
  private void mix(int[] paramArrayOfInt)
  {
    paramArrayOfInt[0] ^= paramArrayOfInt[1] << 11;
    paramArrayOfInt[3] += paramArrayOfInt[0];
    paramArrayOfInt[1] += paramArrayOfInt[2];
    paramArrayOfInt[1] ^= paramArrayOfInt[2] >>> 2;
    paramArrayOfInt[4] += paramArrayOfInt[1];
    paramArrayOfInt[2] += paramArrayOfInt[3];
    paramArrayOfInt[2] ^= paramArrayOfInt[3] << 8;
    paramArrayOfInt[5] += paramArrayOfInt[2];
    paramArrayOfInt[3] += paramArrayOfInt[4];
    paramArrayOfInt[3] ^= paramArrayOfInt[4] >>> 16;
    paramArrayOfInt[6] += paramArrayOfInt[3];
    paramArrayOfInt[4] += paramArrayOfInt[5];
    paramArrayOfInt[4] ^= paramArrayOfInt[5] << 10;
    paramArrayOfInt[7] += paramArrayOfInt[4];
    paramArrayOfInt[5] += paramArrayOfInt[6];
    paramArrayOfInt[5] ^= paramArrayOfInt[6] >>> 4;
    paramArrayOfInt[0] += paramArrayOfInt[5];
    paramArrayOfInt[6] += paramArrayOfInt[7];
    paramArrayOfInt[6] ^= paramArrayOfInt[7] << 8;
    paramArrayOfInt[1] += paramArrayOfInt[6];
    paramArrayOfInt[7] += paramArrayOfInt[0];
    paramArrayOfInt[7] ^= paramArrayOfInt[0] >>> 9;
    paramArrayOfInt[2] += paramArrayOfInt[7];
    paramArrayOfInt[0] += paramArrayOfInt[1];
  }
  
  private void setKey(byte[] paramArrayOfByte)
  {
    this.workingKey = paramArrayOfByte;
    if (this.engineState == null) {
      this.engineState = new int[256];
    }
    if (this.results == null) {
      this.results = new int[256];
    }
    for (int i = 0; i < 256; i++)
    {
      int[] arrayOfInt2 = this.engineState;
      this.results[i] = 0;
      arrayOfInt2[i] = 0;
    }
    this.c = 0;
    this.b = 0;
    this.a = 0;
    this.index = 0;
    byte[] arrayOfByte = new byte[paramArrayOfByte.length + (0x3 & paramArrayOfByte.length)];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
    for (int j = 0; j < arrayOfByte.length; j += 4) {
      this.results[(j >> 2)] = byteToIntLittle(arrayOfByte, j);
    }
    int[] arrayOfInt1 = new int[8];
    for (int k = 0; k < 8; k++) {
      arrayOfInt1[k] = -1640531527;
    }
    for (int m = 0; m < 4; m++) {
      mix(arrayOfInt1);
    }
    for (int n = 0; n < 2; n++) {
      for (int i1 = 0; i1 < 256; i1 += 8)
      {
        int i2 = 0;
        if (i2 < 8)
        {
          int i4 = arrayOfInt1[i2];
          if (n <= 0) {}
          for (int i5 = this.results[(i1 + i2)];; i5 = this.engineState[(i1 + i2)])
          {
            arrayOfInt1[i2] = (i5 + i4);
            i2++;
            break;
          }
        }
        mix(arrayOfInt1);
        for (int i3 = 0; i3 < 8; i3++) {
          this.engineState[(i1 + i3)] = arrayOfInt1[i3];
        }
      }
    }
    isaac();
    this.initialised = true;
  }
  
  public String getAlgorithmName()
  {
    return "ISAAC";
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (!(paramCipherParameters instanceof KeyParameter)) {
      throw new IllegalArgumentException("invalid parameter passed to ISAAC init - " + paramCipherParameters.getClass().getName());
    }
    setKey(((KeyParameter)paramCipherParameters).getKey());
  }
  
  public void processBytes(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
  {
    if (!this.initialised) {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    if (paramInt1 + paramInt2 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt3 + paramInt2 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    for (int i = 0; i < paramInt2; i++)
    {
      if (this.index == 0)
      {
        isaac();
        this.keyStream = intToByteLittle(this.results);
      }
      paramArrayOfByte2[(i + paramInt3)] = ((byte)(this.keyStream[this.index] ^ paramArrayOfByte1[(i + paramInt1)]));
      this.index = (0x3FF & 1 + this.index);
    }
  }
  
  public void reset()
  {
    setKey(this.workingKey);
  }
  
  public byte returnByte(byte paramByte)
  {
    if (this.index == 0)
    {
      isaac();
      this.keyStream = intToByteLittle(this.results);
    }
    byte b1 = (byte)(paramByte ^ this.keyStream[this.index]);
    this.index = (0x3FF & 1 + this.index);
    return b1;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.ISAACEngine
 * JD-Core Version:    0.7.0.1
 */