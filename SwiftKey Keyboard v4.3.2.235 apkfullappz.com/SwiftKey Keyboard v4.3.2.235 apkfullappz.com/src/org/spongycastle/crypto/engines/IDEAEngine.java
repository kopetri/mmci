package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class IDEAEngine
  implements BlockCipher
{
  private static final int BASE = 65537;
  protected static final int BLOCK_SIZE = 8;
  private static final int MASK = 65535;
  private int[] workingKey = null;
  
  private int bytesToWord(byte[] paramArrayOfByte, int paramInt)
  {
    return (0xFF00 & paramArrayOfByte[paramInt] << 8) + (0xFF & paramArrayOfByte[(paramInt + 1)]);
  }
  
  private int[] expandKey(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt = new int[52];
    if (paramArrayOfByte.length < 16)
    {
      byte[] arrayOfByte = new byte[16];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, arrayOfByte.length - paramArrayOfByte.length, paramArrayOfByte.length);
      paramArrayOfByte = arrayOfByte;
    }
    for (int i = 0; i < 8; i++) {
      arrayOfInt[i] = bytesToWord(paramArrayOfByte, i * 2);
    }
    int j = 8;
    if (j < 52)
    {
      if ((j & 0x7) < 6) {
        arrayOfInt[j] = (0xFFFF & ((0x7F & arrayOfInt[(j - 7)]) << 9 | arrayOfInt[(j - 6)] >> 7));
      }
      for (;;)
      {
        j++;
        break;
        if ((j & 0x7) == 6) {
          arrayOfInt[j] = (0xFFFF & ((0x7F & arrayOfInt[(j - 7)]) << 9 | arrayOfInt[(j - 14)] >> 7));
        } else {
          arrayOfInt[j] = (0xFFFF & ((0x7F & arrayOfInt[(j - 15)]) << 9 | arrayOfInt[(j - 14)] >> 7));
        }
      }
    }
    return arrayOfInt;
  }
  
  private int[] generateWorkingKey(boolean paramBoolean, byte[] paramArrayOfByte)
  {
    if (paramBoolean) {
      return expandKey(paramArrayOfByte);
    }
    return invertKey(expandKey(paramArrayOfByte));
  }
  
  private void ideaFunc(int[] paramArrayOfInt, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = bytesToWord(paramArrayOfByte1, paramInt1);
    int j = bytesToWord(paramArrayOfByte1, paramInt1 + 2);
    int k = bytesToWord(paramArrayOfByte1, paramInt1 + 4);
    int m = bytesToWord(paramArrayOfByte1, paramInt1 + 6);
    int n = 0;
    int i1 = 0;
    while (n < 8)
    {
      int i5 = i1 + 1;
      int i6 = mul(i, paramArrayOfInt[i1]);
      int i7 = i5 + 1;
      int i8 = 0xFFFF & j + paramArrayOfInt[i5];
      int i9 = i7 + 1;
      int i10 = 0xFFFF & k + paramArrayOfInt[i7];
      int i11 = i9 + 1;
      int i12 = mul(m, paramArrayOfInt[i9]);
      int i13 = i10 ^ i6;
      int i14 = i8 ^ i12;
      int i15 = i11 + 1;
      int i16 = mul(i13, paramArrayOfInt[i11]);
      int i17 = 0xFFFF & i14 + i16;
      i1 = i15 + 1;
      int i18 = mul(i17, paramArrayOfInt[i15]);
      int i19 = 0xFFFF & i16 + i18;
      i = i6 ^ i18;
      m = i12 ^ i19;
      j = i18 ^ i10;
      k = i19 ^ i8;
      n++;
    }
    int i2 = i1 + 1;
    wordToBytes(mul(i, paramArrayOfInt[i1]), paramArrayOfByte2, paramInt2);
    int i3 = i2 + 1;
    wordToBytes(k + paramArrayOfInt[i2], paramArrayOfByte2, paramInt2 + 2);
    int i4 = i3 + 1;
    wordToBytes(j + paramArrayOfInt[i3], paramArrayOfByte2, paramInt2 + 4);
    wordToBytes(mul(m, paramArrayOfInt[i4]), paramArrayOfByte2, paramInt2 + 6);
  }
  
  private int[] invertKey(int[] paramArrayOfInt)
  {
    int[] arrayOfInt = new int[52];
    int i = 0 + 1;
    int j = mulInv(paramArrayOfInt[0]);
    int k = i + 1;
    int m = addInv(paramArrayOfInt[1]);
    int n = k + 1;
    int i1 = addInv(paramArrayOfInt[2]);
    int i2 = n + 1;
    int i3 = mulInv(paramArrayOfInt[3]);
    int i4 = 52 - 1;
    arrayOfInt[51] = i3;
    int i5 = i4 - 1;
    arrayOfInt[50] = i1;
    int i6 = i5 - 1;
    arrayOfInt[49] = m;
    int i7 = i6 - 1;
    arrayOfInt[48] = j;
    int i8 = 1;
    int i9 = i2;
    while (i8 < 8)
    {
      int i26 = i9 + 1;
      int i27 = paramArrayOfInt[i9];
      int i28 = i26 + 1;
      int i29 = paramArrayOfInt[i26];
      int i30 = i7 - 1;
      arrayOfInt[i30] = i29;
      int i31 = i30 - 1;
      arrayOfInt[i31] = i27;
      int i32 = i28 + 1;
      int i33 = mulInv(paramArrayOfInt[i28]);
      int i34 = i32 + 1;
      int i35 = addInv(paramArrayOfInt[i32]);
      int i36 = i34 + 1;
      int i37 = addInv(paramArrayOfInt[i34]);
      i9 = i36 + 1;
      int i38 = mulInv(paramArrayOfInt[i36]);
      int i39 = i31 - 1;
      arrayOfInt[i39] = i38;
      int i40 = i39 - 1;
      arrayOfInt[i40] = i35;
      int i41 = i40 - 1;
      arrayOfInt[i41] = i37;
      i7 = i41 - 1;
      arrayOfInt[i7] = i33;
      i8++;
    }
    int i10 = i9 + 1;
    int i11 = paramArrayOfInt[i9];
    int i12 = i10 + 1;
    int i13 = paramArrayOfInt[i10];
    int i14 = i7 - 1;
    arrayOfInt[i14] = i13;
    int i15 = i14 - 1;
    arrayOfInt[i15] = i11;
    int i16 = i12 + 1;
    int i17 = mulInv(paramArrayOfInt[i12]);
    int i18 = i16 + 1;
    int i19 = addInv(paramArrayOfInt[i16]);
    int i20 = i18 + 1;
    int i21 = addInv(paramArrayOfInt[i18]);
    int i22 = mulInv(paramArrayOfInt[i20]);
    int i23 = i15 - 1;
    arrayOfInt[i23] = i22;
    int i24 = i23 - 1;
    arrayOfInt[i24] = i21;
    int i25 = i24 - 1;
    arrayOfInt[i25] = i19;
    arrayOfInt[(i25 - 1)] = i17;
    return arrayOfInt;
  }
  
  private int mul(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0) {}
    for (int i1 = 65537 - paramInt2;; i1 = 65537 - paramInt1)
    {
      return i1 & 0xFFFF;
      if (paramInt2 != 0) {
        break;
      }
    }
    int i = paramInt1 * paramInt2;
    int j = i & 0xFFFF;
    int k = i >>> 16;
    int m = j - k;
    if (j < k) {}
    for (int n = 1;; n = 0)
    {
      i1 = m + n;
      break;
    }
  }
  
  private int mulInv(int paramInt)
  {
    if (paramInt < 2)
    {
      i = paramInt;
      return i;
    }
    int i = 1;
    int j = 65537 / paramInt;
    int k = 65537 % paramInt;
    for (;;)
    {
      if (k == 1) {
        break label79;
      }
      int m = paramInt / k;
      paramInt %= k;
      i = 0xFFFF & i + j * m;
      if (paramInt == 1) {
        break;
      }
      int n = k / paramInt;
      k %= paramInt;
      j = 0xFFFF & j + i * n;
    }
    label79:
    return 0xFFFF & 1 - j;
  }
  
  private void wordToBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    paramArrayOfByte[paramInt2] = ((byte)(paramInt1 >>> 8));
    paramArrayOfByte[(paramInt2 + 1)] = ((byte)paramInt1);
  }
  
  int addInv(int paramInt)
  {
    return 0xFFFF & 0 - paramInt;
  }
  
  public String getAlgorithmName()
  {
    return "IDEA";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof KeyParameter))
    {
      this.workingKey = generateWorkingKey(paramBoolean, ((KeyParameter)paramCipherParameters).getKey());
      return;
    }
    throw new IllegalArgumentException("invalid parameter passed to IDEA init - " + paramCipherParameters.getClass().getName());
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if (this.workingKey == null) {
      throw new IllegalStateException("IDEA engine not initialised");
    }
    if (paramInt1 + 8 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + 8 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    ideaFunc(this.workingKey, paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    return 8;
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.IDEAEngine
 * JD-Core Version:    0.7.0.1
 */