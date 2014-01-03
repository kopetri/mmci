package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class SkipjackEngine
  implements BlockCipher
{
  static final int BLOCK_SIZE = 8;
  static short[] ftable = { 163, 215, 9, 131, 248, 72, 246, 244, 179, 33, 21, 120, 153, 177, 175, 249, 231, 45, 77, 138, 206, 76, 202, 46, 82, 149, 217, 30, 78, 56, 68, 40, 10, 223, 2, 160, 23, 241, 96, 104, 18, 183, 122, 195, 233, 250, 61, 83, 150, 132, 107, 186, 242, 99, 154, 25, 124, 174, 229, 245, 247, 22, 106, 162, 57, 182, 123, 15, 193, 147, 129, 27, 238, 180, 26, 234, 208, 145, 47, 184, 85, 185, 218, 133, 63, 65, 191, 224, 90, 88, 128, 95, 102, 11, 216, 144, 53, 213, 192, 167, 51, 6, 101, 105, 69, 0, 148, 86, 109, 152, 155, 118, 151, 252, 178, 194, 176, 254, 219, 32, 225, 235, 214, 228, 221, 71, 74, 29, 66, 237, 158, 110, 73, 60, 205, 67, 39, 210, 7, 212, 222, 199, 103, 24, 137, 203, 48, 31, 141, 198, 143, 170, 200, 116, 220, 201, 93, 92, 49, 164, 112, 136, 97, 44, 159, 13, 43, 135, 80, 130, 84, 100, 38, 125, 3, 64, 52, 75, 28, 115, 209, 196, 253, 59, 204, 251, 127, 171, 230, 62, 91, 165, 173, 4, 35, 156, 20, 81, 34, 240, 41, 121, 113, 126, 255, 140, 14, 226, 12, 239, 188, 114, 117, 111, 55, 161, 236, 211, 142, 98, 139, 134, 16, 232, 8, 119, 17, 190, 146, 79, 36, 197, 50, 54, 157, 207, 243, 166, 187, 172, 94, 108, 169, 19, 87, 37, 181, 227, 189, 168, 58, 1, 5, 89, 42, 70 };
  private boolean encrypting;
  private int[] key0;
  private int[] key1;
  private int[] key2;
  private int[] key3;
  
  private int g(int paramInt1, int paramInt2)
  {
    int i = 0xFF & paramInt2 >> 8;
    int j = paramInt2 & 0xFF;
    int k = i ^ ftable[(j ^ this.key0[paramInt1])];
    int m = j ^ ftable[(k ^ this.key1[paramInt1])];
    int n = k ^ ftable[(m ^ this.key2[paramInt1])];
    return (m ^ ftable[(n ^ this.key3[paramInt1])]) + (n << 8);
  }
  
  private int h(int paramInt1, int paramInt2)
  {
    int i = paramInt2 & 0xFF;
    int j = 0xFF & paramInt2 >> 8;
    int k = i ^ ftable[(j ^ this.key3[paramInt1])];
    int m = j ^ ftable[(k ^ this.key2[paramInt1])];
    int n = k ^ ftable[(m ^ this.key1[paramInt1])];
    return n + ((m ^ ftable[(n ^ this.key0[paramInt1])]) << 8);
  }
  
  public int decryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = (paramArrayOfByte1[(paramInt1 + 0)] << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 1)]);
    int j = (paramArrayOfByte1[(paramInt1 + 2)] << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 3)]);
    int k = (paramArrayOfByte1[(paramInt1 + 4)] << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 5)]);
    int m = (paramArrayOfByte1[(paramInt1 + 6)] << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 7)]);
    int n = 31;
    for (int i1 = 0; i1 < 2; i1++)
    {
      for (int i2 = 0; i2 < 8; i2++)
      {
        int i5 = k;
        k = m;
        m = i;
        i = h(n, j);
        j = i ^ i5 ^ n + 1;
        n--;
      }
      for (int i3 = 0; i3 < 8; i3++)
      {
        int i4 = k;
        k = m;
        m = j ^ i ^ n + 1;
        i = h(n, j);
        j = i4;
        n--;
      }
    }
    paramArrayOfByte2[(paramInt2 + 0)] = ((byte)(i >> 8));
    paramArrayOfByte2[(paramInt2 + 1)] = ((byte)i);
    paramArrayOfByte2[(paramInt2 + 2)] = ((byte)(j >> 8));
    paramArrayOfByte2[(paramInt2 + 3)] = ((byte)j);
    paramArrayOfByte2[(paramInt2 + 4)] = ((byte)(k >> 8));
    paramArrayOfByte2[(paramInt2 + 5)] = ((byte)k);
    paramArrayOfByte2[(paramInt2 + 6)] = ((byte)(m >> 8));
    paramArrayOfByte2[(paramInt2 + 7)] = ((byte)m);
    return 8;
  }
  
  public int encryptBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int i = (paramArrayOfByte1[(paramInt1 + 0)] << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 1)]);
    int j = (paramArrayOfByte1[(paramInt1 + 2)] << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 3)]);
    int k = (paramArrayOfByte1[(paramInt1 + 4)] << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 5)]);
    int m = (paramArrayOfByte1[(paramInt1 + 6)] << 8) + (0xFF & paramArrayOfByte1[(paramInt1 + 7)]);
    int n = 0;
    for (int i1 = 0; i1 < 2; i1++)
    {
      for (int i2 = 0; i2 < 8; i2++)
      {
        int i5 = m;
        m = k;
        k = j;
        j = g(n, i);
        i = j ^ i5 ^ n + 1;
        n++;
      }
      for (int i3 = 0; i3 < 8; i3++)
      {
        int i4 = m;
        m = k;
        k = i ^ j ^ n + 1;
        j = g(n, i);
        i = i4;
        n++;
      }
    }
    paramArrayOfByte2[(paramInt2 + 0)] = ((byte)(i >> 8));
    paramArrayOfByte2[(paramInt2 + 1)] = ((byte)i);
    paramArrayOfByte2[(paramInt2 + 2)] = ((byte)(j >> 8));
    paramArrayOfByte2[(paramInt2 + 3)] = ((byte)j);
    paramArrayOfByte2[(paramInt2 + 4)] = ((byte)(k >> 8));
    paramArrayOfByte2[(paramInt2 + 5)] = ((byte)k);
    paramArrayOfByte2[(paramInt2 + 6)] = ((byte)(m >> 8));
    paramArrayOfByte2[(paramInt2 + 7)] = ((byte)m);
    return 8;
  }
  
  public String getAlgorithmName()
  {
    return "SKIPJACK";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (!(paramCipherParameters instanceof KeyParameter)) {
      throw new IllegalArgumentException("invalid parameter passed to SKIPJACK init - " + paramCipherParameters.getClass().getName());
    }
    byte[] arrayOfByte = ((KeyParameter)paramCipherParameters).getKey();
    this.encrypting = paramBoolean;
    this.key0 = new int[32];
    this.key1 = new int[32];
    this.key2 = new int[32];
    this.key3 = new int[32];
    for (int i = 0; i < 32; i++)
    {
      this.key0[i] = (0xFF & arrayOfByte[(i * 4 % 10)]);
      this.key1[i] = (0xFF & arrayOfByte[((1 + i * 4) % 10)]);
      this.key2[i] = (0xFF & arrayOfByte[((2 + i * 4) % 10)]);
      this.key3[i] = (0xFF & arrayOfByte[((3 + i * 4) % 10)]);
    }
  }
  
  public int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if (this.key1 == null) {
      throw new IllegalStateException("SKIPJACK engine not initialised");
    }
    if (paramInt1 + 8 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt2 + 8 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    if (this.encrypting) {
      encryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
    for (;;)
    {
      return 8;
      decryptBlock(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
    }
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.SkipjackEngine
 * JD-Core Version:    0.7.0.1
 */