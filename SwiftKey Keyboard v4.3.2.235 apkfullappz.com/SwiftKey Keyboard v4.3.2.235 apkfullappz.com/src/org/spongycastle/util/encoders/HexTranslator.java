package org.spongycastle.util.encoders;

public class HexTranslator
  implements Translator
{
  private static final byte[] hexTable = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  
  public int decode(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
  {
    int i = paramInt2 / 2;
    int j = 0;
    if (j < i)
    {
      int k = paramArrayOfByte1[(paramInt1 + j * 2)];
      int m = paramArrayOfByte1[(1 + (paramInt1 + j * 2))];
      if (k < 97)
      {
        paramArrayOfByte2[paramInt3] = ((byte)(k - 48 << 4));
        label57:
        if (m >= 97) {
          break label110;
        }
        paramArrayOfByte2[paramInt3] = ((byte)(paramArrayOfByte2[paramInt3] + (byte)(m - 48)));
      }
      for (;;)
      {
        paramInt3++;
        j++;
        break;
        paramArrayOfByte2[paramInt3] = ((byte)(10 + (k - 97) << 4));
        break label57;
        label110:
        paramArrayOfByte2[paramInt3] = ((byte)(paramArrayOfByte2[paramInt3] + (byte)(10 + (m - 97))));
      }
    }
    return i;
  }
  
  public int encode(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
  {
    int i = 0;
    for (int j = 0; i < paramInt2; j += 2)
    {
      paramArrayOfByte2[(paramInt3 + j)] = hexTable[(0xF & paramArrayOfByte1[paramInt1] >> 4)];
      paramArrayOfByte2[(1 + (paramInt3 + j))] = hexTable[(0xF & paramArrayOfByte1[paramInt1])];
      paramInt1++;
      i++;
    }
    return paramInt2 * 2;
  }
  
  public int getDecodedBlockSize()
  {
    return 1;
  }
  
  public int getEncodedBlockSize()
  {
    return 2;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.encoders.HexTranslator
 * JD-Core Version:    0.7.0.1
 */