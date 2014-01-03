package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class ISO10126d2Padding
  implements BlockCipherPadding
{
  SecureRandom random;
  
  public int addPadding(byte[] paramArrayOfByte, int paramInt)
  {
    int i = (byte)(paramArrayOfByte.length - paramInt);
    while (paramInt < -1 + paramArrayOfByte.length)
    {
      paramArrayOfByte[paramInt] = ((byte)this.random.nextInt());
      paramInt++;
    }
    paramArrayOfByte[paramInt] = i;
    return i;
  }
  
  public String getPaddingName()
  {
    return "ISO10126-2";
  }
  
  public void init(SecureRandom paramSecureRandom)
    throws IllegalArgumentException
  {
    if (paramSecureRandom != null)
    {
      this.random = paramSecureRandom;
      return;
    }
    this.random = new SecureRandom();
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
 * Qualified Name:     org.spongycastle.crypto.paddings.ISO10126d2Padding
 * JD-Core Version:    0.7.0.1
 */