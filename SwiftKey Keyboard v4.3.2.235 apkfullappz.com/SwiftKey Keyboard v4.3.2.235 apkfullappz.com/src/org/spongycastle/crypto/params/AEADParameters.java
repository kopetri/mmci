package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class AEADParameters
  implements CipherParameters
{
  private byte[] associatedText;
  private KeyParameter key;
  private int macSize;
  private byte[] nonce;
  
  public AEADParameters(KeyParameter paramKeyParameter, int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.key = paramKeyParameter;
    this.nonce = paramArrayOfByte1;
    this.macSize = paramInt;
    this.associatedText = paramArrayOfByte2;
  }
  
  public byte[] getAssociatedText()
  {
    return this.associatedText;
  }
  
  public KeyParameter getKey()
  {
    return this.key;
  }
  
  public int getMacSize()
  {
    return this.macSize;
  }
  
  public byte[] getNonce()
  {
    return this.nonce;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.AEADParameters
 * JD-Core Version:    0.7.0.1
 */