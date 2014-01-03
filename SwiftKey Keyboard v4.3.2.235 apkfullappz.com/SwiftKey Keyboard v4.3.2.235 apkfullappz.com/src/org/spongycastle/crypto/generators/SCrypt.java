package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.engines.Salsa20Engine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.util.Pack;
import org.spongycastle.util.Arrays;

public class SCrypt
{
  private static void BlockMix(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int paramInt)
  {
    System.arraycopy(paramArrayOfInt1, -16 + paramArrayOfInt1.length, paramArrayOfInt2, 0, 16);
    int i = 0;
    int j = 0;
    int k = paramArrayOfInt1.length >>> 1;
    for (int m = paramInt * 2; m > 0; m--)
    {
      Xor(paramArrayOfInt2, paramArrayOfInt1, i, paramArrayOfInt3);
      Salsa20Engine.salsaCore(8, paramArrayOfInt3, paramArrayOfInt2);
      System.arraycopy(paramArrayOfInt2, 0, paramArrayOfInt4, j, 16);
      j = k + i - j;
      i += 16;
    }
    System.arraycopy(paramArrayOfInt4, 0, paramArrayOfInt1, 0, paramArrayOfInt4.length);
  }
  
  private static void Clear(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null) {
      Arrays.fill(paramArrayOfByte, (byte)0);
    }
  }
  
  private static void Clear(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt != null) {
      Arrays.fill(paramArrayOfInt, 0);
    }
  }
  
  private static void ClearAll(int[][] paramArrayOfInt)
  {
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      Clear(paramArrayOfInt[i]);
    }
  }
  
  private static byte[] MFcrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt2 * 128;
    byte[] arrayOfByte1 = SingleIterationPBKDF2(paramArrayOfByte1, paramArrayOfByte2, paramInt3 * i);
    int[] arrayOfInt = null;
    try
    {
      int j = arrayOfByte1.length >>> 2;
      arrayOfInt = new int[j];
      Pack.littleEndianToInt(arrayOfByte1, 0, arrayOfInt);
      int k = i >>> 2;
      int m = 0;
      while (m < j)
      {
        SMix(arrayOfInt, m, paramInt1, paramInt2);
        m += k;
      }
      Pack.intToLittleEndian(arrayOfInt, arrayOfByte1, 0);
      byte[] arrayOfByte2 = SingleIterationPBKDF2(paramArrayOfByte1, arrayOfByte1, paramInt4);
      return arrayOfByte2;
    }
    finally
    {
      Clear(arrayOfByte1);
      Clear(arrayOfInt);
    }
  }
  
  private static void SMix(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt3 * 32;
    int[] arrayOfInt1 = new int[16];
    int[] arrayOfInt2 = new int[16];
    int[] arrayOfInt3 = new int[i];
    int[] arrayOfInt4 = new int[i];
    int[][] arrayOfInt = new int[paramInt2][];
    for (;;)
    {
      try
      {
        System.arraycopy(paramArrayOfInt, paramInt1, arrayOfInt4, 0, i);
        int j = 0;
        if (j < paramInt2)
        {
          arrayOfInt[j] = Arrays.clone(arrayOfInt4);
          BlockMix(arrayOfInt4, arrayOfInt1, arrayOfInt2, arrayOfInt3, paramInt3);
          j++;
          continue;
          if (m < paramInt2)
          {
            Xor(arrayOfInt4, arrayOfInt[(k & arrayOfInt4[(i - 16)])], 0, arrayOfInt4);
            BlockMix(arrayOfInt4, arrayOfInt1, arrayOfInt2, arrayOfInt3, paramInt3);
            m++;
            continue;
          }
          System.arraycopy(arrayOfInt4, 0, paramArrayOfInt, paramInt1, i);
          return;
        }
      }
      finally
      {
        ClearAll(arrayOfInt);
        ClearAll(new int[][] { arrayOfInt4, arrayOfInt1, arrayOfInt2, arrayOfInt3 });
      }
      int k = paramInt2 - 1;
      int m = 0;
    }
  }
  
  private static byte[] SingleIterationPBKDF2(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    PKCS5S2ParametersGenerator localPKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(new SHA256Digest());
    localPKCS5S2ParametersGenerator.init(paramArrayOfByte1, paramArrayOfByte2, 1);
    return ((KeyParameter)localPKCS5S2ParametersGenerator.generateDerivedMacParameters(paramInt * 8)).getKey();
  }
  
  private static void Xor(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt, int[] paramArrayOfInt3)
  {
    for (int i = -1 + paramArrayOfInt3.length; i >= 0; i--) {
      paramArrayOfInt1[i] ^= paramArrayOfInt2[(paramInt + i)];
    }
  }
  
  public static byte[] generate(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return MFcrypt(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.SCrypt
 * JD-Core Version:    0.7.0.1
 */