package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class PKCS7Padding
  implements BlockCipherPadding
{
  public int addPadding(byte[] paramArrayOfByte, int paramInt)
  {
    int i = (byte)(paramArrayOfByte.length - paramInt);
    while (paramInt < paramArrayOfByte.length)
    {
      paramArrayOfByte[paramInt] = i;
      paramInt++;
    }
    return i;
  }
  
  public String getPaddingName()
  {
    return "PKCS7";
  }
  
  public void init(SecureRandom paramSecureRandom)
    throws IllegalArgumentException
  {}
  
  public int padCount(byte[] paramArrayOfByte)
    throws InvalidCipherTextException
  {
    int i = 0xFF & paramArrayOfByte[(-1 + paramArrayOfByte.length)];
    if ((i > paramArrayOfByte.length) || (i == 0)) {
      throw new InvalidCipherTextException("pad block corrupted");
    }
    for (int j = 1; j <= i; j++) {
      if (paramArrayOfByte[(paramArrayOfByte.length - j)] != i) {
        throw new InvalidCipherTextException("pad block corrupted");
      }
    }
    return i;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.paddings.PKCS7Padding
 * JD-Core Version:    0.7.0.1
 */