package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class TBCPadding
  implements BlockCipherPadding
{
  public int addPadding(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 255;
    int j = paramArrayOfByte.length - paramInt;
    int k;
    if (paramInt > 0)
    {
      if ((0x1 & paramArrayOfByte[(paramInt - 1)]) == 0) {}
      for (;;)
      {
        k = (byte)i;
        while (paramInt < paramArrayOfByte.length)
        {
          paramArrayOfByte[paramInt] = k;
          paramInt++;
        }
        i = 0;
      }
    }
    if ((0x1 & paramArrayOfByte[(-1 + paramArrayOfByte.length)]) == 0) {}
    for (;;)
    {
      k = (byte)i;
      break;
      i = 0;
    }
    return j;
  }
  
  public String getPaddingName()
  {
    return "TBC";
  }
  
  public void init(SecureRandom paramSecureRandom)
    throws IllegalArgumentException
  {}
  
  public int padCount(byte[] paramArrayOfByte)
    throws InvalidCipherTextException
  {
    int i = paramArrayOfByte[(-1 + paramArrayOfByte.length)];
    for (int j = -1 + paramArrayOfByte.length; (j > 0) && (paramArrayOfByte[(j - 1)] == i); j--) {}
    return paramArrayOfByte.length - j;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.paddings.TBCPadding
 * JD-Core Version:    0.7.0.1
 */