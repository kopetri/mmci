package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class ParametersWithSalt
  implements CipherParameters
{
  private CipherParameters parameters;
  private byte[] salt;
  
  public ParametersWithSalt(CipherParameters paramCipherParameters, byte[] paramArrayOfByte)
  {
    this(paramCipherParameters, paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public ParametersWithSalt(CipherParameters paramCipherParameters, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.salt = new byte[paramInt2];
    this.parameters = paramCipherParameters;
    System.arraycopy(paramArrayOfByte, paramInt1, this.salt, 0, paramInt2);
  }
  
  public CipherParameters getParameters()
  {
    return this.parameters;
  }
  
  public byte[] getSalt()
  {
    return this.salt;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.ParametersWithSalt
 * JD-Core Version:    0.7.0.1
 */