package org.spongycastle.crypto.params;

public class DESedeParameters
  extends DESParameters
{
  public static final int DES_EDE_KEY_LENGTH = 24;
  
  public DESedeParameters(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
    if (isWeakKey(paramArrayOfByte, 0, paramArrayOfByte.length)) {
      throw new IllegalArgumentException("attempt to create weak DESede key");
    }
  }
  
  public static boolean isWeakKey(byte[] paramArrayOfByte, int paramInt)
  {
    return isWeakKey(paramArrayOfByte, paramInt, paramArrayOfByte.length - paramInt);
  }
  
  public static boolean isWeakKey(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    for (int i = paramInt1; i < paramInt2; i += 8) {
      if (DESParameters.isWeakKey(paramArrayOfByte, i)) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.DESedeParameters
 * JD-Core Version:    0.7.0.1
 */