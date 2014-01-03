package org.spongycastle.crypto.engines;

public final class CAST6Engine
  extends CAST5Engine
{
  protected static final int BLOCK_SIZE = 16;
  protected static final int ROUNDS = 12;
  protected int[] _Km = new int[48];
  protected int[] _Kr = new int[48];
  protected int[] _Tm = new int['À'];
  protected int[] _Tr = new int['À'];
  private int[] _workingKey = new int[8];
  
  protected final void CAST_Decipher(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    for (int i = 0; i < 6; i++)
    {
      int m = 4 * (11 - i);
      paramInt3 ^= F1(paramInt4, this._Km[m], this._Kr[m]);
      paramInt2 ^= F2(paramInt3, this._Km[(m + 1)], this._Kr[(m + 1)]);
      paramInt1 ^= F3(paramInt2, this._Km[(m + 2)], this._Kr[(m + 2)]);
      paramInt4 ^= F1(paramInt1, this._Km[(m + 3)], this._Kr[(m + 3)]);
    }
    for (int j = 6; j < 12; j++)
    {
      int k = 4 * (11 - j);
      paramInt4 ^= F1(paramInt1, this._Km[(k + 3)], this._Kr[(k + 3)]);
      paramInt1 ^= F3(paramInt2, this._Km[(k + 2)], this._Kr[(k + 2)]);
      paramInt2 ^= F2(paramInt3, this._Km[(k + 1)], this._Kr[(k + 1)]);
      paramInt3 ^= F1(paramInt4, this._Km[k], this._Kr[k]);
    }
    paramArrayOfInt[0] = paramInt1;
    paramArrayOfInt[1] = paramInt2;
    paramArrayOfInt[2] = paramInt3;
    paramArrayOfInt[3] = paramInt4;
  }
  
  protected final void CAST_Encipher(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    for (int i = 0; i < 6; i++)
    {
      int m = i * 4;
      paramInt3 ^= F1(paramInt4, this._Km[m], this._Kr[m]);
      paramInt2 ^= F2(paramInt3, this._Km[(m + 1)], this._Kr[(m + 1)]);
      paramInt1 ^= F3(paramInt2, this._Km[(m + 2)], this._Kr[(m + 2)]);
      paramInt4 ^= F1(paramInt1, this._Km[(m + 3)], this._Kr[(m + 3)]);
    }
    for (int j = 6; j < 12; j++)
    {
      int k = j * 4;
      paramInt4 ^= F1(paramInt1, this._Km[(k + 3)], this._Kr[(k + 3)]);
      paramInt1 ^= F3(paramInt2, this._Km[(k + 2)], this._Kr[(k + 2)]);
      paramInt2 ^= F2(paramInt3, this._Km[(k + 1)], this._Kr[(k + 1)]);
      paramInt3 ^= F1(paramInt4, this._Km[k], this._Kr[k]);
    }
    paramArrayOfInt[0] = paramInt1;
    paramArrayOfInt[1] = paramInt2;
    paramArrayOfInt[2] = paramInt3;
    paramArrayOfInt[3] = paramInt4;
  }
  
  protected int decryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int[] arrayOfInt = new int[4];
    CAST_Decipher(BytesTo32bits(paramArrayOfByte1, paramInt1), BytesTo32bits(paramArrayOfByte1, paramInt1 + 4), BytesTo32bits(paramArrayOfByte1, paramInt1 + 8), BytesTo32bits(paramArrayOfByte1, paramInt1 + 12), arrayOfInt);
    Bits32ToBytes(arrayOfInt[0], paramArrayOfByte2, paramInt2);
    Bits32ToBytes(arrayOfInt[1], paramArrayOfByte2, paramInt2 + 4);
    Bits32ToBytes(arrayOfInt[2], paramArrayOfByte2, paramInt2 + 8);
    Bits32ToBytes(arrayOfInt[3], paramArrayOfByte2, paramInt2 + 12);
    return 16;
  }
  
  protected int encryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int[] arrayOfInt = new int[4];
    CAST_Encipher(BytesTo32bits(paramArrayOfByte1, paramInt1), BytesTo32bits(paramArrayOfByte1, paramInt1 + 4), BytesTo32bits(paramArrayOfByte1, paramInt1 + 8), BytesTo32bits(paramArrayOfByte1, paramInt1 + 12), arrayOfInt);
    Bits32ToBytes(arrayOfInt[0], paramArrayOfByte2, paramInt2);
    Bits32ToBytes(arrayOfInt[1], paramArrayOfByte2, paramInt2 + 4);
    Bits32ToBytes(arrayOfInt[2], paramArrayOfByte2, paramInt2 + 8);
    Bits32ToBytes(arrayOfInt[3], paramArrayOfByte2, paramInt2 + 12);
    return 16;
  }
  
  public String getAlgorithmName()
  {
    return "CAST6";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  
  public void reset() {}
  
  protected void setKey(byte[] paramArrayOfByte)
  {
    int i = 1518500249;
    int j = 19;
    for (int k = 0; k < 24; k++) {
      for (int i3 = 0; i3 < 8; i3++)
      {
        this._Tm[(i3 + k * 8)] = i;
        i += 1859775393;
        this._Tr[(i3 + k * 8)] = j;
        j = 0x1F & j + 17;
      }
    }
    byte[] arrayOfByte = new byte[64];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
    for (int m = 0; m < 8; m++) {
      this._workingKey[m] = BytesTo32bits(arrayOfByte, m * 4);
    }
    for (int n = 0; n < 12; n++)
    {
      int i1 = 8 * (n * 2);
      int[] arrayOfInt1 = this._workingKey;
      arrayOfInt1[6] ^= F1(this._workingKey[7], this._Tm[i1], this._Tr[i1]);
      int[] arrayOfInt2 = this._workingKey;
      arrayOfInt2[5] ^= F2(this._workingKey[6], this._Tm[(i1 + 1)], this._Tr[(i1 + 1)]);
      int[] arrayOfInt3 = this._workingKey;
      arrayOfInt3[4] ^= F3(this._workingKey[5], this._Tm[(i1 + 2)], this._Tr[(i1 + 2)]);
      int[] arrayOfInt4 = this._workingKey;
      arrayOfInt4[3] ^= F1(this._workingKey[4], this._Tm[(i1 + 3)], this._Tr[(i1 + 3)]);
      int[] arrayOfInt5 = this._workingKey;
      arrayOfInt5[2] ^= F2(this._workingKey[3], this._Tm[(i1 + 4)], this._Tr[(i1 + 4)]);
      int[] arrayOfInt6 = this._workingKey;
      arrayOfInt6[1] ^= F3(this._workingKey[2], this._Tm[(i1 + 5)], this._Tr[(i1 + 5)]);
      int[] arrayOfInt7 = this._workingKey;
      arrayOfInt7[0] ^= F1(this._workingKey[1], this._Tm[(i1 + 6)], this._Tr[(i1 + 6)]);
      int[] arrayOfInt8 = this._workingKey;
      arrayOfInt8[7] ^= F2(this._workingKey[0], this._Tm[(i1 + 7)], this._Tr[(i1 + 7)]);
      int i2 = 8 * (1 + n * 2);
      int[] arrayOfInt9 = this._workingKey;
      arrayOfInt9[6] ^= F1(this._workingKey[7], this._Tm[i2], this._Tr[i2]);
      int[] arrayOfInt10 = this._workingKey;
      arrayOfInt10[5] ^= F2(this._workingKey[6], this._Tm[(i2 + 1)], this._Tr[(i2 + 1)]);
      int[] arrayOfInt11 = this._workingKey;
      arrayOfInt11[4] ^= F3(this._workingKey[5], this._Tm[(i2 + 2)], this._Tr[(i2 + 2)]);
      int[] arrayOfInt12 = this._workingKey;
      arrayOfInt12[3] ^= F1(this._workingKey[4], this._Tm[(i2 + 3)], this._Tr[(i2 + 3)]);
      int[] arrayOfInt13 = this._workingKey;
      arrayOfInt13[2] ^= F2(this._workingKey[3], this._Tm[(i2 + 4)], this._Tr[(i2 + 4)]);
      int[] arrayOfInt14 = this._workingKey;
      arrayOfInt14[1] ^= F3(this._workingKey[2], this._Tm[(i2 + 5)], this._Tr[(i2 + 5)]);
      int[] arrayOfInt15 = this._workingKey;
      arrayOfInt15[0] ^= F1(this._workingKey[1], this._Tm[(i2 + 6)], this._Tr[(i2 + 6)]);
      int[] arrayOfInt16 = this._workingKey;
      arrayOfInt16[7] ^= F2(this._workingKey[0], this._Tm[(i2 + 7)], this._Tr[(i2 + 7)]);
      this._Kr[(n * 4)] = (0x1F & this._workingKey[0]);
      this._Kr[(1 + n * 4)] = (0x1F & this._workingKey[2]);
      this._Kr[(2 + n * 4)] = (0x1F & this._workingKey[4]);
      this._Kr[(3 + n * 4)] = (0x1F & this._workingKey[6]);
      this._Km[(n * 4)] = this._workingKey[7];
      this._Km[(1 + n * 4)] = this._workingKey[5];
      this._Km[(2 + n * 4)] = this._workingKey[3];
      this._Km[(3 + n * 4)] = this._workingKey[1];
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.CAST6Engine
 * JD-Core Version:    0.7.0.1
 */