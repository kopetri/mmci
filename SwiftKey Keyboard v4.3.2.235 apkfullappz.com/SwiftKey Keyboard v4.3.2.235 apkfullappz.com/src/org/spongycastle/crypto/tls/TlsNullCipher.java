package org.spongycastle.crypto.tls;

public class TlsNullCipher
  implements TlsCipher
{
  protected byte[] copyData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    return arrayOfByte;
  }
  
  public byte[] decodeCiphertext(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return copyData(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public byte[] encodePlaintext(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return copyData(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsNullCipher
 * JD-Core Version:    0.7.0.1
 */