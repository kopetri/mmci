package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class ParametersWithSBox
  implements CipherParameters
{
  private CipherParameters parameters;
  private byte[] sBox;
  
  public ParametersWithSBox(CipherParameters paramCipherParameters, byte[] paramArrayOfByte)
  {
    this.parameters = paramCipherParameters;
    this.sBox = paramArrayOfByte;
  }
  
  public CipherParameters getParameters()
  {
    return this.parameters;
  }
  
  public byte[] getSBox()
  {
    return this.sBox;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.ParametersWithSBox
 * JD-Core Version:    0.7.0.1
 */