package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public abstract interface BlockCipherPadding
{
  public abstract int addPadding(byte[] paramArrayOfByte, int paramInt);
  
  public abstract String getPaddingName();
  
  public abstract void init(SecureRandom paramSecureRandom)
    throws IllegalArgumentException;
  
  public abstract int padCount(byte[] paramArrayOfByte)
    throws InvalidCipherTextException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.paddings.BlockCipherPadding
 * JD-Core Version:    0.7.0.1
 */