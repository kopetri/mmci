package org.spongycastle.crypto;

import org.spongycastle.util.Strings;

public abstract class PBEParametersGenerator
{
  protected int iterationCount;
  protected byte[] password;
  protected byte[] salt;
  
  public static byte[] PKCS12PasswordToBytes(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar.length > 0)
    {
      arrayOfByte = new byte[2 * (1 + paramArrayOfChar.length)];
      for (int i = 0; i != paramArrayOfChar.length; i++)
      {
        arrayOfByte[(i * 2)] = ((byte)(paramArrayOfChar[i] >>> '\b'));
        arrayOfByte[(1 + i * 2)] = ((byte)paramArrayOfChar[i]);
      }
    }
    byte[] arrayOfByte = new byte[0];
    return arrayOfByte;
  }
  
  public static byte[] PKCS5PasswordToBytes(char[] paramArrayOfChar)
  {
    byte[] arrayOfByte = new byte[paramArrayOfChar.length];
    for (int i = 0; i != arrayOfByte.length; i++) {
      arrayOfByte[i] = ((byte)paramArrayOfChar[i]);
    }
    return arrayOfByte;
  }
  
  public static byte[] PKCS5PasswordToUTF8Bytes(char[] paramArrayOfChar)
  {
    return Strings.toUTF8ByteArray(paramArrayOfChar);
  }
  
  public abstract CipherParameters generateDerivedMacParameters(int paramInt);
  
  public abstract CipherParameters generateDerivedParameters(int paramInt);
  
  public abstract CipherParameters generateDerivedParameters(int paramInt1, int paramInt2);
  
  public int getIterationCount()
  {
    return this.iterationCount;
  }
  
  public byte[] getPassword()
  {
    return this.password;
  }
  
  public byte[] getSalt()
  {
    return this.salt;
  }
  
  public void init(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    this.password = paramArrayOfByte1;
    this.salt = paramArrayOfByte2;
    this.iterationCount = paramInt;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.PBEParametersGenerator
 * JD-Core Version:    0.7.0.1
 */