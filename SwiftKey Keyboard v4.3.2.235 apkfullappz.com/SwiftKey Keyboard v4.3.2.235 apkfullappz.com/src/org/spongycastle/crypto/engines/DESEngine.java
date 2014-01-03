package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class DESEngine
  implements BlockCipher
{
  protected static final int BLOCK_SIZE = 8;
  private static final int[] SP1 = { 16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756 };
  private static final int[] SP2 = { -2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, -2147483648, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, -2147483648, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, -2147483648, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, -2147483648, -2146435040, -2146402272, 1081344 };
  private static final int[] SP3 = { 520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584 };
  private static final int[] SP4 = { 8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928 };
  private static final int[] SP5 = { 256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080 };
  private static final int[] SP6 = { 536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312 };
  private static final int[] SP7 = { 2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154 };
  private static final int[] SP8 = { 268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696 };
  private static final int[] bigbyte;
  private static final short[] bytebit = { 128, 64, 32, 16, 8, 4, 2, 1 };
  private static final byte[] pc1;
  private static final byte[] pc2;
  private static final byte[] totrot;
  private int[] workingKey = null;
  
  static
  {
    bigbyte = new int[] { 8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1 };
    pc1 = new byte[] { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3 };
    totrot = new byte[] { 1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28 };
    pc2 = new byte[] { 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31 };
  }
  
  protected void desFunc(int[] paramArrayOfInt, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = (0xFF & paramArrayOfByte1[(paramInt1 + 0)]) << 24 | (0xFF & paramArrayOfByte1[(paramInt1 + 1)]) << 16 | (0xFF & paramArrayOfByte1[(paramInt1 + 2)]) << 8 | 0xFF & paramArrayOfByte1[(paramInt1 + 3)];
    int j = (0xFF & paramArrayOfByte1[(paramInt1 + 4)]) << 24 | (0xFF & paramArrayOfByte1[(paramInt1 + 5)]) << 16 | (0xFF & paramArrayOfByte1[(paramInt1 + 6)]) << 8 | 0xFF & paramArrayOfByte1[(paramInt1 + 7)];
    int k = 0xF0F0F0F & (j ^ i >>> 4);
    int m = j ^ k;
    int n = i ^ k << 4;
    int i1 = 0xFFFF & (m ^ n >>> 16);
    int i2 = m ^ i1;
    int i3 = n ^ i1 << 16;
    int i4 = 0x33333333 & (i3 ^ i2 >>> 2);
    int i5 = i3 ^ i4;
    int i6 = i2 ^ i4 << 2;
    int i7 = 0xFF00FF & (i5 ^ i6 >>> 8);
    int i8 = i5 ^ i7;
    int i9 = i6 ^ i7 << 8;
    int i10 = 0xFFFFFFFF & (i9 << 1 | 0x1 & i9 >>> 31);
    int i11 = 0xAAAAAAAA & (i8 ^ i10);
    int i12 = i8 ^ i11;
    int i13 = i10 ^ i11;
    int i14 = 0xFFFFFFFF & (i12 << 1 | 0x1 & i12 >>> 31);
    for (int i15 = 0; i15 < 8; i15++)
    {
      int i33 = (i13 << 28 | i13 >>> 4) ^ paramArrayOfInt[(0 + i15 * 4)];
      int i34 = SP7[(i33 & 0x3F)] | SP5[(0x3F & i33 >>> 8)] | SP3[(0x3F & i33 >>> 16)] | SP1[(0x3F & i33 >>> 24)];
      int i35 = i13 ^ paramArrayOfInt[(1 + i15 * 4)];
      i14 ^= (i34 | SP8[(i35 & 0x3F)] | SP6[(0x3F & i35 >>> 8)] | SP4[(0x3F & i35 >>> 16)] | SP2[(0x3F & i35 >>> 24)]);
      int i36 = (i14 << 28 | i14 >>> 4) ^ paramArrayOfInt[(2 + i15 * 4)];
      int i37 = SP7[(i36 & 0x3F)] | SP5[(0x3F & i36 >>> 8)] | SP3[(0x3F & i36 >>> 16)] | SP1[(0x3F & i36 >>> 24)];
      int i38 = i14 ^ paramArrayOfInt[(3 + i15 * 4)];
      i13 ^= (i37 | SP8[(i38 & 0x3F)] | SP6[(0x3F & i38 >>> 8)] | SP4[(0x3F & i38 >>> 16)] | SP2[(0x3F & i38 >>> 24)]);
    }
    int i16 = i13 << 31 | i13 >>> 1;
    int i17 = 0xAAAAAAAA & (i14 ^ i16);
    int i18 = i14 ^ i17;
    int i19 = i16 ^ i17;
    int i20 = i18 << 31 | i18 >>> 1;
    int i21 = 0xFF00FF & (i19 ^ i20 >>> 8);
    int i22 = i19 ^ i21;
    int i23 = i20 ^ i21 << 8;
    int i24 = 0x33333333 & (i22 ^ i23 >>> 2);
    int i25 = i22 ^ i24;
    int i26 = i23 ^ i24 << 2;
    int i27 = 0xFFFF & (i26 ^ i25 >>> 16);
    int i28 = i26 ^ i27;
    int i29 = i25 ^ i27 << 16;
    int i30 = 0xF0F0F0F & (i28 ^ i29 >>> 4);
    int i31 = i28 ^ i30;
    int i32 = i29 ^ i30 << 4;
    paramArrayOfByte2[(paramInt2 + 0)] = ((byte)(0xFF & i32 >>> 24));
    paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(0xFF & i32 >>> 16));
    paramArrayOfByte2[(paramInt2 + 2)] = ((byte)(0xFF & i32 >>> 8));
    paramArrayOfByte2[(paramInt2 + 3)] = ((byte)(i32 & 0xFF));
    paramArrayOfByte2[(paramInt2 + 4)] = ((byte)(0xFF & i31 >>> 24));
    paramArrayOfByte2[(paramInt2 + 5)] = ((byte)(0xFF & i31 >>> 16));
    paramArrayOfByte2[(paramInt2 + 6)] = ((byte)(0xFF & i31 >>> 8));
    paramArrayOfByte2[(paramInt2 + 7)] = ((byte)(i31 & 0xFF));
  }
  
  protected int[] generateWorkingKey(boolean paramBoolean, byte[] paramArrayOfByte)
  {
    int[] arrayOfInt = new int[32];
    boolean[] arrayOfBoolean1 = new boolean[56];
    boolean[] arrayOfBoolean2 = new boolean[56];
    int i = 0;
    if (i < 56)
    {
      int i8 = pc1[i];
      if ((paramArrayOfByte[(i8 >>> 3)] & bytebit[(i8 & 0x7)]) != 0) {}
      for (int i9 = 1;; i9 = 0)
      {
        arrayOfBoolean1[i] = i9;
        i++;
        break;
      }
    }
    for (int j = 0; j < 16; j++)
    {
      int i1;
      int i2;
      int i3;
      label115:
      int i7;
      if (paramBoolean)
      {
        i1 = j << 1;
        i2 = i1 + 1;
        arrayOfInt[i2] = 0;
        arrayOfInt[i1] = 0;
        i3 = 0;
        if (i3 >= 28) {
          break label184;
        }
        i7 = i3 + totrot[j];
        if (i7 >= 28) {
          break label168;
        }
        arrayOfBoolean2[i3] = arrayOfBoolean1[i7];
      }
      for (;;)
      {
        i3++;
        break label115;
        i1 = 15 - j << 1;
        break;
        label168:
        arrayOfBoolean2[i3] = arrayOfBoolean1[(i7 - 28)];
      }
      label184:
      int i4 = 28;
      if (i4 < 56)
      {
        int i6 = i4 + totrot[j];
        if (i6 < 56) {
          arrayOfBoolean2[i4] = arrayOfBoolean1[i6];
        }
        for (;;)
        {
          i4++;
          break;
          arrayOfBoolean2[i4] = arrayOfBoolean1[(i6 - 28)];
        }
      }
      for (int i5 = 0; i5 < 24; i5++)
      {
        if (arrayOfBoolean2[pc2[i5]] != 0) {
          arrayOfInt[i1] |= bigbyte[i5];
        }
        if (arrayOfBoolean2[pc2[(i5 + 24)]] != 0) {
          arrayOfInt[i2] |= bigbyte[i5];
        }
      }
    }
    for (int k = 0; k != 32; k += 2)
    {
      int m = arrayOfInt[k];
      int n = arrayOfInt[(k + 1)];
      arrayOfInt[k] = ((0xFC0000 & m) << 6 | (m & 0xFC0) << 10 | (0xFC0000 & n) >>> 10 | (n & 0xFC0) >>> 6);
      arrayOfInt[(k + 1)] = ((0x3F000 & m) << 12 | (m & 0x3F) << 16 | (0x3F000 & n) >>> 4 | n & 0x3F);
    }
    return arrayOfInt;
  }
  
  public String getAlgorithmName()
  {
    return "DES";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof KeyParameter))
    {
      if (((KeyParameter)paramCipherParameters).getKey().length > 8) {
        throw new IllegalArgumentException("DES key too long - should be 8 bytes");
      }
      this.workingKey = generateWorkingKey(paramBoolean, ((KeyParameter)paramCipherParameters).getKey());
      return;
    }
    throw new IllegalArgumentException("invalid parameter passed to DES init - " + paramCipherParameters.getClass().getName());
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if (this.workingKey == null) {
      throw new IllegalStateException("DES engine not initialised");
    }
    if (paramInt1 + 8 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + 8 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    desFunc(this.workingKey, paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    return 8;
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.DESEngine
 * JD-Core Version:    0.7.0.1
 */