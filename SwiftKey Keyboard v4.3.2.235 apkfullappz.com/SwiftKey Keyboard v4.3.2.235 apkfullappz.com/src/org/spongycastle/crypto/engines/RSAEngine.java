package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;

public class RSAEngine
  implements AsymmetricBlockCipher
{
  private RSACoreEngine core;
  
  public int getInputBlockSize()
  {
    return this.core.getInputBlockSize();
  }
  
  public int getOutputBlockSize()
  {
    return this.core.getOutputBlockSize();
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (this.core == null) {
      this.core = new RSACoreEngine();
    }
    this.core.init(paramBoolean, paramCipherParameters);
  }
  
  public byte[] processBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.core == null) {
      throw new IllegalStateException("RSA engine not initialised");
    }
    return this.core.convertOutput(this.core.processBlock(this.core.convertInput(paramArrayOfByte, paramInt1, paramInt2)));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RSAEngine
 * JD-Core Version:    0.7.0.1
 */