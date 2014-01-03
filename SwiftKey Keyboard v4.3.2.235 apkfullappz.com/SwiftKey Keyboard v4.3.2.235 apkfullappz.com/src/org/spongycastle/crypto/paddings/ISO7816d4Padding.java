package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class ISO7816d4Padding
  implements BlockCipherPadding
{
  public int addPadding(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte.length - paramInt;
    paramArrayOfByte[paramInt] = -128;
    for (int j = paramInt + 1; j < paramArrayOfByte.length; j++) {
      paramArrayOfByte[j] = 0;
    }
    return i;
  }
  
  public String getPaddingName()
  {
    return "ISO7816-4";
  }
  
  public void init(SecureRandom paramSecureRandom)
    throws IllegalArgumentException
  {}
  
  public int padCount(byte[] paramArrayOfByte)
    throws InvalidCipherTextException
  {
    for (int i = -1 + paramArrayOfByte.length; (i > 0) && (paramArrayOfByte[i] == 0); i--) {}
    if (paramArrayOfByte[i] != -128) {
      throw new InvalidCipherTextException("pad block corrupted");
    }
    return paramArrayOfByte.length - i;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.paddings.ISO7816d4Padding
 * JD-Core Version:    0.7.0.1
 */