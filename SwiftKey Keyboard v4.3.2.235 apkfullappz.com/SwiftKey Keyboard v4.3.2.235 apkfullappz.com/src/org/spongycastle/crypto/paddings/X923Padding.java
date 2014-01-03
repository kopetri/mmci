package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class X923Padding
  implements BlockCipherPadding
{
  SecureRandom random = null;
  
  public int addPadding(byte[] paramArrayOfByte, int paramInt)
  {
    int i = (byte)(paramArrayOfByte.length - paramInt);
    if (paramInt < -1 + paramArrayOfByte.length)
    {
      if (this.random == null) {
        paramArrayOfByte[paramInt] = 0;
      }
      for (;;)
      {
        paramInt++;
        break;
        paramArrayOfByte[paramInt] = ((byte)this.random.nextInt());
      }
    }
    paramArrayOfByte[paramInt] = i;
    return i;
  }
  
  public String getPaddingName()
  {
    return "X9.23";
  }
  
  public void init(SecureRandom paramSecureRandom)
    throws IllegalArgumentException
  {
    this.random = paramSecureRandom;
  }
  
  public int padCount(byte[] paramArrayOfByte)
    throws InvalidCipherTextException
  {
    int i = 0xFF & paramArrayOfByte[(-1 + paramArrayOfByte.length)];
    if (i > paramArrayOfByte.length) {
      throw new InvalidCipherTextException("pad block corrupted");
    }
    return i;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.paddings.X923Padding
 * JD-Core Version:    0.7.0.1
 */