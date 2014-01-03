package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class ZeroBytePadding
  implements BlockCipherPadding
{
  public int addPadding(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte.length - paramInt;
    while (paramInt < paramArrayOfByte.length)
    {
      paramArrayOfByte[paramInt] = 0;
      paramInt++;
    }
    return i;
  }
  
  public String getPaddingName()
  {
    return "ZeroByte";
  }
  
  public void init(SecureRandom paramSecureRandom)
    throws IllegalArgumentException
  {}
  
  public int padCount(byte[] paramArrayOfByte)
    throws InvalidCipherTextException
  {
    for (int i = paramArrayOfByte.length; (i > 0) && (paramArrayOfByte[(i - 1)] == 0); i--) {}
    return paramArrayOfByte.length - i;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.paddings.ZeroBytePadding
 * JD-Core Version:    0.7.0.1
 */